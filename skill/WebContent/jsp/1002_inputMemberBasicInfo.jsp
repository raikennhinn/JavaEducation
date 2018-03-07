<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="skillManagement.enumeration.MemberBasicInfoErrorFlag, skillManagement.bean.MemberBasicBean
, java.util.Properties, skillManagement.util.FileUtility, java.util.ArrayList, skillManagement.bean.KubunBean" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="stylesheet" type="text/css" href="../css/skill.css">
<title>スキル管理システム　メンバー基本情報入力</title>
</head>

<script>
	var gamen = "";
	var match = location.search.match(/gamen=(.*?)(&|$)/);
	if(match) {
	    gamen = decodeURIComponent(match[1]);
	}

	function pageBack() {
		if (gamen == "1003") {
			location.href='../jsp/1003_memberInfo.jsp'
		} else {
			location.href='../jsp/1001_memberList.jsp'
		}
	}

</script>

<form method="post" action="../skillManagement/InputMemberBasicInfoServlet">

<%
MemberBasicBean bean = (MemberBasicBean)session.getAttribute("login_info");
String employeeNumber = bean.getEmployeeNumber();
String affiliationKubunName = bean.getAffiliationKubunName();
String name = bean.getName();

MemberBasicInfoErrorFlag flag = (MemberBasicInfoErrorFlag)request.getAttribute("error");
%>
	<header class="user_header">
		<nav class="user_header_nav"><a href="../skillManagement/LogoutServlet">ログアウト</a></nav>
		<span class="user_header_span">社員番号：<%= employeeNumber %></span>
		<span class="user_header_span">所属：<%= affiliationKubunName %></span>
		<span class="user_header_span">氏名：<%= name %></span>
	</header>

<h1>メンバー基本情報入力</h1>
<hr>
<p>
<label for="" name="emp_num">社員番号</label><%= employeeNumber %>
</p>

<%
	Properties properties = FileUtility.createProperties();
	//メッセージ："※パスワード変更した場合、変更後のパスワードをここにも入力してください。"
	String message1 = properties.getProperty("I100201");
	//メッセージ："※システム登録されたばかりのメンバーの方は必ずパスワード変更をしてください。"
	String message2 = properties.getProperty("W100201");
%>

<p>
<label for="">パスワード</label>
<% if(flag == MemberBasicInfoErrorFlag.NoPW){ %>
<input type="password" name="password" class="error" size="16">&nbsp;
<% } else { %>
<input type="password" name="password" class="required" size="16">&nbsp;
<% } %>
<span class="caution_message"><%=message2%></span>
</p>

<p>
<label for="">パスワード（再度）</label>
<input type="password" name="re_password" class="required" size="16">&nbsp;
<span class="info_message"><%=message1%></span>
</p>

<p><label for="">所属</label>
<p>
<% ArrayList<KubunBean> affiliationKubun = (ArrayList<KubunBean>)request.getAttribute("affiliation_list"); %>
<% if(flag == MemberBasicInfoErrorFlag.NoAff){ %>
	<select class="error" name="belong" tabindex="2">
<% } else { %>
	<select class="required" name="belong" tabindex="2">
<% } %>
		<option value=""></option>
		<% for(KubunBean kBean : affiliationKubun){ %>
		<option value="<%=kBean.getAffiliationKubun() %>">
		<%= kBean.getAffiliationKubunName() %>
		</option>
		<% } %>
	</select>
</p>

<p>
<label for="">氏名</label>
<% if(flag == MemberBasicInfoErrorFlag.NoName){ %>
<input type="text" name="name" class="error" value="" size="24">
<% } else { %>
<input type="text" name="name" class="required" value="<%= name %>" size="24">
<% } %>
</p>

<p>
<label for="">年齢</label>
<input type="text" name="age" value="" size="24">
</p>

<p>
<label for="">性別</label>
<input type="radio" name="sex" value="men" checked="checked">男
<input type="radio" name="sex" value="woman">女
</p>

<p>
<label for="">最終学歴</label>
<input type="text" name="final_education" value="" size="24">
</p>

<p>
<label for="">入社年月</label>
<input type="text" name="entry_year" value="" size="4">年
<input type="text" name="entry_month" value="" size="2">月
</p>

<span style="margin:10px;">
<!-- <input type="button" value="入力完了" onclick="location.href='../jsp/1003_memberInfo.jsp'"> -->
<input type="submit" value="入力完了">
</span>

<span style="margin:10px;">
<input type="button" value="戻る" onclick="pageBack()">
</span>

</form>
</body>
</html>