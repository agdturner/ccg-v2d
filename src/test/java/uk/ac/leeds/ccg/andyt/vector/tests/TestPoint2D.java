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
import uk.ac.leeds.ccg.andyt.vector.geometry.Vector_Point2D;

/**
 * Class for tests and example uses of Vector_Point2D.
 */
public class TestPoint2D {

    public static void main(String[] args) {
        new TestPoint2D().run();
    }

    public void run() {
        test_Constructors();
        test_Equality();
    }

    public void test_Constructors() {
        System.out.println("<test_Constructors()>");
        Vector_Point2D a_Point;

        a_Point = new Vector_Point2D("0.1", "0.3");
        System.out.println(
                "new Point2D(\"0.1\",\"0.3\"); "
                + a_Point.toString());

        a_Point = new Vector_Point2D("0.1", "0.3", 2);
        System.out.println(
                "new Point2D(\"0.1\",\"0.3\",2); "
                + a_Point.toString());

        a_Point = new Vector_Point2D(0, 0);
        System.out.println(
                "new Point2D(0,0); "
                + a_Point.toString());

        a_Point = new Vector_Point2D(0.1, 0.3);
        System.out.println(
                "new Point2D(0.1d,0.3d); "
                + a_Point.toString());

        a_Point = new Vector_Point2D(0.1d, 0.3d, 1);
        System.out.println(
                "new Point2D(0.1d,0.3d,2); "
                + a_Point.toString());

        a_Point = new Vector_Point2D(0.1d, 0.3d, 2);
        System.out.println(
                "new Point2D(0.1d,0.3d,2); "
                + a_Point.toString());

        a_Point = new Vector_Point2D(
                new BigDecimal("0.1"),
                new BigDecimal("0.3"),
                new BigDecimal("0.005"),
                new BigDecimal("0.003"));
        System.out.println(
                "a_Point = new Point2D(" +
                "new BigDecimal(\"0.1\")," +
                "new BigDecimal(\"0.3\")," +
                "new BigDecimal(\"0.005\")," +
                "new BigDecimal(\"0.003\"));" +
                a_Point.toString());

        System.out.println("</test_Constructors()>");


    }

    public void test_Equality() {
        System.out.println("<test_Equality()>");

        Vector_Point2D a_Point;
        a_Point = new Vector_Point2D(0.1d, 0.3d);
        System.out.println(
                "a_Point = new Point(0.1d,0.3d); "
                + a_Point.toString());

        Vector_Point2D b_Point;
        b_Point = new Vector_Point2D("0.1", "0.3");
        System.out.println(
                "b_Point = new Point2D(\"0.1\",\"0.3\"); "
                + b_Point.toString());

        System.out.println(
                "a_Point.equals(b_Point); "
                + a_Point.equals(b_Point));

        Vector_Point2D c_Point;
        c_Point = new Vector_Point2D(a_Point, 1);
        System.out.println(
                "c_Point = new Point2D(a_Point,1); "
                + c_Point.toString());

        System.out.println(
                "b_Point.equals(c_Point); "
                + b_Point.equals(c_Point));

        System.out.println("</test_Equality()>");

    }
}
