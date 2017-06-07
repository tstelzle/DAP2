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
	
	//geerbte Klasse von Comparable
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

//Klasse Job fuer Augabenteil 2
class Job implements Comparable<Job> 
{
	int[] job;

	//Konstruktor erzeugt einen Job als Feld mit erstem Element Dauer und dem zweiten als Deadline des Prozesses
	public Job(int dauer, int deadline)
	{
		job = new int[] {dauer, deadline};
	}
	
	//get-er Methoden
	public int getDauer()
	{
		return job[0];
	}

	public int getDeadline()
	{
		return job[1];
	}

	//Ausgabe der Jobs
	public String toString()
	{
		String ausgabe = new String("[" + this.getDauer() + "," + this.getDeadline() + "]");
		return ausgabe;
	}

	//geerbte Methode von Comparable
	public int compareTo(Job other)
	{
		if(this.getDeadline() < other.getDeadline())
		{
			return -1;
		}
		else if(this.getDeadline() == other.getDeadline())
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}
}

//Die Klasse implementiert den Algorithmus und organisiert das Einlesen und die Ausgabe
public class Anwendung2
{	
	//globale Variablen
	//Speicherung des Dateinamens und der Anzahl der Zeilen fuer die Ausgabe
	static String dateiName;
	static int ZeilenCount;
	//Speicherung der Verspätung 
	static int dueCount;

	public static void main(String[] args)
	{
		//Pruefen auf richte Argumenteanzahl
		if(args.length != 2)
		{
			System.out.println("Falsche Argumentanzahl.");
			System.exit(0);
		}

		//einlesen des Argumente und pruefen auf Korrektheit
		//und initialisieren der Variablen
		String dateiPfad = "";
		RandomAccessFile file = null;
		String algorithmus = "";

		try{
			algorithmus = args[0];
		}catch(Exception e)
		{
			System.out.println("Algorithmus nicht bekannt.");
			System.exit(0);
		}


		try{
			dateiPfad = args[1];
			file = new RandomAccessFile(dateiPfad, "r");
		}catch(Exception e)
		{
			System.out.println("Dateipfad existiert nicht.");
			System.exit(0);
		}
		
		//Fals try and catch fehlschlaegt wird hier das Programm beendet (wird es aber in catch schon)
		if(dateiPfad.equals(""))
		{
			System.exit(0);
		}
		if(file == null)
		{
			System.exit(0);
		}
		if(algorithmus.equals(""))
		{
			System.out.println("Algorithmus konnte nicht eingelesen werden.");
			System.exit(0);
		}
		if(!(algorithmus.equals("Interval") || algorithmus.equals("Lateness")))
		{
			System.out.println("Algorithmus nicht bekannt.");
			System.exit(0);
		}

		//Speicher des Dateinamens
		int index = dateiPfad.lastIndexOf('/');
		dateiName = dateiPfad.substring(index+1, dateiPfad.length());

		//Auswahl des Algorithmus
		if(auswahl(algorithmus))
		{
			//Datei auslesen
			ArrayList<Intervall> auslesenArr = auslesen(file);
			//kopiert die ArrayList auslesenArr (wichtig fuer die Ausgabe)
			ArrayList<Intervall> Arr = copy(auslesenArr);
			//Array sortieren
			ArrayList<Intervall> SortArr = sortStart(Arr);	
			//Schedulingalgorithmus anwenden
			ArrayList<Intervall> ScheduleArr = intervallScheduling(SortArr);
			//Berechnung ausgeben
			ausgabe(auslesenArr, SortArr, ScheduleArr);
		}
		else{
			//Lateness algorithmus (ablauf gleich zu Intervallscheduling)
			ArrayList<Job> auslesenArr = auslesen2(file);
			ArrayList<Job> Arr = copy2(auslesenArr);
			ArrayList<Job> SortArr = sortStart2(Arr);
			ArrayList<Integer> lateness = latenessScheduling(SortArr);
			ausgabe2(auslesenArr, SortArr, lateness);
		}
	}

	//Methode zur Auswahl welcher Algorithmus verwendet wird
	public static boolean auswahl(String algo)
	{
		if(algo.equals("Interval"))
		{
			return true;
		}	
		return false;
	}

