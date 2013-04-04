import java.util.ArrayList;
import java.util.TreeMap;

public class Interpreter {
	private TreeMap<String, Llama> objects;
	private TreeMap<String, Method> methods;
	private TreeMap<String, Integer> ints;
	private TreeMap<String, Boolean> bools;
	private TreeMap<String, String> strings;
	private TreeMap<String, Double> decimals;
	private TreeMap<String, ArrayList<Object>> lists;// unsure how I want to
														// handle lists and
														// arrays yet. We'll
														// deal with it later.
	private TreeMap<String, Object[]> arrays;

	private final String[] KEYWORDS = { "isPressed", "show", "announce",
			"answer", "question", "try", "otherwise", "if", "for", "forever",
			"while", "else", "launch", "repeat", "new", "construct", "to",
			"end", "clean", "createprojectvar", "mousepos", "stop", "namepage",
			"pagelist", "wait", "pick", "cancel", "int", "decimal", "string",
			"char", "list", "boolean", "array" };

	public Interpreter(String entireCode, ArrayList<Llama> l) {

		methods = new TreeMap<String, Method>();
		objects = new TreeMap<String, Llama>();

		// create the map of llama names to llamas from the arraylist
		for (Llama l1 : l) {
			objects.put(l1.getName(), l1);
		}

		// create the map of method names to method bodies

		String[] method = entireCode.split("to ");

		for (int i = 0; i < method.length; i++) {
			if (!method[i].trim().equals("") && method[i] != null) {
				String[] headerAndRest = method[i].split("\n", 2);
				// System.out.println(headerAndRest.length);
				String[] args = null;
				String[] andArgs = headerAndRest[0].split("(");
				if (andArgs.length > 1) {
					args = andArgs[1].split(",");

				}
				methods.put(headerAndRest[0].trim(), new Method(andArgs[0],
						args, headerAndRest[1], null));
			}
		}
		// System.out.println(methods);
	}

	public void interpret(String[] toBeInterpreted) {
		// basically, this method should split the input string into individual
		// commands, and then execute them appropriately.
		// it operates recursively - code is split down until it is executable
		// commands, which are then carried out.

		if (toBeInterpreted.length == 1) {// first we check if we have single
											// strings

			if (toBeInterpreted[0].contains("\n")) { // if the only item in the
														// array contains
														// newlines, we have a
														// new block of code
														// that we need to deal
														// with.
				String[] lines = toBeInterpreted[0].split("\n"); // divide the
																	// string
																	// into
																	// lines
				String[] temp = null;

				for (int i = 0; i < lines.length; i++) { // use a for loop to
															// address each line
															// directly. We will
															// split into upper
															// level containers
															// first, using
															// curly braces to
															// determine where
															// containers are
					if (lines[i].contains("{")) {// if this line has a curly
													// brace, everything up
													// until the next } as a
													// block of lines
						int j = i;
						while (true) {// i think this structure may be analagous
										// to a for loop, looking back, but who
										// gives a fuck?
							temp = new String[j - i];
							int count = 1;
							if (lines[j].contains("}")
									|| lines[j].contains("{")) {
								if (lines[j].contains("{")) {
									count++;
								} else {
									count--;
									if (count == 0) {
										for (int k = i; k <= j; k++) {
											temp[k - i] = lines[k];
										}
										interpret(temp);
										break;
									}
								}
							} else {
								j++;
							}
						}

					} else { // no {, so just send the line by itself.
						temp = new String[1];
						temp[0] = lines[i];
						interpret(temp);
					}
				}

			} else { // what if toBeInterpreted[0] does not contain a newline?
						// That means its a single line of code for us to
						// interpret!

				// need to figure out what commands need to be executed, then
				// pass them along to Leo's backend, in the proscribed format:
				// args[] and objArgs[]

			}

		} else { // here we have discovered that we've got multiple strings being
					// passed in. This means its a loop of some kind that we
					// need to deal with, so we check each loop keyword and run
					// through what needs to be done then

			// if first
			if (toBeInterpreted[0].startsWith("if")) {

			} else if (toBeInterpreted[0].startsWith("for")) { // for

			} else if (toBeInterpreted[0].startsWith("while")) { // while

			} else if (toBeInterpreted[0].startsWith("repeat")) { // repeat

			} else if (toBeInterpreted[0].startsWith("try")) { // try

			} else if (toBeInterpreted[0].startsWith("otherwise")) { // catch

			} else if (toBeInterpreted[0].startsWith("forever")) { // while(true)

			}

		}

	}
	
