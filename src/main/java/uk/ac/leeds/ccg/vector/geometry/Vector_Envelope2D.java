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
import uk.ac.leeds.ccg.vector.core.Vector_Environment;
import uk.ac.leeds.ccg.vector.geometry.Vector_LineSegment2D;
import uk.ac.leeds.ccg.vector.geometry.Vector_Point2D;

/**
 * A class for simplifying geometry in 2D. For any AbstractGeometry an
 * Vector_Envelope2D can be retrieved via getEnvelope(). It is to contain the
 * extreme values with respect to the X and Y axes.
 */
public class Vector_Envelope2D extends Vector_AbstractGeometry2D {

    public BigDecimal XMin;
    public BigDecimal XMax;
    public BigDecimal YMin;
    public BigDecimal YMax;

    public Vector_Envelope2D(Vector_Environment ve) {
        super(ve);        
    }

    public Vector_Envelope2D(Vector_Envelope2D e) {
        super(e.e);
        YMin = new BigDecimal(e.YMin.toString());
        YMax = new BigDecimal(e.YMax.toString());
        XMin = new BigDecimal(e.XMin.toString());
        XMax = new BigDecimal(e.XMax.toString());
        DecimalPlacePrecision = e.DecimalPlacePrecision;
        //applyDecimalPlacePrecision();
    }

    public Vector_Envelope2D(
            Vector_AbstractGeometry2D g) {
        super(g.e);
        Vector_Envelope2D e = g.getEnvelope2D();
        XMin = e.XMin;
        XMax = e.XMin;
        YMin = e.YMin;
        YMax = e.YMax;
        DecimalPlacePrecision = e.DecimalPlacePrecision;
        //applyDecimalPlacePrecision();
    }

    public Vector_Envelope2D(
            Vector_Point2D a,
            Vector_Point2D b) {
        super(a.e);
        if (a.y.compareTo(b.y) == 1) {
            YMin = b.y;
            YMax = a.y;
        } else {
            YMin = a.y;
            YMax = b.y;
        }
        if (a.x.compareTo(b.x) == 1) {
            XMin = b.x;
            XMax = a.x;
        } else {
            XMin = a.x;
            XMax = b.x;
        }
        DecimalPlacePrecision = Math.max(
                a.getDecimalPlacePrecision(),
                b.getDecimalPlacePrecision());
        //applyDecimalPlacePrecision();
    }

    public Vector_Envelope2D(
            Vector_Environment ve,
            BigDecimal x,
            BigDecimal y) {
        super(ve);
        YMin = new BigDecimal(y.toString());
        YMax = new BigDecimal(y.toString());
        XMin = new BigDecimal(x.toString());
        XMax = new BigDecimal(x.toString());
        DecimalPlacePrecision = Math.max(
                x.scale(),
                y.scale());
        //applyDecimalPlacePrecision();
    }

    public Vector_Envelope2D(
            Vector_AbstractGeometry2D[] g) {
        super(g[0].e);
        Vector_Envelope2D e = g[0].getEnvelope2D();
        XMin = e.XMin;
        DecimalPlacePrecision = XMin.scale();
        XMax = e.XMin;
        DecimalPlacePrecision = Math.max(DecimalPlacePrecision, XMax.scale());
        YMin = e.YMin;
        DecimalPlacePrecision = Math.max(DecimalPlacePrecision, YMin.scale());
        YMax = e.YMax;
        DecimalPlacePrecision = Math.max(DecimalPlacePrecision, YMax.scale());
        for (int i = 1; i < g.length; i++) {
            e = g[i].getEnvelope2D();
            if (e.XMin.compareTo(XMin) == -1) {
                XMin = e.XMin;
                DecimalPlacePrecision = Math.max(DecimalPlacePrecision, XMin.scale());
            }
            if (e.XMax.compareTo(XMax) == 1) {
                XMax = e.XMax;
                DecimalPlacePrecision = Math.max(DecimalPlacePrecision, XMax.scale());
            }
            if (e.YMin.compareTo(YMin) == -1) {
                YMin = e.YMin;
                DecimalPlacePrecision = Math.max(DecimalPlacePrecision, YMin.scale());
            }
            if (e.YMax.compareTo(YMax) == 1) {
                YMax = e.YMax;
                DecimalPlacePrecision = Math.max(DecimalPlacePrecision, YMax.scale());
            }
        }
        applyDecimalPlacePrecision();
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "("
                + super.toString()
                + "XMin(" + XMin.toString() + "),"
                + "XMax(" + XMax.toString() + "),"
                + "YMin(" + YMin.toString() + "),"
                + "YMax(" + YMax.toString() + "))";
    }

//    public static Vector_Envelope2D envelope(
//            Vector_Envelope2D a,
//            Vector_Envelope2D b) {
//        Vector_Envelope2D result = new Vector_Envelope2D();
//        result.XMin = a.XMin.min(b.XMin);
//        result.YMin = a.YMin.min(b.YMin);
//        result.XMax = a.XMax.max(b.XMax);
//        result.YMax = a.YMax.max(b.YMax);
//        return result;
//    }
    public Vector_Envelope2D envelope(Vector_Envelope2D e) {
        Vector_Envelope2D r = new Vector_Envelope2D(e.e);
        r.XMin = e.XMin.min(this.XMin);
        r.YMin = e.YMin.min(this.YMin);
        r.XMax = e.XMax.max(this.XMax);
        r.YMax = e.YMax.max(this.YMax);
        return r;
    }

