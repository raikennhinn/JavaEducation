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
import webApplication.servlet.CSVDataCreation;
import webApplication.util.DataBaseUtility;

/**
 * 従業員関係を操作するクラス
 * @author i1621
 *
 */
public class Employee implements CSVDataCreation{
	//変数
	private int employee_no;		//社員コード
	private String employee_name;	//氏名
	private String employee_namekana; //かな氏名
	private String sex;				//性別
	private int age;				//年齢
	private Date birthday;			//生年月日
	Shozoku shozoku;				//Shozokuクラスの
	private  int pref_CD;			//都道府県コード
	private String prefName;		//都道府県名
	private String address;			//住所
	private String mail_address;	//メールアドレス
	private String note;			//備考欄

	/**
	 *  「年齢」未入力をあらわす定数
	 */
	private final int AGE_EMPTY = -1;
	/**
	 *  「都道府県」未入力をあらわす定数
	 */
	private final int PREFECTURE_EMPTY = -1;

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

//	かな入力の名前と氏名
	public String getEmployee_namekana() {
		return employee_namekana;
	}
	public void setEmployee_namekana(String employee_namekana) {
		this.employee_namekana = employee_namekana;
	}

	public int getPref_CD() {
		return pref_CD;
	}
	public void setPref_CD(int pref_CD) {
		this.pref_CD = pref_CD;
	}


