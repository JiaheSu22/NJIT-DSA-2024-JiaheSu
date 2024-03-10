package oy.tol.tra;

import java.util.Comparator;
import java.util.function.Predicate;

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
// fastsort is the alternative.
//    public static <T extends Comparable<T>> void sort(T[] array) {
//        int i = array.length - 1;
//        for (int j = 0; j <= i - 1; j++) {
//            for (int k = j + 1; k <= i; k++) {
//                if (array[j].compareTo(array[k]) > 0) {
//                    T tmp = array[k];
//                    array[k] = array[j];
//                    array[j] = tmp;
//                }
//            }
//        }
//    }

    //slow linear search
    public static <T> int slowLinearSearch(T aValue, T[] fromArray, int fromIndex, int toIndex) {
        for (int index = fromIndex; index < toIndex; index++) {
            if (fromArray[index].equals(aValue)) {
                return index;
            }
        }
        return -1;
    }

    public static <T extends Comparable<T>> int binarySearch(T aValue, T[] fromArray, int fromIndex, int toIndex) {
        while (fromIndex <= toIndex) {
            int midIndex = fromIndex + (toIndex - fromIndex) / 2;
            int compareResult = fromArray[midIndex].compareTo(aValue);
            if (compareResult == 0) {
                return midIndex;
            } else if (compareResult < 0) {
                fromIndex = midIndex + 1;
            } else {
                toIndex = midIndex - 1;
            }
        }
        return -1;
    }

    public static <E extends Comparable<E>> void fastSort(E[] array) {
        quickSort(array, 0, array.length - 1);
    }

    public static <E extends Comparable<E>> void quickSort(E[] array, int begin, int end) {
        if (begin >= end) {
            return;
        }
        int partitionIndex = partition(array, begin, end);
        quickSort(array, begin, partitionIndex - 1);
        quickSort(array, partitionIndex + 1, end);
    }

    private static <E extends Comparable<E>> int partition(E[] array, int begin, int end) {
//      //first method:
//        E pivot = array[end];
//        int i = begin - 1;
//        for (int j = begin; j < end; j++) {
//            if (array[j].compareTo(pivot) <= 0) {
//                i++;
//                swap(array, i, j);
//            }
//        }
//        swap(array, i + 1, end);
//        return i + 1;
        //second method:
        E pivot = array[end];
        int leftPointer = begin;
        int rightPointer = end;
        while (leftPointer < rightPointer) {
            while (leftPointer < rightPointer && array[leftPointer].compareTo(pivot) < 0) {
                leftPointer++;
            }
            while (leftPointer < rightPointer && array[rightPointer].compareTo(pivot) > 0) {
                rightPointer--;
            }
            swap(array, leftPointer, rightPointer);
        }
        return leftPointer;
    }

    private static <E> void swap(E[] array, int index1, int index2) {
        E temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    public static <T> int partitionByRule(T[] elements, int count, Predicate<T> rule) {
        int left = 0;
        int right = count - 1;

        while (left < right) {
            // Find an element from the right that matches the rule
            while (left < right && rule.test(elements[right])) {
                right--;
            }

            // Swap the right element (matching the rule) with the left element (not matching)
            T temp = elements[left];
            elements[left] = elements[right];
            elements[right] = temp;

            // Find an element from the left that doesn't match the rule
            while (left < right && !rule.test(elements[left])) {
                left++;
            }

            // Swap the left element (not matching) with the right element (matching)
            temp = elements[left];
            elements[left] = elements[right];
            elements[right] = temp;
        }

        // Return the index of the first element that matches the rule
        return left;
    }

    public static <T> void sortWithComparator(T[] array, Comparator<T> comparator) {
        boolean swapped;
        for (int i = 0; i < array.length - 1; i++) {
            swapped = false;
            for (int j = 0; j < array.length - i - 1; j++) {
                if (comparator.compare(array[j], array[j + 1]) > 0) {
                    // Swap array[j] and array[j+1]
                    T temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                }
            }
            // If no two elements were swapped by inner loop, then break
            if (!swapped) break;
        }
    }
}
