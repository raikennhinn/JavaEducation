<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% // TODO ここでJSTLの使用を宣言 %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>従業員更新</title>
	<!-- Css使用 -->
	<link rel="stylesheet" type="text/css" href="../css/webApplication.css">
</head>
<body onLoad="document.update.shozoku_code.focus()">
	<h1>従業員更新</h1>
	<!-- 一覧から取得したコードのひとつが入る→URLに直接入力したものを入れる -->
	<!-- formタグを使用し、各データのテキストボックスを記述 -->
	<!-- 初期値（社員コードを元にひっぱってきたデータを表示する） -->
	<%//従業員コードに関しては、サーブレットから取り出した値を表示すればよいか %>
	<c:set var="emp" value="${requestScope['emp']}" />

	<form name="update" target="_self" method="POST" action="../EmployeeUpdate/">
	<p>従業員No：<label > ${emp.employee_no} </label></p>
	<p>所属コード:<input type="text" style="ime-mode:disabled;" maxlength="3" name="shozoku_code" size="4" value="${emp.shozoku.shozoku_code}"></p>
	<p>氏名:<input type="text" name="name" size="8" value="${emp.employee_name}"></p>
	<p>氏名カナ：<input type="text" name="namekana" size="16" value="${emp.employee_namekana}"></p>
			<p>性別:
		<c:set var="sex" value="${emp.sex}" />
		<!-- sexが空（null？）なら、もしくはempが空（null？）なら、男を選択された状態でラジオボタンを表示 -->
		<c:if test="${empty emp.sex}">
			<input type="radio" name="sex" value="0" checked>男
			<input type="radio" name="sex" value="1" >女
		</c:if>
		<!-- sexが0なら男のradioをchecked、1なら女のradioをcheckedにする -->
		<c:if test="${emp.sex==\"0\"}">
			<input type="radio" name="sex" value="0" checked>男
			<input type="radio" name="sex" value="1" >女
		</c:if>
		<c:if test="${emp.sex==\"1\"}">
			<input type="radio" name="sex" value="0" >男
			<input type="radio" name="sex" value="1" checked>女
		</c:if>
		</p>


	<p>年齢:
	<c:if test="${emp.age==-1}">
		<input type="text" name="age" size="3" value="">
	</c:if>
	<c:if test="${emp.age!=-1}">
		<input type="text" name="age" size="3" value="${emp.age}">
	</c:if>
	</p>
	<p>生年月日:<input type="text" name="birthday" size="10" value="${emp.birthdayAtSlash}"></p>
	<p>

	<p><!-- メッセージ表示欄--></p>
	<!--③メッセージMapからメッセージ文字列を取得し、エラーチェックイベントに組み込む -->
	<!-- ⑪の項目が入る valueに何の値を入れればよいのか？URLの値を-->
	<p><input type="hidden" name="employee_no" value="${emp.employee_no}" ></p>

	<!-- 登録と取り消しボタン -->
	<p><input type="button" value=" 登録 " onclick="check()">
	<input type="reset" name="リセット" value=" 取消 "  onclick="ColorRiset()"></p>
	</form>
</body>
	<!-- JavaScriptを指定 -->
	<script src="../js/EmployeeUpdate.js" type="text/javascript">
	</script>
</html>