package ren06_hizukeJikoku.day;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 日付を取得して表示する練習
 */
public class Day1 {
	public static void main(String[] args) {
//		Calenderを使用する
		Calendar cal1 = Calendar.getInstance();
//		日付を指定する
		cal1.set(2017, 11, 6);
//		表示を実行
		System.out.println(cal1.getTime());

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy'年'MM'月'dd'日'");
		 System.out.println(sdf1.format(cal1.getTime()));
	}

}
