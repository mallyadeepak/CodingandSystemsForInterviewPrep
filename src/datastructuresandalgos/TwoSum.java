package datastructuresandalgos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// A= [1, 3, 1, 3, 4, 0]
// T= 3
// { 1:0, 2, 3, 1:2, 3: 3, 4: 4}
public class TwoSum {

    public static void main(String[] args) {
        int[] pair = twoSum(new int[]{2, 7, 11, 15}, 9);
        System.out.println(pair[0] + " " + pair[1]);
        pair = twoSum(new int[]{2, 2, 7, 11, 2, 15}, 9);
        System.out.println(pair[0] + " " + pair[1]);
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
    private static List<int[]> twoSumAllPairs(int[] nums, int target) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        List<int[]> result = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            // If complement is already in map, form all valid pairs
            if (map.containsKey(complement)) {
                for (int j : map.get(complement)) {
                    result.add(new int[]{j, i});
                }
            }

            // Add current number to the map for future complements
            map.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
        }

        return result;
    }
}
