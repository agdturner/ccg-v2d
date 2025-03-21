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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import uk.ac.leeds.ccg.math.geometry.Math_AngleDouble;

/**
 * V2D_ConvexArea_d extends V2D_Area_d and is for representing a convex area
 * comprising one or more triangles sharing internal edges and with a convex
 * external edge. All points of convex areas are on the external edge.
 *
 * @author Andy Turner
 * @version 2.0
 */
public class V2D_ConvexArea_d extends V2D_Area_d {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the contiguous triangles.
     */
    public ArrayList<V2D_Triangle_d> triangles;

    /**
     * Create a new instance.
     *
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @param triangles A non-empty list of coplanar triangles.
     */
    public V2D_ConvexArea_d(double epsilon, V2D_Triangle_d... triangles) {
        this(epsilon, V2D_Triangle_d.getPoints(triangles));
    }

    /**
     * Create a new instance.
     *
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @param points A list of points with at least 3 that are non-coplanar.
     */
    public V2D_ConvexArea_d(double epsilon, V2D_Point_d... points) {
        this(epsilon, Arrays.asList(points));
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @param points A non-empty list of points in a plane given by n.
     */
    public V2D_ConvexArea_d(double epsilon, List<V2D_Point_d> points) {
        super(points.get(0).env, V2D_Vector_d.ZERO);
        ArrayList<V2D_Point_d> h = new ArrayList<>();
        ArrayList<V2D_Point_d> uniquePoints = V2D_Point_d.getUnique(
                points, epsilon);
        //uniquePoints.sort(V2D_Point_d::compareTo);
        uniquePoints.sort((p1, p2) -> p1.compareTo(p2));
        // Compute convex hull
        // https://rosettacode.org/wiki/Convex_hull#Java
        // lower hull
        for (V2D_Point_d pt : uniquePoints) {
            while (h.size() >= 2 && !ccw(h.get(h.size() - 2),
                    h.get(h.size() - 1), pt)) {
                h.remove(h.size() - 1);
            }
            h.add(pt);
        }
        // upper hull
        int t = h.size() + 1;
        for (int i = uniquePoints.size() - 1; i >= 0; i--) {
            V2D_Point_d pt = uniquePoints.get(i);
            while (h.size() >= t && !ccw(h.get(h.size() - 2), h.get(h.size() - 1), pt)) {
                h.remove(h.size() - 1);
            }
            h.add(pt);
        }
        ArrayList<V2D_Point_d> ups = V2D_Point_d.getUnique(h, epsilon);
        this.points = new HashMap<>();
        for (var p : ups) {
            this.points.put(this.points.size(), p);
        }
        // Add edge
        edges = new HashMap<>();
        V2D_Point_d p0 = this.points.get(0);
        for (int i = 1; i < this.points.size(); i++) {
            V2D_Point_d p1 = this.points.get(i);
            edges.put(edges.size(), new V2D_LineSegment_d(p0, p1));
            p0 = p1;
        }
        edges.put(edges.size(), new V2D_LineSegment_d(p0, this.points.get(0)));
    }

    // ccw returns true if the three points make a counter-clockwise turn
    private static boolean ccw(V2D_Point_d a, V2D_Point_d b,
            V2D_Point_d c) {
        double ax = a.getX();
        double ay = a.getY();
        return ((b.getX() - ax) * (c.getY() - ay))
                > ((b.getY() - ay) * (c.getX() - ax));
    }

    /**
     * Create a new instance.
     *
     * @param ch The convex hull.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V2D_ConvexArea_d(V2D_ConvexArea_d ch, double epsilon) {
        this(epsilon, ch.getPointsArray());
    }

    /**
     * Create a new instance.
     *
     * @param ch The convex hull to add to the convex hull with t.
     * @param t The triangle used to set the normal and to add to the convex
     * hull with ch.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V2D_ConvexArea_d(V2D_ConvexArea_d ch, V2D_Triangle_d t,
            double epsilon) {
        this(epsilon, V2D_FiniteGeometry_d.getPoints(ch, t));
    }

    @Override
    public V2D_Point_d[] getPointsArray() {
        return points.values().toArray(new V2D_Point_d[points.size()]);
    }

    @Override
    public HashMap<Integer, V2D_Point_d> getPoints() {
        return points;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(this.getClass().getName()).append("(\n");
        {
            s.append("points  (\n");
            for (var entry : points.entrySet()) {
                s.append(" (");
                s.append(entry.getKey());
                s.append(", ");
                s.append(entry.getValue().toString());
                s.append("),\n");
            }
            int l = s.length();
            s = s.delete(l - 2, l);
            s.append("\n )");
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
            for (var entry : points.entrySet()) {
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
     * Check if {@code this} is equal to {@code c}.
     *
     * @param c An instance to compare for equality.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff all the triangles are the same.
     */
    public boolean equals(V2D_ConvexArea_d c, double epsilon) {
        if (points.values().parallelStream().allMatch(x
                -> x.equalsAny(c.points.values(), epsilon))) {
            return c.points.values().parallelStream().allMatch(x
                    -> x.equalsAny(points.values(), epsilon));
        }
        return false;
//        HashSet<Integer> indexes = new HashSet<>();
//        for (var x : points.values()) {
//            boolean found = false;
//            for (int i = 0; i < c.points.size(); i++) {
//                if (x.equals(epsilon, c.points.get(i))) {
//                    found = true;
//                    indexes.add(i);
//                    break;
//                }
//            }
//            if (!found) {
//                return false;
//            }
//        }
//        for (int i = 0; i < c.points.size(); i++) {
//            if (!indexes.contains(i)) {
//                boolean found = false;
//                for (var x : points.values()) {
//                    if (x.equals(epsilon, c.points.get(i))) {
//                        found = true;
//                        break;
//                    }
//                }
//                if (!found) {
//                    return false;
//                }
//            }
//        }
//        return true;
    }

    @Override
    public V2D_AABB_d getAABB() {
        if (en == null) {
            en = points.get(0).getAABB();
            for (int i = 1; i < points.size(); i++) {
                en = en.union(points.get(i).getAABB());
            }
        }
        return en;
    }

    /**
     * If this is effectively a triangle, the triangle is returned. If this is
     * effectively a rectangle, the rectangle is returned. Otherwise this is
     * returned.
     *
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return Either a triangle, rectangle or this.
     */
    public V2D_FiniteGeometry_d simplify(double epsilon) {
        if (isTriangle()) {
            return new V2D_Triangle_d(points.get(0), points.get(1),
                    points.get(2));
        } else if (isRectangle(epsilon)) {
            return new V2D_Rectangle_d(points.get(0), points.get(2),
                    points.get(1), points.get(3));
        } else {
            return this;
        }
    }

    /**
     * Identify if this is intersected by point.
     *
     * @param pt The point to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} is intersected by {@code p}.
     */
    public boolean intersects(V2D_Point_d pt, double epsilon) {
        return getAABB().intersects(pt)
                && intersects0(pt, epsilon);
    }

    /**
     * Identify if this is intersected by point. This does not check the
     * envelope.
     *
     * @param pt The point to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} is intersected by {@code p}.
     */
    public boolean intersects0(V2D_Point_d pt, double epsilon) {
        return getTriangles().parallelStream().anyMatch(x
                -> x.intersects(pt, epsilon));
    }

    /**
     * Identify if {@code this} contains {@code p}.
     *
     * @param pt The point to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} contains {@code p}.
     */
    public boolean contains(V2D_Point_d pt, double epsilon) {
        return intersects(pt, epsilon)
                && !V2D_LineSegment_d.intersects(epsilon, pt,
                        edges.values());
    }

    /**
     * Identify if {@code this} contains {@code l}.
     *
     * @param l The line segment to test for containment with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} contains {@code l}.
     */
    public boolean contains(V2D_LineSegment_d l, double epsilon) {
        return intersects(l, epsilon)
                && !V2D_LineSegment_d.intersects(epsilon, l,
                        edges.values());
    }

    /**
     * Identify if {@code this} contains {@code t}.
     *
     * @param t The triangle to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} contains {@code t}.
     */
    public boolean contains(V2D_Triangle_d t, double epsilon) {
        return intersects(t, epsilon)
                && t.getPoints().values().parallelStream().allMatch(x
                        -> contains(x, epsilon));
//        return intersects(t, epsilon)
//                && !t.getEdges().values().parallelStream().anyMatch(x
//                        -> V2D_LineSegment_d.intersects(epsilon, x,
//                        edges.values()));
    }

    /**
     * Identify if {@code this} contains {@code r}.
     *
     * @param r The rectangle to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} contains {@code r}.
     */
    public boolean contains(V2D_Rectangle_d r, double epsilon) {
        return intersects(r, epsilon)
                && r.getPoints().values().parallelStream().allMatch(x
                        -> contains(x, epsilon));
    }

    /**
     * Identify if this contains line segment.
     *
     * @param ch The convex hull to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} is intersected by {@code p}.
     */
    public boolean contains(V2D_ConvexArea_d ch, double epsilon) {
        return intersects(ch, epsilon)
                && ch.getPoints().values().parallelStream().allMatch(x
                        -> contains(x, epsilon));
//        return intersects(ch, epsilon)
//                && !ch.getEdges().values().parallelStream().anyMatch(x
//                -> V2D_LineSegment_d.intersects(epsilon, x,
//                        edges.values()));
    }

    /**
     * Identify if this is intersected by line segment {@code l}.
     *
     * @param l The line segment to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} is intersected by {@code l}.
     */
    public boolean intersects(V2D_LineSegment_d l, double epsilon) {
        return l.intersects(getAABB(), epsilon)
                && intersects0(l, epsilon);
    }

    /**
     * Identify if this is intersected by line segment {@code l}.
     *
     * @param l The line segment to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} is intersected by {@code l}.
     */
    public boolean intersects0(V2D_LineSegment_d l, double epsilon) {
        return getTriangles().parallelStream().anyMatch(x
                -> x.intersects(l, epsilon));
    }

    /**
     * Identify if this is intersected by triangle {@code t}.
     *
     * @param t The triangle to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} is intersected by {@code t}.
     */
    public boolean intersects(V2D_Triangle_d t, double epsilon) {
        return t.intersects(getAABB(), epsilon)
                && intersects0(t, epsilon);
    }

    /**
     * Identify if this is intersected by triangle {@code t}.
     *
     * @param t The triangle to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} is intersected by {@code t}.
     */
    public boolean intersects0(V2D_Triangle_d t, double epsilon) {
        return getTriangles().parallelStream().anyMatch(x
                -> x.intersects0(t, epsilon));
    }

    /**
     * Identify if this is intersected by rectangle {@code r}.
     *
     * @param r The rectangle to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} is intersected by {@code r}.
     */
    public boolean intersects(V2D_Rectangle_d r, double epsilon) {
        return r.intersects(getAABB(), epsilon)
                && intersects0(r, epsilon);
    }

    /**
     * Identify if this is intersected by rectangle {@code r}.
     *
     * @param r The rectangle to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} is intersected by {@code r}.
     */
    public boolean intersects0(V2D_Rectangle_d r, double epsilon) {
        return getTriangles().parallelStream().anyMatch(x
                -> r.intersects(x, epsilon));
    }

    /**
     * Identify if this is intersected by convex hull {@code ch}.
     *
     * @param ch The convex hull to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} is intersected by {@code ch}.
     */
    public boolean intersects(V2D_ConvexArea_d ch, double epsilon) {
        return ch.intersects(getAABB(), epsilon)
                && intersects(ch.getAABB(), epsilon)
                && intersects0(ch, epsilon);
    }

    /**
     * Identify if this is intersected by convex hull {@code ch}.
     *
     * @param ch The convex hull to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} is intersected by {@code ch}.
     */
    public boolean intersects0(V2D_ConvexArea_d ch, double epsilon) {
        return getTriangles().parallelStream().anyMatch(x
                -> ch.intersects0(x, epsilon))
                || ch.getTriangles().parallelStream().anyMatch(x
                        -> intersects0(x, epsilon));
    }

    /**
     * This sums all the areas irrespective of any overlaps.
     *
     * @return The area of the triangle (rounded).
     */
    public double getArea() {
        double sum = 0d;
        for (var t : getTriangles()) {
            sum = sum + t.getArea();
        }
        return sum;
    }

//    /**
//     * This sums all the perimeters irrespective of any overlaps.
//     */
//    @Override
//    public double getPerimeter() {
//        double sum = 0d;
//        for (var t : triangles) {
//            sum = sum + t.getPerimeter();
//        }
//        return sum;
//    }
//
//    /**
//     * Get the intersection between {@code this} and the triangle {@code t}.
//     *
//     * @param t The triangle to intersect with.
//     * @param epsilon The tolerance within which two vectors are regarded as
//     * equal.
//     * @return The V2D_Geometry.
//     */
//    public V2D_FiniteGeometry_d getIntersect(V2D_Triangle_d t,
//            double epsilon) {
//        // Create a set all the intersecting triangles from this.
//        List<V2D_Point_d> ts = new ArrayList<>();
//        for (V2D_Triangle_d t2 : triangles) {
//            V2D_FiniteGeometry_d i = t2.getIntersect(t, epsilon);
//            ts.addAll(Arrays.asList(i.getPoints()));
//        }
//        ArrayList<V2D_Point_d> tsu = V2D_Point_d.getUnique(ts, epsilon);
//        if (tsu.isEmpty()) {
//            return null;
//        } else {
//            return new V2D_ConvexArea_d(t.pl.n, epsilon,
//                    tsu.toArray(V2D_Point_d[]::new)).simplify(epsilon);
//        }
    ////        switch (size) {
////            case 0:
////                return null;
////            case 1:
////                return t2s.iterator().next();
////            case 2:
////                Iterator<V2D_Triangle> ite = t2s.iterator();
////                return getGeometry(ite.next(), ite.next(), oom);
////            default:
////                return getGeometry(oom, t2s.toArray(V2D_Triangle[]::new));
//    }
    
    
    
    @Override
    public V2D_ConvexArea_d rotate(V2D_Point_d pt, double theta) {
        theta = Math_AngleDouble.normalise(theta);
        if (theta == 0d) {
            return new V2D_ConvexArea_d(this, 0d);
        } else {
            return rotateN(pt, theta);
        }
    }

    @Override
    public V2D_ConvexArea_d rotateN(V2D_Point_d pt, double theta) {
        V2D_Point_d[] pts = new V2D_Point_d[points.size()];
        for (int i = 0; i < points.size(); i++) {
            pts[0] = points.get(i).rotateN(pt, theta);
        }
        return new V2D_ConvexArea_d(0d, pts);
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
        return getAABB().intersects(aabb, epsilon)
                && intersects0(aabb, epsilon);
    }
    
    /**
     * For evaluating if {@code this} is intersected by the Axis Aligned 
     * Bounding Box aabb.
     *
     * @param aabb The Axis Aligned Bounding Box to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff the geometry intersects aabb at the given 
     * precision.
     */
    public boolean intersects0(V2D_AABB_d aabb, double epsilon) {
        return getTriangles().parallelStream().anyMatch(x
                    -> x.intersects(aabb, epsilon));
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff this is a rectangle.
     */
    public boolean isRectangle(double epsilon) {
        if (points.size() == 4) {
            return V2D_Rectangle_d.isRectangle(points.get(0),
                    points.get(1), points.get(2), points.get(3), epsilon);
        }
        return false;
    }

//    /**
//     * Clips this using t.
//     *
//     * @param t The triangle to clip this with.
//     * @param pt A point that is used to return the side of this that is
//     * clipped.
//     * @param epsilon The tolerance within which two vectors are regarded as
//     * equal.
//     * @return null, the whole or a part of this.
//     */
//    public V2D_FiniteGeometry_d clip(V2D_Triangle_d t,
//            V2D_Point_d pt, double epsilon) {
//        V2D_Point_d tp = t.getP();
//        V2D_Point_d tq = t.getQ();
//        V2D_Point_d tr = t.getR();
//        V2D_Vector_d n = t.pl.n;
//        V2D_Point_d pp = new V2D_Point_d(tp.offset.add(n), tp.rel);
//        V2D_PlaneDouble ppl = new V2D_PlaneDouble(tp, tq, pp);
//        V2D_Point_d qp = new V2D_Point_d(tq.offset.add(n), tq.rel);
//        V2D_PlaneDouble qpl = new V2D_PlaneDouble(tq, tr, qp);
//        V2D_Point_d rp = new V2D_Point_d(tr.offset.add(n), tr.rel);
//        V2D_PlaneDouble rpl = new V2D_PlaneDouble(tr, tp, rp);
//        V2D_FiniteGeometry_d cppl = clip(ppl, tr, epsilon);
//        if (cppl == null) {
//            return null;
//        } else if (cppl instanceof V2D_Point_d) {
//            return cppl;
//        } else if (cppl instanceof V2D_LineSegment_d cppll) {
//            V2D_FiniteGeometry_d cppllcqpl = cppll.clip(qpl, pt, epsilon);
//            if (cppllcqpl == null) {
//                return null;
//            } else if (cppllcqpl instanceof V2D_Point_d) {
//                return cppllcqpl;
//            } else {
//                return ((V2D_LineSegment_d) cppllcqpl).clip(rpl, pt, epsilon);
//            }
//        } else if (cppl instanceof V2D_Triangle_d cpplt) {
//            V2D_FiniteGeometry_d cppltcqpl = cpplt.clip(qpl, pt, epsilon);
//            if (cppltcqpl == null) {
//                return null;
//            } else if (cppltcqpl instanceof V2D_Point_d) {
//                return cppltcqpl;
//            } else if (cppltcqpl instanceof V2D_LineSegment_d cppltcqpll) {
//                return cppltcqpll.clip(rpl, pt, epsilon);
//            } else if (cppltcqpl instanceof V2D_Triangle_d cppltcqplt) {
//                return cppltcqplt.clip(rpl, pt, epsilon);
//            } else {
//                V2D_ConvexArea_d c = (V2D_ConvexArea_d) cppltcqpl;
//                return c.clip(rpl, tq, epsilon);
//            }
//        } else {
//            V2D_ConvexArea_d c = (V2D_ConvexArea_d) cppl;
//            V2D_FiniteGeometry_d cc = c.clip(qpl, pt, epsilon);
//            if (cc == null) {
//                return cc;
//            } else if (cc instanceof V2D_Point_d) {
//                return cc;
//            } else if (cc instanceof V2D_LineSegment_d cppll) {
//                V2D_FiniteGeometry_d cccqpl = cppll.clip(qpl, pt, epsilon);
//                if (cccqpl == null) {
//                    return null;
//                } else if (cccqpl instanceof V2D_Point_d) {
//                    return cccqpl;
//                } else {
//                    return ((V2D_LineSegment_d) cccqpl).clip(rpl, pt, epsilon);
//                }
//            } else if (cc instanceof V2D_Triangle_d ccct) {
//                return ccct.clip(rpl, tq, epsilon);
//            } else {
//                V2D_ConvexArea_d ccc = (V2D_ConvexArea_d) cc;
//                return ccc.clip(rpl, pt, epsilon);
//            }
//        }
//    }
//
    /**
     * If pts are all equal then a V2D_Point is returned. If two are different,
     * then a V2D_LineSegment is returned. Three different, then a V2D_Triangle
     * is returned. If four or more are different then a V2D_ConvexHullCoplanar
     * is returned.
     *
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @param pts The points.
     * @return Either a V2D_Point, V2D_LineSegment, V2D_Triangle, or
     * V2D_ConvexHullCoplanar.
     */
    public static V2D_FiniteGeometry_d getGeometry(double epsilon,
            ArrayList<V2D_Point_d> pts) {
        return getGeometry(epsilon, pts.toArray(V2D_Point_d[]::new));
    }

    /**
     * If pts are all equal then a V2D_Point is returned. If two are different,
     * then a V2D_LineSegment is returned. Three different, then a V2D_Triangle
     * is returned. If four or more are different then a V2D_ConvexHullCoplanar
     * is returned.
     *
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @param pts The points.
     * @return Either a V2D_Point, V2D_LineSegment, V2D_Triangle, or
     * V2D_ConvexHullCoplanar.
     */
    public static V2D_FiniteGeometry_d getGeometry(double epsilon,
            V2D_Point_d... pts) {
        ArrayList<V2D_Point_d> upts = V2D_Point_d.getUnique(
                Arrays.asList(pts), epsilon);
        Iterator<V2D_Point_d> i = upts.iterator();
        switch (upts.size()) {
            case 1 -> {
                return i.next();
            }
            case 2 -> {
                return new V2D_LineSegment_d(i.next(), i.next());
            }
            case 3 -> {
                if (V2D_Line_d.isCollinear(epsilon, pts)) {
                    return V2D_LineSegment_d.getGeometry(epsilon, pts);
                } else {
                    return new V2D_Triangle_d(i.next(), i.next(), i.next());
                }
            }
            default -> {
                V2D_Point_d ip = i.next();
                V2D_Point_d iq = i.next();
                V2D_Point_d ir = i.next();
                while (V2D_Line_d.isCollinear(epsilon, ip, iq, ir) && i.hasNext()) {
                    ir = i.next();
                }
                if (V2D_Line_d.isCollinear(epsilon, ip, iq, ir)) {
                    return new V2D_LineSegment_d(epsilon, pts);
                } else {
                    return new V2D_ConvexArea_d(epsilon, pts);
                }
            }
        }
    }

    /**
     * Returns the contiguous triangles constructing them first if necessary.
     *
     * This implementation does not computes a centroid and alternates between
     * clockwise and anticlockwise to fill the space with triangles.
     *
     * @return A list of triangles that make up the convex hull.
     */
    public ArrayList<V2D_Triangle_d> getTriangles() {
        if (triangles == null) {
            triangles = new ArrayList<>();
            V2D_Point_d[] ps = getPointsArray();
            V2D_Point_d p0 = ps[0];
            V2D_Point_d p1 = ps[1];
            for (int i = 2; i < ps.length; i++) {
                V2D_Point_d p2 = ps[i];
                triangles.add(new V2D_Triangle_d(p0, p1, p2));
                p1 = p2;
            }
        }
        return triangles;
    }

    /**
     * Returns the edges constructing them first if necessary.
     *
     * @return A list of triangles that make up the convex hull.
     */
    @Override
    public HashMap<Integer, V2D_LineSegment_d> getEdges() {
        if (edges == null) {
            edges = new HashMap<>();
            V2D_Point_d p0 = this.points.get(0);
            V2D_Point_d p1 = this.points.get(1);
            this.edges.put(this.edges.size(), new V2D_LineSegment_d(p0, p1));
            for (int i = 2; i < this.points.size(); i++) {
                p0 = p1;
                p1 = this.points.get(i);
                this.edges.put(this.edges.size(), new V2D_LineSegment_d(
                        p0, p1));
            }
            edges.put(this.edges.size(), new V2D_LineSegment_d(p1,
                    this.points.get(0)));
        }
        return edges;
    }
}
