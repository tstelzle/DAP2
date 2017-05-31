/*
Die Application.java beinhaltet mehere Klassen, welche insgesmat die Aufgabe 4.1 loesen
Autoren: Tarek Stelzle und Frederic Arnold
*/

//abstrake Klasse fuer verschiedene Objekte mit beliebig vielen Eckpunkten
abstract class Simplex
{
	int dim;
	Point[] points;
	
	//Konstruktur erstellt einen neuen Point
	public Simplex(int d, Point... points)
	{
		this.dim = d;
		this.points = points;
	}

	//Methode zur Ausgabe des i-ten Points
	public Point getPoint(int i)
	{
		if(i >= this.dim || i < 0) {
			throw new IllegalArgumentException();
		}
		return this.points[i];
	}
	//Methode berechnet die Distanz zwischen zwei Punkten
	private double dist(int fst, int snd)
	{
		Point fstPoint = this.points[fst];
		Point sndPoint = this.points[snd];
		double sum = 0;
		for(int i=0; i<fstPoint.dim; i++) {
			double tmp = (fstPoint.get(i)-sndPoint.get(i));
			sum += tmp*tmp;
		}
		return Math.sqrt(sum);
	}

	//Methode addiert die Distanzen von allen Aussenkanten
	public double perimeter()
	{
		if(this.dim <= 1) {
			return 0.0;
		}
		double sum = 0;
		for(int i=0; i<this.dim; i++) {
			sum += dist(i, (i+1)%this.dim);
		}
		if(this.dim == 2) {
			sum -= dist(0, 1);
		}
		return sum;
	}
	
	//abstrakte Methoden Vorgabe: soll in den Unterklassen pruefen, ob das Konstrukt ein Simplex ist
	public abstract boolean validate();
}


//Unterklasse Triangle von Simplex
class Triangle extends Simplex
{
	//erstellt ein Triangle mit dem Konstruktor von Simplex
	public Triangle(int d, Point... points)
	{
		super(d, points);
	}

	//geerbte Klasse validate, kontrolliert ob es ein Triangle ist
	public boolean validate()
	{
		for(int i=0; i<3; i++) {
			if(this.points[i].dim() != 2) {
				return false;
			}
		}
		return this.dim == 3;
	}
}

//Interface zur Vorgabe
interface Distance {
	//Methode gibt die Implementierung von distance vor, welches die Streckenlaenge zwischen 2 Punkten berechnen soll
	public double distance(Point p1, Point p2);
}

//Eucklid Distance implentiert das Interface Distance
class EuclidDistance implements Distance
{
	public EuclidDistance()	{}

	//Interfacemethode: Berechnet mit hilfe von Simplex(dist) die Streckenlanegen zwischen 2 Punkten
	public double distance(Point p1, Point p2)
	{
		Triangle t = new Triangle(2, p1, p2);
		return t.perimeter();
	}
}

//Application konstruiert Beispielfaelle fuer die oben implementieren Klassen
public class Application
{
	public static void main(String [] args)
	{
		//akzeptier nur Argumente mit 6 oder 0 Eingaben
		if(args.length != 0 && args.length != 6) {
			System.out.println("Falsche Argumentenanzahl!");
			return;
		}

		//initialisiert ein Feld fuer 3 Koordinaten
		double [] doubles = {0, 0, 0, 0, 0, 0};
		//Bei Uebergabe von Elementen werden die Koordinaten nach dem Muster x1, y1, x2, y2, x3, y3 eingefuegt
		if(args.length == 6) {
			try {
				for(int i=0; i<6; i++) {
					doubles[i] = Double.parseDouble(args[i]);
				}
			} catch(Exception e) {
				System.out.println("Ein Argument war kein double.");
				return;
			}
		//Bei keiner Uebergabe von Elementen werden zufaellige werden zwischen -1000 und 1000 eingefuegt
		} else {
			java.util.Random numberGenerator = new java.util.Random();
			for(int i=0; i<6; i++) {
				doubles[i] = (numberGenerator.nextDouble()*2000)-1000;
			}
		}

		//Ein Simplex des Typs Triangle wird erstellt
		Triangle tri = new Triangle(3, new Point(2, doubles[0], doubles[1]), new Point(2, doubles[2], doubles[3]), new Point(2, doubles[4], doubles[5]));
		//Der Umfang des Triangle wird ausgegeben
		System.out.println("Umfang: " + tri.perimeter());
	}
}
