package models.response.review;

import models.response.album.Album;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.sql.Timestamp;

/**
 * Created by Sagar Gopale on 3/11/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Review {

    public Long id;
    public Long assignedToId;
    public String assignedToName;
    public Long patientId;
    public String patientName;
    public String email;
    public String age;
    public String gender;
    public Album album;
    public Timestamp created;

    public Review() {
    }

    public Review(Long id, Long assignedToId, String assignedToName, Long patientId, String patientName, String email, String age, String gender, Album album, Timestamp created) {
        this.id = id;
        this.assignedToId = assignedToId;
        this.assignedToName = assignedToName;
        this.patientId = patientId;
        this.patientName = patientName;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.album = album;
        this.created = created;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getAssignedToName() {
        return assignedToName;
    }

    public void setAssignedToName(String assignedToName) {
        this.assignedToName = assignedToName;
    }
}
