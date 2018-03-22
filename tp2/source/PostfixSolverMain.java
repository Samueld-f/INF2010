import java.io.*;
import java.util.Stack;

public class PostfixSolverMain 
{
	public static void main(String[] args) throws IOException 
	{
		Stack<Double> stack = new Stack<Double>();
		
		String s = "25 5 2 * + 15 3 / 5 - +";
		
		//L'expression est separee en tokens selon les espaces
		for(String token : s.split("\\s")) 
		{
			if (token.charAt(0) > 47 && token.charAt(0) < 58)
				stack.push(Double.parseDouble(token));
			else if (token.charAt(0) == '+')
			{
				double val2 = stack.pop();
				double val1 = stack.pop();
				stack.push(val1 + val2);
			}
			else if (token.charAt(0) == '-')
			{
				double val2 = stack.pop();
				double val1 = stack.pop();
				stack.push(val1 - val2);
			}
			else if (token.charAt(0) == '*')
			{
				double val2 = stack.pop();
				double val1 = stack.pop();
				stack.push(val1 * val2);
			}
			else if (token.charAt(0) == '/')
			{
				double val2 = stack.pop();
				double val1 = stack.pop();
				stack.push(val1 / val2);
			}
			
		}
     
		System.out.println("25 + 5*2 + 15/3 - 5 = "+stack.peek());
		if(stack.peek() == 35)
			System.out.println("It's all good");
		else
			System.out.println("Erreur: mauvais resultat");
     }
}
