package webApplication.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import webApplication.bean.Employee;
import webApplication.util.MessegeUtility;

public class EmployeeSearchSrevlet extends CommonServlet {
	/**
	 * 社員検索の実行を行うサーブレット
	 * 実際のSQL実行は、Employeeクラスで行う
	 */
	@Override
	protected void doServlet(HttpServletRequest req, HttpServletResponse resp, HttpSession hpSession, Logger logger)
			throws ServletException, IOException {

		logger.info("検索を開始します");

		int employeeNo = 0;;
		//検索を行う値（受け取るパラメータ）を変数に入れる
		if(req.getParameter("employee_noSearch").equals("") ) {
		}else {
			employeeNo = Integer.parseInt(req.getParameter("employee_noSearch"));	//従業員No
		}
		int ShozokuCode = Integer.parseInt(req.getParameter("ShozokuSearch"));		//所属コード
		String name = req.getParameter("employeeNameSearch");						//氏名
		int prefCode = Integer.parseInt(req.getParameter("prefSearch"));				//都道府県（コード）
		String address = req.getParameter("addressSearch");						//住所

		//入手したパラメータを引数として渡し、検索を実行し、結果をＥｍｐｌｏｙｅｅクラスのオブジェクトempSerchへ入れる
		Employee empSearch = new Employee();
		try {
			//処理を実行し、結果を入れるArrayListを生成と同時に検索処理を実行
			ArrayList<Employee> empList = empSearch.Search(employeeNo, ShozokuCode, name, prefCode, address, logger);

			//セッションに保存(検索結果の全件分データ)
			hpSession.setAttribute("employee_list", empList);

			if(empList.size() == 0) {
				hpSession.setAttribute("page_no", 0);
				hpSession.setAttribute("current_page_no", 0);
				req.setAttribute("empList", empList);
				req.setAttribute("mes", MessegeUtility.message1("ECOMMON07"));
				req.setAttribute("flg", false);
				hpSession.setAttribute("detaflg", false);
			} else {
				//2employeeの件数を15で割った数をページ数として算出
				double page = (double)empList.size() / 15;
				if(page <= 1) {
					page = 1;
				}
				//3.ページ数をセッションに保存
				hpSession.setAttribute("page_no", (int)Math.ceil(page));
				//現在ページを1として、セッションの保存
				hpSession.setAttribute("current_page_no", 1);
				//employee_listの1件目から15件目のみをリクエストにセット
				if(empList.size() < 15) {
					req.setAttribute("empList", empList);
				}else {
					req.setAttribute("empList",empList.subList(0,15));
				}
				hpSession.setAttribute("detaflg", true);
			}

			// ソート関連のアトリビュートを初期化
			hpSession.setAttribute("sort", 0);
			hpSession.setAttribute("UpDown", "");
			hpSession.setAttribute("select", "");
			hpSession.setAttribute("category", "");

			//選択した値をセットする
			if(employeeNo == 0) {
				String empNo = String.valueOf(employeeNo);
				empNo ="";
				hpSession.setAttribute("employee_noSearch", empNo);			//従業員No
			}else {
				hpSession.setAttribute("employee_noSearch", employeeNo);	//従業員No
			}

			hpSession.setAttribute("ShozokuSearch", ShozokuCode);			//所属コード
			hpSession.setAttribute("employeeNameSearch", name);			//氏名
			hpSession.setAttribute("prefSearch", prefCode);				//都道府県コード
			hpSession.setAttribute("addressSearch", address);				//住所

			//画面へ帰ってくる
			this.getServletContext().getRequestDispatcher("/jsp/employeeList.jsp").
			forward(req, resp);


		} catch (Exception e) {
			throw new ServletException(e);
		}


	}

}
