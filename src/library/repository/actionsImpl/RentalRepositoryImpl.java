package library.repository.actionsImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import library.Client;
import library.Readable;
import library.Rental;
import library.exceptions.ClientNotFoundException;
import library.repository.actions.RentalRepository;
import library.utils.DatabaseSingleton;

import static library.utils.Queries.RENTAL__FIND_ALL;
import static library.utils.Queries.RENTAL__FIND_BY_ID;
import static library.utils.Queries.RENTAL__FIND_BY_READABLE;
import static library.utils.Queries.RENTAL__FIND_BY_CLIENT;
import static library.utils.Queries.RENTAL__SAVE;
import static library.utils.Queries.RENTAL__DELETE;

public class RentalRepositoryImpl implements RentalRepository {
    private DatabaseSingleton dbSingleton = DatabaseSingleton.getInstance();

    @Override
    public Rental[] findAllRentals() {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(RENTAL__FIND_ALL);
            ResultSet resultSet = stmt.executeQuery();
            List<Rental> rentals = new ArrayList<>();
            while (resultSet.next()) {
                rentals.add(Rental.fromResultSet(resultSet));
            }
            return rentals.toArray(Rental[]::new);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Rental[0];
    }

    @Override
    public Optional<Rental> findRentalByID(UUID id) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(RENTAL__FIND_BY_ID);
            stmt.setString(1, id.toString());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next())
                return Optional.of(Rental.fromResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Rental[] findRentalsByReadable(Readable readable) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(RENTAL__FIND_BY_READABLE);
            stmt.setString(1, readable.getID().toString());
            ResultSet resultSet = stmt.executeQuery();
            List<Rental> rentals = new ArrayList<>();
            while (resultSet.next()) {
                rentals.add(Rental.fromResultSet(resultSet));
            }
            return rentals.toArray(Rental[]::new);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Rental[0];
    }

    @Override
    public Rental[] findRentalsByClient(Client client) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(RENTAL__FIND_BY_CLIENT);
            stmt.setString(1, client.getID().toString());
            ResultSet resultSet = stmt.executeQuery();
            List<Rental> rentals = new ArrayList<>();
            while (resultSet.next()) {
                rentals.add(Rental.fromResultSet(resultSet));
            }
            return rentals.toArray(Rental[]::new);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Rental[0];
    }

    @Override
    public boolean saveRental(Rental rental) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(RENTAL__SAVE);
            stmt.setString(1, rental.getID().toString());
            stmt.setTimestamp(2, new java.sql.Timestamp(rental.getCreationDate().getTime()));
            try {
                stmt.setString(3, rental.getClient(false).getID().toString());
            } catch (ClientNotFoundException e) {
                stmt.setString(3, null);
            }
            stmt.setTimestamp(4, new java.sql.Timestamp(rental.getStartDate().getTime()));
            stmt.setTimestamp(5, new java.sql.Timestamp(rental.getPredictedEndDate().getTime()));
            stmt.setTimestamp(6,
                    rental.getEndDate().isEmpty() ? null : new java.sql.Timestamp(rental.getEndDate().get().getTime()));
            stmt.setString(7, rental.getRentedReadable(false).getID().toString());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteRental(Rental rental) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(RENTAL__DELETE);
            stmt.setString(1, rental.getID().toString());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
