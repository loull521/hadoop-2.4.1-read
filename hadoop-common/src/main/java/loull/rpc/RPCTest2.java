package loull.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.hadoop.HadoopIllegalArgumentException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.ProtobufRpcEngine;
import org.apache.hadoop.ipc.ProtocolSignature;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RpcEngine;
import org.apache.hadoop.ipc.Server;
import org.apache.hadoop.ipc.VersionedProtocol;
import org.apache.hadoop.net.NetUtils;

public class RPCTest2 {
	
	private static final String ADDRESS = "0.0.0.0";
	private static final int PORT = 54321;
	
	private static Configuration conf = new Configuration();
	
	public void setupConfForProtobuf() {
		conf.setClass("org.engine"+LoullProtocol.class.getName(), ProtobufRpcEngine.class, RpcEngine.class);
	}
	
	public interface LoullProtocol extends VersionedProtocol {
		public static final long versionID = 3L;
		int add(int a, int b) throws IOException;
		String getName() throws IOException;
	}
	
	public static class LoullImpl implements LoullProtocol {
		@Override
		public long getProtocolVersion(String protocol, long clientVersion)
				throws IOException {
			return LoullProtocol.versionID;
		}

		@Override
		public ProtocolSignature getProtocolSignature(String protocol,
				long clientVersion, int clientMethodsHash) throws IOException {
			return new ProtocolSignature(LoullProtocol.versionID, null);
		}

		@Override
		public int add(int a, int b) throws IOException {
			return a + b;
		}

		@Override
		public String getName() throws IOException {
			return "loull, you are so handsome!";
		}
	}
	
	private void testRpcCalls(Configuration conf) throws IOException {
		Server server = new RPC.Builder(conf).setProtocol(LoullProtocol.class)
				.setInstance(new LoullImpl()).setBindAddress(ADDRESS).setPort(0).build();
		server.start();
		
		InetSocketAddress addr = NetUtils.getConnectAddress(server);
		LoullProtocol proxy = RPC.getProxy(LoullProtocol.class, LoullProtocol.versionID, addr, conf);
		System.out.println("call add: " + proxy.add(1, 10));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("call getName: " + proxy.getName());
	}
	
	private static void startServer(Configuration conf) throws HadoopIllegalArgumentException, IOException {
		Server server = new RPC.Builder(conf).setProtocol(LoullProtocol.class)
				.setInstance(new LoullImpl()).setBindAddress(ADDRESS).setPort(PORT).build();
		server.start();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("server started.");
	}
	
	private static LoullProtocol createProxy(Configuration conf) throws IOException {
		InetSocketAddress addr = new InetSocketAddress(ADDRESS, PORT);
		LoullProtocol proxy = RPC.getProxy(LoullProtocol.class, LoullProtocol.versionID, addr, conf);
		return proxy;
	}

	public static void main(String[] args) {
//		RPCTest2 rpcTest = new RPCTest2();
//		try {
//			rpcTest.testRpcCalls(conf);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		try {
			startServer(conf);
			LoullProtocol proxy = createProxy(conf);
			int value = proxy.add(1, 4);
			System.out.println(value);
			System.out.println(proxy.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
