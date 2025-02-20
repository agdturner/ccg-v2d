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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import uk.ac.leeds.ccg.math.geometry.Math_AngleDouble;
import uk.ac.leeds.ccg.v2d.core.d.V2D_EnvironmentDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.light.V2D_VTriangleDouble;

/**
 * For representing and processing triangles in 2D.
 *
 * @author Andy Turner
 * @version 2.0
 */
public class V2D_TriangleDouble extends V2D_ShapeDouble {

    private static final long serialVersionUID = 1L;

    /**
     * Defines one of the corners of the triangle.
     */
    protected V2D_VectorDouble pv;

    /**
     * Defines one of the corners of the triangle.
     */
    protected V2D_VectorDouble qv;

    /**
     * Defines one of the corners of the triangle.
     */
    protected V2D_VectorDouble rv;

    /**
     * For storing one corner of the triangle.
     */
    protected V2D_PointDouble p;

    /**
     * For storing one corner of the triangle.
     */
    protected V2D_PointDouble q;

    /**
     * For storing one corner of the triangle.
     */
    protected V2D_PointDouble r;

    /**
     * For storing the line segment from {@link #getP()} to {@link #getQ()} for
     * a given Order of Magnitude and RoundingMode precision.
     */
    protected V2D_LineSegmentDouble pq;

    /**
     * For storing the line segment from {@link #getQ()} to {@link #getR()} for
     * a given Order of Magnitude and RoundingMode precision.
     */
    protected V2D_LineSegmentDouble qr;

    /**
     * For storing the line segment from {@link #getR()} to {@link #getP()} for
     * a given Order of Magnitude and RoundingMode precision.
     */
    protected V2D_LineSegmentDouble rp;

//    /**
//     * For storing the centroid.
//     */
//    private V2D_PointDouble c;
    /**
     * Creates a new triangle.
     *
     * @param t The triangle to clone.
     */
    public V2D_TriangleDouble(V2D_TriangleDouble t) {
        super(t.env, new V2D_VectorDouble(t.offset));
        pv = new V2D_VectorDouble(t.pv);
        qv = new V2D_VectorDouble(t.qv);
        rv = new V2D_VectorDouble(t.rv);
    }

    /**
     * Creates a new triangle.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param t The triangle to initialise this from.
     */
    public V2D_TriangleDouble(V2D_EnvironmentDouble env,
            V2D_VectorDouble offset, V2D_VTriangleDouble t) {
        this(env, offset,
                new V2D_VectorDouble(t.pq.p),
                new V2D_VectorDouble(t.pq.q),
                new V2D_VectorDouble(t.qr.q));
    }

    /**
     * Creates a new triangle.{@link #offset} is set to
     * {@link V2D_VectorDouble#ZERO}.
     *
     * @param env What {@link #env} is set to.
     * @param pv What {@link #pv} is set to.
     * @param qv What {@link #qv} is set to.
     * @param rv What {@link #rv} is set to.
     */
    public V2D_TriangleDouble(V2D_EnvironmentDouble env, V2D_VectorDouble pv,
            V2D_VectorDouble qv, V2D_VectorDouble rv) {
        this(env, V2D_VectorDouble.ZERO, pv, qv, rv);
    }

    /**
     * Creates a new triangle. pv, qv and rv must all be different.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param pv What {@link #pv} is set to.
     * @param qv What {@link #qv} is set to.
     * @param rv What {@link #rv} is set to.
     */
    public V2D_TriangleDouble(V2D_EnvironmentDouble env,
            V2D_VectorDouble offset, V2D_VectorDouble pv,
            V2D_VectorDouble qv, V2D_VectorDouble rv) {
        super(env, offset);
        this.pv = pv;
        this.qv = qv;
        this.rv = rv;
        // Debugging code:
        if (pv.equals(env.epsilon, qv) || pv.equals(env.epsilon, rv)
                || qv.equals(env.epsilon, rv)) {
            throw new RuntimeException("pv.equals(qv, env.epsilon) "
                    + "|| pv.equals(rv, env.epsilon) "
                    + "|| qv.equals(rv, env.epsilon)");
        }
    }

    /**
     * Creates a new triangle.
     *
     * @param l A line segment representing one of the three edges of the
     * triangle.
     * @param r Defines the other point relative to l.offset that defines the
     * triangle.
     */
    public V2D_TriangleDouble(V2D_LineSegmentDouble l, V2D_VectorDouble r) {
        this(l.env,
                new V2D_VectorDouble(l.offset),
                new V2D_VectorDouble(l.l.pv),
                new V2D_VectorDouble(l.l.v),
                new V2D_VectorDouble(r));
    }

    /**
     * Creates a new instance.
     *
     * @param p Used to initialise {@link #offset} and {@link #pl}.
     * @param q Used to initialise {@link #qv}.
     * @param r Used to initialise {@link #rv}.
     */
    public V2D_TriangleDouble(V2D_PointDouble p, V2D_PointDouble q,
            V2D_PointDouble r) {
        this(p.env,
                new V2D_VectorDouble(p.offset),
                new V2D_VectorDouble(p.rel),
                q.getVector().subtract(p.offset),
                r.getVector().subtract(p.offset));
    }

    /**
     * Creates a new triangle.
     *
     * @param ls A line segment.
     * @param pt A point.
     */
    public V2D_TriangleDouble(V2D_LineSegmentDouble ls, V2D_PointDouble pt) {
        this(ls.getP(), ls.getQ(), pt);
    }

    /**
     * Creates a new triangle.
     *
     * @param env The environment.
     * @param offset What {@link #offset} is set to.
     * @param t The triangle to initialise this from.
     */
    public V2D_TriangleDouble(V2D_EnvironmentDouble env,
            V2D_VectorDouble offset, V2D_TriangleDouble t) {
        this(env, offset,
                new V2D_VectorDouble(t.pv).add(t.offset).subtract(offset),
                new V2D_VectorDouble(t.qv).add(t.offset).subtract(offset),
                new V2D_VectorDouble(t.rv).add(t.offset).subtract(offset));
    }

    /**
     * @return A new point based on {@link #pv} and {@link #offset}.
     */
    public final V2D_PointDouble getP() {
        if (p == null) {
            p = new V2D_PointDouble(env, offset, pv);
        }
        return p;
    }

