package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * This class is a JPanel which displays the company name as a title for the
 * application.
 *
 */
public class TitlePanel extends JPanel {

    private JLabel title;

    /**
     * The constructor calls the init() to initialise this JPanel and its
     * JComponents.
     */
    public TitlePanel() {
        init();
    }

    private void init() {

        setPreferredSize(new Dimension(500, 100));
        setBorder(BorderFactory
                .createLineBorder(new java.awt.Color(0, 0, 0), 2));
        setBackground(new Color(150, 170, 225));

        title = new JLabel("Real Office Furniture Co");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setPreferredSize(new Dimension(1000, 100));
        title.setFont(new Font("Serif", Font.PLAIN, 50));
        this.add(title);
    }
}
