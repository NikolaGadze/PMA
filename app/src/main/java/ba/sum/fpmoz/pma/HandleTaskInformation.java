package ba.sum.fpmoz.pma;

import android.media.Image;

import java.util.List;
import java.util.Map;

public class HandleTaskInformation {
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
}
