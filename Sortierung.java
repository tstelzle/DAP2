public class Sortierung
{
    public Sortierung() {}
    public static void main(String []args) {}
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
