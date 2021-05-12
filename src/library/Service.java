package library;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

public final class Service {
    private static final String LOG_CSV_PATH = "db/log.csv";
    private static BufferedWriter logWriter;

    /**
     * Start the program. Must be called before any other method call.
     */
    public static void start() throws IOException {
        logWriter = new BufferedWriter(new FileWriter(LOG_CSV_PATH, true));
    }

    /**
     * Exit the program.
     */
    public static void exit() throws IOException {
        DatabaseSingleton.getInstance().close();
        if (logWriter != null)
            logWriter.close();
    }

    /**
     * Add a new client
     *
     * @throws IOException
     */
    public static Client addClient(String firstName, String lastName, String CNP) throws RuntimeException, IOException {
        logWriter.append("addClient," + new Date().toString() + System.lineSeparator());
        Client client = new Client(firstName, lastName, CNP);
        client.save();
        return client;
    }

    /**
     * Add a new section
     *
     * @throws IOException
     */
    public static Section addSection(String name) throws RuntimeException, IOException {
        logWriter.append("addSection," + new Date().toString() + System.lineSeparator());
        Section section = new Section(name);
        section.save();
        return section;
    }

    /**
     * Add a new author
     *
     * @throws IOException
     */
    public static Author addAuthor(String firstName, String lastName, Date birthDate)
            throws RuntimeException, IOException {
        logWriter.append("addAuthor," + new Date().toString() + System.lineSeparator());
        Author author = new Author(firstName, lastName, birthDate);
        author.save();
        return author;
    }

    /**
     * Add a new publisher
     *
     * @throws IOException
     */
    public static Publisher addPublisher(String name) throws RuntimeException, IOException {
        logWriter.append("addPublisher," + new Date().toString() + System.lineSeparator());
        Publisher publisher = new Publisher(name);
        publisher.save();
        return publisher;
    }

    /**
     * Add a new book
     *
     * @throws IOException
     */
    public static Book addBook(String title, String sectionName, String ISBN, String language, String authorFirstName,
            String authorLastName, String releaseYear, String publisherName)
            throws NoSuchElementException, IOException {
        logWriter.append("addBook," + new Date().toString() + System.lineSeparator());
        Author author = Author.findByFullName(authorFirstName, authorLastName).orElseThrow();
        Section section = Section.findByName(sectionName).orElseThrow();
        Publisher publisher = Publisher.findByName(publisherName).orElseThrow();

        Book book = new Book(title, section, ISBN, language, author, releaseYear, publisher);
        book.save();

        return book;
    }

    /**
     * Return a rented book
     *
     * @throws IOException
     */
    public static Rental returnRentedBook(String bookISBN, String clientCNP)
            throws NoSuchElementException, IOException {
        logWriter.append("returnRentedBook," + new Date().toString() + System.lineSeparator());
        Book book = Book.findByISBN(bookISBN).orElseThrow();
        Client client = Client.findByCNP(clientCNP).orElseThrow();
        Rental[] clientRentals = Rental.findAllRentalsByClientID(client.getID());
        Rental rental = Arrays.stream(clientRentals)
                .filter(r -> r.getRentedReadable().getID().equals(book.getID()) && r.getEndDate().isEmpty()).findFirst()
                .orElseThrow();
        rental.setEndDate(new Date());
        rental.save();
        return rental;
    }

    /**
     * Add new book rental
     *
     * @throws IOException
     */
    public static Rental rentBook(String bookISBN, String clientCNP, Date predictedEndDate)
            throws RuntimeException, NoSuchElementException, IOException {
        logWriter.append("rentBook," + new Date().toString() + System.lineSeparator());
        Book book = Book.findByISBN(bookISBN).orElseThrow();
        Client client = Client.findByCNP(clientCNP).orElseThrow();
        if (!book.isAvailableForRental())
            throw new RuntimeException("The book is not available for rental.");
        Rental rental = new Rental(book, client, predictedEndDate);
        rental.save();
        return rental;
    }

    /**
     * Add new magazine
     *
     * @throws IOException
     */
    public static Magazine addMagazine(String title, String sectionName, String ISBN, String language, Date issueDate)
            throws NoSuchElementException, IOException {
        logWriter.append("addMagazine," + new Date().toString() + System.lineSeparator());
        Section section = Section.findByName(sectionName).orElseThrow();

        Magazine magazine = new Magazine(title, section, ISBN, language, issueDate);
        magazine.save();

        return magazine;
    }

    /**
     * Return a rented magazine
     *
     * @throws IOException
     */
    public static Rental returnRentedMagazine(String magazineISBN, String clientCNP)
            throws NoSuchElementException, IOException {
        logWriter.append("returnRentedMagazine," + new Date().toString() + System.lineSeparator());
        Magazine magazine = Magazine.findByISBN(magazineISBN).orElseThrow();
        Client client = Client.findByCNP(clientCNP).orElseThrow();
        Rental[] clientRentals = Rental.findAllRentalsByClientID(client.getID());
        Rental rental = Arrays.stream(clientRentals)
                .filter(r -> r.getRentedReadable().getID().equals(magazine.getID()) && r.getEndDate().isEmpty())
                .findFirst().orElseThrow();
        rental.setEndDate(new Date());
        rental.save();
        return rental;
    }

    /**
     * Add new magazine rental
     *
     * @throws IOException
     */
    public static Rental rentMagazine(String magazineISBN, String clientCNP, Date predictedEndDate)
            throws RuntimeException, NoSuchElementException, IOException {
        logWriter.append("rentMagazine," + new Date().toString() + System.lineSeparator());
        Magazine magazine = Magazine.findByISBN(magazineISBN).orElseThrow();
        Client client = Client.findByCNP(clientCNP).orElseThrow();
        if (!magazine.isAvailableForRental())
            throw new RuntimeException("The magazine is not available for rental.");
        Rental rental = new Rental(magazine, client, predictedEndDate);
        rental.save();
        return rental;
    }

    /**
     * Get all books of an author, using its first and last names
     *
     * @return a list of books sorted by title
     * @throws IOException
     */
    public static List<Book> getBooksOfAuthor(String authorFirstName, String authorLastName)
            throws NoSuchElementException, IOException {
        logWriter.append("getBooksOfAuthor," + new Date().toString() + System.lineSeparator());
        Author author = Author.findByFullName(authorFirstName, authorLastName).orElseThrow();
        return author.getBooks();
    }

    /**
     * Get all books of a publisher, using its name
     *
     * @return a list of books sorted by title
     * @throws IOException
     */
    public static List<Book> getBooksOfPublisher(String publisherName) throws NoSuchElementException, IOException {
        logWriter.append("getBooksOfPublisher," + new Date().toString() + System.lineSeparator());
        Publisher publisher = Publisher.findByName(publisherName).orElseThrow();
        return publisher.getBooks();
    }

    /**
     * Get all rentals of a client, using its CNP
     *
     * @throws IOException
     */
    public static List<Rental> getRentalsOfClient(String clientCNP) throws RuntimeException, IOException {
        logWriter.append("getRentalsOfClient," + new Date().toString() + System.lineSeparator());
        Client client = Client.findByCNP(clientCNP).orElseThrow();
        return client.getRentals();
    }
}
