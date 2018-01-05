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

public class ShainTourokuServlet extends CommonServlet{

	/**
	 * CommonServletからの継承
	 * doServletの実行、doGetメソッドの呼び出し、実行
	 */
	@Override
	protected void doServlet(HttpServletRequest req,HttpServletResponse resp, HttpSession hpSession,Logger logger) throws ServletException, IOException{

		logger.info("社員登録実行");

		// フィルタで行うため不要になった
//		// リクエストパラメータのエンコード
//		req.setCharacterEncoding("UTF-8");

		//トークンの照合
//		String tokenReq = req.getParameter("token");
//		String tokenSes = (String)hpSession.getAttribute("token");
//		if(tokenReq == null || tokenSes == null || !tokenReq.equals(tokenSes)) {
//			req.setAttribute("mes", "画面遷移が不正です。");
//			req.setAttribute("flg",false);
//			this.getServletContext().getRequestDispatcher("/jsp/shainTouroku.jsp").forward(req, resp);
//			return;
//		}


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
//
		// アトリビュートにsetempをセット（キーは"emp"でよい）
		req.setAttribute("emp", setemp);
		logger.debug(setemp+"をリクエストにセットしました");

		//接続
		try {

			//登録したい従業員Ｎｏが存在するかどうかチェック
			//存在していた場合は登録は行えない
			boolean emp_nocheck = Employee.employeeNoCheck(setemp.getEmployee_no());
			if(emp_nocheck == true) {
				// すでにテーブルに存在する従業員No.の場合、元の画面に戻る
				req.setAttribute("mes", "入力された従業員No.のデータはすでに存在します。");
				logger.info("従業員Noのデータ重複。実行不可");
				req.setAttribute("flg",false);
				this.getServletContext().getRequestDispatcher("/jsp/shainTouroku.jsp").forward(req, resp);
				return;
			}

			//登録したい所属コードが存在するかどうかチェック
			//所属コードのあるかどうかの確認ない場合はｊ登録できる
			boolean check = Shozoku.checkCode(setemp.getShozoku().getShozoku_code());
			//存在しない場合
			if(check == false) {
				// 所属マスタに存在しない所属コードの場合、元の画面に戻る
				req.setAttribute("mes", "入力された所属コードの所属は存在しません。");
				req.setAttribute("mes", "入力された所属コード" + MessegeUtility.message1("ECOMMON05"));
				logger.info("所属コードが存在しない。実行不可");
				req.setAttribute("flg",false);
				this.getServletContext().getRequestDispatcher("/jsp/shainTouroku.jsp").
				forward(req, resp);
				return;
			}

			//登録ＳＱＬの実行、結果画面への遷移

			int insert = 0;
			insert = setemp.insertSQL();
			if(insert == 1) {
				//成功した;
//				req.setAttribute("mes", "従業員の登録に成功しました");
				req.setAttribute("mes",  MessegeUtility.message1("I110001"));
				req.setAttribute("flg",true);
			}else {
				//失敗した
//				req.setAttribute("mes", "従業員の登録に失敗しました");
				req.setAttribute("mes",  MessegeUtility.message1("E110001"));
				req.setAttribute("flg",false);
			}


			this.getServletContext().getRequestDispatcher("/jsp/shainHenkouKekka.jsp").
			forward(req, resp);

		} catch (SQLException e) {
			e.printStackTrace();
			for(StackTraceElement ste : e.getStackTrace()) {
				logger.error(ste.toString());
			}
		} catch (NamingException e) {
			e.printStackTrace();
			for(StackTraceElement ste : e.getStackTrace()) {
				logger.error(ste.toString());
			}
		}

	}
}
