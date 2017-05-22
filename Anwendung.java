//Die Klasse berechnet das Scheduling fuer Prozess die in einer Textdatei gespeichert sind.

//Autoren: Tarek Stelzle und Frederic Arnold

//import fuer das Einlesen und Auslesen der Datei
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

//import fuer LinkedList
import java.util.*;


//Die Klasse Intervall erstellt ein Objekt welches die Dauer des Prozesses in einem Array angibt.
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

	//Zweite Ausgabemethode - wird in diesem Programm nicht verwendet
	public String toString2()
	{
		String ausgabe = new String("Intervallanfang: " + getStart() + " Intevallende: " + getEnd());
		return ausgabe;
	}
}


//Die Klasse implementiert den Algorithmus und organisiert das Einlesen und die Ausgabe
public class Anwendung
{
	static String dateiName;
	static int ZeilenCount;

	public Anwendung()
	{

	}

	public static void main(String[] args)
	{
		//einlesen des Dateipfades und pruefen auf Korrektheit
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
		Intervall[] auslesenArr = auslesen(dateiPfad);
		//Array sortieren
		Intervall[] SortArr = sortEnd(auslesenArr);	
		//Schedulingalgorithmus anwenden
		Intervall[] ScheduleArr = intervallScheduling(SortArr);
		//Berechnung ausgeben
		ausgabe(auslesenArr, SortArr, ScheduleArr);
	}

	//Methode zur Ausgabe eines Array
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
	
	//Methode zur Ausgabe, die in der Aufgabenstellung vorgegeben war
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

	//Algorithmus zum Intervallscheduling, der in der Vorlesung vorgegeben wurde
	public static Intervall[] intervallScheduling(Intervall[] intervalls)
	{
		//erstellen einer Liste, zur Speicherung der gewaehlten Prozesse (einfacher als Array)
		LinkedList<Intervall> ausgabeList = new LinkedList<Intervall>();
		//Abbruch fuer die Vorschleife
		int n = intervalls.length;
		//erstes Element wird in die Liste eingefuegt, da das Array schon sortiert ist
		ausgabeList.add(intervalls[0]);
		//Variable j wird initialisiert
		int j=0;

		//Die for-Schleife durchlauft das Array
		for(int i=1; i< n; i++)
		{
			//Falls der Anfangswert des Vorgaengers groesser gleich dem Endwert des Nachfolgers ist, 
			//wird der Prozess mit dem Anfangswert in die Liste aufgenommen
			if(intervalls[i].getStart() >= intervalls[j].getEnd())
			{
				ausgabeList.add(intervalls[i]);
				//j wird auf den Vorgaenger gesetzt
				j=i;
			}
		}
		
		//Liste wird in ein Arrayuebertragen, dass dann zurueckgegeben wird
		int k=0;
		Intervall[] ausgabe = new Intervall[ausgabeList.size()];

		while(k < ausgabeList.size())
		{
			ausgabe[k] = ausgabeList.get(k);
			k++;
		}

		return ausgabe;
	}
	
	//Sortieren des Arrays mit Bubblesort
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

	//Auslesend der Datei
	public static Intervall[] auslesen(String dateipfad)
	{
		//Zeilensaehler (wichtig fuer die Ausgabe)
		int count = 0;

		//erstellen einer Liste fuer die ausgelesenen Zeilen
		LinkedList<Intervall> liste = new LinkedList<Intervall>();
	
		//Ausleser initialiseren
		BufferedReader in = null;
		try{
			//Versuch den Ausleser zu erstellen
			in = new BufferedReader(new FileReader(dateipfad));
			//Variable fuer die naechste Zeile initialisieren
			String zeile = null;
			//Auslesen der Zeilen nacheinander, solange bis einen null ist
			while((zeile = in.readLine()) != null)
			{
				//Zeilenzaehler wird um eins erhoeht
				count++;
				//Initialisieren von 2 Strings
				String Zeins = "";
				String Zzwei = "";

				int x = zeile.indexOf(",");
				
				//Initialisieren von 2 String, fuer den Anfangs- und Endwert des Prozess
				Zeins = zeile.substring(0, x);
				Zzwei = zeile.substring(x+1, zeile.length());
				
				int Start = 0;
				int End = 0;
				//Versuch die Strings in Ints umzuwandeln
				try{
					Start = Integer.parseInt(Zeins);
					End = Integer.parseInt(Zzwei);
				}catch (Exception e)
				{
					System.out.println("Hier sollte eine Zahl stehen.");
					return null;
				}
				
				//neues Intervall mit den 2 Ints erstellen
				Intervall lesen = new Intervall(Start, End);
				//Intervall der Liste hinzufuegen
				liste.add(lesen);
			}	
		}catch(IOException e){
			e.printStackTrace();
		}
		finally
		{
			//insofern der Ausleser noch nicht geschlossen wurde, wird er hier geschlossen
			if(in != null)
			{
				try{
					in.close();
				}
				catch(IOException e){

				}
			}
		}
			

		//Umschreiben der Liste in ein Array
		Intervall[] ausgabe = new Intervall[liste.size()];

		int i=0;
		while(i < liste.size())
		{
			ausgabe[i] = liste.get(i);
			i++;
		}
		
		//globale Variable auf Zeilenzaehler setzen
		ZeilenCount = count;
		//Rueckgabe des Arrays
		return ausgabe;
		
	}
}
