package com.company;

public class Point3d extends Point2d {
    private double zCoord;

    public Point3d(double x, double y, double z) {
        super(x, y);
        this.zCoord = z;
    }

    public Point3d() {
        this(0.0, 0.0, 0.0);
    }

    public double getZ() {
        return this.zCoord;
    }

    public void setZ(double val) {
        this.zCoord = val;
    }

    public double distanceTo(Point3d a) {
        double distance = Math.sqrt(Math.pow(a.getX() - getX(), 2)
                + Math.pow(a.getY() - getY(), 2) + Math.pow(a.getZ() - getZ(), 2));
        return Math.round(distance * 100) / 100.0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof Point3d) {
            Point3d p = (Point3d) obj;
            return p.getX() == p.getX() && p.getY() == p.getY() && p.getZ() == p.getZ();
        } else return false;
    }
}
