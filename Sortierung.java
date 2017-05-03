import java.util.Random;


public class Sortierung
{
    public Sortierung()
    {
    }

    public static void main(String []args)
    {
        Random numberGen = new Random();
        if(args.length != 2) {
            System.out.println("Bitte zwei Argumente angeben!");
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

        if(args[1].equals("auf")) {
            for(int i=0; i<arr.length; i++)
                arr[i] = i;
        } else if(args[1].equals("ab")) {
            for(int i=0; i<arr.length; i++)
                arr[i] = n-i;
        } else if(args[1].equals("rand")) {
            for(int i=0; i<arr.length; i++)
                arr[i] = numberGen.nextInt();
        } else {
            System.out.println("Fehler: Zweites Argument wurde nicht erkannt!");
            return;
        }

        long tStart, tEnd;
        tStart = System.currentTimeMillis();
        insertionSort(arr);
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
        assert isSorted(array);
    }

    public static void mergeSort(int []array)
    {
        int[] tmpArray = new int[array.length];
        mergeSort(array, tmpArray, 0, array.length-1);
        assert isSorted(array);
    }

    public static void mergeSort(int[] array, int[] tmpArray, int left, int right)
    {
        return;
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
