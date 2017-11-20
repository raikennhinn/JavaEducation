<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="webApplication.bean.Employee" %>
<% // TODO ここでJSTLの使用を宣言 %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html">
<html>
<head>

<title>所属情報詳細</title>
<link rel="stylesheet" type="text/css" href="../css/j-master.css">
</head>


<body>
<!-- セッションの保存されたshozokuMoreを取得する-->
	<c:set var="shozokuMore" value="${sessionScope['shozokuMore']}" />
<!-- ラベルの表示 -->
<h1>所属情報詳細</h1>
<p>所属コード：<label>${shozokuMore.shozoku_code}</label></p>
<p>所属部：<label>${shozokuMore.shozoku_bu}</label></p>
<p>所属課：<label>${shozokuMore.shozoku_ka}</label></p>
<p>所属係：<label>${shozokuMore.shozoku_kakari}</label></p>

<form>
<!-- 閉じるボタンとしてOKボタンの設置 -->
<p><p><input type="button" value=" OK " onclick="window.close()"></p>
</form>
</body>

</html>
