package loull.hadoop.pb.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import loull.hadoop.pb.rpc.CalculatorPbWrapper.CalRequest;
import loull.hadoop.pb.rpc.CalculatorPbWrapper.CalResponse;
import loull.hadoop.pb.rpc.CalculatorPbWrapper.CalculateService;

import com.google.protobuf.BlockingService;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Message;
import com.google.protobuf.ServiceException;

public class Server {

	private Class protocol;
	private BlockingService impl;
	private static String bindAddress = "localhost";
	private static int PORT = 8989;
	private boolean running = true;
	
	private Listener listener;
	private Responder responder;
	
	private int CALL_QUEUE_SIZE = 1 << 10;
	private BlockingQueue<Call> callQueue =
			new LinkedBlockingQueue<Server.Call>(CALL_QUEUE_SIZE);
	
	public Server(Class protocol, BlockingService protocolImpl, String address, int port) throws IOException {
		this.protocol = protocol;
		this.impl = protocolImpl;
		bindAddress = address;
		PORT = port;
		listener = new Listener();
		responder = new Responder();
	}
	
	public Server(Class protocol, BlockingService protocolImpl) throws IOException {
		this(protocol, protocolImpl, bindAddress, PORT);
	}
	
	public void start() {
		System.out.println("Server start.");
		listener.start();
		responder.start();
		System.out.println("Listener Thread start");
		System.out.println("Responder thread start");
	}
	
	private class Listener extends Thread {

		private ServerSocketChannel acceptChannel = null;
		private Random random = new Random();
		private Reader[] readers;
		private int curentReader = 0;
		private Selector listenerSelector;
		private InetSocketAddress address;
		private int READER_NUM = 10;
		
		public Listener() throws IOException {
			address = new InetSocketAddress(bindAddress, PORT);
			acceptChannel = ServerSocketChannel.open();
			acceptChannel.configureBlocking(false);
			acceptChannel.socket().bind(address);//TODO ..
			System.out.println("Listen on InetAddress " + bindAddress + ":" + PORT);
			
			listenerSelector = Selector.open();
			acceptChannel.register(listenerSelector, SelectionKey.OP_ACCEPT);
			readers = new Reader[READER_NUM];
			for (int i=0; i<READER_NUM; i++) {
				System.out.println("Reader " + i + " thread start.");
				Reader reader = new Reader("Socket Reader #" + i);
				readers[i] = reader;
				readers[i].start();
			}
		}
		
