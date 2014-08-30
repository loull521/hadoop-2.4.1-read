package loull.jvm;

import java.util.ArrayList;

public class ClassLoaderTest {
	
	public static void main(String[] args) {
		System.out.println(ClassLoaderTest.class.getClassLoader());
		System.out.println(String.class.getClassLoader());
		System.out.println(Thread.currentThread().getContextClassLoader());
		ClassLoader app = ClassLoaderTest.class.getClassLoader();
		System.out.println(app.getParent());
		ClassLoader ext = app.getParent();
		System.out.println(ext.getParent());
		System.out.println(MyList.class.getClassLoader());
		System.out.println(ArrayList.class.getClassLoader());
	}
	
	static class MyList extends ArrayList {
		
	}

}
