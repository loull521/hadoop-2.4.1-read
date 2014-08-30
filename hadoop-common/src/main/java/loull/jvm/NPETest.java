package loull.jvm;

import java.io.IOException;
import java.util.List;

public class NPETest {
	
	private static void fun1() {
		List<Integer> list = null;
		for (int i : list) {
			System.out.println(i);
		}
		System.out.println("test end");
	}
	
	private static void test1() {
		try {
			fun1();
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("test1 catch");
//			return;
		} 
		finally {
			System.out.println("test1 finally");
		}
		System.out.println("test1 end");
	}
	
	private static void throwExp() throws IOException {
		throw new IOException("loull");
	}
	
	private static void test2() {
		try {
			throwExp();
		} catch (IOException e) {
			System.out.println("catch test2");
			e.printStackTrace();
		} 
		finally {
			System.out.println("finally test2");
		}
		System.out.println("test2 end");
	}

	public static void main(String[] args) {
		System.out.println("start test1");
		test1();
		System.out.println("start test2");
		test2();
		System.out.println("main end");
	}
}
