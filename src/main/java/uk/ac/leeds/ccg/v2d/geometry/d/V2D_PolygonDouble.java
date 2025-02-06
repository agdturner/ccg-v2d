/*
 * Copyright 2020 Andy Turner, University of Leeds.
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
import java.util.Iterator;
import uk.ac.leeds.ccg.math.geometry.Math_AngleDouble;

/**
 * Comprising two collections of V2D_ConvexHullDouble one representing parts and
 * the other representing holes. Parts may intersect. Holes may intersect. So in
 * OGC terms, this is pore like a multi-polygon as holes may effectively split
 * parts and they may collectively cover all parts. Simplification of a
 * V2D_Polygon may be possible and may involve creating new geometries to
 * represent parts individually which may involve some rounding.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_PolygonDouble extends V2D_FiniteGeometryDouble {

    private static final long serialVersionUID = 1L;

    /**
     * The ch.
     */
    protected final V2D_ConvexHullDouble ch;

    /**
     * The collection of holes.
     */
    protected final ArrayList<V2D_PolygonDouble> holes;

    /**
     * The collection of edges.
     */
    protected final ArrayList<V2D_LineSegmentDouble> edges;

    /**
     * Create a new instance.
     *
     * @param p The polygon to duplicate.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V2D_PolygonDouble(V2D_PolygonDouble p, double epsilon) {
        this(p.getConvexHull(epsilon), p.getEdges(), p.getHoles(epsilon));
    }

    /**
     * Create a new instance.
     *
     * @param ch The convex hull.
     */
    public V2D_PolygonDouble(V2D_ConvexHullDouble ch) {
        this(ch, new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Create a new instance.
     *
     * @param ch The convex hull.
     * @param holes The holes.
     */
    public V2D_PolygonDouble(V2D_ConvexHullDouble ch,
            ArrayList<V2D_PolygonDouble> holes) {
        this(ch, new ArrayList<>(), holes);
    }

    /**
     * Create a new instance.
     *
     * @param ch The convex hull.
     * @param edges The edges.
     * @param holes A potentially empty list of holes.
     */
    public V2D_PolygonDouble(V2D_ConvexHullDouble ch,
            ArrayList<V2D_LineSegmentDouble> edges,
            ArrayList<V2D_PolygonDouble> holes) {
        super();
        this.ch = ch;
        this.edges = edges;
        this.holes = holes;
    }

    @Override
    public String toString() {
        String s = this.getClass().getName() + "(";
        {
            s += "\nCh(\n" + ch.toString() + "\n)\n";
        }
        {
            if (holes != null) {
                s += "\nHoles(\n";
                Iterator<V2D_PolygonDouble> ite = holes.iterator();
                s += ite.next().toString();
                while (ite.hasNext()) {
                    s += ", " + ite.next();
                }
                s += "\n)\n";
            }
        }
        s += "\n)";
        return s;
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
     * @return A copy of {@link holes} with the given tolerance applied.
     */
    public ArrayList<V2D_LineSegmentDouble> getEdges() {
        ArrayList<V2D_LineSegmentDouble> r = new ArrayList<>();
        for (V2D_LineSegmentDouble l : edges) {
            r.add(new V2D_LineSegmentDouble(l));
        }
        return r;
    }

    /**
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A copy of {@link holes} with the given tolerance applied.
     */
    public ArrayList<V2D_PolygonDouble> getHoles(double epsilon) {
        ArrayList<V2D_PolygonDouble> r = new ArrayList<>();
        for (V2D_PolygonDouble h : holes) {
            r.add(new V2D_PolygonDouble(h, epsilon));
        }
        return r;
    }

    @Override
    public V2D_PointDouble[] getPoints() {
        V2D_PointDouble[] ePs = ch.getPoints();
        int np = ePs.length;
        for (var x : holes) {
            np += x.getPoints().length;
        }
        V2D_PointDouble[] r = new V2D_PointDouble[np];
        int i = 0;
        for (var p : ePs) {
            r[i] = p;
            i++;
        }
        for (var h : holes) {
            for (var p : h.getPoints()) {
                r[i] = p;
                i++;
            }
        }
        return r;
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
                for (V2D_PolygonDouble h : holes) {
                    if (h.isIntersectedBy(pt, epsilon)) {
                        return false;
                    }
                }
                return true;
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
     * @return {@code true} iff there is an intersection.
     */
    public boolean isIntersectedBy(V2D_LineSegmentDouble l, double epsilon) {
        en = getEnvelope();
        // Check envelopes intersect.
        if (l.getEnvelope().isIntersectedBy(en, epsilon)) {
            if (l.isIntersectedBy(en, epsilon)) {
                for (V2D_TriangleDouble t : ch.triangles) {
                    if (t.isIntersectedBy(l, epsilon)) {
                        /**
                         * If the lineSegment is fully in a hole, then it does
                         * not intersect otherwise it might! At the moment this 
                         * implementation biases holes!
                         */
                        for (V2D_PolygonDouble h : holes) {
                            if (h.isIntersectedBy(l, epsilon)) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Identify if this is intersected by t. There is a boundary issue with
     * this: The edge of the holes are regarded as non-intersecting and this
     * might not bee desirable!
     *
     * @param l The triangle to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff there is an intersection.
     */
    public boolean isIntersectedBy(V2D_TriangleDouble t, double epsilon) {
        // Check envelopes intersect.
        if (t.isIntersectedBy(getEnvelope(), epsilon)) {
            if (isIntersectedBy(t.getEnvelope(), epsilon)) {
                if (getConvexHull(epsilon).isIntersectedBy(t, epsilon)) {
                    for (V2D_PolygonDouble h : getHoles(epsilon)) {
                        /**
                         * If the triangle is fully in a hole, then it does
                         * not intersect otherwise it might! At the moment this 
                         * implementation biases holes!
                         */
                        if (h.isIntersectedBy(t, epsilon)) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Identify if this is intersected by point {@code p}.
     *
     * @param r The convex hull to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff the geometry is intersected by {@code ch.
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
     * Identify if this is intersected by point {@code p}.
     *
     * @param ch The convex hull to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff the geometry is intersected by {@code ch.
     */
    public boolean isIntersectedBy(V2D_ConvexHullDouble ch, double epsilon) {
        // Check envelopes intersect.
        if (ch.isIntersectedBy(getEnvelope(), epsilon)) {
            if (isIntersectedBy(ch.getEnvelope(), epsilon)) {
                if (getConvexHull(epsilon).isIntersectedBy(ch, epsilon)) {
                    for (V2D_PolygonDouble h : getHoles(epsilon)) {
                        /**
                         * If the convex hull is fully in a hole, then it does
                         * not intersect otherwise it might! At the moment this 
                         * implementation biases holes!
                         */
                        if (h.isIntersectedBy(ch, epsilon)) {
                            return false;
                        }
                    }
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
        if (holes != null) {
            for (int i = 0; i < holes.size(); i++) {
                holes.get(i).translate(v);
            }
        }
    }

    @Override
    public V2D_PolygonDouble rotate(V2D_PointDouble pt, double theta,
            double epsilon) {
        theta = Math_AngleDouble.normalise(theta);
        if (theta == 0d) {
            return new V2D_PolygonDouble(getConvexHull(epsilon), getEdges(), getHoles(epsilon));
        } else {
            return rotateN(pt, theta, epsilon);
        }
    }

    @Override
    public V2D_PolygonDouble rotateN(V2D_PointDouble pt, double theta, double epsilon) {
        V2D_ConvexHullDouble rch = getConvexHull(epsilon).rotate(pt, theta, epsilon);
        ArrayList<V2D_PolygonDouble> rholes = new ArrayList<>();
        ArrayList<V2D_PolygonDouble> tholes = getHoles(epsilon);
        if (tholes != null) {
            for (int i = 0; i < tholes.size(); i++) {
                rholes.add(tholes.get(i).rotate(pt, theta, epsilon));
            }
        }
        ArrayList<V2D_LineSegmentDouble> tedges = getEdges();
        ArrayList<V2D_LineSegmentDouble> redges = new ArrayList<>();
        if (tedges != null) {
            for (int i = 0; i < tedges.size(); i++) {
                redges.add(tedges.get(i).rotate(pt, theta, epsilon));
            }
        }
        return new V2D_PolygonDouble(rch, redges, rholes);
    }

    @Override
    public boolean isIntersectedBy(V2D_EnvelopeDouble aabb, double epsilon) {
        en = getEnvelope();
        if (en.isIntersectedBy(aabb, epsilon)) {
            if (getConvexHull(epsilon).isIntersectedBy(aabb, epsilon)) {
                return true;
            }
        }
        return false;
    }
}
