import java.util.Random;
import java.util.Stack;


public class SortStackMain 
{
	static final int COUNT = 30;
	static final int MAX_VALUE = 1000;
	
	public static void main(String[] args) 
	{
		boolean sortIsGood = true;
		
		Random generator = new Random( System.nanoTime() );
		Stack<Integer> stack = new Stack<Integer>();
		
		for(int i = 0; i < COUNT; i++)
			stack.push(generator.nextInt(MAX_VALUE));
		
		stack = sortStack(stack);
		
		boolean countIsGood = size(stack) == COUNT;
			
		int tmp = stack.pop();
		while(!stack.isEmpty())
		{
			System.out.print(tmp + ", ");
			
			if(tmp > stack.peek())
				sortIsGood = false;
			
			tmp = stack.pop();
		}
		System.out.println(tmp);
		
		if(!countIsGood)
			System.out.println("Erreur: il manque des elements dans la pile");
		else if(!sortIsGood)
			System.out.println("Erreur: le trie a echoue");
		else
			System.out.println("It's all good");
	}
    
	private static int size(Stack<Integer> stack) 
	{
		Stack<Integer> newStack = (Stack<Integer>)stack.clone();
		int size = 0;
		while (!newStack.empty())
		{
			newStack.pop();
			size++;
		}
		return size;
    }
    
	static Stack<Integer> sortStack(Stack<Integer> stack)
	{
		Stack<Integer> buffer = new Stack<Integer>();
		int maxStack;
		int maxBuffer;
		int sizeStack = size(stack);
		for(int i = 0; i < sizeStack/2; i++)
		{
			maxStack = stack.pop();
			while (!stack.empty())
			{	
				if(stack.peek() > maxStack) 
				{
					buffer.push(maxStack);
					maxStack = stack.pop();
				}
				else
					buffer.push(stack.pop());
			}
			maxBuffer = buffer.pop();
			int sizeBuffer = size(buffer);
			for(int j = 0; j < sizeBuffer-(i*2); j++)
			{
				if(buffer.peek() > maxBuffer) 
				{
					stack.push(maxBuffer);
					maxBuffer = buffer.pop();
				}
				else
					stack.push(buffer.pop());
			}
			buffer.push(maxStack);
			buffer.push(maxBuffer);
		}
		if(size(stack) != 0)
			buffer.push(stack.pop());
		return buffer;
	}		
}
