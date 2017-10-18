package ren05_collection;

import java.util.ArrayList;

/**
 * ArrayListの例１
 * forでまわす
 */
public class ListP1 {
	public static void main(String[] args) {

		ArrayList<String> strList = new ArrayList<String>();
		strList.add("りんご");
		strList.add("みかん");
		strList.add("すいか");
		strList.add("いちご");

		for(int i = 0; i < strList.size();i++) {
			System.out.println(strList.get(i));
		}
	}
}
