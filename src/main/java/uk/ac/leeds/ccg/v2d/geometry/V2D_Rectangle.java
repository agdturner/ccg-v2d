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
package uk.ac.leeds.ccg.v2d.geometry;

import ch.obermuhlner.math.big.BigRational;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v2d.core.V2D_Environment;

/**
 * For representing and processing rectangles in 2D. A rectangle is a right
 * angled quadrilateral.
 * 
 * @author Andy Turner
 * @version 2.0
 */
public class V2D_Rectangle extends V2D_Shape {

    private static final long serialVersionUID = 1L;

    /**
     * For storing a triangle that makes up the rectangle.
     */
    protected final V2D_Triangle pqr;

    /**
     * For storing a triangle that makes up half the rectangle.
     */
    protected final V2D_Triangle rsp;

    /**
     * Create a new instance.
     * 
     * @param r Another rectangle.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V2D_Rectangle(V2D_Rectangle r, int oom, RoundingMode rm) {
        this(r.getP(oom, rm), r.getQ(oom, rm), r.getR(oom, rm), r.getS(oom, rm), oom, rm);
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
    public V2D_Rectangle(V2D_Environment env, V2D_Vector offset, V2D_Vector p,
            V2D_Vector q, V2D_Vector r, V2D_Vector s) {
        super(env, offset);
        pqr = new V2D_Triangle(env, p, q, r);
        rsp = new V2D_Triangle(env, r, s, p);
    }

    /**
     * Creates a new instance.
     *
     * @param p Used to initialise {@link #offset}, {@link #pqr} and {@link #rsp}.
     * @param q Used to initialise {@link #pqr} and {@link #rsp}.
     * @param r Used to initialise {@link #pqr} and {@link #rsp}.
     * @param s Used to initialise {@link #rsp}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V2D_Rectangle(V2D_Point p, V2D_Point q, V2D_Point r, V2D_Point s,
            int oom, RoundingMode rm) {
        this(p.env, V2D_Vector.ZERO, p.getVector(oom, rm), q.getVector(oom, rm), 
                r.getVector(oom, rm), s.getVector(oom, rm));
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
    public V2D_Point[] getPointsArray(int oom, RoundingMode rm) {
        V2D_Point[] re = new V2D_Point[4];
        re[0] = getP(oom, rm);
        re[1] = getQ(oom, rm);
        re[2] = getR(oom, rm);
        re[3] = getS(oom, rm);
        return re;
    }
    
    @Override
    public HashMap<Integer, V2D_Point> getPoints(int oom, RoundingMode rm) {
        HashMap<Integer, V2D_Point> pts = new HashMap<>(4);
        pts.put(0, getP(oom, rm));
        pts.put(1, getQ(oom, rm));
        pts.put(2, getR(oom, rm));
        pts.put(2, getS(oom, rm));
        return pts;
    }

    
    /**
     * @return A collection of the edges.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    @Override
    public HashMap<Integer, V2D_LineSegment> getEdges(int oom, RoundingMode rm) {
        HashMap<Integer, V2D_LineSegment> edges = new HashMap<>();
        edges.put(0, getPQ(oom, rm));
        edges.put(0, getQR(oom, rm));
        edges.put(0, getRS(oom, rm));
        edges.put(0, getSP(oom, rm));
        return edges;
    }
    
    /**
     * @return {@link #pqr}.
     */
    public V2D_Triangle getPQR() {
        return pqr;
    }
    
