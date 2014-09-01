package loull.hadoop.pb.rpc2;

import loull.hadoop.pb.rpc2.CalculatorPbWrapper.CalculatorRequestProto;
import loull.hadoop.pb.rpc2.CalculatorPbWrapper.CalculatorResponseProto;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;

public class CalculatorPbServerImpl implements CalculatorPB{
	
	private Calculator real;
	
	public CalculatorPbServerImpl(Calculator impl) {
		this.real = impl;
	}

//	@Override
//	public CalResponse add(RpcController controller, CalRequest request)
//			throws ServiceException {
//		CalResponse.Builder builder = CalResponse.newBuilder();
//		int x = request.getX();
//		int y = request.getY();//从序列化对象中取出方法参数
//		int result = real.add(x, y);//调用真正的方法
//		builder.setValue(result);//设置序列化对象的值
//		return builder.build();//返回序列化结果
//	}

	@Override
	public CalculatorResponseProto add(RpcController controller,
			CalculatorRequestProto requestProto) throws ServiceException {
		CalculatorRequestPBImpl request = new CalculatorRequestPBImpl(requestProto);
		CalculatorResponse response = real.add(request);
		CalculatorResponseProto responseProto = ((CalculatorResponsePBImpl)response).getProto();
		return responseProto;
	}
}
