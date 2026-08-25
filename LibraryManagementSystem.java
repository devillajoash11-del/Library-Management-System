import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

// Main library app using Swing.
// This class ties together the system model with a simple GUI to simulate the workflow shown in the class diagram.
public class LibraryManagementSystem extends JFrame {
    private final Map<String, User> users = new HashMap<>();
    private final List<Book> books = new ArrayList<>();
    private final List<BorrowRequest> requests = new ArrayList<>();
    private final List<BorrowTransaction> transactions = new ArrayList<>();
    private final List<Notification> notifications = new ArrayList<>();
    private final List<Report> reports = new ArrayList<>();

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);
    private final JTextArea outputArea = new JTextArea();
    private final JTextField loginIDField = new JTextField();
    private final JPasswordField loginPasswordField = new JPasswordField();
    private User currentUser;

    public LibraryManagementSystem() {
        initializeData();
        setTitle("Library Management System");
        setSize(980, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top heading
        JLabel titleLabel = new JLabel("Library Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        /*
         * The application uses CardLayout so the user can move between the login screen
         * and the dashboard screen depending on the role of the logged-in user.
         */
        mainPanel.add(createLoginPanel(), "login");
        mainPanel.add(createStudentPanel(), "student");
        mainPanel.add(createLibrarianPanel(), "librarian");
        mainPanel.add(createAdminPanel(), "admin");

        add(mainPanel, BorderLayout.CENTER);

        // Output panel at the bottom
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        outputArea.setBackground(new Color(248, 249, 250));
        outputArea.setBorder(BorderFactory.createTitledBorder("System Output"));
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        showLoginScreen();
        setVisible(true);
    }

    // Fills the system with sample users, books, and reports so the app is ready to use immediately.
    private void initializeData() {
        users.put("S001", new Student("S001", "Ana Dela Cruz", "ana@email.com", "1234", "ST001", "BSIT", "A"));
        users.put("S002", new Student("S002", "Ben Santos", "ben@email.com", "1234", "ST002", "BSCS", "B"));
        users.put("L001", new Librarian("L001", "Maria Lopez", "maria@email.com", "admin123", "LIB001"));
        users.put("A001", new Administrator("A001", "Carlos Reyes", "carlos@email.com", "root123", "ADM001"));
        // Test credential required by the user: email as user ID and password pizza123
        users.put("user@gmail.com", new Student("user@gmail.com", "Test User", "user@gmail.com", "pizza123", "ST100", "General", "1"));

        // Sample books with cover images
        books.add(new Book("B001", "Clean Code", "Robert C. Martin", "Programming", "9780132350884", true, "https://images-na.ssl-images-amazon.com/images/P/0132350882.01.L.jpg"));
        books.add(new Book("B002", "Head First Java", "Kathy Sierra", "Programming", "9780596009205", true, "https://images-na.ssl-images-amazon.com/images/P/0596009208.01.L.jpg"));
        books.add(new Book("B003", "The Alchemist", "Paulo Coelho", "Fiction", "9780061122415", true, "https://images-na.ssl-images-amazon.com/images/P/0061122416.01.L.jpg"));
        books.add(new Book("B004", "Database Design", "Michael J. Hernandez", "Database", "9780132144987", false, "https://images-na.ssl-images-amazon.com/images/P/0132144984.01.L.jpg"));
        books.add(new Book("B005", "Effective Java", "Joshua Bloch", "Programming", "9780134685991", true, "https://images-na.ssl-images-amazon.com/images/P/0134685997.01.L.jpg"));
        books.add(new Book("B006", "Design Patterns", "Gang of Four", "Programming", "9780201633610", true, "https://images-na.ssl-images-amazon.com/images/P/0201633612.01.L.jpg"));
        books.add(new Book("B007", "The Pragmatic Programmer", "Hunt & Thomas", "Programming", "9780201616224", true, "https://images-na.ssl-images-amazon.com/images/P/0201616224.01.L.jpg"));
        books.add(new Book("B008", "1984", "George Orwell", "Fiction", "9780451524935", true, "https://images-na.ssl-images-amazon.com/images/P/0451524934.01.L.jpg"));
        books.add(new Book("B009", "To Kill a Mockingbird", "Harper Lee", "Fiction", "9780061120084", true, "https://images-na.ssl-images-amazon.com/images/P/0061120081.01.L.jpg"));
        books.add(new Book("B010", "The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "9780743273565", true, "https://images-na.ssl-images-amazon.com/images/P/0743273567.01.L.jpg"));
        books.add(new Book("B011", "Project Management", "Harold Kerzner", "Business", "9781118022276", true, "https://images-na.ssl-images-amazon.com/images/P/1118022270.01.L.jpg"));
        books.add(new Book("B012", "The Lean Startup", "Eric Ries", "Business", "9780670921602", true, "https://images-na.ssl-images-amazon.com/images/P/0670921602.01.L.jpg"));

        transactions.add(new BorrowTransaction("T001", "B004", "S001", new Date(), new Date(System.currentTimeMillis() + 86400000L), "Active"));

        notifications.add(new Notification("N001", "Welcome to the library system.", new Date(), "Sent"));

        reports.add(new Report("R001", "Borrow Summary", "Total active borrow transactions: 1", new Date()));
        reports.add(new Report("R002", "Inventory Overview", "Total books available: 11", new Date()));
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 200, 30, 200));

        JLabel userLabel = new JLabel("User ID:");
        JLabel passwordLabel = new JLabel("Password:");
        JButton loginButton = new JButton("Login");
        JButton resetButton = new JButton("Reset");

        loginButton.addActionListener(e -> handleLogin());
        resetButton.addActionListener(e -> {
            loginIDField.setText("");
            loginPasswordField.setText("");
            outputArea.setText("Input fields reset.");
        });

        panel.add(userLabel);
        panel.add(loginIDField);
        panel.add(passwordLabel);
        panel.add(loginPasswordField);
        panel.add(new JLabel());
        panel.add(loginButton);
        panel.add(new JLabel());
        panel.add(resetButton);
        panel.add(new JLabel());
        panel.add(new JLabel());

        return panel;
    }

    private JPanel createStudentPanel() {
        JPanel panel = new JPanel(new CardLayout());
        
        // Button panel
        JPanel buttonPanel = new JPanel(new BorderLayout(10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel gridPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        JButton viewBooksButton = new JButton("View Books");
        JButton searchBooksButton = new JButton("Search Books");
        JButton borrowBookButton = new JButton("Borrow Book");
        JButton viewHistoryButton = new JButton("View History");
        JButton notificationsButton = new JButton("View Notifications");
        JButton logoutButton = new JButton("Logout");

        viewBooksButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) panel.getLayout();
            cl.show(panel, "books");
        });
        searchBooksButton.addActionListener(e -> {
            String keyword = JOptionPane.showInputDialog(this, "Enter keyword to search:", "Search Book", JOptionPane.PLAIN_MESSAGE);
            if (keyword != null && !keyword.trim().isEmpty()) {
                Student student = (Student) currentUser;
                outputArea.setText(student.searchBook(keyword, books));
            }
        });
        borrowBookButton.addActionListener(e -> {
            String bookID = JOptionPane.showInputDialog(this, "Enter Book ID to borrow:", "Borrow Book", JOptionPane.PLAIN_MESSAGE);
            if (bookID != null) {
                outputArea.setText(requestBorrow(bookID));
            }
        });
        viewHistoryButton.addActionListener(e -> outputArea.setText(getBorrowHistory()));
        notificationsButton.addActionListener(e -> outputArea.setText(getNotifications()));
        logoutButton.addActionListener(e -> showLoginScreen());

        gridPanel.add(viewBooksButton);
        gridPanel.add(searchBooksButton);
        gridPanel.add(borrowBookButton);
        gridPanel.add(viewHistoryButton);
        gridPanel.add(notificationsButton);
        gridPanel.add(logoutButton);

        buttonPanel.add(gridPanel, BorderLayout.NORTH);
        
        // Books display panel
        JPanel booksPanel = createBooksDisplayPanel(() -> {
            CardLayout cl = (CardLayout) panel.getLayout();
            cl.show(panel, "buttons");
        });
        
        panel.add(buttonPanel, "buttons");
        panel.add(booksPanel, "books");
        
        return panel;
    }

    private JPanel createLibrarianPanel() {
        JPanel panel = new JPanel(new CardLayout());
        
        // Button panel
        JPanel buttonPanel = new JPanel(new BorderLayout(10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel gridPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        JButton viewRequestsButton = new JButton("View Requests");
        JButton approveRequestButton = new JButton("Approve Request");
        JButton rejectRequestButton = new JButton("Reject Request");
        JButton viewBooksButton = new JButton("Manage Books");
        JButton processReturnButton = new JButton("Process Return");
        JButton logoutButton = new JButton("Logout");

        viewRequestsButton.addActionListener(e -> outputArea.setText(getRequestsList()));
        approveRequestButton.addActionListener(e -> {
            String requestID = JOptionPane.showInputDialog(this, "Enter request ID:", "Approve Request", JOptionPane.PLAIN_MESSAGE);
            if (requestID != null) {
                outputArea.setText(approveRequest(requestID));
            }
        });
        rejectRequestButton.addActionListener(e -> {
            String requestID = JOptionPane.showInputDialog(this, "Enter request ID:", "Reject Request", JOptionPane.PLAIN_MESSAGE);
            if (requestID != null) {
                outputArea.setText(rejectRequest(requestID));
            }
        });
        viewBooksButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) panel.getLayout();
            cl.show(panel, "books");
        });
        processReturnButton.addActionListener(e -> {
            String transactionID = JOptionPane.showInputDialog(this, "Enter transaction ID:", "Process Return", JOptionPane.PLAIN_MESSAGE);
            if (transactionID != null) {
                outputArea.setText(processReturn(transactionID));
            }
        });
        logoutButton.addActionListener(e -> showLoginScreen());

        gridPanel.add(viewRequestsButton);
        gridPanel.add(approveRequestButton);
        gridPanel.add(rejectRequestButton);
        gridPanel.add(viewBooksButton);
        gridPanel.add(processReturnButton);
        gridPanel.add(logoutButton);

        buttonPanel.add(gridPanel, BorderLayout.NORTH);
        
        // Books display panel
        JPanel booksPanel = createBooksDisplayPanel(() -> {
            CardLayout cl = (CardLayout) panel.getLayout();
            cl.show(panel, "buttons");
        });
        
        panel.add(buttonPanel, "buttons");
        panel.add(booksPanel, "books");
        
        return panel;
    }

    private JPanel createAdminPanel() {
        JPanel panel = new JPanel(new CardLayout());
        
        // Button panel
        JPanel buttonPanel = new JPanel(new BorderLayout(10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel gridPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        JButton viewUsersButton = new JButton("View Users");
        JButton viewBooksButton = new JButton("View Books");
        JButton viewReportsButton = new JButton("View Reports");
        JButton addNotificationButton = new JButton("Add Notification");
        JButton logoutButton = new JButton("Logout");

        viewUsersButton.addActionListener(e -> outputArea.setText(getUsersList()));
        viewBooksButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) panel.getLayout();
            cl.show(panel, "books");
        });
        viewReportsButton.addActionListener(e -> outputArea.setText(getReports()));
        addNotificationButton.addActionListener(e -> {
            String message = JOptionPane.showInputDialog(this, "Enter notification message:", "Add Notification", JOptionPane.PLAIN_MESSAGE);
            if (message != null && !message.trim().isEmpty()) {
                notifications.add(new Notification("N" + (notifications.size() + 1), message, new Date(), "Sent"));
                outputArea.setText("Notification added successfully.");
            }
        });
        logoutButton.addActionListener(e -> showLoginScreen());

        gridPanel.add(viewUsersButton);
        gridPanel.add(viewBooksButton);
        gridPanel.add(viewReportsButton);
        gridPanel.add(addNotificationButton);
        gridPanel.add(logoutButton);

        buttonPanel.add(gridPanel, BorderLayout.NORTH);
        
        // Books display panel
        JPanel booksPanel = createBooksDisplayPanel(() -> {
            CardLayout cl = (CardLayout) panel.getLayout();
            cl.show(panel, "buttons");
        });
        
        panel.add(buttonPanel, "buttons");
        panel.add(booksPanel, "books");
        
        return panel;
    }

    // Handles login and routes the user to the appropriate dashboard.
    private void handleLogin() {
        String userID = loginIDField.getText().trim();
        String password = new String(loginPasswordField.getPassword());

        if (userID.isEmpty() || password.isEmpty()) {
            outputArea.setText("Please enter a user ID and password.");
            return;
        }

        User user = users.get(userID);
        if (user == null) {
            outputArea.setText("User not found. Please try again.");
            return;
        }

        if (!user.getPassword().equals(password)) {
            outputArea.setText("Incorrect password. Please try again.");
            return;
        }

        currentUser = user;
        // Indicate successful login explicitly, then show welcome and role
        outputArea.setText("Login successful\nWelcome, " + user.getName() + "!\nRole: " + user.getRole());

        if (user instanceof Student) {
            cardLayout.show(mainPanel, "student");
        } else if (user instanceof Librarian) {
            cardLayout.show(mainPanel, "librarian");
        } else if (user instanceof Administrator) {
            cardLayout.show(mainPanel, "admin");
        }
    }

    private JPanel createBooksDisplayPanel(Runnable backAction) {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(245, 245, 245));

        // Header with back button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(245, 245, 245));
        JButton backButton = new JButton("← Back");
        backButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        backButton.addActionListener(e -> backAction.run());
        headerPanel.add(backButton, BorderLayout.WEST);
        
        JLabel titleLabel = new JLabel("Library Books Catalog");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Books panel with scroll
        JPanel booksPanel = new JPanel();
        booksPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        booksPanel.setBackground(new Color(245, 245, 245));

        // Add book cards
        for (Book book : books) {
            JPanel bookCard = createBookCard(book);
            booksPanel.add(bookCard);
        }

        JScrollPane scrollPane = new JScrollPane(booksPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createBookCard(Book book) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(10, 10));
        card.setPreferredSize(new Dimension(155, 280));
        card.setMaximumSize(new Dimension(155, 280));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));

        // Add subtle shadow effect
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // Book cover image
        JPanel coverPanel = new JPanel();
        coverPanel.setPreferredSize(new Dimension(145, 120));
        coverPanel.setBackground(new Color(240, 240, 240));
        
        JLabel coverLabel = new JLabel();
        coverLabel.setPreferredSize(new Dimension(145, 120));
        coverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        coverLabel.setVerticalAlignment(SwingConstants.CENTER);
        
        // Load book cover image
        try {
            String coverUrl = book.getCoverImageUrl();
            if (coverUrl != null && !coverUrl.isEmpty()) {
                URL url = new URL(coverUrl);
                ImageIcon originalIcon = new ImageIcon(url);
                ImageIcon scaledIcon = new ImageIcon(
                    originalIcon.getImage().getScaledInstance(140, 115, java.awt.Image.SCALE_SMOOTH)
                );
                coverLabel.setIcon(scaledIcon);
            }
        } catch (Exception e) {
            // Fallback: show placeholder
            coverLabel.setText("📚");
            coverLabel.setFont(new Font("SansSerif", Font.PLAIN, 48));
        }
        
        coverPanel.add(coverLabel);

        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout(0, 5));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Title
        JLabel titleLabel = new JLabel("<html><b>" + truncateText(book.getTitle(), 18) + "</b></html>");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        titleLabel.setForeground(new Color(33, 33, 33));
        titleLabel.setVerticalAlignment(SwingConstants.TOP);
        
        // Author
        JLabel authorLabel = new JLabel(truncateText(book.getAuthor(), 18));
        authorLabel.setFont(new Font("SansSerif", Font.PLAIN, 9));
        authorLabel.setForeground(new Color(120, 120, 120));

        // Availability status
        String availabilityText = book.isAvailable() ? "Available" : "Not Available";
        Color availabilityColor = book.isAvailable() ? new Color(76, 175, 80) : new Color(244, 67, 54);
        JLabel statusLabel = new JLabel(availabilityText);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 8));
        statusLabel.setForeground(availabilityColor);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout(0, 3));
        textPanel.setBackground(Color.WHITE);
        textPanel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel authorStatusPanel = new JPanel();
        authorStatusPanel.setLayout(new BorderLayout(0, 2));
        authorStatusPanel.setBackground(Color.WHITE);
        authorStatusPanel.add(authorLabel, BorderLayout.NORTH);
        authorStatusPanel.add(statusLabel, BorderLayout.SOUTH);
        
        textPanel.add(authorStatusPanel, BorderLayout.CENTER);

        infoPanel.add(textPanel, BorderLayout.CENTER);

        card.add(coverPanel, BorderLayout.NORTH);
        card.add(infoPanel, BorderLayout.CENTER);

        return card;
    }

    private String truncateText(String text, int maxLength) {
        if (text == null) return "";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength) + "...";
    }

    private void showLoginScreen() {
        currentUser = null;
        loginIDField.setText("");
        loginPasswordField.setText("");
        cardLayout.show(mainPanel, "login");
        outputArea.setText("Please log in to continue.");
    }

    private String getBookList() {
        StringBuilder builder = new StringBuilder();
        for (Book book : books) {
            builder.append(book).append("\n");
        }
        return builder.toString();
    }

    private String getUsersList() {
        StringBuilder builder = new StringBuilder();
        for (User user : users.values()) {
            builder.append(user).append("\n");
        }
        return builder.toString();
    }

    private String getRequestsList() {
        StringBuilder builder = new StringBuilder();
        if (requests.isEmpty()) {
            return "No active borrow requests.";
        }
        for (BorrowRequest request : requests) {
            builder.append(request).append("\n");
        }
        return builder.toString();
    }

    private String getBorrowHistory() {
        StringBuilder builder = new StringBuilder();
        if (transactions.isEmpty()) {
            return "No borrow history yet.";
        }
        for (BorrowTransaction transaction : transactions) {
            if (transaction.getBorrowerID().equals(currentUser.getUserID())) {
                builder.append(transaction).append("\n");
            }
        }
        return builder.length() == 0 ? "No borrow history for this user." : builder.toString();
    }

    private String getNotifications() {
        StringBuilder builder = new StringBuilder();
        if (notifications.isEmpty()) {
            return "No notifications available.";
        }
        for (Notification notification : notifications) {
            builder.append(notification).append("\n");
        }
        return builder.toString();
    }

    private String getReports() {
        StringBuilder builder = new StringBuilder();
        if (reports.isEmpty()) {
            return "No reports available.";
        }
        for (Report report : reports) {
            builder.append(report).append("\n");
        }
        return builder.toString();
    }

    private String requestBorrow(String bookID) {
        if (!(currentUser instanceof Student)) {
            return "Only students can request books.";
        }

        Book selectedBook = findBook(bookID);
        if (selectedBook == null) {
            return "Book not found.";
        }

        if (!selectedBook.isAvailable()) {
            return "Selected book is currently unavailable.";
        }

        String requestID = "REQ" + (requests.size() + 1);
        BorrowRequest request = new BorrowRequest(requestID, currentUser.getUserID(), bookID, new Date(), "Pending");
        requests.add(request);

        notifications.add(new Notification("N" + (notifications.size() + 1), "Borrow request sent for book " + bookID, new Date(), "Sent"));
        return "Borrow request created successfully. Request ID: " + requestID;
    }

    private String approveRequest(String requestID) {
        for (BorrowRequest request : requests) {
            if (request.getRequestID().equalsIgnoreCase(requestID)) {
                request.setStatus("Approved");
                Book book = findBook(request.getBookID());
                if (book != null) {
                    book.setAvailable(false);
                }
                transactions.add(new BorrowTransaction("T" + (transactions.size() + 1), request.getBookID(), request.getStudentID(), new Date(), new Date(System.currentTimeMillis() + 604800000L), "Active"));
                notifications.add(new Notification("N" + (notifications.size() + 1), "Borrow request approved for request " + requestID, new Date(), "Sent"));
                return "Request approved and book marked as borrowed.";
            }
        }
        return "Request ID not found.";
    }

    private String rejectRequest(String requestID) {
        for (BorrowRequest request : requests) {
            if (request.getRequestID().equalsIgnoreCase(requestID)) {
                request.setStatus("Rejected");
                notifications.add(new Notification("N" + (notifications.size() + 1), "Borrow request rejected for request " + requestID, new Date(), "Sent"));
                return "Request rejected successfully.";
            }
        }
        return "Request ID not found.";
    }

    private String processReturn(String transactionID) {
        for (BorrowTransaction transaction : transactions) {
            if (transaction.getTransactionID().equalsIgnoreCase(transactionID)) {
                transaction.setStatus("Returned");
                transaction.setReturnDate(new Date());
                Book book = findBook(transaction.getBookID());
                if (book != null) {
                    book.returnBook();
                }
                notifications.add(new Notification("N" + (notifications.size() + 1), "Book returned for transaction " + transactionID, new Date(), "Sent"));
                return "Return processed successfully.";
            }
        }
        return "Transaction ID not found.";
    }

    private Book findBook(String bookID) {
        for (Book book : books) {
            if (book.getBookID().equalsIgnoreCase(bookID)) {
                return book;
            }
        }
        return null;
    }
}
