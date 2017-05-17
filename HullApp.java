/*
Die Klasse löst die Aufgabe 4.2

Autoren: Frederic Arnold und Tarek Stelzle
*/

import java.util.*;

// stellt die Main-Methode bereit und testet die Klasse ConvexHull
public class HullApp
{
	public HullApp()
	{
	}

	public static void main(String[] args)
	{
		if(args.length > 0) {
			System.out.println("Kommandozeilenargumente werden ignoriert!");
		}
		ConvexHull ch = new ConvexHull();
		LinkedList<Point> hull = ch.simpleConvex(ch.pointsInTriangle());
		System.out.println("Punkte der konvexen Hülle: " + (hull.size()/2));
	}
}

// stellt die Algorithmen für Aufgabe 4.2 bereit 
class ConvexHull
{
	public ConvexHull()
	{
		
	}
		
	public LinkedList<Point> simpleConvex(Point[] ps)
	{	

		LinkedList<Point> liste = new LinkedList<Point>();
		Point[][] arr2 = perm2(ps);
		for(int i=0; i<arr2.length; i++)
		{
			boolean valid = true;
				for(int w=0; w<ps.length; w++)
				{
					if(!isLeftOf(ps[w], arr2[i][0], arr2[i][1])) {
						valid = false;
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

	// gibt ein Array von 1000 Punkten zurück,
	// die in dem Dreieck [(10,10), (10,100), (100,10)] liegen
	public Point[] pointsInTriangle()
	{
		java.util.Random numberGen = new java.util.Random();
		Point[] arr = new Point[1000];
		double sum;
		double frac;
		for(int i=0; i<1000; i++) {
			sum = numberGen.nextDouble()*90;
			frac = numberGen.nextDouble();
			arr[i] = new Point(2, sum*frac+10, sum*(1-frac)+10);
		}
		return arr;
	}

	// prüft, ob der Punkt lr links von der Geraden (p1 -> p2) liegt
	boolean isLeftOf(Point lr, Point p1, Point p2)
	{
		double lrx = lr.get(0);
		double lry = lr.get(1);
		double p1x = p1.get(0);
		double p1y = p1.get(1);
		double p2x = p2.get(0);
		double p2y = p2.get(1);
		double grad = (p2y-p1y)/(p2x-p1x);
		double c = p1y - grad*p1x;
		return lry > grad*lrx+c;
	}

	// gibt ein Array von allen zwei-elementigen Permutationen von ps zurück,
	// eine Permutation ist ein Array der Größe 2
	Point[][] perm2(Point[] ps)
	{
		Point[] arr = ps;
		Point[][] arr2 = new Point[arr.length*(arr.length-1)][2];
		int x = 0;
		for(int i=0; i<ps.length; i++)
		{
			for(int u=0; u<ps.length; u++)
			{
				if(i != u)
				{
					arr2[x][0] = arr[i];
					arr2[x][1] = arr[u];
					x++;
				}
			}
		}
		return arr2;
	}
}
