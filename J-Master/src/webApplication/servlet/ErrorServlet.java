package webApplication.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class ErrorServlet extends CommonServlet {

	/**
	 * エラーサーブレット
	 * @Override
	 */
	protected void doServlet(HttpServletRequest req, HttpServletResponse resp, HttpSession hpSession, Logger logger)
			throws ServletException, IOException {

		logger.info("エラーサーブレットが起動");

//		①doGetメソッド
//		①-1) リクエストより例外の情報を取得して、ログに出力する。
		StringBuffer buf = new StringBuffer();
		buf.append("例外クラス:");
		buf.append(req.getAttribute("javax.servlet.error.exception_type"));
		buf.append(" 例外メッセージ:");
		buf.append(req.getAttribute("javax.servlet.error.message"));
		buf.append(" 例外発生箇所（ファイル／クラス）:");
		buf.append(req.getAttribute("javax.servlet.error.request_url"));
		buf.append(" 例外発生サーブレット名:");
		buf.append(req.getAttribute("javax.servlet.error.servlet_name"));
		buf.append(" HTTPステータスコード:");
		buf.append(req.getAttribute("javax.servlet.error.status_code"));

		logger.error(buf.toString());

//		①-2) すべてのセッション情報を破棄（session.invalidateメソッドを実行）する
        //セクションにログインで使用しているユーザー情報を取得し、入れる。
        HttpSession session = req.getSession(false);
        if(session != null) {
        	session.invalidate();
        }

//		①-3) エラー画面に遷移する。
        // 理由は不明だが、Forwardではなくリダイレクトを使う？
        resp.sendRedirect(req.getContextPath() + "/jsp/9002_Error.jsp");
	}

}
