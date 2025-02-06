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
     * The collection of holes.
     */
    protected final ArrayList<V2D_Polygon> holes;

    /**
     * The collection of edges.
     */
    protected final ArrayList<V2D_LineSegment> edges;

    /**
     * Create a new instance.
     *
     * @param p The polygon to duplicate.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     */
    public V2D_Polygon(V2D_Polygon p, int oom, RoundingMode rm) {
        this(p.getConvexHull(oom, rm), p.getEdges(), p.getHoles(oom, rm));
    }
    
    /**
     * Create a new instance.
     *
     * @param ch The convex hull.
     */
    public V2D_Polygon(V2D_ConvexHull ch) {
        this(ch, new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Create a new instance.
     *
     * @param ch The convex hull.
     * @param holes The holes.
     */
    public V2D_Polygon(V2D_ConvexHull ch, ArrayList<V2D_Polygon> holes) {
        this(ch, new ArrayList<>(), holes);
    }

    /**
     * Create a new instance.
     *
     * @param ch The convex hull.
     * @param edges The edges.
     * @param holes A potentially empty list of V2D_ConvexHull holes.
     */
    public V2D_Polygon(V2D_ConvexHull ch,
            ArrayList<V2D_LineSegment> edges,
            ArrayList<V2D_Polygon> holes) {
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
                Iterator<V2D_Polygon> ite = holes.iterator();
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A copy of {@link exterior#ch} with the given tolerance applied.
     */
    public V2D_ConvexHull getConvexHull(int oom, RoundingMode rm) {
        return new V2D_ConvexHull(ch, oom, rm);
    }

    /**
     * @return A copy of {@link holes} with the given tolerance applied.
     */
    public ArrayList<V2D_LineSegment> getEdges() {
        ArrayList<V2D_LineSegment> r = new ArrayList<>();
        for (V2D_LineSegment l : edges) {
            r.add(new V2D_LineSegment(l));
        }
        return r;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A copy of {@link holes} with the given tolerance applied.
     */
    public ArrayList<V2D_Polygon> getHoles(int oom, RoundingMode rm) {
        ArrayList<V2D_Polygon> r = new ArrayList<>();
        for (V2D_Polygon h : holes) {
            r.add(new V2D_Polygon(h, oom, rm));
        }
        return r;
    }

    @Override
    public V2D_Point[] getPoints(int oom, RoundingMode rm) {
        V2D_Point[] ePs = ch.getPoints(oom, rm);
        int np = ePs.length;
        for (var x : holes) {
            np += x.getPoints(oom, rm).length;
        }
        V2D_Point[] r = new V2D_Point[np];
        int i = 0;
        for (var p : ePs) {
            r[i] = p;
            i++;
        }
        for (var h : holes) {
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
                for (V2D_Polygon h : holes) {
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
                        for (V2D_Polygon h : holes) {
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
                         * If the lineSegment is fully in a hole, then it does
                         * not intersect otherwise it might! At the moment this
                         * implementation biases holes!
                         */
                    for (V2D_Polygon h : getHoles(oom, rm)) {
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
                    for (V2D_Polygon h : getHoles(oom, rm)) {
                        /**
                         * If the convex hull is fully in a hole, then it does
                         * not intersect otherwise it might! At the moment this 
                         * implementation biases holes!
                         */
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
        if (holes != null) {
            for (int i = 0; i < holes.size(); i++) {
                holes.get(i).translate(v, oom, rm);
            }
        }
    }

    @Override
    public V2D_Polygon rotate(V2D_Point pt, BigRational theta,
            Math_BigDecimal bd, int oom, RoundingMode rm) {
        theta = Math_AngleBigRational.normalise(theta, bd, oom, rm);
        if (theta.compareTo(BigRational.ZERO) == 0) {
            return new V2D_Polygon(getConvexHull(oom, rm), getEdges(), getHoles(oom, rm));
        } else {
            return rotateN(pt, theta, bd, oom, rm);
        }
    }

    @Override
    public V2D_Polygon rotateN(V2D_Point pt, BigRational theta,
            Math_BigDecimal bd, int oom, RoundingMode rm) {
        V2D_ConvexHull rch = getConvexHull(oom, rm).rotate(pt, theta, bd, oom, rm);
        ArrayList<V2D_Polygon> rholes = new ArrayList<>();
        ArrayList<V2D_Polygon> tholes = getHoles(oom, rm);
        if (tholes != null) {
            for (int i = 0; i < tholes.size(); i++) {
                rholes.add(tholes.get(i).rotate(pt, theta, bd, oom, rm));
            }
        }
        ArrayList<V2D_LineSegment> tedges = getEdges();
        ArrayList<V2D_LineSegment> redges = new ArrayList<>();
        if (tedges != null) {
            for (int i = 0; i < tedges.size(); i++) {
                redges.add(tedges.get(i).rotate(pt, theta, bd, oom, rm));
            }
        }
        return new V2D_Polygon(rch, redges, rholes);
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
