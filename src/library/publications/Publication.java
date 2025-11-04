package library.publications;
import java.io.Serializable;

/**
 * Defines publications.
 */
public class Publication implements Serializable {
    
	private static final long serialVersionUID = 1L;
	private String title;
    private int yearPublished;
    private int pageCount;
    private String publicationID;
    
    public static final String DEFAULT_CREATOR = "Nežinomas";
    public static int totalPublications = 0;

    public Publication() {
        this("Nežinomas", 0, 0);
    }

    public Publication(String title, int yearPublished, int pageCount) {
        this.title = title;
        this.yearPublished = yearPublished;
        this.pageCount = pageCount;
        this.publicationID = generatePublicationID();
        totalPublications++;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }
    
    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
    
    public final int getTitleLength() {
        return title.length();
    }
    
    public void modifyTitle (String title) {
        this.title += "(" + title + ")";
    }
    
    /**
     * Generates a publication ID for a book.
     */
    public String generatePublicationID() {
        StringBuilder idBuilder = new StringBuilder();
        idBuilder.append(title.substring(0, Math.min(title.length(), 3)));
        idBuilder.append(yearPublished);
        idBuilder.append(pageCount);
        return idBuilder.toString();
    }
    
    public String getPublicationID() {
        return publicationID;
    }
    
    public String toString() {
        return "Pavadinimas: " + title +
        		"; ID: " + publicationID +
        		"; išleidimo metai: " + yearPublished +
        		"; puslapiu skaicius: " + pageCount;
    }
}
