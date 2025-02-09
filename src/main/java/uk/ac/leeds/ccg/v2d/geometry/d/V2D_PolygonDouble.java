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
     * The collection of externalEdges.
     */
    protected final ArrayList<V2D_LineSegmentDouble> externalEdges;

    /**
     * The collection of externalHoles.
     */
    protected final ArrayList<V2D_PolygonDouble> externalHoles;

    /**
     * The collection of internalEdges.
     */
    protected final ArrayList<V2D_LineSegmentDouble> internalEdges;

    /**
     * The collection of internalHoles.
     */
    protected final ArrayList<V2D_PolygonDouble> internalHoles;

    /**
     * Create a new instance.
     *
     * @param p The polygon to duplicate.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V2D_PolygonDouble(V2D_PolygonDouble p, double epsilon) {
        this(p.getConvexHull(epsilon), p.getExternalEdges(),
                p.getExternalHoles(epsilon), p.getInternalEdges(),
                p.getInternalHoles(epsilon));
    }

    /**
     * Create a new instance.
     *
     * @param ch What {@link #ch} is set to.
     */
    public V2D_PolygonDouble(V2D_ConvexHullDouble ch) {
        this(ch, new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Create a new instance.
     *
     * @param ch What {@link #ch} is set to.
     * @param externalHoles What {@link #externalHoles} is set to.
     */
    public V2D_PolygonDouble(V2D_ConvexHullDouble ch,
            ArrayList<V2D_PolygonDouble> externalHoles) {
        this(ch, new ArrayList<>(), externalHoles);
    }

    /**
     * Create a new instance.
     *
     * @param ch What {@link #ch} is set to.
     * @param externalEdges What {@link #externalEdges} is set to.
     * @param externalHoles What {@link #externalHoles} is set to.
     */
    public V2D_PolygonDouble(V2D_ConvexHullDouble ch,
            ArrayList<V2D_LineSegmentDouble> externalEdges,
            ArrayList<V2D_PolygonDouble> externalHoles) {
        this(ch, externalEdges, externalHoles, new ArrayList<>(),
                new ArrayList<>());
    }

    /**
     * Create a new instance.
     *
     * @param ch What {@link #ch} is set to.
     * @param externalEdges What {@link #externalEdges} is set to.
     * @param externalHoles What {@link #externalHoles} is set to.
     * @param internalEdges What {@link #internalEdges} is set to.
     * @param internalHoles What {@link #internalHoles} is set to.
     */
    public V2D_PolygonDouble(V2D_ConvexHullDouble ch,
            ArrayList<V2D_LineSegmentDouble> externalEdges,
            ArrayList<V2D_PolygonDouble> externalHoles,
            ArrayList<V2D_LineSegmentDouble> internalEdges,
            ArrayList<V2D_PolygonDouble> internalHoles) {
        super();
        this.ch = ch;
        this.externalEdges = externalEdges;
        this.externalHoles = externalHoles;
        this.internalEdges = internalEdges;
        this.internalHoles = internalHoles;
    }

    @Override
    public String toString() {
        String s = this.getClass().getName() + "(";
        {
            s += "\nCh(\n" + ch.toString() + "\n)\n";
        }
        {
            if (externalEdges != null) {
                if (externalEdges.size() > 0) {
                    s += "\nexternalEdges(\n";
                    Iterator<V2D_LineSegmentDouble> ite = externalEdges.iterator();
                    s += ite.next().toString();
                    while (ite.hasNext()) {
                        s += ", " + ite.next();
                    }
                    s += "\n)\n";
                }
            }
        }

        {
            if (externalHoles != null) {
                if (externalHoles.size() > 0) {
                    s += "\nexternalHoles(\n";
                    Iterator<V2D_PolygonDouble> ite = externalHoles.iterator();
                    s += ite.next().toString();
                    while (ite.hasNext()) {
                        s += ", " + ite.next();
                    }
                    s += "\n)\n";
                }
            }
        }
        {
            if (internalEdges != null) {
                if (internalEdges.size() > 0) {
                    s += "\ninternalEdges(\n";
                    Iterator<V2D_LineSegmentDouble> ite = internalEdges.iterator();
                    s += ite.next().toString();
                    while (ite.hasNext()) {
                        s += ", " + ite.next();
                    }
                    s += "\n)\n";
                }
            }
        }

        {
            if (internalHoles != null) {
                if (internalHoles.size() > 0) {
                    s += "\ninternalHoles(\n";
                    Iterator<V2D_PolygonDouble> ite = internalHoles.iterator();
                    s += ite.next().toString();
                    while (ite.hasNext()) {
                        s += ", " + ite.next();
                    }
                    s += "\n)\n";
                }
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
     * @return A copy of {@link externalEdges}.
     */
    public ArrayList<V2D_LineSegmentDouble> getExternalEdges() {
        ArrayList<V2D_LineSegmentDouble> r = new ArrayList<>();
        for (V2D_LineSegmentDouble l : externalEdges) {
            r.add(new V2D_LineSegmentDouble(l));
        }
        return r;
    }

    /**
     * @return A copy of {@link internalEdges}.
     */
    public ArrayList<V2D_LineSegmentDouble> getInternalEdges() {
        ArrayList<V2D_LineSegmentDouble> r = new ArrayList<>();
        for (V2D_LineSegmentDouble l : internalEdges) {
            r.add(new V2D_LineSegmentDouble(l));
        }
        return r;
    }

    /**
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A copy of {@link holes#externalHoles} with the given tolerance
     * applied.
     */
    public ArrayList<V2D_PolygonDouble> getExternalHoles(double epsilon) {
        ArrayList<V2D_PolygonDouble> r = new ArrayList<>();
        for (V2D_PolygonDouble h : externalHoles) {
            r.add(new V2D_PolygonDouble(h, epsilon));
        }
        return r;
    }

    /**
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A copy of {@link holes#internalHoles} with the given tolerance
     * applied.
     */
    public ArrayList<V2D_PolygonDouble> getInternalHoles(double epsilon) {
        ArrayList<V2D_PolygonDouble> r = new ArrayList<>();
        for (V2D_PolygonDouble h : internalHoles) {
            r.add(new V2D_PolygonDouble(h, epsilon));
        }
        return r;
    }

    @Override
    public V2D_PointDouble[] getPoints() {
        V2D_PointDouble[] ePs = ch.getPoints();
        int np = ePs.length;
        for (var x : externalHoles) {
            np += x.getPoints().length;
        }
        V2D_PointDouble[] r = new V2D_PointDouble[np];
        int i = 0;
        for (var p : ePs) {
            r[i] = p;
            i++;
        }
        for (var h : externalHoles) {
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
                for (V2D_PolygonDouble h : externalHoles) {
                    if (h.isIntersectedBy(pt, epsilon)) {
                        return false;
                    }
                }
                for (V2D_PolygonDouble h : internalHoles) {
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
                        for (V2D_PolygonDouble h : externalHoles) {
                            if (h.isIntersectedBy(l, epsilon)) {
                                return false;
                            }
                        }
                        for (V2D_PolygonDouble h : internalHoles) {
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
                    /**
                     * If the triangle is fully in a hole, then it does not
                     * intersect otherwise it might! At the moment this
                     * implementation biases holes!
                     */
                    for (V2D_PolygonDouble h : externalHoles) {
                        if (h.isIntersectedBy(t, epsilon)) {
                            return false;
                        }
                    }
                    for (V2D_PolygonDouble h : internalHoles) {
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
                    /**
                     * If the convex hull is fully in a hole, then it does not
                     * intersect otherwise it might! At the moment this
                     * implementation biases holes!
                     */
                    for (V2D_PolygonDouble h : externalHoles) {
                        if (h.isIntersectedBy(ch, epsilon)) {
                            return false;
                        }
                    }
                    for (V2D_PolygonDouble h : internalHoles) {
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
        if (internalEdges != null) {
            for (int i = 0; i < internalEdges.size(); i++) {
                internalEdges.get(i).translate(v);
            }
        }
        if (internalHoles != null) {
            for (int i = 0; i < internalHoles.size(); i++) {
                internalHoles.get(i).translate(v);
            }
        }
    }

    @Override
    public V2D_PolygonDouble rotate(V2D_PointDouble pt, double theta,
            double epsilon) {
        theta = Math_AngleDouble.normalise(theta);
        if (theta == 0d) {
            return new V2D_PolygonDouble(getConvexHull(epsilon),
                    getExternalEdges(), getExternalHoles(epsilon),
                    getInternalEdges(), getInternalHoles(epsilon));
        } else {
            return rotateN(pt, theta, epsilon);
        }
    }

    @Override
    public V2D_PolygonDouble rotateN(V2D_PointDouble pt, double theta, double epsilon) {
        V2D_ConvexHullDouble rch = getConvexHull(epsilon).rotate(pt, theta, epsilon);
        ArrayList<V2D_LineSegmentDouble> rExternalEdges = new ArrayList<>();
        if (externalEdges != null) {
            for (int i = 0; i < externalEdges.size(); i++) {
                rExternalEdges.add(externalEdges.get(i).rotate(pt, theta, epsilon));
            }
        }
        ArrayList<V2D_PolygonDouble> rExternalHoles = new ArrayList<>();
        if (externalHoles != null) {
            for (int i = 0; i < externalHoles.size(); i++) {
                rExternalHoles.add(externalHoles.get(i).rotate(pt, theta, epsilon));
            }
        }
        ArrayList<V2D_LineSegmentDouble> rInternalEdges = new ArrayList<>();
        if (internalEdges != null) {
            for (int i = 0; i < internalEdges.size(); i++) {
                rInternalEdges.add(internalEdges.get(i).rotate(pt, theta, epsilon));
            }
        }
        ArrayList<V2D_PolygonDouble> rInternalHoles = new ArrayList<>();
        if (internalHoles != null) {
            for (int i = 0; i < internalHoles.size(); i++) {
                rInternalHoles.add(internalHoles.get(i).rotate(pt, theta, epsilon));
            }
        }
        return new V2D_PolygonDouble(rch, rExternalEdges, rExternalHoles, rInternalEdges, rInternalHoles);
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
