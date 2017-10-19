package keishouroot.keisouR2;

public class Manager extends Employee {
	//メンバ変数定義
	private int staff;

	//コンストラクタ定義
	public Manager(int staff) {
		this.staff = staff;
	}

	public Manager(int id, String name, String section, String phone,int staff) {
		super(id, name, section, phone);
		this.staff = staff;
	}

	/**
	 * メソッドのオーバーライド実施
	 */
	@Override
	public void print() {
		super.print();
		System.out.println("STAFF	:"+staff);
	}

}
