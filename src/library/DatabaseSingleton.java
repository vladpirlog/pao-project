package library;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import library.interfaces.Closeable;
import library.interfaces.Connectable;

public class DatabaseSingleton implements Connectable, Closeable {
    private static final DatabaseSingleton instance = new DatabaseSingleton();
    private BufferedReader csvReader;
    private BufferedWriter csvWriter;

    private final List<Client> clients = new ArrayList<>();
    private final List<Publisher> publishers = new ArrayList<>();
    private final List<Section> sections = new ArrayList<>();
    private final List<Author> authors = new ArrayList<>();
    private final List<Book> books = new ArrayList<>();
    private final List<Magazine> magazines = new ArrayList<>();
    private final List<Rental> rentals = new ArrayList<>();

    private static final String CLIENTS_CSV_PATH = "db/clients.csv";
    private static final String PUBLISHERS_CSV_PATH = "db/publishers.csv";
    private static final String SECTIONS_CSV_PATH = "db/sections.csv";
    private static final String AUTHORS_CSV_PATH = "db/authors.csv";
    private static final String BOOKS_CSV_PATH = "db/books.csv";
    private static final String MAGAZINES_CSV_PATH = "db/magazines.csv";
    private static final String RENTALS_CSV_PATH = "db/rentals.csv";

    public static DatabaseSingleton getInstance() {
        return instance;
    }

    @Override
    public void connect() throws IOException, ParseException, IndexOutOfBoundsException {
        String row;
        csvReader = new BufferedReader(new FileReader(CLIENTS_CSV_PATH));
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            clients.add(new Client(data));
        }
        csvReader.close();

