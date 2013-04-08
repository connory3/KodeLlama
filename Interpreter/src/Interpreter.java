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

		ints = new TreeMap<String, Integer>();
		decimals = new TreeMap<String, Double>();
		strings = new TreeMap<String, String>();
		bools = new TreeMap<String, Boolean>();

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
				String[] andArgs = headerAndRest[0].split("\\(");
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

		} else { // here we have discovered that we've got multiple strings
					// being
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

	public boolean evaluateBoolean(String b) {
		System.out.println(b);
		String bool = b.trim();

		if (bool.startsWith("(") && bool.endsWith(")") ) {
			
			if(!bool.contains("and")
				&& !bool.contains("or"))
				return evaluateBoolean(bool.substring(1, bool.length() - 1));
			else{
				int count = 1;
				int spot1 = 1;
				boolean getsToZero = false;
				while (count > 0) {
					// System.out.println(count);
					if (bool.indexOf(')', spot1) <= bool.indexOf('(', spot1)
							|| bool.indexOf('(', spot1) == -1) {
						count--;
						spot1 = bool.indexOf(')', spot1) + 1;
						if(count == 0 && spot1 < bool.length()){
							getsToZero = true;
						}
						// System.out.println("here");
					} else {
						count++;
						spot1 = bool.indexOf('(', spot1) + 1;
					}
				}
				if(!getsToZero){
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

			/*
			 * System.out.println(spot1); System.out.println(bool.indexOf("or",
			 * spot1)); System.out.println(bool.indexOf('(',spot1));
			 */

			if (bool.indexOf("and", spot1) < bool.indexOf('(', spot1)
					&& bool.indexOf("and", spot1) > spot1) {
				return evaluateBoolean(bool.substring(0,
						bool.indexOf("and", spot1)))
						&& evaluateBoolean(bool.substring(bool.indexOf("and",
								spot1) + 3));

			} else if (bool.indexOf("or", spot1) < bool.indexOf('(', spot1)
					&& bool.indexOf("or", spot1) > spot1) {
				return evaluateBoolean(bool.substring(0,
						bool.indexOf("or", spot1)))
						|| evaluateBoolean(bool.substring(bool.indexOf("or",
								spot1) + 2));

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
					toCheck[0].trim();
					toCheck[1].trim();

					// we only care about ints and doubles, so we'll ignore
					// other cases!

					boolean prim1 = false, prim2 = false;
					double p11 = 0., p21 = -1.;

					try {
						p11 = Double.parseDouble(toCheck[0]);
						prim1 = true;
					} catch (Exception e) {

					}
					try {
						p21 = Double.parseDouble(toCheck[1]);
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
						p1 = Integer.parseInt(toCheck[0]);
						prim1 = true;
					} catch (Exception e) {

					}
					try {
						p2 = Integer.parseInt(toCheck[1]);
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
					toCheck[0].trim();
					toCheck[1].trim();

					// we only care about ints and doubles, so we'll ignore
					// other cases!

					boolean prim1 = false, prim2 = false;
					double p11 = 0., p21 = -1.;

					try {
						p11 = Double.parseDouble(toCheck[0]);
						prim1 = true;
					} catch (Exception e) {

					}
					try {
						p21 = Double.parseDouble(toCheck[1]);
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
						p1 = Integer.parseInt(toCheck[0]);
						prim1 = true;
					} catch (Exception e) {

					}
					try {
						p2 = Integer.parseInt(toCheck[1]);
						prim2 = true;
					} catch (Exception e) {

					}
					System.out.println(p1 + "    " + p2);
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
							toCheck[0].trim();
							toCheck[1].trim();

							// check if we're dealing with integers
							boolean prim1 = false, prim2 = false;
							int p1 = 0, p2 = -1;

							try {
								p1 = Integer.parseInt(toCheck[0]);
								prim1 = true;
							} catch (Exception e) {

							}
							try {
								p2 = Integer.parseInt(toCheck[1]);
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
								p11 = Double.parseDouble(toCheck[0]);
								prim1 = true;
							} catch (Exception e) {

							}
							try {
								p21 = Double.parseDouble(toCheck[1]);
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

									return strings.get(toCheck[0]).equals(
											toCheck[1]);

								}
							} else if (strings.containsKey(toCheck[1])) {
								return strings.get(toCheck[1]).equals(
										toCheck[0]);
							} else {
								return toCheck[0].equals(toCheck[1]);
							}

						} else if (bool.contains(">")) {

							String[] toCheck = bool.split(">");
							// assume two items
							toCheck[0].trim();
							toCheck[1].trim();

							// we only care about ints and doubles, so we'll
							// ignore
							// other cases!

							boolean prim1 = false, prim2 = false;
							double p11 = 0., p21 = -1.;

							try {
								p11 = Double.parseDouble(toCheck[0]);
								prim1 = true;
							} catch (Exception e) {

							}
							try {
								p21 = Double.parseDouble(toCheck[1]);
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
								p1 = Integer.parseInt(toCheck[0]);
								prim1 = true;
							} catch (Exception e) {

							}
							try {
								p2 = Integer.parseInt(toCheck[1]);
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
							toCheck[0].trim();
							toCheck[1].trim();

							// we only care about ints and doubles, so we'll
							// ignore
							// other cases!

							boolean prim1 = false, prim2 = false;
							double p11 = 0., p21 = -1.;

							try {
								p11 = Double.parseDouble(toCheck[0]);
								prim1 = true;
							} catch (Exception e) {

							}
							try {
								p21 = Double.parseDouble(toCheck[1]);
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
								p1 = Integer.parseInt(toCheck[0]);
								prim1 = true;
							} catch (Exception e) {

							}
							try {
								p2 = Integer.parseInt(toCheck[1]);
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

						}
						// in the event that one is an integer and the other is
						// not - then we return false.
						return false;
					}
					//return false;
					// if there aren't ints or doubles (i.e. we've gotten this
					// far!), we're assuming that this is just false.

				}

			
		return false;// if we get here, something happened that shouldn't, so
						// we'll just say false and be done with it.

	}

	public static void main(String[] args) {
		ArrayList<Llama> l = new ArrayList<Llama>();
		String s = "to eat\nafiejfije\nafefjiafie\nieflaifj\nend\n\n\nto haveCookies\nsetheija;fij\n iejifaj \neifaj\nend\n";
		Interpreter i = new Interpreter(s, l);
		System.out.println(i.evaluateBoolean("false = False"));
		System.out.println(i.evaluateBoolean("10 <= 10"));
		System.out.println(i.evaluateBoolean("not ((5 = 5) and (5 = 5))"));

	}

	//
	public static boolean parseBoolean(String s1) {
		String s = s1.trim();
		if (s != null) {
			if (s.equalsIgnoreCase("true")) {
				return true;
			} else if (s.equalsIgnoreCase("false")) {
				return false;
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new NullPointerException();
		}

	}

}
