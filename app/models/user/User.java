package models.user;

import models.Status;
import models.review.Review;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sagar Gopale on 3/8/14.
 */
@Entity
@Table(name="o_user")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long id;

    @Constraints.Required
    private String userName;

    @Constraints.Required
    private String password;

    @Constraints.Required
    private String displayName;

    @Constraints.Required
    private UserType userType = UserType.DOCTOR;

    @OneToMany(mappedBy = "assignedTo")
    private List<Review> reviewList = new ArrayList<Review>();

    @Constraints.Required
    private String location;

    @Constraints.Required
    private String phone;

    @OneToOne
    private User createdBy;

    @OneToOne
    private User modifiedBy;

    @Constraints.Required
    private Status status = Status.ACTIVE;

    public User() {
    }

    public User(String userName, String password, String displayName, UserType userType) {
        this.userName = userName;
        this.password = password;
        this.displayName = displayName;
        this.userType = userType;
    }

    public User(String userName, String password, String displayName, UserType userType, String location, String phone) {
        this.userName = userName;
        this.password = password;
        this.displayName = displayName;
        this.userType = userType;
        this.location = location;
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setPassword(String password) { this.password = password; }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

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

    public static Finder<Long, User> find = new Finder<Long, User>(
            Long.class, User.class
    );

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
