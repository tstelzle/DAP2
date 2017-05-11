//Die Klasse implementiert Bubblesort mit Zeitmessung 
/*
**Autor: Frederic Arnold und Tarek Stelzle
*/
public class Bubblesort
{
	public Bubblesort()
	{

	}

	public static void main(String[] args)
	{
		//Initialisieren des float Parameters fuer die Zeit
		float zeit = 0f;

		//Auslesen des uebergebenen Zeitparameters mit Pruefung auf Korrektheit
		if(args.length != 1)
		{
			System.out.println("Falsche Argumentanzahl!");	
			return;
		}
		else{
			try{
				zeit = Float.parseFloat(args[0]);				
			}
			catch(Exception e){
				System.out.println("Es wurde keine Zahl eingegeben!");
				return;
			}
		}

		if(zeit < 0)
		{
			System.out.println("Zeit ist kleiner null!");
			return;
		}

		//Ausgabe der Zeit - nur zur Kontrolle
		System.out.println("Übergebenes Argument: " + zeit);

		//Feld zur Sortierung befuellen
		int[] tmp = Feld(1000);

		//Variablen zur Zeitmessung (msek = uebergebene Zeit als long zum Vergleich)
		long msecInitial, msecArg;
		msecArg = (long)(zeit *1000);	
		System.out.println("Übergebenes Argument als Long:" + msecArg);
		
		//Berechnung der benoetigten Zeit
		msecInitial = sortAndTime(tmp);
		
		while(msecInitial < msecArg) {
			System.out.println("\nFeldlänge: " + tmp.length);
			System.out.println("Bubblesort war schneller als ihre Eingabe. Es brauchte: " + msecInitial +" Millisekunden");
			tmp = Feld(tmp.length*2);
			msecInitial = sortAndTime(tmp);
		}
		binarySearch(tmp.length/2, tmp.length, msecArg);
		/*
		//Pruefung ob Bubblesort laenger dauerte als die uebergebene Variable (zeit)
		if(msecInitial < msecArg)
		{	
			System.out.println("Feldlänge: " + tmp.length);
			System.out.println("Bubblesort war schneller als ihre Eingabe. Es brauchte: " + msecInitial +" Millisekunden");
			System.out.println();
			tmp = Feld(tmp.length * 2);
		}
		else{
			System.out.println("Bubblesort brauch länger als ihre Eingabe. Binäre Suche wird jetzt gestartet!");
			binarySearch((tmp.length / 2), tmp.length, msecArg);
			System.out.println();

		}	
		*/

	}

	//Methode zum ueberpruefen ob ein Feld aufsteigend sortiert ist
	public static boolean sortiert(int[] arr)
	{
		for(int i=1; i< arr.length; i++)
		{
			if(arr[i-1] > arr[i])
			{
				return false;
			}
		}
		return true;
	}
	
	//Methode zum Initialisieren von dem Feld (n ist die groesse des Feldes)
	public static int[] Feld(int n)
	{
		int[] arr = new int[n];
		for(int i=0; i < n; i++)
		{
			arr[i] = n+1-i;
		}
		return arr;
	}
	
	//Algorithmus Binärsuche
	public static void binarySearch(int lborder, int rborder, long msek)
	{
		int mborder = (rborder+lborder)/2;
		long tStart, tEnd, tduration;
		int[] tmp = Feld(mborder);

		tStart = System.currentTimeMillis();
		bubbleSort(tmp);
		tEnd = System.currentTimeMillis();

		tduration = tEnd - tStart;
		
		System.out.println("\nFeldlänge: " + tmp.length);
		System.out.println("Bubblesort braucht " + tduration + " Millisekunden.");
		if(tduration - 100 < msek && msek < tduration + 100)
		{
			System.out.println("Fertig!");
			return;
		}
		if(tduration < msek)
		{
			System.out.println("Bubblesort wird im oberen Bereich des Feldes gestartet!");
			binarySearch(mborder, rborder, msek);
		}
		if(tduration > msek)
		{
			System.out.println("Bubblesort wird im unteren Bereich des Feldes gestartet!");
			binarySearch(lborder, mborder, msek);
		}	


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
	public static long sortAndTime(int[] tmp)
	{
		//Bubblesort Ausfuehrung und Zeitmessung
		long tStart = System.currentTimeMillis();
		bubbleSort(tmp);
		long tEnd = System.currentTimeMillis();
		
		//Berechnung der benoetigten Zeit
		return tEnd - tStart;
	}
}
