package models.helper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import models.patient.Patient;
import models.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sagar Gopale on 5/2/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StepTwoCache {

    public List<User> doctors = new ArrayList<User>();
    public Patient patient;
    public Long reviewId;

    public StepTwoCache() {
    }

    public StepTwoCache(List<User> doctors, Patient patient, Long reviewId) {
        this.doctors = doctors;
        this.patient = patient;
        this.reviewId = reviewId;
    }

    public List<User> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<User> doctors) {
        this.doctors = doctors;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setP(Patient patient) {
        this.patient = patient;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }
}
