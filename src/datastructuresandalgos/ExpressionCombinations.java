package datastructuresandalgos;

/**
 * The problem statement is as follows: Given a string of digits, for example "1234" and a target sum lets say 6,
 * find all the combinations of the digits that can sum to the target sum using '+' and '-' operator.
 * <p>
 * For example:
 * <p>
 * 1 + 2 + 3 = 6 is one combination
 * 2 + 4 is another combination
 * 1 - 3 + 4 = 6 is another combination
 * <p>
 * <p>
 * Some questions to ask:
 * <p>
 * 1/ Can I assume that the input string will contain only digits from 0 to 9? Yes
 * 2/ Do I need to return the output in a specific order or is any order feasible? Any is fine
 * 3/ Are the digits in sorted order or random? No
 * 4/ Do I need to return the combinations or just the total number of combinations? Number is fine!
 * 5/ Does this require including all the digits?
 * <p>
 * Approach:
 * <p>
 * Since I need to find all combinations, I need to generate each possible combination starting from an initial state,
 * and check if it reaches a terminal state. To do so: I can start with the initial state of an empty string and
 * currentSum being 0, and then consider expanding to new states. At each decision point, I can partition:
 * <p>
 * Either by expanding using 1 character, 2 character, n - 1 characters.
 * For each of the choices, I can expandtheSum by using + or -1 (two choices).
 * <p>
 * Terminal state:
 * If I reach the end of the string and the currentSum, then I have found a combination that sums to target.
 * and I add it or return 1 if i just need to count it. In this case, return 1 as we are just counting.
 * <p>
 * Thats my approach - can I go ahead with the coding?
 */
public class ExpressionCombinations {

    public static int totalExpressionCombinations(String digits, int target) {
        return dfs(digits, 0, 0, target, "");
    }

    /**
     * Time complexity is equal to the number of partitions which is equal to 2^N-1 with N being the size of the string.
     *
     * You can either cut or not cut (expand current number). So 2 possibilities for N-1 positions in the string.
     * For each of them, you have 2 choices i.e 2^k-1, where k is the number of partitions. In the worst case K == N,
     * because you can have a cut at every position. So time complexity would be O(4^N-1)
     *
     * Space would be for the result same as O(4^N-1 * length of the string i.e N), but for the call stack it would be the recursion depth
     * which is going to be O(N) which is length of the digits at most including temporary space for currentExpression.
     *
     * @param digits
     * @param index
     * @param currentSum
     * @param target
     * @param currentExpression
     * @return
     */
    private static int dfs(String digits, int index, long currentSum, long target, String currentExpression) {
        if (index == digits.length()) {
            if (currentSum == target) {
                System.out.println(currentExpression);
                return 1;
            } else {
                return 0;
            }
        } else {

            int ways = 0;
            for (int i = index + 1; i <= digits.length(); i++) {

                String substring = digits.substring(index, i);

                // Skip numbers with leading zeros
                if (substring.length() > 1 && substring.charAt(0) == '0') {
                    continue;
                }

                long digit = Long.parseLong(substring);

                if (index == 0) {
                    // First number sets the initial value, no operator
                    ways = ways + dfs(digits, i, digit, target, substring);
                } else {
                    ways = ways +  dfs(digits, i, currentSum + digit, target, currentExpression + "+" + digit);
                    ways = ways + dfs(digits, i, currentSum - digit, target, currentExpression + "-" + digit);
                }
            }

            return ways;
        }
    }

    public static void main(String[] args) {

        System.out.println("TestCase1: " + totalExpressionCombinations("12382", 6));
        System.out.println("TestCase1: " + totalExpressionCombinations("3456237490", 9191));
        System.out.println("TestCase1: " + totalExpressionCombinations("232", 8));


    }
}
