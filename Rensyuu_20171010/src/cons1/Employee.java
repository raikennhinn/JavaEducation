package cons1;

/**
 * 従業員クラス
 * メンバ変数とコンストラクタ、メソッド
 */
public class Employee {
	//メンバ変数の定義
	private int id;			//ID
	private String name;		//名前
	private String section;	//部署
	private String phone;		//内線

	//コンストラクタの定義
	public Employee(int id,String name,String section,String phone) {
		this.id = id;
		this.name = name;
		this.section = section;
		this.phone = phone;
	}
	public void print() {
		System.out.println("ID		:"+id);
		System.out.println("NAME	:"+name);
		System.out.println("SECTION	:"+section);
		System.out.println("PHONE	:"+phone);
	}
}
