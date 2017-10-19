package ren10_keisho_interface. Tyusyou2;

public class MobilePhone implements Phone{
	private String name;

	//コンストラクタ引数1つ定義
	public MobilePhone(String name) {
		this.name = name;
		System.out.println(this.name+"さんの携帯です。");
	}
	/**
	 * callメソッドのオーバーライド
	 */

	@Override
	public void call() {
		System.out.println(name+"さんの携帯が鳴ります");
	}

	/**
	 * stopメソッドのオーバーライド
	 */
	@Override
	public void stop() {
		System.out.println(name+"さんの携帯が切れます");
	}



}
