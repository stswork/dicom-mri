package service;

import play.Play;

public class TelestrokeWebService {

    private static final String GOOGLE_BUCKET_ACCESS_KEY = Play.application().configuration().getString("google.bucket.key");
    private static final String GOOGLE_BUCKET_NAME = Play.application().configuration().getString("google.bucket.name");
    private static final String GOOGLE_ACL_PRIVATE = "project-private";
    private static final String GOOGLE_ACL_PUBLIC = "public-read";

    /*public static boolean writeFile() {

        boolean _written = false;

        GcsService gcsService = GcsServiceFactory.createGcsService();
        GcsFilename filename = new GcsFilename(GOOGLE_BUCKET_NAME, "");
        GcsFileOptions builder = new GcsFileOptions.Builder()
                .acl(GOOGLE_ACL_PRIVATE)
                .mimeType("image/jpeg")
                .build();



        return _written;
    }*/
}
