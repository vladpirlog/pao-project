package library.repository.actionsImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import library.Readable;
import library.Section;
import library.exceptions.SectionNotFoundException;
import library.repository.actions.SectionRepository;
import library.utils.DatabaseSingleton;

import static library.utils.Queries.SECTION__FIND_ALL;
import static library.utils.Queries.SECTION__FIND_BY_ID;
import static library.utils.Queries.SECTION__FIND_BY_NAME;
import static library.utils.Queries.SECTION__FIND_BY_READABLE;
import static library.utils.Queries.SECTION__SAVE;
import static library.utils.Queries.SECTION__DELETE;

public class SectionRepositoryImpl implements SectionRepository {
    private DatabaseSingleton dbSingleton = DatabaseSingleton.getInstance();

    @Override
    public Section[] findAllSections() {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(SECTION__FIND_ALL);
            ResultSet resultSet = stmt.executeQuery();
            List<Section> sections = new ArrayList<>();
            while (resultSet.next()) {
                sections.add(Section.fromResultSet(resultSet));
            }
            return sections.toArray(Section[]::new);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Section[0];
    }

    @Override
    public Optional<Section> findSectionByID(UUID id) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(SECTION__FIND_BY_ID);
            stmt.setString(1, id.toString());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next())
                return Optional.of(Section.fromResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Section> findSectionByName(String name) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(SECTION__FIND_BY_NAME);
            stmt.setString(1, name);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                Section section = Section.fromResultSet(resultSet);
                return Optional.of(section);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Section findSectionByReadable(Readable readable) throws SectionNotFoundException {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(SECTION__FIND_BY_READABLE);
            stmt.setString(1, readable.getID().toString());
            stmt.setString(2, readable.getID().toString());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next())
                return Section.fromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new SectionNotFoundException();
    }

    @Override
    public boolean saveSection(Section section) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(SECTION__SAVE);
            stmt.setString(1, section.getID().toString());
            stmt.setTimestamp(2, new java.sql.Timestamp(section.getCreationDate().getTime()));
            stmt.setString(3, section.getName());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteSection(Section section) {
        try {
            PreparedStatement stmt = dbSingleton.getConnection().prepareStatement(SECTION__DELETE);
            stmt.setString(1, section.getID().toString());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
