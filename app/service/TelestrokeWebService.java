package service;

import play.Logger;
import play.Play;
import play.libs.F;
import play.libs.WS;
import play.mvc.Result;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static play.mvc.Results.ok;

public class TelestrokeWebService {

    private static final String GS_BUCKET_NAME = Play.application().configuration().getString("google.bucket.name");
    private static final String GS_BASE_URL = Play.application().configuration().getString("google.bucket.base.url");
    private static final String GS_UPLOAD_URL = Play.application().configuration().getString("gs.upload.url");
    private static final String GS_ACCESS_KEY = Play.application().configuration().getString("google.bucket.access.key");
    private static final String GS_ACL_PRIVATE = "project-private";
    private static final String GS_ACL_PUBLIC = "public-read";
    private static final long TIMEOUT = 60L;

    public static boolean uploadFile(File file, Long imageId) {

        String _uploadUrl = GS_BASE_URL + GS_UPLOAD_URL + imageId;
        WS.Response response = null;
        boolean _written = false;
        try {
            response = WS.url(_uploadUrl)
                    .setHeader("Content-Type", "image/jpeg")
                    .put(file).get(TIMEOUT, TimeUnit.SECONDS);
            Logger.info("BODY : " + response.getBody());
            Logger.info("STATUS : " + response.getStatus());
            if(response.getStatus() == 200)
                _written = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return _written;
    }
}