    /**
     * @return A new point based on {@link #qv} and {@link #offset}.
     */
    public final V2D_PointDouble getQ() {
        if (q == null) {
            q = new V2D_PointDouble(env, offset, qv);
        }
        return q;
    }

    /**
     * @return A new point based on {@link #rv} and {@link #offset}.
     */
    public final V2D_PointDouble getR() {
        if (r == null) {
            r = new V2D_PointDouble(env, offset, rv);
        }
        return r;
    }

    /**
     * For getting the line segment from {@link #getP()} to {@link #getQ()}.
     *
     * @return Line segment from rv to pv.
     */
    public final V2D_LineSegmentDouble getPQ() {
        if (pq == null) {
            pq = new V2D_LineSegmentDouble(env, offset, pv, qv);
        }
        return pq;
    }

    /**
     * @return {@code qv.subtract(pv)}
     */
    public final V2D_VectorDouble getPQV() {
        return qv.subtract(pv);
    }

    /**
     * For getting the line segment from {@link #getQ()} to {@link #getR()}.
     *
     * @return Line segment from qv to rv.
     */
    public final V2D_LineSegmentDouble getQR() {
        if (qr == null) {
            qr = new V2D_LineSegmentDouble(env, offset, qv, rv);
        }
        return qr;
    }

    /**
     * @return {@code rv.subtract(qv)}
     */
    public final V2D_VectorDouble getQRV() {
        return rv.subtract(qv);
    }

    /**
     * For getting the line segment from {@link #getR()} to {@link #getP()}.
     *
     * @return Line segment from rv to pv.
     */
    public final V2D_LineSegmentDouble getRP() {
        if (rp == null) {
            rp = new V2D_LineSegmentDouble(env, offset, rv, pv);
        }
        return rp;
    }

    /**
     * @return {@code pv.subtract(rv)}
     */
    public final V2D_VectorDouble getRPV() {
        return pv.subtract(rv);
    }

    /**
     * @return The internal angle at {@link #pv}.
     */
    public final double getAngleP() {
        return getPQV().getAngle(getRPV().reverse());
    }

    /**
     * @return The internal angle at {@link #qv}.
     */
    public final double getAngleQ() {
        return getPQV().reverse().getAngle(getQRV());
    }

    /**
     * @return The internal angle at {@link #rv}.
     */
    public final double getAngleR() {
        return getQRV().reverse().getAngle(getRPV());
    }

    @Override
    public V2D_EnvelopeDouble getEnvelope() {
        if (en == null) {
            en = new V2D_EnvelopeDouble(getP(), getQ(), getR());
        }
        return en;
    }

    @Override
    public V2D_PointDouble[] getPointsArray() {
        V2D_PointDouble[] pts = new V2D_PointDouble[3];
        pts[0] = getP();
        pts[1] = getQ();
        pts[2] = getR();
        return pts;
    }

    @Override
    public HashMap<Integer, V2D_PointDouble> getPoints() {
        if (points == null) {
            points = new HashMap<>(3);
            points.put(0, getP());
            points.put(1, getQ());
            points.put(2, getR());
        }
        return points;
    }

    /**
     * @return A collection of the external edges.
     */
    @Override
    public HashMap<Integer, V2D_LineSegmentDouble> getEdges() {
        if (edges == null) {
            edges = new HashMap<>(3);
            edges.put(0, getPQ());
            edges.put(1, getQR());
            edges.put(2, getRP());
        }
        return edges;
    }

    /**
     * @param pt The point to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} is intersected by {@code pt}
     */
    public boolean intersects(V2D_PointDouble pt, double epsilon) {
        if (getEnvelope().intersects(pt.getEnvelope())) {
            return intersects0(pt, epsilon);
        }
        return false;
    }

    /**
     * @param pt The point to for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} contains {@code pt}.
     */
    public boolean contains(V2D_PointDouble pt, double epsilon) {
        if (intersects(pt, epsilon)) {
            return !(getPQ().intersects(pt)
                    || getQR().intersects(pt)
                    || getRP().intersects(pt));
        }
        return false;
    }

    /**
     * @param ls The line segment to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return True iff there is an intersection.
     */
    public boolean intersects(V2D_LineSegmentDouble ls, double epsilon) {
        if (ls.intersects(getEnvelope(), epsilon)) {
            return intersects0(ls, epsilon);
        } else {
            return false;
        }
    }

    /**
     * @param ls The line segment to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return True iff there is an intersection.
     */
    protected boolean intersects0(V2D_LineSegmentDouble ls, double epsilon) {
        // Ensure use of p, q and r. 
        getP();
        getQ();
        getR();
        // Get the points.
        V2D_PointDouble lsp = ls.getP();
        V2D_PointDouble lsq = ls.getQ();
        return (getPQ().l.isOnSameSide(r, lsp, epsilon)
                || pq.l.isOnSameSide(r, lsq, epsilon))
                && (getQR().l.isOnSameSide(p, lsp, epsilon)
                || qr.l.isOnSameSide(p, lsq, epsilon))
                && (getRP().l.isOnSameSide(q, lsp, epsilon)
                || rp.l.isOnSameSide(q, lsq, epsilon));
    }

    /**
     * @param ls The line segments to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return True iff there is an intersection.
     */
    public boolean intersects(double epsilon, ArrayList<V2D_LineSegmentDouble> ls) {
        return ls.parallelStream().anyMatch(x -> intersects(x, epsilon));
    }

    /**
     * Identify if this contains the line segment.
     *
     * @param ls The line segment to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} contains {@code ls}.
     */
    public boolean contains(V2D_LineSegmentDouble ls, double epsilon) {
        return contains(ls.getP(), epsilon)
                && contains(ls.getQ(), epsilon);
    }

    /**
     * Identify if this contains the triangle.
     *
     * @param t The triangle to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} contains {@code t}.
     */
    public boolean contains(V2D_TriangleDouble t, double epsilon) {
        return t.getPoints().values().parallelStream().allMatch(x
                -> contains(x, epsilon));
    }

