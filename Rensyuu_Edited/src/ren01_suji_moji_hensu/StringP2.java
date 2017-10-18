package ren01_suji_moji_hensu;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 文字列の長さ２
 */
public class StringP2 {
	public static void main(String[] args) throws IOException{
		System.out.print("文字列を入力してください:");
		BufferedReader br =
				new BufferedReader(new InputStreamReader(System.in));
			String str = br.readLine();

			System.out.println(str+"は"+str.length()+"文字です。");
	}

}
