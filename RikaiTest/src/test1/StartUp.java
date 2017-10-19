package test1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class StartUp {

	 //enumの定義
	 enum Seibetu{
		 Otoko(0, "男"),// 男
		 Onna(1, "女");// 女

		private final int scode;
		private final String sname;

		 private Seibetu(int scode,String sname) {
			 this.scode = scode;
			 this.sname = sname;
		 }

		 public static Seibetu getSeibetu(int code) {
			 if(code == Seibetu.Otoko.scode) {
				 return Seibetu.Otoko;
			 } else if(code == Seibetu.Onna.scode) {
				 return Seibetu.Onna;
			 } else {
				 throw new IllegalArgumentException("不明な性別です。");
			 }
		 }

		 public int getScode() {
			 return this.scode;
		 }
	 }


	public static void main(String[] args) {


		// 第一問　JDBCドライバを使って、MySQLとの接続を行う
		// とりあえずエラーがでなければOK
		// localhostのMySQLに、rikaiユーザーで認証。（パスワードもrikai）
		// データベースはsampledb040に接続

		//不要　→　Class.forName("[MySQLのJDBCドライバークラス（パッケージ含めたクラス名を文字列で指定]");
		// 参考：http://www.ne.jp/asahi/hishidama/home/tech/java/DriverManager.html#JDBC4.0
//		try {
//			Class.forName("org.gjt.mm.mysql.Driver");
//		} catch (ClassNotFoundException e2) {
//			e2.printStackTrace();
//		}



		//必要事項の記述
		Connection conn = null;
		String url = "jdbc:mysql://localhost/sampledb040";
		String user = "rikai";
		String password = "rikai";
		Statement stmt = null;
		ResultSet rs = null;
		//接続実施
		try {
			conn = DriverManager.getConnection(url, user, password);
			// 第二問
			// １従業員情報（社員番号、所属コード、所属部・課・係（SQLで文字連結させて）、名前、性別（Java内で男女の文字に変換）enumを使う、年齢、生年月日を取得して
			// ２結果を二次元配列に格納する
			// ３従業員のデータ数は、実行するまでわからないものとする

			//ステートメントの作成（問い合わせ等を実施するために必要）
			stmt = conn.createStatement();

			//SQL文を記述し、実行する
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT employee_no,employee.shozoku_code,employee_name,sex,age,birthday,CONCAT(shozoku_bu,shozoku_ka,shozoku_kakari) as shozoku_name ");
			sb.append("FROM employee ");
			sb.append("JOIN shozoku ");
			sb.append("ON employee.shozoku_code = shozoku.shozoku_code ");
			sb.append("ORDER BY employee_no");
//			String sql = "SELECT employee_no,employee.shozoku_code,employee_name,sex,age,birthday,CONCAT(shozoku_bu,shozoku_ka,shozoku_kakari) as shozoku_name FROM employee JOIN shozoku ON employee.shozoku_code = shozoku.shozoku_code order by employee_no";
			String sql = sb.toString();
//			int num = stmt.executeUpdate(sql);
			rs = stmt.executeQuery(sql);

			//変数定義
			int code =0;
			int shozoku =0;
			String shozokuName;
			String sexStr;
			int age =0;
			Date birthday;
			int count = 0;

			while(rs.next()) {
				count++;
			}
			//データの一番初めに戻る
			rs.first();
			rs.previous();	// カーソルを-1にする（存在しない行だが、falseになるだけでエラーにはならない）

			//2次元配列の定義
			String date[][] = new String[count][7];

			//配列のカウント実施変数
			int i =0;
			//rs（employee）のデータを取得
			while(rs.next()) {		//ここでカーソル0からループ（上でprevious()したおかげ）
				code = rs.getInt("employee_no");				//社員番号
				date[i][0]=String.valueOf(code);				//配列に格納


				shozoku = rs.getInt("shozoku_code");			//所属コード
				date[i][1]=String.valueOf(shozoku);			//配列に格納

				shozokuName = rs.getString("shozoku_name");		//所属名
				date[i][2]=String.valueOf(shozokuName);		//配列に格納

				String name = rs.getString("employee_name");	//名前
				date[i][3]= name;								//配列に格納

				int sex = rs.getInt("sex");					//性別
				//性別の結び付け(数を文字へ)を実施する
				Seibetu sei = Seibetu.getSeibetu(sex);
				sexStr = sei.sname;								//性別が格納されている
				date[i][4]=sexStr;								//配列に格納

				age = rs.getInt("age");							//年齢
				date[i][5]=String.valueOf(age);				//配列に格納

				birthday = rs.getDate("birthday");				//生年月日
				date[i][6]=String.valueOf(birthday);			//配列に格納

				 //iに１をたす
				i=i+1;

			}

			// 二次元配列の中身をループで取り出して出力
			System.out.println("社員コード	所属コード	所属名 名前 性別 年齢 生年月日");
//			for(int j=0; j < count; j++) {
//				System.out.println(date[j][0]+":"+date[j][1]+":"+date[j][2]+":"+date[j][3]+":"+date[j][4]+":"+date[j][5]+":"+date[j][6]);
//			}
			for(int j =0; j<count; j++) {
				for(int a =0; a <date[j].length; a++) {
					if(a == date[j].length - 1) {
						System.out.println(date[j][a]);
					}else {
						System.out.print(date[j][a]+":");
					}
				}
			}

			/*
			 *  第三問　検索結果をクラスとリストに保持するパターン
			 */
			ArrayList<Employee> shyain = new ArrayList<Employee>();


			// ResultSetからレコード１件ずつwhileループで各カラムをゲット、
			//データの一番初めに戻る
			rs.first();
			rs.previous();

			/*
			 * 同じempやszkに繰り返し項目をセットして、それをArrayListに入れているので、
			 * 全件同じ値になっているはず！！　（実際にループでprintlnして確かめて）
			 */
			while(rs.next()) {
				Employee emp = new Employee();
				Shozoku szk = new Shozoku();
			// ResultSetからgetした各項目を、EmployeeとShozokuオブジェクトにセット
			// (なにか処理が必要)
			// ArrayListにEmployeeオブジェクトをセット
				code = rs.getInt("employee_no");				//社員コード
				emp.setEmployee_code(code);						//Employee にセット

				String name = rs.getString("employee_name");	//氏名
				emp.setEmployee_name(name);

				shozoku = rs.getInt("shozoku_code");			//ここでemployeeクラスから所属名を取り出す必要
				shozokuName = rs.getString("shozoku_name");

				// ここで所属コードをszkにセット
				szk.setShozoku_code(shozoku);
				szk.setShozoku_name(shozokuName);
				// szkをEmployeeオブジェクト(emp)にセット
//				emp.setShozoku_code(szk.getShozoku_code());		//所属コードを両クラス同じにする
				emp.setShozoku(szk);


				int sex = rs.getInt("sex");					//性別
				//性別の結び付け(数を文字へ)を実施する
				Seibetu sei = Seibetu.getSeibetu(sex);
				sexStr = sei.sname;								//性別が格納されている
				emp.setSex(sexStr);


				age = rs.getInt("age");							//年齢
				emp.setAge(age);

				birthday = rs.getDate("birthday");				//生年月日
				emp.setBirthday(birthday);


				shyain.add(emp);

//				System.out.println(emp);
			}

			// ArrayListの中身をループで取り出して出力
			for(Employee emp : shyain) {
//				System.out.println(emp);
				emp.aisatu();
			}


			//close()はfinally句


		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
//		System.out.println(conn);
	}



}


