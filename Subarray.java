import java.util.Random;

public class Subarray {
	
	public Subarray()
	{
		
	}
	
	public static void main(String[] args)
	{
		int n = 0;
		
		try{
			n = Integer.parseInt(args[0]);
		}
		catch(Exception e)
		{
			System.out.println("Parameter konnte nicht eingelesen werden.");
			System.exit(0);
		}
		
		if(n == 0)
		{
			System.out.println("Länge ist null! - Denk nochmal nach!");
			System.exit(0);
		}

		Random rand = new Random();
		
		int[] pos = new int[n];
		int[] neg = new int[n];
		int[] both = new int[n];
			
		for(int i=0; i<pos.length; i++)
		{
			pos[i] = rand.nextInt(100);
			neg[i] = rand.nextInt(99) - 100;
			both[i] = rand.nextInt(200) -100;
		}
		
		System.out.println("Folgende Arrays mit Laenge " + n + " wurden erstellt.");
		
		printArr(pos);
		printArr(neg);
		printArr(both);

		zeitMessung(pos);
		zeitMessung(neg);
		zeitMessung(both);
	}
	
	public static void printArr(int[] arr)
	{
		System.out.print("[");
		for(int i=0; i<arr.length; i++)
		{
			if(i == arr.length -1)
			{
				System.out.print(arr[i]);
			}
			else{
				System.out.print(arr[i] + ", ");
			}
		}
		System.out.println("]");
	}
	
	public static void zeitMessung(int[] arr)
	{
		long tStart = System.currentTimeMillis();
		naiv(arr);
		long tEnd = System.currentTimeMillis();
		System.out.println(", Dauer: " + (tEnd - tStart));
	}

	public static void naiv(int[] arr)
	{
		int sum = 0;
		int sum2 = 0;
		int s = 0;
		int e = arr.length -1;
		boolean pos = true;
		boolean neg = true;

		for(int i=0; i<arr.length; i++)
		{
			if(arr[i] > 0)
			{
				pos = false;
			}

			if(arr[i] < 0)
			{
				neg = false;
			}
		}

		if(neg)
		{
			for(int i = 0; i<arr.length; i++)
			{
				sum += arr[i];
			}
		}

		if(pos)
		{
			sum  = arr[0];

			for(int i = 1; i<arr.length; i++)
			{
				if(sum < arr[i])
				{
					sum = arr[i];
				}
			}
		}
		
		if(pos == false && neg == false)
		{
			sum = 0;
			for(int i=0; i<arr.length; i++)
			{
				for(int j=0; j<arr.length; j++)
				{		
					sum2 = 0;

					for(int x=i; x<= j; x++)
					{
						sum2 += arr[x];
					}

					if(sum2 > sum)
					{
						s = i;
						e = j;
						sum = sum2;
					}
				}
			}
		}

		System.out.print("Linke Grenze: " + s + ", Rechte Grenze: " + e + ", Gesamtsumme: " + sum);
	}

	public static int[] algorithm(int[] arr)
	{
		if(arr.length == 1)
		{
			if(arr[0] < 0)
			{
				// TODO return [0,1];
			}
		}
		for(int j=0; j<arr.length; j++)
		{
		
		}
		return null;
	}

}
