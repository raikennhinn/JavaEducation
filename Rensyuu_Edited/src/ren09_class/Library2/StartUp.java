package ren09_class.Library2;


/**
 * クラスの練習問題　集約と委譲２
 */
public class StartUp{
	public static void main(String[] args){

		//本の情報を格納
		Book book = new Book("okok", "Java太郎");

		//図書館の情報を本の情報とともに格納
		Library lib = new Library("東京都港区", "03-1234-5678", book);

		//表示
		lib.display();
	}
}