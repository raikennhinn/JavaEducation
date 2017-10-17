package Tyusyou３;

public class MobilePhone extends BasePhone{

	//コンストラクタ(引数1つ)
	public MobilePhone(String name){
		super(name);
		System.out.println(name + "さんの携帯電話です。");
	}

	//Phoneインタフェースからオーバーライドしたcall()メソッド
	public void call(){
		System.out.println(name + "さんの携帯電話が鳴ります。");
	}
}