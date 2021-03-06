package webApplication.bean;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import webApplication.enumeration.Seibetu;

public class Employee {
	//変数
	private int employee_no;		//社員コード
//	private int shozoku_code;		//所属コード　shozoku（所属オブジェクト）が持っているので不要
	private String employee_name;	//氏名
	private String employee_namekana; //かな氏名
	private String sex;				//性別
	private int age;				//年齢
	private Date birthday;			//生年月日
	Shozoku shozoku;				//Shozokuクラスの


	//社員コードのゲッターとセッター
	public int getEmployee_no() {
		return employee_no;
	}
	public void setEmployee_no(int employee_code) {
		this.employee_no = employee_code;
	}
//	//所属コードのゲッターとセッター
//	public int getShozoku_code() {
//		return shozoku_code;
//	}
//	public void setShozoku_code(int shozoku_code) {
//		this.shozoku_code = shozoku_code;
//	}
	//氏名のゲッターとセッター
	public String getEmployee_name() {
		return employee_name;
	}
	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}
	//性別のゲッターとセッター
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	// 画面に出力するための性別ゲッター
	public String getSexAtKanji() {
		Seibetu sei = Seibetu.getSeibetu(Integer.parseInt(this.sex));
		return sei.getName();
	}

	//年齢のゲッターとセッター
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	//生年月日のゲッターとセッター
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	// 画面に出力するための生年月日ゲッター
	public String getBirthdayAtSlash() {
		if(this.birthday == null) {
			return "";
		} else {
			return this.birthday.toString().replace('-', '/');
		}
	}

	//所属コードの結びつけゲッターとセッター
	public Shozoku getShozoku() {
		return shozoku;
	}
	public void setShozoku(Shozoku shozoku) {
		this.shozoku = shozoku;
//		shozoku.getShozoku_name();	所属名をゲットしてEmployeeにセットするのは不要
		//EmployeeとShozokuで同じ項目を重複して持つ意味はない
	}

	public String toString() {
		return employee_no + employee_name + sex;

	}

	//aisatu()メソッド
	public void aisatu() {
		System.out.println("社員番号："+employee_no+" 氏名："+employee_name + " " + shozoku.pritShozoku()+" 性別："+sex+" 年齢："+age+" 生年月日"+birthday);
//		shozoku.pritShozoku();
	}

