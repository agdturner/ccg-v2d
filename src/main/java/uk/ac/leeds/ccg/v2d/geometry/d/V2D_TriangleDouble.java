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
package uk.ac.leeds.ccg.v2d.geometry.d;

import java.util.ArrayList;
import java.util.List;
import uk.ac.leeds.ccg.v2d.geometry.d.light.V2D_VTriangleDouble;

/**
 * For representing and processing triangles in 2D. A triangle has a non-zero
 * area. The corner points are {@link #pl}, {@link #q} and {@link #r}. The
 * following depicts a generic triangle {@code
 *   p                                               q
 * pv *- - - - - - - - - - - + - - - - - - - - - - -* qv
 *     \~                   mpq                   ~/
 *      \  ~                 |                 ~  /
 *       \    ~              |              ~    /
 *        \      ~           |           ~      /
 *   -n    \        ~        |        ~        /
 *          \n         ~     |     ~          /
 *           \      -n    ~  |  ~            /
 *            \              c              /
 *             \          ~  |  ~   +n     /
 *              \      ~     |     ~      / +n
 *               \  ~        |        ~  /          +n
 *                + mrp      |      mqr +                   +n
 *             rp  \         |         /  qr      normal heading out from the page.
 *                  \        |        /
 *                   \       |       /
 *                    \      |      /
 *                     \     |     /
 *                      \    |    /
 *                       \   |   /
 *                        \  |  /
 *                         \ | /
 *                          \|/
 *                           *
 *                           r
 * }
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_TriangleDouble extends V2D_FiniteGeometryDouble {

    private static final long serialVersionUID = 1L;

    /**
     * Defines one of the corners of the triangle.
     */
    protected V2D_VectorDouble p;

    /**
     * Defines one of the corners of the triangle.
     */
    protected V2D_VectorDouble q;

    /**
     * Defines one of the corners of the triangle.
     */
    protected V2D_VectorDouble r;

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

    /**
     * Creates a new triangle.
     *
     * @param t The triangle to clone.
     */
    public V2D_TriangleDouble(V2D_TriangleDouble t) {
        super(new V2D_VectorDouble(t.offset));
        p = new V2D_VectorDouble(t.p);
        q = new V2D_VectorDouble(t.q);
        r = new V2D_VectorDouble(t.r);
    }

    /**
     * Creates a new triangle.
     *
     * @param offset What {@link #offset} is set to.
     * @param t The triangle to initialise this from.
     */
    public V2D_TriangleDouble(V2D_VectorDouble offset, V2D_VTriangleDouble t) {
        this(offset, new V2D_VectorDouble(t.pq.p), new V2D_VectorDouble(t.pq.q),
                new V2D_VectorDouble(t.qr.q));
    }

    /**
     * Creates a new triangle. {@link #offset} is set to
     * {@link V2D_VectorDouble#ZERO}.
     *
     * @param p What {@link #pl} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     */
    public V2D_TriangleDouble(V2D_VectorDouble p, V2D_VectorDouble q,
            V2D_VectorDouble r) {
        this(V2D_VectorDouble.ZERO, p, q, r);
    }

    /**
     * Creates a new triangle.
     *
     * Warning p, q and r must all be different. No checks are done for
     * efficiency reasons.
     *
     * @param offset What {@link #offset} is set to.
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     */
    public V2D_TriangleDouble(V2D_VectorDouble offset, V2D_VectorDouble p,
            V2D_VectorDouble q, V2D_VectorDouble r) {
        super(offset);
        this.p = p;
        this.q = q;
        this.r = r;
        if (p.equals(q) || p.equals(r) || q.equals(r)) {
            throw new RuntimeException("p.equals(q) || p.equals(r) || q.equals(r)");
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
        this(new V2D_VectorDouble(l.offset), new V2D_VectorDouble(l.l.pv),
                new V2D_VectorDouble(l.qv), new V2D_VectorDouble(r));
    }

    /**
     * Creates a new instance.
     *
     * @param p Used to initialise {@link #offset} and {@link #pl}.
     * @param q Used to initialise {@link #q}.
     * @param r Used to initialise {@link #r}.
     */
    public V2D_TriangleDouble(V2D_PointDouble p, V2D_PointDouble q,
            V2D_PointDouble r) {
        this(new V2D_VectorDouble(p.offset), new V2D_VectorDouble(p.rel),
                q.getVector().subtract(p.offset), r.getVector().subtract(p.offset));
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
     * @param offset What {@link #offset} is set to.
     * @param t The triangle to initialise this from.
     */
    public V2D_TriangleDouble(V2D_VectorDouble offset, V2D_TriangleDouble t) {
        this(offset,
                new V2D_VectorDouble(t.p).add(t.offset).subtract(offset),
                new V2D_VectorDouble(t.q).add(t.offset).subtract(offset),
                new V2D_VectorDouble(t.r).add(t.offset).subtract(offset));
    }

    /**
     * @return A new point based on {@link #p} and {@link #offset}.
     */
    public final V2D_PointDouble getP() {
        return new V2D_PointDouble(offset, p);
    }

    /**
     * @return A new point based on {@link #q} and {@link #offset}.
     */
    public final V2D_PointDouble getQ() {
        return new V2D_PointDouble(offset, q);
    }

    /**
     * @return A new point based on {@link #r} and {@link #offset}.
     */
    public final V2D_PointDouble getR() {
        return new V2D_PointDouble(offset, r);
    }

    /**
     * For getting the line segment from {@link #getP()} to {@link #getQ()}.
     *
     * @return Line segment from r to pv.
     */
    public final V2D_LineSegmentDouble getPQ() {
        if (pq == null) {
            pq = new V2D_LineSegmentDouble(offset, p, q);
        }
        return pq;
    }

    /**
     * @return {@code q.subtract(p)}
     */
    public final V2D_VectorDouble getPQV() {
        return q.subtract(p);
    }

    /**
     * For getting the line segment from {@link #getQ()} to {@link #getR()}.
     *
     * @return Line segment from q to r.
     */
    public final V2D_LineSegmentDouble getQR() {
        if (qr == null) {
            qr = new V2D_LineSegmentDouble(offset, q, r);
        }
        return qr;
    }

    /**
     * @return {@code r.subtract(q)}
     */
    public final V2D_VectorDouble getQRV() {
        return r.subtract(q);
    }

    /**
     * For getting the line segment from {@link #getR()} to {@link #getP()}.
     *
     * @return Line segment from r to p.
     */
    public final V2D_LineSegmentDouble getRP() {
        if (rp == null) {
            rp = new V2D_LineSegmentDouble(offset, r, p);
        }
        return rp;
    }

    /**
     * @return {@code p.subtract(r)}
     */
    public final V2D_VectorDouble getRPV() {
        return p.subtract(r);
    }

    @Override
    public V2D_EnvelopeDouble getEnvelope() {
        if (en == null) {
            en = new V2D_EnvelopeDouble(getP(), getQ(), getR());
        }
        return en;
    }

    @Override
    public V2D_PointDouble[] getPoints() {
        V2D_PointDouble[] re = new V2D_PointDouble[3];
        re[0] = getP();
        re[1] = getQ();
        re[2] = getR();
        return re;
    }

    /**
     * @param pt The point to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A point or line segment.
     */
    public boolean isIntersectedBy(V2D_PointDouble pt, double epsilon) {
        if (getEnvelope().isIntersectedBy(pt.getEnvelope(), epsilon)) {
            return isAligned(pt, epsilon);
        }
        return false;
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
    public boolean isAligned(V2D_PointDouble pt, double epsilon) {
        if (getPQ().l.isOnSameSide(pt, getR(), epsilon)) {
            if (getQR().l.isOnSameSide(pt, getP(), epsilon)) {
                if (getRP().l.isOnSameSide(pt, getQ(), epsilon)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * A line segment aligns with this if both end points are aligned according
     * to
     * {@link #isAligned(uk.ac.leeds.ccg.v2d.geometry.d.V2D_PointDouble, double)}
     *
     * @param l The line segment to check if it is in alignment.
     * @param epsilon The tolerance.
     * @return {@code true} iff l is aligned with this.
     */
    public boolean isAligned(V2D_LineSegmentDouble l, double epsilon) {
        if (isAligned(l.getP(), epsilon)) {
            return isAligned(l.getQ(), epsilon);
        }
        return false;
    }

    /**
     * A triangle aligns with this if all points are aligned according to
     * {@link #isAligned(uk.ac.leeds.ccg.v2d.geometry.d.V2D_PointDouble, double)}.
     *
     * @param t The triangle to check if it is in alignment.
     * @param epsilon The tolerance.
     * @return {@code true} iff l is aligned with this.
     */
    public boolean isAligned(V2D_TriangleDouble t, double epsilon) {
        if (isAligned(t.getP(), epsilon)) {
            if (isAligned(t.getQ(), epsilon)) {
                return isAligned(t.getR(), epsilon);
            }
        }
        return false;
    }

    /**
     * https://en.wikipedia.org/wiki/Heron%27s_formula
     *
     * @return The area of the triangle.
     */
    public double getArea() {
        // Calculate s
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
        if (lpqi == null) {
            if (lqri == null) {
                return null;
            } else {
                if (lrpi == null) {
                    return lqri;
                } else {
                    return V2D_LineSegmentDouble.getGeometry(
                            (V2D_PointDouble) lqri, (V2D_PointDouble) lrpi,
                            epsilon);
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
     * Get the intersection between the geometry and the ray {@code r}.
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
        } else if (g instanceof V2D_PointDouble gp) {
//            if (r.getPl().isOnSameSide(gp, r.l.getQ(), epsilon)) {
//                return gp;
//            } else {
//                return null;
//            }
            if (r.isAligned(gp, epsilon)) {
                return gp;
            } else {
                return null;
            }
        } else {
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
//            if (r.isAligned(lsp, epsilon)) {
//                if (r.isAligned(lsq, epsilon)) {
//                    return ls;
//                } else {
//                    return V2D_LineSegmentDouble.getGeometry(r.l.getP(), lsp, epsilon);
//                }
//            } else {
//                if (r.isAligned(lsq, epsilon)) {
//                    return V2D_LineSegmentDouble.getGeometry(r.l.getP(), lsq, epsilon);
//                } else {
//                    throw new RuntimeException();
//                }
//            }
        }
    }

    /**
     * Get the intersection between the geometry and the line segment {@code l}.
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
            if (isAligned(gp, epsilon)) {
                if (l.isBetween(gp, epsilon)) {
                    return gp;
                }
            }
            return null;
        }
        V2D_LineSegmentDouble ls = (V2D_LineSegmentDouble) g;
        if (ls.isBetween(l.getP(), epsilon) || ls.isBetween(l.getQ(), epsilon)
                || l.isBetween(getP(), epsilon)) {
            return l.getIntersectionLS(epsilon, (V2D_LineSegmentDouble) g);
        } else {
            return null;
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
        if (getEnvelope().isIntersectedBy(t.getEnvelope(), epsilon)) {
            /**
             * Get intersections between the triangle edges. If there are none,
             * then either this returns t or vice versa. If there are some, then
             * in some cases the result is a single triangle, and in others it
             * is a polygon which can be represented as a set of coplanar
             * triangles.
             */
            // Check if vertices intersect
            V2D_PointDouble pttp = t.getP();
            V2D_PointDouble pttq = t.getQ();
            V2D_PointDouble pttr = t.getR();
            boolean pi = isIntersectedBy(pttp, epsilon);
            boolean qi = isIntersectedBy(pttq, epsilon);
            boolean ri = isIntersectedBy(pttr, epsilon);
            if (pi && qi && ri) {
                return t;
            }
            V2D_PointDouble ptp = getP();
            V2D_PointDouble ptq = getQ();
            V2D_PointDouble ptr = getR();
            boolean pit = t.isIntersectedBy(ptp, epsilon);
            boolean qit = t.isIntersectedBy(ptq, epsilon);
            boolean rit = t.isIntersectedBy(ptr, epsilon);
            if (pit && qit && rit) {
                return this;
            }
            if (isAligned(t, epsilon)) {
                return t;
            }
            if (t.isAligned(this, epsilon)) {
                return this;
            }
            V2D_FiniteGeometryDouble gpq = t.getIntersection(getPQ(), epsilon);
            V2D_FiniteGeometryDouble gqr = t.getIntersection(getQR(), epsilon);
            V2D_FiniteGeometryDouble grp = t.getIntersection(getRP(), epsilon);
            if (gpq == null) {
                if (gqr == null) {
                    
                    if (grp == null) {
                        return null;
                    } else if (grp instanceof V2D_PointDouble grpp) {
                        return grp;
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
                        if (gpql.isIntersectedBy(gqrp, epsilon)) {
                            return gpql;
                        } else {
                            return new V2D_ConvexHullDouble(
                                    epsilon, gpql.getP(),
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
                                new V2D_ConvexHullDouble(
                                epsilon, gpql.getP(),
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
        double dx = (p.dx + q.dx + r.dx) / 3d;
        double dy = (p.dy + q.dy + r.dy) / 3d;
        return new V2D_PointDouble(offset, new V2D_VectorDouble(dx, dy));
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
        V2D_PointDouble tp = t.getP();
        V2D_PointDouble thisp = getP();
        if (tp.equals(epsilon, thisp)) {
            V2D_PointDouble tq = t.getQ();
            V2D_PointDouble thisq = getQ();
            if (tq.equals(epsilon, thisq)) {
                return t.getR().equals(epsilon, getR());
            } else if (tq.equals(epsilon, getR())) {
                return t.getR().equals(epsilon, thisq);
            } else {
                return false;
            }
        } else if (tp.equals(epsilon, getQ())) {
            V2D_PointDouble tq = t.getQ();
            V2D_PointDouble thisr = getR();
            if (tq.equals(epsilon, thisr)) {
                return t.getR().equals(epsilon, thisp);
            } else if (tq.equals(epsilon, thisp)) {
                return t.getR().equals(epsilon, thisr);
            } else {
                return false;
            }
        } else if (tp.equals(epsilon, getR())) {
            V2D_PointDouble tq = t.getQ();
            if (tq.equals(epsilon, thisp)) {
                return t.getR().equals(epsilon, getQ());
            } else if (tq.equals(epsilon, getQ())) {
                return t.getR().equals(epsilon, thisp);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void translate(V2D_VectorDouble v) {
        super.translate(v);
        if (en != null) {
            en.translate(v);
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
    public V2D_TriangleDouble rotate(V2D_PointDouble pt, double theta, double epsilon) {
        return new V2D_TriangleDouble(
                getP().rotate(pt, theta, epsilon),
                getQ().rotate(pt, theta, epsilon),
                getR().rotate(pt, theta, epsilon));
    }

    /**
     * @param pad Padding
     * @return A String representation of this.
     */
    public String toString(String pad) {
        String res = pad + this.getClass().getSimpleName() + "(\n";
        res += pad + " offset=(" + this.offset.toString(pad + " ") + "),\n"
                + pad + " p=(" + this.p.toString(pad + " ") + "),\n"
                + pad + " q=(" + this.q.toString(pad + " ") + "),\n"
                + pad + " r=(" + this.r.toString(pad + " ") + "))";
        return res;
    }

    /**
     * @param pad Padding
     * @return A simple String representation of this.
     */
    public String toStringSimple(String pad) {
        String res = pad + this.getClass().getSimpleName() + "(\n";
        res += pad + " offset=(" + this.offset.toStringSimple("") + "),\n"
                + pad + " p=(" + this.p.toStringSimple("") + "),\n"
                + pad + " q=(" + this.q.toStringSimple("") + "),\n"
                + pad + " r=(" + this.r.toStringSimple("") + "))";
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
                        //return V2D_LineSegmentDouble.getGeometry(p, q, r, epsilon);
                        V2D_LineSegmentDouble pq = new V2D_LineSegmentDouble(p, q);
                        if (pq.isIntersectedBy(r, epsilon)) {
                            return pq;
                        } else {
                            V2D_LineSegmentDouble qr = new V2D_LineSegmentDouble(q, r);
                            if (qr.isIntersectedBy(p, epsilon)) {
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
     * Useful in calculating the intersection of two triangles. If there are 3
     * unique points then a triangle is returned. If there are 4 or more unique
     * points, then a V2D_ConvexHullCoplanar is returned.
     *
     * @param l1 A line segment.
     * @param l2 A line segment.
     * @param pt A point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return either {@code pl} or {@code new V2D_LineSegment(pl, qv)} or
     * {@code new V2D_Triangle(pl, qv, r)}
     */
    protected static V2D_FiniteGeometryDouble getGeometry(
            V2D_LineSegmentDouble l1, V2D_LineSegmentDouble l2,
            V2D_PointDouble pt, double epsilon) {
//        V2D_PointDouble l1p = l1.getP();
//        V2D_PointDouble l1q = l1.getQ();
//        V2D_PointDouble l2p = l2.getP();
//        V2D_PointDouble l2q = l2.getQ();
//        ArrayList<V2D_PointDouble> points;
//        {
//            List<V2D_PointDouble> pts = new ArrayList<>();
//            pts.add(l1p);
//            pts.add(l1q);
//            pts.add(l2p);
//            pts.add(l2q);
//            pts.add(pt);
//            points = V2D_PointDouble.getUnique(pts, epsilon);
//        }
//        int n = points.size();
//        if (n == 2) {
//            return l1;
//        } else if (n == 3) {
//            Iterator<V2D_PointDouble> ite = points.iterator();
//            return getGeometry(ite.next(), ite.next(), ite.next(), epsilon);
//        } else {
//            V2D_PointDouble[] pts = new V2D_PointDouble[points.size()];
//            int i = 0;
//            for (var p : points) {
//                pts[i] = p;
//                i++;
//            }
//            return new V2D_ConvexHullDouble(epsilon, pts);
//        }
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

//    /**
//     * Used in intersecting a triangle and a tetrahedron. If there are 3 unique
//     * points then a triangle is returned. If there are 4 points, then a
//     * V2D_ConvexHullCoplanar is returned.
//     *
//     * @param l1 A line segment.
//     * @param l2 A line segment.
//     * @param epsilon The tolerance within which two vectors are regarded as
//     * equal.
//     * @return either {@code pl} or {@code new V2D_LineSegment(pl, qv)} or
//     * {@code new V2D_Triangle(pl, qv, r)}
//     */
//    protected static V2D_FiniteGeometryDouble getGeometry2(
//            V2D_LineSegmentDouble l1, V2D_LineSegmentDouble l2, double epsilon) {
//        V2D_PointDouble l1p = l1.getP();
//        V2D_PointDouble l1q = l1.getQ();
//        V2D_PointDouble l2p = l2.getP();
//        V2D_PointDouble l2q = l2.getQ();
//        ArrayList<V2D_PointDouble> points;
//        {
//            List<V2D_PointDouble> pts = new ArrayList<>();
//            pts.add(l1p);
//            pts.add(l1q);
//            pts.add(l2p);
//            pts.add(l2q);
//            points = V2D_PointDouble.getUnique(pts, epsilon);
//        }
//        int n = points.size();
//        switch (n) {
//            case 2:
//                return l1;
//            case 3:
//                Iterator<V2D_PointDouble> ite = points.iterator();
//                return getGeometry(ite.next(), ite.next(), ite.next(), epsilon);
//            default:
//                V2D_PointDouble[] pts = new V2D_PointDouble[points.size()];
//                int i = 0;
//                for (var p : points) {
//                    pts[i] = p;
//                    i++;
//                }
//                V2D_PlaneDouble pl = new V2D_PlaneDouble(pts[0], pts[1], pts[2]);
//                return new V2D_ConvexHullCoplanarDouble(pl.n, epsilon, pts);
//        }
//    }
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
        V2D_PointDouble pt = (V2D_PointDouble) ab.getIntersection(epsilon, cd);
        V2D_PointDouble abp = ab.getP();
        V2D_PointDouble cdp = cd.getP();
        if (pt == null) {
            //V2D_TriangleDouble t = new V2D_TriangleDouble(cd, abp);
            return new V2D_ConvexHullDouble(epsilon, abp, cdp, ab.getQ(), cd.getQ());
        } else {
            if (abp.equals(epsilon, pt)) {
                if (cdp.equals(epsilon, pt)) {
                    return new V2D_TriangleDouble(pt, ab.getQ(), cd.getQ());
                } else {
                    return new V2D_TriangleDouble(pt, ab.getQ(), cdp);
                }
            } else {
                if (cdp.equals(epsilon, pt)) {
                    return new V2D_TriangleDouble(pt, abp, cd.getQ());
                } else {
                    return new V2D_TriangleDouble(pt, abp, cdp);
                }
            }
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
        if (l.isIntersectedBy(a, epsilon)) {
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
        if (l.isIntersectedBy(p, epsilon)) {
            return l;
        }
        return new V2D_TriangleDouble(p, l.getP(), l.getQ());
    }

    /**
     * For getting the point opposite a side of a triangle given the side.
     *
     * @param l a line segment either equal to one of the edges of this: null
     * null null null null null null null null null null null null null null
     * null null null null null null null null null null null null null null
     * null null null null     {@link #getPQ()},
     * {@link #getQR()} or {@link #getRP()}.
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
        if (isIntersectedBy(pt, epsilon)) {
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
        if (isAligned(lp, epsilon)) {
            d2 = Math.min(d2, getDistanceSquared(lp, epsilon));
        }
        if (isAligned(lq, epsilon)) {
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
     * Clips this using t.
     *
     * @param t The triangle to clip this with.
     * @param pt A point that is used to return the side of this that is
     * clipped.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return null, the whole or a part of this.
     */
    public V2D_FiniteGeometryDouble clip(V2D_TriangleDouble t,
            V2D_PointDouble pt, double epsilon) {
//        V2D_PointDouble tp = t.getP();
//        V2D_PointDouble tq = t.getQ();
//        V2D_PointDouble tr = t.getR();
//        V2D_VectorDouble n = t.pl.n;
//        V2D_PointDouble ppt = new V2D_PointDouble(tp.offset.add(n), tp.rel);
//        V2D_PlaneDouble ppl = new V2D_PlaneDouble(tp, tq, ppt);
//        V2D_PointDouble qpt = new V2D_PointDouble(tq.offset.add(n), tq.rel);
//        V2D_PlaneDouble qpl = new V2D_PlaneDouble(tq, tr, qpt);
//        V2D_PointDouble rpt = new V2D_PointDouble(tr.offset.add(n), tr.rel);
//        V2D_PlaneDouble rpl = new V2D_PlaneDouble(tr, tp, rpt);
//        V2D_FiniteGeometryDouble cppl = clip(ppl, pt, epsilon);
//        if (cppl == null) {
//            return null;
//        } else if (cppl instanceof V2D_PointDouble) {
//            return cppl;
//        } else if (cppl instanceof V2D_LineSegmentDouble cppll) {
//            V2D_FiniteGeometryDouble cppllcqpl = cppll.clip(qpl, pt, epsilon);
//            if (cppllcqpl == null) {
//                return null;
//            } else if (cppllcqpl instanceof V2D_PointDouble cppllcqplp) {
//                return getGeometry(cppll, cppllcqplp, epsilon);
//                //return cppllcqpl;
//            } else {
//                return ((V2D_LineSegmentDouble) cppllcqpl).clip(rpl, pt, epsilon);
//            }
//        } else if (cppl instanceof V2D_TriangleDouble cpplt) {
//            V2D_FiniteGeometryDouble cppltcqpl = cpplt.clip(qpl, pt, epsilon);
//            if (cppltcqpl == null) {
//                return null;
//            } else if (cppltcqpl instanceof V2D_PointDouble) {
//                return cppltcqpl;
//            } else if (cppltcqpl instanceof V2D_LineSegmentDouble cppltcqpll) {
//                return cppltcqpll.clip(rpl, pt, epsilon);
//            } else if (cppltcqpl instanceof V2D_TriangleDouble cppltcqplt) {
//                return cppltcqplt.clip(rpl, pt, epsilon);
//            } else {
//                V2D_ConvexHullCoplanarDouble c = (V2D_ConvexHullCoplanarDouble) cppltcqpl;
//                return c.clip(rpl, pt, epsilon);
//            }
//        } else {
//            V2D_ConvexHullCoplanarDouble c = (V2D_ConvexHullCoplanarDouble) cppl;
//            V2D_FiniteGeometryDouble cc = c.clip(qpl, pt, epsilon);
//            if (cc == null) {
//                return cc;
//            } else if (cc instanceof V2D_PointDouble) {
//                return cc;
//            } else if (cc instanceof V2D_LineSegmentDouble cppll) {
//                V2D_FiniteGeometryDouble cccqpl = cppll.clip(qpl, pt, epsilon);
//                if (cccqpl == null) {
//                    return null;
//                } else if (cccqpl instanceof V2D_PointDouble) {
//                    return cccqpl;
//                } else {
//                    return ((V2D_LineSegmentDouble) cccqpl).clip(rpl, pt, epsilon);
//                }
//            } else if (cc instanceof V2D_TriangleDouble ccct) {
//                return ccct.clip(rpl, pt, epsilon);
//            } else {
//                V2D_ConvexHullCoplanarDouble ccc = (V2D_ConvexHullCoplanarDouble) cc;
//                return ccc.clip(rpl, pt, epsilon);
//            }
//        }
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isIntersectedBy(V2D_EnvelopeDouble aabb, double epsilon) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
