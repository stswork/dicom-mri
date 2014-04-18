package models.actor.mailer;

import models.patient.Gender;

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
    private Integer pAge;
    private Gender pGender;
    private String dName;
    private String dEmail;
    private String mName;
    private String mLocation;
    private String mPhone;
    private String logoUri;
    private String tagUri;
    private String contentBoxUri;
    private String url;

    public Mail() {
    }

    public Mail(String pName, String pEmail, String dName, String dEmail) {
        this.pName = pName;
        this.pEmail = pEmail;
        this.dName = dName;
        this.dEmail = dEmail;
    }



    public Mail(String pName, String pEmail, Integer pAge, Gender pGender, String dName, String dEmail, String mName, String mLocation, String mPhone,String url) {
        this.pName = pName;
        this.pEmail = pEmail;
        this.pAge = pAge;
        this.pGender = pGender;
        this.dName = dName;
        this.dEmail = dEmail;
        this.mName = mName;
        this.mLocation = mLocation;
        this.mPhone = mPhone;
        this.url=url;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getpAge() {
        return pAge;
    }

    public void setpAge(Integer pAge) {
        this.pAge = pAge;
    }

    public Gender getpGender() {
        return pGender;
    }

    public void setpGender(Gender pGender) {
        this.pGender = pGender;
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

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmLocation() {
        return mLocation;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }
}

