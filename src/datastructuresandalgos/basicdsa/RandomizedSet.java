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

    public boolean insert(int val) {
        if (valToIndex.containsKey(val)) {
            return false;
        }
        valToIndex.put(val, values.size());
        values.add(val);
        return true;
    }

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