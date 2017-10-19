package ren09_class.class01;


/**
 * 商品
 * シンプルなクラス
 */
class Item{
	//定義
	String code;	//商品コード
	String name;	//商品名
	int   price;	//価格

	//画面表示
	public void print(){
		System.out.println("商品コード:" + code);
		System.out.println("商品名　　:" + name);
		System.out.println("価格　　　:" + price);
	}
}
