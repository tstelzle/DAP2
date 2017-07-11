public class Heap2 {
	
	public int[] heap;
	public int heapSize;
	private int size;
	
	public Heap2(int n)
	{
		heapSize = n;
		heap = new int[heapSize];
		size = -1;
	}

	public static void main(String[] args)
	{
		/*
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
		*/

		Heap2 h = new Heap2(100);
		h.insert(3);
		h.insert(4);
		h.insert(1);
		h.insert(7);
		h.insert(5);

		h.printHeap2();
		System.out.println();
		h.printHeap();
		System.out.println();
		System.out.println(h.extractMax());
	}
	
	public int left(int i)
	{
		return 2*i+1;		
	}
	
	public int parent(int i)
	{
		return (i-1)/2;	
	}
	
	public int right(int i)
	{
		return 2*i+2;
	}

	public void heapify(int i)
	{
		int current = 0;
		int leftChild, rightChild, largerChild;
		boolean stop = false;
		while(!stop)
		{
			leftChild = left(current);
			rightChild = right(current);
			if(leftChild <= size)
			{
				if(rightChild <= size)
				{
					if( heap[rightChild] > heap[leftChild])
					{
						largerChild = rightChild;
					}
					else{
						largerChild = leftChild;
					}
				}
				else{
					largerChild = leftChild;
				}
				if(heap[largerChild] < heap[current])
				{
					change(current, largerChild);
					current = largerChild;
				}
				else{
					stop = true;
				}
			}
			else{
				stop = true;
			}
		}
		
	}
	
	public void insert(int key)
	{
		if(size < heapSize)
		{
			size ++;
			heap[size] = key;
			for(int i=0; i<=size; i++)
			{
				heapify(i);
			}
			/*
			int current = size;
			boolean stop = (current == 0);
			while(!stop)
			{
				int parent = parent(current);
				if(parent>=0 && heap[current] < heap[parent])
				{
					change(current, parent);
					current = parent;
				}
				else{
					stop = true;
				}
			}
			*/
		}
	}
	
	public int extractMax()
	{
		return heap[size];		
	}
	
	public void printHeap()
	{
		int i = 0; 
		int p = 1;
		while(i <=size)
		{
			while(i < p && i <=size)
			{
				System.out.print(heap[i] + " ");
				if(i == 0)
				{
					i++;
				}
				i++;
			}
			i = p;
			p = 2*p;
			System.out.println();
		}

	}
	
	public void printHeap2()
	{
		for(int i = 0; i<=size; i++)
		{
			System.out.print(heap[i]+ " ");
		}
		System.out.println();
	}

	public void change(int i, int j)
	{
		int tmp = heap[i];
		int tmp2 = heap[j];
		heap[i] = tmp2;
		heap[j] = tmp;
	}

	/*
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
	*/

}

