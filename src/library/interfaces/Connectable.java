package library.interfaces;

import java.io.IOException;

@FunctionalInterface
public interface Connectable {
    public void connect() throws IOException;
}
