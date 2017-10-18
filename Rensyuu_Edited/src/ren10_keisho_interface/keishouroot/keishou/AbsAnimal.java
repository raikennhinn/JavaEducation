package ren10_keisho_interface.keishouroot.keishou;

/**
 * 抽象クラスの例
 */
public abstract class AbsAnimal {

	/**
	 * 名前
	 */
	private String name = "";

	/**
	 * 身長
	 */
	private int sintyou = 0;

	/**
	 * 体重
	 */
	private int taiju = 0;

	/**
	 * 動物の鳴く動作を表す抽象メソッド
	 */
	abstract protected void cry();

	/**
	 * 走る動作を示すメソッド
	 */
	public void run() {
		System.out.println("走った");
	}
}
