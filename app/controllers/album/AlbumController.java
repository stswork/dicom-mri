package controllers.album;

import com.avaje.ebean.annotation.Transactional;
import models.album.Image;
import models.response.ResponseMessage;
import models.response.ResponseMessageType;
import models.user.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by Sagar Gopale on 3/9/14.
 */
public class AlbumController extends Controller {

    @Transactional
    public static Result removeImage() {
        Long id = 0L;
        User u = (User) ctx().args.get("user");
        try {
            id = Long.parseLong(request().body().asFormUrlEncoded().get("id")[0]);
        } catch(Exception e) {
            return badRequest(Json.toJson(new ResponseMessage(400, "Invalid parameters passed!", ResponseMessageType.NOT_FOUND)));
        }
        Image i = Image.find.byId(id);
        if(i == null)
            return notFound(Json.toJson(new ResponseMessage(404, "Image not found", ResponseMessageType.NOT_FOUND)));
        i.delete();
        return ok(Json.toJson(new ResponseMessage(200, "Image successfully removed", ResponseMessageType.SUCCESSFUL)));
    }

}
