package webApplication.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import webApplication.bean.Employee;

public class SortServlet extends CommonServlet {
	/**
	 * ソートを実行するサーブレット
	 */
	@Override
	protected void doServlet(HttpServletRequest req, HttpServletResponse resp, HttpSession hpSession, Logger logger)
			throws ServletException, IOException {

		logger.info("ソート開始");

		final int UPDOWM_DEFAULT = 0;
		final int UPDOWM_ASCENDING = 1;
		final int UPDOWM_DESCENDING = 2;
		//【ソートサーブレット】
		//・セッションよりEmployeeのリストを取得する
		ArrayList<Employee> employeeList = (ArrayList<Employee>) hpSession.getAttribute("employee_list");

		//ソートする項目の値を取得
		String category = req.getParameter("id");

		//UpDownをbooleanにする必要がある。
		int UpDown = Integer.parseInt(req.getParameter("UpDown"));
		//後に再表示時に必要となる項目名
		String selectName = req.getParameter("select");

		//前回動作させた項目と同様だった場合は＋１をして実行する。それ以外は、１にリセットし、昇順から動作
		if(category.equals(hpSession.getAttribute("category"))) {
			UpDown = UpDown +1;
		}else{
			UpDown = UPDOWM_ASCENDING;
		}

		if(UpDown == 3) {
			UpDown = UPDOWM_DEFAULT;
		}

		//・EmployeeのリストをEmployeeのソートメソッドに渡し、ソートを実行する
		if(UpDown != UPDOWM_DEFAULT) {
			Employee.EmployeeSort(employeeList,category,UpDown);
		}


		//拡張For文で実現する
		for(Employee emp : employeeList ) {
			if(emp.getBirthdayAtSlash().equals("1000/01/01")) {
				emp.setBirthday(null);
			}
		}


		//再表示する前に、一度セッションに入れる。
		hpSession.setAttribute("employee_list", employeeList);

		if(employeeList.size() <= 15) {
			req.setAttribute("empList", employeeList);
		}else {
			req.setAttribute("empList", employeeList.subList(0,15));
		}



		//現在ページを1として、セッションの保存
		hpSession.setAttribute("current_page_no", 1);


		//今回動作させた項目名を記録しておく
		hpSession.setAttribute("category", category);

//		req.setAttribute("category",category);
		hpSession.setAttribute("select",selectName);

		switch(UpDown) {
			case UPDOWM_ASCENDING:
				//昇順に変更しました
				logger.info(category+"を基準に昇順に変更");
				hpSession.setAttribute("sort", UPDOWM_ASCENDING);
				hpSession.setAttribute("UpDown", UPDOWM_ASCENDING);
				break;

			case UPDOWM_DESCENDING:
				//降順に変更しました。
				logger.info(category+"を基準に降順に変更");
				hpSession.setAttribute("sort", UPDOWM_DESCENDING);
				hpSession.setAttribute("UpDown", UPDOWM_DESCENDING);
				break;

			case UPDOWM_DEFAULT:
				//デフォルトに戻しました。
				logger.info("初期表示へ戻しました。");
				hpSession.setAttribute("sort", UPDOWM_DEFAULT);
				hpSession.setAttribute("UpDown", UPDOWM_DEFAULT);
				//デフォルトの表示に戻るため、初期表示のサーブレットを呼び出せ　EmployeeList
				this.getServletContext().getRequestDispatcher("/EmployeeList/").
				forward(req, resp);
				return;
			}

		//・ソートした結果で一覧１ページ目を再表示する
		//EmployeeListPager

		this.getServletContext().getRequestDispatcher("/jsp/employeeList.jsp").
		forward(req, resp);

	}

}
