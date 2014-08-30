package loull.state;

public class PatternMachineDemo {
	
	public interface Parser {
		void parse(String text) throws Exception;
	}
	
	abstract class State {
		public abstract void isAlpha(char c);
		public abstract void isDigit(char c);
		public abstract void isOther(char c);
		
		public void dispatch(char c) {
			if (Character.isLetter(c)) {
				isAlpha(c);
			}
			else if (Character.isDigit(c)) {
				isDigit(c);
			}
			else {
				isOther(c);
			}
		}
	}
	
	public class PatternMachine implements Parser {
		
		private String mtoken = "";
		
		private StartState mStartState = new StartState();
		private KeywordState mKeywordState = new KeywordState();
		private NumberState mNumberState = new NumberState();
		
		private State mState = mStartState;
		
		void setState(State state) {
			mState = state;
		}
		
		State getState() {
			return mState;
		}
		
		public class StartState extends State {
			@Override
			public void isAlpha(char c) {
				setState(mKeywordState);
				mtoken += c;
			}

			@Override
			public void isDigit(char c) {
				setState(mNumberState);
				mtoken += c;
			}

			@Override
			public void isOther(char c) {
			}
		}
		
		public class KeywordState extends State {
			@Override
			public void isAlpha(char c) {
				mtoken += c;
			}

			@Override
			public void isDigit(char c) {
				mtoken += c;
			}

			@Override
			public void isOther(char c) {
				setState(mStartState);
				System.out.println("keyword: " + mtoken);
				mtoken = "";
			}
			
		}
		
		public class NumberState extends State {

			@Override
			public void isAlpha(char c) {
				setState(mStartState);
				System.out.println("Number: " + mtoken);
				mtoken = "";
			}

			@Override
			public void isDigit(char c) {
				mtoken += c;
			}

			@Override
			public void isOther(char c) {
				setState(mStartState);
				System.out.println("number: " + mtoken);
				mtoken = "";
			}
		}
		
		@Override
		public void parse(String text){
			for (char c : text.toCharArray()) {
				mState.dispatch(c);
			}
		}
		
	}//end of PatternMachine
	
	public static void main(String[] args) {
		new PatternMachineDemo().new PatternMachine().parse("JKJT&U$%&*((*JFJJ9777iuhkYU^"
				+ "^&*&(*HHY*(O(J  8748974979 jjlkur98jj*&");
	}

}
