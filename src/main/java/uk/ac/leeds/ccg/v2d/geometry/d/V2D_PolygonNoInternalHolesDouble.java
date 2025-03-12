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
import java.util.Collection;
import java.util.HashMap;
import uk.ac.leeds.ccg.math.geometry.Math_AngleDouble;

/**
 * For representing a polygon with no internal holes. External holes are similar
 * polygons that share some part of an edge with the convex hull.
 *
 * @author Andy Turner
 * @version 2.0
 */
public class V2D_PolygonNoInternalHolesDouble extends V2D_ShapeDouble {

    private static final long serialVersionUID = 1L;

    /**
     * The convex hull.
     */
    public V2D_ConvexHullDouble ch;

    /**
     * The collection of externalHoles comprised of points in {@link points}.
     * Only two points of an external hole should intersect the edges of the
     * convex hull.
     */
    public HashMap<Integer, V2D_PolygonNoInternalHolesDouble> externalHoles;

    /**
     * Create a new instance that is a shallow copy of the polygon.
     *
     * @param p The polygon to copy.
     */
    public V2D_PolygonNoInternalHolesDouble(
            V2D_PolygonNoInternalHolesDouble p) {
        this(p.points, p.ch, p.edges, p.externalHoles);
    }

//    /**
//     * Create a new instance that is a deep copy of the polygon.
//     *
//     * @param p The polygon to copy.
//     * @param epsilon The tolerance within which two vectors are regarded as
//     * equal.
//     */
//    public V2D_PolygonNoInternalHolesDouble(V2D_PolygonNoInternalHolesDouble p,
//            double epsilon) {
//        this(p.getPoints(), p.getConvexHull(epsilon), p.getEdges(),
//                p.getExternalHoles(epsilon));
//    }
    /**
     * Create a new instance that is also a convex hull.
     *
     * @param ch This convex hull with the points from which this is created and
     * what {@link #ch} is set to.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V2D_PolygonNoInternalHolesDouble(V2D_ConvexHullDouble ch,
            double epsilon) {
        this(ch.getPointsArray(), ch, epsilon);
    }

    /**
     * Create a new instance.
     *
     * @param points The external edge points in clockwise order.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V2D_PolygonNoInternalHolesDouble(V2D_PointDouble[] points,
            double epsilon) {
        this(points, new V2D_ConvexHullDouble(epsilon, points), epsilon);
    }

    /**
     * Create a new instance.
     *
     * @param points The external edge points in clockwise order.
     * @param ch What {@link #ch} is set to.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V2D_PolygonNoInternalHolesDouble(V2D_PointDouble[] points,
            V2D_ConvexHullDouble ch, double epsilon) {
        super(points[0].env, V2D_VectorDouble.ZERO);
        this.points = new HashMap<>();
        this.ch = ch;
        edges = new HashMap<>();
        externalHoles = new HashMap<>();
        PTThing p = new PTThing();
        p.pts = new ArrayList<>();
        p.points = points;
        p.isHole = false;
        // Find a start on the edge.
        int i0 = 0;
        boolean foundStart = false;
        while (!foundStart) {
            p.p0 = points[i0];
            p.p0int = V2D_LineSegmentDouble.intersects(epsilon, p.p0,
                    ch.edges.values());
            if (p.p0int) {
                foundStart = true;
            }
            i0 ++;
        }
        int i1 = i0;
        p.p1 = points[i1];
        i1++;
        if (p.p0.equals(p.p1, epsilon)) {
            p.p1int = p.p0int;
        } else {
            this.points.put(this.points.size(), p.p0);
            edges.put(edges.size(), new V2D_LineSegmentDouble(p.p0, p.p1));
            p.p1int = V2D_LineSegmentDouble.intersects(epsilon, p.p1,
                    ch.edges.values());
            if (p.p0int) {
                if (!p.p1int) {
                    p.pts.add(p.p0);
                    p.isHole = true;
                }
            }
        }
        for (int i = i1; i < points.length; i++) {
            doThing(epsilon, i, p);
        }
        for (int i = 0; i < i0; i++) {
            doThing(epsilon, i, p);
        }
    }

    private void doThing(double epsilon, int index, PTThing p) {
        p.p0 = p.p1;
        p.p0int = p.p1int;
        p.p1 = p.points[index];
        if (p.p0.equals(p.p1, epsilon)) {
            p.p1int = p.p0int;
        } else {
            p.p1int = V2D_LineSegmentDouble.intersects(epsilon, p.p1,
                    ch.edges.values());
            if (p.isHole) {
                if (p.p1int) {
                    p.pts.add(p.p0);
                    p.pts.add(p.p1);
                    externalHoles.put(externalHoles.size(),
                            new V2D_PolygonNoInternalHolesDouble(
                                    p.pts.toArray(V2D_PointDouble[]::new),
                                    epsilon));
                    p.pts = new ArrayList<>();
                    p.isHole = false;
                } else {
                    p.pts.add(p.p0);
                }
            } else {
                if (p.p0int) {
                    if (!p.p1int) {
                        p.pts.add(p.p0);
                        p.isHole = true;
                    }
                }
            }
            this.points.put(this.points.size(), p.p0);
            edges.put(edges.size(), new V2D_LineSegmentDouble(p.p0, p.p1));
        }
    }

    protected class PTThing {

        boolean isHole;
        boolean p0int;
        boolean p1int;
        ArrayList<V2D_PointDouble> pts;
        V2D_PointDouble[] points;
        V2D_PointDouble p0;
        V2D_PointDouble p1;

        PTThing() {
        }
    }

    /**
     * Create a new instance.
     *
     * @param points What {@link #points} is set to.
     * @param ch What {@link #ch} is set to.
     * @param externalEdges What {@link #edges} is set to.
     * @param externalHoles What {@link #externalHoles} is set to.
     */
    public V2D_PolygonNoInternalHolesDouble(
            HashMap<Integer, V2D_PointDouble> points, V2D_ConvexHullDouble ch,
            HashMap<Integer, V2D_LineSegmentDouble> externalEdges,
            HashMap<Integer, V2D_PolygonNoInternalHolesDouble> externalHoles) {
        super(ch.env, V2D_VectorDouble.ZERO);
        this.points = points;
        this.ch = ch;
        this.edges = externalEdges;
        this.externalHoles = externalHoles;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(this.getClass().getName()).append("(");
        {
            s.append("\nch (\n").append(ch.toString()).append("\n)\n");
        }
        {
            s.append("\nedges (\n");
            if (edges != null) {
                for (var entry : edges.entrySet()) {
                    s.append("(");
                    s.append(entry.getKey());
                    s.append(",");
                    s.append(entry.getValue().toString());
                    s.append("), ");
                }
            }
            int l = s.length();
            s = s.delete(l - 2, l);
            s.append("\n)\n");
        }
        {
            s.append("\nexternalHoles (\n");
            if (externalHoles != null) {
                for (var entry : externalHoles.entrySet()) {
                    s.append("(");
                    s.append(entry.getKey());
                    s.append(",");
                    s.append(entry.getValue().toString());
                    s.append("), ");
                }
            }
            int l = s.length();
            s = s.delete(l - 2, l);
            s.append("\n)\n");
        }
        s.append("\n)");
        return s.toString();
    }

