package library.repository.actionsImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import library.Book;
import library.Publisher;
import library.repository.actions.PublisherRepository;
import library.utils.DatabaseSingleton;

import static library.utils.Queries.PUBLISHER__FIND_ALL;
import static library.utils.Queries.PUBLISHER__FIND_BY_ID;
import static library.utils.Queries.PUBLISHER__FIND_BY_NAME;
import static library.utils.Queries.PUBLISHER__FIND_BY_BOOK;
import static library.utils.Queries.PUBLISHER__SAVE;
import static library.utils.Queries.PUBLISHER__DELETE;

public class PublisherRepositoryImpl implements PublisherRepository {
    private DatabaseSingleton dbSingleton = DatabaseSingleton.getInstance();

    @Override
    public Publisher[] findAllPublishers() {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(PUBLISHER__FIND_ALL);
            ResultSet resultSet = stmt.executeQuery();
            List<Publisher> publishers = new ArrayList<>();
            while (resultSet.next()) {
                publishers.add(Publisher.fromResultSet(resultSet));
            }
            return publishers.toArray(Publisher[]::new);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Publisher[0];
    }

    @Override
    public Optional<Publisher> findPublisherByID(UUID id) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(PUBLISHER__FIND_BY_ID);
            stmt.setString(1, id.toString());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next())
                return Optional.of(Publisher.fromResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Publisher> findPublisherByName(String name) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(PUBLISHER__FIND_BY_NAME);
            stmt.setString(1, name);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next())
                return Optional.of(Publisher.fromResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Publisher> findPublisherByBook(Book book) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(PUBLISHER__FIND_BY_BOOK);
            stmt.setString(1, book.getID().toString());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next())
                return Optional.of(Publisher.fromResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean savePublisher(Publisher publisher) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(PUBLISHER__SAVE);
            stmt.setString(1, publisher.getID().toString());
            stmt.setTimestamp(2, new java.sql.Timestamp(publisher.getCreationDate().getTime()));
            stmt.setString(3, publisher.getName());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deletePublisher(Publisher publisher) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(PUBLISHER__DELETE);
            stmt.setString(1, publisher.getID().toString());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
