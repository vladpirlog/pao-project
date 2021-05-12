package library;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class Readable extends Entity implements Comparable<Readable> {
    private String title;
    private Section section;
    private String ISBN;
    private String language;

    protected Readable(UUID id, Date creationDate, String title, Section section, String ISBN, String language) {
        super(id, creationDate);
        this.title = title;
        this.section = section;
        this.ISBN = ISBN;
        this.language = language;
    }

    protected Readable(String title, Section section, String ISBN, String language) {
        super();
        this.title = title;
        this.section = section;
        this.ISBN = ISBN;
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isAvailableForRental() {
        Rental[] rentals = Rental.findAllRentalsByReadableID(getID());
        if (rentals.length == 0)
            return true;
        Optional<Date> lastEndDate = rentals[rentals.length - 1].getEndDate();
        if (lastEndDate.isEmpty())
            return false;
        return lastEndDate.get().compareTo(new Date()) < 0;
    }

    @Override
    public int compareTo(Readable r) {
        return this.getTitle().compareTo(r.getTitle());
    }
}
