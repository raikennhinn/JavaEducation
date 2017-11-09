package webApplication.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import webApplication.util.DataBaseUtility;

public class Shozoku {
	private int shozoku_code;		//所属コード
	private String shozoku_bu;		//所属部
	private String shozoku_ka;		//所属課
	private String shozoku_kakari;	//所属係り
	private String shozoku_name; 	//所属名

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
	//確認用
	public void kakunin() {
		System.out.println(shozoku_code +":"+ shozoku_bu +":"+shozoku_ka+":"+shozoku_kakari+":"+printName());

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
}
