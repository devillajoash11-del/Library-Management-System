import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

// Lumang standalone login window na gumagamit ng credential map.
// Ang pangunahing application login ay nasa LibraryManagementSystem.
public class LoginPage implements ActionListener {
    // Mga visual component ng login window.
    private final JFrame frame;
    private final JButton loginButton;
    private final JButton resetButton;
    private final JTextField userIDField;
    private final JPasswordField userPasswordField;
    private final JLabel userIDLabel;
    private final JLabel userPasswordLabel;
    private final JLabel messageLabel;
    // Credential map na ginagamit ng standalone login.
    private final Map<String, String> loginInfo;

    // Gumagawa ng login form at ikinakabit ang mga event handler nito.
    public LoginPage(Map<String, String> loginInfoOriginal) {
        this.loginInfo = loginInfoOriginal;

        frame = new JFrame("Library Login");
        userIDLabel = new JLabel("User ID:");
        userPasswordLabel = new JLabel("Password:");
        messageLabel = new JLabel();
        userIDField = new JTextField();
        userPasswordField = new JPasswordField();
        loginButton = new JButton("Login");
        resetButton = new JButton("Reset");

        userIDLabel.setBounds(50, 100, 100, 25);
        userPasswordLabel.setBounds(50, 150, 100, 25);
        userIDField.setBounds(150, 100, 200, 25);
        userPasswordField.setBounds(150, 150, 200, 25);

        messageLabel.setBounds(125, 250, 250, 35);
        messageLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));

        loginButton.setBounds(125, 200, 100, 30);
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);

        resetButton.setBounds(225, 200, 100, 30);
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);

        frame.add(userIDLabel);
        frame.add(userPasswordLabel);
        frame.add(messageLabel);
        frame.add(userIDField);
        frame.add(userPasswordField);
        frame.add(loginButton);
        frame.add(resetButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(480, 360);
        frame.setMinimumSize(new java.awt.Dimension(420, 320));
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    // Pinoproseso ang reset at login actions ng user.
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            userIDField.setText("");
            userPasswordField.setText("");
            messageLabel.setText("");
            return;
        }

        if (e.getSource() == loginButton) {
            String userID = userIDField.getText().trim();
            String password = new String(userPasswordField.getPassword());

            if (userID.isEmpty() || password.isEmpty()) {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Please enter your ID and password");
                return;
            }

            if (loginInfo.containsKey(userID)) {
                if (loginInfo.get(userID).equals(password)) {
                    messageLabel.setForeground(new Color(0, 128, 0));
                    messageLabel.setText("Login successful");
                    frame.dispose();
                    new WelcomePage(userID);
                } else {
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setText("Wrong password");
                }
            } else {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Username not found");
            }
        }
    }
}