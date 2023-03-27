package GUI;

import Interface.ItemDetailsListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * This JPanel displays the details of items in the basket when they are left
 * clicked and displays a running total of the basket when items are added,
 * removed or updated.
 */
public class DetailsPanel extends JPanel implements ItemDetailsListener {

    private JPanel titlePanel;
    private JLabel titleLabel;
    private JPanel detailsPanel;
    private JLabel detailsLabel;
    private JPanel totalTitlePanel;
    private JPanel totalDetailsPanel;
    private JLabel totalTitleLabel;
    private JLabel basketNameLabel;
    private JLabel totalLabel;

    /**
     * The constructor calls the init() method which initialises the JComponents
     * on the panel.
     */
    public DetailsPanel() {
        init();
    }

    private void init() {

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;

        setLayout(layout);
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 2));

        c.gridy = 0;
        titlePanel = new JPanel();
        titlePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        add(titlePanel, c);

        titleLabel = new JLabel();
        titleLabel.setText("<HTML>Item Details</HTML>");
        titleLabel.setFont(new java.awt.Font("Nimbus Sans L", 1, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLabel);

        c.gridy = 1;
        detailsPanel = new JPanel();
        detailsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        detailsPanel.setMinimumSize(new Dimension(200, 300));
        add(detailsPanel, c);

        detailsLabel = new JLabel();
        detailsLabel.setFont(new java.awt.Font("Nimbus Sans L", 1, 18));
        detailsLabel.setPreferredSize(new Dimension(200, 300));
        detailsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        detailsLabel.setVerticalAlignment(SwingConstants.CENTER);
        detailsPanel.add(detailsLabel);

        c.gridy = 2;
        totalTitlePanel = new JPanel();
        totalTitlePanel.setLayout(new GridBagLayout());
        GridBagConstraints cB = new GridBagConstraints();
        
        totalTitlePanel.setBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2));
        add(totalTitlePanel, c);
        cB.gridy = 0;
        basketNameLabel = new JLabel();
        basketNameLabel.setFont(new java.awt.Font("Nimbus Sans L", 1, 24));
        basketNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        basketNameLabel.setVerticalAlignment(SwingConstants.CENTER);
        basketNameLabel.setVerticalAlignment(JLabel.TOP);
        totalTitlePanel.add(basketNameLabel, cB);
        
        cB.gridy = 1;
        totalTitleLabel = new JLabel();
        totalTitleLabel.setVerticalAlignment(JLabel.BOTTOM);
        totalTitleLabel.setText("<HTML>Basket Total</HTML>");
        totalTitleLabel.setFont(new java.awt.Font("Nimbus Sans L", 1, 24));
        totalTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalTitleLabel.setVerticalAlignment(SwingConstants.CENTER);
        totalTitlePanel.add(totalTitleLabel, cB);

        c.gridy = 3;
        totalDetailsPanel = new JPanel();
        totalDetailsPanel.setBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2));
        add(totalDetailsPanel, c);

        totalLabel = new JLabel();
        totalLabel.setFont(new java.awt.Font("Nimbus Sans L", 1, 28));
        totalLabel.setPreferredSize(new Dimension(200, 70));
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalLabel.setVerticalAlignment(SwingConstants.CENTER);
        totalDetailsPanel.add(totalLabel);
    }

    /**
     * {@inheritDoc}.
     *
     * The panel will maintain focus until elsewhere in the application is
     * clicked. The escape characters in the String are replaced with html
     * elements to enable them to be displayed correctly in the UI.
     */
    @Override
    public void showDetails(String details) {

        details = "<html>" + details + "</html>";
        details = details.replaceAll("\n", "<br>");

        detailsLabel.setText(details);

        this.requestFocus();

        this.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent e) {

                detailsLabel.setText(null);
                revalidate();
                repaint();
            }
        });
    }

    /**
     * {@inheritDoc}.
     *
     * @see BasketHandler#paintBasket() updates this panel with the basket
     * total.
     */
    @Override
    public void showTotal(double total, String basketName) {

        if (basketName != null) {
            basketNameLabel.setText("<HTML>" + basketName + "<HTML>");
            basketNameLabel.setVisible(true);
            totalLabel.setText("£" + String.format("%.2f", total));

            
        } else {
            basketNameLabel.setVisible(false);
            totalLabel.setText("£" + String.format("%.2f", total));
        }
    }
}
