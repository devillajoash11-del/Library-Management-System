import java.util.Date;

// Tracks an approved borrow transaction from checkout until return.
public class BorrowTransaction {
    private String transactionID;
    private String bookID;
    private String borrowerID;
    private Date borrowDate;
    private Date dueDate;
    private Date returnDate;
    private String status;

    public BorrowTransaction(String transactionID, String bookID, String borrowerID, Date borrowDate, Date dueDate, String status) {
        this.transactionID = transactionID;
        this.bookID = bookID;
        this.borrowerID = borrowerID;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = null;
        this.status = status;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String getBookID() {
        return bookID;
    }

    public String getBorrowerID() {
        return borrowerID;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BorrowTransaction{" +
                "transactionID='" + transactionID + '\'' +
                ", bookID='" + bookID + '\'' +
                ", borrowerID='" + borrowerID + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
