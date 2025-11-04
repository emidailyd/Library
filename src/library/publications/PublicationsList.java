package library.publications;

import library.interfaces.Sortable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Defines a list for publications.
 */
public class PublicationsList implements Serializable, Sortable<Publication> {
	
	private static final long serialVersionUID = 1L;
	private List<Publication> publications;

    public PublicationsList() {
        publications = new ArrayList<>();
    }
    
    public List<Publication> getPublications() {
        return publications;
    }


    public void addPublication(Publication publication) {
        publications.add(publication);
    }

    @Override
    public void sortByTitle(List<Publication> items) {
        Collections.sort(items, (a, b) -> a.getTitle().compareTo(b.getTitle()));
    }

    @Override
    public void sortByYear(List<Publication> items) {
        Collections.sort(items, (a, b) -> Integer.compare(a.getYearPublished(), b.getYearPublished()));
    }

    public void sortByTitle() {
        sortByTitle(publications);
    }

    public void sortByYear() {
        sortByYear(publications);
    }
    
    public void clear() {
        publications.clear();
    }

    public void addAll(List<Publication> otherPublications) {
        publications.addAll(otherPublications);
    }
}
