"use strict";

/*
 * Constructs a coupon exception object.
 */
function CouponException(msg, couponid) {
	this.message = msg.concat(" id : ").concat(couponid);
	this.stack = (new Error()).stack;
}
CouponException.prototype = Object.create(Error.prototype);
CouponException.prototype.name = "CouponException";

/*
 * A basic CouponException test.
 */
function testCouponException() {
	console.log("Beginning CouponException test...");
	try {
		// Try throwing a CouponException and see if catching it works.
		throw new CouponException("coupon error", 15);
	}
	catch(e) {
		if(e instanceof CouponException) {
			// We caught it.
			console.log("error is a CouponException");
			console.log(e.message);
			console.log(e.stack);
		}
		else {
			// We didn't catch it.
			console.log("CouponException test failed.\n")
			throw e;
		}
	}
	console.log("CouponException test passed.\n");
}