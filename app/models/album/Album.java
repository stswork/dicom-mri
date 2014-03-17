package models.album;

import models.comment.Comment;
import models.review.Review;
import models.patient.Patient;
import models.user.User;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.joda.time.DateTime;
import play.data.format.Formats;
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
public class Album extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany
    private List<Comment> commentList = new ArrayList<Comment>();

    @OneToMany
    private List<Image> imageList = new ArrayList<Image>();

    @ManyToOne
    private Patient patient;

    @OneToMany(mappedBy = "album")
    private List<Review> reviewList = new ArrayList<Review>();

    @Formats.DateTime(pattern = "MM/dd/yy")
    private Timestamp created = new Timestamp(DateTime.now().toDate().getTime());

    @OneToOne
    private User createdBy;

    @OneToOne
    private User modifiedBy;

    public Album() {
    }

    public Album(Patient patient) {
        this.patient = patient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public static Finder<Long, Album> find = new Finder<Long, Album>(
            Long.class, Album.class
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
