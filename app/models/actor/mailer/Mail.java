package models.actor.mailer;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 3/16/14
 * Time: 9:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class Mail {

    private String pName;
    private String pEmail;
    private String dName;
    private String dEmail;
    private String logoUri;
    private String tagUri;
    private String contentBoxUri;

    public Mail() {
    }

    public Mail(String pName, String pEmail, String dName, String dEmail) {
        this.pName = pName;
        this.pEmail = pEmail;
        this.dName = dName;
        this.dEmail = dEmail;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpEmail() {
        return pEmail;
    }

    public void setpEmail(String pEmail) {
        this.pEmail = pEmail;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getdEmail() {
        return dEmail;
    }

    public void setdEmail(String dEmail) {
        this.dEmail = dEmail;
    }

    public String getLogoUri() {
        return logoUri;
    }

    public void setLogoUri(String logoUri) {
        this.logoUri = logoUri;
    }

    public String getTagUri() {
        return tagUri;
    }

    public void setTagUri(String tagUri) {
        this.tagUri = tagUri;
    }

    public String getContentBoxUri() {
        return contentBoxUri;
    }

    public void setContentBoxUri(String contentBoxUri) {
        this.contentBoxUri = contentBoxUri;
    }
}

