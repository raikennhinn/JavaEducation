import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 余り（余剰）の計算
 */
public class Suzi1 {

	public static void main(String[] args) throws IOException{
		System.out.print("整数を入力してください：");
		BufferedReader br =
			new BufferedReader(new InputStreamReader(System.in));

			String str = br.readLine();
			int res = Integer.parseInt(str);

			int amari = res % 2;

			System.out.println(amari);

			if(amari == 1) {
				System.out.println("奇数です");
			} else if(amari == 0) {
				System.out.println("偶数です");
			} else {
				System.out.println("数字ではありません");
			}
	}

}
