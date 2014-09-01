package loull.hadoop.pb.rpc2;

import loull.hadoop.pb.rpc2.CalculatorPbWrapper.CalculatorResponseProto;

public class CalculatorResponsePBImpl extends CalculatorResponse {
	
	private int value;
	private CalculatorResponseProto proto = null;
	private CalculatorResponseProto.Builder builder = null;
	
	public CalculatorResponsePBImpl() {
		builder = CalculatorResponseProto.newBuilder();
	}
	
	public CalculatorResponseProto getProto() {
		if (proto == null) {
			builder.setValue(value);
			proto = builder.build();
		}
		return proto;
	}

	@Override
	public void setValue(int val) {
		value = val;
	}

	@Override
	public int getValue() {
		return value;
	}

}
