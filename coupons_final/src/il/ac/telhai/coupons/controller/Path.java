package il.ac.telhai.coupons.controller;

/**
 * An enum that stores request paths that the controller handles 
 * and view paths that we will be redirected to.
 * 
 */
public enum Path {
    ADDED_VIEW("/coupons/added.html"),
    REMOVED_VIEW("/coupons/removed.html"),
    COUPONS_JSON_VIEW("/couponsjson.jsp"),
    ERROR_VIEW("/error.jsp"),
    INDEX_VIEW("/coupons/index.html"),
    MANAGER_VIEW("/manager.jsp"),
    LOGIN_VIEW("/login.jsp"),
    LOGIN_CONTROLLER("login"),
    LOGOUT_CONTROLLER("logout"),
    CHECK_PASSWORD_CONTROLLER("checkPassword"),
    MANAGER_CONTROLLER("manager"),
    ADD_COUPON_CONTROLLER("add"),
    UPDATE_COUPON_CONTROLLER("update"),
    DELETE_COUPON_CONTROLLER("delete"),
    COUPONS_JSON_CONTROLLER("couponsjson"),
    ADD_TO_SHOP_CART_CONTROLLER("addtoshopcart"),
    SHOW_SHOP_CART_CONTROLLER("showshopcart"),
    REMOVE_FROM_CART_CONTROLLER("removefromshopcart"),
    OTHER_CONTROLLER("*"),
    FULL_OTHER_CONTROLLER("/couponcontroller/*");
    
    private final String path;
    
    private Path(final String path) {
        this.path = path;
    }
    
    @Override
    public String toString() {
        return path;
    }
}
