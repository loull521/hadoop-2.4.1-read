package loull.hadoop.pb.rpc2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

import org.apache.hadoop.io.DataOutputBuffer;

import com.google.protobuf.Message;
import com.google.protobuf.ServiceException;

import loull.hadoop.pb.rpc2.CalculatorPbWrapper.CalculatorRequestProto;
import loull.hadoop.pb.rpc2.CalculatorPbWrapper.CalculatorResponseProto;
import loull.hadoop.pb.rpc2.ProtobufRpcProtos.RequestHeaderProto;

public class CalculatorPbClientImpl implements Calculator {
	
	private CalculatorPB proxy;
	private static String ADDRESS = "localhost";
	private static int PORT = 8989;
	
	public CalculatorPbClientImpl() {
		this(ADDRESS, PORT);
	}
	
	public CalculatorPbClientImpl(String address, int port) {
		ADDRESS = address;
		PORT = port;
		System.out.println("CalculatorPbClientImpl get the proxy");
		proxy = (CalculatorPB) Proxy.newProxyInstance(CalculatorPB.class.getClassLoader(), 
				new Class[] {CalculatorPB.class}, new Invoker());
	}
	
	public class Invoker implements InvocationHandler {
		@Override
		public Object invoke(Object proxy, Method method, Object[] args)//用上这个method
				throws Throwable {
			Socket socket = new Socket(ADDRESS, PORT);
			System.out.println("CalculatorPbClientImpl connect to @ " + ADDRESS + ":" + PORT);
			
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			DataInputStream in = new DataInputStream(socket.getInputStream());
			
			RequestHeaderProto.Builder builder = RequestHeaderProto.newBuilder();
			builder.setMethodName(method.getName());
			RequestHeaderProto rpcRequestHeaderProto = builder.build();
			Message theRequest = (Message)args[1];//0:rpcController 1:message
			
			DataOutputBuffer dob = new DataOutputBuffer();
			rpcRequestHeaderProto.writeDelimitedTo(dob);
			theRequest.writeDelimitedTo(dob);
			
			byte[] data = dob.getData();
			int totolLength = data.length;
			
			out.writeInt(totolLength);
			out.write(data);
			out.flush();
			socket.shutdownOutput();
			
			int dataLen = in.readInt();
			byte[] responseData = new byte[dataLen];
			in.read(responseData);
			CalculatorResponseProto response = CalculatorResponseProto.parseFrom(responseData);
			System.out.println("response: " + response);
			socket.close();
			return response;
		}
	}

//	@Override
//	public int add(int a, int b) {
//		CalRequest.Builder builder = CalRequest.newBuilder();
//		builder.setMethodName("add");
//		builder.setX(a);
//		builder.setY(b);
//		CalRequest request = builder.build();
//		CalResponse response = null;
//		try {
//			response = proxy.add(null, request);
//		} catch (ServiceException e) {
//			e.printStackTrace();
//		}
//		return response.getValue();
//	}

	@Override
	public CalculatorResponse add(CalculatorRequest request) {
		CalculatorRequestProto requestProto = ((CalculatorRequestPBImpl)request).getProto();
		try {
			CalculatorResponseProto responseProto = proxy.add(null, requestProto);
			return CalculatorResponse.newInstance(responseProto.getValue());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

}
