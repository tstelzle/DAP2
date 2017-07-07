import java.util.ArrayList;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;

public class Graph extends Node
{
	public ArrayList<Node> nodes;

	public static void main(String[] args)
	{
		String dateiPfad = "";
		RandomAccessfile = null;
	}

	public boolean contains(int id)
	{
		for(Node n : nodes)
		{	
			if(n.getId() == id)
			{
				return true;
			}
		}
		return false;
	}
	
	public void addNode(int id)
	{
		if(getNode(id) == null)
		{
			Node n = new Node(id);
			nodes.add(n);
		}
	}

	public Node getNode(int id)
	{
		for(Node n : nodes)
		{
			if(n.getId() == id)
			{
				return n;
			}
		}
		return null;
	}

	public void addEdge(int src, int dst)
	{
		Node n = new Node(src);
		Node m = new Node(dst);
		n.addEdge(m);
	}

	public static Graph fromFile(String filepath)
	{
		String zeile = "";
		nodes = new ArrayList<Node>();

		try{
			while(true)
			{
				zeile = file.readLine();
				StringTokenizer st = new StringTokenizer(zeile, ",");
				Node src = new Node(st.nextToken());
				Node dst = new Node(st.nextToken());
				nodes.add(src);
				nodes.add(dst);
				addEdge(dst, src);

			}
		}
		catch(Exception e)
		{
			
		}
		finally
		{	
			try{
				file.close();
			}
			catch(Exception e)
			{
				System.out.println("Reader konnte nicht geschlossen werden.");
				System.exit(0);
			}
		}

	}
}

