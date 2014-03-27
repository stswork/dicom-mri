package models.album;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import models.user.User;
import org.joda.time.DateTime;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 3/20/14
 * Time: 10:17 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Login extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Constraints.Required
    private String imageUrl;

    @Formats.DateTime(pattern = "MM/dd/yy")
    private Timestamp created = new Timestamp(DateTime.now().toDate().getTime());

    @OneToOne
    private User createdBy;

    @OneToOne
    private User modifiedBy;

    public Login() {
    }

    public Login(Long id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
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
