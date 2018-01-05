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
	<link rel="stylesheet" type="text/css" href="../css/j-master.css">



	<!-- メッセージMapをリクエストから取得 -->
	<c:set var="mesMap" value="${requestScope['mesMap']}" />
	<c:set var="mes_minyuryoku" value="${mesMap['ECOMMON01']}" />
	<c:set var="mes_ketasu" value="${mesMap['ECOMMON02']}" />
	<c:set var="mes_suji" value="${mesMap['ECOMMON03']}" />
	<c:set var="mes_hiduke" value="${mesMap['ECOMMON04']}" />
	<c:set var="mes_katakana" value="${mesMap['ECOMMON06']}" />
	<c:set var="emp" value="${requestScope['emp']}" />

	<script>
	function load(){

		document.getElementById("prefCD_${emp.pref_CD}").selected = true;
	}

		function checkInput(){
			//カラーコード変数
			var ErrColor="#ff5555";

			//各項目のチェック実施
			var update_form = document.forms['update'];
			//所属コード
			var sh_code = update_form.elements['shozoku_code'];
			var sh_codeLabel = "所属コード";
	//		var sh_colorName ='shozoku_code';
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
			var name = update_form.elements['name'];
			var nameStr = "氏名";

			if(Ketasu(name.value, 30, nameStr, "${mes_ketasu}")){
				name.focus();
				name.style.backgroundColor = ErrColor;
				return;
			}
			//カナ氏名
			var namekana = update_form.elements['namekana'];
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
			var age = update_form.elements['age'];
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
			var birthday = update_form.elements['birthday'];
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
				update_form.submit();
			}
		}


	</script>
</head>
<body onLoad="document.update.shozoku_code.focus();load()">
	<div id="header_field">
		<%@ include file="UserInfoHedder.jsp" %>
	</div>
	<div id="main_field">
	<h1>従業員更新</h1>
	<c:set var="flg" value="${requestScope['flg']}" />
	<c:if test="${flg}">
		<p>${requestScope['mes']}</p>
	</c:if>
	<c:if test="${!flg}">
		<p id="error_msg">${requestScope['mes']}</p>
	</c:if>

	<!-- 一覧から取得したコードのひとつが入る→URLに直接入力したものを入れる -->
	<!-- formタグを使用し、各データのテキストボックスを記述 -->
	<!-- 初期値（社員コードを元にひっぱってきたデータを表示する） -->
	<%//従業員コードに関しては、サーブレットから取り出した値を表示すればよいか %>
	<!--<c:set var="emp" value="${requestScope['emp']}" />-->

	<form name="update" target="_self" method="POST" action="../EmployeeUpdate/">
	<p id="Absolutely">従業員No：<label > ${emp.employee_no} </label></p>
	<c:if test="${flg}">
		<!--  flgが0だった場合何もしない-->
		<p id="Absolutely">所属コード:<input type="text" style="ime-mode:disabled;" maxlength="3" name="shozoku_code" size="4" value="${emp.shozoku.shozoku_code}"></p>
	</c:if>
	<c:if test="${!flg}">
		<!--  flgが1だった場合何もしない-->
		<p id="Absolutely">所属コード:<input type="text" style="ime-mode:disabled; background-color:#ff5555;" maxlength="3" name="shozoku_code" size="4" value="${emp.shozoku.shozoku_code}" ></p>
	</c:if>

	<p>氏名:<input type="text" maxlength="30" name="name" size="16" value="${emp.employee_name}"></p>
	<p>氏名カナ：<input type="text" maxlength="60" name="namekana" size="30" value="${emp.employee_namekana}"></p>
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
		<input type="text" style="ime-mode:disabled;" maxlength="2" name="age" size="3" value="">
	</c:if>
	<c:if test="${emp.age!=-1}">
		<input type="text" style="ime-mode:disabled;" maxlength="2" name="age" size="3" value="${emp.age}">
	</c:if>
	</p>
	<p>生年月日:<input type="text" name="birthday" size="10" value="${emp.birthdayAtSlash}"></p>
	<!--<p>都道府県:<input type="text" name="prefecture" size="3" value="${emp.pref_CD}"></p>-->
	<p><c:set var="prefList" value="${sessionScope['prefList']}" />
			都道府県：<select name="prefecture" >
			<!-- <option value="0" id="prefCD_0"></option> -->
				<c:forEach var="prefList" items="${prefList}">
				<!-- <option ~~~ selected />とすることで、画面表示した時点で選択状態にできる -->
				<option value="${prefList.prefCode}" id="prefCD_${prefList.prefCode}">${prefList.prefCode}:${prefList.prefName}</option>
				</c:forEach>
			</select>
	</p>
	<p>住所:<input type="text" name="address" size="50" maxlength="100" value="${emp.address}"></p>
	<p>メールアドレス:<input type="text" name="mail_address" size="30" maxlength="50" value="${emp.mail_address}"></p>
	<p>備考:<textarea name="note" cols="20" rows=4>${emp.note}</textarea></p>

	<p><!-- メッセージ表示欄--></p>
	<!--③メッセージMapからメッセージ文字列を取得し、エラーチェックイベントに組み込む -->
	<!-- ⑪の項目が入る valueに何の値を入れればよいのか？URLの値を-->
	<p><input type="hidden" name="employee_no" value="${emp.employee_no}" ></p>

	<!-- 登録と取り消しボタン -->
	<p><input type="button" value=" 登録 " onclick="checkInput()">
	<input type="reset" name="リセット" value=" 取消 "  onclick="ColorRiset()"></p>

	<p><a href="../EmployeeList/">戻る</a></p>
	</form>
</div>
</body>
	<!-- JavaScriptを指定 -->
	<script src="../js/EmployeeInputCheck.js" type="text/javascript"></script>
</html>