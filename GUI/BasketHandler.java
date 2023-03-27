package GUI;

import Interface.ItemDetailsListener;
import Interface.FormListener;
import Interface.BasketListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import static java.awt.event.MouseEvent.BUTTON1;
import static java.awt.event.MouseEvent.BUTTON2;
import static java.awt.event.MouseEvent.BUTTON3;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import Data.Basket;
import Data.Chair;
import Data.Desk;
import Data.Item;
import Data.Table;
import Database.DatabaseHandler;
import java.awt.event.MouseAdapter;
import javax.swing.JDialog;
import javax.swing.Timer;

/**
 * The BasketHandler class acts as the interface between the GUI and the Basket
 * data class, all the user interactions with the basket through the GUI will be
 * handled by this class.
 */
public class BasketHandler extends JPanel implements BasketListener {

    private ItemDetailsListener detailsListener;
    private FormListener formListener;
    private final Basket basket;
    private final DatabaseHandler database;
    private final ArrayList<JLabel> gridLabels = new ArrayList<>();

    /**
     * The constructor instantiates a new Basket and database object and calls
     * the init() to populate the gridLabels ArrayList with the JLabels used to
     * display the images of the items in the basket.
     *
     * The {@link DatabaseHandler#reloadBasket(Data.Basket)} 
     * is used when the program is run to reload the previous basket, which was 
     * not saved, from the last time the program was run.
     */
    public BasketHandler() {
        basket = Basket.getInstance();
        database = DatabaseHandler.getInstance();
        init();
    }

