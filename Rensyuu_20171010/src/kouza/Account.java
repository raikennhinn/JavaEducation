package kouza;

public class Account {
	//メンバ変数定義
	private static float interest = 0.002f;		//利率
	private String name;			//名前
	private float balance;		//残高

	//コンストラクタ引数ひとつを定義
	public Account(String name) {
		this.name = name;
	}
	//コンストラクタ引数２つを定義
	public Account(String name, float balance) {
		this.name = name;
		this.balance = balance;
	}

	//メソッドの定義
	public void print(){
		System.out.println("Name:"+name);
		System.out.println("Balance"+balance);
	}
	public void interestAt() {

		balance = balance + balance * interest;
	}
	public static float getInterest() {
		 return interest;
	}
}
