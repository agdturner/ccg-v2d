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

import java.io.Serializable;
import java.util.HashSet;
import uk.ac.leeds.ccg.v2d.core.d.V2D_Environment_d;

/**
 * An envelope contains all the extreme values with respect to the X and Y axes.
 * In this implementation, it may have length of zero in any direction. For a
 * point the envelope is essentially the point. The envelope may also be a line
 * or a rectangle.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_AABB_d implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The environment.
     */
    protected final V2D_Environment_d env;

    /**
     * For storing the offset of this.
     */
    private V2D_Vector_d offset;

    /**
     * The minimum x-coordinate.
     */
    private final double xMin;

    /**
     * The maximum x-coordinate.
     */
    private final double xMax;

    /**
     * The minimum y-coordinate.
     */
    private final double yMin;

    /**
     * The maximum y-coordinate.
     */
    private final double yMax;

    /**
     * For storing the left point.
     */
    protected V2D_Point_d ll;

    /**
     * For storing the upper left point.
     */
    protected V2D_Point_d ul;

    /**
     * For storing the upper right point.
     */
    protected V2D_Point_d ur;

    /**
     * For storing the lower right point.
     */
    protected V2D_Point_d lr;

    /**
     * The top/upper edge.
     */
    protected V2D_FiniteGeometry_d t;

    /**
     * The right edge.
     */
    protected V2D_FiniteGeometry_d r;

    /**
     * The bottom/lower edge.
     */
    protected V2D_FiniteGeometry_d b;

    /**
     * The left edge.
     */
    protected V2D_FiniteGeometry_d l;

    /**
     * For storing all the points. N.B {@link #ll}, {@link #ul}, {@link #ur},
     * {@link #ul} may all be the same.
     */
    protected HashSet<V2D_Point_d> pts;

    /**
     * @param e An envelop.
     */
    public V2D_AABB_d(V2D_AABB_d e) {
        env = e.env;
        offset = e.offset;
        yMin = e.yMin;
        yMax = e.yMax;
        xMin = e.xMin;
        xMax = e.xMax;
        t = e.t;
        r = e.r;
        b = e.b;
        l = e.l;
        ll = e.ll;
        ul = e.ul;
        ur = e.ur;
        lr = e.lr;
        pts = e.pts;
    }

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param x The x-coordinate of a point.
     * @param y The y-coordinate of a point.
     */
    public V2D_AABB_d(V2D_Environment_d env, double x, double y) {
        this(new V2D_Point_d(env, x, y));
    }

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param xMin What {@link xMin} is set to.
     * @param xMax What {@link xMax} is set to.
     * @param yMin What {@link yMin} is set to.
     * @param yMax What {@link yMax} is set to.
     */
    public V2D_AABB_d(V2D_Environment_d env,
            double xMin, double xMax,
            double yMin, double yMax) {
        this(new V2D_Point_d(env, xMin, yMin),
                new V2D_Point_d(env, xMax, yMax));
    }

    /**
     * Create a new instance.
     *
     * @param gs The geometries used to form the envelope.
     */
    public V2D_AABB_d(V2D_FiniteGeometry_d... gs) {
        V2D_AABB_d e = new V2D_AABB_d(gs[0]);
        for (V2D_FiniteGeometry_d g : gs) {
            e = e.union(new V2D_AABB_d(g));
        }
        env = e.env;
        yMin = e.yMin;
        yMax = e.yMax;
        xMin = e.xMin;
        xMax = e.xMax;
        t = e.t;
        r = e.r;
        b = e.b;
        l = e.l;
        ll = e.ll;
        ul = e.ul;
        ur = e.ur;
        lr = e.lr;
        pts = e.pts;
    }

    /**
     * Create a new instance.
     *
     * @param g The geometry used to form the envelope.
     */
    public V2D_AABB_d(V2D_FiniteGeometry_d g) {
        this(g.getPointsArray());
    }

    /**
     * Create a new instance.
     *
     * @param points The points used to form the envelope.
     */
    public V2D_AABB_d(V2D_Point_d... points) {
        //offset = points[0].offset;
        //offset = V2D_Vector_d.ZERO;
        int len = points.length;
        switch (len) {
            case 0 ->
                throw new RuntimeException("Cannot create envelope from an empty "
                        + "collection of points.");
            case 1 -> {
                //offset = points[0].offset;
                offset = V2D_Vector_d.ZERO;
                xMin = points[0].getX();
                xMax = xMin;
                yMin = points[0].getY();
                yMax = yMin;
                t = points[0];
                l = t;
                r = t;
                b = t;
            }
            default -> {
                //offset = points[0].offset;
                offset = V2D_Vector_d.ZERO;
                double xmin = points[0].getX();
                double xmax = xmin;
                double ymin = points[0].getY();
                double ymax = ymin;
                for (int i = 1; i < points.length; i++) {
                    double x = points[i].getX();
                    xmin = Math.min(xmin, x);
                    xmax = Math.max(xmax, x);
                    double y = points[i].getY();
                    ymin = Math.min(ymin, y);
                    ymax = Math.max(ymax, y);
                }
                xMin = xmin;
                xMax = xmax;
                yMin = ymin;
                yMax = ymax;
            }
        }
        env = points[0].env;
    }

    /**
     * @return This represented as a string.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + "(xMin=" + getXMin() + ", xMax=" + getXMax()
                + ", yMin=" + getYMin() + ", yMax=" + getYMax() + ")";
    }

    /**
     * @return {@link #pts} initialising first if it is null.
     */
    public HashSet<V2D_Point_d> getPoints() {
        if (pts == null) {
            pts = new HashSet<>(4);
            pts.add(getLL());
            pts.add(getUL());
            pts.add(getUR());
            pts.add(getLR());
        }
        return pts;
    }

    /**
     * Test for equality.
     *
     * @param e The V2D_AABB to test for equality with this.
     * @return {@code true} iff {@code this} and {@code e} are equal.
     */
    public boolean equals(V2D_AABB_d e) {
        return this.getXMin() == e.getXMin()
                && this.getXMax() == e.getXMax()
                && this.getYMin() == e.getYMin()
                && this.getYMax() == e.getYMax();
    }

    /**
     * For getting {@link #xMin}.
     *
     * @return {@link #xMin}.
     */
    public double getXMin() {
        return xMin + offset.dx;
    }

    /**
     * For getting {@link #xMax}.
     *
     * @return {@link #xMax}.
     */
    public double getXMax() {
        return xMax + offset.dx;
    }

    /**
     * For getting {@link #yMin}.
     *
     * @return {@link #yMin}.
     */
    public double getYMin() {
        return yMin + offset.dy;
    }

    /**
     * For getting {@link #yMax}.
     *
     * @return {@link #yMax}.
     */
    public double getYMax() {
        return yMax + offset.dy;
    }

    /**
     * @return The LL corner point {@link #ll} setting it first if it is null.
     */
    public V2D_Point_d getLL() {
        if (ll == null) {
            ll = new V2D_Point_d(env, xMin, yMin);
        }
        return ll;
    }

    /**
     * @return The UL corner point {@link #ul} setting it first if it is null.
     */
    public V2D_Point_d getUL() {
        if (ul == null) {
            ul = new V2D_Point_d(env, xMin, yMax);
        }
        return ul;
    }

    /**
     * @return The UR corner point {@link #ur} setting it first if it is null.
     */
    public V2D_Point_d getUR() {
        if (ur == null) {
            ur = new V2D_Point_d(env, xMax, yMax);
        }
        return ur;
    }

    /**
     * @return The LR corner point {@link #lr} setting it first if it is null.
     */
    public V2D_Point_d getLR() {
        if (lr == null) {
            lr = new V2D_Point_d(env, xMax, yMin);
        }
        return lr;
    }

    /**
     * @return the left of the envelope.
     */
    public V2D_FiniteGeometry_d getLeft() {
        if (l == null) {
            double xmin = getXMin();
            double ymin = getYMin();
            double ymax = getYMax();
            if (ymin == ymax) {
                l = new V2D_Point_d(env, xmin, ymax);
            } else {
                l = new V2D_LineSegment_d(
                        new V2D_Point_d(env, xmin, ymin),
                        new V2D_Point_d(env, xmin, ymax));
            }
        }
        return l;
    }

    /**
     * @return the right of the envelope.
     */
    public V2D_FiniteGeometry_d getRight() {
        if (r == null) {
            double xmax = getXMax();
            double ymin = getYMin();
            double ymax = getYMax();
            if (ymin == ymax) {
                r = new V2D_Point_d(env, xmax, ymax);
            } else {
                r = new V2D_LineSegment_d(
                        new V2D_Point_d(env, xmax, ymin),
                        new V2D_Point_d(env, xmax, ymax));
            }
        }
        return r;
    }

    /**
     * @return the top of the envelope.
     */
    public V2D_FiniteGeometry_d getTop() {
        if (t == null) {
            double xmin = getXMin();
            double xmax = getXMax();
            double ymax = getYMax();
            if (xmin == xmax) {
                t = new V2D_Point_d(env, xmin, ymax);
            } else {
                t = new V2D_LineSegment_d(
                        new V2D_Point_d(env, xmin, ymax),
                        new V2D_Point_d(env, xmax, ymax));
            }
        }
        return t;
    }

    /**
     * @return the bottom of the envelope.
     */
    public V2D_FiniteGeometry_d getBottom() {
        if (b == null) {
            double xmin = getXMin();
            double xmax = getXMax();
            double ymin = getYMin();
            if (xmin == xmax) {
                b = new V2D_Point_d(env, xmin, ymin);
            } else {
                b = new V2D_LineSegment_d(
                        new V2D_Point_d(env, xmin, ymin),
                        new V2D_Point_d(env, xmax, ymin));
            }
        }
        return b;
    }

    /**
     * Translates this using {@code v}.
     *
     * @param v The vector of translation.
     */
    public void translate(V2D_Vector_d v) {
        offset = offset.add(v);
        pts = null;
        ll = null;
        ul = null;
        ur = null;
        lr = null;
        l = null;
        t = null;
        r = null;
        b = null;
//        xMax += v.dx;
//        xMin += v.dx;
//        yMax += v.dy;
//        yMin += v.dy;
    }

    /**
     * Calculate and return the approximate (or exact) centroid of the envelope.
     *
     * @return The approximate or exact centre of this.
     */
    public V2D_Point_d getCentroid() {
        return new V2D_Point_d(env,
                (this.getXMax() + this.getXMin()) / 2d,
                (this.getYMax() + this.getYMin()) / 2d);
    }

    /**
     * @param e The V2D_AABB to union with this.
     * @return an Envelope which is {@code this} union {@code e}.
     */
    public V2D_AABB_d union(V2D_AABB_d e) {
        if (contains(e)) {
            return this;
        } else {
            return new V2D_AABB_d(e.env,
                    Math.min(e.getXMin(), getXMin()),
                    Math.max(e.getXMax(), getXMax()),
                    Math.min(e.getYMin(), getYMin()),
                    Math.max(e.getYMax(), getYMax()));
        }
    }

    /**
     * If {@code e} touches, or overlaps then it intersects.
     *
     * @param e The Vector_Envelope2D to test for intersection.
     * @return {@code true} if this intersects with {@code e}.
     */
    public boolean intersects(V2D_AABB_d e) {
        return intersects(e, 0d);
    }

    /**
     * If {@code e} touches, or overlaps then it intersects.
     *
     * @param e The Vector_Envelope2D to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if this intersects with {@code e}.
     */
    public boolean intersects(V2D_AABB_d e, double epsilon) {
        if (isBeyond(e, epsilon)) {
            return !e.isBeyond(this, epsilon);
        } else {
            return true;
        }
    }

    /**
     * @param e The envelope to test if {@code this} is beyond.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} is beyond {@code e} (i.e. they do
     * not touch or intersect).
     */
    public boolean isBeyond(V2D_AABB_d e, double epsilon) {
        return getXMax() + epsilon < e.getXMin()
                || getXMin() - epsilon > e.getXMax()
                || getYMax() + epsilon < e.getYMin()
                || getYMin() - epsilon > e.getYMax();
    }

    /**
     * Containment includes the boundary. So anything in or on the boundary is
     * contained.
     *
     * @param e V2D_AABB
     * @return if this is contained by {@code e}
     */
    public boolean contains(V2D_AABB_d e) {
        return getXMax() <= e.getXMax()
                && getXMin() >= e.getXMin()
                && getYMax() <= e.getYMax()
                && getYMin() >= e.getYMin();
    }

    /**
     * The location of p may get rounded.
     *
     * @param p The point to test if it is contained.
     * @return {@code} true iff {@code this} contains {@code p}
     */
    public boolean contains(V2D_Point_d p) {
        return contains(p.getX(), p.getY());
    }

    /**
     * @param x The x-coordinate of the point to test for intersection.
     * @param y The y-coordinate of the point to test for intersection.
     * @return {@code true} if this intersects with {@code pl}
     */
    public boolean contains(double x, double y) {
        return getXMax() <= x
                && getXMin() >= x
                && getYMax() <= y
                && getYMin() >= y;
    }

    /**
     * @param l The line to test for containment.
     * @return {@code true} if this intersects with {@code pl}
     */
    public boolean contains(V2D_LineSegment_d l) {
        return contains(l.getP()) && contains(l.getQ());
    }

    /**
     * @param s The shape to test for containment.
     * @return {@code true} if this intersects with {@code pl}
     */
    public boolean contains(V2D_Area_d s) {
        return contains(s.getAABB()) && contains0(s);
    }

    /**
     * @param s The shape to test for containment.
     * @return {@code true} if this intersects with {@code pl}
     */
    public boolean contains0(V2D_Area_d s) {
        return s.getPoints().values().parallelStream().allMatch(x
                -> contains(x));
    }

    /**
     * @param p The point to test for intersection.
     * @return {@code true} if this intersects with {@code pl}
     */
    public boolean intersects(V2D_Point_d p) {
        return intersects(p.getX(), p.getY());
    }

    /**
     * @param x The x-coordinate of the point to test for intersection.
     * @param y The y-coordinate of the point to test for intersection.
     * @return {@code true} if this intersects with {@code pl}
     */
    public boolean intersects(double x, double y) {
        return x >= getXMin()
                && x <= getXMax()
                && y >= getYMin()
                && y <= getYMax();
    }

    /**
     * @param en The envelop to intersect.
     * @return {@code null} if there is no intersection; {@code en} if
     * {@code this.equals(en)}; otherwise returns the intersection.
     */
    public V2D_AABB_d getIntersection(V2D_AABB_d en) {
        if (!this.intersects(en)) {
            return null;
        }
        return new V2D_AABB_d(en.env,
                Math.max(getXMin(), en.getXMin()),
                Math.min(getXMax(), en.getXMax()),
                Math.max(getYMin(), en.getYMin()),
                Math.min(getYMax(), en.getYMax()));
    }
}
