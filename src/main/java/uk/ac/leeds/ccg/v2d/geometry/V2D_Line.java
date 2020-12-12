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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import uk.ac.leeds.ccg.math.Math_BigDecimal;
import uk.ac.leeds.ccg.v2d.projection.V2D_Project;

/**
 * 2D representation of an infinite length line. The line passes through the
 * points {@link #p} and {@link #q} and is travelling in the direction 
 * {@link #v}.
 * <ul>
 * <li>Vector Form
 * <ul>
 * <li>(x,y) = (p.x,p.y) + t(v.dx,v.dy)</li>
 * </ul>
 * <li>Parametric Form (where t describes a particular point on the line)
 * <ul>
 * <li>x = p.x + t(v.dx)</li>
 * <li>y = p.y + t(v.dy)</li>
 * </ul>
 * <li>Symmetric Form (assume v.dx and v.dy are nonzero)
 * <ul>
 * <li>(x−p.x)/v.dx = (y−p.y)/v.dy</li>
 * </ul></li>
 * </ul>
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class V2D_Line extends V2D_Geometry {

    private static final long serialVersionUID = 1L;

    /**
     * A point defining the line.
     */
    public V2D_Point p;

    /**
     * A point defining the line.
     */
    public V2D_Point q;

    /**
     * The direction vector from {@link #p} in the direction of {@link #q}.
     */
    public V2D_Vector v;

    /**
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param checkCoincidence If true a check for coincidence of {@link #p} and
     * {@link #q} is done otherwise it is not done (as in the case when the line
     * is a line segment defining the edge of an envelope which is allowed to be
     * a point).
     * @throws RuntimeException If {@code p} and {@code q} are coincident and
     * {@code checkCoincidence} is {@code true}.
     */
    public V2D_Line(V2D_Point p, V2D_Point q, boolean checkCoincidence) {
        if (checkCoincidence) {
            if (p.equals(q)) {
                throw new RuntimeException("The inputs p and q are the same point "
                        + "and do not define a line.");
            }
        }
        init(p, q);
    }

    private void init(V2D_Point p, V2D_Point q) {
        this.p = new V2D_Point(p);
        this.q = new V2D_Point(q);
        v = new V2D_Vector(q.x.subtract(p.x), q.y.subtract(p.y));
    }

    /**
     * {@code p} should not be equal to {@code q} unless the line is a line
     * segment which is part of an envelope. If unsure the use
     * {@link #V2D_Line(V2D_Point, V2D_Point, boolean)}.
     *
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     */
    public V2D_Line(V2D_Point p, V2D_Point q) {
        init(p, q);
    }

    /**
     * @param p What {@link #p} is set to.
     * @param v What {@link #v} is set to.
     */
    public V2D_Line(V2D_Point p, V2D_Vector v) {
        this.p = new V2D_Point(p);
        this.v = v;
        q = V2D_Project.project(p, v);
    }
    
    /**
     * @param l Vector_LineSegment3D
     */
    public V2D_Line(V2D_Line l) {
        this.p = l.p;
        this.q = l.q;
        this.v = l.v;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[p=" + p.toString()
                + ", q=" + q.toString() + ", v=" + v.toString() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V2D_Line) {
            return equals((V2D_Line) o);
        }
        return false;
    }
    
    public boolean equals(V2D_Line l) {
        return this.isIntersectedBy(l.p) && this.isIntersectedBy(l.q);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.p);
        hash = 17 * hash + Objects.hashCode(this.v);
        return hash;
    }

    /**
     * @param pt A point to test for intersection.
     * @return {@code true} if p is on the line.
     */
    public boolean isIntersectedBy(V2D_Point pt) {
        return ((p.x.subtract(pt.x)).multiply(q.y.subtract(pt.y))).subtract((p.y.subtract(pt.y)).multiply(q.x.subtract(pt.x))).isZero();
        //V2D_Vector ppt = new V2D_Vector(pt.x.subtract(p.x), pt.y.subtract(p.y));
        //V2D_Vector cp = v.getCrossProduct(ppt);
        //return cp.dx.isZero() && cp.dy.isZero();
    }

    /**
     * @param l The line to test this with to see if they are parallel.
     * @return {@code true} If this and {@code l} are parallel.
     */
    public boolean isParallel(V2D_Line l) {
        V2D_Vector cp = v.getCrossProduct(l.v);
        return cp.dx.isZero() && cp.dy.isZero();
    }

    /**
     * This computes the intersection and tests if it is {@code null}
     *
     * @param l The line to test if it isIntersectedBy with this.
     * @return {@code true} If this and {@code l} intersect.
     */
    public boolean isIntersectedBy(V2D_Line l) {
        return getIntersection(l) != null;
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}.
     *
     * @param l The line to get intersection with this.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V2D_Geometry getIntersection(V2D_Line l) {
        return getIntersection(this, l);
    }

    /**
     * 
     * @param l0 Line to intersect with {@code l1}.
     * @param l1 Line to intersect with {@code l0}.
     * @return {@code null} if {@code l0} and {@code l1} are parallel. If 
     * {@code l0} and {@code l1} effectively define the same line, then 
     * {@code l0} is returned. Otherwise the point of intersection between 
     * {@code l0} and {@code l1} is returned.
     */
    public static V2D_Geometry getIntersection(V2D_Line l0, V2D_Line l1) {
        // Check the points.
        if (l0.isIntersectedBy(l1.p)) {
            if (l0.isIntersectedBy(l1.q)) {
                return l0; // The lines are coincident.
            } else {
                return l1.p;
            }
        } else {
            if (l0.isIntersectedBy(l1.q)) {
                return l1.q;
            }
        }
        if (l1.isIntersectedBy(l0.p)) {
            return l0.p;
        }
        if (l1.isIntersectedBy(l0.q)) {
            return l0.q;
        }
        // Case of parallel and non equal lines.
        if (l0.isParallel(l1)) {
            return null;
        }
        /**
         * Find the intersection point where the two equations of the lines
         * meet. (x(t)−x0)/a = (y(t)−y0)/b
         */
        // (x−p.x)/a = (y−p.y)/b
        // t = (v.dx - x0)/p.x;
        // t = (v.dy - y0)/p.y;
        // x(t) = t(dx)+q.x
        // y(t) = t(dy)+q.y
        // 1: t(v.dx)+q.x = s(l.v.dx)+l.q.x
        // 2: t(v.dy)+q.y = s(l.v.dy)+l.q.y
        // Let:
        // l.v.dx = k; l.v.dy = l
        // From 1:
        // t = ((s(k)+l.q.x-q.x)/(v.dx))
        // Let:
        // l.q.x-q.x = e; l.q.y-q.y = f
        // v.dx = a; v.dy = b
        // t = (sk+e)/a
        // Sub into 2:
        // ((sk+e)/a)b+q.y = sl + l.q.y
        // skb/a +eb/a - s1 = l.q.y - q.y
        // s(kb/a - l) = l.q.y - q.y - eb/a
        // s = (l.q.y - q.y - eb/a) / ((kb/a) - l)
        BigRational t;
        if (l0.v.dx.isZero()) {
            // Line has constant x                    
            BigRational den = l1.v.dy.multiply(l0.v.dx).divide(l0.v.dy)
                    .subtract(l1.v.dx);
            BigRational num = l1.q.x.subtract(l0.q.x).subtract(l1.q.y
                    .subtract(l0.q.y).multiply(l0.v.dx).divide(l0.v.dy));
            t = num.divide(den).multiply(l1.v.dy).add(l1.q.y)
                    .subtract(l0.q.y).divide(l0.v.dy);
        } else {
            //dy dz nonzero
            BigRational den = l1.v.dx.multiply(l0.v.dy).divide(l0.v.dx)
                    .subtract(l1.v.dy);
            BigRational num = l1.q.y.subtract(l0.q.y).subtract(l1.q.x
                    .subtract(l0.q.x).multiply(l0.v.dy).divide(l0.v.dx));
            t = num.divide(den).multiply(l1.v.dx).add(l1.q.x)
                    .subtract(l0.q.x).divide(l0.v.dx);
        }
        return new V2D_Point(
                t.multiply(l0.v.dx).add(l0.q.x),
                t.multiply(l0.v.dy).add(l0.q.y));
    }

    /**
     * @param l A line.
     * @param scale The scale for the precision of the result.
     * @param rm The RoundingMode for any rounding.
     * @return The shortest distance between this and {@code l}.
     */
    public BigDecimal getDistance(V2D_Line l, int scale, RoundingMode rm) {
        // The coordinates of points along the lines are given by:
        // p = <p.x, p.y, p.z> + t<v.dx, v.dy, v.dz>
        // lp = <l.p.x, l.p.y, l.p.z> + t<l.v.dx, l.v.dy, l.v.dz>
        // p2 = r2+t2e2
        // The line connecting the closest points has direction vector:
        // n = v.l.v
        if (this.isParallel(l)) {
            V2D_Line ol = new V2D_Line(p, v.getOrthogonalVector());
            return p.getDistance((V2D_Point) ol.getIntersection(l), scale, rm);
//        V2D_Vector n = v.getCrossProduct(l.v);
//        v.getDotProduct(l.v);
//        // d = n.(p−l.p)/||n||
//        V2D_Vector p_sub_lp = new V2D_Vector(p.x.subtract(l.p.x),
//                p.y.subtract(l.p.y));
//        BigRational m = BigRational.valueOf(n.getMagnitude(scale, rm));
//        BigRational d = n.getDotProduct(p_sub_lp).divide(m);
//        return Math_BigDecimal.roundIfNecessary(d.toBigDecimal(), scale, rm);
        }
        return BigDecimal.ZERO;
    }
}
