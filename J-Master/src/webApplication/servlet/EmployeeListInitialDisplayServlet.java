package webApplication.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import webApplication.bean.Employee;
import webApplication.bean.Shozoku;
import webApplication.enumeration.Seibetu;
import webApplication.util.DataBaseUtility;
import webApplication.util.MessegeUtility;



public class EmployeeListInitialDisplayServlet extends CommonServlet{

	/**
	 * CommonServletからの継承
	 * doServletの実行、doGetメソッドの呼び出し、実行
	 */
	@Override
	protected void doServlet(HttpServletRequest req,HttpServletResponse resp, HttpSession hpSession,Logger logger) throws ServletException, IOException{

		logger.info("従業員リスト表示");
//	HttpServlet {
//
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//		// ロガーインスタンスの生成
//        Logger logger = Logger.getLogger(EmployeeListServlet.class);
//        logger.info("開始");
		//セッションの生成。
//		HttpSession session = hpSession;
		int num = 0;
		/*
		 * JDBC接続～データ取得
		 */
		//必要変数の用意
		Connection conn = null;
		Statement stmt =null;
		ResultSet rs = null;

		//接続
		try {
			conn = DataBaseUtility.conectionDB();
			stmt = conn.createStatement();


		//１．DBのemployee、shozokuテーブルをすべて結合させた結果を対応するクラスに格納、ArrayListに全件Addする
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT ");
			sb.append("  e.employee_no, ");
			sb.append("  e.shozoku_code, ");
			sb.append("  e.employee_name, ");
			sb.append("  e.employee_name_kana, ");
			sb.append("  e.sex, ");
			sb.append("  e.age, ");
			sb.append("  e.birthday,");
			sb.append("  CONCAT( ");
			sb.append("    sh.shozoku_bu, ");
			sb.append("    CASE sh.shozoku_ka ");
			sb.append("      When '（なし）' Then '' ");
			sb.append("      Else sh.shozoku_ka ");
			sb.append("    END,");
			sb.append("    CASE sh.shozoku_kakari ");
			sb.append("      When '（なし）' Then '' ");
			sb.append("      Else sh.shozoku_kakari ");
			sb.append("    END ");
			sb.append("  ) as shozoku_name ");
			sb.append("FROM employee e ");
			sb.append("JOIN shozoku sh ");
			sb.append("on e.shozoku_code = sh.shozoku_code ");
			sb.append("ORDER BY employee_no");

			String sql = sb.toString();

	        logger.debug("検索SQL:" + sql);

			rs = stmt.executeQuery(sql);

			ArrayList<Employee> employeeList = new ArrayList<Employee>();

			while(rs.next()) {
				Employee emp = new Employee();					//Employeeクラスのオブジェクトを生成
				Shozoku szk = new Shozoku();					//Shozokuクラスのオブジェクトを生成

				int code = rs.getInt("employee_no");				//社員コード
				emp.setEmployee_no(code);						//Employee にセット


				String name = rs.getString("employee_name");	//氏名
				emp.setEmployee_name(name);

				int shozoku = rs.getInt("shozoku_code");			//ここでemployeeクラスから所属名を取り出す必要
				String shozokuName = rs.getString("shozoku_name");

				// ここで所属コードをszkにセット
				szk.setShozoku_code(shozoku);
				szk.setShozoku_name(shozokuName);
//				emp.setShozoku_code(szk.getShozoku_code());		//所属コードを両クラス同じにする
				emp.setShozoku(szk);


				int sex = rs.getInt("sex");					//性別
				//性別の結び付け(数を文字へ)を実施する
				Seibetu sei = Seibetu.getSeibetu(sex);
				String sexStr = sei.getName();								//性別が格納されている
				emp.setSex(sexStr);


				int age = rs.getInt("age");							//年齢
				emp.setAge(age);

				Date birthday = rs.getDate("birthday");				//生年月日
				emp.setBirthday(birthday);


				employeeList.add(emp);
//				num = num+1;
			}

			num = employeeList.size();

			//1取得した従業員情報を保存
			hpSession.setAttribute("employee_list", employeeList);

			// 従業員情報が0件の場合、ページ数=0、現在ページ数も0とする
			// セッションにもリクエストにも、空の従業員情報ArrayListをそのままセット
			if(num == 0) {
				hpSession.setAttribute("page_no", 0);
				hpSession.setAttribute("current_page_no", 0);
				req.setAttribute("empList", employeeList);
				req.setAttribute("mes", MessegeUtility.message1("ECOMMON07"));
				req.setAttribute("flg", false);
				hpSession.setAttribute("detaflg", false);
			} else {
				//2employeeの件数を15で割った数をページ数として算出
				double page = (double)num / 15;
				//3.ページ数をセッションに保存
				hpSession.setAttribute("page_no", (int)Math.ceil(page));
				//現在ページを1として、セッションの保存
				hpSession.setAttribute("current_page_no", 1);
				//employee_listの1件目から15件目のみをリクエストにセット
				req.setAttribute("empList",employeeList.subList(0,15));
				hpSession.setAttribute("detaflg", true);
			}

			// ソート関連のアトリビュートを初期化
			hpSession.setAttribute("sort", 0);
			hpSession.setAttribute("UpDown", "");
			hpSession.setAttribute("select", "");
			hpSession.setAttribute("category", "");

			// 取得したデータをクライアントに渡す
			// 引数１には、キーとなる文字列を設定
			// 引数２に、渡すデータをセットする
//			req.setAttribute("empList", employeeList);

			// フォワード先（JSP or サーブレット）を指定する
			this.getServletContext().getRequestDispatcher("/jsp/employeeList.jsp").
			forward(req, resp);

		} catch (SQLException | NamingException e) {
			e.printStackTrace();
			for(StackTraceElement ste : e.getStackTrace()) {
				logger.error(ste.toString());
			}
		}finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				for(StackTraceElement ste : e.getStackTrace()) {
					logger.error(ste.toString());
				}
			}

		}


	}
}
