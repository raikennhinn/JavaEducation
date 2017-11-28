<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="webApplication.bean.Employee" %>
<!DOCTYPE html">
<html>
<head>
	<title>所属長の選択</title>
	<link rel="stylesheet" type="text/css" href="../css/j-master.css">
<script>

// ラジオボタンを選択したときに、選択値をhiddenに格納する
function selectEmployee(employee) {
	// 引数のemployeeを、hidden項目「selection」にセット

	// HTML文書中のform要素name指定で取得
	var emp_form = document.forms['empNoSelect'];

	// form要素（formタグ）の中から、name指定でinput要素を取得→ここでhidden要素「selection」を取得したい
	var emp_employee = emp_form.elements['selection'];

	// 取得したhidden要素「selection」のvalue属性に、employeeを代入
	emp_employee.value = employee;

	// デバッグ用
	var emp_employee2 = emp_form.elements['selection2'];
	emp_employee2.value = employee;
}

// 選択されたラジオボタンの情報を親画面に返す
function returnParentWindow() {
	// hiddenから従業員情報を取得
	var emp_form = document.forms['empNoSelect'];
	var emp_employee = emp_form.elements['selection'];
	var select = emp_employee.value;
	//文字列分割 --をデリミタ（区切り文字）にして文字列を分ける
	var emps = select.split('--');
	var emp_No = emps[0];
	var emp_Name = emps[1];
	var jsonStr = '[{ "employee_no" : "' + emp_No + '", "employee_name" : "' + emp_Name + '" }]';
	// json形式で従業員情報の文字列を作成し、JSONオブジェクトに変換
	var json = eval(jsonStr);

	window.returnValue = json;
	window.close();
}
function displayReturn(){
	window.returnValue = '';
	window.close();
}
</script>

</head>
<body>
	<form name="empNoSelect">
		<h1>所属長の選択</h1>
		<div align="right"><a href="javascript:void(0);" onclick="displayReturn();">戻る</a></div>
		<p><input type="button" value=" 選択 " onclick="returnParentWindow();"></p>

		<table border="1">
			<colgroup>
				<col style="width:50">
				<col style="width:90">
				<col style="width:110">
			</colgroup>
			<tr>
				<th>選択</th>
				<th>従業員No</th>
				<th>氏　名</th>
			</tr>
			<c:forEach var="emp" items="${employeeList}">
			<tr>
				<td><input type="radio" name="employee_no" value="${emp.employee_no}" onclick="selectEmployee('${emp.employee_no}--${emp.employee_name}')"></td>
				<td>${emp.employee_no}</td>
				<td>${emp.employee_name}</td>
			</tr>
			</c:forEach>
		</table>
		<input type="hidden" name="shozoku_code" value="${requestScope['shozokuCode']}" />
		<input type="hidden" name="selection" value=""/>
		<input type="text" name="selection2" value=""/>
	</form>
</body>
</html>