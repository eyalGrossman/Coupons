package il.ac.telhai.coupons.model;

import java.util.Collection;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

/**
 * The <code>GetCategoriesTx</code> class implements a transaction that gets all of the categories in the database.
 * 
 * @author Nahum and Eyal
 */
final class GetCategoriesTx extends CouponTxTemplate<Collection<String> > {

    /**
     * Constructs a new <code>GetCategoriesTx</code> that will get all categories when executed.
     * 
     * @param factory
     */
    public GetCategoriesTx(SessionFactory factory) {
        super(factory);
    }

    /**
     * Query the database for existing categories.
     * 
     * @return A collection of a category strings.
     */
    @Override
    public Collection<String> dbActions() {
        return castList(String.class, session.createQuery("SELECT DISTINCT category FROM Coupon").list());
    }

    /**
     * {@inheritDoc}. Namely, that we couldn't get the categories.
     */
    @Override
    public void txError(HibernateException e) throws CouponException {
        throw new CouponException("Failed to get coupon categories", e);
    }
}