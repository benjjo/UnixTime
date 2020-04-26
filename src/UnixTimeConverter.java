
import java.util.*;
import javax.swing.*;

/**
 *  Converts a Unix time code to an output screen
 *  Big ups to: http://www.java2s.com/Tutorial/Java/0240__Swing/MessagePopUps.htm
 *
 * @author Ben McGuffog
 * @version 16-04-2020
 */

public class UnixTimeConverter {

    public static Calendar scottishEpoch = Calendar.getInstance();

    /**
     * Constructor to setup the scottishEpoch values. i.e. Midnight, 1st Jan 1970
     */
    private UnixTimeConverter() {
        UnixTimeConverter.reset();

        SwingUtilities.invokeLater(new Runnable() {  // Creates a new swing thread so Swing can manage the thread.
            @Override
            public void run() {
                JFrame frame = new MainFrame("Unix Time");
                frame.setSize(265, 150); // sets the size of the frame
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets up JFrame to close when cancelled.
                frame.setVisible(true); // Draws the frame to the screen
            }
        });
    }

    /**
     * Resets the data back to Epoch.
     */
    public static void reset(){
        scottishEpoch.set(Calendar.YEAR, 1970);
        scottishEpoch.set(Calendar.DAY_OF_YEAR, 1);
        scottishEpoch.set(Calendar.HOUR_OF_DAY, 1);
        scottishEpoch.set(Calendar.MINUTE, 0);
        scottishEpoch.set(Calendar.SECOND, 0);
        scottishEpoch.set(Calendar.MILLISECOND, 0);
    }

    /**
     * Getter for the scottishEpoch value
     */
    public static Calendar getEpoch(){
        return UnixTimeConverter.scottishEpoch;
    }
    /**
     * Setter for the scottishEpoch value
     */
    public static void setEpochSeconds(int seconds){
        UnixTimeConverter.reset();
        UnixTimeConverter.scottishEpoch.set(Calendar.SECOND, seconds);
    }

    /**
     * Starts the program by calling the constructor.
     */
    public static void main(String[] args) {
        new UnixTimeConverter();
    }
}