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
package uk.ac.leeds.ccg.v2d.geometry;

import ch.obermuhlner.math.big.BigRational;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
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
 * @version 1.0
 */
public class V2D_Polygon extends V2D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * The ch.
     */
    protected final V2D_ConvexHull ch;

    /**
     * The collection of externalEdges.
     */
    protected final ArrayList<V2D_LineSegment> externalEdges;

    /**
     * The collection of externalHoles.
     */
    protected final ArrayList<V2D_Polygon> externalHoles;

    /**
     * /**
     * The collection of internalEdges.
     */
    protected final ArrayList<V2D_LineSegment> internalEdges;

    /**
     * The collection of internalHoles.
     */
    protected final ArrayList<V2D_Polygon> internalHoles;

    /**
     * Create a new instance.
     *
     * @param p The polygon to duplicate.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     */
    public V2D_Polygon(V2D_Polygon p, int oom, RoundingMode rm) {
        this(p.getConvexHull(oom, rm), p.getExternalEdges(),
                p.getExternalHoles(oom, rm), p.getInternalEdges(),
                p.getInternalHoles(oom, rm));
    }

    /**
     * Create a new instance.
     *
     * @param ch What {@link #ch} is set to.
     */
    public V2D_Polygon(V2D_ConvexHull ch) {
        this(ch, new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Create a new instance.
     *
     * @param ch What {@link #ch} is set to.
     * @param externalHoles What {@link #externalHoles} is set to.
     */
    public V2D_Polygon(V2D_ConvexHull ch, ArrayList<V2D_Polygon> externalHoles) {
        this(ch, new ArrayList<>(), externalHoles);
    }

    /**
     * Create a new instance.
     *
     * @param ch What {@link #ch} is set to.
     * @param externalEdges What {@link #externalEdges} is set to.
     * @param externalHoles What {@link #externalHoles} is set to.
     */
    public V2D_Polygon(V2D_ConvexHull ch,
            ArrayList<V2D_LineSegment> externalEdges,
            ArrayList<V2D_Polygon> externalHoles) {
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
    public V2D_Polygon(V2D_ConvexHull ch,
            ArrayList<V2D_LineSegment> externalEdges,
            ArrayList<V2D_Polygon> externalHoles,
            ArrayList<V2D_LineSegment> internalEdges,
            ArrayList<V2D_Polygon> internalHoles) {
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
                    Iterator<V2D_LineSegment> ite = externalEdges.iterator();
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
                    Iterator<V2D_Polygon> ite = externalHoles.iterator();
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
                    Iterator<V2D_LineSegment> ite = internalEdges.iterator();
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
                    Iterator<V2D_Polygon> ite = internalHoles.iterator();
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
    public ArrayList<V2D_LineSegment> getExternalEdges() {
        ArrayList<V2D_LineSegment> r = new ArrayList<>();
        for (V2D_LineSegment l : externalEdges) {
            r.add(new V2D_LineSegment(l));
        }
        return r;
    }

    /**
     * @return A copy of {@link internalEdges}.
     */
    public ArrayList<V2D_LineSegment> getInternalEdges() {
        ArrayList<V2D_LineSegment> r = new ArrayList<>();
        for (V2D_LineSegment l : internalEdges) {
            r.add(new V2D_LineSegment(l));
        }
        return r;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A copy of {@link externalHoles} with the given tolerance applied.
     */
    public ArrayList<V2D_Polygon> getExternalHoles(int oom, RoundingMode rm) {
        ArrayList<V2D_Polygon> r = new ArrayList<>();
        for (V2D_Polygon h : externalHoles) {
            r.add(new V2D_Polygon(h, oom, rm));
        }
        return r;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A copy of {@link internalHoles} with the given tolerance applied.
     */
    public ArrayList<V2D_Polygon> getInternalHoles(int oom, RoundingMode rm) {
        ArrayList<V2D_Polygon> r = new ArrayList<>();
        for (V2D_Polygon h : internalHoles) {
            r.add(new V2D_Polygon(h, oom, rm));
        }
        return r;
    }

    @Override
    public V2D_Point[] getPoints(int oom, RoundingMode rm) {
        V2D_Point[] ePs = ch.getPoints(oom, rm);
        int np = ePs.length;
        for (var x : externalHoles) {
            np += x.getPoints(oom, rm).length;
        }
        V2D_Point[] r = new V2D_Point[np];
        int i = 0;
        for (var p : ePs) {
            r[i] = p;
            i++;
        }
        for (var h : externalHoles) {
            for (var p : h.getPoints(oom, rm)) {
                r[i] = p;
                i++;
            }
        }
        return r;
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
                for (V2D_Polygon h : externalHoles) {
                    if (h.isIntersectedBy(pt, oom, rm)) {
                        return false;
                    }
                }
                for (V2D_Polygon h : internalHoles) {
                    if (h.isIntersectedBy(pt, oom, rm)) {
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is an intersection.
     */
    public boolean isIntersectedBy(V2D_LineSegment l, int oom, RoundingMode rm) {
        en = getEnvelope(oom, rm);
        // Check envelopes intersect.
        if (l.getEnvelope(oom, rm).isIntersectedBy(en, oom)) {
            if (l.isIntersectedBy(en, oom, rm)) {
                for (V2D_Triangle t : ch.triangles) {
                    if (t.isIntersectedBy(l, oom, rm)) {
                        /**
                         * If the lineSegment is fully in a hole, then it does
                         * not intersect otherwise it might! At the moment this
                         * implementation biases holes!
                         */
                        for (V2D_Polygon h : externalHoles) {
                            if (h.isIntersectedBy(l, oom, rm)) {
                                return false;
                            }
                        }
                        for (V2D_Polygon h : internalHoles) {
                            if (h.isIntersectedBy(l, oom, rm)) {
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
     * @param t The triangle to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is an intersection.
     */
    public boolean isIntersectedBy(V2D_Triangle t, int oom, RoundingMode rm) {
        // Check envelopes intersect.
        if (t.isIntersectedBy(getEnvelope(oom, rm), oom, rm)) {
            if (isIntersectedBy(t.getEnvelope(oom, rm), oom, rm)) {
                if (getConvexHull(oom, rm).isIntersectedBy(t, oom, rm)) {
                    /**
                     * If the lineSegment is fully in a hole, then it does not
                     * intersect otherwise it might! At the moment this
                     * implementation biases holes!
                     */
                    for (V2D_Polygon h : externalHoles) {
                        if (h.isIntersectedBy(t, oom, rm)) {
                            return false;
                        }
                    }
                    for (V2D_Polygon h : internalHoles) {
                        if (h.isIntersectedBy(t, oom, rm)) {
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
        ArrayList<V2D_LineSegment> rExternalEdges = new ArrayList<>();
        if (externalEdges != null) {
            for (int i = 0; i < externalEdges.size(); i++) {
                rExternalEdges.add(externalEdges.get(i).rotate(pt, theta, bd, oom, rm));
            }
        }
        ArrayList<V2D_Polygon> rExternalHoles = new ArrayList<>();
        if (externalHoles != null) {
            for (int i = 0; i < externalHoles.size(); i++) {
                rExternalHoles.add(externalHoles.get(i).rotate(pt, theta, bd, oom, rm));
            }
        }
        ArrayList<V2D_LineSegment> rInternalEdges = new ArrayList<>();
        if (internalEdges != null) {
            for (int i = 0; i < internalEdges.size(); i++) {
                rInternalEdges.add(internalEdges.get(i).rotate(pt, theta, bd, oom, rm));
            }
        }
        ArrayList<V2D_Polygon> rInternalHoles = new ArrayList<>();
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
