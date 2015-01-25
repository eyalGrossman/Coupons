package il.ac.telhai.coupons.controller;

/**
 * 
 * 
 * 
 */
public enum Attribute {
    MANAGER_LOGGED("mannagerloggedin"),
    LOGGER("logger"),
    ERROR("errorObj"),
    COUPONS_JSON("couponsjson"),
    SHOP_CART("shopCart");
    
    private final String path;
    
    private Attribute(final String path) {
        this.path = path;
    }
    
    @Override
    public String toString() {
        return path;
    }
}
