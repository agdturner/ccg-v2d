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
public class V2D_PolygonNoInternalHolesDouble extends V2D_FiniteGeometryDouble {

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
     */
    public V2D_PolygonNoInternalHolesDouble(V2D_ConvexHullDouble ch) {
        this(ch, new HashMap<>(), new HashMap<>());
    }

    /**
     * Create a new instance.
     *
     * @param points The external edge points in clockwise order.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V2D_PolygonNoInternalHolesDouble(V2D_PointDouble[] points, double epsilon) {
        super();
        ch = new V2D_ConvexHullDouble(epsilon, points);
        // construct edges and points
        externalEdges = new HashMap<>();
        externalHoles = new HashMap<>();
        V2D_PointDouble p0 = points[0];
        boolean isHole = false;
        boolean p0int = V2D_LineSegmentDouble.isIntersectedBy(epsilon, p0, ch.edges.values());
        V2D_PointDouble p1 = points[1];
        externalEdges.put(externalEdges.size(), new V2D_LineSegmentDouble(p0, p1));
        boolean p1int = V2D_LineSegmentDouble.isIntersectedBy(epsilon, p1, ch.edges.values());
        ArrayList<V2D_PointDouble> pts = new ArrayList<>();
        if (p0int) {
            if (!p1int) {
                pts.add(p0);
                isHole = true;
            }
        }
        for (int i = 2; i < this.points.size(); i++) {
            p0 = p1;
            p0int = p1int;
            p1 = this.points.get(i);
            p1int = V2D_LineSegmentDouble.isIntersectedBy(epsilon, p1, ch.edges.values());
            if (isHole) {
                if (p1int) {
                    externalHoles.put(externalHoles.size(), new V2D_PolygonNoInternalHolesDouble(pts.toArray(V2D_PointDouble[]::new), epsilon));
                    pts = new ArrayList<>();
                    isHole = false;
                } else {
                    pts.add(p1);
                }
            } else {
                if (p0int) {
                    if (!p1int) {
                        pts.add(p0);
                        isHole = true;
                    }
                }
            }
            externalEdges.put(externalEdges.size(), new V2D_LineSegmentDouble(p0, p1));
        }
        externalEdges.put(externalEdges.size(), new V2D_LineSegmentDouble(p1, this.points.get(0)));
    }

    /**
     * Create a new instance.
     *
     * @param ch What {@link #ch} is set to.
     * @param externalEdges What {@link #externalEdges} is set to.
     * @param externalHoles What {@link #externalHoles} is set to.
     */
    public V2D_PolygonNoInternalHolesDouble(V2D_ConvexHullDouble ch,
            HashMap<Integer, V2D_LineSegmentDouble> externalEdges,
            HashMap<Integer, V2D_PolygonNoInternalHolesDouble> externalHoles) {
        super();
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A copy of {@link exterior#ch} with the given tolerance applied.
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
    public V2D_PointDouble[] getPoints() {
        Collection<V2D_PointDouble> pts = points.values();
        return pts.toArray(V2D_PointDouble[]::new);
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
                return !externalHoles.values().parallelStream().anyMatch(x -> x.contains(pt, epsilon));
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
     * Identify if this contains ch.
     *
     * @param ch The convex hull to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff there is containment.
     */
    public boolean contains(V2D_ConvexHullDouble ch, double epsilon) {
        if (isIntersectedBy(ch, epsilon)) {
            return Arrays.asList(ch.getPoints()).parallelStream().allMatch(x -> contains(x, epsilon));
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
                            -> V2D_LineSegmentDouble.isIntersectedBy(epsilon, x, externalEdges.values()))) {
                        return true;
                    }
                    if (externalHoles.values().parallelStream().anyMatch(x
                            -> x.isIntersectedBy(tp, epsilon)
                            || x.isIntersectedBy(tq, epsilon)
                            || x.isIntersectedBy(tr, epsilon))) {
                        return false;
                    }
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
                if (Arrays.asList(ch.getPoints()).parallelStream().anyMatch(x -> isIntersectedBy(x, epsilon))) {
                    return true;
                }
                if (Arrays.asList(getPoints()).parallelStream().anyMatch(x -> ch.isIntersectedBy(x, epsilon))) {
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
                if (Arrays.asList(getPoints()).parallelStream().anyMatch(x -> p.isIntersectedBy(x, epsilon))) {
                    return true;
                }
                if (Arrays.asList(p.getPoints()).parallelStream().anyMatch(x -> isIntersectedBy(x, epsilon))) {
                    return true;
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
        en = getEnvelope();
        if (en.isIntersectedBy(aabb)) {
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
        int id = externalHoles.size();
        externalHoles.put(id, p);
        return id;
    }
}
