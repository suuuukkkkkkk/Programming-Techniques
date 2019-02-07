package operations.member;

import helper.DateTime;
import operations.exceptions.InsufficientCreditException;
import operations.holding.Holding;

/**
 * The standard member class
 */
public class StandardMember extends Member {

    /**
     * Constructor
     */
    public StandardMember(String standardMemberId, String standardMemberName) {
        super(standardMemberId, standardMemberName, 30);
    }

    /**
     * To test whether the standard member can return the holding
     */
    public boolean returnHolding(Holding holding, DateTime returnDate) throws InsufficientCreditException {
        if (DateTime.diffDays(returnDate, holding.getBorrowDate()) <= holding.getMaxLoanPeriod()) {
            // if no late fee is required
            if (this.getCredit() >= 0)
                return true;
            else
                throw new InsufficientCreditException();
        } else {
            // if late fee applies
            if (this.getCredit() < holding.getLateFee())
                throw new InsufficientCreditException();
            else
                return true;
        }

    }

    /**
     * Reset credit to 30
     */
    public void resetCredit() {
        this.maxCredit = 30;
        this.updateRemainingCredit(30);
    }

    /**
     * Calculate remaining credit
     */
    public int calculateRemainingCredit() {
        return 0;
    }
}
