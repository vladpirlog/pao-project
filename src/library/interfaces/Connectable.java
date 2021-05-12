package library.interfaces;

import java.io.IOException;
import java.text.ParseException;

@FunctionalInterface
public interface Connectable {
    public void connect() throws IOException, ParseException, RuntimeException;
}
