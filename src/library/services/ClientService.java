package library.services;

import java.util.Optional;
import java.util.UUID;

import library.Client;
import library.Rental;
import library.exceptions.ClientNotFoundException;
import library.repository.actionsImpl.ClientRepositoryImpl;

public class ClientService {
    private static ClientRepositoryImpl clientRepository = new ClientRepositoryImpl();

    public static Client[] findAllClients() {
        return clientRepository.findAllClients();
    }

    public static Optional<Client> findClientByID(UUID id) {
        return clientRepository.findClientByID(id);
    }

    public static Optional<Client> findClientByCNP(String CNP) {
        return clientRepository.findClientByCNP(CNP);
    }

    public static Client[] findClientsByFullName(String firstName, String lastName) {
        return clientRepository.findClientsByFullName(firstName, lastName);
    }

    public static Client findClientByRental(Rental rental) throws ClientNotFoundException {
        return clientRepository.findClientByRental(rental);
    }

    public static boolean saveClient(Client client) {
        return clientRepository.saveClient(client);
    }

    public static boolean deleteClient(Client client) {
        return clientRepository.deleteClient(client);
    }
}
