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

import java.math.BigDecimal;
import org.ojalgo.function.BigFunction;

/**
 * Vector Line2D
 * @author Andy Turner
 * @version 1.0.0
 */
public class V2D_Line extends V2D_Geometry {

    /**
     * The angle clockwise from the Y-Axis which defines the line.
     */
    BigDecimal angle;

    /**
     * A point on the line
     */
    V2D_Point _Point2D;

    public V2D_Line(double angleClockwiseFromYAxis, V2D_Point p) {
        super(p.e);
        this.angle = new BigDecimal(angleClockwiseFromYAxis);
        this._Point2D = new V2D_Point(p);
    }

    public V2D_Line(BigDecimal a_AngleClockwiseFromYAxis, V2D_Point p) {
        super(p.e);
        this.angle = new BigDecimal(a_AngleClockwiseFromYAxis.toString());
        this._Point2D = new V2D_Point(p);
    }

    public V2D_Point[] getExtremePointsOnLine(double ydiffExtremity) {
        V2D_Point[] result = new V2D_Point[2];
        BigDecimal ydiff_BigDecimal = new BigDecimal(ydiffExtremity);
        BigDecimal xdiff_BigDecimal
                = BigFunction.TAN.invoke(angle).multiply(ydiff_BigDecimal);
        result[0] = new V2D_Point(
                e,
                this._Point2D.x.add(xdiff_BigDecimal),
                this._Point2D.y.add(ydiff_BigDecimal));
        result[1] = new V2D_Point(
                e,
                this._Point2D.x.subtract(xdiff_BigDecimal),
                this._Point2D.y.subtract(ydiff_BigDecimal));
        return result;
    }

//    public Point2D[] getExtremePointsOnLine(double ydiffExtremity){
//        Point2D[] result = new Point2D[2];
//        BigDecimal ydiff_BigDecimal = new BigDecimal(ydiffExtremity);
//        BigDecimal xdiff_BigDecimal = new BigDecimal(Math.tan(angle.doubleValue()) * ydiffExtremity);
//        result[0] = new Point2D(
//                this._Point2D.x.add(xdiff_BigDecimal),
//                this._Point2D.y.add(ydiff_BigDecimal));
//       result[1] = new Point2D(
//                this._Point2D.x.subtract(xdiff_BigDecimal),
//                this._Point2D.y.subtract(ydiff_BigDecimal));
//        return result;
//    }
    public V2D_Geometry getIntersection(
            V2D_Line a_Line2D,
            double ydiffExtremity,
            BigDecimal t,
            int a_DecimalPlacePrecision) {
        V2D_Geometry result;
        V2D_Point[] extremePointsOnThis = this.getExtremePointsOnLine(
                ydiffExtremity);
        V2D_LineSegment extremePointsOnThis_LineSegment2D = new V2D_LineSegment(
                extremePointsOnThis[0],
                extremePointsOnThis[1]);
        V2D_Point[] extremePointsOna_Line2D = a_Line2D.getExtremePointsOnLine(
                ydiffExtremity);
        V2D_LineSegment extremePointsOna_LineSegment2D = new V2D_LineSegment(
                extremePointsOna_Line2D[0],
                extremePointsOna_Line2D[1]);
        result = extremePointsOnThis_LineSegment2D.getIntersection(
                extremePointsOna_LineSegment2D,
                t);
        return result;
    }
}
