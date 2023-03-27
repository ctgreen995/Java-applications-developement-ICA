package Interface;

import javax.swing.JPanel;

/**
 * This interface is used by the {@link GUI.Application} implementation to 
 * enable the generating of the relevant item forms in the centre panel of the
 * Application JPanel, the interface is used differently when adding an item or
 * replacing an item in the basket.
 *
 * When adding a new item to the basket the calling class is the
 * {@link GUI.AddItemPanel} which does not have access to the basket, 
 * necessitating the Application class to instantiate the item form, allowing 
 * the item ID number to be pre-filled on the form, therefore a {@link FormType}
 * enum constant is passed as an argument to the
 * {@link #addItemForm(Interface.FormListener.FormType)} method signifying which
 * item form is to be instantiated.
 *
 * When updating an item in the basket the calling class is
 * {@link GUI.BasketHandler}, which has access to the item details to be 
 * updated in the basket, therefore the relevant item form is instantiated in 
 * the BasketHandler class and passed as an argument to the
 * {@link #replaceItemForm(javax.swing.JPanel)} method.
 *
 * @see GUI.ChairForm
 * @see GUI.DeskForm
 * @see GUI.TableForm
 */
public interface FormListener {

    /**
     * This enum is used to represent which item form is required to be used
     * when adding a new item to the basket.
     */
    enum FormType {

        CHAIR, TABLE, DESK;
    }

    /**
     * This constant is the maximum number of items which can be present in the
     * basket at any one time.
     */
    int MAX_ITEMS = 9;

    /**
     * Receives the type of form from the AddItemPanel class and adds the
     * relevant form to the application window centre panel when adding a new
     * item to basket.
     *
     * @param type FormType Enum constant used to identify which form is to be
     * added to the application window centre panel.
     */
    public void addItemForm(FormType type);

    /**
     * Receives a furniture item form from the BasketHandler class and adds it
     * to the application window centre panel, used for updating an item in the
     * basket.
     *
     * @param form the furniture item form which is pre-filled with the details
     * of the item that is to be updated in the basket.
     */
    public void replaceItemForm(JPanel form);
}
