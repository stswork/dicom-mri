package models.comment;

import models.album.Album;
import models.user.User;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.joda.time.DateTime;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sagar Gopale on 3/8/14.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Constraints.Required
    private String message;

    @ManyToOne
    private Album album;

    @ManyToOne
    private User commentedBy;

    @OneToOne
    private User createdBy;

    @OneToOne
    private User modifiedBy;

    @Formats.DateTime(pattern = "MM/dd/yy")
    private Timestamp created = new Timestamp(DateTime.now().toDate().getTime());

    public Comment() {
    }

    public Comment(String message, Album album, User commentedBy) {
        this.message = message;
        this.album = album;
        this.commentedBy = commentedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public User getCommentedBy() {
        return commentedBy;
    }

    public void setCommentedBy(User commentedBy) {
        this.commentedBy = commentedBy;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public static Finder<Long, Comment> find = new Finder<Long, Comment>(
            Long.class, Comment.class
    );

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
}
