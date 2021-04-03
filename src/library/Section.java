package library;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import library.database.DatabaseSingleton;
import library.interfaces.Saveable;

public class Section extends Entity implements Saveable {
    private String name;
    private Set<Readable> readables;

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
    public boolean save() throws RuntimeException {
        return DatabaseSingleton.getInstance().saveSection(this);
    }
}