    /**
     * @return A copy of {@link exterior#ch} with the given tolerance applied.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V2D_ConvexHullDouble getConvexHull(double epsilon) {
        return new V2D_ConvexHullDouble(ch, epsilon);
    }

    /**
     * @return {@link #edges}.
     */
    @Override
    public HashMap<Integer, V2D_LineSegmentDouble> getEdges() {
        return edges;
    }

//    /**
//     * @return Deep copy of {@link #edges}.
//     */
//    @Override
//    public HashMap<Integer, V2D_LineSegmentDouble> getEdges() {
//        HashMap<Integer, V2D_LineSegmentDouble> r = new HashMap<>();
//        for (V2D_LineSegmentDouble l : edges.values()) {
//            r.put(r.size(), new V2D_LineSegmentDouble(l));
//        }
//        return r;
//    }
    /**
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A copy of {@link externalHoles} with the given tolerance applied.
     */
    public HashMap<Integer, V2D_PolygonNoInternalHolesDouble> getExternalHoles(
            double epsilon) {
        HashMap<Integer, V2D_PolygonNoInternalHolesDouble> r = new HashMap<>();
        for (V2D_PolygonNoInternalHolesDouble h : externalHoles.values()) {
            r.put(r.size(), new V2D_PolygonNoInternalHolesDouble(h));
        }
        return r;
    }

    /**
     * @return {@link externalHoles}.
     */
    public HashMap<Integer, V2D_PolygonNoInternalHolesDouble> getExternalHoles() {
        return externalHoles;
    }

    @Override
    public V2D_PointDouble[] getPointsArray() {
        Collection<V2D_PointDouble> pts = points.values();
        return pts.toArray(V2D_PointDouble[]::new);
    }

    @Override
    public HashMap<Integer, V2D_PointDouble> getPoints() {
        return points;
    }

    @Override
    public V2D_AABBDouble getAABB() {
        if (en == null) {
            en = ch.getAABB();
        }
        return en;
    }

    /**
     * Identify if this is intersected by pt.
     *
     * @param pt The point to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff there is an intersection.
     */
    public boolean intersects(V2D_PointDouble pt, double epsilon) {
        return ch.intersects(pt, epsilon)
                && (!V2D_LineSegmentDouble.intersects(epsilon, pt,
                        ch.edges.values())
                && !externalHoles.values().parallelStream().anyMatch(x
                        -> x.contains(pt, epsilon)));
    }

