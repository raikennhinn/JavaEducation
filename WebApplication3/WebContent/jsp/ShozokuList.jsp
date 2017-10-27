<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="webApplication.bean.Shozoku" %>
<!DOCTYPE html">
<html>
<head>
	<title>所属コード一覧</title>
	<link rel="stylesheet" type="text/css" href="../css/webApplication.css">
</head>
<body>
	<!-- 見出しとして、「従業員リスト」の文字列を出力（ある程度スタイルを設定して） -->
	<!-- <h1 align="center">従業員リスト</h1> -->

	<!-- 外部スタイルシートにスタイルを定義し、それを読み込む形式で。 -->
	<!-- スタイルは、タグに対して設定 -->
	<h1>所属コード一覧</h1>

	<!-- サーブレットから送られてきたデータを取得 -->
	<%  ArrayList<Shozoku> szkItiran = (ArrayList<Shozoku>)request.getAttribute("ShozokuList"); %>

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
	<% for(Shozoku shzk : szkItiran){ %>
		<tr>
			<td><%=shzk.getShozoku_code() %></td>
			<td><%=shzk.getShozoku_bu() %></td>
			<td><%=shzk.getShozoku_ka() %></td>
			<td><%=shzk.getShozoku_kakari() %></td>
			<td><%=shzk.printName() %></td>
		</tr>
	<% }%>
	<!-- テーブル行タグの前後をfor文のスクリプトレットではさんで、データ件数文タグとデータを表示する -->
	<!-- データ各項目を出力するのもスクリプトレットで -->


	<!-- テーブルタグを閉じる -->
	</table>
</body>
</html>