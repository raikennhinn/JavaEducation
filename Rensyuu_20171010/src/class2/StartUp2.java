package class2;

/**
 * Employeeクラスの利用
 */
public class StartUp2 {

	public static void main(String[] args) {
		Employee data = new Employee();
		//値をセットする
		data.setId(381065);
		data.setName("春川花子");
		data.setSection("システム開発部");
		data.setPhone("1035-1234");
		//画面に表示する
		data.print();
	}

}
