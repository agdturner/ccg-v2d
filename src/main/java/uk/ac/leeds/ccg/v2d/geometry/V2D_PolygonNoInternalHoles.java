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
import java.util.Collection;
import java.util.HashMap;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_LineSegmentDouble;

/**
 * For representing a polygon with no internal holes. External holes are similar
 * polygons that share some part of an edge with the convex hull.
 *
 * @author Andy Turner
 * @version 2.0
 */
public class V2D_PolygonNoInternalHoles extends V2D_Shape {

    private static final long serialVersionUID = 1L;

    /**
     * The collection of points that form a linear ring in a clockwise order.
     * The linear ring should not be self intersecting. Keys are identifiers.
     */
    public HashMap<Integer, V2D_Point> points;

    /**
     * The convex hull.
     */
    public V2D_ConvexHull ch;

    /**
     * The collection of externalEdges comprised of points in {@link points}.
     */
    public HashMap<Integer, V2D_LineSegment> externalEdges;

    /**
     * The collection of externalHoles comprised of points in {@link points}.
     * Only two points of an external hole should intersect the edges of the
     * convex hull.
     */
    public HashMap<Integer, V2D_PolygonNoInternalHoles> externalHoles;

    /**
     * Create a new instance.
     *
     * @param p The polygon to copy.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V2D_PolygonNoInternalHoles(V2D_PolygonNoInternalHoles p, int oom, RoundingMode rm) {
        this(p.getPoints(oom, rm), p.getConvexHull(oom, rm),
                p.getExternalEdges(), p.getExternalHoles(oom, rm));
    }

    /**
     * Create a new instance.
     *
     * @param ch What {@link #ch} is set to.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V2D_PolygonNoInternalHoles(V2D_ConvexHull ch, int oom, RoundingMode rm) {
        this(ch.getPointsArray(oom, rm), oom, rm);
    }

    /**
     * Create a new instance.
     *
     * @param points The external edge points in clockwise order.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V2D_PolygonNoInternalHoles(V2D_Point[] points, int oom, RoundingMode rm) {
        super(points[0].env, V2D_Vector.ZERO);
        this.points = new HashMap<>();
        ch = new V2D_ConvexHull(oom, rm, points);
        // construct edges and points
        externalEdges = new HashMap<>();
        externalHoles = new HashMap<>();
        PTThing p = new PTThing();
        p.p0 = points[0];
        p.isHole = false;
        p.p0int = V2D_LineSegment.isIntersectedBy(oom, rm, p.p0, ch.edges.values());
        p.p1 = points[1];
        p.pts = new ArrayList<>();
        p.points = points;
        if (p.p0.equals(p.p1, oom, rm)) {
            p.p1int = p.p0int;
        } else {
            this.points.put(this.points.size(), p.p0);
            externalEdges.put(externalEdges.size(), new V2D_LineSegment(p.p0, p.p1, oom, rm));
            p.p1int = V2D_LineSegment.isIntersectedBy(oom, rm, p.p1, ch.edges.values());
            if (p.p0int) {
                if (!p.p1int) {
                    p.pts.add(p.p0);
                    p.isHole = true;
                }
            }
        }
        for (int i = 2; i < points.length; i++) {
            doThing(oom, rm, i, p);
        }
        doThing(oom, rm, 0, p);
    }

    private void doThing(int oom, RoundingMode rm, int index, PTThing p) {
        p.p0 = p.p1;
        p.p0int = p.p1int;
        p.p1 = p.points[index];
        if (p.p0.equals(p.p1, env.oom, env.rm)) {
            p.p1int = p.p0int;
        } else {
            p.p1int = V2D_LineSegment.isIntersectedBy(env.oom, env.rm, p.p1, ch.edges.values());
            if (p.isHole) {
                if (p.p1int) {
                    p.pts.add(p.p0);
                    p.pts.add(p.p1);
                    externalHoles.put(externalHoles.size(),
                            new V2D_PolygonNoInternalHoles(
                                    p.pts.toArray(V2D_Point[]::new), oom, rm));
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
            externalEdges.put(externalEdges.size(), new V2D_LineSegment(p.p0, p.p1, oom, rm));
        }
    }

    protected class PTThing {

        boolean isHole;
        boolean p0int;
        boolean p1int;
        ArrayList<V2D_Point> pts;
        V2D_Point[] points;
        V2D_Point p0;
        V2D_Point p1;

        PTThing() {
        }
    }

    /**
     * Create a new instance.
     *
     *
     * @param ch What {@link #ch} is set to.
     * @param externalEdges What {@link #externalEdges} is set to.
     * @param externalHoles What {@link #externalHoles} is set to.
     */
    public V2D_PolygonNoInternalHoles(HashMap<Integer, V2D_Point> points,
            V2D_ConvexHull ch,
            HashMap<Integer, V2D_LineSegment> externalEdges,
            HashMap<Integer, V2D_PolygonNoInternalHoles> externalHoles) {
        super(ch.env, V2D_Vector.ZERO);
        this.points = points;
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A copy of {@link exterior#ch} with the given tolerance applied.
     */
    public V2D_ConvexHull getConvexHull(int oom, RoundingMode rm) {
        return new V2D_ConvexHull(ch, oom, rm);
    }

    /**
     * @return A copy of {@link externalEdges}.
     */
    public HashMap<Integer, V2D_LineSegment> getExternalEdges() {
        HashMap<Integer, V2D_LineSegment> r = new HashMap<>();
        for (V2D_LineSegment l : externalEdges.values()) {
            r.put(r.size(), new V2D_LineSegment(l));
        }
        return r;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A copy of {@link externalHoles} with the given tolerance applied.
     */
    public HashMap<Integer, V2D_PolygonNoInternalHoles> getExternalHoles(int oom, RoundingMode rm) {
        HashMap<Integer, V2D_PolygonNoInternalHoles> r = new HashMap<>();
        for (V2D_PolygonNoInternalHoles h : externalHoles.values()) {
            r.put(r.size(), new V2D_PolygonNoInternalHoles(h, oom, rm));
        }
        return r;
    }

    @Override
    public V2D_Point[] getPointsArray(int oom, RoundingMode rm) {
        Collection<V2D_Point> pts = points.values();
        return pts.toArray(V2D_Point[]::new);
    }

    @Override
    public HashMap<Integer, V2D_Point> getPoints(int oom, RoundingMode rm) {
        return points;
    }

    @Override
    public V2D_Envelope getEnvelope(int oom, RoundingMode rm) {
        if (en == null) {
            en = ch.getEnvelope(oom, rm);
        }
        return en;
    }

    /**
     * Identify if this is intersected by pt.
     *
     * @param pt The point to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is an intersection.
     */
    public boolean isIntersectedBy(V2D_Point pt, int oom, RoundingMode rm) {
        if (getEnvelope(oom, rm).isIntersectedBy(pt, oom, rm)) {
            if (ch.isIntersectedBy(pt, oom, rm)) {
                if (V2D_LineSegment.isIntersectedBy(oom, rm, pt, externalEdges.values())) {
                    return true;
                }
                return !externalHoles.values().parallelStream().anyMatch(x -> x.isIntersectedBy(pt, oom, rm));
            }
        }
        return false;
    }

    /**
     * Identify if this contains pt.
     *
     * @param pt The point to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is an intersection.
     */
    public boolean contains(V2D_Point pt, int oom, RoundingMode rm) {
        if (isIntersectedBy(pt, oom, rm)) {
            return !V2D_LineSegment.isIntersectedBy(oom, rm, pt, externalEdges.values());
        }
        return false;
    }

    /**
     * Identify if this contains ls.
     *
     * @param ls The line segment to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is an intersection.
     */
    public boolean contains(V2D_LineSegment ls, int oom, RoundingMode rm) {
        if (contains(ls.getP(), oom, rm)) {
            return contains(ls.getQ(oom, rm), oom, rm);
        }
        return false;
    }

    /**
     * Identify if this contains t.
     *
     * @param t The triangle to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is containment.
     */
    public boolean contains(V2D_Triangle t, int oom, RoundingMode rm) {
        if (contains(t.getP(oom, rm), oom, rm)) {
            if (contains(t.getQ(oom, rm), oom, rm)) {
                return contains(t.getR(oom, rm), oom, rm);
            }
        }
        return false;
    }

    /**
     * Identify if this contains r.
     *
     * @param r The rectangle to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is containment.
     */
    public boolean contains(V2D_Rectangle r, int oom, RoundingMode rm) {
        if (contains(r.getP(oom, rm), oom, rm)) {
            if (contains(r.getQ(oom, rm), oom, rm)) {
                if (contains(r.getR(oom, rm), oom, rm)) {
                    return contains(r.getS(oom, rm), oom, rm);
                }
            }
        }
        return false;
    }

    /**
     * Identify if this contains aabb.
     *
     * @param aabb The envelope to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is containment.
     */
    public boolean contains(V2D_Envelope aabb, int oom, RoundingMode rm) {
        BigRational xmin = aabb.getXMin(oom);
        BigRational xmax = aabb.getXMax(oom);
        BigRational ymin = aabb.getYMin(oom);
        BigRational ymax = aabb.getYMax(oom);
        if (contains(new V2D_Point(env, xmin, ymin), oom, rm)) {
            if (contains(new V2D_Point(env, xmin, ymax), oom, rm)) {
                if (contains(new V2D_Point(env, xmax, ymax), oom, rm)) {
                    return contains(new V2D_Point(env, xmax, ymin), oom, rm);
                }
            }
        }
        return false;
    }

    /**
     * Identify if this contains ch.
     *
     * @param ch The convex hull to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is containment.
     */
    public boolean contains(V2D_ConvexHull ch, int oom, RoundingMode rm) {
        if (isIntersectedBy(ch, oom, rm)) {
            //return Arrays.asList(ch.getPointsArray(oom, rm)).parallelStream().allMatch(x -> contains(x, oom, rm));
            return ch.getPoints(oom, rm).values().parallelStream().allMatch(x -> contains(x, oom, rm));
        }
        return false;
    }

    /**
     * Identify if this contains the polygon.
     *
     * @param p The polygon to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@code this} contains {@code p}.
     */
    public boolean contains(V2D_PolygonNoInternalHoles p, int oom, RoundingMode rm) {
        if (isIntersectedBy(p, oom, rm)) {
            if (p.externalEdges.values().parallelStream().anyMatch(x
                    -> V2D_LineSegment.isIntersectedBy(oom, rm, x, externalEdges.values()))) {
                return false;
            }
        }
        return false;
    }

    /**
     * Identify if this is intersected by l.
     *
     * @param l The line segment to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is an intersection.
     */
    public boolean isIntersectedBy(V2D_LineSegment l, int oom, RoundingMode rm) {
        en = getEnvelope(oom, rm);
        // Check envelopes intersect.
        if (l.getEnvelope(oom, rm).isIntersectedBy(en, oom)) {
            if (l.isIntersectedBy(en, oom, rm)) {
                if (ch.isIntersectedBy(l, oom, rm)) {
                    if (V2D_LineSegment.isIntersectedBy(oom, rm, l, externalEdges.values())) {
                        return true;
                    }
                    return !externalHoles.values().parallelStream().anyMatch(x -> x.isIntersectedBy(l, oom, rm));
                }
            }
        }
        return false;
    }

    /**
     * Identify if this is intersected by t.
     *
     * @param t The triangle to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is an intersection.
     */
    public boolean isIntersectedBy(V2D_Triangle t, int oom, RoundingMode rm) {
        if (t.isIntersectedBy(getEnvelope(oom, rm), oom, rm)) {
            if (isIntersectedBy(t.getEnvelope(oom, rm), oom, rm)) {
                // If any of the edges intersect or if one geometry contains the other, there is an intersection.
                if (getExternalEdges().values().parallelStream().anyMatch(
                        x -> V2D_LineSegment.isIntersectedBy(oom, rm, x, t.getExternalEdges(oom, rm)))) {
                    return true;
                }
                if (ch.isIntersectedBy(t, oom, rm)) {
                    V2D_Point tp = t.getP(oom, rm);
                    if (isIntersectedBy(tp, oom, rm)) {
                        return true;
                    }
                    V2D_Point tq = t.getQ(oom, rm);
                    if (isIntersectedBy(tq, oom, rm)) {
                        return true;
                    }
                    V2D_Point tr = t.getR(oom, rm);
                    if (isIntersectedBy(tr, oom, rm)) {
                        return true;
                    }
                    if (t.getExternalEdges(oom, rm).parallelStream().anyMatch(x
                            -> V2D_LineSegment.isIntersectedBy(oom, rm, x, externalEdges.values()))) {
                        return true;
                    }
                    return !(externalHoles.values().parallelStream().anyMatch(x
                            -> x.isIntersectedBy(tp, oom, rm)
                            || x.isIntersectedBy(tq, oom, rm)
                            || x.isIntersectedBy(tr, oom, rm)));
                }
            }
        }
        return false;
    }

    /**
     * Identify if this is intersected by point {@code p}.
     *
     * @param r The convex hull to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff the geometry is intersected by {@code ch.
     */
    public boolean isIntersectedBy(V2D_Rectangle r, int oom, RoundingMode rm) {
        // Check envelopes intersect.
        if (r.isIntersectedBy(getEnvelope(oom, rm), oom, rm)) {
            if (isIntersectedBy(r.getEnvelope(oom, rm), oom, rm)) {
                // These could be parallelised:
                if (isIntersectedBy(r.getPQR(), oom, rm)) {
                    return true;
                }
                if (isIntersectedBy(r.getRSP(), oom, rm)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Identify if this is intersected by {@code ch}.
     *
     * @param ch The convex hull to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff this is intersected by {@code ch}.
     */
    public boolean isIntersectedBy(V2D_ConvexHull ch, int oom, RoundingMode rm) {
        // Check envelopes intersect.
        if (ch.isIntersectedBy(getEnvelope(oom, rm), oom, rm)) {
            if (isIntersectedBy(ch.getEnvelope(oom, rm), oom, rm)) {
                // If any of the edges intersect or if one geometry contains the other, there is an intersection.
                if (getExternalEdges().values().parallelStream().anyMatch(x -> V2D_LineSegment.isIntersectedBy(oom, rm, x, ch.getEdges(oom, rm).values()))) {
                    return true;
                }
                //if (Arrays.asList(ch.getPointsArray(oom, rm)).parallelStream().anyMatch(x -> isIntersectedBy(x, oom, rm))) {
                if (ch.getPoints(oom, rm).values().parallelStream().anyMatch(x -> isIntersectedBy(x, oom, rm))) {
                    return true;
                }
                //if (Arrays.asList(getPointsArray(oom, rm)).parallelStream().anyMatch(x -> ch.isIntersectedBy(x, oom, rm))) {
                if (getPoints(oom, rm).values().parallelStream().anyMatch(x -> ch.isIntersectedBy(x, oom, rm))) {
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff this is intersected by {@code p}.
     */
    public boolean isIntersectedBy(V2D_PolygonNoInternalHoles p, int oom, RoundingMode rm) {
        if (p.isIntersectedBy(getEnvelope(oom, rm), oom, rm)) {
            if (isIntersectedBy(p.getEnvelope(oom, rm), oom, rm)) {
                if (p.isIntersectedBy(ch, oom, rm))  {
                    if (isIntersectedBy(p.ch, oom, rm)) {
                        // If any of the edges intersect or if one polygon contains the other, there is an intersection.
                        if (getExternalEdges().values().parallelStream().anyMatch(x -> V2D_LineSegment.isIntersectedBy(oom, rm, x, p.getExternalEdges().values()))) {
                            return true;
                        }
                        //if (Arrays.asList(getPointsArray(oom, rm)).parallelStream().anyMatch(x -> p.isIntersectedBy(x, oom, rm))) {
                        if (getPoints(oom, rm).values().parallelStream().anyMatch(x -> p.isIntersectedBy(x, oom, rm))) {
                            return true;
                        }
                        //if (Arrays.asList(p.getPointsArray(oom, rm)).parallelStream().anyMatch(x -> isIntersectedBy(x, oom, rm))) {
                        if (p.getPoints(oom, rm).values().parallelStream().anyMatch(x -> isIntersectedBy(x, oom, rm))) {
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
    public void translate(V2D_Vector v, int oom, RoundingMode rm) {
        super.translate(v, oom, rm);
        if (en != null) {
            en.translate(v, oom, rm);
        }
        ch.translate(v, oom, rm);
        if (externalEdges != null) {
            for (int i = 0; i < externalEdges.size(); i++) {
                externalEdges.get(i).translate(v, oom, rm);
            }
        }
        if (externalHoles != null) {
            for (int i = 0; i < externalHoles.size(); i++) {
                externalHoles.get(i).translate(v, oom, rm);
            }
        }
    }

    @Override
    public V2D_PolygonNoInternalHoles rotate(V2D_Point pt, BigRational theta,
            Math_BigDecimal bd, int oom, RoundingMode rm) {
        theta = Math_AngleBigRational.normalise(theta, bd, oom, rm);
        if (theta.compareTo(BigRational.ZERO) == 0) {
            return new V2D_PolygonNoInternalHoles(this, oom, rm);
        } else {
            return rotateN(pt, theta, bd, oom, rm);
        }
    }

    @Override
    public V2D_PolygonNoInternalHoles rotateN(V2D_Point pt, BigRational theta,
            Math_BigDecimal bd, int oom, RoundingMode rm) {
        V2D_ConvexHull rch = getConvexHull(oom, rm).rotate(pt, theta, bd, oom, rm);
        HashMap<Integer, V2D_Point> rPoints = new HashMap<>();
        for (var x : this.points.entrySet()) {
            rPoints.put(x.getKey(), x.getValue().rotateN(pt, theta, bd, oom, rm));
        }
        HashMap<Integer, V2D_LineSegment> rExternalEdges = new HashMap<>();
        if (externalEdges != null) {
            for (int i = 0; i < externalEdges.size(); i++) {
                rExternalEdges.put(rExternalEdges.size(), externalEdges.get(i).rotate(pt, theta, bd, oom, rm));
            }
        }
        HashMap<Integer, V2D_PolygonNoInternalHoles> rExternalHoles = new HashMap<>();
        if (externalHoles != null) {
            for (int i = 0; i < externalHoles.size(); i++) {
                rExternalHoles.put(rExternalHoles.size(), externalHoles.get(i).rotate(pt, theta, bd, oom, rm));
            }
        }
        return new V2D_PolygonNoInternalHoles(rPoints, rch, rExternalEdges, rExternalHoles);
    }

    @Override
    public boolean isIntersectedBy(V2D_Envelope aabb, int oom, RoundingMode rm) {
        if (getEnvelope(oom, rm).isIntersectedBy(aabb, oom)) {
            if (ch.isIntersectedBy(aabb, oom, rm)) {
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
    public int addExternalHole(V2D_PolygonNoInternalHoles p) {
        int pid = externalHoles.size();
        externalHoles.put(pid, p);
        return pid;
    }
}