        csvReader = new BufferedReader(new FileReader(PUBLISHERS_CSV_PATH));
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            publishers.add(new Publisher(data));
        }
        csvReader.close();

        csvReader = new BufferedReader(new FileReader(SECTIONS_CSV_PATH));
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            sections.add(new Section(data));
        }
        csvReader.close();

        csvReader = new BufferedReader(new FileReader(AUTHORS_CSV_PATH));
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            authors.add(new Author(data));
        }
        csvReader.close();

        csvReader = new BufferedReader(new FileReader(BOOKS_CSV_PATH));
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            books.add(new Book(data));
        }
        csvReader.close();

        csvReader = new BufferedReader(new FileReader(MAGAZINES_CSV_PATH));
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            magazines.add(new Magazine(data));
        }
        csvReader.close();

        csvReader = new BufferedReader(new FileReader(RENTALS_CSV_PATH));
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            rentals.add(new Rental(data));
        }
        csvReader.close();
    }

    @Override
    public void close() throws IOException {
        csvWriter = new BufferedWriter(new FileWriter(CLIENTS_CSV_PATH));
        clients.forEach(c -> {
            try {
                csvWriter.write(c.serialize());
                csvWriter.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        csvWriter.close();

        csvWriter = new BufferedWriter(new FileWriter(PUBLISHERS_CSV_PATH));
        publishers.forEach(p -> {
            try {
                csvWriter.write(p.serialize());
                csvWriter.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        csvWriter.close();

        csvWriter = new BufferedWriter(new FileWriter(SECTIONS_CSV_PATH));
        sections.forEach(s -> {
            try {
                csvWriter.write(s.serialize());
                csvWriter.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        csvWriter.close();

        csvWriter = new BufferedWriter(new FileWriter(AUTHORS_CSV_PATH));
        authors.forEach(a -> {
            try {
                csvWriter.write(a.serialize());
                csvWriter.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        csvWriter.close();

        csvWriter = new BufferedWriter(new FileWriter(BOOKS_CSV_PATH));
        books.forEach(b -> {
            try {
                csvWriter.write(b.serialize());
                csvWriter.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        csvWriter.close();

        csvWriter = new BufferedWriter(new FileWriter(MAGAZINES_CSV_PATH));
        magazines.forEach(m -> {
            try {
                csvWriter.write(m.serialize());
                csvWriter.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        csvWriter.close();

        csvWriter = new BufferedWriter(new FileWriter(RENTALS_CSV_PATH));
        rentals.forEach(r -> {
            try {
                csvWriter.write(r.serialize());
                csvWriter.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        csvWriter.close();
    }

    // Database actions for class Author
    public Optional<Author> findAuthorByID(UUID id) {
        return authors.stream().filter(a -> a.getID().equals(id)).findFirst();
    }

    public Optional<Author> findAuthorByFullName(String firstName, String lastName) {
        return authors.stream().filter(a -> a.getFullName().equals(firstName + " " + lastName)).findFirst();
    }

    public boolean saveAuthor(Author author) {
        return authors.contains(author) ? false : authors.add(author);
    }

    public boolean deleteAuthor(Author author) {
        return authors.remove(author);
    }

    // Database actions for class Book
    public Optional<Book> findBookByID(UUID id) {
        return books.stream().filter(b -> b.getID().equals(id)).findFirst();
    }

    public Optional<Book> findBookByTitle(String title) {
        return books.stream().filter(b -> b.getTitle().equals(title)).findFirst();
    }

    public Optional<Book> findBookByISBN(String ISBN) {
        return books.stream().filter(b -> b.getISBN().equals(ISBN)).findFirst();
    }

    public boolean saveBook(Book book) {
        if (books.contains(book))
            return false;
        book.getAuthor().ifPresent(a -> a.addBook(book));
        book.getSection().addReadable(book);
        book.getPublisher().ifPresent(p -> p.addBook(book));
        return books.add(book);
    }

    public boolean deleteBook(Book book) {
        return books.remove(book);
    }

    // Database actions for class Client
    public Optional<Client> findClientByID(UUID id) {
        return clients.stream().filter(c -> c.getID().equals(id)).findFirst();
    }

    public Optional<Client> findByClientCNP(String CNP) {
        return clients.stream().filter(c -> c.getCNP().equals(CNP)).findFirst();
    }

    public Optional<Client> findByClientFullName(String firstName, String lastName) {
        return clients.stream().filter(c -> c.getFullName().equals(firstName + " " + lastName)).findFirst();
    }

    public boolean saveClient(Client client) {
        return clients.contains(client) ? false : clients.add(client);
    }

    public boolean deleteClient(Client client) {
        return clients.remove(client);
    }

    // Database actions for class Magazine
    public Optional<Magazine> findMagazineByID(UUID id) {
        return magazines.stream().filter(m -> m.getID().equals(id)).findFirst();
    }

    public Optional<Magazine> findMagazineByTitle(String title) {
        return magazines.stream().filter(m -> m.getTitle().equals(title)).findFirst();
    }

    public Optional<Magazine> findMagazineByISBN(String ISBN) {
        return magazines.stream().filter(m -> m.getISBN().equals(ISBN)).findFirst();
    }

    public boolean saveMagazine(Magazine magazine) {
        return magazines.contains(magazine) ? false
                : magazines.add(magazine) && magazine.getSection().addReadable(magazine);
    }

    public boolean deleteMagazine(Magazine magazine) {
        return magazines.remove(magazine);
    }

    // Database actions for class Publisher
    public Optional<Publisher> findPublisherByID(UUID id) {
        return publishers.stream().filter(p -> p.getID().equals(id)).findFirst();
    }

    public Optional<Publisher> findPublisherByName(String name) {
        return publishers.stream().filter(p -> p.getName().equals(name)).findFirst();
    }

    public boolean savePublisher(Publisher publisher) {
        return publishers.contains(publisher) ? false : publishers.add(publisher);
    }

    public boolean deletePublisher(Publisher publisher) {
        return publishers.remove(publisher);
    }

    // Database actions for class Rental
    public Optional<Rental> findRentalByID(UUID id) {
        return rentals.stream().filter(r -> r.getID().equals(id)).findFirst();
    }

    public Rental[] findAllRentalsByReadableID(UUID id) {
        return rentals.stream().filter(r -> r.getRentedReadable().getID().equals(id))
                .sorted((r1, r2) -> r1.getStartDate().compareTo(r2.getStartDate())).toArray(Rental[]::new);
    }

    public Rental[] findAllRentalsByClientID(UUID id) {
        return rentals.stream().filter(r -> r.getClient().getID().equals(id))
                .sorted((r1, r2) -> r1.getStartDate().compareTo(r2.getStartDate())).toArray(Rental[]::new);
    }

    public boolean saveRental(Rental rental) {
        return rentals.contains(rental) ? false : rentals.add(rental) && rental.getClient().addRental(rental);
    }

    public boolean deleteRental(Rental rental) {
        return rentals.remove(rental);
    }

    // Database actions for class Section
    public Optional<Section> findSectionByID(UUID id) {
        return sections.stream().filter(s -> s.getID().equals(id)).findFirst();
    }

    public Optional<Section> findSectionByName(String name) {
        return sections.stream().filter(s -> s.getName().equals(name)).findFirst();
    }

    public boolean saveSection(Section section) {
        return sections.contains(section) ? false : sections.add(section);
    }

    public boolean deleteSection(Section section) {
        return sections.remove(section);
    }
}
