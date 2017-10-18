package ren07_reigai;


/**
 * MyThrowsクラスの使用
 */
public class StartUp {

	public static void main(String[] args) {

		try {
			MyThrows my = new MyThrows();

			int index = my.convertNumber(args[0]);

			my.exFunc(index);

		} catch (NumberFormatException e) {
			System.out.println("数値を入力してください");
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("要素数を超えています");
		}

	}

}
