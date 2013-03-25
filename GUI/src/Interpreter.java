import org.eclipse.swt.graphics.Point;


public class Interpreter
{
	public String runCommand(String text, Llama llama)//TODO add the rest of the functions
	{
		if (text.contains("."))// Format of all known valid functions
		{
			String command = "";
			String argument = "";
			// Separate command from object
			command = "";
			if (text.contains(" "))// Ignore anything after a space after the command
			{
				command = text.substring(text.indexOf('.') + 1, text.indexOf(' '));
			}
			else
			{
				command = text.substring(text.indexOf('.') + 1, text.length());
			}
			if (command.contains("("))
			{
				if (command.contains(")"))// Separate argument from command
				{
					argument = command.substring(command.indexOf('(') + 1, command.indexOf(')'));
					command = command.substring(0, command.indexOf('('));
				}
			}
			command = command.toLowerCase();
			argument = argument.toLowerCase();
			if (argument.equals(""))
			{
				/*
				 * back(int n) / bk(int n) done forward(int n) / fd(int n) done distance(Llama n) done lt(int n) / leftTurn(int n) done rt(int n) /
				 * rightTurn(int n) done infront() done behind() done pd() / penDown() done pu() / penUp() done position() / pos() done remove() - Will not be
				 * implemented; this should not be a method of the llama itself.
				 */
				if (command.equals("infront"))
				{
					llama.inFront();
					return (llama.name + " has been placed in front.");
				}
				else if (command.equals("behind"))
				{
					llama.behind();
					return (llama.name + " has been placed behind.");
				}
				else if (command.equals("pd"))//TODO make the pen do something
				{
					llama.pd();
					return (llama.name + "'s pen is now down.");
				}
				else if (command.equals("pendown"))
				{
					llama.pd();
					return (llama.name + "'s pen is now down.");
				}
				else if (command.equals("pu"))
				{
					llama.pu();
					return (llama.name + "'s pen is now up.");
				}
				else if (command.equals("penup"))
				{
					llama.pu();
					return (llama.name + "'s pen is now up.");
				}
				else if (command.equals("position"))
				{
					Point objectPosition = llama.position();
					return (llama.name + "'s position is: " + objectPosition.toString());
				}
			}
			else
			{
				if (command.equals("back"))
				{
					llama.back(Integer.parseInt(argument));
					return (llama.name + " has been moved back " + argument + " pixels.");
				}
				else if (command.equals("bk"))
				{
					llama.back(Integer.parseInt(argument));
					return (llama.name + " has been moved back " + argument + " pixels.");
				}
				else if (command.equals("forward"))
				{
					llama.forward(Integer.parseInt(argument));
					return (llama.name + " has been moved forward " + argument + " pixels.");
				}
				else if (command.equals("fd"))
				{
					llama.forward(Integer.parseInt(argument));
					return (llama.name + " has been moved forward " + argument + " pixels.");
				}
				else if (command.equals("distance"))
				{
					// TODO
				}
				else if (command.equals("lt"))
				{
					llama.lt(Integer.parseInt(argument));
					return (llama.name + " has been turned left " + argument + " degrees.");
				}
				else if (command.equals("leftturn"))
				{
					llama.lt(Integer.parseInt(argument));
					return (llama.name + " has been turned left " + argument + " degrees.");
				}
				else if (command.equals("rt"))
				{
					llama.rt(Integer.parseInt(argument));
					return (llama.name + " has been turned right " + argument + " degrees.");
				}
				else if (command.equals("rightturn"))
				{
					llama.rt(Integer.parseInt(argument));
					return (llama.name + " has been turned right " + argument + " degrees.");
				}
			}
		}
		return ("Command not recognized");
	}
}
