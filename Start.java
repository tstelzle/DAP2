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

	}
}
