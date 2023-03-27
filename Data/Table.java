package Data;

import javax.swing.ImageIcon;

/**
 * This Table class is a subclass of the {@link Item} class and represents a
 * Table furniture item.
 *
 * This class calculates the individual item price, the total price is
 * calculated in the super class via the {@link Item#getTotalPrice()} method.
 */
public class Table extends Item {

    private final Base base;
    private final int units;
    private final int diameter;

    /**
     * The constructor invokes the super class constructor, assigns the values
     * to the instance variables and calls the {@link #getItemPrice()} method
     * which calculates the individual item price.
     *
     * @param id is the ID number of the item.
     * @param material is a {@link Material} enum constant representing what the
     * item is made from.
     * @param quantity is how many of this item there is.
     * @param base is the {@link Base} enum constant representing the type of
     * base the table has.
     * @param diameter is the int diameter of the table top.
     */
    public Table(int id, Material material, int quantity,
            Base base, int diameter) {
        super(id, material, quantity);
        TYPE = "Table";
        this.base = base;
        this.diameter = diameter;
        units = diameter * diameter;
        image = new ImageIcon(
                new ImageIcon("resources/wood-table.png")
                        .getImage().getScaledInstance(
                                120, 120, java.awt.Image.SCALE_SMOOTH));
        getItemPrice();
    }

    /**
     * This method returns the diameter specified for the Table.
     *
     * @return diameter is the int diameter of the Table top.
     */
    public int getDiameter() {
        return diameter;
    }

    /**
     * This method returns the type of Base the Table has.
     *
     * @return base is the {@link Base} enum constant representing the type of
     * base the Table has.
     */
    public Base getBase() {
        return base;
    }

    /**
     * This method calculates the individual item price for a Table, given the
     * values of the relevant attributes.
     *
     * @return price is the cost of an individual Table.
     */
    @Override
    public final double getItemPrice() {
        return price = Math.round(
                ((units * material.cost) + base.cost) * 100) / 100.0;
    }

    /**
     * This method overrides the class toString() and is used to display the
     * information for a completed Table object.
     *
     * @return output.toString() is a String representing the Tables attributes.
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("Type: ").append(TYPE)
                .append("\nID Number: ").append(id)
                .append("\nMaterial: ").append(material)
                .append("\nDiameter: ").append(diameter).append("cm")
                .append("\nBase: ").append(base)
                .append("\nQuantity: ").append(quantity)
                .append("\nSub Total: £").append(df.format(price))
                .append("\nPrice: £").append(df.format(getTotalPrice()));
        return output.toString();
    }
}