    /**
     * The point pt aligns with this if it is on the inside of each plane
     * defined triangle edge (with a normal given by the cross product of the
     * triangle normal and the edge line vector) within a tolerance given by
     * epsilon.
     *
     * @param pt The point to check if it is in alignment.
     * @param epsilon The tolerance.
     * @return {@code true} iff pl is aligned with this.
     */
    public boolean intersects0(V2D_PointDouble pt, double epsilon) {
        return (getPQ().l.isOnSameSide(pt, getR(), epsilon)
                && getQR().l.isOnSameSide(pt, getP(), epsilon)
                && getRP().l.isOnSameSide(pt, getQ(), epsilon));
    }

    /**
     * A line segment aligns with this if both end points are aligned according
     * to
     * {@link #intersects0(uk.ac.leeds.ccg.v2d.geometry.d.V2D_PointDouble, double)}
     *
     * @param l The line segment to check if it is in alignment.
     * @param epsilon The tolerance.
     * @return {@code true} iff l is aligned with this.
     */
    public boolean isInteresctedBy0(V2D_LineSegmentDouble l, double epsilon) {
        if (intersects0(l.getP(), epsilon)) {
            return intersects0(l.getQ(), epsilon);
        }
        return getEdges().values().parallelStream().anyMatch(x
                -> x.intersects(l, epsilon));
    }

    /**
     * @param t The triangle to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return True iff there is an intersection.
     */
    public boolean intersects(V2D_TriangleDouble t, double epsilon) {
        //if (t.getEnvelope().intersects(getEnvelope())) {
        if (t.intersects(getEnvelope(), epsilon)) {
            return V2D_TriangleDouble.this.intersects0(t, epsilon);
        } else {
            return false;
        }
    }

    /**
     * @param t The triangle to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return True iff there is an intersection.
     */
    public boolean intersects0(V2D_TriangleDouble t, double epsilon) {
//        return (getPoints().values().parallelStream().anyMatch(x
//                -> t.intersects0(x, epsilon))
//                || t.getPoints().values().parallelStream().anyMatch(x
//                        -> intersects0(x, epsilon)));
        return intersects0(t.getP(), epsilon)
                || intersects0(t.getQ(), epsilon)
                || intersects0(t.getR(), epsilon)
                || t.intersects0(getP(), epsilon)
                || t.intersects0(getQ(), epsilon)
                || t.intersects0(getR(), epsilon);
    }

    /**
     * https://en.wikipedia.org/wiki/Heron%27s_formula
     *
     * @return The area of the triangle.
     */
    public double getArea() {
        double a = getPQ().getLength();
        double b = getQR().getLength();
        double c = getRP().getLength();
        return Math.sqrt(((a + b + c) / 2d) * ((b + c - a) / 2d) * ((a - b + c) / 2d) * ((a + b - c) / 2d));
    }

    /**
     * @return The perimeter.
     */
    public double getPerimeter() {
        return getPQ().getLength() + getQR().getLength() + getRP().getLength();
    }

    /**
     * @param l The line to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A point or line segment.
     */
    public V2D_FiniteGeometryDouble getIntersection(V2D_LineDouble l,
            double epsilon) {
        /**
         * Get the intersection of the line and each edge of the triangle.
         */
        V2D_FiniteGeometryDouble lpqi = getPQ().getIntersection(epsilon, l);
        V2D_FiniteGeometryDouble lqri = getQR().getIntersection(epsilon, l);
        V2D_FiniteGeometryDouble lrpi = getRP().getIntersection(epsilon, l);
        /**
         * This may appear overly complicated in parts, but due to imprecision
         * some odd cases may arise!
         */
        if (lpqi == null) {
            if (lqri == null) {
                return lrpi;
            } else if (lqri instanceof V2D_PointDouble lqrip) {
                if (lrpi == null) {
                    return lqri;
                } else if (lrpi instanceof V2D_PointDouble lrpip) {
                    return V2D_LineSegmentDouble.getGeometry(lqrip, lrpip,
                            epsilon);
                } else {
                    return V2D_LineSegmentDouble.getGeometry(
                            (V2D_LineSegmentDouble) lrpi, lqrip, epsilon);
                }
            } else {
                V2D_LineSegmentDouble lqril = (V2D_LineSegmentDouble) lqri;
                if (lrpi == null) {
                    return lqril;
                } else if (lrpi instanceof V2D_PointDouble lrpip) {
                    return V2D_LineSegmentDouble.getGeometry(lqril, lrpip,
                            epsilon);
                } else {
                    V2D_LineSegmentDouble lrpil = (V2D_LineSegmentDouble) lrpi;
                    return V2D_LineSegmentDouble.getGeometry(epsilon, lqril, lrpil);
                }
            }
        } else if (lpqi instanceof V2D_PointDouble lpqip) {
            if (lqri == null) {
                if (lrpi == null) {
                    return lpqi;
                } else {
                    return V2D_LineSegmentDouble.getGeometry(lpqip,
                            (V2D_PointDouble) lrpi, epsilon);
                }
            } else if (lqri instanceof V2D_PointDouble lqrip) {
                if (lrpi == null) {
                    return V2D_LineSegmentDouble.getGeometry(lqrip, lpqip,
                            epsilon);
                } else if (lrpi instanceof V2D_LineSegmentDouble) {
                    return lrpi;
                } else {
                    return getGeometry(lpqip, lqrip, (V2D_PointDouble) lrpi,
                            epsilon);
                }
            } else {
                return lqri;
            }
        } else {
            return lpqi;
        }
    }

