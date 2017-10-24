<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% // TODO ここでJSTLの使用を宣言 %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>従業員登録</title>
	<link rel="stylesheet" type="text/css" href="../css/webApplication.css">
</head>

<body>
<!-- タイトルを見出しで。昨日の一覧画面と同じスタイルを適用する。 -->
	<h1>従業員登録</h1>
		<c:set var="flg" value="${requestScope['flg']}" />
	<c:if test="${flg}">
		<p>${requestScope['mes']}</p>
	</c:if>
	<c:if test="${!flg}">
		<p id="error_msg">${requestScope['mes']}</p>
	</c:if>
<!-- employeeテーブルの各項目（更新者更新日時を除く）を入力するのに適切なフォームコントロールをそれぞれ記述 -->
	<form name="shain" target="_self" method="POST" action="../shainTouroku/">
<% // TODO サーバーでの入力チェックエラーで戻ってきたときに、入力内容がクリアされないようにする %>
<% // TODO 式言語を使ってrequestに保持された従業員の値を取得、表示する %>
<% // TODO JSTLで、requestに従業員が保持されているか判定が必要 %>
		<c:set var="emp" value="${requestScope['emp']}" />
		<p>社員番号:<input type="text" name="employee_code" size="10" value="${emp.employee_no}"></p>

<!-- 所属コードについては、ひとまず単純なテキストボックスでよい -->
		<p>所属コード:<input type="text" name="shozoku_code" size="4" value="${emp.shozoku.shozoku_code}"></p>

		<p>氏名:<input type="text" name="name" size="8" value="${emp.employee_name}"></p>
		<p>氏名カナ：<input type="text" name="namekana" size="16" value="${emp.employee_namekana}"></p>
		<p>性別:
		<c:set var="sex" value="${emp.sex}" />
		<!-- sexが空（null？）なら、もしくはempが空（null？）なら、男を選択された状態でラジオボタンを表示 -->
		<c:if test="${empty sex}">
			<input type="radio" name="sex" value="0" checked>男
			<input type="radio" name="sex" value="1" >女
		</c:if>
		<!-- sexが0なら男のradioをchecked、1なら女のradioをcheckedにする -->
		<c:if test="${sex==\"0\"}">
			<input type="radio" name="sex" value="0" checked>男
			<input type="radio" name="sex" value="1" >女
		</c:if>
		<c:if test="${sex==\"1\"}">
			<input type="radio" name="sex" value="0" >男
			<input type="radio" name="sex" value="1" checked>女
		</c:if>
		</p>

		<p>年齢:<input type="text" name="age" size="3" value="${emp.age}"></p>

		<p>生年月日:<input type="text" name="birthday" size="10" value="${emp.birthdayAtSlash}"></p>

<!-- 登録ボタンとリセットボタンを用意する。
　　　登録ボタンは押下時に入力チェック（後述）と、登録確認ダイアログを表示。OKを押したら登録処理（サーブレット）起動。
	リセットボタンは、HTML標準のリセット機能でよい。
	 -->
		<!-- 登録ボタンとリセットボタン -->
		<p>
			<input type="button" value=" 登録 " onclick="touroku()">
			<input type="reset" name="リセット" value=" 取消 "></p>
	</form>
</body>

<!-- 入力チェック。
　・未入力チェック（必須項目）、
　・桁数チェック（文字数／バイト数オーバーチェック）（テキストボックス全般）、
　・数字チェック（数値項目）
　これらをJavaScriptで行う　-->


	<!-- 定義方法<script src="●●.js"></script> jsファイルにうつす-->
	<script src="../js/ShainTouroku.js" type="text/javascript">
	</script>

</html>