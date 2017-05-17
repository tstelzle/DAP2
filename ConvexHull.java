import java.util.*;

public class ConvexHull
{
	public ConvexHull()
	{
		
	}
	
	public static void main(String[] args)
	{
		Point[] ps = {new Point(2,10.0,10.0), new Point(2, 10.0,100.0), new Point(2, 100.0,10.0), new Point(2, 5.0, 5.0)};
		LinkedList<Point> liste = simpleConvex(ps);
		int i = 0;
		while(i < liste.size())
		{
			System.out.println(liste.get(i));
			i++;
		}
	}

	public LinkedList<Point> simpleConvex(Point[] ps)
	{	

		LinkedList<Point> liste = new LinkedList<Point>();
		Point[][] arr2 = choose2(ps);
		for(int i=0; i<arr2.length; i++)
		{
			boolean valid = true;
				for(int w=0; w<ps.length; w++)
				{
					if(ps[w] != arr2[i][0] && ps[w]!= arr2[i][1])
					{
						if(ps[w].get(0) < arr2[i][0].get(0) || ps[w].get(0) > arr2[i][1].get(0) || ps[w].get(1) < arr2[i][0].get(1) || ps[w].get(1) > arr2[i][1].get(1))
						{
							valid = false;
						}
					}
				}
			if(valid)
			{
				liste.add(arr2[i][0]);
				liste.add(arr2[i][1]);
			}
		}
		return liste;
	}

	Point[][] choose2(Point[] ps)
	{
		Point[] arr = ps;
		Point[][] arr2 = new Point[arr.length*arr.length][2];
		int x = 0;
		for(int i=0; i<ps.length; i++)
		{
			int y = 0;
			for(int u=0; u<ps.length; u++)
			{
				if(arr[i] != arr[u])
				{
					arr2[x][y] = arr[i];
					y++;
					arr2[x][y] = arr[u];
					x++;
				}
			}
		}
		return arr2;
	}
}
