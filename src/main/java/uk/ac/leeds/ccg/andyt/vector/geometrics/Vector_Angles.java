/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.andyt.vector.geometrics;

import uk.ac.leeds.ccg.andyt.vector.geometry.Vector_Point2D;

/**
 *
 * @author geoagdt
 */
public class Vector_Angles {

    public Vector_Angles() {
    }

    public static void main(String[] args) {
        new Vector_Angles().run();
    }

    public void run() {
        // Do application code here...
    }

    public static double[] getAnglesToYAxisClockwise_doubleArray(
            Vector_Point2D[] a_LineRing) {
        double[] result = new double[a_LineRing.length];
        for (int i = 0; i < a_LineRing.length - 1; i++) {
            result[i] = a_LineRing[i].getAngle_double(a_LineRing[i + 1]);
        }
        result[a_LineRing.length - 1] = a_LineRing[a_LineRing.length - 1].getAngle_double(a_LineRing[0]);
        return result;
    }

    /**
     * Returns the Clockwise inside angles for a_LineRing 
     * @param a_LineRing
     * @return
     */
    public static double[] getInsideAnglesClockwise_doubleArray(
            Vector_Point2D[] a_LineRing) {
        double[] result = new double[a_LineRing.length];
        result[0] = getInsideAngleClockwise_doubleArray(
                a_LineRing[a_LineRing.length - 1],
                a_LineRing[0],
                a_LineRing[1]);
        for (int i = 1; i < a_LineRing.length - 1; i++) {
            result[i] = getInsideAngleClockwise_doubleArray(
                    a_LineRing[i - 1],
                    a_LineRing[i],
                    a_LineRing[i + 1]);
        }
        result[a_LineRing.length - 1] = getInsideAngleClockwise_doubleArray(
                a_LineRing[a_LineRing.length - 2],
                a_LineRing[a_LineRing.length - 1],
                a_LineRing[0]);
        return result;
    }

    /**
     * @param a_Point2D
     * @param b_Point2D
     * @param c_Point2D
     * @return The inside Angle at b_Point for a Linear Ring assuming the ring
     * is clockwise and the Linear Ring Sequence is a_Point, b_Point, c_Point.
     */
    public static double getInsideAngleClockwise_doubleArray(
            Vector_Point2D a_Point2D,
            Vector_Point2D b_Point2D,
            Vector_Point2D c_Point2D) {
        double angle_aby = (Math.PI * 2.0d) - a_Point2D.getAngle_double(b_Point2D);
        //double angle_aby_deg = getRadToDeg_double(angle_aby);
        double angle_bcy = Math.PI - b_Point2D.getAngle_double(c_Point2D);
        //double angle_bcy_deg = getRadToDeg_double(angle_bcy);
        double result = angle_bcy - angle_aby;
        if (result < 0) {
            result = (Math.PI * 2.0d) + result;
        }
        //double result_deg = getRadToDeg_double(result);
        return result;
    }

    public static double getRadToDeg_double(
            double angleInRadians) {
        return (angleInRadians / Math.PI) * 180.0d;
    }
}