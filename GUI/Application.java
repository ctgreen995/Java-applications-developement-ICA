package GUI;

import Interface.ItemListener;
import Interface.FormListener;
import static Interface.FormListener.FormType.CHAIR;
import static Interface.FormListener.FormType.DESK;
import static Interface.FormListener.FormType.TABLE;
import java.awt.BorderLayout;
import javax.swing.*;
import Data.Item;
import java.awt.Dimension;

/**
 * This application handles customer orders for furniture items in a user
 * friendly GUI, stores them in a database for later use and generates summaries
 * of orders and outputs them to console.
 *
 * @author K0170764
 */
public class Application extends JFrame implements FormListener, ItemListener {

    private AddItemPanel addItems;
    private TitlePanel title;
    private FeaturePanel features;
    private DetailsPanel details;
    private BasketHandler basket;
    private BorderLayout layout;

    /**
     * The constructor calls the init() method to initialise the JFrame, and
     * JPanels, this method also registers the relevant interface listeners and
     * calls the {@link BasketHandler#paintBasket()} method to display any
     * items in the basket.
     */
    public Application() {
        init();
    }   

    private void init() {

        layout = new BorderLayout();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(layout);
        setMinimumSize(new Dimension(1000, 750));

        addItems = new AddItemPanel();
        add(addItems, BorderLayout.WEST);

        details = new DetailsPanel();
        add(details, BorderLayout.EAST);

        basket = new BasketHandler();
        add(basket, BorderLayout.CENTER);
        basket.setAlignmentX(SwingConstants.CENTER);
        basket.setAlignmentY(SwingConstants.CENTER);

        title = new TitlePanel();
        add(title, BorderLayout.NORTH);

        features = new FeaturePanel();
        add(features, BorderLayout.SOUTH);

        features.registerBasketListener(basket);
        basket.registerDetailsListener(details);
        addItems.registerFormListener(this);
        basket.registerFormListener(this);

        setTitle("Furniture Orders");
        pack();
        setLocationRelativeTo(null);

        basket.paintBasket();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Application().setVisible(true);
            }
        });
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void addItemForm(FormType type) {
        
        if (basket.getBasketSize() == MAX_ITEMS) {
            JOptionPane.showMessageDialog(this, "Basket is full.");
            return;
        }
        switch (type) {
            case CHAIR:
                ChairForm chair = new ChairForm(basket.getLastId() + 1);
                this.remove(layout.getLayoutComponent(BorderLayout.CENTER));
                this.add(chair, BorderLayout.CENTER);
                chair.registerItemListener(this);
                break;
            case TABLE:
                TableForm table = new TableForm(basket.getLastId() + 1);
                this.remove(layout.getLayoutComponent(BorderLayout.CENTER));
                this.add(table, BorderLayout.CENTER);
                table.registerItemListener(this);
                break;
            case DESK:
                DeskForm desk = new DeskForm(basket.getLastId() + 1);
                this.remove(layout.getLayoutComponent(BorderLayout.CENTER));
                this.add(desk, BorderLayout.CENTER);
                desk.registerItemListener(this);
        }
        validate();
        repaint();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void replaceItemForm(JPanel form) {

        if (form instanceof ChairForm) {
            ChairForm chair = (ChairForm) form;
            this.remove(layout.getLayoutComponent(BorderLayout.CENTER));
            this.add(chair, BorderLayout.CENTER);
            chair.registerItemListener(this);

        } else if (form instanceof TableForm) {
            TableForm table = (TableForm) form;

            this.remove(layout.getLayoutComponent(BorderLayout.CENTER));
            this.add(table, BorderLayout.CENTER);
            table.registerItemListener(this);

        } else if (form instanceof DeskForm) {
            DeskForm desk = (DeskForm) form;
            this.remove(layout.getLayoutComponent(BorderLayout.CENTER));
            this.add(desk, BorderLayout.CENTER);
            desk.registerItemListener(this);
        }
        validate();
        repaint();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void cancelItem() {

        this.remove(layout.getLayoutComponent(BorderLayout.CENTER));
        this.add(basket);

        validate();
        repaint();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void addItem(Item item) {

        basket.addItem(item);

        this.remove(layout.getLayoutComponent(BorderLayout.CENTER));
        this.add(basket);

        validate();
        repaint();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void replaceItem(int index, Item item) {

        basket.replaceItem(index, item);

        this.remove(layout.getLayoutComponent(BorderLayout.CENTER));
        this.add(basket);

        validate();
        repaint();
    }
}
