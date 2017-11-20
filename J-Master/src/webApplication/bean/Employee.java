package webApplication.bean;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import webApplication.enumeration.Seibetu;
import webApplication.util.DataBaseUtility;

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
	 * DBより従業員No.指定で情報を取得し、自分自身にセットする
	 * @param employee_no
	 * @throws SQLException
	 * @throws NamingException
	 */
	public void setEmployeeDataByDB(String employee_no, Logger logger) throws SQLException, NamingException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//DBへの接続実施
			conn = DataBaseUtility.conectionDB();

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

			logger.debug("従業員情報単一検索SQL：" + select);

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
//				Seibetu sei = Seibetu.getSeibetu(sex);
//				String sexStr = sei.getName();					//性別が格納されている
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
	public static Employee getEmployeeData(String employee_no, Logger logger) throws SQLException, NamingException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//DBへの接続実施
			conn = DataBaseUtility.conectionDB();

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
			logger.debug("従業員情報単一検索SQL：" + select);

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
//				Seibetu sei = Seibetu.getSeibetu(sex);
//				String sexStr = sei.getName();					//性別が格納されている
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
	 * 使用したい従業員情報を各変数へ格納する
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
			conn = DataBaseUtility.conectionDB();

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


	/**
	 * 従業員Ｎｏが存在するかどうか確認を行うメソッド
	 * 存在しない場合はfalse,正しい場合はtrueを返す
	 * @throws NamingException
	 * @throws SQLException
	 */
	public static boolean employeeNoCheck(int emp_no) throws SQLException, NamingException {
		//DBへの接続実施
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try{
			conn = DataBaseUtility.conectionDB();
			// 従業員の存在チェック
			// 従業員テーブルにすでに存在する従業員No.が入力されていないこと
			StringBuilder stb = new StringBuilder();
			stb.append("SELECT ");
			stb.append("employee_no ");
			stb.append("FROM employee ");
			stb.append("WHERE employee_no = ? ");	// SQLに条件をつけて照合させたほうがよい
			stb.append("ORDER BY employee_no;");
			String select = stb.toString();
			//ＳＱＬ文の形成
			ps = conn.prepareStatement(select);
			ps.setInt(1, emp_no);
			rs = ps.executeQuery();
			//存在している場合はtrue
			if(rs.next() == true) {
				return true;
			}
			return false;

		} finally {
			rs.close();
			ps.close();
			conn.close();
		}
	}


	/**
	 * 社員の登録を実行（insert）の実行
	 * @throws NamingException
	 * @throws SQLException
	 */
	public int insertSQL() throws SQLException, NamingException {

		//DBに接続を実施
		//DBへの接続実施
		Connection conn = null;
		PreparedStatement ps = null;

		//必要な変数
		int insert = 0;
		// 「年齢」未入力をあらわす定数
		final int AGE_EMPTY = -1;


		try{

			conn = DataBaseUtility.conectionDB();

			// オートコミットをオフにする
			conn.setAutoCommit(false);

			//社員登録を実行する（Ｅｍｐｌｏｙｅｅに移す）
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT into employee");
			sb.append(" (employee_no, ");
			sb.append(" shozoku_code, ");
			sb.append(" employee_name, ");
			sb.append(" employee_name_kana, ");
			sb.append(" sex, ");
			sb.append(" age, ");
			sb.append(" birthday)");
			sb.append(" values ( ?, ?, ?, ?, ?, ?, ? );");
			//ＳＱＬの作成
			String sql = sb.toString();
			// PreparedStatementの場合、作成にSQLが必須
			ps = conn.prepareStatement(sql);
			//パラメータに値をセット
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

			//実行と同時にupdateに値を入れる
			insert = ps.executeUpdate();

			//insetが成功していればコミット失敗した場合はロールバック
			if(insert == 1) {
				//コミットする
				conn.commit();
			}

			return insert;

		} finally {
			conn.rollback();
			ps.close();
			conn.close();
		}
	}

	public int deleteSQL(String number) throws SQLException, NamingException {
		int delete = 0;

		//DBに接続を実施
		//DBへの接続実施
		Connection conn = null;
		PreparedStatement ps = null;

		try{
			conn = DataBaseUtility.conectionDB();
			// オートコミットをオフにする
			conn.setAutoCommit(false);

			//社員削除を実行するSQL文の作成
			StringBuilder sb = new StringBuilder();
			sb.append(" DELETE ");
			sb.append(" FROM employee ");
			sb.append(" WHERE employee_no = ? ; ");
			//ＳＱＬの作成
			String sql = sb.toString();
			// PreparedStatementの場合、作成にSQLが必須
			ps = conn.prepareStatement(sql);
			//?に値を入れる
			ps.setString(1, number);
			//SQL実行をおこなう
			delete = ps.executeUpdate();

			//削除が成功していればコミット失敗した場合はロールバック
			if(delete == 1) {
				//コミットする
				conn.commit();
			}

			return delete;


		}finally {
			conn.rollback();
			ps.close();
			conn.close();
		}
	}

	/**
	 * 所属コードを元に従業員情報を返す
	 * @throws NamingException
	 * @throws SQLException
	 */
	public static ArrayList<Employee> getEmployeeShozokuCode(int shozokuCode, Logger logger) throws SQLException, NamingException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//DBへの接続実施
			conn = DataBaseUtility.conectionDB();

			//SQLで所属コードを条件とし情報の取得を実施
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT employee_no, ");
			sb.append(" shozoku_code, ");
			sb.append(" employee_name ");
			sb.append(" FROM employee ");
			sb.append(" WHERE shozoku_code = ? ");

			//文字列へ
			String select = sb.toString();

			logger.debug("従業員情報所属単位の検索SQL：" + select);

			//？に取得してきた従業員Noをセットし、実行
			ps = conn.prepareStatement(select);
			ps.setInt(1,shozokuCode);
			rs = ps.executeQuery();

			ArrayList<Employee> empList = new ArrayList<Employee>();

			while(rs.next()) {
				//employeeオブジェクトの作成
				Employee emp = new Employee();
				//従業員No のセット
				emp.setEmployee_no(rs.getInt("employee_no"));

				//Shozokuオブジェクトを作成
				Shozoku szk = new Shozoku();
				//所属コードをemployeeオブジェクトを介して所属オブジェクトにつめる
				szk.setShozoku_code(shozokuCode);
				emp.setShozoku(szk);	//empのShozokuにセット

				//氏名
				emp.setEmployee_name(rs.getString("employee_name"));

				empList.add(emp);

			}

			//従業員情報各種を返す
			return empList;

		} finally {
			rs.close();
			ps.close();
			conn.close();
		}
	}

	//	【Employeeクラス】
	//	・クラスメソッドとして、Employeeのリストのソートメソッドを実装する
	//  用いてCollections.sort()を実行する。
	public static void EmployeeSort(List<Employee> employeeList,String category,int b) {
		//aは項目名、ｂは昇順か降順か。名前変更
		//サーブレットからパラメータで受け取ったソート項目・昇順or降順にしたがって、必要なComparatorクラスを
		//Comparetorの識別、
		//b →trueが昇順、falseが降順
		Comparator<Employee> comp= null;
		switch (category){
			case "employeeNo":
				if(b == 1) {
					comp = new ComparatorUpEmployee_no();
				}else if(b ==2){
					comp = new ComparatorDownEmployee_no();
				}
				break;
			case "shozokuName":
				if(b == 1) {
					comp = new ComparatorUpShozokuName();
				}else if(b == 2){
					comp = new ComparatorDownShozokuName();
				}
				break;
			case "name":
				if(b == 1) {
					comp = new ComparatorUpName();
				}else if(b == 2){
					comp = new ComparatorDownName();
				}
				break;
			case "sex":
				if(b == 1) {
					comp = new ComparatorUpSex();
				}else if(b == 2){
					comp = new ComparatorDownSex();
				}
				break;
			case "age":
				if(b == 1) {
					comp = new ComparatorUpaAge();
				}else if(b == 2){
					comp = new ComparatorDownaAge();
				}
				break;
			case "birthday":
				if(b == 1) {
					comp = new ComparatorUpBirthday();
				}else if(b == 2){
					comp = new ComparatorDownBirthday();
				}
				break;
		}

		Collections.sort(employeeList, comp);
	}
}

