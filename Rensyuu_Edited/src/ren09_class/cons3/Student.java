package ren09_class.cons3;

/**
 * 生徒
 * ゲッター
 */
class Student{
	//�����o�ϐ�
	private String name;		//���O
	private int math;			//���w�̓��_
	private int chemistry;		//�Ȋw�̓��_
	private int physics;		//�����̓��_

	//�R���X�g���N�^
	public Student(String pName, int pMath, int pChemistry, int pPhysics){
		name = pName;
		math = pMath;
		chemistry = pChemistry;
		physics = pPhysics;
	}
	//�����o�ϐ��̒l�擾���\�b�h
	public String getName(){
		return name;
	}
	public int getMath(){
		return math;
	}

	public int getChemistry(){
		return chemistry;
	}
	public int getPhysics(){
		return physics;
	}
	//���v�l�擾���\�b�h
	public int getTotal(){
		return math + chemistry + physics;
	}
}