	//Auslesen Methode fuer die Klasse Intervall
	public static ArrayList<Intervall> auslesen(RandomAccessFile file)
	{
		//String zeile initialisieren
		String zeile = "";
		//ausgabe ArrayList erstellen
		ArrayList<Intervall> speicher = new ArrayList<Intervall>();
		try{
			//while Schleife - Reader wirft Exception try bricht die while Schleife ab
			while(true)
			{
				//globale Variable zaehlt die Zeilen
				ZeilenCount++;
				//liest die naechste Zeile
				zeile = file.readLine();
				//erstellt neues StringTokenizer Objekt, die Tokens werden mit Komma getrennt
				StringTokenizer st = new StringTokenizer(zeile, ",");
				//erstes Token der Zeile (vor dem Komma)
				int start = Integer.parseInt(st.nextToken());
				//zweites Token der Zeile (nach dem Komma)
				int ende = Integer.parseInt(st.nextToken());
				//erstellt neues Intervallobjekt
				Intervall rueckgabe = new Intervall(start, ende);
				speicher.add(rueckgabe);
				//fuegt das Intervall der Ausgabe ArrayList hinzu
			}
		//faengt die Exception und macht nichts damit (gewollte Exception -> End of file)
		}catch(Exception e)
		{
			
		}
		finally{
			//scliesst den Reader
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

	//Kopiert die ArrayList (wichtig fuer die Ausgabe)
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
		System.out.println("Intervalls wurden " + ZeilenCount + " Zeilen mit folgendem Inhalt gelesen:");
		ArrAusgabe(auslesenArr);
		System.out.println("");
		System.out.println("Sortiert:");
		ArrAusgabe(SortArr);
		System.out.println("");
		System.out.println("Berechnetes Intervallscheduling:");
		ArrAusgabe(ScheduleArr);

	}	
	
	//Sortiert die eingelesen Liste
	public static ArrayList<Intervall> sortStart(ArrayList<Intervall> array)
	{
		Collections.sort(array);
		return array;
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
			assert (i<n) : "Falsch! i ist zu groß! (in intervallScheduling)";
			//Falls der Anfangswert des Vorgaengers groesser gleich dem Endwert des Nachfolgers ist, 
			//wird der Prozess mit dem Anfangswert in die Liste aufgenommen
			if(intervalls.get(i).getStart() >= intervalls.get(j).getEnd())
			{
				ausgabeList.add(intervalls.get(i));
				//j wird auf den Vorgaenger gesetzt
				j=i;
			}
		}	

		return ausgabeList;
	}

	//Auslesen Methode fuer die Klasse Job
	public static ArrayList<Job> auslesen2(RandomAccessFile file)
	{
		//String zeile initialisieren
		String zeile = "";
		//ausgabe ArrayList erstellen
		ArrayList<Job> speicher = new ArrayList<Job>();
		try{
			//while Schleife - Reader wirft Exception try bricht die while Schleife ab
			while(true)
			{
				//globale Variable zaehlt die Zeilen
				ZeilenCount++;
				//liest die naechste Zeile
				zeile = file.readLine();
				//erstellt neues StringTokenizer Objekt, die Tokens werden mit Komma getrennt
				StringTokenizer st = new StringTokenizer(zeile, ",");
				//erstes Token der Zeile (vor dem Komma)
				int dauer = Integer.parseInt(st.nextToken());
				//zweites Token der Zeile (nach dem Komma)
				int deadline = Integer.parseInt(st.nextToken());
				//erstellt neues Intervallobjekt
				Job rueckgabe = new Job(dauer, deadline);
				speicher.add(rueckgabe);
				//fuegt das Intervall der Ausgabe ArrayList hinzu
			}
		//faengt die Exception und macht nichts damit (gewollte Exception -> End of file)
		}catch(Exception e)
		{
			
		}
		finally{
			//scliesst den Reader
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

	//Kopiert die ArrayList (wichtig fuer die Ausgabe)
	public static ArrayList<Job> copy2(ArrayList<Job> arr)
	{
		ArrayList<Job> ausgabe = new ArrayList<Job>();
		for(int i=0; i<arr.size(); i++)
		{
			ausgabe.add(arr.get(i));
		}

		return ausgabe;
	}

	//Methode zur Ausgabe eines Array
	public static void ArrAusgabe2(ArrayList<Job> arr)
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

	//Ausgabe von einem Integer ArrayList
	public static void ArrAusgabe3(ArrayList<Integer> arr)
	{
		System.out.print("[");
		for(int i=0; i<arr.size(); i++)
		{
			if(i != arr.size()-1)
			{	
				System.out.print(arr.get(i));
				System.out.print(", ");
			}
			else{
				System.out.print(arr.get(i));
			}	
		}
		System.out.println("]");
		
	}
	
	//Methode zur Ausgabe, die in der Aufgabenstellung vorgegeben war
	public static void ausgabe2(ArrayList<Job> auslesenArr, ArrayList<Job> SortArr, ArrayList<Integer> ScheduleArr)
	{
		System.out.println("");
		System.out.println("Bearbeite Datei \"" + dateiName + "\".");
		System.out.println("");
		System.out.println("Es wurden " + ZeilenCount + " Zeilen mit folgendem Inhalt gelesen:");
		ArrAusgabe2(auslesenArr);
		System.out.println("");
		System.out.println("Sortiert:");
		ArrAusgabe2(SortArr);
		System.out.println("");
		System.out.println("Berechnetes Latenessscheduling:");
		ArrAusgabe3(ScheduleArr);
		System.out.println("");
		System.out.println("Berechnete maximale Verspätung: " + dueCount);
	}	
	
	//Sortiert die eingelesen Liste
	public static ArrayList<Job> sortStart2(ArrayList<Job> array)
	{
		Collections.sort(array);
		return array;
	}

	//Lateness Algorithmus aus der Vorlesung
	public static ArrayList<Integer> latenessScheduling(ArrayList<Job> jobs)
	{
		ArrayList<Integer> speicher = new ArrayList<Integer>();
		int z=0;
		dueCount = 0;
		for(int i=0; i<jobs.size(); i++)
		{
			speicher.add(z);
			z+=jobs.get(i).getDauer();
			
			int x = z - jobs.get(i).getDeadline();
			if(x > dueCount)
			{
				dueCount = x;
			}
			assert (z >= speicher.get(i)) : "Falsch!";
		}
		

		return speicher;
	}
}
