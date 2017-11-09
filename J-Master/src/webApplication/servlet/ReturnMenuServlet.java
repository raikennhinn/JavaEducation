package webApplication.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class ReturnMenuServlet extends CommonServlet {

	@Override
	protected void doServlet(HttpServletRequest req, HttpServletResponse resp, HttpSession hpSession, Logger logger)
			throws ServletException, IOException {

		logger.info("メニュー画面に戻ります");

		// フォワード先（JSP or サーブレット）を指定する
		this.getServletContext().getRequestDispatcher("/jsp/Menu.jsp").
		forward(req, resp);

	}

}
