package loull.hadoop.pb.rpc2;

public abstract class CalculatorRequest {
	
	public static CalculatorRequest newInstance(int x, int y) {
		CalculatorRequest request = new CalculatorRequestPBImpl();
		request.setX(x);
		request.setY(y);
		return request;
	}
	
	public abstract void setX(int x);
	public abstract void setY(int y);

	public abstract int getX();
	public abstract int getY();
}
