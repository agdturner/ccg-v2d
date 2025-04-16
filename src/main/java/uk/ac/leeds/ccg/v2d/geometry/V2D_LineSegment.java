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
import java.util.Arrays;
import java.util.Collection;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v2d.core.V2D_Environment;

/**
 * 2D representation of a finite length line (a line segment). The line begins
 * at the point of {@link #l} and ends at the point {@link #qv}.
 *
 * @author Andy Turner
 * @version 2.0
 */
public class V2D_LineSegment extends V2D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * The line of which this line segment is part and for which the point on
     * the line is one end point of this segment.
     */
    public final V2D_Line l;

    /**
     * For storing the line at l.getP() orthogonal to l.v.
     */
    protected V2D_Line pl;

    /**
     * For storing the line at getQ() orthogonal to l.v.
     */
    protected V2D_Line ql;

    /**
     * For storing the length of the line squared.
     */
    protected BigRational len2;

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is cloned from.
     */
    public V2D_LineSegment(V2D_LineSegment l) {
        super(l.env, l.offset);
        this.l = new V2D_Line(l.l);
    }

    /**
     * Create a new instance. {@link #offset} is set to {@link V2D_Vector#ZERO}.
     *
     * @param env What {@link #env} is set to.
     * @param p What the point of {@link #l} is cloned from.
     * @param q What {@link #qv} is cloned from.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V2D_LineSegment(V2D_Environment env, V2D_Vector p, V2D_Vector q,
            int oom, RoundingMode rm) {
        this(env, V2D_Vector.ZERO, p, q, oom, rm);
    }

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param p What the point of {@link #l} is cloned from.
     * @param q What {@link #qv} is cloned from.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V2D_LineSegment(V2D_Environment env, V2D_Vector offset, V2D_Vector p,
            V2D_Vector q, int oom, RoundingMode rm) {
        super(env, offset);
        l = new V2D_Line(env, offset, p, q, oom, rm);
    }

    /**
     * Create a new instance.
     *
     * @param p What the point of {@link #l} is cloned from.
     * @param q What {@link #qv} is cloned from.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V2D_LineSegment(V2D_Point p, V2D_Point q, int oom, RoundingMode rm) {
        super(p.env, p.offset);
        V2D_Point q2 = new V2D_Point(q);
        q2.setOffset(offset, oom, rm);
        l = new V2D_Line(env, offset, p.rel, q2.rel, oom, rm);
    }
    
    /**
     * Create a new instance that intersects all points.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param points Any number of collinear points, with two being different.
     */
    public V2D_LineSegment(int oom, RoundingMode rm, 
            ArrayList<V2D_Point> points) {
        this(oom, rm, points.toArray(V2D_Point[]::new));
    }

    /**
     * Create a new instance that intersects all points.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param points Any number of collinear points, with two being different.
     */
    public V2D_LineSegment(int oom, RoundingMode rm, V2D_Point... points) {
        super(points[0].env, points[0].offset);
        ArrayList<V2D_Point> unique = V2D_Point.getUnique(Arrays.asList(points),
                oom, rm);
        int n = unique.size();
        if (n < 2) {
            throw new RuntimeException("Line segment points the same.");
        }
        V2D_Point p0 = unique.get(0);
        V2D_Point p1 = unique.get(1);
        V2D_LineSegment ls = new V2D_LineSegment(p0, p1, oom, rm);
        for (int i = 2; i < n; i++) {
            V2D_Point p = unique.get(i);
            if (!ls.intersects(p, oom, rm)) {
                V2D_LineSegment l2 = new V2D_LineSegment(ls.getP(), p, oom, rm);
                V2D_Point lq = ls.getQ(oom, rm);
                if (l2.intersects(lq, oom, rm)) {
                    ls = l2;
                } else {
                    ls = new V2D_LineSegment(ls.getQ(oom, rm), p, oom, rm);
                }
            }
        }
        this.l = ls.l;
    }

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     */
    public V2D_LineSegment(V2D_Line l) {
        super(l.env);
        this.l = new V2D_Line(l);
    }

    /**
     * @return {@link #l} pv with {@link #l} offset applied.
     */
    public V2D_Point getP() {
        return l.getP();
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code l.getQ(oom, rm)}.
     */
    public V2D_Point getQ(int oom, RoundingMode rm) {
        return l.getQ(oom, rm);
    }

    /**
     * Translate (move relative to the origin).
     *
     * @param v The vector to translate.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    @Override
    public void translate(V2D_Vector v, int oom, RoundingMode rm) {
        super.translate(v, oom, rm);
        l.translate(v, oom, rm);
        if (pl != null) {
            pl.translate(v, oom, rm);
        }
        if (ql != null) {
            ql.translate(v, oom, rm);
        }
    }

    @Override
    public String toString() {
        return toStringSimple("");
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of this.
     */
    public String toString(String pad) {
        return this.getClass().getSimpleName() + "\n"
                + pad + "(\n"
                + toStringFields(pad + " ") + "\n"
                + pad + ")";
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of this.
     */
    public String toStringSimple(String pad) {
        return this.getClass().getSimpleName() + "\n"
                + pad + "(\n"
                + toStringFieldsSimple(pad + " ") + "\n"
                + pad + ")";
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    @Override
    protected String toStringFields(String pad) {
        String r = super.toStringFields(pad) + "\n"
                + pad + ",\n";
        r += pad + "l=" + l.toStringFields(pad);
        return r;
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    @Override
    protected String toStringFieldsSimple(String pad) {
        String r = super.toStringFieldsSimple(pad) + ",\n";
        r += pad + "l=" + l.toStringFieldsSimple(pad);
        return r;
    }

    /**
     * @param l The line segment to test if it is the same as {@code this}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@code l} is the same as {@code this}.
     */
    public boolean equals(V2D_LineSegment l, int oom, RoundingMode rm) {
        if (equalsIgnoreDirection(l, oom, rm)) {
            return this.l.getP().equals(l.getP(), oom, rm);
        }
        return false;
    }

    /**
     * @param l The line segment to test if it is equal to {@code this}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@code this} is equal to {@code l}. This ignores
     * the order of the point of {@link #l} and {@link #qv}.
     */
    public boolean equalsIgnoreDirection(V2D_LineSegment l, int oom, RoundingMode rm) {
        if (this.l.equals(l.l, oom, rm)) {
            return intersects(l.getQ(oom, rm), oom, rm)
                    && l.intersects(getQ(oom, rm), oom, rm);
        }
        return false;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The length of {@code this}.
     */
    public Math_BigRationalSqrt getLength(int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getLength2(oom, rm), oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The length of {@code this} squared.
     */
    public BigRational getLength2(int oom, RoundingMode rm) {
        return getP().getDistanceSquared(getQ(oom, rm), oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code new V2D_AABB(start, end)}
     */
    @Override
    public V2D_AABB getAABB(int oom, RoundingMode rm) {
        if (en == null) {
            en = new V2D_AABB(oom, getP(), getQ(oom, rm));
        }
        return en;
    }

    @Override
    public V2D_Point[] getPointsArray(int oom, RoundingMode rm) {
        V2D_Point[] r = new V2D_Point[2];
        r[0] = getP();
        r[1] = getQ(oom, rm);
        return r;
    }
    
    /**
     * Checks the envelope.
     * 
     * @param pt A point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if {@code this} is intersected by {@code pv}.
     */
    public boolean intersects(V2D_Point pt, int oom, RoundingMode rm) {
        if (getAABB(oom, rm).contains(pt, oom)) {
            return intersects0(pt, oom, rm);
        } else {
            return false;
        }
    }

    /**
     * Does not check the envelope.
     * 
     * @param pt A point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if {@code this} is intersected by {@code pv}.
     */
    public boolean intersects0(V2D_Point pt, int oom, RoundingMode rm) {
        if (l.intersects(pt, oom, rm)) {
            V2D_Point tp = getP();
            Math_BigRationalSqrt a = pt.getDistance(oom, rm, tp);
            if (a.getX().isZero()) {
                return true;
            }
            V2D_Point tq = getQ(oom, rm);
            Math_BigRationalSqrt b = pt.getDistance(oom, rm, tq);
            if (b.getX().isZero()) {
                return true;
            }
            Math_BigRationalSqrt d = tp.getDistance(oom, rm, tq);
            Math_BigRationalSqrt apb = a.add(b, oom, rm);
            if (apb == null) {
                int oomt = oom - 2;
                return a.getSqrt(oomt, rm).add(b.getSqrt(oomt, rm)).compareTo(
                        d.getSqrt(oomt, rm)) != 1;
            } else {
                return apb.equals(d, oom);
//                if (apb.compareTo(d) != 1) {
//                    return true;
//                }
            }
        } else {
            return false;
        }
    }
    
    /**
     * @param aabb The Axis Aligned Bounding Box to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if {@code this} is intersected by {@code aabb}.
     */
    public boolean intersects(V2D_AABB aabb, int oom, RoundingMode rm) {
        if (getAABB(oom, rm).intersects(aabb, oom)) {
            if (aabb.intersects(getP(), oom, rm)
                    || aabb.intersects(getQ(oom, rm), oom, rm)) {
                return true;
            }
            V2D_FiniteGeometry left = aabb.getLeft(oom, rm);
            if (left instanceof V2D_LineSegment ll) {
                if (intersects(ll, oom, rm)) {
                    return true;
                }
            } else {
                if (intersects((V2D_Point) left, oom, rm)) {
                    return true;
                }
            }
            V2D_FiniteGeometry r = aabb.getRight(oom, rm);
            if (r instanceof V2D_LineSegment rl) {
                if (intersects(rl, oom, rm)) {
                    return true;
                }
            } else {
                if (intersects((V2D_Point) r, oom, rm)) {
                    return true;
                }
            }
            V2D_FiniteGeometry t = aabb.getTop(oom, rm);
            if (t instanceof V2D_LineSegment tl) {
                if (intersects(tl, oom, rm)) {
                    return true;
                }
            } else {
                if (intersects((V2D_Point) t, oom, rm)) {
                    return true;
                }
            }
            V2D_FiniteGeometry b = aabb.getBottom(oom, rm);
            if (b instanceof V2D_LineSegment bl) {
                if (intersects(bl, oom, rm)) {
                    return true;
                }
            } else {
                if (intersects((V2D_Point) b, oom, rm)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param l A line to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if {@code this} is intersected by {@code l}.
     */
    public boolean intersects(V2D_Line l, int oom, RoundingMode rm) {
        if (this.l.intersects(l, oom, rm)) {
            return getIntersect(l, oom, rm) != null;
        }
        return false;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param l A line to test for with any of the lines in ls.
     * @param ls The other lines to test for intersection with l.
     * @return {@code true} if {@code l} is intersects any of the 
     * line segments in {@code ls}.
     */
    public static boolean intersects(int oom, RoundingMode rm, 
            V2D_LineSegment l, V2D_LineSegment... ls) {
        return intersects(oom, rm, l, Arrays.asList(ls));
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param l A line to test for with any of the lines in ls.
     * @param ls The other lines to test for intersection with l.
     * @return {@code true} if {@code l} is intersects any of the 
     * line segments in {@code ls}.
     */
    public static boolean intersects(int oom, RoundingMode rm,
            V2D_LineSegment l, Collection<V2D_LineSegment> ls) {
        return ls.parallelStream().anyMatch(x -> x.intersects(l, oom, rm));
    }

    /**
     * @param p A point to test for intersection.
     * @param ls The lines to test for intersection with p.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if {@code p} intersects any of the 
     * line segments in {@code ls}.
     */
    public static boolean intersects(int oom, RoundingMode rm, V2D_Point p,
            V2D_LineSegment... ls) {
        return intersects(oom, rm, p, Arrays.asList(ls));
    }

    /**
     * @param p A point to test for intersection.
     * @param ls The lines to test for intersection with p.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if {@code p} intersects any of the 
     * line segments in {@code ls}.
     */
    public static boolean intersects(int oom, RoundingMode rm, V2D_Point p,
            Collection<V2D_LineSegment> ls) {
        return ls.parallelStream().anyMatch(x -> x.intersects(p, oom, rm));
    }

    /**
     * @param l A line segment to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if {@code this} is intersected by {@code l}.
     */
    public boolean intersects(V2D_LineSegment l, int oom, RoundingMode rm) {
        return getIntersect(l, oom, rm) != null;
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}. If they overlap in a line return the part that
     * overlaps (the order of points is not defined). If they intersect at a
     * point, the point is returned. {@code null} is returned if the two line
     * segments do not intersect.
     *
     * @param l The line to get intersection with this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V2D_FiniteGeometry getIntersect(V2D_Line l, int oom, RoundingMode rm) {
        BigRational x1 = getP().getX(oom, rm);
        BigRational x2 = getQ(oom, rm).getX(oom, rm);
        BigRational x3 = l.getP().getX(oom, rm);
        BigRational x4 = l.getQ(oom, rm).getX(oom, rm);
        BigRational y1 = this.l.p.getY(oom, rm);
        BigRational y2 = this.l.q.getY(oom, rm);
        BigRational y3 = l.p.getY(oom, rm);
        BigRational y4 = l.q.getY(oom, rm);
        BigRational den = V2D_Line.getIntersectDenominator(x1, x2, x3, x4, y1, y2, y3, y4);
        V2D_Geometry li = this.l.getIntersect(l, den, x1, x2, x3, x4, y1, y2, y3, y4, oom, rm);
        if (li != null) {
            if (li instanceof V2D_Point pli) {
                //if (intersects(pli, oom, rm)) {
                if (isAligned(pli, oom, rm)) {
                    return pli;
                } else {
                    return null;
                }
            } else {
                return this;
            }
        }
        return null;
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}. If they overlap in a line return the part that
     * overlaps (the order of points is not defined). If they intersect at a
     * point, the point is returned. {@code null} is returned if the two line
     * segments do not intersect.
     *
     * @param l The line to get intersection with this.
     * @param li The non-null line intersection.
     * @param den getIntersectDenominator(x1, x2, x3, x4, y1, y2, y3, y4)
     * @param x1 getP().getX()
     * @param x2 getQ().getX()
     * @param x3 l.getP().getX()
     * @param x4 l.getQ().getX()
     * @param y1 this.l.p.getY()
     * @param y2 this.l.q.getY()
     * @param y3 l.p.getY()
     * @param y4 l.q.getY()
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V2D_FiniteGeometry getIntersect(V2D_Line l, V2D_Geometry li,
            BigRational den, BigRational x1, BigRational x2, BigRational x3,
            BigRational x4, BigRational y1, BigRational y2, BigRational y3,
            BigRational y4, int oom, RoundingMode rm) {
        if (li instanceof V2D_Line) {
            return this;
        }
        BigRational t = (((x1.subtract(x3)).multiply(y3.subtract(y4)))
                .subtract((y1.subtract(y3)).multiply(x3.subtract(x4))))
                .divide(den);
        //if ((t >= -epsilon) && (t <= 1D + epsilon)) {
        if ((t.compareTo(BigRational.ZERO) != -1) && (t.compareTo(BigRational.ONE) != 1)) {
            return (V2D_Point) li;
        } else {
            BigRational u = (((x1.subtract(x2)).multiply(y1.subtract(y3)))
                    .subtract((y1.subtract(y2)).multiply(x1.subtract(x3))))
                    .divide(den);
            //if (u >= -epsilon && u <= 1D + epsilon) {
            if ((u.compareTo(BigRational.ZERO) != -1) && (u.compareTo(BigRational.ONE) != 1)) {
                return (V2D_Point) li;
            } else {
                return null;
            }
        }
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}. If they overlap in a line return the part that
     * overlaps (the order of points is not defined). If they intersect at a
     * point, the point is returned. {@code null} is returned if the two line
     * segments do not intersect.
     *
     * @param ls The line to get intersection with this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V2D_FiniteGeometry getIntersect(V2D_LineSegment ls, int oom, 
            RoundingMode rm) {
        if (!getAABB(oom, rm).intersects(ls.getAABB(oom, rm), oom)) {
            return null;
        }
        BigRational x1 = getP().getX(oom, rm);
        BigRational x2 = getQ(oom, rm).getX(oom, rm);
        BigRational x3 = ls.getP().getX(oom, rm);
        BigRational x4 = ls.getQ(oom, rm).getX(oom, rm);
        BigRational y1 = getP().getY(oom, rm);
        BigRational y2 = getQ(oom, rm).getY(oom, rm);
        BigRational y3 = ls.getP().getY(oom, rm);
        BigRational y4 = ls.getQ(oom, rm).getY(oom, rm);
        BigRational den = V2D_Line.getIntersectDenominator(x1, x2, x3, x4, 
                y1, y2, y3, y4);
        if (den.compareTo(BigRational.ZERO) == 0) {
            // Either the line segments are collinear or parallel but not 
            // collinear.
            if (this.l.equals(ls.l, oom, rm)) {
                return getIntersect0(ls, oom, rm);
            } else {
                return null;
            }
        }
        // Get intersection of infinite lines. 
        V2D_Geometry li = l.getIntersect(ls.l, den, x1, x2, x3, x4, y1, y2, 
                y3, y4, oom, rm);
        // Get intersection with infinite lines.
        V2D_FiniteGeometry tils = getIntersect(ls.l, li, den, x1, x2, x3, x4,
                y1, y2, y3, y4, oom, rm);
        V2D_FiniteGeometry lsil = ls.getIntersect(l, li, den, x1, x2, x3, x4,
                y1, y2, y3, y4, oom, rm);
        if (li == null) {
            return null;
        } else {
            if (tils == null) {
                if (lsil == null) {
                    return null;
                } else {
                    if (lsil instanceof V2D_Point lsilp) {
                        //if (intersects(lsilp, oom, rm)) {
                        if (isAligned(lsilp, oom, rm)) {
                            return lsilp;
                        } else {
                            return null;
                        }
                    } else {
                        //throw new RuntimeException();
                        return null;
                    }
                }
            } else {
                if (tils instanceof V2D_Point tilsp) {
                    //if (intersects(tilsp, oom, rm) && ls.intersects(tilsp, oom, rm)) {
                    if (isAligned(tilsp, oom, rm) && ls.isAligned(tilsp, oom, rm)) {
                        return tilsp;
                    } else {
                        return null;
                    }
                } else {
                    return getIntersect0(ls, oom, rm);
                }
            }
        }
    }

    /**
     * Use when this and ls are collinear and intersect.
     *
     * @param ls An intersecting collinear line segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The intersection.
     */
    public V2D_FiniteGeometry getIntersect0(V2D_LineSegment ls, int oom,
            RoundingMode rm) {
        /**
         * Check the type of intersection. {@code
         * A) p_q l.p_l.q
         *
         * 1)  p +----------+ q
         *              l.p +----------+ l.q
         *
         * 2)  p +----------+ q
         *         l.p +----------+ l.q
         *
         * 3)  p +------------------------+ q
         *         l.p +----------+ l.q
         *
         * 4)        p +----------+ q
         *     l.p +----------------------+ l.q
         *
         * 5)    p +----------+ q
         *     l.p +----------+ l.q
         *
         * 6)         p +----------+ q
         *     l.p +----------+ l.q
         *
         * 7)              p +----------+ q
         *     l.p +---------+ l.q
         *
         * B) q_p l.p_l.q
         *
         * 8)  q +----------+ p
         *              l.p +----------+ l.q
         *
         * 9)  q +----------+ p
         *         l.p +----------+ l.q
         *
         * 10) q +------------------------+ p
         *         l.p +----------+ l.q
         *
         * 11)       q +----------+ p
         *     l.p +----------------------+ l.q
         *
         * 12)       q +----------+ p
         *         l.p +----------+ l.q
         *
         * 13)       q +----------+ p
         *     l.p +----------+ l.q
         *
         * 14)              q +----------+ p
         *     l.p +----------+ l.q
         *
         * C) p_q l.q_l.p
         *
         * 15) p +----------+ q
         *              l.q +----------+ l.p
         *
         * 16) p +----------+ q
         *         l.q +----------+ l.p
         *
         * 17) p +------------------------+ q
         *         l.q +----------+ l.p
         *
         * 18)       p +----------+ q
         *     l.q +------------------------+ l.p
         *
         * 19)       p +----------+ q
         *         l.q +----------+ l.p
         *
         * 20)       p +----------+ q
         *     l.q +----------+ l.p
         *
         * 21)              p +----------+ q
         *     l.q +----------+ l.p
         *
         * D) q_p l.q_l.p
         *
         * 22) q +----------+ p
         *              l.q +----------+ l.p
         *
         * 23) q +----------+ p
         *         l.q +----------+ l.p
         *
         * 24) q +------------------------+ p
         *         l.q +----------+ l.p
         *
         * 25)       q +----------+ p
         *     l.q +------------------------+ l.p
         *
         * 26)       p +----------+ q
         *         l.q +----------+ l.p
         *
         * 27)       q +----------+ p
         *    l.q +---------+ l.p
         *
         * 28)            q +----------+ p
         *    l.q +---------+ l.p
         * }
         */
        V2D_Point lp = ls.getP();
        V2D_Point lq = ls.getQ(oom, rm);
        V2D_Point tp = getP();
        V2D_Point tq = getQ(oom, rm);
        //if (intersects(lp, oom, rm)) {
        if (isAligned(lp, oom, rm)) {
            // Cases: 1, 2, 3, 5, 8, 9, 10, 12, 17, 19, 20, 21, 24, 26, 27, 28
            //if (intersects(lq, oom, rm)) {
            if (isAligned(lq, oom, rm)) {
                // Cases: 3, 5, 10, 12, 17, 19, 24, 26
                return ls;
            } else {
                // Cases: 1, 2, 8, 9, 20, 21, 27, 28
                //if (ls.intersects(tp, oom, rm)) {
                if (ls.isAligned(tp, oom, rm)) {
                    // Cases: 8, 9, 20, 21
                    if (tp.equals(lp, oom, rm)) {
                        // Cases: 8, 21
                        return tp;
                    } else {
                        // Cases: 9, 20
                        return V2D_LineSegment.getGeometry(lp, tp, oom, rm);
                    }
                } else {
                    // Cases: 1, 2, 27, 28
                    if (lp.equals(tq, oom, rm)) {
                        // Cases: 1, 28
                        return lp;
                    } else {
                        // Case: 2, 27
                        return V2D_LineSegment.getGeometry(lp, tq, oom, rm);
                    }
                }
            }
        } else {
            // Cases: 4, 6, 7, 11, 13, 14, 15, 16, 18, 22, 23, 25
            //if (intersects(lq, oom, rm)) {
            if (isAligned(lq, oom, rm)) {
                // Cases: 6, 7, 13, 14, 15, 16, 22, 23
                //if (ls.intersects(tp, oom, rm)) {
                if (ls.isAligned(tp, oom, rm)) {
                    // Cases: 6, 7, 22, 23
                    //if (ls.intersects(tq, oom, rm)) {
                    if (ls.isAligned(tq, oom, rm)) {
                        // Cases: 23
                        return V2D_LineSegment.getGeometry(lq, tp, oom, rm);
                    } else {
                        // Cases: 6, 7, 22, 
                        if (tp.equals(lq, oom, rm)) {
                            // Cases: 7, 22
                            return tp;
                        } else {
                            // Case: 6
                            return V2D_LineSegment.getGeometry(tp, lq, oom, rm);
                        }
                    }
                } else {
                    // Cases: 13, 14, 15, 16
                    if (tq.equals(lq, oom, rm)) {
                        // Cases: 14, 15
                        return tq;
                    } else {
                        // Case: 13, 16
                        //return new V2D_LineSegment(e, l.qv, ls.l.qv);
                        return V2D_LineSegment.getGeometry(tq, lq, oom, rm);
                    }
                }
            } else {
                // Cases: 4, 11, 18, 25
                return this;
            }
        }
    }

    /**
     * If the distance from a point to the line is less than the distance of the
     * point from either end of the line and the distance from either end of the
     * line is greater than the length of the line then the distance is the
     * shortest of the distances from the point to the points at either end of
     * the line segment. In all other cases, the distance is the distance
     * between the point and the line.
     *
     * @param p A point for which the minimum distance from {@code this} is
     * returned.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The minimum distance between this and {@code pv}.
     */
    public BigRational getDistance(V2D_Point p, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom, rm), oom, rm)
                .getSqrt(oom, rm);
    }

    /**
     * If the distance from a point to the line is less than the distance of the
     * point from either end of the line and the distance from either end of the
     * line is greater than the length of the line then the distance is the
     * shortest of the distances from the point to the points at either end of
     * the line segment. In all other cases, the distance is the distance
     * between the point and the line.
     *
     * @param pt A point for which the minimum distance from {@code this} is
     * returned.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The minimum distance between this and {@code pv}.
     */
    public BigRational getDistanceSquared(V2D_Point pt, int oom,
            RoundingMode rm) {
        if (intersects(pt, oom, rm)) {
            return BigRational.ZERO;
        }
        V2D_Point poi = l.getPointOfIntersect(pt, true, oom, rm);
        if (isAligned(poi, oom, rm)) {
            return poi.getDistanceSquared(pt, oom, rm);
        } else {
            return BigRational.min(pt.getDistanceSquared(getP(), oom, rm),
                    pt.getDistanceSquared(getQ(oom, rm), oom, rm));
        }
    }

    /**
     * Calculates and returns if pt is in line with this. It is in line if it is
     * between the planes defined by the ends of the line segment with the
     * normal vector as the vector of the line.
     *
     * @param pt The point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} If pt is in line with this.
     */
    public boolean isAligned(V2D_Point pt, int oom, RoundingMode rm) {
        if (getPL().isOnSameSide(pt, getQ(oom, rm), oom, rm)) {
            return getQL(oom, rm).isOnSameSide(pt, getP(), oom, rm);
        }
        return false;
    }

    /**
     * Calculates and returns if l is in line with this. It is in line if both
     * end points of l are in line with this as according to
     * {@link #isAligned(uk.ac.leeds.ccg.v3d.geometry.V2D_Point, int, java.math.RoundingMode)}.
     *
     * @param l The line segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} If pt is in line with this.
     */
    public boolean isAligned(V2D_LineSegment l, int oom, RoundingMode rm) {
        if (isAligned(l.getP(), oom, rm)) {
            return isAligned(l.getQ(oom, rm), oom, rm);
        }
        return false;
    }

    /**
     * @param l The line segment to return the distance from.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The distance from {@code this} to {@code l} at the {@code oom}
     * precision.
     */
    public BigRational getDistance(V2D_LineSegment l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom, rm), oom, rm)
                .getSqrt(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code l}.
     */
    public BigRational getDistanceSquared(V2D_LineSegment l, int oom,
            RoundingMode rm) {
        if (getIntersect(l, oom, rm) != null) {
            return BigRational.ZERO;
        }
        V2D_LineSegment loi = getLineOfIntersect(l, oom, rm);
        if (loi == null) {
            /**
             * Lines are parallel.
             */
            return getDistanceSquared(l.getP(), oom, rm);
        } else {
            return loi.getLength2(oom, rm);
        }
    }

    /**
     * Calculate and return the midpoint between pv and qv.
     *
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return the midpoint between pv and qv to the OOM precision.
     */
    public V2D_Point getMidpoint(int oom, RoundingMode rm) {
        V2D_Vector pmpq = l.v.divide(BigRational.valueOf(2), oom, rm);
        return new V2D_Point(env, offset, l.pv.add(pmpq, oom, rm));
    }

    /**
     * For returning the other end of the line segment as a point.
     *
     * @param pt A point equal to either {@link #getP()} or the point of
     * {@link #l}.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The other point that is not equal to a.
     */
    public V2D_Point getOtherPoint(V2D_Point pt, int oom, RoundingMode rm) {
        if (getP().equals(pt, oom, rm)) {
            return getQ(oom, rm);
        } else {
            return pt;
        }
    }

    /**
     * If p and q are equal then the point is returned otherwise the line
     * segment is returned
     *
     * @param p A point.
     * @param q Another possibly equal point.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return either {@code pv} or {@code new V2D_LineSegment(pv, qv)}
     */
    public static V2D_FiniteGeometry getGeometry(V2D_Point p, V2D_Point q,
            int oom, RoundingMode rm) {
        if (p.equals(q, oom, rm)) {
            return p;
        } else {
            return new V2D_LineSegment(p, q, oom, rm);
        }
    }

    /**
     * If p, q and r are equal then the point is returned otherwise a line
     * segment is returned where all the points are on the line segment.
     *
     * @param p A point possibly equal to q or r, but certainly collinear.
     * @param q A point possibly equal to p or r, but certainly collinear.
     * @param r A point possibly equal to p or q, but certainly collinear.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return either {@code pv} or {@code new V2D_LineSegment(pv, qv)}
     */
    public static V2D_FiniteGeometry getGeometry(V2D_Point p, V2D_Point q,
            V2D_Point r, int oom, RoundingMode rm) {
        if (p.equals(q, oom, rm)) {
            return getGeometry(p, r, oom, rm);
        } else if (q.equals(r, oom, rm)) {
            return getGeometry(p, r, oom, rm);
        } else if (p.equals(r, oom, rm)) {
            return getGeometry(p, q, oom, rm);
        } else {
            V2D_LineSegment ls = new V2D_LineSegment(p, q, oom, rm);
            if (ls.intersects(r, oom, rm)) {
                return ls;
            } else {
                ls = new V2D_LineSegment(p, r, oom, rm);
                if (ls.intersects(q, oom, rm)) {
                    return ls;
                } else {
                    return new V2D_LineSegment(q, r, oom, rm);
                }
//-----------
//            if (r.isAligned(p, q, oom, rm)) {
//                return new V2D_LineSegment(p, q, oom, rm);
//            } else {
//                if (p.isAligned(r, q, oom, rm)) {
//                    return new V2D_LineSegment(q, r, oom, rm);
//                } else {
//                    return new V2D_LineSegment(p, r, oom, rm);
//                }
//            }
            }
        }
    }

    /**
     * If pv, qv and r are equal then the point is returned otherwise a line
     * segment is returned where all the points are on the line segment.
     *
     * @param l A line segment.
     * @param pt A point collinear with l.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return either {@code pv} or {@code new V2D_LineSegment(pv, qv)}
     */
    public static V2D_LineSegment getGeometry(V2D_LineSegment l,
            V2D_Point pt, int oom, RoundingMode rm) {
        return (V2D_LineSegment) getGeometry(l.getP(), l.getQ(oom, rm), pt, oom, rm);
    }

    /**
     * Get the smallest line segment intersected by all pts.
     *
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @param pts Collinear points.
     * @return either {@code pv} or {@code new V2D_LineSegment(pv, qv)}
     */
    public static V2D_FiniteGeometry getGeometry(int oom, RoundingMode rm,
            V2D_Point... pts) {
        int length = pts.length;
        switch (length) {
            case 0 -> {
                return null;
            }
            case 1 -> {
                return pts[0];
            }
            case 2 -> {
                return getGeometry(pts[0], pts[1], oom, rm);
            }
            case 3 -> {
                return getGeometry(pts[0], pts[1], pts[2], oom, rm);
            }
            default -> {
                V2D_FiniteGeometry g = getGeometry(pts[0], pts[1], pts[2], oom, rm);
                for (int i = 3; i < length; i++) {
                    if (g instanceof V2D_Point gp) {
                        g = getGeometry(gp, pts[i], oom, rm);
                    } else {
                        g = getGeometry((V2D_LineSegment) g, pts[i], oom, rm);
                    }
                }
                return g;
            }

        }
    }

    /**
     * Calculates the shortest line segment which all line segments intersect
     * with.
     *
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @param ls Collinear line segments.
     * @return The shortest line segment which all the line segment points in ls
     * intersect with.
     */
    public static V2D_LineSegment getGeometry(int oom, RoundingMode rm,
            V2D_LineSegment... ls) {
        switch (ls.length) {
            case 0 -> {
                return null;
            }
            case 1 -> {
                return ls[0];
            }
            default -> {
                V2D_LineSegment r = getGeometry(ls[0], ls[1].getP(), oom, rm);
                r = getGeometry(r, ls[1].getQ(oom, rm), oom, rm);
                for (int i = 1; i < ls.length; i++) {
                    r = getGeometry(r, ls[i].getP(), oom, rm);
                    r = getGeometry(r, ls[i].getQ(oom, rm), oom, rm);
                }
                return r;
            }

        }
    }

    /**
     * Get the line of intersection (the shortest line) between {@code this} and
     * {@code l}.
     *
     * @param l The line to get the line of intersection with.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The line of intersection between {@code this} and {@code l}.
     */
    public V2D_LineSegment getLineOfIntersect(V2D_Line l, int oom, 
            RoundingMode rm) {
        if (getIntersect(l, oom, rm) != null) {
            return null;
        }
        V2D_LineSegment loi = null;
        V2D_Point tp = getP();
        V2D_Point tq = getQ(oom, rm);
        if (loi == null) {
            BigRational pd = l.getDistanceSquared(tp, oom, rm);
            BigRational qd = l.getDistanceSquared(tq, oom, rm);
            if (pd.compareTo(qd) == 1) {
                return new V2D_LineSegment(tq, 
                        l.getPointOfIntersect(tq, true, oom, rm), oom, rm);
            } else {
                return new V2D_LineSegment(tp, 
                        l.getPointOfIntersect(tp, true, oom, rm), oom, rm);
            }
        } else {
            V2D_Point lsp = loi.getP();
            //V2D_Point lsq = loi.getQ();
            V2D_Vector pv = l.v.rotate90();
            V2D_Line plp = new V2D_Line(tp, pv);
            V2D_Line plq = new V2D_Line(tq, pv);
            if (plp.isOnSameSide(lsp, tq, oom, rm)) {
                if (plq.isOnSameSide(lsp, tp, oom, rm)) {
                    /**
                     * The line of intersection connects in the line segment, so
                     * return it.
                     */
                    return loi;
                } else {
                    return new V2D_LineSegment(tq, lsp, oom, rm);
                }
            } else {
                return new V2D_LineSegment(tp, lsp, oom, rm);
            }
        }
//        if (getIntersect(l, oom, rm) != null) {
//            return null;
//        }
//        V2D_LineSegment loi = this.l.getLineOfIntersect(l, oom, rm);
//        V2D_Point tp = getP();
//        V2D_Point tq = getQ();
//        if (loi == null) {
//            BigRational pd = l.getDistanceSquared(tp, oom, rm);
//            BigRational qd = l.getDistanceSquared(tq, oom, rm);
//            if (pd.compareTo(qd) == 1) {
//                return new V2D_LineSegment(tq, l.getPointOfIntersect(tq, oom, rm), oom, rm);
//            } else {
//                return new V2D_LineSegment(tp, l.getPointOfIntersect(tp, oom, rm), oom, rm);
//            }
//        } else {
//            V2D_Point lsp = loi.getP();
//            //V2D_Point lsq = loi.getQ();
//            V2D_Plane plp = new V2D_Plane(tp, l.v);
//            V2D_Plane plq = new V2D_Plane(tq, l.v);
//            if (plp.isOnSameSide(lsp, tq, oom, rm)) {
//                if (plq.isOnSameSide(lsp, tp, oom, rm)) {
//                    /**
//                     * The line of intersection connects in the line segment, so
//                     * return it.
//                     */
//                    return loi;
//                } else {
//                    return new V2D_LineSegment(tq, lsp, oom, rm);
//                }
//            } else {
//                return new V2D_LineSegment(tp, lsp, oom, rm);
//            }
//        }
//        return null;
    }

    /**
     * Get the line of intersection (the shortest line) between {@code this} and
     * {@code l}.
     *
     * @param ls The line segment to get the line of intersection with.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The line of intersection between {@code this} and {@code l}.
     */
    public V2D_LineSegment getLineOfIntersect(V2D_LineSegment ls, int oom, 
            RoundingMode rm) {
        V2D_FiniteGeometry ilsl = getIntersect(ls, oom, rm);
        if (ilsl == null) {
            V2D_Point lsp = ls.getP();
            V2D_Point lsq = ls.getQ(oom, rm);
            V2D_Point tp = getP();
            V2D_Point tq = getQ(oom, rm);
            // Get the line of intersection between this and ls.l
            V2D_LineSegment tloi = getLineOfIntersect(ls.l, oom, rm);
            V2D_LineSegment lsloi = ls.getLineOfIntersect(l, oom, rm);
            if (tloi == null) {
                if (lsloi == null) {
                    // The line segments are collinear, but not intersecting.
                    BigRational lspd2tp = lsp.getDistanceSquared(tp, oom, rm);
                    BigRational lspd2tq = lsp.getDistanceSquared(tq, oom, rm);
                    BigRational lsqd2tp = lsq.getDistanceSquared(tp, oom, rm);
                    BigRational lsqd2tq = lsq.getDistanceSquared(tq, oom, rm);
                    if (lspd2tp.compareTo(lspd2tq) == -1) {
                        if (lspd2tp.compareTo(lsqd2tp) == -1) {
                            if (lspd2tp.compareTo(lsqd2tq) == -1) {
                                return new V2D_LineSegment(lsp, tp, oom, rm);
                            } else {
                                return new V2D_LineSegment(lsq, tq, oom, rm);
                            }
                        } else {
                            if (lsqd2tp.compareTo(lsqd2tq) == -1) {
                                return new V2D_LineSegment(lsq, tp, oom, rm);
                            } else {
                                return new V2D_LineSegment(lsq, tq, oom, rm);
                            }
                        }
                    } else {
                        if (lspd2tq.compareTo(lsqd2tp) == -1) {
                            if (lspd2tq.compareTo(lsqd2tq) == -1) {
                                return new V2D_LineSegment(lsp, tq, oom, rm);
                            } else {
                                return new V2D_LineSegment(lsq, tq, oom, rm);
                            }
                        } else {
                            if (lsqd2tp.compareTo(lsqd2tq) == -1) {
                                return new V2D_LineSegment(lsq, tp, oom, rm);
                            } else {
                                return new V2D_LineSegment(lsq, tq, oom, rm);
                            }
                        }
                    }
                }
                V2D_Point tip;
                V2D_Point lsloiq = lsloi.getQ(oom, rm);
                // Is the intersection point on this within the line segment?
                // Can use isAligned to do this more clearly?
                V2D_Vector pv = l.v.rotate90();
                V2D_Line tppl = new V2D_Line(tp, pv);
                V2D_Line tqpl = new V2D_Line(tq, pv);
                if (tppl.isOnSameSide(lsloiq, tq, oom, rm)) {
                    if (tqpl.isOnSameSide(lsloiq, tp, oom, rm)) {
                        /**
                         * The line of intersection connects in this, so lsloiq
                         * is one of the points wanted.
                         */
                        return lsloi;
                    } else {
                        // tq is closest.
                        tip = tq;
                    }
                } else {
                    // tp is closest.
                    tip = tp;
                }
                return new V2D_LineSegment(tip, lsloi.getP(), oom, rm);
            } else {
                V2D_Point tloip = tloi.getP(); // This is the end of the line segment on this.
                V2D_Point tloiq = tloi.getQ(oom, rm);
                if (lsloi == null) {
                    V2D_Point lsip;
                    // Is the intersection point on ls within the line segment?
                    // Can use isAligned to do this more clearly?
                    V2D_Vector pv = ls.l.v.rotate90();
                    V2D_Line lsppl = new V2D_Line(lsp, pv);
                    if (lsppl.isOnSameSide(tloiq, lsq, oom, rm)) {
                        V2D_Line lsqpl = new V2D_Line(lsq, pv);
                        if (lsqpl.isOnSameSide(tloiq, lsp, oom, rm)) {
                            /**
                             * The line of intersection connects in this, so
                             * lsloiq is one of the points wanted.
                             */
                            lsip = tloiq;
                        } else {
                            // lsq is closest.
                            lsip = lsq;
                        }
                    } else {
                        // lsp is closest.
                        lsip = lsp;
                    }
                    return new V2D_LineSegment(tloip, lsip, oom, rm);
                    //return new V2D_LineSegment(tq, lsip);
                    //return new V2D_LineSegment(tp, lsip);
                    //return new V2D_LineSegment(tloiq, getNearestPoint(this, tloiq));
                } else {
                    // tloip is on
                    if (isAligned(tloip, oom, rm)) {
                        return new V2D_LineSegment(tloip, getNearestPoint(ls, tloip, oom, rm), oom, rm);
                    } else {
                        return new V2D_LineSegment(
                                getNearestPoint(this, tloip, oom, rm),
                                getNearestPoint(ls, tloip, oom, rm), oom, rm);
                    }
                }
            }
        } else {
            return null;
        }
//        V2D_FiniteGeometry ilsl = getIntersect(ls, oom, rm);
//        if (ilsl == null) {
//            V2D_Point lsp = ls.getP();
//            V2D_Point lsq = ls.getQ();
//            V2D_Point tp = getP();
//            V2D_Point tq = getQ();
//            // Get the line of intersection between this and ls.l
//            V2D_LineSegment tloi = getLineOfIntersect(ls.l, oom, rm);
//            V2D_LineSegment lsloi = ls.getLineOfIntersect(l, oom, rm);
//            if (tloi == null) {
//                V2D_Point tip;
//                V2D_Point lsloiq = lsloi.getQ();
//                // Is the intersection point on this within the line segment?
//                // Can use isAligned to do this more clearly?
//                V2D_Plane tppl = new V2D_Plane(tp, l.v);
//                V2D_Plane tqpl = new V2D_Plane(tq, l.v);
//                if (tppl.isOnSameSide(lsloiq, tq, oom, rm)) {
//                    if (tqpl.isOnSameSide(lsloiq, tp, oom, rm)) {
//                        /**
//                         * The line of intersection connects in this, so lsloiq
//                         * is one of the points wanted.
//                         */
//                        return lsloi;
//                    } else {
//                        // tq is closest.
//                        tip = tq;
//                    }
//                } else {
//                    // tp is closest.
//                    tip = tp;
//                }
//                return new V2D_LineSegment(tip, lsloiq, oom, rm);
//            } else {
//                V2D_Point tloip = tloi.getP(); // This is the end of the line segment on this.
//                V2D_Point tloiq = tloi.getQ();
//                if (lsloi == null) {
//                    V2D_Point lsip;
//                    // Is the intersection point on ls within the line segment?
//                    // Can use isAligned to do this more clearly?
//                    V2D_Plane lsppl = new V2D_Plane(lsp, ls.l.v);
//                    if (lsppl.isOnSameSide(tloiq, lsq, oom, rm)) {
//                        V2D_Plane lsqpl = new V2D_Plane(lsq, ls.l.v);
//                        if (lsqpl.isOnSameSide(tloiq, lsp, oom, rm)) {
//                            /**
//                             * The line of intersection connects in this, so
//                             * lsloiq is one of the points wanted.
//                             */
//                            lsip = tloiq;
//                        } else {
//                            // lsq is closest.
//                            lsip = lsq;
//                        }
//                    } else {
//                        // lsp is closest.
//                        lsip = lsp;
//                    }
//                    return new V2D_LineSegment(tloip, lsip, oom, rm);
//                    //return new V2D_LineSegment(tq, lsip, oom, rm);
//                    //return new V2D_LineSegment(tp, lsip, oom, rm);
//                    //return new V2D_LineSegment(tloiq, getNearestPoint(this, tloiq, oom, rm), oom, rm);
//                } else {
//                    // tloip is on
//                    if (isAligned(tloip, oom, rm)) {
//                        return new V2D_LineSegment(tloip, getNearestPoint(ls, tloip, oom, rm), oom, rm);
//                    } else {
//                        return new V2D_LineSegment(
//                                getNearestPoint(this, tloip, oom, rm),
//                                getNearestPoint(ls, tloip, oom, rm), oom, rm);
//                    }
//                }
//            }
//        } else {
//            return null;
//        }
//        return null;
    }

    /**
     * Useful for intersection tests.
     *
     * @return The line with a point at l.getP() and vector at 90 degrees to the
     * line vector.
     */
    public V2D_Line getPL() {
        if (pl == null) {
            pl = new V2D_Line(l.getP(), l.v.rotate90());
        }
        return pl;
    }

    /**
     * Useful for intersection tests.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The line with a point at l.getQ() and vector at 90 degrees to the
     * line vector.
     */
    public V2D_Line getQL(int oom, RoundingMode rm) {
        if (ql == null) {
            ql = new V2D_Line(getQ(oom, rm), l.v.rotate90());
        }
        return ql;
    }

    /**
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param pt Is on the line of {@code l}, but not on {@code l}.
     * @return The nearest point on {@code l} to {@code pv}.
     */
    protected static V2D_Point getNearestPoint(V2D_LineSegment l, V2D_Point pt, int oom, RoundingMode rm) {
        V2D_Point lp = l.getP();
        V2D_Point lq = l.getQ(oom, rm);
        BigRational dlpp = lp.getDistanceSquared(pt, oom, rm);
        BigRational dlqp = lq.getDistanceSquared(pt, oom, rm);
        if (dlpp.compareTo(dlqp) == -1) {
            return lp;
        } else {
            return lq;
        }
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public BigRational getDistance(V2D_Line l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom, rm), oom, rm)
                .getSqrt(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code l}.
     */
    public BigRational getDistanceSquared(V2D_Line l, int oom, RoundingMode rm) {
        if (getIntersect(l, oom, rm) != null) {
            return BigRational.ZERO;
        }
        BigRational pd = l.getDistanceSquared(getP(), oom, rm);
        BigRational qd = l.getDistanceSquared(getQ(oom, rm), oom, rm);
        return BigRational.min(pd, qd);
    }

    @Override
    public V2D_LineSegment rotate(V2D_Point pt, BigRational theta, Math_BigDecimal bd,
            int oom, RoundingMode rm) {
        theta = Math_AngleBigRational.normalise(theta, bd, oom, rm);
        if (theta.compareTo(BigRational.ZERO) == 0d) {
            return new V2D_LineSegment(this);
        } else {
            return rotateN(pt, theta, bd, oom, rm);
        }
    }

    @Override
    public V2D_LineSegment rotateN(V2D_Point pt, BigRational theta,
            Math_BigDecimal bd, int oom, RoundingMode rm) {
        return new V2D_LineSegment(
                getP().rotateN(pt, theta, bd, oom, rm),
                getQ(oom, rm).rotateN(pt, theta, bd, oom, rm), oom, rm);
    }

//    /**
//     * Clips this using pl and returns the part that is on the same side as pt.
//     *
//     * @param pl The plane that clips.
//     * @param pt A point that is used to return the side of the clipped line
//     * segment.
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode for any rounding.
//     * @return null, the whole or a part of this.
//     */
//    public V2D_FiniteGeometry clip(V2D_Line pl, V2D_Point pt, int oom, RoundingMode rm) {
//        V2D_FiniteGeometry i = getIntersect(pl, oom, rm);
//        V2D_Point tp = getP();
//        if (i == null) {
//            if (pl.isOnSameSide(tp, pt, oom, rm)) {
//                return this;
//            } else {
//                return null;
//            }
//        } else if (i instanceof V2D_Point ip) {
//            if (pl.isOnSameSide(tp, pt, oom, rm)) {
//                return V2D_LineSegment.getGeometry(ip, tp, oom, rm);
//            } else {
//                V2D_Point tq = this.getQ(oom, rm);
//                return V2D_LineSegment.getGeometry(ip, tq, oom, rm);
//            }
//        } else {
//            return this;
//        }
//    }
    
   

}
