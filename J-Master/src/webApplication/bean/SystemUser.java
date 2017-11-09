package webApplication.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import webApplication.util.DataBaseUtility;

public class SystemUser {
	/*
	 * ・ID
	 * ・PASS
	 * ・氏名
	 * ・権限区分
	 * ・メールアドレス
	 * の変数を作成、getとｓｅｔを作成
	 */
	private String id;
	private String pass;
	private String name;
	private int userauth;
	private String maildaddless;
	private boolean check;

	//セッターとゲッター
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getUserauth() {
		return userauth;
	}
	public void setUserauth(int userauth) {
		this.userauth = userauth;
	}
	public String getMaildaddless() {
		return maildaddless;
	}
	public void setMaildaddless(String maildaddless) {
		this.maildaddless = maildaddless;
	}
	public boolean isCheck() {
		return check;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}


	//loginメソッドを作成
	//ないようとして、SystemUserオブジェクトにユーザ情報やログイン成否（True/False）をセットして、メソッド呼び出し元に返却
	public void  userCheck(String id,String pass) throws SQLException, NamingException {
		//引数としてパスとＩＤを受け取り
		//ＤＢで検索を実行
		//存在していた場合はオブジェクトにセットを行いつつ、判定を含めたオブジェクトを返す
		//DBの接続
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try{
			conn = DataBaseUtility.conectionDB();
			//ＳＱＬにてＩＤとＰＡＳＳを元に検索を実施
			StringBuilder stb = new StringBuilder();
			stb.append(" SELECT id, ");
			stb.append(" pass, ");
			stb.append(" name, ");
			stb.append(" user_auth, ");
			stb.append(" mailaddless ");
			stb.append(" FROM system_user ");
			stb.append(" WHERE id = ? and ");
			stb.append(" pass = ? ");
			String select = stb.toString();
			//ＳＱＬ文の形成
			ps = conn.prepareStatement(select);
			ps.setString(1, id);
			ps.setString(2, pass);
			//ＳＱＬの実行
			rs = ps.executeQuery();

			//何も情報が得られないようであれば、エラーメッセージＩＤを、ヒットするようであれば情報を詰めたオブジェクトを返す。
			if(rs.next() == true) {
				//id
				this.id = rs.getString("id");
				//pass
				this.pass = rs.getString("pass");
				//name
				this.name = rs.getString("name");
				//user_auth
				this.userauth = rs.getInt("user_auth");
				//mailaddless
				this.maildaddless = rs.getString("mailaddless");

				//true
				this.check = true;
			}else {
				//ヒットしなかったのでfalseをオブジェクトに格納して返す。
				this.check = false;
			}
		}finally{
			rs.close();
			ps.close();
			conn.close();
		}
	}


}
