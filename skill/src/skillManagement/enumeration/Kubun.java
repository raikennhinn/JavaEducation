package skillManagement.enumeration;

/**
 * すべてのカテゴリの区分値を表す列挙型
 */
public enum Kubun {
	/**
	 * AUTHORITY_NORMAL：権限区分 通常権限
	 */
	AUTHORITY_NORMAL(1),

	/**
	 * AUTHORITY_ADMIN：権限区分 管理者権限
	 */
	AUTHORITY_ADMIN(9);

	private final int kubun_value;
	Kubun(int kubun_value){
		this.kubun_value = kubun_value;
	}

	public int getKubun_value(){
		return this.kubun_value;
	}
}
