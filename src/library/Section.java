package library;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import library.interfaces.Deletable;
import library.interfaces.Saveable;
import library.interfaces.Serializable;

public class Section extends Entity implements Saveable, Deletable, Serializable {
    private String name;
    private Set<Readable> readables;

    protected Section(String[] data) throws ParseException, IndexOutOfBoundsException {
        super(UUID.fromString(data[0]), Util.parseDate(data[1]));
        this.name = data[2];
        this.readables = new TreeSet<>();
    }

    public Section(String name) {
        super();
        this.name = name;
        this.readables = new TreeSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Readable> getReadables() {
        return new ArrayList<>(readables);
    }

    public boolean addReadable(Readable readable) {
        return readables.add(readable);
    }

    public boolean removeReadable(Readable readable) {
        return readables.remove(readable);
    }

    public boolean containsReadable(Readable readable) {
        return readables.contains(readable);
    }

    public static Optional<Section> findByID(UUID id) {
        return DatabaseSingleton.getInstance().findSectionByID(id);
    }

    public static Optional<Section> findByName(String name) {
        return DatabaseSingleton.getInstance().findSectionByName(name);
    }

    @Override
    public boolean save() {
        return DatabaseSingleton.getInstance().saveSection(this);
    }

    @Override
    public boolean delete() {
        return DatabaseSingleton.getInstance().deleteSection(this);
    }

    @Override
    public String serialize() {
        String[] fields = { getID().toString(), getCreationDate().toString(), name };
        return String.join(",", fields);
    }
}
