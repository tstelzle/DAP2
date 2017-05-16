class Point
{
	int dim;
	double[] ds;

	public Point(int d, double... args)
	{
		if(d != args.length) {
			throw new IllegalArgumentException();
		}
		this.dim = d;
		this.ds = args;
	}

	public double get(int i)
	{
		if(i >= this.ds.length || i < 0) {
			throw new IllegalArgumentException();
		}
		return this.ds[i];
	}

	public int dim()
	{
		return this.dim;
	}
}	


abstract class Simplex
{
	int dim;
	Point[] points;

	public Simplex(int d, Point... points)
	{
		this.dim = d;
		this.points = points;
	}
	public Point getPoint(int i)
	{
		if(i >= this.dim || i < 0) {
			throw new IllegalArgumentException();
		}
		return this.points[i];
	}
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
	public abstract boolean validate();
}


class Triangle extends Simplex
{
	public Triangle(int d, Point... points)
	{
		super(d, points);
	}
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


interface Distance {
	public double distance(Point p1, Point p2);
}


class EuclidDistance implements Distance
{
	public EuclidDistance() {}
	public double distance(Point p1, Point p2)
	{
		Triangle t = new Triangle(2, p1, p2);
		return t.perimeter();
	}
}


public class Application
{
	public static void main(String [] args)
	{
		if(args.length != 0 && args.length != 6) {
			System.out.println("Falsche Argumentenanzahl!");
			return;
		}
		double [] doubles = {0, 0, 0, 0, 0, 0};
		if(args.length == 6) {
			try {
				for(int i=0; i<6; i++) {
					doubles[i] = Double.parseDouble(args[i]);
				}
			} catch(Exception e) {
				System.out.println("Ein Argument war kein double.");
				return;
			}
		} else {
			java.util.Random numberGenerator = new java.util.Random();
			for(int i=0; i<6; i++) {
				doubles[i] = (numberGenerator.nextDouble()*2000)-1000;
			}
		}
		Triangle tri = new Triangle(3, new Point(2, doubles[0], doubles[1]), new Point(2, doubles[2], doubles[3]), new Point(2, doubles[4], doubles[5]));
		System.out.println("Umfang: " + tri.perimeter());
	}
}
