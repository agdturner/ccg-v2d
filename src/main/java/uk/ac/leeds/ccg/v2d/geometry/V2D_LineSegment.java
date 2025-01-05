/*
 * Copyright 2019 Andy Turner, University of Leeds.
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
import uk.ac.leeds.ccg.v2d.geometry.envelope.V2D_Envelope;

/**
 * Class for a line segment in 2D. The line segment is defined by the two points
 * of the {@link V2D_Line}. The line segment is between and includes the points.
 * The direction of the line is given by the vector and the point designation.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class V2D_LineSegment extends V2D_Line implements V2D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the envelope.
     */
    private V2D_Envelope e;

    /**
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     */
    public V2D_LineSegment(V2D_Point p, V2D_Point q) {
        super(p, q);
    }

    /**
     * @param p What {@link #p} is set to.
     * @param v What {@link #v} is set to.
     */
    public V2D_LineSegment(V2D_Point p, V2D_Vector v) {
        super(p, v);
    }

    /**
     * @param l Vector_LineSegment2D
     */
    public V2D_LineSegment(V2D_LineSegment l) {
        this(l.p, l.q);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V2D_LineSegment) {
            return equals((V2D_LineSegment) o);
        }
        return false;
    }

    /**
     * @param l The line segment to test if it is equal to {@code this}.
     * @return {@code true} iff {@code this} is equal to {@code l}
     */
    public boolean equals(V2D_LineSegment l) {
        return (p.equals(l.p) && q.equals(l.q)) || (p.equals(l.q) && q.equals(l.p));
    }

    /**
     *
     * @param l The line segment to test if it is equal to {@code this}.
     * @return {@code true} iff {@code this} is equal to {@code l} ignoring the
     * direction of {@link #v}.
     */
    public boolean equalsIgnoreDirection(V2D_LineSegment l) {
        if (equals(l)) {
            return true;
        } else {
            return p.equals(l.q) && q.equals(l.p);
        }
    }

    /**
     * @param oom The order of magnitude for the precision.
     * @return The length of this as a BigDecimal
     */
    public BigRational getLength(int oom, RoundingMode rm) {
        return p.getDistance(q, oom, rm);
    }

    /**
     * @return {@code new V2D_Envelope(p, q)}
     */
    @Override
    public V2D_Envelope getEnvelope() {
        if (e == null) {
            e = new V2D_Envelope(p, q);
        }
        return e;
    }

    @Override
    public boolean isIntersectedBy(V2D_LineSegment l, boolean b) {
        boolean ei = getEnvelope().isIntersectedBy(l.getEnvelope());
        if (ei) {
            return super.isIntersectedBy(l);
        }
        return false;
    }

    /**
     * @param p The point to test if it is on this.
     * @return {@code true} if {@code p} is on this.
     */
    @Override
    public boolean isIntersectedBy(V2D_Point p) {
        if (getEnvelope().isIntersectedBy(p)) {
            return super.isIntersectedBy(p);
        }
        return false;
    }

    /**
     * Calculate and return the intersection between this and {@code l}.
     *
     * @param l The line segment to intersect with.
     * @return {@code null} if this does not intersect {@code l}; the point of
     * intersection if this intersects {@code l} at a point; and the line
     * segment if this intersects {@code l} over a line segment.
     */
    public V2D_Geometry getIntersection(V2D_LineSegment l) {
        // Special cases
        if (equalsIgnoreDirection(l)) {
            // The two lines are the same.
            return l;
        }
        if (!(getEnvelope().isIntersectedBy(l)
                && l.getEnvelope().isIntersectedBy(this))) {
            return null;
        }
        BigRational x2minusx1 = q.x.subtract(p.x);
        BigRational y2minusy1 = q.y.subtract(p.y);
        BigRational x4minusx3 = l.q.x.subtract(l.p.x);
        BigRational y4minusy3 = l.q.y.subtract(l.p.y);
        BigRational denominator = (y4minusy3.multiply(x2minusx1))
                .subtract(x4minusx3.multiply(y2minusy1));
        boolean parallel = false;
        if (denominator.compareTo(BigRational.ZERO) == 0) {
            //System.out.println("parallel lines");
            parallel = true;
        }
        BigRational y1minusy3 = p.y.subtract(l.p.y);
        BigRational x1minusx3 = p.x.subtract(l.p.x);
        BigRational uamultiplicand = (x4minusx3.multiply(y1minusy3))
                .subtract(y4minusy3.multiply(x1minusx3));
        BigRational ubmultiplicand = (x2minusx1.multiply(y1minusy3))
                .subtract(y2minusy1.multiply(x1minusx3));
        if (uamultiplicand.compareTo(BigRational.ZERO) == 0
                && ubmultiplicand.compareTo(BigRational.ZERO) == 0
                && parallel) {
            //System.out.println("lines coincident");
            //V2D_LineSegment ot = this.getOrderedLineSegment2D();
            //V2D_LineSegment oa = l.getOrderedLineSegment2D();
            boolean ts = l.isIntersectedBy(p);
            boolean te = l.isIntersectedBy(q);
            boolean as = isIntersectedBy(l.p);
            boolean ae = isIntersectedBy(l.q);
            if (ts) {
                if (te) {
                    if (as) {
                        if (ae) {
                            return new V2D_LineSegment(l);
                        } else {
                            return new V2D_LineSegment(p, q);
                        }
                    } else {
                        if (ae) {
                            return new V2D_LineSegment(p, l.q);
                        } else {
                            return new V2D_LineSegment(this);
                        }
                    }
                } else {
                    if (as) {
                        if (ae) {
                            return new V2D_LineSegment(l);
                        } else {
                            return new V2D_Point(p);
                        }
                    } else {
                        System.out.println("Wierd...");
                        return null;
                    }
                }
            } else {
                if (te) {
                    if (as) {
                        if (ae) {
                            return new V2D_LineSegment(l.p, l.q);
                        } else {
                            return new V2D_LineSegment(l.p, q);
                        }
                    } else {
                        System.out.println("Wierd...");
                        return null;
                    }
                } else {
                    return new V2D_LineSegment(l);
                }
            }
        }
        if (parallel) {
            return null;
        }
        BigRational ua = uamultiplicand.divide(denominator);
        if (ua.compareTo(BigRational.ONE) != 1
                && ua.compareTo(BigRational.ZERO) != -1) {
            BigRational dx = p.x.add(ua.multiply(x2minusx1));
            BigRational dy = p.y.add(ua.multiply(y2minusy1));
            return new V2D_Point(dx, dy);
        } else {
            BigRational ub = ubmultiplicand.divide(denominator);
            if (ub.compareTo(BigRational.ONE) != 1
                    && ub.compareTo(BigRational.ZERO) != -1) {
                BigRational dx = l.p.x.add(ub.multiply(x4minusx3));
                BigRational dy = l.p.y.add(ub.multiply(y4minusy3));
                return new V2D_Point(dx, dy);
            }
        }
        return null;
    }

