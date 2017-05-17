public class Point
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
