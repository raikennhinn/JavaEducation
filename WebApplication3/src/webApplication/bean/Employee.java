package webApplication.bean;

import java.sql.Date;

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
		return this.birthday.toString().replace('-', '/');
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


}