    /**
     * Identify if this contains pt.
     *
     * @param pt The point to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff there is an intersection.
     */
    public boolean contains(V2D_PointDouble pt, double epsilon) {
        return intersects(pt, epsilon)
                && !V2D_LineSegmentDouble.intersects(epsilon, pt,
                        edges.values());
    }

    /**
     * Identify if this contains ls.
     *
     * @param ls The line segment to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff there is an intersection.
     */
    public boolean contains(V2D_LineSegmentDouble ls, double epsilon) {
        return contains(ls.getP(), epsilon)
                && contains(ls.getQ(), epsilon);
    }

    /**
     * Identify if this contains t.
     *
     * @param t The triangle to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff there is containment.
     */
    public boolean contains(V2D_TriangleDouble t, double epsilon) {
        return contains(t.getP(), epsilon)
                && contains(t.getQ(), epsilon)
                && contains(t.getR(), epsilon);
    }

    /**
     * Identify if this contains r.
     *
     * @param r The rectangle to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff there is containment.
     */
    public boolean contains(V2D_RectangleDouble r, double epsilon) {
        return contains(r.getP(), epsilon)
                && contains(r.getQ(), epsilon)
                && contains(r.getR(), epsilon)
                && contains(r.getS(), epsilon);
    }

    /**
     * Identify if this contains aabb.
     *
     * @param aabb The envelope to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff there is containment.
     */
    public boolean contains(V2D_AABBDouble aabb, double epsilon) {
        double xmin = aabb.getXMin();
        double xmax = aabb.getXMax();
        double ymin = aabb.getYMin();
        double ymax = aabb.getYMax();
        return contains(new V2D_PointDouble(env, xmin, ymin), epsilon)
                && contains(new V2D_PointDouble(env, xmin, ymax), epsilon)
                && contains(new V2D_PointDouble(env, xmax, ymax), epsilon)
                && contains(new V2D_PointDouble(env, xmax, ymin), epsilon);
    }

    /**
     * Identify if this contains ch.
     *
     * @param ch The convex hull to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff there is containment.
     */
    public boolean contains(V2D_ConvexHullDouble ch, double epsilon) {
        return this.ch.intersects(ch, epsilon)
                && ch.getPoints().values().parallelStream().allMatch(x
                        -> contains(x, epsilon));
    }

    /**
     * Identify if this contains the polygon.
     *
     * @param p The polygon to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} contains {@code p}.
     */
    public boolean contains(V2D_PolygonNoInternalHolesDouble p, double epsilon) {
        return this.ch.intersects(p.ch, epsilon)
                && p.getPoints().values().parallelStream().allMatch(x
                        -> contains(x, epsilon));
    }

    /**
     * Identify if this is intersected by l.
     *
     * @param l The line segment to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff this is intersected by l.
     */
    public boolean intersects(V2D_LineSegmentDouble l, double epsilon) {
        return ch.intersects(l, epsilon)
                && (!V2D_LineSegmentDouble.intersects(epsilon, l,
                        ch.edges.values())
                || !externalHoles.values().parallelStream().anyMatch(x
                        -> x.contains(l, epsilon)));
    }

    /**
     * Identify if this is intersected by the triangle.
     *
     * @param t The triangle to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff this is intersected by {@code t}.
     */
    public boolean intersects(V2D_TriangleDouble t, double epsilon) {
        return ch.intersects(t, epsilon)
                && intersects0(t, epsilon);
    }

    /**
     * Identify if this is intersected by the triangle.
     *
     * @param t The triangle to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff this is intersected by {@code t}.
     */
    public boolean intersects0(V2D_TriangleDouble t, double epsilon) {
        V2D_PointDouble tp = t.getP();
        V2D_PointDouble tq = t.getQ();
        V2D_PointDouble tr = t.getR();
        return (intersects(tp, epsilon)
                || intersects(tq, epsilon)
                || intersects(tr, epsilon))
                || t.getEdges().values().parallelStream().anyMatch(x
                        -> V2D_LineSegmentDouble.intersects(epsilon, x,
                        edges.values()))
                && !externalHoles.values().parallelStream().anyMatch(x
                        -> x.contains(tp, epsilon)
                && x.contains(tq, epsilon)
                && x.contains(tr, epsilon));
    }

    /**
     * Identify if this is intersected by the rectangle.
     *
     * @param r The convex hull to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff this is intersected by {@code r}.
     */
    public boolean intersects(V2D_RectangleDouble r, double epsilon) {
        return ch.intersects(r, epsilon)
                && (intersects0(r.getPQR(), epsilon)
                || intersects0(r.getRSP(), epsilon));
    }

