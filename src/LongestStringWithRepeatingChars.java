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
                if (end - map.get(ch) > longest) {
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
}
