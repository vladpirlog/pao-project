package library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import library.services.AuthorService;
import library.services.BookService;

public class Author extends Entity {
    private String firstName;
    private String lastName;
    private Optional<Date> birthDate;
    private Optional<String> birthPlace;
    private Optional<Date> deathDate;
    private Set<Book> books;

    /**
     * Used for initialising authors from the database.
     */
    private Author(UUID id, Date creationDate, String firstName, String lastName, Date birthDate, String birthPlace,
            Date deathDate) {
        super(id, creationDate);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = Optional.ofNullable(birthDate);
        this.birthPlace = Optional.ofNullable(birthPlace);
        this.deathDate = Optional.ofNullable(deathDate);
    }

    public Author(String firstName, String lastName, Date birthDate, String birthPlace, Date deathDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = Optional.ofNullable(birthDate);
        this.birthPlace = Optional.ofNullable(birthPlace);
        this.deathDate = Optional.ofNullable(deathDate);
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

    public void setBirthDate(Date birthDate) {
        this.birthDate = Optional.ofNullable(birthDate);
    }

    public Optional<String> getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = Optional.ofNullable(birthPlace);
    }

    public Optional<Date> getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(Date deathDate) {
        this.deathDate = Optional.ofNullable(deathDate);
    }

    /**
     * Lazy-load or force a refresh of the books set.
     */
    public Set<Book> getBooks(boolean forceRefetch) {
        if (books == null || forceRefetch)
            books = new TreeSet<>(Arrays.asList(BookService.findBooksByAuthor(this)));
        return books;
    }

    public static Author[] findAll() {
        return AuthorService.findAllAuthors();
    }

    public static Optional<Author> findByID(UUID id) {
        return AuthorService.findAuthorByID(id);
    }

    public static Optional<Author> findByFullName(String firstName, String lastName) {
        return AuthorService.findAuthorByFullName(firstName, lastName);
    }

    public static Optional<Author> findByBook(Book book) {
        return AuthorService.findAuthorByBook(book);
    }

    @Override
    public boolean save() {
        return AuthorService.saveAuthor(this);
    }

    @Override
    public boolean delete() {
        return AuthorService.deleteAuthor(this);
    }

    @Override
    public String serialize() {
        String[] fields = { getID().toString(), getCreationDate().toString(), getFirstName(), getLastName(),
                getBirthDate().isPresent() ? getBirthDate().get().toString() : "",
                getBirthPlace().isPresent() ? getBirthPlace().get().toString() : "",
                getDeathDate().isPresent() ? getDeathDate().get().toString() : "" };
        return String.join(",", fields);
    }

    /**
     * SQL table: id, creationDate, firstName, lastName, birthDate, birthPlace,
     * deathDate
     */
    public static Author fromResultSet(ResultSet resultSet) throws SQLException {
        return new Author(UUID.fromString(resultSet.getString(1)), resultSet.getTimestamp(2), resultSet.getString(3),
                resultSet.getString(4), resultSet.getDate(5), resultSet.getString(6), resultSet.getDate(7));
    }
}
