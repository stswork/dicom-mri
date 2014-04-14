package models.response.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Sagar Gopale on 3/11/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    public long id;
    public String userName;
    public String displayName;
    public String userType;
    public String location;
    public String phone;

    public User() {
    }

    public User(long id, String userName, String displayName, String userType) {
        this.id = id;
        this.userName = userName;
        this.displayName = displayName;
        this.userType = userType;
    }

    public User(long id, String userName, String displayName, String userType, String location, String phone) {
        this.id = id;
        this.userName = userName;
        this.displayName = displayName;
        this.userType = userType;
        this.location = location;
        this.phone = phone;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
