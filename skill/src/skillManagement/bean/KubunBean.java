package skillManagement.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * 区分マスタのBeanクラス
 */
public class KubunBean {

	/*
	 * ------------------------------------
	 * メンバ変数
	 * ------------------------------------
	 */

	/**
	 * 所属区分
	 */
	private int affiliationKubun;
	/**
	 * 所属区分（名称）
	 */
	private String affiliationKubunName;
	/**
	 * 権限区分
	 */
	private int authKubun;
	/**
	 * 権限区分（名称）
	 */
	private String authKubunName;

	/*
	 * ------------------------------------
	 * コンストラクタ
	 * ------------------------------------
	 */

	/**
	 * デフォルト（引数無し）コンストラクタ
	 */
	public KubunBean(){

	}

	/**
	 * コンストラクタ（生成時に値をすべてセットする）
	 * @param affiliationKubun 所属区分
	 * @param affiliationKubunName 所属区分（名称）
	 */
	public KubunBean(int affiliationKubun, String affiliationKubunName){
		this.affiliationKubun = affiliationKubun;
		this.affiliationKubunName = affiliationKubunName;

	}

	/*
	 * ------------------------------------
	 * ゲッター／セッター
	 * ------------------------------------
	 */

	/**
	 * 所属区分を返す。
	 * @return affiliationKubun
	 */
	public int getAffiliationKubun() {
		return affiliationKubun;
	}

	/**
	 * 所属区分を設定する
	 * @param affiliationKubun
	 */
	public void setAffiliationKubun(int affiliationKubun) {
		this.affiliationKubun = affiliationKubun;
	}

	/**
	 * 所属区分（名称）を返す。
	 * @return affiliationKubunName
	 */
	public String getAffiliationKubunName() {
		return affiliationKubunName;
	}

	/**
	 * 所属区分（名称）を設定する
	 * @param affiliationKubunNname
	 */
	public void setAffiliationKubunName(String affiliationKubunName) {
		this.affiliationKubunName = affiliationKubunName;
	}

	/**
	 * 権限区分を返す。
	 * @return authKubun
	 */
	public int getAuthKubun() {
		return authKubun;
	}

	/**
	 * 権限区分を設定する
	 * @param authKubun
	 */
	public void setAuthKubun(int authKubun) {
		this.authKubun = authKubun;
	}

	/**
	 * 権限区分（名称）を返す。
	 * @return authKubunName
	 */
	public String getAuthKubunName() {
		return authKubunName;
	}

	/**
	 * 権限区分（名称）を設定する
	 * @param authKubunName
	 */
	public void setAuthKubunName(String authKubunName) {
		this.authKubunName = authKubunName;
	}


	/**
	 * 所属区分の値と名称のセットをリストで取得する
	 * @return
	 * @throws SQLException
	 * @throws NamingException
	 */
	public static ArrayList<KubunBean> getAffiliationKubunBeanList() throws SQLException, NamingException {
		Context context;
		Connection db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<KubunBean> list = new ArrayList<KubunBean>();
		try {
			context = new InitialContext();
			//データベースへの接続
			DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/skill_management");
			db = ds.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("SELECT");
			sb.append(" kubun_value ");
			sb.append(" ,kubun_name ");
			sb.append("FROM");
			sb.append(" m_kubun ");
			sb.append("WHERE");
			sb.append(" kubun_category = ?");

			//DBからレコードを抽出
			ps = db.prepareStatement(sb.toString());

			//プレースホルダーに対して、値を設定
			ps.setString(1, "AFFILIATION");

			//SQL実行
			rs = ps.executeQuery();

			//ResultSet取得
			while(rs.next()){
				KubunBean bean = new KubunBean();

				bean.setAffiliationKubun(rs.getInt("kubun_value"));
				bean.setAffiliationKubunName(rs.getString("kubun_name"));

				list.add(bean);

			}
		} finally {
				if(rs != null) { rs.close(); }
				if(ps != null) { ps.close(); }
				if(db != null) { db.close(); }
		}
		return list;
	}

	/**
	 * 権限区分の値をキーとした名称のマップで取得する
	 * @return
	 * @throws SQLException
	 * @throws NamingException
	 */
	public static HashMap<String, String> getAuthKubunMap() throws SQLException, NamingException {
		Context context;
		Connection db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String authKubun = null;
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			context = new InitialContext();
			//データベースへの接続
			DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/skill_management");
			db = ds.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("SELECT");
			sb.append(" kubun_value ");
			sb.append(" ,kubun_name ");
			sb.append("FROM");
			sb.append(" m_kubun ");
			sb.append("WHERE");
			sb.append(" kubun_category = ?");

			//DBからレコードを抽出
			ps = db.prepareStatement(sb.toString());

			//プレースホルダーに対して、値を設定
			ps.setString(1, "AUTHORITY");

			//SQL実行
			rs = ps.executeQuery();

			//ResultSet取得
			while(rs.next()){
				authKubun = rs.getString("kubun_name");
				map.put(rs.getString("kubun_value"), authKubun);
			}
		} finally {
				if(rs != null) { rs.close(); }
				if(ps != null) { ps.close(); }
				if(db != null) { db.close(); }
		}
		return map;
	}

}
