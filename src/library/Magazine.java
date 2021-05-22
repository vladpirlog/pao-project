package library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import library.exceptions.SectionNotFoundException;
import library.services.MagazineService;

public class Magazine extends Readable {
    private Date issueDate;

    /**
     * Used for initialising magazines from the database.
     */
    private Magazine(UUID id, Date creationDate, String title, String ISBN, String language, Date issueDate) {
        super(id, creationDate, title, null, ISBN, language);
        this.issueDate = issueDate;
    }

    public Magazine(String title, Section section, String ISBN, String language, Date issueDate) {
        super(title, section, ISBN, language);
        this.issueDate = issueDate;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public static Magazine[] findAll() {
        return MagazineService.findAllMagazines();
    }

    public static Optional<Magazine> findByID(UUID id) {
        return MagazineService.findMagazineByID(id);
    }

    public static Magazine[] findByTitle(String title) {
        return MagazineService.findMagazineByTitle(title);
    }

    public static Optional<Magazine> findByISBN(String ISBN) {
        return MagazineService.findMagazineByISBN(ISBN);
    }

    public static Magazine[] findBySection(Section section) {
        return MagazineService.findMagazinesBySection(section);
    }

    public static Optional<Magazine> findByRental(Rental rental) {
        return MagazineService.findMagazineByRental(rental);
    }

    @Override
    public boolean save() {
        return MagazineService.saveMagazine(this);
    }

    @Override
    public boolean delete() {
        return MagazineService.deleteMagazine(this);
    }

    @Override
    public String serialize() {
        String sectionIDString = "";

        try {
            sectionIDString = getSection(false).getID().toString();
        } catch (SectionNotFoundException e) {
            e.printStackTrace();
        }

        String[] fields = { getID().toString(), getCreationDate().toString(), getTitle(), sectionIDString, getISBN(),
                getLanguage(), getIssueDate().toString() };
        return String.join(",", fields);
    }

    /**
     * SQL table: id, creationDate, title, sectionId, ISBN, language, issueDate
     */
    public static Magazine fromResultSet(ResultSet resultSet) throws SQLException {
        return new Magazine(UUID.fromString(resultSet.getString(1)), resultSet.getTimestamp(2), resultSet.getString(3),
                resultSet.getString(5), resultSet.getString(6), resultSet.getDate(7));
    }
}
