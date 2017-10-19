package Tyusyou３;

/**
 * 抽象クラスの練習問題３
 */
class StartUp{
	public static void main(String[] args){
		//Controllerクラスのオブジェクト生成
		Controller con = new Controller();

		//MobilePhoneクラスのオブジェクトの生成
		MobilePhone mp = new MobilePhone("吉田");

		System.out.println("-callメソッドの呼び出し-");
		con.call(mp);

		System.out.println("-stopメソッドの呼び出し-");
		con.stop(mp);


		System.out.println("----------------------------");
		//DeskPhoneクラスのオブジェクト生成と利用
		DeskPhone dp = new DeskPhone("岡田");

		System.out.println("-callメソッドの呼び出し-");
		con.call(dp);

		System.out.println("-stopメソッドの呼び出し-");
		con.stop(dp);
	}
}