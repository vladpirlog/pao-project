package library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import library.exceptions.SectionNotFoundException;
import library.services.AuthorService;
import library.services.BookService;
import library.services.PublisherService;

public class Book extends Readable {
    private Optional<Author> author;
    private Optional<String> releaseYear;
    private Optional<Publisher> publisher;

    /**
     * Used for initialising books from the database.
     */
    private Book(UUID id, Date creationDate, String title, String ISBN, String language, String releaseYear) {
        super(id, creationDate, title, null, ISBN, language);
        this.releaseYear = Optional.ofNullable(releaseYear);
    }

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

    /**
     * Lazy-load or force a refresh of the author field.
     */
    public Optional<Author> getAuthor(boolean forceRefetch) {
        if (author == null || forceRefetch)
            author = AuthorService.findAuthorByBook(this);
        return author;
    }

    public void setAuthor(Author author) {
        this.author = Optional.ofNullable(author);
    }

    public Optional<String> getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = Optional.of(releaseYear);
    }

    /**
     * Lazy-load or force a refresh of the publisher field.
     */
    public Optional<Publisher> getPublisher(boolean forceRefetch) {
        if (publisher == null || forceRefetch)
            publisher = PublisherService.findPublisherByBook(this);
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = Optional.ofNullable(publisher);
    }

    public static Book[] findAll() {
        return BookService.findAllBooks();
    }

    public static Optional<Book> findByID(UUID id) {
        return BookService.findBookByID(id);
    }

    public static Book[] findByTitle(String title) {
        return BookService.findBooksByTitle(title);
    }

    public static Optional<Book> findByISBN(String ISBN) {
        return BookService.findBookByISBN(ISBN);
    }

    public static Book[] findBooksBySection(Section section) {
        return BookService.findBooksBySection(section);
    }

    public static Book[] findBooksByAuthor(Author author) {
        return BookService.findBooksByAuthor(author);
    }

    public static Book[] findBooksByPublisher(Publisher publisher) {
        return BookService.findBooksByPublisher(publisher);
    }

    public static Optional<Book> findBookByRental(Rental rental) {
        return BookService.findBookByRental(rental);
    }

    @Override
    public boolean save() {
        return BookService.saveBook(this);
    }

    @Override
    public boolean delete() {
        return BookService.deleteBook(this);
    }

    @Override
    public String serialize() {
        String sectionIDString = "";

        try {
            sectionIDString = getSection(false).getID().toString();
        } catch (SectionNotFoundException e) {
            e.printStackTrace();
        }

        String[] fields = { getID().toString(), getCreationDate().toString(), getTitle(), sectionIDString, getISBN(),
                getLanguage(), getAuthor(false).isPresent() ? getAuthor(false).get().getID().toString() : "",
                getReleaseYear().isPresent() ? getReleaseYear().get() : "",
                getPublisher(false).isPresent() ? getPublisher(false).get().getID().toString() : "" };
        return String.join(",", fields);
    }

    /**
     * SQL table: id, creationDate, title, sectionId, ISBN, language, authorId,
     * releaseYear, publisherId
     */
    public static Book fromResultSet(ResultSet resultSet) throws SQLException {
        return new Book(UUID.fromString(resultSet.getString(1)), resultSet.getTimestamp(2), resultSet.getString(3),
                resultSet.getString(5), resultSet.getString(6), resultSet.getString(8));
    }
}
