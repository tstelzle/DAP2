//Die Klasse implementiert einen Automaten, der die Rueckgabe von Geldwerten berechnet
//Autoren: Tarek Stelzle und Frederic Arnold

public class Coin
{
	public static void main(String[] args)
	{
		//Pruefen auf richtige Argumentanzahl
		if(args.length != 2)
		{
			System.out.println("Falsche Anzahl an Argumenten.");
			return;
		}

		//Variable initialisieren und Argument den Variable zuweisen
		String currency = "";
		int betrag = 0;

		try{
			currency = args[0];
			betrag = Integer.parseInt(args[1]);

		}catch(Exception E)
		{
			System.out.println("Die Argumente waren nicht vom richtigen Typ.");
			return;
		}
		
		//Pruefen auf Korrektheit der Argumente
		if(!currency.equals("Euro") && !currency.equals("Alternative"))
		{
			System.out.println(currency);
			System.out.println("Keine vorhandene Waehrung.");
			return;
		}

		if(betrag < 0)
		{
			System.out.println("Der Betrag darf nicht kleiner null sein.");
			return;
		}
		
		//Richtiges Waehrung auswahlen
		int[] w = switchCurrency(currency);
		//Ausgabe in ein Array speichern
		int[] ausgabe = change(betrag, w);
		
		//Ausgabe der Daten
		printer(ausgabe, w);
	}

	//Automatenalgorithmus
	public static int[] change(int b, int[] w)
	{
		//Variable initialisieren und mit Nullen befuellen
		int i = 0;
		int[] ausgabe = new int[w.length];

		for(int k=0; k<ausgabe.length; k++)
		{
			ausgabe[k] = 0;
		}
		
		//Pruefung ob der Geldbetrag noch groesser Null ist
		while(b > 0)
		{
			//sum beschreibt die Anzahl der Muenzen oder Scheine die gebraucht werden
			int sum = 0;
			assert (sum==0) : "Summe wurde nicht zurueckgesetzt.";

			//solange der Geldbetrag groesser dem Schein- oder Muenzbetrag ist 
			//wird die Summe um eins erhoeht
			//und der Betrag um den jeweiligen Wert verringert
			while(b >= w[i])
			{
				assert (b>= w[i]) : "Sollte nicht in der While-Schleife sein.";
				sum = sum + 1;
				b = b - w[i];
				assert b>=0 : "Zu viel abgezogen.";
			}
			//Speichern der Summe in dem Array
			ausgabe[i] = sum;
			i++;
		}	
		return ausgabe;
	}
	
	//Methode zur Auswahl der Waehrung
	private static int[] switchCurrency(String eingabe)
	{
		int[] w;

		if(eingabe.equals("Euro"))
		{
			w = new int[] {200, 100, 50, 20, 10, 5, 2, 1};
		}
		else{
			w = new int[] {200, 100, 50, 20, 10, 5, 4, 2, 1};
		}
		
		return w;
	}

	//Methode zum Ausgeben(printen) der Werte
	public static void printer(int[] ausgabe, int[] w)
	{
		System.out.print("Ausgegeben wird: ");
		for(int i = 0; i < ausgabe.length; i++)
		{
			if(i + 1 == ausgabe.length)
			{
				System.out.print( ausgabe[i] + "x" + w[i]);
			}
			else{
				System.out.print( ausgabe[i] + "x" + w[i] + ", ");
			}
		}
		System.out.print("\n");
	}
}
