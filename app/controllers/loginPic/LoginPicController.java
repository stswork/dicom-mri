package controllers.loginPic;

import actions.Authenticated;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.annotation.Transactional;
import models.album.Image;
import models.album.Login;
import models.amazon.s3.S3File;
import models.response.ResponseMessage;
import models.response.ResponseMessageType;
import models.user.User;
import models.user.UserType;
import org.apache.commons.lang3.StringUtils;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 3/20/14
 * Time: 10:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginPicController extends Controller {

    @Transactional
    @With(Authenticated.class)
    public static Result save(long id){
        models.response.user.User u = (models.response.user.User) ctx().args.get("user");
        if(!UserType.valueOf(u.getUserType()).equals(UserType.SUPER_USER))
            return redirect(controllers.routes.AuthenticationController.login());
        Login loginPic = null;
        loginPic = (id > 0) ? Ebean.find(Login.class)
                .where(
                        Expr.eq("id", id)
                ).findUnique() : null;
        if(loginPic == null)
            loginPic = new Login();
        return ok(views.html.admin.save.render("Administrator", loginPic, u));
    }

    @Transactional
    @With(Authenticated.class)
    public static Result handleSave() {
        models.response.user.User u = (models.response.user.User) ctx().args.get("user");
        User loggedInUser = User.find.byId(u.getId());
        Login loginPic;
        File file = null;
        Image i = null;
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart uploadFilePart = body.getFile("jpegFC");
        if (uploadFilePart != null) {
            try{
                S3File s3File = new S3File();
                s3File.name = uploadFilePart.getFilename();
                s3File.file = uploadFilePart.getFile();
                s3File.save();
                loginPic = new Login();
                loginPic.setImageUrl(s3File.getUrl().toString());
                loginPic.setCreatedBy(loggedInUser);
                loginPic.save();
                return redirect(controllers.loginPic.routes.LoginPicController.save(0));
            } catch (Exception e) {
                e.printStackTrace();
                badRequest(Json.toJson(new ResponseMessage(400, "File Upload Error Submission!", ResponseMessageType.BAD_REQUEST)));
            }
        }
        else {
            badRequest(Json.toJson(new ResponseMessage(400, "File Upload Error Submission!", ResponseMessageType.BAD_REQUEST)));
        }
        return redirect(controllers.loginPic.routes.LoginPicController.save(0));

    }





    }
