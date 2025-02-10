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
import java.util.HashMap;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;

/**
 * Comprising two collections of V2D_ConvexHull one representing parts and the
 * other representing holes. Parts may intersect. Holes may intersect. So in OGC
 * terms, this is pore like a multi-polygon as holes may effectively split parts
 * and they may collectively cover all parts. Simplification of a V2D_Polygon
 * may be possible and may involve creating new geometries to represent parts
 * individually which may involve some rounding.
 *
 * @author Andy Turner
 * @version 2.0
 */
public class V2D_Polygon extends V2D_PolygonNoInternalHoles {

    private static final long serialVersionUID = 1L;

    /**
     * The collection of internalHoles.
     */
    public HashMap<Integer, V2D_PolygonNoInternalHoles> internalHoles;

    /**
     * Create a new instance.
     *
     * @param p The polygon to duplicate.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     */
    public V2D_Polygon(V2D_Polygon p, int oom, RoundingMode rm) {
        this(p.getConvexHull(oom, rm), p.getExternalEdges(),
                p.getExternalHoles(oom, rm), p.getInternalHoles(oom, rm));
    }

    /**
     * Create a new instance.
     *
     * @param ch What {@link #ch} is set to.
     * @param externalEdges What {@link #externalEdges} is set to.
     * @param externalHoles What {@link #externalHoles} is set to.
     * @param internalHoles What {@link #internalHoles} is set to.
     */
    public V2D_Polygon(V2D_ConvexHull ch,
            HashMap<Integer, V2D_LineSegment> externalEdges,
            HashMap<Integer, V2D_PolygonNoInternalHoles> externalHoles, 
            HashMap<Integer, V2D_PolygonNoInternalHoles> internalHoles) {
        super(ch, externalEdges, externalHoles);
        this.internalHoles = internalHoles;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A copy of {@link internalHoles} with the given tolerance applied.
     */
    public HashMap<Integer, V2D_PolygonNoInternalHoles> getInternalHoles(int oom, RoundingMode rm) {
        HashMap<Integer, V2D_PolygonNoInternalHoles> r = new HashMap<>();
        for (V2D_PolygonNoInternalHoles h : internalHoles.values()) {
            r.put(r.size(), new V2D_PolygonNoInternalHoles(h, oom, rm));
        }
        return r;
    }

    /**
     * Identify if this is intersected by pt.
     *
     * @param pt The point to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is an intersection.
     */
    @Override
    public boolean isIntersectedBy(V2D_Point pt, int oom, RoundingMode rm) {
        if (super.isIntersectedBy(pt, oom, rm)) {
            return !internalHoles.values().parallelStream().anyMatch(x -> x.contains(pt, oom, rm));
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
    @Override
    public boolean isIntersectedBy(V2D_LineSegment l, int oom, RoundingMode rm) {
        if (super.isIntersectedBy(l, oom, rm)) {
            return !internalHoles.values().parallelStream().anyMatch(x -> x.contains(l, oom, rm));
        }
        return false;
    }

    /**
     * Identify if this is intersected by t. There is a boundary issue with
     * this: The edge of the holes are regarded as non-intersecting and this
     * might not bee desirable!
     *
     * @param t The triangle to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is an intersection.
     */
    @Override
    public boolean isIntersectedBy(V2D_Triangle t, int oom, RoundingMode rm) {
        if (super.isIntersectedBy(t, oom, rm)) {
            return !internalHoles.values().parallelStream().anyMatch(x -> x.contains(t, oom, rm));
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
    @Override
    public boolean isIntersectedBy(V2D_Rectangle r, int oom, RoundingMode rm) {
        if (super.isIntersectedBy(r, oom, rm)) {
            return !internalHoles.values().parallelStream().anyMatch(x -> x.isIntersectedBy(r, oom, rm));
        }
        return false;
    }

    /**
     * Identify if this is intersected by point {@code p}.
     *
     * @param ch The convex hull to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff the geometry is intersected by {@code ch.
     */
    public boolean isIntersectedBy(V2D_ConvexHull ch, int oom, RoundingMode rm) {
        // Check envelopes intersect.
        if (ch.isIntersectedBy(getEnvelope(oom, rm), oom, rm)) {
            if (isIntersectedBy(ch.getEnvelope(oom, rm), oom, rm)) {
                if (getConvexHull(oom, rm).isIntersectedBy(ch, oom, rm)) {
                    /**
                     * If the convex hull is fully in a hole, then it does not
                     * intersect otherwise it might! At the moment this
                     * implementation biases holes!
                     */
                    for (V2D_Polygon h : externalHoles) {
                        if (h.isIntersectedBy(ch, oom, rm)) {
                            return false;
                        }
                    }
                    for (V2D_Polygon h : internalHoles) {
                        if (h.isIntersectedBy(ch, oom, rm)) {
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
        if (internalEdges != null) {
            for (int i = 0; i < internalEdges.size(); i++) {
                internalEdges.get(i).translate(v, oom, rm);
            }
        }
        if (internalHoles != null) {
            for (int i = 0; i < internalHoles.size(); i++) {
                internalHoles.get(i).translate(v, oom, rm);
            }
        }
    }

    @Override
    public V2D_Polygon rotate(V2D_Point pt, BigRational theta,
            Math_BigDecimal bd, int oom, RoundingMode rm) {
        theta = Math_AngleBigRational.normalise(theta, bd, oom, rm);
        if (theta.compareTo(BigRational.ZERO) == 0) {
            return new V2D_Polygon(getConvexHull(oom, rm), getExternalEdges(),
                    getExternalHoles(oom, rm), getInternalEdges(),
                    getInternalHoles(oom, rm));
        } else {
            return rotateN(pt, theta, bd, oom, rm);
        }
    }

    @Override
    public V2D_Polygon rotateN(V2D_Point pt, BigRational theta,
            Math_BigDecimal bd, int oom, RoundingMode rm) {
        V2D_ConvexHull rch = getConvexHull(oom, rm).rotate(pt, theta, bd, oom, rm);
        HashMap<Integer, V2D_LineSegment> rExternalEdges = new HashMap<>();
        if (externalEdges != null) {
            for (int i = 0; i < externalEdges.size(); i++) {
                rExternalEdges.add(externalEdges.get(i).rotate(pt, theta, bd, oom, rm));
            }
        }
        HashMap<Integer, V2D_Polygon> rExternalHoles = new HashMap<>();
        if (externalHoles != null) {
            for (int i = 0; i < externalHoles.size(); i++) {
                rExternalHoles.add(externalHoles.get(i).rotate(pt, theta, bd, oom, rm));
            }
        }
        HashMap<Integer, V2D_LineSegment> rInternalEdges = new HashMap<>();
        if (internalEdges != null) {
            for (int i = 0; i < internalEdges.size(); i++) {
                rInternalEdges.add(internalEdges.get(i).rotate(pt, theta, bd, oom, rm));
            }
        }
        HashMap<Integer, V2D_Polygon> rInternalHoles = new HashMap<>();
        if (internalHoles != null) {
            for (int i = 0; i < internalHoles.size(); i++) {
                rInternalHoles.add(internalHoles.get(i).rotate(pt, theta, bd, oom, rm));
            }
        }
        return new V2D_Polygon(rch, rExternalEdges, rExternalHoles, rInternalEdges, rInternalHoles);
    }

    @Override
    public boolean isIntersectedBy(V2D_Envelope aabb, int oom, RoundingMode rm) {
        en = getEnvelope(oom, rm);
        if (en.isIntersectedBy(aabb, oom)) {
            if (getConvexHull(oom, rm).isIntersectedBy(aabb, oom, rm)) {
                return true;
            }
        }
        return false;
    }
}
