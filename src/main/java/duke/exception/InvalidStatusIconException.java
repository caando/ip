package duke.exception;

public class InvalidStatusIconException extends Exception {
    
    private final String statusIcon;

    public InvalidStatusIconException(String message, String statusIcon) {
        super(message);
        this.statusIcon = statusIcon;
    }

    public String getStatusIcon() {
        return statusIcon;
    }
}
