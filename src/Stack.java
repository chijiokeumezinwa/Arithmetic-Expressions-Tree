
public class Stack {
	//we'll use a linked list implementation

	private LinkedList stack;

	public Stack() {
		stack = new LinkedList();
	}

	public Stack(char element) {
		push(element);
	}

	public int size() {
		return stack.size();
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	void push(char element) {
		// TODO Auto-generated method stub
		stack.insertLast(element);
	}

	public void push(String string) {
		// TODO Auto-generated method stub
		stack.insertLast(string);
	}

	String pop() throws Exception {
		if(isEmpty())
			throw new Exception("Stack is empty");
		String c = "";
		int size = size();
		if(stack.get(size-1) == 0)
			c += stack.removeLast1();
		else
			c += stack.removeLast();
		return c;
	}

	String peek() throws Exception {
		if(isEmpty())
			throw new Exception("Stack is empty");
		String c = "";
		int size = size();
		if(stack.get(size-1) == 0)
			c = stack.get1(size-1);
		else
			c += stack.get(size-1);
		return c;
	}

	void printStack() throws Exception{
		if(isEmpty())
			throw new Exception("Stack is empty");
		int i = size()-1;
		System.out.println("_____");
		while(i >= 0) {
			if(stack.get(i) == 0)
				System.out.println("|"+ stack.get1(i) +" |");
			else
				System.out.println("|"+ stack.get(i) +" |");

			System.out.println("-----");
			i--;
		}
	}

	public void eraseBlank() throws Exception {
		// TODO Auto-generated method stub
		if(isEmpty())
			throw new Exception("Stack is empty");
		int i = size()-1;
		while(i >= 0) {
			if(stack.get(i) == ' ')
				stack.remove(i);
			i--;
		}
	}
}
