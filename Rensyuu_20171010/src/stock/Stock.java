package stock;

public class Stock {
	//メンバ変数を定義
	private String code;			//商品コード
	private int stockQuantity;		//在庫数

	//メソッドの定義
	public void setCode(String code) {
		this.code = code;
	}
	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	public void print() {
		System.out.println("商品コード	:"+code);
		System.out.println("在庫数		:"+stockQuantity);
	}

}
