package keishouroot.keishou2;

import keishouroot.keishou.Car;

/**
 * スーパークラスの継承とインターフェースの実装例
 */
public class EVCar extends Car implements IVehicle, IAnimal {

	public EVCar() {
	}

	public EVCar(int num) {
		super(num);
	}

	public EVCar(int num, double gaso) {
		super(num, gaso);
	}

	public EVCar(int num, double gaso, String name) {
		super(num, gaso, name);
	}

	public EVCar(int num, double gaso, String name, double def) {
		super(num, gaso, name, def);
	}

	@Override
	public void show() {
	}

	@Override
	public int print(String str) {
		return 0;
	}

	@Override
	public void hoeru() {

	}

}
