<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="webApplication.bean.Employee" %>
<!DOCTYPE html">
<html>
<head>
	<title>従業員リスト</title>
	<link rel="stylesheet" type="text/css" href="../css/j-master.css">
	<script type="text/javascript">
	//ラジオボタンの入力チェック
	function check(actionURL){
		//各項目のチェック実施
		//document.forms.(参照するフォームのid).(参照するコントロールのid)
		//empNoUp_form.empNoUp
		var empNoUp_form = document.forms['empNoUpdate'];
		var flag = 0;
		for (i = 0; i < document.empNoUpdate.empNoUp.length; i++){
			if (document.empNoUpdate.empNoUp[i].checked){
				flag = 1;
			}
		}
		if(flag == 0){
			alert("ラジオボタンが選択されていません");
			return;
		}else{
			empNoUp_form.action = actionURL;
			empNoUp_form.submit();
		}
	}





	</script>
</head>
<body>
	<div id="header_field">
		<%@ include file="UserInfoHedder.jsp" %>
	</div>
	<div id="main_field">
	<form name="empNoUpdate" target="_self" method="POST">
	<!-- 見出しとして、「従業員リスト」の文字列を出力（ある程度スタイルを設定して） -->
	<!-- <h1 align="center">従業員リスト</h1> -->

	<!-- 外部スタイルシートにスタイルを定義し、それを読み込む形式で。 -->
	<!-- スタイルは、タグに対して設定 -->
	<h1>従業員リスト</h1>

	<!-- サーブレットから送られてきたデータを取得 -->
	<!-- req.setAttribute("empList", employeeList); -->
	<c:set var="empList" value="${requestScope['empList']}" />
	<!-- チェックが入っているかどうかの判定を入れるひつようがある -->
	<p><input type="button" value=" 更新 " onclick="check('../EmployeeUpdateInit/')">
	<input type="button" value="新規登録" onclick="location.href='../shainTourokuInit/'">
	<input type="button" value="削除" onclick="check('../EmployeeDelete/')"></p>
	<p><a href="../ReturnMenu/">メニューへ戻る</a></p>

	<c:set var="flg" value="${requestScope['flg']}" />
	<c:if test="${flg}">
		<p>${requestScope['mes']}</p>
	</c:if>
	<c:if test="${!flg}">
		<p id="error_msg">${requestScope['mes']}</p>
	</c:if>

	<!-- テーブル形式で全件表示。テーブルヘッダ行も表示する -->
	<!-- まずはテーブルタグとテーブルヘッダタグを普通に書く -->

	<%// TODO 新規登録ボタン、更新ボタン、削除ボタンを追加 %>
	<%// TODO 従業員選択ラジオボタンを列に追加 %>
	<%// TODO ラジオボタンの値には従業員Noを設定 %>
	<table border="1">
		<tr>
			<th>更新</th>
			<th>社員番号</th>
			<th>所属名</th>
			<th>氏名</th>
			<th>性別</th>
			<th>年齢</th>
			<th>生年月日</th>
		</tr>
	<!-- テーブル行タグの前後をfor文のスクリプトレットではさんで、データ件数文タグとデータを表示する -->
	<!-- データ各項目を出力するのもスクリプトレットで -->

	<c:forEach var="emp" items="${empList}">
		<tr>
			<td><input type="radio" name="empNoUp" value="${emp.employee_no}" ></td>
			<td>${emp.employee_no}</td>
			<td>${emp.shozoku.shozoku_code}</td>
			<td>${emp.employee_name}</td>
			<td>${emp.sex}</td>
			<td>
				<c:if test="${emp.age==0}">
				</c:if>
				<c:if test="${emp.age!=0}">
					${emp.age}
				</c:if>
			</td>
			<td>${emp.birthday}</td>
		</tr>
	</c:forEach>
	<!-- テーブルタグを閉じる -->
	</table>

	<%// TODO 各ボタンを実行するJavaScript関数を実装する %>
	<%// TODO 新規登録の場合はただshainTouroku.jsp画面に遷移すればよい %>
	<%// TODO 更新、削除の場合は、選択されたラジオボタンから従業員Noを取得して、リクエストパラメータにセットされるよう仕向ける %>
	<%// TODO →JavaScript単独ではできない。JavaScriptではFormのhidden項目にセットし、Submitでパラメータに入るようにする %>
	</form>
	</div>
</body>
</html>