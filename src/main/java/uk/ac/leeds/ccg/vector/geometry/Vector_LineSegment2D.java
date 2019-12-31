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
package uk.ac.leeds.ccg.vector.geometry;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import uk.ac.leeds.ccg.grids.d2.Grids_Dimensions;
import uk.ac.leeds.ccg.grids.d2.grid.Grids_GridNumber;
import uk.ac.leeds.ccg.vector.core.Vector_Environment;
//import org.ojalgo.function.implementation.BigFunction;
//import org.ojalgo.constant.BigMath;

/**
 * Class for a line segment in 2D represented by two Point2D instances, one is
 * designated a start point and the other an end point. In this way a line
 * segment explicitly has a direction. Two Vector_LineSegment2D instances are
 * regarded as equal iff their start and end points are the same.
 */
public class Vector_LineSegment2D
        extends Vector_AbstractGeometry2D
        implements Comparable, Serializable {

    public Vector_Point2D Start;
    public Vector_Point2D End;

    /**
     * Creates a default Vector_LineSegment2D with: Start = null; End = null;
     *
     * @param ve
     */
    public Vector_LineSegment2D(
            Vector_Environment ve) {
        super(ve);
    }

    /**
     * Creates a Vector_LineSegment2D with: this.Start = new
     * Point2D(_StartPoint); this.End = new Point2D(_EndPoint);
     * setDecimalPlacePrecision(Math.max( Start.getDecimalPlacePrecision(),
     * End.getDecimalPlacePrecision()));
     *
     * @param start Vector_Point2D
     * @param end Vector_Point2D
     */
    public Vector_LineSegment2D(
            Vector_Point2D start,
            Vector_Point2D end) {
        super(start.e);
        Start = new Vector_Point2D(start);
        End = new Vector_Point2D(end);
        DecimalPlacePrecision = Math.max(
                Start.getDecimalPlacePrecision(),
                End.getDecimalPlacePrecision());
    }

    /**
     * Creates a Vector_LineSegment2D with: _StartPoint = _StartPoint; _EndPoint
     * = _EndPoint; DecimalPlacePrecision_Integer =
     * DecimalPlacePrecision_Integer; RoundingMode = RoundingMode.
     *
     * @param start Vector_Point2D
     * @param end Vector_Point2D
     * @param decimalPlacePrecision int
     */
    public Vector_LineSegment2D(
            Vector_Point2D start,
            Vector_Point2D end,
            int decimalPlacePrecision) {
        super(start.e);
        Start = new Vector_Point2D(start, decimalPlacePrecision);
        End = new Vector_Point2D(end, decimalPlacePrecision);
        DecimalPlacePrecision = decimalPlacePrecision;
    }

    /**
     *
     * @param l Vector_LineSegment2D
     */
    public Vector_LineSegment2D(
            Vector_LineSegment2D l) {
        super(l.e);
        this.Start = new Vector_Point2D(l.Start);
        this.End = new Vector_Point2D(l.End);
        this.DecimalPlacePrecision = l.DecimalPlacePrecision;
    }

    @Override
    public String toString() {
        return "LineSegment2D(" + super.toString()
                + "Start(" + Start.toString() + ")"
                + "End(" + End.toString() + "))";
    }

    /**
     * Default is 1. Calls compareTo on the _StartPoint.
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Object o) {
        if (o instanceof Vector_LineSegment2D) {
            Vector_LineSegment2D a_LineSegment2D = (Vector_LineSegment2D) o;
            int compareStart = Start.compareTo(a_LineSegment2D.Start);
            if (compareStart == 0) {
                return Start.compareTo(a_LineSegment2D.Start);
            } else {
                return compareStart;
            }
        }
        return 1;
    }

    /**
     * @param a_DecimalPlacePrecision The decimal place precision to be used for
     * this.
     * @return The length of this as a BigDecimal
     */
    public BigDecimal getLength(int a_DecimalPlacePrecision) {
        BigDecimal length;
        length = Start.getDistance(End,
                a_DecimalPlacePrecision);
        return length;
    }

    /**
     * TODO: Correct with regard DecimalPlacePrecision_Integer!?
     *
     * @return Vector_Envelope2D
     */
    @Override
    public Vector_Envelope2D getEnvelope2D() {
        return new Vector_Envelope2D(
                Start,
                End);
    }

    /**
     *
     * @param g
     * @param l
     * @param tollerance
     * @param dp
     * @param hoome
     * @return true iff l intersects g
     */
    public static boolean getIntersects(Grids_GridNumber g,
            Vector_LineSegment2D l, BigDecimal tollerance, int dp,
            boolean hoome) {
        Grids_Dimensions dimensions;
        dimensions = g.getDimensions();
        return getIntersects(
                dimensions.getXMin(),
                dimensions.getYMin(),
                dimensions.getXMax(),
                dimensions.getYMax(),
                l,
                tollerance,
                dp,
                hoome);
    }

    /**
     * @param xmin
     * @param ymin
     * @param xmax
     * @param ymax
     * @param l
     * @param tollerance
     * @param a_DecimalPlacePrecision
     * @param handleOutOfMemoryError
     * @return true iff l intersects the envelope defined by minx, miny, maxx,
     * maxy.
     */
    public static boolean getIntersects(
            BigDecimal xmin,
            BigDecimal ymin,
            BigDecimal xmax,
            BigDecimal ymax,
            Vector_LineSegment2D l,
            BigDecimal tollerance,
            int a_DecimalPlacePrecision,
            boolean handleOutOfMemoryError) {
        if (l.Start.x.compareTo(xmin) != -1
                && l.Start.x.compareTo(xmax) != 1
                && l.Start.y.compareTo(ymin) != -1
                && l.Start.y.compareTo(ymax) != 1) {
            return true;
        }
        if (l.End.x.compareTo(xmin) != -1
                && l.End.x.compareTo(xmax) != 1
                && l.End.y.compareTo(ymin) != -1
                && l.End.y.compareTo(ymax) != 1) {
            return true;
        }
        Vector_Point2D bottomLeft;
        bottomLeft = new Vector_Point2D(
                l.e,
                xmin,
                ymin);
        Vector_Point2D bottomRight;
        bottomRight = new Vector_Point2D(
                l.e, xmax, ymin);
        // Check bottom
        Vector_LineSegment2D section;
        section = new Vector_LineSegment2D(
                bottomLeft, bottomRight, a_DecimalPlacePrecision);
        if (l.getIntersects(section, tollerance, a_DecimalPlacePrecision, handleOutOfMemoryError)) {
            return true;
        }
        Vector_Point2D topRight;
        topRight = new Vector_Point2D(
                l.e,
                xmax,
                ymax);
        // Check right
        section = new Vector_LineSegment2D(
                bottomRight, topRight, a_DecimalPlacePrecision);
        if (l.getIntersects(section, tollerance, a_DecimalPlacePrecision, handleOutOfMemoryError)) {
            return true;
        }
        Vector_Point2D topLeft;
        topLeft = new Vector_Point2D(
                l.e, xmin, ymax);
        // Check left
        section = new Vector_LineSegment2D(
                bottomLeft, topLeft, a_DecimalPlacePrecision);
        if (l.getIntersects(section, tollerance, a_DecimalPlacePrecision, handleOutOfMemoryError)) {
            return true;
        }
        // Check top
        Vector_LineSegment2D top;
        section = new Vector_LineSegment2D(
                topLeft, topRight, a_DecimalPlacePrecision);
        return l.getIntersects(section, tollerance, a_DecimalPlacePrecision, handleOutOfMemoryError);
    }

    public static boolean getIntersects(
            Vector_LineSegment2D l,
            Vector_LineSegment2D[] lines,
            BigDecimal tollerance,
            int decimalPlacePrecision,
            boolean handleOutOfMemoryError) {
        for (int i = 0; i < lines.length; i++) {
            boolean intersects;
            intersects = l.getIntersects(
                    lines[i],
                    tollerance,
                    decimalPlacePrecision,
                    handleOutOfMemoryError);
            if (intersects) {
                return true;
            }
        }
        return false;
    }

    /**
     * Intersection done by first seeing if Envelope intersects....
     *
     * @param l A Vector_LineSegment2D to test.
     * @param tollerance
     * @param decimalPlacePrecision The decimal place precision to be used for
     * this.
     * @param handleOutOfMemoryError
     *
     * @return True iff a_LineSegment2D getIntersects this.
     */
    public boolean getIntersects(
            Vector_LineSegment2D l,
            BigDecimal tollerance,
            int decimalPlacePrecision,
            boolean handleOutOfMemoryError) {
        Vector_AbstractGeometry2D intersection;
        intersection = getIntersection(
                l,
                tollerance,
                decimalPlacePrecision);
        return intersection != null;
    }

    /**
     * For optimisation reasons, intersection done by first seeing if there is
     * Envelope intersection...
     *
     * @param l Vector_LineSegment2D to test for intersection.
     * @param ignoreThisStartPoint2D if true then if
     * this.Start.getIntersects(a_LineSegment2D) return false
     * @param tollerance
     * @param decimalPlacePrecision The decimal place precision to be used for
     * this.
     * @param handleOutOfMemoryError
     * @return true iff a_LineSegment2D getIntersects this.
     */
    public boolean getIntersects(
            Vector_LineSegment2D l,
            boolean ignoreThisStartPoint2D,
            BigDecimal tollerance,
            int decimalPlacePrecision,
            boolean handleOutOfMemoryError) {
        if (ignoreThisStartPoint2D) {
            if (this.Start.getIntersects(
                    l,
                    decimalPlacePrecision)) {
                return false;
            } else {
                return getIntersects(
                        l,
                        tollerance,
                        decimalPlacePrecision,
                        handleOutOfMemoryError);
            }
        } else {
            return getIntersects(
                    l,
                    tollerance,
                    decimalPlacePrecision,
                    handleOutOfMemoryError);
        }
    }

    /**
     * Intersection done by calculating angle or gradient of the line and
     * comparing this with that of a_Point.
     *
     * @param a_Point2D A Vector_Point2D to test for intersection.
     * @param a_DecimalPlacePrecision The decimal place precision to be used for
     * this.
     * @return true iff a_Point2D getIntersects this.
     */
    public boolean getIntersects(
            Vector_Point2D a_Point2D,
            int a_DecimalPlacePrecision) {
        if (getEnvelope2D().getIntersects(a_Point2D) == false) {
            return false;
        }
        if (Start.y.compareTo(End.y) == -1) {
            // StartPoint is Below EndPoint
            if (Start.x.compareTo(End.x) == -1) {
                // StartPoint is Left of EndPoint
                return isOnGradient(
                        a_Point2D,
                        a_DecimalPlacePrecision);
            } else {
                if (Start.x.compareTo(End.x) == 0) {
                    // StartPoint has same x as EndPoint
                    return true;
                } else {
                    // StartPoint is Right of EndPoint
                    return isOnGradient(
                            a_Point2D,
                            a_DecimalPlacePrecision);
                }
            }
        } else {
            if (Start.y.compareTo(End.y) == 0) {
                // StartPoint has same y as EndPoint
                return true;
            } else {
                // StartPoint is Above EndPoint
                if (Start.x.compareTo(End.x) == -1) {
                    // StartPoint is Left of EndPoint
                    return isOnGradient(
                            a_Point2D,
                            a_DecimalPlacePrecision);
                } else {
                    if (Start.x.compareTo(End.x) == 0) {
                        // StartPoint has same x as EndPoint
                        return true;
                    } else {
                        // StartPoint is Right of EndPoint
                        return isOnGradient(
                                a_Point2D,
                                a_DecimalPlacePrecision);
                    }
                }
            }
        }
    }

    protected BigDecimal getGradient(
            int a_DecimalPlacePrecision) {
        return Start.getGradient(End,
                a_DecimalPlacePrecision);
    }

    protected boolean isOnGradient(
            Vector_Point2D a_Point2D,
            int a_DecimalPlacePrecision) {
        //Point2D b_Point2D = a_Point2D.
        //MathContext a_MathContext = new MathContext(DecimalPlacePrecision_Integer + 5);
        BigDecimal xDiff0 = End.x.subtract(Start.x);
        BigDecimal yDiff0 = End.y.subtract(Start.y);
        BigDecimal xDiff1 = a_Point2D.x.subtract(Start.x);
        BigDecimal yDiff1 = a_Point2D.y.subtract(Start.y);
        BigDecimal gradient0;
        if (yDiff0.compareTo(BigDecimal.ZERO) == 0) {
            return yDiff1.compareTo(BigDecimal.ZERO) == 0;
        } else {
            gradient0 = xDiff0.divide(
                    yDiff0,
                    a_DecimalPlacePrecision + 2, // + 2 sufficient?
                    RoundingMode.CEILING);
        }
        BigDecimal gradient1;
        if (yDiff1.compareTo(BigDecimal.ZERO) == 0) {
            return false;
        } else {
            gradient1 = xDiff1.divide(
                    yDiff1,
                    a_DecimalPlacePrecision + 2, // + 2 sufficient?
                    RoundingMode.CEILING);
        }
        return gradient0.compareTo(gradient1) == 0;
    }

    public Vector_LineSegment2D getOrderedLineSegment2D() {
        if (Start.y.compareTo(End.y) == -1) {
            return new Vector_LineSegment2D(this);
        } else {
            if (Start.y.compareTo(End.y) == 0) {
                if (Start.x.compareTo(End.x) != 1) {
                    return new Vector_LineSegment2D(this);
                }
            }
        }
        return new Vector_LineSegment2D(
                this.End,
                this.Start,
                this.getDecimalPlacePrecision());
    }

    /**
     * Intersection method adapted from
     * <a href="http://local.wasp.uwa.edu.au/~pbourke/geometry/lineline2d/">http://local.wasp.uwa.edu.au/~pbourke/geometry/lineline2d/</a>
     *
     * @param l Vector_LineSegment2D
     * @param tollerance
     * @param decimalPlacePrecision Precision...
     * @return null if this does not intersect a_LineSegment2D; a Point2D if
     * this getIntersects a_LineSegment2D at a point; and, a
     * Vector_LineSegment2D if this getIntersects a_LineSegment2D in a line.
     * TODO: Not sure about using Rounding Modes here, it might be best to
     * handle arithmetic exceptions and provide another method into which a
     * MathContext can be passed...
     */
    public Vector_AbstractGeometry2D getIntersection(
            Vector_LineSegment2D l,
            BigDecimal tollerance,
            int decimalPlacePrecision) {
        // Special cases
        if (this.Start.y.compareTo(l.Start.y) == 1
                && this.Start.y.compareTo(l.End.y) == 1
                && this.End.y.compareTo(l.Start.y) == 1
                && this.End.y.compareTo(l.End.y) == 1) {
            return null;
        }
        if (this.Start.x.compareTo(l.Start.x) == 1
                && this.Start.x.compareTo(l.End.x) == 1
                && this.End.x.compareTo(l.Start.x) == 1
                && this.End.x.compareTo(l.End.x) == 1) {
            return null;
        }
        if (this.Start.y.compareTo(l.Start.y) == -1
                && this.Start.y.compareTo(l.End.y) == -1
                && this.End.y.compareTo(l.Start.y) == -1
                && this.End.y.compareTo(l.End.y) == -1) {
            return null;
        }
        if (this.Start.x.compareTo(l.Start.x) == -1
                && this.Start.x.compareTo(l.End.x) == -1
                && this.End.x.compareTo(l.Start.x) == -1
                && this.End.x.compareTo(l.End.x) == -1) {
            return null;
        }
        //MathContext a_MathContext = new MathContext(this.DecimalPlacePrecision_Integer + 100);
        BigDecimal x2minusx1 = End.x.subtract(Start.x);
        BigDecimal y2minusy1 = End.y.subtract(Start.y);
        BigDecimal x4minusx3 = l.End.x.subtract(l.Start.x);
        BigDecimal y4minusy3 = l.End.y.subtract(l.Start.y);
        BigDecimal denominator = (y4minusy3.multiply(x2minusx1)).subtract(x4minusx3.multiply(y2minusy1));
        boolean parallel = false;
        // Not sure about use of RoundingMode here!!
        if (denominator.setScale(decimalPlacePrecision, RoundingMode.FLOOR).compareTo(BigDecimal.ZERO) == 0) {
            //System.out.println("parallel lines");
            parallel = true;
        }
        BigDecimal y1minusy3 = Start.y.subtract(l.Start.y);
        BigDecimal x1minusx3 = Start.x.subtract(l.Start.x);
        BigDecimal uamultiplicand = (x4minusx3.multiply(y1minusy3)).subtract(y4minusy3.multiply(x1minusx3));
        BigDecimal ubmultiplicand = (x2minusx1.multiply(y1minusy3)).subtract(y2minusy1.multiply(x1minusx3));
        if (uamultiplicand.setScale(decimalPlacePrecision, RoundingMode.FLOOR).compareTo(BigDecimal.ZERO) == 0
                && ubmultiplicand.setScale(decimalPlacePrecision, RoundingMode.FLOOR).compareTo(BigDecimal.ZERO) == 0
                && parallel) {
            //System.out.println("lines coincident");
            Vector_LineSegment2D ot = getOrderedLineSegment2D();
            Vector_LineSegment2D oa = l.getOrderedLineSegment2D();
            boolean ts = ot.Start.getIntersects(
                    oa,
                    decimalPlacePrecision);
            boolean te = ot.End.getIntersects(
                    oa,
                    decimalPlacePrecision);
            boolean as = oa.Start.getIntersects(
                    ot,
                    decimalPlacePrecision);
            boolean ae = oa.End.getIntersects(
                    ot,
                    decimalPlacePrecision);
            if (ts) {
                if (te) {
                    if (as) {
                        if (ae) {
                            return new Vector_LineSegment2D(l);
                        } else {
                            return new Vector_LineSegment2D(
                                    ot.Start,
                                    ot.End);
                        }
                    } else {
                        if (ae) {
                            return new Vector_LineSegment2D(
                                    ot.Start,
                                    oa.End);
                        } else {
                            return new Vector_LineSegment2D(
                                    this);
                        }
                    }
                } else {
                    if (as) {
                        if (ae) {
                            return new Vector_LineSegment2D(l);
                        } else {
                            return new Vector_Point2D(ot.Start);
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
                            return new Vector_LineSegment2D(
                                    oa.Start,
                                    oa.End);
                        } else {
                            return new Vector_LineSegment2D(
                                    oa.Start,
                                    ot.End);
                        }
                    } else {
                        System.out.println("Wierd...");
                        return null;
                    }
                } else {
                    return new Vector_LineSegment2D(l);
                }
            }
        }
        if (parallel) {
            return null;
        }
        BigDecimal ua = uamultiplicand.divide(
                denominator,
                decimalPlacePrecision,
                RoundingMode.CEILING);
        if (ua.compareTo(BigDecimal.ONE) != 1
                && ua.compareTo(BigDecimal.ZERO) != -1) {
            BigDecimal intersectx = Start.x.add(
                    ua.multiply(x2minusx1));
            BigDecimal intersecty = Start.y.add(
                    ua.multiply(y2minusy1));
            BigDecimal deltaXEndX;
            deltaXEndX = intersectx.subtract(l.End.x);
            BigDecimal deltaXStartX;
            deltaXStartX = intersectx.subtract(l.Start.x);
            BigDecimal deltaYEndY;
            deltaYEndY = intersecty.subtract(l.End.y);
            BigDecimal deltaYStartY;
            deltaYStartY = intersecty.subtract(l.Start.y);
            if (((deltaXEndX.compareTo(tollerance) == 1 && deltaXEndX.compareTo(tollerance.negate()) == 1)
                    && (deltaXStartX.compareTo(tollerance) == 1 && deltaXStartX.compareTo(tollerance.negate()) == 1))
                    || ((deltaXEndX.compareTo(tollerance) == -1 && deltaXEndX.compareTo(tollerance.negate()) == -1)
                    && (deltaXStartX.compareTo(tollerance) == -1) && deltaXStartX.compareTo(tollerance.negate()) == -1)
                    || ((deltaYEndY.compareTo(tollerance) == 1 && deltaYEndY.compareTo(tollerance.negate()) == 1)
                    && deltaYStartY.compareTo(tollerance) == 1 && deltaYStartY.compareTo(tollerance.negate()) == 1)
                    || ((deltaYEndY.compareTo(tollerance) == -1 && deltaYEndY.compareTo(tollerance.negate()) == -1)
                    && (deltaYStartY.compareTo(tollerance) == -1 && deltaYStartY.compareTo(tollerance.negate()) == -1))) {
                return null;
            }
//            if ((intersectx1.compareTo(a_LineSegment2D.End.x) == 1
//                    && intersectx1.compareTo(a_LineSegment2D.Start.x) == 1)
//                    || (intersectx1.compareTo(a_LineSegment2D.End.x) == -1
//                    && intersectx1.compareTo(a_LineSegment2D.Start.x) == -1)
//                    || (intersecty1.compareTo(a_LineSegment2D.End.y) == 1
//                    && intersecty1.compareTo(a_LineSegment2D.Start.y) == 1)
//                    || (intersecty1.compareTo(a_LineSegment2D.End.y) == -1
//                    && intersecty1.compareTo(a_LineSegment2D.Start.y) == -1)) {
//                return null;
//            }
//            BigDecimal ub = ubmultiplicand.divide(
//                    denominator,
//                    a_DecimalPlacePrecision,
//                RoundingMode.HALF_UP);
            return new Vector_Point2D(
                    e,
                    intersectx,
                    intersecty,
                    decimalPlacePrecision);
        } else {
            BigDecimal ub = ubmultiplicand.divide(
                    denominator,
                    decimalPlacePrecision,
                    RoundingMode.CEILING);
            if (ub.compareTo(BigDecimal.ONE) != 1
                    && ub.compareTo(BigDecimal.ZERO) != -1) {
                BigDecimal intersectx = l.Start.x.add(
                        ub.multiply(x4minusx3));
                BigDecimal intersecty = l.Start.y.add(
                        ub.multiply(y4minusy3));
                BigDecimal deltaXEndX;
                deltaXEndX = intersectx.subtract(l.End.x);
                BigDecimal deltaXStartX;
                deltaXStartX = intersectx.subtract(l.Start.x);
                BigDecimal deltaYEndY;
                deltaYEndY = intersecty.subtract(l.End.y);
                BigDecimal deltaYStartY;
                deltaYStartY = intersecty.subtract(l.Start.y);
                if (((deltaXEndX.compareTo(tollerance) == 1 && deltaXEndX.compareTo(tollerance.negate()) == 1)
                        && (deltaXStartX.compareTo(tollerance) == 1 && deltaXStartX.compareTo(tollerance.negate()) == 1))
                        || ((deltaXEndX.compareTo(tollerance) == -1 && deltaXEndX.compareTo(tollerance.negate()) == -1)
                        && (deltaXStartX.compareTo(tollerance) == -1) && deltaXStartX.compareTo(tollerance.negate()) == -1)
                        || ((deltaYEndY.compareTo(tollerance) == 1 && deltaYEndY.compareTo(tollerance.negate()) == 1)
                        && deltaYStartY.compareTo(tollerance) == 1 && deltaYStartY.compareTo(tollerance.negate()) == 1)
                        || ((deltaYEndY.compareTo(tollerance) == -1 && deltaYEndY.compareTo(tollerance.negate()) == -1)
                        && (deltaYStartY.compareTo(tollerance) == -1 && deltaYStartY.compareTo(tollerance.negate()) == -1))) {
                    return null;
                }

//                if ((deltaXEndX.compareTo(tollerance) == 1
//                        && deltaXStartX.compareTo(tollerance) == 1)
//                        || (deltaXEndX.compareTo(tollerance) == -1
//                        && deltaXStartX.compareTo(tollerance) == -1)
//                        || (deltaYEndY.compareTo(tollerance) == 1
//                        && deltaYStartY.compareTo(tollerance) == 1)
//                        || (deltaYEndY.compareTo(tollerance) == -1
//                        && deltaYStartY.compareTo(tollerance) == -1)) {
//                    return null;
//                }
//                if ((intersectx2.compareTo(a_LineSegment2D.End.x) == 1
//                        && intersectx2.compareTo(a_LineSegment2D.Start.x) == 1)
//                        || (intersectx2.compareTo(a_LineSegment2D.End.x) == -1
//                        && intersectx2.compareTo(a_LineSegment2D.Start.x) == -1)
//                        || (intersecty2.compareTo(a_LineSegment2D.End.y) == 1
//                        && intersecty2.compareTo(a_LineSegment2D.Start.y) == 1)
//                        || (intersecty2.compareTo(a_LineSegment2D.End.y) == -1
//                        && intersecty2.compareTo(a_LineSegment2D.Start.y) == -1)) {
//                    return null;
//                }
                return new Vector_Point2D(
                        e,
                        intersectx,
                        intersecty,
                        decimalPlacePrecision);
            }
        }
        return null;
    }

    /**
     * l.Start.x >= xmin && l.Start.x < xmax &&
 l.Start.y >= ymin && l.Start.y < ymax
     *
     * @param tollerance
     * @param xmin
     * @param ymin
     * @param xmax
     * @param ymax
     * @param l
     * @param directionIn
     * @param a_DecimalPlacePrecision
     * @param handleOutOfMemoryError
     * @return
     */
    public static Object[] getLineToIntersectLineRemainingDirection(
            BigDecimal tollerance,
            BigDecimal xmin,
            BigDecimal ymin,
            BigDecimal xmax,
            BigDecimal ymax,
            Vector_LineSegment2D l,
            Integer directionIn,
            int a_DecimalPlacePrecision,
            boolean handleOutOfMemoryError) {
        Object[] result;
        result = new Object[3];
        if (directionIn == null) {
            //if (l.Start.x == xmin) {
            BigDecimal deltaXmin;
            deltaXmin = l.Start.x.subtract(xmin);
            if (deltaXmin.compareTo(tollerance) == -1 && deltaXmin.compareTo(tollerance.negate()) == 1) {
                //if (l.Start.y == ymin) {
                BigDecimal deltaYmin;
                deltaYmin = l.Start.y.subtract(ymin);
                if (deltaYmin.compareTo(tollerance) == -1 && deltaYmin.compareTo(tollerance.negate()) == 1) {
                    directionIn = 1;
                } else {
                    //if (l.Start.y == ymax) {
                    BigDecimal deltaYmax;
                    deltaYmax = l.Start.y.subtract(ymax);
                    if (deltaYmax.compareTo(tollerance) == -1 && deltaYmax.compareTo(tollerance.negate()) == 1) {
                        directionIn = 3;
                    } else {
                        directionIn = 2;
                    }
                }
            } else {
                //if (l.Start.x == xmax) {
                BigDecimal deltaXmax;
                deltaXmax = l.Start.y.subtract(xmax);
                if (deltaXmax.compareTo(tollerance) == -1 && deltaXmax.compareTo(tollerance.negate()) == 1) {
                    //if (l.Start.y == ymin) {
                    BigDecimal deltaYmin;
                    deltaYmin = l.Start.y.subtract(ymin);
                    if (deltaYmin.compareTo(tollerance) == -1 && deltaYmin.compareTo(tollerance.negate()) == 1) {
                        directionIn = 7;
                    } else {
                        //if (l.Start.y == ymax) {
                        BigDecimal deltaYmax;
                        deltaYmax = l.Start.y.subtract(ymax);
                        if (deltaYmax.compareTo(tollerance) == -1 && deltaYmax.compareTo(tollerance.negate()) == 1) {
                            directionIn = 5;
                        } else {
                            directionIn = 6;
                        }
                    }
                } else {
                    //if (l.Start.y == ymin) {
                    BigDecimal deltaYmin;
                    deltaYmin = l.Start.y.subtract(ymin);
                    if (deltaYmin.compareTo(tollerance) == -1 && deltaYmin.compareTo(tollerance.negate()) == 1) {
                        directionIn = 4;
                    } else {
                        //if (l.Start.y == ymax) {
                        BigDecimal deltaYmax;
                        deltaYmax = l.Start.y.subtract(ymax);
                        if (deltaYmax.compareTo(tollerance) == -1 && deltaYmax.compareTo(tollerance.negate()) == 1) {
                            directionIn = 0;
                        } else {
                            if (l.Start.y.compareTo(l.End.y) == -1) {
                                if (l.Start.x.compareTo(l.End.x) == -1) {
                                    directionIn = 1;
                                } else {
                                    if (l.Start.x.compareTo(l.End.x) == 0) {
                                        directionIn = 0;
                                    } else {
                                        directionIn = 7;
                                    }
                                }
                            } else {
                                if (l.Start.y.compareTo(l.End.y) == 0) {
                                    if (l.Start.x.compareTo(l.End.x) == -1) {
                                        directionIn = 2;
                                    } else {
                                        if (l.Start.x.compareTo(l.End.x) == 0) {
                                            directionIn = 0; // This should not happen
                                        } else {
                                            directionIn = 6;
                                        }
                                    }
                                } else {
                                    if (l.Start.x.compareTo(l.End.x) == -1) {
                                        directionIn = 3;
                                        //directionIn = 7;
                                    } else {
                                        if (l.Start.x.compareTo(l.End.x) == 0) {
                                            directionIn = 4;
                                        } else {
                                            directionIn = 5;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Vector_Point2D bottomLeft;
        Vector_Point2D bottomRight;
        Vector_Point2D topRight;
        Vector_Point2D topLeft;
        bottomLeft = new Vector_Point2D(
                l.e, xmin, ymin);
        bottomRight = new Vector_Point2D(
                l.e, xmax, ymin);
        topLeft = new Vector_Point2D(
                l.e, xmin, ymax);
        topRight = new Vector_Point2D(
                l.e, xmax, ymax);
        Object[] lineToIntersectLineRemainingDirection = null;
        if (directionIn == 0) {
            // check top
            lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckTop(
                    tollerance,
                    xmin, ymin, xmax, ymax, l,
                    a_DecimalPlacePrecision, handleOutOfMemoryError,
                    topLeft, topRight);
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check right
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckRight(
                        tollerance,
                        xmin, ymin, xmax, ymax, l,
                        a_DecimalPlacePrecision, handleOutOfMemoryError,
                        topRight, bottomRight);
            }
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check left
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckLeft(
                        tollerance,
                        xmin, ymin, xmax, ymax, l,
                        a_DecimalPlacePrecision, handleOutOfMemoryError,
                        topLeft, bottomLeft);
            }
        }

        if (directionIn == 1) {
            // check top
            lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckTop(
                    tollerance,
                    xmin, ymin, xmax, ymax, l,
                    a_DecimalPlacePrecision, handleOutOfMemoryError,
                    topLeft, topRight);
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check right
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckRight(
                        tollerance,
                        xmin, ymin, xmax, ymax, l,
                        a_DecimalPlacePrecision, handleOutOfMemoryError,
                        topRight, bottomRight);
            }
        }

        if (directionIn == 2) {
            // check top
            lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckTop(
                    tollerance,
                    xmin, ymin, xmax, ymax, l,
                    a_DecimalPlacePrecision, handleOutOfMemoryError,
                    topLeft, topRight);
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check right
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckRight(
                        tollerance,
                        xmin, ymin, xmax, ymax, l,
                        a_DecimalPlacePrecision, handleOutOfMemoryError,
                        topRight, bottomRight);
            }
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check bottom
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckBottom(
                        tollerance,
                        xmin, ymin, xmax, ymax, l,
                        a_DecimalPlacePrecision, handleOutOfMemoryError,
                        bottomLeft, bottomRight);
            }
        }

        if (directionIn == 3) {
            // check right
            lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckRight(
                    tollerance,
                    xmin, ymin, xmax, ymax, l,
                    a_DecimalPlacePrecision, handleOutOfMemoryError,
                    topRight, bottomRight);
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check bottom
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckBottom(
                        tollerance,
                        xmin, ymin, xmax, ymax, l,
                        a_DecimalPlacePrecision, handleOutOfMemoryError,
                        bottomLeft, bottomRight);
            }
        }
        if (directionIn == 4) {
            // check right
            lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckRight(
                    tollerance,
                    xmin, ymin, xmax, ymax, l,
                    a_DecimalPlacePrecision, handleOutOfMemoryError,
                    topRight, bottomRight);
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check bottom
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckBottom(
                        tollerance,
                        xmin, ymin, xmax, ymax, l,
                        a_DecimalPlacePrecision, handleOutOfMemoryError,
                        bottomLeft, bottomRight);
            }
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check left
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckLeft(
                        tollerance,
                        xmin, ymin, xmax, ymax, l,
                        a_DecimalPlacePrecision, handleOutOfMemoryError,
                        topLeft, bottomLeft);
            }
        }

        if (directionIn == 5) {
            // check bottom
            lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckBottom(
                    tollerance,
                    xmin, ymin, xmax, ymax, l,
                    a_DecimalPlacePrecision, handleOutOfMemoryError,
                    bottomLeft, bottomRight);
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check left
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckLeft(
                        tollerance,
                        xmin, ymin, xmax, ymax, l,
                        a_DecimalPlacePrecision, handleOutOfMemoryError,
                        topLeft, bottomLeft);
            }
        }

        if (directionIn == 6) {
            // check bottom
            lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckBottom(
                    tollerance,
                    xmin, ymin, xmax, ymax, l,
                    a_DecimalPlacePrecision, handleOutOfMemoryError, bottomLeft, bottomRight);
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check left
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckLeft(
                        tollerance,
                        xmin, ymin, xmax, ymax, l,
                        a_DecimalPlacePrecision, handleOutOfMemoryError,
                        topLeft, bottomLeft);
            }
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check top
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckTop(
                        tollerance,
                        xmin, ymin, xmax, ymax, l, a_DecimalPlacePrecision, handleOutOfMemoryError, topLeft, topRight);
            }
        }

        if (directionIn == 7) {
            // check left
            lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckLeft(
                    tollerance,
                    xmin, ymin, xmax, ymax, l,
                    a_DecimalPlacePrecision, handleOutOfMemoryError,
                    topLeft, bottomLeft);
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check top
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckTop(
                        tollerance,
                        xmin, ymin, xmax, ymax, l,
                        a_DecimalPlacePrecision, handleOutOfMemoryError,
                        topLeft, topRight);
            }
        }
        // Do remainder
        if (lineToIntersectLineRemainingDirection[2] == null) {
            result[0] = l;
            result[1] = null;
            result[2] = null;
        } else {
            result = lineToIntersectLineRemainingDirection;
        }
        return result;
    }

    protected static Object[] doLineToIntersectLineRemainingDirectionCheckRight(
            BigDecimal tollerance,
            BigDecimal xmin,
            BigDecimal ymin,
            BigDecimal xmax,
            BigDecimal ymax,
            Vector_LineSegment2D l,
            int a_DecimalPlacePrecision,
            boolean handleOutOfMemoryError,
            Vector_Point2D topRight,
            Vector_Point2D bottomRight) {
        Object[] result;
        result = new Object[3];
        Vector_LineSegment2D line = null;
        Vector_LineSegment2D remainingLine = null;
        Integer directionOut = null;
        Vector_LineSegment2D section;
        Object[] lineToIntersectIntersectPoint;
        topRight = new Vector_Point2D(
                l.e, xmax, ymax);
        section = new Vector_LineSegment2D(
                bottomRight, topRight, a_DecimalPlacePrecision);
        if (l.getIntersects(section, tollerance, a_DecimalPlacePrecision, handleOutOfMemoryError)) {
            lineToIntersectIntersectPoint = getLineToIntersectIntersectPoint(
                    l,
                    section,
                    tollerance,
                    a_DecimalPlacePrecision);
            line = (Vector_LineSegment2D) lineToIntersectIntersectPoint[0];
            Vector_Point2D newStartPoint;
            newStartPoint = (Vector_Point2D) lineToIntersectIntersectPoint[1];
            //if (newStartPoint.y.compareTo(xmax) == 0) {
            BigDecimal deltaXmax;
            deltaXmax = newStartPoint.x.subtract(xmax);
            if (deltaXmax.compareTo(tollerance) == -1 && deltaXmax.compareTo(tollerance.negate()) == 1) {
                //if (newStartPoint.y.compareTo(ymax) == 0) {
                BigDecimal deltaYmax;
                deltaYmax = newStartPoint.y.subtract(ymax);
                if (deltaYmax.compareTo(tollerance) == -1 && deltaYmax.compareTo(tollerance.negate()) == 1) {
                    directionOut = 1;
                } else {
                    //if (newStartPoint.y.compareTo(ymin) == 0) {
                    BigDecimal deltaYmin;
                    deltaYmin = newStartPoint.y.subtract(ymin);
                    if (deltaYmin.compareTo(tollerance) == -1 && deltaYmin.compareTo(tollerance.negate()) == 1) {
                        directionOut = 3;
                    } else {
                        directionOut = 2;
                    }
                }
            } else {
                directionOut = 2;
            }
            remainingLine = new Vector_LineSegment2D(
                    (Vector_Point2D) lineToIntersectIntersectPoint[1],
                    l.End, a_DecimalPlacePrecision);
        }
        result[0] = line;
        result[1] = remainingLine;
        result[2] = directionOut;
        return result;
    }

    protected static Object[] doLineToIntersectLineRemainingDirectionCheckLeft(
            BigDecimal tollerance,
            BigDecimal xmin,
            BigDecimal ymin,
            BigDecimal xmax,
            BigDecimal ymax,
            Vector_LineSegment2D l,
            int a_DecimalPlacePrecision,
            boolean handleOutOfMemoryError,
            Vector_Point2D topLeft,
            Vector_Point2D bottomLeft) {
        Object[] result;
        result = new Object[3];
        Vector_LineSegment2D line = null;
        Vector_LineSegment2D remainingLine = null;
        Integer directionOut = null;
        Vector_LineSegment2D section;
        Object[] lineToIntersectIntersectPoint;
        section = new Vector_LineSegment2D(
                bottomLeft, topLeft, a_DecimalPlacePrecision);
        if (l.getIntersects(section, tollerance, a_DecimalPlacePrecision, handleOutOfMemoryError)) {
            lineToIntersectIntersectPoint = getLineToIntersectIntersectPoint(
                    l,
                    section,
                    tollerance,
                    a_DecimalPlacePrecision);
            line = (Vector_LineSegment2D) lineToIntersectIntersectPoint[0];
            Vector_Point2D newStartPoint;
            newStartPoint = (Vector_Point2D) lineToIntersectIntersectPoint[1];
            //if (newStartPoint.y.compareTo(xmin) == 0) {
            BigDecimal deltaXmin;
            deltaXmin = newStartPoint.x.subtract(xmin);
            if (deltaXmin.compareTo(tollerance) == -1 && deltaXmin.compareTo(tollerance.negate()) == 1) {
                //if (newStartPoint.y.compareTo(ymax) == 0) {
                BigDecimal deltaYmax;
                deltaYmax = newStartPoint.y.subtract(ymax);
                if (deltaYmax.compareTo(tollerance) == -1 && deltaYmax.compareTo(tollerance.negate()) == 1) {
                    directionOut = 7;
                } else {
                    //if (newStartPoint.y.compareTo(ymin) == 0) {
                    BigDecimal deltaYmin;
                    deltaYmin = newStartPoint.y.subtract(ymin);
                    if (deltaYmin.compareTo(tollerance) == -1 && deltaYmin.compareTo(tollerance.negate()) == 1) {
                        directionOut = 5;
                    } else {
                        directionOut = 6;
                    }
                }
            } else {
                directionOut = 6;
            }
            remainingLine = new Vector_LineSegment2D(
                    (Vector_Point2D) lineToIntersectIntersectPoint[1],
                    l.End, a_DecimalPlacePrecision);
        }
        result[0] = line;
        result[1] = remainingLine;
        result[2] = directionOut;
        return result;
    }

    protected static Object[] doLineToIntersectLineRemainingDirectionCheckTop(
            BigDecimal tollerance,
            BigDecimal xmin,
            BigDecimal ymin,
            BigDecimal xmax,
            BigDecimal ymax,
            Vector_LineSegment2D l,
            int a_DecimalPlacePrecision,
            boolean handleOutOfMemoryError,
            Vector_Point2D topLeft,
            Vector_Point2D topRight) {
        Object[] result;
        result = new Object[3];
        Vector_LineSegment2D line = null;
        Vector_LineSegment2D remainingLine = null;
        Integer directionOut = null;
        Vector_LineSegment2D section;
        Object[] lineToIntersectIntersectPoint;
        section = new Vector_LineSegment2D(
                topLeft, topRight, a_DecimalPlacePrecision);
        if (l.getIntersects(section, tollerance, a_DecimalPlacePrecision, handleOutOfMemoryError)) {
            lineToIntersectIntersectPoint = getLineToIntersectIntersectPoint(
                    l,
                    section,
                    tollerance,
                    a_DecimalPlacePrecision);
            line = (Vector_LineSegment2D) lineToIntersectIntersectPoint[0];
            Vector_Point2D newStartPoint;
            newStartPoint = (Vector_Point2D) lineToIntersectIntersectPoint[1];
            //if (newStartPoint.y.compareTo(ymax) == 0) {
            BigDecimal deltaYmax;
            deltaYmax = newStartPoint.x.subtract(ymax);
            if (deltaYmax.compareTo(tollerance) == -1 && deltaYmax.compareTo(tollerance.negate()) == 1) {
                //if (newStartPoint.y.compareTo(xmax) == 0) {
                BigDecimal deltaXmax;
                deltaXmax = newStartPoint.x.subtract(xmax);
                if (deltaXmax.compareTo(tollerance) == -1 && deltaXmax.compareTo(tollerance.negate()) == 1) {
                    directionOut = 1;
                } else {
                    //if (newStartPoint.y.compareTo(xmin) == 0) {
                    BigDecimal deltaXmin;
                    deltaXmin = newStartPoint.x.subtract(xmin);
                    if (deltaXmin.compareTo(tollerance) == -1 && deltaXmin.compareTo(tollerance.negate()) == 1) {
                        directionOut = 7;
                    } else {
                        directionOut = 0;
                    }
                }
            } else {
                directionOut = 0;
            }
            remainingLine = new Vector_LineSegment2D(
                    (Vector_Point2D) lineToIntersectIntersectPoint[1],
                    l.End, a_DecimalPlacePrecision);
        }
        result[0] = line;
        result[1] = remainingLine;
        result[2] = directionOut;
        return result;
    }

    protected static Object[] doLineToIntersectLineRemainingDirectionCheckBottom(
            BigDecimal tollerance,
            BigDecimal xmin,
            BigDecimal ymin,
            BigDecimal xmax,
            BigDecimal ymax,
            Vector_LineSegment2D l,
            int a_DecimalPlacePrecision,
            boolean handleOutOfMemoryError,
            Vector_Point2D bottomLeft,
            Vector_Point2D bottomRight) {
        Object[] result;
        result = new Object[3];
        Vector_LineSegment2D line = null;
        Vector_LineSegment2D remainingLine = null;
        Integer directionOut = null;
        Vector_LineSegment2D section;
        Object[] lineToIntersectIntersectPoint;
        section = new Vector_LineSegment2D(
                bottomLeft, bottomRight, a_DecimalPlacePrecision);
        if (l.getIntersects(section, tollerance, a_DecimalPlacePrecision, handleOutOfMemoryError)) {
            lineToIntersectIntersectPoint = getLineToIntersectIntersectPoint(
                    l,
                    section,
                    tollerance,
                    a_DecimalPlacePrecision);
            line = (Vector_LineSegment2D) lineToIntersectIntersectPoint[0];
            Vector_Point2D newStartPoint;
            newStartPoint = (Vector_Point2D) lineToIntersectIntersectPoint[1];
            //if (newStartPoint.y.compareTo(ymin) == 0) {
            BigDecimal deltaYmin;
            deltaYmin = newStartPoint.y.subtract(ymin);
            if (deltaYmin.compareTo(tollerance) == -1 && deltaYmin.compareTo(tollerance.negate()) == 1) {
                //if (newStartPoint.y.compareTo(xmax) == 0) {
                BigDecimal deltaXmax;
                deltaXmax = newStartPoint.x.subtract(xmax);
                if (deltaXmax.compareTo(tollerance) == -1 && deltaXmax.compareTo(tollerance.negate()) == 1) {
                    directionOut = 3;
                } else {
                    //if (newStartPoint.y.compareTo(xmin) == 0) {
                    BigDecimal deltaXmin;
                    deltaXmin = newStartPoint.x.subtract(xmin);
                    if (deltaXmin.compareTo(tollerance) == -1 && deltaXmin.compareTo(tollerance.negate()) == 1) {
                        directionOut = 5;
                    } else {
                        directionOut = 4;
                    }
                }
            } else {
                directionOut = 4;
            }
            remainingLine = new Vector_LineSegment2D(
                    (Vector_Point2D) lineToIntersectIntersectPoint[1],
                    l.End, a_DecimalPlacePrecision);
        }
        result[0] = line;
        result[1] = remainingLine;
        result[2] = directionOut;
        return result;
    }

    protected static Object[] getLineToIntersectIntersectPoint(
            Vector_LineSegment2D l,
            Vector_LineSegment2D section,
            BigDecimal tollerance,
            int a_DecimalPlacePrecision) {
        Object[] result;
        result = new Object[2];
        Vector_LineSegment2D lineToIntersect = null;
        Vector_Point2D intersectPoint = null;
        Vector_AbstractGeometry2D intersection;
        intersection = l.getIntersection(section, tollerance, a_DecimalPlacePrecision);
        if (intersection instanceof Vector_Point2D) {
            intersectPoint = (Vector_Point2D) intersection;
            lineToIntersect = new Vector_LineSegment2D(
                    l.Start,
                    intersectPoint,
                    a_DecimalPlacePrecision);
        } else {
            lineToIntersect = (Vector_LineSegment2D) intersection;
            intersectPoint = lineToIntersect.End;
        }
        result[0] = lineToIntersect;
        result[1] = intersectPoint;
        return result;
    }

    /**
     * TODO: Control precision! The angle returned is the smallest angle between
 this and the x axis and so is between Math.PI and -Math.PI.
     *
     * @return The angle in radians to the x axis
     */
    public double getAngleToX_double() {
        BigDecimal dx = this.End.x.subtract(this.Start.x);
        BigDecimal dy = this.End.y.subtract(this.Start.y);
        return Math.atan2(dy.doubleValue(), dx.doubleValue());
    }

