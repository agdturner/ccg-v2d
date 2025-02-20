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
     * The convex hull.
     */
    public V2D_ConvexHull ch;

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
    public V2D_PolygonNoInternalHoles(V2D_PolygonNoInternalHoles p, int oom,
            RoundingMode rm) {
        this(p.getPoints(oom, rm), p.getConvexHull(oom, rm),
                p.getEdges(), p.getExternalHoles());
    }

    /**
     * Create a new instance.
     *
     * @param ch What {@link #ch} is set to.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V2D_PolygonNoInternalHoles(V2D_ConvexHull ch, int oom,
            RoundingMode rm) {
        this(ch.getPointsArray(oom, rm), oom, rm);
    }

    /**
     * Create a new instance.
     *
     * @param points The external edge points in clockwise order.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V2D_PolygonNoInternalHoles(V2D_Point[] points, int oom,
            RoundingMode rm) {
        super(points[0].env, V2D_Vector.ZERO);
        this.points = new HashMap<>();
        ch = new V2D_ConvexHull(oom, rm, points);
        edges = new HashMap<>();
        externalHoles = new HashMap<>();
        PTThing p = new PTThing();
        p.p0 = points[0];
        p.isHole = false;
        p.p0int = V2D_LineSegment.isIntersectedBy(oom, rm, p.p0,
                ch.edges.values());
        p.p1 = points[1];
        p.pts = new ArrayList<>();
        p.points = points;
        if (p.p0.equals(p.p1, oom, rm)) {
            p.p1int = p.p0int;
        } else {
            this.points.put(this.points.size(), p.p0);
            edges.put(edges.size(), new V2D_LineSegment(p.p0, p.p1, oom, rm));
            p.p1int = V2D_LineSegment.isIntersectedBy(oom, rm, p.p1,
                    ch.edges.values());
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
            p.p1int = V2D_LineSegment.isIntersectedBy(env.oom, env.rm, p.p1,
                    ch.edges.values());
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
            edges.put(edges.size(), new V2D_LineSegment(p.p0, p.p1, oom, rm));
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
     * @param points What {@link #points} is set to.
     * @param ch What {@link #ch} is set to.
     * @param edges What {@link #edges} is set to.
     * @param externalHoles What {@link #externalHoles} is set to.
     */
    public V2D_PolygonNoInternalHoles(HashMap<Integer, V2D_Point> points,
            V2D_ConvexHull ch, HashMap<Integer, V2D_LineSegment> edges,
            HashMap<Integer, V2D_PolygonNoInternalHoles> externalHoles) {
        super(ch.env, V2D_Vector.ZERO);
        this.points = points;
        this.ch = ch;
        this.edges = edges;
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A copy of {@link exterior#ch} with the given tolerance applied.
     */
    public V2D_ConvexHull getConvexHull(int oom, RoundingMode rm) {
        return new V2D_ConvexHull(ch, oom, rm);
    }

    /**
     * @return A copy of {@link edges}.
     */
    public HashMap<Integer, V2D_LineSegment> getEdges() {
        HashMap<Integer, V2D_LineSegment> r = new HashMap<>();
        for (V2D_LineSegment l : edges.values()) {
            r.put(r.size(), new V2D_LineSegment(l));
        }
        return r;
    }

    /**
     * @return {@link edges}.
     */
    @Override
    public HashMap<Integer, V2D_LineSegment> getEdges(int oom, RoundingMode rm) {
        return edges;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A copy of {@link externalHoles} with the given tolerance applied.
     */
    public HashMap<Integer, V2D_PolygonNoInternalHoles> getExternalHoles(
            int oom, RoundingMode rm) {
        HashMap<Integer, V2D_PolygonNoInternalHoles> r = new HashMap<>();
        for (V2D_PolygonNoInternalHoles h : externalHoles.values()) {
            r.put(r.size(), new V2D_PolygonNoInternalHoles(h, oom, rm));
        }
        return r;
    }

    /**
     * @return {@link externalHoles}.
     */
    public HashMap<Integer, V2D_PolygonNoInternalHoles> getExternalHoles() {
        return externalHoles;
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
        if (ch.isIntersectedBy(pt, oom, rm)) {
            return isIntersectedBy0(pt, oom, rm);
        }
        return false;
    }

    /**
     * Identify if this is intersected by pt.
     *
     * @param pt The point to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is an intersection.
     */
    public boolean isIntersectedBy0(V2D_Point pt, int oom, RoundingMode rm) {
        return V2D_LineSegment.isIntersectedBy(oom, rm, pt, edges.values())
                || !externalHoles.values().parallelStream().anyMatch(x
                        -> x.isIntersectedBy(pt, oom, rm));
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
        return isIntersectedBy(pt, oom, rm)
                && !V2D_LineSegment.isIntersectedBy(oom, rm, pt,
                        edges.values());
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
        return contains(ls.getP(), oom, rm)
                && contains(ls.getQ(oom, rm), oom, rm);
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
        return contains(t.getP(oom, rm), oom, rm)
                && contains(t.getQ(oom, rm), oom, rm)
                && contains(t.getR(oom, rm), oom, rm);
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
        return contains(r.getP(oom, rm), oom, rm)
                && contains(r.getQ(oom, rm), oom, rm)
                && contains(r.getR(oom, rm), oom, rm)
                && contains(r.getS(oom, rm), oom, rm);
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
        return contains(new V2D_Point(env, xmin, ymin), oom, rm)
                && contains(new V2D_Point(env, xmin, ymax), oom, rm)
                && contains(new V2D_Point(env, xmax, ymax), oom, rm)
                && contains(new V2D_Point(env, xmax, ymin), oom, rm);
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
        return this.ch.isIntersectedBy(ch, oom, rm)
                && ch.getPoints(oom, rm).values().parallelStream().allMatch(x
                        -> contains(x, oom, rm));
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
        return ch.isIntersectedBy(p.ch, oom, rm)
                && p.getPoints(oom, rm).values().parallelStream().allMatch(x
                        -> contains(x, oom, rm));
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
        return ch.isIntersectedBy(l, oom, rm)
                && (V2D_LineSegment.isIntersectedBy(oom, rm, l,
                        edges.values())
                || !externalHoles.values().parallelStream().anyMatch(x
                        -> x.contains(l, oom, rm)));
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
        return ch.isIntersectedBy(t, oom, rm)
                && isIntersectedBy0(t, oom, rm);
    }

    /**
     * Identify if this is intersected by t.
     *
     * @param t The triangle to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is an intersection.
     */
    public boolean isIntersectedBy0(V2D_Triangle t, int oom, RoundingMode rm) {
        V2D_Point tp = t.getP(oom, rm);
        V2D_Point tq = t.getQ(oom, rm);
        V2D_Point tr = t.getR(oom, rm);
        return (isIntersectedBy(tp, oom, rm)
                || isIntersectedBy(tq, oom, rm)
                || isIntersectedBy(tr, oom, rm))
                || (t.getEdges(oom, rm).values().parallelStream().anyMatch(x
                        -> V2D_LineSegment.isIntersectedBy(oom, rm, x,
                        edges.values())))
                || !(externalHoles.values().parallelStream().anyMatch(x
                        -> x.contains(tp, oom, rm)
                || x.contains(tq, oom, rm)
                || x.contains(tr, oom, rm)));
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
        return ch.isIntersectedBy(r, oom, rm)
                && (isIntersectedBy0(r.getPQR(), oom, rm)
                || isIntersectedBy0(r.getRSP(), oom, rm));
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
        return this.ch.isIntersectedBy(ch, oom, rm)
                && /**
                 * If any of the edges intersect or if one geometry contains the
                 * other, there is an intersection.
                 */
                getEdges().values().parallelStream().anyMatch(x
                        -> V2D_LineSegment.isIntersectedBy(oom, rm, x,
                        ch.getEdges(oom, rm).values()))
                || ch.getPoints(oom, rm).values().parallelStream().anyMatch(x
                        -> isIntersectedBy(x, oom, rm))
                || getPoints(oom, rm).values().parallelStream().anyMatch(x
                        -> ch.isIntersectedBy(x, oom, rm));
    }

    /**
     * Identify if this is intersected by the polygon.
     *
     * @param p The convex hull to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff this is intersected by {@code p}.
     */
    public boolean isIntersectedBy(V2D_PolygonNoInternalHoles p, int oom,
            RoundingMode rm) {
        return p.ch.isIntersectedBy(p.ch, oom, rm)
                && /**
                 * If any of the edges intersect or if one polygon contains the
                 * other, there is an intersection.
                 */
                getEdges().values().parallelStream().anyMatch(x
                        -> V2D_LineSegment.isIntersectedBy(oom, rm, x,
                        p.getEdges().values()))
                || getPoints(oom, rm).values().parallelStream().anyMatch(x
                        -> p.isIntersectedBy(x, oom, rm))
                || p.getPoints(oom, rm).values().parallelStream().anyMatch(x
                        -> isIntersectedBy(x, oom, rm));
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
        if (edges != null) {
            for (int i = 0; i < edges.size(); i++) {
                edges.get(i).translate(v, oom, rm);
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
        theta = Math_AngleBigRational.normalise(theta, bd, oom - 2, rm);
        if (theta.compareTo(BigRational.ZERO) == 0) {
            return new V2D_PolygonNoInternalHoles(this, oom, rm);
        } else {
            return rotateN(pt, theta, bd, oom, rm);
        }
    }

    @Override
    public V2D_PolygonNoInternalHoles rotateN(V2D_Point pt, BigRational theta,
            Math_BigDecimal bd, int oom, RoundingMode rm) {
        V2D_ConvexHull rch = getConvexHull(oom, rm).rotate(pt, theta, bd, oom,
                rm);
        HashMap<Integer, V2D_Point> rPoints = new HashMap<>();
        for (var x : this.points.entrySet()) {
            rPoints.put(x.getKey(), x.getValue().rotateN(pt, theta, bd, oom, rm));
        }
        HashMap<Integer, V2D_LineSegment> rExternalEdges = new HashMap<>();
        if (edges != null) {
            for (int i = 0; i < edges.size(); i++) {
                rExternalEdges.put(rExternalEdges.size(), edges.get(i).rotate(
                        pt, theta, bd, oom, rm));
            }
        }
        HashMap<Integer, V2D_PolygonNoInternalHoles> rExternalHoles
                = new HashMap<>();
        if (externalHoles != null) {
            for (int i = 0; i < externalHoles.size(); i++) {
                rExternalHoles.put(rExternalHoles.size(),
                        externalHoles.get(i).rotate(pt, theta, bd, oom, rm));
            }
        }
        return new V2D_PolygonNoInternalHoles(rPoints, rch, rExternalEdges,
                rExternalHoles);
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
