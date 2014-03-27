package models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Sagar Gopale on 3/8/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseMessage {

    public int status;
    public String message;
    public ResponseMessageType responseMessageType;

    public ResponseMessage() {
    }

    public ResponseMessage(int status, String message, ResponseMessageType responseMessageType) {
        this.status = status;
        this.message = message;
        this.responseMessageType = responseMessageType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseMessageType getResponseMessageType() {
        return responseMessageType;
    }

    public void setResponseMessageType(ResponseMessageType responseMessageType) {
        this.responseMessageType = responseMessageType;
    }
}
