package loull.pb.rpc;

import loull.pb.rpc.LoullRPCService.LoRequest;
import loull.pb.rpc.LoullRPCService.LoResponse;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;

public class MyBlockingInterfaceImpl implements LoullRPCService.MyService.BlockingInterface{

	@Override
	public LoResponse loullSample(RpcController controller, LoRequest request)
			throws ServiceException {
		System.out.println("agent is : " + request.getAgent());
		System.out.println("threshold is : " + request.getThresh());
		return null;
	}

}
