package webApplication.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import webApplication.bean.Employee;

public class ShozokuLeaderSelectServlet extends CommonServlet {

	/**
	 * 所属一覧JSPから所属コードを受け取り、一覧検索【Employeeクラスに存在】を実行するメソッド
	 */
	@Override
	protected void doServlet(HttpServletRequest req, HttpServletResponse resp, HttpSession hpSession, Logger logger)
			throws ServletException, IOException {


		logger.info("選択した所属コードを元に所属している従業員を表示します。");
		//所属一覧JSPのパラメータ（所属コード）を受け取る。
		int shozokuCode = Integer.parseInt(req.getParameter("ShozokuCode"));

		//所属コードを引数として、所属クラスに存在する所属コードの社員検索実施を実行する。
		//実行したものは、employeeクラスのオブジェクトに入れる必要がある。
		try {
			ArrayList<Employee> empList =  Employee.getEmployeeShozokuCode(shozokuCode, logger);

			//empに所属コードの従業員一覧が入っているためこれをセッションに入れて返す
			req.setAttribute("employeeList", empList);

			// 選択された所属コードをそのままリクエストにセット
			req.setAttribute("shozokuCode", shozokuCode);

			// 結果画面jspへフォワード
			this.getServletContext().getRequestDispatcher("/jsp/ShozokuLeaderSelect.jsp").
			forward(req, resp);




		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}

	}

}
