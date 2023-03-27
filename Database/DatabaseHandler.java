package Database;

import Data.Arms;
import Data.Base;
import Data.Basket;
import Data.Chair;
import Data.Desk;
import Data.Item;
import Data.Material;
import Data.Table;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * This database handler class creates the database for the application and
 * facilitates all connections and read, write or query interactions with the
 * database.
 */
public class DatabaseHandler {

    private static DatabaseHandler database;

    /**
     * This private constructor is a singleton used to instantiate a single
     * database class for the program, it also calls the {@link #createTables()}
     * method to create the tables in the database.
     */
    private DatabaseHandler() {
        createTables();
    }

    /**
     * This method is used to get the single instance of the database class.
     *
     * @return database is the database handler class for the program.
     */
    public static DatabaseHandler getInstance() {

        if (database == null) {
            database = new DatabaseHandler();
        }
        return database;
    }

    /**
     * This method creates and establishes a connection with the database.
     *
     * @return conn the connection to the database.
     */
    private Connection connect() {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:basket.sqlite3");
        } catch (SQLException ex) {

            Logger.getLogger(DatabaseHandler.class.getName()).log(
                    Level.SEVERE, null, ex);
        }

        return conn;
    }

    /**
     * This method creates the tables in the database, the Baskets table
     * contains the text names of the baskets, the Items table contains the
     * basket items, and the Basket_Items table contains foreign keys of the
     * basket names and the item id numbers, which make up a composite primary
     * key.
     */
    private void createTables() {

        String baskets = "CREATE TABLE IF NOT EXISTS Baskets (\n"
                + "basket_name TEXT PRIMARY KEY NOT NULL);";

        String basketItems = "CREATE TABLE IF NOT EXISTS Basket_Items (\n"
                + "basket_name TEXT NOT NULL,\n"
                + "item_id INTEGER,"
                + "PRIMARY KEY (basket_name, item_id)\n"
                + "FOREIGN KEY (basket_name) REFERENCES Baskets (basket_name),"
                + "FOREIGN KEY (item_id) REFERENCES Items (id))";

        String items = "CREATE TABLE IF NOT EXISTS Items (\n"
                + "id INTEGER PRIMARY KEY,\n"
                + "type TEXT NOT NULL,\n"
                + "material TEXT NOT NULL,\n"
                + "quantity INTEGER NOT NULL,\n"
                + "arms TEXT,\n"
                + "base TEXT,\n"
                + "diameter INTEGER,\n"
                + "drawers INTEGER,\n"
                + "width INTEGER,\n"
                + "depth INTEGER,\n"
                + "price REAL NOT NULL\n"
                + ");";

        try ( Connection conn = connect()) {
            Statement createBasket = conn.createStatement();
            createBasket.execute(baskets);

            Statement createItems = conn.createStatement();
            createItems.execute(items);

            Statement createBasketItems = conn.createStatement();
            createBasketItems.execute(basketItems);

        } catch (SQLException ex) {

            Logger.getLogger(DatabaseHandler.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is used to reload any items that remained in the basket,
     * without being saved, when the application was closed.
     *
     * @param basket is the basket which is to be populated with items.
     * @return basket the basket containing any items that were not saved.
     */
    public Basket reloadBasket(Basket basket) {

        String idString = "SELECT * FROM Items LEFT JOIN"
                + " Basket_Items ON Basket_Items.item_id = Items.id"
                + " WHERE NOT EXISTS (SELECT 1 FROM Basket_Items "
                + "WHERE Basket_Items.item_id = Items.id);";

        ArrayList<String[]> items = new ArrayList<>();

        try ( Connection conn = connect()) {
            PreparedStatement readIds = conn.prepareStatement(idString);

            ResultSet idResult = readIds.executeQuery();
            ResultSetMetaData rsmd = idResult.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while (idResult.next()) {

                String[] item = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {

                    item[i - 1] = idResult.getString(i);

                }
                items.add(item);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(
                    Level.SEVERE, null, ex);
        }

        for (String[] itemValues : items) {
            switch (itemValues[1]) {
                case "Chair":
                    Chair chair = new Chair(
                            Integer.valueOf(itemValues[0]),
                            Material.valueOf(itemValues[2]),
                            Integer.valueOf(itemValues[3]),
                            Arms.valueOf(itemValues[4]));
                    basket.add(chair);
                    break;
                case "Table":
                    Table table = new Table(
                            Integer.valueOf(itemValues[0]),
                            Material.valueOf(itemValues[2]),
                            Integer.valueOf(itemValues[3]),
                            Base.valueOf(itemValues[5]),
                            Integer.valueOf(itemValues[6]));
                    basket.add(table);
                    break;
                case "Desk":
                    Desk desk = new Desk(
                            Integer.valueOf(itemValues[0]),
                            Material.valueOf(itemValues[2]),
                            Integer.valueOf(itemValues[3]),
                            Integer.valueOf(itemValues[7]),
                            Integer.valueOf(itemValues[8]),
                            Integer.valueOf(itemValues[9]));
                    basket.add(desk);
                    break;
                default:
                    break;
            }
        }
        return basket;
    }

    /**
     * This method deletes any items from the items table in the database which
     * have not been saved to a basket, it is used when clearing the items from
     * the current basket.
     */
    public void cleanDatabase() {

        String idString = "DELETE FROM Items WHERE id NOT IN "
                + "(SELECT item_id FROM Basket_Items);";

        try ( Connection conn = connect()) {

            PreparedStatement deleteId = conn.prepareStatement(idString);

            deleteId.execute();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }

    /**
     * This method writes the current basket to the database when the save
     * basket button is clicked.
     *
     * The method first gets a name from the user to save the basket under via
     * the {@link #getUserBasketName(java.lang.String)} method. That name is 
     * then checked against basket names currently in the Baskets table in the 
     * database via the 
     * {@link #queryBasketNames(java.lang.String, java.sql.Connection)} 
     * method, if the name exists the user is asked if they wish to overwrite 
     * the basket, if so the basket is overwritten with the new basket via the
     * {@link #updateBasket(java.lang.String, Data.Basket, java.sql.Connection)}
     * method.
     *
     * When writing any basket to the database, the ID numbers of the items in
     * the basket are checked against the item ID numbers in the Basket_Items
     * table in the database via the {@link #queryBasketItems(
     * java.lang.String, Data.Basket, java.sql.Connection)} method, if the ID
     * numbers exist in the table then those items are already present in
     * another basket and the operation is illegal and an error message is
     * displayed and the save basket operation aborted.
     *
     * @param basket is the basket which is to be written to the database.
     * @return is a boolean, true if the basket was written to the database.
     */
    public boolean writeBasket(Basket basket) {

        String basketName = getUserBasketName(basket.getBasketName());

        if (basketName == null) {
            return false;
        }

        try ( Connection conn = connect()) {

            if (queryBasketNames(basketName, conn)) {
                if (JOptionPane.showConfirmDialog(
                        JOptionPane.getRootFrame(),
                        "Overwrite basket?",
                        "Basket Exists", JOptionPane.OK_CANCEL_OPTION)
                        == JOptionPane.OK_OPTION) {

                    if (queryBasketItems(basketName, basket, conn)) {
                        JOptionPane.showMessageDialog(
                                null,
                                "An item, or items, already present in another "
                                + "basket!",
                                "Error!",
                                JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    updateBasket(basketName, basket, conn);
                    return true;
                } else {
                    return false;
                }
            }
            if (queryBasketItems(basketName, basket, conn)) {
                JOptionPane.showMessageDialog(
                        null,
                        "An item, or items, already present in another "
                        + "basket!",
                        "Error!",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }

            String basketString = "INSERT INTO Baskets VALUES (?);";

            String basketItemsString = "INSERT INTO Basket_Items "
                    + "(basket_name, item_id) VALUES (?,?);";

            PreparedStatement insertBasketItems;

            conn.setAutoCommit(false);

            PreparedStatement insertBasket
                    = conn.prepareStatement(basketString);
            insertBasket.setString(1, basketName);
            insertBasket.execute();

            for (int i = 0; i < basket.size(); i++) {

                insertBasketItems = conn.prepareStatement(basketItemsString);
                insertBasketItems.setString(
                        1, String.valueOf(basketName));
                insertBasketItems.setInt(2, basket.get(i).getId());
                insertBasketItems.executeUpdate();
            }

            conn.commit();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return true;
    }

    /**
     * This method gets the name to assign to the basket, from the user.
     *
     * The method enforces a character count of between 1 and 25 characters.
     *
     * @return nameField.getText() the basket name as a String.
     */
    private String getUserBasketName(String basketName) {

        JTextField nameField = new JTextField();

        PlainDocument jTextLimit = new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a)
                    throws BadLocationException {

                if ((getLength() + str.length()) <= 20) {
                    super.insertString(offs, str, a);
                }
            }
        };
        if (basketName == null) {

            DefaultListModel<String> model = new DefaultListModel<>();
            JList<String> jNames = new JList<>(model);
            jNames.setVisibleRowCount(5);

            String basketNames = "SELECT * FROM Baskets";

            try ( Connection conn = connect()) {

                PreparedStatement readNames
                        = conn.prepareStatement(basketNames);
                ResultSet storedNames = readNames.executeQuery();

                while (storedNames.next()) {

                    model.addElement(storedNames.getString(1));
                }

            } catch (SQLException e) {
                Logger.getLogger(DatabaseHandler.class.getName())
                        .log(Level.SEVERE, null, e);
            }
            JScrollPane nameScroll = new JScrollPane(jNames);
            nameField.setDocument(jTextLimit);

            jNames.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        JList string = (JList) e.getSource();
                        nameField.setText(string.getSelectedValue().toString());
                    }
                }

            });

            while (nameField.getText().isBlank()) {

                int result = JOptionPane.showOptionDialog(null,
                        new Object[]{"Currently saved baskets",
                            nameScroll,
                            "Enter customer name, Max 20 characters",
                            nameField},
                        "Basket name",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, null, null);

                if (result != JOptionPane.OK_OPTION) {
                    return null;
                }

                if (nameField.getText().isBlank()) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                            "Cannot be empty.");
                }
            }
        } else {
            nameField.setText(basketName);
        }
        return nameField.getText();
    }

    /**
     * This method queries the Baskets table to check if it already contains the
     * basket name provided by the user.
     *
     * @param basketName basket name provided by the user.
     * @param conn the database connection created by the calling methods try
     * catch statement.
     * @return names.next() boolean, returns true if the name is present in the
     * Baskets table.
     * @throws SQLException sql exception caught in the calling methods catch
     * clause.
     */
    private boolean queryBasketNames(String basketName, Connection conn)
            throws SQLException {

        String queryNames = "SELECT Baskets.basket_name FROM Baskets WHERE "
                + "Baskets.basket_name = '" + basketName + "';";

        PreparedStatement getNames = conn.prepareStatement(queryNames);
        ResultSet names = getNames.executeQuery();
        return names.next();
    }

    /**
     * This method queries the Basket_Items table in the database to check if
     * the items in the current basket are present in any other baskets, will
     * return true if the is a match in another basket.
     *
     * @param name the name of the current basket.
     * @param basket the current basket to be saved.
     * @param conn the database connection created in the calling methods try
     * catch statement.
     * @return boolean, true if a match is found, false if no match found and
     * the basket can be saved.
     * @throws SQLException sql exception caught in the calling methods catch
     * clause.
     */
    private boolean queryBasketItems(
            String name, Basket basket, Connection conn) throws SQLException {

        String basketItemString = "SELECT * FROM Basket_Items;";

        PreparedStatement checkBasketItem
                = conn.prepareStatement(basketItemString);
        ResultSet basketItems = checkBasketItem.executeQuery();

        while (basketItems.next()) {
            for (Item item : basket) {
                if (basketItems.getInt(2) == item.getId()) {
                    if (!basketItems.getString(1).equals(name)) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    /**
     * This method overwrites a basket in the database by deleting basket_name
     * rows which match the basket name of the current basket from Basket_Items
     * table, then inserting the current basket name and item id numbers.
     *
     * @param name the name of the basket replacing the basket in the database.
     * @param basket the basket containing the items to be written to the
     * database.
     * @param conn the database connection created in the calling methods try
     * catch statement.
     * @throws SQLException sql exception caught in the calling methods catch
     * clause.
     */
    private void updateBasket(String name, Basket basket, Connection conn)
            throws SQLException {

        String deleteString = "DELETE FROM Basket_Items "
                + "WHERE basket_name = ?;";

        PreparedStatement deleteBasketItems
                = conn.prepareStatement(deleteString);

        deleteBasketItems.setString(1, name);
        deleteBasketItems.execute();

        String insertString = "INSERT INTO Basket_Items "
                + "VALUES (?,?);";

        PreparedStatement insertBasketItems;

        for (int i = 0; i < basket.size(); i++) {
            insertBasketItems = conn.prepareStatement(insertString);
            insertBasketItems.setString(1, name);
            insertBasketItems.setInt(2, basket.get(i).getId());
            insertBasketItems.execute();
        }

    }

    /**
     * This method writes the item which has been added to the basket to the
     * Items table in the database.
     *
     * @param item the Item which is to be written to the database.
     */
    public void writeItem(Item item) {

        String itemString = "INSERT INTO Items (id, type, material, quantity, "
                + "arms, base, diameter, drawers, width, depth, price) VALUES "
                + "(?,?,?,?,?,?,?,?,?,?,?);";

        try ( Connection conn = connect()) {
            PreparedStatement insertItem = conn.prepareStatement(itemString);

            conn.setAutoCommit(false);

            insertItem.setInt(1, item.getId());
            insertItem.setString(2, item.getType());
            insertItem.setString(3, String.valueOf(item.getMaterial()));
            insertItem.setInt(4, item.getQuantity());

            if (item instanceof Chair) {
                Chair chair = (Chair) item;
                insertItem.setString(5, String.valueOf(chair.getArms()));
                insertItem.setDouble(11, chair.getTotalPrice());
            } else if (item instanceof Table) {
                Table table = (Table) item;
                insertItem.setString(6, String.valueOf(table.getBase()));
                insertItem.setInt(7, table.getDiameter());
                insertItem.setDouble(11, table.getTotalPrice());
            } else {
                Desk desk = (Desk) item;
                insertItem.setInt(8, desk.getDrawers());
                insertItem.setInt(9, desk.getWidth());
                insertItem.setInt(10, desk.getDepth());
                insertItem.setDouble(11, desk.getTotalPrice());
            }
            insertItem.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method deletes an item from the database.
     *
     * @param item the Item which is to be deleted from the database.
     */
    public void deleteItem(Item item) {

        String removeString = "DELETE FROM Items WHERE id = "
                + item.getId() + ";";

        try ( Connection conn = connect()) {

            PreparedStatement removeItem = conn.prepareStatement(removeString);
            removeItem.execute();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }

    /**
     * This method updates an item in the Items table in the database.
     *
     * @param item the updated item to overwrite the item in the database.
     */
    public void updateItem(Item item) {

        String updateString = "UPDATE Items SET material = ?, quantity = ?, "
                + "arms = ?, base = ?, diameter = ?, drawers = ?, width = ?, "
                + "depth = ?, price = ? WHERE id = ?";

        try ( Connection conn = connect()) {
            PreparedStatement updateItem = conn.prepareStatement(updateString);
            conn.setAutoCommit(false);

            updateItem.setString(1, String.valueOf(item.getMaterial()));
            updateItem.setInt(2, item.getQuantity());
            updateItem.setDouble(9, item.getTotalPrice());
            updateItem.setInt(10, item.getId());

            if (item instanceof Chair) {

                Chair chair = (Chair) item;
                updateItem.setString(3, String.valueOf(chair.getArms()));

            } else if (item instanceof Table) {

                Table table = (Table) item;
                updateItem.setString(4, String.valueOf(table.getBase()));
                updateItem.setInt(5, table.getDiameter());

            } else {
                Desk desk = (Desk) item;
                updateItem.setInt(6, desk.getDrawers());
                updateItem.setInt(7, desk.getWidth());
                updateItem.setInt(8, desk.getDepth());
            }

            updateItem.execute();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method queries the Items table in the database to retrieve the id
     * number of the last item added to the database, used to generate the id
     * number when adding a new item to the basket.
     *
     * @return id the id number of the last item added to the Items table in the
     * database.
     */
    public int readLastId() {
        int id = 0;
        String idString = "SELECT id FROM Items WHERE id = (SELECT MAX(id)"
                + " FROM Items);";

        try ( Connection conn = connect()) {
            Statement getLastId = conn.createStatement();
            ResultSet lastId = getLastId.executeQuery(idString);
            if (lastId.next()) {
                id = lastId.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return id;
    }

    /**
     * This method retrieves a basket from the database, by first querying the
     * Baskets table for the saved basket names via the
     * {@link #readBasketNames()} method, if a name is selected the basket is
     * returned.
     *
     * @param basket is the basket to be populated with items.
     * @return basket, the basket loaded from the database.
     */
    public Basket readBasket(Basket basket) {

        String basketName = readBasketNames();

        String basketString = "SELECT id, type, material, quantity, arms, base,"
                + " diameter, drawers, width, depth, price FROM Items LEFT JOIN"
                + " Basket_Items ON Basket_Items.item_id = Items.id "
                + "WHERE Basket_Items.basket_name = '" + basketName + "';";

        ArrayList<String[]> items = new ArrayList<>();

        try ( Connection conn = connect()) {

            PreparedStatement readBasket = conn.prepareStatement(basketString);
            ResultSet basketResult = readBasket.executeQuery();

            ResultSetMetaData rsmd = basketResult.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while (basketResult.next()) {

                String[] item = new String[columnCount];

                for (int i = 1; i <= columnCount; i++) {

                    item[i - 1] = basketResult.getString(i);

                }
                items.add(item);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(
                    Level.SEVERE, null, ex);
        }

        for (String[] itemValues : items) {
            switch (itemValues[1]) {
                case "Chair":
                    Chair chair = new Chair(
                            Integer.valueOf(itemValues[0]),
                            Material.valueOf(itemValues[2]),
                            Integer.valueOf(itemValues[3]),
                            Arms.valueOf(itemValues[4]));
                    basket.add(chair);
                    break;
                case "Table":
                    Table table = new Table(
                            Integer.valueOf(itemValues[0]),
                            Material.valueOf(itemValues[2]),
                            Integer.valueOf(itemValues[3]),
                            Base.valueOf(itemValues[5]),
                            Integer.valueOf(itemValues[6]));
                    basket.add(table);
                    break;
                case "Desk":
                    Desk desk = new Desk(
                            Integer.valueOf(itemValues[0]),
                            Material.valueOf(itemValues[2]),
                            Integer.valueOf(itemValues[3]),
                            Integer.valueOf(itemValues[7]),
                            Integer.valueOf(itemValues[8]),
                            Integer.valueOf(itemValues[9]));
                    basket.add(desk);
                    break;
                default:
                    break;
            }
        }
        basket.setBasketName(basketName);
        return basket;
    }

    /**
     * This method reads the basket names form the Baskets table, if the user
     * selects a basket that name is returned, if no name is selected the method
     * returns null, if no baskets are present in the table a message is
     * displayed to the user stating there are no saved baskets.
     *
     * @return basketName the name of the basket selected by the user.
     */
    private String readBasketNames() {

        String basketName = null;

        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> jNames = new JList<>(model);
        jNames.setVisibleRowCount(5);

        String basketNames = "SELECT * FROM Baskets";

        try ( Connection conn = connect()) {

            PreparedStatement readNames = conn.prepareStatement(basketNames);
            ResultSet storedNames = readNames.executeQuery();

            while (storedNames.next()) {

                model.addElement(storedNames.getString(1));
            }

            if (model.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No saved baskets.");
                return null;
            }

            String[] options = {"OK", "Cancel"};
            JScrollPane nameScroll = new JScrollPane(jNames);
            int result = JOptionPane.showOptionDialog(
                    null,
                    nameScroll,
                    "Select a Basket",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (result != JOptionPane.OK_OPTION) {
                return null;
            }

            basketName = jNames.getSelectedValue();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return basketName;
    }
}
