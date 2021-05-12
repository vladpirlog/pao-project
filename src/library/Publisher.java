package library;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import library.interfaces.Deletable;
import library.interfaces.Saveable;
import library.interfaces.Serializable;

public class Publisher extends Entity implements Saveable, Deletable, Serializable {
    private String name;
    private Set<Book> books;

    protected Publisher(String[] data) throws ParseException, IndexOutOfBoundsException {
        super(UUID.fromString(data[0]), Util.parseDate(data[1]));
        this.name = data[2];
        this.books = new TreeSet<>();
    }

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
    public boolean save() {
        return DatabaseSingleton.getInstance().savePublisher(this);
    }

    @Override
    public boolean delete() {
        return DatabaseSingleton.getInstance().deletePublisher(this);
    }

    @Override
    public String serialize() {
        String[] fields = { getID().toString(), getCreationDate().toString(), name };
        return String.join(",", fields);
    }
}
