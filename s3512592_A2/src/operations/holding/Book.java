package operations.holding;

import helper.DateTime;

/**
 * The book class
 */
public class Book extends Holding {

    /**
     * Constructor
     */
    public Book(String id, String title) {
        super(id, title);
    }

    /**
     * Calculate late fee of a book
     */
    public void calculateLateFee(DateTime returnDate) {
        int duration = DateTime.diffDays(returnDate, this.getBorrowDate());
        if (duration <= this.getMaxLoanPeriod()) {
            this.setLateFee(0);
        } else {
            int late = (duration - this.getMaxLoanPeriod()) * 2;
            this.setLateFee(late);
        }
    }

}
