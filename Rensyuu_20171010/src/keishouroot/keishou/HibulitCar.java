package keishouroot.keishou;

/**
 * ハイブリット・カー
 * サブクラスの実例
 */
public class HibulitCar extends Car {

	// 残電気
	private double denki;

	private double gasorin = 1000.0;

	/**
	 * 消費電力量デフォルト値
	 * （クラスフィールド）
	 */
	private final double DEFAULT_DENKI = 10;

	/**
	 * デフォルトコンストラクタ
	 */
	public HibulitCar() {

	}


	/*
	 * 電気の引数が末尾にくるコンストラクタ群
	 */

	public HibulitCar(int num) {
		this(num, 0.0);
	}

	public HibulitCar(int num, double gaso) {
		this(num, gaso, "(不明）");
	}

	public HibulitCar(int num, double gaso, String name) {
		this(num, gaso, name, 30);
	}

	public HibulitCar(int num, double gaso, String name, double def) {
		this(num, gaso, name, def, 30);
	}
	public HibulitCar(int num, double gaso, String name, double def,double denki) {
		super(num, gaso, name, def);
		this.denki = denki;
	}

	/*
	 * 電気の引数が頭にくるコンストラクタ群
	 */
	public HibulitCar(double denki) {
		this(denki, 0);
	}

	public HibulitCar(double denki, int num) {
		this(denki, num, 0.0);
	}

	public HibulitCar(double denki, int num, double gaso) {
		this(denki, num, gaso, "(不明）");
	}

	public HibulitCar(double denki, int num, double gaso, String name) {
		this(denki, num, gaso, name, 30);
	}

	public HibulitCar(double denki, int num, double gaso, String name, double def) {
		super(num, gaso, name, def);
		this.denki = denki;
	}


	public double getDenki() {
		return denki;
	}

	public void setDenki(double denki) {
		this.denki = denki;
	}

	/**
	 * 電気で走るメソッド（ガソリンで走るrunメソッドの似たやつを別名で新規に定義
	 * @return
	 */
	public double erun() {
		return erun(this.DEFAULT_DENKI);
	}
	public double erun(double siyou) {
		this.denki =denki - siyou;
		if(denki<0) {
			denkiEmpty();
		}
		return denki;
	}
	private void denkiEmpty() {
		System.out.println("電力が足りません");
		this.denki = 0;
	}

	/**
	 * CarのprintSをオーバーライド
	 * 残り電力も表示する
	 */
	@Override
	public void printS() {
		super.printS();
		System.out.println("電気の残量:"+denki);
	}
}
