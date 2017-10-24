<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="webApplication.bean.Employee" %>
<!DOCTYPE html">
<html>
<head>
	<title>従業員リスト</title>
	<link rel="stylesheet" type="text/css" href="../css/webApplication.css">
</head>
<body>
	<!-- 見出しとして、「従業員リスト」の文字列を出力（ある程度スタイルを設定して） -->
	<!-- <h1 align="center">従業員リスト</h1> -->

	<!-- 外部スタイルシートにスタイルを定義し、それを読み込む形式で。 -->
	<!-- スタイルは、タグに対して設定 -->
	<h1>従業員リスト</h1>

	<!-- サーブレットから送られてきたデータを取得 -->
	<%  ArrayList<Employee> employeeList = (ArrayList<Employee>)request.getAttribute("empList"); %>

	<!-- テーブル形式で全件表示。テーブルヘッダ行も表示する -->
	<!-- まずはテーブルタグとテーブルヘッダタグを普通に書く -->

	<%// TODO 新規登録ボタン、更新ボタン、削除ボタンを追加 %>
	<%// TODO 従業員選択ラジオボタンを列に追加 %>
	<%// TODO ラジオボタンの値には従業員Noを設定 %>
	<table border="1">
		<tr>
			<th>社員番号</th>
			<th>所属名</th>
			<th>氏名</th>
			<th>性別</th>
			<th>年齢</th>
			<th>生年月日</th>
		</tr>
	<!-- テーブル行タグの前後をfor文のスクリプトレットではさんで、データ件数文タグとデータを表示する -->
	<!-- データ各項目を出力するのもスクリプトレットで -->
	<% for(Employee emp : employeeList){ %>
		<tr>
			<td><%=emp.getEmployee_no() %></td>
			<td><%=emp.getShozoku().getShozoku_name() %></td>
			<td><%=emp.getEmployee_name() %></td>
			<td><%=emp.getSex() %></td>
			<td><%=emp.getAge() %></td>
			<td><%=emp.getBirthday() %></td>
		</tr>
	<% }%>


	<!-- テーブルタグを閉じる -->
	</table>
	<%// TODO 各ボタンを実行するJavaScript関数を実装する %>
	<%// TODO 新規登録の場合はただshainTouroku.jsp画面に遷移すればよい %>
	<%// TODO 更新、削除の場合は、選択されたラジオボタンから従業員Noを取得して、リクエストパラメータにセットされるよう仕向ける %>
	<%// TODO →JavaScript単独ではできない。JavaScriptではFormのhidden項目にセットし、Submitでパラメータに入るようにする %>
</body>
</html>