package datastructuresandalgos.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of integers, you need to find if you can partition the array into two subsets
 * such that sum is equal.
 */
public class PartitionSum {

    private boolean findTarget(int index, int[] nums, int target, int currentSum,
                               Map<String, Boolean> cache) {
        String key = index + "-" + currentSum;
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        if (index == nums.length) {
            return target == currentSum;
        } else {
            // Include the current num in the subset
            boolean res1 = false;
            if (currentSum + nums[index] <= target) {
                res1 = findTarget(index + 1, nums, target, currentSum + nums[index], cache);
            }
            // Exclude the current num in the subset,
            boolean res2 = findTarget(index + 1, nums, target, currentSum, cache);
            boolean res = res1 || res2;
            cache.put(key, res);
            return res;
        }
    }

    private boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 2 != 0) {
            return false;
        }
        int target = sum / 2;
        Map<String, Boolean> cache = new HashMap<>();
        return findTarget(0, nums, target, 0, cache);
    }

    public boolean canPartitionIterative(int[] nums) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % 2 != 0) return false;

        int n = nums.length;
        int target = sum / 2;
        boolean[][] dp = new boolean[n + 1][target + 1];

        // Initialize dp[0][0] = true (zero elements make sum zero)
        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= target; j++) {
                if (j >= nums[i - 1]) {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i - 1]];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[n][target + 1];
    }


    public static void main(String[] args) {
        PartitionSum partitionSum = new PartitionSum();
        boolean res = partitionSum.canPartition(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        res = partitionSum.canPartition(new int[]{3, 4, 7});
        System.out.println("Res = " + res);
    }
}
