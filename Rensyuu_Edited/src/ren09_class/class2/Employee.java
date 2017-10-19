package ren09_class.class2;

/**
 * 従業員
 * カプセル化
 */
class Employee{
	private int id;				//ID
	private String name;		//名前
	private String section;		//セクション
	private String phone;		//電話番号

		//�l(ID)�̐ݒ�
	public void setId(int pId){
		id = pId;
	}
		//�l(���O)�̐ݒ�
	public void setName(String pName){
		name = pName;
	}
		//�l(����)�̐ݒ�
	public void setSection(String pSection){
		section = pSection;
	}
		//�l(����)�̐ݒ�
	public void setPhone(String pPhone){
		phone = pPhone;
	}
		//�l(ID)�
	public int getId(){
		return id;
	}
		//�l(���O)�̎擾
	public String getName(){
		return name;
	}
		//�l(����)�̎擾
	public String getSection(){
		return section;
	}
		//�l(����)�̎擾
	public String getPhone(){
		return phone;
	}

	//�\�����\�b�h
	public void print(){
		System.out.println("ID   	:" + id);
		System.out.println("NAME	:" + name);
		System.out.println("SECTION	:" + section);
		System.out.println("PHONE	:" + phone);


	}
}