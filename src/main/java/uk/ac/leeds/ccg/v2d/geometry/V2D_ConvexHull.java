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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;

/**
 * For representing a convex hulls - convex shapes with no holes.
 *
 * @author Andy Turner
 * @version 2.0
 */
public class V2D_ConvexHull extends V2D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * The collection of points in a clockwise order.
     */
    public HashMap<Integer, V2D_Point> points;

    /**
     * The collection of edges. These are the points in clockwise order as a
     * linear ring. Keys are IDs
     */
    public HashMap<Integer, V2D_LineSegment> edges;

    /**
     * For storing the contiguous triangles.
     */
    public ArrayList<V2D_Triangle> triangles;

    /**
     * Create a new instance.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param triangles A non-empty list of coplanar triangles.
     */
    public V2D_ConvexHull(int oom, RoundingMode rm, V2D_Triangle... triangles) {
        this(oom, rm, V2D_Triangle.getPoints(triangles, oom, rm));
    }
    
    /**
     * Create a new instance. An algorithm for generating a convex hull from a
     * set of coplanar points known as the "quick hull" algorithm (see
     * <a href="https://en.wikipedia.org/wiki/Quickhull">
     * https://en.wikipedia.org/wiki/Quickhull</a>) :
     * <ol>
     * <li>Partition the points:
     * <ul>
     * <li>Calculate the distances between the points with the minimum and
     * maximum x, the minimum and maximum y, and the minimum and maximum z
     * values.</li>
     * <li>Choose the points that have the largest distance between them to
     * define the dividing plane that is orthogonal to the plane of the
     * polygon.</li>
     * <li>Let the points on one side of the dividing plane be one group and
     * those on the other be the another group.</li>
     * </ul></li>
     * <li>Add the two end points of the partition to the convex hull.</li>
     * <li>Deal with each group of points in turn.</li>
     * <li>If there is only one other point on a side of the partition then add
     * it to the convex hull.</li>
     * <li>If there are more than one, then find the one with the biggest
     * distance from the partition and add this to the convex hull.</li>
     * <li>We can now ignore all the other points that intersect the triangle
     * given by the 3 points now in the convex hull.</li>
     * <li>Create a new plane dividing the remaining points on this side of the
     * first dividing plane. Two points on the plane are the last point added to
     * the convex hull and the closest point on the line defined by the other
     * two points in the convex hull. The new dividing plane is orthogonal to
     * the first dividing plane.</li>
     * <li>Let the points in this group that are on one side of the dividing
     * plane be another group and those on the other be the another group.</li>
     * <li>Repeat the process dealing with each group in turn (Steps 3 to 9) in
     * a depth first manner.</li>
     * </ol>
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param points The points from which to construct the convex hull. There
     * must be at least three non-linear points.
     */
    public V2D_ConvexHull(int oom, RoundingMode rm, V2D_Point... points) {
        super();
        ArrayList<V2D_Point> h = new ArrayList<>();
        ArrayList<V2D_Point> uniquePoints = V2D_Point.getUnique(Arrays.asList(points), oom, rm);
        //uniquePoints.sort(V2D_Point::compareTo);
        uniquePoints.sort((p1, p2) -> p1.compareTo(p2, oom, rm));
        // Compute convex hull
        // https://rosettacode.org/wiki/Convex_hull#Java
        // lower hull
        for (V2D_Point pt : uniquePoints) {
            while (h.size() >= 2 && !ccw(h.get(h.size() - 2), h.get(h.size() - 1), pt, oom, rm)) {
                h.remove(h.size() - 1);
            }
            h.add(pt);
        }
        // upper hull
        int t = h.size() + 1;
        for (int i = uniquePoints.size() - 1; i >= 0; i--) {
            V2D_Point pt = uniquePoints.get(i);
            while (h.size() >= t && !ccw(h.get(h.size() - 2), h.get(h.size() - 1), pt, oom, rm)) {
                h.remove(h.size() - 1);
            }
            h.add(pt);
        }
        ArrayList<V2D_Point> ups = V2D_Point.getUnique(h, oom, rm);        
        this.points = new HashMap<>();
        for (var p: ups) {
            this.points.put(this.points.size(), p);
        }
    }

    // ccw returns true if the three points make a counter-clockwise turn
    private static boolean ccw(V2D_Point a, V2D_Point b, V2D_Point c, int oom, RoundingMode rm) {
        BigRational ax = a.getX(oom, rm);
        BigRational ay = a.getY(oom, rm);
        return ((b.getX(oom, rm).subtract(ax)).multiply(c.getY(oom, rm).subtract(ay)))
                .compareTo((b.getY(oom, rm).subtract(ay)).multiply(c.getX(oom, rm).subtract(ax))) == 1;
    }

    /**
     * Create a new instance.
     *
     * @param ch The input convex hull.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V2D_ConvexHull(V2D_ConvexHull ch, int oom, RoundingMode rm) {
        this(oom, rm, V2D_FiniteGeometry.getPoints(oom, rm, ch));
    }

    /**
     * Create a new instance.
     *
     * @param ch The convex hull to add to the convex hull with t.
     * @param t The triangle to add to the convex hull with ch.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V2D_ConvexHull(V2D_ConvexHull ch, V2D_Triangle t, int oom, RoundingMode rm) {
        this(oom, rm, V2D_FiniteGeometry.getPoints(oom, rm, ch, t));
    }

    @Override
    public V2D_Point[] getPoints(int oom, RoundingMode rm) {
        int np = points.size();
        V2D_Point[] pts = new V2D_Point[np];
        for (int i = 0; i < np; i++) {
            pts[i] = new V2D_Point(points.get(i));
        }
        return pts;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(this.getClass().getName()).append("(");
        {
            s.append("\npoints (\n");
                for(var entry : points.entrySet()) {
                    s.append("(");
                    s.append(entry.getKey());
                    s.append(",");
                    s.append(entry.getValue().toString());
                    s.append("), ");
                }
            int l = s.length();
            s = s.delete(l - 2, l);
            s.append("\n)\n");
        }
        s.append("\n)");
        return s.toString();
    }

    /**
     * @return Simple string representation.
     */
    public String toStringSimple() {
        StringBuilder s = new StringBuilder();
        s.append(this.getClass().getName()).append("(");
        {
            s.append("points (");
                for(var entry : points.entrySet()) {
                    s.append("(");
                    s.append(entry.getKey());
                    s.append(",");
                    s.append(entry.getValue().toString());
                    s.append("), ");
                }
            int l = s.length();
            s = s.delete(l - 2, l);
            s.append(")");
        }
        s.append(")");
        return s.toString();
    }

    /**
     * Check if {@code this} is equal to {@code i}.
     *
     * @param c An instance to compare for equality.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff all the triangles are the same.
     */
    public boolean equals(V2D_ConvexHull c, int oom, RoundingMode rm) {
        HashSet<Integer> indexes = new HashSet<>();
        for (var x : points.values()) {
            boolean found = false;
            for (int i = 0; i < c.points.size(); i++) {
                if (x.equals(c.points.get(i), oom, rm)) {
                    found = true;
                    indexes.add(i);
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        for (int i = 0; i < c.points.size(); i++) {
            if (!indexes.contains(i)) {
                boolean found = false;
                for (var x : points.values()) {
                    if (x.equals(c.points.get(i), oom, rm)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public V2D_Envelope getEnvelope(int oom, RoundingMode rm) {
        if (en == null) {
            en = points.get(0).getEnvelope(oom, rm);
            for (int i = 1; i < points.size(); i++) {
                en = en.union(points.get(i).getEnvelope(oom, rm), oom);
            }
        }
        return en;
    }

    /**
     * If this is effectively a triangle, the triangle is returned. If this is
     * effectively a rectangle, the rectangle is returned. Otherwise this is
     * returned.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return Either a triangle, rectangle or this.
     */
    public V2D_FiniteGeometry simplify(int oom, RoundingMode rm) {
        if (isTriangle()) {
            return new V2D_Triangle(points.get(0), points.get(1),
                    points.get(2), oom, rm);
        } else if (isRectangle(oom, rm)) {
            return new V2D_Rectangle(points.get(0), points.get(2),
                    points.get(1), points.get(3), oom, rm);
        } else {
            return this;
        }
    }

    /**
     * Identify if this is intersected by point {@code p}.
     *
     * @param pt The point to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    public boolean isIntersectedBy(V2D_Point pt, int oom, RoundingMode rm) {
        if (getEnvelope(oom, rm).isIntersectedBy(pt, oom, rm)) {
            return getTriangles(oom, rm).parallelStream().anyMatch(x -> x.isIntersectedBy(pt, oom, rm));
        }
        return false;
    }

    /**
     * Identify if this is intersected by point {@code p}.
     *
     * @param l The line segment to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    public boolean isIntersectedBy(V2D_LineSegment l, int oom, RoundingMode rm) {
        if (getEnvelope(oom, rm).isIntersectedBy(l.getEnvelope(oom, rm), oom)) {
            return getTriangles(oom, rm).parallelStream().anyMatch(x -> x.isIntersectedBy(l, oom, rm));
        }
        return false;
    }

    /**
     * Identify if this is intersected by point {@code p}.
     *
     * @param t The triangle to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    public boolean isIntersectedBy(V2D_Triangle t, int oom, RoundingMode rm) {
        // Check envelopes intersect.
        if (t.isIntersectedBy(getEnvelope(oom, rm), oom, rm)) {
            if (isIntersectedBy(t.getEnvelope(oom, rm), oom, rm)) {
                return getTriangles(oom, rm).parallelStream().anyMatch(x -> x.isIntersectedBy(t, oom, rm));
            }
        }
        return false;
    }

    /**
     * Identify if this is intersected by point {@code p}.
     *
     * @param r The rectangle to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    public boolean isIntersectedBy(V2D_Rectangle r, int oom, RoundingMode rm) {
        if (r.isIntersectedBy(getEnvelope(oom, rm), oom, rm)) {
            if (isIntersectedBy(r.getEnvelope(oom, rm), oom, rm)) {
                return getTriangles(oom, rm).parallelStream().anyMatch(x -> r.isIntersectedBy(x, oom, rm));
            }
        }
        return false;
    }

    /**
     * Identify if this is intersected by point {@code p}.
     *
     * @param ch The convex hull to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff the geometry is intersected by {@code ch.
     */
    public boolean isIntersectedBy(V2D_ConvexHull ch, int oom, RoundingMode rm) {
        if (ch.isIntersectedBy(getEnvelope(oom, rm), oom, rm)) {
            if (isIntersectedBy(ch.en, oom, rm)) {
                return getTriangles(oom, rm).parallelStream().anyMatch(x -> ch.isIntersectedBy(x, oom, rm));
            }
        }
        return false;
    }

    /**
     * @param pt The point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if the point is aligned with any of the parts.
     */
    protected boolean isAligned(V2D_Point pt, int oom, RoundingMode rm) {
        for (V2D_Triangle t : getTriangles(oom, rm)) {
            if (t.isAligned(pt, oom, rm)) {
                return true;
            }
        }
        return false;
        //return triangles.parallelStream().anyMatch(t -> (t.isIntersectedBy0(pt, oom)));
    }

    /**
     * This sums all the areas irrespective of any overlaps.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The area of the triangle (rounded).
     */
    public BigRational getArea(int oom, RoundingMode rm) {
        BigRational sum = BigRational.ZERO;
        for (var t : getTriangles(oom, rm)) {
            sum = sum.add(t.getArea(oom, rm));
        }
        return sum;
    }

//    /**
//     * This sums all the perimeters irrespective of any overlaps.
//     *
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode for any rounding.
//     */
//    public BigRational getPerimeter(int oom, RoundingMode rm) {
//        BigRational sum = BigRational.ZERO;
//        for (var t : triangles) {
//            sum = sum.add(t.getPerimeter(oom, rm));
//        }
//        return sum;
//    }
    /**
     * Get the intersection between the geometry and the triangle {@code t}.
     *
     * @param t The triangle to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V2D_Geometry.
     */
    public V2D_FiniteGeometry getIntersection(V2D_Triangle t, int oom, RoundingMode rm) {
        // Create a set all the intersecting triangles from this.
        List<V2D_Point> ts = new ArrayList<>();
        for (V2D_Triangle t2 : triangles) {
            V2D_FiniteGeometry i = t2.getIntersection(t, oom, rm);
            ts.addAll(Arrays.asList(i.getPoints(oom, rm)));
        }
        ArrayList<V2D_Point> tsu = V2D_Point.getUnique(ts, oom, rm);
        if (tsu.isEmpty()) {
            return null;
        } else {
            return new V2D_ConvexHull(oom, rm,
                    tsu.toArray(V2D_Point[]::new)).simplify(oom, rm);
        }
//        switch (size) {
//            case 0:
//                return null;
//            case 1:
//                return t2s.iterator().next();
//            case 2:
//                Iterator<V2D_Triangle> ite = t2s.iterator();
//                return getGeometry(ite.next(), ite.next(), oom);
//            default:
//                return getGeometry(oom, t2s.toArray(V2D_Triangle[]::new));
    }

//    @Override
//    public boolean isEnvelopeIntersectedBy(V2D_Line l, int oom) {
//        return getEnvelope().isIntersectedBy(l, oom);
//    }
    @Override
    public V2D_ConvexHull rotate(V2D_Point pt, BigRational theta,
            Math_BigDecimal bd, int oom, RoundingMode rm) {
        theta = Math_AngleBigRational.normalise(theta, bd, oom, rm);
        if (theta.compareTo(BigRational.ZERO) == 0d) {
            return new V2D_ConvexHull(this, oom, rm);
        } else {
            return rotateN(pt, theta, bd, oom, rm);
        }
    }

    @Override
    public V2D_ConvexHull rotateN(V2D_Point pt, BigRational theta,
            Math_BigDecimal bd, int oom, RoundingMode rm) {
        V2D_Point[] pts = new V2D_Point[points.size()];
        for (int i = 0; i < points.size(); i++) {
            pts[0] = points.get(i).rotateN(pt, theta, bd, oom, rm);
        }
        return new V2D_ConvexHull(oom, rm, V2D_Triangle.getPoints(pts, oom, rm));
    }
    
    @Override
    public boolean isIntersectedBy(V2D_Envelope aabb, int oom, RoundingMode rm) {
        if (getEnvelope(oom, rm).isIntersectedBy(aabb, oom)) {
            return getTriangles(oom, rm).parallelStream().anyMatch(x -> x.isIntersectedBy(aabb, oom, rm));
        }
        return false;
    }

    /**
     * If all {@link #triangles} form a single triangle return true
     *
     * @return {@code true} iff this is a triangle.
     */
    public final boolean isTriangle() {
        return points.size() == 3;
    }

    /**
     * If all {@link #triangles} form a single triangle return true
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff this is a rectangle.
     */
    public boolean isRectangle(int oom, RoundingMode rm) {
        if (points.size() == 4) {
            return V2D_Rectangle.isRectangle(points.get(0),
                    points.get(1), points.get(2), points.get(3), oom, rm);
        }
        return false;
    }

//    /**
//     * Clips this using the pl and return the part that is on the same side as
//     * pl.
//     *
//     * @param pl The plane that clips.
//     * @param p A point that is used to return the side of the clipped triangle.
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode for any rounding.
//     * @return null, the whole or a part of this.
//     */
//    public V2D_FiniteGeometry clip(V2D_Plane pl, V2D_Point p, int oom, RoundingMode rm) {
//        V2D_FiniteGeometry i = getIntersection(pl, oom, rm);
//        if (i == null) {
//            V2D_Point pp = this.triangles.get(0).getPl(oom, rm).getP();
//            if (pl.isOnSameSide(pp, p, oom, rm)) {
//                return this;
//            } else {
//                return null;
//            }
//        } else if (i instanceof V2D_Point ip) {
//            if (pl.isOnSameSide(ip, p, oom, rm)) {
//                V2D_Point pp = this.triangles.get(0).getPl(oom, rm).getP();
//                if (pl.isOnSameSide(pp, p, oom, rm)) {
//                    return this;
//                } else {
//                    return ip;
//                }
//            } else {
//                return null;
//            }
//        } else {
//            // i instanceof V2D_LineSegment
//            V2D_LineSegment il = (V2D_LineSegment) i;
//            ArrayList<V2D_Point> pts = new ArrayList<>();
//            for (V2D_Point pt : points) {
//                if (pl.isOnSameSide(pt, p, oom, rm)) {
//                    pts.add(pt);
//                }
//            }
//            if (pts.isEmpty()) {
//                return il;
//            } else {
//                return new V2D_ConvexHull(oom, rm,
//                        this.triangles.get(0).getPl(oom, rm).n,
//                        pts.toArray(V2D_Point[]::new));
//            }
//        }
//    }
//
//    /**
//     * Clips this using t.
//     *
//     * @param t The triangle to clip this with.
//     * @param pt A point that is used to return the side of this that is
//     * clipped.
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode for any rounding.
//     * @return null, the whole or a part of this.
//     */
//    public V2D_FiniteGeometry clip(V2D_Triangle t, V2D_Point pt, int oom, RoundingMode rm) {
//        V2D_Point tp = t.getP();
//        V2D_Point tq = t.getQ();
//        V2D_Point tr = t.getR();
//        V2D_Vector n = t.getPl(oom, rm).n;
//        V2D_Point pp = new V2D_Point(tp.offset.add(n, oom, rm), tp.rel);
//        V2D_Plane ppl = new V2D_Plane(tp, tq, pp, oom, rm);
//        V2D_Point qp = new V2D_Point(tq.offset.add(n, oom, rm), tq.rel);
//        V2D_Plane qpl = new V2D_Plane(tq, tr, qp, oom, rm);
//        V2D_Point rp = new V2D_Point(tr.offset.add(n, oom, rm), tr.rel);
//        V2D_Plane rpl = new V2D_Plane(tr, tp, rp, oom, rm);
//        V2D_FiniteGeometry cppl = clip(ppl, tr, oom, rm);
//        if (cppl == null) {
//            return null;
//        } else if (cppl instanceof V2D_Point) {
//            return cppl;
//        } else if (cppl instanceof V2D_LineSegment cppll) {
//            V2D_FiniteGeometry cppllcqpl = cppll.clip(qpl, pt, oom, rm);
//            if (cppllcqpl == null) {
//                return null;
//            } else if (cppllcqpl instanceof V2D_Point) {
//                return cppllcqpl;
//            } else {
//                return ((V2D_LineSegment) cppllcqpl).clip(rpl, pt, oom, rm);
//            }
//        } else if (cppl instanceof V2D_Triangle cpplt) {
//            V2D_FiniteGeometry cppltcqpl = cpplt.clip(qpl, pt, oom, rm);
//            if (cppltcqpl == null) {
//                return null;
//            } else if (cppltcqpl instanceof V2D_Point) {
//                return cppltcqpl;
//            } else if (cppltcqpl instanceof V2D_LineSegment cppltcqpll) {
//                return cppltcqpll.clip(rpl, pt, oom, rm);
//            } else if (cppltcqpl instanceof V2D_Triangle cppltcqplt) {
//                return cppltcqplt.clip(rpl, pt, oom, rm);
//            } else {
//                V2D_ConvexHull c = (V2D_ConvexHull) cppltcqpl;
//                return c.clip(rpl, tq, oom, rm);
//            }
//        } else {
//            V2D_ConvexHull c = (V2D_ConvexHull) cppl;
//            V2D_FiniteGeometry cc = c.clip(qpl, pt, oom, rm);
//            if (cc == null) {
//                return cc;
//            } else if (cc instanceof V2D_Point) {
//                return cc;
//            } else if (cc instanceof V2D_LineSegment cppll) {
//                V2D_FiniteGeometry cccqpl = cppll.clip(qpl, pt, oom, rm);
//                if (cccqpl == null) {
//                    return null;
//                } else if (cccqpl instanceof V2D_Point) {
//                    return cccqpl;
//                } else {
//                    return ((V2D_LineSegment) cccqpl).clip(rpl, pt, oom, rm);
//                }
//            } else if (cc instanceof V2D_Triangle ccct) {
//                return ccct.clip(rpl, tq, oom, rm);
//            } else {
//                V2D_ConvexHull ccc = (V2D_ConvexHull) cc;
//                return ccc.clip(rpl, pt, oom, rm);
//            }
//        }
//    }
    /**
     * If pts are all equal then a V2D_Point is returned.If two are different,
     * then a V2D_LineSegment is returned. Three different, then a V2D_Triangle
     * is returned. If four or more are different then a V2D_ConvexHull is
     * returned.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param pts The points.
     * @return Either a V2D_Point, V2D_LineSegment, V2D_Triangle, or
     * V2D_ConvexHull.
     */
    public static V2D_FiniteGeometry getGeometry(int oom, RoundingMode rm, V2D_Point... pts) {
        ArrayList<V2D_Point> upts = V2D_Point.getUnique(Arrays.asList(pts), oom, rm);
        Iterator<V2D_Point> i = upts.iterator();
        switch (upts.size()) {
            case 1 -> {
                return i.next();
            }
            case 2 -> {
                return new V2D_LineSegment(i.next(), i.next(), oom, rm);
            }
            case 3 -> {
                return new V2D_Triangle(i.next(), i.next(), i.next(), oom, rm);
            }
            default -> {
                V2D_Point ip = i.next();
                V2D_Point iq = i.next();
                V2D_Point ir = i.next();
                while (V2D_Line.isCollinear(oom, rm, ip, iq, ir) && i.hasNext()) {
                    ir = i.next();
                }
                if (V2D_Line.isCollinear(oom, rm, ip, iq, ir)) {
                    return new V2D_LineSegment(oom, rm, pts);
                } else {
                    return new V2D_ConvexHull(oom, rm, pts);
                }
            }
        }
    }

    /**
     * Returns the contiguous triangles constructing them first if necessary.
     * 
     * This implementation does not compute a centroid and alternates between
     * clockwise and anticlockwise to fill the space with triangles.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A list of triangles that make up the convex hull.
     */
    public ArrayList<V2D_Triangle> getTriangles(int oom, RoundingMode rm) {
        if (triangles == null) {
            triangles = new ArrayList<>();
            V2D_Point[] ps = getPoints(oom, rm);
            V2D_Point p0 = ps[0];
            V2D_Point p1 = ps[1];
            for (int i = 2; i < ps.length; i++) {
                V2D_Point p2 = ps[i];
                triangles.add(new V2D_Triangle(p0, p1, p2, oom, rm));
                p1 = p2;
            }
        }
        return triangles;
    }

    /**
     * Returns the edges constructing them first if necessary.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A list of triangles that make up the convex hull.
     */
    public HashMap<Integer, V2D_LineSegment> getEdges(int oom, RoundingMode rm) {
        if (edges == null) {
            edges = new HashMap<>();
            V2D_Point p0 = this.points.get(0);
            V2D_Point p1 = this.points.get(1);
            this.edges.put(this.edges.size(), new V2D_LineSegment(p0, p1, oom, rm));
            for (int i = 2; i < this.points.size(); i++) {
                p0 = p1;
                p1 = this.points.get(i);
                this.edges.put(this.edges.size(), new V2D_LineSegment(p0, p1, oom, rm));
            }
            edges.put(this.edges.size(),new V2D_LineSegment(p1, this.points.get(0), oom, rm));
        }
        return edges;
    }
}
