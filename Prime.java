/**
* Die Klasse 
*
* @author Tarek Stelzle and Frederic Arnold
* @version 0.9
* @since  today 
*/
public class Prime
{
	public Prime()
	{

	}

	public static void main(String[] args)
	{
		// Einlesen der ersten Eingabe. Bei unsinnigen oder nicht vorhandenen Eingaben wird n auf 0 gesetzt.
		int n;
		try {
			n = Integer.parseInt(args[0]);
		} catch (Exception e) {
			n = 0;
			// todo: Fehlermeldung
		}
		if(n < 0)
			n = 0;

		// Einlesen, ob -o angegeben wurde
		boolean ausgabe2 = false;
		if(args.length == 2)
		{
			if(args[1].equals("-o"))
			{
				ausgabe2 = true;
			}
		}

		// Wir rufen die Funktion sieb auf und speichern die Rückgabe in ein Array.
		// Wir iterieren durch dieses Array, zählen die Primzahlen und drucken sie (falls -o angegeben wurde)
		// aus.
		int count = 0;
		boolean[] zahlen = sieb(n);
		for(int i = 2; i <= n; i++)
		{
			if(zahlen[i])
			{
				count++;
				if(ausgabe2)
				{
					System.out.println("Die " + count + "te Primzahl ist: " + i);
				}
			}
		}
		System.out.println("Primzahlen: " + count);
	}

	// Wendet das Sieb des Eratosthenes an und gibt ein Array von booleans zurück, in dem die Einträge, die mit Primzahlen korrespondieren, auf true gesetzt sind
	public static boolean[] sieb(int n)
	{
		// Eintrag zur Zahl 2 liegt in zahlen[2], das heißt zahlen[0] und zahlen[1] werden nie benutzt
		boolean[] zahlen = new boolean[n+1];
		for(int i = 2; i <= n; i++)
		{
			zahlen[i] = true;
		}

		for(int j = 2; j <= n; j++)
		{
			if(zahlen[j])
			{
				for(int k = 2; j*k <= n; k++)
				{
					zahlen[j*k] = false;
				}
			}
		}
		return zahlen;
	}
}
