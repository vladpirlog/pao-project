package library;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import library.database.DatabaseSingleton;
import library.interfaces.Saveable;

public class Publisher extends Entity implements Saveable {
    private String name;
    private Set<Book> books;

    public Publisher(String name) {
        super();
        this.name = name;
        this.books = new TreeSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    public boolean addBook(Book book) {
        return books.add(book);
    }

    public boolean removeBook(Book book) {
        return books.remove(book);
    }

    public boolean containsBook(Book book) {
        return books.contains(book);
    }

    public static Optional<Publisher> findByID(UUID id) {
        return DatabaseSingleton.getInstance().findPublisherByID(id);
    }

    public static Optional<Publisher> findByName(String name) {
        return DatabaseSingleton.getInstance().findPublisherByName(name);
    }

    @Override
    public boolean save() throws RuntimeException {
        return DatabaseSingleton.getInstance().savePublisher(this);
    }
}
