package datastructuresandalgos.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Generate All Valid Parentheses
 * Given an integer n, generate all strings with n matching parentheses. "matching" parentheses mean
 *
 * there is equal number of opening and closing parentheses.
 * each opening parenthesis has matching closing parentheses.
 * For example, () is a valid string but )( is not a valid string because ) has no matching parenthesis before it and ( has no matching parenthesis after it.
 *
 * Input
 * n: number of matching parentheses
 * Output
 * all valid strings with n matching parentheses
 *
 */
public class GenerateParanthesis {

    public static void main(String[] args) {
        GenerateParanthesis generateParanthesis = new GenerateParanthesis();
        List<String> output = generateParanthesis.generateParanthesis(3);
        for (int i = 0; i < output.size(); i++) {
            System.out.println(i + 1 + " : " + output.get(i));
        }
    }


    /**
     * "" -> openP or closedP 2 options
     * So I create the state space tree, by checking if I can generate a matching paranthesis by adding a ( or )
     * I can add a ) only if the number of ( is >= ), otherwise i can only add a (
     * State that I'm going to keep is openSoFar and closedSoFar. If a match happens.
     * @param n
     * @return
     */
    public List<String> generateParanthesis(int n) {
        return generateParanthesisWithState(n * 2, 0, 0, "");
    }

    private List<String> generateParanthesisWithState(int n, int openParSoFar, int closedParSoFar, String generatedParan) {
        List<String> result = new ArrayList<>();
        if (generatedParan.length() == n) {
            result.add(generatedParan);
        } else {
            // I have two choices. I can extend the generatedParan by adding a ( or ), but i need to know
            // that the openSoFar >= closedSoFar to add a ), otherwise i can add only (
            if (openParSoFar < n / 2) {
                List<String> list1 = generateParanthesisWithState(n, openParSoFar + 1, closedParSoFar, generatedParan + "(");
                for (String s : list1) {
                    result.add(s);
                }
            }
            if (openParSoFar > closedParSoFar) {
                List<String> list2 = generateParanthesisWithState(n, openParSoFar, closedParSoFar + 1, generatedParan + ")");
                for (String s : list2) {
                    result.add(s);
                }
            }

        }
        return result;
    }
}
