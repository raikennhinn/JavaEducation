package ren09_class.class01;

import java.util.ArrayList;

/**
 * Carクラスの使用
 */
public class StartUp {

	public static void main(String[] args) {


		Car car1 = new Car();	// CarのインスタンスCar1を生成

		System.out.println(car1.getName());


		car1.number = 2154534;
		car1.setGasorin(931.3213);
		car1.setName("プリウス");
		car1.printS();
		System.out.println(car1.getName() + "走った。ガソリン残量:" + car1.run());

		Car car2 = new Car();	// CarのインスタンスCar2を生成

		car2.number = 412343124;
		car2.setGasorin(30.424);
		car2.setName("レクサス");
		car2.printS();
		System.out.println(car2.getName() + "走った。ガソリン残量:" + car2.run(50));


		Car.showSum();


		// CarのインスタンスCar3を、コンストラクタ①を使って生成
		Car car3 = new Car(548930);
		// CarのインスタンスCar4を、コンストラクタ②を使って生成
		Car car4 = new Car(234123,45.5);
		Car car5 = new Car(342432, 43.5, "タント");
		Car car6 = new Car(342432, 60.5, "タント", 50.0);
		car6.printS();
		System.out.println(car6.getName() + "走った。ガソリン残量:" + car6.run());

		car6.setGasorin(100);
		car6.printS();
		System.out.println(car6.getName() + "走った。ガソリン残量:" + car6.run(150));

		Car.showSum();


		Car[] cars = new Car[6];
		cars[0] = car1;
		cars[1] = car2;
		cars[2] = new Car();

		ArrayList<Car> carList = new ArrayList<Car>();
		carList.add(car1);
		carList.add(car2);
		carList.add(car3);
		carList.add(car5);

		carList.get(3).printS();
	}

}
