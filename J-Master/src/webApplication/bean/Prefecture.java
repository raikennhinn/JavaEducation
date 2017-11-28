package webApplication.bean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import webApplication.util.DataBaseUtility;

public class Prefecture {

	//変数宣言
	private int prefCode;		//都道府県コード
	private String prefName;	//都道府県名

	public int getPrefCode() {
		return prefCode;
	}
	public void setPrefCode(int prefCode) {
		this.prefCode = prefCode;
	}
	public String getPrefName() {
		return prefName;
	}
	public void setPrefName(String prefName) {
		this.prefName = prefName;
	}

	/**
	 * 都道府県一覧を取得するSQLの実行
	 * @param logger
	 * @return
	 * @throws SQLException
	 * @throws NamingException
	 */
	public ArrayList<Prefecture> allPrefGet(Logger logger) throws SQLException, NamingException {

		logger.info("都道府県一覧取得を開始します。");
		//DB接続
		Connection conn = null;
		Statement stmt =null;
		ResultSet rs = null;
		//接続
		try{
			conn = DataBaseUtility.conectionDB();
			stmt = conn.createStatement();

			//SQL文の作成
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT ");
			sb.append(" pref_CD, ");
			sb.append(" PREF_NAME ");
			sb.append(" FROM t01prefecture ");
			sb.append(" ORDER BY pref_CD; ");
			//SQLをセットし実行する
			String sql = sb.toString();
			logger.debug("都道府県一覧取得SQL：" + sql);
			rs = stmt.executeQuery(sql);
			//一覧を格納しておくArrayList
			ArrayList<Prefecture> prefList = new ArrayList<Prefecture>();

			while(rs.next()) {
				Prefecture pref = new Prefecture();
				//都道府県コードのセット
				pref.setPrefCode(rs.getInt("pref_CD"));
				//都道府県名のセット
				pref.setPrefName(rs.getString("PREF_NAME"));

				prefList.add(pref);
			}
		//引数でリストを返す
		return prefList;

		}finally {
			rs.close();
			stmt.close();
			conn.close();
		}
	}
}
