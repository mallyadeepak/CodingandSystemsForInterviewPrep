package datastructuresandalgos.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  Write a program to output all possible sums made with an array elements i.e all possible subset sums.
 */
public class MakeSum {

    public static int maxSum = Integer.MIN_VALUE;

    private static void getSubsetSums(List<Integer> integerList,
                                      int index,
                                      int currentSum,
                                      List<Integer> outputList) {
        if (index == integerList.size()) {
            outputList.add(currentSum);
            if (currentSum > maxSum) {
                maxSum = currentSum;
            }
        } else {
            // Two options:
            // 1. Include element at index in the currSum
            getSubsetSums(integerList, index + 1, currentSum + integerList.get(index), outputList);
            // 2. Exclude element at index in the currSum.
            getSubsetSums(integerList, index + 1, currentSum, outputList);
        }
    }

    private static List<Integer> getSubsetSums(List<Integer> integerList) {
        List<Integer> outputList = new ArrayList<>();
        getSubsetSums(integerList, 0, 0, outputList);
        return outputList;
    }

    public static void main(String[] args) {
        MakeSum makeSum = new MakeSum();

        System.out.println(
                makeSum.getSubsetSums(Arrays.asList(11, 3, 1, 3, -3, 10))
        );
        System.out.println("MaxSum = " + maxSum);
    }
}
