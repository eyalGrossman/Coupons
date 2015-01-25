package il.ac.telhai.coupons.model;

import java.util.Collection;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

/**
 * The <code>GetCouponsTx</code> class implements a transaction that gets all coupons from the database.
 * 
 * @author Nahum and Eyal
 */
final class GetCouponsTx extends CouponTxTemplate<Collection<Coupon>> {

    private String category;

    /**
     * Constructs a new <code>GetCouponsTx</code> that will get all coupons when executed.
     * 
     * @param factory
     */
    public GetCouponsTx(SessionFactory factory) {
        this(factory, null);
    }

    public GetCouponsTx(SessionFactory factory, String category) {
        super(factory);
        setCategory(category);
    }
    
    /**
     * Perform the database query that gets a list of all existing coupons.
     * 
     * @return A collection with all of the coupons.
     */
    @Override
    public Collection<Coupon> dbActions() {
        if(category != null && !category.equals("")) {
            return castList(Coupon.class, session.createQuery("FROM Coupon WHERE category='" + category + "'").list());
        }
        return castList(Coupon.class, session.createQuery("FROM Coupon").list());
    }

    /**
     * {@inheritDoc}. Namely, that we couldn't get the coupons.
     */
    @Override
    public void txError(HibernateException e) throws CouponException {
        throw new CouponException("Failed to get coupons", e);
    }

    private void setCategory(String category) {
        this.category = category;
    }
}