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

import java.util.Comparator;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_PointDouble;

/**
 * Comparator for ordering points around a centroid.
 *
 * @author Andy Turner
 */
public class V2D_SortByCentroidDouble implements Comparator<V2D_PointDouble> {

    V2D_PointDouble centroid;

    public V2D_SortByCentroidDouble(V2D_PointDouble centroid) {
        this.centroid = centroid;
    }

    @Override
    public int compare(V2D_PointDouble a, V2D_PointDouble b) {
        double cx = centroid.getX();
        double ax = a.getX();
        double bx = b.getX();
        double axscx = ax - cx;
        double bxscx = bx - cx;
        if (axscx >= 0d && bxscx < 0d) {
            return 1;
        }
        if (axscx < 0d && bxscx >= 0d) {
            return -1;
        }
        double cy = centroid.getY();
        double ay = a.getY();
        double by = b.getY();
        double ayscy = ay - cy;
        double byscy = by - cy;
        if (axscx == 0 && bxscx == 0) {
            if (ayscy >= 0 || byscy >= 0) {
                if (ay > by) {
                    return 1;
                } else {
                    return -1;
                }
            }
            if (by > ay) {
                return 1;
            } else {
                return -1;
            }
        }
        // Cross product.
        double det = axscx * byscy - bxscx * ayscy;
        if (det < 0d) {
            return 1;
        }
        if (det > 0d) {
            return -1;
        }
        // The centroid, a and b are collinear, further is greater.
        double d1 = axscx * axscx + ayscy * ayscy;
        double d2 = bxscx * bxscx + byscy * byscy;
        if (d1 > d2) {
            return 1;
        } else {
            return -1;
        }
    }

}
