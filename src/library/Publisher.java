package library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import library.services.BookService;
import library.services.PublisherService;

public class Publisher extends Entity {
    private String name;
    private Set<Book> books;

    /**
     * Used for initialising publishers from the database.
     */
    private Publisher(UUID id, Date creationDate, String name) {
        super(id, creationDate);
        this.name = name;
    }

    public Publisher(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Lazy-load or force a refresh of the books set.
     */
    public Set<Book> getBooks(boolean forceRefetch) {
        if (books == null || forceRefetch)
            books = new TreeSet<>(Arrays.asList(BookService.findBooksByPublisher(this)));
        return books;
    }

    public static Publisher[] findAll() {
        return PublisherService.findAllPublishers();
    }

    public static Optional<Publisher> findByID(UUID id) {
        return PublisherService.findPublisherByID(id);
    }

    public static Optional<Publisher> findByName(String name) {
        return PublisherService.findPublisherByName(name);
    }

    public static Optional<Publisher> findByBook(Book book) {
        return PublisherService.findPublisherByBook(book);
    }

    @Override
    public boolean save() {
        return PublisherService.savePublisher(this);
    }

    @Override
    public boolean delete() {
        return PublisherService.deletePublisher(this);
    }

    @Override
    public String serialize() {
        String[] fields = { getID().toString(), getCreationDate().toString(), getName() };
        return String.join(",", fields);
    }

    /**
     * SQL table: id, creationDate, name
     */
    public static Publisher fromResultSet(ResultSet resultSet) throws SQLException {
        return new Publisher(UUID.fromString(resultSet.getString(1)), resultSet.getTimestamp(2),
                resultSet.getString(3));
    }
}
