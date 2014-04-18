package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import models.patient.Patient;
import models.user.User;
import models.user.UserType;
import org.apache.commons.lang3.StringUtils;
import play.mvc.*;

import java.util.List;

public class Application extends Controller {
  
    /*public static Result login() {
        if (StringUtils.isEmpty(session("user"))) {
            return ok(views.html.index.render("Welcome"));
        } else {
            models.response.user.User u = (models.response.user.User) ctx().args.get("user");
            User user = User.find.byId(u.getId());
            if(user.getUserType().equals(UserType.DOCTOR))
                return redirect(controllers.review.routes.ReviewController.getPatientsToReview());
            else if(user.getUserType().equals(UserType.MRI_SCAN_CENTER))
                return redirect(controllers.review.routes.ReviewController.getPatientsToReview());
        }
        return ok(views.html.index.render("Welcome"));
    }*/


    /*public static Result patientSave() {

        //User u=User.find(UserType.DOCTOR);
        //User user = User.find.fetch();

        List<User> user= Ebean.find(User.class).where(Expr.eq("userType", UserType.DOCTOR)).findList();
        Patient patient = Patient.find.byId((long)121);

        return ok(views.html.patient.save.render("Patient", user, patient));
    }*/
}
