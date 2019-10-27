//matriciation number: s1794094
package matcher;

public class StudentClass {

	public KMPMatcher kmpMatcher;

	public StudentClass(String text, String pattern) {
		kmpMatcher = new KMPMatcher(text, pattern);
	}

	public void buildPrefixFunction() {
		kmpMatcher.setPrefixFunction(computePrefixFunction(kmpMatcher.getPattern()));
	}
	
	public static int[] computePrefixFunction(String pattern) {
		int m = pattern.length();
		int[] pi = new int[pattern.length()];
		int k = 0; // holds length of matched characters
		// pi[0] = 0; // I omitted this since java initialize array values to zero automatically

		for (int q=1; q<m; q++){ //scan the pattern from left to right
			char currentChar = pattern.charAt(q);
			while(k > 0 && pattern.charAt(k) != currentChar)
				k = pi[k-1]; //next character does not match
			if (pattern.charAt(k) == currentChar)
				k +=1; //next character matches
			pi[q]=k;
			
		}
		return pi;
	}

	

	public static class KMPMatcher {

		private String text;
		private String pattern;
		private int textLen;
		private int patternLen;
		private int[] prefixFunction;
		private Queue matchIndices;

		public KMPMatcher(String text, String pattern) {
			this.text = text;
			this.pattern = pattern;
			this.textLen = text.length();
			this.patternLen = pattern.length();
			this.prefixFunction = new int[patternLen + 1];
			this.matchIndices = new Queue();
		}

		public void setPrefixFunction(int[] prefixFunction) {
			this.prefixFunction = prefixFunction;
		}

		public int[] getPrefixFunction() {
			return prefixFunction;
		}

		public String getPattern() {
			return pattern;
		}

		public Queue getMatchIndices() {
			return matchIndices;
		}

		public void search() {
			if (patternLen > textLen) // || or patternLen == 0) which I omitted because it was changing the running time of KMP - making harder to choose Xover 
				return;				  
			int q = 0; //  length of matched characters
			setPrefixFunction(computePrefixFunction(getPattern())); 
			for (int i = 0; i < this.textLen; i++){ //scan the text from left to right
				char currentChar = text.charAt(i);
				while(q > 0 && pattern.charAt(q) != currentChar)
					q = prefixFunction[q-1]; //next character does not match
				if (pattern.charAt(q) == currentChar)
					q++; //next character matches
				if (q == patternLen){ // found a complete match 
					matchIndices.enqueue(i-(patternLen-1));
					q = prefixFunction[q-1]; // look for the next match
				}
			}
		}
	}


	public static void main(String[] args){
		// KMPMatcher test = new KMPMatcher("hhjhhj", "");
		// test.search();
		// for (int i: computePrefixFunction("ababbabababc")){
		// 	System.out.println(i);
		// }
		// System.out.print(test.matchIndices.toString());
		Matcher testMycode = new Matcher();
		// testMycode.testKMPMatcher(10000, 1000);
		
		
		
		// Matcher.getRuntimes(10,10000, "matcherTimes.txt");	
		// Matcher.getRatios(10,100,240000, "matcherRatios.txt");
		Matcher.plotRuntimes(0.012700,0.010709, "matcherTimes.txt");	


	}
}