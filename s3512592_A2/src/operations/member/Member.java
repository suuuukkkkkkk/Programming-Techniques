package operations.member;

import helper.DateTime;
import operations.SystemOperations;
import operations.exceptions.InsufficientCreditException;
import operations.holding.Holding;

import java.util.ArrayList;

/**
 * The member class needs to be inherited
 */
abstract public class Member implements SystemOperations {
    protected String mid; // the member id
    protected String name; // the member name
    protected int credit; // the credit left
    protected int maxCredit; // max credit value
    protected ArrayList<Holding> holdings; // current borrowings on hold

    /**
     * The constructor
     */
    public Member(String memberID, String fullName, int credit) {
        this.mid = memberID;
        this.name = fullName;
        this.maxCredit = credit;
        this.credit = maxCredit;
    }

    /**
     * Used to get the member ID
     */
    public String getID() {
        return mid;
    }

    /**
     * Used to set the member ID
     */
    public void setID(String mid) {
        this.mid = mid;
    }

    /**
     * Get the full name of the member
     */
    public String getFullName() {
        return name;
    }

    /**
     * Set the full name of the member
     */
    public void setFullName(String name) {
        this.name = name;
    }


    public boolean getStatus() {
        if (this.credit <= 0)
            return false;

        return true;
    }

    /**
     * Set the credit
     */
    public boolean setStatus() {
        return false;
    }

    /**
     * Get the credit
     */
    public int getCredit() {
        return credit;
    }

    /**
     * Set the credit
     */
    public void setCredit(int credit) {
        this.credit = credit;
    }

    /**
     * Get the max credit of the member
     */
    public int getMaxCredit() {
        return maxCredit;
    }

    /**
     * Get the current holdings on hand
     */
    public ArrayList<Holding> getHoldings() {
        return holdings;
    }

    /**
     * Set the holdings on hand
     */
    public void setHoldings(ArrayList<Holding> holdings) {
        this.holdings = holdings;
    }

    /**
     * Update the remaining credit
     */
    public boolean updateRemainingCredit (int loanFee) {
        if (this.credit <= 0)
            return false;

        return true;
    }

    /**
     * Don't understand what it does
     */
    public boolean checkAllowedCreditOverdraw(int loanFee) {
        return false;
    }

    /**
     * To test whether it can borrow a holding
     */
    public boolean borrowHolding (Holding holding) {
        try {
            if (holding.getStatus()) {
                if (this.credit < holding.getDefaultLoanFee())
                    throw new InsufficientCreditException();
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    /**
     * Print the record
     */
    public void print() {
        System.out.format("ID:         %s%n", this.getID());
        System.out.format("Title:      %s%n", this.getFullName());
        System.out.format("Max Credit: %s%n", this.getMaxCredit());
        System.out.println("");
    }

    public boolean active() {
        return true;
    }

    public boolean deactive() {
        return false;
    }

    // the children classes need to override
    abstract public int calculateRemainingCredit();

    // reset the credit to the max
    abstract public void resetCredit();

    // return the holding
    abstract public boolean returnHolding(Holding holding, DateTime returnDate) throws InsufficientCreditException;

    /**
     * To string method
     */
    public String toString() {
        //member_id:full_name:remaining_credit
        return this.getID() + ":" + this.getFullName() + ":" + this.getCredit();
    }
}
