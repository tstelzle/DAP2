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
	
	//Methode zur Ausgabe einer int Matrix -- Ueberpruefung der Korrektheit
	public static void printArrArr(int[][] arr)
	{
		for(int i=0; i<arr.length; i++)
		{
			for(int j=0; j<arr[i].length; j++)
			{
				System.out.print(arr[i][j] + " ");
			}
			System.out.println("");
		}
	System.out.println("");
	}
	
	//Methode zur Ausgabe einer Integer Matrix -- Ueberpruefung der Korrektheit
	public static void printArrArr(Integer[][] arr)
	{
		for(int i=0; i<arr.length; i++)
		{
			for(int j=0; j<arr[i].length; j++)
			{
				System.out.print(arr[i][j] + " ");
			}
			System.out.println("");
		}
	System.out.println("");

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
		System.out.println(", Dauer: " + (tEnd - tStart));
	}
	
	//Methode welche den nicht naiven Algorithmus auf das Feld ausfuehrt und eine Zeitmessung vornimmt
	public static void zeitMessungNNaiv(int[] arr)
	{
		long tStart = System.currentTimeMillis();
		notNaiv(arr);
		long tEnd = System.currentTimeMillis();
		System.out.println(", Dauer: " + (tEnd - tStart));

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
			// Erster Fall, vorherige Summe ist gleich null, daraus folgt negativer Wert oder erstes Feld 
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
			//Wenn die Zahl davor schon negativ war, soll das Feld nicht noch kleiner werden
			else if(summe[i-1] + arr[i-1] <= arr[i-1])
			{
				links[i] = i-1;
				rechts[i] = i-1;
				summe[i] = arr[i-1];
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
		//Felder und Variablen werden deklariert
		int n = arr.length;
		Integer[][] summe = new Integer[n][n];
		summe[0][0] = 0;
		int[][] links = new int[n][n];
		int[][] rechts = new int[n][n];
		int sum = 0;
		int s = 0;
		int e = arr.length -1;
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
		
		//Wenn sowohl negativ als auch positive Zahlen vorhanden sind
		if(pos == false && neg == false)
		{
			//Durchlaufen aller moeglichen Subarraykombinationen mit zwei For-Schleifen 
			for(int i=0; i<arr.length; i++)
			{
				for(int j=0; j<arr.length; j++)
				{		
					//Wenn wir in der ersten Spalte sind, kann er nicht auf den Vorgaenger zugreifen, deswegen braucht man einen Sonderfall
					if(j == 0)
					{
						//Wenn die Zahl in arr[0] groesser 0 ist (positiv) ist das die neue Summe
						if(arr[j] >= 0)
						{
							summe[i][j] = arr[j];	
							links[i][j] = j;
							rechts[i][j] = j;
						}
						//ansonsten ist sie kleiner null, deswegen wir die Summe auf null gesetzt
						else
						{
							summe[i][j] = null;
							links[i][j] = 0;
							rechts[i][j] = 0;
						}
					}
					//Zeiger ist nicht in der ersten Spalte
					else
					{
						//Zahl davor war null, Summe ist gleich null			
						if(summe[i][j-1] == null)
						{
							if(arr[j] < 0)
							{
								summe[i][j] = null;
								links[i][j] = 0;
								rechts[i][j] = 0;
							}
							else{
								summe[i][j] = arr[j];
								links[i][j] = j;
								rechts[i][j] = j;
							}
						}
						//Summe mit jetziger Zahl ist kleiner als Summe davor, d.h. Zahl ist negativ
						else if(summe[i][j-1] + arr[j] < summe[i][j-1])
						{
							summe[i][j]  = null;
							links[i][j] = j;
							rechts[i][j] = j;
						}
						//Ansonsten ist die Zahl positiv und die Summe davor war positiv, d.h. Subarray wird erweitert
						else{
							summe[i][j]  = summe[i][j-1] + arr[j];
							links[i][j] = links[i][j-1];
							rechts[i][j] = rechts[i][j-1] + 1;
						}
					}
				}
			}
			
			//Maximum wird gesucht und die Grenzen werden passend dazu gespeichert
			for(int i=0; i<summe.length; i++)
			{
				for(int j=0; j<summe[i].length; j++)
				{
					if(summe[i][j] == null)
					{
							
					}
					else if(sum < summe[i][j])
					{
						sum = summe[i][j];
						s = links[i][j]; 
						e = rechts[j][j];
					}
				}
			}
		}
		//Ausgabe der Loesung
		System.out.print("Linke Grenze: " + s + ", Rechte Grenze: " + e + ", Gesamtsumme: " + sum);
	}
}
