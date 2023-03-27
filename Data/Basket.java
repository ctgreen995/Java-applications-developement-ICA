package Data;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * This Basket ArrayList acts as a shopping basket for furniture Items.
 */
public class Basket extends ArrayList<Item> {

    private static Basket basket;
    private String basketName = null;
    
    /**
     * The default constructor, this is a singleton design pattern and 
     * restricts the program to one instance of a basket.
     */
    private Basket() {
    }
    
    /**
     * This method returns the single instance of the basket for the program.
     * 
     * @return basket is the single instance of the basket for the program.
     */
    public static Basket getInstance() {
        if(basket == null) {
            basket = new Basket();
        }
        return basket;
    }

    /**
     * This method gets the basket name, used to pre-fill the basket name when 
     * saving.
     * 
     * @return basketName is the name of the basket.
     */
    public String getBasketName() {
        return basketName;
    }

    /**
     * Sets the basket name, used to pre-fill the basket name when loading a 
     * basket.
     * 
     * @param basketName is the name of the basket.
     */
    public void setBasketName(String basketName) {
        this.basketName = basketName;
    }

    /**
     * This method returns the total price for all items in the basket.
     *
     * @return total is the total price of all items in the basket.
     */
    public double getTotal() {

        double total = 0;
        for (Item item : this) {
            total += item.getTotalPrice();
        }
        return total;
    }

    /**
     * This method overrides the toString method for the class and sorts the
     * Items in the basket into ascending order by prices and then returns a
     * StringBuilder toString() which can be output to console in a summary.
     *
     * @return output.toString() is the basket item information in ascending
     * order by total price.
     */
    @Override
    public String toString() {

        ArrayList<Item> sortedBasket = new ArrayList<>();

        for (Item item : this) {
            sortedBasket.add(item);
        }
        sortedBasket.sort(Comparator.comparingDouble(Item::getTotalPrice));

        StringBuilder output = new StringBuilder();
        
        if(getBasketName() != null) {
            output.append("Basket Name: ").append(basketName);
        }

        output.append("\n\nPrice in ascending order\n\n");

        int i = 0;
        for (Item item : sortedBasket) {

            output.append("Item: ").append(i + 1).append("\nID Number: ")
                    .append(item.getId()).append("\nType: ")
                    .append(item.getClass().getSimpleName())
                    .append("\nItem Price: £")
                    .append(item.getItemPrice())
                    .append("\nSubTotal: £")
                    .append(item.getTotalPrice()).append("\n\n");
            i++;
        }
        output.append("\nBasket Total: £").append(getTotal());
        return output.toString();
    }
}
