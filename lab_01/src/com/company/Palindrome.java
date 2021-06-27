package com.company;

public class Palindrome {

    public static void main(String[] args ) {
        for (int i=0;i<args.length; i++){
            String s = args[i];
            System.out.println(isPalindrome(s));
        }
    }
    public static String reverseString(String s){
        String a = "";

        for (int i = s.length()-1; i>=0;i--){
            a+=s.charAt(i);
        }
        return a.toLowerCase();
    }
    public static boolean isPalindrome(String s){

        return s.equals(reverseString(s));
    }
}
