package webApplication.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import webApplication.bean.Shozoku;

public class ShozokuLeaderUpdateServlet extends CommonServlet {

	/**
	 * 所属長の登録を行うサーブレット
	 */
	@Override
	protected void doServlet(HttpServletRequest req, HttpServletResponse resp, HttpSession hpSession, Logger logger)
			throws ServletException, IOException {

		logger.info("所属長の登録の実施を開始。");

		//SHozokuListからパラメータをうけとる。受け取ったパラメータをリストに入れる
		String[] Shozoku_Leader = req.getParameterValues("hidden_employee_no");
		ArrayList<String> ShozokuL = new ArrayList<String>();
		String[] ShL;

		//空白で無いもののみの取り出し。ShozokuLに入れる
		for(int i =0; i< Shozoku_Leader.length; i++) {
			if(!Shozoku_Leader[i].equals("")) {
				ShozokuL.add(Shozoku_Leader[i]);
			}
		}
		//SQLが実行されているかどうかの確認を行う。SQLを実行すべき件数
		List<String> erroCode = new ArrayList<String>();
		int sql = 0;
		int goukei =0;
		//所属クラスのメソッドを使うためにオブジェクト生成
		Shozoku shozoku = new Shozoku();
		try {
		//受け取った段階では、従業員No--所属コードという状態になっているため、2つを分ける。
			for(int i =0;i < ShozokuL.size(); i++) {
				//0に社員番号、1に所属コードでSｈLに値を入れる
				ShL = ShozokuL.get(i).split("--");
				//数値を足していく
				sql = shozoku.ShozokuLeaderUpdate(Integer.parseInt(ShL[0]),Integer.parseInt(ShL[1]),logger);
				//更新に失敗した場合
				if(sql == 0) {
					erroCode.add(ShL[1]);
				}
				goukei = goukei + sql;
			}

			//結果を元にリスト初期表示ShozokuListServletへと帰る
			//実行が終了した数と、リストの長さが同じ
			if(goukei == ShozokuL.size()) {
				//登録を行い、初期表示画面へと帰ってくる。
				logger.info("指定した所属長の登録が完了しました。");
				//メッセージに追加を行いそれを表示するように変更する
				req.setAttribute("mes", "指定した所属長の登録が完了しました");
				//初期表示、ShozokuListサーブレットに接続
				req.setAttribute("flg", true);
				this.getServletContext().getRequestDispatcher("/ShozokuList/").
				forward(req, resp);
			}else{
				logger.info("登録要求数と実登録数が合いません");

				String code ="";
				for(int i = 0; i < erroCode.size(); i++) {
					//最大値はサイズー1
					if(i == erroCode.size() -1 ) {
						code = code + erroCode.get(i);
					} else {
						code = code + erroCode.get(i)+",";
					}
				}
				req.setAttribute("mes","指定した"+code+"の所属長の登録が失敗しました");
				//初期表示、ShozokuListサーブレットに接続
				req.setAttribute("flg", false);
				this.getServletContext().getRequestDispatcher("/ShozokuList/").
				forward(req, resp);
			}

		} catch (NumberFormatException | SQLException | NamingException e) {
				e.printStackTrace();
		}
	}

}
