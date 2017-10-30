
//条件を満たしていなかった際のフォーカスについて


//以下functionはshainTouroku.jsと同文
function Minyuryoku(a,name,colorName, mes) {
	if(a == ''){
		alert(name + mes);
//				return 1;
//				判定ならbooleanのほうがいい

		return true;
	}
}

// 桁数チェック関数を定義
function Ketasu(a,i,name, mes) {
	if(a.length > i){
		alert(name+":" + i + mes);
		return true;
	}
}

// 数字チェック関数を定義
function Suzi(a,name, mes) {
	if(isNaN(parseInt(a))) {
		if(a.length > 0){
			alert(name + mes);
			return true;
	}
	}
}

// 日付チェック関数
function hiduke(a, name, pattern, mes) {
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
		alert(name + mes);
		return true;
	}
}


// カナチェック関数
function kana(a, name, mes) {
	a = (a == null) ? "" : a;
	if(a.length > 0){
		var reg = new RegExp(/^[ｦ-ﾟ]*$/);
		if(a.match(/^[ァ-ヶー　]*$/)) {// 全角カナチェック
			return false;
		} else if(reg.test(a)) {// 半角カナチェック
			return false;
		} else {
			alert(name + mes);
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
