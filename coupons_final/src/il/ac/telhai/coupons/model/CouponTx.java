package il.ac.telhai.coupons.model;

/**
 * The interface to a coupon transaction.
 * 
 * @see CouponTxTemplate
 * @author Nahum and Eyal
 *
 * @param <T> The coupon transaction return type (ie <code>Coupon</code> object, integer, etc).
 */
public interface CouponTx <T> {
     /**
     * Execute the coupon transaction.
     * 
     * @return The transaction's result.
     * @throws CouponException
     */
     public T execute() throws CouponException;
}
