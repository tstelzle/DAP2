import java.util.Random;


public class Sortierung
{
    public Sortierung()
    {
    }

    public static void main(String []args)
    {
        Random numberGen = new Random();
        if (args.length == 0) {
            System.out.println("Fehler: n angeben!");
            return;
        } else if(args.length > 3) {
            System.out.println("Fehler: Zu viele Argumente!");
            return;
        }

        int n = 0;
        try {
            n = Integer.parseInt(args[0]);
        } catch(Exception e) {
            System.out.println("Fehler: Erstes Argument keine Zahl!");
            return;
        }
        if(n<0) {
            System.out.println("Fehler: n < 0");
            return;
        }
        int []arr = new int[n];

        boolean usingMergeSort = true;
        if(args.length >= 2) {
            if(args[1].equals("insert")) {
                usingMergeSort = false;
            } else if(!args[1].equals("merge")) {
                System.out.println("Fehler: Zweites Argument keine Sortierung!");
                return;
            }
        }
        if(args.length == 3) {
            if(args[2].equals("auf")) {
                for(int i=0; i<arr.length; i++)
                    arr[i] = i;
            } else if(args[2].equals("ab")) {
                for(int i=0; i<arr.length; i++)
                    arr[i] = n-i;
            } else if(args[2].equals("rand")) {
                for(int i=0; i<arr.length; i++)
                    arr[i] = numberGen.nextInt();
            } else {
                System.out.println("Fehler: Drittes Argument wurde nicht erkannt!");
                return;
            }
        } else {
            for(int i=0; i<arr.length; i++)
                arr[i] = numberGen.nextInt();
        }

        long tStart, tEnd;
        tStart = System.currentTimeMillis();
        if(usingMergeSort) {
            mergeSort(arr);
        } else {
            insertionSort(arr);
        }
        tEnd = System.currentTimeMillis();
        if(isSorted(arr)) {
            System.out.println("Feld sortiert.");
        } else {
            System.out.println("Feld NICHT sortiert!");
        }
        System.out.println("Zeit: " + (tEnd-tStart) + " Millisekunden");

        if(arr.length <= 100) {
            for(int i=0; i < arr.length; i++)
                System.out.println(arr[i]);
        }
    }

    public static void insertionSort(int []array)
    {
        for(int j = 1; j < array.length; j++) {
            int key = array[j];
            int i = j-1;
            while((i >= 0) && array[i] > key) {
                array[i+1] = array[i];
                i--;
            }
            array[i+1] = key;
        }
        assert isSorted(array) : "insertionSort hat die Liste nicht sortiert :/";
    }

    public static void mergeSort(int []array)
    {
        int[] tmpArray = new int[array.length];
        mergeSort(array, tmpArray, 0, array.length-1);
        assert isSorted(array) : "mergeSort hat die Liste nicht sortiert :/";
    }

    public static void mergeSort(int[] array, int[] tmpArray, int left, int right)
    {
        if(left < right) {
            int q = (left+right)/2;
            mergeSort(array, tmpArray, left, q);
            mergeSort(array, tmpArray, q+1, right);
            merge(array, tmpArray, left, right);
        }
    }

    public static void merge(int[] array, int[] tmpArray, int left, int right)
    {
        int mid = (left+right+2)/2;
        int pos1 = left;
        int pos2 = mid;
        for(int i=left; i <= right; i++) {
            if(pos1 >= mid) {
                tmpArray[i] = array[pos2];
                pos2++;
                continue;
            }
            if(pos2 > right) {
                tmpArray[i] = array[pos1];
                pos1++;
                continue;
            }
            if(array[pos1] <= array[pos2]) {
                tmpArray[i] = array[pos1];
                pos1++;
                continue;
            }
            tmpArray[i] = array[pos2];
            pos2++;
        }
        for(int i=left; i <= right; i++) {
            array[i] = tmpArray[i];
        }
    }

    public static boolean isSorted(int []array)
    {
        if(array.length == 0)
            return true;
        int tmp = array[0];
        for(int i = 1; i < array.length; i++) {
            if(tmp > array[i])
                return false;
            tmp = array[i];
        }
        return true;
    }
}
