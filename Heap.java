public class Heap {
	
	public int[] heap;
	public int heapSize;
	
	public Heap(int n)
	{
		heapSize = n;
		heap = new int[heapSize];
	}

	public static void main(String[] args)
	{
		int n = 0;
		try{
			n = Integer.parseInt(args[0]);
		}
		catch(Exception e)
		{
			System.out.println("Zahl konnte nicht eingelesen werden.");
			System.exit(0);
		}

		if(n == 0)
		{
			System.out.println("Der Baum ist leer.");
			System.exit(0);
		}

		Heap h = new Heap(n);
		h.insert(3);
		h.insert(4);
		h.insert(1);
		h.insert(7);

		//printArr(arr);
		h.printHeap();
	}
	
	public int left(int i)
	{
		return heap[2*i+1];		
	}
	
	public int parent(int i)
	{
		return heap[(i-1)/2];	
	}
	
	public int right(int i)
	{
		return heap[2*i+2];	
	}

	public void heapify(int i)
	{
		if(right(i) > i && left(i) > parent(i))
		{

		}
		else{
				change(i, parent(i));
		}
	}
	
	public void insert(int key)
	{
		int i = 0;
		while(i < heap.length)
		{
			if(heap[i] < key)
			{
				change(i, key);
				i = heap.length;
				//System.out.println("einfuegen");
			}
			//System.out.println("Schleife insert");
			i++;
			heapify(i);
		}
	}
	
	public int extractMax()
	{
		return 0;		
	}
	
	public void printHeap()
	{
		/*
		int p = 1;

		for(int i=0; i < p ; i++ 
		*/

		int i = 0; 
		int p = 1;
		while(i < heap.length)
		{
			while(i < p && i < heap.length)
			{
				System.out.print(heap[i] + " ");
				i++;
			}
			i = p;
			p = 2*p;
			System.out.println();
		}

	}

	public void change(int i, int key)
	{
		int tmp = 0;
		while(i<heap.length)
		{
			tmp = heap[i];
			heap[i] = key;
			key = tmp;
			i++;
		}
	}

	public void printArr(int[] arr)
	{
		System.out.print("[");
		for(int i=0; i<arr.length; i++)
		{
			if(i == arr.length -1)
			{
				System.out.println(arr[i] + "]");
			}
			else{
				System.out.print(arr[i] + ", ");
			}
		}
	}

}