    /**
     * Get the intersection between {@code this} and the ray {@code rv}.
     *
     * @param r The ray to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The V2D_Geometry.
     */
    public V2D_FiniteGeometryDouble getIntersection(V2D_RayDouble r,
            double epsilon) {
        V2D_FiniteGeometryDouble g = getIntersection(r.l, epsilon);
        if (g == null) {
            return null;
        }
        if (g instanceof V2D_PointDouble gp) {
            if (r.isAligned(gp, epsilon)) {
                return gp;
            } else {
                return null;
            }
        }
        V2D_LineSegmentDouble ls = (V2D_LineSegmentDouble) g;
        V2D_PointDouble lsp = ls.getP();
        V2D_PointDouble lsq = ls.getQ();
        if (r.getPl().isOnSameSide(lsp, r.l.getQ(), epsilon)) {
            if (r.getPl().isOnSameSide(lsq, r.l.getQ(), epsilon)) {
                return ls;
            } else {
                return V2D_LineSegmentDouble.getGeometry(r.l.getP(), lsp, epsilon);
            }
        } else {
            if (r.getPl().isOnSameSide(lsq, r.l.getQ(), epsilon)) {
                return V2D_LineSegmentDouble.getGeometry(r.l.getP(), lsq, epsilon);
            } else {
                throw new RuntimeException();
            }
        }
//            if (rv.intersects0(lsp, epsilon)) {
//                if (rv.intersects0(lsq, epsilon)) {
//                    return ls;
//                } else {
//                    return V2D_LineSegmentDouble.getGeometry(rv.l.getP(), lsp, epsilon);
//                }
//            } else {
//                if (rv.intersects0(lsq, epsilon)) {
//                    return V2D_LineSegmentDouble.getGeometry(rv.l.getP(), lsq, epsilon);
//                } else {
//                    throw new RuntimeException();
//                }
//            }
    }

    /**
     * Compute and return the intersection with the line segment.
     *
     * @param l The line segment to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The V2D_Geometry.
     */
    public V2D_FiniteGeometryDouble getIntersection(V2D_LineSegmentDouble l,
            double epsilon) {
        V2D_FiniteGeometryDouble g = getIntersection(l.l, epsilon);
        if (g == null) {
            return null;
        }
        if (g instanceof V2D_PointDouble gp) {
            if (intersects(gp, epsilon)) {
                if (l.isBetween(gp, epsilon)) {
                    return gp;
                }
            }
            return null;
        }
        V2D_LineSegmentDouble ls = (V2D_LineSegmentDouble) g;
        V2D_FiniteGeometryDouble lils = l.getIntersectionLS(epsilon, ls);
        if (lils == null) {
            return null;
        } else if (lils instanceof V2D_PointDouble lilsp) {
            if (intersects(lilsp, epsilon)) {
                return lilsp;
            } else {
                return null;
            }
        } else {
            V2D_LineSegmentDouble lilsl = (V2D_LineSegmentDouble) lils;
            if (intersects(lilsl, epsilon)) {
                return l.getIntersectionLS(epsilon, ls);
            } else {
                return null;
            }
        }
    }