		@Override
		public void run() {
			while(running) {
				try {
					listenerSelector.select();
					Iterator<SelectionKey> iter = listenerSelector.selectedKeys().iterator();
					while (iter.hasNext()) {
						SelectionKey selectionKey = iter.next();
						iter.remove();
						if (selectionKey.isValid()) {
							if (selectionKey.isAcceptable()) {
								try {
									doAccept(selectionKey);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
						selectionKey = null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}//while
			
			try {
				acceptChannel.close();
				listenerSelector.close();
				listenerSelector = null;
				acceptChannel = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}//run
		
		private void doAccept(SelectionKey key) throws IOException, InterruptedException {
			ServerSocketChannel server = (ServerSocketChannel) key.channel();
			SocketChannel channel;
			while ((channel = server.accept()) != null) {
				System.out.println("Accept a connection" + channel.getRemoteAddress().toString());
				channel.configureBlocking(false);
				Reader reader = getReader();
				Connection connection = new Connection(channel);
				key.attach(connection);
				reader.addConnection(connection);
			}
		}
		
		private synchronized Reader getReader() {
			return readers[(curentReader++) % readers.length];
		}
	}//Listener class
	
	private class Reader extends Thread {
		private Selector readSelector;
		private BlockingQueue<Connection> pendingConnections;
		public static final int READER_PENDING_CONN_QUEUE_SIZE = 1 << 6;

		public Reader(String name) throws IOException {
			super(name);
			readSelector = Selector.open();
			pendingConnections = new LinkedBlockingQueue<Connection>(READER_PENDING_CONN_QUEUE_SIZE);
		}
		
		@Override
		public void run() {
			try {
				doRunLoop();
			} finally {
				try {
					readSelector.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		private void doRunLoop() {
			while (running) {
				try {
					int size = pendingConnections.size();
					for (int i=size; i>0; i--) {
						Connection conn = pendingConnections.take();
						conn.channel.register(readSelector, SelectionKey.OP_READ, conn);
						System.out.println("take up a conn from blockingqueue. And register OP_READ");
					}
					int n = readSelector.select();
					if (n <= 0) {
						System.out.println("select() wakeup by added a conn");
					} else {
						System.out.println("seclet() some skey");
					}
					
					Iterator<SelectionKey> iterator = readSelector.selectedKeys().iterator();
					while (iterator.hasNext()) {
						SelectionKey readKey = iterator.next();
						System.out.println("select a key: readyOpt:" + readKey.readyOps());
						iterator.remove();
						if (readKey.isValid()) {
							if (readKey.isReadable()) {
								doRead(readKey);
							}
						}
						readKey = null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}//while
		}
		
		private void doRead(SelectionKey readKey) {
			System.out.println("doRead a skey");
			int count = 0;
			Connection conn = (Connection) readKey.attachment();
			
			try {
				count = conn.readAndProcess();
				System.out.println("read count = " + count);
			} catch (IOException e) {
				//TODO close conn
				e.printStackTrace();
			}
			
			if (count < 0) {
				System.out.println("close conn. count = " + count);
//				conn.processTail();
//				System.out.println("before cancel:" + readKey.interestOps());
				readKey.cancel();
//				System.out.println("after cancel:" + readKey.interestOps());
				conn.close();
				conn = null;
			}
		}
		
		public void addConnection(Connection conn) throws InterruptedException {
			pendingConnections.put(conn);
			readSelector.wakeup();//唤醒阻塞在select()方法的线程
			System.out.println("conn added to BockingQueue.And wakeup readSelector select()");
		}
		
		void shutdown() {
			assert !running;
			readSelector.wakeup();
			try {
				join();//调用线程等待reader线程结束
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}//Reader
	
	private class Call {
		private Connection connection;
		byte[] messageData;
		Message message;
		long time;
		
		public Call(Connection conn, Message message, long time) {
			this.connection = conn;
			this.message = message;
			this.time = time;
		}
		
		public byte[] getMessageData() {
			return messageData;
		}

		public Message getMessage() {
			return message;
		}

		public long getTime() {
			return time;
		}
	}
	
	private class Connection{
		SocketChannel channel;
		ByteBuffer data = null;
		ByteBuffer dataLengthBuffer = ByteBuffer.allocate(4);
		
		public Connection(SocketChannel channel) {
			this.channel = channel;
		}

		public void processTail() {
			ByteBuffer buffer = ByteBuffer.allocate(16);
			int nbyte = 0;
			while(true) {
				buffer.clear();
				try {
					int r = channel.read(buffer);
					System.out.println("r = " + r);
					if (r <= 0) {
						System.out.println("Tail bytes = " + nbyte);
						break;
					}
					nbyte += r;
					buffer.flip();
					System.out.println("Tail:" + new String(buffer.array()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		public int readAndProcess() throws IOException {
			while (true) {
				int count = -1;
				if (dataLengthBuffer.remaining() > 0) {
					count = channel.read(dataLengthBuffer);
					if (count < 0 || dataLengthBuffer.hasRemaining()) {
						return count;
					}
				}
				
				if (null == data) {
					dataLengthBuffer.flip();//
					data = ByteBuffer.allocate(dataLengthBuffer.getInt());
				}
				
				if (data.hasRemaining()) {
					count = channel.read(data);
				}
				
				if (!data.hasRemaining()) {
					dataLengthBuffer.clear();
					CalRequest request = CalRequest.parseFrom(data.array());
					data = null;
					Call call = new Call(this, request, System.currentTimeMillis());
					try {
						callQueue.put(call);
						System.out.println("put a call in callQueue");
					} catch (InterruptedException e) {
					}
				}
				return count;
			}
		}//readAndProcess
		
		public void close() {
			data = null;
			dataLengthBuffer = null;
			if (channel.isOpen()){
				try {
					channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}//Connection
	
	private class Handler extends Thread{
	}
	
	private class Responder extends Thread {
		
		@Override
		public void run () {
			while (running) {
				try {
					Call call = callQueue.take();
					System.out.println("take out a call from callQueue");
					System.out.println("call.getMessage:" + call.getMessage());
					CalRequest request = (CalRequest)call.getMessage();
					MethodDescriptor methodDescriptor = 
							CalculateService.getDescriptor()
							.findMethodByName(request.getMethodName());
					CalResponse response = (CalResponse)
							impl.callBlockingMethod(methodDescriptor, null, request);
					byte[] data = response.toByteArray();
					ByteBuffer dataLengthBuffer = ByteBuffer.allocate(4);
					ByteBuffer databuf = ByteBuffer.allocate(data.length);
					dataLengthBuffer.putInt(data.length);
					dataLengthBuffer.flip();
					databuf.put(data);
					databuf.flip();
					SocketChannel channel = call.connection.channel;
					
//					Thread.sleep(2000);
//					if (!channel.isOpen()) {
//						System.out.println("channel is closed");
//					}
					
					channel.write(dataLengthBuffer);
					channel.write(databuf);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ServiceException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
