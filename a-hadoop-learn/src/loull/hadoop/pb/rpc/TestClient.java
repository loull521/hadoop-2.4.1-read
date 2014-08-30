package loull.hadoop.pb.rpc;

import java.util.Random;

public class TestClient {

	public static void main(String[] args) {
		Calculator calculator = new CalculatorPbClientImpl();
		Random random = new Random(0);
		for (int i=0; i<5; i++) {
			int x = random.nextInt(100);
			int y = random.nextInt(100);
			int result = calculator.add(x, y);
			System.out.println("TestClient result = " + result + "\n\n");
		}
		
	}
}
