package webApplication.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import webApplication.bean.Shozoku;
import webApplication.util.DataBaseUtility;

public class ShozokuListServlet extends CommonServlet{

	/**
	 * CommonServletからの継承
	 * doServletの実行、doGetメソッドの呼び出し、実行
	 */
	@Override
	protected void doServlet(HttpServletRequest req,HttpServletResponse resp, HttpSession hpSession,Logger logger) throws ServletException, IOException{

		logger.info("所属コード一覧");

		String aa = null;
		aa.equals("");

		//Shozokuの一覧を表示する
		/*
		 * JDBC接続～データ取得
		 */
		//必要変数の用意
		Connection conn = null;
		Statement stmt =null;
		ResultSet rs = null;
		//接続
		try {
			conn = DataBaseUtility.conectionDB();
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

			logger.debug("所属リスト取得SQL：" + sql);

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

		} catch (SQLException | NamingException e) {
			for(StackTraceElement ste : e.getStackTrace()) {
				logger.error(ste.toString());
			}
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

