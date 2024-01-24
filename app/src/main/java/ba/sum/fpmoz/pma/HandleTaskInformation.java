package ba.sum.fpmoz.pma;

import android.media.Image;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class HandleTaskInformation implements Serializable {
    public String name;
    public String description;
    public String dateOfCreation;
    public String dateOfCompletion;
    public String status;
    public Map<String, Boolean> members;
    public List<Image> images;

    public String creatorUUID;

    public HandleTaskInformation() {}

    public HandleTaskInformation(String name, String description, String dateOfCreation, String dateOfCompletion, String status, Map<String, Boolean> members, List<Image> images, String creatorUUID) {
        this.name = name;
        this.description = description;
        this.dateOfCreation = dateOfCreation;
        this.dateOfCompletion = dateOfCompletion;
        this.status = status;
        this.members = members;
        this.images = images;
        this.creatorUUID = creatorUUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getDateOfCompletion() {
        return dateOfCompletion;
    }

    public void setDateOfCompletion(String dateOfCompletion) {
        this.dateOfCompletion = dateOfCompletion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Boolean> getMembers() {
        return members;
    }

    public void setMembers(Map<String, Boolean> members) {
        this.members = members;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getCreatorUUID() {
        return creatorUUID;
    }

    public void setCreatorUUID(String creatorUUID) {
        this.creatorUUID = creatorUUID;
    }
}
