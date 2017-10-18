package ren09_class.kouza;

/**
 * クラスの練習問題
 */
class StartUp{
	public static void main(String[] args){

		//現在の利率を確認
		System.out.println("今日の利率:" + Account.getInterest());

		//口座開設
		System.out.println("以下の2つの口座を開設します。");
		Account account1 = new Account("山野");				//山野さんの口座
		Account account2 = new Account("森田",200000f);		//森田さんの口座

		//情報表示
		System.out.println("---------------------------------");
		account1.print();
		System.out.println("---------------------------------");
		account2.print();
		System.out.println("---------------------------------");

		//利息計算
		System.out.println("1年後の残高は？");
		account1.interestAt();
		account2.interestAt();

		//情報表示
		System.out.println("---------------------------------");
		account1.print();
		System.out.println("---------------------------------");
		account2.print();
		System.out.println("---------------------------------");

	}
}