package models.request.patient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sagar Gopale on 3/8/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientRequest {

    public long id = 0;
    public long albumId = 0;
    public String fullName;
    public String email;
    public Integer age;
    public String gender;
    public String comment;
    public List<Long> doctorIds = new ArrayList<Long>();

    public PatientRequest() {
    }

    public PatientRequest(long id, long albumId, String fullName, String email, Integer age, String gender, String comment, List<Long> doctorIds) {
        this.id = id;
        this.albumId = albumId;
        this.fullName = fullName;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.comment = comment;
        this.doctorIds = doctorIds;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Long> getDoctorIds() {
        return doctorIds;
    }

    public void setDoctorIds(List<Long> doctorIds) {
        this.doctorIds = doctorIds;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }
}
