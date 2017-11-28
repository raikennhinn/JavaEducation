package webApplication.servlet;

import java.io.IOException;
import java.util.Enumeration;

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

		//現在保持しているセッションキーをログに残す
		logger.info("現段階の所持しているセッション");
		Enumeration e = hpSession.getAttributeNames();
		while(e.hasMoreElements()) {
			String key = (String)e.nextElement();
			logger.info(key);
		}
		//該当のセッションの削除
		hpSession = req.getSession(false);
        if(hpSession != null) {
        	hpSession.removeAttribute("select");
        	hpSession.removeAttribute("employee_list");
        	hpSession.removeAttribute("current_page_no");
        	hpSession.removeAttribute("page_no");
        	hpSession.removeAttribute("detaflg");
        	hpSession.removeAttribute("sort");
        	hpSession.removeAttribute("UpDown");
        	hpSession.removeAttribute("category");
        	hpSession.removeAttribute("shozokuList");
        	hpSession.removeAttribute("prefList");
        	hpSession.removeAttribute("employee_noSearch");
        	hpSession.removeAttribute("ShozokuSearch");
        	hpSession.removeAttribute("employeeNameSearch");
        	hpSession.removeAttribute("prefSearch");
        	hpSession.removeAttribute("addressSearch");
        }
        //ログに現段階でどのセッションが残っているのかを出す。
        logger.info("現段階の所持しているセッション");
        e = hpSession.getAttributeNames();
		while(e.hasMoreElements()) {
			String key = (String)e.nextElement();
			logger.info(key);
		}


		// フォワード先（JSP or サーブレット）を指定する
		this.getServletContext().getRequestDispatcher("/jsp/Menu.jsp").
		forward(req, resp);

	}

}
