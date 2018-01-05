<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<script type="text/javascript">

function getNow() {
	var now = new Date();
	var year = now.getFullYear();
	var mon = now.getMonth()+1; //１を足すこと
	var day = now.getDate();
	var hour = now.getHours();
	var min = now.getMinutes();

	//出力用
	var s = year + "/" + mon + "/" + day + " ";
	var h = hour + ":" + min;
	//document.write(s + h);
	document.getElementById("view_time").innerHTML = s + h;
}

function logout_action(){
	var logout_form = document.forms['logout'];
	// サーバーにリクエストを送信
	logout_form.submit();
}

</script>
<style>
#logout_link {
	color:#00ff00;
}

</style>
<!-- </head> -->
<!-- <body> -->

	<c:set var="login_info" value="${sessionScope['login_info']}" />
	<!-- ログインユーザーサーブレットから、IDに対する名前の取得  -->

	<p>J-Master<p>
	<p>ユーザー名</p>
	<p>${login_info.name}</p>
	<p></p>
	<p>現在日時</p>
	<!-- 分単位（時間が動くように）設定する -->
	<script type="text/javascript">
		setInterval("getNow()", 100);
	</script>
<form name="logout" method="post" action="../Logout/">
	<p><span id="view_time"></span></p>
	<input type="hidden" name="id" value="${login_info.id}"/>
	<input type="hidden" name="name" value="${login_info.name}"/>
	<p><A id="logout_link" Href="javascript:void(0);" onclick="logout_action()">ログアウト</A></p>


</form>
<!-- </body> -->


<!-- </html> -->