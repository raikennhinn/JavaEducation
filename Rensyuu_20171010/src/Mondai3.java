import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 繰り返しの加算と平均計算
 */
public class Mondai3 {
	public static void main(String[] args) throws IOException{
//		テストの点数を入力させる
		System.out.println("テストの点数を入力する");
		int goukei = 0;
		BufferedReader br =
				new BufferedReader(new InputStreamReader(System.in));
		for(int i=0;i<5;i++) {
			String str = br.readLine();
			int ten = Integer.parseInt(str);
			goukei = goukei + ten;
		}
		System.out.println("5つの点数の合計は"+goukei+"点");
		double ave = (double)goukei / 5;
		System.out.println("平均は"+ave+"です");
	}

}