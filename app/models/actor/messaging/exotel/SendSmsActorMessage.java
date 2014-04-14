package models.actor.messaging.exotel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 4/14/14
 * Time: 11:27 PM
 * To change this template use File | Settings | File Templates.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SendSmsActorMessage {

    public String body;
    public String phone;

    public SendSmsActorMessage() {
    }

    public SendSmsActorMessage(String body, String phone) {
        this.body = body;
        this.phone = phone;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}