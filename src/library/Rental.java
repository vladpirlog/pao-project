package library;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import library.database.DatabaseSingleton;
import library.interfaces.Saveable;

public class Rental extends Entity implements Saveable {
    private Client client;
    private Date startDate;
    private Date predictedEndDate;
    private Optional<Date> endDate;
    private ReadableCopy rentedCopy;

    public Rental(ReadableCopy rentedCopy, Client client, Date startDate, Date predictedEndDate, Date endDate) {
        super();
        this.client = client;
        this.startDate = startDate;
        this.predictedEndDate = predictedEndDate;
        this.endDate = Optional.ofNullable(endDate);
        this.rentedCopy = rentedCopy;
    }

    public Rental(ReadableCopy rentedCopy, Client client, Date startDate, Date predictedEndDate) {
        this(rentedCopy, client, startDate, predictedEndDate, null);
    }

    public Rental(ReadableCopy rentedCopy, Client client, Date predictedEndDate) {
        this(rentedCopy, client, new Date(), predictedEndDate, null);
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

    public ReadableCopy getRentedCopy() {
        return rentedCopy;
    }

    public void setRentedCopy(ReadableCopy rentedCopy) {
        this.rentedCopy = rentedCopy;
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

    @Override
    public boolean save() throws RuntimeException {
        return DatabaseSingleton.getInstance().saveRental(this);
    }
}