	public String getPrefName() {
		return prefName;
	}
	public void setPrefName(String prefName) {
		this.prefName = prefName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMail_address() {
		return mail_address;
	}
	public void setMail_address(String mail_address) {
		this.mail_address = mail_address;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	 * EmployeeUpdateInitServlet
	 * 未使用
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
			sb.append(" birthday, ");
			sb.append(" pref_CD, ");
			sb.append(" address, ");
			sb.append(" mail_address, ");
			sb.append(" note ");
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
				emp.setSex(Integer.toString(sex));

				//年齢
				int age = rs.getInt("age");
				emp.setAge(age);

				//生年月日
				Date birthday = rs.getDate("birthday");
				emp.setBirthday(birthday);

				//都道府県
				int pref_cd = rs.getInt("pref_CD");
				emp.setPref_CD(pref_cd);
				//住所
				String address = rs.getString("address");
				emp.setAddress(address);
				//メールアドレス
				String mail_address = rs.getString("mail_address");
				emp.setMail_address(mail_address);
				//備考
				String note = rs.getString("note");
				emp.setNote(note);
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
			String birthday,
			String prefecture,
			String address,
			String mail_address,
			String note){

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
		//都道府県
		if(prefecture.equals("")) {
			this.pref_CD = PREFECTURE_EMPTY;
		} else {
			this.pref_CD = Integer.parseInt(prefecture);
		}
		//住所
		this.address = address;
		//メールアドレス
		this.mail_address = mail_address;
		//備考
		this.note = note;
	}


	/**
	 * UPDATEのSQLを実行する
	 * @return
	 * @throws SQLException
	 * @throws NamingException
	 */
	public int updateSQL(String admin) throws SQLException, NamingException {
		//DBへの接続実施
		Connection conn = null;
		PreparedStatement ps = null;

		//必要な変数
		int update = 0;

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
			inempUpdate.append(" pref_CD = ?, ");
			inempUpdate.append(" address = ?, ");
			inempUpdate.append(" mail_address = ?, ");
			inempUpdate.append(" note = ?, ");
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
			if(this.pref_CD == PREFECTURE_EMPTY) {
				ps.setNull(8, java.sql.Types.INTEGER);
			}else {
				ps.setInt(8, this.pref_CD);
			}
			ps.setString(9, this.address);
			ps.setString(10, this.mail_address);
			ps.setString(11, this.note);

			ps.setString(12, admin);	// 将来的にはログインユーザ名

			ps.setInt(13, this.employee_no);
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
			sb.append(" birthday, ");
			sb.append(" pref_CD, ");
			sb.append(" address, ");
			sb.append(" mail_address, ");
			sb.append(" note)");
			sb.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? );");
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
			if(this.pref_CD == PREFECTURE_EMPTY) {
				ps.setNull(8, java.sql.Types.INTEGER);
			}else {
				ps.setInt(8, this.pref_CD);
			}
			ps.setString(9, this.address);
			ps.setString(10, this.mail_address);
			ps.setString(11, this.note);

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
	/**
	 * 絞り込み検索の実施
	 * @param employeeNO
	 * @param ShzokuCode
	 * @param name
	 * @param prefCode
	 * @param address
	 * @param logger
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Employee> Search(
			int employeeNO,int ShzokuCode,String name,int prefCode,String address,Logger logger)
			throws Exception {
		//値を返すArrayListの作成
		ArrayList<Employee> empList = new ArrayList<Employee>();

		logger.info("検索を行っています");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DataBaseUtility.conectionDB();
			//SQL文の生成
			StringBuilder sb = new StringBuilder();

			sb.append(" SELECT ");
			sb.append(" e.employee_no, ");
			sb.append(" e.shozoku_code, ");
			sb.append(" e.employee_name, ");
			sb.append(" e.employee_name_kana, ");
			sb.append(" e.sex, ");
			sb.append(" e.age, ");
			sb.append(" e.birthday, ");
			sb.append(" e.pref_CD, ");
			sb.append(" e.address, ");
			sb.append(" e.mail_address, ");
			sb.append(" e.note, ");
			sb.append(" CONCAT( ");
			sb.append(" 	 sh.shozoku_bu, ");
			sb.append("  	 CASE sh.shozoku_ka ");
			sb.append(" 		 When '（なし）' Then '' ");
			sb.append("  		 Else sh.shozoku_ka ");
			sb.append(" 		 END, ");
			sb.append(" 	 CASE sh.shozoku_kakari ");
			sb.append("  		 When '（なし）' Then '' ");
			sb.append("  		 Else sh.shozoku_kakari ");
			sb.append("  		 END ");
			sb.append("  		 ) as shozoku_name ");
			sb.append(" FROM employee e ");
			sb.append(" JOIN shozoku sh ");
			sb.append(" on e.shozoku_code = sh.shozoku_code ");
			//検索条件を入れていく。引数にnullもしくわ0が入っていた場合は、検索条件から除外する
			//coutでifを通った行の値（通った属性名）を保持。
			ArrayList<String> SerchName = new ArrayList<String>();
			if(employeeNO != 0 || ShzokuCode != 0 || !name.equals("") || prefCode != 0 || !address.equals("")) {
				sb.append(" WHERE ");
			}
			if(employeeNO != 0) {
				sb.append(" employee_no = ? ");
				SerchName.add("従業員No");
			}
			if(ShzokuCode != 0) {
				if(employeeNO != 0) {
					sb.append(" AND ");
				}
				sb.append(" e.shozoku_code = ?  ");
				SerchName.add("所属コード");
			}
			if(!name.equals("")) {
				if(employeeNO !=0 || ShzokuCode != 0) {
					sb.append(" AND ");
				}
				sb.append(" e.employee_name LIKE  ? ");
				SerchName.add("氏名");
			}
			if(prefCode != 0) {
				if(employeeNO !=0 || ShzokuCode != 0 || !name.equals("")) {
					sb.append(" AND ");
				}
				sb.append(" e.pref_CD = ? ");
				SerchName.add("都道府県コード");
			}
			if(!address.equals("")) {
				if(employeeNO !=0 || ShzokuCode != 0 || !name.equals("")|| prefCode != 0) {
					sb.append(" AND ");
				}
				sb.append(" e.address LIKE ? ");
				SerchName.add("住所");
			}
			sb.append(" ORDER BY employee_no; ");

			//文字列へ
			String serch = sb.toString();
			//？に取得してきた従業員Noをセットし、実行
			ps = conn.prepareStatement(serch);

			//for でカウントした数でps.set●●を追加してもよいが、データ型が異なるので、その部分をどうするのか
			for(int i =0; i < SerchName.size() ; i++ ) {
				if(SerchName.get(i).equals("従業員No")) {
					ps.setInt(i+1, employeeNO);
				}
				if(SerchName.get(i).equals("所属コード")) {
					ps.setInt(i+1, ShzokuCode);
				}
				if(SerchName.get(i).equals("氏名")) {
					ps.setString(i+1, "%"+name+"%");
				}
				if(SerchName.get(i).equals("都道府県コード")) {
					ps.setInt(i+1, prefCode);
				}
				if(SerchName.get(i).equals("住所")) {
					ps.setString(i+1, "%"+address+"%");
				}
			}

			logger.debug("実行SQL："+serch);
			rs = ps.executeQuery();

			while(rs.next()) {
				Employee emp = new Employee();					//Employeeクラスのオブジェクトを生成
				Shozoku szk = new Shozoku();					//Shozokuクラスのオブジェクトを生成

				int code = rs.getInt("employee_no");				//社員コード
				emp.setEmployee_no(code);						//Employee にセット


				String setname = rs.getString("employee_name");	//氏名
				emp.setEmployee_name(setname);

				int shozoku = rs.getInt("shozoku_code");			//ここでemployeeクラスから所属名を取り出す必要
				String shozokuName = rs.getString("shozoku_name");

				// ここで所属コードをszkにセット
				szk.setShozoku_code(shozoku);
				szk.setShozoku_name(shozokuName);
//				emp.setShozoku_code(szk.getShozoku_code());		//所属コードを両クラス同じにする
				emp.setShozoku(szk);


				int sex = rs.getInt("sex");					//性別
				//性別の結び付け(数を文字へ)を実施する
				Seibetu sei = Seibetu.getSeibetu(sex);
				String sexStr = sei.getName();								//性別が格納されている
				emp.setSex(sexStr);


				int age = rs.getInt("age");							//年齢
				emp.setAge(age);

				Date birthday = rs.getDate("birthday");				//生年月日
				emp.setBirthday(birthday);

				//追加分
				emp.setPref_CD(rs.getInt("pref_CD"));			//都道府県コード

				emp.setAddress(rs.getString("address"));		//住所

				emp.setMail_address(rs.getString("mail_address")); //メールアドレス

				emp.setNote(rs.getString("note"));				//備考欄

				empList.add(emp);
			}
			//値の入ったリストを返す
			return empList;

		}finally {
				rs.close();
				ps.close();
				conn.close();
			}

	}

	/**
	 * CSV出力を実行するために、1行ずつの列をまとめて実行する。
	 */
	public String getCSVLine() {
		//入れた文字列を連結させてサーブレットへ文字列として返す。
		String LineData;

		StringBuilder sb = new StringBuilder();

		sb.append("\"");
		sb.append( this.employee_no );
		sb.append("\"");
		sb.append(",");
		sb.append("\"");
		sb.append( this.shozoku.getShozoku_code() );
		sb.append(":");
		sb.append( this.shozoku.getShozoku_name() );
		sb.append("\"");
		sb.append(",");
		sb.append("\"");
		sb.append( this.employee_name );
		sb.append("\"");
		sb.append(",");
		sb.append("\"");
		sb.append( this.sex );
		sb.append("\"");
		sb.append(",");
		sb.append("\"");
		sb.append( this.age );
		sb.append("\"");
		sb.append(",");
		sb.append("\"");
		sb.append( this.birthday );
		sb.append("\"");
		sb.append(",");
		sb.append("\"");
		sb.append( this.PREFECTURE_EMPTY );
		sb.append("\"");
		sb.append(",");
		sb.append("\"");
		sb.append(this.address);
		sb.append("\"");
		sb.append(",");
		sb.append("\"");
		sb.append(this.mail_address);
		sb.append("\"");
		sb.append(",");
		sb.append("\"");
		sb.append(this.note);
		sb.append("\"");
		sb.append("\n");

		LineData =  sb.toString();

		return LineData;
	}


	/**
	 * ・クラスメソッドとして、Employeeのリストのソートメソッドを実装する
	 *  用いてCollections.sort()を実行する。
	 * @param employeeList
	 * @param category
	 * @param b
	 */
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
/**
 * 【項目別Comparatorクラス】
 * 従業員Noの昇順
 * @author i1621
 *
 */
class ComparatorUpEmployee_no implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		Employee empNo1 = o1;
		Employee empNo2 = o2;

		return empNo1.getEmployee_no() - empNo2.getEmployee_no();
	}

}
/**
 * 【項目別Comparatorクラス】
 * 従業員Noの降順
 * @author i1621
 *
 */
