package test1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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



		}catch(SQLException e){
			System.out.println("×");
		}

		System.out.println(conn);
	}

}
