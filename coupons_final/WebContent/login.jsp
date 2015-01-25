<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" name="viewport" content="width=device-width, initial-scale=1">
<title>Manager Login</title>
<script type="text/javascript" src="/coupons/jquery-1.11.2.js"></script>
<link rel="stylesheet" href="//code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.css" />
<script type="text/javascript" src="//code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.js"></script>

<script type="text/javascript">
    var frm = $('#user_login');
    $('#user_login').submit(function() {
        $.ajax({
            type : frm.attr('method'),
            url : frm.attr('action'),
            data : frm.serializeArray(),
        });
    });
</script>

</head>
<body>
    <div data-role="page">
        <div data-role="header">
            <a href="/coupons/index.html" data-icon="back">Back</a>
            <h1>Manager Login</h1>
        </div>
        <div data-role="main" class="ui-content">
            <form method="post" action="checkPassword" id="user_login">
                <div class="ui-field-contain">
                    <label for="user">User:</label>
                    <input type="text" name="user" id="user">
                    <label for="password">Password:</label>
                    <input type="password" name="password" id="password">
                </div>
                <input type="submit" data-inline="true" id="submit" value="Login">
            </form>
        </div>

        <div data-role="footer">
            <h5>Nahum Farchi and Eyal Grossman, 2015, Telhai &copy</h5>
        </div>
    </div>
</body>
</html>