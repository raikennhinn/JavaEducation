package ren11_hosokuJikou.hosoku;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ren10_keisho_interface.keishouroot.keishou.AbsAnimal;
import ren10_keisho_interface.keishouroot.keishou.Car;

/**
 * Java補足
 * <li>文字列の大小比較</li>
 * <li>文字列数値変換</li>
 * <li>可変長引数</li>
 * <li>ネストクラス</li>
 * <li>Arraysクラス</li>
 * <li>ラベルを使ったループの脱出</li>
 * <li>DigDecimalクラス</li>
 * <li>enum</li>
 * <li>try-with-resource文</li>
 * <li>例外の自作とthrow</li>
 */
public class StartUp {

	public static void main(String[] args) throws MyException {

		/*
		 * Stringの比較（compareTo)
		 */
		String s1 = "apple";
		String s2 = "Japan";

		/*
		 * 大なら正の数、小なら負の数
		 */
		System.out.println(s1.compareTo(s2));

		/*
		 * 文字列＞数値変換
		 */
		int ip = Integer.parseInt("100");
		double dp = Double.parseDouble("512.36");
		short sp = Short.parseShort("120");

		// 文字列を直接数値からに入れるのはダメ。
		// 型変換されるのは、同じ数値型どうしのみ
//		int j = "100";

		// 数値をStringに代入もできない
//		String s3 = 100;

		// 文字列に対して数値を+すると、数値が文字列に変換される
		String s4 = "お金が" + 100;

		/*
		 * 数値＞文字列変換
		 */
		String intStr = Integer.toString(100);
		String doubleStr = Double.toString(100.25);
		String floatStr = Float.toString(52.3f);

		/*
		 * ラッパークラス
		 * Integer、Double、Float
		 */

		/*
		 * 可変長引数
		 */
		kahenExample(2,8,4,5,15,1,5);


		/*
		 * ネストしたクラス
		 * ・staticなネストしたクラス
		 * ・非staticなネストしたクラス（内部クラス）
		 * ・ローカル内部クラス
		 * ・匿名クラス
		 */
		NestExample ne1 = new NestExample();
		ne1.tekito();

		//privateなネストクラスは外部では使えない
//		NextExample.StaticNest.staticNestMethod();
//
//		NonStaticNext nsn1 = new NextExample.NonStaticNest();
//		nsn1.NestMethod();

		//匿名クラス
		new AbsAnimal() {
			@Override
			protected void cry() {
				System.out.println("もー");
			}
		}.cry();


		ArrayList<Car> carList = new ArrayList<Car>();
		carList.add(new Car(1234));
		carList.add(new Car(6234));
		carList.add(new Car(9133));
		carList.add(new Car(372));
		carList.add(new Car(5319));

		// 匿名クラスを使ってソート順を降順に変更してソート
		Collections.sort(carList, new Comparator<Car>() {
			@Override
			public int compare(Car arg0, Car arg1) {
				return arg1.number - arg0.number;
			}
		});
		// ソートしたあとのcarListを並び順にアクセスして、println()で情報を出力する
		for(int i = 0; i <  carList.size(); i++) {
			System.out.println("ナンバー：" + carList.get(i).getNumber());
		}

		/*
		 * Arraysクラス
		 */
		String[] strs = {"ああ","いい","うう"};

		// 配列をリストに変換（ただしリストの変更不可）（リストが内部的に配列を参照しているため）
		List<String> listStrs = Arrays.asList(strs);
//		listStrs.add("ふぁｓふぁ");

		// 配列をリストに変換（リストの変更化）（コピーしているため）
		List<String> listStrs2 = new ArrayList<String>();
		Collections.addAll(listStrs2, strs);

		// リスト（に限らずコレクション全般）を配列に変換
		String[] strs2 = listStrs2.toArray(new String[listStrs2.size()]);

		/*
		 * ラベルを使ったループのbreak
		 */
		int loopcnp = 0;
		loopout:
		while(true) {
			System.out.println("第一ループ");
			while(true) {
				System.out.println("第二ループ");
				if(loopcnp == 50) {
					break loopout;
				}
				loopcnp++;
			}
		}

		/*
		 * BigDecimal
		 */
		BigDecimal bd = new BigDecimal(10000.00);
		BigDecimal bd2 = new BigDecimal(3000.00);
		bd = bd.add(bd);
		System.out.println(bd);
		bd = bd.subtract(bd2);
		System.out.println(bd);

		/*
		 * 列挙型（enum)
		 */
		Kakari k = Kakari.ICHI_KAKARI;
		k = Kakari.NI_KAKARI;

		if(k == Kakari.NI_KAKARI) {
			System.out.println("あなたは２係です。");
		}

		System.out.println("私は" + k.getBangou() + k.getName() + "所属です");



		switch(k) {
		case ICHI_KAKARI:
			System.out.println("あなたは１係です。");
			break;
		case NI_KAKARI:
			System.out.println("あなたは２係です。");
			break;
		case SAN_KAKARI:
			System.out.println("あなたは３係です。");
			break;
		case YON_KAKARI:
			System.out.println("あなたは４係です。");
			break;
		default:
			System.out.println("あなたは無所属です。");
		}

		/*
		 * try-with-resource文
		 */
		try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("test.txt")));) {

			pw.println("Hello");
			pw.println("World!!");
			pw.format("Hello, %d", 10);

			System.out.println("書き込み完了");

		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * 例外の自作とthrow
		 */
		MyException me = new MyException("自作の例外！！");
		throw me;
	}

	/**
	 * 開発部の係をあらわす列挙型
	 */
	public enum Kakari {
		ICHI_KAKARI(1, "１係"),
		NI_KAKARI(2, "２係"),
		SAN_KAKARI(3, "３係"),
		YON_KAKARI(4, "４係");

		private final int kakariBangou;
		private final String kakariName;

		Kakari(int bangou, String name) {
			this.kakariBangou = bangou;
			this.kakariName = name;
		}

		public int getBangou() {
			return this.kakariBangou;
		}
		public String getName() {
			return this.kakariName;
		}
	}

	//かつてのJavaはintの定数で上の列挙型に近いものを表現していた
	static final int ICHI_KAKARI = 0;
	static final int NI_KAKARI = 1;
	static final int SAN_KAKARI = 2;
	static final int YON_KAKARI = 3;

	/**
	 * 可変長引数メソッド
	 */
	private static void kahenExample(int s, int... ints) {
		for(int in : ints) {
			System.out.println("引数は:" + in + "です。");
		}

		for(int i = 0; i < ints.length; i++) {
			System.out.println("引数intsの" + i + "番目は:" + ints[i] + "です。");
		}
	}
}
