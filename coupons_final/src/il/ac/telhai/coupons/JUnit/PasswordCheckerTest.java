package il.ac.telhai.coupons.JUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import il.ac.telhai.coupons.model.CouponException;
import il.ac.telhai.coupons.model.PasswordChecker;

import org.junit.BeforeClass;
import org.junit.Test;

public class PasswordCheckerTest {

    private static PasswordChecker ch;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        ch = new PasswordChecker();
    }

    @Test
    public void testManagerLogin() throws CouponException {
        Boolean result = ch.isManager("eyal", "eyal");
        assertTrue(result);//If is true then the we are corrent and Eyal is an manager 
        //else fail and we fail.
    }
    @Test
    public void testNonManagerLogin() throws CouponException {
        Boolean result = ch.isManager("eyal", "nahom");
        assertFalse(result);//If is false then the we are corrent and Eyal is not a manager 
        //else the test fail
    }
}
