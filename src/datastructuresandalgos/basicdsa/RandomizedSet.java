package datastructuresandalgos.basicdsa;

import java.util.*;

public class RandomizedSet {
    private Map<Integer, Integer> valToIndex;
    private List<Integer> values;
    private Random rand;

    public RandomizedSet() {
        valToIndex = new HashMap<>();
        values = new ArrayList<>();
        rand = new Random();
    }

    /** O(1) time **/
    public boolean insert(int val) {
        if (valToIndex.containsKey(val)) {
            return false;
        }
        valToIndex.put(val, values.size());
        values.add(val);
        return true;
    }

    /** O(1) time **/
    // Check if the Map contains the value, if no return false
    // Get the index of the val to remove
    // Get the value at the lastIndex
    // Update the value at index in the values list to the lastVal
    // Update the index in the map for lastVal to point to index.
    // Remove the last element: 1/ Remove last elemnnt for values list 3/ Remove val from map
    public boolean remove(int val) {
        if (!valToIndex.containsKey(val)) {
            return false;
        }
        int index = valToIndex.get(val);
        int lastVal = values.get(values.size() - 1);

        // Move the last element to the place of the element to delete
        values.set(index, lastVal);
        valToIndex.put(lastVal, index);

        // Remove last element
        values.remove(values.size() - 1);
        valToIndex.remove(val);
        return true;
    }

    /** O(1) time **/
    public int getRandom() {
        int randomIndex = rand.nextInt(values.size());
        return values.get(randomIndex);
    }

    public static void main(String[] args) {
        RandomizedSet rs = new RandomizedSet();
        System.out.println("RandomizedSet");
        System.out.println(rs.insert(1)); // true
        System.out.println(rs.insert(2)); // true
        System.out.println(rs.remove(1)); // true
        System.out.println(rs.getRandom()); // 2 (only element)
    }
}