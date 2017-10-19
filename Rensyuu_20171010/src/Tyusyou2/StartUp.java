package Tyusyou2;


/**
 * 抽象クラスの練習問題２
 */
class StartUp{
	public static void main(String[] args){

		//MobilePhone1クラスのオブジェクトの生成
		MobilePhone mp = new MobilePhone("吉田");

		//call()メソッドの呼び出し
		System.out.println("-callメソッドの呼び出し-");
		mp.call();

		//stop()メソッドの呼び出し
		System.out.println("-stopメソッドの呼び出し-");
		mp.stop();
	}
}