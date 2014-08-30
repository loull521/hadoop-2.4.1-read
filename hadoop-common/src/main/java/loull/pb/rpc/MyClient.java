package loull.pb.rpc;

import loull.pb.rpc.LoullRPCService.LoRequest;
import loull.pb.rpc.LoullRPCService.LoResponse;
import loull.pb.rpc.LoullRPCService.MyService;

import com.google.protobuf.BlockingRpcChannel;
import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import com.googlecode.protobuf.socketrpc.RpcChannels;
import com.googlecode.protobuf.socketrpc.RpcConnectionFactory;
import com.googlecode.protobuf.socketrpc.SocketRpcConnectionFactories;
import com.googlecode.protobuf.socketrpc.SocketRpcController;

public class MyClient {

	public static void main(String[] args) {
		System.out.println("Cliet~~~~~~~~~~~~~~~");
		RpcConnectionFactory connectionFactory = SocketRpcConnectionFactories.createRpcConnectionFactory("0.0.0.0", 8989);
		BlockingRpcChannel channel = RpcChannels.newBlockingRpcChannel(connectionFactory);
		
		LoullRPCService.MyService.BlockingInterface service = MyService.newBlockingStub(channel);
		RpcController rpcController = new SocketRpcController();
		LoRequest request = LoRequest.newBuilder().setAgent("loull~hello").setThresh(16).build();
		try {
			LoResponse response = service.loullSample(rpcController, request);
		} catch (ServiceException e) {
			System.out.println("fuck");
			e.printStackTrace();
		}
		
		if (rpcController.failed()) {
			System.err.println(rpcController.errorText());
		}
		System.out.println("client end~~~~~~~~~~~~~~~");
	}
}
