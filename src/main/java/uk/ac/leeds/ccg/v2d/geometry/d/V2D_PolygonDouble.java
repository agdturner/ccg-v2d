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
        super(p, epsilon);
        this.internalHoles = new HashMap<>();
        for (var x: p.internalHoles.entrySet()) {
            this.internalHoles.put(x.getKey(), new V2D_PolygonNoInternalHolesDouble(
                    x.getValue(), epsilon));
        }
    }

    /**
     * Create a new instance.
     *
     * @param p The polygon with no internal holes to use as a basis for this.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V2D_PolygonDouble(V2D_PolygonNoInternalHolesDouble p, double epsilon) {
        this(p, new HashMap<>(), epsilon);
    }
    
    /**
     * Create a new instance.
     *
     * @param p The polygon with no internal holes to use as a basis for this.
     * @param internalHoles What {@link #internalHoles} is set to.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V2D_PolygonDouble(V2D_PolygonNoInternalHolesDouble p,
            HashMap<Integer, V2D_PolygonNoInternalHolesDouble> internalHoles,
            double epsilon) {
        super(p, epsilon);
        this.internalHoles = internalHoles;
    }

    /**
     * Create a new instance.
     *
     * @param pts The external edge points in a clockwise order.
     * @param internalHoles What {@link #internalHoles} is set to.
     */
    public V2D_PolygonDouble(V2D_PointDouble[] pts, HashMap<Integer, 
            V2D_PolygonNoInternalHolesDouble> internalHoles) {
        super(pts);
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
            return new V2D_PolygonDouble(this, epsilon);
        } else {
            return rotateN(pt, theta, epsilon);
        }
    }

    @Override
    public V2D_PolygonDouble rotateN(V2D_PointDouble pt, double theta, double epsilon) {
        V2D_PolygonNoInternalHolesDouble exterior = super.rotateN(pt, theta, epsilon);
        HashMap<Integer, V2D_PolygonNoInternalHolesDouble> rInternalHoles = new HashMap<>();
        if (internalHoles != null) {
            for (int i = 0; i < internalHoles.size(); i++) {
                rInternalHoles.put(rInternalHoles.size(), internalHoles.get(i).rotate(pt, theta, epsilon));
            }
        }
        return new V2D_PolygonDouble(exterior, rInternalHoles, epsilon);
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
