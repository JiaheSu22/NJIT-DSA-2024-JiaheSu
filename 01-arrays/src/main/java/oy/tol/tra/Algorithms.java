package oy.tol.tra;

public class Algorithms {
    public static <T> void reverse(T[] array) {
        int i = 0;
        int j = array.length - 1;
        while (i < j) {
            T temp = array[i];
            array[i] = array[j];
            array[j] = temp;
            i++;
            j--;
        }
    }

    public static <T extends Comparable<T>> void sort(T[] array){
        int i = array.length-1;
        for (int j = 0; j <= i - 1; j++) {
            for (int k = j + 1; k <= i; k++) {
                if (array[j].compareTo(array[k]) > 0) {
                    T tmp = array[k];
                    array[k] = array[j];
                    array[j] = tmp;
                }
            }
        }
    }
}
