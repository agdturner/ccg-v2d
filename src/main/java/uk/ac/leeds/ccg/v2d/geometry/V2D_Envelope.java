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
import java.math.BigDecimal;
import uk.ac.leeds.ccg.v2d.core.V2D_Environment;
import uk.ac.leeds.ccg.v2d.geometry.envelope.V2D_EnvelopeEdgeBottom;
import uk.ac.leeds.ccg.v2d.geometry.envelope.V2D_EnvelopeEdgeLeft;
import uk.ac.leeds.ccg.v2d.geometry.envelope.V2D_EnvelopeEdgeRight;
import uk.ac.leeds.ccg.v2d.geometry.envelope.V2D_EnvelopeEdgeTop;

/**
 * An envelope contains all the extreme values with respect to the X and Y axes.
 * It is an axis aligned bounding box, which may have length of zero in any
 * direction. For a point the envelope is essentially the point.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_Envelope extends V2D_Geometry implements V2D_FiniteGeometry {

    /**
     * The minimum x-coordinate.
     */
    private BigRational xMin;

    /**
     * The maximum x-coordinate.
     */
    private BigRational xMax;

    /**
     * The minimum y-coordinate.
     */
    private BigRational yMin;

    /**
     * The maximum y-coordinate.
     */
    private BigRational yMax;
    
    public V2D_EnvelopeEdgeTop t;
    public V2D_EnvelopeEdgeRight r;
    public V2D_EnvelopeEdgeBottom b;
    public V2D_EnvelopeEdgeLeft l;

    /**
     * @param e An envelope.
     */
    public V2D_Envelope(V2D_Envelope e) {
        yMin = e.yMin;
        yMax = e.yMax;
        xMin = e.xMin;
        xMax = e.xMax;
    }

    /**
     * @param gs Any number of finite geometries;
     */
    public V2D_Envelope(V2D_FiniteGeometry... gs) {
        if (gs.length > 0) {
            V2D_Envelope g = gs[0].getEnvelope();
            xMin = g.xMin;
            xMax = g.xMax;
            yMin = g.yMin;
            yMax = g.yMax;
            for (int i = 1; i < gs.length; i++) {
                g = gs[i].getEnvelope();
                xMin = BigRational.min(xMin, g.xMin);
                xMax = BigRational.max(xMax, g.xMax);
                yMin = BigRational.min(yMin, g.yMin);
                yMax = BigRational.max(yMax, g.yMax);
            }
            init();
        }
    }

    private void init() {
        V2D_Point tl = new V2D_Point(getxMin(), getyMin());
        V2D_Point tr = new V2D_Point(getxMin(), getyMax());
        V2D_Point bl = new V2D_Point(getxMax(), getyMax());
        V2D_Point br = new V2D_Point(getxMax(), getyMin());
        t = new V2D_EnvelopeEdgeTop(tl, tr);
        r = new V2D_EnvelopeEdgeRight(tr, br);
        b = new V2D_EnvelopeEdgeBottom(br, bl);
        l = new V2D_EnvelopeEdgeLeft(bl, tl);
    }

    /**
     * @param x The x-coordinate of a point.
     * @param y The y-coordinate of a point.
     */
    public V2D_Envelope(BigRational x, BigRational y) {
        xMin = x;
        xMax = x;
        yMin = y;
        yMax = y;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "(" + super.toString()
                + "xMin=" + getxMin().toString() + ", xMax=" + getxMax().toString() + ","
                + "yMin=" + getyMin().toString() + ", yMax=" + getyMax().toString() + ")";
    }

    public V2D_Envelope envelope(V2D_Envelope e) {
        V2D_Envelope r = new V2D_Envelope();
        r.xMin = BigRational.min(e.getxMin(), this.getxMin());
        r.yMin = BigRational.min(e.getyMin(), this.getyMin());
        r.xMax = BigRational.max(e.getxMax(), this.getxMax());
        r.yMax = BigRational.max(e.getyMax(), this.getyMax());
        return r;
    }

    /**
     * If {@code e} touches, or overlaps then it intersects.
     *
     * @param e The V2D_Envelope to test for intersection.
     * @return {@code true} if this intersects with {@code e}.
     */
    public boolean getIntersects(V2D_Envelope e) {
        // Does this contain any corners of e
        boolean r = getIntersects(e.getxMin(), e.getyMin());
        if (r) {
            return r;
        }
        r = getIntersects(e.getxMin(), e.getyMax());
        if (r) {
            return r;
        }
        r = getIntersects(e.getxMax(), e.getyMin());
        if (r) {
            return r;
        }
        r = getIntersects(e.getxMax(), e.getyMax());
        if (r) {
            return r;
        }
        // Does e contain and corners of this
        r = e.getIntersects(getxMax(), getyMax());
        if (r) {
            return r;
        }
        r = e.getIntersects(getxMin(), getyMax());
        if (r) {
            return r;
        }
        r = e.getIntersects(getxMax(), getyMin());
        if (r) {
            return r;
        }
        r = e.getIntersects(getxMax(), getyMax());
        if (r) {
            return r;
        }
        /**
         * Check to see if xMin and xMax are between e.xMin and e.xMax, and
         * e.yMin and e.yMax are between yMin and yMax.
         */
        if (e.getxMax().compareTo(getxMax()) != 1 && e.getxMax().compareTo(getxMin()) != -1
                && e.getxMin().compareTo(getxMax()) != 1
                && e.getxMin().compareTo(getxMin()) != -1) {
            if (getyMin().compareTo(e.getyMax()) != 1 && getyMin().compareTo(e.getyMin()) != -1
                    && getyMax().compareTo(e.getyMax()) != 1
                    && getyMax().compareTo(e.getyMin()) != -1) {
                return true;
            }
        }
        /**
         * Check to see if e.xMin and e.xMax are between xMax, and yMin and yMax
         * are between e.yMin and e.yMax.
         */
        if (getxMax().compareTo(e.getxMax()) != 1 && getxMax().compareTo(e.getxMin()) != -1
                && getxMin().compareTo(e.getxMax()) != 1
                && getxMin().compareTo(e.getxMin()) != -1) {
            if (e.getyMin().compareTo(getyMax()) != 1 && e.getyMin().compareTo(getyMin()) != -1
                    && e.getyMax().compareTo(getyMax()) != 1
                    && e.getyMax().compareTo(getyMin()) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * A quick test for intersection between this and {@code l}.
     *
     * @param l A line segment to test for intersection.
     * @return 0 if no, 1 if yes, 2 if maybe.
     */
    public int getIntersectsFailFast(V2D_LineSegment l) {
        V2D_Envelope a_Envelope2D = l.getEnvelope();
        if (a_Envelope2D.getIntersects(getEnvelope())) {
            if (getIntersects(l.start)) {
                return 1;
            }
            if (getIntersects(l.end)) {
                return 1;
            }
            return 2;
        } else {
            return 0;
        }
    }

    /**
     * @param l A line segment to test for intersection.
     * @param t The tolerance.
     * @return {@code true} if this intersects with {@code l}.
     */
    public boolean getIntersects(V2D_LineSegment l, BigDecimal t) {
        return V2D_LineSegment.getIntersects(getxMin(), getyMin(), getxMax(), getyMax(), l, t);
    }

    /**
     * @param p The point to test for intersection.
     * @return {@code true} if this intersects with {@code p}
     */
    public boolean getIntersects(V2D_Point p) {
        return p.x.compareTo(getxMin()) != -1 && p.x.compareTo(getxMax()) != 1
                && p.y.compareTo(getyMin()) != -1 && p.y.compareTo(getyMax()) != 1;
    }

    /**
     * @param x The x-coordinate of the point to test for intersection.
     * @param y The y-coordinate of the point to test for intersection.
     * @return {@code true} if this intersects with {@code p}
     */
    public boolean getIntersects(BigDecimal x, BigDecimal y) {
        return x.compareTo(getxMin()) != -1 && x.compareTo(getxMax()) != 1
                && y.compareTo(getyMin()) != -1 && y.compareTo(getyMax()) != 1;
    }

    @Override
    public V2D_Envelope getEnvelope() {
        return this;
    }

    /**
     * @return the xMin
     */
    public BigRational getxMin() {
        return xMin;
    }

    /**
     * @return the xMax
     */
    public BigRational getxMax() {
        return xMax;
    }

    /**
     * @return the yMin
     */
    public BigRational getyMin() {
        return yMin;
    }

    /**
     * @return the yMax
     */
    public BigRational getyMax() {
        return yMax;
    }
}