    /**
     * Computes and returns the intersection between {@code this} and {@code t}.
     * The intersection could be: null, a point, a line segment, a triangle, or
     * a V2D_ConvexHullCoplanar (with 4, 5, or 6 sides).
     *
     * @param t The triangle intersect with this.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code t} and {@code this} or
     * {@code null} if there is no intersection.
     */
    public V2D_FiniteGeometryDouble getIntersection(V2D_TriangleDouble t,
            double epsilon) {
        if (getEnvelope().intersects(t.getEnvelope(), epsilon)) {
            /**
             * Get intersections between the triangle edges. If there are none,
             * then either this returns t or vice versa. If there are some, then
             * in some cases the result is a single triangle, and in others it
             * is a polygon which can be represented as a set of coplanar
             * triangles.
             */
            V2D_PointDouble ptp = getP();
            V2D_PointDouble ptq = getQ();
            V2D_PointDouble ptr = getR();
            if (t.intersects0(ptp, epsilon)
                    && t.intersects0(ptq, epsilon)
                    && t.intersects0(ptr, epsilon)) {
                return this;
            }
            V2D_PointDouble pttp = t.getP();
            V2D_PointDouble pttq = t.getQ();
            V2D_PointDouble pttr = t.getR();
            boolean pi = intersects(pttp, epsilon);
            boolean qi = intersects(pttq, epsilon);
            boolean ri = intersects(pttr, epsilon);
            if (pi && qi && ri) {
                return t;
            }
//            if (intersects0(t, epsilon)) {
//                return t;
//            }
//            if (t.intersects0(this, epsilon)) {
//                return this;
//            }
            V2D_FiniteGeometryDouble gpq = t.getIntersection(getPQ(), epsilon);
            V2D_FiniteGeometryDouble gqr = t.getIntersection(getQR(), epsilon);
            V2D_FiniteGeometryDouble grp = t.getIntersection(getRP(), epsilon);
            if (gpq == null) {
                if (gqr == null) {
                    if (grp == null) {
                        return null;
                    } else if (grp instanceof V2D_PointDouble grpp) {
                        return grpp;
                    } else {
                        if (qi) {
                            return getGeometry((V2D_LineSegmentDouble) grp, pttq, epsilon);
                        } else {
                            return grp;
                        }
                    }
                } else if (gqr instanceof V2D_PointDouble gqrp) {
                    if (grp == null) {
                        return gqr;
                    } else if (grp instanceof V2D_PointDouble grpp) {
                        return V2D_LineSegmentDouble.getGeometry(
                                gqrp, grpp, epsilon);
                    } else {
                        V2D_LineSegmentDouble ls = (V2D_LineSegmentDouble) grp;
                        return getGeometry(gqrp, ls.getP(), ls.getQ(), epsilon);
                    }
                } else {
                    V2D_LineSegmentDouble gqrl = (V2D_LineSegmentDouble) gqr;
                    if (grp == null) {
                        if (pi) {
                            return getGeometry(gqrl, pttp, epsilon);
                        } else {
                            return gqr;
                        }
                    } else if (grp instanceof V2D_PointDouble grpp) {
                        return getGeometry(grpp, gqrl.getP(),
                                gqrl.getQ(), epsilon);
                    } else {
                        return getGeometry((V2D_LineSegmentDouble) gqr,
                                (V2D_LineSegmentDouble) grp, epsilon);
                    }
                }
            } else if (gpq instanceof V2D_PointDouble gpqp) {
                if (gqr == null) {
                    if (grp == null) {
                        return gpq;
                    } else if (grp instanceof V2D_PointDouble grpp) {
                        return V2D_LineSegmentDouble.getGeometry(
                                gpqp, grpp, epsilon);
                    } else {
                        V2D_LineSegmentDouble ls = (V2D_LineSegmentDouble) grp;
                        return getGeometry(gpqp, ls.getP(),
                                ls.getQ(), epsilon);
                    }
                } else if (gqr instanceof V2D_PointDouble gqrp) {
                    if (grp == null) {
                        return gqr;
                    } else if (grp instanceof V2D_PointDouble grpp) {
                        return getGeometry(gpqp, gqrp, grpp, epsilon);
                    } else {
                        return getGeometry((V2D_LineSegmentDouble) grp,
                                gqrp, gpqp, epsilon);
                    }
                } else {
                    V2D_LineSegmentDouble ls = (V2D_LineSegmentDouble) gqr;
                    if (grp == null) {
                        return getGeometry(ls, gpqp, epsilon);
                    } else if (grp instanceof V2D_PointDouble grpp) {
                        return getGeometry(ls, grpp, gpqp, epsilon);
                    } else {
                        return getGeometry(ls, (V2D_LineSegmentDouble) grp,
                                gpqp, epsilon);
                    }
                }
            } else {
                V2D_LineSegmentDouble gpql = (V2D_LineSegmentDouble) gpq;
                if (gqr == null) {
                    if (grp == null) {

                        if (ri) {
                            return getGeometry(gpql, pttr, epsilon);
                        } else {
                            return gpq;
                        }

                    } else if (grp instanceof V2D_PointDouble grpp) {
                        return getGeometry(grpp, gpql.getP(), gpql.getQ(),
                                epsilon);
                    } else {
                        return getGeometry(gpql,
                                (V2D_LineSegmentDouble) grp, epsilon);
                    }
                } else if (gqr instanceof V2D_PointDouble gqrp) {
                    if (grp == null) {
                        if (gpql.intersects(gqrp, epsilon)) {
                            return gpql;
                        } else {
                            return new V2D_ConvexHullDouble(epsilon, gpql.getP(),
                                    gpql.getQ(), gqrp);
                        }
                    } else if (grp instanceof V2D_PointDouble grpp) {
                        ArrayList<V2D_PointDouble> pts = new ArrayList<>();
                        pts.add(gpql.getP());
                        pts.add(gpql.getQ());
                        pts.add(gqrp);
                        pts.add(grpp);
                        ArrayList<V2D_PointDouble> pts2 = V2D_PointDouble.getUnique(pts, epsilon);
                        return switch (pts2.size()) {
                            case 2 ->
                                new V2D_LineSegmentDouble(pts2.get(0), pts2.get(1));
                            case 3 ->
                                new V2D_TriangleDouble(pts2.get(0), pts2.get(1), pts2.get(2));
                            default ->
                                new V2D_ConvexHullDouble(epsilon, gpql.getP(),
                                gpql.getQ(), gqrp, grpp);
                        };
                    } else {
                        V2D_LineSegmentDouble grpl = (V2D_LineSegmentDouble) grp;
                        return V2D_ConvexHullDouble.getGeometry(
                                epsilon, gpql.getP(),
                                gpql.getQ(), gqrp, grpl.getP(),
                                grpl.getQ());
                    }
                } else {
                    V2D_LineSegmentDouble gqrl = (V2D_LineSegmentDouble) gqr;
                    if (grp == null) {
                        return V2D_ConvexHullDouble.getGeometry(
                                epsilon,
                                gpql.getP(), gpql.getQ(),
                                gqrl.getP(), gqrl.getQ());
                    } else if (grp instanceof V2D_PointDouble grpp) {
                        return V2D_ConvexHullDouble.getGeometry(
                                epsilon, gpql.getP(),
                                gpql.getQ(), gqrl.getP(),
                                gqrl.getQ(), grpp);
                    } else {
                        V2D_LineSegmentDouble grpl = (V2D_LineSegmentDouble) grp;
                        return V2D_ConvexHullDouble.getGeometry(
                                epsilon, gpql.getP(),
                                gpql.getQ(), gqrl.getP(),
                                gqrl.getQ(), grpl.getP(),
                                grpl.getQ());
                    }
                }
            }
        } else {
            return null;
        }
    }

    /**
     * Calculate and return the centroid as a point. The original implementation
     * used intersection, but it is simpler to get the average of the x, y and z
     * coordinates from the points at each vertex.
     *
     * @return The centroid point.
     */
    public V2D_PointDouble getCentroid() {
        double dx = (pv.dx + qv.dx + rv.dx) / 3d;
        double dy = (pv.dy + qv.dy + rv.dy) / 3d;
        return new V2D_PointDouble(env, offset, new V2D_VectorDouble(dx, dy));
    }

    /**
     * Test if two triangles are equal. Two triangles are equal if they have 3
     * coincident points, so even if the order is different and one is clockwise
     * and the other anticlockwise, or one faces the other way.
     *
     * @param t The other triangle to test for equality.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} is equal to {@code t}.
     */
    public boolean equals(V2D_TriangleDouble t, double epsilon) {
        return getPoints().values().parallelStream().allMatch(x
                -> x.equalsAny(t.getPoints().values(), epsilon));
//        V2D_PointDouble tp = t.getP();
//        V2D_PointDouble thisp = getP();
//        if (tp.equals(epsilon, thisp)) {
//            V2D_PointDouble tq = t.getQ();
//            V2D_PointDouble thisq = getQ();
//            if (tq.equals(epsilon, thisq)) {
//                return t.getR().equals(epsilon, getR());
//            } else if (tq.equals(epsilon, getR())) {
//                return t.getR().equals(epsilon, thisq);
//            } else {
//                return false;
//            }
//        } else if (tp.equals(epsilon, getQ())) {
//            V2D_PointDouble tq = t.getQ();
//            V2D_PointDouble thisr = getR();
//            if (tq.equals(epsilon, thisr)) {
//                return t.getR().equals(epsilon, thisp);
//            } else if (tq.equals(epsilon, thisp)) {
//                return t.getR().equals(epsilon, thisr);
//            } else {
//                return false;
//            }
//        } else if (tp.equals(epsilon, getR())) {
//            V2D_PointDouble tq = t.getQ();
//            if (tq.equals(epsilon, thisp)) {
//                return t.getR().equals(epsilon, getQ());
//            } else if (tq.equals(epsilon, getQ())) {
//                return t.getR().equals(epsilon, thisp);
//            } else {
//                return false;
//            }
//        } else {
//            return false;
//        }
    }

