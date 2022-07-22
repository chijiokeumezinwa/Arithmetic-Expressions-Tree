
public class ExpressionTree {
	 //root 
	static Node root = null;
	private int size = 0;

	private class Node{
		private String data;
		private Node left,right;

		public Node(String data, Node left, Node right) {
			this.data = data;
			this.left = left;
			this.right = right;
		}
	}

	//size method
	public int size() {
		return size;
	}

	//isEmpty method
	public boolean isEmpty() {
		return size == 0;
	}

	//add method
	void add(String e) throws Exception {
		if(e == null)
			throw new Exception("null input");
		else {
			root = add(root, e);
			size++;
		}
	}

	Node add(Node v, String e) {
		// TODO Auto-generated method stub

		if(v == null) { 
			v = new Node(e, null,null);
		}
		else {
			if(v.right == null)
				v.right = add(v.right, e);
			else
				v.left = add(v.left, e);
		}
		return v;
	}

	public void printTree() {
		// TODO Auto-generated method stub
		printTree(root, 0);
	}
	
	private void printTree(Node v, int position) {
		//we assume v to be the root.
		if(v == null) {
			return;
		}
		else {
			printTree(v.right, position+1);
			int i = 0;
			while(i < position) {
				System.out.print("\t");
				i++;
			}
			System.out.println(v.data);
			printTree(v.left, position+1);
		}
		
	}


	public static void evaluateTree() {
		// TODO Auto-generated method stub
		int result = evaluateTree(root);
		System.out.println("The result of the tree: ");
		System.out.println(result);
	}

	private static int evaluateTree(Node v) {
		// TODO Auto-generated method stub
		if(v == null)
			return 0;
		else {
			if(v.left == null && v.right == null)
				return Integer.parseInt(v.data);
		}
		
		int leftTreeResult = evaluateTree(v.left);
		int rightTreeResult = evaluateTree(v.right);
		
		return operation(v.data, leftTreeResult, rightTreeResult);
				
	}

	private static int operation(String data, int leftTreeResult, int rightTreeResult) {
		// TODO Auto-generated method stub
		if(data.equals("+"))
			return leftTreeResult + rightTreeResult;
		else if(data.equals("-"))
			return leftTreeResult - rightTreeResult;
		else if(data.equals("*"))
			return leftTreeResult * rightTreeResult;
		else if(data.equals("/"))
			return leftTreeResult / rightTreeResult;
		return 0;
	}

}
