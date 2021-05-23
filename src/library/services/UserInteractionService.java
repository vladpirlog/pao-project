package library.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import library.utils.DatabaseSingleton;
import library.Author;
import library.Book;
import library.Client;
import library.Magazine;
import library.Publisher;
import library.Rental;
import library.Section;
import library.exceptions.NotAvailableForRentalException;

public final class UserInteractionService {
    private static LoggingService loggingService;

    /**
     * Start the program. Must be called before any other method call.
     */
    public static void start() throws IOException, ClassNotFoundException, SQLException {
        DatabaseSingleton.getInstance().connect();
        loggingService = new LoggingService();
    }

    /**
     * Exit the program.
     */
    public static void exit() throws IOException, SQLException {
        DatabaseSingleton.getInstance().close();
        loggingService.exit();
    }

    /**
     * Add a new client
     */
    public static Client addClient(String firstName, String lastName, String CNP) throws IOException {
        loggingService.log("addClient");
        Client client = new Client(firstName, lastName, CNP);
        client.save();
        return client;
    }

    /**
     * Add a new section
     */
    public static Section addSection(String name) throws IOException {
        loggingService.log("addSection");
        Section section = new Section(name);
        section.save();
        return section;
    }

    /**
     * Add a new author
     */
    public static Author addAuthor(String firstName, String lastName, Date birthDate) throws IOException {
        loggingService.log("addAuthor");
        Author author = new Author(firstName, lastName, birthDate);
        author.save();
        return author;
    }

    /**
     * Add a new publisher
     */
    public static Publisher addPublisher(String name) throws IOException {
        loggingService.log("addPublisher");
        Publisher publisher = new Publisher(name);
        publisher.save();
        return publisher;
    }

    /**
     * Add a new book
     */
    public static Book addBook(String title, String sectionName, String ISBN, String language, String authorFirstName,
            String authorLastName, String releaseYear, String publisherName)
            throws NoSuchElementException, IOException {
        loggingService.log("addBook");
        Author author = Author.findByFullName(authorFirstName, authorLastName).orElseThrow();
        Section section = Section.findByName(sectionName).orElseThrow();
        Optional<Publisher> publisher = Publisher.findByName(publisherName);

        Book book = new Book(title, section, ISBN, language, author, releaseYear, publisher.orElse(null));
        book.save();

        return book;
    }

    /**
     * Return a rented book
     */
    public static Rental returnRentedBook(String bookISBN, String clientCNP)
            throws NoSuchElementException, IOException {
        loggingService.log("returnRentedBook");
        Book book = Book.findByISBN(bookISBN).orElseThrow();
        Client client = Client.findByCNP(clientCNP).orElseThrow();
        Rental[] clientRentals = Rental.findByClient(client);
        Rental rental = Arrays.stream(clientRentals)
                .filter(r -> r.getRentedReadable(false).getID().equals(book.getID()) && r.getEndDate().isEmpty())
                .findFirst().orElseThrow();
        rental.setEndDate(new Date());
        rental.save();
        return rental;
    }

    /**
     * Add new book rental
     */
    public static Rental rentBook(String bookISBN, String clientCNP, Date predictedEndDate)
            throws NoSuchElementException, IOException, NotAvailableForRentalException {
        loggingService.log("rentBook");
        Book book = Book.findByISBN(bookISBN).orElseThrow();
        Client client = Client.findByCNP(clientCNP).orElseThrow();
        return book.rent(client, predictedEndDate);
    }

    /**
     * Add new magazine
     */
    public static Magazine addMagazine(String title, String sectionName, String ISBN, String language, Date issueDate)
            throws NoSuchElementException, IOException {
        loggingService.log("addMagazine");
        Section section = Section.findByName(sectionName).orElseThrow();

        Magazine magazine = new Magazine(title, section, ISBN, language, issueDate);
        magazine.save();

        return magazine;
    }

    /**
     * Return a rented magazine
     */
    public static Rental returnRentedMagazine(String magazineISBN, String clientCNP)
            throws NoSuchElementException, IOException {
        loggingService.log("returnRentedMagazine");
        Magazine magazine = Magazine.findByISBN(magazineISBN).orElseThrow();
        Client client = Client.findByCNP(clientCNP).orElseThrow();
        Rental[] clientRentals = Rental.findByClient(client);
        Rental rental = Arrays.stream(clientRentals)
                .filter(r -> r.getRentedReadable(false).getID().equals(magazine.getID()) && r.getEndDate().isEmpty())
                .findFirst().orElseThrow();
        rental.setEndDate(new Date());
        rental.save();
        return rental;
    }

