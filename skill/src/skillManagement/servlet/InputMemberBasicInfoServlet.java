package skillManagement.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import skillManagement.bean.KubunBean;
import skillManagement.bean.MemberBasicBean;
import skillManagement.enumeration.MemberBasicInfoErrorFlag;
import skillManagement.util.FileUtility;

public class InputMemberBasicInfoServlet extends HttpServlet{
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{

		//メンバー基本情報作成画面で入力した値を取得
		request.setCharacterEncoding("UTF-8");
		String employeeNumber = request.getParameter("emp_num");
		String password = request.getParameter("password");
		String belong = request.getParameter("belong");
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		String sex = request.getParameter("sex");
		String finalEducation = request.getParameter("final_education");
		String entryYear = request.getParameter("entry_year");
		String entryMonth = request.getParameter("entry_month");

		// セッションよりログインユーザ（更新者）情報を取得
		HttpSession session = request.getSession(true);
		MemberBasicBean bean = (MemberBasicBean)session.getAttribute("login_info");
		String updateEmployeeNumber = bean.getEmployeeNumber();
		String updateName = bean.getName();

		/*
		 * バリデーションチェック
		 */
		MemberBasicInfoErrorFlag status =
				checkInput(employeeNumber,password,belong,name,age,sex,finalEducation,entryYear,entryMonth);
		String errorMessage = null;

		// 入力エラー時、エラーメッセージを取得して元の画面に戻る
		if(status != MemberBasicInfoErrorFlag.NoError){

			request.setAttribute("error", status);

			Properties properties = FileUtility.createProperties();
			if(status == MemberBasicInfoErrorFlag.NoPW){
				//メッセージ："パスワードが未入力です。";
				errorMessage = properties.getProperty("ECOMMON02");
//			}else if(status == MemberBasicInfoErrorFlag.NoPW1){
//				//メッセージ："パスワード（再度）が未入力です。";
//				errorMessage = properties.getProperty("E100201");
//			}else if(status == MemberBasicInfoErrorFlag.NoPW2){
//				//メッセージ："パスワードとパスワード（再度）の入力内容が一致しません。パスワードの再入力をお願いします。";
//				errorMessage = properties.getProperty("E100202");
			}else if(status == MemberBasicInfoErrorFlag.NoAff){
				//メッセージ："所属が未選択です。";
				errorMessage = properties.getProperty("ECOMMON03");
			}else if(status == MemberBasicInfoErrorFlag.NoName){
				//メッセージ："氏名が未入力です。";
				errorMessage = properties.getProperty("ECOMMON04");
			}
			request.setAttribute("errorMessage", errorMessage);

			// 区分コンボボックスのコレクション取得
			try {
				ArrayList<KubunBean> affiliationKubunName = KubunBean.getAffiliationKubunBeanList();
				request.setAttribute("affiliation_list", affiliationKubunName);
			} catch (SQLException | NamingException e) {
				throw new ServletException(e);
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/1002_inputMemberBasicInfo.jsp");
            dispatcher.forward(request, response);
		}

		/*
		 * DB登録実行
		 */
		int data;
		try {
			data = update(employeeNumber,password,belong,name,age,sex,finalEducation,entryYear,entryMonth
					,updateEmployeeNumber,updateName);
		} catch (NamingException | SQLException e) {
			throw new ServletException(e);
		}

		String message = null;
		if(data != 0){
			//成功時
			RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/1003_memberInfo.jsp");
	        dispatcher.forward(request, response);
		}else{
			//失敗時
			Properties properties = FileUtility.createProperties();
			//メッセージ："システムエラーによりメンバー基本情報の登録に失敗しました。";
			message = properties.getProperty("E100203");
			request.setAttribute("sysErrorMessage", message);
		}
	}

	/**
	 * メンバー基本情報更新処理
	 * @param employeeNumber
	 * @param password
	 * @param belong
	 * @param name
	 * @param age
	 * @param sex
	 * @param finalEducation
	 * @param entryYear
	 * @param entryMonth
	 * @param updateEmployeeNumber
	 * @param updateName
	 * @return
	 * @throws NamingException
	 * @throws SQLException
	 */
	private int update(String employeeNumber, String password, String belong, String name, String age, String sex,
			String finalEducation, String entryYear, String entryMonth, String updateEmployeeNumber,
			String updateName) throws NamingException, SQLException {

		Context context;
		int result = 0;

		context = new InitialContext();
		//データベースへの接続
		DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/skill_management");

		StringBuilder sb = new StringBuilder();

		sb.append("UPDATE");
		sb.append(" skill_management.member_basic ");
		sb.append("SET");
		sb.append(" affiliation_kubun=?");
		sb.append(" name=?");
		sb.append(" password=?");
		sb.append(" age=?");
		sb.append(" sex=?");
		sb.append(" final_education=?");
		sb.append(" entry_year=?");
		sb.append(" basic_fill_in_status=?");
		sb.append(" update_date=?");
		sb.append(" update_employee_number=?");
		sb.append(" update_employee_name=? ");
		sb.append("WHERE");
		sb.append(" employee_number=?");

		try (Connection db = ds.getConnection();
				PreparedStatement ps = db.prepareStatement(sb.toString());) {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			//プレースホルダーに対して、値を設定
			ps.setString(1, belong);
			ps.setString(2, name);
			ps.setString(3, password);
			ps.setString(4, age);
			ps.setString(5, sex);
			ps.setString(6, finalEducation);
			ps.setString(7, entryYear);
			ps.setString(8, "1");
			ps.setTimestamp(9, timestamp);
			ps.setString(10, updateEmployeeNumber);
			ps.setString(11, updateName);
			ps.setString(12, employeeNumber);

			//SQL実行
			result = ps.executeUpdate();
		}
		return result;
	}

	/**
	 * 入力チェック
	 * @param employeeNumber　社員番号
	 * @param password　パスワード
	 * @param belong　所属
	 * @param name　氏名
	 * @param age　年齢
	 * @param sex　性別
	 * @param finalEducation　最終学歴
	 * @param entryYear　年
	 * @param entryMonth　月
	 * @return flag　エラーフラグ
	 */
	private MemberBasicInfoErrorFlag checkInput(String employeeNumber, String password, String belong, String name,
			String age, String sex, String finalEducation, String entryYear, String entryMonth) {

		MemberBasicInfoErrorFlag flag = MemberBasicInfoErrorFlag.NoError;

		/*
		 * 未入力チェック
		 */
		if(password == ""){
			flag = MemberBasicInfoErrorFlag.NoPW;
			return flag;
//		}else if(re_password == ""){
//			flag = MemberBasicInfoErrorFlag.NoPW1;
//			return flag;
//		}else if(不一致){
//			flag = MemberBasicInfoErrorFlag.NoPW2;
//			return flag;
		}else if(belong == ""){
			flag = MemberBasicInfoErrorFlag.NoAff;
			return flag;
		}else if(name == ""){
			flag = MemberBasicInfoErrorFlag.NoName;
			return flag;
		}

		return flag;
	}

}
