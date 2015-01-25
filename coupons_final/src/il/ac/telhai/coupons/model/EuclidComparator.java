package il.ac.telhai.coupons.model;

import java.util.Comparator;
import java.util.Date;

/**
 * A comparator for comparing by distance from a given latitude and longitude.
 * 
 * @author Nahum & Eyal
 *
 */
public class EuclidComparator implements Comparator<Coupon> {
    private Coupon compareTo;
    private static final String NAME = "compare";

    /**
     * Constructs a comparator with the given latitude and longitude as the origin.
     * 
     * @param latitude The origin's latitude.
     * @param longitude The origin's longitude.
     * @throws CouponException
     */
    public EuclidComparator(double latitude, double longitude) throws CouponException {
        compareTo = new Coupon(NAME, NAME, NAME, NAME, new Date(), latitude, longitude);
    }
    
    /**
     * Compares the two coupons by the distance from the origin.
     */
    public int compare(Coupon c1, Coupon c2) {
        if(c1.getDistance(compareTo) < c2.getDistance(compareTo)) {
            return -1;
        }
        else if(c1.getDistance(compareTo) > c2.getDistance(compareTo)) {
            return 1;
        }
        return 0;
    }
}