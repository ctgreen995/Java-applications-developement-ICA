package GUI;

import Interface.ItemListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import Data.Desk;
import Data.Material;
import javax.swing.SwingConstants;

/**
 * This JPanel is used to represent a details form to be filled by the user when
 * adding or updating a {@link Desk} to the basket.
 */
public class DeskForm extends JPanel implements ActionListener {

    private JButton addButton;
    private JButton updateButton;
    private JButton cancelButton;
    private JLabel woodLabel;
    private JComboBox<Material> woodCombo;
    private JLabel drawerLabel;
    private JComboBox drawerCombo;
    private JLabel deskImage;
    private JLabel idNumberLabel;
    private JTextField idNumber;
    private JLabel deskTitle;
    private JLabel quantLabel;
    private JSpinner quantSpin;
    private JLabel widthLabel;
    private JSpinner widthSpin;
    private JLabel depthLabel;
    private JSpinner depthSpin;
    private int index;

    /**
     * The {@link Application} class which implements the ItemListener
     * interface.
     */
    private ItemListener itemListener;

    /**
     * This constructor is used when adding a new item to the basket, it
     * pre-fills the ID number field, taken from the last ID number in the
     * database, the init method initialises the JComponents on the form.
     *
     * @param id the item ID number to be displayed to the user.
     */
    public DeskForm(int id) {
        init();
        idNumber.setText(String.valueOf(id));
    }

    /**
     * This constructor is used when updating an item in the basket, it
     * pre-fills all fields in the form with the details from the item itself,
     * the init method initialises the JComponents on the form.
     *
     * @param index the basket index of the item to be passed back to the
     * {@link Application#replaceItem(int, Data.Item)} method.
     * @param desk the item to be updated.
     */
    public DeskForm(int index, Desk desk) {
        this.index = index;
        init();
        drawerCombo.setSelectedItem(desk.getDrawers());
        widthSpin.setValue(desk.getWidth());
        depthSpin.setValue(desk.getDepth());
        idNumber.setText(String.valueOf(desk.getId()));
        quantSpin.setValue(desk.getQuantity());
        woodCombo.setSelectedItem(desk.getMaterial());
        addButton.setVisible(false);
        updateButton.setVisible(true);
    }

