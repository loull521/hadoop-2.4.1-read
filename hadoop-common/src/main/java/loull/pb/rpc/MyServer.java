package loull.pb.rpc;

import java.util.concurrent.Executors;

import com.googlecode.protobuf.socketrpc.RpcServer;
import com.googlecode.protobuf.socketrpc.ServerRpcConnectionFactory;
import com.googlecode.protobuf.socketrpc.SocketRpcConnectionFactories;

public class MyServer {
	
	public static void main(String[] args) {
		System.out.println("server start++++++++++++++++++");
		ServerRpcConnectionFactory rpcConnectionFactory = SocketRpcConnectionFactories.createServerRpcConnectionFactory(8989);
		RpcServer server = new RpcServer(rpcConnectionFactory, Executors.newFixedThreadPool(10), true);
		server.registerService(new MyServiceImpl());
		server.registerBlockingService(LoullRPCService.MyService.newReflectiveBlockingService(new MyBlockingInterfaceImpl()));
		server.run();
		System.out.println("server end---------------------");
	}

}
