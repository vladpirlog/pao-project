package library;

import java.text.ParseException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import library.interfaces.Deletable;
import library.interfaces.Saveable;
import library.interfaces.Serializable;

public class Magazine extends Readable implements Saveable, Deletable, Serializable {
    private Date issueDate;

    protected Magazine(String[] data) throws ParseException, IndexOutOfBoundsException, NoSuchElementException {
        super(UUID.fromString(data[0]), Util.parseDate(data[1]), data[2],
                Section.findByID(UUID.fromString(data[3])).orElseThrow(), data[4], data[5]);
        this.issueDate = Util.parseDate(data[6]);

        this.getSection().addReadable(this);
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
    public boolean save() {
        return DatabaseSingleton.getInstance().saveMagazine(this);
    }

    @Override
    public boolean delete() {
        return DatabaseSingleton.getInstance().deleteMagazine(this);
    }

    @Override
    public String serialize() {
        String[] fields = { getID().toString(), getCreationDate().toString(), getTitle(),
                getSection().getID().toString(), getISBN(), getLanguage(), issueDate.toString() };
        return String.join(",", fields);
    }
}
