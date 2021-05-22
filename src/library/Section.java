package library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import library.exceptions.SectionNotFoundException;
import library.services.BookService;
import library.services.MagazineService;
import library.services.SectionService;

public class Section extends Entity {
    private String name;
    private Set<Book> books;
    private Set<Magazine> magazines;

    /**
     * Used for initialising rentals from the database.
     */
    private Section(UUID id, Date creationDate, String name) {
        super(id, creationDate);
        this.name = name;
    }

    public Section(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Lazy-load or force a refresh of the books set.
     */
    public Set<Book> getBooks(boolean forceRefetch) {
        if (books == null || forceRefetch)
            books = new TreeSet<>(Arrays.asList(BookService.findBooksBySection(this)));
        return books;
    }

    /**
     * Lazy-load or force a refresh of the magazines set.
     */
    public Set<Magazine> getMagazines(boolean forceRefetch) {
        if (magazines == null || forceRefetch)
            magazines = new TreeSet<>(Arrays.asList(MagazineService.findMagazinesBySection(this)));
        return magazines;
    }

    public static Section[] findAll() {
        return SectionService.findAllSections();
    }

    public static Optional<Section> findByID(UUID id) {
        return SectionService.findSectionByID(id);
    }

    public static Optional<Section> findByName(String name) {
        return SectionService.findSectionByName(name);
    }

    public static Section findByReadable(Readable readable) throws SectionNotFoundException {
        return SectionService.findSectionByReadable(readable);
    }

    @Override
    public boolean save() {
        return SectionService.saveSection(this);
    }

    @Override
    public boolean delete() {
        return SectionService.deleteSection(this);
    }

    @Override
    public String serialize() {
        String[] fields = { getID().toString(), getCreationDate().toString(), getName() };
        return String.join(",", fields);
    }

    /**
     * SQL table: id, creationDate, name
     */
    public static Section fromResultSet(ResultSet resultSet) throws SQLException {
        return new Section(UUID.fromString(resultSet.getString(1)), resultSet.getTimestamp(2), resultSet.getString(3));
    }
}
