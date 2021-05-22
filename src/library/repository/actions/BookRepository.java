package library.repository.actions;

import java.util.Optional;
import java.util.UUID;

import library.Author;
import library.Book;
import library.Publisher;
import library.Rental;
import library.Section;
import library.exceptions.SectionNotFoundException;

public interface BookRepository {
    Book[] findAllBooks();

    Optional<Book> findBookByID(UUID id);

    Book[] findBooksByTitle(String title);

    Optional<Book> findBookByISBN(String ISBN);

    Book[] findBooksBySection(Section section);

    Book[] findBooksByAuthor(Author author);

    Book[] findBooksByPublisher(Publisher publisher);

    Optional<Book> findBookByRental(Rental rental);

    boolean saveBook(Book book) throws SectionNotFoundException;

    boolean deleteBook(Book book);
}
