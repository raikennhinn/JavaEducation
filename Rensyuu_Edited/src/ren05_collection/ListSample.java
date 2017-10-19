package ren05_collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * List、Set、Map
 * ループ、要素の削除、ソート
 */
public class ListSample {

	public static void main(String[] args) {

//		// リスト：順番を持つコレクション（配列に近い）
//		List<Integer> list = new ArrayList<Integer>();
//
//		for(int i = 0; i < 5; i++) {
//			list.add(i * 5);
//		}
//
//		for(int num : list)
//			System.out.println("リストの中 : " + num);
//
////		int amount = 0;
////		for(int i = 0; i < list.size(); i++) {
////			amount = amount + (int) list.get(i);
////			System.out.println("合計値 : " + amount);
////		}
//
//		Collections.sort(list, Comparator.reverseOrder());
//		System.out.println("--- Sorted ---");
//
//		for(int num : list)
//			System.out.println("リストの中 : " + num);
//
//		list.remove(3);
//
//		for(int num : list)
//			System.out.println("リストの中 : " + num);


//		List<String> strList = new ArrayList<String>();
//		strList.add("ためしに");
//		strList.add("やって");
//		strList.add("みる");
//
////		strList.remove("やって");
//		strList.remove(1);
//
//
//		for(String str : strList)
//			System.out.println("リストの中 : " + str);


//		// セット：重複を持たないコレクション（集合）
//		Set<Integer> set = new HashSet<Integer>();
//		set.add(1);
//		set.add(0);
//		set.add(0);
//		set.add(0);
//		set.add(0);
//		set.add(5);
//
//		for(int s : set)
//			System.out.println("セットの中 : " + s);

		// マップ：キーと値の組み合わせコレクション
		Map<Integer, String> map = new HashMap<Integer, String>();

		map.put(1621, "池田さん");
		map.put(3000, "だれか０２");
		map.put(1677, "だれか０１");
		map.put(1457, "平田マサキ");

		Set<Integer> keys = map.keySet();
		for(int key : keys) {
			System.out.println(map.get(key));
		}

//		Iterator<Integer> it = keys.iterator();
//		while(it.hasNext()) {
//			System.out.println(map.get(it.next()));
//		}
	}

}
