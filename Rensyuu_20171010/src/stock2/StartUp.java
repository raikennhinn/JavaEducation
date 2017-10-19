package stock2;

/**
 * Stockクラスの利用
 */
public class StartUp {

	public static void main(String[] args) {
		Stock stock = new Stock();
		stock.setCode("AA001");
		stock.setStockQuantity(1000);
		stock.reduceStock(100);
		System.out.println("商品の在庫数："+stock.getStockQuantity());


	}

}
