package keishouroot.keishou2;

import keishouroot.keishou.Animal;

/**
 * パッケージをまたいだインポート・継承の例
 */
public class Dog extends Animal {

	public static void main(String[]args) {
		Animal ani = new Animal();
		//アクセス修飾子がprotectedの場合、パッケージをまたいでの呼び出しはできない
//		ani.hoeru();

		//サブクラスではpublicにオーバーライドしたため、呼び出し可能
		Dog dog = new Dog();
		dog.hoeru();
	}
}
