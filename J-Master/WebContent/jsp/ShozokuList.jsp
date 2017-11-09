<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="webApplication.bean.Shozoku" %>
<!DOCTYPE html">
<html>
<head>
	<title>所属コード一覧</title>
	<link rel="stylesheet" type="text/css" href="../css/j-master.css">
</head>
<body>

	<!-- 見出しとして、「従業員リスト」の文字列を出力（ある程度スタイルを設定して） -->
	<!-- <h1 align="center">従業員リスト</h1> -->

	<!-- 外部スタイルシートにスタイルを定義し、それを読み込む形式で。 -->
	<!-- スタイルは、タグに対して設定 -->

	<div id="header_field">
		<%@ include file="UserInfoHedder.jsp" %>
	</div>
	<div id="main_field">
	<h1>所属コード一覧</h1>
	<p><a href="../ReturnMenu/">メニューに戻る</a></p>
	<!-- サーブレットから送られてきたデータを取得  ShozokuList-->
	<!-- req.setAttribute("ShozokuList", szkItiran); -->
	<c:set var="ShozokuList" value="${requestScope['ShozokuList']}" />

	<!-- テーブル形式で全件表示。テーブルヘッダ行も表示する -->
	<!-- まずはテーブルタグとテーブルヘッダタグを普通に書く -->

	<table border="1">
		<tr>
			<th>所属コード</th>
			<th>所属部</th>
			<th>所属課</th>
			<th>所属係</th>
			<th>所属名</th>
		</tr>

	<c:forEach var="sh" items="${ShozokuList}">
		<tr>
			<td>${sh.shozoku_code}</td>
			<td>${sh.shozoku_bu}</td>
			<td>${sh.shozoku_ka}</td>
			<td>${sh.shozoku_kakari}</td>
			<td>${sh.shozoku_bu} ${sh.shozoku_ka} ${sh.shozoku_kakari}</td>

		</tr>
	</c:forEach>

	<!-- テーブルタグを閉じる -->
	</table>


	</div>
</body>
</html>