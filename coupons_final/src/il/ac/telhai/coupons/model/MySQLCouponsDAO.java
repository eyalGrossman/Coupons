package il.ac.telhai.coupons.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * The <code>MySQLCouponsDAO</code> class provides a direct and uniform way to access the database.
 * This class uses the singleton design pattern since we don't want there to be more than one DAO instance.
 * 
 * @author Nahum and Eyal
 *
 */
public class MySQLCouponsDAO implements ICouponsDAO {

    /* ----- Private data members ----- */

    /**
     * The session factory is used to create Hibernate sessions.
     */
    private SessionFactory factory = null;

    /* ----- Singleton related ----- */

    /**
     * The one and only DAO instance.
     */
    private static MySQLCouponsDAO instance = null;

    /**
     * A private constructor to make sure there is only one instance.
     * 
     * @throws CouponException
     */
    private MySQLCouponsDAO() throws CouponException {
        try {
            // Configure Hibernate and build the session factory.
            factory = new AnnotationConfiguration().
                    configure().
                    addAnnotatedClass(Coupon.class).
                    buildSessionFactory();
        }
        catch(HibernateException e)	{
            throw new CouponException("Failed to create Hibernate session factory.", e);
        }
    }

    /**
     * If no <code>MySQLCouponsDAO</code> instance was created up until one, create one. In any case return the
     * singleton instance.
     * 
     * @return The singleton instance.
     * @throws CouponException
     */
    public static MySQLCouponsDAO getInstance() throws CouponException {
        // Check if there's a MySQLCouponsDAO instance.
        if(instance == null) {
            instance = new MySQLCouponsDAO();
        }
        return instance;
    }

    /* ----- DAO methods ----- 
     * All DAO methods use CouponTxTemplate implementations (AddCouponTx, DeleteCouponTx, etc).
     * First we create an instance of the relevant CouponTxTemplate implementation class, and then we execute it 
     * and return the result.  
     */

    /**
     * {@inheritDoc}
     * Uses <code>AddCouponTx</code> to carry out the transaction.
     */
    @Override
    public int addCoupon(Coupon coupon) throws CouponException {
        if(getCoupon(coupon.getName()) != null) {
            return -1;
        }
        CouponTx<Integer> tx = new AddCouponTx(factory, coupon);
        return tx.execute();
    }

    /**
     * {@inheritDoc}
     * Uses <code>DeleteCouponTx</code> to carry out the transaction.
     */
    @Override
    public Coupon deleteCoupon(Coupon coupon) throws CouponException {
        CouponTx<Coupon> tx = new DeleteCouponTx(factory, coupon);
        return tx.execute();
    }

    /**
     * {@inheritDoc}
     * Uses <code>GetCouponsTx</code> to carry out the transaction.
     */
    @Override
    public Collection<Coupon> getCoupons() throws CouponException {
        CouponTx<Collection<Coupon>> tx = new GetCouponsTx(factory);
        return tx.execute();
    }
    
    /**
     * {@inheritDoc}
     * Uses <code>GetCouponsTx</code> to carry out the transaction.
     */
    @Override
    public Collection<Coupon> getCoupons(double latitude, double longitude)
            throws CouponException {
        if (latitude < 0 || longitude < 0) {
            return getCoupons();
        }
        Collection<Coupon> coupons = this.getCoupons();
        return sortByEuclid(coupons, latitude, longitude);
    }

    /**
     * {@inheritDoc}
     * Uses <code>GetCouponsTx</code> to carry out the transaction.
     */
    @Override
    public Collection<Coupon> getCouponsByCategory(String category) 
            throws CouponException {
        CouponTx<Collection<Coupon>> tx = new GetCouponsTx(factory, category);
        return tx.execute();
    }
    
    /**
     * {@inheritDoc}
     * Uses <code>GetCouponsTx</code> to carry out the transaction.
     */
    @Override
    public Collection<Coupon> getCouponsByCategory(
            String category, 
            double latitude, 
            double longitude) 
                    throws CouponException {
        if(latitude < 0 || longitude < 0) {
            return getCouponsByCategory(category);
        }
        CouponTx<Collection<Coupon>> tx = new GetCouponsTx(factory, category);
        Collection<Coupon> coupons = tx.execute();
        return sortByEuclid(coupons, latitude, longitude);
    }

    /**
     * {@inheritDoc}
     * Uses <code>GetCouponTx</code> to carry out the transaction.
     */
    @Override
    public Coupon getCoupon(int id) throws CouponException {
        CouponTx<Coupon> tx = new GetCouponTx(factory, id);
        return tx.execute();
    }
    
    /**
     * {@inheritDoc}
     * Uses <code>GetCouponTx</code> to carry out the transaction.
     */
    @Override
    public Coupon getCoupon(String name) throws CouponException {
        CouponTx<Coupon> tx = new GetCouponTx(factory, name);
        return tx.execute();
    }

    /**
     * {@inheritDoc}
     * Uses <code>UpdateCouponTx</code> to carry out the transaction.
     */
    @Override
    public Coupon updateCoupon(Coupon coupon) throws CouponException {
        CouponTx<Coupon> tx = new UpdateCouponTx(factory, coupon);
        return tx.execute();
    }

    /**
     * {@inheritDoc}
     * Uses <code>GetCategoriesTx</code> to carry out the transaction.
     */
    @Override
    public Collection<String> getCategories() throws CouponException {
        CouponTx<Collection<String>> tx = new GetCategoriesTx(factory);
        return tx.execute();
    }
    
    public Collection<Coupon> filterByDate(Collection<Coupon> coupons) {
        // Filter by date and position
        Date today = new Date();
        Iterator<Coupon> it = coupons.iterator();
        while(it.hasNext()) {
            Coupon coup = it.next();
            if(coup.isExpired(today)) {
                System.out.println("removing expired coupon " + coup);
                it.remove();
            }
        }
        return coupons;
    }
    
    public Collection<Coupon> sortByEuclid(Collection<Coupon> coupons, double latitude, double longitude) 
            throws CouponException {
        Collections.sort((List<Coupon>) coupons, new EuclidComparator(latitude, longitude));
        return coupons;
    }
    
    /**
     * Filter by date and sort by distance from the given origin (latitude and longitude).
     * 
     * @param coupons The coupons to filter and sort.
     * @param latitude The origin's latitude.
     * @param longitude The origin's longitude.
     * @return The sorted/filtered coupons collection.
     * @throws CouponException 
     */
    public Collection<Coupon> filterByLatLongDate(
            Collection<Coupon> coupons, 
            double latitude, 
            double longitude) throws CouponException {
        coupons = filterByDate(coupons);
        
        return sortByEuclid(coupons, latitude, longitude);
    }
}
