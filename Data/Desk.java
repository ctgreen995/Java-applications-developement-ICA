package Data;

import javax.swing.ImageIcon;

/**
 * This Desk class is a subclass of the {@link Item} class and represents a Desk
 * furniture item.
 */
public class Desk extends Item {

    private final int drawers;
    private final int width;
    private final int depth;
    private final int HEIGHT = 80;
    private final double DRAWERPRICE = 8.50;

    /**
     * The constructor invokes the super class constructor, assigns the values
     * to the instance variables and calls the {@link #getItemPrice()} method
     * which calculates the individual item price.
     *
     * @param id is the ID number of the item.
     * @param material is a {@link Material} enum constant representing what the
     * item is made from.
     * @param quantity is how many of this item there is.
     * @param drawers is how many drawers this item has, used for calculating
     * the additional cost for the amount of drawers the Desk has.
     * @param width is how many cm wide the desk is.
     * @param depth is how many cm deep the desk is.
     */
    public Desk(int id, Material material, int quantity,
            int drawers, int width, int depth) {
        super(id, material, quantity);
        TYPE = "Desk";
        this.drawers = drawers;
        this.width = width;
        this.depth = depth;
        image = new ImageIcon(
                new ImageIcon("resources/desk.png")
                        .getImage()
                        .getScaledInstance(
                                120, 120, java.awt.Image.SCALE_SMOOTH));
        getItemPrice();
    }

    /**
     * This method returns the number of drawers the desk has.
     *
     * @return drawers is the number of drawers the desk has.
     */
    public int getDrawers() {
        return drawers;
    }

    /**
     * This method returns the width of the desk.
     *
     * @return width is the width of the desk.
     */
    public int getWidth() {
        return width;
    }

    /**
     * this method returns the depth of the desk.
     *
     * @return depth is the depth of the desk.
     */
    public int getDepth() {
        return depth;
    }

    /**
     * This method calculates the individual item price for a Desk, given the
     * values of the relevant attributes.
     *
     * @return price is the cost of an individual Desk.
     */
    @Override
    public final double getItemPrice() {

        return price = Math.round(
                ((((((HEIGHT + width + depth) * 12) + (depth * width))
                * material.cost)) + (drawers * DRAWERPRICE)) * 100) / 100.0;
    }

    /**
     * This method overrides the class toString() and is used to display the
     * information for a completed Desk object.
     *
     * @return output.toString() is a String representing the Desk's attributes.
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("Type: ").append(TYPE)
                .append("\nID Number: ").append(id)
                .append("\nMaterial: ").append(material)
                .append("\nWidth: ").append(width).append("cm")
                .append("\nDepth: ").append(depth).append("cm")
                .append("\nDrawers: ").append(drawers)
                .append("\nQuantity: ").append(quantity)
                .append("\nSub Total: £").append(df.format(price))
                .append("\nPrice: £").append(df.format(getTotalPrice()));
        return output.toString();
    }
}
