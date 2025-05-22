package datastructuresandalgos.basicdsa;

import java.util.*;

public class MergeIntervals {

    public static List<int[]> mergeIntervalsWithConstraint(List<int[]> intervals, int k) {
        List<int[]> result = new ArrayList<>();

        if (intervals == null || intervals.size() == 0) return result;

        // Step 1: Sort by start time
        // intervals.sort(Comparator.comparingInt(a -> a[0]));
        intervals.sort(Comparator.comparingInt(a -> a[0]));

        // Step 2: Initialize with first interval
        int[] prev = intervals.get(0);
        result.add(prev);

        for (int i = 1; i < intervals.size(); i++) {
            int[] curr = intervals.get(i);

            int overlap = Math.min(prev[1], curr[1]) - Math.max(prev[0], curr[0]);

            if (overlap >= k) {
                // Merge: update the previous interval
                prev[1] = Math.max(prev[1], curr[1]);
            } else {
                // No merge: add current interval
                result.add(curr);
                prev = curr;
            }
        }

        return result;
    }

    public static void printIntervals(List<int[]> intervals) {
        for (int[] interval : intervals) {
            System.out.print("[" + interval[0] + "," + interval[1] + "] ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        List<TestCase> testCases = List.of(
                new TestCase(new int[][]{{1,3}, {2,6}, {8,10}, {15,18}}, 2),
                new TestCase(new int[][]{{1,3}, {2,6}, {8,10}, {15,18}}, 1),
                new TestCase(new int[][]{{1,10}, {2,3}, {4,5}, {6,7}}, 0),
                new TestCase(new int[][]{{1,2}, {4,5}, {7,8}}, 1),
                new TestCase(new int[][]{{1,2}, {2,4}, {4,6}}, 1),
                new TestCase(new int[][]{{1,5}, {3,7}}, 2),
                new TestCase(new int[][]{{1,5}, {4,8}, {7,10}}, 4),
                new TestCase(new int[][]{}, 1),
                new TestCase(new int[][]{{5,10}}, 1)
        );

        int caseNum = 1;
        for (TestCase tc : testCases) {
            System.out.println("Test case " + (caseNum++) + ", k = " + tc.k);
            List<int[]> input = new ArrayList<>();
            for (int[] interval : tc.intervals) input.add(interval.clone());

            List<int[]> output = mergeIntervalsWithConstraint(input, tc.k);
            printIntervals(output);
            System.out.println("------------");
        }
    }

    static class TestCase {
        int[][] intervals;
        int k;

        public TestCase(int[][] intervals, int k) {
            this.intervals = intervals;
            this.k = k;
        }
    }
}
