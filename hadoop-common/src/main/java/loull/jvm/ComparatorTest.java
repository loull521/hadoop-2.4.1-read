package loull.jvm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class ComparatorTest {

	public static void main(String[] args) {
		int[] a = new int[] {1,2,3};
		Arrays.asList(a);
		System.out.println(Arrays.asList(a).get(0)[1]);
		
		java.util.List<Integer> list = new ArrayList<Integer>();
		list.add(4);
		list.add(3);
		list.add(5);
		Collections.sort(list, new MyComparator());
		
		for (int i : list) {
			System.out.println(i);
		}
	}
}

class MyComparator implements Comparator<Integer> {

	@Override
	public int compare(Integer o1, Integer o2) {
		int ret = o1.compareTo(o2);
		return ret;
//		if (o1 < o2) return -1;
//		else return 1;
	}
}