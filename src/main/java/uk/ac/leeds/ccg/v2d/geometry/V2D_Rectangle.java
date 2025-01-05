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


import ch.obermuhlner.math.big.BigRational;
import uk.ac.leeds.ccg.v2d.geometry.envelope.V2D_Envelope;

/**
 * For representing and processing rectangles in 2D. In special cases, the
 * rectangle could be a line segment or a point when two or all of
 * {@link #p}, {@link #q}, {@link #r} and {@link #s} are equal respectively.
 * {@code
 *         t
 *  p ----------- q
 *  |             |
 * l|             |ri
 *  |             |
 *  s ----------- r
 *         b
 * }
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class V2D_Rectangle extends V2D_Geometry implements V2D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * A corner of the rectangle. The others are {@link #q}, {@link #r}, and
     * {@link #s}.
     */
    public final V2D_Point p;

    /**
     * A corner of the rectangle. The others are {@link #p}, {@link #r}, and
     * {@link #s}.
     */
    public final V2D_Point q;

    /**
     * A corner of the rectangle. The others are {@link #p}, {@link #q}, and
     * {@link #s}.
     */
    public final V2D_Point r;

    /**
     * The other corner of the rectangle. The others are {@link #p}, {@link #q},
     * and {@link #r}.
     */
    public final V2D_Point s;

    /**
     * The vector from {@link #p} to {@link #q}.
     */
    protected final V2D_Vector pq;

    /**
     * The vector from {@link #q} to {@link #r}.
     */
    protected final V2D_Vector qr;

    /**
     * For storing the line segment from {@link #p} to {@link #q}.
     */
    protected final V2D_LineSegment t;

    /**
     * For storing the line segment from {@link #q} to {@link #r}.
     */
    protected final V2D_LineSegment ri;

    /**
     * For storing the line segment from {@link #r} to {@link #s}.
     */
    protected final V2D_LineSegment b;

    /**
     * For storing the vector from {@link #s} to {@link #p}.
     */
    protected final V2D_LineSegment l;

    /**
     * For storing the envelope
     */
    protected V2D_Envelope en;

    /**
     * @param p The top left corner of the rectangle.
     * @param q The top right corner of the rectangle.
     * @param r The bottom right corner of the rectangle.
     * @param s The bottom left corner of the rectangle.
     * @throws java.lang.RuntimeException iff the points do not define a
     * rectangle.
     */
    public V2D_Rectangle(V2D_Point p, V2D_Point q, V2D_Point r, V2D_Point s) {
        this.p = p;
        this.q = q;
        this.r = r;
        this.s = s;
        //en = new V2D_Envelope(p, q, r, s); Not initialised here as it causes a StackOverflowError
        pq = new V2D_Vector(p, q);
        qr = new V2D_Vector(q, r);
        t = new V2D_LineSegment(p, q);
        ri = new V2D_LineSegment(q, r);
        b = new V2D_LineSegment(r, s);
        l = new V2D_LineSegment(s, p);
        // Check for rectangle.
        if (pq.isZeroVector()) {
            if (qr.isZeroVector()) {
                // Rectangle is a point.
            } else {
                // Rectangle is a line.
            }
        } else {
            if (qr.isZeroVector()) {
                // Rectangle is a line.
            } else {
                // Rectangle has area.
                if (!(pq.isOrthogonal(qr))) {
                    throw new RuntimeException("The points do not define a rectangle.");
                }
            }
        }
    }

    @Override
    public V2D_Envelope getEnvelope() {
        if (en == null) {
            en = new V2D_Envelope(p, q, r, s);
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
            if (this.t.isIntersectedBy(pt) || ri.isIntersectedBy(pt)
                    || b.isIntersectedBy(pt) || l.isIntersectedBy(pt)) {
                return true;
            }
            V2D_Vector ppt = new V2D_Vector(p, pt);
            V2D_Vector qpt = new V2D_Vector(q, pt);
            V2D_Vector rpt = new V2D_Vector(r, pt);
            V2D_Vector spt = new V2D_Vector(s, pt);
            V2D_Vector rs = new V2D_Vector(r, s);
            V2D_Vector sp = new V2D_Vector(s, p);
            V2D_Vector cp = pq.getCrossProduct(ppt);
            V2D_Vector cq = qr.getCrossProduct(qpt);
            V2D_Vector cr = rs.getCrossProduct(rpt);
            V2D_Vector cs = sp.getCrossProduct(spt);
            /**
             * If cp, cq, cr, and cs are all in the same direction then pt
             * intersects.
             */
            BigRational mp = cp.getMagnitudeSquared();
            BigRational mq = cq.getMagnitudeSquared();
            V2D_Vector cpq = cp.add(cq);
            BigRational mpq = cpq.getMagnitudeSquared();
            if (mpq.compareTo(mp) == 1 && mpq.compareTo(mq) == 1) {
                BigRational mr = cr.getMagnitudeSquared();
                V2D_Vector cpqr = cpq.add(cr);
                BigRational mpqr = cpqr.getMagnitudeSquared();
                if (mpqr.compareTo(mr) == 1 && mpqr.compareTo(mpq) == 1) {
                    BigRational ms = cs.getMagnitudeSquared();
                    BigRational mpqrs = cpqr.add(cs).getMagnitudeSquared();
                    if (mpqrs.compareTo(ms) == 1 && mpqrs.compareTo(mpqr) == 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param l The line to intersect with.
     * @return A point or line segment.
     */
    @Override
    public V2D_Geometry getIntersection(V2D_Line l) {
        if (!isEnvelopeIntersectedBy(l)) {
            return null;
        }
        /**
         * Get the intersection of the line and each edge of the rectangle.
         */
        V2D_Geometry ti = t.getIntersection(l);
        if (ti == null) {
            // Check ri, l
            V2D_Geometry rii = ri.getIntersection(l);
            if (rii == null) {
                // Check b, l
                V2D_Geometry bi = b.getIntersection(l);
                if (bi == null) {
                    // Check l
                    V2D_Geometry tli = this.l.getIntersection(l);
                    if (tli == null) {
                        return null;
                    } else {
                        return tli;
                    }
                } else if (bi instanceof V2D_LineSegment) {
                    return bi;
                } else {
                    // Check l
                    V2D_Geometry tli = this.l.getIntersection(l);
                    if (tli == null) {
                        return bi;
                    } else {
                        return new V2D_LineSegment((V2D_Point) bi,
                                (V2D_Point) tli);
                    }
                }
            } else if (rii instanceof V2D_LineSegment) {
                return rii;
            } else {
                // Check b, l
                V2D_Geometry bi = b.getIntersection(l);
                if (bi == null) {
                    // Check l
                    V2D_Geometry tli = this.l.getIntersection(l);
                    if (tli == null) {
                        return rii;
                    } else {
                        return new V2D_LineSegment((V2D_Point) rii,
                                (V2D_Point) tli);
                    }
                } else if (bi instanceof V2D_LineSegment) {
                    return bi;
                } else {
                    // Check l
                    V2D_Geometry tli = this.l.getIntersection(l);
                    if (tli == null) {
                        V2D_Point riip = (V2D_Point) rii;
                        V2D_Point bip = (V2D_Point) bi;
                        if (riip.equals(bip)) {
                            return bip;
                        } else {
                            return new V2D_LineSegment(riip, bip);
                        }
                    } else {
                        return new V2D_LineSegment((V2D_Point) bi,
                                (V2D_Point) tli);
                    }
                }
            }
        } else if (ti instanceof V2D_LineSegment) {
            return ti;
        } else {
            // Check ri, b, l
            V2D_Geometry rii = ri.getIntersection(l);
            if (rii == null) {
                // Check b, l
                V2D_Geometry bi = b.getIntersection(l);
                if (bi == null) {
                    // Check l
                    V2D_Geometry tli = this.l.getIntersection(l);
                    if (tli == null) {
                        return ti;
                    } else {
                        V2D_Point tlip = (V2D_Point) tli;
                        V2D_Point tip = (V2D_Point) ti;
                        if (tlip.equals(tip)) {
                            return tlip;
                        } else {
                            return new V2D_LineSegment(tlip, tip);
                        }
                    }
                } else if (bi instanceof V2D_LineSegment) {
                    return bi;
                } else {
                    return new V2D_LineSegment((V2D_Point) ti, (V2D_Point) bi);
                }
            } else {
                V2D_Point tip = (V2D_Point) ti;
                V2D_Point riip = (V2D_Point) rii;
                if (tip.equals(riip)) {
                    // Check b, l
                    V2D_Geometry sri = b.getIntersection(l);
                    if (sri == null) {
                        // Check l
                        V2D_Geometry tli = this.l.getIntersection(l);
                        if (tli == null) {
                            return rii;
                        } else {
                            return new V2D_LineSegment(riip,
                                    (V2D_Point) tli);
                        }
                    } else if (sri instanceof V2D_LineSegment) {
                        return sri;
                    } else {
                        return new V2D_LineSegment(riip, (V2D_Point) sri);
                    }
                } else {
                    return new V2D_LineSegment(riip, tip);
                }
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
        if (!isEnvelopeIntersectedBy(l)) {
            return false;
        }
        /**
         * Check intersection of the line with edges of the rectangle.
         */
        if(t.isIntersectedBy(l)) {
            return true;
        }
        if(ri.isIntersectedBy(l)) {
            return true;
        }
        return this.l.isIntersectedBy(l);
    }
    
    @Override
    public boolean isIntersectedBy(V2D_LineSegment l, boolean b) {
        if (!getEnvelope().isIntersectedBy(l.getEnvelope())) {
            return false;
        }
        /**
         * Check intersection of the line with edges of the rectangle.
         */
        if(t.isIntersectedBy(l, b)) {
            return true;
        }
        if(ri.isIntersectedBy(l, b)) {
            return true;
        }
        if(this.b.isIntersectedBy(l, b)) {
            return true;
        }
        return this.l.isIntersectedBy(l);
    }
}
