package ren03_roop_haisetsu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * <li>or条件</li>
 * <li>forループ</li>
 * <li>forループのネスト</li>
 * <li>whileループ</li>
 * <li>配列（数値、文字列）</li>
 * <li>多次元配列</li>
 */
public class Hello {

	public static void main(String[] args) {





		try {

			BufferedReader br =
				new BufferedReader(new InputStreamReader(System.in));

			String str = br.readLine();

//			int res = Integer.parseInt(str);
//
//			// or条件 5より大きい　もしくは 2より小さい
//			if( res > 2 && res < 5 ||  && ) {
//
//
//
//				System.out.print("Good!");
//			}

			// 5回入力させて、偶数回目の入力値を出力する
			// これをfor文、while文でそれぞれ記述する

//			for (int i=1;i<6;i++) {
//				String str1 = br.readLine();
//				int res1 = Integer.parseInt(str1);
//				int gusu = i % 2;
//			   if(gusu == 0) {
//				   System.out.println(i+"回目の数値"+res1);
//			   }
//			}

//			for(int i = 1; i <= 9; i++) {
//				for(int j = 1; j <= 9; j++) {
//					if(j == 9) {
//						System.out.println(i * j);
//					} else {
//						System.out.print(i * j + ", ");
//					}
//				}
//			}
//
//
//			int i = 1;
//			while(i<6) {
//
//				String str1 = br.readLine();
//				int res1 = Integer.parseInt(str1);
//				int gusu = i % 2;
//				if(gusu == 0) {
//					System.out.println(i+"回目の数値"+res1);
//				}
//				i++;
//			}
//
//			int b = (1,10);

//			int[] b = {0, 1, 2, 3, 4, 5};
//			b = new int[6];
//			b[0] = 0;
//			b[1] = 1;
//			b[2] = 2;
//			b[3] = 3;
//			b[4] = 4;
//			b[5] = 5;
//
//			for(int i = 0; i < 6; i++) {
//				System.out.println(b[i]);
//			}
//
//			for(int bi : b) {
//				System.out.println(bi);
//			}

			// forでもwhileでもいいので、サイズ10の配列を宣言して、その中に1～10の値を入れる
//			int[] a;
//			a = new int[10];
//			int len = a.length;
//			System.out.println("int b の長さ：" + len);
//
//			for(int i = 0; i < len; i++) {
//				a[i] = i + 1;
//				System.out.println(a[i]);
//			}

//			String[] abc = new String[3];
//			abc[0] = "Hello";
//			abc[1] = "World";
//			abc[2] = "!!!";
//
//
//			String hello = "Hello World!!";
//			hello.length();
//			System.out.println("String hello の長さ：" + hello.length());
//
//			Hello[] hellos;


//
		} catch (IOException e) {
			e.printStackTrace();
		}

		int[][]  kuku;
		kuku =new int[9][9];
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				 kuku[i][j] = (i + 1) * (j + 1);
	//			if(j == 9) {
	//				System.out.println(kuku[i][j]);
	//			} else{
	//				System.out.print(kuku[i][j]+", ");
	//			}
			}
		}


		for(int[] i : kuku) {
			for(int j : i) {
				System.out.print(j + ", ");
			}
		}
	}
}
