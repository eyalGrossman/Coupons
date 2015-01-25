package il.ac.telhai.coupons.controller;

import il.ac.telhai.coupons.model.Coupon;
import il.ac.telhai.coupons.model.CouponException;
import il.ac.telhai.coupons.model.MySQLCouponsDAO;
import il.ac.telhai.coupons.model.PasswordChecker;
import il.ac.telhai.coupons.model.ShoppingCart;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 * Servlet implementation class CouponsController manages and redirects the given requests.
 * 
 * @author Nahum and Eyal
 */
@WebServlet("/couponcontroller/*")
public class CouponsController extends HttpServlet {

    /* ----- Private data members ----- */

    /**
     * Version number.
     */
    private static final long serialVersionUID = 1L; 
    /**
     * Logger for info/debug/error/etc messages.
     */
    private Logger logger = null;
    /**
     * DAO object for accessing the model.
     */
    private MySQLCouponsDAO couponsDAO = null;
    /**
     * The variable for checking if the given user name and password
     * belong to a manager account.
     */
    private PasswordChecker managerChecker;
    
    private enum Constant {
        PATH_INDEX(1);
        
        final int value;
        
        private Constant(final int val) {
            this.value = val;
        }
        
        public int value() {
            return value;
        }
    }
    
    /* ----- Constructors ----- */

    /**
     * Constructs a new <code>CouponController</code>.
     * 
     * @throws CouponException 
     * @throws IOException 
     * @throws NoSuchAlgorithmException 
     * @see HttpServlet#HttpServlet()
     */
    public CouponsController() throws CouponException, IOException, NoSuchAlgorithmException {
        super();

        // A DAO object for accessing the model.
        couponsDAO = MySQLCouponsDAO.getInstance();
        managerChecker = new PasswordChecker();

        // Set up logger.
        logger = Logger.getRootLogger();
        BasicConfigurator.configure();
        logger.setLevel(Level.DEBUG);
    }

    /* ----- Methods ----- */

    /**
     * The controller's <code>doGet</code> method acts as a dispatcher that redirects get requests to the right 
     * place.
     * 
     * @param request The given client request.
     * @param response The response to be returned to the client.
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        logger.debug("handling GET");
        logger.debug("path is "+request.getPathInfo());
        request.setAttribute(Attribute.LOGGER.toString(), logger);
        String jspPage = this.getPath(request);
        logger.debug("before compare");
        try {
            if(jspPage.equals(Path.LOGIN_CONTROLLER.toString())) {
                logger.debug("request for login page received");
                if(request.getSession().getAttribute(Attribute.MANAGER_LOGGED.toString()) != null) {
                    logger.debug("manager was logged in already");
                    this.dispatch(Path.MANAGER_VIEW.toString(), request, response);
                }
                else {
                    this.dispatch(Path.LOGIN_VIEW.toString(), request, response);
                }
            }
            else if(jspPage.equals(Path.LOGOUT_CONTROLLER.toString())) {
                this.logout(request, response);
            }
            else if (jspPage.equals(Path.CHECK_PASSWORD_CONTROLLER.toString())) {
                this.loginManager(request, response);
            }
            else if(jspPage.equals(Path.MANAGER_CONTROLLER.toString())) { 
                this.dispatch(Path.MANAGER_VIEW.toString(), request, response);
            }
            else if(jspPage.equals(Path.ADD_COUPON_CONTROLLER.toString())) {
                this.addCoupon(request, response, false);
            }
            else if(jspPage.equals(Path.UPDATE_COUPON_CONTROLLER.toString())) {
                this.updateCoupon(request, response);
            }
            else if(jspPage.equals(Path.DELETE_COUPON_CONTROLLER.toString())) {
                this.deleteCoupon(request, response);
            }
            else if(jspPage.equals(Path.COUPONS_JSON_CONTROLLER.toString())) {
                logger.debug("got request for couponsjson");
                this.couponJSON(request, response);
            }
            else if(jspPage.equals(Path.ADD_TO_SHOP_CART_CONTROLLER.toString())) {
                this.addToShoppingCart(request, response);
            }
            else if(jspPage.equals(Path.SHOW_SHOP_CART_CONTROLLER.toString())) {
                this.showShoppingCart(request, response);
            }   
            else if(jspPage.equals(Path.REMOVE_FROM_CART_CONTROLLER.toString())) {
                this.removeFromShoppingCart(request, response);
            }
            else if(jspPage.equals(Path.OTHER_CONTROLLER.toString())) {
                response.sendRedirect(response.encodeURL(Path.INDEX_VIEW.toString()));
            }
            else {
                throw new CouponException("Bad url: " + jspPage);
            }
        }
        catch(CouponException e) {
            this.logger.debug("An ecception was thrown " + e.getMessage());
            request.setAttribute(Attribute.ERROR.toString(), e);
            this.dispatch(Path.ERROR_VIEW.toString(), request, response);
        }
    }

    /**
     * Logout the manager from the application.
     * 
     * @param request
     * @param response
     * @throws IOException 
     * @throws ServletException 
     */
    private void logout(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getSession().removeAttribute(Attribute.MANAGER_LOGGED.toString());
        response.sendRedirect(response.encodeURL(Path.INDEX_VIEW.toString()));
    }

