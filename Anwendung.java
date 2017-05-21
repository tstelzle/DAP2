import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Intervall
{
	int[] bereich;

	public Intervall(int Start, int End)
	{
		bereich = new int[] {Start, End};
	}

	public int getStart()
	{
		return this.bereich[0];
	}

	public int getEnd()
	{
		return this.bereich[1];
	}

	public String toString()
	{
		String ausgabe = new String("[" + this.getStart() + "," + this.getEnd() + "]");
		return ausgabe;
	}

	public String toString2()
	{
		String ausgabe = new String("Intervallanfang: " + getStart() + " Intevallende: " + getEnd());
		return ausgabe;
	}
}


public class Anwendung
{
	static String dateiName;
	static int ZeilenCount;

	public Anwendung()
	{

	}

	public static void main(String[] args)
	{
		String dateiPfad = "";
		try{
			dateiPfad = args[0];
			File file = new File(dateiPfad);
			if(!file.canRead() || !file.isFile())
			{
				System.out.println("Datei kann nicht eingelesen werden.");
				return;
			}
		}catch(Exception e)
		{
			System.out.println("Dateipfad existiert nicht.");
			return;
		}

		if(dateiPfad.equals(""))
		{
			return;
		}

		int index = dateiPfad.lastIndexOf('/');
		dateiName = dateiPfad.substring(index+1, dateiPfad.length());
		Intervall[] auslesenArr = auslesen(dateiPfad);
		
		Intervall[] SortArr = sortEnd(auslesenArr);	
		Intervall[] ScheduleArr = intervallScheduling(SortArr);
		ausgabe(auslesenArr, SortArr, ScheduleArr);
	}

	public static void ArrAusgabe(Intervall[] arr)
	{
		System.out.print("[");
		for(int i=0; i<arr.length; i++)
		{
			if(i != arr.length-1)
			{	
				System.out.print(arr[i].toString());
				System.out.print(", ");
			}
			else{
				System.out.print(arr[i].toString());
			}	
		}
		System.out.println("]");
		
	}

	public static void ausgabe(Intervall[] auslesenArr, Intervall[] SortArr, Intervall[] ScheduleArr)
	{
		System.out.println("Bearbeite Datei \"" + dateiName + "\".");
		System.out.println("");
		System.out.println("Es wurden " + ZeilenCount + " mit folgendem Inhalt gelesen:");
		ArrAusgabe(auslesenArr);
		System.out.println("");
		System.out.println("Sortiert:");
		ArrAusgabe(SortArr);
		System.out.println("");
		System.out.println("Berechnetes Intervallscheduling:");
		ArrAusgabe(ScheduleArr);
	}	

	public static Intervall[] intervallScheduling(Intervall[] intervalls)
	{
		LinkedList<Intervall> ausgabeList = new LinkedList<Intervall>();
		int n = intervalls.length;
		ausgabeList.add(intervalls[0]);
		int j=0;

		for(int i=1; i< n; i++)
		{
			if(intervalls[i].getStart() >= intervalls[j].getEnd())
			{
				ausgabeList.add(intervalls[i]);
				j=i;
			}
		}
		
		int k=0;
		Intervall[] ausgabe = new Intervall[ausgabeList.size()];

		while(k < ausgabeList.size())
		{
			ausgabe[k] = ausgabeList.get(k);
			k++;
		}

		return ausgabe;
	}
	
	public static Intervall[] sortEnd(Intervall[] intervalls)
	{
		int k=intervalls.length;
		while(k>0)
		{
			for(int i=1; i<intervalls.length;i++)
			{
				if(intervalls[i-1].getEnd() > intervalls[i].getEnd())
				{	
					Intervall tmp = intervalls[i-1];
					intervalls[i-1] = intervalls[i];
					intervalls[i] = tmp;
				}
			}
		k = k-1;
		}

		return intervalls;
	}

	public static Intervall[] auslesen(String dateipfad)
	{
		int count = 0;

		LinkedList<Intervall> liste = new LinkedList<Intervall>();
	
		BufferedReader in = null;
		try{
			in = new BufferedReader(new FileReader(dateipfad));
			String zeile = null;
			while((zeile = in.readLine()) != null)
			{
				count++;
				String Zeins = "";
				String Zzwei = "";

				int x = zeile.indexOf(",");
				
				Zeins = zeile.substring(0, x);
				Zzwei = zeile.substring(x+1, zeile.length());

				int Start = Integer.parseInt(Zeins);
				int End = Integer.parseInt(Zzwei);
				Intervall lesen = new Intervall(Start, End);
				liste.add(lesen);
			}	
		}catch(IOException e){
			e.printStackTrace();
		}
		finally
		{
			if(in != null)
			{
				try{
					in.close();
				}
				catch(IOException e){

				}
			}
		}
			
		Intervall[] ausgabe = new Intervall[liste.size()];

		int i=0;
		while(i < liste.size())
		{
			ausgabe[i] = liste.get(i);
			i++;
		}
		
		ZeilenCount = count;
		return ausgabe;
		
	}
}
