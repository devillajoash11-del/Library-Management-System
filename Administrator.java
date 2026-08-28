import java.util.List;

// Uri ng user na namamahala sa users at reports ng library.
public class Administrator extends User {
    // Natatanging administrator identifier.
    private String adminID;

    // Gumagawa ng administrator profile.
    public Administrator(String userID, String name, String email, String password, String adminID) {
        super(userID, name, email, password, "Administrator");
        this.adminID = adminID;
    }

    // Ibinabalik ang administrator ID.
    public String getAdminID() {
        return adminID;
    }

    // Ipinapakita ang lahat ng users para sa administration.
    public String manageUsers(List<User> users) {
        StringBuilder output = new StringBuilder();
        for (User user : users) {
            output.append(user).append("\n");
        }
        return output.toString();
    }

    // Ipinapakita ang lahat ng reports ng library.
    public String viewReports(List<Report> reports) {
        StringBuilder output = new StringBuilder();
        for (Report report : reports) {
            output.append(report).append("\n");
        }
        return output.toString();
    }
}
