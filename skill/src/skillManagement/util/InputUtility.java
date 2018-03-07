package skillManagement.util;

/**
 * 画面入力を取り扱うユーティリティクラス
 *
 */
public class InputUtility {

	/**
	 * 指定された文字列が、半角英数字(記号含む)か否かを返します。
	 *
	 * @param value 処理対象となる文字列
	 * @return true:半角英数字である(もしくは対象文字がない), false:半角英数字でない
	 */
	public static Boolean isHalfWidthAlphanumeric(String value) {

		// nullか空文字ならなにもしない（trueを返す）
	    if ( value == null || value.length() == 0 ) {
	        return true;
	    }

	    // 文字の長さとバイト数が一致しない場合はfalse（半角でない）
	    int len = value.length(); // 文字長
	    byte[] bytes = value.getBytes(); // 文字バイト数

	    if ( len != bytes.length ) {
	        return false;
	    }
	    // 半角ならばtrue
	    return true;
	}
}
