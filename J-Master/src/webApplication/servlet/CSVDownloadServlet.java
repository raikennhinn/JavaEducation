package webApplication.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import webApplication.bean.Employee;

public class CSVDownloadServlet extends CommonServlet {

	@Override
	protected void doServlet(HttpServletRequest req, HttpServletResponse resp, HttpSession hpSession, Logger logger)
			throws ServletException, IOException {

		logger.info("SCVダウンロードを開始します。");
		//セッションの取得
		ArrayList<Employee> empList = (ArrayList<Employee>) hpSession.getAttribute("employee_list");
		//CSVに記述する前準備
		resp.setContentType("text/csv;charset=UTF8");
		String fileName = "EmployeeList.csv";

		resp.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		PrintWriter writer = resp.getWriter();

		writer.write("社員番号,所属,氏名,性別,年齢,生年月日,都道府県,住所,メールアドレス,備考欄\r\n");

		//一行ずつEmploueeクラスに渡してgetCSVLineを使用し、、一行分のデータの文字列を得て書き込みを実施する
		for(int i=0; i < empList.size();i++) {
			//繰返し処理を行い、一行分のデータで実施
			String LineData = empList.get(i).getCSVLine();
			//CSVにきじゅつする;
			writer.write(LineData);

		}
		writer.close();
	}

}
