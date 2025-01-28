/*
 * Copyright 2022 Andy Turner, University of Leeds.
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v2d.geometrics.V2D_Geometrics;
import uk.ac.leeds.ccg.v2d.geometrics.V2D_SortByCentroid;

/**
 * A class for representing and using coplanar convex hulls. These are a special
 * type of polygon: They have no holes and all the angles are convex. Below is a
 * basic algorithm for generating a convex hull from a set of coplanar points
 * known as the "quick hull" algorithm (see
 * <a href="https://en.wikipedia.org/wiki/Quickhull">
 * https://en.wikipedia.org/wiki/Quickhull</a>) :
 * <ol>
 * <li>Partition the points:
 * <ul>
 * <li>Calculate the distances between the points with the minimum and maximum
 * x, the minimum and maximum y, and the minimum and maximum z values.</li>
 * <li>Choose the points that have the largest distance between them to define
 * the dividing plane that is orthogonal to the plane of the polygon.</li>
 * <li>Let the points on one side of the dividing plane be one group and those
 * on the other be the another group.</li>
 * </ul></li>
 * <li>Add the two end points of the partition to the convex hull.</li>
 * <li>Deal with each group of points in turn.</li>
 * <li>If there is only one other point on a side of the partition then add it
 * to the convex hull.</li>
 * <li>If there are more than one, then find the one with the biggest distance
 * from the partition and add this to the convex hull.</li>
 * <li>We can now ignore all the other points that intersect the triangle given
 * by the 3 points now in the convex hull.</li>
 * <li>Create a new plane dividing the remaining points on this side of the
 * first dividing plane. Two points on the plane are the last point added to the
 * convex hull and the closest point on the line defined by the other two points
 * in the convex hull. The new dividing plane is orthogonal to the first
 * dividing plane.</li>
 * <li>Let the points in this group that are on one side of the dividing plane
 * be another group and those on the other be the another group.</li>
 * <li>Repeat the process dealing with each group in turn (Steps 3 to 9) in a
 * depth first manner.</li>
 * </ol>
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_ConvexHull extends V2D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * The collection of triangles.
     */
    protected final ArrayList<V2D_Triangle> triangles;

    /**
     * The collection of points.
     */
    protected final ArrayList<V2D_Point> points;

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
     * Create a new instance.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param points A non-empty list of points in a plane given by n.
     */
    public V2D_ConvexHull(int oom, RoundingMode rm, V2D_Point... points) {
        super();
        ArrayList<V2D_Point> uniquePoints = V2D_Point.getUnique(Arrays.asList(points), oom, rm);
        V2D_Point[] up = new V2D_Point[uniquePoints.size()];
        up = uniquePoints.toArray(up);
        V2D_Point centroid = V2D_Geometrics.getCentroid(oom, rm, up);
        V2D_SortByCentroid sbc = new V2D_SortByCentroid(centroid, oom, rm);
        Arrays.sort(up, sbc);
        this.points = new ArrayList<>(Arrays.asList(up));
        this.triangles = new ArrayList<>();
    }

    /**
     * Create a new instance.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param gs The input convex hulls.
     */
    public V2D_ConvexHull(int oom, RoundingMode rm, V2D_ConvexHull... gs) {
        this(oom, rm, V2D_FiniteGeometry.getPoints(gs));
    }

    /**
     * Create a new instance.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param ch The convex hull to add to the convex hull with t.
     * @param t The triangle used to set the normal and to add to the convex
     * hull with ch.
     */
    public V2D_ConvexHull(int oom, RoundingMode rm, V2D_ConvexHull ch, V2D_Triangle t) {
        this(oom, rm, V2D_FiniteGeometry.getPoints(ch, t));
    }

    @Override
    public V2D_Point[] getPoints() {
        int np = points.size();
        V2D_Point[] re = new V2D_Point[np];
        for (int i = 0; i < np; i++) {
            re[i] = new V2D_Point(points.get(i));
        }
        return re;
    }

    @Override
    public String toString() {
        String s = this.getClass().getName() + "(";
        Iterator<V2D_Point> ite = points.iterator();
        s += ite.next().toString();
        while (ite.hasNext()) {
            s += ", " + ite.next();
        }
        s += ")";
        return s;
    }

    /**
     * @return Simple string representation.
     */
    public String toStringSimple() {
        String s = this.getClass().getName() + "(";
        Iterator<V2D_Point> ite = points.iterator();
        s += ite.next().toString();
        while (ite.hasNext()) {
            s += ", " + ite.next().toStringSimple(s);
        }
        s += ")";
        return s;
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
        for (var x : points) {
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
                for (var x : points) {
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
    public V2D_Envelope getEnvelope(int oom) {
        if (en == null) {
            en = points.get(0).getEnvelope(oom);
            for (int i = 1; i < points.size(); i++) {
                en = en.union(points.get(i).getEnvelope(oom), oom);
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
        // Check envelopes intersect.
        if (getEnvelope(oom).isIntersectedBy(pt, oom, rm)) {
                // Check point is in a triangle
                for (var t : triangles) {
                    if (t.isAligned(pt, oom, rm)) {
                        return true;
                    }
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
        for (V2D_Triangle triangle : triangles) {
            if (triangle.isAligned(pt, oom, rm)) {
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
        for (var t : triangles) {
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
            ts.addAll(Arrays.asList(i.getPoints()));
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
        V2D_Triangle[] rts = new V2D_Triangle[triangles.size()];
        for (int i = 0; i < triangles.size(); i++) {
            rts[0] = triangles.get(i).rotate(pt, theta, bd, oom, rm);
        }
        return new V2D_ConvexHull(oom, rm, rts);
    }
//
//    /**
//     *
//     * @param pts
//     * @param p0
//     * @param p1
//     * @param n
//     * @param index
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode for any rounding.
//     * @return the number of points added.
//     */
//    private void compute(ArrayList<V2D_Point> pts, V2D_Point p0, V2D_Point p1,
//            int index, int oom, RoundingMode rm) {
//        AB ab = new AB(pts, p0, p1, oom, rm);
//        {
//            // Process ab.a
//            if (!ab.a.isEmpty()) {
//                if (ab.a.size() > 1) {
//                    V2D_Point apt = ab.a.get(ab.maxaIndex);
//                    points.add(index, apt);
//                    index++;
//                    V2D_Triangle atr = new V2D_Triangle(p0, p1, apt, oom, rm);
//                    TreeSet<Integer> removeIndexes = new TreeSet<>();
//                    for (int i = 0; i < ab.a.size(); i++) {
//                        if (atr.isIntersectedBy(ab.a.get(i), oom, rm)) {
//                            removeIndexes.add(i);
//                            //index--;
//                        }
//                        Iterator<Integer> ite = removeIndexes.descendingIterator();
//                        while (ite.hasNext()) {
//                            ab.a.remove(ite.next().intValue());
//                        }
//                        if (!ab.a.isEmpty()) {
//                            if (ab.a.size() > 1) {
//                                // Divide again
//                                V2D_Line l = new V2D_Line(p0, p1, oom, rm);
//                                V2D_Point proj = l.getPointOfIntersection(apt, oom, rm);
//                                compute(ab.a, proj, apt, index, oom, rm);
//                            } else {
//                                points.add(index, ab.a.get(0));
//                                index++;
//                            }
//                        }
//                    }
//                } else {
//                    points.add(index, ab.a.get(0));
//                    index++;
//                }
//            }
//        }
//        {
//            // Process ab.b
//            if (!ab.b.isEmpty()) {
//                if (ab.b.size() > 1) {
//                    V2D_Point bpt = ab.b.get(ab.maxbIndex);
//                    points.add(index, bpt);
//                    index++;
//                    V2D_Triangle btr = new V2D_Triangle(p0, p1, bpt, oom, rm);
//                    TreeSet<Integer> removeIndexes = new TreeSet<>();
//                    for (int i = 0; i < ab.b.size(); i++) {
//                        if (btr.isIntersectedBy(ab.b.get(i), oom, rm)) {
//                            removeIndexes.add(i);
//                            //index--;
//                        }
//                    }
//                    Iterator<Integer> ite = removeIndexes.descendingIterator();
//                    while (ite.hasNext()) {
//                        ab.b.remove(ite.next().intValue());
//                    }
//                    if (!ab.b.isEmpty()) {
//                        if (ab.b.size() > 1) {
//                            // Divide again
//                            V2D_Line l = new V2D_Line(p0, p1, oom, rm);
//                            V2D_Point proj = l.getPointOfIntersection(bpt, oom, rm);
//                            compute(ab.b, bpt, proj, index, oom, rm);
//                        } else {
//                            points.add(index, ab.b.get(0));
//                        }
//                    }
//                } else {
//                    points.add(index, ab.b.get(0));
//                }
//            }
//        }
//    }

    @Override
    public boolean isIntersectedBy(V2D_Envelope aabb, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

//    /**
//     * A class for helping to calculate a convex hull.
//     */
//    public class AB {
//
//        /**
//         * The points that are above the plane.
//         */
//        ArrayList<V2D_Point> a;
//
//        /**
//         * For storing the index of the point in {@link #a} that is the maximum
//         * distance from the plane.
//         */
//        int maxaIndex;
//
//        /**
//         * The points that are below the plane.
//         */
//        ArrayList<V2D_Point> b;
//
//        /**
//         * For storing the index of the point in {@link #b} that is the maximum
//         * distance from the plane.
//         */
//        int maxbIndex;
//
//        /**
//         * Create a new instance.
//         *
//         * @param pts The points.
//         * @param p0 One end of the line segment dividing the convex hull.
//         * @param p1 One end of the line segment dividing the convex hull.
//         * @param oom The Order of Magnitude for the precision.
//         * @param rm The RoundingMode for any rounding.
//         */
//        public AB(ArrayList<V2D_Point> pts, V2D_Point p0, V2D_Point p1,
//                int oom, RoundingMode rm) {
//            // Find points above and below the plane.
//            // Points that are not on the plane.
//            ArrayList<V2D_Point> no = new ArrayList<>();
//            for (int i = 0; i < pts.size(); i++) {
//                V2D_Point pt = pts.get(i);
//                no.add(pt);
//            }
//            no = V2D_Point.getUnique(no, oom, rm);
//
//            if (no.size() < 1) {
//                int debug = 1;
//            }
//
//            // Go through points that are not on the plane.
//            a = new ArrayList<>();
//            b = new ArrayList<>();
//            V2D_Point pt0 = no.get(0);
//            BigRational maxads = plane.getDistanceSquared(pt0, oom, rm);
//            BigRational maxbds = BigRational.ZERO;
//            a.add(pt0);
//            maxaIndex = 0;
//            maxbIndex = -1;
//            for (int i = 1; i < no.size(); i++) {
//                V2D_Point pt = no.get(i);
//                BigRational ds = plane.getDistanceSquared(pt, oom, rm);
//                if (plane.isOnSameSide(pt0, pt, oom, rm)) {
//                    a.add(pt);
//                    if (ds.compareTo(maxads) == 1) {
//                        maxads = ds;
//                        maxaIndex = a.size() - 1;
//                    }
//                } else {
//                    b.add(pt);
//                    if (ds.compareTo(maxbds) == 1) {
//                        maxbds = ds;
//                        maxbIndex = b.size() - 1;
//                    }
//                }
//            }
//        }
//    }

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
 then a V2D_LineSegment is returned. Three different, then a V2D_Triangle
 is returned. If four or more are different then a V2D_ConvexHull
 is returned.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param pts The points.
     * @return Either a V2D_Point, V2D_LineSegment, V2D_Triangle, or
 V2D_ConvexHull.
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

}
