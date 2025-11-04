package library.exceptions;

/**
 * Exception that is thrown if a book is unavailable to borrow.
 */
public class BookNotAvailableException extends LibraryException {
	
	private static final long serialVersionUID = 1L;
	
	public String title;

	public BookNotAvailableException(String title) {
        super("Pasirinkta knyga šiuo metu užimta ");
        this.title = title;
    }
	
	public String getTitle() {
		return this.title;
	}
}