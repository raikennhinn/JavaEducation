<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>J-Master ～従業員情報システム～　ログイン</title>
</head>
<body onload="send();">
<% //response.sendRedirect("/J-Master/UserLoginInit/"); %>
<form method="GET" name="send" action="./UserLoginInit/"></form>
<script>
	function send() {
		document.forms['send'].submit();
	}
</script>
</body>
</html>