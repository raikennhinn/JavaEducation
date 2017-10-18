package ren09_class.Library;

public class Library {
	private String address;	//住所
	private String phone;		//電話番号
	private Book book;			//本の情報

	//コンストラクタ定義
	public Library(String adress,String phone,Book book) {
		this.address = adress;
		this.phone = phone;
		this.book = book;
	}

	//displayメソッド定義
	public void display() {
		System.out.println("---図書館の情報---");
		System.out.println("住所："+address);
		System.out.println("電話番号："+phone);
		System.out.println("---本の情報---");
		System.out.println("タイトル："+book.title);
		System.out.println("著者名："+book.author);
	}

}
