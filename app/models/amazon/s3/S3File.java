package models.amazon.s3;

/**
 * Created by Sagar Gopale on 12/20/13.
 */

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import play.Logger;
import play.db.ebean.Model;
import plugins.S3Plugin;

import javax.persistence.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class S3File extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public UUID id;

    private String bucket;

    public String name;

    @Transient
    public File file;

    public S3File() {
    }

    public S3File(UUID id, String name, File file) {
        this.id = id;
        this.name = name;
        this.file = file;
    }

    public URL getUrl() throws MalformedURLException {
        return new URL("https://s3-ap-southeast-1.amazonaws.com/" + bucket + "/" + getActualFileName());
    }

    private String getActualFileName() {
        return "/" + name;
    }

    @Override
    public void save() {
        if (S3Plugin.amazonS3 == null) {
            Logger.error("Could not save because amazonS3 was null");
            throw new RuntimeException("Could not save");
        }
        else {
            this.bucket = S3Plugin.s3Bucket;

            //super.save(); // assigns an id

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, getActualFileName(), file);
            putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead); // public for all
            S3Plugin.amazonS3.putObject(putObjectRequest); // upload file
        }
    }

    @Override
    public void delete() {
        if (S3Plugin.amazonS3 == null) {
            Logger.error("Could not delete because amazonS3 was null");
            throw new RuntimeException("Could not delete");
        }
        else {
            S3Plugin.amazonS3.deleteObject(bucket, getActualFileName());
            super.delete();
        }
    }

}