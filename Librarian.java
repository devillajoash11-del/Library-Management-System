import java.util.List;

// Uri ng user na namamahala sa libro at borrowing activities.
public class Librarian extends User {
    // Natatanging librarian identifier.
    private String librarianID;

    // Gumagawa ng librarian profile.
    public Librarian(String userID, String name, String email, String password, String librarianID) {
        super(userID, name, email, password, "Librarian");
        this.librarianID = librarianID;
    }

    // Ibinabalik ang librarian ID.
    public String getLibrarianID() {
        return librarianID;
    }

    // Inaaprubahan ang isang borrow request.
    public boolean approveBorrowRequest(BorrowRequest request) {
        request.setStatus("Approved");
        return true;
    }

    // Tinatanggihan ang isang borrow request.
    public boolean rejectBorrowRequest(BorrowRequest request) {
        request.setStatus("Rejected");
        return true;
    }

    // Minamarkahan ang transaction bilang returned at itinatala ang oras.
    public String processReturn(BorrowTransaction transaction) {
        transaction.setStatus("Returned");
        transaction.setReturnDate(new java.util.Date());
        return "Return processed for transaction " + transaction.getTransactionID();
    }

    // Ibinabalik ang listahan ng librong pinamamahalaan ng librarian.
    public String manageBooks(List<Book> books) {
        StringBuilder output = new StringBuilder();
        for (Book book : books) {
            output.append(book).append("\n");
        }
        return output.toString();
    }
}
