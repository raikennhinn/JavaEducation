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

	<c:set var="select" value="${sessionScope['select']}" />
	<c:set var="category" value="${sessionScope['category']}" />
	<c:set var="UpDown" value="${sessionScope['UpDown']}" />

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


	<!-- セッションの保存されたpage_noを取得する-->
	<c:set var="page_no" value="${sessionScope['page_no']}" />
	<!-- ２．セッション保存のcurrent_page_noを取得する -->
	<c:set var="current_page_no" value="${sessionScope['current_page_no']}" />
	<!-- ラベルの表示-->
	<!-- 15より少なければ、ラベルで、多ければリンクとして表示15件以内→ページ数が2より小さい -->
	<c:if test="${current_page_no <=1}">
		<p>
			<label>&lt;&lt;</label>
			<label>&lt;</label>
	</c:if>
	<c:if test="${current_page_no >=2}">
	<!-- 2ページ目以上15件以上のデータが存在する場合はリンクありのデータを表示する。（1ページより下は無いのでラベル） -->
	<!-- 「<<」をリンク化し、パラメータを1にする。「<」もリンク化し、パラメータを「current_page_no - 1」にする
		逆に、最終ページを表示した場合は「>」「>>」をラベルにする。 -->
		<p>
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
	</p>

	<!-- テーブル形式で全件表示。テーブルヘッダ行も表示する -->
	<!-- まずはテーブルタグとテーブルヘッダタグを普通に書く -->

	<%// TODO 新規登録ボタン、更新ボタン、削除ボタンを追加 %>
	<%// TODO 従業員選択ラジオボタンを列に追加 %>
	<%// TODO ラジオボタンの値には従業員Noを設定 %>
	<!-- セッションの保存されたsortを取得する-->
	<c:set var="sort" value="${sessionScope['sort']}" />
	<div id="#all_area">
	<table border="1" id="title" name="table">
	<!-- if文でtrueだったらこれを、falseだったら文字列のみの表示を行う -->
		<c:if test="${detaflg}">
		<colgroup >
			<col style="width:50">
			<col style="width:100">
			<col style="width:130">
			<col style="width:250">
			<col style="width:70">
			<col style="width:50">
			<col style="width:130">
		</colgroup>
		<tr>
			<th>更新</th>
			<th onmouseover="MousOnColor('employeeNo')"  onmouseout="MousOutColor('employeeNo')" onclick="MousOnClick('employeeNo','${sort}','empSelect')" id="employeeNo" >社員番号<span id="empSelect"></span></th>
			<th onmouseover="MousOnColor('shozokuName')"  onmouseout="MousOutColor('shozokuName')" onclick="MousOnClick('shozokuName','${sort}','ShozokuSelect')" id="shozokuName" >所属名<span id="ShozokuSelect"></span></th>
			<th onmouseover="MousOnColor('name')"  onmouseout="MousOutColor('name')" onclick="MousOnClick('name','${sort}','nameSelect')" id="name" >氏名<span id="nameSelect"></span></th>
			<th onmouseover="MousOnColor('sex')"  onmouseout="MousOutColor('sex')" onclick="MousOnClick('sex','${sort}','sexSelect')" id="sex" >性別<span id="sexSelect"></span></th>
			<th onmouseover="MousOnColor('age')"  onmouseout="MousOutColor('age')" onclick="MousOnClick('age','${sort}','ageSelect')" id="age" >年齢<span id="ageSelect"></span></th>
			<th onmouseover="MousOnColor('birthday')"  onmouseout="MousOutColor('birthday')" onclick="MousOnClick('birthday','${sort}','birthdaySelect')" id="birthday" >生年月日<span id="birthdaySelect"></span></th>
		</tr>
			</c:if>
		<!-- falseだったら、文字列のみの表示 -->
		<c:if test="${!detaflg}">
		<colgroup >
			<col style="width:50">
			<col style="width:100">
			<col style="width:130">
			<col style="width:250">
			<col style="width:70">
			<col style="width:50">
			<col style="width:130">
		</colgroup>
		<tr>
			<th>更新</th>
			<th>社員番号</th>
			<th>所属名</th>
			<th>氏名</th>
			<th>性別</th>
			<th>年齢</th>
			<th>生年月日</th>
		</tr>
		</c:if>
	</table>
	<!-- テーブル行タグの前後をfor文のスクリプトレットではさんで、データ件数文タグとデータを表示する -->
	<!-- データ各項目を出力するのもスクリプトレットで -->
	<div id="scroll_box">
	<table border="1" id="data">
		<colgroup>
			<col style="width:50">
			<col style="width:100">
			<col style="width:130">
			<col style="width:250">
			<col style="width:70">
			<col style="width:50">
			<col style="width:130">
		</colgroup>
	<c:forEach var="emp" items="${empList}">
		<tr>
			<td><input type="radio" name="empNoUp" value="${emp.employee_no}" ></td>
			<td>${emp.employee_no}</td>
			<td><a href="javascript:void(0);" onclick="showModalDialog('../ShozokuData/?ShozokuCode=${emp.shozoku.shozoku_code}', '', 'dialogHeight:270px;dialogWidth:260px');">
				${emp.shozoku.shozoku_code} ${emp.shozoku.shozoku_name}</a>
			</td>
			<td title="${emp.employee_name}"><div style="overflow:hidden;">${emp.employee_name}</div></td>
			<td>${emp.sex}</td>
			<td>
				<c:if test="${emp.age==0}">
				</c:if>
				<c:if test="${emp.age!=0}">
					${emp.age}
				</c:if>
			</td>
			<td>
				${emp.birthdayAtSlash}
			</td>
		</tr>
	</c:forEach>
	<!-- テーブルタグを閉じる -->
	</table>
	</div>
	</div>
	</form>
	</div>
</body>
</html>