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
import java.util.ArrayList;

/**
 * An envelope contains all the extreme values with respect to the X and Y axes.
 * In this implementation, it may have length of zero in any direction. For a
 * point the envelope is essentially the point. The envelope may also be a line
 * or a rectangle.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_EnvelopeDouble implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the offset of this.
     */
    protected V2D_VectorDouble offset;

    /**
     * The minimum x-coordinate.
     */
    protected final double xMin;

    /**
     * The maximum x-coordinate.
     */
    protected final double xMax;

    /**
     * The minimum y-coordinate.
     */
    protected final double yMin;

    /**
     * The maximum y-coordinate.
     */
    protected final double yMax;

    /**
     * The top edge.
     */
    protected V2D_FiniteGeometryDouble t;

    /**
     * The right edge.
     */
    protected V2D_FiniteGeometryDouble r;

    /**
     * The bottom edge.
     */
    protected V2D_FiniteGeometryDouble b;

    /**
     * The left edge.
     */
    protected V2D_FiniteGeometryDouble l;
    
    /**
     * For storing all the corner points. These are in order: lb, lt, rt, rb.
     */
    protected V2D_PointDouble[] pts;

    /**
     * Create a new instance.
     *
     * @param x The x-coordinate of a point.
     * @param y The y-coordinate of a point.
     */
    public V2D_EnvelopeDouble(double x, double y) {
        this(new V2D_PointDouble(x, y));
    }

    /**
     * Create a new instance.
     *
     * @param xMin What {@link xMin} is set to.
     * @param xMax What {@link xMax} is set to.
     * @param yMin What {@link yMin} is set to.
     * @param yMax What {@link yMax} is set to.
     */
    public V2D_EnvelopeDouble(
            double xMin, double xMax,
            double yMin, double yMax) {
        this(new V2D_PointDouble(xMin, yMin),
                new V2D_PointDouble(xMax, yMax));
    }
    
    /**
     * Create a new instance.
     *
     * @param points The points used to form the envelope.
     */
    public V2D_EnvelopeDouble(V2D_PointDouble... points) {
        //offset = points[0].offset;
        //offset = V2D_VectorDouble.ZERO;
        int len = points.length;
        switch (len) {
            case 0 ->
                throw new RuntimeException("Cannot create envelope from an empty "
                        + "collection of points.");
            case 1 -> {
                //offset = points[0].offset;
                offset = V2D_VectorDouble.ZERO;
                xMin = points[0].getX();
                xMax = xMin;
                yMin = points[0].getY();
                yMax = yMin;
            }
            default -> {
                //offset = points[0].offset;
                offset = V2D_VectorDouble.ZERO;
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
    }

    /**
     * Create a new instance.
     *
     * @param gs The geometries used to form the envelope.
     */
    public V2D_EnvelopeDouble(V2D_FiniteGeometryDouble... gs) {
        V2D_EnvelopeDouble e = new V2D_EnvelopeDouble(gs[0]);
        for (V2D_FiniteGeometryDouble g : gs) {
            e = e.union(new V2D_EnvelopeDouble(g));
        }
        this.offset = e.offset;
        this.pts = e.pts;
        this.xMax = e.xMax;
        this.xMin = e.xMin;
        this.yMax = e.yMax;
        this.yMin = e.yMin;
    }

    /**
     * Create a new instance.
     *
     * @param g The geometry used to form the envelope.
     */
    public V2D_EnvelopeDouble(V2D_FiniteGeometryDouble g) {
        this(g.getPointsArray());
    }

    /**
     * Create a new instance.
     *
     * @param ls The line segment used to form the envelope.
     */
    public V2D_EnvelopeDouble(V2D_LineSegmentDouble ls) {
        this(ls.getP(), ls.getQ());
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
     * Translates this using {@code v}.
     *
     * @param v The vector of translation.
     */
    public void translate(V2D_VectorDouble v) {
        offset = offset.add(v);
        pts = null;
    }

    /**
     * @param e The V2D_Envelope to union with this.
     * @return an Envelope which is {@code this} union {@code e}.
     */
    public V2D_EnvelopeDouble union(V2D_EnvelopeDouble e) {
        if (e.isContainedBy(this)) {
            return this;
        } else {
            return new V2D_EnvelopeDouble(Math.min(e.getXMin(), getXMin()),
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
    public boolean isIntersectedBy(V2D_EnvelopeDouble e) {
        return isIntersectedBy(e, 0d);
    }

    /**
     * If {@code e} touches, or overlaps then it intersects.
     *
     * @param e The Vector_Envelope2D to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if this intersects with {@code e}.
     */
    public boolean isIntersectedBy(V2D_EnvelopeDouble e, double epsilon) {
        if (isBeyond(this, e, epsilon)) {
            return !isBeyond(e, this, epsilon);
        } else {
            return true;
        }
    }

    /**
     * @param e1 The envelope to test.
     * @param e2 The envelope to test against.
     * @return {@code true} iff e1 is beyond e2 (i.e. they do not touch or
     * intersect).
     */
    public static boolean isBeyond(V2D_EnvelopeDouble e1,
            V2D_EnvelopeDouble e2) {
        return isBeyond(e1, e2, 0d);
    }

    /**
     * @param e1 The envelope to test.
     * @param e2 The envelope to test against.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff e1 is beyond e2 (i.e. they do not touch or
     * intersect).
     */
    public static boolean isBeyond(V2D_EnvelopeDouble e1, V2D_EnvelopeDouble e2,
            double epsilon) {
        if (e1.getXMax() + epsilon < e2.getXMin()) {
            return true;
        } else if (e1.getXMin() - epsilon > e2.getXMax()) {
            return true;
        } else if (e1.getYMax() + epsilon < e2.getYMin()) {
            return true;
        } else {
            return e1.getYMin() - epsilon > e2.getYMax();
        }
    }

    /**
     * Containment includes the boundary. So anything in or on the boundary is
     * contained.
     *
     * @param e V2D_Envelope
     * @return if this is contained by {@code e}
     */
    public boolean isContainedBy(V2D_EnvelopeDouble e) {
        return getXMax() <= e.getXMax()
                && getXMin() >= e.getXMin()
                && getYMax() <= e.getYMax()
                && getYMin() >= e.getYMin();
    }

    /**
     * @param p The point to test for intersection.
     * @return {@code true} if this intersects with {@code pl}
     */
    public boolean isIntersectedBy(V2D_PointDouble p) {
        return isIntersectedBy(p.getX(), p.getY());
    }

    /**
     * @param x The x-coordinate of the point to test for intersection.
     * @param y The y-coordinate of the point to test for intersection.
     * @return {@code true} if this intersects with {@code pl}
     */
    public boolean isIntersectedBy(double x, double y) {
        return x >= getXMin() && x <= getXMax()
                && y >= getYMin() && y <= getYMax();
    }

    /**
     * @param en The envelop to intersect.
     * @return {@code null} if there is no intersection; {@code en} if
     * {@code this.equals(en)}; otherwise returns the intersection.
     */
    public V2D_EnvelopeDouble getIntersection(V2D_EnvelopeDouble en) {
        if (this.equals(en)) {
            return en;
        }
        if (!this.isIntersectedBy(en)) {
            return null;
        }
        return new V2D_EnvelopeDouble(
                Math.max(getXMin(), en.getXMin()),
                Math.min(getXMax(), en.getXMax()),
                Math.max(getYMin(), en.getYMin()),
                Math.min(getYMax(), en.getYMax()));
    }

    /**
     * Test for equality.
     *
     * @param e The V2D_Envelope to test for equality with this.
     * @return {@code true} iff this and e are equal.
     */
    public boolean equals(V2D_EnvelopeDouble e) {
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
     * Calculate and return the approximate (or exact) centroid of the envelope.
     *
     * @return The approximate or exact centre of this.
     */
    public V2D_PointDouble getCentroid() {
        return new V2D_PointDouble(
                (this.getXMax() + this.getXMin()) / 2d,
                (this.getYMax() + this.getYMin()) / 2d);
    }

    /**
     * Return all the points of this with at least oom precision.
     *
     * @return The corners of this as points.
     */
    public V2D_PointDouble[] getPoints() {
        if (pts == null) {
            pts = new V2D_PointDouble[4];
            pts[0] = new V2D_PointDouble(getXMin(), getYMin());
            pts[1] = new V2D_PointDouble(getXMin(), getYMax());
            pts[2] = new V2D_PointDouble(getXMax(), getYMax());
            pts[3] = new V2D_PointDouble(getXMax(), getYMin());
        }
        return pts;
    }
    
    /**
     * @return the left of the envelope.
     */
    public V2D_FiniteGeometryDouble getLeft() {
        if (l == null) {
            double xmin = getXMin();
            double ymin = getYMax();
            double ymax = getYMax();
            if (ymin == ymax) {
                l = new V2D_PointDouble(xmin, ymax);
            } else {
                l = new V2D_LineSegmentDouble(
                    new V2D_PointDouble(xmin, ymin),
                    new V2D_PointDouble(xmin, ymax));
            }
        }
        return l;
    }

    /**
     * @return the right of the envelope.
     */
    public V2D_FiniteGeometryDouble getRight() {
        if (r == null) {
            double xmax = getXMax();
            double ymin = getYMax();
            double ymax = getYMax();
            if (ymin == ymax) {
                r = new V2D_PointDouble(xmax, ymax);
            } else {
                r = new V2D_LineSegmentDouble(
                    new V2D_PointDouble(xmax, ymin),
                    new V2D_PointDouble(xmax, ymax));
            }
        }
        return r;
    }

    /**
     * @return the top of the envelope.
     */
    public V2D_FiniteGeometryDouble getTop() {
        if (t == null) {
            double xmin = getXMin();
            double xmax = getXMax();
            double ymax = getYMax();
            if (xmin == xmax) {
                t = new V2D_PointDouble(xmin, ymax);
            } else {
                t = new V2D_LineSegmentDouble(
                    new V2D_PointDouble(xmin, ymax),
                    new V2D_PointDouble(xmax, ymax));
            }
        }
        return t;
    }

    /**
     * @return the bottom of the envelope.
     */
    public V2D_FiniteGeometryDouble getBottom() {
        if (b == null) {
            double xmin = getXMin();
            double xmax = getXMax();
            double ymin = getYMin();
            if (xmin == xmax) {
                b = new V2D_PointDouble(xmin, ymin);
            } else {
                b = new V2D_LineSegmentDouble(
                    new V2D_PointDouble(xmin, ymin),
                    new V2D_PointDouble(xmax, ymin));
            }
        }
        return b;
    }

    /**
     * A collection method to add l to ls iff there is not already an l in ls.
     *
     * @param ls The collection.
     * @param l The line segment to add.
     * @return {@code true} iff l is unique and is added to ls.
     */
    protected boolean addUnique(ArrayList<V2D_LineDouble> ls, V2D_LineDouble l) {
        boolean unique = true;
        for (var x : ls) {
            if (x.equals(l)) {
                unique = false;
                break;
            }
        }
        if (unique) {
            ls.add(l);
        }
        return unique;
    }

    /**
     * A collection method to add pt to pts iff there is not already a pt in
     * pts.
     *
     * @param pts The collection.
     * @param pt The point to add.
     * @return {@code true} iff l is unique and is added to ls.
     */
    protected boolean addUnique(ArrayList<V2D_PointDouble> pts,
            V2D_PointDouble pt) {
        boolean unique = true;
        for (var x : pts) {
            if (x.equals(pt)) {
                unique = false;
                break;
            }
        }
        if (unique) {
            pts.add(pt);
        }
        return unique;
    }
}
