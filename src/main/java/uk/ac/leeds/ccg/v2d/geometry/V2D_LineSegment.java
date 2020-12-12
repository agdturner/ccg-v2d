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

import ch.obermuhlner.math.big.BigDecimalMath;
import ch.obermuhlner.math.big.BigRational;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import uk.ac.leeds.ccg.v2d.geometry.envelope.V2D_Envelope;
//import org.ojalgo.function.implementation.BigFunction;
//import org.ojalgo.constant.BigMath;

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
        return p.equals(l.p) && q.equals(l.q);
    }

    /**
     *
     * @param l The line segment to test if it is equal to {@code this}.
     * @return {@code true} iff {@code this} is equal to {@code l} ignoring the
     * direction of the vector
     */
    public boolean equalsIgnoreDirection(V2D_LineSegment l) {
        if (equals(l)) {
            return true;
        } else {
            return p.equals(l.q) && q.equals(l.p);
        }
    }

    /**
     * @param dp The decimal place precision.
     * @param rm The RoundingMode.
     * @return The length of this as a BigDecimal
     */
    public BigDecimal getLength(int dp, RoundingMode rm) {
        return p.getDistance(q, dp, rm);
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

    /**
     * @param l A line to test for intersection within the specified tolerance.
     * @return true if p is within t of this given scale.
     */
    public boolean isIntersectedBy(V2D_LineSegment l) {
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
}
