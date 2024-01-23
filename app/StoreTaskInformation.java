import java.util.Map;

public class StoreTaskInformation {
    public String taskName;
    public String taskDescription;
    public String dateOfCreation;
    public String dateOfCompletion;
    public String status;
    public Map<String, Boolean> members;


    public StoreTaskInformation() {}

    public StoreTaskInformation(String taskName, String taskDescription, String dateOfCreation, String dateOfCompletion, String status, Map<String, Boolean> members) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.dateOfCreation = dateOfCreation;
        this.dateOfCompletion = dateOfCompletion;
        this.status = status;
        this.members = members;
    }
}
