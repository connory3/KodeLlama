import java.util.ArrayList;
import java.util.TreeMap;

public class Interpreter {
	private TreeMap<String, Llama> objects;
	private TreeMap<String, String> methods;
	
	private final String[] KEYWORDS = {};

	public Interpreter(String entireCode, ArrayList<Llama> l) {

		methods = new TreeMap<String, String>();
		objects = new TreeMap<String, Llama>();
		
		
		// create the map of llama names to llamas from the arraylist
		for (Llama l1 : l) {
			objects.put(l1.getName(), l1);
		}

		// create the map of method names to method bodies

		String[] method = entireCode.split("to ");

		for (int i = 0; i < method.length; i++) {
			if (!method[i].trim().equals("")&&method[i] != null) {
				String[] headerAndRest = method[i].split("\n", 2);
				//System.out.println(headerAndRest.length);
				methods.put(headerAndRest[0].trim(), headerAndRest[1].trim());
			}
		}
		//System.out.println(methods);
	}
	
	public void interpret(String toBeInterpreted){
		//basically, this method should split the input string into individual commands, and then execute them appropriately.
		//when calls are placed to other methods, they should just call this method on the data in the methods object for that method
		
		String[] lines = toBeInterpreted.split("\n");
		
		for(int i = 0; i < lines.length;i++){
			
		}
		
	}

	public static void main(String[] args) {
		ArrayList<Llama> l = new ArrayList<Llama>();
		String s = "to eat\nafiejfije\nafefjiafie\nieflaifj\nend\n\n\nto haveCookies\nsetheija;fij\n iejifaj \neifaj\nend\n";
		new Interpreter(s, l);
	}

}
