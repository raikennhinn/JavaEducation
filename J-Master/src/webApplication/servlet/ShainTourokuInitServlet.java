package webApplication.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import webApplication.util.MessegeUtility;

/**
 * 従業員登録画面　初期表示
 */
public class ShainTourokuInitServlet extends CommonServlet{

	/**
	 * CommonServletからの継承
	 * doServletの実行、doGetメソッドの呼び出し、実行
	 */
	@Override
	protected void doServlet(HttpServletRequest req,HttpServletResponse resp, HttpSession hpSession,Logger logger) throws ServletException, IOException{

		logger.info("社員登録開始");

		//メッセージ情報の取得
		try {
				HashMap<String, String> mes;
				mes = MessegeUtility.message2(
						"ECOMMON01",
						"ECOMMON02",
						"ECOMMON03",
						"ECOMMON04",
						"ECOMMON06"
						);
				req.setAttribute("mesMap", mes);
				//更新画面へ返す
				logger.debug(mes+"をセットしました。");
				this.getServletContext().getRequestDispatcher("/jsp/shainTouroku.jsp").
				forward(req, resp);
				return;
//			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			for(StackTraceElement ste : e1.getStackTrace()) {
				logger.error(ste.toString());
			}
		} catch (NamingException e1) {
			e1.printStackTrace();
			for(StackTraceElement ste : e1.getStackTrace()) {
				logger.error(ste.toString());
			}
		}

	}
}
