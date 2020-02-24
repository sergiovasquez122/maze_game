import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Map class - Representation of a map
 * @author Sergio Vasquez
 */
public class Map {
    /**
     *  Singleton Pattern, only instance of Map class
     */
    private static Map uniqueInstance;
    /**
     * contains the 'interest' events of the map
     */
    private char[][] map;
    /**
     * flags the tell whether a part of the map has yet to be revealed
     */
    private boolean[][] revealed;

    public static Map getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new Map();
        }
        return uniqueInstance;
    }
    /**
     * Constructor - Constructs the map and loads the first level
     */
    private Map() {
        final int MAP_SIZE = 5;
        map = new char[MAP_SIZE][MAP_SIZE];
        revealed = new boolean[MAP_SIZE][MAP_SIZE];
        /// Default map is the first map aka 1
        loadMap(1);
    }


    /**
     * Loads the map with a given level
     * @param mapNum the level of the map to be loaded
     */
    public void loadMap(int mapNum) {
        // currentMap is bounded between [1, 3] for the current amount of maps available
        int currentMap = ( mapNum % 3 );
        if ( currentMap == 0 ) {
            currentMap += 3;
        }

        // Read from file if available
        try {
            Scanner read = new Scanner(new File("Map" + currentMap + ".txt"));
            int rowIndex = 0;
            do {
                // one row is the form "char char char char char"
                String[] tokens = read.nextLine().split(" ");
                for ( int i = 0; i < map.length; ++i ) {
                    char c = tokens[i].charAt(0);
                    // set the current row specific column index into
                    // specified character and reset revealed matrix
                    map[rowIndex][i] = c;
                    revealed[rowIndex][i] = false;
                }
                rowIndex++;
            }
            while ( read.hasNext() );
            // Close the file
            read.close();
        } catch ( FileNotFoundException fnf ) {
            System.out.println("File was not found");
        }
    }
    /**
     * Get the character located at the point's position
     * @param p the position of the point that is to be revealed
     * @return the character at the point's position
     */
    public char getCharAtLoc(Point p) {
        return map[p.x][p.y];
    }
    /**
     * Display the content of the map
     * @param p the point that will be indicated by a '*'
     */
    public void displayMap(Point p) {
        for ( int i = 0; i < map.length; ++i ) {
            for ( int j = 0; j < map[i].length; ++j ) {
                if ( i == p.x && j == p.y ) {
                    System.out.print("* ");
                } else if ( !revealed[i][j] ) {
                    System.out.print("X ");
                } else {
                    System.out.print( map[i][j] + " " );
                }
            }
            System.out.println();
        }
    }
    /**
     * Retrieve starting location of the map
     * @return the Point which is the starting location of the map if not found return null
     */
    public Point findStart() {
        for ( int i = 0; i < map.length; ++i ) {
            for ( int j = 0; j < map[i].length; ++j ) {
                if ( map[i][j] == 's' ) {
                    return new Point(i, j);
                }
            }
        }
        return null;
    }
    /**
     * Reveal the location of the point
     * @param p the location of the point that is to be revealed
     */
    public void reveal(Point p) {
        revealed[p.x][p.y] = true;
    }
    /**
     * Remove the character at the location
     * @param p the point of the character to be removed
     */
    public void removeCharAtLoc(Point p) {
        map[p.x][p.y] = 'n';
    }
}
