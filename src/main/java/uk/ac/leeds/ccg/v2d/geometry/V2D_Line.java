/*
 * Copyright 2020-2025 Andy Turner, University of Leeds.
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
package uk.ac.leeds.ccg.v2d.geometry;

import ch.obermuhlner.math.big.BigRational;
import java.math.RoundingMode;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_BR;
import uk.ac.leeds.ccg.v2d.core.V2D_Environment;

/**
 * 2D representation of an infinite length line. The line passes through the
 * point {@link #pv} with vector {@link #v}.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_Line extends V2D_Geometry {

    private static final long serialVersionUID = 1L;
    
    /**
     * The x axis.
     */
    public static final V2D_Line X_AXIS = new V2D_Line(null,
            V2D_Vector.ZERO, V2D_Vector.I);

    /**
     * The y axis.
     */
    public static final V2D_Line Y_AXIS = new V2D_Line(null,
            V2D_Vector.ZERO, V2D_Vector.J);
    
    /**
     * If this line is defined by a vector, then the calculation of {@link #q}
     * may be imprecise. If this line is defined by points, then {@link #v} may
     * have been imprecisely calculated.
     */
    public boolean isDefinedByVector;

    /**
     * Used to define {@link #p}.
     */
    protected V2D_Vector pv;

    /**
     * Used to store a point on the line as derived from {@link #offset} and
     * {@link #pv}.
     */
    protected V2D_Point p;

    /**
     * Another point on the line that is derived from {@link #offset},
     * {@link #pv} and {@link v}.
     */
    protected V2D_Point q;

    /**
     * The Order of Magnitude for the calculation of qv.
     */
    int oom;

    /**
     * The RoundingMode for the calculation of qv.
     */
    RoundingMode rm;

    /**
     * The vector that defines the line. This will not change under translation,
     * but will change under rotation.
     */
    public V2D_Vector v;

    /**
     * @param l Used to initialise this.
     */
    public V2D_Line(V2D_Line l) {
        super(l.env, new V2D_Vector(l.offset));
        this.pv = new V2D_Vector(l.pv);
        if (l.p != null) {
            this.p = new V2D_Point(l.p);
        }
        if (l.q != null) {
            this.q = new V2D_Point(l.q);
        }
        this.oom = l.oom;
        this.rm = l.rm;
        this.v = new V2D_Vector(l.v);
        this.isDefinedByVector = l.isDefinedByVector;
    }

    /**
     * @param l Used to initialise this.
     */
    public V2D_Line(V2D_LineSegment l) {
        this(l.l);
    }

    /**
     * {@code pv} should not be equal to {@code qv}. {@link #offset} is set to
     * {@link V2D_Vector#ZERO}.
     *
     * @param env What {@link #env} is set to.
     * @param p What {@link #pv} is set to.
     * @param q Another point on the line from which {@link #v} is derived.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V2D_Line(V2D_Environment env, V2D_Vector p, V2D_Vector q, int oom, RoundingMode rm) {
        this(env, V2D_Vector.ZERO, p, q, oom, rm);
    }

    /**
     * {@code pv} should not be equal to {@code qv}.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param pv What {@link #pv} is cloned from.
     * @param qv Used to calculate {@link q} and {@link #v} (which is calculated
     * by taking the difference between pv and qv.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V2D_Line(V2D_Environment env, V2D_Vector offset, V2D_Vector pv,
            V2D_Vector qv, int oom, RoundingMode rm) {
        super(env, offset);
        this.pv = new V2D_Vector(pv);
        if (pv.equals(qv)) {
            throw new RuntimeException("" + pv + " and " + qv + " are the same"
                    + " so do not define a line.");
        }
        q = new V2D_Point(env, offset, qv);
        v = qv.subtract(pv, oom, rm);
        this.oom = oom;
        this.rm = rm;
        //isDefinedByVector = false;
        isDefinedByVector = true;
    }

    /**
     * {@code v} should not be the zero vector {@code <0,0,0>}. {@link #offset}
     * is set to {@link V2D_Vector#ZERO}.
     *
     * @param env What {@link #env} is set to.
     * @param p What {@link #pv} is cloned from.
     * @param v The vector defining the line from {@link #pv}. What {@link #v}
     * is cloned from.
     */
    public V2D_Line(V2D_Environment env, V2D_Vector p, V2D_Vector v) {
        this(env, V2D_Vector.ZERO, p, v);
    }

    /**
     * Checks to ensure v is not the zero vector {@code <0,0,0>}.
     *
     * @param p What {@link #pv} is set to.
     * @param v The vector defining the line from {@link #pv}.
     */
    public V2D_Line(V2D_Point p, V2D_Vector v) {
        this(p.env, p.offset, p.rel, v);
    }

    /**
     * Checks to ensure v is not the zero vector {@code <0,0,0>}.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param p Used to initialise {@link #pv}.
     * @param v Used to initialise {@link #v}.
     * @throws RuntimeException if {@code v.isZero()}.
     */
    public V2D_Line(V2D_Environment env, V2D_Vector offset, V2D_Vector p, V2D_Vector v) {
        super(env, offset);
        if (v.isZero()) {
            throw new RuntimeException("Vector " + v + " is the zero vector "
                    + "which cannot be used to define a line.");
        }
        this.pv = new V2D_Vector(p);
        this.v = new V2D_Vector(v);
        isDefinedByVector = true;
    }

    /**
     * Create a new instance.
     *
     * @param p What {@link #pv} is cloned from.
     * @param q What {@link #v} is derived from.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V2D_Line(V2D_Point p, V2D_Point q, int oom, RoundingMode rm) {
        super(p.env, new V2D_Vector(p.offset));
        V2D_Point q2 = new V2D_Point(q);
        q2.setOffset(p.offset, oom, rm);
        if (p.rel.equals(q2.rel)) {
            throw new RuntimeException("Points " + p + " and " + q
                    + " are the same and so do not define a line.");
        }
        this.pv = new V2D_Vector(p.rel);
        this.v = q2.rel.subtract(this.pv, oom, rm);
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
        r += pad + "p=" + getP().toString(pad) + "\n"
                + pad + ",\n"
                + pad + "v=" + v.toString(pad);
//        if (qv == null) {
//            r += pad + "pv=" + getP().toString(pad) + "\n"
//                    + pad + ",\n"
//                    + pad + "qv=null" + "\n"
//                    + pad + ",\n"
//                    + pad + "v=" + v.toString(pad);
//        } else {
//            if (v == null) {
//                r += pad + "pv=" + getP().toString(pad) + "\n"
//                        + pad + ",\n"
//                        + pad + "qv=" + qv.toString(pad) + "\n"
//                        + pad + ",\n"
//                        + pad + "v=null";
//            } else {
//                r += pad + "pv=" + getP().toString(pad) + "\n"
//                        + pad + ",\n"
//                        + pad + "qv=" + qv.toString(pad) + "\n"
//                        + pad + ",\n"
//                        + pad + "v=" + v.toString(pad);
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
        r += pad + "p=" + getP().toStringSimple("") + ",\n"
                + pad + "v=" + v.toStringSimple(pad);
//        if (qv == null) {
//            r += pad + "pv=" + getP().toStringSimple("") + ",\n"
//                    + pad + "qv=null" + ",\n"
//                    + pad + "v=" + v.toStringSimple(pad);
//        } else {
//            if (v == null) {
//                r += pad + "pv=" + getP().toStringSimple(pad) + ",\n"
//                        + pad + "qv=" + qv.toStringSimple(pad) + ",\n"
//                        + pad + "v=null";
//            } else {
//                r += pad + "pv=" + getP().toStringSimple(pad) + ",\n"
//                        + pad + "qv=" + qv.toStringSimple(pad) + ",\n"
//                        + pad + "v=" + v.toStringSimple(pad);
//            }
//        }
        return r;
    }

    /**
     * @param l The line to test if it is the same as {@code this}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@code l} is the same as {@code this}.
     */
    public boolean equals(V2D_Line l, int oom, RoundingMode rm) {
//        boolean t1 = intersects(l.getP(oom), oom);
//        boolean t2 = intersects(l.getQ(oom), oom);
//        boolean t3 = l.intersects(getP(oom), oom);
//        boolean t4 = l.intersects(getQ(oom),oom);
//        boolean t5 = getV(oom).isScalarMultiple(l.getV(oom), oom);
//        return intersects(l.getP(), oom, rm)
//                && intersects(l.getQ(oom, rm), oom, rm);
        //if (v.isScalarMultiple(l.v, oom, rm)) {
            if (l.intersects(getP(), oom, rm)) {
                if (l.intersects(getQ(oom, rm), oom, rm)) {
                    return true;
                }
            }
        //}
        return false;
    }

