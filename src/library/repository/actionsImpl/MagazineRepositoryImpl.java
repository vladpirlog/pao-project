package library.repository.actionsImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import library.Magazine;
import library.Rental;
import library.Section;
import library.exceptions.SectionNotFoundException;
import library.repository.actions.MagazineRepository;
import library.utils.DatabaseSingleton;

import static library.utils.Queries.MAGAZINE__FIND_ALL;
import static library.utils.Queries.MAGAZINE__FIND_BY_ID;
import static library.utils.Queries.MAGAZINE__FIND_BY_TITLE;
import static library.utils.Queries.MAGAZINE__FIND_BY_ISBN;
import static library.utils.Queries.MAGAZINE__FIND_BY_SECTION;
import static library.utils.Queries.MAGAZINE__FIND_BY_RENTAL;
import static library.utils.Queries.MAGAZINE__SAVE;
import static library.utils.Queries.MAGAZINE__DELETE;

public class MagazineRepositoryImpl implements MagazineRepository {
    private DatabaseSingleton dbSingleton = DatabaseSingleton.getInstance();

    @Override
    public Magazine[] findAllMagazines() {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(MAGAZINE__FIND_ALL);
            ResultSet resultSet = stmt.executeQuery();
            List<Magazine> magazines = new ArrayList<>();
            while (resultSet.next()) {
                magazines.add(Magazine.fromResultSet(resultSet));
            }
            return magazines.toArray(Magazine[]::new);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Magazine[0];
    }

    @Override
    public Optional<Magazine> findMagazineByID(UUID id) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(MAGAZINE__FIND_BY_ID);
            stmt.setString(1, id.toString());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next())
                return Optional.of(Magazine.fromResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Magazine[] findMagazineByTitle(String title) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(MAGAZINE__FIND_BY_TITLE);
            stmt.setString(1, title);
            ResultSet resultSet = stmt.executeQuery();
            List<Magazine> magazines = new ArrayList<>();
            while (resultSet.next()) {
                magazines.add(Magazine.fromResultSet(resultSet));
            }
            return magazines.toArray(Magazine[]::new);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Magazine[0];
    }

    @Override
    public Optional<Magazine> findMagazineByISBN(String ISBN) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(MAGAZINE__FIND_BY_ISBN);
            stmt.setString(1, ISBN);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next())
                return Optional.of(Magazine.fromResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Magazine[] findMagazinesBySection(Section section) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(MAGAZINE__FIND_BY_SECTION);
            stmt.setString(1, section.getID().toString());
            ResultSet resultSet = stmt.executeQuery();
            List<Magazine> magazines = new ArrayList<>();
            while (resultSet.next()) {
                magazines.add(Magazine.fromResultSet(resultSet));
            }
            return magazines.toArray(Magazine[]::new);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Magazine[0];
    }

    @Override
    public Optional<Magazine> findMagazineByRental(Rental rental) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(MAGAZINE__FIND_BY_RENTAL);
            stmt.setString(1, rental.getID().toString());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next())
                return Optional.of(Magazine.fromResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean saveMagazine(Magazine magazine) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(MAGAZINE__SAVE);
            stmt.setString(1, magazine.getID().toString());
            stmt.setTimestamp(2, new java.sql.Timestamp(magazine.getCreationDate().getTime()));
            stmt.setString(3, magazine.getTitle());
            try {
                stmt.setString(4, magazine.getSection(false).getID().toString());
            } catch (SectionNotFoundException e) {
                stmt.setString(4, null);
            }
            stmt.setString(5, magazine.getISBN());
            stmt.setString(6, magazine.getLanguage());
            stmt.setDate(7, new java.sql.Date(magazine.getIssueDate().getTime()));
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteMagazine(Magazine magazine) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(MAGAZINE__DELETE);
            stmt.setString(1, magazine.getID().toString());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
