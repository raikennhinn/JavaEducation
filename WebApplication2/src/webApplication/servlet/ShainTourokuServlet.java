package webApplication.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShainTourokuServlet extends HttpServlet {


	/**
	 * ためしにGETメソッドで送信してみた。
	 * 実際には使わない
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 画面で入力した値をgetParameterで受け取る。
		req.getParameter("employee_code");

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 画面で入力した値をgetParameterで受け取る。

		Connection conn = null;
		String user = "rikai";
		String pass = "rikai";
		String url ="jdbc:mysql://localhost/sampledb040";
		Statement stmt =null;
		ResultSet rs = null;

		try {
			Class.forName("org.gjt.mm.mysql.Driver");
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}

		//接続
		try {
			conn = DriverManager.getConnection(url, user, pass);
			stmt = conn.createStatement();
			int insert = 0;

			req.getParameter("employee_code");
			req.getParameter("shozoku_code");
			req.getParameter("name");
			req.getParameter("sex");
			req.getParameter("age");
			req.getParameter("birthday");

			StringBuilder sb = new StringBuilder();
			sb.append("INSERT into employee");
			sb.append(" (employee_no, ");
			sb.append(" shozoku_code, ");
			sb.append(" employee_name, ");
			sb.append(" sex, ");
			sb.append(" age, ");
			sb.append(" birthday)");
			sb.append(" values ( '" + req.getParameter("employee_code") + "',");
			sb.append(" '" + req.getParameter("shozoku_code") + "', ");
			sb.append(" '" + req.getParameter("name") + "', ");
			sb.append(" '" + req.getParameter("sex") + "',");
			sb.append(" '" + req.getParameter("age") + "',");
			sb.append(" '" + req.getParameter("birthday") + "' ");
			sb.append("  );");

			//　トランザクションが必要

			String sql = sb.toString();
//			rs = stmt.executeQuery(sql);
			insert = stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}


		}

	}

}
