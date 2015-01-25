package il.ac.telhai.coupons.model;

/**
 * The <code>CouponException</code> class represents a coupon related error.
 * 
 * @author Nahum and Eyal
 */
public class CouponException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a <code>CouponException</code> with the specified detail message.
	 * 
	 * @param msg The error message.
	 */
	public CouponException(String msg) {
		super(msg);
	}
	
	/**
	 * Constructs a <code>CouponException</code> with the specified detail message and cause.
	 * 
	 * @param msg The error message.
	 * @param cause The root cause.
	 */
	public CouponException(String msg, Throwable cause) {
		super(msg, cause);
	}
}