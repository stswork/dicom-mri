package models.request.authentication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Sagar Gopale on 3/8/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationRequest {

    public String userName;
    public String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
