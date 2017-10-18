package ren06_hizukeJikoku.datetime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日付時刻
 */
public class DateTimeTest {

	public static void main(String[] args) {
		Date date = new Date();
		System.out.println(date);
//		System.out.println(date.getDay());
//		System.out.println(date.getYear());
//		System.out.println(date.getMonth());
//		System.out.println(date.getDate());
//		System.out.println(date.getHours());
//		System.out.println(date.getMinutes());
//		System.out.println(date.getSeconds());
		System.out.println(date.getTime());


		Calendar cal1 = Calendar.getInstance();

        int year = cal1.get(Calendar.YEAR);        //(2)現在の年を取得
        int month = cal1.get(Calendar.MONTH) + 1;  //(3)現在の月を取得
        int day = cal1.get(Calendar.DATE);         //(4)現在の日を取得
        int hour = cal1.get(Calendar.HOUR_OF_DAY); //(5)現在の時を取得
        int minute = cal1.get(Calendar.MINUTE);    //(6)現在の分を取得
        int second = cal1.get(Calendar.SECOND);    //(7)現在の秒を取得

        cal1.set(2017, 9, 5);

//        StringBuffer dow = new StringBuffer();
        StringBuilder dow = new StringBuilder();
        switch (cal1.get(Calendar.DAY_OF_WEEK)) {  //(8)現在の曜日を取得
            case Calendar.SUNDAY: dow.append("日曜日"); break;
            case Calendar.MONDAY: dow.append("月曜日"); break;
            case Calendar.TUESDAY: dow.append("火曜日"); break;
            case Calendar.WEDNESDAY: dow.append("水曜日"); break;
            case Calendar.THURSDAY: dow.append("木曜日"); break;
            case Calendar.FRIDAY: dow.append("金曜日"); break;
            case Calendar.SATURDAY: dow.append("土曜日"); break;
        }

        //(9)現在の年、月、日、曜日、時、分、秒を表示
        System.out.println(year + "/" + month + "/" + day + " " + dow
            + " " + hour + ":" + minute + ":" + second);


      //フォーマットパターンを指定して、SimpleDateFormatオブジェクトsdf1を生成します。
      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:MM:ss");

//      //フォーマットパターン、ロケールを指定して、SimpleDateFormatオブジェクトsdf1を生成します。
//      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

      System.out.println(sdf1.format(cal1.getTime()));  //(3)Dateオブジェクトを表示

//      sdf1.applyPattern("yyyy/MM/dd");  //(4)フォーマットパターンを変更
//      System.out.println(sdf1.format(date1));  //(5)Dateオブジェクトを表示

	}

}
