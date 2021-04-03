package library;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import library.database.DatabaseSingleton;
import library.interfaces.Saveable;

public class Client extends Entity implements Saveable {
    private String firstName;
    private String lastName;
    private String CNP;
    private List<Rental> rentals;

    public Client(String firstName, String lastName, String CNP) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.CNP = CNP;
        this.rentals = new ArrayList<>();
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

    public List<Rental> getRentals() {
        return new ArrayList<>(rentals);
    }

    public boolean addRental(Rental rental) {
        return rentals.add(rental);
    }

    public boolean removeRental(Rental rental) {
        return rentals.remove(rental);
    }

    public boolean containsRental(Rental rental) {
        return rentals.contains(rental);
    }

    public static Optional<Client> findByID(UUID id) {
        return DatabaseSingleton.getInstance().findClientByID(id);
    }

    public static Optional<Client> findByCNP(String CNP) {
        return DatabaseSingleton.getInstance().findByClientCNP(CNP);
    }

    public static Optional<Client> findByFullName(String firstName, String lastName) {
        return DatabaseSingleton.getInstance().findByClientFullName(firstName, lastName);
    }

    @Override
    public boolean save() throws RuntimeException {
        return DatabaseSingleton.getInstance().saveClient(this);
    }
}
