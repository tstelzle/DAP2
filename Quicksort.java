//Die Klasse Quicksort wendet den Quickosrt algorithmus auf ein Feld mit Zufallszahlen an und misst die Zeit

//Autoren: Tarek Stelzle und Frederic Arnold

public class Quicksort
{
	public Quicksort()
	{

	}
	
	public static void main(String[] args)
	{
		//Eingabe Parameter pruefen
		if(args.length == 0 || args.length > 1)
		{	
			System.out.println("Falsche Argumenteingabe!");
			return;
		}
		
		//Groesse des Arrays initialisieren
		int arrSize = 0;

		//Parameter fuer die Arraygroesse der Variable zuweisen
		try
		{
			arrSize = Integer.parseInt(args[0]);
		}catch(Exception e)
		{	
			System.out.println("Keine Zahl uebergeben!");
			return;
		}
		
		//negative Zahlen werden nicht akzeptiert
		if(arrSize < 0)
		{
			System.out.println("Die Zahl darf nicht negativ sein.");
			return;
		}
		
		//Feld wird initiliasiert, die Methode genRandom befuell das Array
		int[] arr = genRandom(arrSize);
		
		int[] quick = copyArray(arr);
		zeit("Quick", quick);
		assert isSorted(quick);
		int[] insertion = copyArray(arr);
		zeit("Insertion", insertion);
		assert isSorted(insertion);
		int[] bubble = copyArray(arr);
		zeit("Bubble", bubble);
		assert isSorted(bubble);
		int[] merge = copyArray(arr);
		zeit("Merge", merge);
		assert isSorted(merge);
	}	

	public static void zeit(String algorithm, int[] arr)
	{
		long tStart, tEnd;
		if(algorithm.equals("Quick"))
		{
			tStart = System.currentTimeMillis();
			quicksort(arr, 0, arr.length-1);
			tEnd = System.currentTimeMillis();
			System.out.println("Quicksortdauer: " + (tEnd-tStart) + " ms");
			System.out.println("Sorted? " + isSorted(arr));
		}
		else if(algorithm.equals("Insertion"))
		{
			tStart = System.currentTimeMillis();
			insertionSort(arr);
			tEnd = System.currentTimeMillis();
			System.out.println("Insertionsortdauer: " + (tEnd-tStart) + " ms");
			System.out.println("Sorted? " + isSorted(arr));
		}
		else if(algorithm.equals("Bubble"))
		{
			tStart = System.currentTimeMillis();
			bubbleSort(arr);
			tEnd = System.currentTimeMillis();
			System.out.println("Bubblesortdauer: " + (tEnd-tStart) + " ms");
			System.out.println("Sorted? " + isSorted(arr));
		}
		else if(algorithm.equals("Merge"))
		{
			tStart = System.currentTimeMillis();
			mergeSort(arr);
			tEnd = System.currentTimeMillis();
			System.out.println("Mergesortdauer: " + (tEnd-tStart) + " ms");
			System.out.println("Sorted? " + isSorted(arr));
		}

	}

	//Methode zur Pruefung ob ein Array aufsteigend sortiert ist
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

	static int[] copyArray(int[] arr)
	{
		int[] tmp = new int[arr.length];
		for(int i=0; i<arr.length; i++)
			tmp[i] = arr[i];
		return tmp;
	}
	
	//Quicksortalgorithmus nach Aufgabenstellung
	public static void quicksort(int[] arr, int l, int r)
	{
		assert (l>0 || r<arr.length) : "Grenzen falsch.";
		
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
			//FEHLT
			//checkt ob die linke Teiliste nur kleinere Element als pivot beinhaltet, analog fuer die rechte Haelfte
		quicksort(arr, l, j);
		quicksort(arr, i ,r);
		}
	}
	
	public static void insertionSort(int []array)
    	{
        	for(int j = 1; j < array.length; j++) {
            	int key = array[j];
            	int i = j-1;
	        while((i >= 0) && array[i] > key)
		{
               		array[i+1] = array[i];
             		i--;
            	}
           	 array[i+1] = key;
        	}
        	assert isSorted(array) : "insertionSort hat die Liste nicht sortiert :/";
    	}
	
	//Algorithmus Bubblesort wie im Pseudocode vorgegeben
	public static void bubbleSort(int[] array)
	{
		int n = array.length;
		for(int i=0; i < n; i++)
		{
			for(int j=n-1; j>i; j--)
			{
				if(array[j-1] > array[j])
				{
					int tmp = array[j];
					array[j] = array[j-1];
					array[j-1] = tmp;
				}
			}	
		}
	}

	    public static void mergeSort(int []array)
	    {
		int[] tmpArray = new int[array.length];
		mergeSort(array, tmpArray, 0, array.length-1);
		assert isSorted(array) : "mergeSort hat die Liste nicht sortiert :/";
	    }

	    public static void mergeSort(int[] array, int[] tmpArray, int left, int right)
	    {
		if(left < right) {
		    int q = (left+right)/2;
		    mergeSort(array, tmpArray, left, q);
		    mergeSort(array, tmpArray, q+1, right);
		    merge(array, tmpArray, left, right);
		}
	    }

	    public static void merge(int[] array, int[] tmpArray, int left, int right)
	    {
		int mid = (left+right+2)/2;
		int pos1 = left;
		int pos2 = mid;
		for(int i=left; i <= right; i++) {
		    if(pos1 >= mid) {
			tmpArray[i] = array[pos2];
			pos2++;
			continue;
		    }
		    if(pos2 > right) {
			tmpArray[i] = array[pos1];
			pos1++;
			continue;
		    }
		    if(array[pos1] <= array[pos2]) {
			tmpArray[i] = array[pos1];
			pos1++;
			continue;
		    }
		    tmpArray[i] = array[pos2];
		    pos2++;
		}
		for(int i=left; i <= right; i++) {
		    array[i] = tmpArray[i];
		}
	     }
	
	//Methode befuellt ein Array mit zufaelligen Zahlen, die Arraygroesse wird als Parameter uebergeben
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