//    /**
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode for any rounding.
//     * @return {@link #pv} with {@link #offset} applied.
//     */
//    public V2D_Vector getPAsVector(int oom, RoundingMode rm) {
//        return pv.add(offset, oom, rm);
//    } 
    /**
     * The point of the line as calculated from {@link #pv} and {@link #offset}.
     *
     * @return {@link #pv} with {@link #offset} applied.
     */
    public V2D_Point getP() {
        if (p == null) {
            p = new V2D_Point(env, offset, pv);
        }
        return p;
    }

    /**
     * A point on the line as calculated from {@link #pv}, {@link #offset} and
     * {@link #v}.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return Another point on the line derived from {@link #v}.
     */
    public V2D_Point getQ(int oom, RoundingMode rm) {
        if (q == null) {
            initQ(oom, rm);
        } else {
            if (oom < this.oom) {
                initQ(oom, rm);
            } else if (oom == this.oom) {
                if (!rm.equals(this.rm)) {
                    initQ(oom, rm);
                }
            }
        }
        return q;
    }

    private void initQ(int oom, RoundingMode rm) {
        q = new V2D_Point(env, offset, pv.add(v, oom, rm));
        this.oom = oom;
        this.rm = rm;
    }
    
    /**
     * https://en.wikipedia.org/wiki/Line%E2%80%93line_intersection
     * 
     * @param l A line to test for intersection.
     * @param den getIntersectionDenominator(getP().getX(oom, rm), 
     * getQ(oom, rm).getX(oom, rm), l.getP().getX(oom, rm),
     * l.getQ(oom, rm).getX(oom, rm), getP().getY(oom, rm), 
     * getQ(oom, rm).getY(oom, rm), l.getP().getY(oom, rm),
     * l.getQ(oom, rm).getY(oom, rm))
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if lines intersect.
     */
    public boolean intersects(V2D_Line l, BigRational den, int oom, 
            RoundingMode rm) {
        if (den.compareTo(BigRational.ZERO) == 0) {
            // Lines are parallel or coincident.
            return equals(l, oom, rm);
        }
        return true;
    }
    
    /**
     * @param pt A point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if pv is on the line.
     */
    public boolean intersects(V2D_Point pt, int oom, RoundingMode rm) {
        p = getP();
        q = getQ(oom, rm);
        if (p.equals(pt, oom, rm) || q.equals(pt, oom, rm)) {
            return true;
        }
        V2D_Vector dpt = new V2D_Vector(
                    pt.getX(oom, rm).subtract(p.getX(oom, rm)),
                    pt.getY(oom, rm).subtract(p.getY(oom, rm)));
        return dpt.isScalarMultiple(v, oom, rm);
//        int oomN2 = oom - 2;
//        V2D_Point tp = getP();
//        V2D_Point tq = getQ(oom, rm);
//        if (tp.equals(pt, oom, rm)) {
//            return true;
//        }
//        if (tq.equals(pt, oom, rm)) {
//            return true;
//        }
//        V2D_Vector cp;
//        if (tp.equals(V2D_Point.ORIGIN, oom, rm)) {
//            V2D_Vector ppt = new V2D_Vector(
//                    pt.getX(oomN2, rm).subtract(tq.getX(oomN2, rm)),
//                    pt.getY(oomN2, rm).subtract(tq.getY(oomN2, rm)),
//                    pt.getZ(oomN2, rm).subtract(tq.getZ(oomN2, rm)));
//            cp = v.getCrossProduct(ppt, oomN2, rm);
//        } else {
//            V2D_Vector ppt = new V2D_Vector(
//                    pt.getX(oomN2, rm).subtract(tp.getX(oomN2, rm)),
//                    pt.getY(oomN2, rm).subtract(tp.getY(oomN2, rm)),
//                    pt.getZ(oomN2, rm).subtract(tp.getZ(oomN2, rm)));
//            cp = v.getCrossProduct(ppt, oomN2, rm);
//        }
//        return cp.getDX(oom, rm).isZero() && cp.getDY(oom, rm).isZero()
//                && cp.getDZ(oom, rm).isZero();
//        return false;
    }
    
    /**
     * https://en.wikipedia.org/wiki/Line%E2%80%93line_intersection
     *
     * @param l A line to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if lines intersect.
     */
    public boolean intersects(V2D_Line l, int oom, RoundingMode rm) {
        return intersects(l, getIntersectionDenominator(p.getX(oom, rm),
                        getQ(oom, rm).getX(oom, rm), l.p.getX(oom, rm),
                        l.getQ(oom, rm).getX(oom, rm), p.getY(oom, rm), 
                        q.getY(oom, rm), l.p.getY(oom, rm), l.q.getY(oom, rm)),
                oom, rm);
    }

    /**
     * https://en.wikipedia.org/wiki/Line%E2%80%93line_intersection
     *
     * @param l A line to test for intersection.
     * @param x1 getP().getX()
     * @param x2 getQ().getX()
     * @param x3 l.getP().getX()
     * @param x4 l.getQ().getX()
     * @param y1 p.getY()
     * @param y2 q.getY()
     * @param y3 l.p.getY()
     * @param y4 l.q.getY()
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if lines intersect.
     */
    public boolean intersects(V2D_Line l, BigRational x1,
            BigRational x2, BigRational x3, BigRational x4, BigRational y1,
            BigRational y2, BigRational y3, BigRational y4,
            int oom, RoundingMode rm) {
        return intersects(l, V2D_Line.getIntersectionDenominator(x1, x2, 
                x3, x4, y1, y2, y3, y4), oom, rm);
    }

    /**
     * @param l The line to test if it is parallel to this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} If this and {@code l} are parallel.
     */
    public boolean isParallel(V2D_Line l, int oom, RoundingMode rm) {
        //oom -= 2;
        //return getV(oom, rm).isScalarMultiple(l.getV(oom, rm), oom, rm);
        return v.isScalarMultiple(l.v, oom, rm);
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}.
     *
     * @param l The line to get the intersection with {@code this}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V2D_Geometry getIntersection(V2D_Line l, int oom, RoundingMode rm) {
        BigRational x1 = getP().getX(oom, rm);
        BigRational x2 = getQ(oom, rm).getX(oom, rm);
        BigRational x3 = l.getP().getX(oom, rm);
        BigRational x4 = l.getQ(oom, rm).getX(oom, rm);
        BigRational y1 = p.getY(oom, rm);
        BigRational y2 = q.getY(oom, rm);
        BigRational y3 = l.p.getY(oom, rm);
        BigRational y4 = l.q.getY(oom, rm);
        BigRational den = getIntersectionDenominator(x1, x2, x3, x4, y1, y2, y3, y4);
        return getIntersection(l, den, x1, x2, x3, x4, y1, y2, y3, y4, oom, rm);
    }
    
    /**
     * https://en.wikipedia.org/wiki/Line%E2%80%93line_intersection
     * @param l Line to intersect with.
     * @param den getIntersectionDenominator(x1, x2, x3, x4, y1, y2, y3, y4)
     * @param x1 getP().getX(oom, rm)
     * @param x2 getQ(oom, rm).getX(oom, rm)
     * @param x3 l.getP().getX(oom, rm)
     * @param x4 l.getQ(oom, rm).getX(oom, rm)
     * @param y1 p.getY(oom, rm)
     * @param y2 q.getY(oom, rm)
     * @param y3 l.p.getY(oom, rm)
     * @param y4 l.q.getY(oom, rm)
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The geometry or null if there is no intersection.
     */
    public V2D_Geometry getIntersection(V2D_Line l, BigRational den, 
            BigRational x1, BigRational x2, BigRational x3, BigRational x4, 
            BigRational y1, BigRational y2, BigRational y3, BigRational y4, 
            int oom, RoundingMode rm) {
        if (intersects(l, den, oom, rm)) {
            // Check for coincident lines
            if (equals(l, oom -1, rm)) {
                return l;
            }
            BigRational x1y2sy1x2 = ((x1.multiply(y2)).subtract(y1.multiply(x2)));
            BigRational x3y4sy3x4 = ((x3.multiply(y4)).subtract(y3.multiply(x4)));
            BigRational numx = (x1y2sy1x2.multiply(x3.subtract(x4))).subtract(
                    (x1.subtract(x2)).multiply(x3y4sy3x4));
            BigRational numy = (x1y2sy1x2.multiply(y3.subtract(y4))).subtract(
                    (y1.subtract(y2)).multiply(x3y4sy3x4));
            return new V2D_Point(env, numx.divide(den), numy.divide(den));            
        }
        return null;
    }
    
    /**
     * @param x1 p.getX()
     * @param x2 q.getX()
     * @param x3 l.p.getX()
     * @param x4 l.q.getX()
     * @param y1 p.getY()
     * @param y2 q.getY()
     * @param y3 l.p.getY()
     * @param y4 l.q.getY()
     * @return ((x1 - x2) * (y3 - y4)) - ((y1 - y2) * (x3 - x4))
     */
    public static BigRational getIntersectionDenominator(BigRational x1,  
            BigRational x2, BigRational x3, BigRational x4, BigRational y1, 
            BigRational y2, BigRational y3, BigRational y4) {
        return ((x1.subtract(x2)).multiply(y3.subtract(y4))).subtract(
                (y1.subtract(y2)).multiply(x3.subtract(x4)));
    }

    /**
     * @param pt A point for which the shortest line segment to this is
     * returned.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The line segment having the shortest distance between {@code pt}
     * and {@code this}.
     */
    public V2D_FiniteGeometry getLineOfIntersection(V2D_Point pt, int oom,
            RoundingMode rm) {
        if (intersects(pt, oom, rm)) {
            return pt;
        }
        return new V2D_LineSegment(pt, getPointOfIntersection(pt, true, oom, rm), oom, rm);
        //return new V2D_LineSegment(pt.getVector(oom), getPointOfIntersection(pt, oom).getVector(oom), oom);
    }
    /**
     * @param pt The point projected onto this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A point on {@code this} which is the shortest distance from
     * {@code pt}.
     */
    public V2D_Point getPointOfIntersection(V2D_Point pt, int oom,
            RoundingMode rm) {
        if (intersects(pt, oom, rm)) {
            return pt;
        }
        return getPointOfIntersection(pt, true, oom, rm);
    }

    /**
     * @param pt The point projected onto this.
     * @param noint True iff there is no intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A point on {@code this} which is the shortest distance from
     * {@code pt}.
     */
    public V2D_Point getPointOfIntersection(V2D_Point pt, boolean noint, int oom,
            RoundingMode rm) {
        V2D_Line l = new V2D_Line(pt, v.rotate90());
        return (V2D_Point) getIntersection(l, oom, rm);
    }

    /**
     * Calculate and return the line of intersection (the shortest line) between
     * this and l. If this and l intersect then return null. If this and l are
     * parallel, then return null. If the calculated ends of the line of
     * intersection are the same, the return null (this may happen due to
     * imprecision). Adapted in part from:
     * http://paulbourke.net/geometry/pointlineplane/
     *
     * @param l The line to get the line of intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The line of intersection between {@code this} and {@code l}. The
     * point pv is a point on or near this, and the point qv is a point on or
     * near l. Whether the points are on or near is down to rounding error and
     * precision.
     */
    public V2D_LineSegment getLineOfIntersection(V2D_Line l, int oom, RoundingMode rm) {
        int oomn6 = oom - 6;
        if (isParallel(l, oom, rm)) {
            return null;
        }
        if (getIntersection(l, oom, rm) != null) {
            return null;
        }
        V2D_Point tp = getP();
        V2D_Point lp = l.getP();
        V2D_Vector A = new V2D_Vector(tp, lp, oomn6, rm);
        //V2D_Vector B = getV(oom, rm).reverse();
        V2D_Vector B = v.reverse();
        //V2D_Vector C = l.getV(oom, rm).reverse();
        V2D_Vector C = l.v.reverse();

        BigRational AdB = A.getDotProduct(B, oomn6, rm);
        BigRational AdC = A.getDotProduct(C, oomn6, rm);
        BigRational CdB = C.getDotProduct(B, oomn6, rm);
        BigRational BdB = B.getDotProduct(B, oomn6, rm);
        BigRational CdC = C.getDotProduct(C, oomn6, rm);

        BigRational ma = (AdC.multiply(CdB)).subtract(AdB.multiply(CdC))
                .divide((BdB.multiply(CdC)).subtract(CdB.multiply(CdB)));
        BigRational mb = ((ma.multiply(CdB)).add(AdC)).divide(CdC);

        //V2D_Point tpi = tp.translate(B.multiply(ma, oom), oom);
        //V2D_Vector tpi = tp.getVector(oom).add(B.multiply(ma, oom), oom);
        V2D_Vector tpi = tp.getVector(oom, rm).subtract(B.multiply(ma, oom, rm), oom, rm);

        //V2D_Point lpi = l.pv.translate(C.multiply(mb, oom), oom);
        //V2D_Point lpi = lp.translate(C.multiply(mb.negate(), oom), oom);
        //V2D_Vector lpi = lp.getVector(oom).add(C.multiply(mb.negate(), oom), oom);
        V2D_Vector lpi = lp.getVector(oom, rm).subtract(C.multiply(mb, oom, rm), oom, rm);

        //return new V2D_LineSegment(tpi, lpi, oom);
        //return new V2D_LineSegment(tpi.getVector(oom), lpi.getVector(oom), oom);
        //return new V2D_LineSegment(e, tpi, lpi);
        V2D_Point loip = new V2D_Point(env, tpi);
        V2D_Point loiq = new V2D_Point(env, lpi);
        if (loip.equals(loiq, oom, rm)) {
            return null;
        } else {
            return new V2D_LineSegment(loip, loiq, oom, rm);
        }
//        // p13
//        //V2D_Vector plp = new V2D_Vector(pv, lp, oom);
//        V2D_Vector plp = new V2D_Vector(tp, lp, oom);
//        // p43
//        //V2D_Vector lqlp = l.v.reverse();//new V2D_Vector(l.qv, l.pv);
//        V2D_Vector lqlp = l.v;//new V2D_Vector(l.qv, l.pv);
//        if (lqlp.getMagnitudeSquared().compareTo(BigRational.ZERO) == 0) {
//            return null;
//        }
//        // p21
//        //V2D_Vector qp = v.reverse();//new V2D_Vector(qv, pv);
//        V2D_Vector qp = v;//new V2D_Vector(qv, pv);
//        if (qp.getMagnitudeSquared().compareTo(BigRational.ZERO) == 0) {
//            return null;
//        }
//        // p1343
//        BigRational a = (plp.getDX(oom).multiply(lqlp.getDX(oom))).add(plp.getDY(oom)
//                .multiply(lqlp.getDY(oom))).add(plp.getDZ(oom).multiply(lqlp.getDZ(oom)));
//        BigRational b = (lqlp.getDX(oom).multiply(qp.getDX(oom))).add(lqlp.getDY(oom)
//                .multiply(qp.getDY(oom))).add(lqlp.getDZ(oom).multiply(qp.getDZ(oom)));
//        BigRational c = (plp.getDX(oom).multiply(qp.getDX(oom))).add(plp.getDY(oom)
//                .multiply(qp.getDY(oom))).add(plp.getDZ(oom).multiply(qp.getDZ(oom)));
//        BigRational d = (lqlp.getDX(oom).multiply(lqlp.getDX(oom))).add(lqlp.getDY(oom)
//                .multiply(lqlp.getDY(oom))).add(lqlp.getDZ(oom).multiply(lqlp.getDZ(oom)));
//        BigRational e = (qp.getDX(oom).multiply(qp.getDX(oom))).add(qp.getDY(oom)
//                .multiply(qp.getDY(oom))).add(qp.getDZ(oom).multiply(qp.getDZ(oom)));
//        BigRational den = (e.multiply(d)).subtract(b.multiply(b));
//        if (den.compareTo(BigRational.ZERO) == 0) {
//            return null;
//        }
//        BigRational num = (a.multiply(b)).subtract(c.multiply(d));
//        // dmnop = (xm - xn)(xo - xp) + (ym - yn)(yo - yp) + (zm - zn)(zo - zp)
//        // mua = ( d1343 d4321 - d1321 d4343 ) / ( d2121 d4343 - d4321 d4321 )
//        BigRational mua = num.divide(den);
//        // mub = ( d1343 + mua d4321 ) / d4343
//        BigRational mub = (a.add(b.multiply(mua))).divide(d);
//        V2D_Point pi = new V2D_Point(
//                (pv.getX(oom).add(mua.multiply(qp.getDX(oom)))),
//                (pv.getY(oom).add(mua.multiply(qp.getDY(oom)))),
//                (pv.getZ(oom).add(mua.multiply(qp.getDZ(oom)))));
//        V2D_Point qi = new V2D_Point(
//                (l.pv.getX(oom).add(mub.multiply(lqlp.getDX(oom)))),
//                (l.pv.getY(oom).add(mub.multiply(lqlp.getDY(oom)))),
//                (l.pv.getZ(oom).add(mub.multiply(lqlp.getDZ(oom)))));
//        if (pi.equals(qi)) {
//            return pi;
//        }
//        return new V2D_LineSegment(pi, qi, oom);
    }

