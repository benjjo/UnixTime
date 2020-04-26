import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private DetailsPanel detailsPanel;

    public MainFrame(String title){
        super(title);

        // Set the layout manager
        setLayout(new BorderLayout()); // BorderLayout just lets you add components to top, left and centre of the frame.

        // Create Swing components
//        final JTextArea textArea = new JTextArea();  // Big area that you can write text.

        detailsPanel = new DetailsPanel();
/*
        detailsPanel.addDetailListener(new DetailListener(){
            public void detailEventOccurred(DetailEvent event) {
                String text = event.getText();

                textArea.append(text);
            }
        });

 */

        // Add Swing components to the content pane
        Container c = getContentPane();

        //c.add(textArea, BorderLayout.CENTER);
        c.add(detailsPanel, BorderLayout.WEST);
    }
}
