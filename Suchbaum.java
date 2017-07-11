public class Suchbaum
{
	public static void main(String[] args)
	{
		String arr = null;

		if(args.length != 1)
		{
			System.out.println("Falsche Argumentanzahl.");
			System.exit(0);
		}
	
		try{
			arr = args[0];
		}
		catch(Exception e)
		{
			System.out.println("Das Array konnte nicht eingelesen werden.");
			System.exit(0);
		}
		
		String t = arr;
		int counter = 0;
		int x = 0;

		while(x != -1)
		{
			x = t.indexOf(",");
			t = t.substring(x+1, t.length());
				counter = counter +1;
		}

		int[] zahlen = new int[counter];
		
		x = 0;
		int y = 0;
		arr = arr.substring(1, arr.length());
		for(int i=0; i < zahlen.length; i++)
		{
			x = arr.indexOf(",");
			if(x == -1)
			{
				zahlen[i] = Integer.parseInt(arr.substring(0,arr.indexOf("]")));
			}
			else{	
				zahlen[i] = Integer.parseInt(arr.substring(0, x));
				arr = arr.substring(x + 1, arr.length());
			}
		}

		for(int i=0; i<zahlen.length; i++)
		{
			System.out.println(zahlen[i]);
		}
	}

	public static void baum(int[] arr)
	{
		
	}
}
