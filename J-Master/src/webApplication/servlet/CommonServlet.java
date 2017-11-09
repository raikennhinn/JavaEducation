package webApplication.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * すべてのサーブレットクラスで継承するサーブレット抽象クラス。
 * ※UserLoginInitServlet、UserLoginServlet、LogoutServletは除く
 * abstract
 */
abstract class CommonServlet extends HttpServlet {

	@Override
	final protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doCommonWork(req, resp);
	}

	@Override
	final protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doCommonWork(req, resp);
	}

	/**
	 * doGetメソッドdoPostメソッドどちらからも呼ばれる、共通のメソッド。以下の処理を行う。
	 * ①-1) ②のセッションチェック処理を呼ぶ
	 * ①-2) ロガーの作成を行う
	 * ①-3) ③doServleｔメソッドを呼ぶ
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doCommonWork(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//セッションチェック
		HttpSession session = req.getSession(false);
		sessionCheck(req, resp);
		//ロガーの作成
		Logger logger = Logger.getLogger(CommonServlet.class);
//		LoggerCreate(logger);
		//doServleｔメソッドを呼ぶ
		doServlet(req,resp,session,logger);
	}


	//	①doGetメソッド、doPostメソッド（finalを付加。オーバーライドを許可しない）
	// doGetメソッドdoPostメソッドどちらが呼ばれても、共通のメソッドを呼び、以下の処理を行う。

	//	①-1) ②のセッションチェック処理を呼ぶ
	//	②セッションチェック処理
	//	セッション保持しているログイン情報（セッションキー：login_info）が存在するかチェックする
	//	存在しない場合、すべてのセッション情報を破棄（session.invalidateメソッドを実行）して、ログイン画面(0001)に遷移する。（共通処理は終了）
	//  ろぐあうとさーぶれっと
	//セッションチェックを行う共通のメソッド
	private void sessionCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);
		if(session == null) {
			this.getServletContext().getRequestDispatcher("/UserLoginInit/").
    		forward(req, resp);

        	return;
		}
        if(session != null && session.getAttribute("login_info") == null) {
        	session.invalidate();

        	this.getServletContext().getRequestDispatcher("/UserLoginInit/").
    		forward(req, resp);

        	return;
        }

	}
	//	①-2) ロガーの作成を行う
	//	org.apache.log4j.Loggerクラスのインスタンスloggerを生成する。log4j.xmlの設定値は別シート「設定ファイル　設定値」参照。
//	public void LoggerCreate(Logger logger) {
//		logger = Logger.getLogger(CommonServlet.class);
//	}

	//	③doServletメソッド
	//	抽象メソッド。HttpServletRequest、HttpServletResponse、HttpSession、Loggerのオブジェクトを引数に持つ。
	//	ServletExceptionとIOExceptionをthrowsする（doGetやdoPostと同じ）
	//	CommonServletクラスを継承したサブクラスは、このメソッドをオーバーライドして、具体的な処理を記述する。
	//抽象メソッド引数を入れる
	abstract protected void doServlet(HttpServletRequest req,HttpServletResponse resp,HttpSession hpSession,Logger logger) throws ServletException, IOException;
	//他のサーブレットで、継承して実施を行う。

}
