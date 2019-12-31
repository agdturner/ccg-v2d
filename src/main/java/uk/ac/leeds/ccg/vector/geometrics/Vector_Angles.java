/*
 * Copyright 2019 Andy Turner, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leeds.ccg.vector.geometrics;

import uk.ac.leeds.ccg.vector.geometry.Vector_Point2D;

/**
 * Vector Angles
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class Vector_Angles {

    public Vector_Angles() {
    }

    /**
     * @param p Points for which the angles to the Y-Axis are wanted.
     * @return Angles to the Y-Axis for each point in {@code p}.
     */
    public static double[] getAnglesToYAxisClockwise_doubleArray(
            Vector_Point2D[] p) {
        double[] r = new double[p.length];
        for (int i = 0; i < p.length - 1; i++) {
            r[i] = p[i].getAngle_double(p[i + 1]);
        }
        r[p.length - 1] = p[p.length - 1].getAngle_double(p[0]);
        return r;
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