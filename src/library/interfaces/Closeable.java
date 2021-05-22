package library.interfaces;

import java.sql.SQLException;

@FunctionalInterface
public interface Closeable {
    public void close() throws SQLException;
}
