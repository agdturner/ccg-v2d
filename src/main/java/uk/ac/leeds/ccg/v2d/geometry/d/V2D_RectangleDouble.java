/*
 * Copyright 2020 Andy Turner, University of Leeds.
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

import java.util.Arrays;
import uk.ac.leeds.ccg.math.geometry.Math_AngleDouble;

/**
 * For representing and processing rectangles in 2D. A rectangle is a right
 * angled quadrilateral. The four corners are the points
 * {@link #p}, {@link #q}, {@link #r} and {@link #s}. The following depicts a
 * rectangle {@code
 * q  *-----* r
 * |   / |
 * |  /  |
 * | /   |
 * p *-----* s
 * }
 * The angles PQR, QRS, RSP, SPQ are all 90 degrees or Pi/2 radians.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_RectangleDouble extends V2D_FiniteGeometryDouble {

    private static final long serialVersionUID = 1L;

    /**
     * For storing a triangle that makes up the rectangle.
     */
    protected final V2D_TriangleDouble pqr;

    /**
     * For storing a triangle that makes up half the rectangle.
     */
    protected final V2D_TriangleDouble rsp;

    /**
     * Create a new instance.
     *
     * @param r Another rectangle.
     */
    public V2D_RectangleDouble(V2D_RectangleDouble r) {
        this(r.getP(), r.getQ(), r.getR(), r.getS());
    }

    /**
     * Create a new instance.
     *
     * @param offset What {@link #offset} is set to.
     * @param p The bottom left corner of the rectangle.
     * @param q The top left corner of the rectangle.
     * @param r The top right corner of the rectangle.
     * @param s The bottom right corner of the rectangle.
     * @throws java.lang.RuntimeException iff the points do not define a
     * rectangle.
     */
    public V2D_RectangleDouble(V2D_VectorDouble offset, V2D_VectorDouble p,
            V2D_VectorDouble q, V2D_VectorDouble r, V2D_VectorDouble s) {
        super(offset);
        pqr = new V2D_TriangleDouble(p, q, r);
        rsp = new V2D_TriangleDouble(r, s, p);
    }

    /**
     * Creates a new instance.
     *
     * @param p Used to initialise {@link #offset}, {@link #pqr} and
     * {@link #rsp}.
     * @param q Used to initialise {@link #pqr} and {@link #rsp}.
     * @param r Used to initialise {@link #pqr} and {@link #rsp}.
     * @param s Used to initialise {@link #rsp}.
     */
    public V2D_RectangleDouble(V2D_PointDouble p, V2D_PointDouble q,
            V2D_PointDouble r, V2D_PointDouble s) {
        this(V2D_VectorDouble.ZERO, p.getVector(), q.getVector(), r.getVector(), s.getVector());
//        V2D_PointDouble qn = new V2D_PointDouble(q);
//        qn.setOffset(p.offset);
//        V2D_PointDouble rn = new V2D_PointDouble(r);
//        rn.setOffset(p.offset);
//        V2D_PointDouble sn = new V2D_PointDouble(s);
//        sn.setOffset(p.offset);
//        //rsp = new V2D_TriangleDouble(this.offset, rn.rel, sn.rel, p.rel);
//        //pqr = new V2D_TriangleDouble(this.offset, p.rel, qn.rel, rn.rel);
//        pqr = new V2D_TriangleDouble(p, qn, rn);
//        rsp = new V2D_TriangleDouble(rn, sn, p);
    }

    @Override
    public String toString() {
        //return toString("");
        return toStringSimple("");
    }

    /**
     * @param pad Padding
     * @return A simple String representation of this.
     */
    public String toStringSimple(String pad) {
        return pad + this.getClass().getSimpleName() + "("
                + toStringFieldsSimple("") + ")";
    }

    @Override
    protected String toStringFields(String pad) {
        return "\n" + super.toStringFields(pad) + ",\n"
                + pad + "pqr=" + getPQR().toString(pad) + ",\n"
                + pad + "rsp=" + getRSP().toString(pad);
    }

    @Override
    protected String toStringFieldsSimple(String pad) {
        return "\n" + super.toStringFieldsSimple(pad) + ",\n"
                + pad + "pqr=" + getPQR().toStringSimple(pad) + ",\n"
                + pad + "rsp=" + getRSP().toStringSimple(pad);
    }

    @Override
    public V2D_PointDouble[] getPoints() {
        V2D_PointDouble[] re = new V2D_PointDouble[4];
        re[0] = getP();
        re[1] = getQ();
        re[2] = getR();
        re[3] = getS();
        return re;
    }

    /**
     * @return {@link #pqr}.
     */
    public V2D_TriangleDouble getPQR() {
        return pqr;
    }

    /**
     * @return {@link #rsp}.
     */
    public V2D_TriangleDouble getRSP() {
        return rsp;
    }

    /**
     * @return {@link #p} with {@link #offset} applied.
     */
    public V2D_PointDouble getP() {
        return getPQR().getP();
    }

    /**
     * @return {@link #q} with {@link #offset} applied.
     */
    public V2D_PointDouble getQ() {
        return getPQR().getQ();
    }

    /**
     * @return {@link #r} with {@link #offset} applied.
     */
    public V2D_PointDouble getR() {
        return getPQR().getR();
    }

    /**
     * @return {@link #s} with {@link #offset} applied.
     */
    public V2D_PointDouble getS() {
        return getRSP().getQ();
    }

    @Override
    public V2D_EnvelopeDouble getEnvelope() {
        if (en == null) {
            en = getRSP().getEnvelope().union(getPQR().getEnvelope());
        }
        return en;
    }

    /**
     * @param pt The point to test for intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A point or line segment.
     */
    public boolean isIntersectedBy(V2D_PointDouble pt, double epsilon) {
        if (getPQR().isIntersectedBy(pt, epsilon)) {
            return true;
        } else {
            return getRSP().isIntersectedBy(pt, epsilon);
        }
    }
    
    /**
     * @param l The line segment to test for intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A point or line segment.
     */
    public boolean isIntersectedBy(V2D_LineSegmentDouble l, double epsilon) {
        if (getPQR().isIntersectedBy(l, epsilon)) {
            return true;
        } else {
            return getRSP().isIntersectedBy(l, epsilon);
        }
    }
    
    /**
     * @param ls The line segments to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return True iff there is an intersection.
     */
    public boolean isIntersectedBy(double epsilon, V2D_LineSegmentDouble... ls) {
        for (V2D_LineSegmentDouble l : ls) {
            if (isIntersectedBy(l, epsilon)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param t The triangle segment to test for intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A point or line segment.
     */
    public boolean isIntersectedBy(V2D_TriangleDouble t, double epsilon) {
        if (pqr.isIntersectedBy(t, epsilon)) {
            return true;
        } else {
            return rsp.isIntersectedBy(t, epsilon);
        }
    }

    /**
     * @return The line segment from {@link #r} to {@link #s}.
     */
    protected V2D_LineSegmentDouble getRS() {
        return getRSP().getPQ();
    }

    /**
     * @return The line segment from {@link #s} to {@link #p}.
     */
    protected V2D_LineSegmentDouble getSP() {
        return getRSP().getQR();
    }

    /**
     * @param l The line to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A point or line segment.
     */
    public V2D_FiniteGeometryDouble getIntersection(V2D_LineDouble l,
            double epsilon) {
        V2D_FiniteGeometryDouble pqri = pqr.getIntersection(l, epsilon);
        V2D_FiniteGeometryDouble rspi = rsp.getIntersection(l, epsilon);
        return join(epsilon, pqri, rspi);
    }

    private V2D_FiniteGeometryDouble join(double epsilon,
            V2D_FiniteGeometryDouble pqri, V2D_FiniteGeometryDouble rspi) {
        if (pqri == null) {
            if (rspi == null) {
                return null;
            } else {
                return rspi;
            }
        } else if (pqri instanceof V2D_LineSegmentDouble pqril) {
            if (rspi == null) {
                return pqri;
            } else if (rspi instanceof V2D_LineSegmentDouble rspil) {
                return V2D_LineSegmentDouble.getGeometry(epsilon, pqril, rspil);
            } else {
                return pqri;
            }
        } else {
            // pqri instanceof V2D_PointDouble
            if (rspi == null) {
                return pqri;
            } else if (rspi instanceof V2D_LineSegmentDouble) {
                return rspi;
            } else {
                return pqri;
            }
        }
    }

    /**
     * Calculates and returns the intersections between {@code this} and
     * {@code l}.
     *
     * @param l The line segment to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection or {@code null} iff there is no intersection.
     */
    public V2D_FiniteGeometryDouble getIntersection(V2D_LineSegmentDouble l,
            double epsilon) {
        V2D_FiniteGeometryDouble pqri = pqr.getIntersection(l, epsilon);
        V2D_FiniteGeometryDouble rspi = rsp.getIntersection(l, epsilon);
        return join(epsilon, pqri, rspi);
    }

    public double getPerimeter() {
        return (pqr.getPQ().getLength() + pqr.getQR().getLength()) * 2d;
    }

    public double getArea() {
        return pqr.getPQ().getLength() * pqr.getQR().getLength();
    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param p A point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The distance from {@code this} to {@code pl}.
     */
    public double getDistance(V2D_PointDouble p, double epsilon) {
        return Math.sqrt(getDistanceSquared(p, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code pt}.
     *
     * @param pt A point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The distance squared to {@code p}.
     */
    public double getDistanceSquared(V2D_PointDouble pt, double epsilon) {
        double d1 = pqr.getDistanceSquared(pt, epsilon);
        double d2 = rsp.getDistanceSquared(pt, epsilon);
        return Math.min(d1, d2);
    }

    /**
     * Move the rectangle.
     *
     * @param v What is added to {@link #p}, {@link #q}, {@link #r}, {@link #s}.
     */
    @Override
    public void translate(V2D_VectorDouble v) {
        pqr.translate(v);
        rsp.translate(v);
    }

    @Override
    public V2D_RectangleDouble rotate(V2D_PointDouble pt, double theta,
            double epsilon) {
        theta = Math_AngleDouble.normalise(theta);
        if (theta == 0d) {
            return new V2D_RectangleDouble(this);
        } else {
            return rotateN(pt, theta, epsilon);
        }
    }

    @Override
    public V2D_RectangleDouble rotateN(V2D_PointDouble pt,
            double theta, double epsilon) {
        return new V2D_RectangleDouble(
                getP().rotateN(pt, theta, epsilon),
                getQ().rotateN(pt, theta, epsilon),
                getR().rotateN(pt, theta, epsilon),
                getS().rotateN(pt, theta, epsilon));
    }

    /**
     * Computes and returns the intersection between {@code this} and {@code t}.
     * The intersection could be: null, a point, a line segment, a triangle, or
     * a convex hull (with 4, 5, 6 or 7 sides).
     *
     * @param t The triangle to intersect with this.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code t} and {@code this} or
     * {@code null} if there is no intersection.
     */
    public V2D_FiniteGeometryDouble getIntersection(V2D_TriangleDouble t,
            double epsilon) {
        V2D_FiniteGeometryDouble pqrit = pqr.getIntersection(t, epsilon);
        V2D_FiniteGeometryDouble rspit = rsp.getIntersection(t, epsilon);
        if (pqrit == null) {
            return rspit;
        } else if (pqrit instanceof V2D_PointDouble) {
            if (rspit == null) {
                return pqrit;
            } else {
                return rspit;
            }
        } else if (pqrit instanceof V2D_LineSegmentDouble) {
            if (rspit == null) {
                return pqrit;
            } else {
                return rspit;
            }
        } else {
            if (rspit == null) {
                return pqrit;
            }
            V2D_PointDouble[] pqritps = pqrit.getPoints();
            V2D_PointDouble[] rspitps = rspit.getPoints();
            V2D_PointDouble[] pts = Arrays.copyOf(pqritps, pqritps.length + rspitps.length);
            System.arraycopy(rspitps, 0, pts, pqritps.length, rspitps.length);
            return V2D_ConvexHullDouble.getGeometry(epsilon, pts);
        }
    }
    
    /**
     * Computes and returns the intersection between {@code this} and {@code t}.
     * The intersection could be: null, a point, a line segment, a triangle, or
     * a convex hull (with 4, 5, 6 or 7 sides).
     *
     * @param ch The convex hull to intersect with this.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code t} and {@code this} or
     * {@code null} if there is no intersection.
     */
    public V2D_FiniteGeometryDouble getIntersection(V2D_ConvexHullDouble ch,
            double epsilon) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Get the intersection between the geometry and the line segment {@code l}.
     *
     * @param r The ray to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The V2D_Geometry.
     */
    public V2D_FiniteGeometryDouble getIntersection(V2D_RayDouble r,
            double epsilon) {
        V2D_FiniteGeometryDouble gpqr = pqr.getIntersection(r, epsilon);
        V2D_FiniteGeometryDouble grsp = rsp.getIntersection(r, epsilon);
        if (gpqr == null) {
            return grsp;
        } else {
            if (grsp == null) {
                return gpqr;
            }
            return join(epsilon, gpqr, grsp);
        }
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance to {@code l}.
     */
    public double getDistance(V2D_LineDouble l, double epsilon) {
        return Math.sqrt(getDistanceSquared(l, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance to {@code l}.
     */
    public double getDistanceSquared(V2D_LineDouble l, double epsilon) {
        return Math.min(
                rsp.getDistanceSquared(l, epsilon),
                pqr.getDistanceSquared(l, epsilon));
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line segment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance to {@code l}.
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
     * @return The minimum distance to {@code l}.
     */
    public double getDistanceSquared(V2D_LineSegmentDouble l, double epsilon) {
        return Math.min(
                rsp.getDistanceSquared(l, epsilon),
                pqr.getDistanceSquared(l, epsilon));
    }

    /**
     * Get the minimum distance to {@code t}.
     *
     * @param t A triangle.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance squared to {@code t}.
     */
    public double getDistance(V2D_TriangleDouble t, double epsilon) {
        return Math.sqrt(getDistanceSquared(t, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code t}.
     *
     * @param t A triangle.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance squared to {@code t}.
     */
    public double getDistanceSquared(V2D_TriangleDouble t, double epsilon) {
        return Math.min(
                rsp.getDistanceSquared(t, epsilon),
                pqr.getDistanceSquared(t, epsilon));
    }

    /**
     * @param r The rectangle to test if it is equal to this.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff this is equal to r.
     */
    public boolean equals(V2D_RectangleDouble r, double epsilon) {
        V2D_PointDouble[] pts = getPoints();
        V2D_PointDouble[] rpts = r.getPoints();
        for (var x : pts) {
            boolean found = false;
            for (var y : rpts) {
                if (x.equals(epsilon, y)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        for (var x : rpts) {
            boolean found = false;
            for (var y : pts) {
                if (x.equals(epsilon, y)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    /**
     * For testing if four points form a rectangle.
     *
     * @param p First clockwise or anti-clockwise point.
     * @param q Second clockwise or anti-clockwise point.
     * @param r Third clockwise or anti-clockwise point.
     * @param s Fourth clockwise or anti-clockwise point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff pl, qv, r and s form a rectangle.
     */
    public static boolean isRectangle(V2D_PointDouble p, V2D_PointDouble q,
            V2D_PointDouble r, V2D_PointDouble s, double epsilon) {
        V2D_LineSegmentDouble pq = new V2D_LineSegmentDouble(p, q);
        V2D_LineSegmentDouble qr = new V2D_LineSegmentDouble(q, r);
        V2D_LineSegmentDouble rs = new V2D_LineSegmentDouble(r, s);
        V2D_LineSegmentDouble sp = new V2D_LineSegmentDouble(s, p);
        if (pq.l.isParallel(rs.l, epsilon)) {
            if (qr.l.isParallel(sp.l, epsilon)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isIntersectedBy(V2D_EnvelopeDouble aabb, double epsilon) {
        if (pqr.isIntersectedBy(aabb, epsilon)) {
            return true;
        }
        return rsp.isIntersectedBy(aabb, epsilon);
    }
}