    /**
     * Identify if this is intersected by the convex hull.
     *
     * @param ch The convex hull to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff this is intersected by {@code ch}.
     */
    public boolean intersects(V2D_ConvexHullDouble ch, double epsilon) {
        return this.ch.intersects(ch, epsilon)
                && /**
                 * If any of the edges intersect or if one geometry contains the
                 * other, there is an intersection.
                 */
                getEdges().values().parallelStream().anyMatch(x
                        -> V2D_LineSegmentDouble.intersects(epsilon, x,
                        ch.getEdges().values()))
                || ch.getPoints().values().parallelStream().anyMatch(x
                        -> intersects(x, epsilon))
                || getPoints().values().parallelStream().anyMatch(x
                        -> ch.intersects(x, epsilon));
    }

    /**
     * Identify if this is intersected by the polygon.
     *
     * @param p The convex hull to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff this is intersected by {@code p}.
     */
    public boolean intersects(V2D_PolygonNoInternalHolesDouble p,
            double epsilon) {
        return p.ch.intersects(ch, epsilon)
                && /**
                 * If any of the edges intersect or if one polygon contains the
                 * other, there is an intersection.
                 */
                getEdges().values().parallelStream().anyMatch(x
                        -> V2D_LineSegmentDouble.intersects(epsilon, x,
                        p.getEdges().values()))
                || getPoints().values().parallelStream().anyMatch(x
                        -> p.intersects(x, epsilon))
                || p.getPoints().values().parallelStream().anyMatch(x
                        -> intersects(x, epsilon));
    }

    /**
     * This sums all the areas irrespective of any overlaps.
     *
     * @return The area of the triangle (rounded).
     */
    //@Override
    public double getArea() {
        throw new UnsupportedOperationException();
//        BigDecimal sum = BigDecimal.ZERO;
//        for (var t : triangles) {
//            sum = sum.add(t.getArea(oom));
//        }
//        return sum;
    }

    /**
     * This sums all the perimeters irrespective of any overlaps.
     *
     * @return The total length of the external edges.
     */
    //@Override
    public double getPerimeter() {
        throw new UnsupportedOperationException();
//        BigDecimal sum = BigDecimal.ZERO;
//        for (var t : triangles) {
//            sum = sum.add(t.getPerimeter(oom));
//        }
//        return sum;
    }

    @Override
    public void translate(V2D_VectorDouble v) {
        super.translate(v);
        if (en != null) {
            en.translate(v);
        }
        ch.translate(v);
        if (edges != null) {
            for (int i = 0; i < edges.size(); i++) {
                edges.get(i).translate(v);
            }
        }
        if (externalHoles != null) {
            for (int i = 0; i < externalHoles.size(); i++) {
                externalHoles.get(i).translate(v);
            }
        }
    }

    @Override
    public V2D_PolygonNoInternalHolesDouble rotate(V2D_PointDouble pt,
            double theta) {
        theta = Math_AngleDouble.normalise(theta);
        if (theta == 0) {
            return new V2D_PolygonNoInternalHolesDouble(this);
        } else {
            return rotateN(pt, theta);
        }
    }

    @Override
    public V2D_PolygonNoInternalHolesDouble rotateN(V2D_PointDouble pt,
            double theta) {
        double epsilon = 0d;
        V2D_ConvexHullDouble rch = getConvexHull(epsilon).rotate(pt, theta);
        HashMap<Integer, V2D_PointDouble> rPoints = new HashMap<>();
        for (var x : this.points.entrySet()) {
            rPoints.put(x.getKey(), x.getValue().rotateN(pt, theta));
        }
        HashMap<Integer, V2D_LineSegmentDouble> rExternalEdges = new HashMap<>();
        if (edges != null) {
            for (int i = 0; i < edges.size(); i++) {
                rExternalEdges.put(rExternalEdges.size(), edges.get(i).rotate(
                        pt, theta));
            }
        }
        HashMap<Integer, V2D_PolygonNoInternalHolesDouble> rExternalHoles
                = new HashMap<>();
        if (externalHoles != null) {
            for (int i = 0; i < externalHoles.size(); i++) {
                rExternalHoles.put(rExternalHoles.size(),
                        externalHoles.get(i).rotate(pt, theta));
            }
        }
        return new V2D_PolygonNoInternalHolesDouble(rPoints, rch,
                rExternalEdges, rExternalHoles);
    }

//    @Override
//    public boolean intersects(V2D_AABBDouble aabb, double epsilon) {
//        if (getAABB().intersects(aabb)) {
//            if (ch.intersects(aabb, epsilon)) {
//                return true;
//            }
//        }
//        return false;
//    }
    /**
     * Adds an external hole and return its assigned id.
     *
     * @param p
     * @return the id assigned to the external hole
     */
    public int addExternalHole(V2D_PolygonNoInternalHolesDouble p) {
        int pid = externalHoles.size();
        externalHoles.put(pid, p);
        return pid;
    }
}
