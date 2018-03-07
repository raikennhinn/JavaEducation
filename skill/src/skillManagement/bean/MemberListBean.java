package skillManagement.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * メンバー一覧のBeanクラス
 */
public class MemberListBean {

	/*
	 * ------------------------------------
	 * メンバ変数
	 * ------------------------------------
	 */

	/**
	 * 社員番号
	 */
	private String employeeNumber;

	/**
	 * 所属区分（名称）
	 */
	private String affiliationKubunName;

	/**
	 * 氏名
	 */
	private String name;

	/**
	 * 基本情報記入状態区分
	 */
	private int basicFillInStatus;


	/*
	 * ------------------------------------
	 * コンストラクタ
	 * ------------------------------------
	 */

	/**
	 * デフォルト（引数無し）コンストラクタ
	 */
	public MemberListBean(){

	}

	/**
	 * コンストラクタ（生成時に値をすべてセットする）
	 * @param employeeNumber 社員番号
	 * @param affiliationKubunName 所属区分（名称）
	 * @param name 名前
	 * @param basicFillInStatus 基本情報記入状態区分
	 *
	 */
	public MemberListBean(String employeeNumber, String affiliationKubunName, String name, int basicFillInStatus){
		this.employeeNumber = employeeNumber;
		this.affiliationKubunName = affiliationKubunName;
		this.name = name;
		this.basicFillInStatus = basicFillInStatus;
	}

	/**
	 * メンバー一覧データをリストで取得する
	 * @return
	 * @throws SQLException
	 * @throws NamingException
	 *
	 */
	public static ArrayList<MemberListBean> getMemberList() throws SQLException, NamingException {
		//存在チェック
		Context context;
		ArrayList<MemberListBean> list = new ArrayList<MemberListBean>();

		context = new InitialContext();
		//データベースへの接続
		DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/skill_management");

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT");
		sb.append(" b.employee_number ");
		sb.append(" , k.kubun_name AS affiliation_kubun_name ");
		sb.append(" , b.name ");
		sb.append(" , b.basic_fill_in_status ");
		sb.append("FROM");
		sb.append(" skill_management.member_basic AS b ");
		sb.append("JOIN");
		sb.append(" skill_management.m_kubun AS k ");
		sb.append("ON");
		sb.append(" b.affiliation_kubun = k.kubun_value ");
		sb.append("WHERE");
		sb.append(" k.kubun_category = ? ");
		sb.append("ORDER BY");
		sb.append(" employee_number ");

		//DBからレコードを抽出
		try(Connection db = ds.getConnection();
				PreparedStatement ps = db.prepareStatement(sb.toString());
				) {

			//プレースホルダーに対して、値を設定
			ps.setString(1, "AFFILIATION");

			//SQL実行
			try(ResultSet rs = ps.executeQuery();) {
				//ResultSet取得
				while(rs.next()){
					MemberListBean bean = new MemberListBean();

					bean.setEmployeeNumber(rs.getString("employee_number"));
					bean.setAffiliationKubunName(rs.getString("affiliation_kubun_name"));
					bean.setName(rs.getString("name"));
					bean.setBasicFillInStatus(rs.getInt("basic_fill_in_status"));

					list.add(bean);
				}
			}
		}
		return list;
	}

	/*
	 * ------------------------------------
	 * ゲッター／セッター
	 * ------------------------------------
	 */

	/**
	 * 社員番号を返す。
	 * @return employeeNumber
	 */
	public String getEmployeeNumber() {
		return employeeNumber;
	}

	/**
	 * 社員番号を設定する
	 * @param employeeNumber
	 */
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	/**
	 * 所属区分（名称）を返す。
	 * @return affiliationKubunName
	 */
	public String getAffiliationKubunName() {
		return affiliationKubunName;
	}

	/**
	 * 所属区分（名称）を設定する
	 * @param affiliationKubunNname
	 */
	public void setAffiliationKubunName(String affiliationKubunName) {
		this.affiliationKubunName = affiliationKubunName;
	}

	/**
	 * 氏名を返す。
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 氏名を設定する
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 基本情報記入状態区分を返す。
	 * @param basicFillInStatus
	 */
	public int getBasicFillInStatus() {
		return basicFillInStatus;
	}

	/**
	 * 基本情報記入状態区分を設定する
	 * @param basicFillInStatus
	 */
	public void setBasicFillInStatus(int basicFillInStatus) {
		this.basicFillInStatus = basicFillInStatus;
	}

}
