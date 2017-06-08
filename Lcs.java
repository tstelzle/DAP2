/*
*Diese Klasse implementiert die Aufgabenstellung aus Aufgabe 7.
*Autoren: Frederic Arnold und Tarek Stelzle
*/

public class Lcs
{
	public Lcs()
	{

	}

	public static void main(String[] args)
	{
		//n wird initialisiert und absichtlich auf einen falschen Wert gesetzt
		int n = -1;

		//Prüfen der Anzahl der Argumente; Abbruch bei mehr oder weniger als 1 Argument
		if(args.length != 1)
		{
			System.out.println("Falsche Argumentanzahl.");
			System.exit(0);
		}

		//Versuchen das Parament einzulesen
		try{
			n = Integer.parseInt(args[0]);
		}
		catch(Exception e)
		{
			System.out.println("Varialbe n konnte nicht eingelesen werden.");
			System.exit(0);
		}

		//Überprüfen ob der Parameter akzeptabel ist
		if(n<0)
		{
			System.out.println("Die Variable n darf nicht negativ sein.");
			System.exit(0);
		}

		//neuen Randomgenerator erstellen
		java.util.Random r = new java.util.Random();

		//erstellen der 2 Strings
		String a = randStr(n, r);
		String b = randStr(n, r);
		//initialisieren der Zeitmessungsvariablen
		long tStart, tEnd;
		//Zeitmessung und Ausführen des Algorithmus
		tStart = System.currentTimeMillis();
		String result = lcs(a, b);
		tEnd = System.currentTimeMillis();

		//Ausgabe der Ergebnisse
		System.out.printf("String a: %s\nString b: %s\nLCS: %s\nLänge: %d\n", a, b, result, result.length());
		System.out.println("");
		System.out.println("Dauer in Millisekunden: " + (tEnd - tStart));
	}

	//Methode welche die zwei Methoden fuer den Algorithmus aufruft
	public static String lcs(String a, String b)
	{
		int[][] grid = buildgrid(a, b);
		return extractString(grid, a);
	}

	//Hilfsfunktion fuer Tests, welche die Matrix ausgibt
	public static void printGrid(int[][] arr)
	{
		for(int i=0; i < arr.length; i++) {
			for(int j=0; j < arr[i].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println("");
		}
	}

	//Methode welche die Matrix erstellt 
	public static int[][] buildgrid(String a, String b)
	{
		//initialiseren der Matrix
		int[][] grid = new int[a.length()+1][b.length()+1];

		//Alle Werte in dem Array werden auf -1 gesetzt fuer die Assertion
		for(int p = 0; p<grid.length; p++)
		{
			for(int o=0; o<grid[p].length; o++)
			{
				grid[p][o] = -1;
			}
		}

		//Randwerte der Matrix werden mit 0 befuellt
		for(int i = 0; i < grid.length; i++)
		{
			grid[i][0] = 0;
		}

		for(int j = 0; j < b.length(); j++)
		{
			grid[0][j] = 0;
		}

		//Die restlichen Werte werden befuellt
		for(int i = 1; i < grid.length; i++)
		{
			for(int j = 1; j < grid[i].length; j++)
			{
				//Wenn die zwei Chars an der selben Position bei den zwei Strings gleich sind, wird der Matrixwert auf die diagonalen Vorgänger +1 gesetzt
				if(a.charAt(i-1) == b.charAt(j-1))
				{
					grid[i][j] = grid[i-1][j-1] +1;
				}
				//ist der "linke" Wert in der Matrix größer als der "obere" Wert, wird as neue Feld auf den "linken" (den größeren) gesetzt
				else if(grid[i-1][j] >= grid[i][j-1])
				{
					grid[i][j] = grid[i-1][j];
				}
				//ansonsten wird das neue Feld der Matrix auf den "oberen" (auch hier den größten) Wert gesetzt
				else
				{
					grid[i][j] = grid[i][j-1];
				}
				assert pruefen(grid[i][j], Math.min(grid[i-1][j], Math.min(grid[i][j-1], grid[i-1][j-1]))) : "Wert ist nicht sinnvoll.";
			}
			//assert: prueft ob jedes Feld der Reihe mit dem Algorithmus befuellt wurde
			assert checker(grid[i]) : "Ein Array Platz wurde nicht gefuellt!";
		}
		
		//Rueckgabe der Matrix
		return grid;
	}
	
	public static boolean pruefen(int x , int y)
	{
		return (x==y) || (x==y+1);
	}

	//Methode fuer die Assertion (sind die Felder mit den Werten des Algorithmus befuellt worden)
	private static boolean checker(int[] arr)
	{
		//fuer jeden wert in der matrix 
		for(int p : arr)
		{
			//wenn er gleich -1 ist wurde ein Feld nicht befuellt
			if(p == -1)
			{
				return false;
			}
		}
		return true;
	}

	//Methode welchen den LCS anhand der Matrix zurueckgibt
	public static String extractString(int[][] arr, String a)
	{
		//Stringbuilder wird initialisiert
		StringBuilder str = new StringBuilder(Math.max(arr.length, arr[0].length));
		//Variablen fuer den Schleifendurchlauf werden initialisert
		int i = arr.length-1;
		int j = arr[0].length-1;

		//solange keien Grenze in der Matrix erreicht ist
		while(i > 0 && j > 0)
		{
			//Wenn das "obere" Feld größer als das "diagonale" ist, wird in der Reihe eins weitergegangen
			if(arr[i][j-1] > arr[i-1][j-1])
			{
				 j -= 1;
			}
			//Wenn das "linke" Feld größer als die "diagonale" ist, wird in der Spalte eins weitergegangen
			else if(arr[i-1][j] > arr[i-1][j-1])
			{
				i -= 1;	
			}
			//Die "diagonale" das größte Feld
			else
			{
				//Ist die "diagonale" auch größer als unsere momentanes Feld?
				if(arr[i-1][j-1] < arr[i][j])
				{
					//Rueckagabestring wird um den Buchstaben an der Position i des Strings erweitert
					str.insert(0, a.charAt(i-1));
				}
				//Schleifendurchlaufsvariablen werden angepasst
				i -= 1;
				j -= 1;
			}
		}
		
		//String wird zurückgegeben
		return str.toString();
	}

	//Methode zur Erzeugugn eines zufaelligen String anhand des Hinweises
	public static String randStr(int n, java.util.Random r) 
	{
		String alphabet =
		"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder res = new StringBuilder(n);
		while (--n>=0) {
			res.append(alphabet.charAt(r.nextInt(alphabet.length())));
		}
		return res.toString();
	}
}
