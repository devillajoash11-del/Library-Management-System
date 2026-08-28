import java.util.Objects;

// Kumakatawan sa isang libro sa library catalog.
// Iniingatan nito ang detalye, availability, at cover image ng libro.
public class Book {
    // Natatanging identifier ng libro.
    private String bookID;
    // Pamagat ng libro.
    private String title;
    // May-akda ng libro.
    private String author;
    // Kategorya ng libro.
    private String category;
    // ISBN ng libro.
    private String isbn;
    // Nagsasaad kung maaari pang hiramin ang libro.
    private boolean available;
    // URL ng cover image na ipinapakita sa catalog.
    private String coverImageUrl;

    // Gumagawa ng libro na walang cover image.
        // Gumagawa ng libro kasama ang URL ng cover image.
    public Book(String bookID, String title, String author, String category, String isbn, boolean available) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.available = available;
        this.coverImageUrl = "";
    }

    public Book(String bookID, String title, String author, String category, String isbn, boolean available, String coverImageUrl) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.available = available;
        this.coverImageUrl = coverImageUrl;
    }

    // Ibinabalik ang book ID.
    public String getBookID() {
        return bookID;
    }

    // Ibinabalik ang pamagat.
    public String getTitle() {
        return title;
    }

    // Ina-update ang pamagat ng libro.
    public void setTitle(String title) {
        this.title = title;
    }

    // Ibinabalik ang may-akda.
    public String getAuthor() {
        return author;
    }

    // Ina-update ang may-akda ng libro.
    public void setAuthor(String author) {
        this.author = author;
    }

    // Ibinabalik ang kategorya.
    public String getCategory() {
        return category;
    }

    // Ina-update ang kategorya ng libro.
    public void setCategory(String category) {
        this.category = category;
    }

    // Ibinabalik ang ISBN.
    public String getIsbn() {
        return isbn;
    }

    // Ina-update ang ISBN ng libro.
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    // Sinusuri kung available ang libro.
    public boolean isAvailable() {
        return available;
    }

    // Ibinabalik ang cover image URL.
    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    // Ina-update ang cover image URL.
    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    // Ina-update ang availability ng libro.
    public void setAvailable(boolean available) {
        this.available = available;
    }

    // Minamarkahan ang libro bilang nahiram.
    public void checkOut() {
        this.available = false;
    }

    // Minamarkahan ang libro bilang naibalik at available muli.
    public void returnBook() {
        this.available = true;
    }

    // Gumagawa ng readable representation ng detalye ng libro.
    @Override
    public String toString() {
        return "Book{" +
                "bookID='" + bookID + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", available=" + available +
                '}';
    }

    // Dalawang Book object ay pareho kapag pareho ang book ID.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(bookID, book.bookID);
    }

    // Gumagawa ng hash value batay sa book ID para sa collections.
    @Override
    public int hashCode() {
        return Objects.hash(bookID);
    }
}
