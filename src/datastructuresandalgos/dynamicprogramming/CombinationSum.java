package datastructuresandalgos.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

public class CombinationSum {

    public static void main(String[] args) {
        CombinationSum combinationSum = new CombinationSum();
        System.out.println(combinationSum.combinationSum(new int[]{2,3,6,7}, 7));
    }

    private List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<Integer> result = new ArrayList<>();
        return combinationSum(candidates, target, 0, 0, result);
    }

    private List<List<Integer>> combinationSum(int[] candidates,
                                               int target,
                                               int currentSum,
                                               int index,
                                               List<Integer> result) {
        List<List<Integer>> newResult = new ArrayList<>();
        if (currentSum == target) {
            newResult.add(result);
            return newResult;
        }
        if (index == candidates.length) {
            if (currentSum == target) {
                newResult.add(result);
            }
        } else {
            // Include the element at current index
            if (currentSum + candidates[index] <= target) {
                result.add(candidates[index]);
                List<List<Integer>> op1 = combinationSum(candidates, target, currentSum + candidates[index], index, result);
                if (op1.size() > 0) {
                    newResult.addAll(op1);
                }
                result.remove(result.size() - 1);
            }

            // Exclude the element at current index
            List<List<Integer>> op2 = combinationSum(candidates, target, currentSum, index + 1, result);
            if (op2.size() > 0) {
                newResult.addAll(op2);
            }
        }
        return newResult;
    }
}
