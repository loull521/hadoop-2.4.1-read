package loull.hadoop.pb.rpc;

import loull.hadoop.pb.rpc.CalculatorPbWrapper.CalRequest;
import loull.hadoop.pb.rpc.CalculatorPbWrapper.CalResponse;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;

public class CalculatorPbServerImpl implements CalculatorPB{
	
	private Calculator real;
	
	public CalculatorPbServerImpl(Calculator real) {
		this.real = real;
	}

	@Override
	public CalResponse add(RpcController controller, CalRequest request)
			throws ServiceException {
		CalResponse.Builder builder = CalResponse.newBuilder();
		int x = request.getX();
		int y = request.getY();
		int result = real.add(x, y);
		builder.setValue(result);
		return builder.build();
	}
}
