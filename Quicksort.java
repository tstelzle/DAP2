//Die Klasse Quicksort wendet den Quickosrt algorithmus auf ein Feld mit Zufallszahlen an und misst die Zeit

//Autoren: Tarek Stelyzle und Frederic Arnold

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
		
		//Pruefung ob das Array schon sortiert ist
		System.out.println(isSorted(arr));
		
		//Variableninitialisierung fuer die Zeitmessung
		long tStart, tEnd;
		
		//Zeitmessung starten
		tStart = System.currentTimeMillis();
		//Quicksort wird ausgefuhrt
		quicksort(arr, 0, arr.length-1);
		//Zeitmessung stoppen
		tEnd = System.currentTimeMillis();
		
		System.out.println("Dauer: " + (tEnd-tStart) + " ms");
		
		//Pruefung ob das Array aufsteigen sortiert wurde
		System.out.println(isSorted(arr));
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
	
	//Quicksortalgorithmus nach Aufgabenstellung
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
