package ren08_nyushuturyoku;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 入出力（標準入力、ファイル入出力）
 */
public class Hello2 {

	public static void main(String[] args) {


//		PrintWriter pw = null;
//
//		try {
//
//			pw = new PrintWriter(new BufferedWriter(new FileWriter("test.txt")));
//
//			pw.println("Hello");
//			pw.println("World!!");
//			pw.format("Hello, %d", 10);
//
//			System.out.println("書き込み完了");
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			pw.close();
//		}
//
//		File file = new File("test2.txt");
//		FileWriter filewriter = null;
//		try {
//			filewriter = new FileWriter(file);
//			filewriter.write("こんにちは\n");
//			filewriter.write("こんばんは");
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				filewriter.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}

		/*
		 * ファイルから文字を読み込む
		 * 使いやすい方法
		 */
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("test.txt"));

			while(br.ready()) {
				System.out.println(br.readLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		} finally {

			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/*
		 * ファイルから文字を読み込む
		 * 原始的な方法
		 */
		File file = new File("test2.txt");
		FileReader filereader = null;
		try {
			filereader = new FileReader(file);
			int ch;

			while((ch = filereader.read()) != -1){
			    System.out.print((char)ch);
			}

		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
			try {
				filereader.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}


//		//値のコピー
//		int a = 10;
//		int b = a;
//		b = b + 10;
//
//		System.out.println("a:" + a);
//		System.out.println("b:" + b);
//
//
//		// 参照のコピー
//		int[] aa = new int[3];
//		aa[0] = 1;
//
//
//		int[] bb = {1, 2, 3};
//		bb[0] = 10;
//
//		System.out.println("aa[0]:" + aa[0]);// 1じゃないの？
//		System.out.println("bb[0]:" + bb[0]);// 10



//		while(true) {
//			try {
//
//				System.out.println("1～5の数字を入力してください");
//				BufferedReader br =
//					new BufferedReader(new InputStreamReader(System.in));
//
//				String str = br.readLine();
//				int num = Integer.parseInt(str);
//
//				System.out.println("入力したのは" + num + "です");
//
//				String[] animal = {"ひよこ","うさぎ","ぱんだ","こあら","らくだ"};
//
//				System.out.println(num + "は、" + animal[num - 1] + "です");
//
//			} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
//				System.out.println("ちゃんと入力してください！");
//				e.printStackTrace();
//			} catch (IOException ie) {
//				ie.printStackTrace();
//
//			} catch(Exception e) {
//				e.printStackTrace();
//			} finally {
//				System.out.println("完了！");
//			}
//		}
	}
}
