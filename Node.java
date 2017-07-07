import java.util.ArrayList;

public class Node
{
	private int id;
	public ArrayList<Edge> list = new ArrayList<Edge>();
	
	/*
	public Node()
	{
		list = new ArrayList<Edge>();
	}
	*/

	public Node(int id)
	{
		this.id = id;
	}

	public static void main(String[] args)
	{
		//list = new ArrayList<Edge>();	
		Node x = new Node(5);
		Node y = new Node(5);
		Node z = new Node(4);

		x.addEdge(y);

		System.out.println(x.equals(y));
		System.out.println(x.equals(z));
	}

	public int getId()
	{
		return id;
	}	

	public void addEdge(Node dst)
	{
		if(!this.equals(dst))
		{
			Edge e = new Edge(this, dst);
			list.add(e);
		}
	}

	public boolean equals(Object other)
	{
		Node n = null;

		try{
			n = (Node) other;
		}
		catch(Exception e)
		{
			System.out.println("Kein Node-Objekt.");
			System.exit(0);
		}

		if(this.id == n.getId())
		{
			return true;
		}
		return false;
	}
}
