package loull.pb.rpc;

import loull.pb.rpc.LoullRPCService.LoRequest;
import loull.pb.rpc.LoullRPCService.LoResponse;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;

public class MyServiceImpl extends LoullRPCService.MyService {

	@Override
	public void loullSample(RpcController controller, LoRequest request,
			RpcCallback<LoResponse> done) {
		
	}

	
}
