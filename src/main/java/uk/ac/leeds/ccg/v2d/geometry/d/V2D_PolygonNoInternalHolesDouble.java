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
     * The collection of points that form a linear ring in a clockwise order.
     * The linear ring should not be self intersecting. Keys are identifiers.
     */
    public HashMap<Integer, V2D_PointDouble> points;

    /**
     * The convex hull.
     */
    public V2D_ConvexHullDouble ch;

    /**
     * The collection of externalEdges comprised of points in {@link points}.
     */
    public HashMap<Integer, V2D_LineSegmentDouble> externalEdges;

    /**
     * The collection of externalHoles comprised of points in {@link points}.
     * Only two points of an external hole should intersect the edges of the
     * convex hull.
     */
    public HashMap<Integer, V2D_PolygonNoInternalHolesDouble> externalHoles;

    /**
     * Create a new instance.
     *
     * @param p The polygon to copy.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V2D_PolygonNoInternalHolesDouble(V2D_PolygonNoInternalHolesDouble p, double epsilon) {
        this(p.getConvexHull(epsilon), p.getExternalEdges(), p.getExternalHoles(epsilon));
    }

    /**
     * Create a new instance.
     *
     * @param ch What {@link #ch} is set to.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V2D_PolygonNoInternalHolesDouble(V2D_ConvexHullDouble ch, double epsilon) {
        this(ch.getPointsArray(), epsilon);
    }

    /**
     * Create a new instance.
     *
     * @param points The external edge points in clockwise order.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V2D_PolygonNoInternalHolesDouble(V2D_PointDouble[] points, double epsilon) {
        super(points[0].env, V2D_VectorDouble.ZERO);
        this.points = new HashMap<>();
        ch = new V2D_ConvexHullDouble(epsilon, points);
        externalEdges = new HashMap<>();
        externalHoles = new HashMap<>();
        //if (points.length > 4) {
        PTThing p = new PTThing();
        p.p0 = points[0];
        p.isHole = false;
        p.p0int = V2D_LineSegmentDouble.isIntersectedBy(env.epsilon, p.p0, ch.edges.values());
        p.p1 = points[1];
        p.pts = new ArrayList<>();
        p.points = points;
        if (p.p0.equals(p.p1, env.epsilon)) {
            p.p1int = p.p0int;
        } else {
            this.points.put(this.points.size(), p.p0);
            externalEdges.put(externalEdges.size(), new V2D_LineSegmentDouble(p.p0, p.p1));
            p.p1int = V2D_LineSegmentDouble.isIntersectedBy(env.epsilon, p.p1, ch.edges.values());
            if (p.p0int) {
                if (!p.p1int) {
                    p.pts.add(p.p0);
                    p.isHole = true;
                }
            }
        }
        for (int i = 2; i < points.length; i++) {
            //System.out.println("i=" + i + " points.length=" + points.length);
            doThing(env.epsilon, i, p);
        }
        doThing(env.epsilon, 0, p);
//        } else {
//            if (p.isHole) {
//                if (p.p0.equals(points[2], env.epsilon)) {
//                    int debug = 1;
//                }
//                p.pts.add(p.p0);
//                p.pts.add(p.p1);
//                p.pts.add(points[2]);
//                int externalHoleID = externalHoles.size();
//                //System.out.println("externalHoleID=" + externalHoleID);
//                externalHoles.put(externalHoles.size(),
//                        new V2D_PolygonNoInternalHolesDouble(
//                                p.pts.toArray(V2D_PointDouble[]::new)));
//                p.pts = new ArrayList<>();
//                p.isHole = false;
//            //} else {
//            //    int debug = 1;
//                //V2D_LineSegmentDouble.isIntersectedBy(env.epsilon, points[2], ch.edges.values());
//            }
//        }
//        } else {
//            if (points.length == 3) {
//                this.points.put(this.points.size(), points[0]);
//                this.points.put(this.points.size(), points[1]);
//                this.points.put(this.points.size(), points[2]);
//                externalEdges.put(externalEdges.size(), new V2D_LineSegmentDouble(points[0], points[1]));
//                externalEdges.put(externalEdges.size(), new V2D_LineSegmentDouble(points[1], points[2]));
//                externalEdges.put(externalEdges.size(), new V2D_LineSegmentDouble(points[2], points[0]));
//            } else if (points.length == 4) {
//                this.points.put(this.points.size(), points[0]);
//                this.points.put(this.points.size(), points[1]);
//                this.points.put(this.points.size(), points[2]);
//                this.points.put(this.points.size(), points[3]);
//                externalEdges.put(externalEdges.size(), new V2D_LineSegmentDouble(points[0], points[1]));
//                externalEdges.put(externalEdges.size(), new V2D_LineSegmentDouble(points[1], points[2]));
//                externalEdges.put(externalEdges.size(), new V2D_LineSegmentDouble(points[2], points[3]));
//                externalEdges.put(externalEdges.size(), new V2D_LineSegmentDouble(points[3], points[0]));
//            } else {
//                int debug = 1;
//            }
//        }
    }

    private void doThing(double epsilon, int index, PTThing p) {
        p.p0 = p.p1;
        p.p0int = p.p1int;
        p.p1 = p.points[index];
        if (p.p0.equals(p.p1, epsilon)) {
            p.p1int = p.p0int;
        } else {
            p.p1int = V2D_LineSegmentDouble.isIntersectedBy(epsilon, p.p1, ch.edges.values());
            if (p.isHole) {
                if (p.p1int) {
                    p.pts.add(p.p0);
                    p.pts.add(p.p1);
                    //int externalHoleID = externalHoles.size();
                    //System.out.println("externalHoleID=" + externalHoleID);
                    try {

                        externalHoles.put(externalHoles.size(),
                                new V2D_PolygonNoInternalHolesDouble(
                                        p.pts.toArray(V2D_PointDouble[]::new), epsilon));
                    } catch (StackOverflowError e) {
                        externalHoles.put(externalHoles.size(),
                                new V2D_PolygonNoInternalHolesDouble(
                                        p.pts.toArray(V2D_PointDouble[]::new), epsilon));
                    }
                    p.pts = new ArrayList<>();
                    p.isHole = false;
                } else {
                    p.pts.add(p.p1);
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
            externalEdges.put(externalEdges.size(), new V2D_LineSegmentDouble(p.p0, p.p1));
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
     * @param ch What {@link #ch} is set to.
     * @param externalEdges What {@link #externalEdges} is set to.
     * @param externalHoles What {@link #externalHoles} is set to.
     */
    public V2D_PolygonNoInternalHolesDouble(
            V2D_ConvexHullDouble ch,
            HashMap<Integer, V2D_LineSegmentDouble> externalEdges,
            HashMap<Integer, V2D_PolygonNoInternalHolesDouble> externalHoles) {
        super(ch.env, V2D_VectorDouble.ZERO);
        this.ch = ch;
        this.externalEdges = externalEdges;
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
            s.append("\nexternalEdges (\n");
            if (externalEdges != null) {
                for (var entry : externalEdges.entrySet()) {
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
     * @return A copy of {@link externalEdges}.
     */
    public HashMap<Integer, V2D_LineSegmentDouble> getExternalEdges() {
        HashMap<Integer, V2D_LineSegmentDouble> r = new HashMap<>();
        for (V2D_LineSegmentDouble l : externalEdges.values()) {
            r.put(r.size(), new V2D_LineSegmentDouble(l));
        }
        return r;
    }

    /**
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A copy of {@link externalHoles} with the given tolerance applied.
     */
    public HashMap<Integer, V2D_PolygonNoInternalHolesDouble> getExternalHoles(double epsilon) {
        HashMap<Integer, V2D_PolygonNoInternalHolesDouble> r = new HashMap<>();
        for (V2D_PolygonNoInternalHolesDouble h : externalHoles.values()) {
            r.put(r.size(), new V2D_PolygonNoInternalHolesDouble(h, epsilon));
        }
        return r;
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
    public V2D_EnvelopeDouble getEnvelope() {
        if (en == null) {
            en = ch.getEnvelope();
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
    public boolean isIntersectedBy(V2D_PointDouble pt, double epsilon) {
        if (getEnvelope().isIntersectedBy(pt)) {
            if (ch.isIntersectedBy(pt, epsilon)) {
                if (V2D_LineSegmentDouble.isIntersectedBy(epsilon, pt, externalEdges.values())) {
                    return true;
                }
                return !externalHoles.values().parallelStream().anyMatch(x -> x.isIntersectedBy(pt, epsilon));
            }
        }
        return false;
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
        if (isIntersectedBy(pt, epsilon)) {
            return !V2D_LineSegmentDouble.isIntersectedBy(epsilon, pt, externalEdges.values());
        }
        return false;
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
        if (contains(ls.getP(), epsilon)) {
            return contains(ls.getQ(), epsilon);
        }
        return false;
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
        if (contains(t.getP(), epsilon)) {
            if (contains(t.getQ(), epsilon)) {
                return contains(t.getR(), epsilon);
            }
        }
        return false;
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
        if (contains(r.getP(), epsilon)) {
            if (contains(r.getQ(), epsilon)) {
                if (contains(r.getR(), epsilon)) {
                    return contains(r.getS(), epsilon);
                }
            }
        }
        return false;
    }

    /**
     * Identify if this contains aabb.
     *
     * @param aabb The envelope to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff there is containment.
     */
    public boolean contains(V2D_EnvelopeDouble aabb, double epsilon) {
        double xmin = aabb.getXMin();
        double xmax = aabb.getXMax();
        double ymin = aabb.getYMin();
        double ymax = aabb.getYMax();
        if (contains(new V2D_PointDouble(env, xmin, ymin), epsilon)) {
            if (contains(new V2D_PointDouble(env, xmin, ymax), epsilon)) {
                if (contains(new V2D_PointDouble(env, xmax, ymax), epsilon)) {
                    return contains(new V2D_PointDouble(env, xmax, ymin), epsilon);
                }
            }
        }
        return false;
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
        if (isIntersectedBy(ch, epsilon)) {
            //return Arrays.asList(ch.getPointsArray()).parallelStream().allMatch(x -> contains(x, epsilon));
            return ch.getPoints().values().parallelStream().allMatch(x -> contains(x, epsilon));
        }
        return false;
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
        if (isIntersectedBy(p, epsilon)) {
            if (p.externalEdges.values().parallelStream().anyMatch(x
                    -> V2D_LineSegmentDouble.isIntersectedBy(epsilon, x, externalEdges.values()))) {
                return false;
            }
        }
        return false;
    }

    /**
     * Identify if this is intersected by l.
     *
     * @param l The line segment to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff this is intersected by l.
     */
    public boolean isIntersectedBy(V2D_LineSegmentDouble l, double epsilon) {
        en = getEnvelope();
        // Check envelopes intersect.
        if (l.getEnvelope().isIntersectedBy(en, epsilon)) {
            if (l.isIntersectedBy(en, epsilon)) {
                if (ch.isIntersectedBy(l, epsilon)) {
                    if (V2D_LineSegmentDouble.isIntersectedBy(epsilon, l, externalEdges.values())) {
                        return true;
                    }
                    return !externalHoles.values().parallelStream().anyMatch(x -> x.isIntersectedBy(l, epsilon));
                }
            }
        }
        return false;
    }

    /**
     * Identify if this is intersected by the triangle.
     *
     * @param t The triangle to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff this is intersected by {@code t}.
     */
    public boolean isIntersectedBy(V2D_TriangleDouble t, double epsilon) {
        if (t.isIntersectedBy(getEnvelope(), epsilon)) {
            if (isIntersectedBy(t.getEnvelope(), epsilon)) {                
                // If any of the edges intersect or if one geometry contains the other, there is an intersection.
                if (getExternalEdges().values().parallelStream().anyMatch(
                        x -> V2D_LineSegmentDouble.isIntersectedBy(epsilon, x, t.getExternalEdges()))) {
                    return true;
                }
                if (ch.isIntersectedBy(t, epsilon)) {
                    V2D_PointDouble tp = t.getP();
                    if (isIntersectedBy(tp, epsilon)) {
                        return true;
                    }
                    V2D_PointDouble tq = t.getQ();
                    if (isIntersectedBy(tq, epsilon)) {
                        return true;
                    }
                    V2D_PointDouble tr = t.getR();
                    if (isIntersectedBy(tr, epsilon)) {
                        return true;
                    }
                    if (t.getExternalEdges().parallelStream().anyMatch(x
                            -> V2D_LineSegmentDouble.isIntersectedBy(
                                    epsilon, x, externalEdges.values()))) {
                        return true;
                    }
                    return !externalHoles.values().parallelStream().anyMatch(x
                            -> x.isIntersectedBy(tp, epsilon)
                            || x.isIntersectedBy(tq, epsilon)
                            || x.isIntersectedBy(tr, epsilon));
                }
            }
        }
        return false;
    }

    /**
     * Identify if this is intersected by the rectangle.
     *
     * @param r The convex hull to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff this is intersected by {@code r}.
     */
    public boolean isIntersectedBy(V2D_RectangleDouble r, double epsilon) {
        // Check envelopes intersect.
        if (r.isIntersectedBy(getEnvelope(), epsilon)) {
            if (isIntersectedBy(r.getEnvelope(), epsilon)) {
                // These could be parallelised:
                if (isIntersectedBy(r.getPQR(), epsilon)) {
                    return true;
                }
                if (isIntersectedBy(r.getRSP(), epsilon)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Identify if this is intersected by the convex hull.
     *
     * @param ch The convex hull to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff this is intersected by {@code ch}.
     */
    public boolean isIntersectedBy(V2D_ConvexHullDouble ch, double epsilon) {
        if (ch.isIntersectedBy(getEnvelope(), epsilon)) {
            if (isIntersectedBy(ch.getEnvelope(), epsilon)) {
                // If any of the edges intersect or if one geometry contains the other, there is an intersection.
                if (getExternalEdges().values().parallelStream().anyMatch(x -> V2D_LineSegmentDouble.isIntersectedBy(epsilon, x, ch.getEdges().values()))) {
                    return true;
                }
                //if (Arrays.asList(ch.getPoints()).parallelStream().anyMatch(x -> isIntersectedBy(x, epsilon))) {
                if (ch.getPoints().values().parallelStream().anyMatch(x -> isIntersectedBy(x, epsilon))) {
                    return true;
                }
                //if (Arrays.asList(getPoints()).parallelStream().anyMatch(x -> ch.isIntersectedBy(x, epsilon))) {
                if (getPoints().values().parallelStream().anyMatch(x -> ch.isIntersectedBy(x, epsilon))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Identify if this is intersected by the polygon.
     *
     * @param p The convex hull to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff this is intersected by {@code p}.
     */
    public boolean isIntersectedBy(V2D_PolygonNoInternalHolesDouble p, double epsilon) {
        if (p.isIntersectedBy(getEnvelope(), epsilon)) {
            if (isIntersectedBy(p.getEnvelope(), epsilon)) {
                if (p.isIntersectedBy(ch, epsilon)) {
                    if (isIntersectedBy(p.ch, epsilon)) {
                        // If any of the edges intersect or if one polygon contains the other, there is an intersection.
                        if (getExternalEdges().values().parallelStream().anyMatch(x -> V2D_LineSegmentDouble.isIntersectedBy(epsilon, x, p.getExternalEdges().values()))) {
                            return true;
                        }
                        //if (Arrays.asList(getPoints()).parallelStream().anyMatch(x -> p.isIntersectedBy(x, epsilon))) {
                        if (getPoints().values().parallelStream().anyMatch(x -> p.isIntersectedBy(x, epsilon))) {
                            return true;
                        }
                        //if (Arrays.asList(p.getPoints()).parallelStream().anyMatch(x -> isIntersectedBy(x, epsilon))) {
                        if (p.getPoints().values().parallelStream().anyMatch(x -> isIntersectedBy(x, epsilon))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
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
        if (externalEdges != null) {
            for (int i = 0; i < externalEdges.size(); i++) {
                externalEdges.get(i).translate(v);
            }
        }
        if (externalHoles != null) {
            for (int i = 0; i < externalHoles.size(); i++) {
                externalHoles.get(i).translate(v);
            }
        }
    }

    @Override
    public V2D_PolygonNoInternalHolesDouble rotate(V2D_PointDouble pt, double theta,
            double epsilon) {
        theta = Math_AngleDouble.normalise(theta);
        if (theta == 0) {
            return new V2D_PolygonNoInternalHolesDouble(this, epsilon);
        } else {
            return rotateN(pt, theta, epsilon);
        }
    }

    @Override
    public V2D_PolygonNoInternalHolesDouble rotateN(V2D_PointDouble pt, double theta,
            double epsilon) {
        V2D_ConvexHullDouble rch = getConvexHull(epsilon).rotate(pt, theta, epsilon);
        HashMap<Integer, V2D_LineSegmentDouble> rExternalEdges = new HashMap<>();
        if (externalEdges != null) {
            for (int i = 0; i < externalEdges.size(); i++) {
                rExternalEdges.put(rExternalEdges.size(), externalEdges.get(i).rotate(pt, theta, epsilon));
            }
        }
        HashMap<Integer, V2D_PolygonNoInternalHolesDouble> rExternalHoles = new HashMap<>();
        if (externalHoles != null) {
            for (int i = 0; i < externalHoles.size(); i++) {
                rExternalHoles.put(rExternalHoles.size(), externalHoles.get(i).rotate(pt, theta, epsilon));
            }
        }
        return new V2D_PolygonNoInternalHolesDouble(rch, rExternalEdges, rExternalHoles);
    }

    @Override
    public boolean isIntersectedBy(V2D_EnvelopeDouble aabb, double epsilon) {
        if (getEnvelope().isIntersectedBy(aabb)) {
            if (ch.isIntersectedBy(aabb, epsilon)) {
                return true;
            }
        }
        return false;
    }

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
