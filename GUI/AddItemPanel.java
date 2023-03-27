package GUI;

import Interface.FormListener;
import static Interface.FormListener.FormType.CHAIR;
import static Interface.FormListener.FormType.DESK;
import static Interface.FormListener.FormType.TABLE;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The AddItemPanel contains the JButtons for adding items to the basket, each
 * button has an ActionListener providing the functionality for generating the
 * furniture item forms used for adding items to the basket. The AddItemPanel
 * class implements the ActionListener interface to handle the button click
 * functionality for the AddItemPanel.
 *
 * @see ActionListener
 */
public class AddItemPanel extends JPanel implements ActionListener {

    private JButton addChair;
    private ImageIcon chairImage;
    private JButton addTable;
    private ImageIcon tableImage;
    private JButton addDesk;
    private ImageIcon deskImage;

    /**
     * The listener variable is the {@link Application} JPanel which implements 
     * the {@link FormListener} interface.
     */
    private FormListener listener;

    /**
     * The default constructor, calls the init method to initialise the 
     * JComponents and add them to the panel.
     */
    public AddItemPanel() {
        init();
    }

    private void init() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, 500));
        setBorder(
                BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        add(Box.createVerticalGlue());

        chairImage = new ImageIcon(
                new ImageIcon("resources/rimini-without-arms.png")
                        .getImage()
                        .getScaledInstance(
                                120, 120, java.awt.Image.SCALE_SMOOTH));

        addChair = new JButton("Add Chair", chairImage);
        addChair.setHorizontalTextPosition(JButton.CENTER);
        addChair.setVerticalTextPosition(JButton.TOP);
        addChair.addActionListener(this);
        addChair.setPreferredSize(new Dimension(120, 150));
        addChair.setAlignmentX(Component.CENTER_ALIGNMENT);
        addChair.setBackground(new Color(255, 255, 255));
        addChair.setOpaque(true);
        add(addChair);

        add(Box.createVerticalGlue());

        tableImage = new ImageIcon(
                new ImageIcon("resources/wood-table.png")
                        .getImage().getScaledInstance(
                                120, 120, java.awt.Image.SCALE_SMOOTH));

        addTable = new JButton("Add Table", tableImage);
        addTable.setHorizontalTextPosition(JButton.CENTER);
        addTable.setVerticalTextPosition(JButton.TOP);
        addTable.addActionListener(this);
        addTable.setPreferredSize(new Dimension(120, 150));
        addTable.setAlignmentX(Component.CENTER_ALIGNMENT);
        addTable.setBackground(new Color(255, 255, 255));
        addTable.setOpaque(true);
        add(addTable);

        add(Box.createVerticalGlue());

        deskImage = new ImageIcon(
                new ImageIcon("resources/desk.png")
                        .getImage().getScaledInstance(
                                120, 120, java.awt.Image.SCALE_SMOOTH));

        addDesk = new JButton("Add Desk", deskImage);
        addDesk.setHorizontalTextPosition(JButton.CENTER);
        addDesk.setVerticalTextPosition(JButton.TOP);
        addDesk.addActionListener(this);
        addDesk.setPreferredSize(new Dimension(120, 150));
        addDesk.setAlignmentX(Component.CENTER_ALIGNMENT);
        addDesk.setBackground(new Color(255, 255, 255));
        addDesk.setOpaque(true);
        add(addDesk);

        add(Box.createVerticalGlue());
    }

    /**
     * The actionPerformed method is called when an add item button is clicked
     * on this JPanel, the 
     * {@link Application#addItemForm(Interface.FormListener.FormType)} method 
     * is called and a {@link FormListener.FormType} enum constant is passed to 
     * the method, allowing the relevant form to be generated in the Application
     * centre panel.
     * 
     * The listener in this method is the Application class.
     *
     * @param ae is the button click which has been performed by the user.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == addChair) {
            listener.addItemForm(CHAIR);
        } else if (ae.getSource() == addTable) {
            listener.addItemForm(TABLE);
        } else {
            listener.addItemForm(DESK);
        }
    }

    /**
     * Registers the Application class to receive updates of when a button is
     * clicked on the this JPanel to enable the 
     * {@link Application#addItemForm(Interface.FormListener.FormType)} in the
     * Application class to be called.
     *
     * @param listener the Application class which implements
     * @see FormListener
     */
    public void registerFormListener(FormListener listener) {

        this.listener = listener;
    }
}
