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
<script>
	function selectLeader(shozoku_code, index) {
		var ret = showModalDialog('../ShozokuLeaderSelect/?ShozokuCode=' + shozoku_code,
				'', 'dialogHeight:350px;dialogWidth:370px');
	//	alert("retの種類:" + typeof ret);
		if (typeof ret != 'object') {
			return false;
		}

		// テーブル行（ラベルとhidden）に、jsonからデータを取得してセット
		var shozoku_id = 'shozoku_' + shozoku_code;


		// ラベルには[従業員No:従業員名]形式
		// hiddenには[従業員No]のみ
		document.getElementById(shozoku_id).innerHTML = ret[0].employee_no + ':' + ret[0].employee_name;
		var formList = document.forms['shozokuListForm'];
		var empShozoku = formList.elements['hidden_employee_no'][index];
		empShozoku.value = ret[0].employee_no +"--"+ shozoku_code;
	}

	function update(){

		var res = confirm("この内容で登録してもよいですか？");
		if(res){
			// サーバーにリクエストを送信
			var leader_form = document.forms['shozokuListForm'];
			leader_form.action = "../ShozokuLeaderUpdate/";
			leader_form.submit();
		}

	}

</script>

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

	<c:set var="flg" value="${requestScope['flg']}" />
	<c:if test="${flg}">
		<p>${requestScope['mes']}</p>
	</c:if>
	<c:if test="${!flg}">
		<p id="error_msg">${requestScope['mes']}</p>
	</c:if>
	<!-- サーブレットから送られてきたデータを取得  ShozokuList-->
	<!-- req.setAttribute("ShozokuList", szkItiran); -->
	<c:set var="ShozokuList" value="${requestScope['ShozokuList']}" />

	<!-- テーブル形式で全件表示。テーブルヘッダ行も表示する -->
	<!-- まずはテーブルタグとテーブルヘッダタグを普通に書く -->
	<form name="shozokuListForm" target="_self" method="POST">
		<!-- 所属長の登録を実行する  所属長登録サーブレットへ接続-->
		<p><input type="button" value="所属長登録" onclick="update()"/></p>

		<table border="1">

			<tr>
				<th>所属コード</th>
				<th>所属部</th>
				<th>所属課</th>
				<th>所属係</th>
				<th>所属名</th>
				<th colspan="2">所属長 選択</th>
			</tr>

		<c:forEach var="sh" items="${ShozokuList}" varStatus="status">
			<tr>
				<td>${sh.shozoku_code}</td>
				<td>${sh.shozoku_bu}</td>
				<td>${sh.shozoku_ka}</td>
				<td>${sh.shozoku_kakari}</td>
				<td>${sh.shozoku_bu} ${sh.shozoku_ka} ${sh.shozoku_kakari}</td>
				<td id="shozoku_${sh.shozoku_code}">
					${sh.shozoku_leader}
				</td>
				<!-- 所属長選択サブ画面へ遷移する　所属コードをパラメータとして渡す-->
				<td>
					<input type="button" value="選択" onclick="selectLeader('${sh.shozoku_code}', '${status.index}')" />
					<input type="hidden" name="hidden_employee_no" />
				</td>
			</tr>
		</c:forEach>

		<!-- テーブルタグを閉じる -->
		</table>

	</form>
	</div>
</body>
</html>