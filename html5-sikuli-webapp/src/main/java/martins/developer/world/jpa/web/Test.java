package martins.developer.world.jpa.web;

public class Test {

    public static void main(String args[]) {
        String s1 = "abc";
        String s2 = "abc";
        compare(s1, s2); //equal
        StringBuilder sb = new StringBuilder();
        sb.append("abc");
        compare(s1, sb.toString()); //not equal
        String s3 = new String("abc");
        compare(s1, s3); //not equal
        String s4 = s3.intern();
        compare(s1, s4); //equal
        String s5 = Test2.ABC;
        compare(s1, s5); //equal
        String s6 = "xabcx".substring(1, 4);
        compare(s1, s6); //not equal
    }

    private static void compare(String s1, String s2) {
        if(s1 == s2) {
            System.out.println(String.format("Strings '%s' and '%s' are equal.", s1, s2));
        } else {
            System.out.println(String.format("Strings '%s' and '%s' are NOT equal.", s1, s2));
        }
    }
}
