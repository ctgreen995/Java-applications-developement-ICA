package Interface;

/**
 * This interface is used by the {@link GUI.BasketHandler} implementation to 
 * enable the intended functionality of the button clicks on the 
 * {@link GUI.FeaturePanel} JPanel.
 */
public interface BasketListener {

    /**
     * This method is called when the {@link GUI.FeaturePanel#summary} button is
     * clicked on the FeaturePanel, it outputs a summary of the items in the
     * {@link GUI.BasketHandler#basket} to console, in ascending order by price,
     * along with the total of the basket.
     *
     * @return a String of summary of the basket.
     */
    public String createSummary();

    /**
     * This method checks if the basket is empty, if it is empty an error
     * message is shown, if it is not, the basket is written to the database and
     * a confirmation message is shown.
     * 
     * @return returns true if basket saved, false if not.
     */
    public boolean saveBasket();

    /**
     * This method checks if there are unsaved items in the basket, if there are
     * then the user is prompted if they first want to save the current basket,
     * if the basket is empty, a previously saved basket from the database can
     * be loaded from the database.
     */
    public void loadBasket();

    /**
     * This method clears all items from the Basket, then removes the entries
     * from the database.
     */
    public void clearBasket();
}
