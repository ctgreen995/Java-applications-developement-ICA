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
import Data.Base;
import Data.Material;
import Data.Table;
import javax.swing.SwingConstants;

/**
 * This JPanel is used to represent a details form to be filled by the user when
 * adding or updating a {@link Table} to the basket.
 */
public class TableForm extends JPanel implements ActionListener {

    private JButton addButton;
    private JButton updateButton;
    private JButton cancelButton;
    private JLabel woodLabel;
    private JComboBox<Material> woodCombo;
    private JLabel baseComboLabel;
    private JComboBox baseCombo;
    private JLabel tableImage;
    private JLabel idNumberLabel;
    private JTextField idNumber;
    private JLabel tableTitle;
    private JLabel quantLabel;
    private JSpinner quantSpin;
    private JLabel diameterLabel;
    private JSpinner diameterSpin;
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
    public TableForm(int id) {
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
     * @param table the item to be updated.
     */
    public TableForm(int index, Table table) {
        init();
        this.index = index;
        idNumber.setText(String.valueOf(table.getId()));
        quantSpin.setValue(table.getQuantity());
        woodCombo.setSelectedItem(table.getMaterial());
        addButton.setVisible(false);
        updateButton.setVisible(true);
        baseCombo.setSelectedItem(table.getBase());
        diameterSpin.setValue(table.getDiameter());
    }

    private void init() {

        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 2));

        tableImage = new JLabel();
        tableImage.setIcon(new ImageIcon(
                new ImageIcon("resources/wood-table.png")
                        .getImage()
                        .getScaledInstance(
                                120, 120, java.awt.Image.SCALE_SMOOTH)));
        add(tableImage);
        
        layout.putConstraint(SpringLayout.NORTH, tableImage, 35,
                SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, tableImage, 30,
                SpringLayout.WEST, this);

        tableTitle = new JLabel();
        tableTitle.setText("Table");
        tableTitle.setFont(new java.awt.Font("Nimbus Sans L", 1, 32));
        tableTitle.setHorizontalTextPosition(SwingConstants.CENTER);
        add(tableTitle);
      
        layout.putConstraint(SpringLayout.NORTH, tableTitle, 30,
                SpringLayout.NORTH, tableImage);
        layout.putConstraint(SpringLayout.WEST, tableTitle, 70,
                SpringLayout.EAST, tableImage);

        idNumberLabel = new JLabel("ID Number: ");
        add(idNumberLabel);
        idNumberLabel.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));
       
        layout.putConstraint(SpringLayout.NORTH, idNumberLabel, 40,
                SpringLayout.SOUTH, tableImage);
        layout.putConstraint(SpringLayout.WEST, idNumberLabel, 0,
                SpringLayout.WEST, tableImage);

        idNumber = new JTextField();
        add(idNumber);
        idNumber.setPreferredSize(new Dimension(50, 25));
        idNumber.setHorizontalAlignment(SwingConstants.RIGHT);
        idNumber.setBackground(Color.WHITE);
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

        baseComboLabel = new JLabel();
        add(baseComboLabel);
        baseComboLabel.setText("Base: ");
        baseComboLabel.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));
       
        layout.putConstraint(SpringLayout.NORTH, baseComboLabel, 40,
                SpringLayout.SOUTH, woodLabel);
        layout.putConstraint(SpringLayout.WEST, baseComboLabel, 0,
                SpringLayout.WEST, woodLabel);

        baseCombo = new JComboBox<>();
        baseCombo.setModel(new DefaultComboBoxModel<>(Base.values()));
        add(baseCombo);
        baseCombo.setBackground(Color.WHITE);
        baseCombo.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));
       
        layout.putConstraint(SpringLayout.NORTH, baseCombo, -4,
                SpringLayout.NORTH, baseComboLabel);
        layout.putConstraint(SpringLayout.WEST, baseCombo, 0,
                SpringLayout.WEST, woodCombo);

        quantLabel = new JLabel("Quantity: ");
        add(quantLabel);
        quantLabel.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));
        
        layout.putConstraint(SpringLayout.NORTH, quantLabel, 40,
                SpringLayout.SOUTH, baseComboLabel);
        layout.putConstraint(SpringLayout.WEST, quantLabel, 0,
                SpringLayout.WEST, baseComboLabel);

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
        quantSpin.setPreferredSize(new Dimension(50, 25));
        add(quantSpin);
        quantSpin.setBackground(Color.WHITE);
        quantSpin.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));
       
        layout.putConstraint(SpringLayout.NORTH, quantSpin, -3,
                SpringLayout.NORTH, quantLabel);
        layout.putConstraint(SpringLayout.WEST, quantSpin, 0,
                SpringLayout.WEST, woodCombo);

        diameterLabel = new JLabel();
        add(diameterLabel);
        diameterLabel.setText("Diameter (cm): ");
        diameterLabel.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));
        
        layout.putConstraint(SpringLayout.NORTH, diameterLabel, 0,
                SpringLayout.NORTH, quantLabel);
        layout.putConstraint(SpringLayout.WEST, diameterLabel, 20,
                SpringLayout.EAST, woodCombo);

        diameterSpin = new JSpinner();
        diameterSpin.setPreferredSize(new Dimension(60, 25));
        add(diameterSpin);
        diameterSpin.setBackground(Color.WHITE);
        diameterSpin.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));
        
        layout.putConstraint(SpringLayout.NORTH, diameterSpin, -3,
                SpringLayout.NORTH, diameterLabel);
        layout.putConstraint(SpringLayout.WEST, diameterSpin, 10,
                SpringLayout.EAST, diameterLabel);

        int diameterMax = 100;
        int diameterMin = 50;

        SpinnerNumberModel diameterValue = new SpinnerNumberModel(
                75, null, null, 5);

        diameterValue.addChangeListener((ChangeEvent e) -> {
            int value = (int) diameterValue.getValue();
            
            if (value < diameterMin) {
                
                addButton.setEnabled(false);
                diameterValue.setValue(diameterMin);
                
            } else if (value > diameterMax) {
                
                addButton.setEnabled(false);
                diameterValue.setValue(diameterMax);
                
            } else {
                addButton.setEnabled(true);
            }
        });
        diameterSpin.setModel(diameterValue);
    }

    /**
     * This method handles the button clicks for the form, it calls the relevant
     * method in the {@link Application} class.
     *
     * @param e is the button click event which has been triggered.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        Table table = new Table(
                Integer.valueOf(idNumber.getText()),
                (Material) woodCombo.getSelectedItem(),
                (int) quantSpin.getValue(),
                (Base) baseCombo.getSelectedItem(),
                (int) diameterSpin.getValue());

        if (e.getSource() == addButton) {

            itemListener.addItem(table);
        } else if (e.getSource() == updateButton) {

            itemListener.replaceItem(index, table);
        } else {
            itemListener.cancelItem();
        }
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
}
