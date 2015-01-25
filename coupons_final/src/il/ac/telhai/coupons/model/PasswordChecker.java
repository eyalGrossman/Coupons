package il.ac.telhai.coupons.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * The <code>PasswordChecker</code> class check the user name and password.
 * 
 * <p>  <code>PasswordChecker</code> Class checks the wanted user name and password 
 * it also contains the pass word as MD5.
 * 
 * @author Nahum and Eyal
 */
public class PasswordChecker {

    /* ----- Private data members ----- 
    /** 
     * The HashMap for the user name password pair. 
     */
    private HashMap<String, String> userPasswordPair;

    /**
     * Constructs a new <code>PasswordChecker</code>.
     */
    public PasswordChecker() {
        this.userPasswordPair = new HashMap<String, String>();
        this.userPasswordPair = this.readUserAndPassword();
    }

    /* ----- Public methods ----- */
    /**
     * The method that checks if the user name and password are in the 
     * hash map,else return false.
     * 
     * @param username The variable for the user name for the login.
     * @param password The variable for the password for the login.
     * @return True if the parameters are in the hash map.
     * @throws CouponException If failes to encrypt the password.
     */
    public boolean isManager(String username, String password) throws CouponException {
        String passwordAfterMD5 = this.encryptPassword(password);
        String fileMD5Password = this.userPasswordPair.get(username);
        if (fileMD5Password != null && fileMD5Password.equals(passwordAfterMD5))
            return true;
        return false;
    }

    /* ----- Private methods ----- */
    /**
     * The method that reads the user name and password in to the hash map.
     * 
     * @return The user name password hash map.
     */
    private HashMap<String, String> readUserAndPassword() {
        HashMap<String, String> userPassword = new HashMap<String, String>();
        userPassword.put("eyal", "73958253628fc640ff18ca3442dbb1e9");
        userPassword.put("nahum", "47cb5c25462395ac35eee1a71aa3c062");
        return userPassword;
    }

    /**
     * The method encrypt the string given as password.
     * 
     * @param password The string we want to encrypt.
     * @return The encrypt password.
     * @throws CouponException If the encoding is not supported or the encryption algorithm doesn't exist.
     */
    private String encryptPassword(String password) throws CouponException {
        byte[] bytesOfMessage;
        MessageDigest md;
        byte[] thedigest;
        try {
            bytesOfMessage = password.getBytes("UTF-8");
            md = MessageDigest.getInstance("MD5");
            thedigest = md.digest(bytesOfMessage);
         
        } catch(UnsupportedEncodingException e) {
            throw new CouponException("Couldn't encrypt password: encoding is unsupported.", e);
        } catch(NoSuchAlgorithmException e) {
            throw new CouponException("Couldn't encrypt password: no such algorithm.", e); 
        }
        return this.fromByteToString(thedigest);
    }
    
    /**
     * The method that converts a byte string to a string
     * 
     * @param thedigest The MD5 password as an array of bytes.
     * @return The byte array as a string.
     */
    private String fromByteToString(byte[] thedigest){
        StringBuffer sb = new StringBuffer();
        for (byte b : thedigest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return new String(sb);
    }
}