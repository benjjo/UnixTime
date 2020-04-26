import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.event.EventListenerList;

public class DetailsPanel extends JPanel {

    private EventListenerList listenerList = new EventListenerList();

    /**
     * Sets up the details panel on the left, with a lovely wee border.
     */
    public DetailsPanel() {
        Dimension size = getPreferredSize();
        size.width = 250;
        setPreferredSize(size);

        setBorder(BorderFactory.createTitledBorder("Control Panel"));

        JLabel unixLabel = new JLabel("Enter a Unix Code: ");
        JLabel dateLabel = new JLabel();
        int now = (int) (Calendar.getInstance().getTimeInMillis() / 1000);
        final JTextField unixCodeField = new JTextField(String.valueOf(now), 10);

        JButton addBtn = new JButton("Calculate Date");

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String unixCode = unixCodeField.getText().trim();

                try {
                    UnixTimeConverter.setEpochSeconds(Integer.parseInt(unixCode));
                } catch (NumberFormatException anException){
                    JOptionPane.showMessageDialog(null, "Incorrect value format entered. Try using numbers only.");
                    //unixCode = "Unix time began";
                }
                String convertedTime = String.valueOf(UnixTimeConverter.getEpoch().getTime());
                //String text = convertedTime;

                fireDetailEvent(new DetailEvent(this, convertedTime));
            }
        });

        ////SETUP THE OUTPUT BOX////
        addDetailListener(new DetailListener(){
            public void detailEventOccurred(DetailEvent event) {
                String text = event.getText();

                dateLabel.setText(text);
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

        //// Output / button rows ////
        gc.gridx = 0;
        gc.gridwidth = 2;

        gc.anchor = GridBagConstraints.BASELINE;
        gc.gridy = 2;
        gc.weighty = 5;
        add(addBtn, gc);
        gc.gridy = 3;
        gc.weighty = 10;
        add(dateLabel, gc);

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
