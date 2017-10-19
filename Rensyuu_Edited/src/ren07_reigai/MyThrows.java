package ren07_reigai;

/**
 * 練習でつくったクラスです
 * 実行時例外をthrowする
 * @author i1621
 * @version 1.0
 */
public class MyThrows {

	// メンバ変数（フィールド）

	private int[] number = {10,20,30,40};

	/**
	 * 文字列の数値変換
	 * @param s 変換したい文字列
	 * @return 変換した数値
	 * @throws NumberFormatException
	 */
	public int convertNumber(String s) throws NumberFormatException {

		int num = Integer.parseInt(s);

		return num;
	}

	/**
	 * 情報の表示
	 * @param i 添え字
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public void exFunc(int i) throws ArrayIndexOutOfBoundsException{
		System.out.println("要素番号：" + i + "の値は" + number[i] + "です。");
	}

}
