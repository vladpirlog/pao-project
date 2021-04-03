package library;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import library.database.DatabaseSingleton;
import library.interfaces.Saveable;

public class Magazine extends Readable implements Saveable {
    private Date issueDate;

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

    public static Optional<Magazine> findByID(UUID id) {
        return DatabaseSingleton.getInstance().findMagazineByID(id);
    }

    public static Optional<Magazine> findByTitle(String title) {
        return DatabaseSingleton.getInstance().findMagazineByTitle(title);
    }

    public static Optional<Magazine> findByISBN(String ISBN) {
        return DatabaseSingleton.getInstance().findMagazineByISBN(ISBN);
    }

    @Override
    public boolean save() throws RuntimeException {
        return DatabaseSingleton.getInstance().saveMagazine(this);
    }
}
