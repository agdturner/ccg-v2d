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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;

/**
 * A point is defined by two vectors: {@link #offset} and {@link #rel}. Adding
 * these gives the position of a point.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_Point extends V2D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * The origin of the Euclidean space.
     */
    public static final V2D_Point ORIGIN = new V2D_Point(0, 0);

    /**
     * The position relative to the {@link #offset}. Taken together with
     * {@link #offset}, this gives the point location.
     */
    public V2D_Vector rel;

    /**
     * Create a new instance which is completely independent of {@code pv}.
     *
     * @param p The point to clone/duplicate.
     */
    public V2D_Point(V2D_Point p) {
        super(new V2D_Vector(p.offset));
        rel = new V2D_Vector(p.rel);
    }

    /**
     * Create a new instance with {@link #offset} set to
     * {@link V2D_Vector#ZERO}.
     *
     * @param rel Cloned to initialise {@link #rel}.
     */
    public V2D_Point(V2D_Vector rel) {
        this(V2D_Vector.ZERO, rel);
    }

    /**
     * Create a new instance.
     *
     * @param offset What {@link #offset} is set to.
     * @param rel Cloned to initialise {@link #rel}.
     */
    public V2D_Point(V2D_Vector offset, V2D_Vector rel) {
        super(offset);
        this.rel = new V2D_Vector(rel);
    }

    /**
     * Create a new instance with {@link #offset} set to
     * {@link V2D_Vector#ZERO}.
     *
     * @param x What {@link #rel} x component is set to.
     * @param y What {@link #rel} y component is set to.
     */
    public V2D_Point(BigRational x, BigRational y) {
        super(V2D_Vector.ZERO);
        rel = new V2D_Vector(x, y);
    }

    /**
     * Create a new instance with {@link #offset} set to
     * {@link V2D_Vector#ZERO}.
     *
     * @param x What {@link #rel} x component is set to.
     * @param y What {@link #rel} y component is set to.
     */
    public V2D_Point(BigDecimal x, BigDecimal y) {
        this(BigRational.valueOf(x), BigRational.valueOf(y));
    }

    /**
     * Create a new instance with {@link #offset} set to
     * {@link V2D_Vector#ZERO}.
     *
     * @param x What {@link #rel} x component is set to.
     * @param y What {@link #rel} y component is set to.
     */
    public V2D_Point(double x, double y) {
        this(BigRational.valueOf(x), BigRational.valueOf(y));
    }

    /**
     * Create a new instance with {@link #offset} set to
     * {@link V2D_Vector#ZERO}.
     *
     * @param x What {@link #rel} x component is set to.
     * @param y What {@link #rel} y component is set to.
     */
    public V2D_Point(long x, long y) {
        this(BigRational.valueOf(x), BigRational.valueOf(y));
    }

    @Override
    public String toString() {
        return toStringSimple("");
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of this.
     */
    public String toStringSimple(String pad) {
        return this.getClass().getSimpleName() + "("
                + toStringFieldsSimple("") + ")";
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
     * @return A description of the fields.
     */
    @Override
    protected String toStringFields(String pad) {
        return super.toStringFields(pad) + "\n"
                + pad + ",\n"
                + pad + "rel=" + rel.toString(pad);
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    @Override
    protected String toStringFieldsSimple(String pad) {
        return pad + super.toStringFieldsSimple("") + ", rel=" + rel.toStringSimple("");
    }

    /**
     * Two points are equal if they are at the same location defined by each
     * points relative start location and translation vector at the given oom
     * and rm precision.
     *
     * @param p The point to test if it is the same as {@code this}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code pv} is the same as {@code this}.
     */
    public boolean equals(V2D_Point p, int oom, RoundingMode rm) {
        if (p == null) {
            return false;
        }
        if (this.getX(oom, rm).compareTo(p.getX(oom, rm)) == 0) {
            if (this.getY(oom, rm).compareTo(p.getY(oom, rm)) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param ps The points to test if they are coincident.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff all the points are coincident.
     */
    public static boolean equals(int oom, RoundingMode rm,
            V2D_Point... ps) {
        if (ps.length < 2) {
            return true;
        }
        for (int i = 1; i < ps.length; i++) {
            if (!ps[0].equals(ps[i], oom, rm)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public V2D_Point[] getPoints() {
        V2D_Point[] r = new V2D_Point[1];
        r[0] = this;
        return r;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @return The vector - {@code v.add(offset, oom)}.
     */
    public V2D_Vector getVector(int oom, RoundingMode rm) {
        return rel.add(offset, oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The x component of {@link #rel} with {@link #offset} applied.
     */
    public BigRational getX(int oom, RoundingMode rm) {
        return rel.getDX(oom, rm).add(offset.getDX(oom, rm));
    }

    /**
     * @param oom The Order of Magnitude for the application of {@link #offset}.
     * @param rm The RoundingMode if rounding is needed.
     * @return The y component of {@link #rel} with {@link #offset} applied.
     */
    public BigRational getY(int oom, RoundingMode rm) {
        return rel.getDY(oom, rm).add(offset.getDY(oom, rm));
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @return true iff this is equal to the ORIGIN.
     */
    public boolean isOrigin(int oom, RoundingMode rm) {
        return equals(ORIGIN, oom, rm);
    }

    /**
     * Returns true if this is between a and b. If this equals a or b then
     * return true. Being between does not necessarily mean being collinear.
     *
     * @param a A point
     * @param b A point
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @return true iff this is equal to the ORIGIN.
     */
    public boolean isBetween(V2D_Point a, V2D_Point b, int oom, RoundingMode rm) {
        if (this.equals(a, oom, rm)) {
            return true;
        }
        if (this.equals(b, oom, rm)) {
            return true;
        }
        V2D_Vector ab = new V2D_Vector(a, b, oom, rm);
        if (ab.isZero()) {
            return false;
        }
        V2D_Vector v90 = ab.rotate90();
        V2D_Line ap = new V2D_Line(a, v90);
        if (ap.isOnSameSide(this, b, oom, rm)) {
            V2D_Line bp = new V2D_Line(b, v90);
            return bp.isOnSameSide(this, a, oom, rm);
        }
        return false;
    }

    /**
     * Get the distance between this and {@code pv}.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @param p A point.
     * @return The distance from {@code pv} to this.
     */
    public Math_BigRationalSqrt getDistance(int oom, RoundingMode rm, V2D_Point p) {
        if (this.equals(p, oom, rm)) {
            return Math_BigRationalSqrt.ZERO;
        }
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom, rm), oom, rm);
    }

    /**
     * Get the distance between this and {@code pv}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @return The distance from {@code pv} to this.
     */
    public BigRational getDistance(V2D_Point p, int oom, RoundingMode rm) {
        if (this.equals(p, oom, rm)) {
            return BigRational.ZERO;
        }
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom - 6, rm), oom, rm)
                .getSqrt(oom, rm);
    }

    /**
     * Get the distance squared between this and {@code pt}.
     *
     * @param pt A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The distance squared from {@code pv} to this.
     */
    public BigRational getDistanceSquared(V2D_Point pt, int oom,
            RoundingMode rm) {
        int oomn6 = oom - 6;
        BigRational dx = getX(oomn6, rm).subtract(pt.getX(oomn6, rm));
        BigRational dy = getY(oomn6, rm).subtract(pt.getY(oomn6, rm));
        return dx.pow(2).add(dy.pow(2));
    }

    @Override
    public V2D_Envelope getEnvelope(int oom) {
        return new V2D_Envelope(oom, this);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @return The location of the point:
     * <Table>
     * <caption>Locations</caption>
     * <thead>
     * <tr><td>Code</td><td>Description</td></tr>
     * </thead>
     * <tbody>
     * <tr><td>0</td><td>0, 0</td></tr>
     * <tr><td>1</td><td>Px, Py</td></tr>
     * <tr><td>2</td><td>Px, Ny</td></tr>
     * <tr><td>3</td><td>Nx, Py</td></tr>
     * <tr><td>4</td><td>Nx, Ny</td></tr>
     * </tbody>
     * </Table>
     */
    public int getLocation(int oom, RoundingMode rm) {
        if (getX(oom, rm).compareTo(BigRational.ZERO) != -1) {
            if (getY(oom, rm).compareTo(BigRational.ZERO) != -1) {
                if (isOrigin(oom, rm)) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                return 2;
            }
        } else {
            if (getY(oom, rm).compareTo(BigRational.ZERO) != -1) {
                return 3;
            } else {
                return 4;
            }
        }
    }

    /**
     * Change {@link #offset} without changing the overall point vector by also
     * adjusting {@link #rel}.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @param offset What {@link #offset} is set to.
     */
    public void setOffset(V2D_Vector offset, int oom, RoundingMode rm) {
        if (!offset.equals(this.offset, oom, rm)) {
            rel = getVector(oom, rm).subtract(offset, oom, rm);
            this.offset = offset;
        }
    }

    /**
     * Change {@link #rel} without changing the overall point vector by also
     * adjusting {@link #offset}.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @param rel What {@link #rel} is set to.
     */
    public void setRel(V2D_Vector rel, int oom, RoundingMode rm) {
        //offset = getVector(e.oom).subtract(v, e.oom);
        offset = offset.subtract(rel, oom, rm).add(this.rel, oom, rm);
        this.rel = rel;
    }
    
    @Override
    public V2D_Point rotate(V2D_Point pt, BigRational theta, Math_BigDecimal bd, 
            int oom, RoundingMode rm) {
        int oomn9 = oom - 9;
        BigRational na = Math_AngleBigRational.normalise(theta, bd, oomn9, rm);
        if (na.compareTo(BigRational.ZERO) == 0) {
            return new V2D_Point(this);
        } else {
            return rotateN(pt, na, bd, oom, rm);
        }
    }

    @Override
    public V2D_Point rotateN(V2D_Point pt, BigRational theta, Math_BigDecimal bd, 
            int oom, RoundingMode rm) {
        int oomn9 = oom - 9;
        V2D_Vector tv = new V2D_Vector(pt.getX(oomn9, rm), pt.getY(oomn9, rm));
        V2D_Point tp = new V2D_Point(this);
        tp.translate(tv.reverse(), oomn9, rm);
        V2D_Vector tpv = tp.getVector(oomn9, rm);
        V2D_Point r = new V2D_Point(tpv.rotate(theta, bd, oomn9, rm));
        r.translate(tv, oomn9, rm);
        return r;
    }

    /**
     * A collection method for getting unique points.
     *
     * @param pts The points to derive a unique list from.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @return A unique list made from those in pts.
     */
    public static ArrayList<V2D_Point> getUnique(List<V2D_Point> pts,
            int oom, RoundingMode rm) {
//        System.out.println("Before unique");
//        for (int i = 0; i < pts.size(); i++) {
//            System.out.println("i=" + i);
//            System.out.println(pts.get(i).toStringSimple(""));
//        }
        HashSet<Integer> indexes = new HashSet<>();
        ArrayList<V2D_Point> r = new ArrayList<>();
        for (int i = 0; i < pts.size(); i++) {
            if (!indexes.contains(i)) {
                V2D_Point p = pts.get(i);
                r.add(p);
                for (int j = i + 1; j < pts.size(); j++) {
                    if (!indexes.contains(j)) {
                        V2D_Point p2 = pts.get(j);
                        if (p.equals(p2, oom, rm)) {
                            indexes.add(j);
                        }
                    }
                }
            }
        }
//        HashSet<Integer> indexes = new HashSet<>();
//        ArrayList<V2D_Point> r = new ArrayList<>();
//        for (int i = 0; i < pts.size(); i++) {
//            if (!indexes.contains(i)) {
//                V2D_Point p = pts.get(i);
//                boolean added = false;
//                for (int j = i + 1; j < pts.size(); j++) {
//                    if (p.equals(pts.get(j), oom, rm)) {
//                        //r.add(p);
//                        r.add(new V2D_Point(p));
//                        indexes.add(j);
//                        added = true;
//                        break;
//                    }
//                }
//                if (!added) {
//                    //r.add(p);
//                    r.add(new V2D_Point(p));
//                }
//            }
//        }

        System.out.println("After unique");
        for (int i = 0; i < r.size(); i++) {
            System.out.println("i=" + i);
            System.out.println(r.get(i).toStringSimple(""));
        }

        return r;
    }

    @Override
    public boolean isIntersectedBy(V2D_Envelope aabb, int oom, RoundingMode rm) {
        return aabb.isIntersectedBy(this, oom, rm);
    }

    /**
     * @param p The point to compare this with
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @return 1 if this is greater than p, -1 if this is less than p, and 0 
     * otherwise for the given oom and rm. 
     */
    public int compareTo(V2D_Point p, int oom, RoundingMode rm) {
        BigRational y = getY(oom, rm);
        BigRational py = p.getY(oom, rm);
        if (y.compareTo(py) == 1) {
            return 1;
        } else {
            if (y.compareTo(py) == -1) {
                return -1;
            } else {
                BigRational x = getX(oom, rm);
                BigRational px = p.getX(oom, rm);
                if (x.compareTo(px) == 1) {
                    return 1;
                } else {
                    if (x.compareTo(px) == -1) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            }
        }
    }
}
