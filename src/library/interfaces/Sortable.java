package library.interfaces;

import java.util.List;

/**
 * Sorts items.
 */
public interface Sortable<T> {
    void sortByTitle(List<T> items);
    void sortByYear(List<T> items);
}
