package datastructuresandalgos;

import java.util.HashMap;
import java.util.Map;

// numDecodings(str[0..n]) = num(Decodings(str[1..n]) + (Decodings[str[2..n])])
public class StringDecoder {

    Map<String, Integer> cache = new HashMap<>();

    public int numDecodings(String input) {
        Map<Integer, Character> encodingMap = new HashMap<>();
        char ch = 'A';
        for (int i = 1; i <= 26; i++) {
            encodingMap.put(i, ch);
            ch++;
        }
        return numDecodings(input, "", 0, encodingMap);
    }

    public int numDecodings(String A, String encoding, int level, Map<Integer, Character> encodingMap) {
        if (cache.containsKey(A)) {
            return cache.get(A);
        }
        if (A.length() == 0) {
            return 1;
        } else {
            int total = 0;
            if (A.length() >= 1) {
                int digit = Integer.parseInt(A.substring(0, 1));
                total = total + numDecodings(A.substring(1), encoding + encodingMap.get(digit), level + 1, encodingMap);
            }
            if (A.length() >= 2) {
                String twoCharStr = A.substring(0, 2);
                int num = Integer.parseInt(twoCharStr);
                if (num <= 26) {
                    total = total + numDecodings(A.substring(2), encoding + encodingMap.get(num), level + 1, encodingMap);
                }
            }
            cache.put(A, total);
            return total;
        }

    }

    public static void main(String[] args) {
        System.out.println(new StringDecoder().numDecodings("12"));
    }

}