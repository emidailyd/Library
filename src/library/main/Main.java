package library.main;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import library.publications.Book;
import library.publications.Publication;

/**
 * Runs the program.
 */
public class Main {

    public static void main(String[] args) {

        List<Publication> publications = new ArrayList<>();

        //adding books to list
        publications.add(new Book("Sename dvare", "Šatrijos Ragana", 2019, 216));
        publications.add(new Book("Pavasario balsai", "Maironis", 1901, 150));
        publications.add(new Book("Altorių šešėly", "Vincas Mykolaitis-Putinas", 1903, 220));
        publications.add(new Book("Baltaragio malunas", "Vincas Kreve-Mickevicius", 1912, 180));

        
        //creating and displaying gui
        EventQueue.invokeLater(() -> {
            try {
                LibraryGUI window = new LibraryGUI(publications);
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
