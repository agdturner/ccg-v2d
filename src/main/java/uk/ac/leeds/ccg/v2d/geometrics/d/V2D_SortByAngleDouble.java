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
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_VectorDouble;

/**
 * Comparator for ordering points around a centroid.
 *
 * @author Andy Turner
 */
public class V2D_SortByAngleDouble implements Comparator<V2D_PointDouble> {

    /**
     * A point somewhere near the centre of points.
     */
    V2D_PointDouble c;
    
    /**
     * The reference point used to order all other points with respect to the centroid.
     */
    V2D_PointDouble r;

    /**
     * Create a new instance.
     * 
     * @param c A central point.
     * @param r A reference point.
     */
    public V2D_SortByAngleDouble(V2D_PointDouble c, V2D_PointDouble r) {
        this.c = c;
        this.r = r;
    }

    @Override
    public int compare(V2D_PointDouble a, V2D_PointDouble b) {
        double cx = c.getX();
        double cy = c.getY();
        double rx = r.getX();
        double ry = r.getY();
        double ax = a.getX();
        double ay = a.getY();
        double bx = b.getX();
        double by = b.getY();
        double rxscx = rx - cx;
        double ryscy = ry - cy;
        double axscx = ax - cx;
        double ayscy = ay - cy;
        double bxscx = bx - cx;
        double byscy = by - cy;
        V2D_VectorDouble cr = new V2D_VectorDouble(rxscx, ryscy); 
        V2D_VectorDouble ca = new V2D_VectorDouble(axscx, ayscy); 
        V2D_VectorDouble cb = new V2D_VectorDouble(bxscx, byscy);
        double aa = cr.getAngle2(ca);
        double ba = cr.getAngle2(cb);
        if (aa > ba) {
            return 1;
        }
        if (aa < ba) {
            return -1;
        }
        double ma = ca.getMagnitudeSquared();
        double mb = cb.getMagnitudeSquared();
        if (ma > mb) {
            return 1;
        }
        if (ma < mb) {
            return -1;
        }
        return 0;
    }

}
