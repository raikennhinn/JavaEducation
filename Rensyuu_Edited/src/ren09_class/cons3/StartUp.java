package ren09_class.cons3;

/**
 * Studentクラスの利用
 */
public class StartUp {

	public static void main(String[] args) {
		//Studentオブジェクト生成
		Student stu = new Student("山本",77,65,80);

		//合計点の計算、表示
		System.out.println(stu.getName()+"==> "+stu.getMath()+":"+stu.getChemistry()+":"+stu.getPhysics()+" ="+stu.getTotal());
	}

}
