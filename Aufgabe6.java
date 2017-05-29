//Die Klasse berechnet das Scheduling fuer Prozess die in einer Textdatei gespeichert sind.

//Autoren: Tarek Stelzle und Frederic Arnold

//import fuer das Einlesen und Auslesen der Datei
import java.io.RandomAccessFile;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Collections;


//Die Klasse Intervall erstellt ein Objekt welches die Dauer des Prozesses in einem Array angibt.
class Intervall implements Comparable<Intervall>
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

	//Zweite Ausgabemethode - wird in diesem Programm nicht verwendet
	public String toString2()
	{
		String ausgabe = new String("Intervallanfang: " + getStart() + " Intevallende: " + getEnd());
		return ausgabe;
	}

	public int compareTo(Intervall other)
	{
		if(this.getEnd() < other.getEnd())
		{
			return -1;
		}
		else if(this.getEnd() == other.getEnd())
		{
			return 0;
		}
		else
		{
			return +1;
		}
	}	
}


//Die Klasse implementiert den Algorithmus und organisiert das Einlesen und die Ausgabe
public class Aufgabe6
{
	static String dateiName;
	static int ZeilenCount;

	public Aufgabe6()
	{

	}

	public static void main(String[] args)
	{
		//einlesen des Dateipfades und pruefen auf Korrektheit
		String dateiPfad = "";
		RandomAccessFile file;
		/*
		try{
			RandomAccessFile file = new RandomAccessFile(dateiPfad, "r");
		}catch(Exception e)
		{
			System.out.println("Dateipfad kann nicht erstellt werden.");
			System.exit(0);
		}
		*/

		try{
			dateiPfad = args[0];
			file = new RandomAccessFile(dateiPfad, "r");
			/*
			if(!file.canRead() || !file.isFile())
			{
				System.out.println("Datei kann nicht eingelesen werden.");
				return;
			}
			*/
		}catch(Exception e)
		{
			System.out.println("Dateipfad existiert nicht.");
			return;
		}

		//Pruefen auf richte Argumenteanzahl
		if(args.length > 1)
		{
			System.out.println("Zu viele Argumente eingegeben.");
			return;
		}
		
		//Fals try and catch fehlschlaegt wird hier das Programm abgebrochen (unnoetig)
		if(dateiPfad.equals(""))
		{
			return;
		}

		//Speicher des Dateinamens
		int index = dateiPfad.lastIndexOf('/');
		dateiName = dateiPfad.substring(index+1, dateiPfad.length());
		//Datei auslesen
		ArrayList<Intervall> auslesenArr = auslesen(file);
		//Array sortieren
		ArrayList<Intervall> Arr = copy(auslesenArr);
		ArrayList<Intervall> SortArr = sortStart(Arr);	
		//Schedulingalgorithmus anwenden
		ArrayList<Intervall> ScheduleArr = intervallScheduling(SortArr);
		//Berechnung ausgeben
		ausgabe(auslesenArr, SortArr, ScheduleArr);
	}

	public static ArrayList<Intervall> copy(ArrayList<Intervall> arr)
	{
		ArrayList<Intervall> ausgabe = new ArrayList<Intervall>();
		for(int i=0; i<arr.size(); i++)
		{
			ausgabe.add(arr.get(i));
		}

		return ausgabe;
	}

	//Methode zur Ausgabe eines Array
	public static void ArrAusgabe(ArrayList<Intervall> arr)
	{
		System.out.print("[");
		for(int i=0; i<arr.size(); i++)
		{
			if(i != arr.size()-1)
			{	
				System.out.print(arr.get(i).toString());
				System.out.print(", ");
			}
			else{
				System.out.print(arr.get(i).toString());
			}	
		}
		System.out.println("]");
		
	}
	
	//Methode zur Ausgabe, die in der Aufgabenstellung vorgegeben war
	public static void ausgabe(ArrayList<Intervall> auslesenArr, ArrayList<Intervall> SortArr, ArrayList<Intervall> ScheduleArr)
	{
		System.out.println("");
		System.out.println("Bearbeite Datei \"" + dateiName + "\".");
		System.out.println("");
		System.out.println("Es wurden " + ZeilenCount + " Zeilen mit folgendem Inhalt gelesen:");
		ArrAusgabe(auslesenArr);
		System.out.println("");
		System.out.println("Sortiert:");
		ArrAusgabe(SortArr);
		System.out.println("");
		System.out.println("Berechnetes Intervallscheduling:");
		ArrAusgabe(ScheduleArr);
	}	

	//Algorithmus zum Intervallscheduling, der in der Vorlesung vorgegeben wurde
	public static ArrayList<Intervall> intervallScheduling(ArrayList<Intervall> intervalls)
	{
		//erstellen einer Liste, zur Speicherung der gewaehlten Prozesse (einfacher als Array)
		ArrayList<Intervall> ausgabeList = new ArrayList<Intervall>();
		//Abbruch fuer die Vorschleife
		int n = intervalls.size();
		//erstes Element wird in die Liste eingefuegt, da das Array schon sortiert ist
		ausgabeList.add(intervalls.get(0));
		//Variable j wird initialisiert
		int j=0;

		//Die for-Schleife durchlauft das Array
		for(int i=1; i< n; i++)
		{
			//Falls der Anfangswert des Vorgaengers groesser gleich dem Endwert des Nachfolgers ist, 
			//wird der Prozess mit dem Anfangswert in die Liste aufgenommen
			if(intervalls.get(i).getStart() >= intervalls.get(j).getEnd())
			{
				ausgabeList.add(intervalls.get(i));
				//j wird auf den Vorgaenger gesetzt
				j=i;
			}
		}
		
		//Liste wird in ein Arrayuebertragen, dass dann zurueckgegeben wird

		return ausgabeList;
	}

	public static ArrayList<Intervall> sortStart(ArrayList<Intervall> array)
	{
		Collections.sort(array);
		return array;
	}

	public static ArrayList<Intervall> auslesen(RandomAccessFile file)
	{
		String zeile = "";
		ArrayList<Intervall> speicher = new ArrayList<Intervall>();
		try{
			while(true)
			{
				ZeilenCount++;
				zeile = file.readLine();
				StringTokenizer st = new StringTokenizer(zeile, ",");
					int start = Integer.parseInt(st.nextToken());
					int ende = Integer.parseInt(st.nextToken());
					Intervall ivall = new Intervall(start, ende);
					speicher.add(ivall);
			}
		}catch(Exception e)
		{
			
		}
		finally{
			try{
				file.close();
			}catch(Exception e)
			{
				System.out.println("Reader konnte nicht geschlossen werden");
				System.exit(0);
			}
		}
		
		return speicher;
	}
}
