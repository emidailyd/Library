package library.abstracts;

import java.util.Collections;
import java.util.List;

import library.interfaces.Sortable;

/**
 * Sorts books by title or year.
 */
public abstract class AbstractSortable<T> implements Sortable<T> {
	/**
     * Sorts books by title.
     * @param items - the items that will be sorted.
     */
	@Override
    public void sortByTitle(List<T> items) {
        Collections.sort(items, (a, b) -> compareTitles(a, b));
    }

	/**
     * Sorts books by year.
     * @param items - the items that will be sorted.
     */
    @Override
    public void sortByYear(List<T> items) {
        Collections.sort(items, (a, b) -> compareYears(a, b));
    }

    protected abstract int compareTitles(T a, T b);
    protected abstract int compareYears(T a, T b);
}
