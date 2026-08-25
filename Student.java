import java.util.ArrayList;
import java.util.List;

// Student type user. This class represents a library member who can borrow and view books.
public class Student extends User {
    private String studentID;
    private String course;
    private String section;
    private final List<String> borrowedBookIDs = new ArrayList<>();

    public Student(String userID, String name, String email, String password, String studentID, String course, String section) {
        super(userID, name, email, password, "Student");
        this.studentID = studentID;
        this.course = course;
        this.section = section;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getCourse() {
        return course;
    }

    public String getSection() {
        return section;
    }

    public List<String> getBorrowedBookIDs() {
        return borrowedBookIDs;
    }

    public void borrowBook(String bookID) {
        borrowedBookIDs.add(bookID);
    }

    public void returnBook(String bookID) {
        borrowedBookIDs.remove(bookID);
    }

    public String searchBook(String keyword, List<Book> books) {
        StringBuilder result = new StringBuilder();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase())
                    || book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                result.append(book).append("\n");
            }
        }
        return result.toString();
    }

    public String viewBorrowedBooks(List<Book> books) {
        StringBuilder result = new StringBuilder();
        if (borrowedBookIDs.isEmpty()) {
            return "No borrowed books yet.";
        }

        for (String bookID : borrowedBookIDs) {
            for (Book book : books) {
                if (book.getBookID().equals(bookID)) {
                    result.append(book).append("\n");
                }
            }
        }
        return result.toString();
    }
}
