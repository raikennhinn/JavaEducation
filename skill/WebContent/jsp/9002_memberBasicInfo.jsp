<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="skillManagement.enumeration.MemberBasicInfoErrorFlag, skillManagement.bean.MemberBasicBean
, java.util.Properties, skillManagement.util.FileUtility, java.util.ArrayList, skillManagement.bean.KubunBean
, java.util.HashMap, java.util.Set, java.util.Iterator" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="stylesheet" type="text/css" href="../css/skill.css">
<title>スキル管理システム　メンバー基本情報作成</title>
<script>
function controlFocus(flag) {
	if(flag == 'affiliation'){
		document.b_info.affiliation.focus();
	}else if(flag == 'name'){
		document.b_info.name.focus();
	}else if(flag == 'auth'){
		document.b_info.auth.focus();
	}else{
		document.b_info.emp_num.focus();
	}
}
</script>
</head>

<%
MemberBasicInfoErrorFlag flag = (MemberBasicInfoErrorFlag)request.getAttribute("error");
%>

<% if(flag == MemberBasicInfoErrorFlag.NoAff){ %>
<body onLoad="controlFocus('affiliation')">
<% } else if(flag == MemberBasicInfoErrorFlag.NoName) { %>
<body onLoad="controlFocus('name')">
<% } else if(flag == MemberBasicInfoErrorFlag.NoAuth) { %>
<body onLoad="controlFocus('auth')">
<% } else { %>
<body onLoad="controlFocus('emp_num')">
<% } %>

<form name="b_info" method="post" action="../skillManagement/ExeCreateMemberBasicInfoServlet">
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

	<h1>メンバー基本情報作成</h1>
	<hr>
	<div align="center">
	<% String errorMessage = (String)request.getAttribute("errorMessage"); %>
	<% if(errorMessage != null){ %>
	<div class="error_message">
	<%=errorMessage%>
	</div>
	<%}%>
		<table border="0">
			<tr>
				<th>社員番号</th>
				<!-- 社員番号が未入力だった場合、背景色を赤にする -->
				<% if(flag == MemberBasicInfoErrorFlag.NoID){ %>
					<td><input type="text" class="error imeoff" name="emp_num" value="" size="24" maxlength="8" tabindex="1"></td>
				<% } else { %>
					<td><input type="text" class="required imeoff" name="emp_num" value="" size="24" maxlength="8" tabindex="1"></td>
				<% } %>
			</tr>
			<tr>
				<th>所属</th>
				<!-- 所属が未入力だった場合、背景色を赤にする -->
				<% ArrayList<KubunBean> affiliationKubun = (ArrayList<KubunBean>)request.getAttribute("affiliation_list"); %>
				<td>
				<% if(flag == MemberBasicInfoErrorFlag.NoAff){ %>
					<select class="error" name="affiliation" tabindex="2">
				<% } else { %>
					<select class="required" name="affiliation" tabindex="2">
				<% } %>
						<option value=""></option>
						<% for(KubunBean kBean : affiliationKubun){ %>
						<option value="<%=kBean.getAffiliationKubun() %>">
						<%= kBean.getAffiliationKubunName() %>
						</option>
						<% } %>
					</select>
				</td>

			</tr>
			<tr>
				<th>氏名</th>
				<!-- 氏名が未入力だった場合、背景色を赤にする -->
				<% if(flag == MemberBasicInfoErrorFlag.NoName){ %>
					<td><input type="text" class="error" name="name" value="" size="24" maxlength="6" tabindex="3"></td>
				<% } else { %>
					<td><input type="text" class="required" name="name" value="" size="24" maxlength="6" tabindex="3"></td>
				<% } %>
			</tr>
			<tr>
				<th>権限</th>
				<!-- 権限が未入力だった場合、背景色を赤にする -->
				<% HashMap<String, String> map = (HashMap<String, String>)request.getAttribute("auth"); %>
				<td>
				<% if(flag == MemberBasicInfoErrorFlag.NoAuth){ %>
					<select class="error" name="auth" tabindex="4">
				<% } else { %>
					<select class="required" name="auth" tabindex="4">
				<% } %>
						<option value=""></option>
						<%
							Set<String> keys = map.keySet();
							Iterator<String> it = keys.iterator();
							while(it.hasNext()) {
								String key = it.next();
						%>
						<option value="<%=key %>"><%=map.get(key) %></option>
						<% } %>
					</select>
				</td>
			</tr>
		</table>
		<div class="caution_message">

		<%
		Properties properties = FileUtility.createProperties();
		//メッセージ："※作成するメンバーの初期パスワードは、社員番号と同じで設定されます。"
		String message = properties.getProperty("I900201");
		%>
		<%=message%>
		</div>

		<span style="margin:10px;">
		<input type="submit" value="作成" tabindex="5">
		</span>
		<span style="margin:10px;">
		<input type="button" value="戻る" onclick="location.href='../jsp/9001_adminMenu.jsp'" tabindex="6">
		</span>
	</div>
</form>
</body>
</html>