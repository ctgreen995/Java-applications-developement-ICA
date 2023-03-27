package Interface;

import Data.Item;

/**
 * This interface is implemented by the {@link GUI.Application} class and used 
 * to enable items to be added to the basket, or updated in the basket, when the
 * relevant item form has been completed and the add, update, or cancel button,
 * has been clicked.
 */
public interface ItemListener {

    /**
     * This method removes the furniture item form from the centre panel and
     * adds the basket grid, used when cancelling the adding or updating an item
     * to basket process by the user clicking the cancel button.
     */
    public void cancelItem();

    /**
     * This method adds the item to the basket when the calling class, the
     * relevant item form, has been completed, it then removes the furniture
     * item form from the centre panel and adds the basket grid.
     *
     * @param item the item to be added to the basket
     */
    public void addItem(Item item);

    /**
     * This method is used when the furniture item form has been updated, it
     * replaces the item in the basket, removes the item form and adds the
     * basket grid to the centre panel.
     *
     * @param index the basket index of the item to be replaced.
     * @param item the item to replace the item at the index.
     */
    public void replaceItem(int index, Item item);
}
