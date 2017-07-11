import java.util.ArrayList;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;

public class Graph extends Node
{
	public ArrayList<Node> nodes;

	//ich glaub die ArrayList sollte woanderst initialisiert werden
	public Graph()
	{
		nodes = new ArrayList<Node>();
	}

	public static void main(String[] args)
	{
		String dateiPfad = "";
		try{
			dateiPfad = args[0];
		}
		catch(Exception e)
		{
			System.out.println("Argument konnte nich eingelesen werden.");
			System.exit(0);
		}
		Graph g = fromFile(dateiPfad);
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

	//die stimmt noch nicht
	public void addEdge(int src, int dst)
	{
		Node n = new Node(src);
		Node m = new Node(dst);
		n.addEdge(m);
	}

	public Graph fromFile(String filepath)
	{
		Graph g = new Graph();
		RandomAccessFile file = new RandomAccessFile(filepath, "r");
		String zeile = "";
		//nodes = new ArrayList<Node>();

		try{
			//kein schoener Stil - hier sollte nicht while(true) udn auch nicht try ... catch stehen
			while(true)
			{
				zeile = file.readLine();
				StringTokenizer st = new StringTokenizer(zeile, ",");
				Node src = null;
				Node dst = null;
				try{
					src = new Node(Integer.parseInt(st.nextToken()));
					dst = new Node(Integer.parseInt(st.nextToken()));
				}
				catch(Exception e)
				{
					System.out.println("Node konnte nicht erstellt werden.");
					System.exit(0);
				}
				if(src != null && dst != null)
				{
					//anscheinend ist die ArrayList static ... 
					nodes.add(src);
					nodes.add(dst);
					addEdge(dst, src);
				}

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
	return g;
	}
}

