import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.EventListenerList;

public class DetailsPanel extends JPanel {

    /**
     * Sets up the details panel on the left, with a lovely wee border.
     */

    private EventListenerList listenerList = new EventListenerList();

    public DetailsPanel() {
        Dimension size = getPreferredSize();
        size.width = 250;
        setPreferredSize(size);

        setBorder(BorderFactory.createTitledBorder("Control Panel"));

        JLabel unixLabel = new JLabel("Enter a Unix Code: ");
        //JLabel occupationLabel = new JLabel("Occupation");

        final JTextField unixCodeField = new JTextField(10);
        //final JTextField occupationField = new JTextField(10);

        JButton addBtn = new JButton("Calculate Date");

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String unixCode = unixCodeField.getText();
                //String occupation = occupationField.getText();
                try {
                    UnixTimeConverter.setEpochSeconds(Integer.parseInt(unixCode));
                } catch (NumberFormatException anException){
                    JOptionPane.showMessageDialog(null, "Incorrect value format entered." + anException);
                }
                String convertedTime = String.valueOf(UnixTimeConverter.getEpoch().getTime());
                String text = unixCode + ": " + convertedTime + "\n";

                fireDetailEvent(new DetailEvent(this, text));
            }
        });

        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        //// First column ////
        gc.anchor = GridBagConstraints.LINE_END;
        gc.weightx = 0.5;
        gc.weighty = 0.5;

        gc.gridx = 0;
        gc.gridy = 0;

        add(unixLabel, gc);

        //// Second column ////
        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 1;
        gc.gridy = 0;
        add(unixCodeField, gc);


        //// Final Row ////
        gc.weighty = 10;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.gridx = 1;
        gc.gridy = 2;
        add(addBtn, gc);
    }

    public void fireDetailEvent(DetailEvent event) { // Clearly this is just some kind of wizardry.
        Object[] listeners = listenerList.getListenerList();

        for(int i = 0; i < listeners.length; i += 2) { // I mean really?! We're going through this in double steps to find the DetailListener class or some shit..?
            if(listeners[i] == DetailListener.class){
                ((DetailListener)listeners[i+1]).detailEventOccurred(event);
            }
        }
    }

    public void addDetailListener(DetailListener listener) {
        listenerList.add(DetailListener.class, listener);
    }

    public void removeDetailListener(DetailListener listener) {
        listenerList.remove(DetailListener.class, listener);
        // One day ill do this. If I need to.
    }
}