//【項目別Comparatorクラス】
//	・項目ごとの昇順・降順Comparatorクラスを実装し、complareToメソッドをオーバーライドする
//	　これらのクラスはEmployeeクラスで使用する
//	　　数値項目：数値の大小
//	　　日時項目：日時の大小
//	　　文字列項目：StringのcompareTo()メソッドをそのまま利用する
//employeeNo
//【項目別Comparatorクラス】
//	・項目ごとの昇順・降順Comparatorクラスを実装し、complareToメソッドをオーバーライドする
//	　これらのクラスはEmployeeクラスで使用する
//	　　数値項目：数値の大小
//	　　日時項目：日時の大小
//	　　文字列項目：StringのcompareTo()メソッドをそのまま利用する
//employeeNo
class ComparatorUpEmployee_no implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		Employee empNo1 = o1;
		Employee empNo2 = o2;

		return empNo1.getEmployee_no() - empNo2.getEmployee_no();
	}

}

class ComparatorDownEmployee_no implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		Employee empNo1 = o1;
		Employee empNo2 = o2;

		return -(empNo1.getEmployee_no() - empNo2.getEmployee_no());
	}

}
//shozokuName
class ComparatorUpShozokuName implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		Employee empNo1 = o1;
		Employee empNo2 = o2;

		return empNo1.getShozoku().getShozoku_code() - empNo2.getShozoku().getShozoku_code();
	}

}
class ComparatorDownShozokuName implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		Employee empNo1 = o1;
		Employee empNo2 = o2;

		return -(empNo1.getShozoku().getShozoku_code() - empNo2.getShozoku().getShozoku_code());
	}

}
//name
class ComparatorUpName implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		Employee empNo1 = o1;
		Employee empNo2 = o2;

		return empNo1.getEmployee_name().compareTo(empNo2.getEmployee_name());
	}

}
class ComparatorDownName implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		Employee empNo1 = o1;
		Employee empNo2 = o2;

		return -(empNo1.getEmployee_name().compareTo(empNo2.getEmployee_name()));
	}
}
//sex
class ComparatorUpSex implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		Employee empNo1 = o1;
		Employee empNo2 = o2;

		return empNo1.getSex().compareTo(empNo2.getSex());
	}

}
class ComparatorDownSex implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		Employee empNo1 = o1;
		Employee empNo2 = o2;

		return -(empNo1.getSex().compareTo(empNo2.getSex()));
	}

}
//age
class ComparatorUpaAge implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		Employee empNo1 = o1;
		Employee empNo2 = o2;

		return empNo1.getAge() - empNo2.getAge();
	}

}
class ComparatorDownaAge implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		Employee empNo1 = o1;
		Employee empNo2 = o2;

		return -(empNo1.getAge() - empNo2.getAge());
	}

}
//birthday
class ComparatorUpBirthday implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		Employee empNo1 = o1;
		Employee empNo2 = o2;

		//nullの場合処理が通らないので、ありえない最小値を入れて、比較を行う
		Calendar cal= Calendar.getInstance();
		cal.set(1000,1-1,1);
		Date chage =new Date(cal.getTimeInMillis());

		if(empNo1.getBirthday() == null) {
			empNo1.setBirthday(chage);
		}
		if(empNo2.getBirthday() == null) {
			empNo2.setBirthday(chage);
		}


		return empNo1.getBirthday().compareTo(empNo2.getBirthday());
	}

}

class ComparatorDownBirthday implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		Employee empNo1 = o1;
		Employee empNo2 = o2;

		//nullの場合処理が通らないので、ありえない最小値を入れて、比較を行う
		Calendar cal= Calendar.getInstance();
		cal.set(1000,1-1,1);
		Date chage =new Date(cal.getTimeInMillis());

		if(empNo1.getBirthday() == null) {
			empNo1.setBirthday(chage);
		}
		if(empNo2.getBirthday() == null) {
			empNo2.setBirthday(chage);
		}

		return -(empNo1.getBirthday().compareTo(empNo2.getBirthday()));
	}
}

