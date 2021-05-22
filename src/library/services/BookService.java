package library.services;

import java.util.Optional;
import java.util.UUID;

import library.Author;
import library.Book;
import library.Publisher;
import library.Rental;
import library.Section;
import library.repository.actionsImpl.BookRepositoryImpl;

public class BookService {
    private static BookRepositoryImpl bookRepository = new BookRepositoryImpl();

    public static Book[] findAllBooks() {
        return bookRepository.findAllBooks();
    }

    public static Optional<Book> findBookByID(UUID id) {
        return bookRepository.findBookByID(id);
    }

    public static Book[] findBooksByTitle(String title) {
        return bookRepository.findBooksByTitle(title);
    }

    public static Optional<Book> findBookByISBN(String ISBN) {
        return bookRepository.findBookByISBN(ISBN);
    }

    public static Book[] findBooksBySection(Section section) {
        return bookRepository.findBooksBySection(section);
    }

    public static Book[] findBooksByAuthor(Author author) {
        return bookRepository.findBooksByAuthor(author);
    }

    public static Book[] findBooksByPublisher(Publisher publisher) {
        return bookRepository.findBooksByPublisher(publisher);
    }

    public static Optional<Book> findBookByRental(Rental rental) {
        return bookRepository.findBookByRental(rental);
    }

    public static boolean saveBook(Book book) {
        return bookRepository.saveBook(book);
    }

    public static boolean deleteBook(Book book) {
        return bookRepository.deleteBook(book);
    }
}
