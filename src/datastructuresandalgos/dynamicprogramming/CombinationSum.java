package datastructuresandalgos.dynamicprogramming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CombinationSum {

    public static void main(String[] args) {
        CombinationSum combinationSum = new CombinationSum();
        System.out.println(combinationSum.combinationSumCache(new int[]{2,3,6,7}, 7));
    }

    private List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<Integer> result = new ArrayList<>();
        Map<String, List<List<Integer>>> memo =  new HashMap<>();
        return combinationSum(candidates, target, 0, 0, result, memo);
    }

    private List<List<Integer>> combinationSum(int[] candidates,
                                               int target,
                                               int currentSum,
                                               int index,
                                               List<Integer> result,
                                               Map<String, List<List<Integer>>> memo) {


        String key = target + "_" + index;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        List<List<Integer>> newResult = new ArrayList<>();
        if (currentSum == target) {
            newResult.add(new ArrayList<>(result));
            return newResult;
        }
        if (index == candidates.length) {
            if (currentSum == target) {
                newResult.add(new ArrayList<>(result));
            }
        } else {
            // Include the element at current index
            if (currentSum + candidates[index] <= target) {
                result.add(candidates[index]);
                List<List<Integer>> op1 = combinationSum(candidates, target, currentSum + candidates[index], index, result, memo);
                if (op1.size() > 0) {
                    newResult.addAll(op1);
                }
                result.remove(result.size() - 1);
            }

            // Exclude the element at current index
            List<List<Integer>> op2 = combinationSum(candidates, target, currentSum, index + 1, result, memo);
            if (op2.size() > 0) {
                newResult.addAll(op2);
            }
        }
        memo.put(key, new ArrayList<>(newResult));
        return newResult;
    }

    private List<List<Integer>> combinationSumCache(int[] candidates, int target) {
        Map<String, List<List<Integer>>> memo = new HashMap<>();
        return helper(candidates, target, 0, new ArrayList<>(), memo);
    }

    private List<List<Integer>> helper(int[] candidates,
                                       int remainingTarget,
                                       int index,
                                       List<Integer> current,
                                       Map<String, List<List<Integer>>> memo) {

        String key = remainingTarget + "_" + index;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        List<List<Integer>> results = new ArrayList<>();

        if (remainingTarget == 0) {
            results.add(new ArrayList<>(current));
            return results;
        }

        if (index == candidates.length || remainingTarget < 0) {
            return results;
        }

        // Include current element
        current.add(candidates[index]);
        results.addAll(helper(candidates, remainingTarget - candidates[index], index, current, memo));
        current.remove(current.size() - 1);

        // Exclude current element
        results.addAll(helper(candidates, remainingTarget, index + 1, current, memo));

        memo.put(key, new ArrayList<>(results));
        return results;
    }
}
