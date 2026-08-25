// Base class for every user in the library system.
public class User {
    protected String userID;
    protected String name;
    protected String email;
    protected String password;
    protected String role;

    public User(String userID, String name, String email, String password, String role) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void updateProfile(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String toString() {
        return "User{" + "userID='" + userID + '\'' + ", name='" + name + '\'' + ", role='" + role + '\'' + '}';
    }
}
