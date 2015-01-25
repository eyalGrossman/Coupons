package il.ac.telhai.coupons.JUnit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import il.ac.telhai.coupons.model.Coupon;
import il.ac.telhai.coupons.model.ShoppingCart;

import org.junit.BeforeClass;
import org.junit.Test;

public class ShoppingCartTest {

    private static ShoppingCart shopCart;
    private static List <Coupon> couponListTest;
    private static Coupon coupon1,coupon2;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        shopCart = new ShoppingCart();
        couponListTest = new ArrayList<>();
        Date today = new Date();
        coupon1 = new Coupon("Eyal","Student","Something","People",today,33,35);
        coupon2 = new Coupon("Nahum","Student","Something","People",today,35,33);
        couponListTest.add(coupon1);
        couponListTest.add(coupon2);
    }

    @Test
    public void testShopCart() {
        shopCart.addCoupon(coupon1);
        shopCart.addCoupon(coupon2);
        List <Coupon> shopCartList = shopCart.getCouponList();
        if (!couponListTest.equals(shopCartList))
            fail("FAIL addCoupon failed we didn't add coupon as expited\ncouponListTest: " + couponListTest+
                    "\n" + "shopCart :" + shopCartList);
        shopCart.removeCoupon(coupon1);
        shopCart.addCoupon(coupon2);
        assertEquals("FAIL after removing size need to be changed to 1 item", shopCartList.size(), 1);
        shopCartList = shopCart.getCouponList();
        if (couponListTest.equals(shopCartList))
            fail("FAIL removeCoupon failed we didn't remove coupon as expited\ncouponListTest: " + couponListTest+
                    "\n" + "shopCart :" + shopCartList);
    }
}
