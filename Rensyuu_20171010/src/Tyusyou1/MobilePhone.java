package Tyusyou1;

public class MobilePhone extends Phone {

	/**
	 * callのオーバーライド
	 */
	@Override
	public void call(String number) {
		if(number == null) {
			System.out.println("おつなぎできません");
		}else {
			System.out.println("携帯電話で通話中です");
		}
	}

	/**
	 * shotメソッドの定義
	 */
	public void shot() {
		System.out.println("写真を撮りました");
	}



}
