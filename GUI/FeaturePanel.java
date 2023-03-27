package GUI;

import Interface.BasketListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * The panel contains the JButtons used to perform save and load basket
 * functions, generate a basket summary and to clear all current items from the
 * basket.
 */
public class FeaturePanel extends JPanel implements ActionListener {

    private JButton summary;
    private JButton save;
    private JButton load;
    private JButton clear;
    private BasketListener listener;

    /**
     * The constructor calls the init() to initialise the panel and JComponents.
     */
    public FeaturePanel() {
        init();
    }

    private void init() {

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setPreferredSize(new Dimension(1000, 100));
        setAlignmentX(SwingConstants.CENTER);
        setBorder(BorderFactory
                .createLineBorder(new java.awt.Color(0, 0, 0), 2));
        setBackground(new Color(150, 170, 225));

        add(Box.createVerticalStrut(50));

        summary = new JButton("Summary");
        summary.setPreferredSize(new Dimension(150, 100));
        add(summary);
        summary.addActionListener(this);
        summary.setBackground(new Color(255, 255, 255));
        summary.setOpaque(true);

        add(Box.createVerticalStrut(100));

        save = new JButton("Save Basket");
        save.setPreferredSize(new Dimension(150, 100));
        add(save);
        save.addActionListener(this);
        save.setBackground(new Color(255, 255, 255));
        save.setOpaque(true);

        add(Box.createVerticalStrut(50));

        load = new JButton("Load Basket");
        load.setPreferredSize(new Dimension(150, 100));
        add(load);
        load.addActionListener(this);
        load.setBackground(new Color(255, 255, 255));
        load.setOpaque(true);

        add(Box.createVerticalStrut(50));

        clear = new JButton("Clear Basket");
        clear.setPreferredSize(new Dimension(150, 100));
        add(clear);
        clear.addActionListener(this);
        clear.setBackground(new Color(255, 255, 255));
        clear.setOpaque(true);

        add(Box.createVerticalStrut(50));
    }

    /**
     * This method handles the button clicks on the panel, it calls the relevant
     * methods in the {@link BasketHandler} class.
     *
     * @param e the button click which has been generated.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {

            listener.saveBasket();

        } else if (e.getSource() == load) {

            listener.loadBasket();

        } else if (e.getSource() == clear) {

            listener.clearBasket();

        } else if (e.getSource() == summary) {

            System.out.print(listener.createSummary());

        }
    }

    /**
     * Registers the {@link BasketHandler} class, which implements the
     * {@link BasketListener} interface, to receives updates of the button
     * clicks on this JPanel.
     *
     * @param listener the BasketHandler class which implements BasketListener.
     */
    public void registerBasketListener(BasketListener listener) {
        this.listener = listener;
    }
}
