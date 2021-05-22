package library.services;

import java.util.Optional;
import java.util.UUID;

import library.Client;
import library.Readable;
import library.Rental;
import library.repository.actionsImpl.RentalRepositoryImpl;

public class RentalService {
    private static RentalRepositoryImpl rentalRepository = new RentalRepositoryImpl();

    public static Rental[] findAllRentals() {
        return rentalRepository.findAllRentals();
    }

    public static Optional<Rental> findRentalByID(UUID id) {
        return rentalRepository.findRentalByID(id);
    }

    public static Rental[] findRentalsByReadable(Readable readable) {
        return rentalRepository.findRentalsByReadable(readable);
    }

    public static Rental[] findRentalsByClient(Client client) {
        return rentalRepository.findRentalsByClient(client);
    }

    public static boolean saveRental(Rental rental) {
        return rentalRepository.saveRental(rental);
    }

    public static boolean deleteRental(Rental rental) {
        return rentalRepository.deleteRental(rental);
    }
}
