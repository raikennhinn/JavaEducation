package ren04_bunki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * switch文の例
 */
public class Switch1 {

	public static void main(String[] args) throws IOException{
		System.out.println("１か２を入力してください");
		BufferedReader br =
			new BufferedReader(new InputStreamReader(System.in));

		String str = br.readLine();
		int res = Integer.parseInt(str);

		switch(res) {
		case 1:
			System.out.println("1が入力されました。");
			break;
		case 2:
			System.out.println("2が入力されました。");
			break;
		default:
			System.out.println("1と2以外が入力されています。");
			break;
		}
	}

}
