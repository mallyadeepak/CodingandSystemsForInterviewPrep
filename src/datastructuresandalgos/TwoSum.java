package datastructuresandalgos;

import java.util.*;

// A= [1, 3, 1, 3, 4, 0]
// T= 3
// { 1:0, 2, 3, 1:2, 3: 3, 4: 4}
public class TwoSum {

    public static void main(String[] args) {
        int[] pair = twoSum(new int[]{2, 7, 11, 15}, 9);
        System.out.println(pair[0] + " " + pair[1]);
        pair = twoSum(new int[]{2, 2, 7, 11, 2, 15}, 9);
        System.out.println(pair[0] + " " + pair[1]);

        /**
         * Input: nums = [1, 3, 2, 2, 4, 3], target = 5
         * Output: [[1, 4], [2, 3]]
         */
        System.out.println("twoSumAllPairsWithDups");
        List<int[]> output = twoSumAllPairsWithDups(new int[]{1, 3, 2, 2, 4, 3}, 5);
        for (int[] p: output) {
            System.out.println(p[0] + " " + p[1]);
        }
        System.out.println("twoSumAllPairsIndexes");
        output = twoSumAllPairsIndexes(new int[]{2, 2, 3}, 5);
        for (int[] p: output) {
            System.out.println(p[0] + " " + p[1]);
        }

    }

    // O(N) time
    // O(N) space
    private static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return null;
    }

    // O(N) time
    // O(N) space
    private static List<int[]> twoSumAllPairsWithDups(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        Set<List<Integer>> seenPair = new HashSet<>();
        List<int[]> result = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            // If complement is already in map, form all valid pairs
            if (map.containsKey(complement)) {
                int a = nums[i];
                int b = complement;
                List<Integer> pair = Arrays.asList(Math.min(a, b), Math.max(a, b));
                if (!seenPair.contains(pair)) {
                    result.add(new int[]{pair.get(0), pair.get(1)});
                    seenPair.add(pair);
                }
            }
            // Add current number to the map for future complements
            map.put(nums[i], i);
        }

        return result;
    }

    private static List<int[]> twoSumAllPairsIndexes(int[] nums, int target) {
        Map<Integer, List<Integer>> valuetoIndexList = new HashMap<>();
        List<int[]> result = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            if (valuetoIndexList.containsKey(complement)) {
                for (int index : valuetoIndexList.get(complement)) {
                    result.add(new int[]{index, i}); // index < i guaranteed
                }
            }

            valuetoIndexList.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
        }

        return result;
    }
}
