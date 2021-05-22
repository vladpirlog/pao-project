package library;

import java.time.DateTimeException;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import library.exceptions.NotAvailableForRentalException;
import library.exceptions.SectionNotFoundException;
import library.services.RentalService;
import library.services.SectionService;

public abstract class Readable extends Entity implements Comparable<Readable> {
    private String title;
    private Section section;
    private String ISBN;
    private String language;

    public Readable(UUID id, Date creationDate, String title, Section section, String ISBN, String language) {
        super(id, creationDate);
        this.title = title;
        this.section = section;
        this.ISBN = ISBN;
        this.language = language;
    }

    public Readable(String title, Section section, String ISBN, String language) {
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

    /**
     * Lazy-load or force a refresh of the section field.
     *
     * @throws SectionNotFoundException
     */
    public Section getSection(boolean forceRefetch) throws SectionNotFoundException {
        if (section == null || forceRefetch)
            section = SectionService.findSectionByReadable(this);
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

    public boolean isAvailableForRent() {
        Date currentDate = new Date();
        return Arrays.stream(RentalService.findRentalsByReadable(this))
                .allMatch(r -> r.getEndDate().orElse(currentDate).compareTo(currentDate) < 0);
    }

    public Rental rent(Client client, Date predictedEndDate) throws NotAvailableForRentalException, DateTimeException {
        if (!isAvailableForRent())
            throw new NotAvailableForRentalException();
        if (predictedEndDate.compareTo(new Date()) < 0)
            throw new DateTimeException("The predicted end date is invalid.");
        Rental rental = new Rental(this, client, predictedEndDate);
        rental.save();
        return rental;
    }

    @Override
    public int compareTo(Readable r) {
        return this.getTitle().compareTo(r.getTitle());
    }
}
