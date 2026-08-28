import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;

// Simpleng welcome window para sa user na matagumpay na nag-login.
public class WelcomePage {
    // Main window at text label ng welcome screen.
    private final JFrame frame;
    private final JLabel welcomeLabel;

    // Gumagawa at ipinapakita ang welcome window para sa user ID.
    public WelcomePage(String userID) {
        frame = new JFrame("Welcome");
        welcomeLabel = new JLabel();

        welcomeLabel.setText("Hello, " + userID + "!");
        welcomeLabel.setBounds(0, 0, 300, 35);
        welcomeLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));

        frame.add(welcomeLabel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(480, 360);
        frame.setMinimumSize(new java.awt.Dimension(420, 320));
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
