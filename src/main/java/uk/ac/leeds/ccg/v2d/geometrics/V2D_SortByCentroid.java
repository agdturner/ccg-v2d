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
import java.util.Comparator;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Point;

/**
 * Comparator for ordering points around a centroid.
 * 
 * @author Andy Turner
 */
public class V2D_SortByCentroid implements Comparator<V2D_Point>{
    
    V2D_Point centroid;
    
    int oom;
    
    RoundingMode rm;
    
    /**
     * Creates a new instance.
     * 
     * @param centroid The centroid
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V2D_SortByCentroid(V2D_Point centroid, int oom, RoundingMode rm) {
        this.centroid = centroid;
        this.oom = oom;
        this.rm = rm;
    }
    
    @Override
    public int compare(V2D_Point a, V2D_Point b) {
        BigRational cx = centroid.getX(oom, rm);
        BigRational ax = a.getX(oom, rm);
        BigRational bx = b.getX(oom, rm);
        BigRational axscx = ax.subtract(cx);
        BigRational bxscx = bx.subtract(cx);
        if (axscx.compareTo(BigRational.ZERO) != -1 
                && bxscx.compareTo(BigRational.ZERO) == -1) {
            return 1;
        }
        if (axscx.compareTo(BigRational.ZERO) == -1 
                && bxscx.compareTo(BigRational.ZERO) != -1) {
            return -1;
        }
        BigRational cy = centroid.getY(oom, rm);
        BigRational ay = a.getY(oom, rm);
        BigRational by = b.getY(oom, rm);
        BigRational ayscy = ay.subtract(cy);
        BigRational byscy = by.subtract(cy);
        if (axscx.compareTo(BigRational.ZERO) == 0 
                && bxscx.compareTo(BigRational.ZERO) != 0) {
            if (ayscy.compareTo(BigRational.ZERO) != -1 
                && byscy.compareTo(BigRational.ZERO) != -1) {
                if (ay.compareTo(by) == 1) {
                    return 1;
                } else {
                    return -1;
                }
            }
            if (by.compareTo(ay) == 1) {
                    return 1;
                } else {
                    return -1;
                }
        }
        // Cross product.
        BigRational det = (axscx.multiply(byscy)).subtract(bxscx.multiply(ayscy));
        if (det.compareTo(BigRational.ZERO) == -1) {
            return 1;
        }
        if (det.compareTo(BigRational.ZERO) == 1) {
            return -1;
        }
        // The centroid, a and b are collinear, further is greater.
        BigRational d1 = (axscx.multiply(axscx)).add(ayscy.multiply(ayscy));
        BigRational d2 = (bxscx.multiply(bxscx)).add(byscy.multiply(byscy));
        if (d1.compareTo(d2) == 1) {
            return 1;
        } else {
            return -1;
        }
    }
    
}
