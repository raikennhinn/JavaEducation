package skillManagement.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import org.apache.log4j.Logger;

import skillManagement.bean.MemberBasicBean;
import skillManagement.enumeration.Kubun;
import skillManagement.enumeration.MemberBasicInfoErrorFlag;
import skillManagement.util.FileUtility;

/**
 * ログイン処理のサーブレット
 *
 */
@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet{
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{

		// ロガーインスタンスの生成
        Logger logger = Logger.getLogger(LoginServlet.class);
        logger.info("開始");

		/*
		 * XXX【不要】
		 * XXX サーブレットで（out.println()で）HTMLを出力する場合だけ呼ぶもの
		 * XXX ↓引数に "text/html;" と指定してるのもそのため
		 */
		//response.setContentType("text/html; charset=UTF-8");

		//ログイン画面で入力した値を取得
		String empNum = request.getParameter("emp_num");
		String pass = request.getParameter("pass");

		/*
		 * XXX 【不要】
		 * XXX 日本語のパラメータを受け取る場合には必要だが、このログイン画面では不要
		 * XXX また、呼び出すときはパラメータ取得する前にする必要がある
		 * XXX （request.getParameterする前に）
		 */
		//request.setCharacterEncoding("UTF-8");

		/*
		 * バリデーションチェック
		 * XXX メソッド名を修正すること
		 */
//		ErrorFlag status = authUser(emp_num, pass);
		MemberBasicInfoErrorFlag status = checkRequired(empNum, pass);
		String message = null;

		if(status == MemberBasicInfoErrorFlag.NoError){
			// 入力エラーなしの場合

			//ログイン処理
			MemberBasicBean data;
			try {
				data = login(empNum, pass);
			} catch (NamingException | SQLException e) {
				throw new ServletException(e);
			}

			/*
			 * XXX dataの状態をチェックしてログイン成否を判定
			 * XXX 成功時はセッション保持と画面遷移（権限区分により分岐）
			 * XXX 失敗時はエラーメッセージの取得と画面遷移
			 */

			//dataの状態をチェックしてログイン成否を判定
			if(data != null){
				//成功時

				/*
				 * TODO 【修正】
				 * TODO ・ログイン成功後、ログイン情報をセッションに保持する直前に書く
				 * TODO ・セッションがすでに作成されているかをチェックし、
				 * TODO 　作成されていたら、そのセッションを破棄して、新規セッションを取得するようにする
				 */
				HttpSession session = request.getSession(true);

				//セッションを保持
				session.setAttribute("login_info", data);

				//権限区分に応じて遷移
				//管理者権限の場合
				if(data.getAuthKubun() == Kubun.AUTHORITY_ADMIN.getKubun_value()){
					RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/9001_adminMenu.jsp");
					dispatcher.forward(request, response);
				//通常権限の場合
				}else{
					//RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/1001_memberList.jsp");
					RequestDispatcher dispatcher =
							request.getRequestDispatcher("../skillManagement/MemberListServlet");
					dispatcher.forward(request, response);

				}
			}else{
				//失敗時
				Properties properties = FileUtility.createProperties();
				//メッセージ："入力した社員番号もしくはパスワードが正しくありません。";
				message = properties.getProperty("E000102");
				request.setAttribute("message", message);
				request.setAttribute("emp_num", empNum);

				RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/0001_login.jsp");
	            dispatcher.forward(request, response);
			}
		}else{
			// 入力エラーありの場合

			//ログイン画面に戻る
			request.setAttribute("error", status);

			Properties properties = FileUtility.createProperties();
			if(status == MemberBasicInfoErrorFlag.NoBoth){
				//メッセージ："社員番号とパスワードの両方が未入力です。";
				message = properties.getProperty("E000101");
			}else if(status == MemberBasicInfoErrorFlag.NoID){
				//メッセージ："社員番号が未入力です。";
				message = properties.getProperty("ECOMMON01");
			}else if(status == MemberBasicInfoErrorFlag.NoPW){
				//メッセージ："パスワードが未入力です。";
				message = properties.getProperty("ECOMMON02");
				request.setAttribute("emp_num", empNum);
			}
			request.setAttribute("message", message);

			RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/0001_login.jsp");
            dispatcher.forward(request, response);
		}
	}

	/**
	 * 入力チェック
	 * @param emp_num　社員番号
	 * @param pass　パスワード
	 * @return flag　エラーフラグ
	 */
	/* XXX スコープはprivateでよい */
	/* XXX メソッド名を修正すること */
//	public ErrorFlag authUser(String emp_num, String pass){
	private MemberBasicInfoErrorFlag checkRequired(String empNum, String pass){

		MemberBasicInfoErrorFlag flag = MemberBasicInfoErrorFlag.NoError;

		if(empNum == "" && pass == ""){
			flag = MemberBasicInfoErrorFlag.NoBoth;
			return flag;
		}else if(empNum == ""){
			flag = MemberBasicInfoErrorFlag.NoID;
			return flag;
		}else if(pass == ""){
			flag = MemberBasicInfoErrorFlag.NoPW;
			return flag;
		}
		return flag;
	}

	/**
	 * ログイン処理
	 * @param emp_num
	 * @param pass
	 * @return rs
	 */

	/* XXX スコープはprivateでよい */
//	public MemberBasicBean login(String emp_num, String pass){
	private MemberBasicBean login(String empNum, String pass) throws NamingException, SQLException {
		//ログイン処理
		Context context;
		Connection db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		MemberBasicBean bb = null;
		try {
			context = new InitialContext();
			//データベースへの接続
			DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/skill_management");
			db = ds.getConnection();

			/* XXX 区分マスタと結合して、区分の名称を取得
			 * XXX プレースホルダーを使って社員番号やパスワードのパラメータをセットする
			 * */

			StringBuilder sb = new StringBuilder();

			sb.append("SELECT");
			sb.append(" b.affiliation_kubun, k1.kubun_name as affiliation_kubun_name, b.auth_kubun,");
			sb.append(" k2.kubun_name as auth_kubun_name, b.name ");
			sb.append("FROM");
			sb.append(" skill_management.member_basic as b ");
			sb.append("INNER JOIN");
			sb.append(" skill_management.m_kubun as k1 ");
			sb.append("ON");
			sb.append(" b.affiliation_kubun = k1.kubun_value ");
			sb.append("INNER JOIN");
			sb.append(" skill_management.m_kubun as k2 ");
			sb.append("ON");
			sb.append(" b.auth_kubun = k2.kubun_value ");
			sb.append("WHERE");
			sb.append(" b.employee_number = ?");
			sb.append(" and b.password = ?");
			sb.append(" and k1.kubun_category = 'AFFILIATION'");
			sb.append(" and k2.kubun_category = 'AUTHORITY'");

			//DBからレコードを抽出
			ps = db.prepareStatement(sb.toString());

			//プレースホルダーに対して、値を設定
			ps.setString(1, empNum);
			ps.setString(2, pass);

			//SQL実行
			rs = ps.executeQuery();

			//ResultSet取得
			while(rs.next()){
				String employeeNumber = empNum;
				int affiliationKubun = rs.getInt("affiliation_kubun");
				String affiliationKubunName = rs.getString("affiliation_kubun_name");
				String name = rs.getString("name");
				int authKubun = rs.getInt("auth_kubun");
				String authKubunName = rs.getString("auth_kubun_name");

				bb = new MemberBasicBean
						(employeeNumber,affiliationKubun,affiliationKubunName,name,authKubun,authKubunName);
			}

			/* XXX 例外はThrowする */
//		} catch (NamingException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
		} finally {
//			try {
				if(rs != null) { rs.close(); }
				if(ps != null) { ps.close(); }
				if(db != null) { db.close(); }
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
		}
		return bb;
	}
}
