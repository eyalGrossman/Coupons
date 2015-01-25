package il.ac.telhai.coupons.model;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The <code>Coupon</code> class represents a coupon. 
 * 
 * <p>A coupon has a name, unique identifier, description, and an image. It is mapped to the database using 
 * annotations.
 * 
 * @see MySQLCouponsDAO
 * @author Nahum and Eyal
 */
@Entity
@Table(name = "COUPONS")
public class Coupon {

    /* ----- Constants ----- */

    /** 
     * The maximal possible id. 
     */
    private static final long MAX_ID = 1L;

    /* ----- Private data members ----- 
     * We use annotations to map the coupon's data members to columns.
     */

    /** 
     * The coupon's unique identifier.
     * Hibernate generates a unique identifier for us. 
     */
    @Id @GeneratedValue
    @Column(name = "id")
    private int id;

    /** 
     * The coupon's name.
     * A coupon's name is unique (can't have two coupons with the same name). 
     */
    @Column(name = "name", unique = true)
    private String name;

    /** 
     * The coupon's description. 
     */
    @Column(name = "description")
    private String description;

    /** 
     * The coupon's image. 
     */
    @Column(name = "image")
    private String image;

    /**
     * The coupon's category.
     */
    @Column(name = "category")
    private String category;

    /**
     * The coupon position. The hashmap contains the
     * latitude, longitude of the coupon. 
     */
    @Column(name = "position")
    private HashMap<String, Double> position;
    /**
     * The coupon's date.
     */
    @Column(name = "Date")
    private Date expirationDate;

    /* Enum for the date format*/
    private enum Constant {
        FORMAT("yyyy/MM/dd HH:mm:ss");

        private final String value;

