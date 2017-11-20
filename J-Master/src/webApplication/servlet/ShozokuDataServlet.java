package webApplication.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import webApplication.bean.Shozoku;

public class ShozokuDataServlet extends CommonServlet {

	/**
	 * 所属コードをEmployeeListのリンクから受け取り、
	 * 詳細検索を実行し、内容の取得を実行する
	 */
	@Override
	protected void doServlet(HttpServletRequest req, HttpServletResponse resp, HttpSession hpSession, Logger logger)
			throws ServletException, IOException {

		//logger 全画面から所属コードを引き継ぎ作成
		logger.info(req.getParameter("ShozokuCode")+"の所属コードの詳細表示の開始");
		//所属クラスを使用するためにオブジェクト生成実施
		Shozoku shk = new Shozoku();


		try {
			//Shozokuクラスに存在する（作成する）、所属情報検索に、引数として所属コードを渡して検索を開始する。
			//を、shkにつめる。
			shk = shk.shozokuDeta(Integer.parseInt(req.getParameter("ShozokuCode")));

			//帰ってきた情報を、セッションに詰める
			hpSession.setAttribute("shozokuMore", shk);

			//JSPへ移動を行う
			this.getServletContext().getRequestDispatcher("/jsp/ShozokuDetaDisplay.jsp").
			forward(req, resp);






		} catch (NumberFormatException | SQLException | NamingException e) {
			e.printStackTrace();
		}

	}

}
