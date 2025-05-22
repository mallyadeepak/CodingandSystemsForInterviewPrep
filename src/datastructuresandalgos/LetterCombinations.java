package datastructuresandalgos;

import java.util.*;

public class LetterCombinations {

    private static final Map<Character, String> KEYBOARD = new HashMap<>();

    static {
        KEYBOARD.put('2', "abc");
        KEYBOARD.put('3', "def");
        KEYBOARD.put('4', "ghi");
        KEYBOARD.put('5', "jkl");
        KEYBOARD.put('6', "mno");
        KEYBOARD.put('7', "pqrs");
        KEYBOARD.put('8', "tuv");
        KEYBOARD.put('9', "wxyz");
    }

    /**
     *
     * Generate all the letter combinations for a string digits - this can be done iteratively using a stack or
     * recursively. This function does it iteratively by starting with an initial state, and expanding the next states
     * based on the initial states until we reach a terminal state, when we print the combination.
     *
     * @param digits
     * @return
     */
    public static List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.isEmpty()) return result;

        Stack<Pair> stack = new Stack<>();
        stack.push(new Pair("", 0));

        while (!stack.isEmpty()) {
            Pair current = stack.pop();

            if (current.index == digits.length()) {
                result.add(current.path);
                continue;
            }

            char digit = digits.charAt(current.index);
            String letters = KEYBOARD.get(digit);

            if (letters != null) {
                for (char letter : letters.toCharArray()) {
                    stack.push(new Pair(current.path + letter, current.index + 1));
                }
            }
        }

        return result;
    }

    /**
     *
     *  Time complexity of this solution is determined as follows:
     *
     *  The state space tree is there are at most k letters for each digit, so the number of possibilities
     *  are O(4^n) worst case.
     *
     *  Space complexity is for the output combinations and the depth of the stack. Depth of the stack is O(N) because
     *  of n digits exluding the outputList which is going to be total number of letter combinations * N
     *
     *
     * @param digits
     * @return
     */
    public static List<String> letterCombinationsRecursive(String digits) {
        return letterCombinationsRecursive(digits, 0, "");
    }

    private static List<String> letterCombinationsRecursive(String digits, int index, String currentPath) {
        if (digits == null || digits.length() == 0) {
            return Arrays.asList();
        }
        if (index == digits.length()) {
            return Arrays.asList(currentPath);
        } else {

            List<String> outputList = new ArrayList<>();
            String letters = KEYBOARD.get(digits.charAt(index));
            if (letters != null) {
                for (char ch : letters.toCharArray()) {
                    outputList.addAll(letterCombinationsRecursive(digits, index + 1, currentPath + ch));
                }
            }

            return outputList;
        }
    }

    private static class Pair {
        String path;
        int index;

        Pair(String path, int index) {
            this.path = path;
            this.index = index;
        }
    }

    public static void main(String[] args) {
        String digits = "562";
        List<String> combinations = letterCombinations(digits);
        System.out.println(String.join(" ", combinations));


        // Test case 1: Normal input
        System.out.println("Test 1: " + letterCombinations("23"));
        // Expected: [ad, ae, af, bd, be, bf, cd, ce, cf]

        // Test case 2: Empty input
        System.out.println("Test 2: " + letterCombinations(""));
        // Expected: []

        // Test case 3: Single digit
        System.out.println("Test 3: " + letterCombinations("7"));
        // Expected: [p, q, r, s]

        // Test case 4: Contains invalid digit (should skip or error based on design)
        System.out.println("Test 4: " + letterCombinations("1"));
        // Expected: [] or error depending on how you handle invalid input

        // Test case 5: Longer input
        System.out.println("Test 5: " + letterCombinations("234"));
        // Expected: 27 combinations (3×3×3)

        combinations = letterCombinations(digits);
        System.out.println(String.join(" ", combinations));


        // Test case 1: Normal input
        System.out.println("Test 1: " + letterCombinationsRecursive("23"));
        // Expected: [ad, ae, af, bd, be, bf, cd, ce, cf]

        // Test case 2: Empty input
        System.out.println("Test 2: " + letterCombinationsRecursive(""));
        // Expected: []

        // Test case 3: Single digit
        System.out.println("Test 3: " + letterCombinationsRecursive("7"));
        // Expected: [p, q, r, s]

        // Test case 4: Contains invalid digit (should skip or error based on design)
        System.out.println("Test 4: " + letterCombinationsRecursive("1"));
        // Expected: [] or error depending on how you handle invalid input

        // Test case 5: Longer input
        System.out.println("Test 5: " + letterCombinationsRecursive("234"));
        // Expected: 27 combinations (3×3×3)
    }

    static void assertEquals(List<String> actual, List<String> expected) {
        Collections.sort(actual);
        Collections.sort(expected);
        if (!actual.equals(expected)) {
            System.out.println("FAILED: Expected " + expected + ", but got " + actual);
        } else {
            System.out.println("PASSED");
        }
    }
}
