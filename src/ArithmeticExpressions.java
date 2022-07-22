/*
 * Chijioke Umezinwa
 * CS 610
 * Programming Assignment 2
 * Preferably run this program in eclipse or a similar program
 * Also this program only accepts integers, parentheses, and operational symbols
 * Consider the following expression BNF:
<expression>  ::=  <factor>  * <expression>   |   <factor>  /  <expression>   |   <factor>
<factor>  :==  <term> + <factor>  |  <term> - <factor>  |  <term>
<term>  ::=  {  <expression>  }  |  <literal>
<literal>  ::=  0|1|2|3|4|5|6|7|8|9

Using recursive descent, and only recursive descent, scan expressions that adhere to this BNF to build their expression tree; write an integer valued function that scans the tree to evaluate the expression represented by the tree.

There are plenty of clever programs online that you can download to evaluate arithmetic expression tree; if you want zero in this assignment, download one and submit it as programming assignment #2; if you want a grade greater than zero, please follow our instructions. Thanks.

Input:

    A numeric expression adhering to this BNF.

Output:

    Some representation of the expression tree.
    The result of evaluating the expression.

 */
import java.util.Scanner;

public class ArithmeticExpressions {
	private static Stack input = new Stack();
	private static ExpressionTree tree = new ExpressionTree();
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Scanner obj = new Scanner(System.in);
		System.out.println("Enter expression");
		
		String expression = obj.nextLine();
		
		//first lets convert our expression to a linked list
		LinkedList expressionList = parseExpression(expression);
		
		//next we want to call build expression on our token 
		//and also build our stack.
		buildExpression(expressionList);

		input.eraseBlank();//erases any blank spaces in stacks
		input.printStack();//prints our stack
		
		// we aim to use the stack to populate tree
		buildTree(input);
		System.out.println("\n");
		
		//the print out of our tree, read from left to right
		tree.printTree();
		
