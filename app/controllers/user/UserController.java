package controllers.user;

import actions.Authenticated;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.annotation.Transactional;
import models.response.ResponseMessage;
import models.response.ResponseMessageType;
import models.user.User;
import models.user.UserType;
import org.apache.commons.lang3.StringUtils;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 3/22/14
 * Time: 10:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserController extends Controller {

    @With(Authenticated.class)
    public static Result save(long id){
        models.response.user.User u = (models.response.user.User) ctx().args.get("user");
        if(!UserType.valueOf(u.getUserType()).equals(UserType.SUPER_USER))
            return redirect(controllers.routes.AuthenticationController.login());
        User user = null;
        user = (id > 0) ? Ebean.find(User.class)
                .where(
                        Expr.eq("id", id)
                ).findUnique() : null;
        if(user == null){
            user = new User();
        }
        return ok(views.html.user.save.render("Administrator", user, u));
    }


    @Transactional
    @With(Authenticated.class)
    public static Result handleSave(){
        models.response.user.User u = (models.response.user.User) ctx().args.get("user");
        User loggedInUser = User.find.byId(u.getId());
        models.user.User user =null;
        Map<String, String[]> map = request().body().asMultipartFormData().asFormUrlEncoded();
        //Long id = Long.valueOf(StringUtils.isEmpty(map.get("id")[0]) ? "0" : map.get("id")[0]);
        String userName=StringUtils.isEmpty(map.get("userName")[0]) ? StringUtils.EMPTY : map.get("userName")[0];
        String password=StringUtils.isEmpty(map.get("password")[0]) ? StringUtils.EMPTY : map.get("password")[0];
        String displayName=StringUtils.isEmpty(map.get("displayName")[0]) ? StringUtils.EMPTY : map.get("displayName")[0];
        String userType=StringUtils.isEmpty(map.get("userType")[0]) ? StringUtils.EMPTY : map.get("userType")[0];

        user = new User(userName,password,displayName,UserType.valueOf(userType));
        /*user.setUserName(userName);
        user.setPassword(password);
        user.setDisplayName(displayName);
        try {
            user.setUserType(UserType.valueOf(userType));
        }
        catch (Exception e) {
            Logger.info("NO ENUM GENERATED FOR THE STRING " + userType);
            return badRequest(Json.toJson(new ResponseMessage(400, "Invalid form submission!", ResponseMessageType.BAD_REQUEST)));
        }*/
        user.setCreatedBy(loggedInUser);
        user.save();
        return redirect(controllers.user.routes.UserController.save(0));
    }
}
