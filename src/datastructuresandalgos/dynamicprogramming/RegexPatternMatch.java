package datastructuresandalgos.dynamicprogramming;

public class RegexPatternMatch {

    private boolean match(String pattern, String input) {
        return match(pattern, input, pattern.length() - 1, input.length() - 1);
    }

    private boolean match(String pattern, String input, int patternIndex, int inputIndex) {
        // Base case: if both pattern and input are exhausted
        if (patternIndex < 0 && inputIndex < 0) {
            return true;
        }

        // If pattern is exhausted but input is not
        if (patternIndex < 0) {
            return false;
        }

        // If input is exhausted, check if remaining pattern can match empty input
        if (inputIndex < 0) {
            if (pattern.charAt(patternIndex) == '*') {
                return match(pattern, input, patternIndex - 2, inputIndex);
            }
            return false;
        }

        char p = pattern.charAt(patternIndex);
        char i = input.charAt(inputIndex);

        if (p == '*') {
            char prev = pattern.charAt(patternIndex - 1);
            if (prev == i || prev == '.') {
                // Try to match one or more characters or skip this pattern
                return match(pattern, input, patternIndex, inputIndex - 1)
                        || match(pattern, input, patternIndex - 2, inputIndex);
            } else {
                // Skip the '*' and the previous character because no match of prev character followed by *
                return match(pattern, input, patternIndex - 2, inputIndex);
            }
        } else if (p == '.' || p == i) {
            return match(pattern, input, patternIndex - 1, inputIndex - 1);
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        RegexPatternMatch regexPatternMatch = new RegexPatternMatch();
        System.out.println(regexPatternMatch.match("a*", "aaa"));
        System.out.println(regexPatternMatch.match("a*b", "aab"));
        System.out.println(regexPatternMatch.match("a*b", "cab"));
        System.out.println(regexPatternMatch.match("a*", "c"));

    }
}
