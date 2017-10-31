package webApplication.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webApplication.bean.Shozoku;

public class ShozokuListServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


//Shozokuの一覧を表示する
	/*
	 * JDBC接続～データ取得
	 */
	//必要変数の用意
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


//		SQL
		StringBuilder sb = new StringBuilder();
			sb.append(" SELECT ");
			sb.append(" shozoku_code, ");
			sb.append(" shozoku_bu, ");
			sb.append(" CASE shozoku_ka ");
			sb.append(" When '（なし）' Then '' ");
			sb.append(" ELSE shozoku_ka END ");
			sb.append(" as shozoku_ka, ");
			sb.append(" CASE shozoku_kakari ");
			sb.append(" When '（なし）' Then '' ");
			sb.append(" ELSE shozoku_kakari END ");
			sb.append(" as shozoku_kakari ");
			sb.append(" FROM shozoku ");
			sb.append(" ORDER BY shozoku_code; ");
			//SQLをセットし実行する
			String sql = sb.toString();
			rs = stmt.executeQuery(sql);

			ArrayList<Shozoku> szkItiran = new ArrayList<Shozoku>();

//			ループ文で取り出し、セットを実行する
			while(rs.next()) {
				//オブジェクトの生成
				Shozoku sh = new Shozoku();

				//所属コードのセット
				int sh_code = rs.getInt("shozoku_code");
				sh.setShozoku_code(sh_code);

				//所属部のセット
				String sh_bu = rs.getString("shozoku_bu");
				sh.setShozoku_bu(sh_bu);

				//所属課のセット
				String sh_ka = rs.getString("shozoku_ka");
				sh.setShozoku_ka(sh_ka);

				//所属係のセット
				String sh_kakari = rs.getString("shozoku_kakari");
				sh.setShozoku_kakari(sh_kakari);

//				sh.printName();

				szkItiran.add(sh);

			}
			// ArrayListの中身をループで取り出して出力
			for(Shozoku shzk : szkItiran) {
				shzk.kakunin();
			}

			req.setAttribute("ShozokuList", szkItiran);

			// フォワード先（JSP or サーブレット）を指定する
			this.getServletContext().getRequestDispatcher("/jsp/ShozokuList.jsp").
			forward(req, resp);


	} catch (SQLException e) {
		e.printStackTrace();
	}finally{
		try {
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	}
}

