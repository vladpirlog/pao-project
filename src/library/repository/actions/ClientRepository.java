package library.repository.actions;

import java.util.Optional;
import java.util.UUID;

import library.Client;
import library.Rental;
import library.exceptions.ClientNotFoundException;

public interface ClientRepository {
    Client[] findAllClients();

    Optional<Client> findClientByID(UUID id);

    Optional<Client> findClientByCNP(String CNP);

    Client[] findClientsByFullName(String firstName, String lastName);

    Client findClientByRental(Rental rental) throws ClientNotFoundException;

    boolean saveClient(Client client);

    boolean deleteClient(Client client);
}
