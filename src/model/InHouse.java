package model;

/** Models an in-house part.
 *
 * @author Stephanie DelBono
 */
public class InHouse extends Part{

/** The machine ID for the part. */
    private int machineId;

    /** Constructor for a new instance of an in house object.
     * @param id the ID for the part.
     * @param name the name for the part.
     * @param stock the inventory level of the part.
     * @param price the price of the part.
     * @param min the minimum for the part.
     * @param max the maximum for the part.
     * @param machineId the machine ID for the part.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** Getter for the Machine ID.
     *
     * @return machine id of the part.
     */
    public int getMachineId() {
        return machineId;
        }

    /** Setter for the ID.
     *
     * @param machineId the machine id of the part.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    }


