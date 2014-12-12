package uk.ac.leeds.ccg.andyt.vector.tests;
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

import java.math.BigDecimal;
import java.math.MathContext;
import uk.ac.leeds.ccg.andyt.vector.geometry.Vector_Point2D;
import uk.ac.leeds.ccg.andyt.vector.geometry.Vector_LineSegment2D;

/**
 * Class for tests and example uses of Vector_LineSegment2D.
 */
public class TestLineSegment2D {
    public TestLineSegment2D(){}

    public static void main(String[] args){
        new TestLineSegment2D().run();
    }

    public void run(){
        int five_DecimalPlacePrecision = 5;
        int oneHundred_DecimalPlacePrecision = 100;
        // create a SimpleLine
        Vector_Point2D a_Point2D = new Vector_Point2D(0,0);
        Vector_Point2D b_Point2D = new Vector_Point2D(1,1);
        Vector_LineSegment2D a_LineSegment2D = new Vector_LineSegment2D(
                a_Point2D,
                b_Point2D);
        int a_DecimalPlacePrecision = five_DecimalPlacePrecision;
        MathContext a_MathContextForCalculations = new MathContext(oneHundred_DecimalPlacePrecision);
        MathContext a_MathContextReturnedAbstractGeometry2D = new MathContext(five_DecimalPlacePrecision);
        BigDecimal length = a_LineSegment2D.getLength(a_DecimalPlacePrecision);
        System.out.println("length " + length.toPlainString());
        a_Point2D = new Vector_Point2D("0.1","0.1");
        System.out.println("LineSegment2D " + a_LineSegment2D +
                " intersects Point2D " + a_Point2D +
                " " + a_LineSegment2D.getIntersects(a_Point2D,a_DecimalPlacePrecision));
        a_Point2D = new Vector_Point2D("0.0001","0.0001");
        System.out.println("LineSegment2D " + a_LineSegment2D +
                " intersects Point2D " + a_Point2D +
                " " + a_LineSegment2D.getIntersects(a_Point2D,a_DecimalPlacePrecision));
        a_Point2D = new Vector_Point2D("0","1",a_DecimalPlacePrecision);
        b_Point2D = new Vector_Point2D("1","0",a_DecimalPlacePrecision);
        Vector_LineSegment2D b_LineSegment2D = new Vector_LineSegment2D(
                a_Point2D,
                b_Point2D);
        System.out.println("LineSegment2D " + a_LineSegment2D +
                " intersects LineSegment2D " + b_LineSegment2D +
                " " + a_LineSegment2D.getIntersects(b_LineSegment2D,a_DecimalPlacePrecision));
        System.out.println("LineSegment2D " + a_LineSegment2D +
                " intersection LineSegment2D " + b_LineSegment2D +
                " " + a_LineSegment2D.getIntersection(b_LineSegment2D,a_DecimalPlacePrecision));
        a_Point2D = new Vector_Point2D("0.5","0.5",a_DecimalPlacePrecision);
        b_Point2D = new Vector_Point2D("1","1",a_DecimalPlacePrecision);
        b_LineSegment2D = new Vector_LineSegment2D(
                a_Point2D,
                b_Point2D);
        System.out.println("LineSegment2D " + a_LineSegment2D +
                " intersects LineSegment2D " + b_LineSegment2D +
                " " + a_LineSegment2D.getIntersects(b_LineSegment2D,a_DecimalPlacePrecision));
        System.out.println("LineSegment2D " + a_LineSegment2D +
                " intersection LineSegment2D " + b_LineSegment2D +
                " " + a_LineSegment2D.getIntersection(b_LineSegment2D,a_DecimalPlacePrecision));
    }
}
