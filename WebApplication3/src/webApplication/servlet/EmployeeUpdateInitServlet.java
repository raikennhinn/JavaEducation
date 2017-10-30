package webApplication.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webApplication.bean.Employee;
import webApplication.util.MessegeUtility;

public class EmployeeUpdateInitServlet extends HttpServlet {


	/**
	 * 単体テスト用に実装
	 * 本来はGETでは呼び出さない。
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	/**
	 * メソッドのオーバーライド
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// リクエストパラメータのエンコード
		req.setCharacterEncoding("UTF-8");

		try {
			//リクエストパラメータから社員番号を取得
			//従業員（Employee）オブジェクトを生成し、格納、返却
			Employee emp = Employee.getEmployeeData(req.getParameter("employee_no"));

			// パターン２　インスタンスが自分自身にDBから取得した値をセットする
			Employee emp2 = new Employee();
			emp2.setEmployeeDataByDB(req.getParameter("employee_no"));


			//メッセージ情報の取得
			HashMap<String, String> mes =
					MessegeUtility.message2(
							"ECOMMON01",
							"ECOMMON02",
							"ECOMMON03",
							"ECOMMON04",
							"ECOMMON06"
							);

			//アトリビュートにセット
			req.setAttribute("emp", emp);
			req.setAttribute("mesMap", mes);


			//更新画面へ返す
			this.getServletContext().getRequestDispatcher("/jsp/EmployeeUpdate.jsp").
			forward(req, resp);

		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		}
	}
}
