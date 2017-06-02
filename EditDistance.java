package Blatt8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class EditDistance {

	public static void main(String[] args)
	{	
		String a = null;
		String b = null;
		String dateiPfad = null;
		if(args.length == 1)
		{
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
			if(dateiPfad != null)
			{
				LinkedList<String> text = auslesen(dateiPfad);
				for(int x=0; x<text.size(); x++)
				{
					for(int y=0; y<text.size(); y++)
					{
						System.out.println(text.get(x) + " -> " + text.get(y) + " : " + distance(text.get(x), text.get(y)));
					}
				}
			}
			else{
				System.out.println("Es wurde kein Dateipfad eingelesen.");
				System.exit(0);
			}
		}
		else if(args.length == 2)
		{
			try{
				a = args[0];
				b = args[1];
			}catch(Exception e){
				System.out.println("Strings konnten nicht eingelesen werden.");
				System.exit(0);
			}
			
			if(a != null && b != null)
			{
				// TODO Algorithmus aufuehren
				System.out.println("Distanz " + distance(a,b));
			}
			else{
				System.out.println("Es wurden keine Strings eingelesen.");
				System.exit(0);
			}
		}
		else{
			System.out.println("Falsche Anzahl an Argumenten.");
			System.exit(0);
		}
	}
	
	public static int distance(String eins, String zwei)
	{	
		String a = " ";
		String b = " ";
		a = a.concat(eins);
		b = b.concat(zwei);
		
		int[][] dist = new int[a.length()][b.length()];
		
		
		for(int i = 0; i<dist.length; i++)
		{
			for(int k=0; k<dist[i].length; k++)
			{
				int konst  = 1;
				
				if(a.charAt(i) == b.charAt(k))
				{
					konst = 0;
				}
				
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
				else if(a.charAt(i) == b.charAt(k))
				{
					dist[i][k] = Math.min(Math.min(dist[i][k-1], dist[i][k-1]), dist[i-1][k-1]);
				}
				else{
					dist[i][k] = Math.min(Math.min(dist[i][k-1] +1, dist[i-1][k]+1), dist[i-1][k-1]+1);
				}
			}
		}
		
		
		return dist[dist.length-1][dist[a.length()-1].length-1];
	}
	
	public static LinkedList<String> auslesen(String dateiPfad)
	{
		LinkedList<String> speicher = new LinkedList<String>();
		BufferedReader in = null;
		try{
			in = new BufferedReader(new FileReader(dateiPfad));
			String zeile = null;
			while((zeile = in.readLine()) != null)
			{
				//zeile = in.readLine();
				speicher.add(zeile);
			}
		}catch(IOException e){
			System.out.println("Fehler beim einlesen.");
			System.exit(0);
		}finally{
			if(in != null)
			{
				try{
					in.close();
				}catch(IOException e){
					System.out.println("Reader konnte nicht gescchlossen werden.");
					System.exit(0);
				}
			}
		}
		return speicher;
	}
}
