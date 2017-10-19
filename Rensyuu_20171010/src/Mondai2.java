import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 乗算と除算（小数点が出るためdoubleを用いる）
 */
public class Mondai2 {
	public static void main(String[] args)throws IOException {
		System.out.print("三角の高さ:");
		BufferedReader br =
				new BufferedReader(new InputStreamReader(System.in));
			String str = br.readLine();
			int takasa = Integer.parseInt(str);
		System.out.print("三角の底辺:");
			String str1 = br.readLine();
			int teihen = Integer.parseInt(str1);
//			面積の計算を実行
			double menseki = (double)takasa * (double)teihen / 2;
			System.out.println("三角形の面積は"+menseki+"です");
	}

}
