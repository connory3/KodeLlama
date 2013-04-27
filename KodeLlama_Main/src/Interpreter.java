import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

//@Martin Kellogg; April 2013

/*
 TODO list:


 if DONE
 while DONE
 try/otherwise
 for
 repeat DONE
 forever DONE

 launch

 SEND DATA TO MATTHEW

 new DONE for int, decimal
 stop DONE
 variable increments DONE for int, decimal

 method calls

 */
public class Interpreter implements Runnable {
	private TreeMap<String, Llama> objects;
	private TreeMap<String, Method> methods;
	private TreeMap<String, Integer> ints;
	private TreeMap<String, Boolean> bools;
	private TreeMap<String, String> strings;
	private TreeMap<String, Double> decimals;
	// private TreeMap<String, ArrayList<Object>> lists;// unsure how I want to
														// handle lists and
														// arrays yet. We'll
														// deal with it later.
	// private TreeMap<String, Object[]> arrays;

	private TreeMap<String, Llama> objReferences;

	private String entireCode;
	//private ArrayList<Llama> l7;

	private boolean executeAll;

	private MainWindow m;

	private boolean newThreadForever = false;

	private Thread sigma;

	/*
	 * private final String[] KEYWORDS = { "isPressed", "show", "announce",
	 * "answer", "question", "try", "otherwise", "if", "for", "forever",
	 * "while", "else", "launch", "repeat", "new", "construct", "to", "end",
	 * "clean", "createprojectvar", "mousepos", "stop", "namepage", "pagelist",
	 * "wait", "pick", "cancel", "int", "decimal", "string", "char", "list",
	 * "boolean", "array" };
	 */

	//private Thread t;

	private String[] commandLineArgs;

	public void run() {

		if (executeAll) {
			String[] toPass = new String[1];
			toPass[0] = entireCode;

			if (newThreadForever) {
				while (this.interpret(toPass)) {

				}
				;

			} else {
				this.interpret(toPass);
			}
		} else {
			//System.out.println("DO NOT EXECUTE ALL");
			interpret(commandLineArgs);
		}
		Thread.currentThread().interrupt();
	}

	public Interpreter(String eC, TreeMap<String, Integer> i,
			TreeMap<String, Double> d, TreeMap<String, String> s,
			TreeMap<String, Boolean> b, TreeMap<String, Llama> crystal,
			TreeMap<String, Llama> r, TreeMap<String, Method> meth,
			MainWindow m1) {

		entireCode = eC;
		executeAll = true;

		methods = meth;
		objects = crystal;

		ints = i;
		decimals = d;
		strings = s;
		bools = b;

		m = m1;

		objReferences = r;

	}

	public Interpreter(String eC, ArrayList<Llama> l, MainWindow m1, boolean ex) {

		executeAll = ex;

		//l7 = l;
		entireCode = eC;

		methods = new TreeMap<String, Method>();
		objects = new TreeMap<String, Llama>();

		objReferences = new TreeMap<String, Llama>();

		ints = new TreeMap<String, Integer>();
		decimals = new TreeMap<String, Double>();
		strings = new TreeMap<String, String>();
		bools = new TreeMap<String, Boolean>();
		m = m1;

		// create the map of llama names to llamas from the arraylist
		for (Llama l1 : l) {
			objects.put(l1.name, l1);
		}

		// create the map of method names to method bodies

		String[] method = entireCode.split("to ");
		for (int i = 0; i < method.length; i++) {
			if (!method[i].trim().equals("") && method[i] != null) {
				String[] headerAndRest = method[i].split("\n", 2);
				// System.out.println(headerAndRest.length);
				String[] args = { "()" };
				String[] andArgs = headerAndRest[0].split("\\(");
				if (andArgs.length > 1) {
					args = andArgs[1].split(",");
					if (args[args.length - 1].endsWith(")")) {
						args[args.length - 1] = args[args.length - 1]
								.substring(0,
										args[args.length - 1].length() - 1);
					}
				}

				for (int k = 0; k < args.length; k++) {
					args[k] = args[k].trim();

					if (args[k].endsWith(")")) {
						args[k] = args[k].substring(0, args[k].length() - 1);
					}

				}

				headerAndRest[0] = headerAndRest[0].substring(0,
						headerAndRest[0].indexOf("(") + 1);
				methods.put(headerAndRest[0].trim(), new Method(andArgs[0],
						args, headerAndRest[1], null));

			}
		}
		//start();

		// System.out.println(methods.keySet());

		// for testing

		// System.out.println(methods);
	}

	/*public boolean start() {
		sigma = new Thread(this);

		sigma.start();
		return true;
	}*/

	public Interpreter(String eC, TreeMap<String, Llama> crystal,
			TreeMap<String, Method> meth, MainWindow m1) {

		executeAll = true;
		entireCode = eC;
		m = m1;
		methods = new TreeMap<String, Method>();
		objects = crystal;

		objReferences = new TreeMap<String, Llama>();

		ints = new TreeMap<String, Integer>();
		decimals = new TreeMap<String, Double>();
		strings = new TreeMap<String, String>();
		bools = new TreeMap<String, Boolean>();

		// create the map of llama names to llamas from the arraylist

		// create the map of method names to method bodies

		methods = meth;

		// for testing

		// System.out.println(methods);
	}

	public void stop() {
		/*
		try {
			sigma.interrupt();
			// sigma.stop();
		} catch (NullPointerException e) {
			System.out.println("sigma");
			Thread.currentThread().interrupt();
		}
		try {
			t.interrupt();
			// t.stop();
		} catch (NullPointerException e) {
			System.out.println("t");
			Thread.currentThread().interrupt();
		}*/
		try{
			sigma.interrupt();
		}catch(Exception e){
			
		}
		Thread.currentThread().interrupt();
	}

	
	  public void passArgs(String[] toBeInterpreted) { 
		  commandLineArgs = toBeInterpreted; 
		  }
	 

