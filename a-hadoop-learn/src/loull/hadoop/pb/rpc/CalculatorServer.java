package loull.hadoop.pb.rpc;

import java.io.IOException;

import loull.hadoop.pb.rpc.CalculatorPbWrapper.CalculateService;

import com.google.protobuf.BlockingService;

public class CalculatorServer implements Calculator{
	
	private Server server;
	
	public void start() {
		CalculatorPbServerImpl calculatorPbServerImpl = new CalculatorPbServerImpl(this);
		BlockingService service = CalculateService.newReflectiveBlockingService(calculatorPbServerImpl);
		try {
			server = new Server(CalculatorPbServerImpl.class.getInterfaces()[0], service);
		} catch (IOException e) {
			e.printStackTrace();
		}
		server.start();
	}

	@Override
	public int add(int a, int b) {
		return a + b;
	}

	public static void main(String[] args) {
		CalculatorServer service = new CalculatorServer();
		service.start();
	}
}
