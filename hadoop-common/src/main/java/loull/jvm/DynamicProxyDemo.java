package loull.jvm;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class DynamicProxyDemo {

	public interface Book {
	    public void addBook();
	}
	
	public static class BookImpl implements Book {
	    @Override
	    public void addBook() {
	        System.out.println("add books");
	    }
	}
	
	public static class DynamicProxyHandler implements InvocationHandler {
	    private Object target;
	    
	    public DynamicProxyHandler(Object target) {
	        this.target = target;
	    }

	    @Override
	    public Object invoke(Object proxy, Method method, Object[] args)
	            throws Throwable {
	        System.out.println("before invoke");
	        Object result = method.invoke(target, args);
	        System.out.println("after invoke");
	        return result;
	    }
	}
	
	private static Object getProxy(Object target) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new DynamicProxyHandler(target));
    }
	
	public static void main(String[] args) {
		Book proxy = (Book)getProxy(new BookImpl());
		proxy.addBook();
	}
}
