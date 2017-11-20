package webApplication.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import webApplication.bean.Employee;

public class EmployeeListPagerServlet extends CommonServlet {

	/**
	 * @Override
	 */
	protected void doServlet(HttpServletRequest req, HttpServletResponse resp, HttpSession hpSession, Logger logger)
			throws ServletException, IOException {

		logger.info("従業員一覧：ページ移動");

		//１．セッション保存のemployee_listを取得する
		ArrayList<Employee> employeeList = (ArrayList<Employee>) hpSession.getAttribute("employee_list");


		//２．クリックされたページャーのページ番号を取得する
		//再表示を行う→何も値が無かった場合→nullでくるのか？

		int nowPage = Integer.parseInt(req.getParameter("current_page_no"));


		//３．クリックされたページャーのページ番号-1 * 15を基点に、15件分のemployeeの値をリクエストにセット
		int num = nowPage -1;
		int data = num * 15;
		int endData = data +15;
		if(endData > employeeList.size()) {
			endData = employeeList.size();
		}
		//req.setAttribute("empList",employeeList.subList(0,15));
		req.setAttribute("empList", employeeList.subList(data,endData));

		//４．current_page_noをクリックされたページャーのページ番号に変更する
		hpSession.setAttribute("current_page_no", nowPage);

		//５．従業員一覧JSPを再表示する
		this.getServletContext().getRequestDispatcher("/jsp/employeeList.jsp").
		forward(req, resp);


	}

}
