package con2;

/**
 * 在庫クラス
 * 複数のコンストラクタ
 */
public class Stock {
	//メンバ変数の定義
	private String code;			//商品コード
	private int stockQuantity;		//在庫数

	//コンストラクタ引数（1つ）を定義する。
	public Stock(String code) {
		this.code = code;
	}
	//コンストラクタ引数（2つ）を定義する。
	public Stock(String code,int stockQuantity) {
		this.code = code;
		this.stockQuantity = stockQuantity;
	}
	//メソッドの定義をするセッターとゲッター
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	//在庫数を引き当てるメソッド定義
	public void reduceStock(int reduce) {
		stockQuantity = stockQuantity - reduce;
	}

	public void print() {
		System.out.println("商品コード	："+code);
		System.out.println("在庫数		："+stockQuantity);
	}


}
