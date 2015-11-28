/**
 * Component of a library for handling spatial vector data. Copyright (C) 2009
 * Andy Turner, CCG, University of Leeds, UK.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
package uk.ac.leeds.ccg.andyt.vector.geometry;

import java.math.BigDecimal;
//import java.math.MathContext;
//import org.ojalgo.constant.BigMath;
import org.ojalgo.function.implementation.BigFunction;
import uk.ac.leeds.ccg.andyt.vector.core.Vector_Environment;
//import org.ojalgo.function.implementation.FunctionSet;
//import org.ojalgo.function.implementation.PrimitiveFunction;
//import org.ojalgo.function.UnaryFunction;

//import org.ojalgo.function.mu
/**
 *
 * @author geoagdt
 */
public class Vector_Line2D extends Vector_AbstractGeometry2D {

//    /**
//     * The angle defining the line
//     */
//    double _AngleClockwiseFromYAxis;
    /**
     * The angle defining the line
     */
    BigDecimal _AngleClockwiseFromYAxis;

    /**
     * A point on the line
     */
    Vector_Point2D _Point2D;

    public Vector_Line2D(
            double angleClockwiseFromYAxis,
            Vector_Point2D a_Point2D) {
        _Vector_Environment = a_Point2D._Vector_Environment;
        this._AngleClockwiseFromYAxis = new BigDecimal(angleClockwiseFromYAxis);
        this._Point2D = new Vector_Point2D(a_Point2D);
        set_DecimalPlacePrecision(
                Math.max(
                        this._AngleClockwiseFromYAxis.scale(),
                        this._Point2D.get_DecimalPlacePrecision()));
    }

    public Vector_Line2D(
            BigDecimal a_AngleClockwiseFromYAxis,
            Vector_Point2D a_Point2D) {
        _Vector_Environment = a_Point2D._Vector_Environment;
        this._AngleClockwiseFromYAxis = new BigDecimal(a_AngleClockwiseFromYAxis.toString());
        this._Point2D = new Vector_Point2D(a_Point2D);
        set_DecimalPlacePrecision(
                Math.max(
                        this._AngleClockwiseFromYAxis.scale(),
                        this._Point2D.get_DecimalPlacePrecision()));
    }

    /**
     * Potential precision issues with Envelope extent...
     */
    @Override
    public Vector_Envelope2D getEnvelope2D() {
        //throw new UnsupportedOperationException("Not supported yet.");
        BigDecimal minusALot = new BigDecimal(Double.MIN_VALUE);
        BigDecimal plusALot = new BigDecimal(Double.MAX_VALUE);
        Vector_Point2D a_Point2D = new Vector_Point2D(
                _Vector_Environment, minusALot, minusALot);
        Vector_Point2D b_Point2D = new Vector_Point2D(
                _Vector_Environment, plusALot, plusALot);
        return new Vector_Envelope2D(a_Point2D, b_Point2D);
    }

    @Override
    public void applyDecimalPlacePrecision() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Vector_Point2D[] getExtremePointsOnLine(double ydiffExtremity) {
        Vector_Point2D[] result = new Vector_Point2D[2];
        BigDecimal ydiff_BigDecimal = new BigDecimal(ydiffExtremity);
        BigDecimal xdiff_BigDecimal
                = BigFunction.TAN.invoke(_AngleClockwiseFromYAxis).multiply(ydiff_BigDecimal);
        result[0] = new Vector_Point2D(
                _Vector_Environment,
                this._Point2D._x.add(xdiff_BigDecimal),
                this._Point2D._y.add(ydiff_BigDecimal));
        result[1] = new Vector_Point2D(
                _Vector_Environment,
                this._Point2D._x.subtract(xdiff_BigDecimal),
                this._Point2D._y.subtract(ydiff_BigDecimal));
        return result;
    }

//    public Point2D[] getExtremePointsOnLine(double ydiffExtremity){
//        Point2D[] result = new Point2D[2];
//        BigDecimal ydiff_BigDecimal = new BigDecimal(ydiffExtremity);
//        BigDecimal xdiff_BigDecimal = new BigDecimal(Math.tan(_AngleClockwiseFromYAxis.doubleValue()) * ydiffExtremity);
//        result[0] = new Point2D(
//                this._Point2D._x.add(xdiff_BigDecimal),
//                this._Point2D._y.add(ydiff_BigDecimal));
//       result[1] = new Point2D(
//                this._Point2D._x.subtract(xdiff_BigDecimal),
//                this._Point2D._y.subtract(ydiff_BigDecimal));
//        return result;
//    }
    public Vector_AbstractGeometry2D getIntersection(
            Vector_Line2D a_Line2D,
            double ydiffExtremity,
            BigDecimal tollerance,
            int a_DecimalPlacePrecision) {
        Vector_AbstractGeometry2D result;
        Vector_Point2D[] extremePointsOnThis = this.getExtremePointsOnLine(
                ydiffExtremity);
        Vector_LineSegment2D extremePointsOnThis_LineSegment2D = new Vector_LineSegment2D(
                extremePointsOnThis[0],
                extremePointsOnThis[1]);
        Vector_Point2D[] extremePointsOna_Line2D = a_Line2D.getExtremePointsOnLine(
                ydiffExtremity);
        Vector_LineSegment2D extremePointsOna_LineSegment2D = new Vector_LineSegment2D(
                extremePointsOna_Line2D[0],
                extremePointsOna_Line2D[1]);
        result = extremePointsOnThis_LineSegment2D.getIntersection(
                extremePointsOna_LineSegment2D,
                 tollerance,
            a_DecimalPlacePrecision);
        return result;
    }
}
