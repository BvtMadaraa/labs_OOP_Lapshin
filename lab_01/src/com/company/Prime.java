package com.company;

public class Prime {

    public static void main(String[] args) {
	    for (int i = 0; i < 101; i++){
	        if (isPrime(i)) System.out.println(i);
        }
    }
    public static boolean isPrime(int a){
        for (int i = 2; i<(a/2-1);i++){
            if (a%i==0) return false;
        }
        return true;
    }
}
