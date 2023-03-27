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
import Data.Arms;
import Data.Chair;
import Data.Material;
import javax.swing.SwingConstants;

/**
 * This JPanel is used to represent a details form to be filled by the user when
 * adding or updating a {@link Chair} to the basket.
 */
public class ChairForm extends JPanel implements ActionListener {

    private JButton addButton;
    private JButton updateButton;
    private JButton cancelButton;
    private JLabel woodLabel;
    private JComboBox woodCombo;
    private JLabel armLabel;
    private JComboBox armCombo;
    private JLabel chairImage;
    private JLabel idNumberLabel;
    private JTextField idNumber;
    private JLabel chairTitle;
    private JLabel quantLabel;
    private JSpinner quantSpin;
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
     * @param id the ID number to be displayed to the user.
     */
    public ChairForm(int id) {
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
     * @param chair the item to be updated.
     */
    public ChairForm(int index, Chair chair) {
        
        this.index = index;
        init();
        idNumber.setText(String.valueOf(chair.getId()));
        quantSpin.setValue(chair.getQuantity());
        woodCombo.setSelectedItem(chair.getMaterial());
        armCombo.setSelectedItem(chair.getArms());
        addButton.setVisible(false);
        updateButton.setVisible(true);
    }

    private void init() {

        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 2));

        chairImage = new JLabel();
        chairImage.setIcon(new ImageIcon(
                new ImageIcon("resources/rimini-without-arms.png")
                        .getImage()
                        .getScaledInstance(
                                120, 120, java.awt.Image.SCALE_SMOOTH)));
        add(chairImage);
        
        layout.putConstraint(SpringLayout.NORTH, chairImage, 35,
                SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, chairImage, 30,
                SpringLayout.WEST, this);

        chairTitle = new JLabel();
        chairTitle.setText("Chair");
        chairTitle.setFont(new java.awt.Font("Nimbus Sans L", 1, 32));
        chairTitle.setHorizontalTextPosition(SwingConstants.CENTER);
        add(chairTitle);
        
        layout.putConstraint(SpringLayout.NORTH, chairTitle, 30,
                SpringLayout.NORTH, chairImage);
        layout.putConstraint(SpringLayout.WEST, chairTitle, 70,
                SpringLayout.EAST, chairImage);

        idNumberLabel = new JLabel("ID Number: ");
        idNumberLabel.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));
        add(idNumberLabel);
        
        layout.putConstraint(SpringLayout.NORTH, idNumberLabel, 40,
                SpringLayout.SOUTH, chairImage);
        layout.putConstraint(SpringLayout.WEST, idNumberLabel, 0,
                SpringLayout.WEST, chairImage);

        idNumber = new JTextField();
        idNumber.setBackground(Color.WHITE);
        idNumber.setPreferredSize(new Dimension(50, 25));
        idNumber.setHorizontalAlignment(SwingConstants.RIGHT);
        idNumber.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));
        idNumber.setEditable(false);
        add(idNumber);
        
        layout.putConstraint(SpringLayout.NORTH, idNumber, -3,
                SpringLayout.NORTH, idNumberLabel);
        layout.putConstraint(SpringLayout.WEST, idNumber, 10,
                SpringLayout.EAST, idNumberLabel);

        woodLabel = new JLabel("Type of Wood: ");
        woodLabel.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));
        add(woodLabel);
        
        layout.putConstraint(SpringLayout.NORTH, woodLabel, 40,
                SpringLayout.SOUTH, idNumberLabel);
        layout.putConstraint(SpringLayout.WEST, woodLabel, 0,
                SpringLayout.WEST, idNumberLabel);

        woodCombo = new JComboBox<>();
        woodCombo.setModel(new DefaultComboBoxModel<>(Material.values()));
        woodCombo.setBackground(Color.WHITE);
        woodCombo.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));
        add(woodCombo);
        
        layout.putConstraint(SpringLayout.NORTH, woodCombo, -4,
                SpringLayout.NORTH, woodLabel);
        layout.putConstraint(SpringLayout.WEST, woodCombo, 10,
                SpringLayout.EAST, woodLabel);

        armLabel = new JLabel();
        armLabel.setText("Arms: ");
        armLabel.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));
        add(armLabel);
        
        layout.putConstraint(SpringLayout.NORTH, armLabel, 40,
                SpringLayout.SOUTH, woodLabel);
        layout.putConstraint(SpringLayout.WEST, armLabel, 0,
                SpringLayout.WEST, woodLabel);

        armCombo = new JComboBox<>();
        armCombo.setModel(new DefaultComboBoxModel<>(Arms.values()));
        armCombo.setBackground(Color.WHITE);
        armCombo.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));
        add(armCombo);
        
        layout.putConstraint(SpringLayout.NORTH, armCombo, -4,
                SpringLayout.NORTH, armLabel);
        layout.putConstraint(SpringLayout.WEST, armCombo, 0,
                SpringLayout.WEST, woodCombo);

        quantLabel = new JLabel("Quantity: ");
        quantLabel.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));
        add(quantLabel);
        
        layout.putConstraint(SpringLayout.NORTH, quantLabel, 40,
                SpringLayout.SOUTH, armLabel);
        layout.putConstraint(SpringLayout.WEST, quantLabel, 0,
                SpringLayout.WEST, armLabel);

        addButton = new JButton("Add");
        addButton.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));
        addButton.addActionListener(this);
        addButton.setBackground(Color.WHITE);
        add(addButton);
        
        layout.putConstraint(SpringLayout.NORTH, addButton, 50,
                SpringLayout.NORTH, quantLabel);
        layout.putConstraint(SpringLayout.WEST, addButton, 0,
                SpringLayout.WEST, quantLabel);

        updateButton = new JButton("Update");
        updateButton.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));
        updateButton.addActionListener(this);
        updateButton.setBackground(Color.WHITE);
        add(updateButton);
        
        layout.putConstraint(SpringLayout.NORTH, updateButton, 50,
                SpringLayout.NORTH, quantLabel);
        layout.putConstraint(SpringLayout.WEST, updateButton, 0,
                SpringLayout.WEST, quantLabel);
        updateButton.setVisible(false);

        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));
        cancelButton.addActionListener(this);
        cancelButton.setBackground(Color.WHITE);
        add(cancelButton);
        
        layout.putConstraint(SpringLayout.NORTH, cancelButton, 0,
                SpringLayout.NORTH, updateButton);
        layout.putConstraint(SpringLayout.WEST, cancelButton, 40,
                SpringLayout.EAST, updateButton);

        int quantMin = 1;
        int quantMax = 10;

        SpinnerNumberModel quantValue = new SpinnerNumberModel(quantMin,
                quantMin, quantMax, 1);

        quantSpin = new JSpinner(quantValue);
        quantSpin.setPreferredSize(new Dimension(50, 25));
        quantSpin.setBackground(Color.WHITE);
        quantSpin.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));
        add(quantSpin);
        
        layout.putConstraint(SpringLayout.NORTH, quantSpin, -3,
                SpringLayout.NORTH, quantLabel);
        layout.putConstraint(SpringLayout.WEST, quantSpin, 0,
                SpringLayout.WEST, woodCombo);
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

        Chair chair = new Chair(
                Integer.valueOf(idNumber.getText()),
                (Material) woodCombo.getSelectedItem(),
                (int) quantSpin.getValue(),
                (Arms) armCombo.getSelectedItem());

        if (e.getSource() == addButton) {
            
            itemListener.addItem(chair);
            
        } else if (e.getSource() == updateButton) {
            
            itemListener.replaceItem(index, chair);
        
        } else {
            itemListener.cancelItem();
        }
    }
}
