//Autoren: Tarek Stelzle und Frederic Arnold

//Die Klasse Point erstellt ein Objekt, welches eine Dimension und genuegend Koordinaten fuer die Dimension hat
class Point
{
	//globale Variablen fuer das Objekt Point
	int dim;
	double[] ds;

	//Konstruktur erzeugt das Objekt und setzt die globalen Variablen anhand er uebergebenen Parameter
	public Point(int d, double... args)
	{
		if(d != args.length) {
			throw new IllegalArgumentException();
		}
		this.dim = d;
		this.ds = args;
	}

	//Methode zur Ausgabe zur i-ten Koordinate
	public double get(int i)
	{
		if(i >= this.ds.length || i < 0) {
			throw new IllegalArgumentException();
		}
		return this.ds[i];
	}

	//Methode zur Ausgabe der Dimension des Points
	public int dim()
	{
		return this.dim;
	}
}	
