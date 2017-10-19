package ren12_jdbc.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JavaDataAccess01 {
	public static void main (String args[])
			  throws SQLException, ClassNotFoundException {

		// MySQL JDBC Driverのロード
		Class.forName("org.gjt.mm.mysql.Driver");

//		Connection conn = null;
//		Statement stmt = null;
//		ResultSet rset = null;
//
//		try {
//			// MySQLに接続
//			conn = DriverManager.getConnection(
//					"jdbc:mysql://localhost/sampledb040?useUnicode=true&characterEncoding=UTF-8", "pgtop", "12345");
//			// ステートメントを作成
//			stmt = conn.createStatement();
//			// 問合せの実行
//			rset = stmt.executeQuery("select pref_cd, pref_name from t01prefecture");
//			// 問合せ結果の表示
//			while (rset.next()) {
//				// 列番号による指定
//				System.out.println(rset.getInt(1) + "\t" + rset.getString(2));
//			}
//		} finally {
//			// 結果セットをクローズ
//			rset.close();
//			// ステートメントをクローズ
//			stmt.close();
//			// 接続をクローズ
//			conn.close();
//		}

		// Java7以降のもっとも短い書き方
		try (Connection conn = DriverManager.getConnection(
						"jdbc:mysql://localhost/sampledb040?useUnicode=true&characterEncoding=UTF-8", "pgtop", "12345");
				) {

			// 検索
			try (
					Statement stmt = conn.createStatement();
					// t00areaと結合して、東海地方の県名とエリア名の一覧を出力する

					// 【StringBuilderをつかってSQLを組み立てる】

					ResultSet rset =
							stmt.executeQuery("select pref_name,t00area.area_name from t01prefecture join t00area on t01prefecture.area_CD = t00area.area_CD ");
					){
				while (rset.next()) {
					// 列番号による指定
					System.out.println(rset.getString(1) + "\t" +rset.getString(2));
				}
			}

			conn.setAutoCommit(false);

			// 追加
			try (  // ステートメントの作成
					PreparedStatement ps =
					conn.prepareStatement("insert into t01prefecture values(?, ?, ?)");){
				// パラメータの設定
				ps.setInt(1, 51);
				ps.setString(2, "トラン");
				ps.setInt(3, 2);

				// ステートメントの実行
				int result = ps.executeUpdate();

				//【更新】

				//【削除】

				// コミット、ロールバック
				if (result > 0) {
					conn.commit();
				} else {
					conn.rollback();
				}
			}
		}
	}
}