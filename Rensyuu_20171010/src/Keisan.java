import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 加算、乗算
 */
public class Keisan {
	public static void main(String[] args)throws IOException {
		System.out.print("数値を2つ入力してください");

		BufferedReader br =
			new BufferedReader(new InputStreamReader(System.in));
		String str1 = br.readLine();
		String str2 = br.readLine();
		int kazu1 = Integer.parseInt(str1);
		int kazu2 = Integer.parseInt(str2);
		System.out.println("二つの数値を足した結果は"+(kazu1+kazu2)+"です");
		System.out.println("二つの数値を掛け算した結果は"+(kazu1*kazu2)+"です");


	}

}
