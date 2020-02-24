import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * ItemGenerator class - Representation of single ItemGenerator
 * @author Sergio Vasquez
 */
public class ItemGenerator {
    /** Singleton pattern, only instance of ItemGenerator */
    private static ItemGenerator uniqueInstance;
    /** Contains all possible items to be generated */
    private ArrayList<Item> itemList;
    public static ItemGenerator getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new ItemGenerator();
        }
        return uniqueInstance;
    }
    /**
     * Constructor - fills the ItemGenerator with possible items
     */
    private ItemGenerator() {
        final String fileName = "ItemList.txt";
        itemList = new ArrayList<>();

        // Read from file if file is found
        try {
            Scanner read = new Scanner(new File(fileName));
            do {
                String itemName = read.nextLine();
                itemList.add(new Item(itemName));
            } while (read.hasNextLine());
            // Close the file
            read.close();
        } catch (FileNotFoundException fnf) {
            System.out.println("File was not found");
        }
    }
    /**
     * Retrieves a random item from the ItemGenerator
     * @return a random item from the ItemGenerator
     */
    public Item generateItem() {
        // Get an random index in the range [0, itemList.size())
        // with equal likelihood
        Random generator = new Random();
        int randomIndex = generator.nextInt(itemList.size());

        // Create new instance of the itme
        // from the template
        Item i = itemList.get(randomIndex);
        return i.clone();
    }
}
