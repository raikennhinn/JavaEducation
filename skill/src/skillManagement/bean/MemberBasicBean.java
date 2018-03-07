package skillManagement.bean;

/**
 * XXX 全体的にJavaDocコメントを書くこと（クラス、変数、コンストラクタ、セッタゲッタ、ほかメソッドを追加したら）<br>
 * <br>
 * メンバー基本情報のBeanクラス
 */
public class MemberBasicBean {

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
	 * 所属区分
	 */
	private int affiliationKubun;
	/**
	 * 所属区分（名称）
	 */
	private String affiliationKubunName;
	/**
	 * 氏名
	 */
	private String name;
	/**
	 * 権限区分
	 */
	private int authKubun;
	/**
	 * 権限区分（名称）
	 */
	private String authKubunName;

	/*
	 * ------------------------------------
	 * コンストラクタ
	 * ------------------------------------
	 */

	/**
	 * デフォルト（引数無し）コンストラクタ
	 */
	public MemberBasicBean(){

	}

	/**
	 * コンストラクタ（生成時に値をすべてセットする）
	 * @param employeeNumber 社員番号
	 */
	public MemberBasicBean	(String employeeNumber){
		this.employeeNumber = employeeNumber;
	}

	/**
	 * コンストラクタ（生成時に値をすべてセットする）
	 * @param employeeNumber 社員番号
	 * @param affiliationKubun 所属区分
	 * @param affiliationKubunName 所属区分（名称）
	 * @param name 氏名
	 * @param authKubun 権限区分
	 * @param authKubunName 権限区分（名称）
	 */
	public MemberBasicBean
	(String employeeNumber,int affiliationKubun,
			String affiliationKubunName,String name,int authKubun,String authKubunName){
		this.employeeNumber = employeeNumber;
		this.affiliationKubun = affiliationKubun;
		this.affiliationKubunName = affiliationKubunName;
		this.name = name;
		this.authKubun = authKubun;
		this.authKubunName = authKubunName;
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
	 * 所属区分を返す。
	 * @return affiliationKubun
	 */
	public int getAffiliationKubun() {
		return affiliationKubun;
	}

	/**
	 * 所属区分を設定する
	 * @param affiliationKubun
	 */
	public void setAffiliationKubun(int affiliationKubun) {
		this.affiliationKubun = affiliationKubun;
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
	 * 権限区分を返す。
	 * @return authKubun
	 */
	public int getAuthKubun() {
		return authKubun;
	}

	/**
	 * 権限区分を設定する
	 * @param authKubun
	 */
	public void setAuthKubun(int authKubun) {
		this.authKubun = authKubun;
	}

	/**
	 * 権限区分（名称）を返す。
	 * @return authKubunName
	 */
	public String getAuthKubunName() {
		return authKubunName;
	}

	/**
	 * 権限区分（名称）を設定する
	 * @param authKubunName
	 */
	public void setAuthKubunName(String authKubunName) {
		this.authKubunName = authKubunName;
	}


}
