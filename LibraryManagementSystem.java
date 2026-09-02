import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

// Pangunahing library application na gumagamit ng Java Swing.
// Pinag-uugnay nito ang data model, login, dashboards, catalog, at borrowing workflow.
public class LibraryManagementSystem extends JFrame {
    // Mga pangunahing koleksyon ng data na ginagamit ng system.
    private final Map<String, User> users = new HashMap<>();
    private final List<Book> books = new ArrayList<>();
    private final List<BorrowRequest> requests = new ArrayList<>();
    private final List<BorrowTransaction> transactions = new ArrayList<>();
    private final List<Notification> notifications = new ArrayList<>();
    private final List<Report> reports = new ArrayList<>();

    // Mga layout at UI component na kumokontrol sa paglipat ng screens.
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);
    private JPanel studentPanel;
    private JPanel borrowRecordsPanel;
    private JPanel adminPanel;
    private JPanel adminRecordsPanel;
    private final JTextArea outputArea = new JTextArea();
    private final JTextField loginIDField = new JTextField();
    private final JPasswordField loginPasswordField = new JPasswordField();
    private final JComboBox<String> roleSelector = new JComboBox<>(new String[]{"Student", "Admin"});
    private User currentUser;

    // Gumagawa ng main window at inihahanda ang panels at sample data.
    public LibraryManagementSystem() {
        applyAppTheme();
        initializeData();
        setTitle("Library Information System");
        setSize(1080, 760);
        setMinimumSize(new Dimension(900, 620));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(235, 242, 246));

        // Pangunahing heading ng application.
        JLabel titleLabel = new JLabel("Library Information System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(24, 61, 87));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(18, 0, 18, 0));
        add(titleLabel, BorderLayout.NORTH);

        /*
         * CardLayout ang ginagamit para lumipat sa login o dashboard ayon sa role
         * ng kasalukuyang naka-login na user.
         */
        mainPanel.add(createLoginPanel(), "login");
        mainPanel.add(createStudentPanel(), "student");
        mainPanel.add(createLibrarianPanel(), "librarian");
        mainPanel.add(createAdminPanel(), "admin");

        add(mainPanel, BorderLayout.CENTER);

        // Output panel sa ibaba para sa messages at results ng system.
        outputArea.setEditable(false);
        outputArea.setRows(4);
        outputArea.setColumns(60);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        outputArea.setBackground(new Color(250, 252, 253));
        outputArea.setForeground(new Color(34, 55, 70));
        outputArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1),
                "System Output"
        ));
        outputScroll.getViewport().setBackground(Color.WHITE);
        add(outputScroll, BorderLayout.SOUTH);

        showLoginScreen();
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    // Naglalagay ng sample users, books, transactions, notifications, at reports.
    private void initializeData() {
        users.put("S001", new Student("S001", "Ana Dela Cruz", "ana@email.com", "1234", "ST001", "BSIT", "A"));
        users.put("S002", new Student("S002", "Ben Santos", "ben@email.com", "1234", "ST002", "BSCS", "B"));
        users.put("A001", new Administrator("A001", "Carlos Reyes", "carlos@email.com", "root123", "ADM001"));
        // Administrator account para sa pangunahing admin login.
        users.put("admin", new Administrator("admin", "System Administrator", "admin@library.local", "cmdiadmin123", "ADM002"));
        users.put("L001", new Librarian("L001", "Maria Santos", "maria@library.local", "lib123", "LIB001"));
        // Sample account para sa testing ng login.
        users.put("user@gmail.com", new Student("user@gmail.com", "Test User", "user@gmail.com", "pizza123", "ST100", "General", "1"));

        // Mga sample book record na may cover image para sa catalog.
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

        BorrowTransaction t1 = new BorrowTransaction("T001", "B004", "S001", "Ana Dela Cruz", new Date(), new Date(System.currentTimeMillis() + 86400000L), "Active");
        BorrowTransaction t2 = new BorrowTransaction("T002", "B003", "S002", "Ben Santos", new Date(System.currentTimeMillis() - 172800000L), new Date(System.currentTimeMillis() + 86400000L), "Active");
        BorrowTransaction t3 = new BorrowTransaction("T003", "B006", "S001", "Ana Dela Cruz", new Date(System.currentTimeMillis() - 604800000L), new Date(System.currentTimeMillis() - 86400000L), "Returned");
        t3.setReturnDate(new Date(System.currentTimeMillis() - 86400000L));
        transactions.add(t1);
        transactions.add(t2);
        transactions.add(t3);

        notifications.add(new Notification("N001", "Welcome to the library system.", new Date(), "Sent"));

        reports.add(new Report("R001", "Borrow Summary", "Total active borrow transactions: 1", new Date()));
        reports.add(new Report("R002", "Inventory Overview", "Total books available: 11", new Date()));
    }

    // Gumagawa ng iisang login form kung saan pipili ang user ng Student o Admin role.
    private JPanel createLoginPanel() {
        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setBackground(new Color(235, 242, 246));
        outerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JPanel panel = new JPanel(new GridLayout(6, 2, 12, 12));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(190, 205, 216), 1),
            BorderFactory.createEmptyBorder(30, 34, 30, 34)
        ));
        panel.setPreferredSize(new Dimension(480, 310));

        JLabel userLabel = new JLabel("User ID:");
        JLabel roleLabel = new JLabel("Login as:");
        JLabel passwordLabel = new JLabel("Password:");
        JButton loginButton = new JButton("Login");
        JButton resetButton = new JButton("Reset");

        userLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        roleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        userLabel.setForeground(new Color(34, 40, 49));
        passwordLabel.setForeground(new Color(34, 40, 49));
        roleLabel.setForeground(new Color(34, 40, 49));
        loginIDField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        loginIDField.setBorder(BorderFactory.createLineBorder(new Color(195, 204, 214), 1));
        loginIDField.setBackground(new Color(250, 251, 252));
        loginPasswordField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        loginPasswordField.setBorder(BorderFactory.createLineBorder(new Color(195, 204, 214), 1));
        loginPasswordField.setBackground(new Color(250, 251, 252));
        roleSelector.setFont(new Font("SansSerif", Font.PLAIN, 13));
        roleSelector.setBackground(Color.WHITE);
        roleSelector.setBorder(BorderFactory.createLineBorder(new Color(195, 204, 214), 1));

        styleButton(loginButton);
        styleButton(resetButton);

        loginButton.addActionListener(e -> handleLogin());
        resetButton.addActionListener(e -> {
            loginIDField.setText("");
            loginPasswordField.setText("");
            roleSelector.setSelectedItem("Student");
            outputArea.setText("Input fields reset.");
        });

        panel.add(userLabel);
        panel.add(loginIDField);
        panel.add(roleLabel);
        panel.add(roleSelector);
        panel.add(passwordLabel);
        panel.add(loginPasswordField);
        panel.add(new JLabel());
        panel.add(loginButton);
        panel.add(new JLabel());
        panel.add(resetButton);
        panel.add(new JLabel());
        panel.add(new JLabel());

        outerPanel.add(panel);
        return outerPanel;
    }

    // Gumagawa ng student dashboard at mga action button nito.
    private JPanel createStudentPanel() {
        JPanel panel = new JPanel(new CardLayout());
        studentPanel = panel;

        // Panel ng mga pangunahing aksyon ng student.
        JPanel buttonPanel = new JPanel(new BorderLayout(14, 14));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        buttonPanel.setBackground(new Color(235, 242, 246));
        
        JPanel gridPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 12));
        gridPanel.setBackground(new Color(235, 242, 246));
        JButton viewBooksButton = new JButton("View Books");
        JButton searchBooksButton = new JButton("Search Books");
        JButton borrowBookButton = new JButton("Borrow Book");
        JButton recordsButton = new JButton("Borrow Records");
        JButton returnBookButton = new JButton("Return Book");
        JButton logoutButton = new JButton("Logout");

        styleActionButton(viewBooksButton, new Color(59, 130, 246), new Color(37, 99, 235), new Color(29, 78, 216));
        styleActionButton(searchBooksButton, new Color(16, 185, 129), new Color(5, 150, 105), new Color(4, 120, 87));
        styleActionButton(borrowBookButton, new Color(22, 163, 74), new Color(17, 126, 57), new Color(11, 96, 44));
        styleActionButton(recordsButton, new Color(99, 102, 241), new Color(79, 70, 229), new Color(67, 56, 202));
        styleActionButton(returnBookButton, new Color(234, 88, 12), new Color(194, 65, 12), new Color(154, 52, 18));
        styleActionButton(logoutButton, new Color(90, 100, 115), new Color(72, 81, 94), new Color(58, 65, 76));

        // Pinapalaki ang pangunahing student buttons para madaling gamitin.
        java.awt.Dimension largeBtn = new java.awt.Dimension(180, 52);
        viewBooksButton.setPreferredSize(largeBtn);
        viewBooksButton.setMinimumSize(largeBtn);
        viewBooksButton.setMaximumSize(largeBtn);
        searchBooksButton.setPreferredSize(largeBtn);
        searchBooksButton.setMinimumSize(largeBtn);
        searchBooksButton.setMaximumSize(largeBtn);
        borrowBookButton.setPreferredSize(largeBtn);
        borrowBookButton.setMinimumSize(largeBtn);
        borrowBookButton.setMaximumSize(largeBtn);
        recordsButton.setPreferredSize(largeBtn);
        recordsButton.setMinimumSize(largeBtn);
        recordsButton.setMaximumSize(largeBtn);
        returnBookButton.setPreferredSize(largeBtn);
        returnBookButton.setMinimumSize(largeBtn);
        returnBookButton.setMaximumSize(largeBtn);
        logoutButton.setPreferredSize(largeBtn);
        logoutButton.setMinimumSize(largeBtn);
        logoutButton.setMaximumSize(largeBtn);
        viewBooksButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        searchBooksButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        borrowBookButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        recordsButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        returnBookButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        logoutButton.setFont(new Font("SansSerif", Font.BOLD, 14));

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
            showBorrowBookDialog();
        });
        recordsButton.addActionListener(e -> {
            showBorrowRecords();
        });
        returnBookButton.addActionListener(e -> {
            showReturnBookDialog();
        });
        logoutButton.addActionListener(e -> showLoginScreen());

        gridPanel.add(viewBooksButton);
        gridPanel.add(searchBooksButton);
        gridPanel.add(borrowBookButton);
        gridPanel.add(recordsButton);
        gridPanel.add(returnBookButton);
        gridPanel.add(logoutButton);

        buttonPanel.add(gridPanel, BorderLayout.CENTER);
        
        // Catalog panel na ipinapakita kapag pinili ang View Books.
        JPanel booksPanel = createBooksDisplayPanel(() -> {
            CardLayout cl = (CardLayout) panel.getLayout();
            cl.show(panel, "buttons");
        });
        borrowRecordsPanel = createBorrowRecordsPanel(() -> {
            CardLayout cl = (CardLayout) panel.getLayout();
            cl.show(panel, "buttons");
        });
        
        panel.add(buttonPanel, "buttons");
        panel.add(booksPanel, "books");
        panel.add(borrowRecordsPanel, "records");
        
        return panel;
    }

    // Gumagawa ng librarian dashboard at management actions.
    private JPanel createLibrarianPanel() {
        JPanel panel = new JPanel(new CardLayout());
        
        // Panel ng mga librarian action button.
        JPanel buttonPanel = new JPanel(new BorderLayout(14, 14));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        buttonPanel.setBackground(new Color(235, 242, 246));
        
        JPanel gridPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 12));
        gridPanel.setBackground(new Color(235, 242, 246));
        JButton viewRequestsButton = new JButton("View Requests");
        JButton approveRequestButton = new JButton("Approve Request");
        JButton rejectRequestButton = new JButton("Reject Request");
        JButton viewBooksButton = new JButton("Manage Books");
        JButton processReturnButton = new JButton("Process Return");
        JButton logoutButton = new JButton("Logout");

        styleButton(viewRequestsButton);
        styleButton(approveRequestButton);
        styleButton(rejectRequestButton);
        styleButton(viewBooksButton);
        styleButton(processReturnButton);
        styleButton(logoutButton);

        // Pinapalaki ang mahahalagang librarian buttons.
        java.awt.Dimension largeBtnLib = new java.awt.Dimension(180, 52);
        viewBooksButton.setPreferredSize(largeBtnLib);
        viewBooksButton.setMinimumSize(largeBtnLib);
        viewBooksButton.setMaximumSize(largeBtnLib);
        processReturnButton.setPreferredSize(largeBtnLib);
        processReturnButton.setMinimumSize(largeBtnLib);
        processReturnButton.setMaximumSize(largeBtnLib);
        logoutButton.setPreferredSize(largeBtnLib);
        logoutButton.setMinimumSize(largeBtnLib);
        logoutButton.setMaximumSize(largeBtnLib);
        viewRequestsButton.setPreferredSize(largeBtnLib);
        viewRequestsButton.setMinimumSize(largeBtnLib);
        viewRequestsButton.setMaximumSize(largeBtnLib);
        viewBooksButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        logoutButton.setFont(new Font("SansSerif", Font.BOLD, 14));

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

        buttonPanel.add(gridPanel, BorderLayout.CENTER);
        
        // Catalog panel para sa pagtingin at pag-manage ng books.
        JPanel booksPanel = createBooksDisplayPanel(() -> {
            CardLayout cl = (CardLayout) panel.getLayout();
            cl.show(panel, "buttons");
        });
        
        panel.add(buttonPanel, "buttons");
        panel.add(booksPanel, "books");
        
        return panel;
    }

    // Gumagawa ng administrator dashboard at reporting actions.
    private JPanel createAdminPanel() {
        JPanel panel = new JPanel(new CardLayout());
        adminPanel = panel;
        
        // Panel ng mga administrator action button.
        JPanel buttonPanel = new JPanel(new BorderLayout(14, 14));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        buttonPanel.setBackground(new Color(235, 242, 246));
        
        JPanel gridPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 12));
        gridPanel.setBackground(new Color(235, 242, 246));
        JButton addBookButton = new JButton("Add Book");
        JButton deleteBookButton = new JButton("Delete Book");
        JButton searchBookButton = new JButton("Search Book");
        JButton recordsButton = new JButton("Borrower Records");
        JButton logoutButton = new JButton("Logout");

        styleActionButton(addBookButton, new Color(22, 163, 74), new Color(17, 126, 57), new Color(11, 96, 44));
        styleActionButton(deleteBookButton, new Color(220, 53, 69), new Color(180, 35, 51), new Color(141, 24, 38));
        styleActionButton(searchBookButton, new Color(59, 130, 246), new Color(37, 99, 235), new Color(29, 78, 216));
        styleActionButton(recordsButton, new Color(99, 102, 241), new Color(79, 70, 229), new Color(67, 56, 202));
        styleActionButton(logoutButton, new Color(90, 100, 115), new Color(72, 81, 94), new Color(58, 65, 76));

        // Pinapalaki ang mahahalagang administrator buttons.
        java.awt.Dimension largeBtnAdmin = new java.awt.Dimension(180, 52);
        addBookButton.setPreferredSize(largeBtnAdmin);
        addBookButton.setMinimumSize(largeBtnAdmin);
        addBookButton.setMaximumSize(largeBtnAdmin);
        deleteBookButton.setPreferredSize(largeBtnAdmin);
        deleteBookButton.setMinimumSize(largeBtnAdmin);
        deleteBookButton.setMaximumSize(largeBtnAdmin);
        searchBookButton.setPreferredSize(largeBtnAdmin);
        searchBookButton.setMinimumSize(largeBtnAdmin);
        searchBookButton.setMaximumSize(largeBtnAdmin);
        recordsButton.setPreferredSize(largeBtnAdmin);
        recordsButton.setMinimumSize(largeBtnAdmin);
        recordsButton.setMaximumSize(largeBtnAdmin);
        logoutButton.setPreferredSize(largeBtnAdmin);
        logoutButton.setMinimumSize(largeBtnAdmin);
        logoutButton.setMaximumSize(largeBtnAdmin);
        addBookButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        deleteBookButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        searchBookButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        recordsButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        logoutButton.setFont(new Font("SansSerif", Font.BOLD, 14));

        addBookButton.addActionListener(e -> showAddBookDialog());
        deleteBookButton.addActionListener(e -> showDeleteBookDialog());
        searchBookButton.addActionListener(e -> showSearchBookDialog());
        recordsButton.addActionListener(e -> {
            ((CardLayout) panel.getLayout()).show(panel, "records");
        });
        logoutButton.addActionListener(e -> showLoginScreen());

        gridPanel.add(addBookButton);
        gridPanel.add(deleteBookButton);
        gridPanel.add(searchBookButton);
        gridPanel.add(recordsButton);
        gridPanel.add(logoutButton);

        buttonPanel.add(gridPanel, BorderLayout.CENTER);
        
        // Catalog panel para sa administrator.
        JPanel booksPanel = createBooksDisplayPanel(() -> {
            CardLayout cl = (CardLayout) panel.getLayout();
            cl.show(panel, "buttons");
        });
        adminRecordsPanel = createAdminRecordsPanel(() -> {
            CardLayout cl = (CardLayout) panel.getLayout();
            cl.show(panel, "buttons");
        });
        
        panel.add(buttonPanel, "buttons");
        panel.add(booksPanel, "books");
        panel.add(adminRecordsPanel, "records");
        ((CardLayout) panel.getLayout()).show(panel, "records");
        
        return panel;
    }

    // Gumagawa ng admin view para makita ang lahat ng borrowing at return records.
    private JPanel createAdminRecordsPanel(Runnable backAction) {
        JPanel recordsPanel = new JPanel(new BorderLayout(12, 12));
        recordsPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        recordsPanel.setBackground(new Color(245, 245, 245));

        JPanel headerPanel = new JPanel(new BorderLayout(12, 12));
        headerPanel.setBackground(new Color(250, 251, 253));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 228, 236), 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));

        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionsPanel.setOpaque(false);

        JButton manageButton = new JButton("Manage Books");
        styleActionButton(manageButton, new Color(24, 138, 121), new Color(14, 110, 96), new Color(10, 89, 77));
        manageButton.setPreferredSize(new Dimension(155, 38));
        manageButton.setFocusPainted(false);
        manageButton.addActionListener(e -> backAction.run());

        JButton refreshButton = new JButton("Refresh Records");
        styleActionButton(refreshButton, new Color(79, 70, 229), new Color(67, 56, 202), new Color(55, 48, 163));
        refreshButton.setPreferredSize(new Dimension(170, 38));
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> {
            adminPanel.remove(adminRecordsPanel);
            JPanel refreshedPanel = createAdminRecordsPanel(backAction);
            adminRecordsPanel = refreshedPanel;
            adminPanel.add(refreshedPanel, "records");
            ((CardLayout) adminPanel.getLayout()).show(adminPanel, "records");
            adminPanel.revalidate();
            adminPanel.repaint();
        });

        actionsPanel.add(manageButton);
        actionsPanel.add(refreshButton);
        headerPanel.add(actionsPanel, BorderLayout.EAST);

        JLabel titleLabel = new JLabel("Borrower and Return Records");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 21));
        titleLabel.setForeground(new Color(40, 55, 75));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        recordsPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel mainTablePanel = new JPanel(new BorderLayout(8, 8));
        mainTablePanel.setBackground(new Color(245, 245, 245));
        mainTablePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 215, 230)), "Student Borrowing History"),
                BorderFactory.createEmptyBorder(6, 6, 6, 6)
        ));
        mainTablePanel.setMinimumSize(new Dimension(0, 220));

        String[] columns = {"Book Name", "Borrower Full Name", "Borrowed at", "Returned At", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (BorrowTransaction transaction : transactions) {
            Book book = findBook(transaction.getBookID());
            String bookName = book == null ? transaction.getBookID() : book.getTitle();
            String borrowerName = getBorrowerDisplayName(transaction);
            String borrowedAt = formatDate(transaction.getBorrowDate());
            String returnedAt = transaction.getReturnDate() == null ? "Not yet returned" : formatDate(transaction.getReturnDate());
            String status = transaction.getStatus() == null ? "Active" : transaction.getStatus();
            model.addRow(new Object[]{bookName, borrowerName, borrowedAt, returnedAt, status});
        }

        JTable recordsTable = new JTable(model);
        styleTable(recordsTable, new Color(54, 94, 170));
        recordsTable.setRowHeight(34);
        recordsTable.setFillsViewportHeight(true);
        recordsTable.getTableHeader().setReorderingAllowed(false);
        recordsTable.getColumnModel().getColumn(0).setPreferredWidth(260);
        recordsTable.getColumnModel().getColumn(1).setPreferredWidth(220);
        recordsTable.getColumnModel().getColumn(2).setPreferredWidth(170);
        recordsTable.getColumnModel().getColumn(3).setPreferredWidth(170);
        recordsTable.getColumnModel().getColumn(4).setPreferredWidth(120);

        recordsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                         boolean isSelected, boolean hasFocus,
                                                         int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                boolean zebra = (row % 2 == 0);
                setBackground(isSelected ? table.getSelectionBackground() : (zebra ? new Color(250, 252, 255) : Color.WHITE));
                setForeground(table.getForeground());
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                setHorizontalAlignment(column == 4 ? SwingConstants.CENTER : SwingConstants.LEFT);

                if (column == 4) {
                    String status = String.valueOf(value == null ? "" : value);
                    if ("Returned".equalsIgnoreCase(status)) {
                        setBackground(new Color(221, 244, 231));
                        setForeground(new Color(12, 108, 58));
                    } else if ("Active".equalsIgnoreCase(status)) {
                        setBackground(new Color(232, 244, 255));
                        setForeground(new Color(20, 79, 143));
                    } else if ("Pending".equalsIgnoreCase(status)) {
                        setBackground(new Color(255, 245, 214));
                        setForeground(new Color(150, 110, 35));
                    } else if ("Approved".equalsIgnoreCase(status)) {
                        setBackground(new Color(228, 244, 234));
                        setForeground(new Color(6, 104, 64));
                    }
                }
                return c;
            }
        });

        JScrollPane recordsScroll = new JScrollPane(recordsTable);
        recordsScroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 220, 230), 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        recordsScroll.getViewport().setBackground(Color.WHITE);
        mainTablePanel.add(recordsScroll, BorderLayout.CENTER);
        recordsPanel.add(mainTablePanel, BorderLayout.CENTER);

        JPanel requestsPanel = new JPanel(new BorderLayout(10, 10));
        requestsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 215, 230)), "Pending Borrow Requests"),
                BorderFactory.createEmptyBorder(6, 8, 6, 8))
        );
        requestsPanel.setBackground(new Color(245, 245, 245));
        requestsPanel.setMinimumSize(new Dimension(0, 150));

        String[] requestColumns = {"Request ID", "Student", "Book", "Borrower Full Name", "Requested At", "Status"};
        DefaultTableModel requestModel = new DefaultTableModel(requestColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (BorrowRequest request : requests) {
            if (request == null) {
                continue;
            }
            Book book = findBook(request.getBookID());
            String bookName = book == null ? request.getBookID() : book.getTitle();
            String borrowerName = getBorrowerDisplayName(request);
            String studentName = request.getStudentID() == null || request.getStudentID().isEmpty()
                    ? "Unknown Student"
                    : (users.get(request.getStudentID()) == null ? request.getStudentID() : users.get(request.getStudentID()).getName());
            requestModel.addRow(new Object[]{
                    request.getRequestID(),
                    studentName,
                    bookName,
                    borrowerName,
                    formatDate(request.getRequestDate()),
                    request.getStatus() == null ? "Pending" : request.getStatus()
            });
        }

        JTable requestTable = new JTable(requestModel);
        styleTable(requestTable, new Color(54, 94, 170));
        requestTable.setRowHeight(32);
        requestTable.setAutoCreateRowSorter(true);

        JScrollPane requestScroll = new JScrollPane(requestTable);
        requestScroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 220, 230), 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        requestScroll.getViewport().setBackground(Color.WHITE);
        requestsPanel.add(requestScroll, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(new Color(245, 245, 245));
        bottomPanel.add(requestsPanel, BorderLayout.CENTER);

        JTextArea notificationArea = new JTextArea(getAdminNotifications());
        notificationArea.setEditable(false);
        notificationArea.setLineWrap(true);
        notificationArea.setWrapStyleWord(true);
        notificationArea.setRows(5);
        notificationArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
        notificationArea.setBackground(new Color(250, 252, 255));
        notificationArea.setForeground(new Color(30, 45, 60));
        notificationArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 215, 230)), "Notification History"),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        JScrollPane notificationScroll = new JScrollPane(notificationArea);
        notificationScroll.setPreferredSize(new Dimension(0, 120));
        bottomPanel.add(notificationScroll, BorderLayout.SOUTH);
        recordsPanel.add(bottomPanel, BorderLayout.SOUTH);

        return recordsPanel;
    }

    // Binubuo ang notification feed na may malinaw na detalye ng bawat activity.
    private String getAdminNotifications() {
        if (notifications.isEmpty()) {
            return "No notifications yet.";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = notifications.size() - 1; i >= 0; i--) {
            Notification notification = notifications.get(i);
            builder.append("• ")
                    .append(formatDate(notification.getSentDate()))
                    .append("  |  ")
                    .append(notification.getMessage())
                    .append("\n");
        }
        return builder.toString();
    }

    private JPanel createSummaryCard(String label, String value, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout(4, 4));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 228, 236), 1),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)
        ));

        JLabel labelText = new JLabel(label);
        labelText.setFont(new Font("SansSerif", Font.PLAIN, 11));
        labelText.setForeground(new Color(90, 98, 110));

        JLabel valueText = new JLabel(value);
        valueText.setFont(new Font("SansSerif", Font.BOLD, 24));
        valueText.setForeground(accentColor);

        card.add(labelText, BorderLayout.NORTH);
        card.add(valueText, BorderLayout.CENTER);
        return card;
    }

    private void triggerAdminRefreshAndAlert(String title, String message) {
        showSystemNotification(title, message);
        refreshBorrowAndRecordTablesFromNotifications();
    }

    private void addBorrowNotificationAndRefresh(String message) {
        addNotificationToHistory(message);
        refreshBorrowAndRecordTablesFromNotifications();
    }

    private void addBorrowNotificationAndRefresh(String title, String message) {
        addNotificationToHistory(message);
        refreshBorrowAndRecordTablesFromNotifications();
        if (title != null && !title.trim().isEmpty()) {
            showSystemNotification(title, message);
        }
    }

    private void addNotificationToHistory(String message) {
        if (message == null || message.trim().isEmpty()) {
            return;
        }
        notifications.add(new Notification("N" + (notifications.size() + 1), message.trim(), new Date(), "Sent"));
    }

    private void refreshBorrowAndRecordTablesFromNotifications() {
        if (adminPanel != null) {
            refreshAdminRecordsPanel();
        }
        if (currentUser != null && currentUser instanceof Student) {
            showBorrowRecords();
        }
    }

    // Tine-check ang login input at dinadala ang user sa tamang dashboard.
    private void handleLogin() {
        String userID = loginIDField.getText().trim();
        String password = new String(loginPasswordField.getPassword());

        if (userID.isEmpty() || password.isEmpty()) {
            outputArea.setText("Please enter a user ID and password.");
            return;
        }

        User user = users.get(userID);
        String selectedRole = (String) roleSelector.getSelectedItem();

        if ("Admin".equals(selectedRole)) {
            if (user == null || !(user instanceof Administrator)) {
                outputArea.setText("Admin access only. Please use a valid administrator account.");
                return;
            }
            if (!user.getPassword().equals(password)) {
                outputArea.setText("Incorrect admin password. Please try again.");
                return;
            }
        } else if ("Librarian".equals(selectedRole)) {
            if (user == null || !(user instanceof Librarian)) {
                outputArea.setText("Librarian access only. Please use a valid librarian account.");
                return;
            }
            if (!user.getPassword().equals(password)) {
                outputArea.setText("Incorrect librarian password. Please try again.");
                return;
            }
        } else {
            if (user != null && !(user instanceof Student)) {
                outputArea.setText("This account is not registered as a student.");
                return;
            }
            if (user == null) {
                user = new Student(userID, userID,
                        userID.contains("@") ? userID : userID + "@guest.local",
                        password, "ST" + System.currentTimeMillis(), "General", "A");
                users.put(userID, user);
            }
            if (!user.getPassword().equals(password)) {
                outputArea.setText("Incorrect student password. Please try again.");
                return;
            }
        }

        currentUser = user;
        outputArea.setText("Login successful\nWelcome, " + user.getName() + "!\nRole: " + user.getRole());

        if (user instanceof Student) {
            cardLayout.show(mainPanel, "student");
        } else if (user instanceof Librarian) {
            cardLayout.show(mainPanel, "librarian");
        } else if (user instanceof Administrator) {
            refreshAdminRecordsPanel();
            cardLayout.show(mainPanel, "admin");
        }
    }

    // Nire-refresh ang admin records para makita agad ang pinakabagong borrow at return notifications.
    private void refreshAdminRecordsPanel() {
        if (adminPanel == null || adminRecordsPanel == null) {
            return;
        }
        adminPanel.remove(adminRecordsPanel);
        adminRecordsPanel = createAdminRecordsPanel(() -> {
            ((CardLayout) adminPanel.getLayout()).show(adminPanel, "buttons");
        });
        adminPanel.add(adminRecordsPanel, "records");
        ((CardLayout) adminPanel.getLayout()).show(adminPanel, "records");
        adminPanel.revalidate();
        adminPanel.repaint();
    }

    // Nagpapakita ng form para magdagdag ng bagong libro sa catalog.
    private void showAddBookDialog() {
        JPanel form = new JPanel(new GridLayout(0, 1, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(12, 12, 8, 12));
        form.setPreferredSize(new Dimension(390, 270));

        JTextField idField = new JTextField("B" + String.format("%03d", books.size() + 1));
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField isbnField = new JTextField();
        JTextField coverField = new JTextField();
        form.add(new JLabel("Book ID:"));
        form.add(idField);
        form.add(new JLabel("Book title:"));
        form.add(titleField);
        form.add(new JLabel("Author:"));
        form.add(authorField);
        form.add(new JLabel("Category:"));
        form.add(categoryField);
        form.add(new JLabel("ISBN:"));
        form.add(isbnField);
        form.add(new JLabel("Cover image URL (optional):"));
        form.add(coverField);

        int result = JOptionPane.showConfirmDialog(this, form, "Add New Book",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String bookID = idField.getText().trim();
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        if (bookID.isEmpty() || title.isEmpty() || author.isEmpty()) {
            outputArea.setText("Book ID, title, and author are required.");
            return;
        }
        if (findBook(bookID) != null) {
            outputArea.setText("Book ID already exists.");
            return;
        }

        books.add(new Book(bookID, title, author, categoryField.getText().trim(),
                isbnField.getText().trim(), true, coverField.getText().trim()));
        outputArea.setText("Book added successfully.\nTitle: " + title + "\nAuthor: " + author);
    }

    // Nagpapakita ng form para baguhin ang detalye ng isang existing book.
    private void showUpdateBookDialog() {
        String bookID = JOptionPane.showInputDialog(this, "Enter Book ID to update:",
                "Update Book", JOptionPane.PLAIN_MESSAGE);
        if (bookID == null || bookID.trim().isEmpty()) {
            return;
        }

        Book book = findBook(bookID.trim());
        if (book == null) {
            outputArea.setText("Book not found.");
            return;
        }

        JPanel form = new JPanel(new GridLayout(0, 1, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(12, 12, 8, 12));
        form.setPreferredSize(new Dimension(390, 220));
        JTextField titleField = new JTextField(book.getTitle());
        JTextField authorField = new JTextField(book.getAuthor());
        JTextField categoryField = new JTextField(book.getCategory());
        JTextField isbnField = new JTextField(book.getIsbn());
        JTextField coverField = new JTextField(book.getCoverImageUrl());
        form.add(new JLabel("Book title:"));
        form.add(titleField);
        form.add(new JLabel("Author:"));
        form.add(authorField);
        form.add(new JLabel("Category:"));
        form.add(categoryField);
        form.add(new JLabel("ISBN:"));
        form.add(isbnField);
        form.add(new JLabel("Cover image URL:"));
        form.add(coverField);

        int result = JOptionPane.showConfirmDialog(this, form, "Update Book - " + book.getTitle(),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION && !titleField.getText().trim().isEmpty()
                && !authorField.getText().trim().isEmpty()) {
            book.setTitle(titleField.getText().trim());
            book.setAuthor(authorField.getText().trim());
            book.setCategory(categoryField.getText().trim());
            book.setIsbn(isbnField.getText().trim());
            book.setCoverImageUrl(coverField.getText().trim());
            outputArea.setText("Book updated successfully.\nTitle: " + book.getTitle());
        } else if (result == JOptionPane.OK_OPTION) {
            outputArea.setText("Book title and author are required.");
        }
    }

    // Tinatanggal ang piniling libro matapos kumpirmahin ng administrator.
    private void showDeleteBookDialog() {
        String bookID = JOptionPane.showInputDialog(this, "Enter Book ID to delete:",
                "Delete Book", JOptionPane.PLAIN_MESSAGE);
        if (bookID == null || bookID.trim().isEmpty()) {
            return;
        }

        Book book = findBook(bookID.trim());
        if (book == null) {
            outputArea.setText("Book not found.");
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(this,
                "Delete this book?\n\n" + book.getTitle() + "\n" + book.getAuthor(),
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirmation == JOptionPane.YES_OPTION) {
            books.remove(book);
            outputArea.setText("Book deleted successfully.\nTitle: " + book.getTitle());
        }
    }

    // Naghahanap ng libro ayon sa title, author, category, o book ID.
    private void showSearchBookDialog() {
        String keyword = JOptionPane.showInputDialog(this, "Search by title, author, category, or ID:",
                "Search Book", JOptionPane.PLAIN_MESSAGE);
        if (keyword == null || keyword.trim().isEmpty()) {
            return;
        }

        String searchTerm = keyword.trim().toLowerCase();
        StringBuilder result = new StringBuilder("Search Results\n\n");
        int matchCount = 0;
        for (Book book : books) {
            if (book.getBookID().toLowerCase().contains(searchTerm)
                    || book.getTitle().toLowerCase().contains(searchTerm)
                    || book.getAuthor().toLowerCase().contains(searchTerm)
                    || book.getCategory().toLowerCase().contains(searchTerm)) {
                result.append("Book: ").append(book.getTitle())
                        .append("\nAuthor: ").append(book.getAuthor())
                        .append("\nCategory: ").append(book.getCategory())
                        .append("\nID: ").append(book.getBookID())
                        .append("\nStatus: ").append(book.isAvailable() ? "Available" : "Borrowed")
                        .append("\n\n");
                matchCount++;
            }
        }
        if (matchCount == 0) {
            result.append("No books found for: ").append(keyword.trim());
        }
        outputArea.setText(result.toString());
    }

    // Gumagawa ng scrollable at responsive catalog ng lahat ng books.
    private JPanel createBooksDisplayPanel(Runnable backAction) {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(245, 245, 245));

        // Header na may back button at pamagat ng catalog.
        JPanel headerPanel = new JPanel(new BorderLayout(12, 12));
        headerPanel.setBackground(new Color(250, 251, 253));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 228, 236), 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));

        JButton backButton = new JButton("← Back");
        styleButton(backButton);
        backButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        backButton.setPreferredSize(new Dimension(110, 36));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> backAction.run());
        headerPanel.add(backButton, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Library Books Catalog");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(new Color(40, 55, 75));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Scrollable container na nagwo-wrap ng cards sa maraming row.
        JPanel booksPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15)) {
            @Override
            public Dimension getPreferredSize() {
                int availableWidth = getParent() == null ? 760 : getParent().getWidth();
                int cardWidth = 155 + 15;
                int columns = Math.max(1, (availableWidth - 30) / cardWidth);
                int rows = (int) Math.ceil((double) getComponentCount() / columns);
                return new Dimension(Math.max(availableWidth, 760), rows * 280 + (rows + 1) * 15);
            }
        };
        booksPanel.setBackground(new Color(245, 245, 245));

        // Idinadagdag ang isang card para sa bawat libro.
        for (Book book : books) {
            JPanel bookCard = createBookCard(book);
            booksPanel.add(bookCard);
        }

        JScrollPane scrollPane = new JScrollPane(booksPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                booksPanel.revalidate();
            }
        });
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        return mainPanel;
    }

    // Gumagawa ng consistent card na may cover, title, author, at availability.
    private JPanel createBookCard(Book book) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(10, 10));
        card.setPreferredSize(new Dimension(175, 300));
        card.setMaximumSize(new Dimension(175, 300));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));

        // Naglalagay ng simpleng border at spacing para sa polished na card.
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // Lugar para sa cover image ng libro.
        JPanel coverPanel = new JPanel();
        coverPanel.setPreferredSize(new Dimension(165, 140));
        coverPanel.setBackground(new Color(240, 240, 240));
        
        JLabel coverLabel = new JLabel();
        coverLabel.setPreferredSize(new Dimension(165, 140));
        coverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        coverLabel.setVerticalAlignment(SwingConstants.CENTER);
        
        // Kinukuha at sini-scale ang cover image mula sa URL.
        try {
            String coverUrl = book.getCoverImageUrl();
            if (coverUrl != null && !coverUrl.isEmpty()) {
                URL url = new URL(coverUrl);
                ImageIcon originalIcon = new ImageIcon(url);
                ImageIcon scaledIcon = new ImageIcon(
                    originalIcon.getImage().getScaledInstance(160, 135, java.awt.Image.SCALE_SMOOTH)
                );
                coverLabel.setIcon(scaledIcon);
            }
        } catch (Exception e) {
            // Placeholder kapag walang ma-load na cover image.
            coverLabel.setText("📚");
            coverLabel.setFont(new Font("SansSerif", Font.PLAIN, 48));
        }
        
        coverPanel.add(coverLabel);

        // Panel para sa title, author, at availability status.
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout(0, 5));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Ipinapakita ang pamagat ng libro.
        JLabel titleLabel = new JLabel("<html><b>" + truncateText(book.getTitle(), 22) + "</b></html>");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        titleLabel.setForeground(new Color(33, 33, 33));
        titleLabel.setVerticalAlignment(SwingConstants.TOP);
        
        // Ipinapakita ang may-akda ng libro.
        JLabel authorLabel = new JLabel(truncateText(book.getAuthor(), 22));
        authorLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        authorLabel.setForeground(new Color(120, 120, 120));

        // Ipinapakita kung available pa ang libro.
        String availabilityText = book.isAvailable() ? "Available" : "Not Available";
        Color availabilityColor = book.isAvailable() ? new Color(76, 175, 80) : new Color(244, 67, 54);
        JLabel statusLabel = new JLabel(availabilityText);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 9));
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

    // Helper para pare-pareho ang style ng lahat ng buttons.
    private void styleButton(JButton btn) {
        styleActionButton(btn, new Color(63, 114, 175), new Color(43, 87, 141), new Color(31, 70, 118));
    }

    private void styleActionButton(JButton btn, Color baseColor, Color hoverColor, Color pressedColor) {
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setBackground(baseColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(baseColor, 1),
                BorderFactory.createEmptyBorder(12, 18, 12, 18)
        ));
        btn.setContentAreaFilled(true);
        btn.setRolloverEnabled(true);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(hoverColor);
                btn.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(hoverColor, 1),
                        BorderFactory.createEmptyBorder(12, 18, 12, 18)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(baseColor);
                btn.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(baseColor, 1),
                        BorderFactory.createEmptyBorder(12, 18, 12, 18)
                ));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                btn.setBackground(pressedColor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                btn.setBackground(hoverColor);
            }
        });
    }

    private void styleTable(JTable table, Color headerColor) {
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.setFillsViewportHeight(true);
        table.setShowGrid(true);
        table.setGridColor(new Color(220, 228, 236));
        table.setSelectionBackground(new Color(216, 232, 255));
        table.setSelectionForeground(new Color(20, 28, 36));
        table.setIntercellSpacing(new Dimension(5, 4));
        table.setRowMargin(4);
        table.setAutoCreateRowSorter(true);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        table.getTableHeader().setBackground(headerColor);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setPreferredSize(new Dimension(0, 36));
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(headerColor.darker(), 1));
    }

    private void applyAppTheme() {
        // Mga default font at background para sa consistent na UI theme.
        UIManager.put("Label.font", new Font("SansSerif", Font.PLAIN, 13));
        UIManager.put("Button.font", new Font("SansSerif", Font.BOLD, 13));
        UIManager.put("TextField.font", new Font("SansSerif", Font.PLAIN, 13));
        UIManager.put("PasswordField.font", new Font("SansSerif", Font.PLAIN, 13));
        UIManager.put("ComboBox.font", new Font("SansSerif", Font.PLAIN, 13));
        UIManager.put("Panel.background", new ColorUIResource(new Color(235, 242, 246)));
        UIManager.put("ScrollPane.background", new ColorUIResource(new Color(235, 242, 246)));
        UIManager.put("OptionPane.background", new ColorUIResource(new Color(255, 255, 255)));
        UIManager.put("Panel.foreground", new ColorUIResource(new Color(34, 40, 49)));
        UIManager.put("Button.background", new ColorUIResource(new Color(63, 114, 175)));
        UIManager.put("Button.foreground", new ColorUIResource(Color.WHITE));
        UIManager.put("Button.border", BorderFactory.createLineBorder(new Color(63, 114, 175), 1));
    }

    private String truncateText(String text, int maxLength) {
        if (text == null) return "";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength) + "...";
    }

    // Nililinis ang kasalukuyang session at ibinabalik ang user sa login screen.
    private void showLoginScreen() {
        currentUser = null;
        loginIDField.setText("");
        loginPasswordField.setText("");
        cardLayout.show(mainPanel, "login");
        outputArea.setText("Please log in to continue.");
    }

    // Kinokolekta ang text representation ng lahat ng libro.
    private String getBookList() {
        StringBuilder builder = new StringBuilder();
        for (Book book : books) {
            builder.append(book).append("\n");
        }
        return builder.toString();
    }

    // Kinokolekta ang listahan ng registered users para sa administrator.
    private String getUsersList() {
        StringBuilder builder = new StringBuilder();
        for (User user : users.values()) {
            builder.append(user).append("\n");
        }
        return builder.toString();
    }

    // Binubuo ang readable list ng borrow requests para sa librarian.
    private String getRequestsList() {
        StringBuilder builder = new StringBuilder();
        if (requests.isEmpty()) {
            return "No active borrow requests.";
        }
        for (BorrowRequest request : requests) {
            Book book = findBook(request.getBookID());
            String bookName = book == null ? request.getBookID() : book.getTitle();
            String borrowerName = getBorrowerDisplayName(request);
            builder.append("Request ID: ").append(request.getRequestID())
                    .append("\nBook: ").append(bookName)
                    .append("\nBorrower: ").append(borrowerName)
                    .append("\nRequested at: ").append(formatDate(request.getRequestDate()))
                    .append("\nStatus: ").append(request.getStatus())
                    .append("\n\n");
        }
        return builder.toString();
    }

    // Ipinapakita ang book name, borrower, borrow time, return time, at status.
    private String getBorrowHistory() {
        StringBuilder builder = new StringBuilder();
        if (currentUser == null) {
            return "No active user session.";
        }
        if (transactions.isEmpty()) {
            return "No borrow history yet.";
        }
        for (BorrowTransaction transaction : transactions) {
            if (transaction.getBorrowerID().equals(currentUser.getUserID())) {
                Book book = findBook(transaction.getBookID());
                String bookName = book == null ? transaction.getBookID() : book.getTitle();
                String borrowerName = transaction.getBorrowerName() == null || transaction.getBorrowerName().isEmpty()
                        ? currentUser.getName() : transaction.getBorrowerName();
                String returnedAt = transaction.getReturnDate() == null
                        ? "Not yet returned" : formatDate(transaction.getReturnDate());
                builder.append("Book: ").append(bookName)
                        .append("\nBorrower: ").append(borrowerName)
                        .append("\nBorrowed at: ").append(formatDate(transaction.getBorrowDate()))
                        .append("\nReturned at: ").append(returnedAt)
                        .append("\nStatus: ").append(transaction.getStatus())
                        .append("\n\n");
            }
        }
        return builder.length() == 0 ? "No borrow history for this user." : builder.toString();
    }

    // Kinokolekta ang lahat ng notification para sa system output.
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

    // Kinokolekta ang lahat ng report para sa administrator.
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

    // Gumagawa ng malinis na table para sa lahat ng borrow request at transaction record.
    private JPanel createBorrowRecordsPanel(Runnable backAction) {
        JPanel recordsPanel = new JPanel(new BorderLayout(12, 12));
        recordsPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        recordsPanel.setBackground(new Color(245, 245, 245));

        JPanel headerPanel = new JPanel(new BorderLayout(12, 12));
        headerPanel.setBackground(new Color(245, 245, 245));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JButton backButton = new JButton("<- Back");
        styleActionButton(backButton, new Color(90, 100, 115), new Color(72, 81, 94), new Color(58, 65, 76));
        backButton.setPreferredSize(new Dimension(110, 36));
        backButton.addActionListener(e -> backAction.run());

        JButton borrowButton = new JButton("Borrow Book");
        styleActionButton(borrowButton, new Color(19, 141, 117), new Color(14, 110, 92), new Color(9, 82, 68));
        borrowButton.setPreferredSize(new Dimension(150, 36));
        borrowButton.addActionListener(e -> showBorrowBookDialog());

        JButton returnButton = new JButton("Return Book");
        styleActionButton(returnButton, new Color(226, 132, 34), new Color(195, 98, 17), new Color(169, 78, 7));
        returnButton.setPreferredSize(new Dimension(150, 36));
        returnButton.addActionListener(e -> showReturnBookDialog());

        JPanel headerActions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        headerActions.setOpaque(false);
        headerActions.add(borrowButton);
        headerActions.add(returnButton);

        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(headerActions, BorderLayout.EAST);

        JLabel titleLabel = new JLabel("Borrow Records");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 21));
        titleLabel.setForeground(new Color(40, 55, 75));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        recordsPanel.add(headerPanel, BorderLayout.NORTH);

        if (currentUser == null) {
            JLabel sessionLabel = new JLabel("No active user session. Please log in again.");
            sessionLabel.setHorizontalAlignment(SwingConstants.CENTER);
            sessionLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            recordsPanel.add(sessionLabel, BorderLayout.CENTER);
            return recordsPanel;
        }

        String[] columns = {"Book Name", "Borrower Full Name", "Borrowed at", "Returned At", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (BorrowTransaction transaction : transactions) {
            if (transaction == null || !transaction.getBorrowerID().equals(currentUser.getUserID())) {
                continue;
            }

            Book book = findBook(transaction.getBookID());
            String bookName = book == null ? transaction.getBookID() : book.getTitle();
            String borrowerName = (transaction.getBorrowerName() != null && !transaction.getBorrowerName().trim().isEmpty())
                    ? transaction.getBorrowerName().trim() : currentUser.getName();
            String borrowedAt = formatDate(transaction.getBorrowDate());
            String returnedAt = transaction.getReturnDate() == null ? "Not yet returned" : formatDate(transaction.getReturnDate());
            String status = transaction.getStatus() == null ? "Active" : transaction.getStatus();
            model.addRow(new Object[]{bookName, borrowerName, borrowedAt, returnedAt, status});
        }

        for (BorrowRequest request : requests) {
            if (request == null || request.getStudentID() == null || !request.getStudentID().equals(currentUser.getUserID())) {
                continue;
            }
            boolean alreadyAdded = false;
            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 0).equals(findBook(request.getBookID()) == null ? request.getBookID() : findBook(request.getBookID()).getTitle())) {
                    alreadyAdded = true;
                    break;
                }
            }
            if (alreadyAdded) {
                continue;
            }
            Book book = findBook(request.getBookID());
            String bookName = book == null ? request.getBookID() : book.getTitle();
            String borrowerName = (request.getBorrowerName() != null && !request.getBorrowerName().trim().isEmpty())
                    ? request.getBorrowerName().trim() : currentUser.getName();
            model.addRow(new Object[]{bookName, borrowerName, "Pending approval", "Not yet returned", request.getStatus() == null ? "Pending" : request.getStatus()});
        }

        JTable recordsTable = new JTable(model);
        styleTable(recordsTable, new Color(60, 120, 200));
        recordsTable.setRowHeight(34);
        recordsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                         boolean isSelected, boolean hasFocus,
                                                         int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                setBackground(isSelected ? table.getSelectionBackground() : (row % 2 == 0)
                        ? new Color(250, 252, 255) : Color.WHITE);
                setForeground(table.getForeground());
                if (column == 4) {
                    String status = String.valueOf(value == null ? "" : value);
                    if ("Returned".equalsIgnoreCase(status)) {
                        setForeground(new Color(12, 108, 58));
                        setBackground(new Color(221, 244, 231));
                    } else if ("Active".equalsIgnoreCase(status)) {
                        setForeground(new Color(20, 79, 143));
                        setBackground(new Color(232, 244, 255));
                    } else if ("Pending".equalsIgnoreCase(status)) {
                        setForeground(new Color(150, 110, 35));
                        setBackground(new Color(255, 245, 214));
                    }
                }
                setHorizontalAlignment(column == 4 ? SwingConstants.CENTER : SwingConstants.LEFT);
                return c;
            }
        });
        recordsTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        recordsTable.getColumnModel().getColumn(0).setPreferredWidth(260);
        recordsTable.getColumnModel().getColumn(1).setPreferredWidth(220);
        recordsTable.getColumnModel().getColumn(2).setPreferredWidth(170);
        recordsTable.getColumnModel().getColumn(3).setPreferredWidth(170);
        recordsTable.getColumnModel().getColumn(4).setPreferredWidth(120);

        JScrollPane tableScrollPane = new JScrollPane(recordsTable);
        tableScrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(215, 220, 228), 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        tableScrollPane.setMinimumSize(new Dimension(0, 220));
        tableScrollPane.getViewport().setBackground(Color.WHITE);
        recordsPanel.add(tableScrollPane, BorderLayout.CENTER);

        JTextArea activityArea = new JTextArea(getRecentNotificationsForCurrentUser());
        activityArea.setEditable(false);
        activityArea.setLineWrap(true);
        activityArea.setWrapStyleWord(true);
        activityArea.setRows(5);
        activityArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
        activityArea.setBackground(new Color(250, 252, 255));
        activityArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 215, 230)), "Recent Activity"),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        JScrollPane activityScrollPane = new JScrollPane(activityArea);
        activityScrollPane.setPreferredSize(new Dimension(0, 180));
        recordsPanel.add(activityScrollPane, BorderLayout.SOUTH);

        return recordsPanel;
    }

    // Hinahanap ang transaction na tumutugma sa student at libro.
    private BorrowTransaction findTransaction(String studentID, String bookID) {
        for (BorrowTransaction transaction : transactions) {
            if (transaction.getBorrowerID().equals(studentID) && transaction.getBookID().equals(bookID)) {
                return transaction;
            }
        }
        return null;
    }

    private void showSystemNotification(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private String getRecentNotificationsForCurrentUser() {
        if (currentUser == null) {
            return "No recent activity.";
        }

        StringBuilder builder = new StringBuilder();
        String userName = currentUser.getName().toLowerCase();
        String userID = currentUser.getUserID().toLowerCase();

        for (int i = notifications.size() - 1; i >= 0; i--) {
            Notification notification = notifications.get(i);
            String message = notification.getMessage();
            String lowerMessage = message.toLowerCase();
            if (lowerMessage.contains(userName) || lowerMessage.contains(userID)) {
                builder.append("• ")
                        .append(formatDate(notification.getSentDate()))
                        .append("  |  ")
                        .append(message)
                        .append("\n");
            }
        }

        if (builder.length() == 0) {
            return "No recent activity for your account yet.";
        }
        return builder.toString();
    }

    // Nagpapakita ng malinis na form para pumili ng libro at maglagay ng buong pangalan.
    private void showBorrowBookDialog() {
        if (!(currentUser instanceof Student)) {
            outputArea.setText("Only students can request books.");
            return;
        }

        // Isinasama lamang ang mga librong maaari pang hiramin.
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.isAvailable()) {
                availableBooks.add(book);
            }
        }

        if (availableBooks.isEmpty()) {
            outputArea.setText("No books are currently available.");
            return;
        }

        // Binubuo ang form na may book selector at borrower name field.
        JPanel form = new JPanel(new GridLayout(0, 1, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(12, 12, 8, 12));
        form.setPreferredSize(new Dimension(390, 150));

        JLabel bookLabel = new JLabel("Select book name:");
        JComboBox<String> bookSelector = new JComboBox<>();
        for (Book book : availableBooks) {
            bookSelector.addItem(book.getTitle() + "  |  " + book.getAuthor());
        }

        JLabel nameLabel = new JLabel("Full name of borrower:");
        JTextField nameField = new JTextField(currentUser.getName());
        form.add(bookLabel);
        form.add(bookSelector);
        form.add(nameLabel);
        form.add(nameField);

        int result = JOptionPane.showConfirmDialog(this, form, "Borrow Book Details",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String borrowerName = nameField.getText().trim();
            if (borrowerName.isEmpty()) {
                outputArea.setText("Please enter the borrower's full name.");
                return;
            }
            Book selectedBook = availableBooks.get(bookSelector.getSelectedIndex());
            outputArea.setText(requestBorrow(selectedBook.getBookID(), borrowerName));
            if (adminPanel != null) {
                refreshAdminRecordsPanel();
            }
            showBorrowRecords();
        }
    }

    // Ipinapakita agad ang records screen pagkatapos magsumite ng borrow form.
    private void showBorrowRecords() {
        studentPanel.remove(borrowRecordsPanel);
        JPanel refreshedRecordsPanel = createBorrowRecordsPanel(() -> {
            CardLayout cl = (CardLayout) studentPanel.getLayout();
            cl.show(studentPanel, "buttons");
        });
        borrowRecordsPanel = refreshedRecordsPanel;
        studentPanel.add(borrowRecordsPanel, "records");
        CardLayout layout = (CardLayout) studentPanel.getLayout();
        layout.show(studentPanel, "records");
        studentPanel.revalidate();
        studentPanel.repaint();
    }

    // Nagpapakita ng aktibong hiniram na libro at pinoproseso ang pagsauli nito.
    private void showReturnBookDialog() {
        if (!(currentUser instanceof Student)) {
            outputArea.setText("Only students can return books.");
            return;
        }

        List<BorrowTransaction> activeTransactions = new ArrayList<>();
        for (BorrowTransaction transaction : transactions) {
            if (transaction.getBorrowerID().equals(currentUser.getUserID())
                    && "Active".equalsIgnoreCase(transaction.getStatus())) {
                activeTransactions.add(transaction);
            }
        }

        if (activeTransactions.isEmpty()) {
            outputArea.setText("You have no active borrowed books to return.");
            return;
        }

        JPanel form = new JPanel(new GridLayout(0, 1, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(12, 12, 8, 12));
        form.setPreferredSize(new Dimension(410, 135));
        JLabel bookLabel = new JLabel("Select the book to return:");
        JComboBox<String> bookSelector = new JComboBox<>();
        for (BorrowTransaction transaction : activeTransactions) {
            Book book = findBook(transaction.getBookID());
            String bookName = book == null ? transaction.getBookID() : book.getTitle();
            bookSelector.addItem(bookName + "  |  Borrowed " + formatDate(transaction.getBorrowDate()));
        }
        JLabel noteLabel = new JLabel("The return time will be recorded automatically.");
        noteLabel.setForeground(new Color(100, 100, 100));
        form.add(bookLabel);
        form.add(bookSelector);
        form.add(noteLabel);

        int result = JOptionPane.showConfirmDialog(this, form, "Return Book",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            BorrowTransaction selectedTransaction = activeTransactions.get(bookSelector.getSelectedIndex());
            outputArea.setText(processReturn(selectedTransaction.getTransactionID()));
            showSystemNotification("Book Return", outputArea.getText());
            showBorrowRecords();
        }
    }

    // Gumagawa ng pending borrow request gamit ang napiling libro at borrower name.
    private String requestBorrow(String bookID, String borrowerName) {
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
    BorrowRequest request = new BorrowRequest(requestID, currentUser.getUserID(), borrowerName, bookID, new Date(), "Pending");
        requests.add(request);

        addBorrowNotificationAndRefresh("New borrow request | Book: " + selectedBook.getTitle()
                + " | Borrower: " + borrowerName);
        return "Borrow request created successfully.\nBook: " + selectedBook.getTitle()
                + "\nBorrower: " + borrowerName
                + "\nRequested at: " + formatDate(request.getRequestDate())
                + "\nRequest ID: " + requestID;
    }

    // Inaaprubahan ang request, minamarkahan ang libro bilang borrowed, at gumagawa ng transaction.
    private String approveRequest(String requestID) {
        if (requestID == null || requestID.trim().isEmpty()) {
            return "Please provide a request ID.";
        }
        for (BorrowRequest request : requests) {
            if (request.getRequestID().equalsIgnoreCase(requestID.trim())) {
                request.setStatus("Approved");
                Book book = findBook(request.getBookID());
                if (book == null) {
                    request.setStatus("Rejected");
                    return "Cannot approve request: book not found in the catalog.";
                }
                book.setAvailable(false);

                String borrowerName = request.getBorrowerName() == null ? "" : request.getBorrowerName();
                BorrowTransaction transaction = new BorrowTransaction(
                        "T" + (transactions.size() + 1),
                        request.getBookID(),
                        request.getStudentID(),
                        borrowerName,
                        new Date(),
                        new Date(System.currentTimeMillis() + 604800000L),
                        "Active");
                transactions.add(transaction);
                addBorrowNotificationAndRefresh("Borrow approved | Book: " + book.getTitle()
                        + " | Borrower: " + borrowerName
                        + " | Borrowed at: " + formatDate(transaction.getBorrowDate()));
                triggerAdminRefreshAndAlert("Borrow Approved", "Book borrowed successfully.\n\n" + book.getTitle() + "\nBorrower: " + borrowerName);
                return "Request approved and book marked as borrowed.";
            }
        }
        return "Request ID not found.";
    }

    // Tinatanggihan ang borrow request at nagse-save ng rejection status.
    private String rejectRequest(String requestID) {
        if (requestID == null || requestID.trim().isEmpty()) {
            return "Please provide a request ID.";
        }
        for (BorrowRequest request : requests) {
            if (request.getRequestID().equalsIgnoreCase(requestID.trim())) {
                request.setStatus("Rejected");
                addBorrowNotificationAndRefresh("Borrow Request Rejected",
                        "Borrow request rejected | Request ID: " + requestID + " | Book: " +
                                (findBook(request.getBookID()) == null ? request.getBookID() : findBook(request.getBookID()).getTitle()) +
                                " | Borrower: " + getBorrowerDisplayName(request));
                return "Request rejected successfully.";
            }
        }
        return "Request ID not found.";
    }

    // Pinoproseso ang pagsauli, nire-record ang return time, at ginagawang available muli ang libro.
    private String processReturn(String transactionID) {
        if (transactionID == null || transactionID.trim().isEmpty()) {
            return "Please provide a transaction ID.";
        }
        for (BorrowTransaction transaction : transactions) {
            if (transaction.getTransactionID().equalsIgnoreCase(transactionID.trim())) {
                transaction.setStatus("Returned");
                transaction.setReturnDate(new Date());
                Book book = findBook(transaction.getBookID());
                if (book != null) {
                    book.returnBook();
                }
                String borrowerName = transaction.getBorrowerName() == null ? transaction.getBorrowerID() : transaction.getBorrowerName();
                Book returnedBook = findBook(transaction.getBookID());
                addBorrowNotificationAndRefresh("Book returned | Book: " + (returnedBook == null ? transaction.getBookID() : returnedBook.getTitle())
                        + " | Borrower: " + borrowerName
                        + " | Returned at: " + formatDate(transaction.getReturnDate()));
                triggerAdminRefreshAndAlert("Book Returned", "Book return recorded successfully.\n\n" + (returnedBook == null ? transaction.getBookID() : returnedBook.getTitle()) + "\nBorrower: " + borrowerName);
                return "Return processed successfully.\nReturned at: " + formatDate(transaction.getReturnDate());
            }
        }
        return "Transaction ID not found.";
    }

    // Ibinabalik ang malinaw na pangalan ng borrower sa admin records.
    private String getBorrowerDisplayName(BorrowTransaction transaction) {
        if (transaction == null) {
            return "Unknown Borrower";
        }
        if (transaction.getBorrowerName() != null && !transaction.getBorrowerName().trim().isEmpty()) {
            return transaction.getBorrowerName().trim();
        }
        User borrower = users.get(transaction.getBorrowerID());
        return borrower == null ? transaction.getBorrowerID() : borrower.getName();
    }

    // Ibinabalik ang malinaw na pangalan ng borrower sa request list.
    private String getBorrowerDisplayName(BorrowRequest request) {
        if (request == null) {
            return "Unknown Borrower";
        }
        if (request.getBorrowerName() != null && !request.getBorrowerName().trim().isEmpty()) {
            return request.getBorrowerName().trim();
        }
        User borrower = users.get(request.getStudentID());
        return borrower == null ? request.getStudentID() : borrower.getName();
    }

    // Hinahanap ang isang libro sa catalog gamit ang book ID.
    private Book findBook(String bookID) {
        for (Book book : books) {
            if (book.getBookID().equalsIgnoreCase(bookID)) {
                return book;
            }
        }
        return null;
    }

    // Ginagawang madaling basahin ang petsa at oras sa system output.
    private String formatDate(Date date) {
        return new SimpleDateFormat("MMM dd, yyyy - hh:mm a").format(date);
    }
}
