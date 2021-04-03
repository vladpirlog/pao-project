package library;

import java.util.ArrayList;
import java.util.List;

import library.interfaces.Copyable;

public class Readable extends Entity implements Copyable, Comparable<Readable> {
    private String title;
    private Section section;
    private String ISBN;
    private String language;
    private List<ReadableCopy> copies;

    protected Readable(String title, Section section, String ISBN, String language) {
        this.title = title;
        this.section = section;
        this.ISBN = ISBN;
        this.language = language;
        this.copies = new ArrayList<>();
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

    @Override
    public List<ReadableCopy> getCopies() {
        return new ArrayList<>(copies);
    }

    @Override
    public ReadableCopy createCopy() {
        ReadableCopy copy = new ReadableCopy(this);
        copies.add(copy);
        return copy;
    }

    @Override
    public boolean deleteCopy(ReadableCopy copy) {
        return copies.remove(copy);
    }

    @Override
    public boolean containsCopy(ReadableCopy copy) {
        return copies.contains(copy);
    }

    @Override
    public int getNumberOfCopies() {
        return copies.size();
    }

    @Override
    public int compareTo(Readable r) {
        return this.getTitle().compareTo(r.getTitle());
    }
}