//	かな入力の名前と氏名
	public String getEmployee_namekana() {
		return employee_namekana;
	}
	public void setEmployee_namekana(String employee_namekana) {
		this.employee_namekana = employee_namekana;
	}

	/**
	 * DBへの接続を実行するメソッド
	 * @throws SQLException
	 * @throws NamingException
	 */
	private static Connection conectionDB() throws SQLException, NamingException {
		Connection conn = null;
		Context context;

		context = new InitialContext();
		DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/WebApplication");
		conn = ds.getConnection();

		return conn;
	}


	/**
	 * DBより従業員No.指定で情報を取得し、自分自身にセットする
	 * @param employee_no
	 * @throws SQLException
	 * @throws NamingException
	 */
	public void setEmployeeDataByDB(String employee_no) throws SQLException, NamingException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//DBへの接続実施
			conn = conectionDB();

			//SQLで従業員Noを条件とし情報の取得を実施
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT employee_no, ");
			sb.append(" shozoku_code, ");
			sb.append(" employee_name, ");
			sb.append(" employee_name_kana, ");
			sb.append(" sex, ");
			sb.append(" age, ");
			sb.append(" birthday ");
			sb.append(" FROM employee ");
			sb.append(" WHERE employee_no = ? ");

			//文字列へ
			String select = sb.toString();
			//？に取得してきた従業員Noをセットし、実行
			ps = conn.prepareStatement(select);
			ps.setInt(1,Integer.parseInt(employee_no));
			rs = ps.executeQuery();

			while(rs.next()) {

				//Shozokuオブジェクトを作成
				Shozoku szk = new Shozoku();
				//所属コードをemployeeオブジェクトを介して所属オブジェクトにつめる
				int shozoku = rs.getInt("shozoku_code");
				szk.setShozoku_code(shozoku);
				this.setShozoku(szk);	//empのShozokuにセット

				//氏名
				String name = rs.getString("employee_name");	//氏名
				this.setEmployee_name(name);

				//氏名カナ
				String namekana = rs.getString("employee_name_kana");
				this.setEmployee_namekana(namekana);

				//性別
				int sex = rs.getInt("sex");
				//性別の結び付け(数を文字へ)を実施する
				Seibetu sei = Seibetu.getSeibetu(sex);
				String sexStr = sei.getName();					//性別が格納されている
				this.setSex(Integer.toString(sex));

				//年齢
				int age = rs.getInt("age");
				this.setAge(age);

				//生年月日
				Date birthday = rs.getDate("birthday");
				this.setBirthday(birthday);
			}

		} finally {
			rs.close();
			ps.close();
			conn.close();
		}
	}


	/**
	 * 従業員Noでしていした従業員情報を返す
	 * @throws NamingException
	 * @throws SQLException
	 */
	public static Employee getEmployeeData(String employee_no) throws SQLException, NamingException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//DBへの接続実施
			conn = conectionDB();

			//SQLで従業員Noを条件とし情報の取得を実施
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT employee_no, ");
			sb.append(" shozoku_code, ");
			sb.append(" employee_name, ");
			sb.append(" employee_name_kana, ");
			sb.append(" sex, ");
			sb.append(" ifnull(age, -1) as age, ");
			sb.append(" birthday ");
			sb.append(" FROM employee ");
			sb.append(" WHERE employee_no = ? ");

			//文字列へ
			String select = sb.toString();
			//？に取得してきた従業員Noをセットし、実行
			ps = conn.prepareStatement(select);
			ps.setInt(1,Integer.parseInt(employee_no));
			rs = ps.executeQuery();

			//employeeオブジェクトの作成
			Employee emp = new Employee();
			while(rs.next()) {
				//従業員No のセット
				emp.setEmployee_no(Integer.parseInt(employee_no));

				//Shozokuオブジェクトを作成
				Shozoku szk = new Shozoku();
				//所属コードをemployeeオブジェクトを介して所属オブジェクトにつめる
				int shozoku = rs.getInt("shozoku_code");
				szk.setShozoku_code(shozoku);
				emp.setShozoku(szk);	//empのShozokuにセット

				//氏名
				String name = rs.getString("employee_name");	//氏名
				emp.setEmployee_name(name);

				//氏名カナ
				String namekana = rs.getString("employee_name_kana");
				emp.setEmployee_namekana(namekana);

				//性別
				int sex = rs.getInt("sex");
				//性別の結び付け(数を文字へ)を実施する
				Seibetu sei = Seibetu.getSeibetu(sex);
				String sexStr = sei.getName();					//性別が格納されている
				emp.setSex(Integer.toString(sex));

				//年齢
				int age = rs.getInt("age");
				emp.setAge(age);

				//生年月日
				Date birthday = rs.getDate("birthday");
				emp.setBirthday(birthday);
			}

			//従業員情報各種を返す
			return emp;

		} finally {
			rs.close();
			ps.close();
			conn.close();
		}
	}

	/**
	 * UPDATEで使用したい従業員情報を各変数へ格納する
	 * @param employee_no
	 * @param shozoku_code
	 * @param name
	 * @param namekana
	 * @param sex
	 * @param age
	 * @param birthday
	 * @return
	 */
	public void setEmployeeForUpdate(
			String employee_no,
			String shozoku_code,
			String name,
			String namekana,
			String sex,
			String age,
			String birthday){

		// 「年齢」未入力をあらわす定数
		final int AGE_EMPTY = -1;

		//変更された値の取得確認
		//変更された値を各変数にセットする
		//社員コード
		this.employee_no = Integer.parseInt(employee_no);
		//Shozokuクラスのオブジェクトを生成
		Shozoku szk = new Shozoku();
		//所属コード
		szk.setShozoku_code(Integer.parseInt(shozoku_code));
		this.shozoku = szk;
		//名前
		this.employee_name = name;
		//名前カナ
		this.employee_namekana = namekana;
		//性別
		this.sex = sex;
		//年齢
		if(age.equals("")) {
			this.age = AGE_EMPTY;
		}else{
			this.age = Integer.parseInt(age);
		}
		//誕生日
		if(birthday.equals("")) {
			this.birthday = null;
		}else {
			String jdbcBirthday = birthday.replace('/', '-');
			this.birthday = Date.valueOf(jdbcBirthday);
		}
	}


	/**
	 * UPDATEのSQLを実行する
	 * @return
	 * @throws SQLException
	 * @throws NamingException
	 */
	public int updateSQL() throws SQLException, NamingException {
		//DBへの接続実施
		Connection conn = null;
		PreparedStatement ps = null;

		//必要な変数
		int update = 0;
		// 「年齢」未入力をあらわす定数
		final int AGE_EMPTY = -1;
		try {
			conn = conectionDB();

			// オートコミットをオフにする
			conn.setAutoCommit(false);


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
			inempUpdate.append(" birthday = ?, ");
			inempUpdate.append(" update_user = ?, ");
			inempUpdate.append(" update_datetime = NOW() ");
			inempUpdate.append(" WHERE employee_no = ?;");
			//文字列をSQLとしてまとめる
			String sql = inempUpdate.toString();
			// PreparedStatementの場合、作成にSQLが必須
			ps = conn.prepareStatement(sql);
			//？に値をセットする、パラメータに値をセット
			ps.setInt(1, this.employee_no);
			ps.setInt(2, this.shozoku.getShozoku_code() );
			ps.setString(3, this.employee_name);
			ps.setString(4, this.employee_namekana);
			ps.setString(5, this.sex);
			if(this.age == AGE_EMPTY) {
				ps.setNull(6, java.sql.Types.INTEGER);
			}else {
				ps.setInt(6, this.age);
			}
			if(this.birthday == null) {
				ps.setNull(7, java.sql.Types.DATE);
			}else {
				ps.setDate(7, this.birthday);
			}

			ps.setString(8, "更新者");	// 将来的にはログインユーザ名

			ps.setInt(9, this.employee_no);
			//実行と同時にupdateに値を入れる
			update = ps.executeUpdate();

			//updateが成功していればコミット失敗した場合はロールバック
			if(update == 1) {
				//コミットする
				conn.commit();
			}

			return update;

		} finally {
			conn.rollback();
			ps.close();
			conn.close();
		}


	}



}
