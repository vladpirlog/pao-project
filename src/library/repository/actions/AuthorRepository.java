package library.repository.actions;

import java.util.Optional;
import java.util.UUID;

import library.Author;
import library.Book;

public interface AuthorRepository {
    Author[] findAllAuthors();

    Optional<Author> findAuthorByID(UUID id);

    Optional<Author> findAuthorByFullName(String firstName, String lastName);

    Optional<Author> findAuthorByBook(Book book);

    boolean saveAuthor(Author author);

    boolean deleteAuthor(Author author);
}
