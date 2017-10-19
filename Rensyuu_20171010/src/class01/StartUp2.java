package class01;

/**
 * Itemクラスの使用
 */
public class StartUp2 {

	public static void main(String[] args) {
		/**
		 * Itemからitem1を生成し、値を設定する
		 */
		Item item1 = new Item();
		item1.code = "AA001" ;
		item1.name = "ボールペン" ;
		item1.price = 100 ;
		/**
		 * printメソッドを呼び出す
		 */
		item1.print();

		/**
		 * Itemからitem2を生成し、値を設定する
		 */
		Item item2 = new Item();
		item2.code = "BB001" ;
		item2.name = "ノート" ;
		item2.price = 150 ;
		/**
		 * printメソッドを呼び出す
		 */
		item2.print();

	}

}
