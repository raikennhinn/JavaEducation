package ren05_collection;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * ArrayListの例２
 * contains（中身の検索）拡張forやIteratorでまわす
 */
public class ListP2 {
	public static void main(String[] args) {
		ArrayList<String> AList = new ArrayList<String>();
		AList.add("1番目");
		AList.add("2番目");
		AList.add("3番目");
		AList.add("4番目");
		AList.add("5番目");

		if(AList.contains("2番目")) {
			System.out.println("入ってます");
		}

		for(String str : AList) {
			System.out.println(str);
		}

		Iterator<String> list = AList.iterator();
		while(list.hasNext()) {
			System.out.println(list.next());
		}
	}

}