        private Constant(final String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    /* ----- Constructors ----- */

    /** 
     * An empty constructor (for Hibernate). 
     */
    public Coupon() {
        super();
    }

    /**
     * Constructs a new <code>Coupon</code> with the specified id, name, description, and image.
     * 
     * @param id The unique identifier.
     * @param name The coupon's name.
     * @param description The coupon's description.
     * @param image The coupon's image.
     * @param category The coupon's category
     * @param date The coupon's date
     * @param latitude The coupon's latitude
     * @param longitude The coupon's longitude
     * @throws CouponException Throws an Exception when the construction fails.
     */
    public Coupon(String name, String description, String image, String category, 
            String date, double latitude, double longitude) throws CouponException {
        // Use setters for validation.
        this.position = new HashMap<String,Double>(2);
        setName(name);
        setDescription(description);
        setImage(image);
        setCategory(category);
        setPosition("latitude", latitude);
        setPosition("longitude", longitude);
        setExpirationDate(date);
    }

    /**
     * Constructs a new <code>Coupon</code> with the specified id, name, description, and image.
     * 
     * @param id The unique identifier.
     * @param name The coupon's name.
     * @param description The coupon's description.
     * @param image The coupon's image.
     * @param category The coupon's category
     * @param date The coupon's date only in here it gets from a Date class.(for the comparator).
     * @param latitude The coupon's latitude
     * @param longitude The coupon's longitude
     * @throws CouponException Throws an Exception when the construction fails.
     */
    public Coupon(String name, String description, String image, String category, 
            Date date, double latitude, double longitude) throws CouponException {
        // Use setters for validation.
        this(name, description, image, category, 
                new SimpleDateFormat(Constant.FORMAT.toString()).format(date),  latitude, longitude);
    }

    /* ----- Getters and setters ----- 
     * Setters throw exception when the parameters are not valid */

    public int getId() {
        return id;
    }

    /**
     * The method that sets the coupon id.
     * @param id The wanted id.
     * @throws CouponException Throws an Exception if the arguments are not valid.
     */
    public void setId(int id) throws CouponException {
        if(id < 0 || id > MAX_ID)
            throw new CouponException("id is not valid " + id);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    /**
     * The method that sets the coupon name.
     * @param name The wanted name
     * @throws CouponException Throws an Exception if the arguments are not valid.
     */
    public void setName(String name) throws CouponException {
        if(name == null || name.equals(""))
            throw new CouponException("name is not valid " + name);
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    /**
     * The method that sets the coupon description.
     * @param description The wanted description
     * @throws CouponException Throws an Exception if the arguments are not valid.
     */
    public void setDescription(String description) throws CouponException {
        if(description == null || description.equals(""))
            throw new CouponException("description is not valid " + description);
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    /**
     * The method that sets the coupon image.
     * @param image The wanted image
     * @throws CouponException Throws an Exception if the arguments are not valid.
     */
    public void setImage(String image) throws CouponException {
        if(image == null || image.equals(""))
            throw new CouponException("image is not valid " + image);
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    /**
     * The method that sets the coupon category.
     * @param category The wanted category
     * @throws CouponException Throws an Exception if the arguments are not valid.
     */
    public void setCategory(String category) throws CouponException {
        if(category == null || category.equals(""))
            throw new CouponException("category is not valid " + category);
        this.category = category;
    }

    public double getLatitude() {
        return getPosition("latitude");
    }

    /**
     * The method that sets the coupon latitude.
     * @param latitude The wanted latitude
     * @throws CouponException Throws an Exception if the arguments are not valid.
     */
    public void setLatitude(double latitude) throws CouponException {
        if( 0 > latitude || latitude > 50)
            throw new CouponException("latitude is not valid " + latitude);
        setPosition("latitude", latitude);
    }

    public double getLongitude() {
        return getPosition("longitude");
    }

    /**
     * The method that sets the coupon longitude.
     * @param longitude The wanted longitude
     * @throws CouponException Throws an Exception if the arguments are not valid.
     */
    public void setLongitude(double longitude) throws CouponException {
        if( 0 > longitude || longitude > 50)
            throw new CouponException("Longitude is not valid " + longitude);
        setPosition("longitude", longitude);
    } 

    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * The method that sets the expiration date.
     * @param expirationDate The wanted expiration date.
     * @throws CouponException Throws an exception if the date is not legal.
     */
    public void setExpirationDate(String expirationDate) throws CouponException {
        this.expirationDate = processDate(expirationDate);
    }
    
    public double getDistance(Coupon coupon){
        return this.getDistance(coupon.getLatitude(),coupon.getLongitude());
    }

    public double getDistance(double latitude, double longitude){
        return Math.sqrt(Math.pow(latitude-this.getLatitude(), 2) + Math.pow(longitude-this.getLongitude(), 2));
    }
    
    public void setPosition(String key, double value) {
        this.position.put(key,value);
    }

    public double getPosition(String key) {
        return this.position.get(key);
    }
    
    /* ----- Public methods ----- */

    /**
     * Update the fields for which the given value is legal.
     * 
     * @param id The unique identifier.
     * @param name The coupon's name.
     * @param description The coupon's description.
     * @param image The coupon's image.
     * @param category The coupon's category
     * @param date The coupon's date
     * @param latitude The coupon's latitude
     * @param longitude The coupon's longitude
     * @throws CouponException If one of given values is not legal.
     */
    public void update(String name, String description, String image, String category, 
            String date, Double latitude, Double longitude) throws CouponException {
        if(name != null && !name.equals("")) {
            setName(name);
        }
        if(description != null && !description.equals("")) {
            setDescription(description);
        }
        if(image != null && !image.equals("")) {
            setImage(image);
        }
        if(category != null && !category.equals("")) {
            setCategory(category);
        }
        if(date != null && !date.equals("")) {
            setExpirationDate(date);
        }
        if(latitude != null && latitude >= 0) {
            setLatitude(latitude);
        }
        if(longitude != null && longitude >= 0) {
            setLongitude(longitude);
        }
    }

    @Override
    public String toString() {
        return "Coupon [id=" + id + ", name=" + name + ", description="
                + description + ", image=" + image + ", category=" + category
                + ", position=" + position + ", expirationDate="
                + expirationDate + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((category == null) ? 0 : category.hashCode());
        result = prime * result
                + ((description == null) ? 0 : description.hashCode());
        result = prime * result
                + ((expirationDate == null) ? 0 : expirationDate.hashCode());
        result = prime * result + id;
        result = prime * result + ((image == null) ? 0 : image.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result
                + ((position == null) ? 0 : position.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Coupon other = (Coupon) obj;
        if (category == null && other.category != null || !category.equals(other.category)) 
            return false;
        if (description == null && other.description != null || !description.equals(other.description)) 
            return false;
        if (id != other.id)
            return false;
        if (image == null && other.image != null || !image.equals(other.image)) 
            return false;
        if (name == null && other.name != null || !name.equals(other.name)) 
            return false;
        if (expirationDate == null && other.expirationDate != null) 
            return false;
        if (!compareDates(expirationDate,other.expirationDate))
            return false;
        if (position == null && (other.position != null)) 
            return false;
        if (!position.equals(other.position))
            return false;
        return true;
    }
    
    /**
     * Checks whether the coupon's date has expired in relation to the given date. 
     * @param today
     * @return
     */
    public boolean isExpired(Date compareToDate){
        return (this.expirationDate.compareTo(compareToDate) < 0 ) ? true : false;
    }

    /**
     * Convert the coupon on which this method is called to a <code>JSONObject</code>
     *  
     * @return A JSONObject that represents the coupon.
     */
    public JSONObject toJSONObject() {
        Map<String,Object> couponMap = new HashMap<String,Object>();
        couponMap.put("id", this.getId());
        couponMap.put("name", this.getName());
        couponMap.put("description", this.getDescription());
        couponMap.put("image", this.getImage());
        couponMap.put("category", this.getCategory());
        couponMap.put("latitude", this.getLatitude());
        couponMap.put("longitude", this.getLongitude());
        couponMap.put("date", this.getExpirationDate().toString());
        return new JSONObject(couponMap);
    }
    
    /* ----- Private methods ----- */

    /**
     * The method that gets a string with a wanted date and returns the
     * date as Class Date.
     * 
     * @param wantedDate The string that address to your wanted date.
     * @return The desired date 
     * @throws CouponException If failed to process the given date.
     */
    private Date processDate(String wantedDate) throws CouponException {
        SimpleDateFormat wantedFormat = new SimpleDateFormat(Constant.FORMAT.toString());
        try {
            return wantedFormat.parse(wantedDate);
        } catch (ParseException e) {
            throw new CouponException("Failed to process date " + wantedDate, e);
        }
    }

    /*
     * Compare the two given dates (in string form).
     */
    private boolean compareDates(Date DateA,Date DateB){
        SimpleDateFormat wantedFormat = new SimpleDateFormat(Constant.FORMAT.toString());
        String strDate1 = wantedFormat.format(DateA);
        String strDate2 = wantedFormat.format(DateA);
        return strDate1.equals(strDate2);
    }
    
    /* ----- Public static methods ----- */

    /**
     * Convert the given coupons collection to a JSONArray.
     * 
     * @param coupons The coupons to be converted.
     * @return A JSONArray of coupons.
     */
    public static JSONArray toJSONArray(Collection<Coupon> coupons)
            throws CouponException {
        Collection<JSONObject> couponsJSONArray = new ArrayList<JSONObject>();
        // Iterate over coupons and add them to the coupons json array.
        Coupon coupon;
        if (coupons == null) {
            return null;
        }
        Iterator<Coupon> iterator = coupons.iterator();
        while(iterator.hasNext()) {
            // Get the next coupon in the collection.
            coupon = iterator.next();
            // Convert coupon to JSONObject
            JSONObject couponJSON = coupon.toJSONObject();
            // Add the coupon to the JSON array.
            couponsJSONArray.add(couponJSON);
        }
        return new JSONArray(couponsJSONArray);
    }

    /**
     * Convert the given coupons collection to a json object where each key is a category and the value is a json
     * array of coupons (with that same category).
     * 
     * @param coupons The coupons collection to convert.
     * @return The json object with all of the categories mapped to coupons.
     * @throws CouponException If failed to convert to JSON.
     */
    public static JSONObject toCategorizedJSONObject(Collection<Coupon> coupons) 
            throws CouponException {
        // Create a hash table that maps a category to a collection of coupons (of the same category).
        HashMap<String, Collection<Coupon>> categorizedCoupons = new HashMap<String, Collection<Coupon>>();
        for(Coupon coup : coupons) {
            String category = coup.getCategory();
            if(!categorizedCoupons.containsKey(category)) {
                categorizedCoupons.put(category, new ArrayList<Coupon>());
            }
            categorizedCoupons.get(category).add(coup);
        }

        // Create a json object where each key is the category and the value is a json array of coupons.
        JSONObject categorizedJSON = new JSONObject();
        try {
            Iterator<String> categoryIterator = categorizedCoupons.keySet().iterator();
            while(categoryIterator.hasNext()) {
                String category = categoryIterator.next();
                Collection<Coupon> couponsInCategory = categorizedCoupons.get(category);
                categorizedJSON.put(category, Coupon.toJSONArray(couponsInCategory));
            }
        }
        catch(CouponException e) {
            throw new CouponException("Couldn't categorize coupons", e);
        } catch (JSONException e) {
            throw new CouponException("Couldn't convert coupons to JSON", e);
        }
        return categorizedJSON;
    }
}