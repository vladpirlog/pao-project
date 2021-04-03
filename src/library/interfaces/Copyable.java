package library.interfaces;

import java.util.List;

import library.ReadableCopy;

public interface Copyable {
    public List<ReadableCopy> getCopies();

    public ReadableCopy createCopy();

    public boolean containsCopy(ReadableCopy copy);

    public boolean deleteCopy(ReadableCopy copy);

    public int getNumberOfCopies();
}
