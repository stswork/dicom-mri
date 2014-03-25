package controllers;

import actions.Authenticated;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.request.authentication.AuthenticationRequest;
import models.response.ResponseMessage;
import models.response.ResponseMessageType;
import models.user.User;
import models.user.UserType;
import org.apache.commons.lang3.StringUtils;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

/**
 * Created by Sagar Gopale on 3/8/14.
 */
public class AuthenticationController extends Controller {

    public static Result login() {
        if (StringUtils.isEmpty(session("user"))) {
            return ok(views.html.index.render("Welcome"));
        } else {
            return redirect(controllers.review.routes.ReviewController.getPatientsToReview());
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result handleLogin() {

        try {
            AuthenticationRequest ar = null;
            JsonNode body = request().body().asJson();
            ObjectMapper mapper = new ObjectMapper();
            User u = null;
            if (body != null)
                ar = Json.fromJson(body, AuthenticationRequest.class);
            if (ar == null)
                return badRequest(Json.toJson(new ResponseMessage(400, "Invalid parameters passed!", ResponseMessageType.BAD_REQUEST)));
                u = Ebean.find(User.class).where(
                        Expr.and(
                                Expr.eq("userName", ar.getUserName()),
                                Expr.eq("password", ar.getPassword())
                        )
                ).setMaxRows(1).findUnique();
            if(u == null)
                return notFound(Json.toJson(new ResponseMessage(404, "No such user found!", ResponseMessageType.NOT_FOUND)));
            models.response.user.User _responseUser = new models.response.user.User(u.getId(), u.getUserName(), u.getDisplayName(), u.getUserType().name().toUpperCase());
            session("user", StringUtils.toString(org.apache.commons.codec.binary.Base64.encodeBase64(mapper.writeValueAsString(_responseUser).getBytes()), "UTF-8"));
            return ok(Json.toJson(new ResponseMessage(200, "User logged in!", ResponseMessageType.SUCCESSFUL)));
        } catch (Exception e) {
            e.printStackTrace();
            return badRequest(Json.toJson(new ResponseMessage(400, "No such user found!", ResponseMessageType.BAD_REQUEST)));
        }
    }

    @With(Authenticated.class)
    public static Result checkLogin(){

        models.response.user.User u = (models.response.user.User) ctx().args.get("user");
        User loggedInUser = User.find.byId(u.getId());

        return ok();
    }

    public static Result logout() {
        session().clear();
        return redirect(controllers.routes.AuthenticationController.login());
    }
}
