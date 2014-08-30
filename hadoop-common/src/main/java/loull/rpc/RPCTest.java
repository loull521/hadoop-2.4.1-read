package loull.rpc;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;

import javax.net.SocketFactory;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.retry.RetryPolicy;
import org.apache.hadoop.ipc.ProtocolMetaInfoPB;
import org.apache.hadoop.ipc.ProtocolProxy;
import org.apache.hadoop.ipc.ProtocolSignature;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RpcEngine;
import org.apache.hadoop.ipc.Server;
import org.apache.hadoop.ipc.VersionedProtocol;
import org.apache.hadoop.ipc.Client.ConnectionId;
import org.apache.hadoop.net.NetUtils;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.security.token.SecretManager;
import org.apache.hadoop.security.token.TokenIdentifier;

public class RPCTest {

	private static final String ADDRESS = "0.0.0.0";
	
	private static Configuration conf;
	  
	  public void setupConf() {
	    conf = new Configuration();
//	    conf.setClass("rpc.engine." + StoppedProtocol.class.getName(),
//	        StoppedRpcEngine.class, RpcEngine.class);
//	    UserGroupInformation.setConfiguration(conf);
	  }
	
	public interface TestProtocol extends VersionedProtocol {
		public static final long versionID = 1L;
		int add(int v1, int v2) throws IOException;
	}
	
	public static class TestImpl implements TestProtocol {

		@Override
		public long getProtocolVersion(String protocol, long clientVersion)
				throws IOException {
			return TestProtocol.versionID;
		}

		@Override
		public ProtocolSignature getProtocolSignature(String protocol,
				long clientVersion, int clientMethodsHash) throws IOException {
			return new ProtocolSignature(TestProtocol.versionID, null);
		}

		@Override
		public int add(int v1, int v2) throws IOException {
			return v1 + v2;
		}
	}
	
	/**
	   * A basic interface for testing client-side RPC resource cleanup.
	   */
	  private static interface StoppedProtocol {
	    long versionID = 0;
	
	    public void stop();
	  }
	  
	  /**
	   * A class used for testing cleanup of client side RPC resources.
	   */
	  private static class StoppedRpcEngine implements RpcEngine {

	    @SuppressWarnings("unchecked")
	    @Override
	    public <T> ProtocolProxy<T> getProxy(Class<T> protocol, long clientVersion,
	        InetSocketAddress addr, UserGroupInformation ticket, Configuration conf,
	        SocketFactory factory, int rpcTimeout, RetryPolicy connectionRetryPolicy
	        ) throws IOException {
	      T proxy = (T) Proxy.newProxyInstance(protocol.getClassLoader(),
	              new Class[] { protocol }, new StoppedInvocationHandler());
	      return new ProtocolProxy<T>(protocol, proxy, false);
	    }

	    @Override
	    public org.apache.hadoop.ipc.RPC.Server getServer(Class<?> protocol,
	        Object instance, String bindAddress, int port, int numHandlers,
	        int numReaders, int queueSizePerHandler, boolean verbose, Configuration conf,
	        SecretManager<? extends TokenIdentifier> secretManager, 
	        String portRangeConfig) throws IOException {
	      return null;
	    }

	    @Override
	    public ProtocolProxy<ProtocolMetaInfoPB> getProtocolMetaInfoProxy(
	        ConnectionId connId, Configuration conf, SocketFactory factory)
	        throws IOException {
	      throw new UnsupportedOperationException("This proxy is not supported");
	    }
	  }
	  
	  /**
	   * An invocation handler which does nothing when invoking methods, and just
	   * counts the number of times close() is called.
	   */
	  private static class StoppedInvocationHandler
	      implements InvocationHandler, Closeable {
	    
	    private int closeCalled = 0;

	    @Override
	    public Object invoke(Object proxy, Method method, Object[] args)
	        throws Throwable {
	          return null;
	    }

	    @Override
	    public void close() throws IOException {
	      closeCalled++;
	    }
	    
	    public int getCloseCalled() {
	      return closeCalled;
	    }
	    
	  }
	
	private void testCallsInternal(Configuration conf) throws IOException {
		Server server = new RPC.Builder(conf).setProtocol(TestProtocol.class)
				.setInstance(new TestImpl()).setBindAddress(ADDRESS).setPort(0).build();
		TestProtocol proxy = null;
		server.start();
		InetSocketAddress addr = NetUtils.getConnectAddress(server);
		proxy = RPC.getProxy(TestProtocol.class, TestProtocol.versionID, addr, conf);//get protocol client
		
		int r = proxy.add(1, 2);// make a rpc call, and get the returned value
		System.out.println(r);
	}
	
	public static void main(String[] args) {
		
		RPCTest rpcTest = new RPCTest();
		rpcTest.setupConf();
		try {
			rpcTest.testCallsInternal(conf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
