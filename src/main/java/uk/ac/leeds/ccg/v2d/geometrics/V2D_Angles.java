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
package uk.ac.leeds.ccg.v2d.geometrics;

import uk.ac.leeds.ccg.v2d.geometry.V2D_Point;

/**
 * Vector Angles
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class V2D_Angles {

    public V2D_Angles() {
    }

    /**
     * @param p Points for which the angles to the Y-Axis are wanted.
     * @return Angles to the Y-Axis for each point in {@code p}.
     */
    public static double[] getAnglesToYAxisClockwise(V2D_Point[] p) {
        double[] r = new double[p.length];
        for (int i = 0; i < p.length - 1; i++) {
            r[i] = p[i].getAngle_double(p[i + 1]);
        }
        r[p.length - 1] = p[p.length - 1].getAngle_double(p[0]);
        return r;
    }

    /**
     * @param lr A line ring for which the angles to the Y-Axis are wanted.
     * @return The Clockwise inside angles for {@code lr}.
     */
    public static double[] getInsideAnglesClockwise(V2D_Point[] lr) {
        double[] r = new double[lr.length];
        r[0] = getInsideAngleClockwise(lr[lr.length - 1], lr[0], lr[1]);
        for (int i = 1; i < lr.length - 1; i++) {
            r[i] = getInsideAngleClockwise(lr[i - 1], lr[i], lr[i + 1]);
        }
        r[lr.length - 1] = getInsideAngleClockwise(lr[lr.length - 2],
                lr[lr.length - 1], lr[0]);
        return r;
    }

    /**
     * @param a External point.
     * @param b Mid point where inside angle is calculated.
     * @param c External point.
     * @return The inside Angle at b_Point for a Linear Ring assuming the ring
     * is clockwise and the Linear Ring Sequence is a_Point, b_Point, c_Point.
     */
    public static double getInsideAngleClockwise(V2D_Point a,
            V2D_Point b, V2D_Point c) {
        double angle_aby = (Math.PI * 2.0d) - a.getAngle_double(b);
        //double angle_aby_deg = getRadToDeg(angle_aby);
        double angle_bcy = Math.PI - b.getAngle_double(c);
        //double angle_bcy_deg = getRadToDeg(angle_bcy);
        double r = angle_bcy - angle_aby;
        if (r < 0) {
            r = (Math.PI * 2.0d) + r;
        }
        //double result_deg = getRadToDeg(result);
        return r;
    }

    /**
     * @param a The angle in radians to be returned in degrees.
     * @return The angle {@code a} in degrees.
     */
    public static double getRadToDeg(double a) {
        return (a / Math.PI) * 180.0d;
    }
}
