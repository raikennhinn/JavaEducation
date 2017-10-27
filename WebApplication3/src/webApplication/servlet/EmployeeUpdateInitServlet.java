package webApplication.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import webApplication.bean.Employee;
import webApplication.bean.Shozoku;
import webApplication.enumeration.Seibetu;

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

		//リクエストパラメータから社員番号を取得
		//従業員（Employee）オブジェクトを生成し、格納、返却。
		Employee emp = new Employee();
		emp.setEmployee_no(Integer.parseInt(req.getParameter("employee_no")));


		//DBへの接続（contextを使用）
		Connection conn = null;
		PreparedStatement ps =null;
		Context context;
		ResultSet rs = null;
		int update = 0;
		try {
			context = new InitialContext();
			DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/WebApplication");
			conn = ds.getConnection();

			// オートコミットをオフにする
			conn.setAutoCommit(false);

			//取得した社員番号を元に他の情報を取得
			//取得した社員番号を検索するSQL
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT employee_no, ");
			sb.append(" shozoku_code, ");
			sb.append(" employee_name, ");
			sb.append(" employee_name_kana, ");
			sb.append(" sex, ");
			sb.append(" age, ");
			sb.append(" birthday ");
			sb.append(" FROM employee ");
			sb.append(" WHERE employee_no = ? ");
			//文字列へ
			String select = sb.toString();
			//？に取得してきた従業員Noをセットし、実行
			ps = conn.prepareStatement(select);
			ps.setInt(1, emp.getEmployee_no());
			rs = ps.executeQuery();

			//empの中に他項目をつめる
			while(rs.next()) {
				//Shozokuオブジェクトを作成
				Shozoku szk = new Shozoku();
				//所属コードをemployeeオブジェクトを介して所属オブジェクトにつめる
				int shozoku = rs.getInt("shozoku_code");
				szk.setShozoku_code(shozoku);
				emp.setShozoku(szk);	//empのShozokuにセット

				//氏名
				String name = rs.getString("employee_name");	//氏名
				emp.setEmployee_name(name);

				//氏名カナ
				String namekana = rs.getString("employee_name_kana");
				emp.setEmployee_namekana(namekana);

				//性別
				int sex = rs.getInt("sex");
				//性別の結び付け(数を文字へ)を実施する
				Seibetu sei = Seibetu.getSeibetu(sex);
				String sexStr = sei.getName();					//性別が格納されている
				emp.setSex(Integer.toString(sex));

				//年齢
				int age = rs.getInt("age");
				emp.setAge(age);

				//生年月日
				Date birthday = rs.getDate("birthday");
				emp.setBirthday(birthday);

			}

			//メッセージ情報の取得
			//アトリビュートにセット
			req.setAttribute("emp", emp);


			//更新画面へ返す
			this.getServletContext().getRequestDispatcher("/jsp/EmployeeUpdate.jsp").
			forward(req, resp);





		} catch (NamingException | SQLException e) {

			e.printStackTrace();
		}finally {

			try {
				rs.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
