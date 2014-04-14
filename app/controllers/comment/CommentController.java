package controllers.comment;

import actions.Authenticated;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.annotation.Transactional;
import models.album.Album;
import models.comment.Comment;
import models.response.ResponseMessage;
import models.response.ResponseMessageType;
import models.review.Review;
import models.user.User;
import org.apache.commons.lang3.StringUtils;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Sagar Gopale on 3/12/14.
 */
public class CommentController extends Controller {

    @Transactional
    @With(Authenticated.class)
    public static Result handleSave() {

        Comment c = null;
        models.response.user.User u = (models.response.user.User) ctx().args.get("user");
        User loggedInUser = User.find.byId(u.getId());
        Map<String, String[]> map = request().body().asFormUrlEncoded();
        String message = StringUtils.isEmpty(map.get("message")[0]) ? StringUtils.EMPTY : map.get("message")[0];
        Long albumId = Long.valueOf(StringUtils.isEmpty(map.get("aId")[0]) ? "0" : map.get("aId")[0]);
        Long reviewId = Long.valueOf(StringUtils.isEmpty(map.get("rId")[0]) ? "0" : map.get("rId")[0]);
        Review r = Review.find.byId(reviewId);
        Album a = Album.find.byId(albumId);
        if(r == null || a == null)
            return badRequest(Json.toJson(new ResponseMessage(400, "Invalid parameters passed!", ResponseMessageType.BAD_REQUEST)));
        c = new Comment(message, a, loggedInUser);
        c.setCreatedBy(loggedInUser);
        c.save();
        r.setReviewed(true);
        r.update();
        return redirect(controllers.patient.routes.PatientController.save(reviewId));
    }
}
