import java.util.List;

// Administrator controls higher-level library operations and reports.
public class Administrator extends User {
    private String adminID;

    public Administrator(String userID, String name, String email, String password, String adminID) {
        super(userID, name, email, password, "Administrator");
        this.adminID = adminID;
    }

    public String getAdminID() {
        return adminID;
    }

    public String manageUsers(List<User> users) {
        StringBuilder output = new StringBuilder();
        for (User user : users) {
            output.append(user).append("\n");
        }
        return output.toString();
    }

    public String viewReports(List<Report> reports) {
        StringBuilder output = new StringBuilder();
        for (Report report : reports) {
            output.append(report).append("\n");
        }
        return output.toString();
    }
}
