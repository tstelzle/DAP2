/*
* Autoren: Frederic Arnold und Tarek Stelzle
* Die Klasse implementiert eine Lösung zu dem Probelm von den Türmen von Hanoi, wie es in Aufgabe 10.2 gefordert ist.
*/
public class Hanoi
{
	//Die Main-Methode liest die Anzhal der zu verschiebenden Scheiben ein und ruft den rekursiven Aufruf aus
	public static void main(String[] args)
	{
		//Deklaration und Initialisieren des Parametes für die Anzahl zu verschiebender Scheiben
		int n = 0;

		//Versuch den uebergebenden Parameter einzulesen
		try{
			n = Integer.parseInt(args[0]);
		}
		catch(Exception e)
		{
			System.out.println("Argument konnte nicht eingelesen werden.");
			System.exit(0);
		}

		//0 ist kein sinnvoller Parameter => Beenden des Programms
		if(n == 0)
		{
			System.out.println("0 ist kein sinnvoller Parameter.");
			System.exit(0);
		}
		
		//Aufruf der rekursiven Methode zum Loesen des Problems
		move(n, 'A', 'B', 'C');	
	}

	public static void move(int quantity, char start, char help, char target)
	{
		//Wenn nur eine Scheibe verschoben werden muss, kann dies sofort von Stapel start nach target geschehen
		if(quantity == 1)
		{
			System.out.println("Verschiebe oberste Scheibe von " + start + " nach " + target);
		}
		else{
			//Ansonsten werden erst alle bis auf die letzte auf den Hilfstapel gelegt
			move(quantity -1, start, target, help);
			//Danach wird die unterste auf den Zielstapel gelegt
			move(1, start, help, target);
			//Zum Schluss werden die restlichen von dem Hilfstapel auf den Zielstapel verschoben
			move(quantity -1, help, start, target);
		}
	}


}
