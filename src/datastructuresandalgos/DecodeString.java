package datastructuresandalgos;

class DecodeString {

    public static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    public static String decode(String s) {
        StringBuilder solutionStr = new StringBuilder();
        StringBuilder count = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (isDigit(c)) {
                count.append(c);
            } else {
                if (count.length() == 0) {
                    solutionStr.append(c);
                } else {
                    int repeatCount = Integer.parseInt(count.toString());
                    solutionStr.append(String.valueOf(c).repeat(repeatCount));
                    count.setLength(0); // clear the count
                }
            }
        }

        return solutionStr.toString();
    }

    public static void main(String[] args) {
        System.out.println(DecodeString.decode("2"));
        System.out.println(DecodeString.decode("ab2c"));
        System.out.println(DecodeString.decode("a2bc2"));
        System.out.println(DecodeString.decode("abc3d"));

    }
}
