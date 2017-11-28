<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="webApplication.bean.Employee" %>
<% // TODO ここでJSTLの使用を宣言 %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html">
<html>
<head>
	<title>従業員変更結果</title>
	<link rel="stylesheet" type="text/css" href="../css/j-master.css">
</head>


<body>
	<div id="header_field">
		<%@ include file="UserInfoHedder.jsp" %>
	</div>
	<div id="main_field">
	<h1>従業員変更結果</h1>
	<c:set var="emp" value="${requestScope['emp']}" />
<% // TODO 登録成否メッセージをサーバーから取得して表示。成功か失敗かで色変え。成功失敗は別途フラグで判定（JSTL使用） %>
	<c:set var="flg" value="${requestScope['flg']}" />
	<c:if test="${flg}">
		<p>${requestScope['mes']}</p>
	</c:if>
	<c:if test="${!flg}">
		<p id="error_msg">${requestScope['mes']}</p>
	</c:if>
<% // TODO 式言語を使って値を取得、表示する %>
	<p>社員番号:<c:out value="${emp.employee_no}" escapeXml="true" /></p>
	<p>所属コード:<c:out value="${emp.shozoku.shozoku_code}" escapeXml="true" /></p>
	<p>氏名:<c:out value="${emp.employee_name}" escapeXml="true" /></p>
	<p>氏名カナ:<c:out value="${emp.employee_namekana}" escapeXml="true" /></p>
	<p>性別:<c:out value="${emp.sexAtKanji}" escapeXml="true" /></p>
	<p>年齢:
	<c:if test="${emp.age==-1}">
	</c:if>
	<c:if test="${emp.age!=-1}">
		<c:out value="${emp.age}" escapeXml="true" />
	</c:if>
	</p>
	<p>生年月日:<c:out value="${emp.birthdayAtSlash}" escapeXml="true" /></p>
	<p>都道府県:<c:out value="${emp.pref_CD}" escapeXml="true" /></p>
	<p>住所:<c:out value="${emp.address}" escapeXml="true" /></p>
	<p>メールアドレス:<c:out value="${emp.mail_address}" escapeXml="true" /></p>
	<p>備考:<c:out value="${emp.note}" escapeXml="true" /></p>
<% // TODO 「従業員一覧画面に戻る」リンクをここに追加 %>
	<a href="../EmployeeList/">従業員一覧に戻る</a>
	</div>
</body>
</html>