    /**
     * The controller's <code>doPost</code> method acts as a dispatcher that redirects post requests to the right 
     * place.
     * 
     * @param request The given client request.
     * @param response The response to be returned to the client.
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        this.logger.debug("handling POST");
        this.logger.debug("path is " + request.getPathInfo());
        this.doGet(request, response);
    }

    /**
     * Add the coupon (that's derived from the request parameters) to the database through the DAO.
     * 
     * @param request The given request from the client.
     * @param response The response that will be returned to client.
     * @param updateFlag Should the coupon be updated if it already exists?
     * @throws CouponException
     * @throws IOException 
     * @throws ServletException 
     */
    private void addCoupon(HttpServletRequest request,HttpServletResponse response, boolean updateFlag) 
            throws CouponException, ServletException, IOException {
        // Extract coupon parameters from the request.
        String name = null, description = null, image = null, category = null, date = null;
        Double latitude = null, longitude = null;
        try {
            name = request.getParameter("name");
            description = request.getParameter("description");
            image = request.getParameter("image");
            category = request.getParameter("category");
            date = request.getParameter("date");
            longitude = Double.parseDouble(request.getParameter("longitude"));
            latitude = Double.parseDouble(request.getParameter("latitude"));
        }
        catch(NumberFormatException e) {}
        logger.debug("name " + name + "\ndescription " + description + "\nimage " + image + 
                "\ncategory " + category + "\ndate " + date + "\nlongitude " + longitude + "\nlatitude " + latitude);
        
        if(updateFlag == true) {
            // Update the coupon.
            Coupon toUpdate = couponsDAO.getCoupon(name);
            if(toUpdate != null) {
                toUpdate.update(name, description, image, category, date, latitude, longitude);
                couponsDAO.updateCoupon(toUpdate);
            }
            else {
                throw new CouponException("Couln't update! Coupon not found.");
            }
        }
        else {
            // Create and add the coupon.
            Coupon toAdd = new Coupon(name, description, image, category,date , latitude, longitude);
            logger.info("coupon " + toAdd + " was created");
            if(couponsDAO.addCoupon(toAdd) < 0) {
                throw new CouponException("Couldn't add coupon " + toAdd + ". Already exists?");
            }
            logger.info(toAdd + " added succesfuly.");
        }
        
        this.dispatch(Path.MANAGER_VIEW.toString(), request, response);
    }
    
    /**
     * Update the coupon (that's derived from the request parameters) to the database through the DAO.
     * 
     * @param request The given request from the client.
     * @param response The response that will be returned to the client.
     * @throws CouponException
     * @throws IOException 
     * @throws ServletException 
     */
    private void updateCoupon(HttpServletRequest request,HttpServletResponse response) 
            throws CouponException, ServletException, IOException {
        addCoupon(request, response, true);
    }

