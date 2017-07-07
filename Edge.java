public class Edge
{
	private Node src;
	private Node dst;

	public Edge(Node one, Node two)
	{
		src = one;
		dst = two;
	}

	public static void main(String[] args)
	{
		Node n = new Node(5);
		Node m = new Node(3);
		Edge e = new Edge(n, m);
		System.out.println(e.getSrc());
		System.out.println(e.getDst());
		System.out.println(e.getSrc().toString());
	}

	public Node getSrc()
	{
		return src;
	}
	
	public Node getDst()
	{
		return dst;
	}
}
