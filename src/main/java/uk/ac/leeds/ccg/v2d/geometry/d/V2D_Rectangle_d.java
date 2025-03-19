/*
 * Copyright 2025 Andy Turner, University of Leeds.
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
import java.util.Collection;
import java.util.HashMap;
import uk.ac.leeds.ccg.math.geometry.Math_AngleDouble;
import uk.ac.leeds.ccg.v2d.core.d.V2D_Environment_d;

/**
 * For representing and processing rectangles in 2D. A rectangle is a right
 * angled quadrilateral.
 *
 * @author Andy Turner
 * @version 2.0
 */
public class V2D_Rectangle_d extends V2D_Area_d {

    private static final long serialVersionUID = 1L;

    /**
     * For storing a triangle that makes up the rectangle.
     */
    protected final V2D_Triangle_d pqr;

    /**
     * For storing a triangle that makes up half the rectangle.
     */
    protected final V2D_Triangle_d rsp;

    /**
     * Create a new instance.
     *
     * @param r Another rectangle.
     */
    public V2D_Rectangle_d(V2D_Rectangle_d r) {
        this(r.getP(), r.getQ(), r.getR(), r.getS());
    }

    /**
     * Create a new instance.
     *
     * @param env The environment.
     * @param offset What {@link #offset} is set to.
     * @param p The bottom left corner of the rectangle.
     * @param q The top left corner of the rectangle.
     * @param r The top right corner of the rectangle.
     * @param s The bottom right corner of the rectangle.
     * @throws java.lang.RuntimeException iff the points do not define a
     * rectangle.
     */
    public V2D_Rectangle_d(V2D_Environment_d env,
            V2D_Vector_d offset, V2D_Vector_d p,
            V2D_Vector_d q, V2D_Vector_d r, V2D_Vector_d s) {
        super(env, offset);
        pqr = new V2D_Triangle_d(env, p, q, r);
        rsp = new V2D_Triangle_d(env, r, s, p);
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
    public V2D_Rectangle_d(V2D_Point_d p, V2D_Point_d q,
            V2D_Point_d r, V2D_Point_d s) {
        this(p.env, V2D_Vector_d.ZERO, p.getVector(), q.getVector(), r.getVector(), s.getVector());
//        V2D_Point_d qn = new V2D_Point_d(q);
//        qn.setOffset(p.offset);
//        V2D_Point_d rn = new V2D_Point_d(r);
//        rn.setOffset(p.offset);
//        V2D_Point_d sn = new V2D_Point_d(s);
//        sn.setOffset(p.offset);
//        //rsp = new V2D_Triangle_d(this.offset, rn.rel, sn.rel, p.rel);
//        //pqr = new V2D_Triangle_d(this.offset, p.rel, qn.rel, rn.rel);
//        pqr = new V2D_Triangle_d(p, qn, rn);
//        rsp = new V2D_Triangle_d(rn, sn, p);
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
    public V2D_Point_d[] getPointsArray() {
        return getPoints().values().toArray(new V2D_Point_d[4]);
    }

    @Override
    public HashMap<Integer, V2D_Point_d> getPoints() {
        if (points == null) {
            points = new HashMap<>(4);
            points.put(0, getP());
            points.put(1, getQ());
            points.put(2, getR());
            points.put(3, getS());
        }
        return points;
    }

    /**
     * @return A collection of the edges.
     */
    @Override
    public HashMap<Integer, V2D_LineSegment_d> getEdges() {
        if (edges == null) {
            edges = new HashMap<>(4);
            edges.put(0, getPQ());
            edges.put(0, getQR());
            edges.put(0, getRS());
            edges.put(0, getSP());
        }
        return edges;
    }

    /**
     * @return {@link #pqr}.
     */
    public V2D_Triangle_d getPQR() {
        return pqr;
    }

    /**
     * @return {@link #rsp}.
     */
    public V2D_Triangle_d getRSP() {
        return rsp;
    }

    /**
     * @return {@link #p} with {@link #offset} applied.
     */
    public V2D_Point_d getP() {
        return getPQR().getP();
    }

    /**
     * @return {@link #q} with {@link #offset} applied.
     */
    public V2D_Point_d getQ() {
        return getPQR().getQ();
    }

    /**
     * @return {@link #r} with {@link #offset} applied.
     */
    public V2D_Point_d getR() {
        return getPQR().getR();
    }

    /**
     * @return {@link #s} with {@link #offset} applied.
     */
    public V2D_Point_d getS() {
        return getRSP().getQ();
    }

    @Override
    public V2D_AABB_d getAABB() {
        if (en == null) {
            en = getRSP().getAABB().union(getPQR().getAABB());
        }
        return en;
    }

    /**
     * @param pt The point to test for intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A point or line segment.
     */
    public boolean intersects(V2D_Point_d pt, double epsilon) {
        if (getPQR().intersects(pt, epsilon)) {
            return true;
        } else {
            return getRSP().intersects(pt, epsilon);
        }
    }

    /**
     * @param l The line segment to test for intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A point or line segment.
     */
    public boolean intersects(V2D_LineSegment_d l, double epsilon) {
        if (getPQR().intersects(l, epsilon)) {
            return true;
        } else {
            return getRSP().intersects(l, epsilon);
        }
    }

    /**
     * @param ls The line segments to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return True iff there is an intersection.
     */
    public boolean intersects(double epsilon, V2D_LineSegment_d... ls) {
        return intersects(epsilon, Arrays.asList(ls));
    }

    /**
     * @param ls The line segments to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return True iff there is an intersection.
     */
    public boolean intersects(double epsilon, Collection<V2D_LineSegment_d> ls) {
        return ls.parallelStream().anyMatch(x -> intersects(x, epsilon));
    }

    /**
     * @param t The triangle segment to test for intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A point or line segment.
     */
    public boolean intersects(V2D_Triangle_d t, double epsilon) {
        if (pqr.intersects(t, epsilon)) {
            return true;
        } else {
            return rsp.intersects(t, epsilon);
        }
    }

    /**
     * @return The line segment from {@link #p} to {@link #q}.
     */
    protected V2D_LineSegment_d getPQ() {
        return getPQR().getPQ();
    }

    /**
     * @return The line segment from {@link #q} to {@link #r}.
     */
    protected V2D_LineSegment_d getQR() {
        return getPQR().getQR();
    }

    /**
     * @return The line segment from {@link #r} to {@link #s}.
     */
    protected V2D_LineSegment_d getRS() {
        return getRSP().getPQ();
    }

    /**
     * @return The line segment from {@link #s} to {@link #p}.
     */
    protected V2D_LineSegment_d getSP() {
        return getRSP().getQR();
    }

    /**
     * @param l The line to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A point or line segment.
     */
    public V2D_FiniteGeometry_d getIntersect(V2D_Line_d l,
            double epsilon) {
        V2D_FiniteGeometry_d pqri = pqr.getIntersect(l, epsilon);
        V2D_FiniteGeometry_d rspi = rsp.getIntersect(l, epsilon);
        return join(epsilon, pqri, rspi);
    }

    private V2D_FiniteGeometry_d join(double epsilon,
            V2D_FiniteGeometry_d pqri, V2D_FiniteGeometry_d rspi) {
        if (pqri == null) {
            if (rspi == null) {
                return null;
            } else {
                return rspi;
            }
        } else if (pqri instanceof V2D_LineSegment_d pqril) {
            if (rspi == null) {
                return pqri;
            } else if (rspi instanceof V2D_LineSegment_d rspil) {
                return V2D_LineSegment_d.getGeometry(epsilon, pqril, rspil);
            } else {
                return pqri;
            }
        } else {
            // pqri instanceof V2D_Point_d
            if (rspi == null) {
                return pqri;
            } else if (rspi instanceof V2D_LineSegment_d) {
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
    public V2D_FiniteGeometry_d getIntersect(V2D_LineSegment_d l,
            double epsilon) {
        V2D_FiniteGeometry_d pqri = pqr.getIntersect(l, epsilon);
        V2D_FiniteGeometry_d rspi = rsp.getIntersect(l, epsilon);
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
    public double getDistance(V2D_Point_d p, double epsilon) {
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
    public double getDistanceSquared(V2D_Point_d pt, double epsilon) {
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
    public void translate(V2D_Vector_d v) {
        pqr.translate(v);
        rsp.translate(v);
    }

    @Override
    public V2D_Rectangle_d rotate(V2D_Point_d pt, double theta) {
        theta = Math_AngleDouble.normalise(theta);
        if (theta == 0d) {
            return new V2D_Rectangle_d(this);
        } else {
            return rotateN(pt, theta);
        }
    }

    @Override
    public V2D_Rectangle_d rotateN(V2D_Point_d pt, double theta) {
        return new V2D_Rectangle_d(
                getP().rotateN(pt, theta),
                getQ().rotateN(pt, theta),
                getR().rotateN(pt, theta),
                getS().rotateN(pt, theta));
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
    public V2D_FiniteGeometry_d getIntersect(V2D_Triangle_d t,
            double epsilon) {
        V2D_FiniteGeometry_d pqrit = pqr.getIntersect(t, epsilon);
        V2D_FiniteGeometry_d rspit = rsp.getIntersect(t, epsilon);
        if (pqrit == null) {
            return rspit;
        } else if (pqrit instanceof V2D_Point_d) {
            if (rspit == null) {
                return pqrit;
            } else {
                return rspit;
            }
        } else if (pqrit instanceof V2D_LineSegment_d) {
            if (rspit == null) {
                return pqrit;
            } else {
                return rspit;
            }
        } else {
            if (rspit == null) {
                return pqrit;
            }
            V2D_Point_d[] pqritps = pqrit.getPointsArray();
            V2D_Point_d[] rspitps = rspit.getPointsArray();
            V2D_Point_d[] pts = Arrays.copyOf(pqritps, pqritps.length + rspitps.length);
            System.arraycopy(rspitps, 0, pts, pqritps.length, rspitps.length);
            return V2D_ConvexArea_d.getGeometry(epsilon, pts);
        }
    }

    /**
     * Computes and returns the intersection between {@code this} and 
     * {@code ch}. The intersection could be: null, a point, a line segment, or
     * a convex area.
     *
     * @param ch The convex are to intersect with this.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code t} and {@code this} or
     * {@code null} if there is no intersection.
     */
    public V2D_FiniteGeometry_d getIntersect(V2D_ConvexArea_d ch,
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
    public V2D_FiniteGeometry_d getIntersect(V2D_Ray_d r,
            double epsilon) {
        V2D_FiniteGeometry_d gpqr = pqr.getIntersect(r, epsilon);
        V2D_FiniteGeometry_d grsp = rsp.getIntersect(r, epsilon);
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
    public double getDistance(V2D_Line_d l, double epsilon) {
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
    public double getDistanceSquared(V2D_Line_d l, double epsilon) {
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
    public double getDistance(V2D_LineSegment_d l, double epsilon) {
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
    public double getDistanceSquared(V2D_LineSegment_d l, double epsilon) {
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
    public double getDistance(V2D_Triangle_d t, double epsilon) {
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
    public double getDistanceSquared(V2D_Triangle_d t, double epsilon) {
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
    public boolean equals(V2D_Rectangle_d r, double epsilon) {
        Collection<V2D_Point_d> ps = getPoints().values();
        Collection<V2D_Point_d> rps = r.getPoints().values();
        return ps.parallelStream().allMatch(x -> x.equalsAny(rps, epsilon));
//        for (var x : pts) {
//            boolean found = false;
//            for (var y : rpts) {
//                if (x.equals(epsilon, y)) {
//                    found = true;
//                    break;
//                }
//            }
//            if (!found) {
//                return false;
//            }
//        }
//        for (var x : rpts) {
//            boolean found = false;
//            for (var y : pts) {
//                if (x.equals(epsilon, y)) {
//                    found = true;
//                    break;
//                }
//            }
//            if (!found) {
//                return false;
//            }
//        }
//        return true;
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
    public static boolean isRectangle(V2D_Point_d p, V2D_Point_d q,
            V2D_Point_d r, V2D_Point_d s, double epsilon) {
        V2D_LineSegment_d pq = new V2D_LineSegment_d(p, q);
        V2D_LineSegment_d qr = new V2D_LineSegment_d(q, r);
        V2D_LineSegment_d rs = new V2D_LineSegment_d(r, s);
        V2D_LineSegment_d sp = new V2D_LineSegment_d(s, p);
        if (pq.l.isParallel(rs.l, epsilon)) {
            if (qr.l.isParallel(sp.l, epsilon)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Identify if {@code this} is intersected by {@code aabb}.
     * 
     * @param aabb The envelope to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} is intersected by {@code aabb}.
     */
    public boolean intersects(V2D_AABB_d aabb, double epsilon) {
        if (pqr.intersects(aabb, epsilon)) {
            return true;
        }
        return rsp.intersects(aabb, epsilon);
    }
}
