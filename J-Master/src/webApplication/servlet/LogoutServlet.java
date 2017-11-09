package webApplication.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * CommonServletは継承しない
 */
public class LogoutServlet extends HttpServlet {

	/**
	 * ログアウト
	 * １セッションを保持したログイン情報をログに出力
	 * 2すべてのセッション情報の破棄を実施
	 * 3ログイン画面に遷移をする
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//１セッションを保持したログイン情報をログに出力
		// ロガーインスタンスの生成
        Logger logger = Logger.getLogger(LogoutServlet.class);
        logger.info("ユーザID:"+req.getParameter("id")+" NAME:"+req.getParameter("name")+" がログアウトしました。");


		//2すべてのセッション情報の破棄を実施
        //セクションにログインで使用しているユーザー情報を取得し、入れる。
        HttpSession session = req.getSession(false);
        if(session != null) {
        	session.invalidate();
        }

		//3ログイン画面に遷移をする上記でできるか？
		this.getServletContext().getRequestDispatcher("/UserLoginInit/").
		forward(req, resp);
	}





}
