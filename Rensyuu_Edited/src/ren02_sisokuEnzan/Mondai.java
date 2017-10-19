package ren02_sisokuEnzan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 乗算問題
 */
public class Mondai {
	public static void main(String[] args)throws IOException{
		System.out.print("正方形の一辺の長さを入力してください：");
		BufferedReader br =
			new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		int men = Integer.parseInt(str);
		int menseki = men * men;
		System.out.println("一辺が"+str+"の正方形の面積は"+menseki+"です");

}

}
