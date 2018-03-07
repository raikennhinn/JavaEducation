package skillManagement.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ログアウト処理のサーブレット
 *
 */
@SuppressWarnings("serial")
public class LogoutServlet extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{

		HttpSession session = request.getSession(true);

		//セッションを終了
	    session.invalidate();

	    //ログイン画面に遷移
	    RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/0001_login.jsp");
		dispatcher.forward(request, response);

	}
}