	public boolean interpret(String[] toBeInterpreted) {
		// Thread.currentThread();
		// basically, this method should split the input string into individual
		// commands, and then execute them appropriately.
		// it operates recursively - code is split down until it is executable
		// commands, which are then carried out.
		try {
			Thread.sleep(1);
		} catch (InterruptedException e1) {
			stop();
			Thread.currentThread().interrupt();
			return false;
		}
		boolean interrupted = false;
		try{
			interrupted = sigma.isInterrupted();
		}catch(NullPointerException e){
			interrupted = Thread.currentThread().isInterrupted();
		}
		//System.out.println("sigma.interrupted: " + interrupted);
		if (interrupted) {
			//System.out.println("Returning false...");
			stop();
			Thread.currentThread().interrupt();
			return false;
		}
		if (toBeInterpreted[0].equals("") || toBeInterpreted[0].equals("\n")) {
			return true;
		}

		System.out.println("interpret called:\n"
				+ Arrays.toString(toBeInterpreted));

		// update all llama variables

		objects = m.getLlamas();
		
		ints.put("mousex", m.getMouseX());
		ints.put("mousey", m.getMouseY());
		
		

		for (String l9 : objReferences.keySet()) {
			if (!objects.containsKey(l9)) {
				objects.put(l9, objReferences.get(l9));
			}
		}

		for (String s : objects.keySet()) {
			ints.put(s + ".x", objects.get(s).x);
			ints.put(s + ".y", objects.get(s).y);
			decimals.put(s + ".direction", objects.get(s).direction);
			// ints.put(s+".color", objects.get(s).color);
		}

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
						int count = 0;
						boolean control = true;
						while (control) {// i think this structure may be
											// analagous
											// to a for loop, looking back, but
											// who
											// gives a fuck?

							// System.out.println(count);
							temp = new String[j - i];

							if (lines[j].contains("}")
									|| lines[j].contains("{")) {
								if (lines[j].contains("{")) {
									count++;
								} else {
									count--;
									if (count == 0) {

										for (int k = i; k < j; k++) {

											temp[k - i] = lines[k];
										}

										interpret(temp);
										i = j;

										control = false;
									}
								}
							}
							j++;
						}

					} else { // no {, so just send the line by itself.
						if (!lines[i].equals("")) {
							temp = new String[1];
							temp[0] = lines[i];
							interpret(temp);
						}
					}
				}

			} else { // what if toBeInterpreted[0] does not contain a newline?
						// That means its a single line of code for us to
						// interpret!

				// need to figure out what commands need to be executed, then
				// pass them along to the backend, in the proscribed format:
				// args[] and objArgs[]

				if (!toBeInterpreted[0].startsWith("to")) {

					if (toBeInterpreted[0].startsWith("stop")) {
						// shut it all down!

						System.out.println("SHUTTING DOWN");
						// Thread.currentThread().stop(); // so, this method is
						// depracated and I
						// totally
						// shouldn't use it, but
						// it
						// gets the job done...
						stop();
						return false;

					} else if (toBeInterpreted[0].startsWith("wait")) {
						int firstParen = toBeInterpreted[0].indexOf('(');
						int lastParen = toBeInterpreted[0].lastIndexOf(')') + 1;
						int something = assertInt(new Calculator().calculate(
								toBeInterpreted[0].substring(firstParen,
										lastParen), decimals, ints));
						try {
							Thread.sleep(something);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if (toBeInterpreted[0].startsWith("int ")) {
						String bob = toBeInterpreted[0].substring(3);
						bob = bob.trim();
						if (bob.contains("=")) {
							ints.put(bob.substring(0, bob.indexOf("=")).trim(),
									assertInt(new Calculator().calculate(bob
											.substring(bob.indexOf("=") + 1)
											.trim(), decimals, ints)));
						} else {
							ints.put(bob, 0);
						}
					} else if (toBeInterpreted[0].startsWith("decimal ")) {
						String bob = toBeInterpreted[0].substring(7);
						bob = bob.trim();
						if (bob.contains("=")) {
							decimals.put(bob.substring(0, bob.indexOf("="))
									.trim(), new Calculator().calculate(bob
									.substring(bob.indexOf("=") + 1).trim(),
									decimals, ints));
						} else {
							decimals.put(bob, 0.0);
						}

					}else if (toBeInterpreted[0].startsWith("string ")) {
						String bob = toBeInterpreted[0].substring(6);
						bob = bob.trim();
						if (bob.contains("=")) {
							strings.put(bob.substring(0, bob.indexOf("="))
									.trim(), bob.substring(bob.indexOf("=")+1).trim());
						} else {
							strings.put(bob, "");
						}

					}else if (toBeInterpreted[0].startsWith("Llama ")) {
						String bob = toBeInterpreted[0].substring(5);
						bob = bob.trim();
						
						m.newFieldLlama2(bob);

					}else if (toBeInterpreted[0].startsWith("boolean ")) {
						String bob = toBeInterpreted[0].substring(7);
						System.out.println("new boolean created!");
						bob = bob.trim();
						if (bob.contains("=")) {
							bools.put(bob.substring(0, bob.indexOf("=")).trim(), evaluateBoolean(bob.substring(bob.indexOf("=") + 1)));
							System.out.println("with name: "+bob.substring(0, bob.indexOf("=")).trim()+" and with value: "+bools.get(bob.substring(0, bob.indexOf("=")).trim()));
						} else {
							bools.put(bob, false);
							System.out.println(" with value: "+bools.get(bob));
						}
						

					} else {// end of new variable declarations

						// check if the line starts with a variable, in which
						// case
						// we're
						// going to make things happen to it
						boolean variable = false;
						for (String s : ints.keySet()) {
							if (toBeInterpreted[0].startsWith(s+" =")||toBeInterpreted[0].startsWith(s+"=")) {
								variable = true;
								ints.put(s, assertInt(new Calculator()
										.calculate(toBeInterpreted[0]
												.substring(toBeInterpreted[0]
														.indexOf("=") + 1),
												decimals, ints)));
							}
						}

						for (String s : decimals.keySet()) {
							if (toBeInterpreted[0].startsWith(s+" =")||toBeInterpreted[0].startsWith(s+"=")) {
								variable = true;
								System.out.println("Decimals true");
								decimals.put(s, new Calculator().calculate(
										toBeInterpreted[0]
												.substring(toBeInterpreted[0]
														.indexOf("=") + 1),
										decimals, ints));
							}
						}
						for (String s : bools.keySet()) {
							if (toBeInterpreted[0].startsWith(s+" =")||toBeInterpreted[0].startsWith(s+"=")) {
								variable = true;
								System.out.println("bools true");
								bools.put(s, evaluateBoolean(
										toBeInterpreted[0]
												.substring(toBeInterpreted[0]
														.indexOf("=") + 1))
										);
							}
						}
						for (String s : strings.keySet()) {
							if (toBeInterpreted[0].startsWith(s+" =")||toBeInterpreted[0].startsWith(s+"=")) {
								variable = true;
								System.out.println("strings true");
								
								String q = toBeInterpreted[0].substring(toBeInterpreted[0].indexOf("=") + 1);
								if(strings.containsKey(q)){
									strings.put(s, strings.get(q));
								}else{
									strings.put(s, toBeInterpreted[0]
												.substring(toBeInterpreted[0]
														.indexOf("=") + 1).trim());
								}
								
								/*strings.put(s, stringOps(
										toBeInterpreted[0]
												.substring(toBeInterpreted[0]
														.indexOf("=") + 1))
										);*/
								//strings not yet supported; all strings are final!
								//but I added support for changing them into other strings...
							}
						}
						for (String s : objects.keySet()) {
							if (toBeInterpreted[0].startsWith(s+" =")||toBeInterpreted[0].startsWith(s+"=")) {
								variable = true;
								System.out.println("llamas true");
								
								String q = toBeInterpreted[0].substring(toBeInterpreted[0].indexOf("=") + 1);
								if(objects.containsKey(q)){
									objects.put(s, objects.get(q));
								}
								
								
							}
						}
						
						
						for (String s : methods.keySet()) {
							if (toBeInterpreted[0].startsWith(s)) {
								variable = true;
								System.out.println("Methods true");
								// execute the method call
								int firstParen = toBeInterpreted[0]
										.indexOf('(');
								int lastParen = toBeInterpreted[0]
										.lastIndexOf(')') + 1;

								String arg = toBeInterpreted[0].substring(
										firstParen, lastParen);
								// this string represents the arguments to pass
								String[] splitArgs = arg.split(",");
								Object[] toPass = new Object[splitArgs.length];

								for (int i = 0; i < splitArgs.length; i++) {
									// okay, so we need to go through each of
									// these
									// things
									// and see what it is. We'll look through
									// our
									// lists
									// of
									// variables first!
									String toCheck = splitArgs[i].trim();

									if (toCheck.startsWith("(")) {
										toCheck = toCheck.substring(1);
									}
									if (toCheck.endsWith("{")) {
										toCheck = toCheck.substring(0,
												toCheck.length() - 1);
									}

									if (toCheck.endsWith(")")) {
										toCheck = toCheck.substring(0,
												toCheck.length() - 1);
									}

									System.out.println("toCheck is " + toCheck);

									if (ints.containsKey(toCheck)) {
										toPass[i] = new Integer(
												ints.get(toCheck));
									} else if (decimals.containsKey(toCheck)) {
										toPass[i] = new Double(
												decimals.get(toCheck));
									} else if (strings.containsKey(toCheck)) {
										toPass[i] = strings.get(toCheck);
									} else if (bools.containsKey(toCheck)) {
										toPass[i] = new Boolean(
												bools.get(toCheck));
									} else if (objects.containsKey(toCheck)) {
										toPass[i] = objects.get(toCheck);
									} else {// if we get in here, then whatever
											// we're
											// looking at isn't in one of lists
											// of
											// variables, at least not
											// exclusively

										// this boolean determines if we can
										// keep
										// going
										// or
										// if we've found what we're looking for
										boolean keepGoing = true;

										try {// let's check if its numerical
											double d = new Calculator()
													.calculate(toCheck,
															decimals, ints);
											toPass[i] = new Double(d);
											keepGoing = false;

										} catch (Exception e) {
											// no good, move on
										}
										if (keepGoing) {
											// now we need to check if its an
											// object's
											// method, which we need to call
											try {
												// String objName = toCheck
														// .split(".")[0].trim();
												String commandName = toCheck
														.split(".")[1].trim();
												keepGoing = false;
												if (commandName.equals("x")) {
													// toPass[i] = new
													// Integer(objects.get(objName).getX());
												} else if (commandName
														.equals("y")) {
													// toPass[i] = new
													// Integer(objects.get(objName).getY());
												} else if (commandName
														.equals("direction")) {
													// toPass[i] = new
													// Integer(objects.get(objName).getDirection());
												} else if (commandName
														.equals("color")) {
													// toPass[i] = new
													// Integer(objects.get(objName).getColor());
												} else {
													keepGoing = true;
												}

											} catch (Exception e) {

											}
											if (keepGoing) {
												// it isn't any of the previous
												// things:
												// a
												// numerical expression, a llama
												// command, or
												// a variable. Now we should be
												// able
												// to
												// just
												// check for boolean and string
												// literals
												try {
													toPass[i] = parseBoolean(toCheck);
													keepGoing = false;
												} catch (Exception e) {

												}
												if (keepGoing) {
													toPass[i] = toCheck;
												}
											}
										}

									}
								}

								// okay, so I now have an array of Objects that
								// represent the objects to pass. Now I just
								// have to sort them

								TreeMap<String, Llama> l = new TreeMap<String, Llama>();
								TreeMap<String, Integer> in = ints;
								TreeMap<String, Double> d = decimals;
								TreeMap<String, String> s1 = strings;
								TreeMap<String, Boolean> b = bools;

								if (!(toPass[0].equals("()"))) {
									for (int i = 0; i < toPass.length; i++) {
										if (toPass[i] instanceof Integer) {
											in.put(methods.get(s).getArgs()[i],
													(Integer) toPass[i]);
										} else if (toPass[i] instanceof Llama) {
											System.out.println("mark:"
													+ Arrays.toString(methods
															.get(s).getArgs()));
											l.put(methods.get(s).getArgs()[i],
													(Llama) toPass[i]);
										} else if (toPass[i] instanceof Double) {
											d.put(methods.get(s).getArgs()[i],
													(Double) toPass[i]);
										} else if (toPass[i] instanceof Boolean) {
											b.put(methods.get(s).getArgs()[i],
													(Boolean) toPass[i]);
										} else if (toPass[i] instanceof String) {
											s1.put(methods.get(s).getArgs()[i],
													(String) toPass[i]);
										}
									}
								}

								// System.out.println(d.toString());

								Interpreter iNew = new Interpreter(methods.get(
										s).getBody(), in, d, s1, b, objects, l,
										methods, m);
								iNew.run();

								// do this we have to split the argument line by
								// commas,
								// then send the data along to a new
								// interpreter, and start it in a new thread.
								// TODO

							}
						}
						if (variable)
							System.out.println("true");
						if (!variable) {
							// if we get down here, we're going to assume that
							// we
							// can
							// send
							// this data to Matthew's GUI

							if (objects.containsKey(toBeInterpreted[0]
									.split("\\.")[0])) {
								int firstParen = toBeInterpreted[0]
										.indexOf('(');
								int lastParen = toBeInterpreted[0]
										.lastIndexOf(')') + 1;
								String s = toBeInterpreted[0].substring(
										firstParen, lastParen);
								if (s.length() > 2) {
									try {
										s = new Calculator().calculate(
												toBeInterpreted[0].substring(
														firstParen, lastParen),
												decimals, ints)
												+ "";
										toBeInterpreted[0] = toBeInterpreted[0]
												.substring(0, firstParen)
												+ "("
												+ (int) (Double.parseDouble(s))
												+ ")";
									} catch (IllegalArgumentException e) {
										//look for variables - strings and llamas aren't covered yet
										
										if(strings.containsKey(s)){
											
										}
										
										
										try{
											s = ""+parseBoolean(toBeInterpreted[0].substring(
														firstParen, lastParen));
										}catch(Exception e1){
											
										}
									}
								}
								m.execute(toBeInterpreted[0], null);
							} else {

								int firstParen = toBeInterpreted[0]
										.indexOf('(');
								int lastParen = toBeInterpreted[0]
										.lastIndexOf(')') + 1;

								String arg = toBeInterpreted[0].substring(
										firstParen, lastParen);
								// this string represents the arguments to pass
								String[] splitArgs = arg.split(",");
								Object[] toPass = new Object[splitArgs.length];

								for (int i = 0; i < splitArgs.length; i++) {
									// okay, so we need to go through each of
									// these
									// things
									// and see what it is. We'll look through
									// our
									// lists
									// of
									// variables first!
									String toCheck = splitArgs[i];
									
									System.out.println("checking " +toCheck);
									System.out.println("strings is "+strings.toString());

									if (ints.containsKey(toCheck)) {
										toPass[i] = new Integer(
												ints.get(toCheck));
									} else if (decimals.containsKey(toCheck)) {
										toPass[i] = new Double(
												decimals.get(toCheck));
									} else if (strings.containsKey(toCheck)) {
										System.out.println("converted");
										toPass[i] = strings.get(toCheck);
									} else if (bools.containsKey(toCheck)) {
										toPass[i] = new Boolean(
												bools.get(toCheck));
									} else if (objects.containsKey(toCheck)) {
										toPass[i] = objects.get(toCheck);
									} else {// if we get in here, then whatever
											// we're
											// looking at isn't in one of lists
											// of
											// variables, at least not
											// exclusively

										// this boolean determines if we can
										// keep
										// going
										// or
										// if we've found what we're looking for
										boolean keepGoing = true;

										try {// let's check if its numerical
											double d = new Calculator()
													.calculate(toCheck,
															decimals, ints);
											toPass[i] = new Double(d);
											keepGoing = false;

										} catch (Exception e) {
											// no good, move on
										}
										if (keepGoing) {
											// now we need to check if its an
											// object's
											// method, which we need to call
											try {
												// String objName = toCheck
														// .split(".")[0].trim();
												String commandName = toCheck
														.split(".")[1].trim();
												keepGoing = false;
												if (commandName.equals("x")) {
													// toPass[i] = new
													// Integer(objects.get(objName).getX());
												} else if (commandName
														.equals("y")) {
													// toPass[i] = new
													// Integer(objects.get(objName).getY());
												} else if (commandName
														.equals("direction")) {
													// toPass[i] = new
													// Integer(objects.get(objName).getDirection());
												} else if (commandName
														.equals("color")) {
													// toPass[i] = new
													// Integer(objects.get(objName).getColor());
												} else {
													keepGoing = true;
												}

											} catch (Exception e) {

											}
											if (keepGoing) {
												// it isn't any of the previous
												// things:
												// a
												// numerical expression, a llama
												// command, or
												// a variable. Now we should be
												// able
												// to
												// just
												// check for boolean and string
												// literals
												try {
													toPass[i] = parseBoolean(toCheck);
													keepGoing = false;
												} catch (Exception e) {

												}
												if (keepGoing) {
													toPass[i] = toCheck;
												}
											}
										}

									}
								}

								String keyWord = toBeInterpreted[0].substring(
										0, firstParen + 1).trim();

								for (int q = 0; q < toPass.length; q++) {
									if (toPass[q] instanceof String) {
										if (((String) toPass[q])
												.startsWith("(")) {
											toPass[q] = ((String) toPass[q])
													.substring(1);
										}
										if (((String) toPass[q]).endsWith(")")) {
											toPass[q] = ((String) toPass[q])
													.substring(
															0,
															((String) toPass[q])
																	.length() - 1);
										}
									}
								}

								m.execute(keyWord, toPass);

							}
						}
					}
				}

				// System.out.println("doing " + toBeInterpreted[0]);
			}

		} else { // here we have discovered that we've got multiple strings
					// being
					// passed in. This means its a loop of some kind that we
					// need to deal with, so we check each loop keyword and run
					// through what needs to be done then

			// if first
			if (toBeInterpreted[0].startsWith("if")) {

				// we have an if statement
				// we need to find the boolean expression we're evaluating:

				int firstParen = toBeInterpreted[0].indexOf('(');
				int lastParen = toBeInterpreted[0].lastIndexOf(')') + 1;

				if (evaluateBoolean(toBeInterpreted[0].substring(firstParen,
						lastParen))) {
					System.out.println("true");

					String[] toPass = new String[toBeInterpreted.length - 1];
					for (int i = 1; i < toBeInterpreted.length; i++) {
						toPass[i - 1] = toBeInterpreted[i];
					}
					// we would like to just call interpret on each member of
					// toPass, but that would be too simple - it will cause us
					// difficulties. Instead we will flatten it so that it is
					// interpreted using the code above to find loops!
					interpret(flatten(toPass));
				}

			} /*
			 * else if (toBeInterpreted[0].startsWith("while")) { // while
			 * 
			 * int firstParen = toBeInterpreted[0].indexOf('('); int lastParen =
			 * toBeInterpreted[0].lastIndexOf(')') + 1;
			 * 
			 * while (evaluateBoolean(toBeInterpreted[0].substring(firstParen,
			 * lastParen))) { // System.out.println("true");
			 * 
			 * String[] toPass = new String[toBeInterpreted.length - 1]; for
			 * (int i = 1; i < toBeInterpreted.length; i++) { toPass[i - 1] =
			 * toBeInterpreted[i]; } // we would like to just call interpret on
			 * each member of // toPass, but that would be too simple - it will
			 * cause us // difficulties. Instead we will flatten it so that it
			 * is // interpreted using the code above to find loops!
			 * interpret(flatten(toPass)); }
			 * 
			 * } else if (toBeInterpreted[0].startsWith("repeat")) { // repeat
			 * 
			 * int firstParen = toBeInterpreted[0].indexOf('('); int lastParen =
			 * toBeInterpreted[0].lastIndexOf(')') + 1;
			 * 
			 * String inp = toBeInterpreted[0] .substring(firstParen,
			 * lastParen); int q = assertInt(new Calculator().calculate(inp,
			 * decimals, ints));
			 * 
			 * String[] toPass = new String[toBeInterpreted.length - 1]; for
			 * (int i = 1; i < toBeInterpreted.length; i++) { toPass[i - 1] =
			 * toBeInterpreted[i]; }
			 * 
			 * for (int i = 0; i < q; i++) { this.interpret(flatten(toPass)); }
			 */

			else if (toBeInterpreted[0].startsWith("while")) { // while

				int firstParen = toBeInterpreted[0].indexOf('(');
				int lastParen = toBeInterpreted[0].lastIndexOf(')') + 1;
				boolean keepGoing = true;
				while (keepGoing
						&& evaluateBoolean(toBeInterpreted[0].substring(
								firstParen, lastParen))) {
					// System.out.println("true");

					String[] toPass = new String[toBeInterpreted.length - 1];
					for (int i = 1; i < toBeInterpreted.length; i++) {
						toPass[i - 1] = toBeInterpreted[i];
					}
					// we would like to just call interpret on each member of
					// toPass, but that would be too simple - it will cause us
					// difficulties. Instead we will flatten it so that it is
					// interpreted using the code above to find loops!
					keepGoing = interpret(flatten(toPass));
				}

			} else if (toBeInterpreted[0].startsWith("repeat")) { // repeat

				int firstParen = toBeInterpreted[0].indexOf('(');
				int lastParen = toBeInterpreted[0].lastIndexOf(')') + 1;

				String inp = toBeInterpreted[0]
						.substring(firstParen, lastParen);
				int q = assertInt(new Calculator().calculate(inp, decimals,
						ints));

				String[] toPass = new String[toBeInterpreted.length - 1];
				for (int i = 1; i < toBeInterpreted.length; i++) {
					toPass[i - 1] = toBeInterpreted[i];
				}

				boolean keepGoing = true;

				for (int i = 0; i < q; i++) {
					if (keepGoing) {
						keepGoing = this.interpret(flatten(toPass));
					}
				}

			} else if (toBeInterpreted[0].startsWith("try")) { // try &
																// otherwise

				// we need to split into the "try" bit and the "otherwise" bit

				int theOne = -1;
				for (int i = 0; i < toBeInterpreted.length; i++) {
					if (toBeInterpreted[i].contains("otherwise")) {
						theOne = i;
					}
				}

				String[] tryStuff = new String[theOne - 1];
				String[] catchStuff = new String[toBeInterpreted.length
						- theOne - 1];

				for (int i = 0; i < theOne - 1; i++) {
					tryStuff[i] = toBeInterpreted[i + 1];
				}
				for (int i = theOne + 1; i < toBeInterpreted.length; i++) {
					catchStuff[i - (theOne + 1)] = toBeInterpreted[i];
				}

				System.out.println("try: " + Arrays.toString(tryStuff));
				System.out.println("catch: " + Arrays.toString(catchStuff));

				try {
					interpret(flatten(tryStuff));
				} catch (Exception e) {
					interpret(flatten(catchStuff));
				}

			} else if (toBeInterpreted[0].startsWith("forever")) {
				// while (true) {
				// System.out.println("true");
				/*
				 * String[] toPass = new String[toBeInterpreted.length - 1]; for
				 * (int i = 1; i < toBeInterpreted.length; i++) { toPass[i - 1]
				 * = toBeInterpreted[i]; } Interpreter i = new
				 * Interpreter((extraFlatten(toPass)), objects, methods, m);
				 * children.add(i); i.newThreadForever = true; t = new
				 * Thread(i);
				 * 
				 * t.start();
				 */
				// }

				toBeInterpreted[0] = toBeInterpreted[0].replace("forever",
						"while(true)");
				this.interpret(toBeInterpreted);
			} else if (toBeInterpreted[0].startsWith("for")) { // for

				// int firstParen = toBeInterpreted[0].indexOf('(');
				// int lastParen = toBeInterpreted[0].lastIndexOf(')') + 1;
				// String forStuff = toBeInterpreted[0].substring(firstParen,
						// lastParen);
				// String[] forComponents = forStuff.split(";");

				// forComponents is the three parts of a for loop

			}

		}
		return true;

	}

	public boolean evaluateBoolean(String b) {

		String bool = b.trim();
		System.out.println("evaluate boolean has been called on " + bool);

		if (bool.startsWith("(") && bool.endsWith(")")) {

			if (!bool.contains("and") && !bool.contains("or"))
				return evaluateBoolean(bool.substring(1, bool.length() - 1));
			else {
				int count = 1;
				int spot1 = 1;
				boolean getsToZero = false;
				while (count > 0) {
					// System.out.println(count);
					if (bool.indexOf(')', spot1) <= bool.indexOf('(', spot1)
							|| bool.indexOf('(', spot1) == -1) {
						count--;
						spot1 = bool.indexOf(')', spot1) + 1;
						if (count == 0 && spot1 < bool.length()) {
							getsToZero = true;
						}
						// System.out.println("here");
					} else {
						count++;
						spot1 = bool.indexOf('(', spot1) + 1;
					}
				}
				if (!getsToZero) {
					return evaluateBoolean(bool.substring(1, bool.length() - 1));
				}
			}
		}

		if (bool.startsWith("not")) {
			return !evaluateBoolean(bool.substring(3, bool.length()));
		}

		if (bool.contains("(")) {// check for parens
			int count = 1;
			int spot1 = bool.indexOf('(') + 1;
			int originalSpot1 = spot1;
			while (count > 0) {
				// System.out.println(count);
				if (bool.indexOf(')', spot1) <= bool.indexOf('(', spot1)
						|| bool.indexOf('(', spot1) == -1) {
					count--;

					spot1 = bool.indexOf(')', spot1) + 1;

					// System.out.println("here");
				} else {
					count++;
					spot1 = bool.indexOf('(', spot1) + 1;
				}
			}
			if (originalSpot1 > bool.indexOf("or")
					&& originalSpot1 > bool.indexOf("and")
					&& (bool.indexOf("and") != -1 || bool.indexOf("or") != -1)) {
				spot1 = 0;
			}

			if (((bool.indexOf("and", spot1) != -1 && bool
					.indexOf("and", spot1) < bool.indexOf('(', spot1)) || (bool
					.indexOf("and", spot1) != -1 && bool.indexOf('(', spot1) == -1))
					&& (bool.indexOf("and", spot1) > spot1 || bool.indexOf(
							"and", spot1) < originalSpot1)) {

				return (evaluateBoolean(bool.substring(0,
						bool.indexOf("and", spot1))) && evaluateBoolean(bool
						.substring(bool.indexOf("and", spot1) + 3)));

			} else if (((bool.indexOf("or", spot1) != -1 && bool.indexOf("or",
					spot1) < bool.indexOf('(', spot1)) || (bool.indexOf("or",
					spot1) != -1 && bool.indexOf('(', spot1) == -1))
					&& (bool.indexOf("or", spot1) > spot1 || bool.indexOf("or",
							spot1) < originalSpot1)) {

				return (evaluateBoolean(bool.substring(0,
						bool.indexOf("or", spot1))) || evaluateBoolean(bool
						.substring(bool.indexOf("or", spot1) + 2)));

			}

		} else { // we've reduced it to no parens!
			if (bool.contains("and")) {
				String[] thingsToAnd = bool.split("and");
				// we're assuming this is only going to contain two things.
				thingsToAnd[0].trim();
				thingsToAnd[1].trim();

				return evaluateBoolean(thingsToAnd[0])
						&& evaluateBoolean(thingsToAnd[1]);

			} else if (bool.contains("or")) {
				String[] thingsToAnd = bool.split("or");
				// we're assuming this is only going to contain two things.
				thingsToAnd[0].trim();
				thingsToAnd[1].trim();

				return evaluateBoolean(thingsToAnd[0])
						|| evaluateBoolean(thingsToAnd[1]);

			} else {
				// there are no nots, ands or ors. we just have to evaluate
				// what's here.
				if (bool.contains(">=")) {
					String[] toCheck = bool.split(">=");
					// assume two items
					toCheck[0] = toCheck[0].trim();
					toCheck[1] = toCheck[1].trim();

					// we only care about ints and doubles, so we'll ignore
					// other cases!

					boolean prim1 = false, prim2 = false;
					double p11 = 0., p21 = -1.;

					try {
						p11 = new Calculator().calculate(toCheck[0], decimals,
								ints);
						prim1 = true;
					} catch (Exception e) {

					}
					try {
						p21 = new Calculator().calculate(toCheck[1], decimals,
								ints);

						prim2 = true;
					} catch (Exception e) {

					}

					if (decimals.containsKey(toCheck[0]) || prim1) {
						if (decimals.containsKey(toCheck[1]) || prim2) {
							if (prim1 && prim2) {
								return p11 >= p21;
							} else if (prim1) {
								return p11 >= decimals.get(toCheck[1]);
							} else if (prim2) {
								return p21 >= decimals.get(toCheck[0]);
							} else {
								return decimals.get(toCheck[0]) >= decimals
										.get(toCheck[1]);
							}
						}
						return false;

					}
					prim1 = false;
					prim2 = false;
					int p1 = 0, p2 = -1;

					try {
						p1 = assertInt(new Calculator().calculate(toCheck[0],
								decimals, ints));

						prim1 = true;
					} catch (Exception e) {

					}
					try {
						p2 = assertInt(new Calculator().calculate(toCheck[1],
								decimals, ints));

						prim2 = true;
					} catch (Exception e) {

					}

					// next, we check if we do, in fact, have integers, and if
					// so, whether they're equal to each other

					if (ints.containsKey(toCheck[0]) || prim1) {
						if (ints.containsKey(toCheck[1]) || prim2) {
							if (prim1 && prim2) {
								return p1 >= p2;
							} else if (prim1) {
								return p1 >= ints.get(toCheck[1]);
							} else if (prim2) {
								return p2 >= ints.get(toCheck[0]);
							} else {
								return ints.get(toCheck[0]) >= ints
										.get(toCheck[1]);
							}
						}
						// in the event that one is an integer and the other is
						// not - then we return false.
						return false;
					}
					return false;
					// if there aren't ints or doubles (i.e. we've gotten this
					// far!), we're assuming that this is just false.

				} else if (bool.contains("<=")) {
					String[] toCheck = bool.split("<=");
					// assume two items
					toCheck[0] = toCheck[0].trim();
					toCheck[1] = toCheck[1].trim();

					// we only care about ints and doubles, so we'll ignore
					// other cases!

					boolean prim1 = false, prim2 = false;
					double p11 = 0., p21 = -1.;

					try {
						p11 = new Calculator().calculate(toCheck[0], decimals,
								ints);

						prim1 = true;
					} catch (Exception e) {

					}
					try {
						p21 = new Calculator().calculate(toCheck[1], decimals,
								ints);

						prim2 = true;
					} catch (Exception e) {

					}

					if (decimals.containsKey(toCheck[0]) || prim1) {
						if (decimals.containsKey(toCheck[1]) || prim2) {
							if (prim1 && prim2) {
								return p11 <= p21;
							} else if (prim1) {
								return p11 <= decimals.get(toCheck[1]);
							} else if (prim2) {
								return p21 <= decimals.get(toCheck[0]);
							} else {
								return decimals.get(toCheck[0]) <= decimals
										.get(toCheck[1]);
							}
						}
						return false;

					}
					prim1 = false;
					prim2 = false;
					int p1 = 0, p2 = -1;

					try {
						p1 = assertInt(new Calculator().calculate(toCheck[0],
								decimals, ints));

						prim1 = true;
					} catch (Exception e) {

					}
					try {
						p2 = assertInt(new Calculator().calculate(toCheck[1],
								decimals, ints));

						prim2 = true;
					} catch (Exception e) {

					}
					// System.out.println(p1 + "    " + p2);
					// next, we check if we do, in fact, have integers, and if
					// so, whether they're equal to each other

					if (ints.containsKey(toCheck[0]) || prim1) {
						if (ints.containsKey(toCheck[1]) || prim2) {
							if (prim1 && prim2) {
								return p1 <= p2;
							} else if (prim1) {
								return p1 <= ints.get(toCheck[1]);
							} else if (prim2) {
								return p2 <= ints.get(toCheck[0]);
							} else {
								return ints.get(toCheck[0]) <= ints
										.get(toCheck[1]);
							}
						}
					}

				} else if (bool.contains("=")) {
					String[] toCheck = bool.split("=");
					// assume two items
					toCheck[0] = toCheck[0].trim();
					toCheck[1] = toCheck[1].trim();

					// check if we're dealing with integers
					boolean prim1 = false, prim2 = false;
					int p1 = 0, p2 = -1;

					try {
						p1 = assertInt(new Calculator().calculate(toCheck[0],
								decimals, ints));

						prim1 = true;
					} catch (Exception e) {

					}
					try {
						p2 = assertInt(new Calculator().calculate(toCheck[1],
								decimals, ints));

						prim2 = true;
					} catch (Exception e) {

					}

					// next, we check if we do, in fact, have integers,
					// and if
					// so, whether they're equal to each other

					if (ints.containsKey(toCheck[0]) || prim1) {
						if (ints.containsKey(toCheck[1]) || prim2) {
							if (prim1 && prim2) {
								return p1 == p2;
							} else if (prim1) {
								return p1 == ints.get(toCheck[1]);
							} else if (prim2) {
								return p2 == ints.get(toCheck[0]);
							} else {
								return ints.get(toCheck[0]) == ints
										.get(toCheck[1]);
							}
						}
						// in the event that one is an integer and the
						// other is
						// not - then we return false.
						return false;
					}

					// doubles

					prim1 = false;
					prim2 = false;
					double p11 = 0., p21 = -1.;

					try {
						p11 = new Calculator().calculate(toCheck[0], decimals,
								ints);

						prim1 = true;
					} catch (Exception e) {

					}
					try {
						p21 = new Calculator().calculate(toCheck[1], decimals,
								ints);

						prim2 = true;
					} catch (Exception e) {

					}

					if (decimals.containsKey(toCheck[0]) || prim1) {
						if (decimals.containsKey(toCheck[1]) || prim2) {
							if (prim1 && prim2) {
								return p11 == p21;
							} else if (prim1) {
								return p11 == decimals.get(toCheck[1]);
							} else if (prim2) {
								return p21 == decimals.get(toCheck[0]);
							} else {
								return decimals.get(toCheck[0]) == decimals
										.get(toCheck[1]);
							}
						}
						return false;
					}
					// check booleans

					// System.out.println("to here");

					prim1 = false;
					prim2 = false;
					boolean p = false, q = false;

					try {
						p = parseBoolean(toCheck[0]);
						prim1 = true;
					} catch (Exception e) {

					}
					try {
						q = parseBoolean(toCheck[1]);
						prim2 = true;
					} catch (Exception e) {

					}

					if (bools.containsKey(toCheck[0]) || prim1) {
						if (bools.containsKey(toCheck[1]) || prim2) {
							if (prim1 && prim2) {
								return p == q;
							} else if (prim1) {
								return p == bools.get(toCheck[1]);
							} else if (prim2) {
								return q == bools.get(toCheck[0]);
							} else {
								return bools.get(toCheck[0]) == bools
										.get(toCheck[1]);
							}
						}
						return false;
					}

					// We can check for equality of lists and arrays
					// here.
					// Currently this is not supported.

					// Let's check strings - gotta do this last, since
					// it
					// contains the overall else case

					prim1 = true;
					prim2 = true;

					if (strings.containsKey(toCheck[0])) {

						if (strings.containsKey(toCheck[1])) {
							return strings.get(toCheck[0]).equals(
									strings.get(toCheck[1]));
						} else {

							return strings.get(toCheck[0]).equals(toCheck[1]);

						}
					} else if (strings.containsKey(toCheck[1])) {
						return strings.get(toCheck[1]).equals(toCheck[0]);
					} else {
						// System.out.println(toCheck[0] + "?=" + toCheck[1]);

						return toCheck[0].equals(toCheck[1]);
					}

				} else if (bool.contains(">")) {

					String[] toCheck = bool.split(">");
					// assume two items
					toCheck[0] = toCheck[0].trim();
					toCheck[1] = toCheck[1].trim();

					// we only care about ints and doubles, so we'll
					// ignore
					// other cases!

					boolean prim1 = false, prim2 = false;
					double p11 = 0., p21 = -1.;

					try {
						p11 = new Calculator().calculate(toCheck[0], decimals,
								ints);

						prim1 = true;
					} catch (Exception e) {

					}
					try {
						p21 = new Calculator().calculate(toCheck[1], decimals,
								ints);

						prim2 = true;
					} catch (Exception e) {

					}

					if (decimals.containsKey(toCheck[0]) || prim1) {
						if (decimals.containsKey(toCheck[1]) || prim2) {
							if (prim1 && prim2) {
								return p11 > p21;
							} else if (prim1) {
								return p11 > decimals.get(toCheck[1]);
							} else if (prim2) {
								return p21 > decimals.get(toCheck[0]);
							} else {
								return decimals.get(toCheck[0]) > decimals
										.get(toCheck[1]);
							}
						}
						return false;

					}
					prim1 = false;
					prim2 = false;
					int p1 = 0, p2 = -1;

					try {
						p1 = assertInt(new Calculator().calculate(toCheck[0],
								decimals, ints));

						prim1 = true;
					} catch (Exception e) {

					}
					try {
						p2 = assertInt(new Calculator().calculate(toCheck[1],
								decimals, ints));

						prim2 = true;
					} catch (Exception e) {

					}

					// next, we check if we do, in fact, have integers,
					// and if
					// so, whether they're equal to each other

					if (ints.containsKey(toCheck[0]) || prim1) {
						if (ints.containsKey(toCheck[1]) || prim2) {
							if (prim1 && prim2) {
								return p1 > p2;
							} else if (prim1) {
								return p1 > ints.get(toCheck[1]);
							} else if (prim2) {
								return p2 > ints.get(toCheck[0]);
							} else {
								return ints.get(toCheck[0]) > ints
										.get(toCheck[1]);
							}
						}
						// in the event that one is an integer and the
						// other is
						// not - then we return false.
						return false;
					}
					return false;
					// if there aren't ints or doubles (i.e. we've
					// gotten this
					// far!), we're assuming that this is just false.

				} else if (bool.contains("<")) {
					String[] toCheck = bool.split("<");
					// assume two items
					toCheck[0] = toCheck[0].trim();
					toCheck[1] = toCheck[1].trim();

					// we only care about ints and doubles, so we'll
					// ignore
					// other cases!

					boolean prim1 = false, prim2 = false;
					double p11 = 0., p21 = -1.;

					try {
						p11 = new Calculator().calculate(toCheck[0], decimals,
								ints);
						prim1 = true;
					} catch (Exception e) {

					}
					try {
						p21 = new Calculator().calculate(toCheck[1], decimals,
								ints);
						prim2 = true;
					} catch (Exception e) {

					}

					if (decimals.containsKey(toCheck[0]) || prim1) {
						if (decimals.containsKey(toCheck[1]) || prim2) {
							if (prim1 && prim2) {
								return p11 < p21;
							} else if (prim1) {
								return p11 < decimals.get(toCheck[1]);
							} else if (prim2) {
								return p21 < decimals.get(toCheck[0]);
							} else {
								return decimals.get(toCheck[0]) < decimals
										.get(toCheck[1]);
							}
						}
						return false;

					}
					prim1 = false;
					prim2 = false;
					int p1 = 0, p2 = -1;

					try {
						p1 = assertInt(new Calculator().calculate(toCheck[0],
								decimals, ints));
						prim1 = true;
					} catch (Exception e) {

					}
					try {
						p2 = assertInt(new Calculator().calculate(toCheck[1],
								decimals, ints));
						prim2 = true;
					} catch (Exception e) {

					}

					// next, we check if we do, in fact, have integers,
					// and if
					// so, whether they're equal to each other

					if (ints.containsKey(toCheck[0]) || prim1) {
						if (ints.containsKey(toCheck[1]) || prim2) {
							if (prim1 && prim2) {
								return p1 < p2;
							} else if (prim1) {
								return p1 < ints.get(toCheck[1]);
							} else if (prim2) {
								return p2 < ints.get(toCheck[0]);
							} else {
								return ints.get(toCheck[0]) < ints
										.get(toCheck[1]);
							}
						}
						// in the event that one is an integer and the
						// other is
						// not - then we return false.
						return false;
					}
					return false;
					// if there aren't ints or doubles (i.e. we've
					// gotten this
					// far!), we're assuming that this is just false.

				} else {
					// in this case, we're looking at something that has gotten
					// through the filters but doesn't have =, >, <, <=, or >=
					// in it. So we're going to treat it as a boolean literal
					boolean retVal = false;
					retVal = parseBoolean(bool);
					return retVal;
				}
			}

		}
		return (Boolean) (m.execute(bool, null));

		// if we get here, something
		// happened that shouldn't, so
		// we'll just say false and be done with it.

	}

	/*
	 * public static void main(String[] args) { ArrayList<Llama> l = new
	 * ArrayList<Llama>(); // String s = //
	 * "to doSomething\nrepeat(4){\nnew int x = 5\nif(x < 10){\nx=x+1\nif(x > 3){\nHI\n}\n}\ngreetings\n}\n"
	 * ; // /String s ="";
	 * 
	 * String s =
	 * "to doThis\ntry{\nnew Squirrel x = banana\notherwise\nnew int x = 4\n}\n"
	 * ;
	 * 
	 * Interpreter i = new Interpreter(s, l, m); //
	 * System.out.println(i.evaluateBoolean("(true or (1=2))")); //
	 * System.out.println(i.evaluateBoolean("not true")); //
	 * System.out.println(i.evaluateBoolean("true and 10 < 55 or 10 = 10"));
	 * 
	 * }
	 */

	// my own personal parseBoolean function. Takes a string and returns true if
	// the string is "true" or "1", false if the string is "false" or "0", and
	// throws an exception otherwise - NullPointer if the input is null, and
	// IllegalArgument if it doesn't match any of the hardcoded true/false
	// values. Ignores case.

	public static boolean parseBoolean(String s1) {
		String s = s1.trim();
		/*try{
			String[] parts = s1.split("\\.");
			Object[] toPass = null;
			toPass[0] = obj.get(parts[0].trim());
			String commandAnd = parts[1];
			String[] things = commandAnd.split("\\(");
			String[] args = things[1].split(",");
			//now we need to turn args into passable things
		}catch(Exception e){
			
		}*/
		
		
		if (s != null) {
			if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("1")) {
				return true;
			} else if (s.equalsIgnoreCase("false") || s.equalsIgnoreCase("0")) {
				return false;
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new NullPointerException();
		}

	}

	public static int assertInt(double d) {
		if (Math.rint(d) == d) {
			return (int) d;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public static String[] flatten(String[] a) {// returns a one-element array,
												// from a multielement array
		StringBuffer result = new StringBuffer();
		try {
			for (int i = 0; i < a.length; i++) {
				result.append(a[i]);
				result.append("\n");
			}
		} catch (Exception e) {
			System.err.println("too short a string");
		}
		String[] mynewstring = new String[1];
		mynewstring[0] = result.toString().trim();
		return mynewstring;
	}

	public static String extraFlatten(String[] a) {
		StringBuffer result = new StringBuffer();
		try {
			for (int i = 0; i < a.length; i++) {
				result.append(a[i]);
				result.append("\n");
			}
		} catch (Exception e) {
			System.err.println("too short a string");
		}
		// String[] mynewstring = new String[1];
		return result.toString().trim();
		// return mynewstring;
	}

}
