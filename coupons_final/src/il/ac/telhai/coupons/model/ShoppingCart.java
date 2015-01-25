package il.ac.telhai.coupons.model;

import java.util.ArrayList;
import java.util.List;
/**
 * The <code>ShoppingCart</code> class that is responsible on the client shopping cart.
 * 
 * <p>  <code>ShoppingCart</code> Class adds and remove the coupon 
 * from the shopping cart of the client.
 * 
 * @author Nahum and Eyal
 */

public class ShoppingCart {
    /**
     * The field for the adding and removing from the cart.
     */
    List <Coupon> couponList;

    /**
     * The constructor.
     */
    public ShoppingCart() {
        setCouponList(new ArrayList<Coupon>());
    }
    /**
     * The method that adds the coupon to the cart.
     * 
     * @param coupon The coupon we want to add.
     */
    public void addCoupon(Coupon coupon) {
        if(!this.couponList.contains(coupon))
            this.couponList.add(coupon);
    }
    /**
     * The method that removes the coupon from the cart.
     * 
     * @param coupon The coupon we want to remove
     */
    public void removeCoupon(Coupon coupon){
        this.couponList.remove(coupon);
    }
    /**
     * The method that return the coupons inside the shopping cart
     * right now.
     * @return Return the array of coupons that are in the shopping cart.
     */
    public List<Coupon> getCouponList() {
        return couponList;
    }
    /**
     * The method that sets the shop cart client list when we create a 
     * new <code>ShoppingCart</code> object.
     * @param couponList The coupon list we want to set.
     */
    private void setCouponList(List<Coupon> couponList) {
        this.couponList = couponList;
    }
}