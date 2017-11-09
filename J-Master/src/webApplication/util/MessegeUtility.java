package webApplication.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.naming.NamingException;

public class MessegeUtility {


	//DBからキー、エラーメッセージを取得
//	 * ①単独メッセージ取得メソッド
//		①-1) メッセージID一つを引数に、そのIDに対応するメッセージ本文を返却する

	public static String message1(String id) throws SQLException, NamingException {
		// ０．DB接続してconnectionを返却するメソッドを別途つくる
//		DataBaseUtility dbu = new DataBaseUtility();
		//必要変数
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		//DBへの接続実施
		conn = DataBaseUtility.conectionDB();

		// １．StringのメッセージIDを引数に受け取る
		// ２．DB検索。メッセージIDを条件に、messageテーブルからmessegeを取得
		//SQLの実施
		StringBuilder mssb = new StringBuilder();
		//取得したIDからmessegeを検索するSQL
		mssb.append(" SELECT message ");
		mssb.append(" FROM message ");
		mssb.append(" WHERE message_id = ? ");

		//文字列へ
		String sql = mssb.toString();
		//？に取得してきたIDをセットし、実行
		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		rs = ps.executeQuery();

		String mes = null;
		// ３．取得したmessageをStringでreturnして終了
		while(rs.next()){
			mes = rs.getString("message");

		}

		return  mes;

	}

	/**
	 * 単発のメッセージID、メッセージ本文の組み合わせMapを返却する
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws NamingException
	 */
	public HashMap<String, String> getSingleMessageMap(String id) throws  SQLException, NamingException {
		String message = message1(id);
		HashMap<String, String> mesMap = new HashMap<String, String>();
		mesMap.put(id, message);
		return mesMap;
	}


//		②複数メッセージMap取得メソッド
//		②-1) 複数メッセージIDを引数（可変長引数）に、それらIDをキー、メッセージ本文を値としたメッセージ情報Mapを返却する
	public static HashMap<String, String> message2(String... id) throws SQLException, NamingException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		//DBへの接続実施
		conn = DataBaseUtility.conectionDB();

		//MAPの定義
		HashMap<String, String> mes = new HashMap<String, String>();

		//SQLでIDとメッセージの取得
		//SQLの実施
		StringBuilder mssb = new StringBuilder();
		//取得したIDからmessegeを検索するSQL
		mssb.append(" SELECT message_id, ");
		mssb.append(" message ");
		mssb.append(" FROM message ");
		// Where句で使用するmessage_idを、引数で入ってきた数だけ使用するようにしたい
		// 複数の値をWhereの条件に指定する書き方は？
		//WHERE message_id = ? || message_id = ? || message_id = ?
		mssb.append(" WHERE message_id IN( ");
		//全件取得
		//文字列へ
		for(int i =0; i < id.length ; i++) {
			if(i == id.length -1) {
				mssb.append("?");
			}else {
				mssb.append("?,");
			}
		}
		mssb.append(")");

		String sql = mssb.toString();
		ps = conn.prepareStatement(sql);
		for(int i =1; i <= id.length ; i++) {
			ps.setString(i, id[i-1]);
		}
		rs = ps.executeQuery();




		int i = 0;
		while(rs.next()) {
			//idが同じもののみ取得
			// ここで見つかればrs.first();　i++;(iが最後ならbreak;）
			// ここで見つからなければなにもしない
			// rsが最後まで回っても見つからなければ、やはりrs.first()とi++;(iが最後ならbreak;）

			for(i=0; i< id.length ; i++) {
				if(rs.getString("message_id").equals(id[i])) {
					mes.put(id[i], rs.getString("message"));
					break;
				}
			}
		}

		return mes;

	}



//		③全件メッセージMap取得メソッド
//		③-1) IDをキー、メッセージ本文を値としたデータベース全件のメッセージ情報のMapを返却する
	public static HashMap<String, String> message3() throws SQLException, NamingException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		//DBへの接続実施
		conn = DataBaseUtility.conectionDB();

		//MAPの定義
		HashMap<String, String> mesall = new HashMap<String, String>();

		//SQLでIDとメッセージの取得
		//SQLの実施
		StringBuilder mssb = new StringBuilder();
		//取得したIDからmessegeを検索するSQL
		mssb.append(" SELECT message_id, ");
		mssb.append(" message ");
		mssb.append(" FROM message ");
		//全件取得
		//文字列へ
		String sql = mssb.toString();
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();

		while(rs.next()) {
			mesall.put(rs.getString("message_id"), rs.getString("message"));
		}


		return mesall;

	}

}
