package loull.hadoop.pb.rpc2;

import java.io.IOException;

import loull.hadoop.pb.rpc2.CalculatorPbWrapper.CalculateService;

import com.google.protobuf.BlockingService;

public class CalculatorServer implements Calculator{
	
	private Server server;
	
	public void start() {
		CalculatorPbServerImpl calculatorPbServerImpl = new CalculatorPbServerImpl(this);
		BlockingService service = CalculateService.newReflectiveBlockingService(calculatorPbServerImpl);
		try {
			server = new Server(CalculatorPbServerImpl.class.getInterfaces()[0], service);//分别是协议和pb协议实现
		} catch (IOException e) {
			e.printStackTrace();
		}
		server.start();
	}
	
	@Override
	public CalculatorResponse add(CalculatorRequest request) {
		int x = request.getX();
		int y = request.getY();
		int value = x + y;
		CalculatorResponse response = CalculatorResponse.newInstance(value);
		return response;
	}

	public static void main(String[] args) {
		CalculatorServer service = new CalculatorServer();
		service.start();
	}
}