//    /**
//     * @param precision The precision for the {@link MathContext}.
//     * @param rm The rounding mode for the {@link MathContext}.
//     * @return The angle to the x axis.
//     */
//    public BigDecimal getAngleToX(int precision, RoundingMode rm) {
//        return getAngleToX(new MathContext(precision, rm));
//    }
//
//    /**
//     * @param mc The MathContext.
//     * @return The angle to the x axis.
//     */
//    public BigDecimal getAngleToX(MathContext mc) {
//        return BigDecimalMath.atan2(v.dy.toBigDecimal(), v.dx.toBigDecimal(), mc);
//    }
//
//    /**
//     * @param precision The precision for the {@link MathContext}.
//     * @param rm The rounding mode for the {@link MathContext}.
//     * @return The angle to the y axis.
//     */
//    public BigDecimal getAngleToY(int precision, RoundingMode rm) {
//        return getAngleToY(new MathContext(precision, rm));
//    }
//
//    /**
//     * @param mc The MathContext.
//     * @return The angle to the x axis.
//     */
//    public BigDecimal getAngleToY(MathContext mc) {
//        return BigDecimalMath.atan2(v.dx.toBigDecimal(), v.dy.toBigDecimal(), mc);
//    }
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}. If they overlap in a line return the part that
     * overlaps (the order of points is not defined). If they intersect at a
     * point, the point is returned. {@code null} is returned if the two line
     * segments do not intersect.
     *
     * @param l The line to get intersection with this.
     * @param b To distinguish this method from
     * {@link #getIntersection(uk.ac.leeds.ccg.v2d.geometry.V2D_Line)}.
     * @return The intersection between {@code this} and {@code l}.
     */
    @Override
    public V2D_Geometry getIntersection(V2D_LineSegment l, boolean b) {
        return getIntersection(this, l);
    }

//    /**
//     * Implementation of line intercept math by Paul Bourke
//     * http://paulbourke.net/geometry/pointlineplane/
//     */
//    public static V2D_Geometry getIntersection(V2D_LineSegment l0, V2D_LineSegment l1) {
//        if (l0.v.isZeroVector()) {
//            if (l1.isIntersectedBy(l0.p)) {
//                return l0.p;
//            }
//        }
//        if (l1.v.isZeroVector()) {
//            if (l0.isIntersectedBy(l1.p)) {
//                return l1.p;
//            }
//        }
//        BigRational x1 = l0.p.x;
//        BigRational y1 = l0.p.y;
//        BigRational x2 = l0.q.x;
//        BigRational y2 = l0.q.y;
//        BigRational x3 = l1.p.x;
//        BigRational y3 = l1.p.y;
//        BigRational x4 = l1.q.x;
//        BigRational y4 = l1.q.y;
//
//        //denominator = ((y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1))
//        BigRational denominator = ((y4.subtract(y3)).multiply(x2.subtract(x1))
//                .multiply(x4.subtract(x3)).multiply(y2.subtract(y1)));
//        if (denominator.compareTo(BigRational.ZERO) == 0) {
//            // Lines are parallel
//            return null;
//        }
//        BigRational ua = (((x4.subtract(x3)).multiply(y1.subtract(y3)))
//                .subtract((y4.subtract(y3)).multiply(x1.subtract(x3))))
//                .divide(denominator);
//        BigRational ub = (((x2.subtract(x1)).multiply(y1.subtract(y3)))
//                .subtract((y2.subtract(y1)).multiply(x1.subtract(x3))))
//                .divide(denominator);
//        if (ua.compareTo(BigRational.ZERO) == -1
//                || ua.compareTo(BigRational.ONE) == 1
//                || ub.compareTo(BigRational.ZERO) == -1
//                || ub.compareTo(BigRational.ONE) == 1) {
//            return null;
//        }
//        BigRational x = x1.add(ua.multiply(x2.subtract(x1)));
//        BigRational y = y1.add(ua.multiply(y2.subtract(y1)));
//        return new V2D_Point(x, y);
//    }

    /**
     * A utility method for calculating and returning the intersection between 
     * {@code l0} and {@code l1}
     * @param l0 Line to intersect with {@code l1}.
     * @param l1 Line to intersect with {@code l0}.
     * @return The intersection between {@code l0} and {@code l1}.
     */
    public static V2D_Geometry getIntersection(V2D_LineSegment l0, V2D_LineSegment l1) {
        V2D_Envelope ren = l0.getEnvelope().getIntersection(l1.getEnvelope());
        if (ren == null) {
            return null;
        }
        V2D_Geometry li = V2D_Line.getIntersection(l0, l1);
        if (li == null) {
            return null;
        }
        if (li instanceof V2D_Point) {
            return li;
        }
        /**
         * Check the type of intersection. {@code
         * 1)   p ---------- q
         *         l.p ---------- l.q
         * 2)   p ------------------------ q
         *         l.p ---------- l.q
         * 3)        p ---------- q
         *    l.p ------------------------ l.q
         * 4)        p ---------- q
         *    l.p ---------- l.q
         * 5)   q ---------- p
         *         l.p ---------- l.q
         * 6)   q ------------------------ p
         *         l.p ---------- l.q
         * 7)        q ---------- p
         *    l.p ------------------------ l.q
         * 8)        q ---------- p
         *    l.p ---------- l.q
         * 9)   p ---------- q
         *         l.q ---------- l.p
         * 10)   p ------------------------ q
         *         l.q ---------- l.p
         * 11)       p ---------- q
         *    l.q ------------------------ l.p
         * 12)       p ---------- q
         *    l.q ---------- l.p
         * 13)  q ---------- p
         *         l.q ---------- l.p
         * 14)  q ------------------------ p
         *         l.q ---------- l.p
         * 15)       q ---------- p
         *    l.q ------------------------ l.p
         * 16)       q ---------- p
         *    l.q ---------- l.p
         * }
         */
        if (l0.isIntersectedBy(l1.p)) {
            // Cases 1, 2, 5, 6, 14, 16
            if (l0.isIntersectedBy(l1.q)) {
                // Cases 2, 6, 14
                /**
                 * The line segments are effectively the same although the start
                 * and end points may be opposite.
                 */
                return l1;
            } else {
                // Cases 1, 5, 16
                if (l1.isIntersectedBy(l0.p)) {
                    // Cases 5
                    return new V2D_LineSegment(l1.p, l0.p);
                } else {
                    // Cases 1, 16
                    return new V2D_LineSegment(l1.p, l0.q);
                }
            }
        } else {
            // Cases 3, 4, 7, 8, 9, 10, 11, 12, 13, 15
            if (l0.isIntersectedBy(l1.q)) {
                // Cases 4, 8, 9, 10, 11
                if (l1.isIntersectedBy(l0.p)) {
                    // Cases 4, 11, 13
                    if (l1.isIntersectedBy(l0.q)) {
                        // Cases 11
                        return l0;
                    } else {
                        // Cases 4, 13
                        return new V2D_LineSegment(l0.p, l1.q);
                    }
                } else {
                    // Cases 8, 9, 10
                    if (l1.isIntersectedBy(l0.q)) {
                        // Cases 8, 9
                        return new V2D_LineSegment(l0.q, l1.q);
                    } else {
                        // Cases 10                      
                        return l1;
                    }
                }
            } else {
                // Cases 3, 7, 12, 15
                if (l1.isIntersectedBy(l0.p)) {
                    // Cases 3, 12, 15
                    if (l1.isIntersectedBy(l0.q)) {
                        // Cases 3, 15
                        return l0;
                    } else {
                        // Cases 12                 
                        return new V2D_LineSegment(l0.p, l1.p);
                    }
                } else {
                    // Cases 7
                    return l0;
                }
            }
        }
    }
    
    @Override
    public boolean isEnvelopeIntersectedBy(V2D_Line l) {
        return getEnvelope().isIntersectedBy(l);
    }

}
