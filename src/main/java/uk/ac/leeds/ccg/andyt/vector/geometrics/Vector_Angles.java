/**
 * Component of a library for handling spatial vector data.
 * Copyright (C) 2009 Andy Turner, CCG, University of Leeds, UK.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA.
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

    /**
     * 
     * @param a_LineRing A Vector_Point2D[] for which the angles to the YAxis 
     * are wanted.
     * @return A double[] of angles to the YAxis for a_LineRing.
     */
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
     * @param a_LineRing A Vector_Point2D[] for which the angles to the YAxis 
     * are wanted.
     * @return The Clockwise inside angles for a_LineRing.
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
     * @param a External point.
     * @param b Mid point where inside angle is calculated.
     * @param c External point.
     * @return The inside Angle at b_Point for a Linear Ring assuming the ring
     * is clockwise and the Linear Ring Sequence is a_Point, b_Point, c_Point.
     */
    public static double getInsideAngleClockwise_doubleArray(
            Vector_Point2D a,
            Vector_Point2D b,
            Vector_Point2D c) {
        double angle_aby = (Math.PI * 2.0d) - a.getAngle_double(b);
        //double angle_aby_deg = getRadToDeg_double(angle_aby);
        double angle_bcy = Math.PI - b.getAngle_double(c);
        //double angle_bcy_deg = getRadToDeg_double(angle_bcy);
        double result = angle_bcy - angle_aby;
        if (result < 0) {
            result = (Math.PI * 2.0d) + result;
        }
        //double result_deg = getRadToDeg_double(result);
        return result;
    }

    /**
     * This is a basic operation working with double precision.
     * @param angleInRadians The angleInRadians to be returned in degrees.
     * @return The angle in degrees for the angleInRadians.
     */
    public static double getRadToDeg_double(
            double angleInRadians) {
        return (angleInRadians / Math.PI) * 180.0d;
    }
}