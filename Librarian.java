import java.util.List;

// Librarian handles book management and validation of borrowing activities.
public class Librarian extends User {
    private String librarianID;

    public Librarian(String userID, String name, String email, String password, String librarianID) {
        super(userID, name, email, password, "Librarian");
        this.librarianID = librarianID;
    }

    public String getLibrarianID() {
        return librarianID;
    }

    public boolean approveBorrowRequest(BorrowRequest request) {
        request.setStatus("Approved");
        return true;
    }

    public boolean rejectBorrowRequest(BorrowRequest request) {
        request.setStatus("Rejected");
        return true;
    }

    public String processReturn(BorrowTransaction transaction) {
        transaction.setStatus("Returned");
        transaction.setReturnDate(new java.util.Date());
        return "Return processed for transaction " + transaction.getTransactionID();
    }

    public String manageBooks(List<Book> books) {
        StringBuilder output = new StringBuilder();
        for (Book book : books) {
            output.append(book).append("\n");
        }
        return output.toString();
    }
}
