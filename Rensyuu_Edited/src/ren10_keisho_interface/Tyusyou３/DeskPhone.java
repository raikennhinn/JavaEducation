package ren10_keisho_interface. Tyusyou３;

public class DeskPhone extends BasePhone{

	//コンストラクタ(引数1つ)
	public DeskPhone(String name){
		super(name);
		System.out.println(name + "さんの卓上電話です。");
	}

	//BasePhoneクラスからオーバーライドしたcall()メソッド
	public void call(){
		System.out.println(name + "さんの卓上電話が鳴ります。");
	}

	public void dial() {
		System.out.println(name + "さんの卓上電話のダイヤルを回します。");
	}
}