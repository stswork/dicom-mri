package controllers.patient;

import actions.Authenticated;
import actors.mail.MailSenderActor;
import actors.messaging.exotel.SendSmsActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.Query;
import com.avaje.ebean.annotation.Transactional;
import models.actor.messaging.exotel.SendSmsActorMessage;
import models.amazon.s3.S3File;
import models.comment.Comment;
import models.actor.mailer.Mail;
import models.review.Review;
import models.album.Album;
import models.album.Image;
import models.patient.Gender;
import models.patient.Patient;
import models.request.patient.PatientRequest;
import models.response.ResponseMessage;
import models.response.ResponseMessageType;
import models.user.User;
import models.user.UserType;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import play.Logger;
import play.data.Form;
import play.libs.Akka;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import service.TelestrokeWebService;
import utils.DicomManager;
import utils.FileExtensionCheckUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Sagar Gopale on 3/8/14.
 */
public class PatientController extends Controller {

    private static final DateTimeFormatter fmt = DateTimeFormat.forPattern("dd-MMM-yyyy");
    public static final String telestroke_url = "telestroke.url.name";
    //Will be used to display the form to save a patient or edit a patient. Id will be passed if it is edit patient function
    @With(Authenticated.class)
    public static Result save(Long id) {
        models.response.user.User u = (models.response.user.User) ctx().args.get("user");
        Review r = null;
        r = (id > 0) ? Ebean.find(Review.class)
                .fetch("album")
                .fetch("album.imageList")
                .fetch("album.commentList")
                .fetch("album.commentList.commentedBy")
                .fetch("album.patient")
                .fetch("assignedTo")
                .where(
                                Expr.eq("id", id)
                ).findUnique() : null;
        List<User> users= Ebean.find(User.class).where(
                Expr.and(
                        Expr.ne("id", 1),
                        Expr.eq("userType", UserType.DOCTOR)
                )
        ).findList();
		StringBuilder sb = new StringBuilder();
        if(r != null) {
			List<Comment> comments = r.getAlbum().getCommentList();
			for(Comment c: comments){
				if(c.getCommentedBy().getUserType().equals(UserType.MRI_SCAN_CENTER))
					sb.append(c.getMessage());
			}
		} else {
			r = new Review();
		}
        return ok(views.html.patient.step1.render("Patient", u, users, r, sb.toString()));
    }

