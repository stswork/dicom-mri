package models.response.album;

import models.response.comment.Comment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sagar Gopale on 3/11/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Album {

    public long id;
    public List<Comment> comments = new ArrayList<Comment>();
    public List<Image> images = new ArrayList<Image>();

    public Album() {
    }

    public Album(long id, List<Comment> comments, List<Image> images) {
        this.id = id;
        this.comments = comments;
        this.images = images;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
