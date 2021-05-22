package library.repository.actions;

import java.util.Optional;
import java.util.UUID;

import library.Client;
import library.Readable;
import library.Rental;

public interface RentalRepository {
    Rental[] findAllRentals();

    Optional<Rental> findRentalByID(UUID id);

    Rental[] findRentalsByReadable(Readable readable);

    Rental[] findRentalsByClient(Client client);

    boolean saveRental(Rental rental);

    boolean deleteRental(Rental rental);
}
