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

		notNaiv(pos);
		notNaiv(neg);
		notNaiv(both);

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
	
	public static void notNaiv(int[] arr)
	{
		int n = arr.length;
		int[] links = new int[n+1];
		int[] rechts = new int[n+1];
		int[] summe = new int[n+1];

		links[0] = 0;
		rechts[0] = 0;
		summe[0] = 0;

		for(int i=1; i<n+1; i++)
		{
			if(summe[i-1] == 0)
			{
				if(arr[i-1] > 0)
				{
					links[i] = i-1;
					rechts[i] = i-1;
					summe[i] = arr[i-1];
				}
				else{
					summe[i] = 0;
					links[i] = i;
					rechts[i] = i;
				}
			}
			else if(summe[i-1] + arr[i-1] < arr[i-1])
			{
				links[i] = i-1;
				rechts[i] = i-1;
				summe[i] = arr[i-1];
			}
			else
			{
				links[i] = links[i-1];
				rechts[i] = i-1;
				summe[i] = summe[i-1] + arr[i-1];
			}
		}

		int max = summe[0];
		int pos = 0;

		for(int i=1; i<summe.length; i++)
		{
			if(summe[i] > max)
			{
				max = summe[i];
				pos = i;
			}
		}
		
		if(max == 0)
		{
			max = arr[0];
			pos = 0;
			for(int i=1; i<arr.length; i++)
			{
				if(max < arr[i])
				{
					max = arr[i];
					pos = i;
				}	
			}
		}
		
		System.out.println("Linke Grenze: " + links[pos] + ", Rechte Grenze: " + rechts[pos]  + ", Gesamtsumme: " + max);
	}

	public static void naiv(int[] arr)
	{
		int n = arr.length +1;
		int[][] summe = new int[n][n];
		summe[0][0] = 0;
		int[] links = new int[n];
		int[] rechts = new int[n];
		int sum = 0;
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
			e = 0;

			for(int i = 1; i<arr.length; i++)
			{
				if(sum < arr[i])
				{
					sum = arr[i];
					s = e = i;
				}
			}
		}
		

		// TODO Fehler, wenn in arr "both" die ersten 2 Werte negativ sind und der letzte positive gibt er was falsches aus
		if(pos == false && neg == false)
		{
			sum = 0;
			for(int i=0; i<arr.length; i++)
			{
				for(int j=0; j<arr.length; j++)
				{		
					if(j == 0)
					{
						if(arr[j] > 0)
						{
							summe[i][j] = arr[j];	
							links[i] = j;
							rechts[j] = j;
						}
						else
						{
							summe[i][j] = 0;
							links[i] = j;
							rechts[j] = j;
						}
					}
					else
					{
						summe[i][j]  = summe[i][j-1] + arr[j];
						if(i == 0)
						{
							links[i] = 0;
						}
						else
						{
							links[i] = links[i-1];
						}
						rechts[j] = rechts[j-1] + 1;
					}
				}
			}
			
			for(int i=0; i<summe.length; i++)
			{
				for(int j=0; j<summe[i].length; j++)
				{
					if(sum < summe[i][j])
					{
						sum = summe[i][j];
						s = links[i]; 
						e = rechts[j];
					}
				}
			}
		}

		System.out.print("Linke Grenze: " + s + ", Rechte Grenze: " + e + ", Gesamtsumme: " + sum);
	}
}
