<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="skillManagement.bean.MemberBasicBean, java.util.Properties, skillManagement.util.FileUtility
,skillManagement.bean.MemberListBean, java.util.ArrayList, java.util.TreeMap, java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="stylesheet" type="text/css" href="../css/skill.css">
<title>スキル管理システム　メンバー一覧</title>
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

	<h1>メンバー一覧</h1>
	<hr>
	<div align="center">
		<div class="caution_message">
		<%
		Properties properties = FileUtility.createProperties();
		//メッセージ："※氏名の末尾に(*)がついている方は、基本情報の入力やパスワード設定がされていません。"
		String message = properties.getProperty("I100101");
		%>
		<%=message%>
		</div>
		<% int pageInfo[] = (int[])session.getAttribute("1001_page"); %>

<%
// ページ番号（ページャー）情報を取得して、ページリンクを作成・表示
TreeMap<Integer, Boolean> dispPageNum = (TreeMap<Integer, Boolean>)request.getAttribute("dispPageNum");
TreeMap<String, Boolean> nextBack = (TreeMap<String, Boolean>)request.getAttribute("nextBack");
%>
<!-- 「<<」と「<」リンク -->
<%
	if(nextBack.get("dispBack") == true){
%>
	<a href="../skillManagement/PageLinkClickServlet?nextBack=-5">&lt;&lt;</a>
	&nbsp;&nbsp;
	<a href="../skillManagement/PageLinkClickServlet?nextBack=-1">&lt;</a>
<% }else { %>
	&lt;&lt;&nbsp;&nbsp;&lt;
<%
   }
%>

<!-- ページリンク -->
<%
for(Map.Entry<Integer, Boolean> entry : dispPageNum.entrySet()) {
	if(entry.getValue() == true){
%>
	&nbsp;&nbsp;<a href="../skillManagement/PageLinkClickServlet?page=<%= entry.getKey() %>"><%= entry.getKey() %></a>
<% }else { %>
	&nbsp;&nbsp;<%= entry.getKey() %>
<%
   }
}
%>

<!-- 「>」と「>>」リンク -->
<%
	if(nextBack.get("dispNext") == true){
%>
	&nbsp;&nbsp;
	<a href="../skillManagement/PageLinkClickServlet?nextBack=1">&gt;</a>
	&nbsp;&nbsp;
	<a href="../skillManagement/PageLinkClickServlet?nextBack=5">&gt;&gt;</a>
<% }else { %>
	&nbsp;&nbsp;&gt;&nbsp;&nbsp;&gt;&gt;
<%
   }
%>

		<table border="1">
			<tr>
				<th>社員番号</th>
				<th>所属</th>
				<th>氏名</th>
			</tr>
			<%
			ArrayList<MemberListBean> list = (ArrayList<MemberListBean>)session.getAttribute("1001_member_list");
			int firstMember = (int)request.getAttribute("firstMember");
			int listCount = list.size()-firstMember;
			if(listCount > 15){
				listCount = 15;
			 }
// 			int listCount = 0;
// 			if(list.size()-firstMember > 15){
// 				listCount = 15;
// 			}else{
// 				listCount = list.size()-firstMember;
// 			}
			 %>

			<%
			for(int i = firstMember; i < listCount + firstMember; i++){
				MemberListBean Mbean = list.get(i);
			%>
				<tr>
					<td><%= Mbean.getEmployeeNumber() %></td>
					<td><%= Mbean.getAffiliationKubunName() %></td>
					<td>
					<a href="../skillManagement/NameLinkClickServlet
					?basicStatus=<%=Mbean.getBasicFillInStatus() %>&employeeNum=<%= Mbean.getEmployeeNumber() %>">
					<% if(Mbean.getBasicFillInStatus() == 0){ %>
						<!-- 基本情報記入状態区分が「0（未記入)」の場合 -->
						<%= Mbean.getName()  + "(*)" %>
					<% } else if(Mbean.getBasicFillInStatus() == 1){ %>
						<!-- 基本情報記入状態区分が「1（記入済み）」の場合 -->
						<%= Mbean.getName() %>
					<% } %>
					</a>
					</td>
					<input type=hidden name="basicStatus" value="<%=Mbean.getBasicFillInStatus() %>" />
				</tr>
			<% } %>
		</table>
	</div>
</body>
</html>