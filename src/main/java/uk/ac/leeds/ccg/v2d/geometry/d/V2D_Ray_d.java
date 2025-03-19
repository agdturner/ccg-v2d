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

import uk.ac.leeds.ccg.math.geometry.Math_AngleDouble;
import uk.ac.leeds.ccg.v2d.core.d.V2D_Environment_d;

/**
 * 3D representation of a ray - like a line, but one that starts at a point
 * continues infinitely in only one direction. The ray begins at the point of
 * {@link #l} and goes in the direction given by the vector of {@link #l}. The
 * "*" denotes a point in 3D and the ray is shown as a linear feature of "e"
 * symbols in the following depiction: {@code
 *                                     
 *                         y           
 *                         +                           * pl=<x0,y0,z0>
 *                         |                          e
 *                         |                         e
 *                         |                        e
 *                         |                       e
 *                         |                      e
 *                         |                    e
 *                         |                   e
 *                      y0-|                  e
 *                         |                 e
 *                         |          x1    e
 * - ----------------------|-----------|---e---|---- + x
 *                         |              e   x0
 *                         |-y1          e
 *                         |           e
 *                         |          e
 *                         |         e
 *                         |        e
 *                         |       * v=(dx,dy,dz)
 *                         |      e
 *                         |     e
 *                         -    e
 *                         y   e
 * }
 *
 * @author Andy Turner
 * @version 2.0
 */
public class V2D_Ray_d extends V2D_Geometry_d  {

    private static final long serialVersionUID = 1L;

    /**
     * The line of this ray.
     */
    public V2D_Line_d l;
    
    /**
     * For storing the line perpendicular to this ray that goes through the 
     * start point.
     */
    public V2D_Line_d pl;

    /**
     * Create a new instance.
     *
     * @param r What {@code this} is created from.
     */
    public V2D_Ray_d(V2D_Ray_d r) {
        super(r.env);
        l = new V2D_Line_d(r.l);
    }

    /**
     * Create a new instance.
     *
     * @param p What {@code this} is created from.
     * @param v What {@code this} is created from.
     */
    public V2D_Ray_d(V2D_Point_d p, V2D_Vector_d v) {
        super(p.env, p.offset);
        l = new V2D_Line_d(p, v);
    }

