package Data;

import java.text.DecimalFormat;
import javax.swing.ImageIcon;

/**
 * This Item class is an abstract class used to provide the attributes and
 * functionality pertinent to all furniture items.
 */
public abstract class Item {

    protected DecimalFormat df = new DecimalFormat("0.00");
    protected String TYPE;
    protected int id;
    protected Material material;
    protected double price = 0;
    protected int quantity;
    protected ImageIcon image;

    /**
     * The constructor is called by all subclasses to assign values to the
     * attributes which all furniture items require.
     *
     * @param id is the ID number of the item.
     * @param material is a {@link Material} enum constant representing what the
     * item is made from.
     * @param quantity is how many of this item there is.
     */
    public Item(int id, Material material, int quantity) {
        this.id = id;
        this.material = material;
        this.quantity = quantity;
    }

    /**
     * This method returns the type of furniture the item is.
     *
     * @return TYPE is a String representing what type of furniture the item is.
     */
    public String getType() {
        return TYPE;
    }

    /**
     * This method returns the individual price for an item.
     *
     * @return price is the cost of an individual item.
     */
    public double getItemPrice() {
        return price;
    }

    /**
     * This method returns the total price for an item based on the price of an
     * individual item multiplied by the quantity specified.
     *
     * @return the total price of the items.
     */
    public double getTotalPrice() {
        return price * quantity;
    }

    /**
     * This method returns the ID number of the item.
     *
     * @return id is the ID number of the item.
     */
    public int getId() {
        return id;
    }

    /**
     * This method returns the quantity of the individual item.
     *
     * @return quantity is the quantity of the individual item.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * This method returns the material of the item.
     *
     * @return material is a {@link Material} enum constant.
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * This method returns the image representation of the item.
     *
     * @return image is the image representation of the item.
     */
    public ImageIcon getImage() {
        return image;
    }
}
