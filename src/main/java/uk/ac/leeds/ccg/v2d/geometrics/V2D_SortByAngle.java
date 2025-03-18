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
import uk.ac.leeds.ccg.v2d.geometry.V2D_Vector;

/**
 * Comparator for ordering points around a centroid. This is very similar to 
 * uk.ac.leeds.ccg.v2d.geometrics.d.V2D_SortByAngleDouble as the sorting is
 * only a means to an end and is not needed to be very precise. In the 
 * algorithm a conversion to use instances of V2D_Vector happens at a
 * convenient step.
 * 
 * @author Andy Turner
 */
public class V2D_SortByAngle implements Comparator<V2D_Point>{
    
    /**
     * A point somewhere near the centre of points.
     */
    V2D_Point c;
    
    /**
     * The reference point used to order all other points with respect to the centroid.
     */
    V2D_Point r;
    
    /**
     * The order of magnitude for the precision.
     */
    int oom;
    
    /**
     * The RoundingMode for any rounding.
     */
    RoundingMode rm;
    
    /**
     * Creates a new instance.
     *
     * @param c A central point.
     * @param r A reference point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V2D_SortByAngle(V2D_Point c, V2D_Point r, int oom, RoundingMode rm) {
        this.c = c;
        this.r = r;
        this.oom = oom;
        this.rm = rm;
    }
    
    @Override
    public int compare(V2D_Point a, V2D_Point b) {
        BigRational rx = r.getX(oom, rm);
        BigRational ry = r.getY(oom, rm);
        BigRational cx = c.getX(oom, rm);
        BigRational cy = c.getY(oom, rm);
        BigRational ax = a.getX(oom, rm);
        BigRational ay = a.getY(oom, rm);
        BigRational bx = b.getX(oom, rm);
        BigRational by = b.getY(oom, rm);
        double rxscx = rx.subtract(cx).doubleValue();
        double ryscy = ry.subtract(cy).doubleValue();
        double axscx = ax.subtract(cx).doubleValue();
        double ayscy = ay.subtract(cy).doubleValue();
        double bxscx = bx.subtract(cx).doubleValue();
        double byscy = by.subtract(cy).doubleValue();
        V2D_Vector cr = new V2D_Vector(rxscx, ryscy); 
        V2D_Vector ca = new V2D_Vector(axscx, ayscy); 
        V2D_Vector cb = new V2D_Vector(bxscx, byscy);
        BigRational aa = cr.getAngle(ca, oom, rm);
        BigRational ba = cr.getAngle(cb, oom, rm);
        if (aa.compareTo(ba) == 1) {
            return 1;
        }
        if (aa.compareTo(ba) == -1) {
            return -1;
        }
        BigRational ma = ca.getMagnitudeSquared();
        BigRational mb = cb.getMagnitudeSquared();
        if (ma.compareTo(mb) == 1) {
            return 1;
        }
        if (ma.compareTo(mb) == -1) {
            return -1;
        }
        return 0;
    }
    
}
