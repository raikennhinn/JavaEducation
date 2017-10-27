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

public class EmployeeUpdateServlet extends HttpServlet {

		@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// リクエストパラメータのエンコード
		req.setCharacterEncoding("UTF-8");

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


			//変更された値の取得確認

			Employee inemp = new Employee();
			//社員コード
			inemp.setEmployee_no(Integer.parseInt(req.getParameter("employee_no")));
			//Shozokuクラスのオブジェクトを生成
			Shozoku szk = new Shozoku();
			//所属コード
			szk.setShozoku_code(Integer.parseInt(req.getParameter("shozoku_code")));
			inemp.setShozoku(szk);
			//名前
			inemp.setEmployee_name(req.getParameter("name"));
			//名前カナ
			inemp.setEmployee_namekana(req.getParameter("namekana"));
			//性別
			inemp.setSex(req.getParameter("sex"));
			//年齢
			inemp.setAge(Integer.parseInt(req.getParameter("age")));
			//誕生日
			String jdbcBirthday = req.getParameter("birthday").replace('/', '-');
			inemp.setBirthday(Date.valueOf(jdbcBirthday));

			// Employee(Shozoku入り）を、リクエストにセットしておく
			req.setAttribute("inemp",inemp );
			//所属コードが存在するか確認、存在しない場合はエラーを返す
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
				this.getServletContext().getRequestDispatcher("/jsp/EmployeeUpdate.jsp").
				forward(req, resp);
				return;
			}
			rs.close();
			ps.close();

			//OKなら更新を実行する
			//
			StringBuilder inempUpdate = new StringBuilder();
			//UPDATEを実行するSQL文を記述
			inempUpdate.append(" UPDATE employee ");
			inempUpdate.append(" SET ");
			inempUpdate.append(" employee_no = ?, ");
			inempUpdate.append(" shozoku_code = ?, ");
			inempUpdate.append(" employee_name = ?, ");
			inempUpdate.append(" employee_name_kana = ?, ");
			inempUpdate.append(" sex = ?, ");
			inempUpdate.append(" age = ?, ");
			inempUpdate.append(" birthday = ? ");
			inempUpdate.append(" WHERE employee_no = ?;");
			//文字列をSQLとしてまとめる
			String sql = inempUpdate.toString();
			// PreparedStatementの場合、作成にSQLが必須
			ps = conn.prepareStatement(sql);
			//？に値をセットする、パラメータに値をセット
			ps.setInt(1, inemp.getEmployee_no());
			ps.setInt(2, inemp.getShozoku().getShozoku_code() );
			ps.setString(3, inemp.getEmployee_name());
			ps.setString(4, inemp.getEmployee_namekana());
			ps.setString(5, inemp.getSex());
			ps.setInt(6, inemp.getAge());
			ps.setDate(7, inemp.getBirthday());
			ps.setInt(8, inemp.getEmployee_no());
			//実行と同時にupdateに値を入れる
			update = ps.executeUpdate();

			//updateの数値によって更新できたかどうか判断をおこなう
			if(update == 1) {
				//成功した;
				req.setAttribute("mes", "従業員の更新に成功しました");
				req.setAttribute("flg",true);
				//Insert成功ならばコミットして、更新結果画面へ遷移
				//コミットする
				conn.commit();

			}else {
				//失敗した finallyでロールバックをおこなう？
				req.setAttribute("mes", "従業員の更新に失敗しました");
				req.setAttribute("flg",false);
			}
		} catch (NamingException | SQLException e) {

			e.printStackTrace();

		}finally {
			try {
				conn.rollback();
				ps.close();
				conn.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}

		}

	}

}
