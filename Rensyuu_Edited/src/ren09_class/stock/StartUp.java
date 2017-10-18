package ren09_class.stock;

/**
 * Stockクラスの利用
 */
public class StartUp {

	public static void main(String[] args) {
		//Stockクラスからstockを生成
		Stock stock = new Stock();
		//値をセット
		stock.setCode("AA001");
		stock.setStockQuantity(1000);
		//画面に表示を実行する
		stock.print();
	}

}
