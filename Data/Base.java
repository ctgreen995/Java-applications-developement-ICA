package Data;

/**
 * This enum is used to determine which type of base a Table item has.
 */
public enum Base {

    WOOD(45.00), CHROME(35.00);

    /**
     * This cost variable is how much each type of base costs, to add to the
     * price of the a Table item.
     */
    public final double cost;

    private Base(double cost) {
        this.cost = cost;
    }
}
