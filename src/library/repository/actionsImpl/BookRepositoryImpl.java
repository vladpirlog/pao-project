package library.repository.actionsImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import library.Author;
import library.Book;
import library.Publisher;
import library.Rental;
import library.Section;
import library.exceptions.SectionNotFoundException;
import library.repository.actions.BookRepository;
import library.utils.DatabaseSingleton;

import static library.utils.Queries.BOOK__FIND_ALL;
import static library.utils.Queries.BOOK__FIND_BY_ID;
import static library.utils.Queries.BOOK__FIND_BY_TITLE;
import static library.utils.Queries.BOOK__FIND_BY_ISBN;
import static library.utils.Queries.BOOK__FIND_BY_SECTION;
import static library.utils.Queries.BOOK__FIND_BY_AUTHOR;
import static library.utils.Queries.BOOK__FIND_BY_PUBLISHER;
import static library.utils.Queries.BOOK__FIND_BY_RENTAL;
import static library.utils.Queries.BOOK__SAVE;
import static library.utils.Queries.BOOK__DELETE;

public class BookRepositoryImpl implements BookRepository {
    private DatabaseSingleton dbSingleton = DatabaseSingleton.getInstance();

    @Override
    public Book[] findAllBooks() {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(BOOK__FIND_ALL);
            ResultSet resultSet = stmt.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(Book.fromResultSet(resultSet));
            }
            return books.toArray(Book[]::new);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Book[0];
    }

    @Override
    public Optional<Book> findBookByID(UUID id) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(BOOK__FIND_BY_ID);
            stmt.setString(1, id.toString());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next())
                return Optional.of(Book.fromResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Book[] findBooksByTitle(String title) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(BOOK__FIND_BY_TITLE);
            stmt.setString(1, title);
            ResultSet resultSet = stmt.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(Book.fromResultSet(resultSet));
            }
            return books.toArray(Book[]::new);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Book[0];
    }

    @Override
    public Optional<Book> findBookByISBN(String ISBN) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(BOOK__FIND_BY_ISBN);
            stmt.setString(1, ISBN);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next())
                return Optional.of(Book.fromResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Book[] findBooksBySection(Section section) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(BOOK__FIND_BY_SECTION);
            stmt.setString(1, section.getID().toString());
            ResultSet resultSet = stmt.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(Book.fromResultSet(resultSet));
            }
            return books.toArray(Book[]::new);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Book[0];
    }

    @Override
    public Book[] findBooksByAuthor(Author author) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(BOOK__FIND_BY_AUTHOR);
            stmt.setString(1, author.getID().toString());
            ResultSet resultSet = stmt.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(Book.fromResultSet(resultSet));
            }
            return books.toArray(Book[]::new);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Book[0];
    }

    @Override
    public Book[] findBooksByPublisher(Publisher publisher) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(BOOK__FIND_BY_PUBLISHER);
            stmt.setString(1, publisher.getID().toString());
            ResultSet resultSet = stmt.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(Book.fromResultSet(resultSet));
            }
            return books.toArray(Book[]::new);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Book[0];
    }

    @Override
    public Optional<Book> findBookByRental(Rental rental) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(BOOK__FIND_BY_RENTAL);
            stmt.setString(1, rental.getID().toString());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next())
                return Optional.of(Book.fromResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean saveBook(Book book) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(BOOK__SAVE);
            stmt.setString(1, book.getID().toString());
            stmt.setTimestamp(2, new java.sql.Timestamp(book.getCreationDate().getTime()));
            stmt.setString(3, book.getTitle());
            try {
                stmt.setString(4, book.getSection(false).getID().toString());
            } catch (SectionNotFoundException e) {
                stmt.setString(4, null);
            }
            stmt.setString(5, book.getISBN());
            stmt.setString(6, book.getLanguage());
            stmt.setString(7, book.getAuthor(false).isEmpty() ? null : book.getAuthor(false).get().getID().toString());
            stmt.setString(8, book.getReleaseYear().isEmpty() ? null : book.getReleaseYear().get());
            stmt.setString(9,
                    book.getPublisher(false).isEmpty() ? null : book.getPublisher(false).get().getID().toString());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteBook(Book book) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(BOOK__DELETE);
            stmt.setString(1, book.getID().toString());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
