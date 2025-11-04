package library.publications;

/**
 * Journal class which extends Publication
 */
public class Journal extends Publication {

	private static final long serialVersionUID = 1L;
	private String redactor;

    public Journal() {
        super();
        this.redactor = DEFAULT_CREATOR;
    }

    public Journal(String title, String redactor, int yearPublished, int pageCount) {
        super(title, yearPublished, pageCount);
        this.redactor = redactor;
    }

    public String getRedactor() {
        return redactor;
    }

    public void setRedactor(String redactor) {
        this.redactor = redactor;
    }
    
    @Override
    public String generatePublicationID() {
        return "Ž-" + super.generatePublicationID();
    }

    @Override
    public String toString() {
        return "Žurnalas (" +
                super.toString() +
                "; redaktorius: " + redactor +
                ')';
    }
}
