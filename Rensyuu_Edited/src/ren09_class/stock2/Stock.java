package ren09_class.stock2;

public class Stock {
	//メンバ変数の定義
	private String code;
	private int stockQuantity;

	//メソッドを定義する
	//getCode
	public String getCode() {
		return code;
	}
	//setCode
	public void setCode(String code) {
		this.code = code;
	}
	//stockQuantityのgetメソッド
	public int getStockQuantity() {
		return stockQuantity;
	}

	//stockQuantityのsetメソッド
	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	//在庫数を引き当てるメソッド
	public void reduceStock(int stock) {
		stockQuantity = stockQuantity - stock;
	}
	//画面に表示を行うメソッド
	public void print() {
		System.out.println("商品コード	:"+code);
		System.out.println("在庫数		:"+stockQuantity);
	}

}
