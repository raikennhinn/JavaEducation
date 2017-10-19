package ren09_class.cons4;

/**
 * クラス型の配列
 */
public class StartUp {

	public static void main(String[] args) {
		//Studentオブジェクト生成
//		Student[] stu = new Student[5];
//		stu[0] =new Student("a",12,2,53);

		Student[] stu = {
				new Student("a",12,2,53),
				new Student("b",12,20,53),
				new Student("c",120,2,53)
		};

		int total = 0;

		//合計点の計算、表示
		for(int i =0; i<stu.length ;i++) {
			System.out.println(stu[i].getName()+"==> "+stu[i].getMath()+":"+stu[i].getChemistry()+":"+stu[i].getPhysics()+" ="+stu[i].getTotal());
			total = total + stu[i].getTotal();
		}
		// 平均点の計算、表示
		System.out.println("平均：" + (total / stu.length) );
	}

}
