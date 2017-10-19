package ren10_keisho_interface. keishouroot.keishou;

import java.util.Date;

/**
 * 車をあらわすクラス
 * サブクラスの説明のためのスーパークラス
 * クラスの大小比較・ソートの実例にも使用
 */
public class Car implements Comparable<Car> {

	// クラスフィールド
	// 車の合計台数
	private static int summary = 0;


	/**
	 * ナンバー
	 */
	public int  number;

	/**
	 * ガソリン量
	 */
	private double gasorin = 10.0;

	/**
	 * 車種名
	 */
	private String name = "（不明）";

	/**
	 * 購入日
	 */
	private Date buyDate = new Date();

	/**
	 * 消費ガソリン量デフォルト値
	 * （クラスフィールド）
	 */
	private final double DEFAULT_GASORIN;

	/**
	 * デフォルトコンストラクタ
	 */
	public Car() {
		this(0, 0.0);
	}

	// ナンバーのみ初期値をセットするコンストラクタ①を定義
	public Car(int num) {
		this(num, 0.0);
	}

	// ナンバーとガソリン量の初期値をセットするコンストラクタ②を定義
	public Car(int num, double gaso) {
		this(num, gaso, "(不明）");
	}

	public Car(int num, double gaso, String name) {
		this(num, gaso, name, 30);
	}

	public Car(int num, double gaso, String name, double def) {

		summary++;

		this.number = num;
		this.gasorin = gaso;
		this.name = name;
		this.DEFAULT_GASORIN = def;
	}


	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * ガソリンの値を返す
	 * @return
	 */
	public double getGasorin() {
		return this.gasorin;
	}

	/**
	 * ガソリンの値をセットする
	 * @param gasorin
	 */
	public void setGasorin(double gasorin) {
		if(gasorin < 0 || gasorin > 9999) {
			System.out.println("ガソリンは0～9999までしかセットできません！");
		} else {
			this.gasorin = gasorin;
		}
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	//クラスメソッド
	public static void showSum() {
		System.out.println("車は全部で" + summary + "台あります。");
	}


	/*
	 * ナンバーの値、ガソリン量をそれぞれコンソールに出力するメソッドを定義
	 * （アクセス修飾子はpublicで）
	 */
	public void printS() {
		System.out.println(this.name + "のナンバー  :"+this.number);
		System.out.println(this.name + "のガソリン量:"+this.gasorin);
	}

	/*
	 * デフォルトのガソリン量で走る（gasorinからデフォルト量を減算し、ガソリン残量を返す）runメソッドと、
	 * 引数に指定したガソリン量で走る（gasorinから引数指定量を減算し、ガソリン残量を返す）runメソッドを定義する
	 * ガソリンがマイナス値になったら、「ガス欠！」のメッセージをコンソール出力し、ガソリンは0に補正する
	 */
//	デフォルトのガソリン量30を減算し残った数値で条件分岐
	public double run() {
		return run(this.DEFAULT_GASORIN);
	}
//	引数のガソリン量を引き、その結果で条件分岐
	public double run(double siyou) {
		this.gasorin = gasorin - siyou;
		if(gasorin < 0) {
			gasorinEmpty();
		}
		return gasorin;
	}

	/**
	 * ガス欠時の処理
	 * メッセージ表示とガソリン量を0に補正
	 */
	private void gasorinEmpty() {
		System.out.println("ガス欠！");
		this.gasorin = 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ナンバー：" + this.number + "\n");
		sb.append("ガソリン：" + this.gasorin + "\n");
		sb.append("名前：" + this.name + "\n");
		sb.append("燃費：" + this.DEFAULT_GASORIN + "\n");

		return sb.toString();
	}

	@Override
	public boolean equals(Object arg0) {
		Car car = (Car)arg0;

//		if(number == car.number && gasorin == car.gasorin && name.equals(car.name) && DEFAULT_GASORIN == car.DEFAULT_GASORIN ) {
//			return true;
//		}else {
//			return false;
//		}
		//ナンバー、ガソリン、名前、燃費が同じならtrue、そうでなければfalseをreturnするよう実装
//		return number == car.number && gasorin == car.gasorin && name.equals(car.name) && DEFAULT_GASORIN == car.DEFAULT_GASORIN;

		return number == car.number;
	}

	@Override
	public int hashCode() {
		int hash = this.number;
		return hash;
	}

	@Override
	public int compareTo(Car arg0) {

		/*
		 * 仕様
		 * 1.this > argument ならば、正の値（通常＋１）を返す。
		 * 2.this = argument ならば、ゼロを返す。
		 * 3.this < argument ならば、負の値（通常－１）を返す。
		 */

		/*
		 * 規約
		 * 1.a.compareTo(b) = - (b.compareTo(a)) であること。（symmetry）
		 * 　a > b なら　b < a であること
		 * 2.a.compareTo(b) > 0 かつ b.compareTo(c) > 0 ならば、a.compareTo(c) > 0 となること。（transitivity）
		 * 　a > b かつ b > c なら a > c となること
		 * 3.a.compareTo(b) = 0 ならば、a.compareTo(c) = b.compareTo(c) となること。（reflexivity）
		 * 　a = b なら aとcの比較結果と bとcの比較結果が同じとなること
		 * 4.a.compareTo(b) = 0 ならば、a.equals(b) = true となること。（必須ではないが推奨）
		 * 　
		 * 5.引数が異なるクラスの場合、ClassCastException をスローすること。（不要）
		 *
		 * 6.引数が null の場合、NullPointerException をスローすること。
		 */

		// this.number と(Car)arg0.numberの値を大小比較して、上のような仕様・規約を満たす処理を実装する
		int handan = 0;

		if(this.number == arg0.number) {
			// なにもしない
			;
		} else if(this.number > arg0.number) {
			handan++;
		} else if(this.number < arg0.number ) {
			handan--;
		} else {
			// 本来、ありえない
			throw new IllegalArgumentException("Carの大小比較でありえない状況が発生");
		}

		return handan;
	}
}
