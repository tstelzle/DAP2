import java.util.Random;
/*
* Autoren: Tarek Stelzle und Frederic Arnold
* Die Klasse implementiert eine korrekte Loesung zu Aufgabe 10.1
*/
public class Subarray {
	
	//Main Methode
	public static void main(String[] args)
	{
		//Deklaration des einzulesenden Paramters
		int n = 0;
		
		//Versuchen den Paramter einzulesen
		try{
			n = Integer.parseInt(args[0]);
		}
		catch(Exception e)
		{
			System.out.println("Parameter konnte nicht eingelesen werden.");
			System.exit(0);
		}
		
		//Falls kein Paramtere eingelesen wurder, oder der Paramter 0 ist wird abgebrochen, daraus folgt keine sinnvolle Eingabe
		if(n == 0)
		{
			System.out.println("Laenge ist null! - Denk nochmal nach!");
			System.exit(0);
		}

		//erstellen eines neuen RandomGenerators
		Random rand = new Random();
		
		//Deklaration der verschienden Testfelder
		int[] pos = new int[n];
		int[] neg = new int[n];
		int[] both = new int[n];
			
		//Befuellen der Testfelder
		for(int i=0; i<pos.length; i++)
		{
			pos[i] = rand.nextInt(100);
			neg[i] = rand.nextInt(99) - 100;
			both[i] = rand.nextInt(200) -100;
		}
		
		//Ausgabe der Loesung
		System.out.println("Folgende Arrays mit Laenge " + n + " wurden erstellt.");
		
		printArr(pos);
		printArr(neg);
		printArr(both);

		System.out.println("Vorgegebener Agorithmus:");
	
		zeitMessungNNaiv(pos);
		zeitMessungNNaiv(neg);
		zeitMessungNNaiv(both);

		System.out.println("Naiver Algorithmus:");

		zeitMessungNaiv(pos);
		zeitMessungNaiv(neg);
		zeitMessungNaiv(both);
	}
	
	//Methode zur Ausgabe der Testfelder
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
	
	//Methode welche den naiven Algorithmus auf das Feld ausfuehrt und eine Zeitmessung vornimmt
	public static void zeitMessungNaiv(int[] arr)
	{
		long tStart = System.currentTimeMillis();
		naiv(arr);
		long tEnd = System.currentTimeMillis();
		System.out.println(", Dauer: " + (tEnd - tStart) + "ms");
	}
	
	//Methode welche den nicht naiven Algorithmus auf das Feld ausfuehrt und eine Zeitmessung vornimmt
	public static void zeitMessungNNaiv(int[] arr)
	{
		long tStart = System.currentTimeMillis();
		notNaiv(arr);
		long tEnd = System.currentTimeMillis();
		System.out.println(", Dauer: " + (tEnd - tStart) + "ms");

	}
		
	//Implementation des vorgegebenen Algorithmus
	public static void notNaiv(int[] arr)
	{
		//Felder deklarieren fuer die dynamische Speicherung
		int n = arr.length;
		int[] links = new int[n+1];
		int[] rechts = new int[n+1];
		Integer[] summe = new Integer[n+1];

		//erste Werte initialisieren
		links[0] = 0;
		rechts[0] = 0;
		summe[0] = null;
		
		//Durchlaufen des Arrays
		for(int i=1; i<n+1; i++)
		{
			// Erster Fall, vorherige Summe ist gleich NULL, daraus folgt negativer Wert oder erstes Feld 
			if(summe[i-1] == null)
			{
				//Wenn die Wert groesser null ist, wird das Summenfeld und die Grenzen neu gesetze auf diesen einen Wert
				if(arr[i-1] >= 0)
				{
					links[i] = i-1;
					rechts[i] = i-1;
					summe[i] = arr[i-1];
				}
				//Ansonsten wird das Feld wieder auf 0 initialisert
				else{
					summe[i] = null;
					links[i] = i;
					rechts[i] = i;
				}
			}
			//Wenn die vorherige Summe mit dem akutellen Feld kleiner ist als die vorherige Summe => negative Zahl => aktuelle Summe wir auf NULL gesetzt 
			else if(summe[i-1] + arr[i-1] < summe[i-1])
			{
				links[i] = i-1;
				rechts[i] = i-1;
				summe[i] = null;
			}
			
			// Ansonsten wird die aktuelle Zahl in die Summe mit aufgenommen und die Grennzen werden angepasst
			else
			{
				links[i] = links[i-1];
				rechts[i] = i-1;
				summe[i] = summe[i-1] + arr[i-1];
			}
		}

		//Das Maximum wird herausgefunden
		int max = 0;
		int pos = 0;

		for(int i=0; i<summe.length; i++)
		{
			if(summe[i] == null)
			{

			}
			else if(summe[i] > max)
			{
				max = summe[i];
				pos = i;
			}
		}
		
		//Wenn das Maximum null ist, sind nur negative Zahlen vorhanden, deswegen wird die kleinste negative Zahl genommen 
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
		
		//Ergebnis wird ausgegeben
		System.out.print("Linke Grenze: " + links[pos] + ", Rechte Grenze: " + rechts[pos]  + ", Gesamtsumme: " + max);
	}

	public static void naiv(int[] arr)
	{
		int asum = 0;
		int msum = 0;
		int links = 0;
		int rechts = 0;
		boolean pos = true;
		boolean neg = true;
	
		//Ueberpruefung ob alle Zahlen negativ oder positiv sind
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
				msum += arr[i];
			}
			rechts = arr.length -1;
		}

		if(pos)
		{
			msum  = arr[0];

			for(int i = 1; i<arr.length; i++)
			{
				if(msum < arr[i])
				{
					msum = arr[i];
					links = rechts = i;
				}
			}
		}

		if(pos == false && neg == false)
		{
			//Erste For-Schleife fuer die linke Grenze
			for(int i=0; i<arr.length; i++)
			{	
				//aktuelle Summe wird auf 0 gesetzt;
				asum = 0;

				//Zweite For-Schleife fuer die rechte Grenze
				for(int j=i; j<arr.length; j++)
				{		
					//Wenn der aktuelle Wert groesser null ist
					if(arr[j] >= 0)
					{
						//aktuelle Summe wir um das Arrayfeld erhöht
						asum += arr[j];
						//Wenn die aktuelle Summe groesser als die maximale Summe wird die msum verändert und die Grenzen angepasst
						if(asum > msum)
						{
							msum = asum;
							links = i;
							rechts = j;					
						}
					}
					//bei einem negativen Wert wird die linke Grenze neu gesetzt
					else{
						break;
					}
				}
			}
		}
		//Ausgabe der Lösung
		System.out.print("Linke Grenze: " + links + ", Rechte Grenze: " + rechts + ", Gesamtsumme: " + msum);
	}	
}
