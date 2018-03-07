package skillManagement.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
import skillManagement.util.InputUtility;

/**
 * メンバー基本情報作成処理実行のサーブレット
 *
 */
@SuppressWarnings("serial")
public class ExeCreateMemberBasicInfoServlet extends HttpServlet{
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{

		//メンバー基本情報作成画面で入力した値を取得
		request.setCharacterEncoding("UTF-8");
		String employeeNumber = request.getParameter("emp_num");
		String affiliation = request.getParameter("affiliation");
		String name = request.getParameter("name");
		String auth = request.getParameter("auth");

		// セッションよりログインユーザ（更新者）情報を取得
		HttpSession session = request.getSession(true);
		MemberBasicBean bean = (MemberBasicBean)session.getAttribute("login_info");
		String updateEmployeeNumber = bean.getEmployeeNumber();
		String updateName = bean.getName();

		/*
		 * バリデーションチェック
		 */
		MemberBasicInfoErrorFlag status = checkInput(employeeNumber,affiliation,name,auth);
		String errorMessage = null;

		// 入力エラー時、エラーメッセージを取得して元の画面に戻る
		if(status != MemberBasicInfoErrorFlag.NoError){

			request.setAttribute("error", status);

			Properties properties = FileUtility.createProperties();
			if(status == MemberBasicInfoErrorFlag.NoID){
				//メッセージ："社員番号が未入力です。";
				errorMessage = properties.getProperty("ECOMMON01");
			}else if(status == MemberBasicInfoErrorFlag.NoAff){
				//メッセージ："所属が未選択です。";
				errorMessage = properties.getProperty("ECOMMON03");
			}else if(status == MemberBasicInfoErrorFlag.NoName){
				//メッセージ："氏名が未入力です。";
				errorMessage = properties.getProperty("ECOMMON04");
			}else if(status == MemberBasicInfoErrorFlag.NoAuth){
				//メッセージ："権限が未入力です。";
				errorMessage = properties.getProperty("ECOMMON05");
			}else if(status == MemberBasicInfoErrorFlag.IsNotHalfId){
				//メッセージ："社員番号は半角英数字を入力してください。"
				errorMessage = properties.getProperty("E900202");
			}
			request.setAttribute("errorMessage", errorMessage);

			// 区分コンボボックスのコレクション取得
			try {
				ArrayList<KubunBean> affiliationKubunName = KubunBean.getAffiliationKubunBeanList();
				HashMap<String, String> authKubunName = KubunBean.getAuthKubunMap();
				request.setAttribute("affiliation_list", affiliationKubunName);
				request.setAttribute("auth", authKubunName);
			} catch (SQLException | NamingException e) {
				throw new ServletException(e);
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/9002_memberBasicInfo.jsp");
            dispatcher.forward(request, response);
		}

		/*
		 * DB存在チェック
		 */
		String id = null;
		try {
			id = existenceCheck(employeeNumber);
		} catch (SQLException | NamingException e1) {
			throw new ServletException(e1);
		}

		// 入力された基本情報（社員番号）がDBに存在する場合、
		// エラーメッセージを取得して元の画面に戻る
		if(id != null){

			Properties properties = FileUtility.createProperties();
			//メッセージ："入力した社員番号は、既に登録済みです。";
			errorMessage = properties.getProperty("E900201");
			request.setAttribute("errorMessage", errorMessage);

			// 区分コンボボックスのコレクション取得
			try {
				ArrayList<KubunBean> affiliationKubunName = KubunBean.getAffiliationKubunBeanList();
				HashMap<String, String> authKubunName = KubunBean.getAuthKubunMap();
				request.setAttribute("affiliation_list", affiliationKubunName);
				request.setAttribute("auth", authKubunName);
			} catch (SQLException | NamingException e) {
				throw new ServletException(e);
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/9002_memberBasicInfo.jsp");
            dispatcher.forward(request, response);
		}

		/*
		 * DB登録実行
		 */
		int data;
		try {
			data = insert(employeeNumber,affiliation,name,auth,updateEmployeeNumber,updateName);
		} catch (NamingException | SQLException e) {
			throw new ServletException(e);
		}
		String message = null;
		if(data != 0){
			//成功時
			Properties properties = FileUtility.createProperties();
			//メッセージ："メンバーの基本情報が作成されました。";
			message = properties.getProperty("I900301");
			request.setAttribute("message1", message);
		}else{
			//失敗時
			Properties properties = FileUtility.createProperties();
			//メッセージ："システムエラーによりメンバーの基本情報の作成に失敗しました。";
			message = properties.getProperty("E900301");
			request.setAttribute("message2", message);
		}

		/*
		 * メンバー基本情報作成結果画面に遷移
		 */
		// 区分コンボボックスのコレクション取得
		try {
			ArrayList<KubunBean> affiliationKubunName = KubunBean.getAffiliationKubunBeanList();
			HashMap<String, String> authKubunName = KubunBean.getAuthKubunMap();

			for(int i = 0 ; i < affiliationKubunName.size() ; i++){
				KubunBean aff = affiliationKubunName.get(i);

				if(Integer.parseInt(affiliation) == aff.getAffiliationKubun()){
					String kName = aff.getAffiliationKubunName();
					request.setAttribute("affiliation_result", kName);
					break;
				}
			}

			String kName = authKubunName.get(auth);
			request.setAttribute("auth_result", kName);

		} catch (SQLException | NamingException e) {
			throw new ServletException(e);
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/9003_memberBasicInfoResult.jsp");
        dispatcher.forward(request, response);

	}

	/**
	 * 存在チェック
	 * @param employeeNumber　社員番号
	 * @return flag　エラーフラグ
	 * @throws SQLException
	 * @throws NamingException
	 */
	private String existenceCheck(String employeeNumber) throws SQLException, NamingException {
		//存在チェック
		Context context;
		String empNumber = null;

		context = new InitialContext();
		//データベースへの接続
		DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/skill_management");

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT");
		sb.append(" employee_number ");
		sb.append("FROM");
		sb.append(" member_basic ");
		sb.append("WHERE");
		sb.append(" employee_number = ?");

		//DBからレコードを抽出
		try(Connection db = ds.getConnection();
				PreparedStatement ps = db.prepareStatement(sb.toString());
				) {
			//プレースホルダーに対して、値を設定
			ps.setString(1, employeeNumber);

			//SQL実行
			try(ResultSet rs = ps.executeQuery();) {
				//ResultSet取得
				while(rs.next()){
					empNumber = rs.getString("employee_number");
				}
			}
		}
		return empNumber;
	}

	/**
	 * 入力チェック
	 * @param employeeNumber　社員番号
	 * @param affiliation　所属
	 * @param name　氏名
	 * @param auth　権限
	 * @return flag　エラーフラグ
	 */

	private MemberBasicInfoErrorFlag checkInput
	(String employeeNumber, String affiliation, String name, String auth){

		MemberBasicInfoErrorFlag flag = MemberBasicInfoErrorFlag.NoError;

		/*
		 * 未入力チェック
		 */
		if(employeeNumber == ""){
			flag = MemberBasicInfoErrorFlag.NoID;
			return flag;
		}else if(affiliation == ""){
			flag = MemberBasicInfoErrorFlag.NoAff;
			return flag;
		}else if(name == ""){
			flag = MemberBasicInfoErrorFlag.NoName;
			return flag;
		}else if(auth == ""){
			flag = MemberBasicInfoErrorFlag.NoAuth;
			return flag;
		}

		/*
		 * 半角チェック
		 */
		if(!InputUtility.isHalfWidthAlphanumeric(employeeNumber)) {
			flag = MemberBasicInfoErrorFlag.IsNotHalfId;
			return flag;
		}

		return flag;
	}

	/**
	 * メンバー基本情報登録処理
	 * @param employeeNumber　社員番号
	 * @param affiliation　所属
	 * @param name　氏名
	 * @param auth　権限
	 * @return result
	 * @throws SQLException
	 * @throws NamingException
	 */
	private int insert
	(String employeeNumber, String affiliation, String name, String auth,
			String updateEmployeeNumber, String updateName) throws SQLException, NamingException {

		Context context;
		int result = 0;

		context = new InitialContext();
		//データベースへの接続
		DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/skill_management");

		StringBuilder sb = new StringBuilder();

		sb.append("INSERT INTO");
		sb.append(" skill_management.member_basic(");
		sb.append(" employee_number");
		sb.append(" , affiliation_kubun");
		sb.append(" , name");
		sb.append(" , password");
		sb.append(" , auth_kubun");
		sb.append(" , update_employee_number");
		sb.append(" , update_employee_name");
		sb.append(") ");
		sb.append("VALUES");
		sb.append(" (?,?,?,?,?,?,?)");

		try (Connection db = ds.getConnection();
				PreparedStatement ps = db.prepareStatement(sb.toString());) {

			//プレースホルダーに対して、値を設定
			ps.setString(1, employeeNumber);
			ps.setString(2, affiliation);
			ps.setString(3, name);
			ps.setString(4, employeeNumber);
			ps.setString(5, auth);
			ps.setString(6, updateEmployeeNumber);
			ps.setString(7, updateName);

			//SQL実行
			result = ps.executeUpdate();

		}
		return result;
	}
}
