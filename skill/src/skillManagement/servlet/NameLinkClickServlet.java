package skillManagement.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

import skillManagement.bean.MemberBasicBean;

/**
 * 氏名リンククリック処理のサーブレット
 *
 */
@SuppressWarnings("serial")
public class NameLinkClickServlet extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{


		HttpSession session = request.getSession(true);

		String basicFillInStatus = request.getParameter("basicStatus");

		//セッション情報を破棄
		session.removeAttribute("1001_member_list");
		session.removeAttribute("1001_page");

		//検索
//		String employeeNumber =
//		search(employeeNumber);


		//画面遷移
		if(basicFillInStatus.equals("0")){
			//メンバー基本情報記入状態区分が「0（未記入）」の場合
			RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/1002_inputMemberBasicInfo.jsp");
//			RequestDispatcher dispatcher = request.getRequestDispatcher("InputMemberBasicInfoServlet");
			dispatcher.forward(request, response);
		}else if(basicFillInStatus.equals("1")){
			//メンバー基本情報記入状態区分が「1（記入済み）」の場合
			RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/1003_memberInfo.jsp");
			dispatcher.forward(request, response);
		}
	}

//	private MemberBasicBean search(String employeeNumber) throws NamingException, SQLException {
//		//存在チェック
//		Context context;
////		String empNumber = null;
//
//
//		context = new InitialContext();
//		//データベースへの接続
//		DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/skill_management");
//
//		StringBuilder sb = new StringBuilder();
//
//		sb.append("SELECT");
//		sb.append(" employee_number ");
//		sb.append(" affiliation_kubun ");
//		sb.append(" name ");
//		sb.append("FROM");
//		sb.append(" member_basic ");
//		sb.append("WHERE");
//		sb.append(" employee_number = ?");
//
//		//DBからレコードを抽出
//		try(Connection db = ds.getConnection();
//				PreparedStatement ps = db.prepareStatement(sb.toString());
//				) {
//			//プレースホルダーに対して、値を設定
//			ps.setString(1, employeeNumber);
//
//			//SQL実行
//			try(ResultSet rs = ps.executeQuery();) {
//				//ResultSet取得
//				while(rs.next()){
//					String empNumber = rs.getString("employee_number");
//					int affiliationKubun = rs.getInt("affiliation_kubun");
//					String name = rs.getString("name");
//
//					MemberBasicBean bean = new MemberBasicBean(empNumber,affiliationKubun,"",name,0,"");
//				}
//			}
//		}
//		return bean;
//
//
//	}
}
