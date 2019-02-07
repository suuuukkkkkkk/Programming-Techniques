package operations.holding;

import helper.DateTime;


public class Video extends Holding{

    public Video (String holdingId, String title, int loanFee) {
        super(holdingId, title);
        this.defaultLoanFee = loanFee;
    }

    public void calculateLateFee(DateTime returnDate) {
        int duration = DateTime.diffDays(returnDate, this.getBorrowDate());
        if (duration <= this.getMaxLoanPeriod()) {
            this.setLateFee(0);
        } else {
            int late = (duration - this.getMaxLoanPeriod()) * this.getDefaultLoanFee() / 2;
            this.setLateFee(late);
        }
    }

}
