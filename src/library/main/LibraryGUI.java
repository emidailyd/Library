package library.main;

import javax.swing.*;

import library.exceptions.BookNotAvailableException;
import library.publications.Book;
import library.publications.Publication;
import library.publications.PublicationsList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Main user GUI and its functionality.
 */
public class LibraryGUI extends JFrame {

	private static final long serialVersionUID = 1L;
    private PublicationsList publications;
    private JComboBox<String> bookDropdown;
    private JTextArea borrowedBooksArea;

    /**
     * Constructor for initializing the GUI with books.
     * @param publicationList - the books that need to be displayed.
     */
    public LibraryGUI(List<Publication> publicationList) {
        publications = new PublicationsList();
        for (Publication publication : publicationList) {
            publications.addPublication(publication);
        }
        initialize();
    }

    /**
     * Initializes the GUI.
     */
    private void initialize() {
        setTitle("Bibliotekos savitarna");
        setSize(900, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //create panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1));

        //display book list button
        JButton displayButton = new JButton("Parodyti knygų sąrašą");
        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayBookList();
            }
        });
        buttonPanel.add(displayButton);

        //display borrow button
        JButton borrowButton = new JButton("Pasiskolinti knygą");
        borrowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                borrowBook();
            }
        });
        buttonPanel.add(borrowButton);

        //display return button
        JButton returnButton = new JButton("Grąžinti knygą");
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                returnBook();
            }
        });
        buttonPanel.add(returnButton);


        //display load button
        JButton loadButton = new JButton("Įkelti duomenis");
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadFromFile();
            }
        });
        buttonPanel.add(loadButton);

        //display save button
        JButton saveButton = new JButton("Išsaugoti duomenis");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveToFile();
            }
        });
        buttonPanel.add(saveButton);

        //add buttons to frame
        getContentPane().add(buttonPanel, BorderLayout.CENTER);

        //initialize dropdown book list
        publications.sortByTitle(); // Sort publications by title using PublicationsList
        initializeBookDropdown();

        //initialize borrowed books area
        borrowedBooksArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(borrowedBooksArea);
        scrollPane.setPreferredSize(new Dimension(100, 100));
        getContentPane().add(scrollPane, BorderLayout.SOUTH);
    }

    /**
     * Initializes book dropdown for choosing a book.
     */
    private void initializeBookDropdown() {
        bookDropdown = new JComboBox<>();
        for (Publication publication : publications.getPublications()) {
            bookDropdown.addItem(publication.toString());
        }
        getContentPane().add(bookDropdown, BorderLayout.NORTH);
    }

    /**
     * Opens a book list
     */
    private void displayBookList() {
        // Create a panel to hold the sorting buttons
        JPanel sortingButtonPanel = new JPanel();
        sortingButtonPanel.setLayout(new FlowLayout());

        // Create button for sorting by title
        JButton sortByTitleButton = new JButton("Rikiuoti pagal pavadinimą");
        sortByTitleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                publications.sortByTitle();
            }
        });
        sortingButtonPanel.add(sortByTitleButton);

        // Create button for sorting by year
        JButton sortByYearButton = new JButton("Rikiuoti pagal leidimo metus");
        sortByYearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                publications.sortByYear();
            }
        });
        sortingButtonPanel.add(sortByYearButton);

        // Add the sorting buttons panel to the book list dialog
        JOptionPane.showMessageDialog(this, sortingButtonPanel, "Sort Options", JOptionPane.PLAIN_MESSAGE);

        // Create a StringBuilder to hold the book list
        StringBuilder bookList = new StringBuilder("Knygų sąrašas:\n");
        for (Publication publication : publications.getPublications()) {
            bookList.append(publication.toString()).append("\n");
        }

        // Display the book list
        JOptionPane.showMessageDialog(this, bookList.toString(), "Knygų sąrašas", JOptionPane.PLAIN_MESSAGE);
    }


    /**
     * Borrows a book.
     */
    private void borrowBook() {
        try {
        	String selectedBookID = ((Book) publications.getPublications().get(bookDropdown.getSelectedIndex())).getPublicationID();

            for (Publication publication : publications.getPublications()) {
                if (publication instanceof Book) {
                    Book book = (Book) publication;
                    if (book.getPublicationID().equals(selectedBookID)) {
                        try {
                            book.borrowBook();
                            borrowedBooksArea.append(book.toString() + "\n");
                            JOptionPane.showMessageDialog(this, "Pasiskolinta knyga: " + bookDropdown.getSelectedItem(), "Pasiskolinti knygą", JOptionPane.PLAIN_MESSAGE);
                            return;
                        } catch (BookNotAvailableException bnae) {
                            JOptionPane.showMessageDialog(this, bnae.getMessage() + bnae.getTitle(), "Klaida", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Įvyko klaida: " + e.getMessage(), "Klaida", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Returns a book.
     */
    private void returnBook() {
    	String selectedBookID = ((Book) publications.getPublications().get(bookDropdown.getSelectedIndex())).getPublicationID();
        for (Publication publication : publications.getPublications()) {
            if (publication instanceof Book) {
                Book book = (Book) publication;
                if (book.getPublicationID().equals(selectedBookID)) {
                    book.returnBook();
                    JOptionPane.showMessageDialog(this, "Grąžinta knyga: " + bookDropdown.getSelectedItem(), "Grąžinti knygą", JOptionPane.PLAIN_MESSAGE);
                    removeReturnedBook(selectedBookID);
                    return;
                }
            }
        }
    }

    /**
     * Loads information from file.
     */
    private void loadFromFile() {
        String filename = "C:\\Users\\EMI\\eclipse-workspace\\libraryProject\\src\\library_data.bin";
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            FileHandler.load(publications, filename, latch);
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        updateBorrowedBooksArea();
    }

    /**
     * Saves information to file.
     */
    private void saveToFile() {
        String filename = "C:\\Users\\EMI\\eclipse-workspace\\libraryProject\\src\\library_data.bin";
        FileHandler.save(publications, filename);
    }

    /**
     * Updates the borrowed books are after any changes.
     */
    private void updateBorrowedBooksArea() {
        borrowedBooksArea.setText(""); //clear previous contents

        //update the list
        for (Publication publication : publications.getPublications()) {
            if (publication instanceof Book) {
                Book book = (Book) publication;
                if (!book.isAvailable()) {
                    borrowedBooksArea.append(book.toString() + "\n");
                }
            }
        }
    }

    /**
     * Removes a returned book from the borrowed books area.
     * @param returnedBookID - ID of the book that was returned.
     */
    private void removeReturnedBook(String returnedBookID) {
        StringBuilder newTextBuilder = new StringBuilder();
        String currentText = borrowedBooksArea.getText();
        String[] lines = currentText.split("\n");
        for (String line : lines) {
            if (!line.contains(returnedBookID)) {
                newTextBuilder.append(line).append("\n");
            }
        }
        borrowedBooksArea.setText(newTextBuilder.toString());
    }
}
