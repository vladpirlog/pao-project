package library.services;

import java.util.Optional;
import java.util.UUID;

import library.Book;
import library.Publisher;
import library.repository.actionsImpl.PublisherRepositoryImpl;

public class PublisherService {
    private static PublisherRepositoryImpl publisherRepository = new PublisherRepositoryImpl();

    public static Publisher[] findAllPublishers() {
        return publisherRepository.findAllPublishers();
    }

    public static Optional<Publisher> findPublisherByID(UUID id) {
        return publisherRepository.findPublisherByID(id);
    }

    public static Optional<Publisher> findPublisherByName(String name) {
        return publisherRepository.findPublisherByName(name);
    }

    public static Optional<Publisher> findPublisherByBook(Book book) {
        return publisherRepository.findPublisherByBook(book);
    }

    public static boolean savePublisher(Publisher publisher) {
        return publisherRepository.savePublisher(publisher);
    }

    public static boolean deletePublisher(Publisher publisher) {
        return publisherRepository.deletePublisher(publisher);
    }
}
