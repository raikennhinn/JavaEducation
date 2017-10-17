package con2;

/**
 * Stockクラスの使用
 */
public class StartUp {

	public static void main(String[] args) {
		//ストックオブジェクトを二つ生成
		Stock stock1 = new Stock("AA001");
		Stock stock2 = new Stock("BB001",2000);
		stock1.print();
		stock2.print();
	}

}
