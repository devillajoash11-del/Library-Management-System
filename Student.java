import java.util.ArrayList;
import java.util.List;

// Uri ng user na kumakatawan sa library member.
// Naglalaman ito ng student details at mga operasyon sa paghahanap/paghiram ng libro.
public class Student extends User {
    // Natatanging student identifier.
    private String studentID;
    // Kurso ng student.
    private String course;
    // Section ng student.
    private String section;
    // Listahan ng mga book ID na kasalukuyang hawak ng student.
    private final List<String> borrowedBookIDs = new ArrayList<>();

    // Gumagawa ng student profile kasama ang academic details.
    public Student(String userID, String name, String email, String password, String studentID, String course, String section) {
        super(userID, name, email, password, "Student");
        this.studentID = studentID;
        this.course = course;
        this.section = section;
    }

    // Ibinabalik ang student ID.
    public String getStudentID() {
        return studentID;
    }

    // Ibinabalik ang kurso.
    public String getCourse() {
        return course;
    }

    // Ibinabalik ang section.
    public String getSection() {
        return section;
    }

    // Ibinabalik ang listahan ng hiniram na book IDs.
    public List<String> getBorrowedBookIDs() {
        return borrowedBookIDs;
    }

    // Idinadagdag ang libro sa listahan ng hiniram.
    public void borrowBook(String bookID) {
        borrowedBookIDs.add(bookID);
    }

    // Tinatanggal ang libro sa listahan kapag naibalik na.
    public void returnBook(String bookID) {
        borrowedBookIDs.remove(bookID);
    }

    // Hinahanap ang libro ayon sa pamagat o may-akda.
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

    // Ipinapakita ang mga librong kasalukuyang hiniram ng student.
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
