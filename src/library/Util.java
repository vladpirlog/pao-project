package library;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy");

    public static Date parseDate(String date) throws ParseException {
        return DATE_FORMAT.parse(date);
    }
}
