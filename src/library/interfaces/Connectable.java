package library.interfaces;

import java.sql.SQLException;

@FunctionalInterface
public interface Connectable {
    public void connect() throws SQLException, ClassNotFoundException;
}
