package models.patient;

import models.album.Album;
import models.user.User;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.joda.time.DateTime;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sagar Gopale on 3/8/14.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Patient extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Constraints.Required
    private String fullName;

    @Constraints.Required
    private String email;

    @Constraints.Required
    private Integer age;

    @Constraints.Required
    private Gender gender;

    @OneToMany(mappedBy = "patient")
    private List<Album> albumList = new ArrayList<Album>();

    @Formats.DateTime(pattern = "MM/dd/yy")
    private Timestamp created = new Timestamp(DateTime.now().toDate().getTime());

    @OneToOne
    private User createdBy;

    @OneToOne
    private User modifiedBy;

    public Patient() {
    }

    public Patient(String fullName, String email, Integer age) {
        this.fullName = fullName;
        this.email = email;
        this.age = age;
    }

    public Patient(String fullName, String email, Integer age, Gender gender) {
        this.fullName = fullName;
        this.email = email;
        this.age = age;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Album> getAlbumList() {
        return albumList;
    }

    public void setAlbumList(List<Album> albumList) {
        this.albumList = albumList;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public static Finder<Long, Patient> find = new Finder<Long, Patient>(
            Long.class, Patient.class
    );

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(User modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
