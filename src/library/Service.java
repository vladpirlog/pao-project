package library;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public final class Service {

    /**
     * Add a new client
     */
    public static Client addClient(String firstName, String lastName, String CNP) throws RuntimeException {
        Client client = new Client(firstName, lastName, CNP);
        client.save();
        return client;
    }

    /**
     * Add a new section
     */
    public static Section addSection(String name) throws RuntimeException {
        Section section = new Section(name);
        section.save();
        return section;
    }

    /**
     * Add a new author
     */
    public static Author addAuthor(String firstName, String lastName, Date birthDate) throws RuntimeException {
        Author author = new Author(firstName, lastName, birthDate);
        author.save();
        return author;
    }

    /**
     * Add a new publisher
     */
    public static Publisher addPublisher(String name) throws RuntimeException {
        Publisher publisher = new Publisher(name);
        publisher.save();
        return publisher;
    }

    /**
     * Add a new book
     */
    public static Book addBook(String title, String sectionName, String ISBN, String language, String authorFirstName,
            String authorLastName, String releaseYear, String publisherName) throws RuntimeException {
        Optional<Author> author = Author.findByFullName(authorFirstName, authorLastName);
        if (author.isEmpty())
            throw new RuntimeException("No author with the given names");
        Optional<Section> section = Section.findByName(sectionName);
        if (section.isEmpty())
            throw new RuntimeException("No section with the given name");
        Optional<Publisher> publisher = Publisher.findByName(publisherName);
        if (publisher.isEmpty())
            throw new RuntimeException("No publisher with the given name");
        Book book = new Book(title, section.get(), ISBN, language, author.get(), releaseYear, publisher.get());
        book.save();
        return book;
    }

    /**
     * Add copies for a book
     */
    public static List<ReadableCopy> addBookCopies(String bookISBN, int count) throws RuntimeException {
        Optional<Book> book = Book.findByISBN(bookISBN);
        if (book.isEmpty())
            throw new RuntimeException("No book with the given ISBN");
        for (int i = 0; i < count; i++) {
            book.get().createCopy();
        }
        return book.get().getCopies();
    }

    /**
     * Add new book rental
     */
    public static Rental rentBook(String bookISBN, String clientCNP, Date predictedEndDate) throws RuntimeException {
        Optional<Book> book = Book.findByISBN(bookISBN);
        if (book.isEmpty())
            throw new RuntimeException("No book with the given ISBN");
        Optional<Client> client = Client.findByCNP(clientCNP);
        if (client.isEmpty())
            throw new RuntimeException("No client with the given CNP");
        if (book.get().getNumberOfCopies() == 0)
            throw new RuntimeException("The given book has no copies");
        ReadableCopy copy = book.get().getCopies().get(0);
        Rental rental = new Rental(copy, client.get(), predictedEndDate);
        rental.save();
        return rental;
    }

    /**
     * Add new magazine
     */
    public static Magazine addMagazine(String title, String sectionName, String ISBN, String language, Date issueDate)
            throws RuntimeException {
        Optional<Section> section = Section.findByName(sectionName);
        if (section.isEmpty())
            throw new RuntimeException("No section with the given name");
        Magazine magazine = new Magazine(title, section.get(), ISBN, language, issueDate);
        magazine.save();
        return magazine;
    }

    /**
     * Add copies for a magazine
     */
    public static List<ReadableCopy> addMagazineCopies(String magazineISBN, int count) throws RuntimeException {
        Optional<Magazine> magazine = Magazine.findByISBN(magazineISBN);
        if (magazine.isEmpty())
            throw new RuntimeException("No magazine with the given ISBN");
        for (int i = 0; i < count; i++) {
            magazine.get().createCopy();
        }
        return magazine.get().getCopies();
    }

    /**
     * Add new magazine rental
     */
    public static Rental rentMagazine(String magazineISBN, String clientCNP, Date predictedEndDate)
            throws RuntimeException {
        Optional<Magazine> magazine = Magazine.findByISBN(magazineISBN);
        if (magazine.isEmpty())
            throw new RuntimeException("No magazine with the given ISBN");
        Optional<Client> client = Client.findByCNP(clientCNP);
        if (client.isEmpty())
            throw new RuntimeException("No client with the given CNP");
        if (magazine.get().getNumberOfCopies() == 0)
            throw new RuntimeException("The given book has no copies");
        ReadableCopy copy = magazine.get().getCopies().get(0);
        Rental rental = new Rental(copy, client.get(), predictedEndDate);
        rental.save();
        return rental;
    }

    /**
     * Get all books of an author, using its first and last names
     *
     * @return a list of books sorted by title
     */
    public static List<Book> getBooksOfAuthor(String authorFirstName, String authorLastName) throws RuntimeException {
        Optional<Author> author = Author.findByFullName(authorFirstName, authorLastName);
        if (author.isEmpty())
            throw new RuntimeException("No author with the given name");
        return author.get().getBooks();
    }

    /**
     * Get all books of a publisher, using its name
     *
     * @return a list of books sorted by title
     */
    public static List<Book> getBooksOfPublisher(String publisherName) throws RuntimeException {
        Optional<Publisher> publisher = Publisher.findByName(publisherName);
        if (publisher.isEmpty())
            throw new RuntimeException("No publisher with the given name");
        return publisher.get().getBooks();
    }

    /**
     * Get all rentals of a client, using its CNP
     */
    public static List<Rental> getRentalsOfClient(String clientCNP) throws RuntimeException {
        Optional<Client> client = Client.findByCNP(clientCNP);
        if (client.isEmpty())
            throw new RuntimeException("No client with the given CNP");
        return client.get().getRentals();
    }
}
