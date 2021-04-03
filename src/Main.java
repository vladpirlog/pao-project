import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import library.*;
import library.database.DatabaseSingleton;

import java.io.IOException;

public class Main {
    private static BufferedReader br;
    private static final String MENU_PROMPT = "\nPick an option and hit enter:\n" + "1) Add a client\n"
            + "2) Add a publisher\n" + "3) Add a section\n" + "4) Add an author\n" + "5) Add a book\n"
            + "6) Add book copies\n" + "7) Add a magazine\n" + "8) Add magazine copies\n" + "9) Rent a book\n"
            + "10) Rent a magazine\n" + "11) Exit\n";

    private static void addClient() throws RuntimeException, IOException {
        String firstName, lastName, CNP;
        System.out.print("First name: ");
        firstName = br.readLine();

        System.out.print("Last name: ");
        lastName = br.readLine();

        System.out.print("CNP: ");
        CNP = br.readLine();

        System.out.println(Service.addClient(firstName, lastName, CNP));
        System.out.println("Client added.");
    }

    private static void addPublisher() throws RuntimeException, IOException {
        String name;
        System.out.print("Publisher name: ");
        name = br.readLine();

        System.out.println(Service.addPublisher(name));
        System.out.println("Publisher added.");
    }

    private static void addSection() throws RuntimeException, IOException {
        String name;
        System.out.print("Section name: ");
        name = br.readLine();

        System.out.println(Service.addSection(name));
        System.out.println("Section added.");
    }

    private static void addAuthor() throws RuntimeException, IOException, ParseException {
        String firstName, lastName, birthDate;
        System.out.print("First name: ");
        firstName = br.readLine();

        System.out.print("Last name: ");
        lastName = br.readLine();

        System.out.print("Birth date (dd/mm/yyyy): ");
        birthDate = br.readLine();

        System.out.println(Service.addAuthor(firstName, lastName, new SimpleDateFormat("dd/MM/YYYY").parse(birthDate)));
        System.out.println("Author added.");
    }

    private static void addBook() throws RuntimeException, IOException {
        String title, sectionName, ISBN, language, authorFirstName, authorLastName, releaseYear, publisherName;
        System.out.print("Title: ");
        title = br.readLine();

        System.out.print("Section name: ");
        sectionName = br.readLine();

        System.out.print("ISBN: ");
        ISBN = br.readLine();

        System.out.print("Language: ");
        language = br.readLine();

        System.out.print("Author first name: ");
        authorFirstName = br.readLine();

        System.out.print("Author last name: ");
        authorLastName = br.readLine();

        System.out.print("Release year: ");
        releaseYear = br.readLine();

        System.out.print("Publisher name: ");
        publisherName = br.readLine();

        System.out.println(Service.addBook(title, sectionName, ISBN, language, authorFirstName, authorLastName,
                releaseYear, publisherName));
        System.out.println("Book added.");
    }

    private static void addBookCopies() throws RuntimeException, IOException {
        String ISBN, numberOfCopies;
        System.out.print("Book ISBN: ");
        ISBN = br.readLine();

        System.out.print("Number of copies: ");
        numberOfCopies = br.readLine();

        System.out.println(Service.addBookCopies(ISBN, Integer.parseInt(numberOfCopies)));
        System.out.println("Book copies added.");
    }

    private static void addMagazine() throws RuntimeException, IOException, ParseException {
        String title, sectionName, ISBN, language, issueDate;
        System.out.print("Title: ");
        title = br.readLine();

        System.out.print("Section name: ");
        sectionName = br.readLine();

        System.out.print("ISBN: ");
        ISBN = br.readLine();

        System.out.print("Language: ");
        language = br.readLine();

        System.out.print("Issue date (dd/mm/yyyy): ");
        issueDate = br.readLine();

        System.out.println(Service.addMagazine(title, sectionName, ISBN, language,
                new SimpleDateFormat("dd/MM/yyyy").parse(issueDate)));
        System.out.println("Magazine added.");
    }

    private static void addMagazineCopies() throws RuntimeException, IOException {
        String ISBN, numberOfCopies;
        System.out.print("Magazine ISBN: ");
        ISBN = br.readLine();

        System.out.print("Number of copies: ");
        numberOfCopies = br.readLine();

        System.out.println(Service.addMagazineCopies(ISBN, Integer.parseInt(numberOfCopies)));
        System.out.println("Magazine copies added.");
    }

    public static void rentBook() throws RuntimeException, IOException, ParseException {
        String ISBN, clientCNP, predictedEndDate;
        System.out.print("Book ISBN: ");
        ISBN = br.readLine();

        System.out.print("Client CNP: ");
        clientCNP = br.readLine();

        System.out.print("Predicted end date (dd/mm/yyyy): ");
        predictedEndDate = br.readLine();

        System.out
                .println(Service.rentBook(ISBN, clientCNP, new SimpleDateFormat("dd/MM/yyyy").parse(predictedEndDate)));
        System.out.println("Book rental added.");
    }

    public static void rentMagazine() throws RuntimeException, IOException, ParseException {
        String ISBN, clientCNP, predictedEndDate;
        System.out.print("Magazine ISBN: ");
        ISBN = br.readLine();

        System.out.print("Client CNP: ");
        clientCNP = br.readLine();

        System.out.print("Predicted end date (dd/mm/yyyy): ");
        predictedEndDate = br.readLine();

        System.out.println(
                Service.rentMagazine(ISBN, clientCNP, new SimpleDateFormat("dd/MM/yyyy").parse(predictedEndDate)));
        System.out.println("Magazine rental added.");
    }

    public static void main(String[] args) {
        try {
            DatabaseSingleton.getInstance().connect();
            br = new BufferedReader(new InputStreamReader(System.in));
            String option;
            boolean exit = false;
            while (!exit) {
                try {
                    System.out.print(MENU_PROMPT);
                    option = br.readLine();

                    switch (option) {
                    case "1":
                        addClient();
                        break;
                    case "2":
                        addPublisher();
                        break;
                    case "3":
                        addSection();
                        break;
                    case "4":
                        addAuthor();
                        break;
                    case "5":
                        addBook();
                        break;
                    case "6":
                        addBookCopies();
                        break;
                    case "7":
                        addMagazine();
                        break;
                    case "8":
                        addMagazineCopies();
                        break;
                    case "9":
                        rentBook();
                        break;
                    case "10":
                        rentMagazine();
                        break;
                    case "11":
                        exit = true;
                        break;
                    default:
                        System.out.println("Not a valid option.");
                        break;
                    }
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            }
            br.close();
            DatabaseSingleton.getInstance().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
