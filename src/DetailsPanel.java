import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import javax.swing.event.EventListenerList;

public class DetailsPanel extends JPanel implements ActionListener {

    private EventListenerList listenerList = new EventListenerList();
    private String scottTimeOffset = "+01:00 Scotland";
    private String UTCTimeOffset = "0:00 UTC time    ";

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
                }
                String convertedTime = String.valueOf(UnixTimeConverter.getEpoch().getTime());

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

        //// SETUP RADIO BUTTON ////
        JRadioButton scottOffSetButton = new JRadioButton(scottTimeOffset);
        scottOffSetButton.setMnemonic(KeyEvent.VK_B);
        scottOffSetButton.setActionCommand("1");
        scottOffSetButton.setSelected(true);

        JRadioButton UCTOffSetButton = new JRadioButton(UTCTimeOffset);
        UCTOffSetButton.setMnemonic(KeyEvent.VK_B);
        UCTOffSetButton.setActionCommand("0");

        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(scottOffSetButton);
        group.add(UCTOffSetButton);

        //Register a listener for the radio button
        scottOffSetButton.addActionListener( this);
        UCTOffSetButton.addActionListener( this);

        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        //// First column ////
        gc.anchor = GridBagConstraints.ABOVE_BASELINE;
        gc.weightx = 0.5;
        gc.weighty = 2;

        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 2;
        add(UCTOffSetButton, gc);
        gc.gridy = 1;
        add(scottOffSetButton, gc);

        gc.weighty = 7;
        gc.gridwidth = 1;
        gc.gridy = 2;

        add(unixLabel, gc);

        //// Second column ////
        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 1;
        gc.gridy = 2;
        add(unixCodeField, gc);

        //// Output / button rows ////
        gc.gridx = 0;
        gc.gridwidth = 2;

        gc.anchor = GridBagConstraints.BASELINE;
        gc.gridy = 3;
        gc.weighty = 5;
        add(addBtn, gc);
        gc.gridy = 4;
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

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("1")) {
            UnixTimeConverter.timeOffset = 1;
        } else {
            UnixTimeConverter.timeOffset = 0;
        }
    }
}
