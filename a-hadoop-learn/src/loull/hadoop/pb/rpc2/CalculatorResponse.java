package loull.hadoop.pb.rpc2;

public abstract class CalculatorResponse {
	
	public static CalculatorResponse newInstance(int val) {
		CalculatorResponse response = new CalculatorResponsePBImpl();
		response.setValue(val);
		return response;
	}

	public abstract void setValue(int val);
	
	public abstract int getValue();
}