    //Will be used to post data of a patient including the images. Same function will be used to post data after editing the patient info.
    // To remove a particular image from a edit patient screen a separate call to remove image is created in AlbumController.
    @Transactional
    @With(Authenticated.class)
    public static Result stepOne() {
        models.response.user.User u = (models.response.user.User) ctx().args.get("user");
        User loggedInUser = User.find.byId(u.getId());
        List<Http.MultipartFormData.FilePart> fps;
        Album a = null;
        Comment c = null;
        Patient p;
        //Http.MultipartFormData fd = request().body().asMultipartFormData();
        Map<String, String[]> map = request().body().asFormUrlEncoded();
        Long id = Long.valueOf(map.get("id") == null ? "0" : map.get("id")[0]);
        Long albumId = Long.valueOf(map.get("albumId") == null ? "0" : map.get("albumId")[0]);
        String fullName = map.get("fullName") == null ? StringUtils.EMPTY : map.get("fullName")[0];
        String email = map.get("email") == null  ? StringUtils.EMPTY : map.get("email")[0];
        Integer age = Integer.valueOf(map.get("age") == null  ? "0" : map.get("age")[0]);
        String comment = map.get("comment") == null  ? StringUtils.EMPTY : map.get("comment")[0];
        String gender = map.get("gender") == null  ? StringUtils.EMPTY : map.get("gender")[0];
        String[] dIds = map.get("doctorIds");
        Form<PatientRequest> pf = null;
        /*if(pf.hasErrors())
            return badRequest(Json.toJson(new ResponseMessage(400, "Invalid form submission!", ResponseMessageType.BAD_REQUEST)));
*/
        /*try {
            pf = Form.form(PatientRequest.class).bindFromRequest();
            //GETTING PATIENT REQUEST OBJECT FROM DYNAMIC FORM
           // pr = pf.get();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        if(id <= 0) {
            /*//GETTING LIST OF FILES FROM MULTIPART FORM
            fps = fd.getFiles();
            if(fps.size() <= 0 || fps == null)
                return badRequest(Json.toJson(new ResponseMessage(400, "Invalid form submission!", ResponseMessageType.BAD_REQUEST)));
*/
            //SAVING PATIENT
            p = new Patient(fullName, email, age);
            p.setCreatedBy(loggedInUser);
            try {
                p.setGender(Gender.valueOf(gender));
            } catch (Exception e) {
                Logger.info("NO ENUM GENERATED FOR THE STRING " + gender);
                return badRequest(Json.toJson(new ResponseMessage(400, "Please select a proper gender!", ResponseMessageType.BAD_REQUEST)));
            }
            p.save();

            //SAVING ALBUM
            a = new Album(p);
            a.setCreatedBy(loggedInUser);
            a.save();

            /*//SAVING IMAGES
            for(Http.MultipartFormData.FilePart fp : fps) {
                File file = null;
                Image i = null;

                //CHECKING IF FILE HAS VALID EXTENSION
                boolean _validFile = FileExtensionCheckUtil.isValidFile(fp.getFilename());
                if(!_validFile)
                    return badRequest(Json.toJson(new ResponseMessage(400, "Invalid file submission!", ResponseMessageType.BAD_REQUEST)));
                String _extension = FileExtensionCheckUtil.getFileExtension(fp.getFilename());

                //SAVING FILE TO AMAZON S3 BUCKET
                if(StringUtils.equalsIgnoreCase(_extension, "dcm")){
                    file = fp.getFile();
                    BufferedImage jpegImage = DicomManager.getJpegFromDicom(file);
                    if(jpegImage == null)
                        return internalServerError(Json.toJson(new ResponseMessage(500, "Some error occurred! Please try again.", ResponseMessageType.INTERNAL_SERVER_ERROR)));
                    String _s3Url = DicomManager.writeDicomJpegToS3(jpegImage, file.getName());
                    if(StringUtils.isEmpty(_s3Url))
                        return internalServerError(Json.toJson(new ResponseMessage(500, "Some error occurred! Please try again.", ResponseMessageType.INTERNAL_SERVER_ERROR)));
                    i = new Image(_s3Url, a);
                } else if (StringUtils.equalsIgnoreCase(_extension, "jpg") || StringUtils.equalsIgnoreCase(_extension, "jpeg") ||  StringUtils.equalsIgnoreCase(_extension, "png")||  StringUtils.equalsIgnoreCase(_extension, "bmp")) {
                    file = fp.getFile();
                    String _s3Url = DicomManager.writeJpegToS3(file,_extension);
                    if(StringUtils.isEmpty(_s3Url))
                        return internalServerError(Json.toJson(new ResponseMessage(500, "Some error occurred! Please try again.", ResponseMessageType.INTERNAL_SERVER_ERROR)));
                    i = new Image(_s3Url, a);
                } else {
                    return badRequest(Json.toJson(new ResponseMessage(400, "Invalid file submission!", ResponseMessageType.BAD_REQUEST)));
                }
                try {
                    i.setCreatedBy(loggedInUser);
                    i.save();
                    *//*if(!file1.exists())
                        file1.createNewFile();
                    TelestrokeWebService.uploadFile(file1, i.getId());*//*
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/
            c = new Comment(comment, a, loggedInUser);
            c.setCreatedBy(loggedInUser);
            c.save();
        } else {
            p = Ebean.find(Patient.class).fetch("albumList").where(
                    Expr.eq("id", id)
            ).findUnique();
            if(p == null)
                return notFound(Json.toJson(new ResponseMessage(404, "No such patient found", ResponseMessageType.NOT_FOUND)));
            p.setFullName(fullName);
            p.setEmail(email);
            try {
                p.setGender(Gender.valueOf(gender));
            } catch (Exception e) {
                Logger.info("NO ENUM GENERATED FOR THE STRING " + gender);
                return badRequest(Json.toJson(new ResponseMessage(400, "Invalid form submission!", ResponseMessageType.BAD_REQUEST)));
            }
            p.setAge(age);
            p.setModifiedBy(loggedInUser);
            p.update();
            c = Ebean.find(Comment.class).fetch("album").fetch("commentedBy").where(
                    Expr.and(
                            Expr.and(
                                    Expr.eq("album.id", albumId),
                                    Expr.eq("commentedBy.id", u.getId())
                            ),
                            Expr.eq("message", comment)
                    )
            ).setMaxRows(1).findUnique();
            if(c == null) {
                a = Album.find.byId(albumId);
                c = new Comment(comment, a, loggedInUser);
                c.setCreatedBy(loggedInUser);
                c.save();
            }
        }

        //SAVING REVIEW LIST

        if(dIds != null && dIds.length > 0) {
            for(String did: dIds) {
                Long _did = Long.valueOf(did);
                User user = User.find.byId(_did);
                if(user == null)
                    return badRequest(Json.toJson(new ResponseMessage(400, "Invalid doctor selection!", ResponseMessageType.BAD_REQUEST)));
                Review r = null;
                if(albumId <= 0) {
                    r = new Review(false, a, user);
                    r.setCreatedBy(loggedInUser);
                    r.save();
                } else {
                    r = Ebean.find(Review.class).fetch("album").fetch("assignedTo").where(
                            Expr.and(
                                    Expr.eq("album.id", albumId),
                                    Expr.eq("assignedTo.id", _did)
                            )
                    ).setMaxRows(1).findUnique();
                    if(r == null) {
                        r = new Review(false, a, user);
                        r.setCreatedBy(loggedInUser);
                        r.save();
                    }
                }
                try {

                    String url=telestroke_url+"?username="+user.getUserName()+"&password="+user.getPassword()+"&id="+p.getId();

                    //SENDING REGISTRATION SMS
                    String exotelSmsBody = "Message from Telestroke, \nNew patient details " + url + "\nfrom  " + u.getDisplayName();
                    SendSmsActorMessage ssam = new SendSmsActorMessage(exotelSmsBody, user.getPhone());
                    ActorRef ssa = Akka.system().actorOf(new Props(SendSmsActor.class));
                    ssa.tell(ssam, ssa);


                    //SENDING EMAIL
                    Mail mail = new Mail(p.getFullName(), p.getEmail(),p.getAge(),p.getGender(), user.getDisplayName(), user.getUserName(),u.getDisplayName(),u.getLocation(),u.getPhone(),url);
                    ActorRef mailActor = Akka.system().actorOf(Props.create(MailSenderActor.class));
                    mailActor.tell(mail,mailActor);//, routes.Assets.at("images/email-template/logo.png").absoluteURL(request()), routes.Assets.at("images/email-template/tagline.gif").absoluteURL(request()), routes.Assets.at("images/email-template/content_box_bott.gif").absoluteURL(request())
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        //GETTING LIST OF PATIENTS
        List<Review> reviewList = new ArrayList<Review>();
        List<models.response.review.Review> reviews = new ArrayList<models.response.review.Review>();
        Query<Review> query = Ebean.find(Review.class).fetch("assignedTo").fetch("album").fetch("album.commentList").fetch("album.commentList.commentedBy").fetch("album.imageList").fetch("album.patient");
        if(User.find.byId(u.getId()).getUserType().equals(UserType.SUPER_USER))
            reviewList = query.where(Expr.eq("reviewed", false)).findList();
        else {
            reviewList = query.where(
                    Expr.and(
                            Expr.or(
                                    Expr.eq("assignedTo.id", u.getId()),
                                    Expr.eq("createdBy.id", u.getId())
                            ),
                            Expr.eq("reviewed", false)
                    )
            ).findList();
        }
        if(reviewList == null || reviewList.size() <= 0)
            reviewList = new ArrayList<Review>();
        for(Review r: reviewList) {
            List<models.response.album.Image> images = new ArrayList<models.response.album.Image>();
            List<models.response.comment.Comment> comments = new ArrayList<models.response.comment.Comment>();
            models.response.album.Album album = null;

            if(r.getAlbum() != null) {
                for(Image i: r.getAlbum().getImageList()) {
                    models.response.album.Image image = new models.response.album.Image(i.getId(), i.getImageUrl());
                    images.add(image);
                }
                for (models.comment.Comment cmt: r.getAlbum().getCommentList()) {
                    if(cmt.getCommentedBy().getUserType().equals(UserType.DOCTOR)) {
                        models.response.comment.Comment comment1 = new models.response.comment.Comment(cmt.getId(), cmt.getMessage());
                        comments.add(comment1);
                    }
                }
                album = new models.response.album.Album(r.getAlbum().getId(), comments, images);
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
            review.setCreated(fmt.print(r.getCreated().getTime()));
            reviews.add(review);
        }
        return ok(views.html.patient.step2.render("Data list", u, a == null ? 0 : a.getId()));
    }

    @Transactional
    @With(Authenticated.class)
    public static Result stepTwo() {
        models.response.user.User u = (models.response.user.User) ctx().args.get("user");
        User loggedInUser = User.find.byId(u.getId());
        Http.MultipartFormData fd = request().body().asMultipartFormData();
        Map<String, String[]> map = request().body().asMultipartFormData().asFormUrlEncoded();
        Long albumId = Long.valueOf(StringUtils.isEmpty(map.get("albumId")[0]) ? "0" : map.get("albumId")[0]);
        if(albumId <= 0)
            return badRequest(Json.toJson(new ResponseMessage(400, "Invalid parameters passed!", ResponseMessageType.BAD_REQUEST)));
        Album a = Album.find.byId(albumId);
        if(a == null)
            return badRequest(Json.toJson(new ResponseMessage(400, "Invalid parameters passed!", ResponseMessageType.BAD_REQUEST)));

        List<Http.MultipartFormData.FilePart> fps;
        try {
            //GETTING LIST OF FILES FROM MULTIPART FORM
            fps = fd.getFiles();
            if(fps.size() <= 0 || fps == null)
                return badRequest(Json.toJson(new ResponseMessage(400, "Invalid form submission!", ResponseMessageType.BAD_REQUEST)));
            //SAVING IMAGES
            for(Http.MultipartFormData.FilePart fp : fps) {
                File file = null;
                Image i = null;

                //CHECKING IF FILE HAS VALID EXTENSION
                boolean _validFile = FileExtensionCheckUtil.isValidFile(fp.getFilename());
                if(!_validFile)
                    return badRequest(Json.toJson(new ResponseMessage(400, "Invalid file submission!", ResponseMessageType.BAD_REQUEST)));
                String _extension = FileExtensionCheckUtil.getFileExtension(fp.getFilename());

                //SAVING FILE TO AMAZON S3 BUCKET
                if(StringUtils.equalsIgnoreCase(_extension, "dcm")){
                    file = fp.getFile();
                    BufferedImage jpegImage = DicomManager.getJpegFromDicom(file);
                    if(jpegImage == null)
                        return internalServerError(Json.toJson(new ResponseMessage(500, "Oops! Some error occurred. Please try again.", ResponseMessageType.INTERNAL_SERVER_ERROR)));
                    String _s3Url = DicomManager.writeDicomJpegToS3(jpegImage, file.getName());
                    if(StringUtils.isEmpty(_s3Url))
                        return internalServerError(Json.toJson(new ResponseMessage(500, "Oops! Some error occurred. Please try again.", ResponseMessageType.INTERNAL_SERVER_ERROR)));
                    i = new Image(_s3Url, a);
                } else if (StringUtils.equalsIgnoreCase(_extension, "jpg") || StringUtils.equalsIgnoreCase(_extension, "jpeg") ||  StringUtils.equalsIgnoreCase(_extension, "png")||  StringUtils.equalsIgnoreCase(_extension, "bmp")) {
                    file = fp.getFile();
                    String _s3Url = DicomManager.writeJpegToS3(file,_extension);
                    if(StringUtils.isEmpty(_s3Url))
                        return internalServerError(Json.toJson(new ResponseMessage(500, "Oops! Some error occurred. Please try again.", ResponseMessageType.INTERNAL_SERVER_ERROR)));
                    i = new Image(_s3Url, a);
                } else {
                    return badRequest(Json.toJson(new ResponseMessage(400, "Invalid file submission!", ResponseMessageType.BAD_REQUEST)));
                }
                try {
                    i.setCreatedBy(loggedInUser);
                    i.save();
                    /*if(!file1.exists())
                        file1.createNewFile();
                    TelestrokeWebService.uploadFile(file1, i.getId());*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            Logger.error(e.getMessage(), e);
        }
        return ok(Json.toJson(new ResponseMessage(200, "Patient successfully added!", ResponseMessageType.SUCCESSFUL)));
    }
}
