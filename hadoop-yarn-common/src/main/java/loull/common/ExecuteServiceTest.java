package loull.common;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecuteServiceTest {
	
	private static ExecutorService executorService = Executors.newFixedThreadPool(10);

	private static void testSubmit() throws InterruptedException, ExecutionException {
		Future<String> future = executorService.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println("loull");
				Thread.sleep(2000);
				return "ok";
			}
		});
		System.out.println("~~~");
		System.out.println("future.get()=" + future.get());//future.get()会阻塞到线程返回
	}
	
	private static void testInvokeAny() throws InterruptedException, ExecutionException {
		Set<Callable<String>> callables = new HashSet<Callable<String>>();
		callables.add(new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println("do task 1");
				Thread.sleep(100);
				return "task 1";
			}
		});
		callables.add(new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println("do task 2");
				Thread.sleep(500);
				return "task 2";
			}
		});
		callables.add(new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println("do task 3");
				Thread.sleep(1000);
				return "task 3";
			}
		});
		String result = executorService.invokeAny(callables);//返回的是最先执行完毕的
		Thread.sleep(1000);
		System.out.println("result = " + result);
	}
	
	public static void main(String[] args) {
		try {
			testSubmit();
			
			System.out.println("\n***************\n");
			
			testInvokeAny();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			if (executorService != null) {
				executorService.shutdown();
			}
		}
	}
}
