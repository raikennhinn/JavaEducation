package skillManagement.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import skillManagement.bean.MemberListBean;

/**
 * メンバー一覧表示処理のサーブレット
 *
 */
@SuppressWarnings("serial")
public class MemberListServlet extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{

		//画面遷移メソッド
		screenTransition(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{

		//画面遷移メソッド
		screenTransition(request,response);
	}

	public void screenTransition(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		//セッション情報を破棄
		session.removeAttribute("1001_member_list");

		//メンバー一覧データ取得
		try {
			ArrayList<MemberListBean> memberList = MemberListBean.getMemberList();

			//取得した一覧データをセッションに保持
			session.setAttribute("1001_member_list", memberList);

			//セッション情報を破棄
			session.removeAttribute("1001_page");

			//ページ数を算出
			int num = 15;
			int pageNum = memberList.size() / num;

			//ページ数を算出し、余りがある場合
			if(memberList.size() % num != 0){
				pageNum = pageNum + 1;
			}

			int pageInfo[] = {pageNum,1};

			//ページ情報を保持
			session.setAttribute("1001_page", pageInfo);

			//表示するリンクを決定（ページ番号）
			TreeMap<Integer, Boolean> dispPageNum = new TreeMap<Integer, Boolean>();
			//初期表示のため、1ページから順に5ページをページ番号（ページャー）とする

			dispPageNum.put(1, false);
			if(pageNum <= 5){
				for(int i=2; i<=pageNum; i++){
					dispPageNum.put(i, true);
				}
			}else if(pageNum >= 6){
				for(int i=2; i<=5; i++){
					dispPageNum.put(i, true);
				}
			}

			//表示するリンクを決定（矢印）
			TreeMap<String, Boolean> nextBack = new TreeMap<String, Boolean>();
			//初期表示（矢印）
			nextBack.put("dispBack", false);
			nextBack.put("dispNext", true);

			//クリックしたページ番号で一覧表示するデータの１件目に当たるカウンタ
			int firstMember = 0;
			request.setAttribute("firstMember", firstMember);

			request.setAttribute("dispPageNum", dispPageNum);
			request.setAttribute("nextBack", nextBack);

			RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/1001_memberList.jsp");
	        dispatcher.forward(request, response);

		} catch (SQLException | NamingException e) {
			throw new ServletException(e);
		}
	}
}
