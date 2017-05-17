import java.util.*;

public class ConvexHull
{
	public ConvexHull()
	{
		
	}
	
	public static void main(String[] args)
	{
		/*
		LinkedList<Point> liste = new LinkedList<Point>();
		liste.add(new Point(2, 3.0, 4.5));
		System.out.println(liste.size());
		*/
	}

	public LinkedList<Point> simpleConvex(Point[] ps)
	{	
		LinkedList<Point, Point> liste = new LinkedList<Point>();
		Point eins,zwei;
		for(p:ps)
		{
			for(q:ps)
			{
				boolean valid = true;
				for(l:qs)
				{
					if(l != p && l != q)
					{
						
					}
				}
				Point zwei = q;
			}
			Point eins = p;

		}
		liste.add(eins, zwei);
		return null;
	}

	Point[][] choose2(Point[] ps)
	{
		Point[][] arr2;
		for(int i=0; i<ps.length; i++)
		{
			for(int u=1; u<ps.length; u++)
			{
				arr2[i][arr2 		
			}
		}
		return arr2;

	}
}
