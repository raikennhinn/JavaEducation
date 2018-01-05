package webApplication.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import webApplication.util.MessegeUtility;

public class UserLoginInitServlet extends HttpServlet {

	/**
	 * 直接URLをしていして表示を行うためdoGetを使用
	 * 初期表示時に必要なメッセージの取得を実施
	 * メッセージID
	 * ECOMMON01　未入力
	 * E000101　ユーザIDとパスワードの両方が未入力です。
	 * E000102　入力されたユーザIDもしくはパスワードが正しくありません。←に関してはログイン実行後に取得か
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//セッションの有無の確認
		HttpSession session = req.getSession(false);
		if(session != null && session.getAttribute("login_info") != null) {
			// セッションが存在する場合は、メニュー画面に遷移
			this.getServletContext().getRequestDispatcher("/jsp/Menu.jsp").
			forward(req, resp);
			return;
		}

		// 正常／異常フラグにtrue（正常）を立てておく
		req.setAttribute("flg", true);

		//MessegeUtilityクラスのmessage2メソッドからメッセージを取得し、ログイン画面へと移動する。
		try {
			//メッセージ3種を取得
				HashMap<String, String> mes;
				mes = MessegeUtility.message2(
						"ECOMMON01",
						"E000101",
						"E000102"
						);
				req.setAttribute("mesMap", mes);
				//更新画面へ返す
				this.getServletContext().getRequestDispatcher("/jsp/0001_login.jsp").
				forward(req, resp);
				return;

		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
	}

	 /**
	  *  ログイン失敗時に渡されて移動する先としてdoPostを使用する
	  *  E000102　入力されたユーザIDもしくはパスワードが正しくありませんが前処理から送られてくる
	  */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//ログインエラーメッセージの取得　ログイン失敗時
		//MessegeUtilityクラスのmessage2メソッドからメッセージを取得
		try {
			//メッセージ3種を取得
//				String mes;
//				mes = MessegeUtility.message1(String.valueOf(req.getAttribute("mes")));
//				req.setAttribute("mes", mes);
//				req.setAttribute("flg", req.getAttribute("flg"));

				HashMap<String, String> mes;
				mes = MessegeUtility.message2(
						"ECOMMON01",
						"E000101",
						"E000102"
						);
				req.setAttribute("mesMap", mes);

				//更新画面へ返さず、引数でもらったエラーIDの使用し、エラーメッセージを表示する
				this.getServletContext().getRequestDispatcher("/jsp/0001_login.jsp").
				forward(req, resp);
				return;

		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
	}








}
