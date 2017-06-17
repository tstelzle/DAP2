package Blatt10;

import java.util.Random;

public class Subarray {
	
	public Subarray()
	{
		
	}
	
	public static void main(String[] args)
	{
		int n = 0;
		
		try{
			n = Integer.parseInt(args[0]);
		}
		catch(Exception e)
		{
			System.out.println("Parameter konnte nicht eingelesen werden.");
			System.exit(0);
		}
		
		Random rand = new Random();
		
		int[] pos = new int[n];
		int[] neg = new int[n];
		int[] both = new int[n];
			
		for(int i=0; i<pos.length; i++)
		{
			pos[i] = rand.nextInt(100);
			neg[i] = rand.nextInt(99) - 100;
			both[i] = rand.nextInt(200) -100;
		}
		
		System.out.println("Folgende Arrays mit Laenge " + n + " wurden erstellt.");
		
		printArr(pos);
		printArr(neg);
		printArr(both);
	}
	
	public static void printArr(int[] arr)
	{
		System.out.print("[");
		for(int i=0; i<arr.length; i++)
		{
			if(i == arr.length -1)
			{
				System.out.print(arr[i]);
			}
			else{
				System.out.print(arr[i] + ", ");
			}
		}
		System.out.println("]");
	}
	
	public static int[] algorithm(int[] arr)
	{
		if(arr.length == 1)
		{
			if(arr[0] < 0)
			{
				// TODO return [0,1];
			}
		}
		for(int j=0; j<arr.length; j++)
		{
		
		}
	}

}
