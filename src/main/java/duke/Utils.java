package duke;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Utils {

    public static LocalDate parseDate(String dateString) throws DateTimeParseException {
        LocalDate date;
        try {
            date = LocalDate.parse(dateString);
        } catch (DateTimeParseException e1) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy");
                date = LocalDate.parse(dateString, formatter);
            } catch (DateTimeParseException e2) {
                throw e1;
            }
        }
        return date;
    }

    public static String dateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy");
        return date.format(formatter);
    }
}
