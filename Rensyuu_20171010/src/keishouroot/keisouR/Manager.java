package keishouroot.keisouR;

public class Manager extends Employee {
	// メンバ変数の定義
	private int staff;

	//コンストラクタによるスタッフの初期化
	public Manager(int staff) {
		this.staff = staff;
	}
	//画面表示用のメソッド定義
	public void display() {
		print();
		System.out.println("STAFF	:"+staff);
	}
}
