import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * 標準入力（Scannerクラス）
 */
public class Nyuryoku {

	public static void main(String[] args) throws IOException{
		System.out.print("商品の名前を入力してください：");
//			BufferedReader br =
//				new BufferedReader(new InputStreamReader(System.in));
//			String str = br.readLine();
		Scanner scan = new Scanner(System.in);
		String str = scan.next();
			System.out.print("商品の値段を入力してください：");
//			String str1 = br.readLine();
			String str1 = scan.next();
			System.out.print("名称：");
			System.out.println(str);
			int nedan = Integer.parseInt(str1);
            System.out.println("値段："+nedan+"円");
	}

}