    /**
     *  The method that deletes a coupon from the database.
     *  
     * @param request The client request
     * @param response The client response
     * @throws CouponException
     * @throws ServletException
     * @throws IOException
     */
    private void deleteCoupon(HttpServletRequest request, HttpServletResponse response) 
            throws CouponException, ServletException, IOException{
        int id = Integer.parseInt(request.getParameter("deleteID"));
        logger.info("ID of remove coupon is " + id);
        Coupon toRemove = couponsDAO.getCoupon(id);
        logger.info("The coupon to be removed is " + toRemove);
        if(toRemove!= null){
            couponsDAO.deleteCoupon(toRemove);
        }
        this.dispatch(Path.MANAGER_VIEW.toString(), request, response);
    }

    /**
     * The method that checks user name and password in order know if it is a manager.
     *  
     * @param request The client request
     * @param response The client response
     * @throws IOException
     * @throws CouponException 
     * @throws ServletException 
     */
    private void loginManager(HttpServletRequest request, HttpServletResponse response) throws CouponException {
        try {
            String username = request.getParameter("user");
            logger.debug("username " + username);
            String password = request.getParameter("password");
            logger.debug("password " + password);
            // Checks if we got bad input.
            if(username == null || password == null) {
                // Return to the login if needed.
                this.dispatch(Path.LOGIN_VIEW.toString(), request, response);
            }
            // Checks if the variable belong to a manager account.
            else if(managerChecker.isManager(username, password)) {
                logger.debug("Hey manager " + username);
                // If they belong dispatch manager.
                request.getSession().setAttribute(Attribute.MANAGER_LOGGED.toString(), "true");
                this.dispatch(Path.MANAGER_VIEW.toString(), request, response);
            }
            else {
                // Else return to the login page.
                logger.debug("Not a manager account please try again"); 
                this.dispatch(Path.LOGIN_VIEW.toString(), request, response);
            }
        } catch(ServletException | IOException e) {
            throw new CouponException("Couldn't login", e);
        }
    }		

    /**
     * The method that forward to the wanted controller page
     * 
     * @param controllerPage The String that hold to which page the client needs to go to.
     * @param request The client request.
     * @param response The client response
     * @throws ServletException
     * @throws IOException
     */
    private void dispatch(String controllerPage, HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        RequestDispatcher dispatcher = null;
        logger.debug("dispatching to " + controllerPage);
        dispatcher = getServletContext().getRequestDispatcher(controllerPage);
        dispatcher.forward(request, response);
    }

    /**
     * The method that returns your coupons list as JSON.
     * 
     * @param request The client response.showcoupons.html
     * @param response The client response.
     * @throws IOException
     * @throws CouponException
     */
    private void couponJSON(HttpServletRequest request, HttpServletResponse response)
            throws IOException, CouponException, ServletException {
        // Get the coupons
        Collection<Coupon> coupons = couponsDAO.getCoupons();
        
        String latitudeStr = (String) request.getParameter("latitude");
        String longitudeStr = (String) request.getParameter("longitude");
        double latitude, longitude;
        try {
            // Try to extract client's location.
            latitude = Double.parseDouble(latitudeStr);
            longitude = Double.parseDouble(longitudeStr);
            this.logger.debug("latitude is " + latitude);
            this.logger.debug("longitude is " + longitude);
            coupons = couponsDAO.sortByEuclid(coupons, latitude, longitude);
        }
        catch(NumberFormatException | NullPointerException e) {}
        
        // Was the request for all coupons?
        if(request.getParameter("allcoupons") == null) {
            // If not, filter those out of date.
            logger.debug("filtering coupons by date...");
            coupons = couponsDAO.filterByDate(coupons);
        }
        
        JSONObject couponsJSON;
        couponsJSON = Coupon.toCategorizedJSONObject(coupons);
        request.setAttribute(Attribute.COUPONS_JSON.toString(), couponsJSON);
        this.dispatch(Path.COUPONS_JSON_VIEW.toString(), request, response);
    }

