package keishouroot.keishou3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import keishouroot.keishou.Car;

/**
 * Objectクラスのメソッドのオーバーライド、
 * Comparableインターフェースの実装
 */
public class StartUp {

	public static void main(String[] args) {

		/*
		 * toString()のオーバーライド
		 */
		Car car = new Car(1000, 100.0, "いぷさむ", 10);
		System.out.println(car);

		/*
		 * Stringはequals()で比較する
		 */
		String s1 = "ああ";
		String s2 = "あ";
		s2 += "あ";// s2 = new String("ああ")

		System.out.println(s1);
		System.out.println(s2);

		if (s1.equals(s2)) System.out.println("同じです。");

		/*
		 * equals()のオーバーライド
		 */
		Car car1 = new Car(1000, 100.0, "いぷさむ", 10);
		Car car2 = new Car(1000, 100.0, "いぷさむ", 10);

		if (car1.equals(car2)) System.out.println("同じです。");

		/*
		 * hashCode()のオーバーライド
		 */
		Car car3 = new Car(100, 10.0, "いぷさむ", 30);
		System.out.println(car3);

		/*
		 * Comparableインターフェースの実装とcompareTo()のオーバーライド
		 */
		Car car4 = new Car(10001);
		Car car5 = new Car(10000);

		if (car4.equals(car5)) System.out.println("同じです。");

		switch (car4.compareTo(car5)) {
			case 1:
				System.out.println("car4が大きい");
				break;
			case 0:
				System.out.println("car4とcar5は同じ");
				break;
			case -1:
				System.out.println("car4が小さい");
				break;
			default:
				System.out.println("ありえない");
				break;
		}

		// ArrayListにCarオブジェクトを、ナンバーの大小順をバラバラに追加して、
		// それをソートする
		// ソートはCollections.sort(List list)を使う

		ArrayList<Car> carList = new ArrayList<Car>();
		carList.add(new Car(1234));
		carList.add(new Car(6234));
		carList.add(new Car(9133));
		carList.add(new Car(372));
		carList.add(new Car(5319));

		Collections.sort(carList);

		// ソートしたあとのcarListを並び順にアクセスして、println()で情報を出力する
		for(int i = 0; i <  carList.size(); i++) {
			System.out.println("ナンバー：" + carList.get(i).getNumber());
		}

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
	}

}
