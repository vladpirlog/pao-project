import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import library.exceptions.NotAvailableForRentalException;
import library.services.UserInteractionService;

import java.io.IOException;

public class Main {
    private static BufferedReader br;
    private static final String MENU_PROMPT = "\nPick an option and hit enter:\n" + "1) Add a client\n"
            + "2) Add a publisher\n" + "3) Add a section\n" + "4) Add an author\n" + "5) Add a book\n"
            + "6) Add a magazine\n" + "7) Rent a book\n" + "8) Rent a magazine\n" + "9) Return rented book\n"
            + "10) Return rented magazine\n" + "11) Generate CSV files from database content\n" + "12) Exit\n";

    private static void addClient() throws IOException {
        System.out.print("First name: ");
        String firstName = br.readLine();

        System.out.print("Last name: ");
        String lastName = br.readLine();

        System.out.print("CNP: ");
        String CNP = br.readLine();

        System.out.println(UserInteractionService.addClient(firstName, lastName, CNP));
        System.out.println("Client added.");
    }

    private static void addPublisher() throws IOException {
        String name;
        System.out.print("Publisher name: ");
        name = br.readLine();

        System.out.println(UserInteractionService.addPublisher(name));
        System.out.println("Publisher added.");
    }

    private static void addSection() throws IOException {
        String name;
        System.out.print("Section name: ");
        name = br.readLine();

        System.out.println(UserInteractionService.addSection(name));
        System.out.println("Section added.");
    }

    private static void addAuthor() throws IOException, ParseException {
        System.out.print("First name: ");
        String firstName = br.readLine();

        System.out.print("Last name: ");
        String lastName = br.readLine();

        System.out.print("Birth date (dd/mm/yyyy): ");
        String birthDate = br.readLine();

        System.out.println(UserInteractionService.addAuthor(firstName, lastName,
                new SimpleDateFormat("dd/MM/yyyy").parse(birthDate)));
        System.out.println("Author added.");
    }

    private static void addBook() throws IOException {
        System.out.print("Title: ");
        String title = br.readLine();

        System.out.print("Section name: ");
        String sectionName = br.readLine();

        System.out.print("ISBN: ");
        String ISBN = br.readLine();

        System.out.print("Language: ");
        String language = br.readLine();

        System.out.print("Author first name: ");
        String authorFirstName = br.readLine();

        System.out.print("Author last name: ");
        String authorLastName = br.readLine();

        System.out.print("Release year: ");
        String releaseYear = br.readLine();

        System.out.print("Publisher name: ");
        String publisherName = br.readLine();

        System.out.println(UserInteractionService.addBook(title, sectionName, ISBN, language, authorFirstName,
                authorLastName, releaseYear, publisherName));
        System.out.println("Book added.");
    }

    private static void returnRentedBook() throws IOException {
        System.out.print("Book ISBN: ");
        String ISBN = br.readLine();

        System.out.print("Client CNP: ");
        String clientCNP = br.readLine();

        System.out.println(UserInteractionService.returnRentedBook(ISBN, clientCNP));
        System.out.println("Book returned to the library.");
    }

    private static void addMagazine() throws IOException, ParseException {
        System.out.print("Title: ");
        String title = br.readLine();

        System.out.print("Section name: ");
        String sectionName = br.readLine();

        System.out.print("ISBN: ");
        String ISBN = br.readLine();

        System.out.print("Language: ");
        String language = br.readLine();

        System.out.print("Issue date (dd/mm/yyyy): ");
        String issueDate = br.readLine();

        System.out.println(UserInteractionService.addMagazine(title, sectionName, ISBN, language,
                new SimpleDateFormat("dd/MM/yyyy").parse(issueDate)));
        System.out.println("Magazine added.");
    }

    private static void returnRentedMagazine() throws IOException {
        System.out.print("Magazine ISBN: ");
        String ISBN = br.readLine();

        System.out.print("Client CNP: ");
        String clientCNP = br.readLine();

        System.out.println(UserInteractionService.returnRentedMagazine(ISBN, clientCNP));
        System.out.println("Magazine returned to the library.");
    }

    public static void rentBook() throws IOException, ParseException, NotAvailableForRentalException {
        System.out.print("Book ISBN: ");
        String ISBN = br.readLine();

        System.out.print("Client CNP: ");
        String clientCNP = br.readLine();

        System.out.print("Predicted end date (dd/mm/yyyy): ");
        String predictedEndDate = br.readLine();

        System.out.println(UserInteractionService.rentBook(ISBN, clientCNP,
                new SimpleDateFormat("dd/MM/yyyy").parse(predictedEndDate)));
        System.out.println("Book rental added.");
    }

    public static void rentMagazine() throws IOException, ParseException, NotAvailableForRentalException {
        System.out.print("Magazine ISBN: ");
        String ISBN = br.readLine();

        System.out.print("Client CNP: ");
        String clientCNP = br.readLine();

        System.out.print("Predicted end date (dd/mm/yyyy): ");
        String predictedEndDate = br.readLine();

        System.out.println(UserInteractionService.rentMagazine(ISBN, clientCNP,
                new SimpleDateFormat("dd/MM/yyyy").parse(predictedEndDate)));
        System.out.println("Magazine rental added.");
    }

    public static void generateCSVFiles() throws IOException {
        System.out.println("Enter the path of the output directory for the CSV files:");
        String directoryPath = br.readLine();
        UserInteractionService.generateCSVFiles(directoryPath);
        System.out.println("CSV files written.");
    }

    public static void main(String[] args) throws ParseException {
        try {
            UserInteractionService.start();
            br = new BufferedReader(new InputStreamReader(System.in));
            String option;
            boolean exitLoop = false;
            while (!exitLoop) {
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
                            addMagazine();
                            break;
                        case "7":
                            rentBook();
                            break;
                        case "8":
                            rentMagazine();
                            break;
                        case "9":
                            returnRentedBook();
                            break;
                        case "10":
                            returnRentedMagazine();
                            break;
                        case "11":
                            generateCSVFiles();
                            break;
                        case "12":
                            exitLoop = true;
                            break;
                        default:
                            System.out.println("Not a valid option.");
                            break;
                    }
                } catch (NotAvailableForRentalException | RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            }
            br.close();
            UserInteractionService.exit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
