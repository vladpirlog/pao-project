package library;

import java.text.ParseException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import library.interfaces.Deletable;
import library.interfaces.Saveable;
import library.interfaces.Serializable;

public class Rental extends Entity implements Saveable, Deletable, Serializable {
    private Client client;
    private Date startDate;
    private Date predictedEndDate;
    private Optional<Date> endDate;
    private Readable rentedReadable;

    protected Rental(String[] data) throws ParseException, IndexOutOfBoundsException, NoSuchElementException {
        super(UUID.fromString(data[0]), Util.parseDate(data[1]));

        this.client = Client.findByID(UUID.fromString(data[2])).orElseThrow();
        this.startDate = Util.parseDate(data[3]);
        this.predictedEndDate = Util.parseDate(data[4]);
        this.endDate = data[5].isEmpty() ? Optional.empty() : Optional.of(Util.parseDate(data[5]));

        Optional<Book> book = Book.findByID(UUID.fromString(data[6]));
        Optional<Magazine> magazine = Magazine.findByID(UUID.fromString(data[6]));
        if (book.isEmpty() && magazine.isEmpty())
            throw new NoSuchElementException();
        this.rentedReadable = book.isEmpty() ? magazine.get() : book.get();
        this.client.addRental(this);
    }

    public Rental(Readable rentedReadable, Client client, Date startDate, Date predictedEndDate, Date endDate) {
        super();
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

    public Client getClient() {
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

    public Readable getRentedReadable() {
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

    public static Optional<Rental> findByID(UUID id) {
        return DatabaseSingleton.getInstance().findRentalByID(id);
    }

    public static Rental[] findAllRentalsByReadableID(UUID id) {
        return DatabaseSingleton.getInstance().findAllRentalsByReadableID(id);
    }

    public static Rental[] findAllRentalsByClientID(UUID id) {
        return DatabaseSingleton.getInstance().findAllRentalsByClientID(id);
    }

    @Override
    public boolean save() {
        return DatabaseSingleton.getInstance().saveRental(this);
    }

    @Override
    public boolean delete() {
        return DatabaseSingleton.getInstance().deleteRental(this);
    }

    @Override
    public String serialize() {
        String[] fields = { getID().toString(), getCreationDate().toString(), client.getID().toString(),
                startDate.toString(), predictedEndDate.toString(), endDate.get().toString(),
                rentedReadable.getID().toString() };
        return String.join(",", fields);
    }
}
