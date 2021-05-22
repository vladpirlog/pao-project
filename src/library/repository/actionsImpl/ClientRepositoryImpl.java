package library.repository.actionsImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import library.Client;
import library.Rental;
import library.exceptions.ClientNotFoundException;
import library.repository.actions.ClientRepository;
import library.utils.DatabaseSingleton;

import static library.utils.Queries.CLIENT__FIND_ALL;
import static library.utils.Queries.CLIENT__FIND_BY_ID;
import static library.utils.Queries.CLIENT__FIND_BY_CNP;
import static library.utils.Queries.CLIENT__FIND_BY_FULL_NAME;
import static library.utils.Queries.CLIENT__FIND_BY_RENTAL;
import static library.utils.Queries.CLIENT__SAVE;
import static library.utils.Queries.CLIENT__DELETE;

public class ClientRepositoryImpl implements ClientRepository {
    private DatabaseSingleton dbSingleton = DatabaseSingleton.getInstance();

    @Override
    public Client[] findAllClients() {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(CLIENT__FIND_ALL);
            ResultSet resultSet = stmt.executeQuery();
            List<Client> clients = new ArrayList<>();
            while (resultSet.next()) {
                clients.add(Client.fromResultSet(resultSet));
            }
            return clients.toArray(Client[]::new);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Client[0];
    }

    @Override
    public Optional<Client> findClientByID(UUID id) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(CLIENT__FIND_BY_ID);
            stmt.setString(1, id.toString());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next())
                return Optional.of(Client.fromResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Client> findClientByCNP(String CNP) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(CLIENT__FIND_BY_CNP);
            stmt.setString(1, CNP);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next())
                return Optional.of(Client.fromResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Client[] findClientsByFullName(String firstName, String lastName) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(CLIENT__FIND_BY_FULL_NAME);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            ResultSet resultSet = stmt.executeQuery();
            List<Client> clients = new ArrayList<>();
            while (resultSet.next()) {
                clients.add(Client.fromResultSet(resultSet));
            }
            return clients.toArray(Client[]::new);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Client[0];
    }

    @Override
    public Client findClientByRental(Rental rental) throws ClientNotFoundException {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(CLIENT__FIND_BY_RENTAL);
            stmt.setString(1, rental.getID().toString());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next())
                return Client.fromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new ClientNotFoundException();
    }

    @Override
    public boolean saveClient(Client client) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(CLIENT__SAVE);
            stmt.setString(1, client.getID().toString());
            stmt.setTimestamp(2, new java.sql.Timestamp(client.getCreationDate().getTime()));
            stmt.setString(3, client.getFirstName());
            stmt.setString(4, client.getLastName());
            stmt.setString(5, client.getCNP());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteClient(Client client) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(CLIENT__DELETE);
            stmt.setString(1, client.getID().toString());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