class ComparatorDownEmployee_no implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		Employee empNo1 = o1;
		Employee empNo2 = o2;

		return -(empNo1.getEmployee_no() - empNo2.getEmployee_no());
	}

}
/**
 * 【項目別Comparatorクラス】
 * 所属コードの昇順
 * @author i1621
 *
 */
class ComparatorUpShozokuName implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		Employee empNo1 = o1;
		Employee empNo2 = o2;

		return empNo1.getShozoku().getShozoku_code() - empNo2.getShozoku().getShozoku_code();
	}

}
/**
 * 【項目別Comparatorクラス】
 * 所属コードの降順
 * @author i1621
 */
class ComparatorDownShozokuName implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		Employee empNo1 = o1;
		Employee empNo2 = o2;

		return -(empNo1.getShozoku().getShozoku_code() - empNo2.getShozoku().getShozoku_code());
	}

}
/**
 * 【項目別Comparatorクラス】
 * 氏名の昇順
 * @author i1621
 *
 */

class ComparatorUpName implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		Employee empNo1 = o1;
		Employee empNo2 = o2;

		return empNo1.getEmployee_name().compareTo(empNo2.getEmployee_name());
	}

}
/**
 * 【項目別Comparatorクラス】
 * 氏名の降順
 * @author i1621
 *
 */
