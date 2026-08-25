import java.util.Date;

// A borrow request made by a student waiting for librarian approval.
public class BorrowRequest {
    private String requestID;
    private String studentID;
    private String bookID;
    private Date requestDate;
    private String status;

    public BorrowRequest(String requestID, String studentID, String bookID, Date requestDate, String status) {
        this.requestID = requestID;
        this.studentID = studentID;
        this.bookID = bookID;
        this.requestDate = requestDate;
        this.status = status;
    }

    public String getRequestID() {
        return requestID;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getBookID() {
        return bookID;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BorrowRequest{" +
                "requestID='" + requestID + '\'' +
                ", studentID='" + studentID + '\'' +
                ", bookID='" + bookID + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
