package ba.sum.fpmoz.pma;

public class HandleTaskImageInformation {

    public String userUUID;
    public String url;
    public String description;

    public String usersFullName;

    public String taskID;

    public HandleTaskImageInformation() {}

    public HandleTaskImageInformation(String UserUUID, String url, String description) {
        this.userUUID = UserUUID;
        this.url = url;
        this.description = description;
    }

    public HandleTaskImageInformation(String userUUID, String url, String description, String usersFullName) {
        this.userUUID = userUUID;
        this.url = url;
        this.description = description;
        this.usersFullName = usersFullName;
    }

    public HandleTaskImageInformation(String userUUID, String url, String description, String usersFullName, String taskID) {
        this.userUUID = userUUID;
        this.url = url;
        this.description = description;
        this.usersFullName = usersFullName;
        this.taskID = taskID;
    }
}
