package library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import library.exceptions.ClientNotFoundException;
import library.services.ClientService;
import library.services.RentalService;

public class Client extends Entity {
    private String firstName;
    private String lastName;
    private String CNP;
    private List<Rental> rentals;

    /**
     * Used for initialising clients from the database.
     */
    private Client(UUID id, Date creationDate, String firstName, String lastName, String CNP) {
        super(id, creationDate);
        this.firstName = firstName;
        this.lastName = lastName;
        this.CNP = CNP;
    }

    public Client(String firstName, String lastName, String CNP) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.CNP = CNP;
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

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    /**
     * Lazy-load or force a refresh of the rentals list.
     */
    public List<Rental> getRentals(boolean forceRefetch) {
        if (rentals == null || forceRefetch)
            rentals = Arrays.asList(RentalService.findRentalsByClient(this));
        return rentals;
    }

    public static Client[] findAll() {
        return ClientService.findAllClients();
    }

    public static Optional<Client> findByID(UUID id) {
        return ClientService.findClientByID(id);
    }

    public static Optional<Client> findByCNP(String CNP) {
        return ClientService.findClientByCNP(CNP);
    }

    public static Client[] findByFullName(String firstName, String lastName) {
        return ClientService.findClientsByFullName(firstName, lastName);
    }

    public static Client findByRental(Rental rental) throws ClientNotFoundException {
        return ClientService.findClientByRental(rental);
    }

    @Override
    public boolean save() {
        return ClientService.saveClient(this);
    }

    @Override
    public boolean delete() {
        return ClientService.deleteClient(this);
    }

    @Override
    public String serialize() {
        String[] fields = { getID().toString(), getCreationDate().toString(), getFirstName(), getLastName(), getCNP() };
        return String.join(",", fields);
    }

    /**
     * SQL table: id, creationDate, firstName, lastName, CNP
     */
    public static Client fromResultSet(ResultSet resultSet) throws SQLException {
        return new Client(UUID.fromString(resultSet.getString(1)), resultSet.getTimestamp(2), resultSet.getString(3),
                resultSet.getString(4), resultSet.getString(5));
    }
}
