package models.response.user;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by Sagar Gopale on 3/11/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    public long id;
    public String userName;
    public String displayName;
    public String userType;

    public User() {
    }

    public User(long id, String userName, String displayName, String userType) {
        this.id = id;
        this.userName = userName;
        this.displayName = displayName;
        this.userType = userType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
