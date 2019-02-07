package operations.member;

import helper.DateTime;
import operations.holding.Holding;

/**
 * The Premium Member Class
 */
public class PremiumMember extends Member {

    /**
     * The Constructor
     */
    public PremiumMember(String premimumMemberId, String premiumMemberName) {
        super(premimumMemberId, premiumMemberName, 45);
    }

    /**
     * Reset Credit to the 45
     */
    public void resetCredit() {
        this.maxCredit = 45;
        this.updateRemainingCredit(this.maxCredit);
    }

    /**
     * Calculate the remaining credit
     */
    public int calculateRemainingCredit() {
        return 0;
    }

    /**
     * Return holding
     * Premium can always return the holding
     */
    public boolean returnHolding(Holding holding, DateTime returnDate) {
        return true;
    }
}
