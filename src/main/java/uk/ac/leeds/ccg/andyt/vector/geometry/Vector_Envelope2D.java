package uk.ac.leeds.ccg.andyt.vector.geometry;

/**
 * Library for handling spatial vector data.
 * Copyright (C) 2009 Andy Turner, CCG, University of Leeds, UK.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA.
 */
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A class for simplifying geometry in 2D. For any AbstractGeometry an
 * Vector_Envelope2D can be retrieved via getEnvelope(). It is to contain the
 * extreme values with respect to the X and Y axes.
 */
public class Vector_Envelope2D
        extends Vector_AbstractGeometry2D
        implements Serializable {

    public BigDecimal _xmin;
    public BigDecimal _xmax;
    public BigDecimal _ymin;
    public BigDecimal _ymax;

    /**
     * Creates a default Vector_Envelope2D
     <code>
        _xmin = new BigDecimal(Long.MAX_VALUE);
        _xmax = new BigDecimal(Long.MIN_VALUE);
        _ymin = new BigDecimal(Long.MAX_VALUE);
        _ymax = new BigDecimal(Long.MIN_VALUE);
     </code>
     */
    public Vector_Envelope2D() {
        _xmin = new BigDecimal(Long.MAX_VALUE);
        _xmax = new BigDecimal(Long.MIN_VALUE);
        _ymin = new BigDecimal(Long.MAX_VALUE);
        _ymax = new BigDecimal(Long.MIN_VALUE);
    }

    public Vector_Envelope2D(
            Vector_Envelope2D aEnvelope2D) {
        _ymin = new BigDecimal(aEnvelope2D._ymin.toString());
        _ymax = new BigDecimal(aEnvelope2D._ymax.toString());
        _xmin = new BigDecimal(aEnvelope2D._xmin.toString());
        _xmax = new BigDecimal(aEnvelope2D._xmax.toString());
        set_DecimalPlacePrecision(aEnvelope2D._DecimalPlacePrecision_Integer);
        applyDecimalPlacePrecision();
    }

    public Vector_Envelope2D(
            Vector_AbstractGeometry2D aAbstractGeometry2D) {
        Vector_Envelope2D aEnvelope2D = aAbstractGeometry2D.getEnvelope2D();
        _xmin = aEnvelope2D._xmin;
        _xmax = aEnvelope2D._xmin;
        _ymin = aEnvelope2D._ymin;
        _ymax = aEnvelope2D._ymax;
        set_DecimalPlacePrecision(aEnvelope2D._DecimalPlacePrecision_Integer);
        applyDecimalPlacePrecision();
    }

    public Vector_Envelope2D(
            Vector_Point2D aPoint,
            Vector_Point2D bPoint) {
        if (aPoint._y.compareTo(bPoint._y) == 1) {
            _ymin = bPoint._y;
            _ymax = aPoint._y;
        } else {
            _ymin = aPoint._y;
            _ymax = bPoint._y;
        }
        if (aPoint._x.compareTo(bPoint._x) == 1) {
            _xmin = bPoint._x;
            _xmax = aPoint._x;
        } else {
            _xmin = aPoint._x;
            _xmax = bPoint._x;
        }
        set_DecimalPlacePrecision(
                Math.max(
                aPoint.get_DecimalPlacePrecision(),
                bPoint.get_DecimalPlacePrecision()));
        applyDecimalPlacePrecision();
    }

    public Vector_Envelope2D(
            BigDecimal x,
            BigDecimal y) {
        _ymin = new BigDecimal(y.toString());
        _ymax = new BigDecimal(y.toString());
        _xmin = new BigDecimal(x.toString());
        _xmax = new BigDecimal(x.toString());
        set_DecimalPlacePrecision(
                Math.max(
                x.scale(),
                y.scale()));
        applyDecimalPlacePrecision();
    }

    public Vector_Envelope2D(
            Vector_AbstractGeometry2D[] aAbstractGeometry2DArray) {
        Vector_Envelope2D aEnvelope2D = aAbstractGeometry2DArray[0].getEnvelope2D();
        _xmin = aEnvelope2D._xmin;
        _DecimalPlacePrecision_Integer = _xmin.scale();
        _xmax = aEnvelope2D._xmin;
        _DecimalPlacePrecision_Integer = Math.max(_DecimalPlacePrecision_Integer, _xmax.scale());
        _ymin = aEnvelope2D._ymin;
        _DecimalPlacePrecision_Integer = Math.max(_DecimalPlacePrecision_Integer, _ymin.scale());
        _ymax = aEnvelope2D._ymax;
        _DecimalPlacePrecision_Integer = Math.max(_DecimalPlacePrecision_Integer, _ymax.scale());
        for (int i = 1; i < aAbstractGeometry2DArray.length; i++) {
            aEnvelope2D = aAbstractGeometry2DArray[i].getEnvelope2D();
            if (aEnvelope2D._xmin.compareTo(_xmin) == -1) {
                _xmin = aEnvelope2D._xmin;
                _DecimalPlacePrecision_Integer = Math.max(_DecimalPlacePrecision_Integer, _xmin.scale());
            }
            if (aEnvelope2D._xmax.compareTo(_xmax) == 1) {
                _xmax = aEnvelope2D._xmax;
                _DecimalPlacePrecision_Integer = Math.max(_DecimalPlacePrecision_Integer, _xmax.scale());
            }
            if (aEnvelope2D._ymin.compareTo(_ymin) == -1) {
                _ymin = aEnvelope2D._ymin;
                _DecimalPlacePrecision_Integer = Math.max(_DecimalPlacePrecision_Integer, _ymin.scale());
            }
            if (aEnvelope2D._ymax.compareTo(_ymax) == 1) {
                _ymax = aEnvelope2D._ymax;
                _DecimalPlacePrecision_Integer = Math.max(_DecimalPlacePrecision_Integer, _ymax.scale());
            }
        }
        applyDecimalPlacePrecision();
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "("
                + super.toString()
                + "xmin(" + _xmin.toString() + "),"
                + "xmax(" + _xmax.toString() + "),"
                + "ymin(" + _ymin.toString() + "),"
                + "ymax(" + _ymax.toString() + "))";
    }

    public static Vector_Envelope2D envelope(
            Vector_Envelope2D a_Envelope2D,
            Vector_Envelope2D b_Envelope2D) {
        Vector_Envelope2D result = new Vector_Envelope2D();
        result._xmin = a_Envelope2D._xmin.min(b_Envelope2D._xmin);
        result._ymin = a_Envelope2D._ymin.min(b_Envelope2D._ymin);
        result._xmax = a_Envelope2D._xmax.max(b_Envelope2D._xmax);
        result._ymax = a_Envelope2D._ymax.max(b_Envelope2D._ymax);
        return result;
    }

    public Vector_Envelope2D envelope(Vector_Envelope2D a_Envelope2D) {
        Vector_Envelope2D result = new Vector_Envelope2D();
        result._xmin = a_Envelope2D._xmin.min(this._xmin);
        result._ymin = a_Envelope2D._ymin.min(this._ymin);
        result._xmax = a_Envelope2D._xmax.max(this._xmax);
        result._ymax = a_Envelope2D._ymax.max(this._ymax);
        return result;
    }

    /**
     * If aEnvelope2D touches, or overlaps then it getIntersects.
     * @param a_Envelope2D
     * @return 
     */
    public boolean getIntersects(Vector_Envelope2D a_Envelope2D) {
        // Does this contain and corners of a_Envelope2D
        boolean containsCorner;
        containsCorner = getIntersects(
                a_Envelope2D._xmin,
                a_Envelope2D._ymin);
        if (containsCorner) {
            return containsCorner;
        }
        containsCorner = getIntersects(
                a_Envelope2D._xmin,
                a_Envelope2D._ymax);
        if (containsCorner) {
            return containsCorner;
        }
        containsCorner = getIntersects(
                a_Envelope2D._xmax,
                a_Envelope2D._ymin);
        if (containsCorner) {
            return containsCorner;
        }
        containsCorner = getIntersects(
                a_Envelope2D._xmax,
                a_Envelope2D._ymax);
        if (containsCorner) {
            return containsCorner;
        }
        // Does a_Envelope2D contain and corners of this
        containsCorner = a_Envelope2D.getIntersects(
                _xmax,
                _ymax);
        if (containsCorner) {
            return containsCorner;
        }
        containsCorner = a_Envelope2D.getIntersects(
                _xmin,
                _ymax);
        if (containsCorner) {
            return containsCorner;
        }
        containsCorner = a_Envelope2D.getIntersects(
                _xmax,
                _ymin);
        if (containsCorner) {
            return containsCorner;
        }
        containsCorner = a_Envelope2D.getIntersects(
                _xmax,
                _ymax);
        if (containsCorner) {
            return containsCorner;
        }
        /*
         * Check to see if _xmin and _xmax are between a_Envelope2D._xmin and
         * a_Envelope2D._xmax and a_Envelope2D._ymin and a_Envelope2D._ymax are
         * between _ymin and _ymax
         */
        if (a_Envelope2D._xmax.compareTo(_xmax) != 1
                && a_Envelope2D._xmax.compareTo(_xmin) != -1
                && a_Envelope2D._xmin.compareTo(_xmax) != 1
                && a_Envelope2D._xmin.compareTo(_xmin) != -1) {
            if (_ymin.compareTo(a_Envelope2D._ymax) != 1
                    && _ymin.compareTo(a_Envelope2D._ymin) != -1
                    && _ymax.compareTo(a_Envelope2D._ymax) != 1
                    && _ymax.compareTo(a_Envelope2D._ymin) != -1) {
                return true;
            }
        }
        /*
         * Check to see if a_Envelope2D._xmin and a_Envelope2D._xmax are between
         * _xmax and _ymin and _ymax are between a_Envelope2D._ymin and
         * a_Envelope2D._ymax
         */
        if (_xmax.compareTo(a_Envelope2D._xmax) != 1
                && _xmax.compareTo(a_Envelope2D._xmin) != -1
                && _xmin.compareTo(a_Envelope2D._xmax) != 1
                && _xmin.compareTo(a_Envelope2D._xmin) != -1) {
            if (a_Envelope2D._ymin.compareTo(_ymax) != 1
                    && a_Envelope2D._ymin.compareTo(_ymin) != -1
                    && a_Envelope2D._ymax.compareTo(_ymax) != 1
                    && a_Envelope2D._ymax.compareTo(_ymin) != -1) {
                return true;
            }
        }
//        /*
//         * Check to see if _ymin and _ymax are between a_Envelope2D._ymin and
//         * a_Envelope2D._ymax and a_Envelope2D._xmin and a_Envelope2D._xmax are
//         * between _xmin and _xmax
//         */
//        if (
//                a_Envelope2D._ymax.compareTo(_ymax) != 1 &&
//                a_Envelope2D._ymax.compareTo(_ymin) != -1 &&
//                a_Envelope2D._ymin.compareTo(_ymax) != 1 &&
//                a_Envelope2D._ymin.compareTo(_ymin) != -1 ) {
//            if (
//                _xmin.compareTo(a_Envelope2D._xmax) != 1 &&
//                _xmin.compareTo(a_Envelope2D._xmin) != -1 &&
//                _xmax.compareTo(a_Envelope2D._xmax) != 1 &&
//                _xmax.compareTo(a_Envelope2D._xmin) != -1 ) {
//                return true;
//            }
//        }
//        /*
//         * Check to see if a_Envelope2D._ymin and a_Envelope2D._ymax are between
//         * _ymax and _xmin and _xmax are between a_Envelope2D._xmin and
//         * a_Envelope2D._xmax
//         */
//        if (
//                _ymax.compareTo(a_Envelope2D._ymax) != 1 &&
//                _ymax.compareTo(a_Envelope2D._ymin) != -1 &&
//                _ymin.compareTo(a_Envelope2D._ymax) != 1 &&
//                _ymin.compareTo(a_Envelope2D._ymin) != -1 ) {
//            if (
//                a_Envelope2D._xmin.compareTo(_xmax) != 1 &&
//                a_Envelope2D._xmin.compareTo(_xmin) != -1 &&
//                a_Envelope2D._xmax.compareTo(_xmax) != 1 &&
//                a_Envelope2D._xmax.compareTo(_xmin) != -1 ) {
//                return true;
//            }
//        }
        return false;
    }

    /**
     * @param a_LineSegment2D
     * @return 0 if no, 1 if yes, 2 if maybe.
     */
    public int getIntersects(Vector_LineSegment2D a_LineSegment2D) {
        Vector_Envelope2D a_Envelope2D = a_LineSegment2D.getEnvelope2D();
        if (a_Envelope2D.getIntersects(getEnvelope2D())) {
            if (getIntersects(a_LineSegment2D._Start_Point2D)) {
                return 1;
            } else {
                if (getIntersects(a_LineSegment2D._End_Point2D)) {
                    return 1;
                }
                return 2;
            }
        } else {
            return 0;
        }
    }

    public boolean getIntersects(Vector_Point2D aPoint) {
        return aPoint._x.compareTo(_xmin) != -1
                && aPoint._x.compareTo(_xmax) != 1
                && aPoint._y.compareTo(_ymin) != -1
                && aPoint._y.compareTo(_ymax) != 1;
    }

    public boolean getIntersects(
            BigDecimal x,
            BigDecimal y) {
        return x.compareTo(_xmin) != -1
                && x.compareTo(_xmax) != 1
                && y.compareTo(_ymin) != -1
                && y.compareTo(_ymax) != 1;
    }

    @Override
    public Vector_Envelope2D getEnvelope2D() {
        return this;
    }

    @Override
    public void applyDecimalPlacePrecision() {
        _xmin = _xmin.setScale(
                get_DecimalPlacePrecision(),
                get_RoundingMode());
        _xmax = _xmax.setScale(
                get_DecimalPlacePrecision(),
                get_RoundingMode());
        _ymin = _ymin.setScale(
                get_DecimalPlacePrecision(),
                get_RoundingMode());
        _ymax = _ymax.setScale(
                get_DecimalPlacePrecision(),
                get_RoundingMode());
    }
}
