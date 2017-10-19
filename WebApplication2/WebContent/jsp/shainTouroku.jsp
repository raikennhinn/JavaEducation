<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>従業員登録</title>
	<link rel="stylesheet" type="text/css" href="../css/webApplication.css">
</head>

<body>
<!-- タイトルを見出しで。昨日の一覧画面と同じスタイルを適用する。 -->
	<h1>従業員登録</h1>
<!-- employeeテーブルの各項目（更新者更新日時を除く）を入力するのに適切なフォームコントロールをそれぞれ記述 -->
	<form name="shain" target="_self" method="POST" action="../shainTouroku/">

		<p>社員番号:<input type="text" name="employee_code" size="10"></p>
<!-- 所属コードについては、ひとまず単純なテキストボックスでよい -->
		<p>所属コード:<input type="text" name="shozoku_code" size="4"></p>

		<p>氏名:<input type="text" name="name" size="8"></p>

		<p>性別:<input type="radio" name="sex" value="1" checked>男
			<input type="radio" name="sex" value="2" >女
		</p>

		<p>年齢:<input type="text" name="age" size="3"></p>

		<p>生年月日:<input type="text" name="birthday" size="10"></p>

		<!-- 登録ボタンとリセットボタン -->
		<p>
			<input type="button" value=" 登録 " onclick="touroku()">
			<input type="reset" name="リセット" value=" 取消 "></p>
	</form>
<!-- 登録ボタンとリセットボタンを用意する。
　　　登録ボタンは押下時に入力チェック（後述）と、登録確認ダイアログを表示。OKを押したら登録処理（サーブレット）起動。
	リセットボタンは、HTML標準のリセット機能でよい。
	 -->

<!-- 入力チェック。
　・未入力チェック（必須項目）、
　・桁数チェック（文字数／バイト数オーバーチェック）（テキストボックス全般）、
　・数字チェック（数値項目）
　これらをJavaScriptで行う　-->



</body>

	<!-- 定義方法<script src="●●.js"></script> jsファイルにうつす-->
	<script type="text/javascript">
		function touroku(){
			// 入力チェック
			// コントロールの値取得にはDOMを使う


			// DOMを使わないパターン（これで問題ない様子）
			//var emp_code = document.forms['shain'].elements['employee_code'].value;
			//いったんformsだけ変数でとっておいてもいいかも
			var shain_form = document.forms['shain'];
			var emp_code = shain_form.elements['employee_code'].value;
			// 未入力チェック：従業員コードは主キーのため、入力必須
			// 桁数チェック：テキスト入力ならテーブルカラムの桁数を超えてないかチェックが必要
			// （テキストボックスであらかじめ入力可能桁数を制限しておけば不要な場合もあり）
			// 数字チェック：従業員コードはint型のカラムのため、文字が入力されていないかチェックする
/*
			// 入力チェック部分は関数にして使いまわしたほうがいい
			// ソースが短くなる、ミスがあったときに修正箇所が減る、などなど
			if(emp_code == ''){
				alert(emp_code+"入力されていない項目があります。");
				return;
			}
			if(emp_code.length > 4){
				alert("4桁までの数値を入力してください");
				return;
			}
			if(isNaN(parseInt(emp_code))) {
				alert("数値を入力してください");
				return;
			}
*/
//			そもそもbを使いまわす必要がないのでは？
//			var b =0;
//			Minyuryoku(emp_code,b);
//
//			if(b>=1){
//					return;
//				}

//			これでも悪くないが、もっとよくできる
//			var b = Minyuryoku(emp_code);
//			if(b == 1) {
//				return;
//			}
			var emp_codeLabel = "社員番号";
			if(Minyuryoku(emp_code, emp_codeLabel)) {
				shain_form.elements['employee_code'].focus();	// エラー箇所にフォーカスを当てる
				shain_form.elements['employee_code'].style.backgroundColor = "#DF6A6A";	// エラー箇所の色をかえる
				return;
			}

			if(Ketasu(emp_code, 4, emp_codeLabel)) return;

			if(Suzi(emp_code, emp_codeLabel)) return;


			var sh_code = document.forms['shain'].elements['shozoku_code'].value;
			var sh_codeLabel = "所属コード";
			if(Minyuryoku(sh_code, sh_codeLabel)) return;

			if(Ketasu(sh_code, 3, sh_codeLabel)) return;

			if(Suzi(sh_code, sh_codeLabel)) return;

			var name = document.forms['shain'].elements['name'].value;
			var nameStr = "氏名";
			if(Minyuryoku(name, nameStr)) return;

			if(Ketasu(name, 8, nameStr)) return;


			//年齢
			var age = document.forms['shain'].elements['age'].value;
			var ageStr = "年齢";
			if(Ketasu(age, 2, ageStr)) return;

			if(Suzi(age, ageStr)) return;

			//生年月日
			var birthday = shain_form.elements['birthday'].value;
			var birthLabel = "生年月日";
			// 正規表現による日付チェック
			if(hiduke(birthday, birthLabel, '[1-2][0-9]{3}/[0-1][0-9]/[0-3][0-9]')) return;



			// フォームコントロールの値を取得して、必要なチェック関数を使ってチェック

			// コントロールの値取得にはDOMを使う
			// すべてのコントロールの値をチェックして、どこかでエラーがあれば
			// エラーメッセージを表示して、ここの処理を中断する


			// 実行確認
			var res = confirm("この内容で登録してもよいですか？");
			if(res){
				// サーバーにリクエストを送信
				shain_form.submit();
			}
		}

		// 未入力チェック関数を定義
/* 		function Minyuryoku(a,b) {
			if(a == ''){
				alert("入力されていない項目があります。");
				b=b+1;
				return b;
			}
		}
 */
  		function Minyuryoku(a,name) {
			if(a == ''){
				alert(name+"が未入力です。");
//				return 1;
//				判定ならbooleanのほうがいい
				return true;
			}
		}

		// 桁数チェック関数を定義
		function Ketasu(a,i,name) {
			if(a.length > i){
				alert(name+":"+i+"桁までの数値を入力してください");
				return true;
			}
		}

		// 数字チェック関数を定義
		function Suzi(a,name) {
			if(isNaN(parseInt(a))) {
				alert(name+"には数値を入力してください");
				return true;
			}
		}

		// 日付チェック関数
		function hiduke(a, name, pattern) {
			var regex = new RegExp(pattern, 'g');
			// 正規表現チェックと、月日チェックが成功したときのみfalse（チェックOK)
			if(regex.exec(a)) {
				var month = parseInt(a.split('/')[1]);
				var day = parseInt(a.split('/')[2]);
				if(month >= 1 && month <= 12) {
					switch (month) {
						case 2:
							if(day >= 1 && day <= 28) {
								return false;
							}
							break;
						case 4:
						case 6:
						case 9:
						case 11:
							if(day >= 1 && day <= 30) {
								return false;
							}
							break;
						default:
							if(day >= 1 && day <= 31) {
								return false;
							}
							break;
					}
				}
			}
			// どちらかがダメならtrue（チェックNG）
			alert(name+"には有効な日付を入力してください");
			return true;
		}

	</script>

</html>