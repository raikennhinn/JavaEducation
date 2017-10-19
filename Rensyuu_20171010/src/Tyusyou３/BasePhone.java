package Tyusyou３;

public abstract class BasePhone implements Phone{
	//メンバ変数
	protected String name;

	//コンストラクタ(引数1つ)
	public BasePhone(String name){
		this.name = name;
	}

	//Phoneインタフェースからオーバーライドしたstop()メソッド
	public void stop(){
		System.out.println(name + "さんの電話が切れます。");
	}
}