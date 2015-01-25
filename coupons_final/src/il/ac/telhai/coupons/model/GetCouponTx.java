package il.ac.telhai.coupons.model;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

/**
 * The <code>GetCouponTx</code> class implements a transaction that gets the coupon with the a given id from
 * the database.
 * 
 * @author Nahum and Eyal
 */
final class GetCouponTx extends CouponTxTemplate<Coupon> {

    /**
     * The id to be searched for in the database.
     */
    private int id;
    private String name;

    /**
     * Constructs a new <code>GetCouponTx</code> that will get the coupon with the specified id when executed.
     * 
     * @param factory The factory to be used to create the session.
     * @param id The id to be searched for in the database.
     */
    public GetCouponTx(SessionFactory factory, int id, String name) {
        super(factory);
        this.id = id;
        this.name = name;
    }

    public GetCouponTx(SessionFactory factory, int id) throws HibernateException {
        this(factory, id, null);
    }

    public GetCouponTx(SessionFactory factory, String name) {
        this(factory, -1, name);
    }

    /**
     * Get the coupons with the given id.
     * 
     * @return The coupon with the given id.
     */
    @Override
    public Coupon dbActions() {
        if(id >= 0) {
            return (Coupon)session.get(Coupon.class, id);
        }
        Query query = session.createQuery("FROM Coupon WHERE name='" + name + "'");
        if(query.list().size() > 0) {
            return (Coupon) query.list().get(0);
        }
        return null;
    }

    /**
     * {@inheritDoc}. Namely, that we couldn't get the coupon.
     */
    @Override
    public void txError(HibernateException e) throws CouponException {
        throw new CouponException("Failed to get coupon", e);
    }
}