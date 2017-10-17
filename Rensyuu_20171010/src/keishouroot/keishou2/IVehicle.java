package keishouroot.keishou2;

/**
 * インターフェースの例
 */
public interface IVehicle {

	int NUMBER = 10;

	void show();

	//上のメソッドと定義としては同じ（インターフェースではpublic abstract がデフォルトで定義される）
	public abstract int print(String str);



}
