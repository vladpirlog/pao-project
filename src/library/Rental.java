package library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import library.exceptions.ClientNotFoundException;
import library.services.BookService;
import library.services.ClientService;
import library.services.MagazineService;
import library.services.RentalService;

public class Rental extends Entity {
    private Client client;
    private Date startDate;
    private Date predictedEndDate;
    private Optional<Date> endDate;
    private Readable rentedReadable;

    /**
     * Used for initialising rentals from the database.
     */
    private Rental(UUID id, Date creationDate, Date startDate, Date predictedEndDate, Date endDate) {
        super(id, creationDate);
        this.startDate = startDate;
        this.predictedEndDate = predictedEndDate;
        this.endDate = Optional.ofNullable(endDate);
    }

    public Rental(Readable rentedReadable, Client client, Date startDate, Date predictedEndDate, Date endDate) {
        this.client = client;
        this.startDate = startDate;
        this.predictedEndDate = predictedEndDate;
        this.endDate = Optional.ofNullable(endDate);
        this.rentedReadable = rentedReadable;
    }

    public Rental(Readable rentedReadable, Client client, Date startDate, Date predictedEndDate) {
        this(rentedReadable, client, startDate, predictedEndDate, null);
    }

    public Rental(Readable rentedReadable, Client client, Date predictedEndDate) {
        this(rentedReadable, client, new Date(), predictedEndDate, null);
    }

    /**
     * Lazy-load or force a refresh of the client field.
     *
     * @throws ClientNotFoundException
     */
    public Client getClient(boolean forceRefetch) throws ClientNotFoundException {
        if (client == null || forceRefetch)
            client = ClientService.findClientByRental(this);
        return client;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getPredictedEndDate() {
        return predictedEndDate;
    }

    public Optional<Date> getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = Optional.of(endDate);
    }

    /**
     * Lazy-load or force a refresh of the rented readable field.
     */
    public Readable getRentedReadable(boolean forceRefetch) throws NoSuchElementException {
        if (rentedReadable == null || forceRefetch) {
            Optional<Book> rentedBook = BookService.findBookByRental(this);
            if (rentedBook.isPresent()) {
                rentedReadable = rentedBook.get();
            } else {
                rentedReadable = MagazineService.findMagazineByRental(this).orElseThrow();
            }
        }
        return rentedReadable;
    }

    public void setRentedReadable(Readable rentedReadable) {
        this.rentedReadable = rentedReadable;
    }

    /**
     * @return the value in days
     */
    public Long getPredictedRentalDuration() {
        return Long.valueOf((predictedEndDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * @return the value in days
     */
    public Optional<Long> getRentalDuration() {
        if (endDate.isEmpty()) {
            return Optional.empty();
        }
        long numDays = (endDate.get().getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
        return Optional.of(Long.valueOf(numDays));
    }

    public static Rental[] findAll() {
        return RentalService.findAllRentals();
    }

    public static Optional<Rental> findByID(UUID id) {
        return RentalService.findRentalByID(id);
    }

    public static Rental[] findByReadable(Readable readable) {
        return RentalService.findRentalsByReadable(readable);
    }

    public static Rental[] findByClient(Client client) {
        return RentalService.findRentalsByClient(client);
    }

    @Override
    public boolean save() {
        return RentalService.saveRental(this);
    }

    @Override
    public boolean delete() {
        return RentalService.deleteRental(this);
    }

    @Override
    public String serialize() {
        String clientIDString = "";
        try {
            clientIDString = getClient(false).getID().toString();
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        }
        String[] fields = { getID().toString(), getCreationDate().toString(), clientIDString, getStartDate().toString(),
                getPredictedEndDate().toString(), getEndDate().get().toString(),
                getRentedReadable(false).getID().toString() };
        return String.join(",", fields);
    }

    /**
     * SQL table: id, creationDate, clientId, startDate, predictedEndDate, endDate,
     * readableId
     */
    public static Rental fromResultSet(ResultSet resultSet) throws SQLException {
        return new Rental(UUID.fromString(resultSet.getString(1)), resultSet.getTimestamp(2), resultSet.getTimestamp(4),
                resultSet.getTimestamp(5), resultSet.getTimestamp(6));
    }
}