    @Override
    public void translate(V2D_VectorDouble v) {
        super.translate(v);
        if (en != null) {
            en.translate(v);
        }
        if (p != null) {
            p.translate(v);
        }
        if (q != null) {
            q.translate(v);
        }
        if (r != null) {
            r.translate(v);
        }
        if (pq != null) {
            pq.translate(v);
        }
        if (qr != null) {
            qr.translate(v);
        }
        if (rp != null) {
            rp.translate(v);
        }
    }

    @Override
    public V2D_TriangleDouble rotate(V2D_PointDouble pt, double theta) {
        theta = Math_AngleDouble.normalise(theta);
        if (theta == 0d) {
            return new V2D_TriangleDouble(this);
        } else {
            return rotateN(pt, theta);
        }
    }

    @Override
    public V2D_TriangleDouble rotateN(V2D_PointDouble pt, double theta) {
        return new V2D_TriangleDouble(
                getP().rotateN(pt, theta),
                getQ().rotateN(pt, theta),
                getR().rotateN(pt, theta));
    }

    /**
     * @param pad Padding
     * @return A String representation of this.
     */
    public String toString(String pad) {
        String res = pad + this.getClass().getSimpleName() + "(\n";
        res += pad + " offset=(" + this.offset.toString(pad + " ") + "),\n"
                + pad + " p=(" + this.pv.toString(pad + " ") + "),\n"
                + pad + " q=(" + this.qv.toString(pad + " ") + "),\n"
                + pad + " r=(" + this.rv.toString(pad + " ") + "))";
        return res;
    }

    /**
     * @param pad Padding
     * @return A simple String representation of this.
     */
    public String toStringSimple(String pad) {
        String res = pad + this.getClass().getSimpleName() + "(\n";
        res += pad + " offset=(" + this.offset.toStringSimple("") + "),\n"
                + pad + " p=(" + this.pv.toStringSimple("") + "),\n"
                + pad + " q=(" + this.qv.toStringSimple("") + "),\n"
                + pad + " r=(" + this.rv.toStringSimple("") + "))";
        return res;
    }

    @Override
    public String toString() {
        //return toString("");
        return toStringSimple("");
    }

    /**
     * If p, q and r are equal then the point is returned. If two of the points
     * are the same, then a line segment is returned. If all points are
     * different then if they are collinear a line segment is returned,
     * otherwise a triangle is returned.
     *
     * @param p A point.
     * @param q Another possibly equal point.
     * @param r Another possibly equal point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return Either a point, line segment or a triangle.
     */
    public static V2D_FiniteGeometryDouble getGeometry(V2D_PointDouble p,
            V2D_PointDouble q, V2D_PointDouble r, double epsilon) {
        if (p.equals(q)) {
            return V2D_LineSegmentDouble.getGeometry(p, r, epsilon);
        } else {
            if (q.equals(r)) {
                return V2D_LineSegmentDouble.getGeometry(q, p, epsilon);
            } else {
                if (r.equals(p)) {
                    return V2D_LineSegmentDouble.getGeometry(r, q, epsilon);
                } else {
                    if (V2D_LineDouble.isCollinear(epsilon, p, q, r)) {
                        V2D_LineSegmentDouble pq = new V2D_LineSegmentDouble(p, q);
                        if (pq.intersects(r, epsilon)) {
                            return pq;
                        } else {
                            V2D_LineSegmentDouble qr = new V2D_LineSegmentDouble(q, r);
                            if (qr.intersects(p, epsilon)) {
                                return qr;
                            } else {
                                return new V2D_LineSegmentDouble(p, r);
                            }
                        }
                    }
                    return new V2D_TriangleDouble(p, q, r);
                }
            }
        }
    }

    /**
     * Useful in intersecting two triangles. If l1, l2 and l3 are equal then the
     * line segment is returned. If there are 3 unique points then a triangle is
     * returned. If there are 4 or more unique points, then a V2D_ConvexHull is
     * returned.
     *
     * @param l1 A line segment.
     * @param l2 A line segment.
     * @param l3 A line segment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return either {@code pl} or {@code new V2D_LineSegment(pl, qv)} or
     * {@code new V2D_Triangle(pl, qv, rv)}
     */
    protected static V2D_FiniteGeometryDouble getGeometry(
            V2D_LineSegmentDouble l1, V2D_LineSegmentDouble l2,
            V2D_LineSegmentDouble l3, double epsilon) {
        V2D_PointDouble l1p = l1.getP();
        V2D_PointDouble l1q = l1.getQ();
        V2D_PointDouble l2p = l2.getP();
        V2D_PointDouble l2q = l2.getQ();
        V2D_PointDouble l3p = l3.getP();
        V2D_PointDouble l3q = l3.getQ();
        ArrayList<V2D_PointDouble> points;
        {
            List<V2D_PointDouble> pts = new ArrayList<>();
            pts.add(l1p);
            pts.add(l1q);
            pts.add(l2p);
            pts.add(l2q);
            pts.add(l3p);
            pts.add(l3q);
            points = V2D_PointDouble.getUnique(pts, epsilon);
        }
        int n = points.size();
        switch (n) {
            case 2 -> {
                return l1;
            }
            case 3 -> {
                Iterator<V2D_PointDouble> ite = points.iterator();
                return getGeometry(ite.next(), ite.next(), ite.next(), epsilon);
            }
            default -> {
                V2D_PointDouble[] pts = new V2D_PointDouble[points.size()];
                int i = 0;
                for (var p : points) {
                    pts[i] = p;
                    i++;
                }
                return new V2D_ConvexHullDouble(epsilon, pts);
            }
        }
    }

