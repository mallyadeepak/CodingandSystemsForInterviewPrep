package datastructuresandalgos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LongestStringWithRepeatingChars {

    public static void main(String[] args) {
        System.out.println(longestStringWithRepeatingChars("abcabcbb"));
        System.out.println(longestStringWithRepeatingChars("bbbbb"));
        System.out.println(longestStringWithRepeatingChars("pwwkew"));
        System.out.println(longestStringWithRepeatingChars("abba"));

    }

    private static int longestStringWithRepeatingChars(String s) {

        int start = 0;
        int end = 0;
        int longest = 0;
        String longestString = "";
        Map<Character, Integer> map = new HashMap<>();
        while (start <= end && end < s.length()) {
            char ch = s.charAt(end);
            if (map.containsKey(ch)) {
                start = map.get(ch) + 1;
                if (end - start + 1 > longest) {
                    longest = end - start + 1;
                    longestString = s.substring(start, end);
                }
            }
            map.put(ch, end);
            end++;
        }
        System.out.println(longestString);
        return longest;
    }

    /**
     * For ASCII characters ony in the string - we can use an optimization to just use
     * a integer array of 128 characters since there are 128 unique ASCII characters. This is more speed
     * and space efficient than a HashMap i.e less memory overhead than a hashMap.
     *
     * @param input
     * @return
     */
    public int lengthOfLongestSubstring(String input) {
        int[] index = new int[128];       // Store -1 means character not seen yet
        Arrays.fill(index, -1);

        int maxLen = 0;
        int start = 0;

        for (int end = 0; end < input.length(); end++) {
            char c = input.charAt(end);

            // If character was seen and is within the current window
            if (index[c] >= start) {
                start = index[c] + 1;
            }

            index[c] = end; // update last seen position of character
            maxLen = Math.max(maxLen, end - start + 1);
        }

        return maxLen;
    }
}
