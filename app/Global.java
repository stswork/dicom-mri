import com.avaje.ebean.Ebean;
import models.user.User;
import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;

import java.util.List;
import java.util.Map;

/**
 * Created by Sagar Gopale on 3/8/14.
 */
public class Global extends GlobalSettings {

    public void onStart(Application application) {
       Fixtures.insertUsers(application);
    }

    static class Fixtures {

        public static void insertUsers(Application application) {

            if(User.find.findRowCount() == 0) {
                Map<String, List<Object>> initialUsers = (Map<String, List<Object>>) Yaml.load("fixtures/users/initial-data.yml");
                Ebean.save(initialUsers.get("users"));
            }
        }
    }
}
