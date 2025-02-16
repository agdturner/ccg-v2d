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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v2d.core.V2D_Environment;
import uk.ac.leeds.ccg.v2d.geometry.light.V2D_VTriangle;

/**
 * For representing and processing triangles in 2D.
 *
 * @author Andy Turner
 * @version 2.0
 */
public class V2D_Triangle extends V2D_Shape {

    private static final long serialVersionUID = 1L;

    /**
     * A vector for a corner of the triangle. This is invariant under
     * translation and is relative to the other vector corners.
     */
    protected final V2D_Vector pv;

    /**
     * A vector for a corner of the triangle. This is invariant under
     * translation and is relative to the other vector corners.
     */
    protected final V2D_Vector qv;

    /**
     * A vector for a corner of the triangle. This is invariant under
     * translation and is relative to the other vector corners.
     */
    protected final V2D_Vector rv;

    /**
     * A point for a corner of the triangle.
     */
    protected V2D_Point p;

    /**
     * The Order of Magnitude for the precision of {@link #p}.
     */
    int poom;

    /**
     * The RoundingMode used for the calculation of {@link #p} or {@code null}
     * if {@link #p} was input and not calculated.
     */
    RoundingMode prm;

    /**
     * A point for a corner of the triangle.
     */
    protected V2D_Point q;

    /**
     * The Order of Magnitude for the precision of {@link #q}.
     */
    int qoom;

    /**
     * The RoundingMode used for the calculation of {@link #q} or {@code null}
     * if {@link #q} was input and not calculated.
     */
    RoundingMode qrm;

    /**
     * A point for a corner of the triangle.
     */
    protected V2D_Point r;

    /**
     * The Order of Magnitude for the precision of {@link #r}.
     */
    int room;

    /**
     * The RoundingMode used for the calculation of {@link #r} or {@code null}
     * if {@link #r} was input and not calculated.
     */
    RoundingMode rrm;

    /**
     * For storing the line segment from {@link #getP()} to {@link #getQ()} for
     * a given Order of Magnitude and RoundingMode precision.
     */
    private V2D_LineSegment pq;

    /**
     * The Order of Magnitude for the precision of {@link #pq}.
     */
    int pqoom;

    /**
     * The RoundingMode used for the calculation of {@link #pq}.
     */
    RoundingMode pqrm;

    /**
     * For storing the line segment from {@link #getQ()} to {@link #getR()} for
     * a given Order of Magnitude and RoundingMode precision.
     */
    private V2D_LineSegment qr;

    /**
     * The Order of Magnitude for the precision of {@link #qr}.
     */
    int qroom;

    /**
     * The RoundingMode used for the calculation of {@link #qr}.
     */
    RoundingMode qrrm;

    /**
     * For storing the line segment from {@link #getR()} to {@link #getP()} for
     * a given Order of Magnitude and RoundingMode precision.
     */
    private V2D_LineSegment rp;

    /**
     * The Order of Magnitude for the precision of {@link #rp}.
     */
    int rpoom;

    /**
     * The RoundingMode used for the calculation of {@link #rp}.
     */
    RoundingMode rprm;

//    /**
//     * For storing the centroid.
//     */
//    private V2D_Point c;
    /**
     * Creates a new triangle.
     *
     * @param t The triangle to clone.
     */
    public V2D_Triangle(V2D_Triangle t) {
        super(t.env, new V2D_Vector(t.offset));
        pv = new V2D_Vector(t.pv);
        qv = new V2D_Vector(t.qv);
        rv = new V2D_Vector(t.rv);
        if (t.p != null) {
            p = t.p;
            poom = t.poom;
            prm = t.prm;
        }
        if (t.q != null) {
            q = t.q;
            qoom = t.qoom;
            qrm = t.qrm;
        }
        if (t.r != null) {
            r = t.r;
            room = t.room;
            rrm = t.rrm;
        }
        if (t.pq != null) {
            pq = t.pq;
            pqoom = t.pqoom;
            pqrm = t.pqrm;
        }
        if (t.qr != null) {
            qr = t.qr;
            qroom = t.qroom;
            qrrm = t.qrrm;
        }
        if (t.rp != null) {
            rp = t.rp;
            rpoom = t.rpoom;
            rprm = t.rprm;
        }
    }

    /**
     * Creates a new triangle.
     *
     * @param env The environment.
     * @param offset What {@link #offset} is set to.
     * @param t The triangle to initialise this from.
     */
    public V2D_Triangle(V2D_Environment env, V2D_Vector offset,
            V2D_VTriangle t) {
        this(env, offset,
                new V2D_Vector(t.pq.p),
                new V2D_Vector(t.pq.q),
                new V2D_Vector(t.qr.q));
    }

    /**
     * Creates a new triangle.{@link #offset} is set to {@link V2D_Vector#ZERO}.
     *
     * @param env The environment.
     * @param p What {@link #pl} is set to.
     * @param q What {@link #qv} is set to.
     * @param r What {@link #rv} is set to.
     */
    public V2D_Triangle(V2D_Environment env, V2D_Vector p, V2D_Vector q,
            V2D_Vector r) {
        this(env, V2D_Vector.ZERO, p, q, r);
    }

