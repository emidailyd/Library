package library.main;

import java.io.*;
import java.util.concurrent.CountDownLatch;
import java.util.List;
import library.publications.Publication;
import library.publications.PublicationsList;

/**
 * Loads and saves text files.
 */
public class FileHandler {

    //load data from bin file
	public static void load(PublicationsList publicationsList, String filename, CountDownLatch latch) {
        try (ObjectInputStream file = new ObjectInputStream(new FileInputStream(filename))) {
        	@SuppressWarnings("unchecked")
			List<Publication> loadedPublications = (List<Publication>) file.readObject();
            publicationsList.clear();
            publicationsList.addAll(loadedPublications);
            System.out.println("Data successfully loaded from " + filename);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            latch.countDown(); // Signal that loading is done
        }
    }


    //save data to bin file
    public static void save(PublicationsList publicationsList, String filename) {
    	Thread saveThread = new Thread(() -> {
            try (ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(filename))) {
                file.writeObject(publicationsList);
                System.out.println("Duomenys sėkmingai įrašyti į failą " + filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        saveThread.start();
        try {
			saveThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}
