package skillManagement.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * ファイルを取り扱うユーティリティクラス
 */
public class FileUtility {

	/**
	 *
	 * XXX メソッドの説明を書くこと<br>
	 * <br>
	 * プロパティファイルを読み込んでPropetiesのオブジェクトを作成・取得する
	 * @return
	 */
	public static Properties createProperties() throws IOException{

		//プロパティファイルを読み込み
//		BufferedReader reader = null;
		Properties properties = null;


		try (InputStream is = FileUtility.class.getClassLoader().getResourceAsStream("message.properties");){
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");

			/* XXX readerもTry-with-Resource文で初期化＆closeしたほうがよい */
//			reader = new BufferedReader(isr);
//			properties = new Properties();
//			properties.load(reader);
//
//		} finally {
//			if(reader != null){
//				reader.close();
//			}
			try (BufferedReader reader = new BufferedReader(isr);) {
				properties = new Properties();
				properties.load(reader);
			}
		}
		return properties;
	}
}
