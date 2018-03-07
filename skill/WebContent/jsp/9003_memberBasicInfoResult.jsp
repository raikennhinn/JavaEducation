<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="skillManagement.bean.MemberBasicBean, java.util.ArrayList, skillManagement.bean.KubunBean, java.util.HashMap" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="stylesheet" type="text/css" href="../css/skill.css">
<title>スキル管理システム　メンバー基本情報作成結果</title>
</head>
<body>
<%
MemberBasicBean bean = (MemberBasicBean)session.getAttribute("login_info");
String employeeNumber = bean.getEmployeeNumber();
String affiliationKubunName = bean.getAffiliationKubunName();
String name = bean.getName();
%>
	<header class="user_header">
		<nav class="user_header_nav"><a href="../skillManagement/LogoutServlet">ログアウト</a></nav>
		<span class="user_header_span">社員番号：<%= employeeNumber %></span>
		<span class="user_header_span">所属：<%= affiliationKubunName %></span>
		<span class="user_header_span">氏名：<%= name %></span>
	</header>
	<h1>メンバー基本情報作成結果</h1>
	<hr>
	<div align="center">
	<%
	String message1 = (String)request.getAttribute("message1");
	String message2 = (String)request.getAttribute("message2");
	%>
	<% if(message1 != null){ %>
	<div class="info_message"><%=message1%></div>
	<% } else if(message2 != null){ %>
	<div class="error_message"><%=message2%></div>
	<% } %>

		<table border="0">
			<tr>
				<th>社員番号</th>
				<td><%= request.getParameter("emp_num") %></td>
			</tr>
			<tr>
				<th>所属</th>
				<td><%=request.getAttribute("affiliation_result") %></td>
			</tr>
			<tr>
				<th>氏名</th>
				<td><%= request.getParameter("name") %></td>
			</tr>
			<tr>
				<th>権限</th>
				<td><%=request.getAttribute("auth_result") %></td>
			</tr>
		</table>
		<span style="margin:10px;">
		<input type="button" value="管理者メニューに戻る" onclick="location.href='../jsp/9001_adminMenu.jsp'" tabindex="1">
		</span>
		<span style="margin:10px;">
 		<input type="button" value="さらに作成" onclick="location.href='../skillManagement/DispCreateMemberBasicInfoServlet'" tabindex="2">
		</span>
	</div>
</body>
</html>