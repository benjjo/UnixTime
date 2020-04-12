import java.util.*;
import javax.swing.JOptionPane;

/**
 *  Converts a Unix time code to a standard output
 *  Big ups to: http://www.java2s.com/Tutorial/Java/0240__Swing/MessagePopUps.htm
 *
 * @author Ben McGuffog
 * @version 11-04-2020
 */

public class UnixTimeConverter {

    private Calendar scottishEpoch = Calendar.getInstance();

    /**
     * Constructor to setup the scottishEpoch values. i.e. Midnight, 1st Jan 1970
     */
    private UnixTimeConverter() {
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
    private Calendar getEpoch(){
        return this.scottishEpoch;
    }
    /**
     * Setter for the scottishEpoch value
     */
    private void setEpochSeconds(int seconds){
        this.scottishEpoch.set(Calendar.SECOND, seconds);
    }

    /**
     * Prompts the user to enter a valid unix time code.
     * Returns true if the input was successful.
     */
    private boolean getUnixTimeFromTheUser() {
        try {
            this.setEpochSeconds(Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Enter the Unix Time Code:","1577833200")));
            return true;
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Incorrect value format entered.");
        }
        return false;
    }

    /**
     * Presents the converted date to the user in a popup window.
     */
    private void sendTheDateToPopup(){
        if(this.getUnixTimeFromTheUser()){
            String convertedTime = String.valueOf(this.getEpoch().getTime());
            JOptionPane.showMessageDialog(null, convertedTime);
        } else {
            String message = "Something went terribly wrong.";
            JOptionPane.showMessageDialog(null, message);
        }

    }

    /**
     * Starts the program by calling the constructor.
     */
    public static void main(String[] args) {
        new UnixTimeConverter().sendTheDateToPopup();
    }
}