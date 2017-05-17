public class Quicksort
{
	public Quicksort()
	{

	}

	public static void main(String[] args)
	{
		if(args.length == 0 || args.length > 1)
		{	
			System.out.println("Falsche Argumenteingabe!");
			return;
		}

		int arrSize = 0;

		try
		{
			arrSize = Integer.parseInt(args[0]);
		}catch(Exception e)
		{	
			System.out.println("Keine Zahl uebergeben!");
			return;
		}

		if(arrSize < 0)
		{
			System.out.println("Die Zahl darf nicht negativ sein.");
			return;
		}

		int[] arr = genRandom(arrSize);
		
		System.out.println(isSorted(arr));
		
		long tStart, tEnd;

		tStart = System.currentTimeMillis();
		quicksort(arr, 0, arr.length-1);
		tEnd = System.currentTimeMillis();
		
		System.out.println("Dauer: " + (tEnd-tStart) + " ms");

		System.out.println(isSorted(arr));
	}

	public static boolean isSorted(int[] arr)
	{
		for(int i = 1; i<arr.length; i++)
		{
			if(arr[i-1] > arr[i])
			{
				return false;	 
			}
		}

		return true;
	}

	public static void quicksort(int[] arr, int l, int r)
	{
		if(l < r)
		{
			int i = l;
			int j = r;
			int pivot = arr[(l+r)/2];
			while(i <= j)
			{
				while(arr[i] < pivot)
				{
					i = i+1;
				}
				while(arr[j] > pivot)
				{
					j = j-1;
				}
				if(i <= j)
				{
					int tmp = arr[i];
					arr[i] = arr[j];
					arr[j] = tmp;
					i = i+1;
					j = j-1;
				}
			}
		quicksort(arr, l, j);
		quicksort(arr, i ,r);
		}
	}

	public static int[] genRandom(int size)
	{
		java.util.Random numberGen = new java.util.Random();

		int[] arr = new int[size];

		for(int i=0; i < arr.length; i++)
		{
			arr[i] = numberGen.nextInt();
		}

		return arr;
	}
}
