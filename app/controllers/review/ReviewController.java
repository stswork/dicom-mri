package controllers.review;

import actions.Authenticated;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.annotation.Transactional;
import models.album.Image;
import models.response.album.Album;
import models.response.comment.Comment;
import models.review.Review;
import models.response.ResponseMessage;
import models.response.ResponseMessageType;
import models.user.User;
import org.apache.commons.lang3.StringUtils;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sagar Gopale on 3/9/14.
 */
public class ReviewController extends Controller {

    @Transactional
    @With(Authenticated.class)
    public static Result getPatientsToReview() {
        List<Review> reviewList = new ArrayList<Review>();
        List<models.response.review.Review> reviews = new ArrayList<models.response.review.Review>();
        models.response.user.User u = (models.response.user.User) ctx().args.get("user");
        reviewList = Ebean.find(Review.class).fetch("assignedTo").fetch("album").fetch("album.imageList").fetch("album.patient").where(
                Expr.or(
                        Expr.and(
                                Expr.eq("assignedTo.id", u.getId()),
                                Expr.eq("reviewed", false)
                        ),
                        Expr.and(
                                Expr.eq("createdBy.id", u.getId()),
                                Expr.eq("reviewed", false)
                        )
                )
        ).findList();
        if(reviewList == null || reviewList.size() <= 0)
            reviewList = new ArrayList<Review>();
        for(Review r: reviewList) {
            List<models.response.album.Image> images = new ArrayList<models.response.album.Image>();
            List<Comment> comments = new ArrayList<Comment>();
            Album album = null;

            if(r.getAlbum() != null) {
                for(Image i: r.getAlbum().getImageList()) {
                    models.response.album.Image image = new models.response.album.Image(i.getId(), i.getImageUrl());
                    images.add(image);
                }
                for (models.comment.Comment comment: r.getAlbum().getCommentList()) {
                    Comment c = new Comment(comment.getId(), comment.getMessage());
                    comments.add(c);
                }
                album = new Album(r.getAlbum().getId(), comments, images);
            }
            models.response.review.Review review = new models.response.review.Review();
            review.setId(r.getId());
            review.setAssignedToId(r.getAssignedTo().getId());
            review.setAssignedToName(r.getAssignedTo() == null || StringUtils.isEmpty(r.getAssignedTo().getDisplayName()) ? StringUtils.EMPTY : r.getAssignedTo().getDisplayName());
            review.setPatientId(r.getAlbum() == null || r.getAlbum().getPatient() == null ? null : r.getAlbum().getPatient().getId());
            review.setPatientName(r.getAlbum() == null || r.getAlbum().getPatient() == null || StringUtils.isEmpty(r.getAlbum().getPatient().getFullName()) ? StringUtils.EMPTY : r.getAlbum().getPatient().getFullName());
            review.setEmail(r.getAlbum() == null || r.getAlbum().getPatient() == null || StringUtils.isEmpty(r.getAlbum().getPatient().getEmail()) ? StringUtils.EMPTY : r.getAlbum().getPatient().getEmail());
            review.setAge(r.getAlbum() == null || r.getAlbum().getPatient() == null || r.getAlbum().getPatient().getAge() == null ? StringUtils.EMPTY : r.getAlbum().getPatient().getAge().toString());
            review.setGender(r.getAlbum() == null || r.getAlbum().getPatient() == null ? StringUtils.EMPTY : r.getAlbum().getPatient().getGender().name());
            review.setAlbum(album);
            review.setCreated(r.getCreated());
            reviews.add(review);
        }
        return ok(views.html.review.list.render("Data list", u, reviews));
    }

    @Transactional
    @With(Authenticated.class)
    public static Result getPatientsReviewed() {

        List<Review> reviewList = new ArrayList<Review>();
        List<models.response.review.Review> reviews = new ArrayList<models.response.review.Review>();
        models.response.user.User u = (models.response.user.User) ctx().args.get("user");
        reviewList = Ebean.find(Review.class).fetch("assignedTo").fetch("album").fetch("album.imageList").fetch("album.patient").where(
                Expr.or(
                        Expr.and(
                                Expr.eq("assignedTo.id", u.getId()),
                                Expr.eq("reviewed", true)
                        ),
                        Expr.and(
                                Expr.eq("createdBy.id", u.getId()),
                                Expr.eq("reviewed", true)
                        )
                )
        ).findList();
        if(reviewList == null || reviewList.size() <= 0)
            reviewList = new ArrayList<Review>();
        for(Review r: reviewList) {
            List<models.response.album.Image> images = new ArrayList<models.response.album.Image>();
            List<Comment> comments = new ArrayList<Comment>();
            Album album = null;

            if(r.getAlbum() != null) {
                for(Image i: r.getAlbum().getImageList()) {
                    models.response.album.Image image = new models.response.album.Image(i.getId(), i.getImageUrl());
                    images.add(image);
                }
                for (models.comment.Comment comment: r.getAlbum().getCommentList()) {
                    Comment c = new Comment(comment.getId(), comment.getMessage());
                    comments.add(c);
                }
                album = new Album(r.getAlbum().getId(), comments, images);
            }
            models.response.review.Review review = new models.response.review.Review();
            review.setId(r.getId());
            review.setAssignedToId(r.getAssignedTo().getId());
            review.setPatientId(r.getAlbum() == null || r.getAlbum().getPatient() == null ? null : r.getAlbum().getPatient().getId());
            review.setPatientName(r.getAlbum() == null || r.getAlbum().getPatient() == null || StringUtils.isEmpty(r.getAlbum().getPatient().getFullName()) ? StringUtils.EMPTY : r.getAlbum().getPatient().getFullName());
            review.setEmail(r.getAlbum() == null || r.getAlbum().getPatient() == null || StringUtils.isEmpty(r.getAlbum().getPatient().getEmail()) ? StringUtils.EMPTY : r.getAlbum().getPatient().getEmail());
            review.setAge(r.getAlbum() == null || r.getAlbum().getPatient() == null || r.getAlbum().getPatient().getAge() == null ? StringUtils.EMPTY : r.getAlbum().getPatient().getAge().toString());
            review.setGender(r.getAlbum() == null || r.getAlbum().getPatient() == null ? StringUtils.EMPTY : r.getAlbum().getPatient().getGender().name());
            review.setAlbum(album);
            review.setCreated(r.getCreated());
            reviews.add(review);
        }
        return ok(views.html.review.list.render("Data list", u, reviews));
    }

    @Transactional
    public static Result review() {
        Long id = 0L;
        User u = (User) ctx().args.get("user");
        try {
            id = Long.parseLong(request().body().asFormUrlEncoded().get("id")[0]);
        } catch(Exception e) {
            return badRequest(Json.toJson(new ResponseMessage(400, "Invalid parameters passed!", ResponseMessageType.NOT_FOUND)));
        }
        Review r = Review.find.byId(id);
        if(r == null)
            return notFound(Json.toJson(new ResponseMessage(404, "No such review found.", ResponseMessageType.NOT_FOUND)));
        r.setReviewed(true);
        r.setModifiedBy(u);
        r.update();
        return ok(Json.toJson(new ResponseMessage(200, "Patient successfully reviewed!", ResponseMessageType.SUCCESSFUL)));
    }
}
