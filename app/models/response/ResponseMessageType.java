package models.response;

import com.avaje.ebean.annotation.EnumValue;

/**
 * Created by Sagar Gopale on 3/8/14.
 */
public enum ResponseMessageType {
    SUCCESSFUL,
    BAD_REQUEST,
    NOT_FOUND,
    INTERNAL_SERVER_ERROR,
    UNAUTHORIZED
}
