package Library;

/**
 * クラスの練習問題　集約と委譲１
 */
public class StartUp {
	public static void main(String[] args) {
		//Bookクラスのオブジェクトを生成
		Book book = new Book("Javaプログラミング基礎","Java太郎");
		Library lib = new Library("東京都港区","03-1234-5678",book);

		lib.display();

	}

}
