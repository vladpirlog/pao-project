package library.database;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import library.*;
import library.interfaces.Connectable;

public class DatabaseSingleton implements Connectable, Closeable {
    private static final DatabaseSingleton instance = new DatabaseSingleton();

    private final List<Client> clients = new ArrayList<>();
    private final List<Publisher> publishers = new ArrayList<>();
    private final List<Section> sections = new ArrayList<>();
    private final List<Author> authors = new ArrayList<>();
    private final List<Book> books = new ArrayList<>();
    private final List<Magazine> magazines = new ArrayList<>();
    private final List<Rental> rentals = new ArrayList<>();

    public static DatabaseSingleton getInstance() {
        return instance;
    }

    @Override
    public void connect() throws IOException {
    }

    @Override
    public void close() throws IOException {
    }

    // Database actions for class Author
    public Optional<Author> findAuthorByID(UUID id) {
        return authors.stream().filter((Author a) -> a.getID().equals(id)).findFirst();
    }

    public Optional<Author> findAuthorByFullName(String firstName, String lastName) {
        return authors.stream().filter((Author a) -> a.getFullName().equals(firstName + " " + lastName)).findFirst();
    }

    public boolean saveAuthor(Author author) throws RuntimeException {
        Optional<Author> existentAuthor = findAuthorByID(author.getID());
        if (existentAuthor.isPresent())
            throw new RuntimeException("Author with the same ID already added");
        return authors.add(author);
    }

    // Database actions for class Book
    public Optional<Book> findBookByID(UUID id) {
        return books.stream().filter((Book b) -> b.getID().equals(id)).findFirst();
    }

    public Optional<Book> findBookByTitle(String title) {
        return books.stream().filter((Book b) -> b.getTitle().equals(title)).findFirst();
    }

    public Optional<Book> findBookByISBN(String ISBN) {
        return books.stream().filter((Book b) -> b.getISBN().equals(ISBN)).findFirst();
    }

    public boolean saveBook(Book book) throws RuntimeException {
        Optional<Book> existentBook = findBookByID(book.getID());
        if (existentBook.isPresent())
            throw new RuntimeException("Book with the same ID already added");
        book.getAuthor().ifPresent((Author a) -> a.addBook(book));
        book.getSection().addReadable(book);
        book.getPublisher().ifPresent((Publisher p) -> p.addBook(book));
        return books.add(book);
    }

    // Database actions for class Client
    public Optional<Client> findClientByID(UUID id) {
        return clients.stream().filter((Client c) -> c.getID().equals(id)).findFirst();
    }

    public Optional<Client> findByClientCNP(String CNP) {
        return clients.stream().filter((Client c) -> c.getCNP().equals(CNP)).findFirst();
    }

    public Optional<Client> findByClientFullName(String firstName, String lastName) {
        return clients.stream().filter((Client c) -> c.getFullName().equals(firstName + " " + lastName)).findFirst();
    }

    public boolean saveClient(Client client) throws RuntimeException {
        Optional<Client> existentClient = findClientByID(client.getID());
        if (existentClient.isPresent())
            throw new RuntimeException("Client with the same ID already added");
        return clients.add(client);
    }

    // Database actions for class Magazine
    public Optional<Magazine> findMagazineByID(UUID id) {
        return magazines.stream().filter((Magazine m) -> m.getID().equals(id)).findFirst();
    }

    public Optional<Magazine> findMagazineByTitle(String title) {
        return magazines.stream().filter((Magazine m) -> m.getTitle().equals(title)).findFirst();
    }

    public Optional<Magazine> findMagazineByISBN(String ISBN) {
        return magazines.stream().filter((Magazine m) -> m.getISBN().equals(ISBN)).findFirst();
    }

    public boolean saveMagazine(Magazine magazine) throws RuntimeException {
        Optional<Magazine> existentMagazine = findMagazineByID(magazine.getID());
        if (existentMagazine.isPresent())
            throw new RuntimeException("Magazine with the same ID already added");
        magazine.getSection().addReadable(magazine);
        return magazines.add(magazine);
    }

    // Database actions for class Publisher
    public Optional<Publisher> findPublisherByID(UUID id) {
        return publishers.stream().filter((Publisher p) -> p.getID().equals(id)).findFirst();
    }

    public Optional<Publisher> findPublisherByName(String name) {
        return publishers.stream().filter((Publisher p) -> p.getName().equals(name)).findFirst();
    }

    public boolean savePublisher(Publisher publisher) throws RuntimeException {
        Optional<Publisher> existentPublisher = findPublisherByID(publisher.getID());
        if (existentPublisher.isPresent())
            throw new RuntimeException("Publisher with the same ID already added");
        return publishers.add(publisher);
    }

    // Database actions for class Rental
    public Optional<Rental> findRentalByID(UUID id) {
        return rentals.stream().filter((Rental r) -> r.getID().equals(id)).findFirst();
    }

    public boolean saveRental(Rental rental) throws RuntimeException {
        Optional<Rental> existentRental = findRentalByID(rental.getID());
        if (existentRental.isPresent())
            throw new RuntimeException("Rental with the same ID already added");
        rental.getClient().addRental(rental);
        return rentals.add(rental);
    }

    // Database actions for class Section
    public Optional<Section> findSectionByID(UUID id) {
        return sections.stream().filter((Section s) -> s.getID().equals(id)).findFirst();

    }

    public Optional<Section> findSectionByName(String name) {
        return sections.stream().filter((Section s) -> s.getName().equals(name)).findFirst();
    }

    public boolean saveSection(Section section) throws RuntimeException {
        Optional<Section> existentSection = findSectionByID(section.getID());
        if (existentSection.isPresent())
            throw new RuntimeException("Section with the same ID already added");
        return sections.add(section);
    }
}
