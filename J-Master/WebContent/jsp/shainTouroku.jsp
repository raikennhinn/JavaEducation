<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<% // TODO ここでJSTLの使用を宣言 %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.security.NoSuchAlgorithmException" %>
<%@ page import="java.security.SecureRandom" %>
<%
	String tokenStr = "";

	//トークンの作成
	final int TOKEN_LENGTH = 16;//16*2=32バイト

 	//32バイトのCSRFトークンを作成
    byte token[] = new byte[TOKEN_LENGTH];
    StringBuffer buf = new StringBuffer();
    SecureRandom random = null;

    try {
      random = SecureRandom.getInstance("SHA1PRNG");
      random.nextBytes(token);

      for (int i = 0; i < token.length; i++) {
        buf.append(String.format("%02x", token[i]));
      }

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    tokenStr = buf.toString();

	if(session.getAttribute("token") == null) {
	    // トークンをセッションに保存
	    session.setAttribute("token", tokenStr);
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>従業員登録</title>
	<!-- Css使用 -->
	<link rel="stylesheet" type="text/css" href="../css/j-master.css">

	<!-- ＥｍｐｌｏｙｅｅUpdateと違い、最初の段階でメッセージがリクエストで取得できていない -->
	<!--  メッセージMapをリクエストから取得  -->
	<c:set var="mesMap" value="${requestScope['mesMap']}" />
	<c:set var="mes_minyuryoku" value="${mesMap['ECOMMON01']}" />
	<c:set var="mes_ketasu" value="${mesMap['ECOMMON02']}" />
	<c:set var="mes_suji" value="${mesMap['ECOMMON03']}" />
	<c:set var="mes_hiduke" value="${mesMap['ECOMMON04']}" />
	<c:set var="mes_katakana" value="${mesMap['ECOMMON06']}" />

	<script>
		function checkInput(){

			//カラーコード変数
			var ErrColor="#ff5555";

			//各項目のチェック実施
			var shain_form = document.forms['shain'];

			//employee_noは必須
			var emp_no = shain_form.elements['employee_no'];
			var emp_nolabel = "社員番号";
			//入力チェック
			if(Minyuryoku(emp_no.value, emp_nolabel, "${mes_minyuryoku}")) {
				emp_no.focus();
				emp_no.style.backgroundColor = ErrColor;
				return;
			}
			if(Ketasu(emp_no.value, 4, emp_nolabel, "${mes_ketasu}")){
				emp_no.focus();
				emp_no.style.backgroundColor = ErrColor;
				return;
			}
			if(Suzi(emp_no.value, emp_nolabel, "${mes_suji}")){
				emp_no.focus();
				emp_no.style.backgroundColor = ErrColor;
				return;
			}


			//所属コード
			var sh_code = shain_form.elements['shozoku_code'];
			var sh_codeLabel = "所属コード";
			//入力チェック
			if(Minyuryoku(sh_code.value, sh_codeLabel, "${mes_minyuryoku}")) {
				sh_code.focus();
				sh_code.style.backgroundColor = ErrColor;
				return;
			}
			if(Ketasu(sh_code.value, 3, sh_codeLabel, "${mes_ketasu}")){
				sh_code.focus();
				sh_code.style.backgroundColor = ErrColor;
				return;
			}
			if(Suzi(sh_code.value, sh_codeLabel, "${mes_suji}")){
				sh_code.focus();
				sh_code.style.backgroundColor = ErrColor;
				return;
			}

			//氏名
			var name = shain_form.elements['name'];
			var nameStr = "氏名";

			if(Ketasu(name.value, 30, nameStr, "${mes_ketasu}")){
				name.focus();
				name.style.backgroundColor = ErrColor;
				return;
			}
			//カナ氏名
			var namekana = shain_form.elements['namekana'];
			var namekanaStr ="氏名カナ";

			if(Ketasu(namekana.value, 60, namekanaStr, "${mes_ketasu}")){
				namekana.focus();
				namekana.style.backgroundColor = ErrColor;
				return;
			}
			if(kana(namekana.value, namekanaStr, "${mes_katakana}")){
				namekana.focus();
				namekana.style.backgroundColor = ErrColor;
				return;
			}

			//年齢
			var age = shain_form.elements['age'];
			var ageStr = "年齢";
			if(Ketasu(age.value, 2, ageStr, "${mes_ketasu}")){
				age.focus();
				age.style.backgroundColor = ErrColor;
				return;
			}
			if(Suzi(age.value, ageStr, "${mes_suji}")){
				age.focus();
				age.style.backgroundColor = ErrColor;
				return;
			}

			//生年月日
			var birthday = shain_form.elements['birthday'];
			var birthLabel = "生年月日";
			// 正規表現による日付チェック
				if(hiduke(birthday.value, birthLabel, '[1-2][0-9]{3}/[0-1][0-9]/[0-3][0-9]', "${mes_hiduke}")){
					birthday.focus();
					birthday.style.backgroundColor = ErrColor;
					return;
			}


			// 実行確認
			var res = confirm("この内容で登録してもよいですか？");
			if(res){
				// サーバーにリクエストを送信
				shain_form.submit();
			}
		}
	</script>


</head>

<body>
	<div id="header_field">
		<%@ include file="UserInfoHedder.jsp" %>
	</div>
	<div id="main_field">
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
		<p>社員番号:<input type="text" style="ime-mode:disabled;" name="employee_no" size="10" value="${emp.employee_no}"></p>

<!-- 所属コードについては、ひとまず単純なテキストボックスでよい -->
		<p>所属コード:<input type="text" style="ime-mode:disabled;" maxlength="3" name="shozoku_code" size="4" value="${emp.shozoku.shozoku_code}"></p>

		<p>氏名:<input type="text" name="name" maxlength="30" size="16" value="${emp.employee_name}"></p>
		<p>氏名カナ：<input type="text" name="namekana" maxlength="60" size="32" value="${emp.employee_namekana}"></p>
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

		<p>年齢:
			<c:if test="${emp.age==-1}">
				<input type="text" style="ime-mode:disabled;" maxlength="2" name="age" size="3" value="">
			</c:if>
			<c:if test="${emp.age!=-1}">
				<input type="text" style="ime-mode:disabled;" maxlength="2" name="age" size="3" value="${emp.age}">
			</c:if>
		</p>

		<p>生年月日:<input type="text" name="birthday" size="10" value="${emp.birthdayAtSlash}"></p>
		<p>都道府県:<input type="text" name="prefecture" size="3" value="${emp.pref_CD}"></p>
		<p>住所:<input type="text" name="address" size="50" maxlength="100" value="${emp.address}"></p>
		<p>メールアドレス:<input type="text" name="mail_address" size="30" maxlength="50" value="${emp.mail_address}"></p>
		<p>備考:<textarea name="note" cols="20" rows=4>${emp.note}</textarea></p>

<!-- 登録ボタンとリセットボタンを用意する。
　　　登録ボタンは押下時に入力チェック（後述）と、登録確認ダイアログを表示。OKを押したら登録処理（サーブレット）起動。
	リセットボタンは、HTML標準のリセット機能でよい。
	 -->
		<!-- 登録ボタンとリセットボタン -->
		<p>
			<input type="button" value=" 登録 " onclick="checkInput()">
			<input type="reset" name="リセット" value=" 取消 " onclick="ColorallRiset()"></p>
			<p><a href="../EmployeeList/">戻る</a></p>

		<input type="hidden" name="token" value="<%=tokenStr %>" />
	</form>
	</div>
</body>

<!-- 入力チェック。
　・未入力チェック（必須項目）、
　・桁数チェック（文字数／バイト数オーバーチェック）（テキストボックス全般）、
　・数字チェック（数値項目）
　これらをJavaScriptで行う　-->


<!-- 現在employeeUpdate（名称変更予定）へ返る-->
	<!-- 定義方法<script src="●●.js"></script> jsファイルにうつす-->
	<script src="../js/EmployeeInputCheck.js" type="text/javascript">
	</script>

</html>