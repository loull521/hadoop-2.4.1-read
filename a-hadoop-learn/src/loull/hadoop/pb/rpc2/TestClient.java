package loull.hadoop.pb.rpc2;

import java.util.Random;

public class TestClient {

	public static void main(String[] args) {
		Calculator calculator = new CalculatorPbClientImpl();
		Random random = new Random(0);
		for (int i=0; i<5; i++) {
			int x = random.nextInt(100);
			int y = random.nextInt(100);
			CalculatorRequest request = CalculatorRequest.newInstance(x, y);
			CalculatorResponse result = calculator.add(request);
			System.out.println("TestClient result = " + result.getValue() + "\n\n");
		}
		
	}
}
