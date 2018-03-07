package skillManagement.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import skillManagement.bean.KubunBean;

/**
 * メンバー基本情報作成画面表示のサーブレット
 *
 */
@SuppressWarnings("serial")
public class DispCreateMemberBasicInfoServlet extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{

		//データ取得処理
		try {
			// 区分コンボボックスのコレクション取得
			ArrayList<KubunBean> affiliationKubunName = KubunBean.getAffiliationKubunBeanList();
			HashMap<String, String> authKubunName = KubunBean.getAuthKubunMap();

			// リクエストにコレクションをセット
			request.setAttribute("affiliation_list", affiliationKubunName);
			request.setAttribute("auth", authKubunName);
		} catch (SQLException | NamingException e) {
			throw new ServletException(e);
		}

		//メンバー基本情報作成画面に遷移
	    RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/9002_memberBasicInfo.jsp");
		dispatcher.forward(request, response);

	}

}
