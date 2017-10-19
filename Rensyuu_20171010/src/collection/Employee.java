package collection;

public class Employee{
	//�����o�ϐ�
	private int id;					//ID
	private String name;			//���O
	private String section;			//����
	private String phone;			//����

	//�R���X�g���N�^(�����S��)
	public Employee(int id, String name, String section, String phone){
		this.id      = id;
		this.name    = name;
		this.section = section;
		this.phone   = phone;
	}

	//�\�����\�b�h
	public void print(){
		System.out.println("ID     :" + id);
		System.out.println("NAME   :" + name);
		System.out.println("SECTION:" + section);
		System.out.println("PHONE  :" + phone);
	}
}