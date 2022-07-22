
public class LinkedList {
	private int size;
	private Node head,tail;
	
	//Node class
	private class Node{
		private char data; private String data_string;
		private Node prev, next;
		
		public Node(char data, Node prev, Node next) {
			this.data=data;
			this.prev=prev;
			this.next=next;
		}

		public Node(String data_string, Node prev, Node next) {
			// TODO Auto-generated constructor stub
			this.data_string=data_string;
			this.prev=prev;
			this.next=next;
		}
	}
	
	public LinkedList() {
		size =0;
		head=null;
		tail=null;
	}
	
	//size method
	public int size() {
		return size;
	}
	
	//isempty method
	public boolean isEmpty() {
		return size == 0;
	}
	
	//methods: insertBefore, insertAfter, remove, removeFirst, removeLast, head, tail, 
	public char head() throws Exception{
		if(isEmpty())
			throw new Exception("List is empty");
		return head.data;
	}
	
	public char tail() throws Exception{
		if(isEmpty())
			throw new Exception("List is empty");
		return tail.data;
	}
	
	char get(int position) throws Exception{
		//we have to check our position to see if its valid
		if(position < 0 || position > size - 1)
			throw new Exception("Invalid index");
		if(position == 0)
			return head();
		else if(position == size-1)
			return tail();
		//we have to traverse list
		Node trav = head;
		for(int i = 0; i< position; i++)
			trav = trav.next;
		
		return trav.data;
	}
	
	String get1(int position) throws Exception{
		//we have to check our position to see if its valid
		if(position < 0 || position > size - 1)
			throw new Exception("Invalid index");
		if(position == 0)
			return head.data_string;
		else if(position == size-1)
			return tail.data_string;
		//we have to traverse list
		Node trav = head;
		for(int i = 0; i< position; i++)
			trav = trav.next;
		return trav.data_string;
	}

	void set(int position, char element) throws Exception {
		//we have to check our position to see if its valid
		if(position < 0 || position > size - 1)
			throw new Exception("Invalid index");
		if(position == 0) {
			head.data = element;
		}
		else if (position == size-1) {
			tail.data=element;
		}
		else {
			//we have to traverse through the list to get to where we want to go
			Node trav = head;
			for(int i = 0; i < position; i++) {
				trav = trav.next;
			}
			trav.data = element;
		}
	}
	
	void insertBefore(int position, char element) throws Exception {
		//we have to check our position to see if its valid
		if(position < 0 || position > size - 1)
			throw new Exception("Invalid index");
		if(position == 0) {
			insertFirst(element);
			return;
		}
		else if(position == size-1) {
			Node newNode = new Node(element, tail.prev, tail);
			tail.prev.next = newNode;
			tail.prev = newNode;
			size++;
			return;
		}
		else {
			//we have to traverse through the list to get to where we want to go
			Node trav = head;
			for(int i = 0; i < position-1; i++) {
				trav = trav.next;
			}
			Node current = trav.next;
			Node newNode = new Node(element, trav, current);
			trav.next = newNode;
			current.prev = newNode;
			size++;
			return;
		}
	}
	
	void insertAfter(int position, char element) throws Exception{
		if(position < 0 || position > size-1)
			throw new Exception("Invalid index");
		if(position == 0) {
			Node newNode = new Node(element, head, head.next);
			head.next.prev = newNode;
			head.next = newNode;
			size++;
			return;
		}
		else if(position == size -1) {
			insertLast(element);
			return;
		}
		else {
			//we'll have to traverse through the list to get to where we want to go
			Node trav = head;
			for(int i = 0; i < position ; i++)
				trav=trav.next;
			Node current = trav.next;
			Node newNode = new Node(element, trav, current);
			trav.next = newNode;
			current.prev=newNode;
			size++;
			return;
		}
	}
	
	void insertFirst(char element) {
		// TODO Auto-generated method stub
		if(isEmpty()) {
			head=tail=new Node(element, null, null);
		}
		else {
			head.prev = new Node(element, null, head);
			head = head.prev;
		}
		size++;
		return;
	}
	
	void insertLast(char element) {
		// TODO Auto-generated method stub
		if(isEmpty()) {
			head=tail=new Node(element, null, null);
		}
		else {
			tail.next = new Node(element, tail, null);
			tail = tail.next;
		}
		size++;
		return;
	}
	
	public void insertLast(String element) {
		// TODO Auto-generated method stub
		if(isEmpty()) {
			head=tail=new Node(element, null, null);
		}
		else {
			tail.next = new Node(element, tail, null);
			tail = tail.next;
		}
		size++;
		return;
	}
	
	char remove(int position) throws Exception{
		//check if position is valid
		if(position < 0 || position > size-1)
			throw new Exception("invalid index");
		if(position == 0) {
			return removeFirst();
		}
		else if(position == size-1) {
			return removeLast();
		}
		//we have to traverse through list to get to what we want
		Node trav = head;
		for(int i = 0; i < position-1; i++)
			trav= trav.next;
		Node current = trav.next;
		
		trav.next = current.next;
		current.next.prev = trav;
		char removedData= current.data;
		//cleanup memory and reduce size by 1
		current.next = null;
		current.prev=null;
		current=null;
		size--;
		return removedData;
	}
	
	char removeFirst() throws Exception{
		// TODO Auto-generated method stub
		if(isEmpty())
			throw new Exception("Empty List");
		char data = head.data;
		head = head.next;
		size --;
		
		if(isEmpty())
			tail = null;
		else
			head.prev=null;
		return data;
	}
	
	char removeLast() throws Exception{
		// TODO Auto-generated method stub
		if(isEmpty())
			throw new Exception("Empty List");
		char temp = tail.data;
		tail = tail.prev;
		size--;
		if(isEmpty())
			head = null;
		else
			tail.next = null;
		return temp;
	}

	String removeLast1() throws Exception{
		// TODO Auto-generated method stub
		if(isEmpty())
			throw new Exception("Empty List");
		String temp = tail.data_string;
		tail = tail.prev;
		size--;
		if(isEmpty())
			head = null;
		else
			tail.next = null;
		return temp;
	}
}
