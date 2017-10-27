function check(){
	//カラーコード変数
	var ErrColor="#ff5555";

	//各項目のチェック実施
	var update_form = document.forms['update'];
	//所属コード
	var sh_code = update_form.elements['shozoku_code'];
	var sh_codeLabel = "所属コード";
//	var sh_colorName ='shozoku_code';
	if(Minyuryoku(sh_code.value, sh_codeLabel)) {
		sh_code.focus();
		sh_code.style.backgroundColor = ErrColor;
		return;
	}
	if(Ketasu(sh_code.value, 3, sh_codeLabel)){
		sh_code.focus();
		sh_code.style.backgroundColor = ErrColor;
		return;
	}
	if(Suzi(sh_code.value, sh_codeLabel)){
		sh_code.focus();
		sh_code.style.backgroundColor = ErrColor;
		return;
	}

	//氏名
	var name = update_form.elements['name'];
	var nameStr = "氏名";
//	if(Minyuryoku(name.value, nameStr)){
//		name.focus();
//		name.style.backgroundColor = ErrColor;
//		return;
//	}
	if(Ketasu(name.value, 8, nameStr)){
		name.focus();
		name.style.backgroundColor = ErrColor;
		return;
	}
	//カナ氏名
	var namekana = update_form.elements['namekana'];
	var namekanaStr ="氏名カナ";
//	if(Minyuryoku(namekana.value, namekanaStr)){
//		namekana.focus();
//		namekana.style.backgroundColor = ErrColor;
//		return;
//	}
	if(Ketasu(namekana.value, 16, namekanaStr)){
		namekana.focus();
		namekana.style.backgroundColor = ErrColor;
		return;
	}
	if(kana(namekana.value, namekanaStr)){
		namekana.focus();
		namekana.style.backgroundColor = ErrColor;
		return;
	}

	//年齢
	var age = update_form.elements['age'];
	var ageStr = "年齢";
	if(Ketasu(age.value, 2, ageStr)){
		age.focus();
		age.style.backgroundColor = ErrColor;
		return;
	}
	if(Suzi(age.value, ageStr)){
		age.focus();
		age.style.backgroundColor = ErrColor;
		return;
	}

	//生年月日
	var birthday = update_form.elements['birthday'];
	var birthLabel = "生年月日";
	// 正規表現による日付チェック
		if(hiduke(birthday.value, birthLabel, '[1-2][0-9]{3}/[0-1][0-9]/[0-3][0-9]')){
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
//条件を満たしていなかった際のフォーカスについて


//以下functionはshainTouroku.jsと同文
function Minyuryoku(a,name,colorName) {
	if(a == ''){
		alert(name+"が入力されていません");
//				return 1;
//				判定ならbooleanのほうがいい

		return true;
	}
}

// 桁数チェック関数を定義
function Ketasu(a,i,name) {
	if(a.length > i){
		alert(name+":"+i+"桁まで入力できます");
		return true;
	}
}

// 数字チェック関数を定義
function Suzi(a,name) {
	if(isNaN(parseInt(a))) {
		if(a.length > 0){
			alert(name+"には数値を入力してください");
			return true;
	}
	}
}

// 日付チェック関数
function hiduke(a, name, pattern) {
	var regex = new RegExp(pattern, 'g');
	if(a.length > 0){
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
}


// カナチェック関数
function kana(a, name) {
	a = (a == null) ? "" : a;
	if(a.length > 0){
		var reg = new RegExp(/^[ｦ-ﾟ]*$/);
		if(a.match(/^[ァ-ヶー　]*$/)) {// 全角カナチェック
			return false;
		} else if(reg.test(a)) {// 半角カナチェック
			return false;
		} else {
			alert(name + "にはカタカナを入力してください");
			return true;
		}
	}
}

function ColorRiset(){
	//	document.forms['update'].elements['shozoku_code'].style.backgroundColor = "";
	var f = document.forms['update'];

	var sh = f.elements['shozoku_code'];
	sh.style.backgroundColor = "";

	var name = f.elements['name'];
	name.style.backgroundColor = "";

	var namekana = f.elements['namekana'];
	namekana.style.backgroundColor = "";

	var age = f.elements['age'];
	age.style.backgroundColor = "";

	var birthday = f.elements['birthday'];
	birthday.style.backgroundColor = "";
}
