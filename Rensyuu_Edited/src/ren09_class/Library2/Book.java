package ren09_class.Library2;

public class Book{

	//メンバ変数
	String title;		//タイトル
	String author;		//著者名

	//コンストラクタ
	Book(String title, String author){
		this.title  = title;
		this.author = author;
	}

	//本の情報を表示
	void display(){
		System.out.println("タイトル:" + title);
		System.out.println("著者名:" + author);
	}
}