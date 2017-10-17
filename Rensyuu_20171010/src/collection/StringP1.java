package collection;

/**
 * 文字列の長さ
 */
public class StringP1 {
	public static void main(String[] args) {
		String str = "Hello!";
		System.out.println(str+"の文字数は"+str.length()+"文字です");

		for(int i = 0; i < str.length(); i++) {
			System.out.println(str+"の"+ i + "文字目は"+str.charAt(i)+"です");
		}
	}
}
