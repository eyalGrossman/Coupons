package il.ac.telhai.coupons.model;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

/**
 * The <code>DeleteCouponTx</code> class implements a transaction that deletes the given coupon from the database.
 * 
 * @author Nahum and Eyal
 */
final class DeleteCouponTx extends CouponTxTemplate<Coupon> {

    /**
     * The coupon that will be deleted when execute is called.
     */
    private Coupon coupon;

    /**
     * Constructs a new <code>DeleteCouponTx</code> transaction that will delete the given coupon when executed.
     * 
     * @param factory The session factory.
     * @param coupon The coupon to delete.
     */
    public DeleteCouponTx(SessionFactory factory, Coupon coupon) {
        super(factory);
        this.coupon = coupon;
    }

    /**
     * Perform the deletion.
     * 
     * @return The deleted coupon.
     */
    @Override
    public Coupon dbActions() {
        session.delete(coupon);
        return coupon;
    }

    /**
     * {@inheritDoc}. Namely, that deleting the coupon has failed.
     */
    @Override
    public void txError(HibernateException e) throws CouponException {
        throw new CouponException("Failed to delete coupon: " + coupon, e);
    }
}