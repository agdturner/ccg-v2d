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

import java.util.HashMap;
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
 * @version 2.0
 */
public class V2D_PolygonDouble extends V2D_PolygonNoInternalHolesDouble {

    private static final long serialVersionUID = 1L;

    /**
     * The collection of internalHoles. Keys are identifiers.
     */
    public HashMap<Integer, V2D_PolygonNoInternalHolesDouble> internalHoles;

    /**
     * Create a new instance.
     *
     * @param p The polygon to duplicate.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V2D_PolygonDouble(V2D_PolygonDouble p, double epsilon) {
        this(p.getConvexHull(epsilon), p.getExternalEdges(),
                p.getExternalHoles(epsilon), p.getInternalHoles(epsilon));
    }

    /**
     * Create a new instance.
     *
     * @param ch What {@link #ch} is set to.
     * @param externalEdges What {@link #externalEdges} is set to.
     * @param externalHoles What {@link #externalHoles} is set to.
     * @param internalHoles What {@link #internalHoles} is set to.
     */
    public V2D_PolygonDouble(V2D_ConvexHullDouble ch,
            HashMap<Integer, V2D_LineSegmentDouble> externalEdges,
            HashMap<Integer, V2D_PolygonNoInternalHolesDouble> externalHoles,
            HashMap<Integer, V2D_PolygonNoInternalHolesDouble> internalHoles) {
        super(ch, externalEdges, externalHoles);
        this.internalHoles = internalHoles;
    }

    /**
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A copy of {@link internalHoles} with the given tolerance applied.
     */
    public HashMap<Integer, V2D_PolygonNoInternalHolesDouble> getInternalHoles(double epsilon) {
        HashMap<Integer, V2D_PolygonNoInternalHolesDouble> r = new HashMap<>();
        for (V2D_PolygonNoInternalHolesDouble h : internalHoles.values()) {
            r.put(r.size(), new V2D_PolygonNoInternalHolesDouble(h, epsilon));
        }
        return r;
    }

    /**
     * Identify if this is intersected by pt.
     *
     * @param pt The point to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff there is an intersection.
     */
    @Override
    public boolean isIntersectedBy(V2D_PointDouble pt, double epsilon) {
        if (super.isIntersectedBy(pt, epsilon)) {
            return !internalHoles.values().parallelStream().anyMatch(x -> x.contains(pt, epsilon));
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
    @Override
    public boolean isIntersectedBy(V2D_LineSegmentDouble l, double epsilon) {
        if (super.isIntersectedBy(l, epsilon)) {
            return !internalHoles.values().parallelStream().anyMatch(x -> x.contains(l, epsilon));
        }
        return false;
    }

    /**
     * Identify if this is intersected by t. There is a boundary issue with
     * this: The edge of the holes are regarded as non-intersecting and this
     * might not bee desirable!
     *
     * @param t The triangle to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff there is an intersection.
     */
    @Override
    public boolean isIntersectedBy(V2D_TriangleDouble t, double epsilon) {
        if (super.isIntersectedBy(t, epsilon)) {
            return !internalHoles.values().parallelStream().anyMatch(x -> x.contains(t, epsilon));
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
    @Override
    public boolean isIntersectedBy(V2D_RectangleDouble r, double epsilon) {
        if (super.isIntersectedBy(r, epsilon)) {
            if (internalHoles.isEmpty()) {
                return true;
            }
            return !internalHoles.values().parallelStream().anyMatch(x -> x.contains(r, epsilon));
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
    @Override
    public boolean isIntersectedBy(V2D_ConvexHullDouble ch, double epsilon) {
        if (super.isIntersectedBy(ch, epsilon)) {
            if (internalHoles.isEmpty()) {
                return true;
            }
            return !internalHoles.values().parallelStream().anyMatch(x -> x.contains(ch, epsilon));
        }
        return false;
    }

    /**
     * This sums all the areas irrespective of any overlaps.
     *
     * @return The area of the triangle (rounded).
     */
    @Override
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
    @Override
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
                    getInternalHoles(epsilon));
        } else {
            return rotateN(pt, theta, epsilon);
        }
    }

    @Override
    public V2D_PolygonDouble rotateN(V2D_PointDouble pt, double theta, double epsilon) {
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
        HashMap<Integer, V2D_PolygonNoInternalHolesDouble> rInternalHoles = new HashMap<>();
        if (internalHoles != null) {
            for (int i = 0; i < internalHoles.size(); i++) {
                rInternalHoles.put(rInternalHoles.size(), internalHoles.get(i).rotate(pt, theta, epsilon));
            }
        }
        return new V2D_PolygonDouble(rch, rExternalEdges, rExternalHoles, rInternalHoles);
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

    /**
     * Adds an internal hole and return its assigned id.
     *
     * @param p The polygon to add.
     * @return the id assigned to the internal hole
     */
    public int addInternalHole(V2D_PolygonDouble p) {
        int pid = internalHoles.size();
        internalHoles.put(pid, p);
        return pid;
    }
}