//    /**
//     * The angle returned is the smallest angle between this and the x axis and
//     * so is between Math.PI and -Math.PI.
//     * @return The angle in radians to the x axis
//     */
//    public double getAngleToX_double(){
//        double result;
//        BigDecimal dx = this.End.x.subtract(this.Start.x);
//        BigDecimal dy = this.End.y.subtract(this.Start.y);
//        double angleToX = Math.atan2(dy.doubleValue(),dx.doubleValue());
//        if (this.Start.x.compareTo(this.End.x) == -1 ){
//            result = Math.PI - angleToX;
//        } else {
//            result = angleToX;
//        }
//        return result;
//    }
    /**
     * TODO: Control precision! The angle returned is the smallest angle between
 this and the y axis and so is between Math.PI and -Math.PI.
     *
     * @return The angle in radians to the y axis
     */
    public double getAngleToY_double() {
        double result;
        BigDecimal dx = this.End.x.subtract(this.Start.x);
        BigDecimal dy = this.End.y.subtract(this.Start.y);
        double angleToY = Math.atan2(dx.doubleValue(), dy.doubleValue());
        if (this.Start.y.compareTo(this.End.y) == -1) {
            result = Math.PI - angleToY;
        } else {
            result = angleToY;
        }
        return result;
    }

    /**
     * Assuming a_LineSegment.StartPoint == this.
     *
     * @param a_LineSegment2D Vector_LineSegment2D
     * @return BigDecimal
     */
    public BigDecimal getScalarProduct(
            Vector_LineSegment2D a_LineSegment2D) {
        if (a_LineSegment2D.Start.compareTo(this.Start) != 0) {
            System.out.println(
                    "a_LineSegment2D._Start_Point2D.compareTo(this._Start_Point2D) != 0 in "
                    + this.getClass().getName()
                    + ".getScalarProduct(LineSegment2D)");
        }
        return (a_LineSegment2D.End.x.multiply(this.End.x)).add((a_LineSegment2D.End.y.multiply(this.End.y)));
    }

    /**
     * Assuming a_LineSegment.StartPoint == this.
     *
     * @param a_LineSegment2D Vector_LineSegment2D
     * @return BigDecimal
     */
    public BigDecimal getCrossProduct(
            Vector_LineSegment2D a_LineSegment2D) {
        if (a_LineSegment2D.Start.compareTo(this.Start) != 0) {
            System.out.println(
                    "a_LineSegment2D._Start_Point2D.compareTo(this._Start_Point2D) != 0 in "
                    + this.getClass().getName()
                    + ".getCrossProduct(LineSegment2D)");
        }
        return (a_LineSegment2D.End.x.multiply(this.End.y)).subtract((this.End.x.multiply(a_LineSegment2D.End.y)));
    }

    @Override
    public void applyDecimalPlacePrecision() {
        this.Start.applyDecimalPlacePrecision();
        this.End.applyDecimalPlacePrecision();
    }
}
