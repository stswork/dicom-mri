package models.response.album;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by Sagar Gopale on 3/11/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Image {

    public long id;
    public String imageUrl;

    public Image() {
    }

    public Image(long id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
