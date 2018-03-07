<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="skillManagement.enumeration.MemberBasicInfoErrorFlag, skillManagement.bean.MemberBasicBean" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="stylesheet" type="text/css" href="../css/skill.css">
<title>スキル管理システム　ログイン</title>
<style>
@keyframes titleframe {
	from {
		color: #ffffff;
		text-shadow: 0px 0px 0px #ffffff;
		top: 5px;
	}
	to {
		color: #000000;
		text-shadow: 5px 5px 2px #dddddd;
		top: 30px;
	}
}
#system_title_animation {
	text-align:center;
	position:relative;
	animation: titleframe 2.0s ease 0.5s 1 normal both;
}
#system_title {
	text-align:center;
	position:relative;
	top: 30px;
}
#title_space {
	height:80px;
}
</style>
<script>
function controlFocus(flag) {
	if(flag == 'pass'){
		document.login.pass.focus();
	}else{
		document.login.emp_num.focus();
	}
}

</script>
</head>

<%
String message = (String)request.getAttribute("message");
String empNum = (String)request.getAttribute("emp_num");
MemberBasicInfoErrorFlag flag = (MemberBasicInfoErrorFlag)request.getAttribute("error");
%>

<% if(flag == MemberBasicInfoErrorFlag.NoPW){ %>
<body onLoad="controlFocus('pass')">
<% } else { %>
<body onLoad="controlFocus('emp_num')">
<% } %>

<div id="title_space">
<% if(flag == null || flag == MemberBasicInfoErrorFlag.NoError) { %>
<h1 id="system_title_animation">スキル管理システム</h1>
<% } else { %>
<h1 id="system_title">スキル管理システム</h1>
<% } %>
</div>
<hr>
<div align="center">
<% if(message != null){ %>
<div class="error_message">
<%=message%>
</div>
<%}%>

<form name="login" method="post" action="../skillManagement/LoginServlet">
	<table border="0">
		<tr>
			<th>社員番号</th>
			<!-- 社員番号または両方が未入力だった場合、背景色を赤にする -->
			<% if(flag == MemberBasicInfoErrorFlag.NoBoth || flag == MemberBasicInfoErrorFlag.NoID){ %>
				<td><input type="text" class="error imeoff" name="emp_num" value="" size="24"></td>
			<% } else if(flag == MemberBasicInfoErrorFlag.NoPW){ %>
				<td><input type="text" class="required imeoff" name="emp_num" value="<%=empNum%>" size="24"></td>
			<% } else { %>
				<td><input type="text" class="required imeoff" name="emp_num" value="" size="24"></td>
			<% } %>
		</tr>
		<tr>
			<th>パスワード</th>
			<!-- パスワードまたは両方が未入力だった場合、背景色を赤にする -->
			<% if(flag == MemberBasicInfoErrorFlag.NoBoth || flag == MemberBasicInfoErrorFlag.NoPW){ %>
				<td><input type="password" class="error" name="pass" value="" size="24"></td>
			<% } else { %>
				<td><input type="password" class="required" name="pass" value="" size="24"></td>
			<% } %>
		</tr>
		<tr>
			<td colspan="2" align="center"><input type="submit" value="ログイン"></td>
		</tr>
	</table>
</form>
</div>
</body>
</html>