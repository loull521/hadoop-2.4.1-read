package loull.hadoop.pb.rpc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

import com.google.protobuf.ServiceException;

import loull.hadoop.pb.rpc.CalculatorPbWrapper.CalRequest;
import loull.hadoop.pb.rpc.CalculatorPbWrapper.CalResponse;

public class CalculatorPbClientImpl implements Calculator {
	
	private CalculatorPB proxy;
//	Socket socket = null;
	
	public CalculatorPbClientImpl() {
		System.out.println("CalculatorPbClientImpl get the proxy");
		proxy = (CalculatorPB) Proxy.newProxyInstance(CalculatorPB.class.getClassLoader(), 
				new Class[] {CalculatorPB.class}, new Invoker());
//		try {
//			socket = new Socket("localhost", 8989);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	public class Invoker implements InvocationHandler {
		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			Socket socket = new Socket("localhost", 8989);
			System.out.println("CalculatorPbClientImpl connect to localhost:8989");
			
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			DataInputStream in = new DataInputStream(socket.getInputStream());
			
			byte[] bytes = ((CalRequest)args[1]).toByteArray();//TODO why 1
			out.writeInt(bytes.length);
			out.write(bytes);
			out.flush();
//			socket.shutdownOutput();
			
			int dataLen = in.readInt();
			byte[] data = new byte[dataLen];
			int count = in.read(data);
			CalResponse response = CalResponse.parseFrom(data);
			System.out.println("response: " + response);
//			Thread.sleep(2000);
//			System.out.println("client sleep 2s");
			socket.close();
			return response;
		}
	}

	@Override
	public int add(int a, int b) {
		CalRequest.Builder builder = CalRequest.newBuilder();
		builder.setMethodName("add");
		builder.setX(a);
		builder.setY(b);
		CalRequest request = builder.build();
		CalResponse response = null;
		try {
			response = proxy.add(null, request);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return response.getValue();
	}

}
