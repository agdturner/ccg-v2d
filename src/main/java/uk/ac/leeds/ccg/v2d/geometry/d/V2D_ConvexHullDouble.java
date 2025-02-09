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
package uk.ac.leeds.ccg.v2d.geometry.d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import uk.ac.leeds.ccg.math.geometry.Math_AngleDouble;

/**
 * A class for representing convex hulls. These are a special types of polygon:
 * They have no holes and are convex. Below is a basic algorithm
 * for generating a convex hull from a set of coplanar points known as the
 * "quick hull" algorithm (see
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
public class V2D_ConvexHullDouble extends V2D_FiniteGeometryDouble {

    private static final long serialVersionUID = 1L;

    /**
     * The collection of triangles.
     */
    protected final ArrayList<V2D_TriangleDouble> triangles;

    /**
     * The collection of points in a clockwise order.
     */
    protected ArrayList<V2D_PointDouble> points;

    /**
     * Create a new instance.
     *
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @param triangles A non-empty list of coplanar triangles.
     */
    public V2D_ConvexHullDouble(double epsilon,
            V2D_TriangleDouble... triangles) {
        this(epsilon, V2D_TriangleDouble.getPoints(triangles));
    }

//    /**
//     * Create a new instance.
//     *
//     * @param epsilon The tolerance within which two vectors are regarded as
//     * equal.
//     * @param points A non-empty list of points in a plane given by n.
//     */
//    public V2D_ConvexHullDouble(double epsilon, V2D_PointDouble... points) {
//        super();
//        ArrayList<V2D_PointDouble> uniquePoints = V2D_PointDouble.getUnique(Arrays.asList(points), epsilon);
//        V2D_PointDouble[] up = new V2D_PointDouble[uniquePoints.size()];
//        up = uniquePoints.toArray(up);
//        V2D_PointDouble max = up[V2D_GeometricsDouble.getMax(up)];
//        V2D_PointDouble centroid = V2D_GeometricsDouble.getCentroid(up);
//        V2D_SortByAngleDouble sbc = new V2D_SortByAngleDouble(centroid, max);
//        Arrays.sort(up, sbc);
//        this.points = new ArrayList<>(Arrays.asList(up));
//        this.triangles = new ArrayList<>();
//    }
    
    /**
     * Create a new instance.
     *
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @param points A non-empty list of points in a plane given by n.
     */
    public V2D_ConvexHullDouble(double epsilon, V2D_PointDouble... points) {
        super();
        ArrayList<V2D_PointDouble> h = new ArrayList<>();
        ArrayList<V2D_PointDouble> uniquePoints = V2D_PointDouble.getUnique(Arrays.asList(points), epsilon);
        //uniquePoints.sort(V2D_PointDouble::compareTo);
        uniquePoints.sort((p1, p2) -> p1.compareTo(p2));
        // Compute convex hull
        // https://rosettacode.org/wiki/Convex_hull#Java
        // lower hull
        for (V2D_PointDouble pt : uniquePoints) {
            while (h.size() >= 2 && !ccw(h.get(h.size() - 2), h.get(h.size() - 1), pt)) {
                h.remove(h.size() - 1);
            }
            h.add(pt);
        }
        // upper hull
        int t = h.size() + 1;
        for (int i = uniquePoints.size() - 1; i >= 0; i--) {
            V2D_PointDouble pt = uniquePoints.get(i);
            while (h.size() >= t && !ccw(h.get(h.size() - 2), h.get(h.size() - 1), pt)) {
                h.remove(h.size() - 1);
            }
            h.add(pt);
        }
        this.points = V2D_PointDouble.getUnique(h, epsilon);
        this.triangles = new ArrayList<>();
    }

    // ccw returns true if the three points make a counter-clockwise turn
    private static boolean ccw(V2D_PointDouble a, V2D_PointDouble b, V2D_PointDouble c) {
        double ax = a.getX();
        double ay = a.getY();
        return ((b.getX() - ax) * (c.getY() - ay)) > ((b.getY() - ay) * (c.getX() - ax));
    }

    /**
     * Create a new instance.
     *
     * @param ch The convex hull.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V2D_ConvexHullDouble(V2D_ConvexHullDouble ch, double epsilon) {
        this(epsilon, V2D_FiniteGeometryDouble.getPoints(ch));
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
    public V2D_ConvexHullDouble(V2D_ConvexHullDouble ch, V2D_TriangleDouble t,
            double epsilon) {
        this(epsilon, V2D_FiniteGeometryDouble.getPoints(ch, t));
    }

    @Override
    public V2D_PointDouble[] getPoints() {
        int np = points.size();
        V2D_PointDouble[] pts = new V2D_PointDouble[np];
        return points.toArray(pts);
    }

    @Override
    public String toString() {
        String s = this.getClass().getName() + "(";
        Iterator<V2D_PointDouble> ite = points.iterator();
        s += ite.next().toString();
        while (ite.hasNext()) {
            s += ", " + ite.next().toString("");
        }
        s += ")";
        return s;
    }

    /**
     * @return Simple string representation.
     */
    public String toStringSimple() {
        String s = this.getClass().getName() + "(";
        Iterator<V2D_PointDouble> ite = points.iterator();
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff all the triangles are the same.
     */
    public boolean equals(V2D_ConvexHullDouble c, double epsilon) {
        HashSet<Integer> indexes = new HashSet<>();
        for (var x : points) {
            boolean found = false;
            for (int i = 0; i < c.points.size(); i++) {
                if (x.equals(epsilon, c.points.get(i))) {
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
                    if (x.equals(epsilon, c.points.get(i))) {
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
    public V2D_EnvelopeDouble getEnvelope() {
        if (en == null) {
            en = points.get(0).getEnvelope();
            for (int i = 1; i < points.size(); i++) {
                en = en.union(points.get(i).getEnvelope());
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
    public V2D_FiniteGeometryDouble simplify(double epsilon) {
        if (isTriangle()) {
            return new V2D_TriangleDouble(points.get(0), points.get(1),
                    points.get(2));
        } else if (isRectangle(epsilon)) {
            return new V2D_RectangleDouble(points.get(0), points.get(2),
                    points.get(1), points.get(3));
        } else {
            return this;
        }
    }

    /**
     * Identify if this is intersected by point {@code p}.
     *
     * @param pt The point to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    public boolean isIntersectedBy(V2D_PointDouble pt, double epsilon) {
        // Check envelopes intersect.
        if (getEnvelope().isIntersectedBy(pt)) {
            // Check point is in a triangle
            for (var t : getTriangles()) {
                if (t.isIntersectedBy(pt, epsilon)) {
                    //if (t.isAligned(pt, epsilon)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Identify if this is intersected by point {@code p}.
     *
     * @param l The line segment to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    public boolean isIntersectedBy(V2D_LineSegmentDouble l, double epsilon) {
        // Check envelopes intersect.
        if(getEnvelope().isIntersectedBy(l.getEnvelope())) {
        //if (l.isIntersectedBy(getEnvelope(), epsilon)) {
            for (V2D_TriangleDouble t : getTriangles()) {
                if (t.isIntersectedBy(l, epsilon)) {
                    return true;
                }
            }
        //}
        }
        return false;
    }

    /**
     * Identify if this is intersected by point {@code p}.
     *
     * @param t The triangle to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    public boolean isIntersectedBy(V2D_TriangleDouble t, double epsilon) {
        // Check envelopes intersect.
        if (t.isIntersectedBy(getEnvelope(), epsilon)) {
            if (isIntersectedBy(t.getEnvelope(), epsilon)) {
                for (V2D_TriangleDouble tt : getTriangles()) {
                    if (tt.isIntersectedBy(t, epsilon)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Identify if this is intersected by point {@code p}.
     *
     * @param r The rectangle to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    public boolean isIntersectedBy(V2D_RectangleDouble r, double epsilon) {
        // Check envelopes intersect.
        if (r.isIntersectedBy(getEnvelope(), epsilon)) {
            if (isIntersectedBy(r.getEnvelope(), epsilon)) {
                for (V2D_TriangleDouble t : getTriangles()) {
                    if (r.isIntersectedBy(t, epsilon)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Identify if this is intersected by point {@code p}.
     *
     * @param ch The convex hull to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff the geometry is intersected by {@code ch.
     */
    public boolean isIntersectedBy(V2D_ConvexHullDouble ch, double epsilon) {
        // Check envelopes intersect.
        //if (ch.getEnvelope().isIntersectedBy(getEnvelope())) {
        if (ch.isIntersectedBy(en, epsilon)) {
            if (isIntersectedBy(ch.en, epsilon)) {
//                // Could do the two ways in parallel for speed up...
//                for (V2D_TriangleDouble t : ch.getTriangles()) {
//                    if (isIntersectedBy(t, epsilon)) {
//                        return true;
//                    }
//                }
                for (V2D_TriangleDouble t : getTriangles()) {
                    if (ch.isIntersectedBy(t, epsilon)) {
                        return true;
                    }
                }
            }
        }
        //}
        return false;
    }

    /**
     * @param pt The point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if the point is aligned with any of the parts.
     */
    protected boolean isAligned(V2D_PointDouble pt, double epsilon) {
        for (V2D_TriangleDouble triangle : getTriangles()) {
            if (triangle.isAligned(pt, epsilon)) {
                return true;
            }
        }
        return false;
        //return triangles.parallelStream().anyMatch(t -> (t.isIntersectedBy0(pt, oom)));
    }

//    /**
//     * @param pt The point.
//     * @param epsilon The tolerance within which two vectors are regarded as
//     * equal.
//     * @return {@code true} if this intersects with {@code pt}.
//     */
//    @Deprecated
//    protected boolean isIntersectedBy0(V2D_PointDouble pt, double epsilon) {
//        for (V2D_TriangleDouble triangle : triangles) {
//            if (triangle.isIntersectedBy0(pt, epsilon)) {
//                return true;
//            }
//        }
//        return false;
//        //return triangles.parallelStream().anyMatch(t -> (t.isIntersectedBy0(pt, oom)));
//    }
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
//     * Get the intersection between the geometry and the triangle {@code t}.
//     *
//     * @param t The triangle to intersect with.
//     * @param epsilon The tolerance within which two vectors are regarded as
//     * equal.
//     * @return The V2D_Geometry.
//     */
//    public V2D_FiniteGeometryDouble getIntersection(V2D_TriangleDouble t,
//            double epsilon) {
//        // Create a set all the intersecting triangles from this.
//        List<V2D_PointDouble> ts = new ArrayList<>();
//        for (V2D_TriangleDouble t2 : triangles) {
//            V2D_FiniteGeometryDouble i = t2.getIntersection(t, epsilon);
//            ts.addAll(Arrays.asList(i.getPoints()));
//        }
//        ArrayList<V2D_PointDouble> tsu = V2D_PointDouble.getUnique(ts, epsilon);
//        if (tsu.isEmpty()) {
//            return null;
//        } else {
//            return new V2D_ConvexHullDouble(t.pl.n, epsilon,
//                    tsu.toArray(V2D_PointDouble[]::new)).simplify(epsilon);
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
//
////    @Override
////    public boolean isEnvelopeIntersectedBy(V2D_Line l, int oom) {
////        return getEnvelope().isIntersectedBy(l, oom);
    ////    }
    
    
    @Override
    public V2D_ConvexHullDouble rotate(V2D_PointDouble pt, double theta,
            double epsilon) {
        theta = Math_AngleDouble.normalise(theta);
        if (theta == 0d) {
            return new V2D_ConvexHullDouble(this, epsilon);
        } else {
            return rotateN(pt, theta, epsilon);
        }
    }

    @Override
    public V2D_ConvexHullDouble rotateN(V2D_PointDouble pt, double theta, double epsilon) {
        V2D_TriangleDouble[] rts = new V2D_TriangleDouble[getTriangles().size()];
        for (int i = 0; i < triangles.size(); i++) {
            rts[0] = triangles.get(i).rotateN(pt, theta, epsilon);
        }
        return new V2D_ConvexHullDouble(epsilon, rts);
    }
//
//    /**
//     * @param pts The points from which the convex hull is formed.
//     * @param p0 A point in pts that along with p1 and n defines a plane used to
//     * divide the points into 3 types, on, above and below.
//     * @param p1 A point in pts that along with p0 and n defines a plane used to
//     * divide the points into 3 types, on, above and below.
//     * @param n The normal to the plane.
//     * @param index The index into which points that form the convex hull are
//     * added.
//     * @param epsilon The tolerance within which two vectors are regarded as
//     * equal.
//     */
//    private void compute(ArrayList<V2D_PointDouble> pts, V2D_PointDouble p0,
//            V2D_PointDouble p1, V2D_VectorDouble n, int index, double epsilon) {
//        V2D_PlaneDouble pl = new V2D_PlaneDouble(p0, p1, new V2D_PointDouble(
//                p0.offset, p0.rel.add(n)));
//        AB ab = new AB(pts, p0, p1, pl, epsilon);
//        // Process ab.a
//        if (!ab.a.isEmpty()) {
//            if (ab.a.size() == 1) {
//                points.add(index, ab.a.get(0));
//                index++;
//            } else {
//                V2D_PointDouble apt = ab.a.get(ab.maxaIndex);
//                points.add(index, apt);
//                index++;
//                V2D_TriangleDouble atr = new V2D_TriangleDouble(p0, p1, apt);
//                TreeSet<Integer> removeIndexes = new TreeSet<>();
//                for (int i = 0; i < ab.a.size(); i++) {
//                    if (atr.isIntersectedBy(ab.a.get(i), epsilon)) {
//                        removeIndexes.add(i);
//                        //index--;
//                    }
//                }
//                Iterator<Integer> ite = removeIndexes.descendingIterator();
//                while (ite.hasNext()) {
//                    ab.a.remove(ite.next().intValue());
//                }
//                if (!ab.a.isEmpty()) {
//                    if (ab.a.size() == 1) {
//                        points.add(index, ab.a.get(0));
//                        index++;
//                    } else {
//                        // Divide again
//                        V2D_LineDouble l = new V2D_LineDouble(p0, p1);
//                        V2D_PointDouble proj = l.getPointOfIntersection(apt, epsilon);
//                        compute(ab.a, proj, apt, n, index, epsilon);
//                    }
//                }
//            }
//        }
//        // Process ab.b
//        if (!ab.b.isEmpty()) {
//            if (ab.b.size() == 1) {
//                points.add(index, ab.b.get(0));
//                index++;
//            } else {
//                V2D_PointDouble bpt = ab.b.get(ab.maxbIndex);
//                points.add(index, bpt);
//                index++;
//                
//                // Debug
//                if (bpt.equals(epsilon, p1)) {
//                    int debug = 1;
//                    ab = new AB(pts, p0, p1, pl, epsilon);
//                }
//                
//                V2D_TriangleDouble btr = new V2D_TriangleDouble(p0, p1, bpt); // p1 and bpt might be the same!
//                TreeSet<Integer> removeIndexes = new TreeSet<>();
//                for (int i = 0; i < ab.b.size(); i++) {
//                    if (btr.isIntersectedBy(ab.b.get(i), epsilon)) {
//                        removeIndexes.add(i);
//                        //index--;
//                    }
//                }
//                Iterator<Integer> ite = removeIndexes.descendingIterator();
//                while (ite.hasNext()) {
//                    ab.b.remove(ite.next().intValue());
//                }
//                if (!ab.b.isEmpty()) {
//                    if (ab.b.size() == 1) {
//                        points.add(index, ab.b.get(0));
//                        index++;
//                    } else {
//                        // Divide again
//                        V2D_LineDouble l = new V2D_LineDouble(p0, p1);
//                        V2D_PointDouble proj = l.getPointOfIntersection(bpt, epsilon);
//                        compute(ab.b, bpt, proj, n, index, epsilon);
//                    }
//                }
//            }
//        }
//    }
//

    @Override
    public boolean isIntersectedBy(V2D_EnvelopeDouble aabb, double epsilon) {
        if (getEnvelope().isIntersectedBy(aabb, epsilon)) {
            for (V2D_TriangleDouble t : getTriangles()) {
                if (t.isIntersectedBy(aabb, epsilon)) {
                    return true;
                }
            }
        }
        return false;
    }
//
//    /**
//     * A class for helping to calculate a convex hull.
//     */
//    public class AB {
//
//        /**
//         * The points that are above the plane.
//         */
//        public ArrayList<V2D_PointDouble> a;
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
//        public ArrayList<V2D_PointDouble> b;
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
//         * @param pts The points from which to calculate the convex hull.
//         * @param epsilon The tolerance within which two vectors are considered
//         * equal.
//         */
//        public AB(ArrayList<V2D_PointDouble> pts, V2D_PointDouble p0,
//                double epsilon) {
//            a = new ArrayList<>();
//            b = new ArrayList<>();
//            // Find points on, above and below the plane.
//            for (int i = 0; i < pts.size(); i++) {
//                V2D_PointDouble pt = pts.get(i);
//                int sop = pl.getSideOfPlane(pt, epsilon);
//                if (sop > 0) {
//                    a.add(pt);
//                } else if (sop < 0) {
//                    b.add(pt);
//                }
//            }
//            a = V2D_PointDouble.getUnique(a, epsilon);
//            maxaIndex = 0;
//            if (a.size() > 1) {
//                V2D_PointDouble pt0 = a.get(0);
//                double maxds = pl.getDistanceSquared(pt0, epsilon);
//                for (int i = 1; i < a.size(); i++) {
//                    V2D_PointDouble pt = a.get(i);
//                    double ds = pl.getDistanceSquared(pt, epsilon);
//                    if (ds > maxds) {
//                        maxds = ds;
//                        maxaIndex = i;
//                    }
//                }
//            }
//            b = V2D_PointDouble.getUnique(b, epsilon);
//            maxbIndex = 0;
//            if (b.size() > 1) {
//                V2D_PointDouble pt0 = b.get(0);
//                double maxds = pl.getDistanceSquared(pt0, epsilon);
//                for (int i = 1; i < b.size(); i++) {
//                    V2D_PointDouble pt = b.get(i);
//                    double ds = pl.getDistanceSquared(pt, epsilon);
//                    if (ds > maxds) {
//                        maxds = ds;
//                        maxbIndex = i;
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff this is a rectangle.
     */
    public boolean isRectangle(double epsilon) {
        if (points.size() == 4) {
            return V2D_RectangleDouble.isRectangle(points.get(0),
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
//    public V2D_FiniteGeometryDouble clip(V2D_TriangleDouble t,
//            V2D_PointDouble pt, double epsilon) {
//        V2D_PointDouble tp = t.getP();
//        V2D_PointDouble tq = t.getQ();
//        V2D_PointDouble tr = t.getR();
//        V2D_VectorDouble n = t.pl.n;
//        V2D_PointDouble pp = new V2D_PointDouble(tp.offset.add(n), tp.rel);
//        V2D_PlaneDouble ppl = new V2D_PlaneDouble(tp, tq, pp);
//        V2D_PointDouble qp = new V2D_PointDouble(tq.offset.add(n), tq.rel);
//        V2D_PlaneDouble qpl = new V2D_PlaneDouble(tq, tr, qp);
//        V2D_PointDouble rp = new V2D_PointDouble(tr.offset.add(n), tr.rel);
//        V2D_PlaneDouble rpl = new V2D_PlaneDouble(tr, tp, rp);
//        V2D_FiniteGeometryDouble cppl = clip(ppl, tr, epsilon);
//        if (cppl == null) {
//            return null;
//        } else if (cppl instanceof V2D_PointDouble) {
//            return cppl;
//        } else if (cppl instanceof V2D_LineSegmentDouble cppll) {
//            V2D_FiniteGeometryDouble cppllcqpl = cppll.clip(qpl, pt, epsilon);
//            if (cppllcqpl == null) {
//                return null;
//            } else if (cppllcqpl instanceof V2D_PointDouble) {
//                return cppllcqpl;
//            } else {
//                return ((V2D_LineSegmentDouble) cppllcqpl).clip(rpl, pt, epsilon);
//            }
//        } else if (cppl instanceof V2D_TriangleDouble cpplt) {
//            V2D_FiniteGeometryDouble cppltcqpl = cpplt.clip(qpl, pt, epsilon);
//            if (cppltcqpl == null) {
//                return null;
//            } else if (cppltcqpl instanceof V2D_PointDouble) {
//                return cppltcqpl;
//            } else if (cppltcqpl instanceof V2D_LineSegmentDouble cppltcqpll) {
//                return cppltcqpll.clip(rpl, pt, epsilon);
//            } else if (cppltcqpl instanceof V2D_TriangleDouble cppltcqplt) {
//                return cppltcqplt.clip(rpl, pt, epsilon);
//            } else {
//                V2D_ConvexHullDouble c = (V2D_ConvexHullDouble) cppltcqpl;
//                return c.clip(rpl, tq, epsilon);
//            }
//        } else {
//            V2D_ConvexHullDouble c = (V2D_ConvexHullDouble) cppl;
//            V2D_FiniteGeometryDouble cc = c.clip(qpl, pt, epsilon);
//            if (cc == null) {
//                return cc;
//            } else if (cc instanceof V2D_PointDouble) {
//                return cc;
//            } else if (cc instanceof V2D_LineSegmentDouble cppll) {
//                V2D_FiniteGeometryDouble cccqpl = cppll.clip(qpl, pt, epsilon);
//                if (cccqpl == null) {
//                    return null;
//                } else if (cccqpl instanceof V2D_PointDouble) {
//                    return cccqpl;
//                } else {
//                    return ((V2D_LineSegmentDouble) cccqpl).clip(rpl, pt, epsilon);
//                }
//            } else if (cc instanceof V2D_TriangleDouble ccct) {
//                return ccct.clip(rpl, tq, epsilon);
//            } else {
//                V2D_ConvexHullDouble ccc = (V2D_ConvexHullDouble) cc;
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
    public static V2D_FiniteGeometryDouble getGeometry(double epsilon, V2D_PointDouble... pts) {
        ArrayList<V2D_PointDouble> upts = V2D_PointDouble.getUnique(Arrays.asList(pts), epsilon);
        Iterator<V2D_PointDouble> i = upts.iterator();
        switch (upts.size()) {
            case 1 -> {
                return i.next();
            }
            case 2 -> {
                return new V2D_LineSegmentDouble(i.next(), i.next());
            }
            case 3 -> {
                if (V2D_LineDouble.isCollinear(epsilon, pts)) {
                    return V2D_LineSegmentDouble.getGeometry(epsilon, pts);
                } else {
                    return new V2D_TriangleDouble(i.next(), i.next(), i.next());
                }
            }
            default -> {
                V2D_PointDouble ip = i.next();
                V2D_PointDouble iq = i.next();
                V2D_PointDouble ir = i.next();
                while (V2D_LineDouble.isCollinear(epsilon, ip, iq, ir) && i.hasNext()) {
                    ir = i.next();
                }
                if (V2D_LineDouble.isCollinear(epsilon, ip, iq, ir)) {
                    return new V2D_LineSegmentDouble(epsilon, pts);
                } else {
                    return new V2D_ConvexHullDouble(epsilon, pts);
                }
            }
        }
    }

//    /**
//     * This implementation computes a centroid and uses that to construct 
//     * triangles with each edge.
//     * @return A list of triangles that make up the convex hull. 
//     */
//    public ArrayList<V2D_TriangleDouble> getTriangles() {
//        ArrayList<V2D_TriangleDouble> result = new ArrayList<>();
//        V2D_PointDouble[] ps = getPoints();
//        V2D_PointDouble centroid = V2D_GeometricsDouble.getCentroid(ps);
//        V2D_PointDouble p0 = ps[0];
//        for (int i = 1; i < ps.length; i ++) {
//            V2D_PointDouble p1 = ps[i];
//            result.add(new V2D_TriangleDouble(centroid, p0, p1));
//            p0 = p1;
//        }
//        return result;
//    }
    /**
     * This implementation does not computes a centroid and alternates between
     * clockwise and anticlockwise to fill the space with triangles.
     *
     * @return A list of triangles that make up the convex hull.
     */
    public ArrayList<V2D_TriangleDouble> getTriangles() {
        ArrayList<V2D_TriangleDouble> result = new ArrayList<>();
        V2D_PointDouble[] ps = getPoints();
        V2D_PointDouble p0 = ps[0];
        V2D_PointDouble p1 = ps[1];
        for (int i = 2; i < ps.length; i++) {
            V2D_PointDouble p2 = ps[i];
            result.add(new V2D_TriangleDouble(p0, p1, p2));
            p1 = p2;
        }
        return result;
    }
}
