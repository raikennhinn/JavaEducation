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
import webApplication.bean.Shozoku;
import webApplication.util.MessegeUtility;

public class EmployeeUpdateServlet extends CommonServlet{

	/**
	 * CommonServletからの継承
	 * doServletの実行、doGetメソッドの呼び出し、実行
	 */
	@Override
	protected void doServlet(HttpServletRequest req,HttpServletResponse resp, HttpSession hpSession,Logger logger) throws ServletException, IOException{

		logger.info("従業員更新中");

		// フィルタで行うため不要になった
//		// リクエストパラメータのエンコード
//		req.setCharacterEncoding("UTF-8");

		try {

			int update = 0;
			//setEmployeeUpdateで更新する予定の値をEmployeeクラスにセットする
			Employee setemp =  new Employee();
			setemp.setEmployeeForUpdate(
					req.getParameter("employee_no"),
					req.getParameter("shozoku_code"),
					req.getParameter("name"),
					req.getParameter("namekana"),
					req.getParameter("sex"),
					req.getParameter("age"),
					req.getParameter("birthday"),
					req.getParameter("prefecture"),
					req.getParameter("address"),
					req.getParameter("mail_address"),
					req.getParameter("note")
					);

			// アトリビュートにsetempをセット（キーは"emp"でよい）
			req.setAttribute("emp", setemp);

			//所属の確認
			// 所属マスタに存在しない所属コードの場合、元の画面に戻る
			boolean check = Shozoku.checkCode(Integer.parseInt(req.getParameter("shozoku_code")));
			if(check == false) {
				req.setAttribute("mes", "入力された所属コード" + MessegeUtility.message1("ECOMMON05"));
				req.setAttribute("emp", setemp );
				req.setAttribute("flg",false);
				this.getServletContext().getRequestDispatcher("/jsp/EmployeeUpdate.jsp").
				forward(req, resp);
				return;
			}

			//SQLを実行し、更新を行う。
			update = setemp.updateSQL();
			logger.debug("SQLを実行、更新"+update);

			//updateの数値によって更新できたかどうか判断をおこなう


			if(update == 1) {
				//成功した;
//				req.setAttribute("mes", "従業員の更新に成功しました");//メッセージをDBから取得
				req.setAttribute("mes",  MessegeUtility.message1("I120001"));
				req.setAttribute("flg",true);

			}else {
				//失敗した
//				req.setAttribute("mes", "従業員の更新に失敗しました");//メッセージをDBから取得
				req.setAttribute("mes",  MessegeUtility.message1("E120001"));
				req.setAttribute("flg",false);
			}

			// 結果画面jspへフォワード
			this.getServletContext().getRequestDispatcher("/jsp/shainHenkouKekka.jsp").
			forward(req, resp);

		} catch (NamingException | SQLException e) {
			e.printStackTrace();
			for(StackTraceElement ste : e.getStackTrace()) {
				logger.error(ste.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			for(StackTraceElement ste : e.getStackTrace()) {
				logger.error(ste.toString());
			}

		}

	}

}