	public boolean evaluateBoolean(String b){
		
		
		
		String bool = b.trim();
		
		if(bool.startsWith("(")&&bool.endsWith(")")){
			return evaluateBoolean(bool.substring(1, bool.length() - 1));
		}
		
		if(bool.startsWith("not")){
			return !evaluateBoolean(bool.substring(3, bool.length()));
		}
		
		if(bool.contains("(")){//check for parens
			int count = 1;
			int spot1 = bool.indexOf('(');
			while(count > 0){
				if(bool.indexOf(')') <= bool.indexOf('(') || bool.indexOf('(') == -1){
					count--;
					spot1 = bool.indexOf(')');
				}else{
					count++;
					spot1 = bool.indexOf('(');
				}
			}
			if(bool.indexOf("and", spot1) > bool.indexOf('(')){
				return evaluateBoolean(bool.substring(0,bool.indexOf("and"))) && evaluateBoolean(bool.substring(bool.indexOf("and")));
				
			}else if(bool.indexOf("or", spot1) > bool.indexOf('(')){
				return evaluateBoolean(bool.substring(0,bool.indexOf("or"))) || evaluateBoolean(bool.substring(bool.indexOf("or")));
				
			}
			 
		}else { // we've reduced it to no parens!
			if(bool.contains("and")){
				String[] thingsToAnd = bool.split("and");
				//we're assuming this is only going to contain two things.
				thingsToAnd[0].trim();
				thingsToAnd[1].trim();
				
				return evaluateBoolean(thingsToAnd[0]) && evaluateBoolean(thingsToAnd[1]);
				
						
			} else if(bool.contains("or")){
				String[] thingsToAnd = bool.split("or");
				//we're assuming this is only going to contain two things.
				thingsToAnd[0].trim();
				thingsToAnd[1].trim();
				
				return evaluateBoolean(thingsToAnd[0]) || evaluateBoolean(thingsToAnd[1]);
				
						
			}else{
				//there are no nots, ands or ors. we just have to evaluate what's here.
				
				if(bool.contains("=")){
					String[] toCheck = bool.split("=");
					//assume two items
					toCheck[0].trim();
					toCheck[1].trim();
					
					boolean  prim1 = false, prim2 = false;
					int p1 = 0, p2 = -1;
					
					try{
						p1 = Integer.parseInt(toCheck[0]);
						prim1 = true;
					}catch(Exception e){
						
					}
					try{
						p2 = Integer.parseInt(toCheck[1]);
						prim2 = true;
					}catch(Exception e){
						
					}
					
					if(ints.containsKey(toCheck[0])||prim1){
						if(ints.containsKey(toCheck[1])||prim2){
							if(prim1 && prim2){
								return p1 == p2;
							}else if(prim1){
								return p1 == ints.get(toCheck[1]);
							}
							else if(prim2){
								return p2 == ints.get(toCheck[0]);
							}else{
								return ints.get(toCheck[0]) == ints.get(toCheck[1]);
							}
						}
					}
					
				}else if(bool.contains(">")){
					
				}else if(bool.contains("<")){
					
				}else if(bool.contains(">=")){
					
				}else if(bool.contains("<=")){
					
				}
				
			}
		}
	}

	public static void main(String[] args) {
		ArrayList<Llama> l = new ArrayList<Llama>();
		String s = "to eat\nafiejfije\nafefjiafie\nieflaifj\nend\n\n\nto haveCookies\nsetheija;fij\n iejifaj \neifaj\nend\n";
		new Interpreter(s, l);
	}

}