    /**
     * The method return the client wanted functionality of the token.
     * 
     * @param request The client request
     * @return The found token
     */
    private String getPath(HttpServletRequest request) {
        logger.info("url is "+request.getRequestURI());
        String wantedURL = request.getRequestURI();
        List<String> urlList = Arrays.asList(wantedURL.split("/couponcontroller/*"));
        logger.info("Wanted String is " + urlList.get(Constant.PATH_INDEX.value()));
        return urlList.get(Constant.PATH_INDEX.value());
    }

    /* ----- Shopping Cart Methods ----- */
    /**
     * The method that compute the JSON of the needed coupons and 
     * shows them the the client.
     * 
     * @param request The client request
     * @param response The client response
     * @throws CouponException
     * @throws ServletException
     * @throws IOException
     */
    private void showShoppingCart(HttpServletRequest request,
            HttpServletResponse response) throws CouponException, ServletException, IOException {
        HttpSession session = request.getSession();

        Collection<Coupon> couponCollection;
        //Checks if the shopping cart already exist.
        if(!doesShoppingCartExists(session)){
            couponCollection = new ArrayList<Coupon>();
        }
        else {
            ShoppingCart shopCart = (ShoppingCart) session.getAttribute(Attribute.SHOP_CART.toString());
            couponCollection= shopCart.getCouponList();
        }
        //Compute the JSON and returns it to display in the client browser.
        JSONObject coupons;
        coupons = Coupon.toCategorizedJSONObject(couponCollection);
        this.logger.debug(coupons);
        request.setAttribute(Attribute.COUPONS_JSON.toString(), coupons);
        this.dispatch(Path.COUPONS_JSON_VIEW.toString(), request, response);
    }
    
    /**
     * The method that removes a coupon item from the shopping cart.
     * 
     * @param request The client request.
     * @param response The client response.
     * @throws CouponException
     * @throws IOException 
     */
    private void removeFromShoppingCart(HttpServletRequest request,
            HttpServletResponse response) throws CouponException, IOException {
        HttpSession session = request.getSession();
        // If the cart doesn't exist we don't need to remove.
        if(!doesShoppingCartExists(session)){
            return;
        }
        // Get the coupon id and cherry pick's it from the data base and the removes it from the shopping cart.
        int id = Integer.parseInt(request.getParameter("id"));
        logger.info("Id of the coupon that we want to remove from cart " + id);
        Coupon couponRemoveToCart = couponsDAO.getCoupon(id);
        ShoppingCart shopCart = (ShoppingCart) session.getAttribute(Attribute.SHOP_CART.toString());
        shopCart.removeCoupon(couponRemoveToCart);
        response.sendRedirect(response.encodeURL(Path.REMOVED_VIEW.toString()));
    }
    
    /**
     * The method that adds a coupon item into the shopping cart.
     * 
     * @param request The client request.
     * @param response The client response.
     * @throws CouponException
     * @throws IOException 
     */
    private void addToShoppingCart(HttpServletRequest request,
            HttpServletResponse response) throws CouponException, IOException {
        HttpSession session = request.getSession();
        // Check if the shopping cart doesn't exist.
        if(!doesShoppingCartExists(session)) {  
            // Create a shopping cart and add it to the session.
            session.setAttribute(Attribute.SHOP_CART.toString(), new ShoppingCart());
        }
        // Gets the coupon id and cherry pick's it from the data base and the adds it to the shopping cart.
        int id = Integer.parseInt(request.getParameter("id"));
        logger.info("Id of the coupon that we want to add to cart " + id);
        Coupon couponAddToCart = couponsDAO.getCoupon(id);
        ShoppingCart shopCart = (ShoppingCart) session.getAttribute(Attribute.SHOP_CART.toString());
        shopCart.addCoupon(couponAddToCart);
        response.sendRedirect(response.encodeURL(Path.ADDED_VIEW.toString()));
    }

    /**
     * The method that checks if the shopping cart exist on the client session
     * ,return true if it does else return false.
     * 
     * @param session The HttpSession of the client.
     * @return Returns true if we have a shopping cart in that session else return false.
     */
    private boolean doesShoppingCartExists(HttpSession session) {
        return (session.getAttribute(Attribute.SHOP_CART.toString()) == null) ? false : true;
    }
}