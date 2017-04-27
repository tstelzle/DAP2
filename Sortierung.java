public class Sortierung
{
    public Sortierung() {}
    public static void main(String []args) {}
    public static void insertionSort(int []array) {}
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
