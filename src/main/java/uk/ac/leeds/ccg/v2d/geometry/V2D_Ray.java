/*
 * Copyright 2021 Andy Turner, University of Leeds.
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
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;
import uk.ac.leeds.ccg.v2d.core.V2D_Environment;

/**
 * 2D representation of a ray - like a line, but one that starts at a point
 * continues infinitely in only one direction. The ray begins at the point of
 * {@link #l} and goes in the direction given by the vector of {@link #l}.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_Ray extends V2D_Geometry {

    private static final long serialVersionUID = 1L;

    /**
     * The line of this ray.
     */
    public V2D_Line l;
    
    /**
     * For storing the line through the point P of {@link #l} at 90 degrees.
     */
    private V2D_Line pl;
    
    /**
     * Create a new instance.
     *
     * @param r What {@code this} is created from.
     */
    public V2D_Ray(V2D_Ray r) {
        super(r.env);
        l = new V2D_Line(r.l);
    }

    /**
     * Create a new instance.
     *
     * @param p What {@code this} is created from.
     * @param v What {@code this} is created from.
     */
    public V2D_Ray(V2D_Point p, V2D_Vector v) {
        super(p.env, p.offset);
        l = new V2D_Line(p, v);
    }

    /**
     * Create a new instance. {@link #offset} is set to {@link V2D_Vector#ZERO}.
     *
     * @param env What {@link #env} is set to.
     * @param p What {@code this} is created from.
     * @param q What {@code this} is created from.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V2D_Ray(V2D_Environment env, V2D_Vector p, V2D_Vector q, int oom, RoundingMode rm) {
        this(env, V2D_Vector.ZERO, p, q, oom, rm);
    }

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param p What {@link #l} point is set to.
     * @param q What {@link #l} vector is set from.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V2D_Ray(V2D_Environment env, V2D_Vector offset, V2D_Vector p,
            V2D_Vector q, int oom, RoundingMode rm) {
        super(env, offset);
        l = new V2D_Line(env, offset, p, q, oom, rm);
    }

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     */
    public V2D_Ray(V2D_Line l) {
        super(l.env, l.offset);
        this.l = new V2D_Line(l);
    }

    /**
     * Create a new instance.
     *
     * @param p What {@link #offset} and {@link #l} point are set from.
     * @param q What {@link #l} vector is set from.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V2D_Ray(V2D_Point p, V2D_Point q, int oom, RoundingMode rm) {
        super(p.env, p.offset);
        this.l = new V2D_Line(p, q, oom, rm);
    }

    /**
     * @param r The ray to test if it is the same as {@code this}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code r} is the same as {@code this}.
     */
    public boolean equals(V2D_Ray r, int oom, RoundingMode rm) {
        if (l.getP().equals(r.l.getP(), oom, rm)) {
            if (l.v.getDirection() == r.l.v.getDirection()) {
                return l.v.isScalarMultiple(r.l.v, oom, rm);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        //return toString("");
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
        return pad + l.toStringFields(pad);
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    @Override
    protected String toStringFieldsSimple(String pad) {
        return pad + l.toStringFieldsSimple(pad);
    }
    
    /**
     * For initialising and getting {@link #pl}.
     * @return {@link #pl} initialised first if {@code null}.
     */
    public V2D_Line getPl() {
        if (pl == null) {
            pl = new V2D_Line(l.getP(), l.v.rotate90());
        }
        return pl;
    }

    /**
     * @param pt A point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} if {@code this} is intersected by {@code pl}.
     */
    public boolean intersects(V2D_Point pt, int oom, RoundingMode rm) {
        if (pt.equals(l.getP(), oom, rm)) {
            return true;
        }
        if (l.intersects(pt, oom, rm)) {
            pl = getPl();
            return pl.isOnSameSide(pt, this.l.getQ(oom, rm), oom, rm);
        }
        return false;
    }

    /**
     * Intersects a ray with a plane. {@code null} is returned if there is no
     * intersection, {@code this} is returned if the ray is on the plane.
     * Otherwise a point is returned.
     *
     * It is possible to distinguish a ray intersection with a plane (ray-plane)
     * and a plane intersection with a ray (plane-ray). In some cases the two
     * are the same, but due to coordinate number imprecision, sometimes an
     * intersection point cannot be found that is both on the ray and on the
     * plane. For a ray-plane intersection we can force the point to be on the
     * ray and either choose a point on or before the plane, or on or after the
     * plane.
     *
     * For the plane-ray intersection we can force the point to be on the plane
     * and choose the vague direction of the point from the intersection using
     * the orientation of the ray relative to the plane (and where the ray is
     * perpendicular to the plane, we can choose the direction relative to the
     * orientation of the axes and origin).
     *
     * Support ray-plane intersection to choose on or before, or on or after?
     *
     * @param l The line to get the geometrical intersection with this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The intersection between {@code this} and {@code pl}.
     */
    public V2D_Geometry getIntersection(V2D_Line l, int oom, RoundingMode rm) {
        // Check if infinite lines intersect.
        V2D_Geometry g = this.l.getIntersection(l, oom, rm);
        if (g == null) {
            // There is no intersection.
            return g;
        }
        /**
         * If lines intersects at a point, then check this point is on this.
         */
        if (g instanceof V2D_Point pt) {
            if (isAligned(pt, oom, rm)) {
                return g;
            } else {
                return null;
            }
        }
        return this;
    }

    /**
     * Intersects {@code this} with {@code r}. If they are equivalent then
     * return {@code this}. If they overlap then return the part that overlaps -
     * which is either a point, a line segment, or a ray. {@code null} is
     * returned if {@code this} and {@code r} do not intersect.
     *
     * @param r The line to get intersection with this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The intersection between {@code this} and {@code r}.
     */
    public V2D_Geometry getIntersection(V2D_Ray r, int oom, RoundingMode rm) {
        V2D_Geometry rtl = r.getIntersection(l, oom, rm);
        if (rtl == null) {
            return null;
        } else if (rtl instanceof V2D_Point pt) {
            pl = getPl();
            if (pl.isOnSameSide(pt, l.getQ(oom, rm), oom, rm)) {
                return pt;
            } else {
                return null;
            }
        } else {
            // Then rtl is an instance of V2D_Ray.
            V2D_Geometry grl = getIntersection(r.l, oom, rm);
            if (grl instanceof V2D_Point) {
                return grl;
            } else {
                /**
                 * Then grl is an instance of a V2D_Ray. The rays may point
                 * along the same line. If they point in the same direction,
                 * then they intersect and the start of the ray is the start
                 * point of the ray that intersects both rays. If they point in
                 * opposite directions, then they do not intersect unless the
                 * points they start at intersect with the other ray and in this
                 * instance, the intersection is the line segment between them.
                 */
                V2D_Point tp = l.getP();
                V2D_Point rp = r.l.getP();
                pl = getPl();
                V2D_Line rpl = r.getPl();
                if (pl.isOnSameSide(rp, l.getQ(oom, rm), oom, rm)) {
                    if (rpl.isOnSameSide(tp, r.l.getQ(oom, rm), oom, rm)) {
                        if (tp.equals(rp, oom, rm)) {
                            return tp;
                        }
                        return new V2D_LineSegment(rp, tp, oom, rm);
                    } else {
                        return new V2D_Ray(rp, l.v);
                    }
                } else {
                    if (rpl.isOnSameSide(tp, r.l.getQ(oom, rm), oom, rm)) {
                        return new V2D_Ray(tp, l.v);
                    } else {
                        return null;
                    }
                }
            }
        }
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}. If they overlap then return the part that overlaps -
     * which is either a point, a line segment, or a ray. {@code null} is
     * returned if {@code this} and {@code l} do not intersect.
     *
     * @param ls The line to get intersection with this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V2D_FiniteGeometry getIntersection(V2D_LineSegment ls, int oom, RoundingMode rm) {
        V2D_Geometry g = getIntersection(ls.l, oom, rm);
        if (g == null) {
            return null;
        } else if (g instanceof V2D_Point pt) {
            if (isAligned(pt, oom, rm)) {
                if (ls.isBetween(pt, oom, rm)) {
                    return pt;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            V2D_Point rp = l.getP();
            //V2D_Point rq = l.getQ(oom, rm);
            V2D_Point lsp = ls.getP();
            V2D_Point lsq = ls.getQ(oom, rm);
            if (isAligned(lsp, oom, rm)) {
                if (isAligned(lsq, oom, rm)) {
                    return ls;
                } else {
                    return V2D_LineSegment.getGeometry(rp, lsp, oom, rm);
                }
            } else {
                if (isAligned(lsq, oom, rm)) {
                    return V2D_LineSegment.getGeometry(rp, lsq, oom, rm);
                } else {
                    if (intersects(lsp, oom, rm)) {
                        return lsp;
                    }
                    if (intersects(lsq, oom, rm)) {
                        return lsq;
                    }
                    return null;
                }
            }
        }
    }
    
    /**
     * Calculates and returns if pt is in the direction of the ray (i.e. the
     * same side of the ray start point plane as another point on the ray).
     * 
     * @param pt The point. 
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} If pt is in line with this.  
     */
    public boolean isAligned(V2D_Point pt, int oom, RoundingMode rm) {
        return getPl().isOnSameSide(pt, l.getQ(oom, rm), oom, rm);
    }

    /**
     * Translate (move relative to the origin).
     *
     * @param v The vector to translate.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    @Override
    public void translate(V2D_Vector v, int oom, RoundingMode rm) {
        l.translate(v, oom, rm);
        if (pl != null) {
            pl.translate(v, oom, rm);
        }
    }

    @Override
    public V2D_Ray rotate(V2D_Point pt, BigRational theta, Math_BigDecimal bd,
            int oom, RoundingMode rm) {
        theta = Math_AngleBigRational.normalise(theta, bd, oom, rm);
        if (theta.isZero()) {
           return new V2D_Ray(this);
        } else {
            return rotateN(pt, theta, bd, oom, rm);
        }
    }
   
    @Override
    public V2D_Ray rotateN(V2D_Point pt, BigRational theta, Math_BigDecimal bd, 
            int oom, RoundingMode rm) {
        return new V2D_Ray(l.rotateN(pt, theta, bd, oom, rm));
    }
}
