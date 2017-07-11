public class Start
{
	public static void main(String[] args)
	{
		if(args.length != 1 && args.length != 3)
		{
			System.out.println("Falsche Argumentanzahl.");
			System.exit(0);
		}
		
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
		if(args.length == 1)
		{
			System.out.println(g.toString());
		}

		if(args.length == 3)
		{
			String algo = "";
			int startknoten = 0;

			try{
				algo = args[1];
				startknoten = Integer.parseInt(args[2]);
			}
			catch(Exception e)
			{
				System.out.println("Argument konnte nicht eingelesen werden.");
				System.exit(0);
			}

			if(!algo.equals("bfs"))
			{
				System.out.println("Algorithmus nicht bekannt.");
				System.exit(0);
			}

			int[] ausgabe = g.bfs(g, startknoten);
			for(int i=0; i<ausgabe.length; i++)
			{
				System.out.println("Abstand von: " + startknoten + " zu " + i + " ist: " + ausgabe[i]);
			}
		}
	}
}
