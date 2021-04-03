package library;

import java.util.Optional;
import java.util.UUID;

import library.database.DatabaseSingleton;
import library.interfaces.Saveable;

public class Book extends Readable implements Saveable {
    private Optional<Author> author;
    private Optional<String> releaseYear;
    private Optional<Publisher> publisher;

    public Book(String title, Section section, String ISBN, String language, Author author, String releaseYear,
            Publisher publisher) {
        super(title, section, ISBN, language);
        this.author = Optional.ofNullable(author);
        this.releaseYear = Optional.ofNullable(releaseYear);
        this.publisher = Optional.ofNullable(publisher);
    }

    public Book(String title, Section section, String ISBN, String language, Author author, String releaseYear) {
        this(title, section, ISBN, language, author, releaseYear, null);
    }

    public Book(String title, Section section, String ISBN, String language, Author author) {
        this(title, section, ISBN, language, author, null, null);
    }

    public Book(String title, Section section, String ISBN, String language) {
        this(title, section, ISBN, language, null, null, null);
    }

    public Optional<Author> getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = Optional.of(author);
    }

    public Optional<String> getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = Optional.of(releaseYear);
    }

    public Optional<Publisher> getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = Optional.of(publisher);
    }

    public static Optional<Book> findByID(UUID id) {
        return DatabaseSingleton.getInstance().findBookByID(id);
    }

    public static Optional<Book> findByTitle(String title) {
        return DatabaseSingleton.getInstance().findBookByTitle(title);
    }

    public static Optional<Book> findByISBN(String ISBN) {
        return DatabaseSingleton.getInstance().findBookByISBN(ISBN);
    }

    @Override
    public boolean save() throws RuntimeException {
        return DatabaseSingleton.getInstance().saveBook(this);
    }
}
