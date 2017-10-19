package Tyusyou1;

/**
 * 抽象クラスの継承の練習問題１
 */
class StartUp{
	public static void main(String[] args){

		//MobilePhoneクラスのオブジェクトの生成
		MobilePhone mp = new MobilePhone();

		//call()メソッドの呼び出し
		System.out.println("-callメソッドの呼び出し-");
		mp.call("03-1234-5678");
		mp.call(null);

		System.out.println();
		//shot()メソッドの呼び出し
		System.out.println("-shotメソッドの呼び出し-");
		mp.shot();
	}
}