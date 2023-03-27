package Data;

/**
 * This enum is used to specify what material the item is to be made of and the
 * cost associated with that material type.
 */
public enum Material {

    OAK(0.04), WALNUT(0.03);

    /**
     * This cost variable represents how much the unit cost is for each material
     * type.
     */
    public final double cost;

    private Material(final double cost) {
        this.cost = cost;
    }
}
