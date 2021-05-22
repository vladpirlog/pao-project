package library.repository.actions;

import java.util.Optional;
import java.util.UUID;

import library.Book;
import library.Publisher;

public interface PublisherRepository {
    Publisher[] findAllPublishers();

    Optional<Publisher> findPublisherByID(UUID id);

    Optional<Publisher> findPublisherByName(String name);

    Optional<Publisher> findPublisherByBook(Book book);

    boolean savePublisher(Publisher publisher);

    boolean deletePublisher(Publisher publisher);
}
