package webApplication.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import webApplication.bean.Employee;
import webApplication.util.MessegeUtility;

public class EmployeeDeleteServlet extends CommonServlet {

	/**
	 * 削除処理を行う
	 *
	 */
	@Override
	protected void doServlet(HttpServletRequest req, HttpServletResponse resp, HttpSession hpSession, Logger logger)
			throws ServletException, IOException {

		logger.info("削除処理開始");

		Employee emp = new Employee();
		int delete = 0;

		try {

			delete = emp.deleteSQL(String.valueOf(req.getParameter("empNoUp")));


			if(delete == 1) {
				//成功した;
				req.setAttribute("mes",  MessegeUtility.message1("I100001"));
				req.setAttribute("flg",true);
				logger.info("削除処理成功");
			}else {
				//失敗した
				req.setAttribute("mes",  MessegeUtility.message1("E100001"));
				req.setAttribute("flg",false);
				logger.info("削除処理失敗");
			}


				// 結果画面jspへフォワード
				this.getServletContext().getRequestDispatcher("/EmployeeList/").
				forward(req, resp);



		} catch (SQLException | NamingException e) {
			e.printStackTrace();
			for(StackTraceElement ste : e.getStackTrace()) {
				logger.error(ste.toString());
			}
		}
	}

}
