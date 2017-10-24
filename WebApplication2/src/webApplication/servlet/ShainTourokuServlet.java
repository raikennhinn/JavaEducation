package webApplication.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webApplication.bean.Employee;
import webApplication.bean.Shozoku;

public class ShainTourokuServlet extends HttpServlet {

	/**
	 * ためしにGETメソッドで送信してみた。
	 * 実際には使わない
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 画面で入力した値をgetParameterで受け取る。
		req.getParameter("employee_code");

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// リクエストパラメータのエンコード
		req.setCharacterEncoding("UTF-8");

		// 画面で入力した値をgetParameterで受け取る。
		Employee emp = new Employee();
		emp.setEmployee_no(Integer.parseInt(req.getParameter("employee_code")));
		emp.setEmployee_name(req.getParameter("name"));
		emp.setEmployee_namekana(req.getParameter("namekana"));
		emp.setSex(req.getParameter("sex"));
		emp.setAge(Integer.parseInt(req.getParameter("age")));
//		emp.setBirthday(req.getParameter("birthday"));	// 日付型しか入れられない
		// Clandarクラスのインスタンスを取得、パラメータの日付を指定して、
		// それからDateクラスのオブジェクトを取得して、empにセット（→これでできるか、検証）
//		Calendar cal = Calendar.getInstance();
//		String[] birthday = req.getParameter("birthday").split("/");
//		cal.set(Integer.parseInt(birthday[0]),
//				Integer.parseInt(birthday[1]),
//				Integer.parseInt(birthday[2]));
//		emp.setBirthday(cal.getTime());
		// これだとうまくいかなかった
		// Stringからjava.sql.Dateへの変換が必要
		String jdbcBirthday = req.getParameter("birthday").replace('/', '-');
		emp.setBirthday(Date.valueOf(jdbcBirthday));

		// Employeeが持つShozoku型フィールドに、Shozokuオブジェクトをセット
		// Employeeにshozoku_codeを持たせるため
		Shozoku szk = new Shozoku();					//Shozokuクラスのオブジェクトを生成
//		emp.setShozoku(req.getParameter("shozoku_code"));	// 直接所属コードをempにセットできない
		szk.setShozoku_code(Integer.parseInt(req.getParameter("shozoku_code")));
		emp.setShozoku(szk);

		// Employee(Shozoku入り）を、リクエストにセットして次画面に渡す
		req.setAttribute("emp",emp );

		Connection conn = null;
		String user = "rikai";
		String pass = "rikai";
		String url ="jdbc:mysql://localhost/sampledb040";
//		Statement stmt =null;	// SQLパラメータを使うには、PreparedStatementを用いる
		PreparedStatement ps =null;
//		ResultSet rs = null;	// 更新系ではResultSetは不要

		try {
			Class.forName("org.gjt.mm.mysql.Driver");
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}

		//結果を判定する数値を格納する変数
		int update =0;
		//接続
		try {
			conn = DriverManager.getConnection(url, user, pass);
			//TODO トランザクションが必要
			// オートコミットをオフにする
			conn.setAutoCommit(false);

//			stmt = conn.createStatement();
			int insert = 0;

			// TODO 従業員、所属それぞれの存在チェックを行う
			// TODO エラーなら元の画面に戻る
			// TODO 元の画面では入力内容が消えないようにする

			// 従業員の存在チェック
			// 従業員テーブルにすでに存在する従業員No.が入力されていないこと
			StringBuilder stb = new StringBuilder();
			stb.append("SELECT ");
			stb.append("employee_no ");
			stb.append("FROM employee ");
			stb.append("WHERE employee_no = ? ");	// SQLに条件をつけて照合させたほうがよい
			stb.append("ORDER BY employee_no;");
			String select = stb.toString();
			ResultSet rs = null;
			ps = conn.prepareStatement(select);
			ps.setInt(1, emp.getEmployee_no());
			rs = ps.executeQuery();

			if(rs.next()) {
				// すでにテーブルに存在する従業員No.の場合、元の画面に戻る
				req.setAttribute("mes", "入力された従業員No.のデータはすでに存在します。");
				req.setAttribute("flg",false);
				this.getServletContext().getRequestDispatcher("/jsp/shainTouroku.jsp").
				forward(req, resp);
				return;
			}
			rs.close();
			ps.close();

//			while(rs.next()) {
//				int code = rs.getInt("employee_no");
//				if(code == emp.getEmployee_code()) {
////					元の画面に戻る
//					this.getServletContext().getRequestDispatcher("/jsp/shainTouroku.jsp").
//					forward(req, resp);
////					break;
//				}
//			};

			// 所属の存在チェック
			// 所属マスタに存在する所属コードが入力されていること
			StringBuilder stb2 = new StringBuilder();
			stb2.append("SELECT ");
			stb2.append("shozoku_code ");
			stb2.append("FROM shozoku ");
			stb2.append("WHERE shozoku_code = ? ");
			stb2.append("ORDER BY shozoku_code");
			String codeSelect = stb2.toString();
			ps = conn.prepareStatement(codeSelect);
			ps.setInt(1, szk.getShozoku_code());
			rs = ps.executeQuery();

			if(rs.next() == false) {
				// 所属マスタに存在しない所属コードの場合、元の画面に戻る
				req.setAttribute("mes", "入力された所属コードの所属は存在しません。");
				req.setAttribute("flg",false);
				this.getServletContext().getRequestDispatcher("/jsp/shainTouroku.jsp").
				forward(req, resp);
				return;
			}
			rs.close();
			ps.close();


			StringBuilder sb = new StringBuilder();
			sb.append("INSERT into employee");
			sb.append(" (employee_no, ");
			sb.append(" shozoku_code, ");
			sb.append(" employee_name, ");
			sb.append(" employee_name_kana, ");
			sb.append(" sex, ");
			sb.append(" age, ");
			sb.append(" birthday)");
			sb.append(" values ( ?, ?, ?, ?, ?, ?, ? );");

			String sql = sb.toString();
//			rs = stmt.executeQuery(sql);
//			insert = stmt.executeUpdate(sql);

			// PreparedStatementの場合、作成にSQLが必須
			ps = conn.prepareStatement(sql);
			// パラメータに値をセット
			// TODO リクエストパラメータは事前に取得してクラスに格納、そこからセットするかたちに修正
			ps.setInt(1, emp.getEmployee_no());
//			Shozoku szk2 = emp.getShozoku();
			ps.setInt(2, emp.getShozoku().getShozoku_code() );
			ps.setString(3, emp.getEmployee_name());
			ps.setString(4, emp.getEmployee_namekana());
			ps.setString(5, emp.getSex());
			ps.setInt(6, emp.getAge());

//			String calStr = req.getParameter("birthday");
//			int year = Integer.parseInt(calStr.substring(0, 4));
//			int month = Integer.parseInt(calStr.substring(5, 7));
//			int day = Integer.parseInt(calStr.substring(8, 10));
//			Calendar cal = Calendar.getInstance();
//			cal.set(year, month, day);
//			ps.setDate(6, new Date(year, month, day), cal);
			ps.setDate(7, emp.getBirthday());

			update = ps.executeUpdate();

			// TODO 例外以外の成否判定が必要
			// TODO 成功メッセージか、失敗メッセージかをrequestにセットする
			// TODO 成功失敗フラグ（boolean）もrequestにセットする

			if(update == 1) {
				//成功した;
				req.setAttribute("mes", "従業員の登録に成功しました");
				req.setAttribute("flg",true);
				//Insert成功ならばコミットして、更新結果画面へ遷移
				//コミットする
				conn.commit();

			}else {
				//失敗した finallyでロールバックをおこなう？
				req.setAttribute("mes", "従業員の登録に失敗しました");
				req.setAttribute("flg",false);
//				conn.rollback();
			}

			// TODO 更新結果画面jspへの遷移
			this.getServletContext().getRequestDispatcher("/jsp/shainTourokuKekka.jsp").
			forward(req, resp);

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				// ロールバックしておく
				// コミットがされていればロールバックされない
				// エラーが起きてコミットされていなければ、ロールバックされる
				conn.rollback();

				ps.close();
				conn.close();

//				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}


		}

	}

}
