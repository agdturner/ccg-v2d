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
package uk.ac.leeds.ccg.v2d.geometry.d;

import uk.ac.leeds.ccg.math.arithmetic.Math_Double;

/**
 * 2D representation of a finite length line (a line segment). The line begins
 * at the point of {@link #l} and ends at the point {@link #qv}.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_LineSegmentDouble extends V2D_FiniteGeometryDouble {

    private static final long serialVersionUID = 1L;

    /**
     * The line of which this line segment is part and for which the point on
     * the line is one end point of this segment.
     */
    public final V2D_LineDouble l;

    /**
     * For defining {@link q} which is given relative to {@link #offset}.
     */
    protected V2D_VectorDouble qv;

    /**
     * For defining the other end point of the line segment (the other given in
     * {@link #l}.
     */
    protected V2D_PointDouble q;

    /**
     * For storing the line at l.getP() orthogonal to qv.
     */
    protected V2D_LineDouble pl;

    /**
     * For storing the line at getQ() orthogonal to qv.
     */
    protected V2D_LineDouble ql;

    /**
     * For storing the length of the line squared.
     */
    protected double len2;

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is cloned from.
     */
    public V2D_LineSegmentDouble(V2D_LineSegmentDouble l) {
        super(l.offset);
        this.l = new V2D_LineDouble(l.l);
    }

    /**
     * Create a new instance. {@link #offset} is set to {@link V2D_Vector#ZERO}.
     *
     * @param p What the point of {@link #l} is cloned from.
     * @param q What {@link #qv} is cloned from.
     */
    public V2D_LineSegmentDouble(V2D_VectorDouble p, V2D_VectorDouble q) {
        this(V2D_VectorDouble.ZERO, p, q);
    }

    /**
     * Create a new instance.
     *
     * @param offset What {@link #offset} is set to.
     * @param p What the point of {@link #l} is cloned from.
     * @param q What {@link #qv} is cloned from.
     */
    public V2D_LineSegmentDouble(V2D_VectorDouble offset, V2D_VectorDouble p,
            V2D_VectorDouble q) {
        super(offset);
        l = new V2D_LineDouble(offset, p, q);
        this.qv = q;
    }

    /**
     * Create a new instance.
     *
     * @param p What the point of {@link #l} is cloned from.
     * @param q What {@link #qv} is cloned from.
     */
    public V2D_LineSegmentDouble(V2D_PointDouble p, V2D_PointDouble q) {
        super(p.offset);
        V2D_PointDouble q2 = new V2D_PointDouble(q);
        q2.setOffset(offset);
        l = new V2D_LineDouble(offset, p.rel, q2.rel);
        this.qv = q2.rel;
    }

    /**
     * Create a new instance that intersects all points.
     *
     * @param points Any number of collinear points, with two being different.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V2D_LineSegmentDouble(double epsilon, V2D_PointDouble... points) {
        super(points[0].offset);
        V2D_PointDouble p0 = points[0];
        V2D_PointDouble p1 = points[1];
        V2D_LineSegmentDouble ls = new V2D_LineSegmentDouble(p0, p1);
        for (int i = 2; i < points.length; i++) {
            if (!ls.isIntersectedBy(points[i], epsilon)) {
                V2D_LineSegmentDouble l2 = new V2D_LineSegmentDouble(ls.getP(), points[i]);
                V2D_PointDouble lq = ls.getQ();
                if (l2.isIntersectedBy(lq, epsilon)) {
                    ls = l2;
                } else {
                    ls = new V2D_LineSegmentDouble(ls.getQ(), points[i]);
                }
            }
        }
        this.l = ls.l;
        this.qv = ls.qv;
    }

    /**
     * @return {@link #l} pv with {@link #l} offset applied.
     */
    public V2D_PointDouble getP() {
        return l.getP();
    }

    /**
     * @return {@link #qv} with {@link #offset} applied.
     */
    public V2D_PointDouble getQ() {
        if (q == null) {
            q = new V2D_PointDouble(offset, qv);
        }
        return q;
    }

    /**
     * Translate (move relative to the origin).
     *
     */
    @Override
    public void translate(V2D_VectorDouble v) {
        super.translate(v);
        l.translate(v);
        if (q != null) {
            q.translate(v);
        }
        if (pl != null) {
            pl.translate(v);
        }
        if (ql != null) {
            ql.translate(v);
        }
    }

    @Override
    public V2D_PointDouble[] getPoints() {
        V2D_PointDouble[] r = new V2D_PointDouble[2];
        r[0] = getP();
        r[1] = getQ();
        return r;
    }

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     */
    public V2D_LineSegmentDouble(V2D_LineDouble l) {
        super();
        this.l = new V2D_LineDouble(l);
    }

    @Override
    public String toString() {
        //return toString("");
        return toStringSimple("");
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of this.
     */
    public String toString(String pad) {
        return this.getClass().getSimpleName() + "\n"
                + pad + "(\n"
                + toStringFields(pad + " ") + "\n"
                + pad + ")";
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of this.
     */
    public String toStringSimple(String pad) {
        return this.getClass().getSimpleName() + "\n"
                + pad + "(\n"
                + toStringFieldsSimple(pad + " ") + "\n"
                + pad + ")";
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    @Override
    protected String toStringFields(String pad) {
        String r = super.toStringFields(pad) + "\n"
                + pad + ",\n";
        r += pad + "l=" + l.toStringFields(pad) + "\n"
                + pad + ",\n"
                + pad + "q=" + qv.toStringFields(pad);
//        if (l.qv == null) {
//            r += pad + "pv=" + l.getP().toString(pad) + "\n"
//                    + pad + ",\n"
//                    + pad + "qv=null" + "\n"
//                    + pad + ",\n"
//                    + pad + "v=" + l.v.toString(pad);
//        } else {
//            if (l.v == null) {
//                r += pad + "pv=" + l.getP().toString(pad) + "\n"
//                        + pad + ",\n"
//                        + pad + "qv=" + l.qv.toString(pad) + "\n"
//                        + pad + ",\n"
//                        + pad + "v=null";
//            } else {
//                r += pad + "pv=" + l.getP().toString(pad) + "\n"
//                        + pad + ",\n"
//                        + pad + "qv=" + l.qv.toString(pad) + "\n"
//                        + pad + ",\n"
//                        + pad + "v=" + l.v.toString(pad);
//            }
//        }
        return r;
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    @Override
    protected String toStringFieldsSimple(String pad) {
        String r = super.toStringFieldsSimple(pad) + ",\n";
        r += pad + "l=" + l.toStringFieldsSimple(pad) + ",\n"
                + pad + "q=" + qv.toStringFieldsSimple("");
//        if (l.qv == null) {
//            r += pad + "pv=" + l.getP().toStringSimple("") + ",\n"
//                    + pad + "qv=null" + ",\n"
//                    + pad + "v=" + l.v.toStringSimple(pad);
//        } else {
//            if (l.v == null) {
//                r += pad + "pv=" + l.getP().toStringSimple(pad) + ",\n"
//                        + pad + "qv=" + l.qv.toStringSimple(pad) + ",\n"
//                        + pad + "v=null";
//            } else {
//                r += pad + "pv=" + l.getP().toStringSimple(pad) + ",\n"
//                        + pad + "qv=" + l.qv.toStringSimple(pad) + ",\n"
//                        + pad + "v=" + l.v.toStringSimple(pad);
//            }
//        }
        return r;
    }

    /**
     * @param l The line segment to test if it is the same as {@code this}.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code l} is the same as {@code this}.
     */
    public boolean equals(double epsilon, V2D_LineSegmentDouble l) {
        if (equalsIgnoreDirection(epsilon, l)) {
            return this.l.getP().equals(epsilon, l.getP());
        }
        return false;
    }

    /**
     * @param l The line segment to test if it is equal to {@code this}.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} is equal to {@code l}. This ignores
     * the order of the point of {@link #l} and {@link #qv}.
     */
    public boolean equalsIgnoreDirection(double epsilon, 
            V2D_LineSegmentDouble l) {
//        if (l == null) {
//            return false;
//        }
        if (this.l.equals(epsilon, l.l)) {
            return isIntersectedBy(l.getQ(), epsilon)
                    && l.isIntersectedBy(getQ(), epsilon);
        }
        return false;
    }

    /**
     * @return The length of {@code this}.
     */
    public double getLength() {
        return Math.sqrt(getLength2());
    }

    /**
     * @return The length of {@code this} squared.
     */
    public double getLength2() {
        return getP().getDistanceSquared(getQ());
    }

    /**
     * @return {@code new V2D_Envelope(start, end)}
     */
    @Override
    public V2D_EnvelopeDouble getEnvelope() {
        if (en == null) {
            en = new V2D_EnvelopeDouble(getP(), getQ());
        }
        return en;
    }
    
    /**
     * @param pt A point to test for intersection.
     * @return {@code true} if {@code this} is intersected by {@code pv}.
     */
    public boolean isIntersectedBy(V2D_PointDouble pt) {
        boolean ei = getEnvelope().isIntersectedBy(pt.getEnvelope());
        if (ei) {
            if (l.isIntersectedBy(pt)) {
                V2D_PointDouble tp = getP();
                double a = pt.getDistance(tp);
                if (a == 0d) {
                    return true;
                }
                V2D_PointDouble tq = getQ();
                double b = pt.getDistance(tq);
                if (b == 0d) {
                    return true;
                }
                double d = tp.getDistance(tq);
                double apb = a + b;
                return apb == d;
            }
        }
        return false;
    }

    /**
     * @param pt A point to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if {@code this} is intersected by {@code pv}.
     */
    public boolean isIntersectedBy(V2D_PointDouble pt, double epsilon) {
        boolean ei = getEnvelope().isIntersectedBy(pt.getEnvelope(), epsilon);
        if (ei) {
            if (l.isIntersectedBy(epsilon, pt)) {
                V2D_PointDouble tp = getP();
                double a = pt.getDistance(tp);
                if (a == 0d) {
                    return true;
                }
                V2D_PointDouble tq = getQ();
                double b = pt.getDistance(tq);
                if (b == 0d) {
                    return true;
                }
                double d = tp.getDistance(tq);
                double apb = a + b;
                return Math_Double.equals(apb, d, epsilon);
//                if (apb <= d) {
//                    return true;
//                }
            }
        }
        return false;
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}. If they overlap in a line return the part that
     * overlaps (the order of points is not defined). If they intersect at a
     * point, the point is returned. {@code null} is returned if the two line
     * segments do not intersect.
     *
     * @param l The line to get intersection with this.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V2D_FiniteGeometryDouble getIntersection(V2D_LineDouble l,
            double epsilon) {
        // Check if infinite lines intersect.
        V2D_GeometryDouble i = this.l.getIntersection(l, epsilon);
        if (i == null) {
            // There is no intersection.
            return null;
        }
        /**
         * If infinite lines intersects at a point, then check this point is on
         * this.
         */
        if (i instanceof V2D_PointDouble v3D_Point) {
            //if (isIntersectedBy(v3D_Point, epsilon)) {
            if (isBetween(v3D_Point, epsilon)) {
                return v3D_Point;
            } else {
                return null;
            }
        } else if (i instanceof V2D_LineDouble) {
            // If the lines are the same, then return this. 
            return this;
        } else {
            // There is no intersection.
            return null;
        }
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}. If they overlap in a line return the part that
     * overlaps (the order of points is not defined). If they intersect at a
     * point, the point is returned. {@code null} is returned if the two line
     * segments do not intersect.
     *
     * @param ls The line to get intersection with this.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V2D_FiniteGeometryDouble getIntersection(V2D_LineSegmentDouble ls,
            double epsilon) {
        if (!getEnvelope().isIntersectedBy(ls.getEnvelope(), epsilon)) {
            return null;
        }
        // Get intersection of infinite lines. 
        V2D_GeometryDouble r = this.l.getIntersection(ls.l, epsilon);
        if (r == null) {
            return null;
        }
        if (r instanceof V2D_PointDouble rp) {
            return rp;
        }
        /**
         * Check the type of intersection. {@code
         *
         * A) p_q l.p_l.q
         *
         * 1)  p +----------+ q
         *              l.p +----------+ l.q
         *
         * 2)  p +----------+ q
         *         l.p +----------+ l.q
         *
         * 3)  p +------------------------+ q
         *         l.p +----------+ l.q
         *
         * 4)        p +----------+ q
         *     l.p +----------------------+ l.q
         *
         * 5)    p +----------+ q
         *     l.p +----------+ l.q
         *
         * 6)         p +----------+ q
         *     l.p +----------+ l.q
         *
         * 7)              p +----------+ q
         *     l.p +---------+ l.q
         *
         * B) q_p l.p_l.q
         *
         * 8)  q +----------+ p
         *              l.p +----------+ l.q
         *
         * 9)  q +----------+ p
         *         l.p +----------+ l.q
         *
         * 10) q +------------------------+ p
         *         l.p +----------+ l.q
         *
         * 11)       q +----------+ p
         *     l.p +----------------------+ l.q
         *
         * 12)       q +----------+ p
         *         l.p +----------+ l.q
         *
         * 13)       q +----------+ p
         *     l.p +----------+ l.q
         *
         * 14)              q +----------+ p
         *     l.p +----------+ l.q
         *
         * C) p_q l.q_l.p
         *
         * 15) p +----------+ q
         *              l.q +----------+ l.p
         *
         * 16) p +----------+ q
         *         l.q +----------+ l.p
         *
         * 17) p +------------------------+ q
         *         l.q +----------+ l.p
         *
         * 18)       p +----------+ q
         *     l.q +------------------------+ l.p
         *
         * 19)       p +----------+ q
         *         l.q +----------+ l.p
         *
         * 20)       p +----------+ q
         *     l.q +----------+ l.p
         *
         * 21)              p +----------+ q
         *     l.q +----------+ l.p
         *
         * D) q_p l.q_l.p
         *
         * 22) q +----------+ p
         *              l.q +----------+ l.p
         *
         * 23) q +----------+ p
         *         l.q +----------+ l.p
         *
         * 24) q +------------------------+ p
         *         l.q +----------+ l.p
         *
         * 25)       q +----------+ p
         *     l.q +------------------------+ l.p
         *
         * 26)       p +----------+ q
         *         l.q +----------+ l.p
         *
         * 27)       q +----------+ p
         *    l.q +---------+ l.p
         *
         * 28)            q +----------+ p
         *    l.q +---------+ l.p
         * }
         */
        V2D_PointDouble lp = ls.getP();
        V2D_PointDouble lq = ls.getQ();
        V2D_PointDouble tp = getP();
        V2D_PointDouble tq = getQ();
        if (isIntersectedBy(lp, epsilon)) {
            // Cases: 1, 2, 3, 5, 8, 9, 10, 12, 17, 19, 20, 21, 24, 26, 27, 28
            if (isIntersectedBy(lq, epsilon)) {
                // Cases: 3, 5, 10, 12, 17, 19, 24, 26
                return ls;
            } else {
                // Cases: 1, 2, 8, 9, 20, 21, 27, 28
                if (ls.isIntersectedBy(tp, epsilon)) {
                    // Cases: 8, 9, 20, 21
                    if (tp.equals(epsilon, lp)) {
                        // Cases: 8, 21
                        return tp;
                    } else {
                        // Cases: 9, 20
                        return V2D_LineSegmentDouble.getGeometry(lp, tp, epsilon);
                    }
                } else {
                    // Case: 1, 2, 27, 28
                    if (lp.equals(epsilon, tq)) {
                        // Case: 1, 28
                        return lp;
                    } else {
                        // Cases: 2, 27
                        return V2D_LineSegmentDouble.getGeometry(lp, tq, epsilon);
                    }
                }
            }
        } else {
            // Cases: 4, 6, 7, 11, 13, 14, 15, 16, 18, 22, 23, 25
            if (isIntersectedBy(lq, epsilon)) {
                // Cases: 6, 7, 13, 14, 15, 16, 22, 23
                if (ls.isIntersectedBy(tp, epsilon)) {
                    // Cases: 6, 7, 22, 23
                    if (ls.isIntersectedBy(tq, epsilon)) {
                            // Case: 23
                            return V2D_LineSegmentDouble.getGeometry(lq, tp, epsilon);
                    } else {
                        // Cases: 6, 7, 22, 
                        if (tp.equals(epsilon, lq)) {
                            // Cases: 7, 22
                            return tp;
                        } else {
                            // Case: 6
                            return V2D_LineSegmentDouble.getGeometry(tp, lq, epsilon);
                        }
                    }
                } else {
                    // Cases: 13, 14, 15, 16
                    if (tq.equals(epsilon, lq)) {
                        // Cases: 14, 15
                        return tq;
                    } else {
                        // Case: 13, 16
                        return V2D_LineSegmentDouble.getGeometry(tq, lq, epsilon);
                    }
                }
            } else {
                // Cases: 4, 11, 18, 25
                return this;
            }
        }
    }

    /**
     * If the distance from a point to the line is less than the distance of the
     * point from either end of the line and the distance from either end of the
     * line is greater than the length of the line then the distance is the
     * shortest of the distances from the point to the points at either end of
     * the line segment. In all other cases, the distance is the distance
     * between the point and the line.
     *
     * @param p A point for which the minimum distance from {@code this} is
     * returned.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance between this and {@code pv}.
     */
    public double getDistance(V2D_PointDouble p, double epsilon) {
        return Math.sqrt(getDistanceSquared(p, epsilon));
    }

    /**
     * If the distance from a point to the line is less than the distance of the
     * point from either end of the line and the distance from either end of the
     * line is greater than the length of the line then the distance is the
     * shortest of the distances from the point to the points at either end of
     * the line segment. In all other cases, the distance is the distance
     * between the point and the line.
     *
     * @param pt A point for which the minimum distance from {@code this} is
     * returned.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance between this and {@code pv}.
     */
    public double getDistanceSquared(V2D_PointDouble pt, double epsilon) {
        if (isIntersectedBy(pt, epsilon)) {
            return 0d;
        }
        V2D_PointDouble poi = l.getPointOfIntersection(pt, epsilon);
        if (isAligned(poi, epsilon)) {
            return poi.getDistanceSquared(pt);
        } else {
            return Math.min(pt.getDistanceSquared(getP()),
                    pt.getDistanceSquared(getQ()));
        }
    }

    /**
     * Calculates and returns if pt is in line with this. It is in line if it is
     * between the planes defined by the ends of the line segment with the
     * normal vector as the vector of the line.
     *
     * @param pt The point.
     * @return {@code true} If pt is in line with this.
     */
    public boolean isAligned(V2D_PointDouble pt, double epsilon) {
        if (getPL().isOnSameSide(pt, getQ(), epsilon)) {
            return getQL().isOnSameSide(pt, getP(), epsilon);
        }
        return false;
    }

    /**
     * Calculates and returns if l is in line with this. It is in line if both
     * end points of l are in line with this as according to
     * {@link #isAligned(uk.ac.leeds.ccg.v3d.geometry.d.V2D_PointDouble)}.
     *
     * @param l The line segment.
     * @return {@code true} If pt is in line with this.
     */
    public boolean isAligned(V2D_LineSegmentDouble l, double epsilon) {
        if (isAligned(l.getP(), epsilon)) {
            return isAligned(l.getQ(), epsilon);
        }
        return false;
    }

    /**
     * @param l The line segment to return the distance from.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The distance from {@code this} to {@code l} at the {@code oom}
     * precision.
     */
    public double getDistance(V2D_LineSegmentDouble l, double epsilon) {
        return Math.sqrt(getDistanceSquared(l, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance squared to {@code l}.
     */
    public double getDistanceSquared(V2D_LineSegmentDouble l, double epsilon) {
        if (getIntersection(l, epsilon) != null) {
            return 0d;
        }
        V2D_LineSegmentDouble loi = getLineOfIntersection(l, epsilon);
        if (loi == null) {
            /**
             * Lines are parallel.
             */
            return getDistanceSquared(l.getP(), epsilon);
        } else {
            return loi.getLength2();
        }
    }

    /**
     * Calculate and return the midpoint between pv and qv.
     *
     * @return the midpoint between pv and qv to the OOM precision.
     */
    public V2D_PointDouble getMidpoint() {
        V2D_VectorDouble pmpq = l.v.divide(2.0d);
        return new V2D_PointDouble(offset, l.pv.add(pmpq));
    }

    /**
     * For returning the other end of the line segment as a point.
     *
     * @param pt A point equal to either {@link #getP()} or the point of
     * {@link #l}.
     * @return The other point that is not equal to a.
     */
    public V2D_PointDouble getOtherPoint(V2D_PointDouble pt) {
        if (getP().equals(pt)) {
            return getQ();
        } else {
            return pt;
        }
    }

    /**
     * If pv and qv are equal then the point is returned otherwise the line
     * segment is returned
     *
     * @param p A point.
     * @param q Another possibly equal point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return either {@code pv} or {@code new V2D_LineSegment(pv, qv)}
     */
    public static V2D_FiniteGeometryDouble getGeometry(V2D_PointDouble p,
            V2D_PointDouble q, double epsilon) {
        if (p.equals(epsilon, q)) {
            return p;
        } else {
            return new V2D_LineSegmentDouble(p, q);
        }
    }

    /**
     * If pv, qv and r are equal then the point is returned otherwise a line
     * segment is returned where all the points are on the line segment.
     *
     * @param p A point possibly equal to qv or r, but certainly collinear.
     * @param q A point possibly equal to pv or r, but certainly collinear.
     * @param r A point possibly equal to pv or qv, but certainly collinear.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return either {@code pv} or {@code new V2D_LineSegment(pv, qv)}
     */
    public static V2D_FiniteGeometryDouble getGeometry(V2D_PointDouble p,
            V2D_PointDouble q, V2D_PointDouble r, double epsilon) {
        if (p.equals(epsilon, q)) {
            return getGeometry(epsilon, p, r);
        } else if (q.equals(epsilon, r)) {
            return getGeometry(epsilon, p, r);
        } else if (p.equals(epsilon, r)) {
            return getGeometry(epsilon, p, q);
        } else {
            V2D_LineSegmentDouble ls = new V2D_LineSegmentDouble(epsilon, p, q);
            if (ls.isIntersectedBy(r, epsilon)) {
                return ls;
            } else {
                ls = new V2D_LineSegmentDouble(epsilon, p, r);
                if (ls.isIntersectedBy(q, epsilon)) {
                    return ls;
                } else {
                    return new V2D_LineSegmentDouble(epsilon, q, r);
                }
            }
        }
    }

    /**
     * Calculates the shortest line segment which l and pt intersect with.
     *
     * @param l A line segment.
     * @param pt A point collinear with l.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The shortest line segment which l and pt intersect with.
     */
    public static V2D_LineSegmentDouble getGeometry(V2D_LineSegmentDouble l,
            V2D_PointDouble pt, double epsilon) {
        return (V2D_LineSegmentDouble) getGeometry(epsilon, l.getP(), l.getQ(), pt);
    }

    /**
     * Calculates the shortest line segment which all line segments intersect
     * with.
     *
     * @param ls Collinear line segments.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The shortest line segment which all line segments in ls intersect
     * with.
     */
    public static V2D_LineSegmentDouble getGeometry(double epsilon,
            V2D_LineSegmentDouble... ls) {
        switch (ls.length) {
            case 0 -> {
                return null;
            }
            case 1 -> {
                return ls[0];
            }
            default -> {
                V2D_LineSegmentDouble r = getGeometry(ls[0], ls[1].getP(), epsilon);
                r = getGeometry(r, ls[1].getQ(), epsilon);
                for (int i = 1; i < ls.length; i++) {
                    r = getGeometry(r, ls[i].getP(), epsilon);
                    r = getGeometry(r, ls[i].getQ(), epsilon);
                }
                return r;
            }

        }
    }

    /**
     * Calculate and return the smallest line segment intersected by all pts.
     *
     * @param pts Collinear points.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The smallest line segment intersected by all pts
     */
    public static V2D_FiniteGeometryDouble getGeometry(double epsilon, V2D_PointDouble... pts) {
        int length = pts.length;
        switch (length) {
            case 0 -> {
                return null;
            }
            case 1 -> {
                return pts[0];
            }
            case 2 -> {
                return getGeometry(pts[0], pts[1], epsilon);
            }
            case 3 -> {
                return getGeometry(pts[0], pts[1], pts[2], epsilon);
            }
            default -> {
                V2D_FiniteGeometryDouble g = getGeometry(epsilon, pts[0], pts[1], pts[2]);
                for (int i = 3; i < length; i++) {
                    if (g instanceof V2D_PointDouble gp) {
                        g = getGeometry(gp, pts[i], epsilon);
                    } else {
                        g = getGeometry((V2D_LineSegmentDouble) g, pts[i], epsilon);
                    }
                }
                return g;
            }

        }
    }

    /**
     * Get the line of intersection (the shortest line) between {@code this} and
     * {@code l}.
     *
     * @param l The line to get the line of intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The line of intersection between {@code this} and {@code l}.
     */
    public V2D_LineSegmentDouble getLineOfIntersection(V2D_LineDouble l,
            double epsilon) {
        if (getIntersection(l, epsilon) != null) {
            return null;
        }

        //V2D_LineSegmentDouble loi = this.l.getLineOfIntersection(l, epsilon);

        V2D_LineSegmentDouble loi = null;
        
        
        V2D_PointDouble tp = getP();
        V2D_PointDouble tq = getQ();
        if (loi == null) {
            double pd = l.getDistance(tp, epsilon);
            double qd = l.getDistance(tq, epsilon);
            if (pd > qd) {
                return new V2D_LineSegmentDouble(tq, l.getPointOfIntersection(tq, epsilon));
            } else {
                return new V2D_LineSegmentDouble(tp, l.getPointOfIntersection(tp, epsilon));
            }
        } else {
            V2D_PointDouble lsp = loi.getP();
            //V2D_Point lsq = loi.getQ();
            V2D_VectorDouble pv = l.v.rotate90();
            V2D_LineDouble plp = new V2D_LineDouble(tp, pv);
            V2D_LineDouble plq = new V2D_LineDouble(tq, pv);
            if (plp.isOnSameSide(lsp, tq, epsilon)) {
                if (plq.isOnSameSide(lsp, tp, epsilon)) {
                    /**
                     * The line of intersection connects in the line segment, so
                     * return it.
                     */
                    return loi;
                } else {
                    return new V2D_LineSegmentDouble(tq, lsp);
                }
            } else {
                return new V2D_LineSegmentDouble(tp, lsp);
            }
        }
    }

    /**
     * Get the line of intersection (the shortest line) between {@code this} and
     * {@code l}.
     *
     * @param ls The line segment to get the line of intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The line of intersection between {@code this} and {@code l}.
     */
    public V2D_LineSegmentDouble getLineOfIntersection(V2D_LineSegmentDouble ls,
            double epsilon) {
        V2D_FiniteGeometryDouble ilsl = getIntersection(ls, epsilon);
        if (ilsl == null) {
            V2D_PointDouble lsp = ls.getP();
            V2D_PointDouble lsq = ls.getQ();
            V2D_PointDouble tp = getP();
            V2D_PointDouble tq = getQ();
            // Get the line of intersection between this and ls.l
            V2D_LineSegmentDouble tloi = getLineOfIntersection(ls.l, epsilon);
            V2D_LineSegmentDouble lsloi = ls.getLineOfIntersection(l, epsilon);
            if (tloi == null) {
                V2D_PointDouble tip;
                V2D_PointDouble lsloiq = lsloi.getQ();
                // Is the intersection point on this within the line segment?
                // Can use isAligned to do this more clearly?
                V2D_VectorDouble pv = l.v.rotate90();
                V2D_LineDouble tppl = new V2D_LineDouble(tp, pv);
                V2D_LineDouble tqpl = new V2D_LineDouble(tq, pv);
                if (tppl.isOnSameSide(lsloiq, tq, epsilon)) {
                    if (tqpl.isOnSameSide(lsloiq, tp, epsilon)) {
                        /**
                         * The line of intersection connects in this, so lsloiq
                         * is one of the points wanted.
                         */
                        return lsloi;
                    } else {
                        // tq is closest.
                        tip = tq;
                    }
                } else {
                    // tp is closest.
                    tip = tp;
                }
                return new V2D_LineSegmentDouble(tip, lsloiq);
            } else {
                V2D_PointDouble tloip = tloi.getP(); // This is the end of the line segment on this.
                V2D_PointDouble tloiq = tloi.getQ();
                if (lsloi == null) {
                    V2D_PointDouble lsip;
                    // Is the intersection point on ls within the line segment?
                    // Can use isAligned to do this more clearly?
                    V2D_VectorDouble pv = ls.l.v.rotate90();
                    V2D_LineDouble lsppl = new V2D_LineDouble(lsp,pv);
                    if (lsppl.isOnSameSide(tloiq, lsq, epsilon)) {
                        V2D_LineDouble lsqpl = new V2D_LineDouble(lsq, pv);
                        if (lsqpl.isOnSameSide(tloiq, lsp, epsilon)) {
                            /**
                             * The line of intersection connects in this, so
                             * lsloiq is one of the points wanted.
                             */
                            lsip = tloiq;
                        } else {
                            // lsq is closest.
                            lsip = lsq;
                        }
                    } else {
                        // lsp is closest.
                        lsip = lsp;
                    }
                    return new V2D_LineSegmentDouble(tloip, lsip);
                    //return new V2D_LineSegment(tq, lsip);
                    //return new V2D_LineSegment(tp, lsip);
                    //return new V2D_LineSegment(tloiq, getNearestPoint(this, tloiq));
                } else {
                    // tloip is on
                    if (isBetween(tloip, epsilon)) {
                        return new V2D_LineSegmentDouble(tloip, getNearestPoint(ls, tloip));
                    } else {
                        return new V2D_LineSegmentDouble(
                                getNearestPoint(this, tloip),
                                getNearestPoint(ls, tloip));
                    }
                }
            }
        } else {
            return null;
        }
    }

    /**
     * Useful for intersection tests.
     *
     * @return The line at l.getP() orthogonal to l.v
     */
    public V2D_LineDouble getPL() {
        if (pl == null) {
            V2D_PointDouble pt = l.getP();
            pl = new V2D_LineDouble(pt, l.v.rotate90());
        }
        return pl;
    }

    /**
     * Useful for intersection tests.
     *
     * @return The line at getQ() orthogonal to l.v
     */
    public V2D_LineDouble getQL() {
        if (ql == null) {
            V2D_PointDouble pt = getQ();
            ql = new V2D_LineDouble(pt, l.v.rotate90());
        }
        return ql;
    }

    /**
     * Useful for intersection tests.
     *
     * @param pt The point to test if it is between {@link #qv} and the point of
     * {@link #l}.
     * @return {@code true} iff pt lies between the planes at the end of the
     * line segment.
     */
    public boolean isBetween(V2D_PointDouble pt, double epsilon) {
        if (getPL().isOnSameSide(pt, getQ(), epsilon)) {
            if (getQL().isOnSameSide(pt, getP(), epsilon)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param l A line segment.
     * @param pt Is on the line of {@code l}, but not on {@code l}.
     * @return The nearest point on {@code l} to {@code pv}.
     */
    protected static V2D_PointDouble getNearestPoint(V2D_LineSegmentDouble l,
            V2D_PointDouble pt) {
        V2D_PointDouble lp = l.getP();
        V2D_PointDouble lq = l.getQ();
        double dlpp = lp.getDistanceSquared(pt);
        double dlqp = lq.getDistanceSquared(pt);
        if (dlpp < dlqp) {
            return lp;
        } else {
            return lq;
        }
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param ld A line.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance to {@code l}.
     */
    public double getDistance(V2D_LineDouble ld, double epsilon) {
        double pd = ld.getDistance(l.p, epsilon);
        double qd = ld.getDistance(l.p, epsilon);
        return Math.min(pd, qd);
    }

    @Override
    public V2D_LineSegmentDouble rotate(V2D_PointDouble pt, double theta, 
            double epsilon) {
        return new V2D_LineSegmentDouble(
                getP().rotate(pt, theta, epsilon),
                getQ().rotate(pt, theta, epsilon));
    }

//    /**
//     * Clips this using pl and returns the part that is on the same side as pt.
//     *
//     * @param pl The plane that clips.
//     * @param pt A point that is used to return the side of the clipped line
//     * segment.
//     * @param epsilon The tolerance within which two vectors are regarded as
//     * equal.
//     * @return null, the whole or a part of this.
//     */
//    public V2D_FiniteGeometryDouble clip(V2D_LineDouble pl, V2D_PointDouble pt,
//            double epsilon) {
//        V2D_FiniteGeometryDouble i = pl.getIntersection(this, epsilon);
//        V2D_PointDouble tp = getP();
//        if (i == null) {
//            if (pl.isOnSameSide(tp, pt, epsilon)) {
//                return this;
//            } else {
//                return null;
//            }
//        } else if (i instanceof V2D_PointDouble ip) {
//            if (pl.isOnSameSide(tp, pt, epsilon)) {
//                return V2D_LineSegmentDouble.getGeometry(ip, tp, epsilon);
//            } else {
//                V2D_PointDouble tq = getQ();
//                return V2D_LineSegmentDouble.getGeometry(ip, tq, epsilon);
//            }
//        } else {
//            return this;
//        }
//    }

    @Override
    public boolean isIntersectedBy(V2D_EnvelopeDouble aabb, double epsilon) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
