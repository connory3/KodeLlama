import java.util.Scanner;
import java.util.TreeMap;

import net.astesana.javaluator.*;

public class Calculator {

	DoubleEvaluator newEval;
	StaticVariableSet<Double> doubleSet = new StaticVariableSet<Double>();
	public Calculator() {
		newEval = new DoubleEvaluator();
	}
	
	public static void main(String [] args)
	{
		Calculator calculator = new Calculator();
		Scanner kb = new Scanner(System.in);
		System.out.println("What calculation?");
		String initial = kb.nextLine();
		TreeMap<String, Double> testDouble = new TreeMap<String, Double>();
		testDouble.put("x", 50.5);
		TreeMap<String, Integer> testInt = new TreeMap<String, Integer>();
		testInt.put("y", 25);
		System.out.println(calculator.calculate(initial, testDouble, testInt));
		kb.close();
		main(args);
	}
	public void setVariables(TreeMap<String, Double> doubleMap, TreeMap<String, Integer> intMap)
	{
		String dummy[] = new String[0];
		String doubleKeys[] = doubleMap.navigableKeySet().toArray(dummy);
		if (doubleMap.size() > 0)
		{
			for(int i=0; i<doubleKeys.length; i++)
			{
				doubleSet.set(doubleKeys[i], doubleMap.get(doubleKeys[i]));
				//System.out.println("Set " + doubleKeys[i] + " to " + doubleMap.get(doubleKeys[i]) + ".");
			}
		}
		String intKeys[] = intMap.navigableKeySet().toArray(dummy);
		if (intMap.size() > 0)
		{
			for(int i=0; i<intKeys.length; i++)
			{
				doubleSet.set(intKeys[i], intMap.get(intKeys[i]) + 0.0);
				//System.out.println("Set " + intKeys[i] + " to " + intMap.get(intKeys[i]) + ".");
			}
		}
	}
	public Double calculate(String initial, TreeMap<String, Double> doubleMap, TreeMap<String, Integer> intMap) //Calculates strings with variables. 
	{
		if(initial.contains(")("))
		{
			initial = initial.replace(")(", ")*(");
		}
		for (int i=0; i<10; i++)
		{
			if (initial.contains(")" + i))
			{
				initial = initial.replace(")" + i, ")*" + i);
			}
			if (initial.contains(i + "("))
			{
				initial = initial.replace(i + "(", i + "*(");
			}
		}
		setVariables(doubleMap, intMap);
		return newEval.evaluate(initial, doubleSet);
	}
	public Double calculate(String initial) //Can be used to calculate strings without variables.
	{
		if(initial.contains(")("))
		{
			initial = initial.replace(")(", ")*(");
		}
		for (int i=0; i<10; i++)
		{
			if (initial.contains(")" + i))
			{
				initial = initial.replace(")" + i, ")*" + i);
			}
			if (initial.contains(i + "("))
			{
				initial = initial.replace(i + "(", i + "*(");
			}
		}
		return newEval.evaluate(initial);
	}
}
