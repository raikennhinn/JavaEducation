<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" http-equiv="X-UA-Compatible" content="IE=edge" />
<title>J-Master ～従業員情報システム～　ログイン</title>
<link rel="stylesheet" type="text/css" href="../css/j-master.css">
<style>
/* タイトル関連スタイル */
@keyframes titleframe {
	from {
		color: #ffffff;
		text-shadow: 0px 0px 0px #ffffff;
		top: 5px;
	}
	to {
		color: #000000;
		text-shadow: 5px 5px 2px #dddddd;
		top: 20px;
	}
}
#system_title_animation {
	text-align:center;
	position:relative;
	animation: titleframe 2.0s ease 0.0s 1 normal both;
}
#system_title {
	text-align:center;
	position:relative;
	top: 20px;
}
#title_space {
	height:80px;
}

/* 社員画像関連スタイル */
@keyframes shainframe {
	from {
		opacity: 0;
		left: 0px;
	}
	to {
		opacity: 1;
		left: 30px;
	}
}
#shain_animation {
	width:50%;
	height:50%;
	float:left;
	position:relative;
	animation: shainframe 2.0s ease 1.25s 1 normal both;
}
#shain {
	width:50%;
	height:50%;
	float:left;
	position:relative;
}

/* 入力フォーム関連スタイル */
@keyframes input_space_frame {
	from {
		opacity: 0;
		left: 70px;
	}
	to {
		opacity: 1;
		left: 50px;
	}
}
#input_space_animation {
	position:relative;
	top:100px;
	animation: input_space_frame 0.5s ease 0.25s 1 normal both;
}
#input_space {
	position:relative;
	top:100px;
}

#login_button {
	position:relative;
	left:90px;
}

#isSaveCheck {
	margin:30px 0px 0px 30px;
}
</style>

	<!-- メッセージの取得 -->
	<c:set var="mesMap" value="${requestScope['mesMap']}" />
	<!-- 片方が未入力の場合 -->
	<c:set var="mes_minyuryoku1" value="${mesMap['ECOMMON01']}" />
	<!-- 両方が未入力の場合 -->
	<c:set var="mes_minyuryoku2" value="${mesMap['E000101']}" />
	<!-- 入力されたユーザIDもしくはパスワードが正しくありません。 -->
	<c:set var="mes_minyuryoku" value="${mesMap['E000102']}" />
<script>
	//メッセージの適応
	function check(){
		//カラーコード変数
		var ErrColor="#ff5555";

		//各項目のチェック実施
		var login_form = document.forms['login'];
		//ID
		var id = login_form.elements['id'];
		var id_label = "ID";
		//PASS
		var pass = login_form.elements['pass'];
		var pass_label = "パスワード";


		//入力チェック→どちらかが未入力の場合の判定に切り替える
		//フォーカスはIDにそろえる
		//IDが未入力ではなく、パスワードが入力されていなかった場合→パスのみフォーカス、カラー
		//3タイプ　IDが入っていてなくてPASSが入っている
// 		if(id == ''){
// 			if(pass != ''){
// 				alert(id_label + "${mes_minyuryoku1}");
// 				id.focus();
// 				id.style.backgroundColor = ErrColor;
// 				return;
// 			}
// 		}
// 		//IDがはいっていてPASSがはいっていない
// 		if(id != ''){
// 			if(pass == ''){
// 				alert(pass_label + "${mes_minyuryoku1}");
// 				pass.focus();
// 				pass.style.backgroundColor = ErrColor;
// 				return;
// 			}
// 		}
// 		//IDもPASSも入っていない
// 		if(id == ''){
// 			if(pass == ''){
// 				alert(id_label + "${mes_minyuryoku2}");
// 				id.focus();
// 				id.style.backgroundColor = ErrColor;
// 				pass.style.backgroundColor = ErrColor;
// 				return;
// 			}
// 		}
		if(id.value == "" && pass.value == "") {	//id × pass ×
			alert("${mes_minyuryoku2}");
			id.style.backgroundColor = ErrColor;
			pass.style.backgroundColor = ErrColor;
			id.focus();
			return;
		} else if(id.value != "" && pass.value == "") {	//id ○ pass ×
			alert(pass_label + "${mes_minyuryoku1}");
			pass.style.backgroundColor = ErrColor;
			pass.focus();
			return;
		} else if(id.value == "" && pass.value != "") {	//id × pass ○
			alert(id_label + "${mes_minyuryoku1}");
			id.style.backgroundColor = ErrColor;
			id.focus();
			return;
		} else {	//id ○ pass ○
			// サーバーにリクエストを送信
			login_form.submit();
		}
	}

</script>
</head>
<body>
<c:set var="flg" value="${requestScope['flg']}" />
<%
	String user_id = "";
	Cookie[] cookies = request.getCookies();
	for(Cookie cookie : cookies) {
		if(cookie.getName().equals("user_id")) {
			user_id = cookie.getValue();
		}
	}
%>
<div id="title_space">
	<c:if test="${flg}">
		<h1 id="system_title_animation">J-Master ～従業員情報システム～</h1>
	</c:if>
	<c:if test="${!flg}">
		<h1 id="system_title">J-Master ～従業員情報システム～</h1>
	</c:if>
	<!-- ログイン実行時にflｇがfalseで帰ってきた場合のif文 -->
	<c:if test="${flg}">
		<p>${requestScope['mes']}</p>
	</c:if>
	<c:if test="${!flg}">
		<p id="error_msg">${requestScope['mes']}</p>
	</c:if>
</div>

<hr>

<c:if test="${flg}">
<img id="shain_animation" src="../img/kaisya_nakayoshi.png" />
<div id="input_space_animation">
</c:if>
<c:if test="${!flg}">
<img id="shain" src="../img/kaisya_nakayoshi.png" />
<div id="input_space">
</c:if>
	<!-- 情報の取得 　例：　"${emp.age}" <c:set var="emp" value="${requestScope['emp']}" />-->

	<form name="login" method="post" action="../UserLogin/">
		<p class="input"><span class="inputLabel">ユーザID</span><span class="input"><input type="text" name="id" value="<%=user_id %>" size="24" /></span></p>
		<p class="input"><span class="inputLabel">パスワード</span><span class="input"><input type="password" name="pass" value="" size="24" /></span></p>
		<p id="isSaveCheck" class="input"><input type="checkbox" name="isSaveCookie" id="saveCookie"/><label for="saveCookie"><span class="inputLabel">システムユーザIDをクッキーに保存する</span><label></label></p>
		<p class="input"><input id="login_button" type="button" value="ログイン" onclick="check()"></p>
	</form>

</div>
</body>
	<!-- EmployeeInputCheckを活用する -->
	<!--<script src="../js/LoginCheck.js" type="text/javascript">
	</script>-->
</html>