<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="skillManagement.bean.MemberBasicBean" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="stylesheet" type="text/css" href="../css/skill.css">
<title>スキル管理システム　管理者メニュー</title>
</head>
<body>
<%
MemberBasicBean bean = (MemberBasicBean)session.getAttribute("login_info");
String employeeNumber = bean.getEmployeeNumber();
String affiliationKubunName = bean.getAffiliationKubunName();
String name = bean.getName();
%>
	<header class="user_header">
		<nav class="user_header_nav">
		<a href="LogoutServlet" tabindex="3">ログアウト</a>
		</nav>
		<span class="user_header_span">社員番号：<%= employeeNumber %></span>
		<span class="user_header_span">所属：<%= affiliationKubunName %></span>
		<span class="user_header_span">氏名：<%= name %></span>
	</header>

	<h1>管理者メニュー</h1>
	<hr>
	<ul>
		<li><a href="../skillManagement/DispCreateMemberBasicInfoServlet">メンバー基本情報作成</a></li>
		<li><a href="../skillManagement/MemberListServlet">メンバー一覧</a></li>
	</ul>
</body>
</html>