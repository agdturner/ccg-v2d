/*
 * Copyright 2025 Centre for Computational Geography.
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
package uk.ac.leeds.ccg.v2d.geometrics.d;

import uk.ac.leeds.ccg.v2d.geometry.d.V2D_PointDouble;

/**
 *
 * @author Andy Turner
 */
public class V2D_GeometricsDouble {
    
    /**
     * Create a new instance.
     */
    public V2D_GeometricsDouble(){}
    
    /**
     * https://en.wikipedia.org/wiki/Centroid
     * This centroid point is the minimum of the sum of squared Euclidean 
     * distances between itself and each input point. This is not a barycentre.
     * @param ps The input point(s).
     * @return A sort of centroid.
     */
    public static V2D_PointDouble getCentroid(V2D_PointDouble... ps) {
        double x = ps[0].getX();
        double y = ps[0].getY();
        double n = ps.length;
        for (int i = 1; i < n; i ++) {
            x += ps[i].getX();
            y += ps[i].getY();
        }
        x /= n;
        y /= n;
        return new V2D_PointDouble(x, y);
    }

    /**
     * @param ps The points the index of the max of which is returned.
     * @return The maximum point (largest y, largest x).
     */
    public static int getMax(V2D_PointDouble... ps) {
        int r = 0;
        V2D_PointDouble max = ps[0];
        double n = ps.length;
        for (int i = 1; i < n; i ++) {
            if (ps[i].compareTo(max) == 1) {
                r = i;
            }
        }
        return r;
    }
    
    /**
     * @param ps The points the index of the min of which is returned.
     * @return The minimum point (smallest y, smallest x).
     */
    public static int getMin(V2D_PointDouble... ps) {
        int r = 0;
        V2D_PointDouble max = ps[0];
        double n = ps.length;
        for (int i = 1; i < n; i ++) {
            if (ps[i].compareTo(max) == -1) {
                r = i;
            }
        }
        return r;
    }
}
