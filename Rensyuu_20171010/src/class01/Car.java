package class01;

import java.util.Date;

/**
 * 車をあらわすクラス
 * セッタ、ゲッタ、オーバーロード、クラスフィールド・クラスメソッドの例
 */
public class Car {

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
			return gasorin;
		} else {
			return gasorin;
		}
	}

	/**
	 * ガス欠時の処理
	 * メッセージ表示とガソリン量を0に補正
	 */
	private void gasorinEmpty() {
		System.out.println("ガス欠！");
		this.gasorin = 0;
	}
}
