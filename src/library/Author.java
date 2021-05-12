package library;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import library.interfaces.Deletable;
import library.interfaces.Saveable;
import library.interfaces.Serializable;

public class Author extends Entity implements Saveable, Deletable, Serializable {
    private String firstName;
    private String lastName;
    private Optional<Date> birthDate;
    private Optional<String> birthPlace;
    private Optional<Date> deathDate;
    private Set<Book> books;

    protected Author(String[] data) throws ParseException {
        super(UUID.fromString(data[0]), Util.parseDate(data[1]));
        this.firstName = data[2];
        this.lastName = data[3];
        this.birthDate = data.length <= 4 || data[4].isEmpty() ? Optional.empty()
                : Optional.of(Util.parseDate(data[4]));
        this.birthPlace = data.length <= 5 || data[5].isEmpty() ? Optional.empty() : Optional.of(data[5]);
        this.deathDate = data.length <= 6 || data[6].isEmpty() ? Optional.empty()
                : Optional.of(Util.parseDate(data[6]));
        this.books = new TreeSet<>();
    }

    public Author(String firstName, String lastName, Date birthDate, String birthPlace, Date deathDate) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = Optional.ofNullable(birthDate);
        this.birthPlace = Optional.ofNullable(birthPlace);
        this.deathDate = Optional.ofNullable(deathDate);
        this.books = new TreeSet<>();
    }

    public Author(String firstName, String lastName) {
        this(firstName, lastName, null, null, null);
    }

    public Author(String firstName, String lastName, Date birthDate) {
        this(firstName, lastName, birthDate, null, null);
    }

    public Author(String firstName, String lastName, Date birthDate, String birthPlace) {
        this(firstName, lastName, birthDate, birthPlace, null);
    }

    public Author(String firstName, String lastName, Date birthDate, Date deathDate) {
        this(firstName, lastName, birthDate, null, deathDate);
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Optional<Date> getBirthDate() {
        return birthDate;
    }

    public Optional<String> getBirthPlace() {
        return birthPlace;
    }

    public Optional<Date> getDeathDate() {
        return deathDate;
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

    public static Optional<Author> findByID(UUID id) {
        return DatabaseSingleton.getInstance().findAuthorByID(id);
    }

    public static Optional<Author> findByFullName(String firstName, String lastName) {
        return DatabaseSingleton.getInstance().findAuthorByFullName(firstName, lastName);
    }

    @Override
    public boolean save() {
        return DatabaseSingleton.getInstance().saveAuthor(this);
    }

    @Override
    public boolean delete() {
        return DatabaseSingleton.getInstance().deleteAuthor(this);
    }

    @Override
    public String serialize() {
        String[] fields = { getID().toString(), getCreationDate().toString(), firstName, lastName,
                birthDate.isPresent() ? birthDate.get().toString() : "",
                birthPlace.isPresent() ? birthPlace.get().toString() : "",
                deathDate.isPresent() ? deathDate.get().toString() : "" };
        return String.join(",", fields);
    }
}
