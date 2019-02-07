package operations.holding;

import helper.DateTime;
import operations.SystemOperations;
import operations.exceptions.ItemInactiveException;
import operations.exceptions.OnLoanException;

/**
 * The Holding Class used to be inherited
 */
abstract public class Holding implements SystemOperations {

    // children class could directly access and modify the attributes
    protected String id; // the ID
    protected String title; // the title of the holding
    protected int defaultLoanFee; // default loan fee
    protected int maxLoanPeriod; // max loan period
    protected int lateFee; // by default is 0, but it applies when it is returned late
    protected boolean removed; // true if it's removed from system
    protected boolean active;  // true if it is active
    protected boolean onLoan;  // true if it is currently being borrowed by someone
    protected DateTime borrowDate; // the date when it is borrowed
    protected DateTime returnDate; // the date when it is returned after

    /**
     * The constructor
     */
    public Holding (String id, String title) {
        this.id = id;
        this.title = title;
        this.lateFee = 0;
        this.removed = false;
        this.active = true;
        this.onLoan = false;
        this.lateFee = 0;
    }

    /**
     * Used to get the ID of the holding
     */
    public String getID() {
        return id;
    }

    /**
     * Used to set the ID of the holding
     */
    public void setID(String id) {
        this.id = id;
    }

    /**
     * Used to get holding's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Used to set the holding's title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Used to get default loan fee of the holding
     */
    public int getDefaultLoanFee() {
        return defaultLoanFee;
    }

    /**
     * Used to set default loan fee of the holding
     */
    public void setDefaultLoanFee(int defaultLoanFee) {
        this.defaultLoanFee = defaultLoanFee;
    }

    /**
     * Used to get the max loan period
     */
    public int getMaxLoanPeriod() {
        return maxLoanPeriod;
    }

    /**
     * Used to set the max loan period
     */
    public void setMaxLoanPeriod(int maxLoanPeriod) {
        this.maxLoanPeriod = maxLoanPeriod;
    }

    /**
     * Used to get the late fee
     */
    public int getLateFee() {
        return lateFee;
    }

    /**
     * Used to set the late fee
     */
    public void setLateFee(int lateFee) {
        this.lateFee = lateFee;
    }

    /**
     * To get whether it is removed or not
     */
    public boolean isRemoved() {
        return removed;
    }

    /**
     * To set whether it is removed or not
     */
    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    /**
     * To get it active status
     */
    public boolean isActive() {
        return active;
    }

    /**
     * To set the active status
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * To test whether the holding can be borrowed
     * Yes if it is not on loan, is active and not removed
     */
    public boolean getStatus() throws ItemInactiveException, OnLoanException{
        if (this.isOnLoan())
            throw new OnLoanException();
        else if (!this.isActive())
            throw new ItemInactiveException();
        if (this.isRemoved())
            return false;

        return true;
    }

    /**
     * To get the on loan status
     */
    public boolean isOnLoan() {
        return onLoan;
    }

    /**
     * To set the on loan status
     */
    public void setOnLoan(boolean onLoan) {
        this.onLoan = onLoan;
    }

    /**
     * To get the borrow date
     */
    public DateTime getBorrowDate() {
        return borrowDate;
    }

    /**
     * To set the borrow date
     */
    public void setBorrowDate(DateTime borrowDate) {
        this.borrowDate = borrowDate;
    }

    /**
     * To get the returning date
     */
    public DateTime getReturnDate() {
        return returnDate;
    }

    /**
     * To set the return date
     */
    public void setReturnDate(DateTime returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * To test if it can be borrowed
     */
    public boolean borrowHolding() throws ItemInactiveException, OnLoanException {
        if (this.getStatus())
            return true;
        else
            return false;
    }

    /**
     * To test whether it can be returned
     */
    public boolean returnHolding(DateTime dateReturned) {
        int diff = DateTime.diffDays(dateReturned, this.getBorrowDate());
        if (diff >= 0 && this.isActive() && this.isOnLoan())
            return true;

        return false;
    }

    /**
     * To print the record
     */
    public void print() {
        System.out.format("ID:              %s%n", this.getID());
        System.out.format("Title:           %s%n", this.getTitle());
        System.out.format("Loan Fee:        %s%n", this.getDefaultLoanFee());
        System.out.format("Max Loan Period: %s%n", this.getMaxLoanPeriod());
        System.out.println("");
    }

    /**
     * To String method
     */
    public String toString() {
        return this.getID() + ":" + this.getTitle() + ":" + this.getDefaultLoanFee() + ":" + this.getMaxLoanPeriod();
    }

    public boolean deactive() {
        this.setRemoved(true);
        this.setActive(false);
        this.setOnLoan(false);
        return true;
    }

    public boolean active() {
        this.setRemoved(false);
        this.setActive(true);
        this.setOnLoan(false);
        return true;
    }

    
    abstract public void calculateLateFee(DateTime returnDate);

//    public String createId() {
//        String Digit = "0123456789";
//        Random random = new Random();
//
//        String id = "";
//        int nId = 0;
//        for(int i = 0; i < 6; i++) {
//            char digit = Digit.charAt(random.nextInt(Digit.length()));
//            id += String.valueOf(digit);
//            nId += digit;
//        }
//        return id + String.valueOf(nId%10);
//
//    }

}
