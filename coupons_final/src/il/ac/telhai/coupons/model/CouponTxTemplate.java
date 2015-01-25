package il.ac.telhai.coupons.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * An abstract class that defines a coupon transaction with the template method design pattern.
 * We use this design pattern since transactions are mostly made up of common steps.
 *
 * @see CouponTx
 * @author Nahum and Eyal
 *
 * @param <T> The coupon transaction return value (ie <code>Coupon</code> object, integer, etc).
 */
public abstract class CouponTxTemplate<T> implements CouponTx<T> {

    /* ----- Protected data members ----- */

    /**
     * The session used to begin the transaction.
     */
    protected Session session;
    /**
     * The factory used to create the session.
     */
    protected SessionFactory factory;

    /* ----- Constructors ----- */

    /**
     * Constructs a new <code>CouponTxTemplate</code> with the specified session factory.
     * 
     * @param factory The session factory.
     */
    public CouponTxTemplate(SessionFactory factory) {
        this.factory = factory;
    }

    /* ----- Public methods ----- */

    /**
     * A template method that executes the coupon transaction. An execution has the following steps:
     * 
     * <p>
     * 1) Open a session
     * 2) Begin the transaction
     * 3) Perform the database actions (with <code>dbActions</code>)
     * 4) Commit the transaction
     * 
     * <p>
     * If an error occurs in one of these steps, raise a <code>CouponException</code> with <code>txError</code>.
     * 
     * @return The transaction's result.
     */
    @Override
    public final T execute() throws CouponException {
        Transaction tx = null;
        T result = null;

        try {
            // Attempt to make the transaction.
            session = factory.openSession();
            tx = session.beginTransaction();
            result = dbActions();
            tx.commit();
        }
        catch(HibernateException e) {
            // Handle errors by rolling back any changes and raise a new, appropriate error.
            if(tx != null) {
                tx.rollback();
            }
            txError(e);
        }
        finally {
            // Always close the session.
            close();
        }

        return result;
    }

    /**
     * Close the session.
     */
    private void close() {
        if(session != null) {
            session.close();
        }
    }

    /* ----- Abstract methods ----- */

    /**
     * Perform the database query.
     * 
     * @return The database query result.
     */
    protected abstract T dbActions();

    /**
     * Raise an appropriate coupon error.
     * 
     * @param e The root cause of the error.
     * @throws CouponException
     */
    protected abstract void txError(HibernateException e) throws CouponException;
    
    /**
     * Allows safe conversion of a collection since query returns a generic list. 
     * Checks that all of the elements are of the expected type.
     * 
     * @param clazz The class type to convert to.
     * @param c The collection to convert.
     * @return The converted collection.
     */
    protected static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
        List<T> r = new ArrayList<T>(c.size());
        for(Object o: c)
          r.add(clazz.cast(o));
        return r;
    }
}
