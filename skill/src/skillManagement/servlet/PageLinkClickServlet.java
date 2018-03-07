package skillManagement.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import skillManagement.bean.MemberListBean;

/**
 * ページリンククリック処理のサーブレット
 *
 */
@SuppressWarnings("serial")
public class PageLinkClickServlet extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{

		HttpSession session = request.getSession(true);

		//セッション情報を取得
//		String list = (String) session.getAttribute("1001_member_list");
		ArrayList<MemberListBean> list = (ArrayList<MemberListBean>)session.getAttribute("1001_member_list");	//メンバー一覧情報
		int pageInfo[] = (int[])session.getAttribute("1001_page");	//ページ情報（総ページ数と現在表示ページ）

		//表示する一覧情報を決定
		int count = 15;	//1ページに表示する件数
		String page = request.getParameter("page");		//クリックしたページ番号取得
		int clickPage = 0;

		if(page != null){
			clickPage = Integer.parseInt(page);		//クリックしたページ番号取得後、型変換
		}
//		int clickPage = Integer.parseInt(page);		//クリックしたページ番号取得後、型変換
//		int firstMember = (clickPage-1) * count;	//クリックしたページ番号で一覧表示するデータの１件目に当たるカウンタ


		String linkClick = request.getParameter("nextBack");
		int lc = 0;
		if(linkClick != null){
			lc = Integer.parseInt(linkClick);
		}
		switch(lc){
			case -5 :
				clickPage = pageInfo[1] - 5;
				if(clickPage < 0){
					clickPage = 1;
				}
				break;
			case -1 :
				clickPage = pageInfo[1] - 1;
				break;
			case 1 :
				clickPage = pageInfo[1] + 1;
				break;
			case 5 :
				clickPage = pageInfo[1] + 5;
				if(clickPage > pageInfo[0]-1){
					clickPage = pageInfo[0]-1;
				}
				break;
			default :
				break;
		}

		//クリックしたページ番号を、ページ情報の現在表示ページにセット
		pageInfo[1] = clickPage;

		//表示するページリンクを作成
		int totalPage = pageInfo[0];	//総ページ数
		int pageNumber[] = new int[totalPage];	//ページ番号
		//すべてのページ番号を作成
		for(int i=0; i<pageNumber.length; i++){
			pageNumber[i] = i+1;
		}

		int startPage;// ページャーの左端（開始）のページ番号

		// クリックしたページ番号-2が0以下 もしくは クリックしたページ番号-1が0以下の場合は
		// 左端ページ番号を1に設定
		// それ以外は、クリックしたページ番号-2を左端ページ番号に設定
		if(clickPage-2<=0 || clickPage-1<=0){
			startPage = 1;
		}else{
			startPage = clickPage - 2;
		}

		int endPage;// ページャーの右端（終了）のページ番号

		// 最終ページ - 開始ページが4以上の場合、終了ページは開始ページ + 4
		// それ以外の場合、終了ページは最終ページ
		if( (pageNumber[totalPage- 1] - startPage) >= 4 ) {
			endPage = startPage + 4;
		} else {
			endPage = pageNumber[totalPage- 1];
		}

		// 最終ページ近くのリンクをクリックされたときでもページャが5つ表示されるよう対策
		// 総ページが5以上 かつ 最終ページ - 開始ページが4未満の場合
		if(totalPage >= 5) {
			switch (endPage - startPage) {
			// 最終ページ - 開始ページが3なら 開始ページを -1
			// 最終ページ - 開始ページが2なら 開始ページを -2
			// 最終ページ - 開始ページが1なら 開始ページを -3
			// 最終ページ - 開始ページが0なら 開始ページを -4
			case 3 :
				startPage -= 1;
				break;
			case 2 :
				startPage -= 2;
				break;
			case 1 :
				startPage -= 3;
				break;
			case 0 :
				startPage -= 4;
				break;
			}
		}

		// 左端ページ番号を基点に最大5つ、ページ番号配列（pageNumber[]）から取得して、Mapにセット

		//表示するリンクを決定（ページ番号）
		TreeMap<Integer, Boolean> dispPageNum = new TreeMap<Integer, Boolean>();	//クリックしたリンクを中央、前後2ページを表示
		//表示するリンクを決定（矢印）
		TreeMap<String, Boolean> nextBack = new TreeMap<String, Boolean>();

		// startPage、endPageはページ番号（１始まり）のため、
		// pageNumberの添え字に合うよう、-1している
		for(int i = startPage - 1; i < endPage; i++) {
			if(pageNumber[i] == clickPage) {
				// クリックされたページはラベル表示
				dispPageNum.put(pageNumber[i], false);
			} else {
				// ほかをリンク表示
				dispPageNum.put(pageNumber[i], true);
			}
		}

		// 「<<」と「<」を表示
		if(startPage >= 2){
			//リンク表示
			nextBack.put("dispBack", true);
		}else{
			//ラベル表示
			nextBack.put("dispBack", false);
		}

		// 「>」と「>>」を表示
		if(endPage == pageNumber[totalPage-1]){
			//ラベル表示
			nextBack.put("dispNext", false);
		}else{
			//リンク表示
			nextBack.put("dispNext", true);
		}

		request.setAttribute("dispPageNum", dispPageNum);
		request.setAttribute("nextBack", nextBack);

		//クリックしたページ番号で一覧表示するデータの１件目に当たるカウンタ
		int firstMember = (clickPage-1) * count;
		request.setAttribute("firstMember", firstMember);

		//セッションを保持
		session.setAttribute("1001_page", pageInfo);

		RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/1001_memberList.jsp");
        dispatcher.forward(request, response);

	}
}
