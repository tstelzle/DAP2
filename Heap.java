public class Heap
{
	public int[] heap;
	public int heapSize;

	public Heap(int n)
	{
		if(n >= 0)
		{
			heap = new int[n+1];
			heapSize = 0;
			heap[0] = 0;
		}
	}
	
	public static void main(String[] args)
	{
		Heap h = new Heap(100);
		h.insert(4);
		h.printHeap();
		h.insert(3);
		h.printHeap();
		h.insert(5);
		//h.heapify(1);
		h.printHeap();
		h.insert(1);
		//h.heapify(1);

		h.heapify(2);
		h.printHeap();
	}

	public int getHeapSize()
	{
		return heapSize;
	}
	
	public int left(int i)
	{
		return 2*i;
	}

	public int right(int i)
	{
		return 2*i+1;
	}

	public int parent(int i)
	{
		return i/2;
	}

	public void insert(int key)
	{
		if(getHeapSize() + 1 < heap.length)
		{
			heapSize += 1;
			heap[heapSize] = key;
			int current = heapSize;
			boolean stop = (current == 1);
			while(!stop)
			{
				int  parent = parent(current);
				if(parent > 0 && heap[current] < heap[parent])
				{
					swap(current, parent);
					current = parent;
				}
				else{
					stop = true;
				}
			}
		}
	}
	
	public int extractMax()
	{
		return heap[getHeapSize()];
	}
	
	public void printHeap()
	{
		for(int i=1; i<=heapSize; i++)
		{
			System.out.print(heap[i] + " ");
		}
		System.out.println();
	}

	public void heapify(int i)
	{
		int current = i;
		int leftChild, rightChild, largerChild;
		boolean stop = false;
		while(!stop)
		{
			leftChild = left(current);
			rightChild = right(current);
			if(leftChild <= heapSize)
			{
				if(rightChild <= heapSize)
				{
					if(heap[rightChild] < heap[leftChild])
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
					swap(current, largerChild);
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

	public void swap(int i, int j)
	{
		int tmp = heap[i];
		heap[i] = heap[j];
		heap[j] = tmp;
	}
}
