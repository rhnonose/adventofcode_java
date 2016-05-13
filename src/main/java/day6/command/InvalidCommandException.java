package day6.command;

/**
 * Created by rhn on 5/12/16.
 */
public class InvalidCommandException extends Throwable {
    public InvalidCommandException(String message) {
        super(message);
    }
}
