public class Lcs2
{
	public Lcs2()
	{

	}

	public static void main(String[] args)
	{
		int n = -1;

		if(args.length != 1)
		{
			System.out.println("Falsche Argumentanzahl.");
			System.exit(0);
		}

		try{
			n = Integer.parseInt(args[0]);
		}
		catch(Exception e)
		{
			System.out.println("Varialbe n konnte nicht eingelesen werden.");
			System.exit(0);
		}

		if(n<0)
		{
			System.out.println("Die Variable n darf nicht negativ sein.");
			System.exit(0);
		}

		java.util.Random r = new java.util.Random();

		String a = randStr(n, r);
		//System.out.println("String a: " + a);
		String b = randStr(n, r);
		//System.out.println("String b: " + b);

		int[][] grid = buildgrid(a,b);
		System.out.println("Längster Substring: " + grid[a.length()][b.length()]);

		System.out.printf("%s%s%s%s%s%s\n", "String a: ", a, "\nString b: ", b, "\nLCS: ", lcs(a,b));

		//System.out.println(lcs(a, b));
	}
/*
	public static void main(String[] args)
	{
		if(args.length > 2)
			System.out.println("Achtung, überzählige Argumente!");
		String a = args[0];
		String b = args[1];

		java.util.Random r = new java.util.Random();

		int[][] grid = buildgrid(a,b);
		System.out.println("Längster Substring: " + grid[a.length()][b.length()]);

		System.out.printf("%s%s%s%s%s%s\n", "String a: ", a, "\nString b: ", b, "\nLCS: ", lcs(a,b));

		//System.out.println(lcs(a, b));
		System.out.println("");
		printGrid(grid);
	}
*/
	public static String lcs(String a, String b)
	{
		int[][] grid = buildgrid(a, b);
		return extractString(grid, a);
	}

	public static void printGrid(int[][] arr)
	{
		for(int i=0; i < arr.length; i++) {
			for(int j=0; j < arr[i].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println("");
		}
	}

	public static int[][] buildgrid(String a, String b)
	{
		int[][] grid = new int[a.length()+1][b.length()+1];

		for(int i = 0; i < grid.length; i++)
		{
			grid[i][0] = 0;
		}

		for(int j = 0; j < b.length(); j++)
		{
			grid[0][j] = 0;
		}

		for(int i = 1; i < grid.length; i++)
		{
			for(int j = 1; j < grid[i].length; j++)
			{
				if(a.charAt(i-1) == b.charAt(j-1))
				{
					grid[i][j] = grid[i-1][j-1] +1;
				}
				else if(grid[i-1][j] >= grid[i][j-1])
				{
					grid[i][j] = grid[i-1][j];
				}
				else
				{
					grid[i][j] = grid[i][j-1];
				}
			}
		}

		return grid;
	}

	public static String extractString(int[][] arr, String a)
	{
		StringBuilder str = new StringBuilder(Math.max(arr.length, arr[0].length));
		int i = arr.length-1;
		int j = arr[0].length-1;

		while(i > 0 && j > 0)
		{
			if(arr[i][j-1] > arr[i-1][j-1])
			{
				 j -= 1;
			}
			else if(arr[i-1][j] > arr[i-1][j-1])
			{
				i -= 1;	
			}
			else
			{
				if(arr[i-1][j-1] < arr[i][j])
				{
					str.insert(0, a.charAt(i-1));
				}
				i -= 1;
				j -= 1;
			}
		}

		return str.toString();
	}


	public static String randStr(int n, java.util.Random r) 
	{
		String alphabet =
		/*abcdefghijklmnopqrstuvwxyz*/"ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder res = new StringBuilder(n);
		while (--n>=0) {
			res.append(alphabet.charAt(r.nextInt(alphabet.length())));
		}
		return res.toString();
	}
}
