package com.magicvector.ai.util;

import java.util.Arrays;

public class ArrayUtil {


    public static <T> T[] insertAtIndex(T[] arr, int index, T element) {
        int length = arr.length;
        if (index > length || index < 0) {
            throw new IndexOutOfBoundsException("插入位置超出数组范围");
        }
        T[] newArr = Arrays.copyOf(arr, length + 1);
        System.arraycopy(newArr, index, newArr, index + 1, length - index);
        newArr[index] = element;
        return newArr;
    }


    /**
     * @param arr
     * @param fromIndex
     * @param toIndex 不包含该index
     * @param <T>
     * @return
     */
    public static <T> T[] subArray(T[] arr, int fromIndex, int toIndex) {
        if (fromIndex < 0 || fromIndex > toIndex || toIndex > arr.length) {
            throw new IllegalArgumentException("非法的截取范围");
        }
        return Arrays.copyOfRange(arr, fromIndex, toIndex);
    }






}
