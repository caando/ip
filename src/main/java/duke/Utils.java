package duke;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for handling date parsing and formatting.
 * Provides methods to parse a string into a {@link LocalDate} and format a {@link LocalDate} into a string.
 */
public class Utils {

    /**
     * Parses a string into a {@link LocalDate}. The method tries to parse the date string in multiple formats.
     * The first attempt is to parse it using the default {@link LocalDate} format, and if that fails, it tries the
     * "MMM d yyyy" format (e.g., "Jan 1 2022").
     *
     * @param dateString The string representing the date to be parsed.
     * @return The parsed {@link LocalDate}.
     * @throws DateTimeParseException If the date string cannot be parsed using both formats.
     */
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

    /**
     * Converts a {@link LocalDate} to a string representation in the format "MMM d yyyy" (e.g., "Jan 1 2022").
     *
     * @param date The {@link LocalDate} to be formatted.
     * @return The formatted date as a string.
     */
    public static String dateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy");
        return date.format(formatter);
    }
}
