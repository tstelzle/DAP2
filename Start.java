public class Start
{
	public static void main(String[] args)
	{
		String filePath = "";

		try{
			filePath = args[0];
		}
		catch(Exception e)
		{
			System.out.println("Dateipfad konnte nicht eingelesen werden.");
			System.exit(0);
		}
		Graph g = new Graph();
		g = g.fromFile(filePath);
		System.out.println(g.toString());

		int startknoten = 0;

		int[] ausgabe = g.bfs(g, startknoten);
		for(int i=0; i<ausgabe.length; i++)
		{
			System.out.println("Abstand von: " + startknoten + " zu " + i + " ist: " + ausgabe[i]);
		}
		/*
		char[] arr = g.getNodes();
		for(int i=0; i<arr.length; i++)
		{
			System.out.println(i + " " + arr[i]);
		}
		*/
	}
}
