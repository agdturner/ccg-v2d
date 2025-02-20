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
import java.io.Serializable;
import java.math.RoundingMode;
import java.util.HashSet;
import uk.ac.leeds.ccg.v2d.core.V2D_Environment;

/**
 * An envelope contains all the extreme values with respect to the X and Y axes.
 * It is an axis aligned bounding box, which may have length of zero in any
 * direction. For a point the envelope is essentially the point.
 *
 * @author Andy Turner
 * @version 2.0
 */
public class V2D_Envelope implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The environment.
     */
    protected final V2D_Environment env;

    /**
     * For storing the offset of this.
     */
    private V2D_Vector offset;

    /**
     * The minimum x-coordinate.
     */
    private final BigRational xMin;

    /**
     * The maximum x-coordinate.
     */
    private final BigRational xMax;

    /**
     * The minimum y-coordinate.
     */
    private final BigRational yMin;

    /**
     * The maximum y-coordinate.
     */
    private final BigRational yMax;

    /**
     * For storing the lower left point.
     */
    protected V2D_Point ll;

    /**
     * For storing the upper left point.
     */
    protected V2D_Point ul;

    /**
     * For storing the upper right point.
     */
    protected V2D_Point ur;

    /**
     * For storing the lower right point.
     */
    protected V2D_Point lr;

    /**
     * The top/upper edge.
     */
    protected V2D_FiniteGeometry t;

    /**
     * The right edge.
     */
    protected V2D_FiniteGeometry r;

    /**
     * The bottom/lower edge.
     */
    protected V2D_FiniteGeometry b;

    /**
     * The left edge.
     */
    protected V2D_FiniteGeometry l;

    /**
     * For storing all the points. N.B {@link #ll}, {@link #ul}, {@link #ur},
     * {@link #ul} may all be the same.
     */
    protected HashSet<V2D_Point> pts;

    /**
     * @param e An envelop.
     */
    public V2D_Envelope(V2D_Envelope e) {
        env = e.env;
        offset = e.offset;
        yMin = e.yMin;
        yMax = e.yMax;
        xMin = e.xMin;
        xMax = e.xMax;
        t = e.t;
        r = e.r;
        b = e.b;
        l = e.l;
        ll = e.ll;
        ul = e.ul;
        ur = e.ur;
        lr = e.lr;
        pts = e.pts;
    }

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param oom The Order of Magnitude for the precision.
     * @param x The x-coordinate of a point.
     * @param y The y-coordinate of a point.
     */
    public V2D_Envelope(V2D_Environment env, int oom, BigRational x, BigRational y) {
        this(oom, new V2D_Point(env, x, y));
    }

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param oom The Order of Magnitude for the precision.
     * @param xMin What {@link xMin} is set to.
     * @param xMax What {@link xMax} is set to.
     * @param yMin What {@link yMin} is set to.
     * @param yMax What {@link yMax} is set to.
     */
    public V2D_Envelope(V2D_Environment env, int oom,
            BigRational xMin, BigRational xMax,
            BigRational yMin, BigRational yMax) {
        this(oom, new V2D_Point(env, xMin, yMin),
                new V2D_Point(env, xMax, yMax));
    }

    /**
     * Create a new instance.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @param gs The geometries used to form the envelope.
     */
    public V2D_Envelope(int oom, RoundingMode rm, V2D_FiniteGeometry... gs) {
        V2D_Envelope e = new V2D_Envelope(gs[0], oom, rm);
        for (V2D_FiniteGeometry g : gs) {
            e = e.union(new V2D_Envelope(g, oom, rm), oom);
        }
        env = e.env;
        offset = e.offset;
        yMin = e.yMin;
        yMax = e.yMax;
        xMin = e.xMin;
        xMax = e.xMax;
        t = e.t;
        r = e.r;
        b = e.b;
        l = e.l;
        ll = e.ll;
        ul = e.ul;
        ur = e.ur;
        lr = e.lr;
        pts = e.pts;
    }

    /**
     * Create a new instance.
     *
     * @param g The geometry used to form the envelope.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V2D_Envelope(V2D_FiniteGeometry g, int oom, RoundingMode rm) {
        this(oom, g.getPointsArray(oom, rm));
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param points The points used to form the envelop.
     * @throws RuntimeException if points.length == 0.
     */
    public V2D_Envelope(int oom, V2D_Point... points) {
        //offset = points[0].offset;
        //offset = V2D_Vector.ZERO;
        int len = points.length;
        switch (len) {
            case 0 ->
                throw new RuntimeException("Cannot create envelope from an empty "
                        + "collection of points.");
            case 1 -> {
                offset = V2D_Vector.ZERO;
                xMin = points[0].getX(oom, RoundingMode.FLOOR);
                xMax = points[0].getX(oom, RoundingMode.CEILING);
                yMin = points[0].getY(oom, RoundingMode.FLOOR);
                yMax = points[0].getY(oom, RoundingMode.CEILING);
                t = points[0];
                l = t;
                r = t;
                b = t;
            }
            default -> {
                offset = V2D_Vector.ZERO;
                BigRational xmin = points[0].getX(oom, RoundingMode.FLOOR);
                BigRational xmax = points[0].getX(oom, RoundingMode.CEILING);
                BigRational ymin = points[0].getY(oom, RoundingMode.FLOOR);
                BigRational ymax = points[0].getY(oom, RoundingMode.CEILING);
                for (int i = 1; i < points.length; i++) {
                    xmin = BigRational.min(xmin, points[i].getX(oom, RoundingMode.FLOOR));
                    xmax = BigRational.max(xmax, points[i].getX(oom, RoundingMode.CEILING));
                    ymin = BigRational.min(ymin, points[i].getY(oom, RoundingMode.FLOOR));
                    ymax = BigRational.max(ymax, points[i].getY(oom, RoundingMode.CEILING));
                }
                this.xMin = xmin;
                this.xMax = xmax;
                this.yMin = ymin;
                this.yMax = ymax;
            }
        }
        env = points[0].env;
    }

    @Override
    public String toString() {
        return toString(env.oom, env.rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return This represented as a string.
     */
    public String toString(int oom, RoundingMode rm) {
        return this.getClass().getSimpleName()
                + "(xMin=" + getXMin(oom, rm) + ", xMax=" + getXMax(oom, rm)
                + ", yMin=" + getYMin(oom, rm) + ", yMax=" + getYMax(oom, rm) + ")";
    }

    /**
     * @return {@link #pts} initialising first if it is null.
     */
    public HashSet<V2D_Point> getPoints() {
        if (pts == null) {
            pts = new HashSet<>(4);
            pts.add(getLL());
            pts.add(getUL());
            pts.add(getUR());
            pts.add(getLR());
        }
        return pts;
    }

    /**
     * Test for equality.
     *
     * @param e The V2D_Envelope to test for equality with this.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff {@code this} and {@code e} are equal.
     */
    public boolean equals(V2D_Envelope e, int oom) {
        return this.getXMin(oom).compareTo(e.getXMin(oom)) == 0
                && this.getXMax(oom).compareTo(e.getXMax(oom)) == 0
                && this.getYMin(oom).compareTo(e.getYMin(oom)) == 0
                && this.getYMax(oom).compareTo(e.getYMax(oom)) == 0;
    }

    /**
     * For getting {@link #xMin} rounded. RoundingMode.FLOOR is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #xMin} rounded.
     */
    public BigRational getXMin(int oom) {
        return getXMin(oom, RoundingMode.FLOOR);
    }

    /**
     * For getting {@link #xMin} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for the precision.
     * @return {@link #xMin} rounded.
     */
    public BigRational getXMin(int oom, RoundingMode rm) {
        return xMin.add(offset.getDX(oom - 2, rm));
    }

    /**
     * For getting {@link #xMax} rounded. RoundingMode.CEILING is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #xMax} rounded.
     */
    public BigRational getXMax(int oom) {
        return getXMax(oom, RoundingMode.CEILING);
    }

    /**
     * For getting {@link #xMax} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for the precision.
     * @return {@link #xMax} rounded.
     */
    public BigRational getXMax(int oom, RoundingMode rm) {
        return xMax.add(offset.getDX(oom - 2, rm));
    }

    /**
     * For getting {@link #yMin} rounded. RoundingMode.FLOOR is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #yMin} rounded.
     */
    public BigRational getYMin(int oom) {
        return getYMin(oom, RoundingMode.FLOOR);
    }

    /**
     * For getting {@link #yMin} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for the precision.
     * @return {@link #yMin} rounded.
     */
    public BigRational getYMin(int oom, RoundingMode rm) {
        return yMin.add(offset.getDY(oom - 2, rm));
    }

    /**
     * For getting {@link #yMax} rounded. RoundingMode.CEILING is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #yMax} rounded.
     */
    public BigRational getYMax(int oom) {
        return getYMax(oom, RoundingMode.CEILING);
    }

    /**
     * For getting {@link #yMax} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for the precision.
     * @return {@link #yMax} rounded.
     */
    public BigRational getYMax(int oom, RoundingMode rm) {
        return yMax.add(offset.getDY(oom - 2, rm));
    }

    /**
     * @return The LL corner point {@link #ll} setting it first if it is null.
     */
    public V2D_Point getLL() {
        if (ll == null) {
            ll = new V2D_Point(env, xMin, yMin);
        }
        return ll;
    }

    /**
     * @return The UL corner point {@link #ul} setting it first if it is null.
     */
    public V2D_Point getUL() {
        if (ul == null) {
            ul = new V2D_Point(env, xMin, yMax);
        }
        return ul;
    }

    /**
     * @return The UR corner point {@link #ur} setting it first if it is null.
     */
    public V2D_Point getUR() {
        if (ur == null) {
            ur = new V2D_Point(env, xMax, yMax);
        }
        return ur;
    }

    /**
     * @return The LR corner point {@link #lr} setting it first if it is null.
     */
    public V2D_Point getLR() {
        if (lr == null) {
            lr = new V2D_Point(env, xMax, yMin);
        }
        return lr;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return the left of the envelope.
     */
    public V2D_FiniteGeometry getLeft(int oom, RoundingMode rm) {
        if (l == null) {
            BigRational xmin = getXMin(oom);
            BigRational ymin = getYMin(oom);
            BigRational ymax = getYMax(oom);
            if (ymin.compareTo(ymax) == 0) {
                l = new V2D_Point(env, xmin, ymax);
            } else {
                l = new V2D_LineSegment(
                        new V2D_Point(env, xmin, ymin),
                        new V2D_Point(env, xmin, ymax), oom, rm);
            }
        }
        return l;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return the right of the envelope.
     */
    public V2D_FiniteGeometry getRight(int oom, RoundingMode rm) {
        if (r == null) {
            BigRational xmax = getXMax(oom);
            BigRational ymin = getYMin(oom);
            BigRational ymax = getYMax(oom);
            if (ymin.compareTo(ymax) == 0) {
                r = new V2D_Point(env, xmax, ymax);
            } else {
                r = new V2D_LineSegment(
                        new V2D_Point(env, xmax, ymin),
                        new V2D_Point(env, xmax, ymax), oom, rm);
            }
        }
        return r;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return the top of the envelope.
     */
    public V2D_FiniteGeometry getTop(int oom, RoundingMode rm) {
        if (t == null) {
            BigRational xmin = getXMin(oom);
            BigRational xmax = getXMax(oom);
            BigRational ymax = getYMax(oom);
            if (xmin.compareTo(xmax) == 0) {
                t = new V2D_Point(env, xmin, ymax);
            } else {
                t = new V2D_LineSegment(
                        new V2D_Point(env, xmin, ymax),
                        new V2D_Point(env, xmax, ymax), oom, rm);
            }
        }
        return t;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return the bottom of the envelope.
     */
    public V2D_FiniteGeometry getBottom(int oom, RoundingMode rm) {
        if (b == null) {
            BigRational xmin = getXMin(oom);
            BigRational xmax = getXMax(oom);
            BigRational ymin = getYMin(oom);
            if (xmin.compareTo(xmax) == 0) {
                b = new V2D_Point(env, xmin, ymin);
            } else {
                b = new V2D_LineSegment(
                        new V2D_Point(env, xmin, ymin),
                        new V2D_Point(env, xmax, ymin), oom, rm);
            }
        }
        return b;
    }

    /**
     * Translates this using {@code v}.
     *
     * @param v The vector of translation.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public void translate(V2D_Vector v, int oom, RoundingMode rm) {
        offset = offset.add(v, oom, rm);
        pts = null;
        if (ll != null) {
            ll.translate(v, oom, rm);
        }
        if (ul != null) {
            ul.translate(v, oom, rm);
        }
        if (ur != null) {
            ur.translate(v, oom, rm);
        }
        if (lr != null) {
            lr.translate(v, oom, rm);
        }
        if (l != null) {
            l.translate(v, oom, rm);
        }
        if (t != null) {
            t.translate(v, oom, rm);
        }
        if (r != null) {
            r.translate(v, oom, rm);
        }
        if (b != null) {
            b.translate(v, oom, rm);
        }
//        xMax = xMax.add(v.getDX(oom, rm));
//        xMin = xMin.add(v.getDX(oom, rm));
//        yMax = yMax.add(v.getDY(oom, rm));
//        yMin = yMin.add(v.getDY(oom, rm));
    }

    /**
     * Calculate and return the approximate (or exact) centroid of the envelope.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return The approximate or exact centre of this.
     */
    public V2D_Point getCentroid(int oom) {
        return new V2D_Point(env,
                getXMax(oom).add(getXMin(oom)).divide(2),
                getYMax(oom).add(getYMin(oom)).divide(2));
    }

    /**
     * @param e The V3D_Envelope to union with this.
     * @param oom The Order of Magnitude for the precision.
     * @return an Envelope which is {@code this} union {@code e}.
     */
    public V2D_Envelope union(V2D_Envelope e, int oom) {
        if (this.contains(e, oom)) {
            return this;
        } else {
            return new V2D_Envelope(env, oom,
                    BigRational.min(e.getXMin(oom), getXMin(oom)),
                    BigRational.max(e.getXMax(oom), getXMax(oom)),
                    BigRational.min(e.getYMin(oom), getYMin(oom)),
                    BigRational.max(e.getYMax(oom), getYMax(oom)));
        }
    }

    /**
     * If {@code e} touches, or overlaps then it intersects. For collision
     * avoidance, this is biased towards returning an intersection even if there
     * may not be one at a lower oom precision.
     *
     * @param e The Vector_Envelope2D to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} if this intersects with {@code e} it the {@code oom}
     * level of precision.
     */
    public boolean isIntersectedBy(V2D_Envelope e, int oom) {
        if (isBeyond(e, oom)) {
            return !e.isBeyond(this, oom);
        } else {
            return true;
        }
    }

    /**
     * @param e The envelope to test if {@code this} is beyond.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff {@code this} is beyond {@code e} (i.e. they do
     * not touch or intersect).
     */
    public boolean isBeyond(V2D_Envelope e, int oom) {
        return getXMax(oom).compareTo(e.getXMin(oom)) == -1
                || getXMin(oom).compareTo(e.getXMax(oom)) == 1
                || getYMax(oom).compareTo(e.getYMin(oom)) == -1
                || getYMin(oom).compareTo(e.getYMax(oom)) == 1;
    }

    /**
     * @param e V3D_Envelope The envelope to test if it is contained.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff {@code e} is contained by {@code this}
     */
    public boolean contains(V2D_Envelope e, int oom) {
        return getXMax(oom).compareTo(e.getXMax(oom)) != -1
                && getXMin(oom).compareTo(e.getXMin(oom)) != 1
                && getYMax(oom).compareTo(e.getYMax(oom)) != -1
                && getYMin(oom).compareTo(e.getYMin(oom)) != 1;
    }

    /**
     * The location of p may get rounded.
     *
     * @param p The point to test if it is contained.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code} true iff {@code this} contains {@code p}
     */
    public boolean contains(V2D_Point p, int oom) {
        BigRational xu = p.getX(oom, RoundingMode.CEILING);
        BigRational xl = p.getX(oom, RoundingMode.FLOOR);
        BigRational yu = p.getY(oom, RoundingMode.CEILING);
        BigRational yl = p.getY(oom, RoundingMode.FLOOR);
        return getXMax(oom).compareTo(xl) != -1
                && getXMin(oom).compareTo(xu) != 1
                && getYMax(oom).compareTo(yl) != -1
                && getYMin(oom).compareTo(yu) != 1;
    }

    /**
     * @param x The x-coordinate of the point to test for intersection.
     * @param y The y-coordinate of the point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} if this intersects with {@code pl}
     */
    public boolean contains(BigRational x, BigRational y, int oom) {
        return getXMax(oom).compareTo(x) != -1
                && getXMin(oom).compareTo(x) != 1
                && getYMax(oom).compareTo(y) != -1
                && getYMin(oom).compareTo(y) != 1;
    }

    /**
     * @param l The line to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this intersects with {@code pl}
     */
    public boolean contains(V2D_LineSegment l, int oom, RoundingMode rm) {
        return contains(l.getP(), oom) && contains(l.getQ(oom, rm), oom);
    }

    /**
     * @param s The shape to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this intersects with {@code pl}
     */
    public boolean contains(V2D_Shape s, int oom, RoundingMode rm) {
        if (contains(s.getEnvelope(oom, rm), oom)) {
            return s.getPoints(oom, rm).values().parallelStream().allMatch(x
                    -> contains(x, oom));
        }
        return false;
    }

    /**
     * @param p The point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this intersects with {@code pl}
     */
    public boolean isIntersectedBy(V2D_Point p, int oom, RoundingMode rm) {
        return isIntersectedBy(p.getX(oom, rm), p.getY(oom, rm), oom);
    }

    /**
     * This biases intersection.
     *
     * @param x The x-coordinate of the point to test for intersection.
     * @param y The y-coordinate of the point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} if this intersects with {@code pl}
     */
    public boolean isIntersectedBy(BigRational x, BigRational y, int oom) {
        return x.compareTo(getXMin(oom)) != -1
                && x.compareTo(getXMax(oom)) != 1
                && y.compareTo(getYMin(oom)) != -1
                && y.compareTo(getYMax(oom)) != 1;
    }

//    /**
//     * @param l The line to test for intersection.
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode for any rounding.
//     * @return {@code true} if this intersects with {@code pl}
//     */
//    public boolean isIntersectedBy(V2D_Line l, int oom, RoundingMode rm) {
//        if (isIntersectedBy(l.getP(), oom, rm)) {
//            return true;
//        } else {
//            if (isIntersectedBy(l.getQ(oom, rm), oom, rm)) {
//                return true;
//            }
//        }
//        if (t instanceof V2D_LineSegment tl) {
//            if (tl.isIntersectedBy(l, oom)) {
//                return true;
//            } else {
//                if (r instanceof V2D_LineSegment rl) {
//                    if (rl.isIntersectedBy(l, oom)) {
//                        return true;
//                    } else {
//                        if (b instanceof V2D_LineSegment bl) {
//                            if (bl.isIntersectedBy(l, oom)) {
//                                return true;
//                            } else {
//                                if (this.l instanceof V2D_LineSegment ll) {
//                                    if (ll.isIntersectedBy(l, oom)) {
//                                        return true;
//                                    } else {
//                                        return false;
//                                    }
//                                } else {
//                                    // This should not happen!
//                                    return false;
//                                }
//                            }
//                        } else {
//                            // This should not happen!
//                            return false;
//                        }
//                    }
//                } else {
//                    return false;
//                }
//            }
//        } else {
//            if (r instanceof V2D_LineSegment rl) {
//                if (rl.isIntersectedBy(l, oom)) {
//                    return true;
//                } else {
//                    return l.isIntersectedBy(((V2D_Point) r), oom, rm);
//                }
//            } else {
//                return l.isIntersectedBy(((V2D_Point) r), oom, rm);
//            }
//        }
//        return false;
//    }
//    /**
//     * @param l The line to test for intersection.
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode for any rounding.
//     * @return {@code true} if this intersects with {@code pl}
//     */
//    public boolean isIntersectedBy(V2D_LineSegment l, int oom, RoundingMode rm) {
//        if (isIntersectedBy(l.getP(), oom, rm)) {
//            return true;
//        } else {
//            if (isIntersectedBy(l.getQ(oom, rm), oom, rm)) {
//                return true;
//            }
//        }
//        if (t instanceof V2D_LineSegment tl) {
//            if (tl.isIntersectedBy(this, oom)) {
//                return true;
//            } else {
//                if (r instanceof V2D_LineSegment rl) {
//                    if (rl.isIntersectedBy(this, oom)) {
//                        return true;
//                    } else {
//                        if (b instanceof V2D_LineSegment bl) {
//                            if (bl.isIntersectedBy(this, oom)) {
//                                return true;
//                            } else {
//                                if (this.l instanceof V2D_LineSegment ll) {
//                                    if (ll.isIntersectedBy(this, oom)) {
//                                        return true;
//                                    } else {
//                                        return false;
//                                    }
//                                } else {
//                                    // This should not happen!
//                                    return false;
//                                }
//                            }
//                        } else {
//                            // This should not happen!
//                            return false;
//                        }
//                    }
//                } else {
//                    return false;
//                }
//            }
//        } else {
//            if (r instanceof V2D_LineSegment rl) {
//                if (rl.isIntersectedBy(this, oom)) {
//                    return true;
//                } else {
//                    return isIntersectedBy(((V2D_Point) r), oom, rm);
//                }
//            } else {
//                return isIntersectedBy(((V2D_Point) r), oom, rm);
//            }
//        }
//        return false;
//    }
    /**
     * @param en The envelope to intersect.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code null} if there is no intersection; {@code en} if
     * {@code this.equals(en)}; otherwise returns the intersection.
     */
    public V2D_Envelope getIntersection(V2D_Envelope en, int oom) {
        if (!isIntersectedBy(en, oom)) {
            return null;
        }
// Probably quicker without: 
//        if (contains(en, oom)) {
//            return this;
//        }
//        if (en.contains(this, oom)) {
//            return en;
//        }
        return new V2D_Envelope(env, oom,
                BigRational.max(getXMin(oom), en.getXMin(oom)),
                BigRational.min(getXMax(oom), en.getXMax(oom)),
                BigRational.max(getYMin(oom), en.getYMin(oom)),
                BigRational.min(getYMax(oom), en.getYMax(oom)));
    }
}
