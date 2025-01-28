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
package uk.ac.leeds.ccg.v2d.geometrics;

import ch.obermuhlner.math.big.BigRational;
import java.math.RoundingMode;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Point;

/**
 *
 * @author Andy Turner
 */
public class V2D_Geometrics {
    
    /**
     * https://en.wikipedia.org/wiki/Centroid
     * This centroid point is the minimum of the sum of squared Euclidean 
     * distances between itself and each input point. This is not a barycentre.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param ps The input point(s).
     * @return A sort of centroid.
     */
    public static V2D_Point getCentroid(int oom, RoundingMode rm, V2D_Point... ps) {
        BigRational x = ps[0].getX(oom, rm);
        BigRational y = ps[0].getY(oom, rm);
        int n = ps.length;
        for (int i = 1; i < n; i ++) {
            x = x.add(ps[i].getX(oom, rm));
            y = y.add(ps[i].getY(oom, rm));
        }
        x = x.divide(n);
        y = y.divide(n);
        return new V2D_Point(x, y);
    }

}
