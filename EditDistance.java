import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class EditDistance {

	static int[][] dist;

	public EditDistance()
	{

	}

	public static void main(String[] args)
	{	
		//Strings initialisieren
		String a = null;
		String b = null;
		String dateiPfad = null;
		String argument = null;

		//Pruefung auf nur ein Argument
		if(args.length == 1)
		{
			//Dateipfad einlesen und Datei auf Korrektheit pruefen
			try{
				dateiPfad = args[0];
				File file = new File(dateiPfad);
				if(!file.canRead() || !file.isFile())
				{
					System.out.println("Datei kann nicht eingelesen werden.");
					System.exit(0);
				}
			}catch(Exception e){
				System.out.println("Dateipfad konnte nicht eingelesen werden.");
				System.exit(0);
			}
			//Wenn eine Datei eingelesen wurde wird hier der Algorithmus auf diese aufgerfufen
			if(dateiPfad != null)
			{
				//auslesen gibt eine List mit den einzelnen Strings zurueck
				LinkedList<String> text = auslesen(dateiPfad);
				for(int x=0; x<text.size(); x++)
				{
					for(int y=0; y<text.size(); y++)
					{
						String format = "%-7s%-4s%-7s%-3s%s\n";
						System.out.printf(format, text.get(x), "->", text.get(y), ":", distance(text.get(x), text.get(y)) );
					}
				}
			}
			else{
				System.out.println("Es wurde kein Dateipfad eingelesen.");
				System.exit(0);
			}
		}
		//Bei zwei Argumenten
		else if(args.length == 2)
		{
			try{
				b = args[1];
			}catch(Exception e)
			{
				System.out.println("Zweites Argument konnte nicht eingelesen werden.");
				System.exit(0);
			}

			if(b.equals("-o"))
			{
				try{
					dateiPfad = args[0];
					File file = new File(dateiPfad);
					if(!file.canRead() || !file.isFile())
					{
						System.out.println("Datei kann nicht eingelesen werden.");
						System.exit(0);
					}	
				}catch(Exception e)
				{
					System.out.println("Dateipfad konnte nicht eingelesen werden.");
					System.exit(0);
				}
				// TODO Ausgabe der Datei mit printEditOperations
			}
			else{
				try{
					a = args[0];
				}catch(Exception e)
				{
					System.out.println("Erstes Argument konnte nicht eingelesen werden.");
					System.exit(0);
				}
				//Wenn Strings eingelesen wurden wird die Levenshtein Distanz darauf ausgefuehrt
				if(a != null && b != null)
				{
					System.out.println("Distanz: " + distance(a,b));
				}
				else{
					System.out.println("Es wurden keine Strings eingelesen.");
					System.exit(0);
				}
			}
		}
		else if(args.length == 3)
		{
			try{
				a = args[0];
				b = args[1];
				argument = args[2];
			}catch(Exception e){
				System.out.println("Strings oder Argumente konnnte nicht eingelesen werden.");
				System.exit(0);
			}

			if(argument.equals("-o"))
			{
				System.out.println(distance(a,b));
				LinkedList<String> liste = printEditOperations(dist);
				printListe(liste);
			}
			else{
				System.out.println("Drittes Argument nicht bekannt.");
				System.exit(0);
			}
		}
		//Bei mehr Argumenten wird Das Programm geschlossen
		else{
			System.out.println("Falsche Anzahl an Argumenten.");
			System.exit(0);
		}
	}
	
	//Methode welche die Levenshtein Distanz berechnet
	public static int distance(String eins, String zwei)
	{	
		//Strings werden uebergeben und als Praefix wird noch das leere Wort angehaengt
		String a = " ";
		String b = " ";
		a = a.concat(eins);
		b = b.concat(zwei);
		
		//die Matrix zum Zwischenspeichern der Werte wird erstellt
		dist = new int[a.length()][b.length()];
		
		//initialisieren aller Werte auf -1, um nachher zu pruefen ob der Algorithmus ueber alle Felder lief
		for(int p = 0; p<dist.length; p++)
		{
			for(int o=0; o<dist[p].length; o++)
			{
				dist[p][o] = -1;
			}
		}
		
		//Die Matrix wird mit zwei For-Schleifen durchlaufen
		for(int i = 0; i<dist.length; i++)
		{
			for(int k=0; k<dist[i].length; k++)
			{
				//Variable zur Aufrechnung auf die Veraenderungen
				int konst  = 1;
				
				//Wenn die Chars an der Position gleich sind wird die Variable auf 0 gesetzt, da nicht veraendert werden muss
				if(a.charAt(i) == b.charAt(k))
				{
					konst = 0;
				}
				
				//Pruefung auf i=0 und k=0 weil ansonsten die for-Schleifen aus der Matrix rauslaufen koennen
				if(i == 0)
				{
					if(k == 0)
					{
						dist[i][k] = 0;
					}
					else{
						dist[i][k] = dist[i][k-1] + konst;
					}
				}
				else if(k == 0)
				{
					dist[i][k] = dist[i-1][k]+konst;
				}
				//Wenn die Buchstaben gleich sind wird hier nochmal bei i!=0 und k!=0 der neue Wert gespeichert
				else if(a.charAt(i) == b.charAt(k))
				{
					dist[i][k] = Math.min(Math.min(dist[i][k-1], dist[i][k-1]), dist[i-1][k-1]);
				}
				//Wert wird bei Ungleichheit der Werte und bei i!=0 und k!=0 gespeichert
				else{
					dist[i][k] = Math.min(Math.min(dist[i][k-1] +1, dist[i-1][k]+1), dist[i-1][k-1]+1);
				}
			}
			assert checker(dist[i]) : "Ein Array Platz wurde nicht gefuellt!";
		}
		
		//"rechts unten" der Matrix wird zurueckgegeben 
		return dist[dist.length-1][dist[a.length()-1].length-1];
	}

	public static void printListe(LinkedList<String> liste)
	{
		for(int i = 0; i<liste.size(); i++)
		{
			System.out.println(liste.get(i));
		}
	}
	
	// TODO
	public static LinkedList<String> printEditOperations(int[][] arr)
	{
		LinkedList<String> liste = new LinkedList<String>();
		int counter = 0;
		int i = arr.length-1;
		int j = arr[0].length-1;
		while(i > 0 && j > 0)
		{
			counter += 1;
			if(arr[i][j] == arr[i][j-1])
			{
				j--;
				liste.add(counter + ") eins");
			}
			else if(arr[i][j] == arr[i-1][j])
			{
				i--;
				liste.add(counter + ") zwei");
			}
			else{
				liste.add(counter + ") gleich");
				i--;
				j--;
			}
		}
		return liste;
	}
	
	//Methode fuer die Assertion (sind die Felder mit den Werten des Algorithmus befuellt worden)
	private static boolean checker(int[] arr)
	{
		for(int p : arr)
		{
			if(p == -1)
			{
				return false;
			}
		}
		return true;
	}
	
	//Methode zum auslesen einer datei
	public static LinkedList<String> auslesen(String dateiPfad)
	{
		//LinkedList zum Speichern der Inhalte der Liste wird erstellt
		LinkedList<String> speicher = new LinkedList<String>();
		//Reader wird initialisiert
		BufferedReader in = null;
		//Reader versucht alle Zeile einzulesen, bis eine null ist
		try{
			in = new BufferedReader(new FileReader(dateiPfad));
			String zeile = null;
			while((zeile = in.readLine()) != null)
			{
				//fuegt die Zeilen zum Speicher hinzu
				speicher.add(zeile);
			}
		}catch(IOException e){
			System.out.println("Fehler beim einlesen.");
			System.exit(0);
		}finally{
			//Reader wird noch versucht zu schliessen
			if(in != null)
			{
				try{
					in.close();
				}catch(IOException e){
					System.out.println("Reader konnte nicht geschlossen werden.");
					System.exit(0);
				}
			}
		}
		//LinkedList mit den Strings wird zurueckgegeben
		return speicher;
	}
}
