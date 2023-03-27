package Data;

import javax.swing.ImageIcon;

/**
 * This Chair class is a subclass of the {@link Item} class and represents a
 * Chair furniture item.
 *
 * This class calculates the individual item price, the total price is
 * calculated in the super class via the {@link Item#getTotalPrice()} method.
 */
public class Chair extends Item {

    private final Arms arms;
    private final int UNITS = 1625;

    /**
     * The constructor invokes the super class constructor, assigns the values
     * to the instance variables and calls the {@link #getItemPrice()} method
     * which calculates the individual item price.
     *
     * @param id is the ID number of the item.
     * @param material is a {@link Material} enum constant representing what the
     * item is made from.
     * @param quantity is how many of this item there is.
     * @param arms is an {@link Arms} enum constant representing if this Chair
     * has arms or not, used for calculating the additional cost of a Chair with
     * arms.
     */
    public Chair(int id, Material material, int quantity,
            Arms arms) {
        super(id, material, quantity);
        TYPE = "Chair";
        image = new ImageIcon(
                new ImageIcon("resources/rimini-without-arms.png")
                        .getImage()
                        .getScaledInstance(
                                120, 120, java.awt.Image.SCALE_SMOOTH));
        this.arms = arms;
        getItemPrice();
    }

    /**
     * This method returns if a Chair has arms or not.
     *
     * @return arms is an {@link Arms} enum constant.
     */
    public Arms getArms() {
        return arms;
    }

    /**
     * This method calculates the individual item price for a Chair, given the
     * values of the relevant attributes.
     *
     * @return price is the cost of an individual Chair.
     */
    @Override
    public final double getItemPrice() {

        return price = Math.round(
                ((UNITS + arms.cost) * material.cost) * 100) / 100.0;

    }

    /**
     * This method overrides the class toString() and is used to display the
     * information for a completed Chair object.
     *
     * @return output.toString() is a String representing the Chairs attributes.
     */
    @Override
    public String toString() {

        StringBuilder output = new StringBuilder();
        output.append("Type: ").append(TYPE)
                .append("\nID Number: ").append(id)
                .append("\nMaterial: ").append(material)
                .append("\nArms: ").append(arms)
                .append("\nQuantity: ").append(quantity)
                .append("\nSub Total: ").append(df.format(price))
                .append("\nPrice: £").append(df.format(getTotalPrice()));
        return output.toString();
    }
}
