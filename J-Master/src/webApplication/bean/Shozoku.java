package webApplication.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import webApplication.util.DataBaseUtility;
/**
 * 所属を操作するクラス
 * @author i1621
 *
 */
public class Shozoku {
	private int shozoku_code;		//所属コード
	private String shozoku_bu;		//所属部
	private String shozoku_ka;		//所属課
	private String shozoku_kakari;	//所属係り
	private String shozoku_name; 	//所属名（所属部＋所属課+所属係）
	private String shozoku_leader;  //所属長

	//所属コードのゲッターとセッター
	public int getShozoku_code() {
		return shozoku_code;
	}
	public void setShozoku_code(int shozoku_code) {
		this.shozoku_code = shozoku_code;
	}

	public String getShozoku_bu() {
		return shozoku_bu;
	}
	public void setShozoku_bu(String shozoku_bu) {
		this.shozoku_bu = shozoku_bu;
	}
	public String getShozoku_ka() {
		return shozoku_ka;
	}
	public void setShozoku_ka(String shozoku_ka) {
		this.shozoku_ka = shozoku_ka;
	}
	public String getShozoku_kakari() {
		return shozoku_kakari;
	}
	public void setShozoku_kakari(String shozoku_kakari) {
		this.shozoku_kakari = shozoku_kakari;
	}
	//所属名のゲッターとセッター
	public String getShozoku_name() {
		return shozoku_name;
	}
	public void setShozoku_name(String shozoku_name) {
		this.shozoku_name = shozoku_name;
	}

	public String printName() {
		//所属名を作成する
		return shozoku_bu + " " + shozoku_ka + " " + shozoku_kakari;
	}


	//pritShozoku()の作成
	public String pritShozoku(){
		return "所属コード：" + shozoku_code + " 所属名；" + shozoku_name;
	}


	public String getShozoku_leader() {
		return shozoku_leader;
	}
	public void setShozoku_leader(String shozoku_leader) {
		this.shozoku_leader = shozoku_leader;
	}
	/**
	 * 入力された所属値が存在するかどうかのチェックを行う
	 * 存在しない場合はfalse,正しい場合はtrueを返す
	 * @param shozoku_code
	 * @return
	 * @throws NamingException
	 * @throws SQLException
	 */
	public static boolean  checkCode(int shozoku_code) throws SQLException, NamingException{
		//DB接続
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try{
			conn = DataBaseUtility.conectionDB();

			//SQL
			StringBuilder stb2 = new StringBuilder();
			stb2.append("SELECT ");
			stb2.append("shozoku_code ");
			stb2.append("FROM shozoku ");
			stb2.append("WHERE shozoku_code = ? ");
			stb2.append("ORDER BY shozoku_code");
			String codeSelect = stb2.toString();
			ps = conn.prepareStatement(codeSelect);
			ps.setInt(1, shozoku_code);
			rs = ps.executeQuery();

			if(rs.next() == false) {

				return false;
			}

			return true;

		}finally {
			rs.close();
			ps.close();
			conn.close();
		}
	}
	/**
	 * 引数で受け取った所属コードの所属情報を表示する
	 * @param ShozokuCode
	 * @return
	 * @throws SQLException
	 * @throws NamingException
	 */
	public Shozoku shozokuDeta(int ShozokuCode) throws SQLException, NamingException {
		//DB接続
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		//リターンで返すための所属情報を詰める所属オブジェクトの生成
		Shozoku shozoku = new Shozoku();
		try{
			conn = DataBaseUtility.conectionDB();

			//SQL
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT SHOZOKU_CODE, ");
			sb.append(" SHOZOKU_BU, ");
			sb.append(" SHOZOKU_KA, ");
			sb.append(" SHOZOKU_KAKARI ");
			sb.append(" FROM shozoku ");
			sb.append(" WHERE SHOZOKU_CODE = ?");
			//?に引数として受け取ったコードを入れる
			String code = sb.toString();
			ps = conn.prepareStatement(code);
			ps.setInt(1, ShozokuCode);
			//SQLの実行
			rs = ps.executeQuery();

			//SQLで取得してきた値を各項目に入れる
			while(rs.next()) {
				//引数の所属コードをShozokuクラスの所属コードへセット
				shozoku.setShozoku_code(ShozokuCode);
				//所属部の取得とセット
				shozoku.setShozoku_bu(rs.getString("SHOZOKU_BU"));
				//所属課の取得とセット
				shozoku.setShozoku_ka(rs.getString("SHOZOKU_KA"));
				//所属係りの取得とセット
				shozoku.setShozoku_kakari(rs.getString("SHOZOKU_KAKARI"));
			}
			//所属コードを元に検索を行い、得た情報が入っているshozokuを返す
			return shozoku;

		}finally {
			rs.close();
			ps.close();
			conn.close();
		}
	}

