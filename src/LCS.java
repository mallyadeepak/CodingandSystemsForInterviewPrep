import java.util.*;

public class LCS {

    private static Map<String, Integer> cache = new HashMap<>();
    private static Map<String, String> cachewithlcsstring = new HashMap<>();

    /**
     * Example 1
     * Input: s1 = "abccba", s2 = "abddba" Output: "abba"
     *
     * Example 2
     * Input: s1 = "zfadeg", s2 = "cdfsdg" Output: "fdg"
     *
     * Example 3
     * Input: s1 = "abd", s2 = "badc" Output: "ad" (or "bd")
     * @param args
     */
    public static void main(String[] args) {

        String s1 = "abccba";
        String s2 = "abddba";
        System.out.println(lcs(s1, s2, s1.length(), s2.length()));
        System.out.println(lcswithstring(s1, s2, s1.length(), s2.length()));

        s1 = "zfadeg";
        s2 = "cdfsdg";

        cache.clear();
        cachewithlcsstring.clear();
        System.out.println(lcs(s1, s2, s1.length(), s2.length()));
        System.out.println(lcswithstring(s1, s2, s1.length(), s2.length()));

        s1 = "abd";
        s2 = "badc";

        cache.clear();
        cachewithlcsstring.clear();

        System.out.println(lcs(s1, s2, s1.length(), s2.length()));
        System.out.println(lcswithstring(s1, s2, s1.length(), s2.length()));

    }

    /**
     * LCS(s1[0..n-1], s2[0..m-1]) =
     *     if s1[n-1] == s2[m-1]:
     *         1 + LCS(s1[0..n-2], s2[0..m-2])
     *     else:
     *         max(
     *             LCS(s1[0..n-2], s2[0..m-1]),
     *             LCS(s1[0..n-1], s2[0..m-2])
     *         )
     * @param A
     * @param B
     * @return
     */
    private static int lcs(String A, String B, int aIndex, int bIndex) {
        String key = aIndex + "_" + bIndex;
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        int lcs = 0;
        if (A.length() == 0 || B.length() == 0) return 0;
        if (aIndex == 0 ||  bIndex == 0) {
            return 0;
        }
        if (A.charAt(aIndex - 1) != B.charAt(bIndex  - 1)) {
            lcs =
                    Math.max(
                            lcs(A, B, aIndex - 1, bIndex),
                            lcs(A, B, aIndex, bIndex - 1)
                    );
        } else {
            // this is the deepest recursion tree, goes to Len(A) + len(B)
            lcs = 1 + lcs(A, B, aIndex - 1, bIndex - 1);
        }
        cache.put(key, lcs);
        return lcs;
    }

    /**
     *
     * @param A
     * @param B
     * @param aIndex
     * @param bIndex
     * @return
     */
    public static String lcswithstring(String A, String B, int aIndex, int bIndex) {
        String key = aIndex + "_" + bIndex;
        if (cachewithlcsstring.containsKey(key)) {
            return cachewithlcsstring.get(key);
        }

        if (aIndex == 0 || bIndex == 0) {
            return "";
        }

        String result;
        if (A.charAt(aIndex - 1) == B.charAt(bIndex - 1)) {
            result = lcswithstring(A, B, aIndex - 1, bIndex - 1) + A.charAt(aIndex - 1);
        } else {
            String option1 = lcswithstring(A, B, aIndex - 1, bIndex);
            String option2 = lcswithstring(A, B, aIndex, bIndex - 1);
            result = (option1.length() > option2.length()) ? option1 : option2;
        }

        cachewithlcsstring.put(key, result);
        return result;
    }

    /**
     * LCS[i][j] = LCS[A(0..i), B(0..j)] with i and j is exclusive.
     * So we create a table of LCS table of size ALen+1, Blen+1 because we want to handle empty strings.
     * And we build the table bottom up, such that if the character match happens, we add it to the LCS string i.e
     * increase the count by 1, else we take the max of two different options of removing 1 character from each string.
     * At the end we have the LCS at LCS[m][n], and we can walk backwards and build the string. While walking backwards,
     * we check if the end char matches, then we add it if not, we check the DP table to see where we came from, and
     * decrement the index appropriately. which basically means we should come from the path which has the longest common subsequence
     * so we can increase the longest common subsequence.
     * @param A
     * @param B
     * @return
     */
    public static String lcsiterative(String A, String B) {
        int n = A.length();
        int m = B.length();

        // Step 1: Build the DP table
        int[][] dp = new int[n + 1][m + 1];

        // Fill the DP table
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (A.charAt(i - 1) == B.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Step 2: Reconstruct the LCS string from the table
        StringBuilder lcs = new StringBuilder();
        int i = n, j = m;
        while (i > 0 && j > 0) {
            if (A.charAt(i - 1) == B.charAt(j - 1)) {
                lcs.append(A.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        // Reverse the string since we added characters from the end
        return lcs.reverse().toString();
    }

}
