package webApplication.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import webApplication.bean.Employee;
import webApplication.util.MessegeUtility;

/**
 * 従業員更新　初期表示
 *
 */
public class EmployeeUpdateInitServlet extends CommonServlet{

	/**
	 * CommonServletからの継承
	 * doServletの実行、doGetメソッドの呼び出し、実行
	 */
	@Override
	protected void doServlet(HttpServletRequest req,HttpServletResponse resp, HttpSession hpSession,Logger logger) throws ServletException, IOException{


		logger.info("従業員更新開始");

		// フィルタで行うため不要になった
//		// リクエストパラメータのエンコード
//		req.setCharacterEncoding("UTF-8");

		try {
			//リクエストパラメータから社員番号を取得
			//従業員（Employee）オブジェクトを生成し、格納、返却
			Employee emp = Employee.getEmployeeData(req.getParameter("empNoUp"));

			// パターン２　インスタンスが自分自身にDBから取得した値をセットする
			Employee emp2 = new Employee();
			emp2.setEmployeeDataByDB(req.getParameter("empNoUp"));


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
			req.setAttribute("flg",true);
			req.setAttribute("emp", emp);
			req.setAttribute("mesMap", mes);


			//更新画面へ返す
			this.getServletContext().getRequestDispatcher("/jsp/EmployeeUpdate.jsp").
			forward(req, resp);

		} catch (NamingException | SQLException e) {
			e.printStackTrace();
			for(StackTraceElement ste : e.getStackTrace()) {
				logger.error(ste.toString());
			}
		}
	}
}