	/**
	 * 所属長の登録を実行できるSQLを作成し、実行する
	 * アップデートの際に必要な、所属コードと従業員Noを引数として受け取る（loggerもともに）
	 * @throws SQLException
	 * @throws NamingException
	 */
	public int ShozokuLeaderUpdate(int empNo,int shozokuCode,Logger logger) throws SQLException, NamingException {

		//DBへの接続実施
		Connection conn = null;
		PreparedStatement ps = null;

		//必要な変数（実行結果を返す）
		int update = 0;

		try {
			conn = DataBaseUtility.conectionDB();
			// オートコミットをオフにする
			conn.setAutoCommit(false);
			//SQL文の作成
			StringBuilder LeaderpUpdate = new StringBuilder();
			LeaderpUpdate.append(" UPDATE shozoku ");
			LeaderpUpdate.append(" SET ");
			LeaderpUpdate.append(" SHOZOKU_LEADER = ?, ");
			LeaderpUpdate.append(" update_datetime = NOW() ");
			LeaderpUpdate.append(" WHERE SHOZOKU_CODE = ?; ");
			//文字列をSQLとしてまとめる
			String sql = LeaderpUpdate.toString();
			// PreparedStatementの場合、作成にSQLが必須
			ps = conn.prepareStatement(sql);
			//SQLの？に値を入れる
			ps.setInt(1, empNo );
			ps.setInt(2, shozokuCode );
			//実行内容をログに残す
			logger.debug("UPDEATE開始"+ sql);
			//実行と同時にupdateに値を入れる
			update = ps.executeUpdate();

			//updateが成功していればコミット失敗した場合はロールバック
			if(update == 1) {
				//コミットする
				conn.commit();
			}

			//最終的に実行結果の値（1行ずつ更新をおこなうため１）を返す
			return update;

		}finally {
			conn.rollback();
			ps.close();
			conn.close();
		}
	}

	/**
	 * 所属情報一覧
	 * @param logger
	 * @return
	 * @throws SQLException
	 * @throws NamingException
	 */
	public ArrayList<Shozoku> allList(Logger logger) throws SQLException, NamingException {

		//必要変数の用意
		Connection conn = null;
		Statement stmt =null;
		ResultSet rs = null;
		//接続
		try{
			conn = DataBaseUtility.conectionDB();
			stmt = conn.createStatement();


	//		SQL
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT ");
			sb.append(" s.SHOZOKU_CODE, ");
			sb.append(" s.SHOZOKU_BU, ");
			sb.append(" CASE s.SHOZOKU_KA ");
			sb.append(" When '（なし）' Then '' ");
			sb.append(" ELSE s.SHOZOKU_KA END ");
			sb.append(" as SHOZOKU_KA, ");
			sb.append(" CASE s.SHOZOKU_KAKARI ");
			sb.append(" When '（なし）' Then '' ");
			sb.append(" ELSE s.SHOZOKU_KAKARI END ");
			sb.append(" as SHOZOKU_KAKARI, ");
			sb.append(" CONCAT(s.SHOZOKU_LEADER, ':', e.EMPLOYEE_NAME) as SHOZOKU_LEADER ");
			sb.append(" FROM shozoku s ");
			sb.append(" LEFT OUTER JOIN employee e ");
			sb.append(" ON s.SHOZOKU_LEADER = e.EMPLOYEE_NO ");
			sb.append(" ORDER BY s.SHOZOKU_CODE; ");
			//SQLをセットし実行する
			String sql = sb.toString();

			logger.debug("所属リスト取得SQL：" + sql);

			rs = stmt.executeQuery(sql);

			ArrayList<Shozoku> szkItiran = new ArrayList<Shozoku>();

//				ループ文で取り出し、セットを実行する
			while(rs.next()) {
				//オブジェクトの生成
				Shozoku sh = new Shozoku();

				//所属コードのセット
				int sh_code = rs.getInt("SHOZOKU_CODE");
				sh.setShozoku_code(sh_code);

				//所属部のセット
				String sh_bu = rs.getString("SHOZOKU_BU");
				sh.setShozoku_bu(sh_bu);

				//所属課のセット
				String sh_ka = rs.getString("SHOZOKU_KA");
				sh.setShozoku_ka(sh_ka);

				//所属係のセット
				String sh_kakari = rs.getString("SHOZOKU_KAKARI");
				sh.setShozoku_kakari(sh_kakari);

				//所属リーダーのセット
				String sh_leader = rs.getString("SHOZOKU_LEADER");
				sh.setShozoku_leader(sh_leader);

//					sh.printName();

				szkItiran.add(sh);
			}
			return szkItiran;

		}finally {
			rs.close();
			stmt.close();
			conn.close();
		}
	}
}
