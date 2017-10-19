/**
 * 除算（0除算時のエラーチェック含む）
 */
public class Division{
	public static void main(String[] args){

		if(args.length != 2){
			System.out.println("Usage : java Division <割られる整数> <割る整数>");
		}else{
			try{
				//文字列を数値に変換
				int x = Integer.parseInt(args[0]);
				int y = Integer.parseInt(args[1]);

				//割り算
				int w = x / y;
				System.out.println(args[0] + "を" + args[1] + "で割った値は" + w + "です");
			}catch(ArithmeticException e) {
				System.out.println("割る数に０が指定されています");
			}catch(NumberFormatException e) {
				System.out.println("入力された値が整数化できません");
			}
		}
	}
}