    /**
     * Useful in calculating the intersection of two triangles.If there are 3
     * unique points then a triangle is returned. If there are 4 or more unique
     * points, then a V2D_ConvexHullCoplanar is returned.
     *
     * @param l1 A line segment.
     * @param l2 A line segment.
     * @param pt A point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return either {@code pl} or {@code new V2D_LineSegment(pl, qv)} or
     * {@code new V2D_Triangle(pl, qv, rv)}
     */
    protected static V2D_FiniteGeometryDouble getGeometry(
            V2D_LineSegmentDouble l1, V2D_LineSegmentDouble l2,
            V2D_PointDouble pt, double epsilon) {
        V2D_PointDouble l1p = l1.getP();
        V2D_PointDouble l1q = l1.getQ();
        V2D_PointDouble l2p = l2.getP();
        V2D_PointDouble l2q = l2.getQ();
        ArrayList<V2D_PointDouble> points;
        {
            List<V2D_PointDouble> pts = new ArrayList<>();
            pts.add(l1p);
            pts.add(l1q);
            pts.add(l2p);
            pts.add(l2q);
            pts.add(pt);
            points = V2D_PointDouble.getUnique(pts, epsilon);
        }
        int n = points.size();
        switch (n) {
            case 2 -> {
                return l1;
            }
            case 3 -> {
                Iterator<V2D_PointDouble> ite = points.iterator();
                return getGeometry(ite.next(), ite.next(), ite.next(), epsilon);
            }
            default -> {
                V2D_PointDouble[] pts = new V2D_PointDouble[points.size()];
                int i = 0;
                for (var p : points) {
                    pts[i] = p;
                    i++;
                }
                return new V2D_ConvexHullDouble(epsilon, pts);
            }
        }
    }

    /**
     * Useful in calculating the intersection of two triangles.
     *
     * @param ab A line segment and triangle edge.
     * @param cd A line segment and triangle edge.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return a triangle for which l1 and l2 are edges
     */
    protected static V2D_FiniteGeometryDouble getGeometry(
            V2D_LineSegmentDouble ab, V2D_LineSegmentDouble cd,
            double epsilon) {
        V2D_FiniteGeometryDouble g = ab.getIntersection(epsilon, cd);
        if (g instanceof V2D_PointDouble pt) {
            V2D_PointDouble abp = ab.getP();
            V2D_PointDouble cdp = cd.getP();
            if (pt == null) {
                //V2D_TriangleDouble t = new V2D_TriangleDouble(cd, abp);
                return new V2D_ConvexHullDouble(epsilon, abp, cdp, ab.getQ(), cd.getQ());
            } else {
                if (abp.equals(pt, epsilon)) {
                    if (cdp.equals(pt, epsilon)) {
                        return new V2D_TriangleDouble(pt, ab.getQ(), cd.getQ());
                    } else {
                        return new V2D_TriangleDouble(pt, ab.getQ(), cdp);
                    }
                } else {
                    if (cdp.equals(pt, epsilon)) {
                        return new V2D_TriangleDouble(pt, abp, cd.getQ());
                    } else {
                        return new V2D_TriangleDouble(pt, abp, cdp);
                    }
                }
            }
        } else {
            return g;
        }
    }

    /**
     * Useful in calculating the intersection of two triangles.
     *
     * @param l A line segment.
     * @param a A point that is either not collinear to l or intersects l.
     * @param b A point that is either not collinear to l or intersects l.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return a triangle for which l is an edge and pl is a vertex.
     */
    protected static V2D_FiniteGeometryDouble getGeometry(
            V2D_LineSegmentDouble l, V2D_PointDouble a, V2D_PointDouble b,
            double epsilon) {
        if (l.intersects(a, epsilon)) {
            return getGeometry(l, b, epsilon);
        } else {
            return new V2D_TriangleDouble(a, l.getP(), l.getQ());
        }
    }

    /**
     * Useful in calculating the intersection of two triangles.
     *
     * @param l A line segment.
     * @param p A point that is not collinear to l.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return a triangle for which l is an edge and pl is a vertex.
     */
    protected static V2D_FiniteGeometryDouble getGeometry(
            V2D_LineSegmentDouble l, V2D_PointDouble p, double epsilon) {
        if (l.intersects(p, epsilon)) {
            return l;
        }
        return new V2D_TriangleDouble(p, l.getP(), l.getQ());
    }

    /**
     * For getting the point opposite a side of a triangle given the side.
     *
     * @param l A line segment equal to one of the edges of this triangle.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The point of {@code this} that does not intersect with {@code l}.
     */
    public V2D_PointDouble getOpposite(V2D_LineSegmentDouble l, double epsilon) {
        if (getPQ().equalsIgnoreDirection(epsilon, l)) {
            return getR();
        } else {
            if (getQR().equalsIgnoreDirection(epsilon, l)) {
                return getP();
            } else {
                return getQ();
            }
        }
    }

