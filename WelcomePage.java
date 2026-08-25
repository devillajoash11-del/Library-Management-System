import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class WelcomePage {
    private final JFrame frame;
    private final JLabel welcomeLabel;

    public WelcomePage(String userID) {
        frame = new JFrame("Welcome");
        welcomeLabel = new JLabel();

        welcomeLabel.setText("Hello, " + userID + "!");
        welcomeLabel.setBounds(0, 0, 300, 35);
        welcomeLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));

        frame.add(welcomeLabel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
