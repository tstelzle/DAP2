//Stellt die main Methode bereit die aufgerufen wird wenn das Programm ausgeführt wird
public class GGT
{
	public GGT()
	{

	}

	public static void main(String[] args)
	{
		//Nimmt zwei Zahlen von der Kommandozeile und gibt deren GGT über die Kommandozeile zurück
		if (args.length < 2) {
			System.out.println("Zu wenige Argumente!");
		} else {
			if (args.length > 2) {
				System.out.println("Achtung, überzählige Argumente werden ignoriert!");
			}
			try {
				int a = Integer.parseInt(args[0]);
				int b = Integer.parseInt(args[1]);
				if(a > 0 && b > 0)
					System.out.println(euclid(a, b));	
				else
					System.out.println("Negative Zahlen eingegeben!");
			} catch(Exception e) {
				System.out.println("Keine 2 Zahlen eingegeben!");
			}
		}
	}	

	//Berechnet den GGT zweier Zahlen mit dem Euclid Algorithmus
	public static int euclid(int a, int b)
	{
		if(b == 0)
		{
			return a;
		}
		else{
			int x = a % b;	
			return euclid(b, x);
		}
	}
}
