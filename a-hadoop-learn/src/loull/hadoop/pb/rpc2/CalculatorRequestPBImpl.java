package loull.hadoop.pb.rpc2;

import loull.hadoop.pb.rpc2.CalculatorPbWrapper.CalculatorRequestProto;

public class CalculatorRequestPBImpl extends CalculatorRequest {
	
	private int x;
	private int y;
	CalculatorRequestProto.Builder builder = null;
	CalculatorRequestProto proto = null;
	
	public CalculatorRequestPBImpl() {
		builder = CalculatorRequestProto.newBuilder();
	}
	
	public CalculatorRequestPBImpl(CalculatorRequestProto proto) {
		this.proto = proto;
		x = proto.getX();
		y = proto.getY();
	}
	
	public CalculatorRequestProto getProto() {
		if (proto == null) {
			if (builder == null) {
				builder = CalculatorRequestProto.newBuilder();
			}
			builder.setX(x);
			builder.setY(y);
			proto = builder.build();
		}
		return proto;
	}
	
	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

}
