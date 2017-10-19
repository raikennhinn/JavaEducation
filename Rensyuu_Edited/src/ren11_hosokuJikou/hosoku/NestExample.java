package ren11_hosokuJikou.hosoku;

/**
 * ネストしたクラスの例
 * ・staticなネストしたクラス
 * ・非staticなネストしたクラス（内部クラス）
 * ・ローカル内部クラス
 * ・匿名クラス
 */
public class NestExample {

	/**
	 * フィールド
	 */
	private int i = 0;

	/**
	 * コンストラクタ
	 */
	public NestExample() {

	}

	/**
	 * メソッド
	 */
	public void tekito() {

		// staticなネストしたクラスを使う
		StaticNest.staticNestMethod();

		// 非staticなネストしたクラス（内部クラス）を使う
		NonStaticNest nn = new NonStaticNest();
		nn.NestMethod();

		/**
		 * ローカル内部クラス（抽象クラス）
		 */
		abstract class LocalClass {

			private int localint = 0;

			LocalClass(int li) {
				this.localint = li;
			}

			public String message() {
				return "ローカル内部クラスです";
			}

			protected abstract void run();
		}

		/**
		 * ローカル内部クラス（抽象クラスを継承したサブクラス）
		 */
		class SubLocalClass extends LocalClass {

			SubLocalClass(int li) {
				super(li);
			}

			@Override
			public void run() {
				System.out.println("走れ！");
			}

		}

		// ローカル内部クラスを使う
		LocalClass lc = new SubLocalClass(100);

		int ii = lc.localint;
		System.out.println(lc.message());
		lc.run();
	}

	/**
	 * staticなネストしたクラス
	 * @author i1621
	 *
	 */
	private static class StaticNest {

		public static void staticNestMethod() {

		}
	}

	/**
	 * 非staticなネストしたクラス（内部クラス）
	 */
	private class NonStaticNest {

		public void NestMethod() {

		}
	}

}
