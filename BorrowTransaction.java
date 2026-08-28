import java.util.Date;

// Tinututukan ang approved borrowing mula paghiram hanggang pagsauli.
public class BorrowTransaction {
    // Natatanging identifier ng transaction.
    private String transactionID;
    // ID ng librong hiniram.
    private String bookID;
    // User ID ng borrower.
    private String borrowerID;
    // Buong pangalan ng borrower.
    private String borrowerName;
    // Oras kung kailan na-approve at nahiram ang libro.
    private Date borrowDate;
    // Target o due date ng pagsauli.
    private Date dueDate;
    // Aktuwal na oras ng pagsauli; null habang hindi pa naibabalik.
    private Date returnDate;
    // Estado ng transaction gaya ng Active o Returned.
    private String status;

    // Lumang constructor na pinananatili para compatible sa existing code.
        // Gumagawa ng transaction kasama ang buong pangalan ng borrower.
    public BorrowTransaction(String transactionID, String bookID, String borrowerID, Date borrowDate, Date dueDate, String status) {
        this(transactionID, bookID, borrowerID, "", borrowDate, dueDate, status);
    }

    public BorrowTransaction(String transactionID, String bookID, String borrowerID, String borrowerName, Date borrowDate, Date dueDate, String status) {
        this.transactionID = transactionID;
        this.bookID = bookID;
        this.borrowerID = borrowerID;
        this.borrowerName = borrowerName;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = null;
        this.status = status;
    }

    // Ibinabalik ang transaction ID.
    public String getTransactionID() {
        return transactionID;
    }

    // Ibinabalik ang book ID.
    public String getBookID() {
        return bookID;
    }

    // Ibinabalik ang borrower user ID.
    public String getBorrowerID() {
        return borrowerID;
    }

    // Ibinabalik ang buong pangalan ng borrower.
    public String getBorrowerName() {
        return borrowerName;
    }

    // Ibinabalik ang oras ng paghiram.
    public Date getBorrowDate() {
        return borrowDate;
    }

    // Ibinabalik ang due date.
    public Date getDueDate() {
        return dueDate;
    }

    // Ibinabalik ang oras ng pagsauli.
    public Date getReturnDate() {
        return returnDate;
    }

    // Itinatala ang aktuwal na oras ng pagsauli.
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    // Ibinabalik ang status ng transaction.
    public String getStatus() {
        return status;
    }

    // Ina-update ang status ng transaction.
    public void setStatus(String status) {
        this.status = status;
    }

    // Gumagawa ng readable representation para sa transaction output.
    @Override
    public String toString() {
        return "BorrowTransaction{" +
                "transactionID='" + transactionID + '\'' +
                ", bookID='" + bookID + '\'' +
                ", borrowerID='" + borrowerID + '\'' +
            ", borrowerName='" + borrowerName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
