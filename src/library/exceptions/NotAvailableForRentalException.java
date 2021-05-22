package library.exceptions;

public class NotAvailableForRentalException extends Exception {
    public NotAvailableForRentalException() {
        super("Not available for rental.");
    }

    public NotAvailableForRentalException(String message) {
        super(message);
    }
}
