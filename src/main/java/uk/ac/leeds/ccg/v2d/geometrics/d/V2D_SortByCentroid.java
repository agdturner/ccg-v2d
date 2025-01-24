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
public class V2D_SortByCentroid implements Comparator<V2D_PointDouble>{
    
    V2D_PointDouble centroid;
    
    public V2D_SortByCentroid(V2D_PointDouble centroid) {
        this.centroid = centroid;
    }
    
    @Override
    public int compare(V2D_PointDouble a, V2D_PointDouble b) {
        double cx = centroid.getX();
        double ax = a.getX();
        double bx = b.getX();        
        if (ax - cx >= 0d && bx - cx < 0d) {
            return 1;
        }
        if (ax - cx < 0d && bx - cx >= 0d) {
            return -1;
        }
        double cy = centroid.getY();
        double ay = a.getY();
        double by = b.getY();        
        if (ax - cx == 0 && bx - cx == 0) {
            if (ay - cy >= 0 || by - cy >= 0) {
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
        double det = (ax - cx) * (by - cy) - (bx - cx) * (ay - cy);
        if (det < 0d) {
            return 1;
        }
        if (det > 0d) {
            return -1;
        }
        // The centroid, a and b are collinear, further is greater.
        double d1 = (ax - cx) * (ax - cx) + (ay - cy) * (ay - cy);
        double d2 = (bx - cx) * (bx - cx) + (by - cy) * (by - cy);
        if (d1 > d2) {
            return 1;
        } else {
            return -1;
        }
    }
    
}
