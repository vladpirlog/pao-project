package library.services;

import java.util.Optional;
import java.util.UUID;

import library.Author;
import library.Book;
import library.repository.actionsImpl.AuthorRepositoryImpl;

public class AuthorService {
    private static AuthorRepositoryImpl authorRepository = new AuthorRepositoryImpl();

    public static Author[] findAllAuthors() {
        return authorRepository.findAllAuthors();
    }

    public static Optional<Author> findAuthorByID(UUID id) {
        return authorRepository.findAuthorByID(id);
    }

    public static Optional<Author> findAuthorByFullName(String firstName, String lastName) {
        return authorRepository.findAuthorByFullName(firstName, lastName);
    }

    public static Optional<Author> findAuthorByBook(Book book) {
        return authorRepository.findAuthorByBook(book);
    }

    public static boolean saveAuthor(Author author) {
        return authorRepository.saveAuthor(author);
    }

    public static boolean deleteAuthor(Author author) {
        return authorRepository.deleteAuthor(author);
    }
}
