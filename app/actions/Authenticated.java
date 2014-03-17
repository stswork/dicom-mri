package actions;


import com.fasterxml.jackson.databind.ObjectMapper;
import models.response.user.User;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import play.Logger;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.SimpleResult;

public class Authenticated extends Action.Simple {

    @Override
    public F.Promise<SimpleResult> call(Http.Context ctx) throws Throwable {
        String _user = ctx.session().get("user");
        if(!StringUtils.isEmpty(_user)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                User u = mapper.readValue(StringUtils.toString(Base64.decodeBase64(_user.getBytes()), "UTF-8"), User.class);
                ctx.args.put("user", u);
                return delegate.call(ctx);
            } catch(Exception e) {
                e.printStackTrace();
                Logger.error(e.getMessage(), e);
                return F.Promise.pure(redirect(controllers.routes.AuthenticationController.login()));
            }
        } else {
            return F.Promise.pure(redirect(controllers.routes.AuthenticationController.login()));
        }
    }
}
