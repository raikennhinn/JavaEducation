package webApplication.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataBaseUtility {

	/**
	 * DBへの接続を実行するメソッド
	 * @throws SQLException
	 * @throws NamingException
	 */
	public static Connection conectionDB() throws SQLException, NamingException {
		Connection conn = null;
		Context context;

		context = new InitialContext();
		DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/WebApplication");
		conn = ds.getConnection();

		return conn;
	}




}
