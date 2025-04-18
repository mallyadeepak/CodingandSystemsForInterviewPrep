package datastructuresandalgos;

import java.util.PriorityQueue;

class findKthSmallest {

    public int findKthSmallestUsingPartition(int[] nums, int k) {
        return quickSelect(nums, 0, nums.length - 1, k - 1);
    }

    private int quickSelect(int[] nums, int left, int right, int k) {
        if (left == right) return nums[left];

        int pivotIndex = partition(nums, left, right);

        if (k == pivotIndex) {
            return nums[k];  // Found the kth smallest element
        } else if (k < pivotIndex) {
            return quickSelect(nums, left, pivotIndex - 1, k);  // Search left
        } else {
            return quickSelect(nums, pivotIndex + 1, right, k);  // Search right
        }
    }

    private int partition(int[] nums, int left, int right) {
        int pivot = nums[right]; // pick the right most element as the pivot
        int i = left;

        for (int j = left; j < right; j++) {
            if (nums[j] <= pivot) {
                swap(nums, i, j);
                i++;
            }
        }

        swap(nums, i, right);
        return i;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public int findKthSmallestUsingMaxHeap(int[] nums, int k) {
        // Max-heap of size k
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a); // Custom comparator for max-heap

        // Process each element in the array
        for (int num : nums) {
            maxHeap.add(num); // Add the current element to the heap

            // If the heap exceeds size k, remove the largest element
            if (maxHeap.size() > k) {
                maxHeap.poll();  // Remove the root (largest element) of the max-heap
            }
        }

        // The root of the max-heap is the kth smallest element
        return maxHeap.peek();
    }

    public static void main(String[] args) {

        System.out.println(new findKthSmallest().findKthSmallestUsingMaxHeap(new int[]{11, 23, 3, 4, 5, 6, 7, 8, 9}, 5));
        System.out.println(new findKthSmallest().findKthSmallestUsingPartition(new int[]{11, 23, 3, 4, 5, 6, 7, 8, 9}, 5));

    }
}
