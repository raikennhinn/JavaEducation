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
			sb.append(" SHOZOKU_CODE, ");
			sb.append(" SHOZOKU_BU, ");
			sb.append(" CASE SHOZOKU_KA ");
			sb.append(" When '（なし）' Then '' ");
			sb.append(" ELSE SHOZOKU_KA END ");
			sb.append(" as SHOZOKU_KA, ");
			sb.append(" CASE SHOZOKU_KAKARI ");
			sb.append(" When '（なし）' Then '' ");
			sb.append(" ELSE SHOZOKU_KAKARI END ");
			sb.append(" as SHOZOKU_KAKARI, ");
			sb.append(" SHOZOKU_LEADER ");
			sb.append(" FROM shozoku ");
			sb.append(" ORDER BY SHOZOKU_CODE; ");
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