    private void init() {

        setLayout(new GridLayout(3, 3, 5, 5));
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 2));
        setBackground(new Color(150, 170, 225));

        for (int i = 0; i < 9; i++) {
            gridLabels.add(new JLabel());
        }
        for (JLabel label : gridLabels) {

            label.setPreferredSize(new Dimension(120, 120));

            label.setBorder(BorderFactory
                    .createLineBorder(Color.DARK_GRAY, 2, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            label.setBackground(new Color(255, 255, 255));
            label.addMouseListener(mouseHandler);
            label.setOpaque(true);
            label.setBorder(BorderFactory
                    .createLineBorder(Color.DARK_GRAY, 2, true));
            add(label);
        }
        database.reloadBasket(basket);
    }

    /**
     * This method retrieves the last ID from the items table in the database,
     * this is to know what the ID of the next item added to the basket should
     * be.
     *
     * @return the int ID of the last item added to the database.
     */
    public int getLastId() {
        return database.readLastId();
    }

    /**
     * This method returns the size of the basket, it is used to determine if
     * the basket is full when attempting to add a new item to the basket.
     *
     * @return the number of items in the basket.
     */
    public int getBasketSize() {
        return basket.size();
    }

    /**
     * This method adds an item to the basket.
     *
     * @param item is the item to be added to the basket.
     */
    public void addItem(Item item) {

        database.writeItem(item);
        basket.add(item);
        paintBasket();
    }

    /**
     * This method replaces an item, at a specified index, in the basket.
     *
     * @param index the index of the item, in the basket, to be replaced.
     * @param item the item which is to replace the item currently in the
     * basket.
     */
    public void replaceItem(int index, Item item) {

        database.updateItem(item);
        basket.set(index, item);
        paintBasket();
    }

    /**
     * This method sets the icons of the JLabels in the basket grid to the
     * images of the items on the basket and also displays the current total
     * price of the basket via the 
     * {@link DetailsPanel#showTotal(double, java.lang.String)} method.
     */
    public void paintBasket() {

        for (JLabel label : gridLabels) {
            label.setIcon(null);
        }
        for (int i = 0; i < basket.size(); i++) {
            gridLabels.get(i).setIcon(
                    basket.get(i).getImage());
        }
        detailsListener.showTotal(basket.getTotal(), basket.getBasketName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createSummary() {

        return basket.toString();
    }

    /**
     * {@inheritDoc}
     *
     * @see DatabaseHandler#writeBasket(Data.Basket)
     */
    @Override
    public boolean saveBasket() {

        JLabel savedMessage;
        JOptionPane saved;
        JDialog showSaved;

        if (basket.isEmpty()) {

            savedMessage = new JLabel("Basket is empty.", JLabel.CENTER);
            savedMessage.setFont(new java.awt.Font("Arial", 1, 18));
            
            saved = new JOptionPane(
                    savedMessage,
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.DEFAULT_OPTION,
                    null, new Object[]{}, null);
            
            showSaved = saved.createDialog("Error");

            Timer timer = new Timer(1000, e -> showSaved.dispose());
            timer.setRepeats(false);
            timer.start();

            showSaved.setVisible(true);
            return false;

        } else {

            if (database.writeBasket(basket)) {

                clearBasket();
                paintBasket();

                savedMessage = new JLabel("Basket saved.", JLabel.CENTER);
                savedMessage.setFont(new java.awt.Font("Arial", 1, 18));

                saved = new JOptionPane(
                        savedMessage,
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.DEFAULT_OPTION,
                        null, new Object[]{}, null);

                showSaved = saved.createDialog("Success");

                showSaved.setModal(true);

                Timer timer = new Timer(1000, e -> showSaved.dispose());
                timer.setRepeats(false);
                timer.start();

                showSaved.setVisible(true);
                return true;
            }
            return false;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see DatabaseHandler#readBasket(Data.Basket)
     */
    @Override
    public void loadBasket() {

        if (basket.isEmpty()) {

            database.readBasket(basket);
            paintBasket();

        } else {
            int result = JOptionPane.showConfirmDialog(this,
                    "Basket is not empty, save basket first?",
                    "Warning",
                    JOptionPane.YES_NO_CANCEL_OPTION);

            switch (result) {
                case JOptionPane.YES_OPTION:

                    if(saveBasket()) {
                      database.readBasket(basket);
                    paintBasket();
                    break;  
                    } else {
                        break;
                    }

                case JOptionPane.NO_OPTION:

                    clearBasket();
                    database.readBasket(basket);
                    paintBasket();
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see DatabaseHandler#cleanDatabase()}
     */
    @Override
    public void clearBasket() {

        database.cleanDatabase();
        basket.clear();
        basket.setBasketName(null);
        paintBasket();
    }

    /**
     * This {@link MouseAdapter} is used to listen for mouse events on the
     * JLabels in the basket grid, it is passed as an argument to each JLabel in
     * the basket grid.
     */
    MouseAdapter mouseHandler = new MouseAdapter() {

        /**
         * This method handles the mouse click events, left click shows the
         * items details via the
         * {@link DetailsPanel#showDetails(java.lang.String)}, a middle click
         * updates an item and a right click removes an item from the basket.
         */
        @Override
        public void mouseClicked(MouseEvent e) {

            JLabel savedMessage = new JLabel(
                    "Basket space contains no item.", JLabel.CENTER);

            JOptionPane error = new JOptionPane(
                    savedMessage,
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.DEFAULT_OPTION,
                    null, new Object[]{}, null);

            JDialog showError = error.createDialog("Error");

            showError.setModal(true);

            Timer timer = new Timer(1000, ev -> showError.dispose());
            timer.setRepeats(false);

            switch (e.getButton()) {
                
                case BUTTON1:
                
                    for (int i = 0; i < gridLabels.size(); i++) {

                        if (e.getSource() == gridLabels.get(i)) {

                            if (i < basket.size()) {

                                detailsListener.showDetails(
                                        basket.get(i).toString());
                            } else {
                                timer.start();
                                showError.setVisible(true);
                            }
                        }
                    }
                    break;
                    
                case BUTTON2:

                    Item item = null;
                    int index = -1;

                    for (int i = 0; i < gridLabels.size(); i++) {

                        if (e.getSource() == gridLabels.get(i)) {

                            if (i < basket.size()) {
                                
                                index = i;
                                item = basket.get(i);
                                
                            } else {
                                timer.start();
                                showError.setVisible(true);
                                break;
                            }
                        }

                        if (item instanceof Chair) {
                            
                            formListener.replaceItemForm(
                                    new ChairForm(index, (Chair) item));

                        } else if (item instanceof Table) {
                            
                            formListener.replaceItemForm(
                                    new TableForm(index, (Table) item));

                        } else if (item instanceof Desk) {
                            
                            formListener.replaceItemForm(
                                    new DeskForm(index, (Desk) item));
                        }
                    }
                    break;
                    
                case BUTTON3:
                    
                    for (int i = 0; i < gridLabels.size(); i++) {

                        if (e.getSource() == gridLabels.get(i)) {

                            if (i < basket.size()) {

                                int result = JOptionPane.showConfirmDialog(
                                        BasketHandler.this,
                                        "Are you sure you want to remove "
                                        + "this item?");

                                if (result == JOptionPane.YES_OPTION) {
                                    basket.remove(i);
                                    paintBasket();
                                }
                            } else {
                                timer.start();
                                showError.setVisible(true);
                            }
                        }
                    }
            }
        }
    };

    /**
     * This method registers the {@link DetailsPanel} class to receive updates
     * when an item has been left clicked in the basket grid, to enable the
     * panel to display the details of the corresponding item, via
     * {@link DetailsPanel#showDetails(java.lang.String)} method.
     *
     * @param listener DetailsPanel class which implements the
     * {@link ItemDetailsListener} interface.
     */
    public void registerDetailsListener(ItemDetailsListener listener) {
        detailsListener = listener;
    }

    /**
     * This method registers the {@link Application} class to receive updates
     * when an item has been middle clicked in the basket grid, to enable the
     * correct item update form to be displayed in the centre panel of the
     * Application, via the
     * {@link Application#replaceItemForm(javax.swing.JPanel)} method.
     *
     * @param listener the Application class which implements the
     * {@link FormListener} interface.
     */
    public void registerFormListener(FormListener listener) {
        formListener = listener;
    }
}
