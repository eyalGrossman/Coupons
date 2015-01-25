<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Coupons Manager</title>
<script type="text/javascript" src="/coupons/jquery-1.11.2.js"></script>
<link rel="stylesheet" href="//code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.css" />
<script type="text/javascript" src="//code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.js"></script>

<script type="text/javascript">
    var frm = $('#Insert_coupon');
    frm.submit(function() {
        $.ajax({
            type : frm.attr('method'),
            url : frm.attr('action'),
            data : frm.serializeArray(),
            success : function(data) {
                alert(data);
                location.reload();
                $('#Insert_coupon')[0].reset(); // To reset form fields;
            }
        });
    });
</script>
<script type="text/javascript">
    var del = $('#delete_coupon');
    del.submit(function() {
        $.ajax({
            type : del.attr('method'),
            url : del.attr('action'),
            data : del.serialize(),
            success : function(del) {
                alert(del);
                window.locationre = del.url;
            }
        });
    });
</script>

</head>
<body>
    <div data-role="page">
    
        <div data-role="header">
            <a href="/coupons/index.html" data-icon="back">Back</a>
            <h1>Coupon Manager</h1>
        </div>

        <div data-role="main" class="ui-content">
            <form method="post" action="add" id="insert_coupon">
                <div class="ui-field-contain">
                    <br><label for="name">Name:</label>
                    <br><input type="text" name="name" id="name">
                    
                    <br><label for="description">Description:</label>
                    <br><input type="text" name="description" id="description">
                    
                    <br><label for="image">Image:</label>
                    <br><input type="text" name="image" id="image" placeholder="choose image...">
                    
                    <br><label for="category">Category:</label>
                    <br><input type="text" name="category" id="category">
                    
                    <br><label for="Date">Date:</label>
                    <br><input type="text" name="date" id="date" placeholder="yyyy/MM/dd HH:mm:ss">
                    
                    <br><label for="Longitude">Longitude:</label>
                    <br><input type="text" name="longitude" id="longitude">
                    
                    <br><label for="Latitude">Latitude:</label>
                    <br><input type="text" name="latitude" id="latitude">
                </div>
                <input type="submit" data-inline="true" id="submit" value="Add coupon">
            </form>
        </div>
        
        <div data-role="main" class="ui-content">
            <form method="post" action="update" id="insert_coupon">
                <div class="ui-field-contain">
                    <br><label for="name">Name:</label>
                    <br><input type="text" name="name" id="name">
                    
                    <br><label for="description">Description:</label>
                    <br><input type="text" name="description" id="description">
                    
                    <br><label for="image">Image:</label>
                    <br><input type="text" name="image" id="image" placeholder="choose image...">
                    
                    <br><label for="category">Category:</label>
                    <br><input type="text" name="category" id="category">
                    
                    <br><label for="Date">Date:</label>
                    <br><input type="text" name="date" id="date" placeholder="yyyy/MM/dd HH:mm:ss">
                    
                    <br><label for="Longitude">Longitude:</label>
                    <br><input type="text" name="longitude" id="longitude">
                    
                    <br><label for="Latitude">Latitude:</label>
                    <br><input type="text" name="latitude" id="latitude">
                </div>
                <input type="submit" data-inline="true" id="submit" value="Update Coupon">
            </form>
        </div>

        <div data-role="main" class="ui-content">
            <form method="post" action="delete" id="delete_coupon">
                <div class="ui-field-contain">
                    <label for="name">Delete id:</label>
                    <input type="text" name="deleteID" id="deleteID">
                </div>
                <input type="submit" data-inline="true" id="submit" value="Delete coupon">
            </form>
        </div>
        
        <div>
            <a href="/coupons/showcouponsall.html" rel="external">
                <input type="submit" data-inline="true" id="submit" value="Show Coupons">
            </a>
        </div>

        <div>
            <form method="get" action="logout" id="log_out">
                <input type="submit" data-inline="true" id="submit" value="Log out">
            </form>
        </div>

        <div data-role="footer">
            <h5>Nahum Farchi and Eyal Grossman, 2015, Telhai &copy</h5>
        </div>
    </div>
</body>
</html>
