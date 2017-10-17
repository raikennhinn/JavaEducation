package keishouroot.keishou2;

import keishouroot.keishou.Car;
import keishouroot.keishou.HibulitCar;

/**
 * 各種クラスの利用
 * クラス型の配列、instanceof演算子
 */
public class StartUp {

	public static void main(String[] args) {


//		Cat neko = new Cat();
//		neko.cry();
//		neko.run();
//
//


//		HibulitCar hcar = new HibulitCar();
//
//		hcar.setName("プリウス");
//		hcar.setGasorin(100.0);
//		hcar.setDenki(40);
//		hcar.erun(20);
//		hcar.erun(20);
//		hcar.erun(20);
//
//		hcar.printS();
//
//		HibulitCar hcar2 = new HibulitCar(123456.0, 1234, 200.0, "レクサス");
//		hcar2.printS();

//
		Car[] cars = {
				new Car(10,4.0, "ふつうのくるま"),
				new HibulitCar(4.0, 100, 10.0, "はいぶりっとなくるま")
		};


		for(Car car : cars) {
			car.printS();
			if(car instanceof HibulitCar) {
				HibulitCar h = (HibulitCar)car;
			} else if(car instanceof Car) {
				car.run();
			}
		}
//
//		HibulitCar hcar = new HibulitCar();
//
//		hcar.printS();



//		List<String> list = new ArrayList<String>();
//		list = new LinkedList<String>();
//
//
//		for(int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i));
//		}
	}

}
