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
import library.repository.actions.AuthorRepository;
import library.utils.DatabaseSingleton;

import static library.utils.Queries.AUTHOR__FIND_ALL;
import static library.utils.Queries.AUTHOR__FIND_BY_ID;
import static library.utils.Queries.AUTHOR__FIND_BY_FULL_NAME;
import static library.utils.Queries.AUTHOR__FIND_BY_BOOK;
import static library.utils.Queries.AUTHOR__SAVE;
import static library.utils.Queries.AUTHOR__DELETE;

public class AuthorRepositoryImpl implements AuthorRepository {
    private DatabaseSingleton dbSingleton = DatabaseSingleton.getInstance();

    @Override
    public Author[] findAllAuthors() {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(AUTHOR__FIND_ALL);
            ResultSet resultSet = stmt.executeQuery();
            List<Author> authors = new ArrayList<>();
            while (resultSet.next()) {
                authors.add(Author.fromResultSet(resultSet));
            }
            return authors.toArray(Author[]::new);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Author[0];
    }

    @Override
    public Optional<Author> findAuthorByID(UUID id) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(AUTHOR__FIND_BY_ID);
            stmt.setString(1, id.toString());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next())
                return Optional.of(Author.fromResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Author> findAuthorByFullName(String firstName, String lastName) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(AUTHOR__FIND_BY_FULL_NAME);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next())
                return Optional.of(Author.fromResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Author> findAuthorByBook(Book book) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(AUTHOR__FIND_BY_BOOK);
            stmt.setString(1, book.getID().toString());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next())
                return Optional.of(Author.fromResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean saveAuthor(Author author) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(AUTHOR__SAVE);
            stmt.setString(1, author.getID().toString());
            stmt.setTimestamp(2, new java.sql.Timestamp(author.getCreationDate().getTime()));
            stmt.setString(3, author.getFirstName());
            stmt.setString(4, author.getLastName());
            stmt.setDate(5,
                    author.getBirthDate().isEmpty() ? null : new java.sql.Date(author.getBirthDate().get().getTime()));
            stmt.setString(6, author.getBirthPlace().orElse(null));
            stmt.setDate(7,
                    author.getDeathDate().isEmpty() ? null : new java.sql.Date(author.getDeathDate().get().getTime()));
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteAuthor(Author author) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(AUTHOR__DELETE);
            stmt.setString(1, author.getID().toString());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
