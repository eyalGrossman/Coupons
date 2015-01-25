package il.ac.telhai.coupons.model;

import java.util.Collection;

/**
 * The interface to a coupons DAO.
 * 
 * @see MySQLCouponsDAO
 * @author Nahum and Eyal
 */
public interface ICouponsDAO {

    /* ----- Public methods ----- */

    /**
     * Add the given coupon to the database.
     * 
     * @param coupon The coupon to add.
     * @return The coupon's id.
     * @throws CouponException
     */
    public int addCoupon(Coupon coupon) throws CouponException;

    /**
     * Delete the given coupon from the database.
     * 
     * @param coupon The coupon to delete.
     * @return The deleted coupon.
     * @throws CouponException
     */
    public Coupon deleteCoupon(Coupon coupon) throws CouponException;

    /**
     * Get all of the coupons in the database.
     * 
     * @return A coupons collection.
     * @throws CouponException
     */
    public Collection<Coupon> getCoupons() throws CouponException;

    /**
     * Get all of the coupons in the database by longitude and latitude.
     * 
     * @return A coupons collection.
     * @throws CouponException
     */
    public Collection<Coupon> getCoupons(double latitude, double longitude) throws CouponException;

    /**
     * Get all of the coupons from the specified category.
     * 
     * @param category The category to look for.
     * @return A collections of coupons with the specified category.
     * @throws CouponException
     */
    public Collection<Coupon> getCouponsByCategory(String category) throws CouponException;

    /**
     * Get all of the coupons from the specified category, latitude, longitude and expiration date.
     * 
     * @param category The category to look for.
     * @return A collections of coupons with the specified category.
     * @throws CouponException
     */
    public Collection<Coupon> getCouponsByCategory(
            String category, 
            double latitude, 
            double longitude) 
                    throws CouponException;

    /**
     * Get the coupon with the given id from the database.
     * 
     * @param id The id to look for.
     * @return The coupon with the given id or null if it doesn't exist.
     * @throws CouponException
     */
    public Coupon getCoupon(int id) throws CouponException;
    
    /**
     * Get the coupon with the given id from the database.
     * 
     * @param name The name to look for.
     * @return The coupon with the given name or null if it doesn't exist.
     * @throws CouponException
     */
    public Coupon getCoupon(String name) throws CouponException;

    /**
     * Update the given coupon in the database.
     * 
     * @param coupon The coupon to update.
     * @return The updated coupon.
     * @throws CouponException
     */
    public Coupon updateCoupon(Coupon coupon) throws CouponException;

    /**
     * Get the existing categories in the database.
     * 
     * @return A collection of category strings.
     * @throws CouponException
     */
    public Collection<String> getCategories() throws CouponException;
}