import java.util.Date;

// Request ng student na naghihintay ng approval ng librarian.
public class BorrowRequest {
    // Natatanging identifier ng request.
    private String requestID;
    // User ID ng student na humihiram.
    private String studentID;
    // Buong pangalan ng taong hihiram.
    private String borrowerName;
    // ID ng piniling libro.
    private String bookID;
    // Oras kung kailan ginawa ang request.
    private Date requestDate;
    // Kasalukuyang estado ng request.
    private String status;

    // Lumang constructor na pinananatili para compatible sa existing code.
        // Gumagawa ng request kasama ang pangalan ng borrower.
    public BorrowRequest(String requestID, String studentID, String bookID, Date requestDate, String status) {
        this(requestID, studentID, "", bookID, requestDate, status);
    }

    public BorrowRequest(String requestID, String studentID, String borrowerName, String bookID, Date requestDate, String status) {
        this.requestID = requestID;
        this.studentID = studentID;
        this.borrowerName = borrowerName;
        this.bookID = bookID;
        this.requestDate = requestDate;
        this.status = status;
    }

    // Ibinabalik ang request ID.
    public String getRequestID() {
        return requestID;
    }

    // Ibinabalik ang student ID.
    public String getStudentID() {
        return studentID;
    }

    // Ibinabalik ang book ID.
    public String getBookID() {
        return bookID;
    }

    // Ibinabalik ang buong pangalan ng borrower.
    public String getBorrowerName() {
        return borrowerName;
    }

    // Ibinabalik ang oras ng request.
    public Date getRequestDate() {
        return requestDate;
    }

    // Ibinabalik ang status ng request.
    public String getStatus() {
        return status;
    }

    // Binabago ang status kapag approved o rejected na.
    public void setStatus(String status) {
        this.status = status;
    }

    // Gumagawa ng readable representation para sa request list.
    @Override
    public String toString() {
        return "BorrowRequest{" +
                "requestID='" + requestID + '\'' +
                ", studentID='" + studentID + '\'' +
            ", borrowerName='" + borrowerName + '\'' +
                ", bookID='" + bookID + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
