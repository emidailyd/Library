package library.exceptions;

/**
 * Exception to prevent anything unexpected happening in the library.
 */
public class LibraryException extends Exception {

	private static final long serialVersionUID = 1L;

	public LibraryException(String message) {
        super(message);
    }
}