    private void init() {

        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 2));

        deskImage = new JLabel();
        add(deskImage);
        deskImage.setIcon(new ImageIcon(
                new ImageIcon("resources/desk.png")
                        .getImage()
                        .getScaledInstance(
                                120, 120, java.awt.Image.SCALE_SMOOTH)));

        layout.putConstraint(SpringLayout.NORTH, deskImage, 35,
                SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, deskImage, 30,
                SpringLayout.WEST, this);

        deskTitle = new JLabel();
        deskTitle.setText("Desk");
        deskTitle.setFont(new java.awt.Font("Nimbus Sans L", 1, 32));
        deskTitle.setHorizontalTextPosition(SwingConstants.CENTER);
        add(deskTitle);

        layout.putConstraint(SpringLayout.NORTH, deskTitle, 30,
                SpringLayout.NORTH, deskImage);
        layout.putConstraint(SpringLayout.WEST, deskTitle, 70,
                SpringLayout.EAST, deskImage);

        idNumberLabel = new JLabel("ID Number: ");
        add(idNumberLabel);
        idNumberLabel.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));

        layout.putConstraint(SpringLayout.NORTH, idNumberLabel, 40,
                SpringLayout.SOUTH, deskImage);
        layout.putConstraint(SpringLayout.WEST, idNumberLabel, 0,
                SpringLayout.WEST, deskImage);

        idNumber = new JTextField();
        add(idNumber);
        idNumber.setBackground(Color.WHITE);
        idNumber.setPreferredSize(new Dimension(50, 25));
        idNumber.setHorizontalAlignment(SwingConstants.RIGHT);
        idNumber.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));
        idNumber.setEditable(false);

        layout.putConstraint(SpringLayout.NORTH, idNumber, -3,
                SpringLayout.NORTH, idNumberLabel);
        layout.putConstraint(SpringLayout.WEST, idNumber, 10,
                SpringLayout.EAST, idNumberLabel);

        woodLabel = new JLabel("Type of Wood: ");
        add(woodLabel);
        woodLabel.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));

        layout.putConstraint(SpringLayout.NORTH, woodLabel, 40,
                SpringLayout.SOUTH, idNumberLabel);
        layout.putConstraint(SpringLayout.WEST, woodLabel, 0,
                SpringLayout.WEST, idNumberLabel);

        woodCombo = new JComboBox<>();
        woodCombo.setModel(new DefaultComboBoxModel<>(Material.values()));
        add(woodCombo);
        woodCombo.setBackground(Color.WHITE);
        woodCombo.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));

        layout.putConstraint(SpringLayout.NORTH, woodCombo, -4,
                SpringLayout.NORTH, woodLabel);
        layout.putConstraint(SpringLayout.WEST, woodCombo, 10,
                SpringLayout.EAST, woodLabel);

        drawerLabel = new JLabel();
        add(drawerLabel);
        drawerLabel.setText("Drawers: ");
        drawerLabel.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));

        layout.putConstraint(SpringLayout.NORTH, drawerLabel, 40,
                SpringLayout.SOUTH, woodLabel);
        layout.putConstraint(SpringLayout.WEST, drawerLabel, 0,
                SpringLayout.WEST, woodLabel);

        drawerCombo = new JComboBox<>();
        Integer[] drawers = {1, 2, 3, 4};
        drawerCombo.setModel(new DefaultComboBoxModel<>(drawers));
        add(drawerCombo);
        drawerCombo.setBackground(Color.WHITE);
        drawerCombo.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));

        layout.putConstraint(SpringLayout.NORTH, drawerCombo, -4,
                SpringLayout.NORTH, drawerLabel);
        layout.putConstraint(SpringLayout.WEST, drawerCombo, 0,
                SpringLayout.WEST, woodCombo);

        quantLabel = new JLabel("Quantity: ");
        add(quantLabel);
        quantLabel.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));

        layout.putConstraint(SpringLayout.NORTH, quantLabel, 40,
                SpringLayout.SOUTH, drawerLabel);
        layout.putConstraint(SpringLayout.WEST, quantLabel, 0,
                SpringLayout.WEST, drawerLabel);

        widthLabel = new JLabel();
        add(widthLabel);
        widthLabel.setText("Width (cm): ");
        widthLabel.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));

        layout.putConstraint(SpringLayout.NORTH, widthLabel, 0,
                SpringLayout.NORTH, drawerLabel);
        layout.putConstraint(SpringLayout.WEST, widthLabel, 20,
                SpringLayout.EAST, woodCombo);

        depthLabel = new JLabel();
        add(depthLabel);
        depthLabel.setText("Depth (cm): ");
        depthLabel.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));

        layout.putConstraint(SpringLayout.NORTH, depthLabel, 0,
                SpringLayout.NORTH, quantLabel);
        layout.putConstraint(SpringLayout.WEST, depthLabel, 20,
                SpringLayout.EAST, woodCombo);

        addButton = new JButton("Add");
        add(addButton);
        addButton.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));
        addButton.addActionListener(this);
        addButton.setBackground(Color.WHITE);

        layout.putConstraint(SpringLayout.NORTH, addButton, 50,
                SpringLayout.NORTH, quantLabel);
        layout.putConstraint(SpringLayout.WEST, addButton, 0,
                SpringLayout.WEST, quantLabel);

        updateButton = new JButton("Update");
        add(updateButton);
        updateButton.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));
        updateButton.addActionListener(this);
        updateButton.setBackground(Color.WHITE);

        layout.putConstraint(SpringLayout.NORTH, updateButton, 50,
                SpringLayout.NORTH, quantLabel);
        layout.putConstraint(SpringLayout.WEST, updateButton, 0,
                SpringLayout.WEST, quantLabel);
        updateButton.setVisible(false);

        cancelButton = new JButton("Cancel");
        add(cancelButton);
        cancelButton.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));
        cancelButton.addActionListener(this);
        cancelButton.setBackground(Color.WHITE);

        layout.putConstraint(SpringLayout.NORTH, cancelButton, 0,
                SpringLayout.NORTH, updateButton);
        layout.putConstraint(SpringLayout.WEST, cancelButton, 40,
                SpringLayout.EAST, updateButton);

        int quantMin = 1;
        int quantMax = 10;

        SpinnerNumberModel quantValue = new SpinnerNumberModel(quantMin,
                quantMin, quantMax, 1);

        quantSpin = new JSpinner(quantValue);
        quantSpin.setPreferredSize(new Dimension(60, 25));
        add(quantSpin);
        quantSpin.setBackground(Color.WHITE);
        quantSpin.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));

        layout.putConstraint(SpringLayout.NORTH, quantSpin, -3,
                SpringLayout.NORTH, quantLabel);
        layout.putConstraint(SpringLayout.WEST, quantSpin, 0,
                SpringLayout.WEST, woodCombo);

        widthSpin = new JSpinner();
        widthSpin.setPreferredSize(new Dimension(50, 25));
        add(widthSpin);
        widthSpin.setBackground(Color.WHITE);
        widthSpin.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));

        layout.putConstraint(SpringLayout.NORTH, widthSpin, -3,
                SpringLayout.NORTH, widthLabel);
        layout.putConstraint(SpringLayout.WEST, widthSpin, 10,
                SpringLayout.EAST, widthLabel);

        depthSpin = new JSpinner();
        depthSpin.setPreferredSize(new Dimension(60, 25));
        add(depthSpin);
        depthSpin.setBackground(Color.WHITE);
        depthSpin.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));

        layout.putConstraint(SpringLayout.NORTH, depthSpin, -3,
                SpringLayout.NORTH, depthLabel);
        layout.putConstraint(SpringLayout.WEST, depthSpin, 10,
                SpringLayout.EAST, depthLabel);

        int widthMin = 100;
        int widthMax = 200;
        int depthMin = 50;
        int depthMax = 75;

        SpinnerNumberModel widthValue = new SpinnerNumberModel(150,
                null, null, 10);

        widthValue.addChangeListener((ChangeEvent e) -> {
            int value = (int) widthValue.getValue();

            if (value < widthMin) {

                addButton.setEnabled(false);
                widthValue.setValue(widthMin);

            } else if (value > widthMax) {

                addButton.setEnabled(false);
                widthValue.setValue(widthMax);

            } else {
                addButton.setEnabled(true);
            }
        });

        SpinnerNumberModel depthValue = new SpinnerNumberModel(60,
                null, null, 5);

        depthValue.addChangeListener((ChangeEvent e) -> {
            int value = (int) depthValue.getValue();

            if (value < depthMin) {

                addButton.setEnabled(false);
                depthValue.setValue(depthMin);

            } else if (value > depthMax) {

                addButton.setEnabled(false);
                depthValue.setValue(depthMax);

            } else {
                addButton.setEnabled(true);
            }
        });
        widthSpin.setModel(widthValue);
        depthSpin.setModel(depthValue);
    }

    /**
     * This method registers the {@link Application} class to receive updates on
     * the button which has been clicked on the form.
     *
     * @param listener the Application class which implements the
     * {@link ItemListener} interface.
     */
    public void registerItemListener(ItemListener listener) {
        itemListener = listener;
    }

    /**
     * This method handles the button clicks for the form, it calls the relevant
     * method in the {@link Application} class.
     *
     * @param e is the button click event which has been triggered.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        Desk desk = new Desk(Integer.valueOf(
                idNumber.getText()),
                (Material) woodCombo.getSelectedItem(),
                (int) quantSpin.getValue(),
                (Integer) drawerCombo.getSelectedItem(),
                (int) widthSpin.getValue(),
                (int) depthSpin.getValue());

        if (e.getSource() == addButton) {

            itemListener.addItem(desk);

        } else if (e.getSource() == updateButton) {

            itemListener.replaceItem(index, desk);

        } else {
            itemListener.cancelItem();
        }
    }
}
