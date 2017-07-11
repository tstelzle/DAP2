import java.util.ArrayList;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;

public class Graph
{
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private int[] d;
	private Node[] lambda;

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
		if(!contains(id))
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
		if(contains(src) && contains(dst))
		{
			Node n = getNode(src);
			Node m = getNode(dst);
			n.addEdge(m);
			m.addEdge(n);
		}
	}

	public Graph fromFile(String filepath)
	{
		String zeile = null;
		Graph g = new Graph();
		RandomAccessFile file = null;
		try{
			file = new RandomAccessFile(filepath, "r");
		}
		catch(Exception e)
		{
			System.out.println("Die Datei konnte nicht eingelesen werden.");
			System.exit(0);
		}

		try{
			zeile = file.readLine();
		}
		catch(Exception e)
		{
			System.out.println("Zeile konnte nicht eingelesen werden.");
		}

		while(zeile != null)
		{
			StringTokenizer st = new StringTokenizer(zeile, ",");
			int src = -1;
			int dst = -1;
			try{
				src = Integer.parseInt(st.nextToken());
				dst = Integer.parseInt(st.nextToken());
			}
			catch(Exception e)
			{
				System.out.println("Node konnte nicht erstellt werden.");
				System.exit(0);
			}
			g.addNode(src);
			g.addNode(dst);
			
			g.addEdge(src, dst);
			
			try{
				zeile = file.readLine();
			}
			catch(Exception e)
			{
				zeile = null;
			}
		}

		try{
			file.close();
		}
		catch(Exception e)
		{
			System.out.println("FileReader konnte nicht geschlossen werden.");
			System.exit(0);
		}
	return g;
	}

	public char[] getNodes()
	{
		int max = 0;
		int x = 0;
		for(Node n : nodes)
		{
			x = n.getId();
			if(x > max)
			{
				max = x;
			}
		}
		char[] arr = new char[max+1];
		d = new int[max+1];
		lambda = new Node[max+1];

		for(int i=0; i<d.length; i++)
		{
			if(contains(i))
			{
				d[i] = 0;
			}
			else{
				d[i] = -1;
			}
			lambda[i] = null;
		}

		for(Node n : nodes)
		{
			x = n.getId();
			arr[x] = 'w';
		}
		return arr;
	}

	public String toString()
	{
		String ausgabe = "";

		for(Node n : nodes)
		{
			ausgabe = ausgabe + n.getId() + ": " + n.toString() + "\n";
		}
		return ausgabe;
	}

	public int[] bfs(Graph g, int id)
	{
		char[] arr = getNodes();
		ArrayList<Node> liste = new ArrayList<Node>();
		liste.add(getNode(id));
		int x = 0;

		while(liste.size() != 0)
		{
			Node u = liste.get(0);
			ArrayList<Node> neighbours = u.getNeighbours();
			for(Node v : neighbours)
			{
				x = v.getId();
				if(d[x] == -1)
				{
					d[x] = 0;
				}
				if(arr[x] == 'w')
				{
					arr[x] = 'g';
					d[x] = d[u.getId()] + 1;
					lambda[x] = u;
					liste.add(v);
				}
			}
			liste.remove(0);
			arr[u.getId()] = 's';
		}
		return d;
	}
}