    /**
     * Creates a new triangle. pv, qv and rv must all be different according to
     * the {@code env.oom} and {@code env.rm}.
     *
     * @param env The environment.
     * @param offset What {@link #offset} is set to.
     * @param pv What {@link #pv} is set to.
     * @param qv What {@link #qv} is set to.
     * @param rv What {@link #rv} is set to.
     */
    public V2D_Triangle(V2D_Environment env, V2D_Vector offset, V2D_Vector pv,
            V2D_Vector qv, V2D_Vector rv) {
        super(env, offset);
        this.pv = pv;
        this.qv = qv;
        this.rv = rv;
        // Debugging code:
        if (pv.equals(qv, env.oom, env.rm) || pv.equals(rv, env.oom, env.rm)
                || qv.equals(rv, env.oom, env.rm)) {
            throw new RuntimeException("pv.equals(qv, env.oom, env.rm) "
                    + "|| pv.equals(rv, env.oom, env.rm) "
                    + "|| qv.equals(rv, env.oom, env.rm)");
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
    public V2D_Triangle(V2D_LineSegment l, V2D_Vector r) {
        this(l.env, new V2D_Vector(l.offset), new V2D_Vector(l.l.pv),
                new V2D_Vector(l.l.v), new V2D_Vector(r));
    }

    /**
     * Creates a new instance.
     *
     * @param p Used to initialise {@link #offset} and {@link #pv}.
     * @param q Used to initialise {@link #qv}.
     * @param r Used to initialise {@link #rv}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V2D_Triangle(V2D_Point p, V2D_Point q, V2D_Point r, int oom, RoundingMode rm) {
        super(p.env, new V2D_Vector(p.offset));
        this.pv = new V2D_Vector(p.rel);
        //this.p = new V2D_Point(p);
        //this.p.translate(p.offset.reverse(), oom, rm);
        this.qv = q.getVector(oom, rm).subtract(p.offset, oom, rm);
        //this.q = new V2D_Point(q);
        //this.p.translate(p.offset.reverse(), oom, rm);
        this.rv = r.getVector(oom, rm).subtract(p.offset, oom, rm);
        //this.r = new V2D_Point(r);
        //this.p.translate(p.offset.reverse(), env.oom, env.rm);
    }

    /**
     * Creates a new triangle.
     *
     * @param ls A line segment.
     * @param pt A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V2D_Triangle(V2D_LineSegment ls, V2D_Point pt, int oom, RoundingMode rm) {
        this(ls.getP(), ls.getQ(ls.env.oom, ls.env.rm), pt, oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A new point based on {@link #pv} and {@link #offset}.
     */
    public final V2D_Point getP(int oom, RoundingMode rm) {
        if (p == null) {
            initP(oom, rm);
        } else {
            if (prm != null) {
                if (oom < poom) {
                    initP(oom, rm);
                } else {
                    if (oom == poom) {
                        if (!rm.equals(prm)) {
                            initP(oom, rm);
                        }
                    }
                }
            }
        }
        return p;
    }

    private void initP(int oom, RoundingMode rm) {
        p = new V2D_Point(env, offset, pv);
        poom = oom;
        prm = rm;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A new point based on {@link #qv} and {@link #offset}.
     */
    public final V2D_Point getQ(int oom, RoundingMode rm) {
        if (q == null) {
            initQ(oom, rm);
        } else {
            if (qrm != null) {
                if (oom < this.qoom) {
                    initQ(oom, rm);
                } else {
                    if (oom == this.qoom) {
                        if (!rm.equals(this.qrm)) {
                            initQ(oom, rm);
                        }
                    }
                }
            }
        }
        return q;
    }

    private void initQ(int oom, RoundingMode rm) {
        q = new V2D_Point(env, offset, qv);
        qoom = oom;
        qrm = rm;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A new point based on {@link #rv} and {@link #offset}.
     */
    public final V2D_Point getR(int oom, RoundingMode rm) {
        if (r == null) {
            initR(oom, rm);
        } else {
            if (rrm != null) {
                if (oom < room) {
                    initR(oom, rm);
                } else if (oom == room) {
                    if (!rrm.equals(rm)) {
                        initR(oom, rm);
                    }
                }
            }
        }
        return r;
    }

    private void initR(int oom, RoundingMode rm) {
        r = new V2D_Point(env, offset, rv);
        room = oom;
        rrm = rm;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code qv.subtract(pv, oom, rm)}
     */
    public final V2D_Vector getPQV(int oom, RoundingMode rm) {
        return qv.subtract(pv, oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code rv.subtract(qv, oom, rm)}
     */
    public final V2D_Vector getQRV(int oom, RoundingMode rm) {
        return rv.subtract(qv, oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code pv.subtract(rv, oom, rm)}
     */
    public final V2D_Vector getRPV(int oom, RoundingMode rm) {
        return pv.subtract(rv, oom, rm);
    }

    /**
     * For getting the line segment from {@link #getP()} to {@link #getQ()} with
     * at least oom precision given rm.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return Line segment from rv to pv.
     */
    public final V2D_LineSegment getPQ(int oom, RoundingMode rm) {
        if (pq == null) {
            initPQ(oom, rm);
        } else {
            if (pqrm == null) {
                initPQ(oom, rm);
            } else {
                if (oom < pqoom) {
                    initPQ(oom, rm);
                } else {
                    if (oom == pqoom) {

                        if (!pqrm.equals(rm)) {
                            initPQ(oom, rm);
                        }
                    }
                }
            }
        }
        return pq;
    }

    private void initPQ(int oom, RoundingMode rm) {
        pq = new V2D_LineSegment(env, offset, pv, qv, oom, rm);
        pqoom = oom;
        pqrm = rm;
    }

    /**
     * For getting the line segment from {@link #getQ()} to {@link #getR()} with
     * at least oom precision given rm.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return Line segment from rv to pv.
     */
    public final V2D_LineSegment getQR(int oom, RoundingMode rm) {
        if (qr == null) {
            initQR(oom, rm);
        } else {
            if (qrrm == null) {
                initQR(oom, rm);
            } else {
                if (oom < qroom) {
                    initQR(oom, rm);
                } else {
                    if (oom == qroom) {
                        if (!qrrm.equals(rm)) {
                            initQR(oom, rm);
                        }
                    }
                }
            }
        }
        return qr;
    }

    private void initQR(int oom, RoundingMode rm) {
        qr = new V2D_LineSegment(env, offset, qv, rv, oom, rm);
        qroom = oom;
        qrrm = rm;
    }

    /**
     * For getting the line segment from {@link #getR()} to {@link #getP()} with
     * at least oom precision given rm.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return Line segment from rv to pv.
     */
    public final V2D_LineSegment getRP(int oom, RoundingMode rm) {
        if (rp == null) {
            initRP(oom, rm);
        } else {
            if (rprm == null) {
                initRP(oom, rm);
            } else {
                if (oom < rpoom) {
                    initRP(oom, rm);
                } else {
                    if (oom == rpoom) {
                        if (!rprm.equals(rm)) {
                            initRP(oom, rm);
                        }
                    }
                }
            }
        }
        return rp;
    }

    private void initRP(int oom, RoundingMode rm) {
        rp = new V2D_LineSegment(env, offset, rv, pv, oom, rm);
        rpoom = oom;
        rprm = rm;
    }

    /**
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The internal angle at {@link #pv}.
     */
    public final BigRational getAngleP(int oom, RoundingMode rm) {
        return getPQV(oom, rm).getAngle(getRPV(oom, rm).reverse(), oom, rm);
    }

    /**
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The internal angle at {@link #qv}.
     */
    public final BigRational getAngleQ(int oom, RoundingMode rm) {
        return getPQV(oom, rm).reverse().getAngle(getQRV(oom, rm), oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The internal angle at {@link #rv}.
     */
    public final BigRational getAngleR(int oom, RoundingMode rm) {
        return getQRV(oom, rm).reverse().getAngle(getRPV(oom, rm), oom, rm);
    }

    @Override
    public V2D_Envelope getEnvelope(int oom, RoundingMode rm) {
        if (en == null) {
            en = new V2D_Envelope(oom, getP(oom, rm), getQ(oom, rm), getR(oom, rm));
        }
        return en;
    }

    @Override
    public V2D_Point[] getPointsArray(int oom, RoundingMode rm) {
        V2D_Point[] pts = new V2D_Point[3];
        pts[0] = getP(oom, rm);
        pts[1] = getQ(oom, rm);
        pts[2] = getR(oom, rm);
        return pts;
    }

    @Override
    public HashMap<Integer, V2D_Point> getPoints(int oom, RoundingMode rm) {
        HashMap<Integer, V2D_Point> pts = new HashMap<>(3);
        pts.put(0, getP(oom, rm));
        pts.put(1, getQ(oom, rm));
        pts.put(2, getR(oom, rm));
        return pts;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A collection of the external edges.
     */
    public Collection<V2D_LineSegment> getExternalEdges(int oom, RoundingMode rm) {
        HashSet<V2D_LineSegment> edges = new HashSet<>();
        edges.add(getPQ(oom, rm));
        edges.add(getQR(oom, rm));
        edges.add(getRP(oom, rm));
        return edges;
    }

    /**
     * @param pt The point to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return True iff there is an intersection.
     */
    public boolean isIntersectedBy(V2D_Point pt, int oom, RoundingMode rm) {
        if (getEnvelope(oom, rm).isIntersectedBy(pt, oom, rm)) {
            //if (getPl(oom, rm).isIntersectedBy(pt, oom, rm)) {
            return isAligned(pt, oom, rm);
            //return isIntersectedBy0(pt, oom, rm);
            //}
        }
        return false;
    }

    /**
     * @param pt The point to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return True iff pt is in the triangle and not on the edge.
     */
    public boolean contains(V2D_Point pt, int oom, RoundingMode rm) {
        if (isIntersectedBy(pt, oom, rm)) {
            if (this.getPQ(oom, rm).isIntersectedBy(pt, oom, rm)) {
                return false;
            }
            if (this.getQR(oom, rm).isIntersectedBy(pt, oom, rm)) {
                return false;
            }
            return !this.getRP(oom, rm).isIntersectedBy(pt, oom, rm);
        }
        return false;
    }

    /**
     * @param ls The line segment to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return True iff there is an intersection.
     */
    public boolean isIntersectedBy(V2D_LineSegment ls, int oom, RoundingMode rm) {
        if (getEnvelope(oom, rm).isIntersectedBy(ls.getEnvelope(oom, rm), oom)) {
            if (en.isIntersectedBy(ls.getP(), oom, rm)) {
                return isIntersectedBy0(ls, oom, rm);
            }
            if (en.isIntersectedBy(ls.getQ(oom, rm), oom, rm)) {
                return isIntersectedBy0(ls, oom, rm);
            }
            if (getPQ(oom, rm).isIntersectedBy(ls, oom, rm)) {
                return isIntersectedBy0(ls, oom, rm);
            }
            if (getQR(oom, rm).isIntersectedBy(ls, oom, rm)) {
                return isIntersectedBy0(ls, oom, rm);
            }
            if (getRP(oom, rm).isIntersectedBy(ls, oom, rm)) {
                return isIntersectedBy0(ls, oom, rm);
            }
        }
        return false;
    }

    /**
     * @param ls The line segment to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return True iff there is an intersection.
     */
    protected boolean isIntersectedBy0(V2D_LineSegment ls, int oom, RoundingMode rm) {
        getP(oom, rm);
        getQ(oom, rm);
        getR(oom, rm);
        V2D_Point lsp = ls.getP();
        V2D_Point lsq = ls.getQ(oom, rm);
        return (getPQ(oom, rm).l.isOnSameSide(r, lsp, oom, rm)
                || pq.l.isOnSameSide(r, lsq, oom, rm))
                && (getQR(oom, rm).l.isOnSameSide(p, lsp, oom, rm)
                || qr.l.isOnSameSide(p, lsq, oom, rm))
                && (getRP(oom, rm).l.isOnSameSide(q, lsp, oom, rm)
                || rp.l.isOnSameSide(q, lsq, oom, rm));
    }

    /**
     * @param ls The line segments to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return True iff there is an intersection.
     */
    public boolean isIntersectedBy(int oom, RoundingMode rm, V2D_LineSegment... ls) {
        if (getEnvelope(oom, rm).isIntersectedBy(new V2D_Envelope(oom, rm, ls), oom)) {
            for (V2D_LineSegment l : ls) {
                if (isIntersectedBy(l, oom, rm)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param ls The line segments to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return True iff ls is contained in the triangle.
     */
    public boolean contains(V2D_LineSegment ls, int oom, RoundingMode rm) {
        if (contains(ls.getP(), oom, rm)) {
            return contains(ls.getQ(oom, rm), oom, rm);
        }
        return false;
    }

    /**
     * @param t The triangle to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return True iff there is an intersection.
     */
    public boolean isIntersectedBy(V2D_Triangle t, int oom, RoundingMode rm) {
        if (t.getEnvelope(oom, rm).isIntersectedBy(getEnvelope(oom, rm), oom)) {
            if (isIntersectedBy(t.getP(oom, rm), oom, rm)) {
                return true;
            }
            if (isIntersectedBy(t.getQ(oom, rm), oom, rm)) {
                return true;
            }
            if (isIntersectedBy(t.getR(oom, rm), oom, rm)) {
                return true;
            }
            if (t.isIntersectedBy(getP(oom, rm), oom, rm)) {
                return true;
            }
            if (t.isIntersectedBy(getQ(oom, rm), oom, rm)) {
                return true;
            }
            if (t.isIntersectedBy(getR(oom, rm), oom, rm)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The point pt aligns with this if it is on the same side of each plane
     * defined a triangle edge (with a normal given by the cross product of the
     * triangle normal and the edge line vector), and the other point of the
     * triangle. The plane normal may be imprecisely calculated. Greater
     * precision can be gained using a smaller oom.
     *
     * @param pt The point to check if it is in alignment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff pl is aligned with this.
     */
    public boolean isAligned(V2D_Point pt, int oom, RoundingMode rm) {
        if (getPQ(oom, rm).l.isOnSameSide(pt, getR(oom, rm), oom, rm)) {
            if (getQR(oom, rm).l.isOnSameSide(pt, getP(oom, rm), oom, rm)) {
                if (getRP(oom, rm).l.isOnSameSide(pt, getQ(oom, rm), oom, rm)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * A line segment aligns with this if both end points are aligned according
     * to
     * {@link #isAligned(uk.ac.leeds.ccg.v3d.geometry.V2D_Point, int, java.math.RoundingMode)}.
     *
     * @param l The line segment to check if it is in alignment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff l is aligned with this.
     */
    public boolean isAligned(V2D_LineSegment l, int oom, RoundingMode rm) {
        if (isAligned(l.getP(), oom, rm)) {
            return isAligned(l.getQ(oom, rm), oom, rm);
        }
        return false;
    }

    /**
     * A triangle aligns with this if all points are aligned according to
     * {@link #isAligned(uk.ac.leeds.ccg.v3d.geometry.V2D_Point, int, java.math.RoundingMode)}.
     *
     * @param t The triangle to check if it is in alignment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff l is aligned with this.
     */
    public boolean isAligned(V2D_Triangle t, int oom, RoundingMode rm) {
        if (isAligned(t.getP(oom, rm), oom, rm)) {
            if (isAligned(t.getQ(oom, rm), oom, rm)) {
                return isAligned(t.getR(oom, rm), oom, rm);
            }
        }
        return false;
    }

    /**
     * https://en.wikipedia.org/wiki/Heron%27s_formula
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The area of the triangle.
     */
    public BigRational getArea(int oom, RoundingMode rm) {
        BigRational a = getPQ(oom, rm).getLength(oom, rm).getSqrt(oom, rm);
        BigRational b = getQR(oom, rm).getLength(oom, rm).getSqrt(oom, rm);
        BigRational c = getRP(oom, rm).getLength(oom, rm).getSqrt(oom, rm);
        BigRational p1 = (a.add(b).add(c)).divide(2);
        BigRational p2 = (b.add(c).subtract(a)).divide(2);
        BigRational p3 = (a.subtract(b).add(c)).divide(2);
        BigRational p4 = (a.add(b).subtract(c)).divide(2);
        return new Math_BigRationalSqrt(p1.multiply(p2).multiply(p3).multiply(p4), oom, rm).getSqrt(oom, rm);
    }

    /**
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return
     */
    public BigRational getPerimeter(int oom, RoundingMode rm) {
        int oomn2 = oom - 2;
        return getPQ(oomn2, rm).getLength(oomn2, rm).getSqrt(oom, rm)
                .add(getQR(oomn2, rm).getLength(oomn2, rm).getSqrt(oom, rm))
                .add(getRP(oomn2, rm).getLength(oomn2, rm).getSqrt(oom, rm));
    }

    /**
     * @param l The line to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A point or line segment.
     */
    public V2D_FiniteGeometry getIntersection(V2D_Line l, int oom,
            RoundingMode rm) {
        /**
         * Get the intersection of the line and each edge of the triangle.
         */
        int oomn2 = oom - 2;
        V2D_FiniteGeometry lpqi = getPQ(oomn2, rm).getIntersection(l, oomn2, rm);
        V2D_FiniteGeometry lqri = getQR(oomn2, rm).getIntersection(l, oomn2, rm);
        V2D_FiniteGeometry lrpi = getRP(oomn2, rm).getIntersection(l, oomn2, rm);
        /**
         * This may appear overly complicated in parts, but due to imprecision
         * some odd cases may arise!
         */
        if (lpqi == null) {
            if (lqri == null) {
                return lrpi;
            } else if (lqri instanceof V2D_Point lqrip) {
                if (lrpi == null) {
                    return lqri;
                } else if (lrpi instanceof V2D_Point lrpip) {
                    return V2D_LineSegment.getGeometry(oom, rm, lqrip, lrpip);
                } else {
                    return V2D_LineSegment.getGeometry((V2D_LineSegment) lrpi,
                            lqrip, oom, rm);
                }
            } else {
                V2D_LineSegment lqril = (V2D_LineSegment) lqri;
                if (lrpi == null) {
                    return lqril;
                } else if (lrpi instanceof V2D_Point lrpip) {
                    return V2D_LineSegment.getGeometry(lqril, lrpip, oom, rm);
                } else {
                    V2D_LineSegment lrpil = (V2D_LineSegment) lrpi;
                    return V2D_LineSegment.getGeometry(oom, rm, lqril, lrpil);
                }
            }
        } else if (lpqi instanceof V2D_Point lpqip) {
            if (lqri == null) {
                if (lrpi == null) {
                    return lpqi;
                } else {
                    return V2D_LineSegment.getGeometry(lpqip, (V2D_Point) lrpi,
                            oom, rm);
                }
            } else if (lqri instanceof V2D_Point lqrip) {
                if (lrpi == null) {
                    return V2D_LineSegment.getGeometry(lqrip, lpqip, oom, rm);
                } else if (lrpi instanceof V2D_LineSegment) {
                    return lrpi;
                } else {
                    return getGeometry(lpqip, lqrip, (V2D_Point) lrpi, oom, rm);
                }
            } else {
                return lqri;
            }
        } else {
            return lpqi;
        }
    }

    /**
     * If {@code v1} and {@code v2} are the same, then return a point, otherwise
     * return a line segment. In both instance offset is set to
     * {@link V2D_Vector#ZERO}.
     *
     * @param env The environment.
     * @param v1 A vector.
     * @param v2 A vector.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return Either a line segment or a point.
     */
    public static V2D_FiniteGeometry getGeometry(V2D_Environment env,
            V2D_Vector v1, V2D_Vector v2, int oom, RoundingMode rm) {
        if (v1.equals(v2)) {
            return new V2D_Point(env, v1);
        } else {
            return new V2D_LineSegment(env, v1, v2, oom, rm);
        }
    }

    /**
     * Get the intersection between the geometry and the ray {@code rv}.
     *
     * @param r The ray to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V2D_Geometry.
     */
    public V2D_FiniteGeometry getIntersection(V2D_Ray r, int oom,
            RoundingMode rm) {
        V2D_FiniteGeometry g = getIntersection(r.l, oom, rm);
        if (g == null) {
            return null;
        }
        if (g instanceof V2D_Point gp) {
            if (r.isAligned(gp, oom, rm)) {
                return gp;
            } else {
                return null;
            }
        }
        V2D_LineSegment ls = (V2D_LineSegment) g;
        V2D_Point lsp = ls.getP();
        V2D_Point lsq = ls.getQ(oom, rm);
        if (r.getPl().isOnSameSide(lsp, r.l.getQ(oom, rm), oom, rm)) {
            if (r.getPl().isOnSameSide(lsq, r.l.getQ(oom, rm), oom, rm)) {
                return ls;
            } else {
                return V2D_LineSegment.getGeometry(r.l.getP(), lsp, oom, rm);
            }
        } else {
            if (r.getPl().isOnSameSide(lsq, r.l.getQ(oom, rm), oom, rm)) {
                return V2D_LineSegment.getGeometry(r.l.getP(), lsq, oom, rm);
            } else {
                throw new RuntimeException();
            }
        }
//        if (rv.isAligned(lsp, oom, rm)) {
//            if (rv.isAligned(lsq, oom, rm)) {
//                return ls;
//            } else {
//                return V2D_LineSegment.getGeometry(rv.l.getP(), lsp, oom, rm);
//            }
//        } else {
//            if (rv.isAligned(lsq, oom, rm)) {
//                return V2D_LineSegment.getGeometry(rv.l.getP(), lsq, oom, rm);
//            } else {
//                throw new RuntimeException("Exception in triangle-linesegment intersection.");
//            }
//        }
    }

    /**
     * Compute and return the intersection with the line segment.
     *
     * @param l The line segment to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V2D_Geometry.
     */
    public V2D_FiniteGeometry getIntersection(V2D_LineSegment l, int oom,
            RoundingMode rm) {
        V2D_FiniteGeometry g = getIntersection(l.l, oom, rm);
        if (g == null) {
            return null;
        }
        if (g instanceof V2D_Point gp) {
            if (isIntersectedBy(gp, oom, rm)) {
                if (l.isBetween(gp, oom, rm)) {
                    return gp;
                }
            }
            return null;
        }
        V2D_LineSegment ls = (V2D_LineSegment) g;
        V2D_FiniteGeometry lils = l.getIntersectionLS(ls, oom, rm);
        if (lils == null) {
            return null;
        } else if (lils instanceof V2D_Point lilsp) {
            if (isIntersectedBy(lilsp, oom, rm)) {
                return lilsp;
            } else {
                return null;
            }
        } else {
            V2D_LineSegment lilsl = (V2D_LineSegment) lils;
            if (isIntersectedBy(lilsl, oom, rm)) {
                return l.getIntersectionLS(ls, oom, rm);
            } else {
                return null;
            }
        }
//        // Previous version.
//        if (g == null) {
//            return null;
//        }
//        if (g instanceof V2D_Point gp) {
//            if (l.isAligned(gp, oom, rm)) {
//                if (l.isBetween(gp, oom, rm)) {
//                    return gp;
//                }
//            }
//            return null;
//        }
//        V2D_LineSegment ls = (V2D_LineSegment) g;
//        //if (ls.isBetween(l.getP(), oom, rm) || ls.isBetween(l.getQ(), oom, rm) 
//        //        || l.isBetween(getP(), oom, rm)) {
//        if (ls.isBetween(l.getP(), oom, rm) || ls.isBetween(l.getQ(), oom, rm) 
//                || l.isBetween(getP(), oom, rm) || l.isBetween(getQ(), oom, rm)) {
//            return l.getIntersectionLS((V2D_LineSegment) g, oom, rm);
//        } else {
//            return null;
//        }
    }

    /**
     * Computes and returns the intersection between {@code this} and {@code t}.
     * The intersection could be: null, a point, a line segment, a triangle, or
     * a V2D_ConvexHull (with 4, 5, or 6 sides).
     *
     * @param t The triangle intersect with this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The intersection between {@code t} and {@code this} or
     * {@code null} if there is no intersection.
     */
    public V2D_FiniteGeometry getIntersection(V2D_Triangle t, int oom,
            RoundingMode rm) {
        int oomn2 = oom - 2;
        if (getEnvelope(oom, rm).isIntersectedBy(t.getEnvelope(oomn2, rm), oomn2)) {
            /**
             * Get intersections between the triangle edges. If there are none,
             * then either this returns t or vice versa. If there are some, then
             * in some cases the result is a single triangle, and in others it
             * is a polygon which can be represented as a set of coplanar
             * triangles.
             */
            // Check if vertices intersect
            V2D_Point pttp = t.getP(oom, rm);
            V2D_Point pttq = t.getQ(oom, rm);
            V2D_Point pttr = t.getR(oom, rm);
            boolean pi = isIntersectedBy(pttp, oomn2, rm);
            boolean qi = isIntersectedBy(pttq, oomn2, rm);
            boolean ri = isIntersectedBy(pttr, oomn2, rm);
            if (pi && qi && ri) {
                return t;
            }
            V2D_Point ptp = getP(oom, rm);
            V2D_Point ptq = getQ(oom, rm);
            V2D_Point ptr = getR(oom, rm);
            boolean pit = t.isIntersectedBy(ptp, oomn2, rm);
            boolean qit = t.isIntersectedBy(ptq, oomn2, rm);
            boolean rit = t.isIntersectedBy(ptr, oomn2, rm);
            if (pit && qit && rit) {
                return this;
            }
            if (isAligned(t, oomn2, rm)) {
                return t;
            }
            if (t.isAligned(this, oomn2, rm)) {
                return this;
            }
            V2D_FiniteGeometry gpq = t.getIntersection(getPQ(oomn2, rm), oomn2, rm);
            V2D_FiniteGeometry gqr = t.getIntersection(getQR(oomn2, rm), oomn2, rm);
            V2D_FiniteGeometry grp = t.getIntersection(getRP(oomn2, rm), oomn2, rm);
            if (gpq == null) {
                if (gqr == null) {

                    if (grp == null) {
                        return null;
                    } else if (grp instanceof V2D_Point grpp) {
                        return grpp;
                    } else {
                        if (qi) {
                            return getGeometry((V2D_LineSegment) grp, pttq, oom, rm);
                        } else {
                            return grp;
                        }
                    }

                } else if (gqr instanceof V2D_Point gqrp) {
                    if (grp == null) {
                        return gqr;
                    } else if (grp instanceof V2D_Point grpp) {
                        return V2D_LineSegment.getGeometry(
                                gqrp, grpp, oom, rm);
                    } else {
                        V2D_LineSegment ls = (V2D_LineSegment) grp;
                        return getGeometry(gqrp, ls.getP(), ls.getQ(oom, rm), oom, rm);
                    }
                } else {
                    V2D_LineSegment gqrl = (V2D_LineSegment) gqr;
                    if (grp == null) {

                        if (pi) {
                            return getGeometry(gqrl, pttp, oom, rm);
                        } else {
                            return gqr;
                        }

                    } else if (grp instanceof V2D_Point grpp) {
                        return getGeometry(grpp, gqrl.getP(),
                                gqrl.getQ(oom, rm), oom, rm);
                    } else {
                        return getGeometry((V2D_LineSegment) gqr,
                                (V2D_LineSegment) grp, oom, rm);
                    }
                }
            } else if (gpq instanceof V2D_Point gpqp) {
                if (gqr == null) {
                    if (grp == null) {
                        return gpq;
                    } else if (grp instanceof V2D_Point grpp) {
                        return V2D_LineSegment.getGeometry(
                                gpqp, grpp, oom, rm);
                    } else {
                        V2D_LineSegment ls = (V2D_LineSegment) grp;
                        return getGeometry(gpqp, ls.getP(),
                                ls.getQ(oom, rm), oom, rm);
                    }
                } else if (gqr instanceof V2D_Point gqrp) {
                    if (grp == null) {
                        return gqr;
                    } else if (grp instanceof V2D_Point grpp) {
                        return getGeometry(gpqp, gqrp, grpp, oom, rm);
                    } else {
                        return getGeometry((V2D_LineSegment) grp,
                                gqrp, gpqp, oom, rm);
                    }
                } else {
                    V2D_LineSegment ls = (V2D_LineSegment) gqr;
                    if (grp == null) {
                        return getGeometry(ls, gpqp, oom, rm);
                    } else if (grp instanceof V2D_Point grpp) {
                        return getGeometry(ls, grpp, gpqp, oom, rm);
                    } else {
                        return getGeometry(ls, (V2D_LineSegment) grp,
                                gpqp, oom, rm);
                    }
                }
            } else {
                V2D_LineSegment gpql = (V2D_LineSegment) gpq;
                if (gqr == null) {
                    if (grp == null) {

                        if (ri) {
                            return getGeometry(gpql, pttr, oom, rm);
                        } else {
                            return gpq;
                        }

                    } else if (grp instanceof V2D_Point grpp) {
                        return getGeometry(grpp, gpql.getP(), gpql.getQ(oom, rm),
                                oom, rm);
                    } else {
                        return getGeometry(gpql,
                                (V2D_LineSegment) grp, oom, rm);
                    }
                } else if (gqr instanceof V2D_Point gqrp) {
                    if (grp == null) {
                        if (gpql.isIntersectedBy(gqrp, oom, rm)) {
                            return gpql;
                        } else {
                            return new V2D_ConvexHull(oom, rm, gpql.getP(),
                                    gpql.getQ(oom, rm), gqrp);
                        }
                    } else if (grp instanceof V2D_Point grpp) {
                        ArrayList<V2D_Point> pts = new ArrayList<>();
                        pts.add(gpql.getP());
                        pts.add(gpql.getQ(oom, rm));
                        pts.add(gqrp);
                        pts.add(grpp);
                        ArrayList<V2D_Point> pts2 = V2D_Point.getUnique(pts, oom, rm);
                        return switch (pts2.size()) {
                            case 2 ->
                                new V2D_LineSegment(pts2.get(0), pts2.get(1), oom, rm);
                            case 3 ->
                                new V2D_Triangle(pts2.get(0), pts2.get(1), pts2.get(2), oom, rm);
                            default ->
                                new V2D_ConvexHull(oom, rm, gpql.getP(), gpql.getQ(oom, rm), gqrp, grpp);
                        };
                    } else {
                        V2D_LineSegment grpl = (V2D_LineSegment) grp;
                        return V2D_ConvexHull.getGeometry(
                                oom, rm, gpql.getP(),
                                gpql.getQ(oom, rm), gqrp, grpl.getP(),
                                grpl.getQ(oom, rm));
                    }
                } else {
                    V2D_LineSegment gqrl = (V2D_LineSegment) gqr;
                    if (grp == null) {
                        return V2D_ConvexHull.getGeometry(
                                oom, rm,
                                gpql.getP(), gpql.getQ(oom, rm),
                                gqrl.getP(), gqrl.getQ(oom, rm));
                    } else if (grp instanceof V2D_Point grpp) {
                        return V2D_ConvexHull.getGeometry(
                                oom, rm, gpql.getP(),
                                gpql.getQ(oom, rm), gqrl.getP(),
                                gqrl.getQ(oom, rm), grpp);
                    } else {
                        V2D_LineSegment grpl = (V2D_LineSegment) grp;
                        return V2D_ConvexHull.getGeometry(
                                oom, rm, gpql.getP(),
                                gpql.getQ(oom, rm), gqrl.getP(),
                                gqrl.getQ(oom, rm), grpl.getP(),
                                grpl.getQ(oom, rm));
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The centroid point.
     */
    public V2D_Point getCentroid(int oom, RoundingMode rm) {
        oom -= 6;
        BigRational dx = (pv.getDX(oom, rm).add(qv.getDX(oom, rm))
                .add(rv.getDX(oom, rm))).divide(3);
        BigRational dy = (pv.getDY(oom, rm).add(qv.getDY(oom, rm))
                .add(rv.getDY(oom, rm))).divide(3);
        return new V2D_Point(env, offset, new V2D_Vector(dx, dy));
    }

    /**
     * Test if two triangles are equal. Two triangles are equal if they have 3
     * coincident points, so even if the order is different and one is clockwise
     * and the other anticlockwise, or one faces the other way.
     *
     * @param t The other triangle to test for equality.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code this} is equal to {@code t}.
     */
    public boolean equals(V2D_Triangle t, int oom, RoundingMode rm) {
        V2D_Point tp = t.getP(oom, rm);
        V2D_Point thisp = getP(oom, rm);
        if (tp.equals(thisp, oom, rm)) {
            V2D_Point tq = t.getQ(oom, rm);
            V2D_Point thisq = getQ(oom, rm);
            if (tq.equals(thisq, oom, rm)) {
                return t.getR(oom, rm).equals(getR(oom, rm), oom, rm);
            } else if (tq.equals(getR(oom, rm), oom, rm)) {
                return t.getR(oom, rm).equals(thisq, oom, rm);
            } else {
                return false;
            }
        } else if (tp.equals(getQ(oom, rm), oom, rm)) {
            V2D_Point tq = t.getQ(oom, rm);
            V2D_Point thisr = getR(oom, rm);
            if (tq.equals(thisr, oom, rm)) {
                return t.getR(oom, rm).equals(thisp, oom, rm);
            } else if (tq.equals(thisp, oom, rm)) {
                return t.getR(oom, rm).equals(thisr, oom, rm);
            } else {
                return false;
            }
        } else if (tp.equals(getR(oom, rm), oom, rm)) {
            V2D_Point tq = t.getQ(oom, rm);
            if (tq.equals(thisp, oom, rm)) {
                return t.getR(oom, rm).equals(getQ(oom, rm), oom, rm);
            } else if (tq.equals(getQ(oom, rm), oom, rm)) {
                return t.getR(oom, rm).equals(thisp, oom, rm);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void translate(V2D_Vector v, int oom, RoundingMode rm) {
        super.translate(v, oom, rm);
        if (en != null) {
            en.translate(v, oom, rm);
        }
        if (p != null) {
            p.translate(v, oom, rm);
        }
        if (q != null) {
            q.translate(v, oom, rm);
        }
        if (r != null) {
            r.translate(v, oom, rm);
        }
        if (pq != null) {
            pq.translate(v, oom, rm);
        }
        if (qr != null) {
            qr.translate(v, oom, rm);
        }
        if (rp != null) {
            rp.translate(v, oom, rm);
        }
    }

    @Override
    public V2D_Triangle rotate(V2D_Point pt, BigRational theta, Math_BigDecimal bd,
            int oom, RoundingMode rm) {
        theta = Math_AngleBigRational.normalise(theta, bd, oom, rm);
        if (theta.compareTo(BigRational.ZERO) == 0d) {
            return new V2D_Triangle(this);
        } else {
            return rotateN(pt, theta, bd, oom, rm);
        }
    }

    @Override
    public V2D_Triangle rotateN(V2D_Point pt, BigRational theta,
            Math_BigDecimal bd, int oom, RoundingMode rm) {
        return new V2D_Triangle(
                getP(oom, rm).rotateN(pt, theta, bd, oom, rm),
                getQ(oom, rm).rotateN(pt, theta, bd, oom, rm),
                getR(oom, rm).rotateN(pt, theta, bd, oom, rm), oom, rm);
    }

    /**
     * @param pad Padding
     * @return A String representation of this.
     */
    public String toString(String pad) {
        String s = pad + this.getClass().getSimpleName() + "(\n";
        s += pad + " offset=(" + this.offset.toString(pad + " ") + "),\n"
                + pad + " p=(" + this.pv.toString(pad + " ") + "),\n"
                + pad + " q=(" + this.qv.toString(pad + " ") + "),\n"
                + pad + " r=(" + this.rv.toString(pad + " ") + "))";
        return s;
    }

    /**
     * @param pad Padding
     * @return A simple String representation of this.
     */
    public String toStringSimple(String pad) {
        String s = pad + this.getClass().getSimpleName() + "(\n";
        s += pad + " offset=(" + this.offset.toStringSimple("") + "),\n"
                + pad + " p=(" + this.pv.toStringSimple("") + "),\n"
                + pad + " q=(" + this.qv.toStringSimple("") + "),\n"
                + pad + " r=(" + this.rv.toStringSimple("") + "))";
        return s;
    }

    @Override
    public String toString() {
        //return toString("");
        return toStringSimple("");
    }

    /**
     * If p, q and r are equal then the point is returned.If two of the points
     * are the same, then a line segment is returned. If all points are
     * different then a triangle is returned.
     *
     * @param p A point.
     * @param q Another possibly equal point.
     * @param r Another possibly equal point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return either {@code pl} or {@code new V2D_LineSegment(pl, qv)} or
     * {@code new V2D_Triangle(pl, qv, rv)}
     */
    public static V2D_FiniteGeometry getGeometry(V2D_Point p, V2D_Point q,
            V2D_Point r, int oom, RoundingMode rm) {
        if (p.equals(q, oom, rm)) {
            return V2D_LineSegment.getGeometry(p, r, oom, rm);
        } else {
            if (q.equals(r, oom, rm)) {
                return V2D_LineSegment.getGeometry(q, p, oom, rm);
            } else {
                if (r.equals(p, oom, rm)) {
                    return V2D_LineSegment.getGeometry(r, q, oom, rm);
                } else {
                    if (V2D_Line.isCollinear(oom, rm, p, q, r)) {
                        V2D_LineSegment pq = new V2D_LineSegment(p, q, oom, rm);
                        if (pq.isIntersectedBy(r, oom, rm)) {
                            return pq;
                        } else {
                            V2D_LineSegment qr = new V2D_LineSegment(q, r, oom, rm);
                            if (qr.isIntersectedBy(p, oom, rm)) {
                                return qr;
                            } else {
                                return new V2D_LineSegment(p, r, oom, rm);
                            }
                        }
                    }
                    return new V2D_Triangle(p, q, r, oom, rm);
//                    return new V2D_Triangle(pl.e, V2D_Vector.ZERO,
//                            pl.getVector(pl.e.oom),
//                            qv.getVector(pl.e.oom), rv.getVector(pl.e.oom));
                }
            }
        }
    }

    /**
     * Used in intersecting two triangles to give the overall intersection.If
     * l1, l2 and l3 are equal then the line segment is returned. If there are 3
     * unique points then a triangle is returned. If there are 4 or more unique
     * points, then a V2D_ConvexHull is returned.
     *
     *
     * @param l1 A line segment.
     * @param l2 A line segment.
     * @param l3 A line segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return either {@code pl} or {@code new V2D_LineSegment(pl, qv)} or
     * {@code new V2D_Triangle(pl, qv, rv)}
     */
    protected static V2D_FiniteGeometry getGeometry(V2D_LineSegment l1,
            V2D_LineSegment l2, V2D_LineSegment l3, int oom, RoundingMode rm) {
        V2D_Point l1p = l1.getP();
        V2D_Point l1q = l1.getQ(oom, rm);
        V2D_Point l2p = l2.getP();
        V2D_Point l2q = l2.getQ(oom, rm);
        V2D_Point l3p = l3.getP();
        V2D_Point l3q = l3.getQ(oom, rm);
        ArrayList<V2D_Point> points;
        {
            List<V2D_Point> pts = new ArrayList<>();
            pts.add(l1p);
            pts.add(l1q);
            pts.add(l2p);
            pts.add(l2q);
            pts.add(l3p);
            pts.add(l3q);
            points = V2D_Point.getUnique(pts, oom, rm);
        }
        int n = points.size();
        switch (n) {
            case 2 -> {
                return l1;
            }
            case 3 -> {
                Iterator<V2D_Point> ite = points.iterator();
                return getGeometry(ite.next(), ite.next(), ite.next(), oom, rm);
            }
            default -> {
                V2D_Point[] pts = new V2D_Point[points.size()];
                int i = 0;
                for (var p : points) {
                    pts[i] = p;
                    i++;
                }
                //V2D_Plane pl = new V2D_Plane(pts[0], pts[1], pts[2], oom, rm);
                return new V2D_ConvexHull(oom, rm, pts);
            }
        }
    }

    /**
     * Used in intersecting two triangles to give the overall intersection.If
     * there are 3 unique points then a triangle is returned. If there are 4 or
     * more unique points, then a V2D_ConvexHull is returned.
     *
     * @param l1 A line segment.
     * @param l2 A line segment.
     * @param pt A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return either {@code pl} or {@code new V2D_LineSegment(pl, qv)} or
     * {@code new V2D_Triangle(pl, qv, rv)}
     */
    protected static V2D_FiniteGeometry getGeometry(V2D_LineSegment l1,
            V2D_LineSegment l2, V2D_Point pt, int oom, RoundingMode rm) {
        V2D_Point l1p = l1.getP();
        V2D_Point l1q = l1.getQ(oom, rm);
        V2D_Point l2p = l2.getP();
        V2D_Point l2q = l2.getQ(oom, rm);
        ArrayList<V2D_Point> points;
        {
            List<V2D_Point> pts = new ArrayList<>();
            pts.add(l1p);
            pts.add(l1q);
            pts.add(l2p);
            pts.add(l2q);
            pts.add(pt);
            points = V2D_Point.getUnique(pts, oom, rm);
        }
        int n = points.size();
        switch (n) {
            case 2 -> {
                return l1;
            }
            case 3 -> {
                Iterator<V2D_Point> ite = points.iterator();
                return getGeometry(ite.next(), ite.next(), ite.next(), oom, rm);
            }
            default -> {
                V2D_Point[] pts = new V2D_Point[points.size()];
                int i = 0;
                for (var p : points) {
                    pts[i] = p;
                    i++;
                }
                return new V2D_ConvexHull(oom, rm, pts);
            }
        }
    }

    /**
     * Used in intersecting a triangle and a tetrahedron.If there are 3 unique
     * points then a triangle is returned. If there are 4 points, then a
     * V2D_ConvexHull is returned.
     *
     * @param l1 A line segment.
     * @param l2 A line segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return either {@code pl} or {@code new V2D_LineSegment(pl, qv)} or
     * {@code new V2D_Triangle(pl, qv, rv)}
     */
    protected static V2D_FiniteGeometry getGeometry2(V2D_LineSegment l1,
            V2D_LineSegment l2, int oom, RoundingMode rm) {
        V2D_Point l1p = l1.getP();
        V2D_Point l1q = l1.getQ(oom, rm);
        V2D_Point l2p = l2.getP();
        V2D_Point l2q = l2.getQ(oom, rm);
        ArrayList<V2D_Point> points;
        {
            List<V2D_Point> pts = new ArrayList<>();
            pts.add(l1p);
            pts.add(l1q);
            pts.add(l2p);
            pts.add(l2q);
            points = V2D_Point.getUnique(pts, oom, rm);
        }
        points.add(l1p);
        points.add(l1q);
        points.add(l2p);
        points.add(l2q);
        int n = points.size();
        switch (n) {
            case 2 -> {
                return l1;
            }
            case 3 -> {
                Iterator<V2D_Point> ite = points.iterator();
                return getGeometry(ite.next(), ite.next(), ite.next(), oom, rm);
            }
            default -> {
                V2D_Point[] pts = new V2D_Point[points.size()];
                int i = 0;
                for (var p : points) {
                    pts[i] = p;
                    i++;
                }
                return new V2D_ConvexHull(oom, rm, pts);
            }
        }
    }

    /**
     * This may be called when there is an intersection of two triangles. If l1
     * and l2 are two sides of a triangle, return the triangle.
     *
     * @param l1 A line segment and triangle edge.
     * @param l2 A line segment and triangle edge.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return a triangle for which l1 and l2 are edges
     */
    protected static V2D_FiniteGeometry getGeometry(V2D_LineSegment l1,
            V2D_LineSegment l2, int oom, RoundingMode rm) {
        V2D_FiniteGeometry g = l1.getIntersection(l2, oom, rm);
        if (g instanceof V2D_Point pt) {
            V2D_Point l1p = l1.getP();
            V2D_Point l2p = l2.getP();
            if (l1p.equals(pt, oom, rm)) {
                if (l2p.equals(pt, oom, rm)) {
                    return new V2D_Triangle(pt, l1.getQ(oom, rm), l2.getQ(oom, rm), oom, rm);
                } else {
                    return new V2D_Triangle(pt, l1.getQ(oom, rm), l2p, oom, rm);
                }
            } else {
                if (l2p.equals(pt, oom, rm)) {
                    return new V2D_Triangle(pt, l1p, l2.getQ(oom, rm), oom, rm);
                } else {
                    return new V2D_Triangle(pt, l1p, l2p, oom, rm);
                }
            }
        } else {
            return g;
        }
    }

    /**
     * This may be called when there is an intersection of two triangles where l
     * is a side of a triangle and pl is a point.
     *
     * @param l A line segment.
     * @param p1 A point that is either not collinear to l or intersects l.
     * @param p2 A point that is either not collinear to l or intersects l.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return a triangle for which l is an edge and pl is a vertex.
     */
    protected static V2D_FiniteGeometry getGeometry(V2D_LineSegment l,
            V2D_Point p1, V2D_Point p2, int oom, RoundingMode rm) {
        if (l.isIntersectedBy(p1, oom, rm)) {
            return getGeometry(l, p2, oom, rm);
        } else {
            return new V2D_Triangle(p1, l.getP(), l.getQ(oom, rm), oom, rm);
        }
    }

    /**
     * This may be called when there is an intersection of two triangles where l
     * is a side of a triangle and pl is a point that is not collinear to l.
     *
     * @param l A line segment.
     * @param p A point that is not collinear to l.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return a triangle for which l is an edge and pl is a vertex.
     */
    protected static V2D_FiniteGeometry getGeometry(V2D_LineSegment l,
            V2D_Point p, int oom, RoundingMode rm) {
        if (l.isIntersectedBy(p, oom, rm)) {
            return l;
        }
        return new V2D_Triangle(p, l.getP(), l.getQ(oom, rm), oom, rm);
    }

    /**
     * For getting the point opposite a side of a triangle given the side.
     *
     * @param l A line segment equal to one of the edges of this triangle.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The point of {@code this} that does not intersect with {@code l}.
     */
    public V2D_Point getOpposite(V2D_LineSegment l, int oom, RoundingMode rm) {
        if (getPQ(oom, rm).equals(l, oom, rm)) {
            return getR(oom, rm);
        } else {
            if (getQR(oom, rm).equals(l, oom, rm)) {
                return getP(oom, rm);
            } else {
                return getQ(oom, rm);
            }
        }
    }

    /**
     * Get the minimum distance to {@code pv}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The distance squared to {@code pv}.
     */
    public BigRational getDistance(V2D_Point p, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom, rm), oom, rm)
                .getSqrt(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code pt}.
     *
     * @param pt A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The distance squared to {@code pv}.
     */
    public BigRational getDistanceSquared(V2D_Point pt, int oom, RoundingMode rm) {
        if (isIntersectedBy(pt, oom, rm)) {
            return BigRational.ZERO;
        }
        return getDistanceSquaredEdge(pt, oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code pt} from the perimeter.
     *
     * @param pt A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The distance squared between pt and the nearest edge of this.
     */
    public BigRational getDistanceSquaredEdge(V2D_Point pt, int oom,
            RoundingMode rm) {
        int oomn2 = oom - 2;
        BigRational pqd2 = getPQ(oom, rm).getDistanceSquared(pt, oomn2, rm);
        BigRational qrd2 = getQR(oom, rm).getDistanceSquared(pt, oomn2, rm);
        BigRational rpd2 = getRP(oom, rm).getDistanceSquared(pt, oomn2, rm);
        return BigRational.min(pqd2, qrd2, rpd2);
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision.
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public BigRational getDistanceSquared(V2D_Line l, int oom, RoundingMode rm) {
        BigRational dpq2 = getPQ(oom, rm).getDistanceSquared(l, oom, rm);
        BigRational dqr2 = getQR(oom, rm).getDistanceSquared(l, oom, rm);
        BigRational drp2 = getRP(oom, rm).getDistanceSquared(l, oom, rm);
        return BigRational.min(dpq2, dqr2, drp2);
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public BigRational getDistance(V2D_LineSegment l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom, rm), oom, rm)
                .getSqrt(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public BigRational getDistanceSquared(V2D_LineSegment l, int oom,
            RoundingMode rm) {
        if (getIntersection(l, oom, rm) != null) {
            return BigRational.ZERO;
        }
        BigRational dlpq2 = l.getDistanceSquared(getPQ(oom, rm), oom, rm);
        BigRational dlqr2 = l.getDistanceSquared(getQR(oom, rm), oom, rm);
        BigRational dlrp2 = l.getDistanceSquared(getRP(oom, rm), oom, rm);
        BigRational d2 = BigRational.min(dlpq2, dlqr2, dlrp2);
        /**
         * For any end points of l that are aligned with this, calculate the
         * distances as these could be the minimum.
         */
        V2D_Point lp = l.getP();
        V2D_Point lq = l.getQ(oom, rm);
        if (isAligned(lp, oom, rm)) {
            d2 = BigRational.min(d2, getDistanceSquared(lp, oom, rm));
        }
        if (isAligned(lq, oom, rm)) {
            d2 = BigRational.min(d2, getDistanceSquared(lq, oom, rm));
        }
        return d2;
    }

    /**
     * Get the minimum distance to {@code t}.
     *
     * @param t A triangle.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code t}.
     */
    public BigRational getDistance(V2D_Triangle t, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(t, oom, rm), oom, rm)
                .getSqrt(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code t}.
     *
     * @param t A triangle.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code t}.
     */
    public BigRational getDistanceSquared(V2D_Triangle t, int oom,
            RoundingMode rm) {
        if (getIntersection(t, oom, rm) != null) {
            return BigRational.ZERO;
        }
        BigRational dtpq2 = t.getDistanceSquared(getPQ(oom, rm), oom, rm);
        BigRational dtqr2 = t.getDistanceSquared(getQR(oom, rm), oom, rm);
        BigRational dtrp2 = t.getDistanceSquared(getRP(oom, rm), oom, rm);
        return BigRational.min(dtpq2, dtqr2, dtrp2);
//        BigRational dpq2 = getDistanceSquared(t.getPQ(oom, rm), oom, rm);
//        BigRational dqr2 = getDistanceSquared(t.getQR(oom, rm), oom, rm);
//        BigRational drp2 = getDistanceSquared(t.getRP(oom, rm), oom, rm);
//        return BigRational.min(dtpq2, dtqr2, dtrp2, dpq2, dqr2, drp2);
    }

    /**
     * For retrieving a Set of points that are the corners of the triangles.
     *
     * @param triangles The input.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A Set of points that are the corners of the triangles.
     */
    //public static ArrayList<V2D_Point> getPointsArray(V2D_Triangle[] triangles) {
    public static V2D_Point[] getPoints(V2D_Triangle[] triangles, int oom, RoundingMode rm) {
        List<V2D_Point> s = new ArrayList<>();
        for (var t : triangles) {
            s.add(t.getP(oom, rm));
            s.add(t.getQ(oom, rm));
            s.add(t.getR(oom, rm));
        }
        ArrayList<V2D_Point> points = V2D_Point.getUnique(s, oom, rm);
        return points.toArray(V2D_Point[]::new);
    }

    /**
     * Computes and returns the circumcentre of the circmcircle.
     * https://en.wikipedia.org/wiki/Circumcircle
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The circumcentre of a circumcircle of this triangle.
     */
    public V2D_Point getCircumcenter(int oom, RoundingMode rm) {
        V2D_Point a = getP(oom, rm);
        BigRational ax = a.getX(oom, rm);
        BigRational ay = a.getY(oom, rm);
        V2D_Point b = getQ(oom, rm);
        BigRational bx = b.getX(oom, rm);
        BigRational by = b.getY(oom, rm);
        V2D_Point c = getR(oom, rm);
        BigRational cx = c.getX(oom, rm);
        BigRational cy = c.getY(oom, rm);
        BigRational byscy = by.subtract(cy);
        BigRational cysay = cy.subtract(ay);
        BigRational aysby = ay.subtract(by);
        BigRational d = BigRational.TWO.multiply((ax.multiply(byscy))
                .add(bx.multiply(cysay).add(cx.multiply(aysby))));
        BigRational ax2aay2 = ((ax.multiply(ax)).add(ay.multiply(ay)));
        BigRational bx2aby2 = ((bx.multiply(bx)).add(by.multiply(by)));
        BigRational cx2acy2 = ((cx.multiply(cx)).add(cy.multiply(cy)));
        BigRational ux = ((ax2aay2.multiply(byscy)).add(bx2aby2.multiply(cysay))
                .add(cx2acy2.multiply(aysby))).divide(d);
        BigRational uy = ((ax2aay2.multiply(cx.subtract(bx)))
                .add(bx2aby2.multiply(ax.subtract(cx)))
                .add(cx2acy2.multiply(bx.subtract(ax)))).divide(d);
        return new V2D_Point(env, ux, uy);
    }

//    /**
//     * Clips this using pl and returns the part that is on the same side as pt.
//     *
//     * @param pl The plane that clips.
//     * @param pt A point that is used to return the side of the clipped
//     * triangle.
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode if rounding is needed.
//     * @return null, the whole or a part of this.
//     */
//    public V2D_FiniteGeometry clip(V2D_Plane pl, V2D_Point pt, int oom, RoundingMode rm) {
//        V2D_FiniteGeometry i = getIntersection(pl, oom, rm);
//        V2D_Point ppt = getPl(oom, rm).getP();
//        if (i == null) {
//            if (pl.isOnSameSide(ppt, pt, oom, rm)) {
//                return this;
//            } else {
//                return null;
//            }
//        } else if (i instanceof V2D_Point ip) {
//            /**
//             * If at least two points of the triangle are on the same side of pl
//             * as pt, then return this, otherwise return ip. As the calculation
//             * of i is perhaps imprecise, then simply testing if ip equals one
//             * of the triangle corner points and then testing another point to
//             * see if it that is on the same side as pt might not work out
//             * right!
//             */
//            int poll = 0;
//            if (pl.isOnSameSide(ppt, pt, oom, rm)) {
//                poll++;
//            }
//            if (pl.isOnSameSide(getQ(), pt, oom, rm)) {
//                poll++;
//            }
//            if (pl.isOnSameSide(getR(), pt, oom, rm)) {
//                poll++;
//            }
//            if (poll > 1) {
//                return this;
//            } else {
//                return ip;
//            }
//        } else {
//            // i instanceof V2D_LineSegment
//            V2D_LineSegment il = (V2D_LineSegment) i;
//            V2D_Point qpt = getQ();
//            V2D_Point rpt = getR();
//            if (pl.isOnSameSide(ppt, pt, oom, rm)) {
//                if (pl.isOnSameSide(qpt, pt, oom, rm)) {
//                    if (pl.isOnSameSide(rpt, pt, oom, rm)) {
//                        return this;
//                    } else {
//                        return getGeometry(il, getPQ(oom, rm), oom, rm);
//                    }
//                } else {
//                    if (pl.isOnSameSide(rpt, pt, oom, rm)) {
//                        return getGeometry(il, getRP(oom, rm), oom, rm);
//                    } else {
//                        return getGeometry(il, ppt, oom, rm);
//                    }
//                }
//            } else {
//                if (pl.isOnSameSide(qpt, pt, oom, rm)) {
//                    if (pl.isOnSameSide(rpt, pt, oom, rm)) {
//                        return getGeometry(il, getPQ(oom, rm), oom, rm);
//                    } else {
//                        return getGeometry(il, qpt, oom, rm);
//                    }
//                } else {
//                    if (pl.isOnSameSide(rpt, pt, oom, rm)) {
//                        return getGeometry(il, rpt, oom, rm);
//                    } else {
//                        return null;
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * Clips this using t.
//     *
//     * @param t The triangle to clip this with.
//     * @param pt A point that is used to return the side of this that is
//     * clipped.
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode if rounding is needed.
//     * @return null, the whole or a part of this.
//     */
//    public V2D_FiniteGeometry clip(V2D_Triangle t, V2D_Point pt, int oom, RoundingMode rm) {
//        V2D_Point tp = t.getP();
//        V2D_Point tq = t.getQ();
//        V2D_Point tr = t.getR();
//        V2D_Vector n = t.getPl(oom, rm).n;
//        V2D_Point ppt = new V2D_Point(tp.offset.add(n, oom, rm), tp.rel);
//        V2D_Plane ppl = new V2D_Plane(tp, tq, ppt, oom, rm);
//        V2D_Point qpt = new V2D_Point(tq.offset.add(n, oom, rm), tq.rel);
//        V2D_Plane qpl = new V2D_Plane(tq, tr, qpt, oom, rm);
//        V2D_Point rpt = new V2D_Point(tr.offset.add(n, oom, rm), tr.rel);
//        V2D_Plane rpl = new V2D_Plane(tr, tp, rpt, oom, rm);
//        V2D_FiniteGeometry cppl = clip(ppl, pt, oom, rm);
//        if (cppl == null) {
//            return null;
//        } else if (cppl instanceof V2D_Point) {
//            return cppl;
//        } else if (cppl instanceof V2D_LineSegment cppll) {
//            V2D_FiniteGeometry cppllcqpl = cppll.clip(qpl, pt, oom, rm);
//            if (cppllcqpl == null) {
//                return null;
//            } else if (cppllcqpl instanceof V2D_Point cppllcqplp) {
//                return getGeometry(cppll, cppllcqplp, oom, rm);
//                //return cppllcqpl;
//            } else {
//                return ((V2D_LineSegment) cppllcqpl).clip(rpl, pt, oom, rm);
//            }
//        } else if (cppl instanceof V2D_Triangle cpplt) {
//            V2D_FiniteGeometry cppltcqpl = cpplt.clip(qpl, pt, oom, rm);
//            if (cppltcqpl == null) {
//                return null;
//            } else if (cppltcqpl instanceof V2D_Point) {
//                return cppltcqpl;
//            } else if (cppltcqpl instanceof V2D_LineSegment cppltcqpll) {
//                return cppltcqpll.clip(rpl, pt, oom, rm);
//            } else if (cppltcqpl instanceof V2D_Triangle cppltcqplt) {
//                return cppltcqplt.clip(rpl, pt, oom, rm);
//            } else {
//                V2D_ConvexHull c = (V2D_ConvexHull) cppltcqpl;
//                return c.clip(rpl, pt, oom, rm);
//            }
//        } else {
//            V2D_ConvexHull c = (V2D_ConvexHull) cppl;
//            V2D_FiniteGeometry cc = c.clip(qpl, pt, oom, rm);
//            if (cc == null) {
//                return cc;
//            } else if (cc instanceof V2D_Point) {
//                return cc;
//            } else if (cc instanceof V2D_LineSegment cppll) {
//                V2D_FiniteGeometry cccqpl = cppll.clip(qpl, pt, oom, rm);
//                if (cccqpl == null) {
//                    return null;
//                } else if (cccqpl instanceof V2D_Point) {
//                    return cccqpl;
//                } else {
//                    return ((V2D_LineSegment) cccqpl).clip(rpl, pt, oom, rm);
//                }
//            } else if (cc instanceof V2D_Triangle ccct) {
//                return ccct.clip(rpl, pt, oom, rm);
//            } else {
//                V2D_ConvexHull ccc = (V2D_ConvexHull) cc;
//                return ccc.clip(rpl, pt, oom, rm);
//            }
//        }
//    }
    @Override
    public boolean isIntersectedBy(V2D_Envelope aabb, int oom, RoundingMode rm) {
        if (getEnvelope(oom, rm).isIntersectedBy(aabb, oom)) {
            if (aabb.isIntersectedBy(getP(oom, rm), oom, rm)) {
                return true;
            }
            if (aabb.isIntersectedBy(getQ(oom, rm), oom, rm)) {
                return true;
            }
            if (aabb.isIntersectedBy(getR(oom, rm), oom, rm)) {
                return true;
            }
            V2D_FiniteGeometry l = aabb.getLeft(oom, rm);
            if (l instanceof V2D_LineSegment ll) {
                if (isIntersectedBy(ll, oom, rm)) {
                    return true;
                }
            } else {
                if (isIntersectedBy((V2D_Point) l, oom, rm)) {
                    return true;
                }
            }
            V2D_FiniteGeometry right = aabb.getRight(oom, rm);
            if (right instanceof V2D_LineSegment rl) {
                if (isIntersectedBy(rl, oom, rm)) {
                    return true;
                }
            } else {
                if (isIntersectedBy((V2D_Point) right, oom, rm)) {
                    return true;
                }
            }
            V2D_FiniteGeometry t = aabb.getTop(oom, rm);
            if (t instanceof V2D_LineSegment tl) {
                if (isIntersectedBy(tl, oom, rm)) {
                    return true;
                }
            } else {
                if (isIntersectedBy((V2D_Point) t, oom, rm)) {
                    return true;
                }
            }
            V2D_FiniteGeometry b = aabb.getBottom(oom, rm);
            if (b instanceof V2D_LineSegment bl) {
                if (isIntersectedBy(bl, oom, rm)) {
                    return true;
                }
            } else {
                if (isIntersectedBy((V2D_Point) b, oom, rm)) {
                    return true;
                }
            }
        }
        return false;
    }
}
