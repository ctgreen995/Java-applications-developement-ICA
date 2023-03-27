package Data;

/**
 * This enum is used to signify if a {@link Chair} item requires arms or not
 * adds the extra units to be calculated into the cost of the Chair.
 */
public enum Arms {

    YES(250), NO(0);

    /**
     * This cost int variable is the extra units of wood it costs to add arms 
     * to a Chair item.
     */
    public final int cost;

    private Arms(int cost) {
        this.cost = cost;
    }
}
