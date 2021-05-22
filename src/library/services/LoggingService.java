package library.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public final class LoggingService {
    private BufferedWriter logWriter;

    public LoggingService(String csvPath) throws IOException {
        logWriter = new BufferedWriter(new FileWriter(csvPath, true));
    }

    public LoggingService() throws IOException {
        this("db/log.csv");
    }

    public void exit() throws IOException {
        if (logWriter != null)
            logWriter.close();
    }

    public void log(String action) throws IOException {
        logWriter.append(action + "," + new Date().toString() + System.lineSeparator());
    }
}
