package il.ac.telhai.coupons.model;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

/**
 * The <code>AddCouponTx</code> class implements a transaction that adds the given coupon to the database.
 * 
 * @author Nahum and Eyal
 */
final class AddCouponTx extends CouponTxTemplate<Integer> {
	
	/**
	 * The coupon that will be added.
	 */
	private Coupon coupon;
	
	/**
	 * Constructs a new <code>AddCouponTx</code> with the specified session factory and coupon.
	 * 
	 * @param factory The session factory.
	 * @param coupon The coupon to add.
	 */
	public AddCouponTx(SessionFactory factory, Coupon coupon) {
		super(factory);
		this.coupon = coupon;
	}
	
	/**
	 * Save the <code>coupon</code> private member to the database.
	 * 
	 * @return The coupon's id.
	 */
	@Override
	public Integer dbActions() {
		
		
		return (Integer) session.save(coupon);
	}

	/**
	 * {@inheritDoc}. Namely, that adding the coupon has failed.
	 */
	@Override
	public void txError(HibernateException e) throws CouponException {
		throw new CouponException("Failed to add coupon: " + coupon, e);
	}
}