<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="webApplication.bean.Employee" %>
<%@ page import="webApplication.util.HTMLUtility" %>
<!DOCTYPE html">
<html>
<head>
	<title>従業員リスト</title>
	<link rel="stylesheet" type="text/css" href="../css/j-master.css">

	<c:set var="select" value="${sessionScope['select']}" />
	<c:set var="category" value="${sessionScope['category']}" />
	<c:set var="UpDown" value="${sessionScope['UpDown']}" />
	<c:set var="ShozokuSearch" value="${sessionScope['ShozokuSearch']}" />
	<c:set var="prefSearch" value="${sessionScope['prefSearch']}" />

	<script type="text/javascript">

	const UPDOWM_DEFAULT = 0;
	const UPDOWM_ASCENDING = 1;
	const UPDOWM_DESCENDING = 2;

	//ラジオボタンの入力チェック
	function check(actionURL){
		//各項目のチェック実施
		//document.forms.(参照するフォームのid).(参照するコントロールのid)
		//empNoUp_form.empNoUp
		var empNoUp_form = document.forms['empNoUpdate'];
		empNoUp_form.updateButton.disabled = true;
		empNoUp_form.newButton.disabled = true;
		empNoUp_form.deleteButton.disabled = true;
		var flag = 0;
		for (i = 0; i < empNoUp_form.empNoUp.length; i++){
			if (empNoUp_form.empNoUp[i].checked){
				flag = 1;
			}
		}
		if(typeof empNoUp_form.empNoUp.length === "undefined" ){
			if (empNoUp_form.empNoUp.checked){
				flag = 1;
			}
		}

		if(flag == 0){
			alert("ラジオボタンが選択されていません");
			empNoUp_form.updateButton.disabled = false;
			empNoUp_form.newButton.disabled = false;
			empNoUp_form.deleteButton.disabled = false;
			return;
		}else{
			empNoUp_form.action = actionURL;
			empNoUp_form.submit();
		}
	}


	function MousOnColor(id){
		if("${category}" == id){
			return;
		}
		document.getElementById(id).style.backgroundColor = "#F08080";
	}

	function MousOutColor(id){
		if("${category}" == id){
			return;
		}
			document.getElementById(id).style.backgroundColor = "white";
	}

	function MousOnClick(id,sort,select){
		document.getElementById(id).style.backgroundColor = "#CD5C5C";
		//引き数で受け取ったsortに対して＋１を行う
		var UpDown = sort;
		//数字によって表示する文字を判別０なら何もしない、１が↑、2が↓
		//パラメータをつけてソートサーブレットへ移動
		location.href ="../Sort/?id="+id+"&UpDown="+UpDown+"&select="+select;
	}

	//ページ読み込み時に実行
	//選択した項目とその数値によって動作を変える
	window.onload = function(){
		//昇順が選択されていれば、降順が選択されていれば･･･
		if("${UpDown}" == UPDOWM_ASCENDING){
			document.getElementById("${select}").innerHTML = "↑";
			document.getElementById("${category}").style.backgroundColor = "#CD5C5C";
		}else if("${UpDown}"==UPDOWM_DESCENDING){
			document.getElementById("${select}").innerHTML = "↓";
			document.getElementById("${category}").style.backgroundColor = "#CD5C5C";
		}
		//選択されているt所属コードを表示する
		document.getElementById("shozokuCode_${ShozokuSearch}").selected = true;
		document.getElementById("prefCD_${prefSearch}").selected = true;
	}

	// テーブルデータ部のスクロール値を、縦ヘッダ／横ヘッダに代入→スクロールをあわせる
	function scroll() {
		titleXBlock.scrollLeft = dataXBlock.scrollLeft
		dataYBlock.scrollTop = dataXBlock.scrollTop
	}


	function search(){
		var empNoUp_form = document.forms['empNoUpdate'];
		empNoUp_form.searchButton.disabled = true;
		empNoUp_form.action = "../EmployeeSearch/";
		empNoUp_form.submit();
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
			<h2>従業員リスト</h2>

			<!-- サーブレットから送られてきたデータを取得 -->
			<!-- req.setAttribute("empList", employeeList); -->
			<c:set var="empList" value="${requestScope['empList']}" />
			<!-- チェックが入っているかどうかの判定を入れるひつようがある -->
			<p><a href="../ReturnMenu/">メニューへ戻る</a></p>

			<c:set var="flg" value="${requestScope['flg']}" />
			<c:if test="${flg}">
				<p>${requestScope['mes']}</p>
			</c:if>
			<c:if test="${!flg}">
				<p id="error_msg">${requestScope['mes']}</p>
			</c:if>

			<!-- 検索を行うためのボタンやテキストボックスの生成 -->
			<div>
				<p>従業員No：<input type="text"  name="employee_noSearch" size="5" maxlength="4" value="${sessionScope['employee_noSearch']}">
				<!-- 所属コードプルダウンメニュー -->
				<c:set var="shozokuList" value="${sessionScope['shozokuList']}" />
				所属コード：<select name="ShozokuSearch">
						<option value="0" id="shozokuCode_0"></option>
						<c:forEach var="shl" items="${shozokuList}">
							<option id="shozokuCode_${shl.shozoku_code}" value="${shl.shozoku_code}">${shl.shozoku_code}:${shl.shozoku_bu}${shl.shozoku_ka}${shl.shozoku_kakari}</option>
						</c:forEach>
					</select></p>
				<!-- 氏名検索用の文字ー -->
				<p>氏名：<input type="text"  name="employeeNameSearch" size="15" maxlength="30" value="${sessionScope['employeeNameSearch']}">
				<!-- 都道府県検索用のプルダウンメニュー -->
				<c:set var="prefList" value="${sessionScope['prefList']}" />
				都道府県：<select name="prefSearch" >
					<option value="0" id="prefCD_0"></option>
					<c:forEach var="prefList" items="${prefList}">
					<!-- <option ~~~ selected />とすることで、画面表示した時点で選択状態にできる -->
						<option value="${prefList.prefCode}" id="prefCD_${prefList.prefCode}">${prefList.prefCode}:${prefList.prefName}</option>
					</c:forEach>
				</select>
				<!-- 住所検索用のテキスボックス（部分一致） -->
				住所：<input type="text"  name="addressSearch" size="30" maxlength="60" value="${sessionScope['addressSearch']}"></p>

				<p><input type="button" value=" 検索 " name="searchButton" onclick="search()">
				<input type="reset" name="clear" value=" クリア "  ></p>
			</div>
			<div><hr></div>

			<div id="pager_and_dataChangeButtons">
				<span id="pager">
					<!-- セッションの保存されたpage_noを取得する-->
					<c:set var="page_no" value="${sessionScope['page_no']}" />
					<!-- ２．セッション保存のcurrent_page_noを取得する -->
					<c:set var="current_page_no" value="${sessionScope['current_page_no']}" />
					<!-- ラベルの表示-->
					<!-- 15より少なければ、ラベルで、多ければリンクとして表示15件以内→ページ数が2より小さい -->
					<c:if test="${current_page_no <=1}">
							<label>&lt;&lt;</label>
							<label>&lt;</label>
					</c:if>
					<c:if test="${current_page_no >=2}">
					<!-- 2ページ目以上15件以上のデータが存在する場合はリンクありのデータを表示する。（1ページより下は無いのでラベル） -->
					<!-- 「<<」をリンク化し、パラメータを1にする。「<」もリンク化し、パラメータを「current_page_no - 1」にする
						逆に、最終ページを表示した場合は「>」「>>」をラベルにする。 -->
							<a href="../EmployeeListPager/?current_page_no=1" name="page">&lt;&lt;</a>
							<a href="../EmployeeListPager/?current_page_no=${current_page_no - 1}" name="page">&lt;</a>
					</c:if>
						<label>${current_page_no} / ${page_no}</label>
					<c:if test="${page_no == current_page_no}">
							<label>&gt;</label>
							<label>&gt;&gt;</label>
					</c:if>
					<c:if test="${page_no != current_page_no}">
							<a href="../EmployeeListPager/?current_page_no=${current_page_no + 1}" name="page">&gt;</a>
							<a href="../EmployeeListPager/?current_page_no=${page_no}" name="page">&gt;&gt;</a>
					</c:if>
				</span>
				<span id="dataChangeButtons">
					<input type="button" value=" 更新 " name="updateButton" onclick="check('../EmployeeUpdateInit/')">
					<input type="button" value="新規登録" name="newButton" onclick="location.href='../shainTourokuInit/'">
					<input type="button" value="削除" name="deleteButton" onclick="check('../EmployeeDelete/')">
				</span>
			</div>
			<!-- テーブル形式で全件表示。テーブルヘッダ行も表示する -->
			<!-- まずはテーブルタグとテーブルヘッダタグを普通に書く -->

			<%// TODO 新規登録ボタン、更新ボタン、削除ボタンを追加 %>
			<%// TODO 従業員選択ラジオボタンを列に追加 %>
			<%// TODO ラジオボタンの値には従業員Noを設定 %>
			<!-- セッションの保存されたsortを取得する-->
			<c:set var="sort" value="${sessionScope['sort']}" />
			<div id="all_area">
			<!-- 横スクロールができるように -->
				<c:if test="${detaflg}">
				<table border="1" id="titleXYBlockTable" class="tableFontSize">
				<!-- if文でtrueだったらこれを、falseだったら文字列のみの表示を行う -->
	 				<colgroup >
						<col style="width:50px">
						<col style="width:60px">
					</colgroup>
					<tr>
						<th>更新</th>
						<th onmouseover="MousOnColor('employeeNo')"  onmouseout="MousOutColor('employeeNo')" onclick="MousOnClick('employeeNo','${sort}','empSelect')" id="employeeNo" >社員番号<span id="empSelect"></span></th>
					</tr>
				</table>

				<div id="titleXBlock">
					<table border="1" id="titleXBlockTable" class="tableFontSize">
						<colgroup>
							<col style="width:130px">
							<col style="width:150px">
							<col style="width:40px">
							<col style="width:40px">
							<col style="width:100px">
							<col style="width:60px">
							<col style="width:200px">
							<col style="width:150px">
							<col style="width:300px">
						</colgroup>
						<tr>
							<th onmouseover="MousOnColor('shozokuName')"  onmouseout="MousOutColor('shozokuName')" onclick="MousOnClick('shozokuName','${sort}','ShozokuSelect')" id="shozokuName" >所属名<span id="ShozokuSelect"></span></th>
							<th onmouseover="MousOnColor('name')"  onmouseout="MousOutColor('name')" onclick="MousOnClick('name','${sort}','nameSelect')" id="name" >氏名<span id="nameSelect"></span></th>
							<th onmouseover="MousOnColor('sex')"  onmouseout="MousOutColor('sex')" onclick="MousOnClick('sex','${sort}','sexSelect')" id="sex" >性別<span id="sexSelect"></span></th>
							<th onmouseover="MousOnColor('age')"  onmouseout="MousOutColor('age')" onclick="MousOnClick('age','${sort}','ageSelect')" id="age" >年齢<span id="ageSelect"></span></th>
							<th onmouseover="MousOnColor('birthday')"  onmouseout="MousOutColor('birthday')" onclick="MousOnClick('birthday','${sort}','birthdaySelect')" id="birthday" >生年月日<span id="birthdaySelect"></span></th>
							<th>都道府県</th>
							<th>住所</th>
							<th>メールアドレス</th>
							<th>備考欄</th>
						</tr>
					</table>
				</div>
				</c:if>

				<!-- falseだったら、文字列のみの表示 -->
				<c:if test="${!detaflg}">
					<div id="titleXBlock">
						<table border="1" id="titleXYBlockTable" class="tableFontSize">
							<colgroup >
								<col style="width:50">
								<col style="width:60">
							</colgroup>
							<tr>
								<th>更新</th>
								<th>社員番号</th>
							<tr>
						</table>

						<table border="1" id="titleXBlockTable" class="tableFontSize">
							<colgroup >
								<col style="width:130px">
								<col style="width:150px">
								<col style="width:40px">
								<col style="width:40px">
								<col style="width:100px">
								<col style="width:60px">
								<col style="width:200px">
								<col style="width:150px">
								<col style="width:300px">
							</colgroup>
							<tr>
								<th>所属名</th>
								<th>氏名</th>
								<th>性別</th>
								<th>年齢</th>
								<th>生年月日</th>
								<th>都道府県</th>
								<th>住所</th>
								<th>メールアドレス</th>
								<th>備考欄</th>
							</tr>
						</table>
					</div>
				</c:if>

				<!-- テーブル行タグの前後をfor文のスクリプトレットではさんで、データ件数文タグとデータを表示する -->
				<!-- データ各項目を出力するのもスクリプトレットで -->
				<div id="dataYBlock">
					<table border="1" class="tableFontSize">
						<colgroup>
							<col style="width:50">
							<col style="width:60">
						</colgroup>
						<c:forEach var="emp" items="${empList}">
							<tr>
								<td><input type="radio" name="empNoUp" value="${emp.employee_no}" ></td>
								<td><c:out value="${emp.employee_no}" escapeXml="true"/></td>
							</tr>
						</c:forEach>
					</table>
				</div>

				<div id="dataXBlock" onscroll="scroll();">
					<table border="1" id="dataXBlockTable" class="tableFontSize">
						<colgroup>
							<col style="width:130px">
							<col style="width:150px">
							<col style="width:40px">
							<col style="width:40px">
							<col style="width:100px">
							<col style="width:60px">
							<col style="width:200px">
							<col style="width:150px">
							<col style="width:300px">
						</colgroup>
						<c:forEach var="emp" items="${empList}">
							<tr>
								<td title="${emp.shozoku.shozoku_code} ${emp.shozoku.shozoku_name}"><div class="tableColumn"><a href="javascript:void(0);" onclick="showModalDialog('../ShozokuData/?ShozokuCode=${emp.shozoku.shozoku_code}', '', 'dialogHeight:270px;dialogWidth:260px');">
								${emp.shozoku.shozoku_code} ${emp.shozoku.shozoku_name}</a></div></td>
								<td title="${emp.employee_name}"><div class="tableColumn"><c:out value="${emp.employee_name}" escapeXml="true" /></div></td>
								<td title="${emp.sex}"><div class="tableColumn"><c:out value="${emp.sex}" escapeXml="true" /></div></td>
								<td title="${emp.age}"><div class="tableColumn">
								<c:if test="${emp.age==0}">
								</c:if>
								<c:if test="${emp.age!=0}">
									<c:out value="${emp.age}" escapeXml="true" />
								</c:if>
								</div></td>
								<td title="${emp.birthdayAtSlash}"><div class="tableColumn"><c:out value="${emp.birthdayAtSlash}" escapeXml="true" /></div></td>
								<td title="${emp.pref_CD}"><div class="tableColumn"><c:out value="${emp.pref_CD}" escapeXml="true" /></div></td>
								<td title="${emp.address}"><div class="tableColumn"><c:out value="${emp.address}" escapeXml="true" /></div></td>
								<td title="${emp.mail_address}"><div class="tableColumn"><c:out value="${emp.mail_address}" escapeXml="true" /></div></td>
								<td title="${emp.note}"><div class="tableColumn"><c:out value="${emp.note}" escapeXml="true" /></div></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</form>
	</div>
</body>
</html>