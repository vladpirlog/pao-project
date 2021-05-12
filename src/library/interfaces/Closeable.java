package library.interfaces;

import java.io.IOException;

@FunctionalInterface
public interface Closeable {
    public void close() throws IOException;
}