    /**
     * If aEnvelope2D touches, or overlaps then it intersects.
     *
     * @param e The Vector_Envelope2D to test for intersection.
     * @return True iff this intersects with a_Envelope2D.
     */
    public boolean getIntersects(Vector_Envelope2D e) {
        // Does this contain and corners of e
        boolean containsCorner;
        containsCorner = getIntersects(e.XMin, e.YMin);
        if (containsCorner) {
            return containsCorner;
        }
        containsCorner = getIntersects(e.XMin, e.YMax);
        if (containsCorner) {
            return containsCorner;
        }
        containsCorner = getIntersects(e.XMax, e.YMin);
        if (containsCorner) {
            return containsCorner;
        }
        containsCorner = getIntersects(e.XMax, e.YMax);
        if (containsCorner) {
            return containsCorner;
        }
        // Does e contain and corners of this
        containsCorner = e.getIntersects(XMax, YMax);
        if (containsCorner) {
            return containsCorner;
        }
        containsCorner = e.getIntersects(XMin, YMax);
        if (containsCorner) {
            return containsCorner;
        }
        containsCorner = e.getIntersects(XMax, YMin);
        if (containsCorner) {
            return containsCorner;
        }
        containsCorner = e.getIntersects(XMax, YMax);
        if (containsCorner) {
            return containsCorner;
        }
        /**
         * Check to see if XMin and XMax are between e.XMin and e.XMax, and
         * e.YMin and e.YMax are between YMin and YMax.
         */
        if (e.XMax.compareTo(XMax) != 1
                && e.XMax.compareTo(XMin) != -1
                && e.XMin.compareTo(XMax) != 1
                && e.XMin.compareTo(XMin) != -1) {
            if (YMin.compareTo(e.YMax) != 1
                    && YMin.compareTo(e.YMin) != -1
                    && YMax.compareTo(e.YMax) != 1
                    && YMax.compareTo(e.YMin) != -1) {
                return true;
            }
        }
        /**
         * Check to see if e.XMin and e.XMax are between XMax, and YMin and YMax
         * are between e.YMin and e.YMax.
         */
        if (XMax.compareTo(e.XMax) != 1
                && XMax.compareTo(e.XMin) != -1
                && XMin.compareTo(e.XMax) != 1
                && XMin.compareTo(e.XMin) != -1) {
            if (e.YMin.compareTo(YMax) != 1
                    && e.YMin.compareTo(YMin) != -1
                    && e.YMax.compareTo(YMax) != 1
                    && e.YMax.compareTo(YMin) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param l A Vector_LineSegment2D to test for intersection.
     * @return 0 if no, 1 if yes, 2 if maybe.
     */
    public int getIntersectsFailFast(Vector_LineSegment2D l) {
        Vector_Envelope2D a_Envelope2D = l.getEnvelope2D();
        if (a_Envelope2D.getIntersects(getEnvelope2D())) {
            if (getIntersects(l.Start)) {
                return 1;
            }
            if (getIntersects(l.End)) {
                return 1;
            }
            return 2;
        } else {
            return 0;
        }
    }

    /**
     * @param l A Vector_LineSegment2D to test for intersection.
     * @param tollerance
     * @param handleOutOfMemoryError
     * @return true iff this.intersects with a_LineSegment2D.
     */
    public boolean getIntersects(
            Vector_LineSegment2D l,
            BigDecimal tollerance,
            boolean handleOutOfMemoryError) {
        return Vector_LineSegment2D.getIntersects(
                XMin, YMin, XMax, YMax,
                l,
                tollerance,
                DecimalPlacePrecision,
                handleOutOfMemoryError);
    }

    public boolean getIntersects(Vector_Point2D p) {
        return p.x.compareTo(XMin) != -1
                && p.x.compareTo(XMax) != 1
                && p.y.compareTo(YMin) != -1
                && p.y.compareTo(YMax) != 1;
    }

    public boolean getIntersects(
            BigDecimal x,
            BigDecimal y) {
        return x.compareTo(XMin) != -1
                && x.compareTo(XMax) != 1
                && y.compareTo(YMin) != -1
                && y.compareTo(YMax) != 1;
    }

    @Override
    public Vector_Envelope2D getEnvelope2D() {
        return this;
    }

    @Override
    public final void applyDecimalPlacePrecision() {
        XMin = XMin.setScale(
                getDecimalPlacePrecision(),
                get_RoundingMode());
        XMax = XMax.setScale(
                getDecimalPlacePrecision(),
                get_RoundingMode());
        YMin = YMin.setScale(
                getDecimalPlacePrecision(),
                get_RoundingMode());
        YMax = YMax.setScale(
                getDecimalPlacePrecision(),
                get_RoundingMode());
    }
}