		//finally we want to write a function that can scan our tree
		//and evaluate the expression represented by tree
		tree.evaluateTree();
	}

	public static ExpressionTree buildTree(Stack a) throws Exception {
		// TODO Auto-generated method stub
		if (a == null)
			throw new Exception("stack is null");
		if (a.isEmpty())
			throw new Exception("stack is empty");
		
		String element;
		while(!a.isEmpty()) {
			element = a.pop();
			tree.add(element);
		}
		return tree;
	}

	private static LinkedList parseExpression(String expression) throws Exception {
		// TODO Auto-generated method stub
		//we aim to collect each character as its own node.
		//we want to ignore white spaces
		
		int length = expression.length();
		LinkedList a = new LinkedList();
		
		char c;
		for(int i =0; i < length; i++) {
			c= expression.charAt(i);
			if(!literal(c) && !operator(c))
				throw new Exception("You can only use spaces, literals, and operator symbols to define your "
						+ "arithmetic expression");
			if(c != ' ' || literal(c) || operator(c))
				a.insertLast(c);
			
		}
		return a;
	}
	
	private static void buildExpression(LinkedList expressionList) throws Exception {
		// TODO Auto-generated method stub
		
		//lets see if the input is null
		if(expressionList == null)
			return;
		
		//we need to scan for parentheses and if they exist, we need to see if
		//they're at least the correct number and type of parentheses
		boolean a = existenceParenthesis(expressionList);//finds if there are parenthesis
		boolean ab=false;
		if(a == true) {
			ab = scanParentheses(expressionList);//finds if correct number and type
		}											//are used
		
		if(a == true && ab == false)
			throw new Exception("Use of parentheses is incorrect");
		if(a==true && ab==true) {
			//i have to locate first parentheses, the second parentheses,
			//delete the two and call buildFactor
			int indexFirst=0;
			int i = 0;
			int size = expressionList.size();
			
			while(i < size &&
					expressionList.get(i) != '(') {
				i++;
				indexFirst = i;
			}
			int indexLast=0;
			i=0;
			while(i < size &&
					expressionList.get(i) != ')') {
				i++;
				indexLast=i;
			}
			while(i < size &&
					expressionList.get(i) == ')') {
				indexLast=i;
				i++;
			}
		
			if(indexFirst > indexLast)
				throw new Exception("invalid indexes");//checks again if pair is out of order
														// ) can't be before (
			
			//we want to concentrate on removing the elements in between a parentheses
			//from expression list
			
			//the list of terms between parentheses we found, we store in a new list
			LinkedList newList = miniList(expressionList, indexFirst+1, indexLast);
			expressionList.remove(indexFirst);
			expressionList.remove(indexLast-1);
			
			int j = 0;
			while(j < indexLast-1) {
				expressionList.remove(indexFirst);
				j++;
			}
			
			if(expressionList.isEmpty()) {
				buildExpression(newList);
				return;
			}
			//if our list is not empty, we'll have to add a blank node just before
			//indexFirst, to mark the place of the parentheses we took care of
			//otherwise if our list is empty, we're free to leave it as it is, and return out of expression
			
			else if(expressionList.isEmpty() == false) {
				if(indexFirst > expressionList.size()-1)
					expressionList.insertLast(' ');
				else
					expressionList.insertBefore(indexFirst, ' ');
				buildExpression(newList);
				buildExpression(expressionList);
				return;
			}
		}
		//if theres no parentheses, we instead try to scan for the existence
		//of either a * or /;

		boolean result= scanMultDiv(expressionList);
		if(result == false)
			buildFactor(expressionList);
		else {
			//scan for last occurrence of * or /.
			int lastIndexMultDiv = 0;
			int i = 0;
			int size = expressionList.size();
			while(i < size) {
				if(expressionList.get(i) == '*'
						|| expressionList.get(i) == '/')
					lastIndexMultDiv = i;
				i++;
			}
			LinkedList leftTokens,rightTokens;
			//we want to call factor on tokens just before this add or sub
			leftTokens = miniList(expressionList, 0, lastIndexMultDiv);
			buildFactor(leftTokens);

			//we want to call expression on the tokens after this add or sub
			rightTokens = miniList(expressionList, lastIndexMultDiv+1, expressionList.size());
			buildExpression(rightTokens);

			//finally we want to pop the symbol to our stack.
			input.push(expressionList.get(lastIndexMultDiv));
		}

	}
	
	private static boolean existenceParenthesis(LinkedList expressionList) throws Exception{
		// TODO Auto-generated method stub		
		boolean flip = false;
		int i = 0;
		int size = expressionList.size();
		
		while(i<size) {
			if(expressionList.get(i) == '(' ||
					expressionList.get(i) == ')')
				flip = true;
			i++;
		}
		return flip;
	}
	
	private static boolean scanParentheses(LinkedList expressionList) throws Exception {
		int countFirst = 0;
		int i = 0;
		int size = expressionList.size();
		
		while(i < size) {
			if(expressionList.get(i) == '(') {
				countFirst++;
			}
			i++;
		}

		int countSecond = 0;
		i = 0;
		while(i < size) {
			if(expressionList.get(i) == ')') {
				countSecond++;
			}
			i++;
		}
		
		if( countFirst != countSecond)
			return false;
		return true;
	}

	private static LinkedList miniList(LinkedList a, int firstIndex, int lastIndex) throws Exception{
		// TODO Auto-generated method stub
		if(firstIndex > lastIndex)
			throw new Exception("invalid indexes");
		LinkedList b = new LinkedList();
		for(int j = firstIndex; j < lastIndex; j++) {
			char c = a.get(j);
			b.insertLast(c);
		}
		return b;
	}

	private static boolean scanMultDiv(LinkedList expressionList) throws Exception {
		// TODO Auto-generated method stub
		//we want to find if there are * or / symbols
		boolean result = false;
		
		//we want to scan the input for a * or /
		int size = expressionList.size();
		int i = 0;
		while(i < size) {
			if(expressionList.get(i) == '*' || 
					expressionList.get(i) == '/')
				result =true;
			i++;
		}
		return result;
	}
	
	private static void buildFactor(LinkedList newList) throws Exception {
		// TODO Auto-generated method stub
		
		//lets see if the input is null
		if(newList == null)
			return;
	
		//we have to scan for the existence of either a + or - symbol
		boolean result = false;
		result = scanAddSub(newList);
		
		if(result == false)
			buildTerm(newList);
		
		else {
			
			//i have to find the last iteration of the + or - symbol
			//i'll call term on the left hand side and recursively
			//call factor on right hand side
			int lastIndexAddSub=0;
			int i = 0;
			int size = newList.size();
			
			while(i<size) {
				if(newList.get(i) == '+' ||
						newList.get(i) == '-')
					lastIndexAddSub=i;
				i++;
			}
			
			LinkedList leftTokens, rightTokens;
			
			leftTokens = miniList(newList, 0, lastIndexAddSub);
			buildTerm(leftTokens);
			
			rightTokens= miniList(newList, lastIndexAddSub+1, newList.size());
			buildFactor(rightTokens);
			
			//finally we pop the + or - to our stack
			input.push(newList.get(lastIndexAddSub));	
			return;
		}
	}

	private static boolean scanAddSub(LinkedList newList) throws Exception {
		// TODO Auto-generated method stub
		boolean result = false;
		
		int size = newList.size();
		int i = 0;
		
		while(i < size) {
			if(newList.get(i) == '+' ||
					newList.get(i) == '-')
				result = true;
			i++;
		}
		return result;
	}

	private static void buildTerm(LinkedList newList) throws Exception{
		// TODO Auto-generated method stub
		//lets scan for operational symbols and parentheses

		boolean a = scanMultDiv(newList);
		boolean ab = scanAddSub(newList);

		if(a == true || ab == true)
			buildExpression(newList);
		else {
			int size = newList.size();
			
			if(size == 1 && newList.get(0) == ' ') {
				input.push(newList.get(0));
			}
			
			else if(size == 1) {
				input.push(newList.get(0));
			}
			else {
				int i = 0;
				boolean result;
				while (i <= size-1) {
					result = literal(newList.get(i));
					if(result == false)
						throw new Exception("Value must be a proper literal");	
					i++;
				}
				//removes unneeded zeros
				while(newList.get(0) == '0')
					newList.remove(0);
				
				String element = "";
				i = 0;
				size = newList.size();
				while(i < size) {
					char c = newList.get(i);
					element += c;
					i++;
				}
				
				input.push(element);
			}
		}
		return;
	}

	private static boolean literal(char c) {
		// TODO Auto-generated method stub
		if(c == ' ' || c == '(' || c == ')'
				|| c == '0'||c == '1'||c == '2'||c == '3'||c == '4'
				||c == '5'||c == '6'||c == '7'||c == '8'||c == '9')
			return true;
		return false;
	}
	
	private static boolean operator(char c) {
		if(c == '+' || c == '-' || c == '*' || c== '/')
			return true;
		return false;
	}

}
