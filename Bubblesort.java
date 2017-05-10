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
			}
		}

		if(zeit < 0)
		{
			System.out.println("Zeit ist kleiner null!");
			return;
		}

		//Ausgabe der Zeit - nur zur Kontrolle
		System.out.println("Uebergebenes Argument:" + zeit);

		//Feld zur Sortierung befuellen
		int[] tmp = Feld(50000);

		//Variablen zur Zeitmessung (msek = uebergebene Zeit als long zum Vergleich)
		long tStart, tEnd, msec, msek;
		msek = (long)(zeit *1000);	
		System.out.println("Uebergebenes Argument als Long:" + msek);
		
		if(sortiert(tmp))
		{
			System.out.println("Feld Sortiert!");
		}
		else{
			System.out.println("Feld nicht sortiert!");
		}

		//Bubblesort Ausfuehrung und Zeitmessung
		tStart = System.currentTimeMillis();
		bubbleSort(tmp);
		tEnd = System.currentTimeMillis();
		
		//Berechnung der benoetigten Zeit
		msec = tEnd - tStart;
		
		System.out.println("Benoetigte Zeit:" + msec);
		
		//Pruefung ob Bubblesort laenger dauerte als die uebergebene Variable (zeit)
		if(msec < msek)
		{
			System.out.println("Bubblesort braucht nicht so lang!");
		}
		else{
			System.out.println("Bubbleosort braucht laenger!");
		}	

		if(sortiert(tmp))
		{
			System.out.println("Feld Sortiert!");
		}
		else{
			System.out.println("Feld nicht sortiert!");
		}
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
		int[] arr = new int[n+1];
		for(int i=0; i <=n; i++)
		{
			arr[i] = n+1-i;
		}
		return arr;
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
}
