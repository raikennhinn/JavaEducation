package test1;

public class Shozoku {
	private int shozoku_code;	//所属コード
	private String shozoku_name; //所属名

	//所属コードのゲッターとセッター
	public int getShozoku_code() {
		return shozoku_code;
	}
	public void setShozoku_code(int shozoku_code) {
		this.shozoku_code = shozoku_code;
	}
	//所属名のゲッターとセッター
	public String getShozoku_name() {
		return shozoku_name;
	}
	public void setShozoku_name(String shozoku_name) {
		this.shozoku_name = shozoku_name;
	}

	//pritShozoku()の作成
	public String pritShozoku(){
		return "所属コード：" + shozoku_code + " 所属名；" + shozoku_name;
	}



}