    /**
     * Create a new instance. {@link #offset} is set to
     * {@link V2D_Vector_d#ZERO}.
     *
     * @param env What {@link #env} is set to.
     * @param p What {@code this} is created from.
     * @param q What {@code this} is created from.
     */
    public V2D_Ray_d(V2D_Environment_d env, V2D_Vector_d p, 
            V2D_Vector_d q) {
        this(env, V2D_Vector_d.ZERO, p, q);
    }

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param p What {@link #l} point is set to.
     * @param q What {@link #l} vector is set from.
     */
    public V2D_Ray_d(V2D_Environment_d env, V2D_Vector_d offset, 
            V2D_Vector_d p, V2D_Vector_d q) {
        super(env, offset);
        l = new V2D_Line_d(env, offset, p, q);
    }

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     */
    public V2D_Ray_d(V2D_Line_d l) {
        super(l.env, l.offset);
        this.l = new V2D_Line_d(l);
    }

    /**
     * Create a new instance.
     *
     * @param p What {@link #offset} and {@link #l} point are set from.
     * @param q What {@link #l} vector is set from.
     */
    public V2D_Ray_d(V2D_Point_d p, V2D_Point_d q) {
        super(p.env, p.offset);
        this.l = new V2D_Line_d(p, q);
    }

    /**
     * @param r The ray to test if it is the same as {@code this}.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return {@code true} iff {@code r} is the same as {@code this}.
     */
    public boolean equals(V2D_Ray_d r, double epsilon) {
        if (l.getP().equals(r.l.getP())) {
            if (l.v.getDirection() == r.l.v.getDirection()) {
                return l.v.isScalarMultiple(epsilon, r.l.v);
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
     * @return the line perpendicular to the ray passing through l.p.
     */
    public V2D_Line_d getPl() {
        if (pl == null) {
            V2D_Point_d pt = l.getP();
            pl = new V2D_Line_d(pt, l.v.rotate90());
        }
        return pl;
    }

    /**
     * @param pt A point to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return {@code true} if {@code this} is intersected by {@code pl}.
     */
    public boolean intersects(V2D_Point_d pt, double epsilon) {
        if (pt.equals(l.getP())) {
            return true;
        }
        if (l.intersects(epsilon, pt)) {
//            V2D_Point poi = l.getPointOfIntersect(pt);
//            V2D_Ray r = new V2D_Ray(e, getP(), poi.getVector());
            pl = getPl();
//            V2D_Ray r = new V2D_Ray(l.getP(), pt);
//            return r.l.getV().getDirection() == l.getV().getDirection();
            return pl.isOnSameSide(pt, this.l.getQ(), epsilon);
        }
//        boolean isPossibleIntersection = isPossibleIntersection(pt);
//        if (isPossibleIntersection) {
//            Math_BigRationalSqrt a = pl.getDistance(this.pl);
//            if (a.getX().isZero()) {
//                return true;
//            }
//            Math_BigRationalSqrt b = pl.getDistance(this.qv);
//            if (b.getX().isZero()) {
//                return true;
//            }
//            Math_BigRationalSqrt l = this.pl.getDistance(this.qv);
//            if (a.add(b, oom).compareTo(l) != 1) {
//                return true;
//            }
//        }
        return false;
    }

//    /**
//     * Intersects {@code this} with {@code t}. {@code null} is returned if there
//     * is no intersection.
//     *
//     * @param t The triangle to get the geometrical intersection with this.
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode if rounding is needed.
//     * @return The intersection between {@code this} and {@code t}.
//     */
//    @Override
//    public V2D_FiniteGeometry getIntersect(V2D_Triangle t, int oom, RoundingMode rm) {
//        V2D_Geometry g = getIntersect(t.pl);
//        if (g == null) {
//            return null;
//        } else {
//            if (g instanceof V2D_Point pt) {
//                if (t.isAligned(pt)) {
//                    return pt;
//                } else {
//                    return null;
//                }
//            } else {
//                V2D_Geometry g2 = t.getIntersect(l);
//                if (g2 instanceof V2D_Point g2p) {
//                    //if (intersects(g2p)) {
//                    if (t.isAligned(g2p)) {
//                        return g2p;
//                    } else {
//                        return null;
//                    }
//                } else {
//                    return getIntersect((V2D_LineSegment) g2);
//                }
//            }
//        }
//    }
    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}. If they overlap in a line return the part that
     * overlaps (the order of points is not defined). If they intersect at a
     * point, the point is returned. {@code null} is returned if there is no
     * intersection.
     *
     * @param l The line to get the geometrical intersection with this.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V2D_Geometry_d getIntersect(V2D_Line_d l, double epsilon) {
        // Check if infinite lines intersect.
        V2D_Geometry_d g = this.l.getIntersect(epsilon, l);
        if (g == null) {
            // There is no intersection.
            return g;
        }
        /**
         * If lines intersects at a point, then check this point is on this.
         */
        if (g instanceof V2D_Point_d pt) {
            if (isAligned(pt, epsilon)) {
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
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return The intersection between {@code this} and {@code r}.
     */
    public V2D_Geometry_d getIntersect(V2D_Ray_d r, double epsilon) {
        V2D_Geometry_d rtl = r.getIntersect(l, epsilon);
        if (rtl == null) {
            return null;
        } else if (rtl instanceof V2D_Point_d pt) {
            pl = getPl();
            if (pl.isOnSameSide(pt, l.getQ(), epsilon)) {
                return pt;
            } else {
                return null;
            }
        } else {
            // Then rtl is an instance of V2D_Ray.
            V2D_Geometry_d grl = V2D_Ray_d.this.getIntersect(r.l, epsilon);
            if (grl instanceof V2D_Point_d) {
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
                V2D_Point_d tp = l.getP();
                V2D_Point_d rp = r.l.getP();
                pl = getPl();
                V2D_Line_d rpl = r.getPl();
                if (pl.isOnSameSide(rp, l.getQ(), epsilon)) {
                    if (rpl.isOnSameSide(tp, r.l.getQ(), epsilon)) {
                        if (tp.equals(rp)) {
                            return tp;
                        }
                        return new V2D_LineSegment_d(rp, tp);
                    } else {
                        return new V2D_Ray_d(rp, l.v);
                    }
                } else {
                    if (rpl.isOnSameSide(tp, r.l.getQ(), epsilon)) {
                        return new V2D_Ray_d(tp, l.v);
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
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V2D_FiniteGeometry_d getIntersect(V2D_LineSegment_d ls,
            double epsilon) {
        V2D_Geometry_d g = V2D_Ray_d.this.getIntersect(ls.l, epsilon);
        if (g == null) {
            return null;
        } else if (g instanceof V2D_Point_d pt) {
            if (isAligned(pt, epsilon)) {
                if (ls.isAligned(pt, epsilon)) {
                    return pt;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            V2D_Point_d rp = l.getP();
            //V2D_Point rq = l.getQ();
            V2D_Point_d lsp = ls.getP();
            V2D_Point_d lsq = ls.getQ();
            if (isAligned(lsp, epsilon)) {
                if (isAligned(lsq, epsilon)) {
                    return ls;
                } else {
                    return V2D_LineSegment_d.getGeometry(rp, lsp, epsilon);
                }
            } else {
                if (isAligned(lsq, epsilon)) {
                    return V2D_LineSegment_d.getGeometry(rp, lsq, epsilon);
                } else {
                    if (intersects(lsp, epsilon)) {
                        return lsp;
                    }
                    if (intersects(lsq, epsilon)) {
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
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return {@code true} If pt is in line with this.
     */
    public boolean isAligned(V2D_Point_d pt, double epsilon) {
        return getPl().isOnSameSide(pt, l.getQ(), epsilon);
    }

    /**
     * Translate (move relative to the origin).
     *
     * @param v The vector to translate.
     */
    @Override
    public void translate(V2D_Vector_d v) {
        l.translate(v);
        if (pl != null) {
            pl.translate(v);
        }
    }
    
    @Override
    public V2D_Ray_d rotate(V2D_Point_d pt, double theta) {
        theta = Math_AngleDouble.normalise(theta);
        if (theta == 0d) {
            return new V2D_Ray_d(this);
        } else {
            return rotateN(pt, theta);
        }
    }
    
    @Override
    public V2D_Ray_d rotateN(V2D_Point_d pt, double theta) {
        return new V2D_Ray_d(l.rotateN(pt, theta));
    }
}