    /**
     * Add new magazine rental
     */
    public static Rental rentMagazine(String magazineISBN, String clientCNP, Date predictedEndDate)
            throws NoSuchElementException, IOException, NotAvailableForRentalException {
        loggingService.log("rentMagazine");
        Magazine magazine = Magazine.findByISBN(magazineISBN).orElseThrow();
        Client client = Client.findByCNP(clientCNP).orElseThrow();
        return magazine.rent(client, predictedEndDate);
    }

    /**
     * Get all books of an author, using its first and last names
     *
     * @return a list of books sorted by title
     */
    public static List<Book> getBooksOfAuthor(String authorFirstName, String authorLastName)
            throws NoSuchElementException, IOException {
        loggingService.log("getBooksOfAuthor");
        Author author = Author.findByFullName(authorFirstName, authorLastName).orElseThrow();
        return new ArrayList<>(author.getBooks(false));
    }

    /**
     * Get all books of a publisher, using its name
     *
     * @return a list of books sorted by title
     */
    public static List<Book> getBooksOfPublisher(String publisherName) throws NoSuchElementException, IOException {
        loggingService.log("getBooksOfPublisher");
        Optional<Publisher> publisher = Publisher.findByName(publisherName);
        if (publisher.isEmpty())
            return new ArrayList<>();
        return new ArrayList<>(publisher.get().getBooks(false));
    }

    /**
     * Get all rentals of a client, using its CNP
     */
    public static List<Rental> getRentalsOfClient(String clientCNP) throws IOException {
        loggingService.log("getRentalsOfClient");
        Client client = Client.findByCNP(clientCNP).orElseThrow();
        return client.getRentals(false);
    }

    /**
     * Generate CSV files using the information in the database
     */
    public static void generateCSVFiles(String directoryPath) throws IOException {
        loggingService.log("generateCSVFiles");
        BufferedWriter bw;
        bw = new BufferedWriter(new FileWriter(directoryPath + "/authors.csv"));
        bw.write("id,creationDate,firstName,lastName,birthDate,birthPlace,deathDate" + System.lineSeparator());
        bw.write(String.join(System.lineSeparator(),
                Arrays.stream(Author.findAll()).map(Author::serialize).toArray(String[]::new)));
        bw.close();

        bw = new BufferedWriter(new FileWriter(directoryPath + "/books.csv"));
        bw.write("id,creationDate,title,sectionId,ISBN,language,authorId,releaseYear,publisherId"
                + System.lineSeparator());
        bw.write(String.join(System.lineSeparator(),
                Arrays.stream(Book.findAll()).map(Book::serialize).toArray(String[]::new)));
        bw.close();

        bw = new BufferedWriter(new FileWriter(directoryPath + "/clients.csv"));
        bw.write("id,creationDate,firstName,lastName,CNP" + System.lineSeparator());
        bw.write(String.join(System.lineSeparator(),
                Arrays.stream(Client.findAll()).map(Client::serialize).toArray(String[]::new)));
        bw.close();

        bw = new BufferedWriter(new FileWriter(directoryPath + "/magazines.csv"));
        bw.write("id,creationDate,title,sectionId,ISBN,language,issueDate" + System.lineSeparator());
        bw.write(String.join(System.lineSeparator(),
                Arrays.stream(Magazine.findAll()).map(Magazine::serialize).toArray(String[]::new)));
        bw.close();

        bw = new BufferedWriter(new FileWriter(directoryPath + "/publishers.csv"));
        bw.write("id,creationDate,name" + System.lineSeparator());
        bw.write(String.join(System.lineSeparator(),
                Arrays.stream(Publisher.findAll()).map(Publisher::serialize).toArray(String[]::new)));
        bw.close();

        bw = new BufferedWriter(new FileWriter(directoryPath + "/rentals.csv"));
        bw.write("id,creationDate,clientId,startDate,predictedEndDate,endDate,readableId" + System.lineSeparator());
        bw.write(String.join(System.lineSeparator(),
                Arrays.stream(Rental.findAll()).map(Rental::serialize).toArray(String[]::new)));
        bw.close();

        bw = new BufferedWriter(new FileWriter(directoryPath + "/sections.csv"));
        bw.write("id,creationDate,name" + System.lineSeparator());
        bw.write(String.join(System.lineSeparator(),
                Arrays.stream(Section.findAll()).map(Section::serialize).toArray(String[]::new)));
        bw.close();
    }
}
