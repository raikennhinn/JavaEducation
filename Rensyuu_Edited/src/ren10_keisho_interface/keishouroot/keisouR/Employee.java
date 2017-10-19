package ren10_keisho_interface. keishouroot.keisouR;

class Employee{
	//メンバ変数
	private int id;				//ID
	private String name;		//名前
	private String section;		//部署
	private String phone;		//内線

	//コンストラクタ(引数なし)
	public Employee(){
	}

	//コンストラクタ(引数4つ)
	public Employee(int id, String name, String section, String phone){
		this.id      = id;
		this.name    = name;
		this.section = section;
		this.phone   = phone;
	}

	//表示メソッド
	public void print(){
		System.out.println("ID      :" + id);
		System.out.println("NAME    :" + name);
		System.out.println("SECTION :" + section);
		System.out.println("PHONE   :" + phone);
	}
}

