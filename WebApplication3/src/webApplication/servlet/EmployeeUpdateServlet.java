package webApplication.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webApplication.bean.Employee;
import webApplication.bean.Shozoku;

public class EmployeeUpdateServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// リクエストパラメータのエンコード
		req.setCharacterEncoding("UTF-8");

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
					req.getParameter("birthday")
					);



			//所属の確認
			// 所属マスタに存在しない所属コードの場合、元の画面に戻る
			boolean check = Shozoku.checkCode(Integer.parseInt(req.getParameter("shozoku_code")));
			if(check == false) {
				req.setAttribute("mes", "入力された所属コードの所属は存在しません。");
				req.setAttribute("flg",false);
				this.getServletContext().getRequestDispatcher("/jsp/EmployeeUpdate.jsp").
				forward(req, resp);
			}

			//SQLを実行し、更新を行う。
			update = setemp.updateSQL();

			//updateの数値によって更新できたかどうか判断をおこなう
			if(update == 1) {
				//成功した;
				req.setAttribute("mes", "従業員の更新に成功しました");
				req.setAttribute("flg",true);
				//Insert成功ならばコミットして、更新結果画面へ遷移

			}else {
				//失敗した finallyでロールバックをおこなう？
				req.setAttribute("mes", "従業員の更新に失敗しました");
				req.setAttribute("flg",false);
			}

		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();


		}

	}

}
