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
//		int y = request.getY();//�����л�������ȡ����������
//		int result = real.add(x, y);//���������ķ���
//		builder.setValue(result);//�������л������ֵ
//		return builder.build();//�������л����
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