    /**
     * Get the minimum distance to {@code pv}.
     *
     * @param pt A point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The distance squared to {@code pv}.
     */
    public double getDistance(V2D_PointDouble pt, double epsilon) {
        return Math.sqrt(getDistanceSquared(pt, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code pt}.
     *
     * @param pt A point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The distance squared to {@code pt}.
     */
    public double getDistanceSquared(V2D_PointDouble pt, double epsilon) {
        if (intersects(pt, epsilon)) {
            return 0d;
        }
        return getDistanceSquaredEdge(pt, epsilon);
    }

    /**
     * Get the minimum distance squared to {@code pt} from the perimeter.
     *
     * @param pt A point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The distance squared between pt and the nearest edge of this.
     */
    public double getDistanceSquaredEdge(V2D_PointDouble pt, double epsilon) {
        double pqd2 = getPQ().getDistanceSquared(pt, epsilon);
        double qrd2 = getQR().getDistanceSquared(pt, epsilon);
        double rpd2 = getRP().getDistanceSquared(pt, epsilon);
        return Math.min(pqd2, Math.min(qrd2, rpd2));
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @param l A line.
     * @return The minimum distance to {@code l}.
     */
    public double getDistance(V2D_LineDouble l, double epsilon) {
        return Math.sqrt(getDistanceSquared(l, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance to {@code l}.
     */
    public double getDistanceSquared(V2D_LineDouble l, double epsilon) {
        double dpq2 = getPQ().getDistanceSquared(l, epsilon);
        double dqr2 = getQR().getDistanceSquared(l, epsilon);
        double drp2 = getRP().getDistanceSquared(l, epsilon);
        return Math.min(dpq2, Math.min(dqr2, drp2));
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line segment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance to {@code l}.
     */
    public double getDistance(V2D_LineSegmentDouble l, double epsilon) {
        return Math.sqrt(getDistanceSquared(l, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance to {@code l}.
     */
    public double getDistanceSquared(V2D_LineSegmentDouble l, double epsilon) {
        if (getIntersection(l, epsilon) != null) {
            return 0d;
        }
        double dlpq2 = l.getDistanceSquared(getPQ(), epsilon);
        double dlqr2 = l.getDistanceSquared(getQR(), epsilon);
        double dlrp2 = l.getDistanceSquared(getRP(), epsilon);
        double d2 = Math.min(dlpq2, Math.min(dlqr2, dlrp2));
        /**
         * For any end points of l that are aligned with this, calculate the
         * distances as these could be the minimum.
         */
        V2D_PointDouble lp = l.getP();
        V2D_PointDouble lq = l.getQ();
        if (intersects0(lp, epsilon)) {
            d2 = Math.min(d2, getDistanceSquared(lp, epsilon));
        }
        if (intersects0(lq, epsilon)) {
            d2 = Math.min(d2, getDistanceSquared(lq, epsilon));
        }
        return d2;
    }

    /**
     * Get the minimum distance to {@code t}.
     *
     * @param t A triangle.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance squared to {@code t}.
     */
    public double getDistance(V2D_TriangleDouble t, double epsilon) {
        return Math.sqrt(getDistanceSquared(t, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code t}.
     *
     * @param t A triangle.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance squared to {@code t}.
     */
    public double getDistanceSquared(V2D_TriangleDouble t, double epsilon) {
        if (getIntersection(t, epsilon) != null) {
            return 0d;
        }
        double dtpq2 = t.getDistanceSquared(getPQ(), epsilon);
        double dtqr2 = t.getDistanceSquared(getQR(), epsilon);
        double dtrp2 = t.getDistanceSquared(getRP(), epsilon);
        return Math.min(dtpq2, Math.min(dtqr2, dtrp2));
//        double dpq2 = getDistanceSquared(t.getPQ(), epsilon);
//        double dqr2 = getDistanceSquared(t.getQR(), epsilon);
//        double drp2 = getDistanceSquared(t.getRP(), epsilon);
//        return Math.min(dtpq2, Math.min(dtqr2, Math.min(dtrp2, Math.min(dpq2,
//                Math.min(dqr2, drp2)))));
    }

    /**
     * For retrieving a Set of points that are the corners of the triangles.
     *
     * @param triangles The input.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A Set of points that are the corners of the triangles.
     */
    //public static ArrayList<V2D_Point> getPoints(V2D_Triangle[] triangles) {
    public static V2D_PointDouble[] getPoints(V2D_TriangleDouble[] triangles,
            double epsilon) {
        List<V2D_PointDouble> s = new ArrayList<>();
        for (var t : triangles) {
            s.add(t.getP());
            s.add(t.getQ());
            s.add(t.getR());
        }
        ArrayList<V2D_PointDouble> points = V2D_PointDouble.getUnique(s, epsilon);
        return points.toArray(V2D_PointDouble[]::new);
    }

    /**
     * Computes and returns the circumcentre of the circmcircle.
     * https://en.wikipedia.org/wiki/Circumcircle
     *
     * @return The circumcentre of a circumcircle of this triangle.
     */
    public V2D_PointDouble getCircumcenter() {
        double ax = getP().getX();
        double ay = getP().getY();
        double bx = getQ().getX();
        double by = getQ().getY();
        double cx = getR().getX();
        double cy = getR().getY();
        double byscy = by - cy;
        double cysay = cy - ay;
        double aysby = ay - by;
        double d = 2D * ((ax * (byscy)) + (bx * (cysay) + (cx * (aysby))));
        double ax2aay2 = ((ax * ax) + (ay * ay));
        double bx2aby2 = ((bx * bx) + (by * by));
        double cx2acy2 = ((cx * cx) + (cy * cy));
        double ux = ((ax2aay2 * (byscy)) + (bx2aby2 * (cysay)) + (cx2acy2 * (aysby))) / d;
        double uy = ((ax2aay2 * (cx - bx)) + (bx2aby2 * (ax - cx)) + (cx2acy2 * (bx - ax))) / d;
        return new V2D_PointDouble(env, ux, uy);
    }

    /**
     * Identify if {@code this} is intersected by {@code aabb}.
     * 
     * @param aabb The envelope to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} is intersected by {@code aabb}.
     */
    public boolean intersects(V2D_EnvelopeDouble aabb, double epsilon) {
// Was resulting in java.util.ConcurrentModificationException!
//        return (getPoints().values().parallelStream().anyMatch(x
//                -> aabb.contains(x))
//                || aabb.getPoints().parallelStream().anyMatch(x
//                -> intersects(x, epsilon)));
        if (getEnvelope().intersects(aabb, epsilon)) {
            if (aabb.intersects(getP())
                    || aabb.intersects(getQ())
                    || aabb.intersects(getR())) {
                return true;
            }
            V2D_FiniteGeometryDouble l = aabb.getLeft();
            if (l instanceof V2D_LineSegmentDouble ll) {
                if (intersects(ll, epsilon)) {
                    return true;
                }
            } else {
                if (intersects((V2D_PointDouble) l, epsilon)) {
                    return true;
                }
            }
            V2D_FiniteGeometryDouble right = aabb.getRight();
            if (right instanceof V2D_LineSegmentDouble rl) {
                if (intersects(rl, epsilon)) {
                    return true;
                }
            } else {
                if (intersects((V2D_PointDouble) right, epsilon)) {
                    return true;
                }
            }
            V2D_FiniteGeometryDouble t = aabb.getTop();
            if (t instanceof V2D_LineSegmentDouble tl) {
                if (intersects(tl, epsilon)) {
                    return true;
                }
            } else {
                if (intersects((V2D_PointDouble) t, epsilon)) {
                    return true;
                }
            }
            V2D_FiniteGeometryDouble b = aabb.getBottom();
            if (b instanceof V2D_LineSegmentDouble bl) {
                if (intersects(bl, epsilon)) {
                    return true;
                }
            } else {
                if (intersects((V2D_PointDouble) b, epsilon)) {
                    return true;
                }
            }
        }
        return false;
    }
}
