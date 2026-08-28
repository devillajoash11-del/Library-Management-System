import javax.swing.SwingUtilities;

// Entry point ng application.
public class Main {
    // Sinisimulan ang Swing UI sa tamang Event Dispatch Thread.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryManagementSystem());
    }
}
