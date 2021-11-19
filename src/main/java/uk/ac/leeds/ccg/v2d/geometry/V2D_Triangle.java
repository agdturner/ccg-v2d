/*
 * Copyright 2020 Andy Turner, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain xxx copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leeds.ccg.v2d.geometry;

import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.v2d.geometry.envelope.V2D_Envelope;

/**
 * For representing and processing triangles in 2D. In special cases, the
 * triangle could be a line segment or a point when two or all of
 * {@link #p}, {@link #q}, and {@link #r} are equal respectively.
 * {@code
 *      lpq
 *  p -------- q
 *    \      /
 *     \    / lqr
 *  lrp \  /
 *       \/
 *       r
 * }
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class V2D_Triangle extends V2D_Geometry implements V2D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the envelope.
     */
    protected V2D_Envelope en;

    /**
     * One of the points that defines the plane.
     */
    protected final V2D_Point p;

    /**
     * One of the points that defines the plane.
     */
    protected final V2D_Point q;

    /**
     * One of the points that defines the plane.
     */
    protected final V2D_Point r;

    /**
     * The vector representing the move from {@link #p} to {@link #q}.
     */
    protected final V2D_Vector pq;

    /**
     * The vector representing the move from {@link #q} to {@link #r}.
     */
    protected final V2D_Vector qr;
    
    /**
     * The vector representing the move from {@link #r} to {@link #p}.
     */
    protected final V2D_Vector rp;
    
    /**
     * The line from {@link #p} to {@link #q}.
     */
    protected final V2D_LineSegment lpq;

    /**
     * The line from {@link #q} to {@link #r}.
     */
    protected final V2D_LineSegment lqr;

    /**
     * The line from {@link #r} to {@link #p}.
     */
    protected final V2D_LineSegment lrp;

    /**
     * Creates a new triangle.
     *
     * @param p A point that defines the triangle.
     * @param q A point that defines the triangle.
     * @param r A point that defines the triangle.
     */
    public V2D_Triangle(V2D_Point p, V2D_Point q, V2D_Point r) {
        this.p = p;
        this.q = q;
        this.r = r;
        pq = new V2D_Vector(p, q);
        qr = new V2D_Vector(q, r);
        rp = new V2D_Vector(r, p);
        lpq = new V2D_LineSegment(p, q);
        lqr = new V2D_LineSegment(q, r);
        lrp = new V2D_LineSegment(r, p);
    }

    @Override
    public V2D_Envelope getEnvelope() {
        if (en == null) {
            en = new V2D_Envelope(p, q, r);
        }
        return en;
    }

    /**
     * @param pt The point to intersect with.
     * @return A point or line segment.
     */
    @Override
    public boolean isIntersectedBy(V2D_Point pt) {
        if (getEnvelope().isIntersectedBy(pt)) {
                if (lpq.isIntersectedBy(pt) || lqr.isIntersectedBy(pt) || lrp.isIntersectedBy(pt)) {
                    return true;
                }
                V2D_Vector ppt = new V2D_Vector(p, pt);
                V2D_Vector qpt = new V2D_Vector(q, pt);
                V2D_Vector rpt = new V2D_Vector(r, pt);
                V2D_Vector cp = pq.getCrossProduct(ppt);
                V2D_Vector cq = qr.getCrossProduct(qpt);
                V2D_Vector cr = rp.getCrossProduct(rpt);
                /**
                 * If cp, cq and cr are all in the same direction then pt
                 * intersects.
                 */
                Math_BigRational mp = cp.getMagnitudeSquared();
                Math_BigRational mq = cq.getMagnitudeSquared();
                V2D_Vector cpq = cp.add(cq);
                Math_BigRational mpq = cpq.getMagnitudeSquared();
                if (mpq.compareTo(mp) == 1 && mpq.compareTo(mq) == 1) {
                    Math_BigRational mr = cr.getMagnitudeSquared();
                    Math_BigRational mpqr = cpq.add(cr).getMagnitudeSquared();
                    if (mpqr.compareTo(mr) == 1 && mpqr.compareTo(mpq) == 1) {
                        return true;
                    }
                }
        }
        return false;
    }

    /**
     * @param scale The scale.
     * @param rm RoundingMode.
     * @return The area of the triangle (rounded).
     */
    public Math_BigRational getArea(int oom) {
        return pq.getCrossProduct(qr).getMagnitude(oom - 1).divide(2);
    }

    /**
     * @param l The line to intersect with.
     * @return A point or line segment.
     */
    @Override
    public V2D_Geometry getIntersection(V2D_Line l) {
    V2D_Geometry enil = getEnvelope().getIntersection(l);
            if (enil == null) {
                return null;
            }
            /**
             * Get the intersection of the line and each edge of the triangle.
             */
            V2D_Geometry lpqi = lpq.getIntersection(l);
            if (lpqi == null) {
                // Check lqr, lrp
                V2D_Geometry lqri = lqr.getIntersection(l);
                if (lqri == null) {
                    // No need to check lrp.
                    return null;
                } else if (lqri instanceof V2D_LineSegment) {
                    return lqri;
                } else {
                    return new V2D_LineSegment((V2D_Point) lqri,
                                    (V2D_Point) lrp.getIntersection(l));
                }
            } else if (lpqi instanceof V2D_LineSegment) {
                return lpqi;
            } else {
                // Check lqr
                V2D_Geometry lqri = lqr.getIntersection(l);
                if (lqri == null) {
                    return new V2D_LineSegment((V2D_Point) lpqi,
                                    (V2D_Point) lrp.getIntersection(l));
                } else if (lqri instanceof V2D_LineSegment) {
                    return lqri;
                } else {
                    return new V2D_LineSegment((V2D_Point) lqri,
                                    (V2D_Point) lpqi);
                }                
            }
}

    @Override
    public V2D_Geometry getIntersection(V2D_LineSegment l, boolean b) {
        V2D_Geometry i = getIntersection(l);
        if (i == null) {
            return null;
        }
        if (i instanceof V2D_Point) {
            if (l.isIntersectedBy((V2D_Point) i)) {
                return i;
            } else {
                return null;
            }
        } else {
            return ((V2D_LineSegment) i).getIntersection(l, b);
        }
    }

    /**
     * @param l The line for which intersection with the envelope is indicated.
     * @return {@code true} iff {@code l} intersects with the envelope.  
     */
    public boolean isEnvelopeIntersectedBy(V2D_Line l) {
        return getEnvelope().getIntersection(l) != null;
    }
    
    @Override
    public boolean isIntersectedBy(V2D_Line l) {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isIntersectedBy(V2D_LineSegment l, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
