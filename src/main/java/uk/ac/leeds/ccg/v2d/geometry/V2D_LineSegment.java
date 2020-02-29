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

import java.math.BigDecimal;
import java.math.RoundingMode;
import uk.ac.leeds.ccg.v2d.core.V2D_Environment;
//import org.ojalgo.function.implementation.BigFunction;
//import org.ojalgo.constant.BigMath;

/**
 * Class for a line segment in 2D represented by two Point2D instances, one is
 * designated a start point and the other an end point. In this way a line
 * segment explicitly has a direction. Two Vector_LineSegment2D instances are
 * regarded as equal iff their start and end points are the same.
 */
public class V2D_LineSegment extends V2D_Geometry
        implements V2D_FiniteGeometry, Comparable {

    public V2D_Point start;
    public V2D_Point end;

    /**
     * Creates a default Vector_LineSegment2D with: start = null; end = null;
     *
     * @param e Vector environment.
     */
    public V2D_LineSegment(V2D_Environment e) {
        super(e);
    }

    /**
     *
     * @param start V2D_Point
     * @param end V2D_Point
     */
    public V2D_LineSegment(V2D_Point start, V2D_Point end) {
        super(start.e);
        this.start = start;
        this.end = end;
    }

    /**
     *
     * @param l Vector_LineSegment2D
     */
    public V2D_LineSegment(
            V2D_LineSegment l) {
        super(l.e);
        this.start = new V2D_Point(l.start);
        this.end = new V2D_Point(l.end);
    }

    @Override
    public String toString() {
        return "LineSegment2D(start=" + start.toString() + ", end=" + end.toString() + ")";
    }

    /**
     * Default is 1. Calls compareTo on the _StartPoint.
     *
     * @param o The object to compare with.
     * @return -1, 0, 1 If this is less than, equal to, or greater than
     * {@code o}.
     */
    @Override
    public int compareTo(Object o) {
        if (o instanceof V2D_LineSegment) {
            V2D_LineSegment a_LineSegment2D = (V2D_LineSegment) o;
            int compareStart = start.compareTo(a_LineSegment2D.start);
            if (compareStart == 0) {
                return start.compareTo(a_LineSegment2D.start);
            } else {
                return compareStart;
            }
        }
        return 1;
    }

    /**
     * @param dp The decimal place precision.
     * @return The length of this as a BigDecimal
     */
    public BigDecimal getLength(int dp) {
        return start.getDistance(end, dp);
    }

    /**
     * @return {@code new V2D_Envelope(start, end)}
     */
    @Override
    public V2D_Envelope getEnvelope2D() {
        return new V2D_Envelope(start, end);
    }

    /**
     * A bounding box intersection test.
     *
     * @param xmin The minimum x-coordinate of the bounding box.
     * @param ymin The minimum y-coordinate of the bounding box.
     * @param xmax The maximum x-coordinate of the bounding box.
     * @param ymax The maximum y-coordinate of the bounding box.
     * @param l The line segment to test for intersection.
     * @param t The tolerance.
     * @return {code true} if {@code l} intersects the envelope defined by
     * {@code minx}, {@code miny}, {@code maxx}, {@code maxy}.
     */
    public static boolean getIntersects(BigDecimal xmin, BigDecimal ymin,
            BigDecimal xmax, BigDecimal ymax, V2D_LineSegment l,
            BigDecimal t) {
        if (l.start.x.compareTo(xmin) != -1 && l.start.x.compareTo(xmax) != 1
                && l.start.y.compareTo(ymin) != -1
                && l.start.y.compareTo(ymax) != 1) {
            return true;
        }
        if (l.end.x.compareTo(xmin) != -1 && l.end.x.compareTo(xmax) != 1
                && l.end.y.compareTo(ymin) != -1
                && l.end.y.compareTo(ymax) != 1) {
            return true;
        }
        V2D_Point ll = new V2D_Point(l.e, xmin, ymin);
        V2D_Point lr = new V2D_Point(l.e, xmax, ymin);
        // Check bottom
        V2D_LineSegment section = new V2D_LineSegment(ll, lr);
        if (l.getIntersects(section, t)) {
            return true;
        }
        V2D_Point ur = new V2D_Point(l.e, xmax, ymax);
        // Check right
        section = new V2D_LineSegment(lr, ur);
        if (l.getIntersects(section, t)) {
            return true;
        }
        V2D_Point ul = new V2D_Point(l.e, xmin, ymax);
        // Check left
        section = new V2D_LineSegment(ll, ul);
        if (l.getIntersects(section, t)) {
            return true;
        }
        // Check top
        section = new V2D_LineSegment(ul, ur);
        return l.getIntersects(section, t);
    }

    /**
     * Test whether {@code l} intersects any {@code lines}.
     *
     * @param l The line to test for intersection with and lines in
     * {@code lines}.
     * @param lines The lines to test for intersection with {@code l}.
     * @param t tolerance.
     * @param dpp The decimal place precision.
     * @return {@code true if {@code l} intersects any lines in {@code lines}.
     */
    public static boolean getIntersects(V2D_LineSegment l,
            V2D_LineSegment[] lines, BigDecimal t) {
        for (V2D_LineSegment line : lines) {
            boolean intersects = l.getIntersects(line, t);
            if (intersects) {
                return true;
            }
        }
        return false;
    }

    /**
     * Intersection done by first seeing if envelope intersects.
     *
     * @param l A line segment to test.
     * @param t The tolerance.
     *
     * @return {@code true} if {@code l} intersects this.
     */
    public boolean getIntersects(V2D_LineSegment l, BigDecimal t) {
        V2D_Geometry intersection = getIntersection(l, t);
        return intersection != null;
    }

    /**
     * For optimisation reasons, intersection done by first seeing if there is
     * Envelope intersection...
     *
     * @param l line segment to test for intersection.
     * @param ignoreThisStartPoint2D if true then if
     * this.start.getIntersects(a_LineSegment2D) return false
     * @param t tolerance
     * @param dpp The decimal place precision to be used for this.
     * @return {@code true} if {@code l} intersects this.
     */
    public boolean getIntersects(V2D_LineSegment l,
            boolean ignoreThisStartPoint2D, BigDecimal t) {
        if (ignoreThisStartPoint2D) {
            if (this.start.getIntersects(l, t)) {
                return false;
            } else {
                return getIntersects(l, t);
            }
        } else {
            return getIntersects(l, t);
        }
    }

    /**
     * Intersection done by calculating angle or gradient of the line and
     * comparing this with that of a_Point.
     *
     * @param p A point to test for intersection.
     * @param t Tolerance.
     * @return {@code true} if {@code p} intersects this.
     */
    public boolean getIntersects(V2D_Point p, BigDecimal t) {
        if (getEnvelope2D().getIntersects(p) == false) {
            return false;
        }
        if (start.y.compareTo(end.y) == -1) {
            // StartPoint is Below EndPoint
            if (start.x.compareTo(end.x) == -1) {
                // StartPoint is Left of EndPoint
                return isOnGradient(p, t);
            } else {
                if (start.x.compareTo(end.x) == 0) {
                    // StartPoint has same x as EndPoint
                    return true;
                } else {
                    // StartPoint is Right of EndPoint
                    return isOnGradient(p, t);
                }
            }
        } else {
            if (start.y.compareTo(end.y) == 0) {
                // StartPoint has same y as EndPoint
                return true;
            } else {
                // StartPoint is Above EndPoint
                if (start.x.compareTo(end.x) == -1) {
                    // StartPoint is Left of EndPoint
                    return isOnGradient(p, t);
                } else {
                    if (start.x.compareTo(end.x) == 0) {
                        // StartPoint has same x as EndPoint
                        return true;
                    } else {
                        // StartPoint is Right of EndPoint
                        return isOnGradient(p, t);
                    }
                }
            }
        }
    }

    /**
     * @return The gradient.
     */
    protected BigDecimal getGradient(int scale, RoundingMode rm) {
        return start.getGradient(end, scale, rm);
    }

    /**
     *
     * @param p The point.
     * @param t Tolerance
     * @return
     */
    protected boolean isOnGradient(V2D_Point p, BigDecimal t) {
        BigDecimal xDiff0 = end.x.subtract(start.x);
        BigDecimal yDiff0 = end.y.subtract(start.y);
        BigDecimal xDiff1 = p.x.subtract(start.x);
        BigDecimal yDiff1 = p.y.subtract(start.y);
        BigDecimal gradient0;
        if (yDiff0.compareTo(BigDecimal.ZERO) == 0) {
            return yDiff1.compareTo(BigDecimal.ZERO) == 0;
        } else {
            gradient0 = xDiff0.divide(
                    yDiff0,
                    t.scale() + 2, // + 2 sufficient?
                    RoundingMode.CEILING);
        }
        BigDecimal gradient1;
        if (yDiff1.compareTo(BigDecimal.ZERO) == 0) {
            return false;
        } else {
            gradient1 = xDiff1.divide(
                    yDiff1,
                    t.scale() + 2, // + 2 sufficient?
                    RoundingMode.CEILING);
        }
        return gradient0.compareTo(gradient1) == 0;
    }

    public V2D_LineSegment getOrderedLineSegment2D() {
        if (start.y.compareTo(end.y) == -1) {
            return new V2D_LineSegment(this);
        } else {
            if (start.y.compareTo(end.y) == 0) {
                if (start.x.compareTo(end.x) != 1) {
                    return new V2D_LineSegment(this);
                }
            }
        }
        return new V2D_LineSegment(this.end, this.start);
    }

    /**
     * Intersection method adapted from:
     * http://local.wasp.uwa.edu.au/~pbourke/geometry/lineline2d/
     *
     * @param l V2D_LineSegment
     * @param t tolerance
     * @return null if this does not intersect a_LineSegment2D; a Point2D if
     * this getIntersects a_LineSegment2D at a point; and, a V2D_LineSegment if
     * this getIntersects a_LineSegment2D in a line. TODO: Not sure about using
     * Rounding Modes here, it might be best to handle arithmetic exceptions and
     * provide another method into which a MathContext can be passed...
     */
    public V2D_Geometry getIntersection(V2D_LineSegment l,
            BigDecimal t) {
        // Special cases
        if (this.start.y.compareTo(l.start.y) == 1
                && this.start.y.compareTo(l.end.y) == 1
                && this.end.y.compareTo(l.start.y) == 1
                && this.end.y.compareTo(l.end.y) == 1) {
            return null;
        }
        if (this.start.x.compareTo(l.start.x) == 1
                && this.start.x.compareTo(l.end.x) == 1
                && this.end.x.compareTo(l.start.x) == 1
                && this.end.x.compareTo(l.end.x) == 1) {
            return null;
        }
        if (this.start.y.compareTo(l.start.y) == -1
                && this.start.y.compareTo(l.end.y) == -1
                && this.end.y.compareTo(l.start.y) == -1
                && this.end.y.compareTo(l.end.y) == -1) {
            return null;
        }
        if (this.start.x.compareTo(l.start.x) == -1
                && this.start.x.compareTo(l.end.x) == -1
                && this.end.x.compareTo(l.start.x) == -1
                && this.end.x.compareTo(l.end.x) == -1) {
            return null;
        }
        //MathContext a_MathContext = new MathContext(this.DecimalPlacePrecision_Integer + 100);
        BigDecimal x2minusx1 = end.x.subtract(start.x);
        BigDecimal y2minusy1 = end.y.subtract(start.y);
        BigDecimal x4minusx3 = l.end.x.subtract(l.start.x);
        BigDecimal y4minusy3 = l.end.y.subtract(l.start.y);
        BigDecimal denominator = (y4minusy3.multiply(x2minusx1))
                .subtract(x4minusx3.multiply(y2minusy1));
        boolean parallel = false;
        // Not sure about use of RoundingMode here!!
        if (denominator.setScale(t.scale(), RoundingMode.FLOOR).compareTo(
                BigDecimal.ZERO) == 0) {
            //System.out.println("parallel lines");
            parallel = true;
        }
        BigDecimal y1minusy3 = start.y.subtract(l.start.y);
        BigDecimal x1minusx3 = start.x.subtract(l.start.x);
        BigDecimal uamultiplicand = (x4minusx3.multiply(y1minusy3))
                .subtract(y4minusy3.multiply(x1minusx3));
        BigDecimal ubmultiplicand = (x2minusx1.multiply(y1minusy3))
                .subtract(y2minusy1.multiply(x1minusx3));
        if (uamultiplicand.setScale(t.scale(), RoundingMode.FLOOR).compareTo(BigDecimal.ZERO) == 0
                && ubmultiplicand.setScale(t.scale(), RoundingMode.FLOOR).compareTo(BigDecimal.ZERO) == 0
                && parallel) {
            //System.out.println("lines coincident");
            V2D_LineSegment ot = getOrderedLineSegment2D();
            V2D_LineSegment oa = l.getOrderedLineSegment2D();
            boolean ts = ot.start.getIntersects(oa, t);
            boolean te = ot.end.getIntersects(oa, t);
            boolean as = oa.start.getIntersects(ot, t);
            boolean ae = oa.end.getIntersects(ot, t);
            if (ts) {
                if (te) {
                    if (as) {
                        if (ae) {
                            return new V2D_LineSegment(l);
                        } else {
                            return new V2D_LineSegment(ot.start, ot.end);
                        }
                    } else {
                        if (ae) {
                            return new V2D_LineSegment(ot.start, oa.end);
                        } else {
                            return new V2D_LineSegment(this);
                        }
                    }
                } else {
                    if (as) {
                        if (ae) {
                            return new V2D_LineSegment(l);
                        } else {
                            return new V2D_Point(ot.start);
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
                            return new V2D_LineSegment(oa.start, oa.end);
                        } else {
                            return new V2D_LineSegment(oa.start, ot.end);
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
        BigDecimal ua = uamultiplicand.divide(denominator, t.scale(),
                RoundingMode.CEILING);
        if (ua.compareTo(BigDecimal.ONE) != 1
                && ua.compareTo(BigDecimal.ZERO) != -1) {
            BigDecimal intersectx = start.x.add(ua.multiply(x2minusx1));
            BigDecimal intersecty = start.y.add(ua.multiply(y2minusy1));
            BigDecimal deltaXEndX = intersectx.subtract(l.end.x);
            BigDecimal deltaXStartX = intersectx.subtract(l.start.x);
            BigDecimal deltaYEndY = intersecty.subtract(l.end.y);
            BigDecimal deltaYStartY = intersecty.subtract(l.start.y);
            if (((deltaXEndX.compareTo(t) == 1 && deltaXEndX.compareTo(t.negate()) == 1)
                    && (deltaXStartX.compareTo(t) == 1 && deltaXStartX.compareTo(t.negate()) == 1))
                    || ((deltaXEndX.compareTo(t) == -1 && deltaXEndX.compareTo(t.negate()) == -1)
                    && (deltaXStartX.compareTo(t) == -1) && deltaXStartX.compareTo(t.negate()) == -1)
                    || ((deltaYEndY.compareTo(t) == 1 && deltaYEndY.compareTo(t.negate()) == 1)
                    && deltaYStartY.compareTo(t) == 1 && deltaYStartY.compareTo(t.negate()) == 1)
                    || ((deltaYEndY.compareTo(t) == -1 && deltaYEndY.compareTo(t.negate()) == -1)
                    && (deltaYStartY.compareTo(t) == -1 && deltaYStartY.compareTo(t.negate()) == -1))) {
                return null;
            }
//            if ((intersectx1.compareTo(a_LineSegment2D.end.x) == 1
//                    && intersectx1.compareTo(a_LineSegment2D.start.x) == 1)
//                    || (intersectx1.compareTo(a_LineSegment2D.end.x) == -1
//                    && intersectx1.compareTo(a_LineSegment2D.start.x) == -1)
//                    || (intersecty1.compareTo(a_LineSegment2D.end.y) == 1
//                    && intersecty1.compareTo(a_LineSegment2D.start.y) == 1)
//                    || (intersecty1.compareTo(a_LineSegment2D.end.y) == -1
//                    && intersecty1.compareTo(a_LineSegment2D.start.y) == -1)) {
//                return null;
//            }
//            BigDecimal ub = ubmultiplicand.divide(
//                    denominator,
//                    a_DecimalPlacePrecision,
//                RoundingMode.HALF_UP);
            return new V2D_Point(
                    e,
                    intersectx,
                    intersecty,
                    t.scale());
        } else {
            BigDecimal ub = ubmultiplicand.divide(
                    denominator,
                    t.scale(),
                    RoundingMode.CEILING);
            if (ub.compareTo(BigDecimal.ONE) != 1
                    && ub.compareTo(BigDecimal.ZERO) != -1) {
                BigDecimal intersectx = l.start.x.add(
                        ub.multiply(x4minusx3));
                BigDecimal intersecty = l.start.y.add(
                        ub.multiply(y4minusy3));
                BigDecimal deltaXEndX;
                deltaXEndX = intersectx.subtract(l.end.x);
                BigDecimal deltaXStartX;
                deltaXStartX = intersectx.subtract(l.start.x);
                BigDecimal deltaYEndY;
                deltaYEndY = intersecty.subtract(l.end.y);
                BigDecimal deltaYStartY;
                deltaYStartY = intersecty.subtract(l.start.y);
                if (((deltaXEndX.compareTo(t) == 1 && deltaXEndX.compareTo(t.negate()) == 1)
                        && (deltaXStartX.compareTo(t) == 1 && deltaXStartX.compareTo(t.negate()) == 1))
                        || ((deltaXEndX.compareTo(t) == -1 && deltaXEndX.compareTo(t.negate()) == -1)
                        && (deltaXStartX.compareTo(t) == -1) && deltaXStartX.compareTo(t.negate()) == -1)
                        || ((deltaYEndY.compareTo(t) == 1 && deltaYEndY.compareTo(t.negate()) == 1)
                        && deltaYStartY.compareTo(t) == 1 && deltaYStartY.compareTo(t.negate()) == 1)
                        || ((deltaYEndY.compareTo(t) == -1 && deltaYEndY.compareTo(t.negate()) == -1)
                        && (deltaYStartY.compareTo(t) == -1 && deltaYStartY.compareTo(t.negate()) == -1))) {
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
//                if ((intersectx2.compareTo(a_LineSegment2D.end.x) == 1
//                        && intersectx2.compareTo(a_LineSegment2D.start.x) == 1)
//                        || (intersectx2.compareTo(a_LineSegment2D.end.x) == -1
//                        && intersectx2.compareTo(a_LineSegment2D.start.x) == -1)
//                        || (intersecty2.compareTo(a_LineSegment2D.end.y) == 1
//                        && intersecty2.compareTo(a_LineSegment2D.start.y) == 1)
//                        || (intersecty2.compareTo(a_LineSegment2D.end.y) == -1
//                        && intersecty2.compareTo(a_LineSegment2D.start.y) == -1)) {
//                    return null;
//                }
                return new V2D_Point(
                        e,
                        intersectx,
                        intersecty,
                        t.scale());
            }
        }
        return null;
    }

    /**
     * l.start.x >= xmin && l.start.x < xmax &&
     * l.start.y >= ymin && l.start.y < ymax
     *
     * @param t tolerance
     * @param xmin
     * @param ymin
     * @param xmax
     * @param ymax
     * @param l
     * @param directionIn
     * @param dpp decimal place precision
     * @return
     */
    public static Object[] getLineToIntersectLineRemainingDirection(
            BigDecimal t, BigDecimal xmin, BigDecimal ymin, BigDecimal xmax,
            BigDecimal ymax, V2D_LineSegment l, Integer directionIn,
            int dpp) {
        Object[] r = new Object[3];
        if (directionIn == null) {
            //if (l.start.x == xmin) {
            BigDecimal deltaXmin;
            deltaXmin = l.start.x.subtract(xmin);
            if (deltaXmin.compareTo(t) == -1 && deltaXmin.compareTo(t.negate()) == 1) {
                //if (l.start.y == ymin) {
                BigDecimal deltaYmin;
                deltaYmin = l.start.y.subtract(ymin);
                if (deltaYmin.compareTo(t) == -1 && deltaYmin.compareTo(t.negate()) == 1) {
                    directionIn = 1;
                } else {
                    //if (l.start.y == ymax) {
                    BigDecimal deltaYmax;
                    deltaYmax = l.start.y.subtract(ymax);
                    if (deltaYmax.compareTo(t) == -1 && deltaYmax.compareTo(t.negate()) == 1) {
                        directionIn = 3;
                    } else {
                        directionIn = 2;
                    }
                }
            } else {
                //if (l.start.x == xmax) {
                BigDecimal deltaXmax = l.start.y.subtract(xmax);
                if (deltaXmax.compareTo(t) == -1
                        && deltaXmax.compareTo(t.negate()) == 1) {
                    //if (l.start.y == ymin) {
                    BigDecimal deltaYmin = l.start.y.subtract(ymin);
                    if (deltaYmin.compareTo(t) == -1
                            && deltaYmin.compareTo(t.negate()) == 1) {
                        directionIn = 7;
                    } else {
                        //if (l.start.y == ymax) {
                        BigDecimal deltaYmax = l.start.y.subtract(ymax);
                        if (deltaYmax.compareTo(t) == -1
                                && deltaYmax.compareTo(t.negate()) == 1) {
                            directionIn = 5;
                        } else {
                            directionIn = 6;
                        }
                    }
                } else {
                    //if (l.start.y == ymin) {
                    BigDecimal deltaYmin;
                    deltaYmin = l.start.y.subtract(ymin);
                    if (deltaYmin.compareTo(t) == -1
                            && deltaYmin.compareTo(t.negate()) == 1) {
                        directionIn = 4;
                    } else {
                        //if (l.start.y == ymax) {
                        BigDecimal deltaYmax;
                        deltaYmax = l.start.y.subtract(ymax);
                        if (deltaYmax.compareTo(t) == -1 && deltaYmax.compareTo(t.negate()) == 1) {
                            directionIn = 0;
                        } else {
                            if (l.start.y.compareTo(l.end.y) == -1) {
                                if (l.start.x.compareTo(l.end.x) == -1) {
                                    directionIn = 1;
                                } else {
                                    if (l.start.x.compareTo(l.end.x) == 0) {
                                        directionIn = 0;
                                    } else {
                                        directionIn = 7;
                                    }
                                }
                            } else {
                                if (l.start.y.compareTo(l.end.y) == 0) {
                                    if (l.start.x.compareTo(l.end.x) == -1) {
                                        directionIn = 2;
                                    } else {
                                        if (l.start.x.compareTo(l.end.x) == 0) {
                                            directionIn = 0; // This should not happen
                                        } else {
                                            directionIn = 6;
                                        }
                                    }
                                } else {
                                    if (l.start.x.compareTo(l.end.x) == -1) {
                                        directionIn = 3;
                                        //directionIn = 7;
                                    } else {
                                        if (l.start.x.compareTo(l.end.x) == 0) {
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
        V2D_Point bottomLeft = new V2D_Point(l.e, xmin, ymin);
        V2D_Point bottomRight = new V2D_Point(l.e, xmax, ymin);
        V2D_Point topLeft = new V2D_Point(l.e, xmin, ymax);
        V2D_Point topRight = new V2D_Point(l.e, xmax, ymax);
        Object[] lineToIntersectLineRemainingDirection = null;
        if (directionIn == 0) {
            // check top
            lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckTop(
                    t, xmin, ymin, xmax, ymax, l, topLeft, topRight);
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check right
                lineToIntersectLineRemainingDirection
                        = doLineToIntersectLineRemainingDirectionCheckRight(t,
                                xmin, ymin, xmax, ymax, l,
                                topRight, bottomRight);
            }
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check left
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckLeft(
                        t, xmin, ymin, xmax, ymax, l, topLeft, bottomLeft);
            }
        }

        if (directionIn == 1) {
            // check top
            lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckTop(
                    t, xmin, ymin, xmax, ymax, l, topLeft, topRight);
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check right
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckRight(
                        t, xmin, ymin, xmax, ymax, l, topRight, bottomRight);
            }
        }

        if (directionIn == 2) {
            // check top
            lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckTop(
                    t, xmin, ymin, xmax, ymax, l, topLeft, topRight);
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check right
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckRight(
                        t, xmin, ymin, xmax, ymax, l, topRight, bottomRight);
            }
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check bottom
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckBottom(
                        t, xmin, ymin, xmax, ymax, l, bottomLeft, bottomRight);
            }
        }

        if (directionIn == 3) {
            // check right
            lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckRight(
                    t, xmin, ymin, xmax, ymax, l, topRight, bottomRight);
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check bottom
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckBottom(
                        t, xmin, ymin, xmax, ymax, l, bottomLeft, bottomRight);
            }
        }
        if (directionIn == 4) {
            // check right
            lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckRight(
                    t, xmin, ymin, xmax, ymax, l, topRight, bottomRight);
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check bottom
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckBottom(
                        t, xmin, ymin, xmax, ymax, l, bottomLeft, bottomRight);
            }
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check left
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckLeft(
                        t, xmin, ymin, xmax, ymax, l, topLeft, bottomLeft);
            }
        }

        if (directionIn == 5) {
            // check bottom
            lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckBottom(
                    t, xmin, ymin, xmax, ymax, l, bottomLeft, bottomRight);
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check left
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckLeft(
                        t, xmin, ymin, xmax, ymax, l, topLeft, bottomLeft);
            }
        }

        if (directionIn == 6) {
            // check bottom
            lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckBottom(
                    t, xmin, ymin, xmax, ymax, l, bottomLeft, bottomRight);
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check left
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckLeft(
                        t, xmin, ymin, xmax, ymax, l, topLeft, bottomLeft);
            }
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check top
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckTop(
                        t, xmin, ymin, xmax, ymax, l, topLeft, topRight);
            }
        }

        if (directionIn == 7) {
            // check left
            lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckLeft(
                    t, xmin, ymin, xmax, ymax, l, topLeft, bottomLeft);
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check top
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckTop(
                        t, xmin, ymin, xmax, ymax, l, topLeft, topRight);
            }
        }
        // Do remainder
        if (lineToIntersectLineRemainingDirection[2] == null) {
            r[0] = l;
            r[1] = null;
            r[2] = null;
        } else {
            r = lineToIntersectLineRemainingDirection;
        }
        return r;
    }

    protected static Object[] doLineToIntersectLineRemainingDirectionCheckRight(
            BigDecimal t, BigDecimal xmin, BigDecimal ymin,
            BigDecimal xmax, BigDecimal ymax, V2D_LineSegment l,
            V2D_Point topRight, V2D_Point bottomRight) {
        Object[] r = new Object[3];
        V2D_LineSegment line = null;
        V2D_LineSegment remainingLine = null;
        Integer directionOut = null;
        V2D_LineSegment section;
        Object[] lineToIntersectIntersectPoint;
        topRight = new V2D_Point(l.e, xmax, ymax);
        section = new V2D_LineSegment(bottomRight, topRight);
        if (l.getIntersects(section, t)) {
            lineToIntersectIntersectPoint = getLineToIntersectIntersectPoint(
                    l, section, t);
            line = (V2D_LineSegment) lineToIntersectIntersectPoint[0];
            V2D_Point newStartPoint;
            newStartPoint = (V2D_Point) lineToIntersectIntersectPoint[1];
            //if (newStartPoint.y.compareTo(xmax) == 0) {
            BigDecimal deltaXmax;
            deltaXmax = newStartPoint.x.subtract(xmax);
            if (deltaXmax.compareTo(t) == -1 && deltaXmax.compareTo(t.negate()) == 1) {
                //if (newStartPoint.y.compareTo(ymax) == 0) {
                BigDecimal deltaYmax;
                deltaYmax = newStartPoint.y.subtract(ymax);
                if (deltaYmax.compareTo(t) == -1 && deltaYmax.compareTo(t.negate()) == 1) {
                    directionOut = 1;
                } else {
                    //if (newStartPoint.y.compareTo(ymin) == 0) {
                    BigDecimal deltaYmin;
                    deltaYmin = newStartPoint.y.subtract(ymin);
                    if (deltaYmin.compareTo(t) == -1 && deltaYmin.compareTo(t.negate()) == 1) {
                        directionOut = 3;
                    } else {
                        directionOut = 2;
                    }
                }
            } else {
                directionOut = 2;
            }
            remainingLine = new V2D_LineSegment(
                    (V2D_Point) lineToIntersectIntersectPoint[1],
                    l.end);
        }
        r[0] = line;
        r[1] = remainingLine;
        r[2] = directionOut;
        return r;
    }

    protected static Object[] doLineToIntersectLineRemainingDirectionCheckLeft(
            BigDecimal t,
            BigDecimal xmin,
            BigDecimal ymin,
            BigDecimal xmax,
            BigDecimal ymax,
            V2D_LineSegment l,
            V2D_Point topLeft,
            V2D_Point bottomLeft) {
        Object[] result;
        result = new Object[3];
        V2D_LineSegment line = null;
        V2D_LineSegment remainingLine = null;
        Integer directionOut = null;
        V2D_LineSegment section = new V2D_LineSegment(bottomLeft, topLeft);
        Object[] lineToIntersectIntersectPoint;
        if (l.getIntersects(section, t)) {
            lineToIntersectIntersectPoint = getLineToIntersectIntersectPoint(
                    l, section, t);
            line = (V2D_LineSegment) lineToIntersectIntersectPoint[0];
            V2D_Point newStartPoint;
            newStartPoint = (V2D_Point) lineToIntersectIntersectPoint[1];
            //if (newStartPoint.y.compareTo(xmin) == 0) {
            BigDecimal deltaXmin;
            deltaXmin = newStartPoint.x.subtract(xmin);
            if (deltaXmin.compareTo(t) == -1 && deltaXmin.compareTo(t.negate()) == 1) {
                //if (newStartPoint.y.compareTo(ymax) == 0) {
                BigDecimal deltaYmax;
                deltaYmax = newStartPoint.y.subtract(ymax);
                if (deltaYmax.compareTo(t) == -1 && deltaYmax.compareTo(t.negate()) == 1) {
                    directionOut = 7;
                } else {
                    //if (newStartPoint.y.compareTo(ymin) == 0) {
                    BigDecimal deltaYmin;
                    deltaYmin = newStartPoint.y.subtract(ymin);
                    if (deltaYmin.compareTo(t) == -1 && deltaYmin.compareTo(t.negate()) == 1) {
                        directionOut = 5;
                    } else {
                        directionOut = 6;
                    }
                }
            } else {
                directionOut = 6;
            }
            remainingLine = new V2D_LineSegment(
                    (V2D_Point) lineToIntersectIntersectPoint[1],
                    l.end);
        }
        result[0] = line;
        result[1] = remainingLine;
        result[2] = directionOut;
        return result;
    }

    protected static Object[] doLineToIntersectLineRemainingDirectionCheckTop(
            BigDecimal t, BigDecimal xmin, BigDecimal ymin,
            BigDecimal xmax, BigDecimal ymax, V2D_LineSegment l,
            V2D_Point topLeft, V2D_Point topRight) {
        Object[] result;
        result = new Object[3];
        V2D_LineSegment line = null;
        V2D_LineSegment remainingLine = null;
        Integer directionOut = null;
        V2D_LineSegment section;
        Object[] lineToIntersectIntersectPoint;
        section = new V2D_LineSegment(
                topLeft, topRight);
        if (l.getIntersects(section, t)) {
            lineToIntersectIntersectPoint = getLineToIntersectIntersectPoint(
                    l, section, t);
            line = (V2D_LineSegment) lineToIntersectIntersectPoint[0];
            V2D_Point newStartPoint;
            newStartPoint = (V2D_Point) lineToIntersectIntersectPoint[1];
            //if (newStartPoint.y.compareTo(ymax) == 0) {
            BigDecimal deltaYmax;
            deltaYmax = newStartPoint.x.subtract(ymax);
            if (deltaYmax.compareTo(t) == -1 && deltaYmax.compareTo(t.negate()) == 1) {
                //if (newStartPoint.y.compareTo(xmax) == 0) {
                BigDecimal deltaXmax;
                deltaXmax = newStartPoint.x.subtract(xmax);
                if (deltaXmax.compareTo(t) == -1 && deltaXmax.compareTo(t.negate()) == 1) {
                    directionOut = 1;
                } else {
                    //if (newStartPoint.y.compareTo(xmin) == 0) {
                    BigDecimal deltaXmin;
                    deltaXmin = newStartPoint.x.subtract(xmin);
                    if (deltaXmin.compareTo(t) == -1 && deltaXmin.compareTo(t.negate()) == 1) {
                        directionOut = 7;
                    } else {
                        directionOut = 0;
                    }
                }
            } else {
                directionOut = 0;
            }
            remainingLine = new V2D_LineSegment(
                    (V2D_Point) lineToIntersectIntersectPoint[1],
                    l.end);
        }
        result[0] = line;
        result[1] = remainingLine;
        result[2] = directionOut;
        return result;
    }

    protected static Object[] doLineToIntersectLineRemainingDirectionCheckBottom(
            BigDecimal t,
            BigDecimal xmin,
            BigDecimal ymin,
            BigDecimal xmax,
            BigDecimal ymax,
            V2D_LineSegment l,
            V2D_Point bottomLeft,
            V2D_Point bottomRight) {
        Object[] result;
        result = new Object[3];
        V2D_LineSegment line = null;
        V2D_LineSegment remainingLine = null;
        Integer directionOut = null;
        V2D_LineSegment section;
        Object[] lineToIntersectIntersectPoint;
        section = new V2D_LineSegment(
                bottomLeft, bottomRight);
        if (l.getIntersects(section, t)) {
            lineToIntersectIntersectPoint = getLineToIntersectIntersectPoint(
                    l,
                    section,
                    t);
            line = (V2D_LineSegment) lineToIntersectIntersectPoint[0];
            V2D_Point newStartPoint;
            newStartPoint = (V2D_Point) lineToIntersectIntersectPoint[1];
            //if (newStartPoint.y.compareTo(ymin) == 0) {
            BigDecimal deltaYmin;
            deltaYmin = newStartPoint.y.subtract(ymin);
            if (deltaYmin.compareTo(t) == -1 && deltaYmin.compareTo(t.negate()) == 1) {
                //if (newStartPoint.y.compareTo(xmax) == 0) {
                BigDecimal deltaXmax;
                deltaXmax = newStartPoint.x.subtract(xmax);
                if (deltaXmax.compareTo(t) == -1 && deltaXmax.compareTo(t.negate()) == 1) {
                    directionOut = 3;
                } else {
                    //if (newStartPoint.y.compareTo(xmin) == 0) {
                    BigDecimal deltaXmin;
                    deltaXmin = newStartPoint.x.subtract(xmin);
                    if (deltaXmin.compareTo(t) == -1 && deltaXmin.compareTo(t.negate()) == 1) {
                        directionOut = 5;
                    } else {
                        directionOut = 4;
                    }
                }
            } else {
                directionOut = 4;
            }
            remainingLine = new V2D_LineSegment(
                    (V2D_Point) lineToIntersectIntersectPoint[1],
                    l.end);
        }
        result[0] = line;
        result[1] = remainingLine;
        result[2] = directionOut;
        return result;
    }

    /**
     *
     * @param l
     * @param section
     * @param t tolerance
     * @return
     */
    protected static Object[] getLineToIntersectIntersectPoint(
            V2D_LineSegment l,
            V2D_LineSegment section,
            BigDecimal t) {
        Object[] r = new Object[2];
        V2D_LineSegment lineToIntersect;
        V2D_Point intersectPoint;
        V2D_Geometry intersection = l.getIntersection(section, t);
        if (intersection instanceof V2D_Point) {
            intersectPoint = (V2D_Point) intersection;
            lineToIntersect = new V2D_LineSegment(l.start, intersectPoint);
        } else {
            lineToIntersect = (V2D_LineSegment) intersection;
            intersectPoint = lineToIntersect.end;
        }
        r[0] = lineToIntersect;
        r[1] = intersectPoint;
        return r;
    }

    /**
     * TODO: Control precision! The angle returned is the smallest angle between
     * this and the x axis and so is between Math.PI and -Math.PI.
     *
     * @return The angle in radians to the x axis
     */
    public double getAngleToX_double() {
        BigDecimal dx = this.end.x.subtract(this.start.x);
        BigDecimal dy = this.end.y.subtract(this.start.y);
        return Math.atan2(dy.doubleValue(), dx.doubleValue());
    }

//    /**
//     * The angle returned is the smallest angle between this and the x axis and
//     * so is between Math.PI and -Math.PI.
//     * @return The angle in radians to the x axis
//     */
//    public double getAngleToX_double(){
//        double result;
//        BigDecimal dx = this.end.x.subtract(this.start.x);
//        BigDecimal dy = this.end.y.subtract(this.start.y);
//        double angleToX = Math.atan2(dy.doubleValue(),dx.doubleValue());
//        if (this.start.x.compareTo(this.end.x) == -1 ){
//            result = Math.PI - angleToX;
//        } else {
//            result = angleToX;
//        }
//        return result;
//    }
    /**
     * TODO: Control precision! The angle returned is the smallest angle between
     * this and the y axis and so is between Math.PI and -Math.PI.
     *
     * @return The angle in radians to the y axis
     */
    public double getAngleToY_double() {
        double result;
        BigDecimal dx = this.end.x.subtract(this.start.x);
        BigDecimal dy = this.end.y.subtract(this.start.y);
        double angleToY = Math.atan2(dx.doubleValue(), dy.doubleValue());
        if (this.start.y.compareTo(this.end.y) == -1) {
            result = Math.PI - angleToY;
        } else {
            result = angleToY;
        }
        return result;
    }

    /**
     * Assuming a_LineSegment.StartPoint == this.
     *
     * @param a_LineSegment2D V2D_LineSegment
     * @return BigDecimal
     */
    public BigDecimal getScalarProduct(
            V2D_LineSegment a_LineSegment2D) {
        if (a_LineSegment2D.start.compareTo(this.start) != 0) {
            System.out.println(
                    "a_LineSegment2D._Start_Point2D.compareTo(this._Start_Point2D) != 0 in "
                    + this.getClass().getName()
                    + ".getScalarProduct(LineSegment2D)");
        }
        return (a_LineSegment2D.end.x.multiply(this.end.x)).add((a_LineSegment2D.end.y.multiply(this.end.y)));
    }

    /**
     * Assuming a_LineSegment.StartPoint == this.
     *
     * @param a_LineSegment2D V2D_LineSegment
     * @return BigDecimal
     */
    public BigDecimal getCrossProduct(
            V2D_LineSegment a_LineSegment2D) {
        if (a_LineSegment2D.start.compareTo(this.start) != 0) {
            System.out.println(
                    "a_LineSegment2D._Start_Point2D.compareTo(this._Start_Point2D) != 0 in "
                    + this.getClass().getName()
                    + ".getCrossProduct(LineSegment2D)");
        }
        return (a_LineSegment2D.end.x.multiply(this.end.y)).subtract((this.end.x.multiply(a_LineSegment2D.end.y)));
    }
}
