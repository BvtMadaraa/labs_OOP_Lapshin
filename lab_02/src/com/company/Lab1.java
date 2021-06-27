package com.company;

public class Lab1 {
    public static void main(String[] args) {
        Point3d a = new Point3d(); // {0; 0; 0}
        Point3d b = new Point3d(2, 5.4, 152.251); // {2; 5.4; 152.251}
        Point3d c = new Point3d(31,23,44); // {31; 23; 44}
        if (a.equals(b) || b.equals(c) || c.equals(a))
            System.out.println("2 объекта Point3d равны");
        else
        System.out.println(computeArea(a,b,c));
    }
    public static double computeArea(Point3d a, Point3d b, Point3d c){
        double f,s,t, p;
        f = a.distanceTo(b);
        s = b.distanceTo(c);
        t = c.distanceTo(a);
        p = (f + s + t)/2;
        return Math.sqrt(p*(p-f)*(p-s)*(p-t));
    }
}
