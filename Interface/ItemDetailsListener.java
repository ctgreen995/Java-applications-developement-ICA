package Interface;

/**
 * This interface is used to enable the implementing {@link GUI.DetailsPanel} 
 * class to receive item and basket details, form the {@link GUI.BasketHandler} 
 * class to be displayed on the DetailsPanel JPanel.
 */
public interface ItemDetailsListener {

    /**
     * This method is called when an item in the basket is left clicked, the
     * items toString() method is then displayed on a JLabel.
     *
     * @param details the items toString() which is to be displayed.
     * @see GUI.BasketHandler#mouseHandler
     */
    public void showDetails(String details);

    /**
     * This method displays a running total of all the basket items when an item
     * is added, removed or updated in the basket.
     *
     * @param total the total of all items in the basket.
     * @param basketName is the name of the basket.
     */
    public void showTotal(double total, String basketName);
}
