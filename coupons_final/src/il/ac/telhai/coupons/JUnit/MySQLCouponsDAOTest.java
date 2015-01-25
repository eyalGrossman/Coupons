package il.ac.telhai.coupons.JUnit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import il.ac.telhai.coupons.model.Coupon;
import il.ac.telhai.coupons.model.CouponException;
import il.ac.telhai.coupons.model.MySQLCouponsDAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

public class MySQLCouponsDAOTest {
    private static MySQLCouponsDAO dao;
    private static Coupon coupon1,coupon2,coupon3;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        dao = MySQLCouponsDAO.getInstance();
        Date today = new Date();
        coupon1 = new Coupon("Eyal","Student","Something","People",today,33,35);
        coupon2 = new Coupon("Nahum","Student","Something","People","2012/12/12 13:02:22",35,33);
        coupon3 = new Coupon("Haim","Teacher","Something","People",today,34,34);
    }
    @Test
    public void testAddCoupon() {
        try {
            assertTrue("coupon Coupon1 was not added to the db", (dao.addCoupon(coupon1) >= 0));
            assertTrue("coupon Coupon2 was not added to the db", (dao.addCoupon(coupon2) >= 0));
            assertTrue("coupon Coupon3 was not added to the db", (dao.addCoupon(coupon3) >= 0));
            ArrayList<Coupon> coupons = (ArrayList<Coupon>) dao.getCoupons();
            System.out.println(coupons.contains(coupon1));
            assertTrue("Coupon1 was not added", coupons.contains(coupon1));
            assertTrue("Coupon2 was not added", coupons.contains(coupon2));
            assertTrue("Coupon3 was not added", coupons.contains(coupon3));
        } 
        catch (CouponException e) {
            e.printStackTrace();
            fail("exception was thrown when trying to add coupons");
        }
        finally {
            deleteCoupon();
        }
    }

    /**
     * Test method for {@link il.ac.telhai.coupons.model.MySQLCouponsDAO#deleteCoupon(il.ac.telhai.coupons.model.Coupon)}.
     */
    public void deleteCoupon() {
        try {
            dao.deleteCoupon(coupon1);
            dao.deleteCoupon(coupon2);
            dao.deleteCoupon(coupon3);
            Collection<Coupon> coupons = dao.getCoupons();
            if(coupons.contains(coupon1)) {
                fail("Coupon1 was not deleted");
            }
            if(coupons.contains(coupon2)) {
                fail("Coupon2 was not deleted");
            }
            if(coupons.contains(coupon3)) {
                fail("Coupon3 was not deleted");
            }
        } catch (CouponException e) {
            e.printStackTrace();
            fail("exception was thrown when trying to add coupons");
        }
    }
}
