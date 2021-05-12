package library;

import java.text.ParseException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import library.interfaces.Deletable;
import library.interfaces.Saveable;
import library.interfaces.Serializable;

public class Book extends Readable implements Saveable, Deletable, Serializable {
    private Optional<Author> author;
    private Optional<String> releaseYear;
    private Optional<Publisher> publisher;

    protected Book(String[] data) throws ParseException, IndexOutOfBoundsException, NoSuchElementException {
        super(UUID.fromString(data[0]), Util.parseDate(data[1]), data[2],
                Section.findByID(UUID.fromString(data[3])).orElseThrow(), data[4], data[5]);
        this.author = Author.findByID(UUID.fromString(data[6]));
        this.releaseYear = data[7].isEmpty() ? Optional.empty() : Optional.of(data[7]);
        this.publisher = Publisher.findByID(UUID.fromString(data[8]));

        this.author.ifPresent(a -> a.addBook(this));
        this.publisher.ifPresent(p -> p.addBook(this));
        this.getSection().addReadable(this);
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
    public boolean save() {
        return DatabaseSingleton.getInstance().saveBook(this);
    }

    @Override
    public boolean delete() {
        return DatabaseSingleton.getInstance().deleteBook(this);
    }

    @Override
    public String serialize() {
        String[] fields = { getID().toString(), getCreationDate().toString(), getTitle(),
                getSection().getID().toString(), getISBN(), getLanguage(),
                author.isPresent() ? author.get().getID().toString() : "",
                releaseYear.isPresent() ? releaseYear.get() : "",
                publisher.isPresent() ? publisher.get().getID().toString() : "" };
        return String.join(",", fields);
    }
}
