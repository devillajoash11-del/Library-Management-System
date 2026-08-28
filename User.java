// Pangunahing klase ng lahat ng user sa library system.
// Dito inilalagay ang karaniwang impormasyon at profile operations ng user.
public class User {
    // Natatanging identifier ng user.
    protected String userID;
    // Buong pangalan ng user.
    protected String name;
    // Email address ng user.
    protected String email;
    // Password na ginagamit sa pag-login.
    protected String password;
    // Uri o role ng user sa system.
    protected String role;

    // Gumagawa ng user profile gamit ang pangunahing impormasyon nito.
    public User(String userID, String name, String email, String password, String role) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Ibinabalik ang user ID.
    public String getUserID() {
        return userID;
    }

    // Ibinabalik ang buong pangalan ng user.
    public String getName() {
        return name;
    }

    // Ibinabalik ang email ng user.
    public String getEmail() {
        return email;
    }

    // Ibinabalik ang password ng user.
    public String getPassword() {
        return password;
    }

    // Ibinabalik ang role ng user.
    public String getRole() {
        return role;
    }

    // Ina-update ang pangalan at email ng user profile.
    public void updateProfile(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Gumagawa ng maikling text representation para sa system output.
    public String toString() {
        return "User{" + "userID='" + userID + '\'' + ", name='" + name + '\'' + ", role='" + role + '\'' + '}';
    }
}
