package model;

/** Models an outsourced part.
 *
 * @author Stephanie DelBono
 */
public class Outsourced extends Part {

    /** The machine ID for the part. */
    private String companyName;

    /** Constructor for a new instance of an outsourced object.
     * @param id the ID for the part.
     * @param name the name for the part.
     * @param stock the inventory level of the part.
     * @param price the price of the part.
     * @param min the minimum for the part.
     * @param max the maximum for the part.
     * @param companyName the company name for the part.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** Getter for the Company Name.
     *
     * @return company name for the part.
     */
    public String getCompanyName() {
        return companyName;
    }

    /** Setter for the Company name.
     *
     * @param companyName the company name for the part.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }




}
