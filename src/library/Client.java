package library;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import library.interfaces.Deletable;
import library.interfaces.Saveable;
import library.interfaces.Serializable;

public class Client extends Entity implements Saveable, Deletable, Serializable {
    private String firstName;
    private String lastName;
    private String CNP;
    private List<Rental> rentals;

    protected Client(String[] data) throws ParseException, IndexOutOfBoundsException {
        super(UUID.fromString(data[0]), Util.parseDate(data[1]));
        this.firstName = data[2];
        this.lastName = data[3];
        this.CNP = data[4];
        this.rentals = new ArrayList<>();
    }

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
    public boolean save() {
        return DatabaseSingleton.getInstance().saveClient(this);
    }

    @Override
    public boolean delete() {
        return DatabaseSingleton.getInstance().deleteClient(this);
    }

    @Override
    public String serialize() {
        String[] fields = { getID().toString(), getCreationDate().toString(), firstName, lastName, CNP };
        return String.join(",", fields);
    }
}
