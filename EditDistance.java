package Blatt8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class EditDistance {

	public static void main(String[] args)
	{	
		//Strings initialisieren
		String a = null;
		String b = null;
		String dateiPfad = null;
		
		//Prüfung auf nur ein Argument
		if(args.length == 1)
		{
			//Dateipfad einlesen und Datei auf Korrektheit prüfen
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
				//auslesen gibt eine LinkedList mit den einzelnen Strings zurück
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
			//Beide Strings werden versucht einzulesen
			try{
				a = args[0];
				b = args[1];
			}catch(Exception e){
				System.out.println("Strings konnten nicht eingelesen werden.");
				System.exit(0);
			}
			
			//Wenn Strings eingelesen wurden wird die Levenshtein Distanz darauf ausgeführt
			if(a != null && b != null)
			{
				System.out.println("Distanz: " + distance(a,b));
			}
			else{
				System.out.println("Es wurden keine Strings eingelesen.");
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
		//Strings werden uebergeben und als Präfix wird noch das leere Wort angehängt
		String a = " ";
		String b = " ";
		a = a.concat(eins);
		b = b.concat(zwei);
		
		//die Matrix zum Zwischenspeichern der Werte wird erstellt
		int[][] dist = new int[a.length()][b.length()];
		
		//initialisieren aller Werte auf -1, um nachher zu prüfen ob der Algorithmus über alle Felder lief
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
				//Variable zur Aufrechnung auf die Veränderungen
				int konst  = 1;
				
				//Wenn die Chars an der Position gleich sind wird die Variable auf 0 gesetzt, da nicht verändert werden muss
				if(a.charAt(i) == b.charAt(k))
				{
					konst = 0;
				}
				
				//Prüfung auf i=0 und k=0 weil ansonsten die for-Schleifen aus der Matrix rauslaufen können
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
		
		//"rechts unten" der Matrix wird zurückgegeben 
		return dist[dist.length-1][dist[a.length()-1].length-1];
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
		//LinkedList mit den Strings wird zurückgegeben
		return speicher;
	}
}
