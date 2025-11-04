package library.publications;

import java.io.Serializable;
import java.time.LocalDate;

import library.exceptions.BookNotAvailableException;

/**
 * Book class which extends Publication.
 */
public class Book extends Publication implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private String author;
    private boolean available;
    private String lastDateBorrowed;

    /**
     * Book constructor without parameters.
     */
    public Book() {
        super();
        this.author = DEFAULT_CREATOR;
        this.available = true;
        this.lastDateBorrowed = "-";
    }

    /**
     * Book constructor with parameters.
     * @param title, author, yearPublished, pageCount.
     */
    public Book(String title, String author, int yearPublished, int pageCount) {
        super(title, yearPublished, pageCount);
        this.author = author;
        this.available = true;
        this.lastDateBorrowed = "-";
    }

    /**
     * Returns the author of a book.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of a book.
     * @param author - the name of the author.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Returns boolean whether a book is available.
     */
    public boolean isAvailable() {
        return available;
    }
    
    /**
     * Sets a book available.
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    /**
     * Borrows a book.
     * @throws BookNotAvailableException if the book is not available.
     */
    public void borrowBook() throws BookNotAvailableException {
        if (!available) {
            throw new BookNotAvailableException("(" + getTitle() + ")");
        }
        available = false;
        lastDateBorrowed = LocalDate.now().toString();
    }
    
    /**
     * Returns a book.
     */
    public void returnBook() {
    	if(!this.available) {
    		this.available = true;
    	}
    }
    
    /**
     * Generates book-specific publication ID.
     */
    @Override
    public String generatePublicationID() {
        return "K-" + super.generatePublicationID();
    }
    
    /**
     * Generates book-specific information String.
     */
    @Override
    public String toString() {
        return super.toString() +
              "; autorius: " + (author) +
              "; ar laisva: " + (available ? "Taip" : "Ne") +
              "; pasiskolinta buvo: " + lastDateBorrowed + ")";
    }
    
    /**
     * Clones a book.
     * @throws CloneNotSupportedException if book is not cloneable.
     */
    @Override
    public Book clone() throws CloneNotSupportedException {
        Book clonedBook = (Book) super.clone();
        return clonedBook;
    }
}