class ComparatorDownName implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		Employee empNo1 = o1;
		Employee empNo2 = o2;

		return -(empNo1.getEmployee_name().compareTo(empNo2.getEmployee_name()));
	}
}
/**
 * 【項目別Comparatorクラス】
 * 性別の昇順
 * @author i1621
 *
 */
class ComparatorUpSex implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		Employee empNo1 = o1;
		Employee empNo2 = o2;

		return empNo1.getSex().compareTo(empNo2.getSex());
	}

}
/**
 * 【項目別Comparatorクラス】
 * 性別の降順
 * @author i1621
 *
 */
class ComparatorDownSex implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		Employee empNo1 = o1;
		Employee empNo2 = o2;

		return -(empNo1.getSex().compareTo(empNo2.getSex()));
	}

}
/**
 * 【項目別Comparatorクラス】
 * 年齢の昇順
 * @author i1621
 *
 */
class ComparatorUpaAge implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		Employee empNo1 = o1;
		Employee empNo2 = o2;

		return empNo1.getAge() - empNo2.getAge();
	}

}
/**
 * 【項目別Comparatorクラス】
 * 年齢の降順
 * @author i1621
 *
 */
class ComparatorDownaAge implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		Employee empNo1 = o1;
		Employee empNo2 = o2;

		return -(empNo1.getAge() - empNo2.getAge());
	}

}
/**
 * 【項目別Comparatorクラス】
 * 誕生日の昇順
 * @author i1621
 *
 */
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
/**
 * 【項目別Comparatorクラス】
 * 誕生日の降順
 * @author i1621
 *
 */
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

