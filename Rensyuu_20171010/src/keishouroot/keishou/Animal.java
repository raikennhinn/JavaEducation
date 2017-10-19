package keishouroot.keishou;

/**
 * アクセス修飾子protectedのメソッドを持つクラス
 * keishou2パッケージのDogクラスが、本クラスを継承
 */
public class Animal {

	protected void hoeru() {
		System.out.println("ほえました");
	}

	public static void main(String args[]) {
		Animal ani = new Animal();
		ani.hoeru();
	}

}
