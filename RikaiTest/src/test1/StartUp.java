package test1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class StartUp {

	public static void main(String[] args) {
		// 第一問　JDBCドライバを使って、MySQLとの接続を行う
		// とりあえずエラーがでなければOK
		// localhostのMySQLに、rikaiユーザーで認証。（パスワードもrikai）
		// データベースはsampledb040に接続

		//不要　→　Class.forName("[MySQLのJDBCドライバークラス（パッケージ含めたクラス名を文字列で指定]");
		// 参考：http://www.ne.jp/asahi/hishidama/home/tech/java/DriverManager.html#JDBC4.0
//		try {
//			Class.forName("org.gjt.mm.mysql.Driver");
//		} catch (ClassNotFoundException e2) {
//			e2.printStackTrace();
//		}

		//必要事項の記述
		Connection conn = null;
		String url = "jdbc:mysql://localhost/sampledb040";
		String user = "rikai";
		String password = "rikai";
		//接続実施
		try {
			conn = DriverManager.getConnection(url, user, password);
			// １従業員情報（社員番号、所属コード、所属部・課・係（SQLで文字連結させて）、名前、性別（Java内で男女の文字に変換）enumを使う、年齢、生年月日）を取得して
			// ２結果を二次元配列に格納する
			// ３従業員のデータ数は、実行するまでわからないものとする

			//ステートメントの作成（問い合わせ等を実施するために必要）
			Statement stmt1 = conn.createStatement();
			Statement stmt2 = conn.createStatement();
			Statement stmt3 = conn.createStatement();
			//SQL文を記述し、実行する
			String sqlemployee = "SELECT * FROM employee";
			String sqlshozoku = "SELECT * FROM shozoku";
			String sqlbusyo = "SELECT CONCAT(shozoku_bu,shozoku_ka,shozoku_kakari) from shozoku";

			ResultSet rs = stmt1.executeQuery(sqlemployee);
			ResultSet rss = stmt2.executeQuery(sqlshozoku);
			ResultSet busyo = stmt3.executeQuery(sqlbusyo);


			 //データの表示
			  //二次元配列の定義

			//rs（employee）のデータを取得
			 while(rs.next()) {
				 int code = rs.getInt("employee_no");			//社員番号
				 int shozoku = rs.getInt("shozoku_code");		//所属コード
				 String name = rs.getString("employee_name");	//名前
				 int sex = rs.getInt("sex");					//性別
				 //性別の結び付け(数を文字へ)を実施する

//				 enumの記述
//				 public enum {
//
//				 }

				 int age = rs.getInt("age");					//年齢
				 Date birthday = rs.getDate("birthday");		//生年月日
				 System.out.println(code + ":"+ shozoku +":"+name+ ":" );
				 //配列にデータを格納していく

			 }
			 //ress(shozoku)のデータを取得shozokuとemployeeの結びつけ
			 //busyoのデータ取得



			 rs.close();
			 rss.close();
			 busyo.close();
			 stmt1.close();
			 stmt2.close();
			 stmt3.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
//		System.out.println(conn);
		}
	}

