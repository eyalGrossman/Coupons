/**
 * Functions for displaying the coupons.
 */

"use strict";

/*
 * Create the coupon's inner list with all its details.
 * 
 * couponJSON - JSON data describing the coupon
 * 
 * Return a jquery object that's a list describing the coupon.
 */
function createCouponDetails(couponJSON) {
    var $couponDetails = $("<ul data-role='listview' data-theme='c'>");

    // Import the configuration file and create a details list.
    $.getScript('config.js', function() {
        $.each(CONFIG.KEYS_FORMAT, function(i, key) {
            var $details = $("<li>");
            if (key === "image") {
                $details.append('<img src="' + couponJSON.image + '">');
            } else {
                $details.append("<h3>" + key + "</h3>");
                $details.append("<p>" + couponJSON[key] + "</p>")
            }
            $couponDetails.append($details);
        })
        $couponDetails.listview("refresh");
    });

    return $couponDetails;
}

/*
 * Create a coupon jquery object from the given coupon JSON.
 * 
 * couponJSON - JSON data describing the coupon to be created.
 * addToCartButtonFlag - whether to add a 'add to cart' button flag.
 * removeFromCartButtonFlag - wether to add a 'remove from cart' button flag.
 * 
 * Return a jquery object with an inner list describing the coupon.
 */
function createCoupon(couponJSON, addToCartButtonFlag, removeFromCartButtonFlag) {
    var $coupon = $("<li>");
    // Outer coupon.
    var $button = $("<a href='#'>");
    $button.append("<img src='" + couponJSON.image + "'>");
    $button.append("<h3>" + couponJSON.name + "</h3>");
    $coupon.append($button);
    $coupon.append("<p>" + couponJSON.description + "</p>");
    if (addToCartButtonFlag === true) {
        // A button for adding to cart.
        $coupon
                .append('<a href="'
                        + CONFIG.CONTROLLER_PATH.ADD
                        + '?id='
                        + couponJSON.id
                        + '"data-rel="dialog" data-transition="slideup" data-icon="check">Add To Cart</a>');
    }
    if (removeFromCartButtonFlag === true) {
        // A button for removing from cart.
        $coupon
                .append('<a href="'
                        + CONFIG.CONTROLLER_PATH.REMOVE
                        + '?id='
                        + couponJSON.id
                        + '" data-rel="dialog" data-transition="slideup" data-icon="delete">Remove From Cart</a>');
    }

    // Create the inner list with the coupon details.
    var $couponDetails = createCouponDetails(couponJSON);
    $coupon.append($couponDetails);

    return $coupon
}

/*
 * Create a jquery object with a list of categories from the given coupons data.
 * couponsData - the data with all of the categories mapped to an array of
 * coupons in JSON format. categoryName - the category to create.
 * 
 * Return a jquery object that's a list with all of the coupons from the given
 * category.
 */
function createCategory(couponsData, categoryName, addToCartButtonFlag,
        removeFromCartButtonFlag) {
    // The JSON of the given category.
    var couponsCategoryJSON = couponsData[categoryName];
    // Create the category jquery object.
    var $category = $("<li id='" + categoryName + "'><a href='#'>"
            + categoryName + "</a></li>");
    // Create a nested list with all of the coupons from the given category.
    var $couponsList = $("<ul data-role='listview' data-theme='c'>");
    $.each(couponsCategoryJSON, function(i, coup) {
        $couponsList.append(createCoupon(coup, addToCartButtonFlag,
                removeFromCartButtonFlag))
    });
    $category.append($couponsList);
    return $category;
}

/*
 * Show the coupons for the user (with option to add to cart and remove from
 * cart)
 * 
 * couponsDATA - The coupons in JSON format.
 */
function showCouponsUser(couponsData) {
    // Add a section for the given category with a list of coupons inside it.
    console.log("getting and showing coupons...");
    console.log(couponsData);
    var $categoryList = $("#coupons_categories");
    for ( var categoryName in couponsData) {
        var $category = createCategory(couponsData, categoryName, true, false);
        $categoryList.append($category);
    }
    // Refresh the list so it's updated on the screen.
    $categoryList.listview("refresh");
}

/*
 * Show the coupons for the cart view (remove button only - no add button).
 * 
 * couponsData - The coupons in JSON format.
 */
function showCouponsCart(couponsData) {
    // Add a section for the given category with a list of coupons inside it.
    console.log("getting and showing coupons...");
    console.log(couponsData);
    var $categoryList = $("#coupons_categories");
    for ( var categoryName in couponsData) {
        var $category = createCategory(couponsData, categoryName, false, true);
        $categoryList.append($category);
    }
    // Refresh the list so it's updated on the screen.
    $categoryList.listview("refresh");
}

/*
 * Show the coupons for the manager (no remove or add buttons).
 * 
 * couponsData - The coupons in JSON format.
 */
function showCouponsManager(couponsData) {
    // Add a section for the given category with a list of coupons inside it.
    console.log("getting and showing coupons...");
    console.log(couponsData);
    var $categoryList = $("#coupons_categories");
    for ( var categoryName in couponsData) {
        var $category = createCategory(couponsData, categoryName, false, false);
        $categoryList.append($category);
    }
    // Refresh the list so it's updated on the screen.
    $categoryList.listview("refresh");
}