//    /**
//     * @param v The vector to translate.
//     * @param oom The Order of Magnitude for the precision of the calculation.
//     * @return a new line.
//     */
//    @Override
//    public V2D_Line translate(V2D_Vector v, int oom) {
//        V2D_Line l = new V2D_Line(this);
//        l.offset = l.offset.add(v, oom);
//        return l;
//    }
    /**
     * Calculate and return the distance from {@code this} to {@code pt} rounded
     * to {@code oom} precision. See:
     * <ul>
     * <li>https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line</li>
     * <li>Weisstein, Eric W. "Point-Line Distance--3-Dimensional." From
     * MathWorld--A Wolfram Web Resource.
     * https://mathworld.wolfram.com/Point-LineDistance3-Dimensional.html</li>
     * </ul>
     *
     * @param pt A point for which the minimum distance from {@code this} is
     * returned.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The minimum distance between this and {@code pv}.
     */
    public BigRational getDistance(V2D_Point pt, int oom, RoundingMode rm) {
        if (intersects(pt, oom, rm)) {
            return BigRational.ZERO;
        }
        return new Math_BigRationalSqrt(
                getDistanceSquared(pt, true, oom, rm), oom, rm).getSqrt(oom, rm);
    }

    /**
     * Calculates and returns the squared distance from this to pt.
     *
     * See:
     * <ul>
     * <li>https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line</li>
     * <li>Weisstein, Eric W. "Point-Line Distance--3-Dimensional." From
     * MathWorld--A Wolfram Web Resource.
     * https://mathworld.wolfram.com/Point-LineDistance3-Dimensional.html</li>
     * </ul>
     *
     * @param pt A point for which the minimum distance from {@code this} is
     * returned.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The minimum distance between this and {@code pv}.
     */
    public BigRational getDistanceSquared(V2D_Point pt, int oom, RoundingMode rm) {
        if (intersects(pt, oom, rm)) {
            return BigRational.ZERO;
        } else {
            return getDistanceSquared(pt, true, oom, rm);
        }
    }

    /**
     * Calculates and returns the squared distance from {@code this} to
     * {@code pt}. This should only be used if it is known that {@code this}
     * does not intersect with {@code pt} (in which case an error is thrown).
     *
     * See:
     * <ul>
     * <li>https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line</li>
     * <li>Weisstein, Eric W. "Point-Line Distance--3-Dimensional." From
     * MathWorld--A Wolfram Web Resource.
     * https://mathworld.wolfram.com/Point-LineDistance3-Dimensional.html</li>
     * </ul>
     *
     * @param pt A point for which the minimum distance from {@code this} is
     * returned.
     * @param noint True iff there is no intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The minimum distance between this and {@code pv}.
     */
    protected BigRational getDistanceSquared(V2D_Point pt, boolean noint,
            int oom, RoundingMode rm) {
        V2D_Point poi = getPointOfIntersection(pt, noint, oom, rm);
        return poi.getDistanceSquared(pt, oom, rm);
    }

    /**
     * https://en.wikipedia.org/wiki/Skew_lines#Nearest_points
     * https://en.wikipedia.org/wiki/Distance_between_two_parallel_lines
     *
     * @param l A line for which the minimum distance from {@code this} is
     * returned.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The minimum distance between this and {@code l}.
     */
    public BigRational getDistance(V2D_Line l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom, rm), oom, rm)
                .getSqrt(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code l}.
     */
    public BigRational getDistanceSquared(V2D_Line l, int oom, RoundingMode rm) {
        if (this.equals(l, oom, rm)) {
            return BigRational.ZERO;
        }
        if (this.isParallel(l, oom, rm)) {
            return this.getDistanceSquared(l.p, oom, rm);
        }
        return BigRational.ZERO;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff this is parallel to the plane defined by x=0.
     */
    public boolean isParallelToX0(int oom, RoundingMode rm) {
        return v.dx.compareTo(Math_BigRationalSqrt.ZERO) == 0;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff this is parallel to the plane defined by y=0.
     */
    public boolean isParallelToY0(int oom, RoundingMode rm) {
        return v.dy.compareTo(Math_BigRationalSqrt.ZERO) == 0;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The points that define the plan as a matrix.
     */
    public Math_Matrix_BR getAsMatrix(int oom, RoundingMode rm) {
        V2D_Point tp = getP();
        V2D_Point tq = getQ(oom, rm);
        BigRational[][] m = new BigRational[2][2];
        m[0][0] = tp.getX(oom, rm);
        m[0][1] = tp.getY(oom, rm);
        m[1][0] = tq.getX(oom, rm);
        m[1][1] = tq.getY(oom, rm);
        return new Math_Matrix_BR(m);
    }

    /**
     * Translate (move relative to the origin).
     *
     * @param v The vector to translate.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    @Override
    public void translate(V2D_Vector v, int oom, RoundingMode rm) {
        super.translate(v, oom, rm);
        if (p != null) {
            this.p.translate(v, oom, rm);
        }
        if (q != null) {
            this.q.translate(v, oom, rm);
        }
    }

    @Override
    public V2D_Line rotate(V2D_Point pt, BigRational theta, Math_BigDecimal bd,
            int oom, RoundingMode rm) {
        theta = Math_AngleBigRational.normalise(theta, bd, oom, rm);
        if (theta.compareTo(BigRational.ZERO) == 0d) {
            return new V2D_Line(this);
        } else {
            return rotateN(pt, theta, bd, oom, rm);
        }
    }
    
    @Override
    public V2D_Line rotateN(V2D_Point pt, BigRational theta, Math_BigDecimal bd,
            int oom, RoundingMode rm) {
        V2D_Point rp = getP().rotateN(pt, theta, bd, oom, rm);
        V2D_Vector rv = v.rotateN(theta, bd, oom, rm);
        return new V2D_Line(rp, rv);
    }
    
    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param ps The points to test for collinearity.
     * @return {@code true} iff all points are collinear with l.
     */
    public static boolean isCollinear(int oom, RoundingMode rm, V2D_Point... ps) {
        V2D_Line l = getLine(oom, rm, ps);
        if (l == null) {
            return false;
        }
        return isCollinear(oom, rm, l, ps);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param l The line to test points are collinear with.
     * @param ps The points to test if they are collinear with l.
     * @return {@code true} iff all points are collinear with l.
     */
    public static boolean isCollinear(int oom, RoundingMode rm, V2D_Line l, 
            V2D_Point... ps) {
        for (var p : ps) {
            if (!l.intersects(p, oom, rm)) {
                return false;
            }
        }
        return true;
    }

    /**
     * There should be at least two different points in points. This does not
     * check for collinearity of all the points. It returns a line defined by 
     * the first points that have the greatest distance between them.
     * 
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param points Any number of points, but with two being different.
     * @return A line defined by any two different points or null if the points
     * are coincident.
     */
    public static V2D_Line getLine(int oom, RoundingMode rm, V2D_Point... points) {
        if (points.length < 2) {
            return null;
        }
        // Find the points which are furthest apart.
        BigRational max = BigRational.ZERO;
        V2D_Point a = null;
        V2D_Point b = null;
        for (int i = 0; i < points.length; i ++) {
            for (int j = i + 1; j < points.length; j ++) {
                BigRational d2 = points[i].getDistanceSquared(points[j], oom, rm);
                if (d2.compareTo(max) == 1) {
                    a = points[i];
                    b = points[j];
                    max = d2;
                }
            }
        }
        if (max.compareTo(BigRational.ZERO) == 0d) {
            return null;
        } else {
            return new V2D_Line(a, b, oom, rm);
        }
    }
    
    /**
     * https://math.stackexchange.com/questions/162728/how-to-determine-if-2-points-are-on-opposite-sides-of-a-line
     * @param a A point.
     * @param b Another point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return True iff a and b are on the same side of this. (If a is on the 
     * line, then so must b for them to be on the same side.
     */
    public boolean isOnSameSide(V2D_Point a, V2D_Point b, int oom, RoundingMode rm) {
        if (intersects(a, oom, rm) && intersects(b, oom, rm)) {
            return true;
        }
        p = getP();
        q = getQ(oom, rm);
        BigRational x1 = p.getX(oom, rm);
        BigRational y1 = p.getY(oom, rm);
        BigRational x2 = q.getX(oom, rm);
        BigRational y2 = q.getY(oom, rm);
        BigRational ax = a.getX(oom, rm);
        BigRational ay = a.getY(oom, rm);
        BigRational bx = b.getX(oom, rm);
        BigRational by = b.getY(oom, rm);
        BigRational y1sy2 = y1.subtract(y2); 
        BigRational x2sx1 = x2.subtract(x1);
        BigRational p1 = (y1sy2.multiply(ax.subtract(x1))).add(
                x2sx1.multiply(ay.subtract(y1)));
        BigRational p2 = (y1sy2.multiply(bx.subtract(x1))).add(
                ((x2sx1).multiply(by.subtract(y1))));
        return (p1.multiply(p2)).compareTo(BigRational.ZERO) != -1;
        //return ((y1-y2)*(ax-x1)+ (x2-x1)*(ay-y1))*((y1-y2)*(bx-x1)+(x2-x1)*(by-y1)) + epsilon >= 0D;
    }
}