    /**
     * @return {@link #rsp}.
     */
    public V2D_Triangle getRSP() {
        return rsp;
    }
    
    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #p} with {@link #offset} applied.
     */
    public V2D_Point getP(int oom, RoundingMode rm) {
        return getPQR().getP(oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #q} with {@link #offset} applied.
     */
    public V2D_Point getQ(int oom, RoundingMode rm) {
        return getPQR().getQ(oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #r} with {@link #offset} applied.
     */
    public V2D_Point getR(int oom, RoundingMode rm) {
        return getPQR().getR(oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #s} with {@link #offset} applied.
     */
    public V2D_Point getS(int oom, RoundingMode rm) {
        return getRSP().getQ(oom, rm);
    }

    @Override
    public V2D_AABB getAABB(int oom, RoundingMode rm) {
        if (en == null) {
            en = rsp.getAABB(oom, rm).union(pqr.getAABB(oom, rm), oom);
        }
        return en;
    }

    /**
     * @param pt The point to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A point or line segment.
     */
    public boolean intersects(V2D_Point pt, int oom, RoundingMode rm) {
        if (pqr.intersects(pt, oom, rm)) {
            return true;
        } else {
            return rsp.intersects(pt, oom, rm);
        }
    }
    
    /**
     * @param l The line segment to test for intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A point or line segment.
     */
    public boolean intersects(V2D_LineSegment l, int oom, RoundingMode rm) {
        if (getPQR().intersects(l, oom, rm)) {
            return true;
        } else {
            return getRSP().intersects(l, oom, rm);
        }
    }
    
    /**
     * @param ls The line segments to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return True iff there is an intersection.
     */
    public boolean intersects(int oom, RoundingMode rm, V2D_LineSegment... ls) {
        return intersects(oom, rm, Arrays.asList(ls));
    }
    
    /**
     * @param ls The line segments to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return True iff there is an intersection.
     */
    public boolean intersects(int oom, RoundingMode rm, Collection<V2D_LineSegment> ls) {
        return ls.parallelStream().anyMatch(x -> intersects(x, oom, rm));
    }
    
    /**
     * @param t The triangle segment to test for intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A point or line segment.
     */
    public boolean intersects(V2D_Triangle t, int oom, RoundingMode rm) {
        if (pqr.intersects(t, oom, rm)) {
            return true;
        } else {
            return rsp.intersects(t, oom, rm);
        }
    }

    /**
     * @return The line segment from {@link #p} to {@link #q}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    protected V2D_LineSegment getPQ(int oom, RoundingMode rm) {
        return getPQR().getPQ(oom, rm);
    }

    /**
     * @return The line segment from {@link #q} to {@link #r}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    protected V2D_LineSegment getQR(int oom, RoundingMode rm) {
        return getPQR().getQR(oom, rm);
    }

    /**
     * @return The line segment from {@link #r} to {@link #s}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    protected V2D_LineSegment getRS(int oom, RoundingMode rm) {
        return getRSP().getPQ(oom, rm);
    }

    /**
     * @return The line segment from {@link #s} to {@link #p}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    protected V2D_LineSegment getSP(int oom, RoundingMode rm) {
        return getRSP().getQR(oom, rm);
    }

    /**
     * @param l The line to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A point or line segment.
     */
    public V2D_FiniteGeometry getIntersect(V2D_Line l,
            int oom, RoundingMode rm) {
            V2D_FiniteGeometry pqri = pqr.getIntersect(l, oom, rm);
            V2D_FiniteGeometry rspi = rsp.getIntersect(l, oom, rm);
            return join(oom, rm, pqri, rspi);
    }

    private V2D_FiniteGeometry join(int oom, RoundingMode rm,
            V2D_FiniteGeometry pqri, V2D_FiniteGeometry rspi) {
        if (pqri == null) {
            if (rspi == null) {
                return null;
            } else {
                return rspi;
            }
        } else if (pqri instanceof V2D_LineSegment pqril) {
            if (rspi == null) {
                return pqri;
            } else if (rspi instanceof V2D_LineSegment rspil) {
                return V2D_LineSegment.getGeometry(oom, rm, pqril, rspil);
            } else {
                return pqri;
            }
        } else {
            // pqri instanceof V2D_Point
            if (rspi == null) {
                return pqri;
            } else if (rspi instanceof V2D_LineSegment) {
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The intersection or {@code null} iff there is no intersection.
     */
    public V2D_FiniteGeometry getIntersect(V2D_LineSegment l,
            int oom, RoundingMode rm) {
            V2D_FiniteGeometry pqri = pqr.getIntersect(l, oom, rm);
            V2D_FiniteGeometry rspi = rsp.getIntersect(l, oom, rm);
            return join(oom, rm, pqri, rspi);
    }

//    public BigRational getPerimeter(int oom, RoundingMode rm) {
//        return (pqr.getPQ(oom, rm).getLength(oom, rm).add(
//                pqr.getQR(oom, rm).getLength(oom, rm)))
//                .mulitply(BigRational.TWO);
//    }
//
//    public BigRational getArea(int oom, RoundingMode rm) {
//        return pqr.getPQ(oom, rm).getLength(oom, rm).multiply(
//                pqr.getQR(oom, rm).getLength(oom, rm));
//    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The distance from {@code this} to {@code pl}.
     */
    public BigRational getDistance(V2D_Point p, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom, rm), oom, rm).getSqrt(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code pt}.
     *
     * @param pt A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The distance squared to {@code p}.
     */
    public BigRational getDistanceSquared(V2D_Point pt, int oom, RoundingMode rm) {
        BigRational d1 = pqr.getDistanceSquared(pt, oom, rm);
        BigRational d2 = rsp.getDistanceSquared(pt, oom, rm);
        return BigRational.min(d1, d2);
    }

    /**
     * Move the rectangle.
     *
     * @param v What is added to {@link #p}, {@link #q}, {@link #r}, {@link #s}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    @Override
    public void translate(V2D_Vector v, int oom, RoundingMode rm) {
        pqr.translate(v, oom, rm);
        rsp.translate(v, oom, rm);
    }

    @Override
    public V2D_Rectangle rotate(V2D_Point pt, BigRational theta, 
            Math_BigDecimal bd, int oom, RoundingMode rm) {
        theta = Math_AngleBigRational.normalise(theta, bd, oom, rm);
        if (theta.compareTo(BigRational.ZERO) == 0d) {
            return new V2D_Rectangle(this, oom, rm);
        } else {
            return rotateN(pt, theta, bd, oom, rm);
        }
    }
    
    @Override
    public V2D_Rectangle rotateN(V2D_Point pt, BigRational theta, 
            Math_BigDecimal bd, int oom, RoundingMode rm) {
        return new V2D_Rectangle(
                getP(oom, rm).rotateN(pt, theta, bd, oom, rm),
                getQ(oom, rm).rotateN(pt, theta, bd, oom, rm),
                getR(oom, rm).rotateN(pt, theta, bd, oom, rm),
                getS(oom, rm).rotateN(pt, theta, bd, oom, rm), oom, rm);
    }

    /**
     * Computes and returns the intersection between {@code this} and {@code t}.
     * The intersection could be: null, a point, a line segment, a triangle, or
     * a convex hull (with 4, 5, 6 or 7 sides).
     *
     * @param t The triangle intersect with this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The intersection between {@code t} and {@code this} or
     * {@code null} if there is no intersection.
     */
    public V2D_FiniteGeometry getIntersect(V2D_Triangle t,
            int oom, RoundingMode rm) {
        int oomn2 = oom -2;
            V2D_FiniteGeometry pqrit = pqr.getIntersect(t, oomn2-10, rm);
            V2D_FiniteGeometry rspit = rsp.getIntersect(t, oomn2, rm);
            if (pqrit == null) {
                return rspit;
            } else if (pqrit instanceof V2D_Point) {
                if (rspit == null) {
                    return pqrit;
                } else {
                    return rspit;
                }
            } else if (pqrit instanceof V2D_LineSegment) {
                if (rspit == null) {
                    return pqrit;
                } else {
                    return rspit;
                }
            } else {
                if (rspit == null) {
                    return pqrit;
                }
                V2D_Point[] pqritps = pqrit.getPointsArray(oom, rm);
                V2D_Point[] rspitps = rspit.getPointsArray(oom, rm);
                V2D_Point[] pts = Arrays.copyOf(pqritps, pqritps.length + rspitps.length);
                System.arraycopy(rspitps, 0, pts, pqritps.length, rspitps.length);
                return V2D_ConvexHull.getGeometry(oom, rm, pts);
            }
    }

    /**
     * Get the intersection between the geometry and the line segment {@code l}.
     *
     * @param r The ray to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The V2D_Geometry.
     */
    public V2D_FiniteGeometry getIntersect(V2D_Ray r, int oom, RoundingMode rm) {
        V2D_FiniteGeometry gpqr = pqr.getIntersect(r, oom, rm);
        V2D_FiniteGeometry grsp = rsp.getIntersect(r, oom, rm);
        if (gpqr == null) {
            return grsp;
        } else {
            if (grsp == null) {
                return gpqr;
            }
            return join(oom, rm, gpqr, grsp);
        }
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The minimum distance to {@code l}.
     */
    public BigRational getDistance(V2D_Line l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom, rm), oom, rm).getSqrt(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The minimum distance to {@code l}.
     */
    public BigRational getDistanceSquared(V2D_Line l, int oom, RoundingMode rm) {
        return BigRational.min(
                rsp.getDistanceSquared(l, oom, rm),
                pqr.getDistanceSquared(l, oom, rm));
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The minimum distance to {@code l}.
     */
    public BigRational getDistance(V2D_LineSegment l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom, rm), oom, rm).getSqrt(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The minimum distance to {@code l}.
     */
    public BigRational getDistanceSquared(V2D_LineSegment l, int oom, RoundingMode rm) {
        return BigRational.min(
                rsp.getDistanceSquared(l, oom, rm),
                pqr.getDistanceSquared(l, oom, rm));
    }

    /**
     * Get the minimum distance to {@code t}.
     *
     * @param t A triangle.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The minimum distance squared to {@code t}.
     */
    public BigRational getDistance(V2D_Triangle t, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(t, oom, rm), oom, rm).getSqrt(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code t}.
     *
     * @param t A triangle.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The minimum distance squared to {@code t}.
     */
    public BigRational getDistanceSquared(V2D_Triangle t, int oom, RoundingMode rm) {
        return BigRational.min(
                rsp.getDistanceSquared(t, oom, rm),
                pqr.getDistanceSquared(t, oom, rm));
    }

    /**
     * @param r The rectangle to test if it is equal to this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff this is equal to r.
     */
    public boolean equals(V2D_Rectangle r, int oom, RoundingMode rm) {
        V2D_Point[] pts = getPointsArray(oom, rm);
        V2D_Point[] rpts = r.getPointsArray(oom, rm);
        for (var x : pts) {
            boolean found = false;
            for (var y : rpts) {
                if (x.equals(y, oom, rm)) {
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
                if (x.equals(y, oom, rm)) {
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff pl, qv, r and s form a rectangle.
     */
    public static boolean isRectangle(V2D_Point p, V2D_Point q,
            V2D_Point r, V2D_Point s, int oom, RoundingMode rm) {
        V2D_LineSegment pq = new V2D_LineSegment(p, q, oom, rm);
        V2D_LineSegment qr = new V2D_LineSegment(q, r, oom, rm);
        V2D_LineSegment rs = new V2D_LineSegment(r, s, oom, rm);
        V2D_LineSegment sp = new V2D_LineSegment(s, p, oom, rm);
        if (pq.l.isParallel(rs.l, oom, rm)) {
            if (qr.l.isParallel(sp.l, oom, rm)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Identify if {@code this} is intersected by {@code aabb}.
     * 
     * @param aabb The envelope to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code this} is intersected by {@code aabb}.
     */
    public boolean intersects(V2D_AABB aabb, int oom, RoundingMode rm) {
        if (pqr.intersects(aabb, oom, rm)) {
            return true;
        }
        return rsp.intersects(aabb, oom, rm);
    }
}
