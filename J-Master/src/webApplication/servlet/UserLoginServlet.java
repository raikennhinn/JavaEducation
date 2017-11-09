package webApplication.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import webApplication.bean.SystemUser;
import webApplication.util.MessegeUtility;

public class UserLoginServlet extends HttpServlet {

	/**
	 * ログイン実施時の値を受け取り、判定を行い、返す
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//値を受け取る オブジェクトの生成
		SystemUser sysUser = new SystemUser();
		boolean check;
		try {
			//ＩＤとＰＡＳＳで照合を実行
			sysUser.userCheck(req.getParameter("id"), req.getParameter("pass"));
			//checkに実行結果を入れる
			check = sysUser.isCheck();

			//失敗時
			if(check == false) {
				//一致するものがなかったことをメッセージに入れて返す
				req.setAttribute("mes", MessegeUtility.message1("E000102"));
				req.setAttribute("flg", false);
				//初期表示のサーブレットに移す
				this.getServletContext().getRequestDispatcher("/UserLoginInit/").
				forward(req, resp);
				return;
			}


			//成功時
			//セクションにユーザー情報をセット
			HttpSession session = req.getSession(true);
			session.setAttribute("login_info", sysUser);

			//チェックボックスがonであれば、クッキーの作成
			String isSaveCookie = req.getParameter("isSaveCookie");
			if("on".equals(isSaveCookie)) {
				// クッキー作成
				Cookie cookie = new Cookie("user_id",req.getParameter("id"));
				cookie.setMaxAge(365 * 24 * 60 * 60);	//１年
//				cookie.setMaxAge(180);	//
				resp.addCookie(cookie);
			}

			//メニュー画面に移動
			this.getServletContext().getRequestDispatcher("/jsp/Menu.jsp").
			forward(req, resp);



		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}
