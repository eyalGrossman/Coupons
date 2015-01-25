package il.ac.telhai.coupons.model;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

/**
 * The <code>GetCouponTx</code> class implements a transaction that updates the given coupon in the database.
 *  
 * @author Nahum and Eyal
 */
final class UpdateCouponTx extends CouponTxTemplate<Coupon> {

    private Coupon coupon;

    /**
     * Constructs a new <code>UpdateCouponTx</code> that will update the specified coupon when executed.
     * 
     * @param factory The factory with which the session will be created.
     * @param coupon The coupon to be updates.
     */
    public UpdateCouponTx(SessionFactory factory, Coupon coupon) {
        super(factory);
        this.coupon = coupon;
    }

    /**
     * Perform the update action.
     * 
     * @return The coupon that was updates.
     */
    @Override
    public Coupon dbActions() {
        session.update(coupon);
        return coupon;
    }

    /**
     * {@inheritDoc}. Namely, that we coudln't update the coupon.
     */
    @Override
    public void txError(HibernateException e) throws CouponException {
        throw new CouponException("Failed to update coupon: " + coupon, e);
    }
}