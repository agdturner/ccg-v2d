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
import uk.ac.leeds.ccg.andyt.vector.geometry.Vector_Envelope2D;
import uk.ac.leeds.ccg.andyt.vector.geometry.Vector_Point2D;

/**
 * Class for tests and example uses of Vector_Envelope2D.
 */
public class TestEnvelope2D {

    public static void main(String[] args) {
        new TestEnvelope2D().run();
    }

    public void run() {
        test_IntersectPoint2D();
        test_IntersectEnvelope2D();
    }

    public void test_IntersectPoint2D() {
        System.out.println("<test_IntersectPoint2D()>");
        Vector_Point2D aPoint;
        Vector_Point2D bPoint;
        aPoint = new Vector_Point2D("0", "0");
        bPoint = new Vector_Point2D("1", "1");
        Vector_Envelope2D aEnvelope2D = new Vector_Envelope2D(
                aPoint,
                bPoint);
        System.out.println(
                aEnvelope2D.toString() + ".instersects(" + aPoint.toString() + ") "
                + aEnvelope2D.getIntersects(aPoint));
        System.out.println(
                aEnvelope2D.toString() + ".instersects(" + bPoint.toString() + ") "
                + aEnvelope2D.getIntersects(bPoint));
        aPoint = new Vector_Point2D("0.1", "0.3");
        System.out.println(
                aEnvelope2D.toString() + ".instersects(" + aPoint.toString() + ") "
                + aEnvelope2D.getIntersects(aPoint));
        aPoint = new Vector_Point2D("1.01", "0.3");
        System.out.println(
                aEnvelope2D.toString() + ".instersects(" + aPoint.toString() + ") "
                + aEnvelope2D.getIntersects(aPoint));
        System.out.println("</test_IntersectPoint2D()>");
    }

    public void test_IntersectEnvelope2D() {
        System.out.println("<test_IntersectEnvelope2D()>");

        Vector_Point2D a_Point2D;
        Vector_Point2D b_Point2D;
        Vector_Envelope2D a_Envelope2D;
        Vector_Envelope2D b_Envelope2D;

        // Test 1
        System.out.println("<test>");

        a_Point2D = new Vector_Point2D(0, 0);
        b_Point2D = new Vector_Point2D(1, 1);
        a_Envelope2D = new Vector_Envelope2D(
                a_Point2D,
                b_Point2D);
        a_Point2D = new Vector_Point2D(1, 1);
        b_Point2D = new Vector_Point2D(2, 2);
        b_Envelope2D = new Vector_Envelope2D(
                a_Point2D,
                b_Point2D);

        if (a_Envelope2D.getIntersects(b_Envelope2D)) {
            System.out.println(
                    a_Envelope2D.toString() + ".intersects(" + b_Envelope2D.toString() + ") "
                    + a_Envelope2D.getIntersects(b_Envelope2D));
        } else {
            System.out.println(
                    a_Envelope2D.toString() + ".intersects(" + b_Envelope2D.toString() + ") "
                    + a_Envelope2D.getIntersects(b_Envelope2D));
        }
        System.out.println("</test>");

        // Test 2
        System.out.println("<test>");
        a_Point2D = new Vector_Point2D(-1, 0);
        b_Point2D = new Vector_Point2D(1, 0);
        a_Envelope2D = new Vector_Envelope2D(
                a_Point2D,
                b_Point2D);
        a_Point2D = new Vector_Point2D(0, -1);
        b_Point2D = new Vector_Point2D(0, 1);
        b_Envelope2D = new Vector_Envelope2D(
                a_Point2D,
                b_Point2D);
        if (a_Envelope2D.getIntersects(b_Envelope2D)) {
            System.out.println(
                    a_Envelope2D.toString() + ".intersects(" + b_Envelope2D.toString() + ") "
                    + a_Envelope2D.getIntersects(b_Envelope2D));
        } else {
            System.out.println(
                    a_Envelope2D.toString() + ".intersects(" + b_Envelope2D.toString() + ") "
                    + a_Envelope2D.getIntersects(b_Envelope2D));
        }
        if (b_Envelope2D.getIntersects(a_Envelope2D)) {
            System.out.println(
                    b_Envelope2D.toString() + ".intersects(" + a_Envelope2D.toString() + ") "
                    + b_Envelope2D.getIntersects(a_Envelope2D));
        } else {
            System.out.println(
                    b_Envelope2D.toString() + ".intersects(" + a_Envelope2D.toString() + ") "
                    + b_Envelope2D.getIntersects(a_Envelope2D));
        }
        System.out.println("</test>");

        // Test 3
        System.out.println("<test>");

        a_Point2D = new Vector_Point2D(-1, 0);
        b_Point2D = new Vector_Point2D(1, 0);
        a_Envelope2D = new Vector_Envelope2D(
                a_Point2D,
                b_Point2D);
        a_Point2D = new Vector_Point2D(-0.5, 0);
        b_Point2D = new Vector_Point2D(0.5, 0);
        b_Envelope2D = new Vector_Envelope2D(
                a_Point2D,
                b_Point2D);
        if (a_Envelope2D.getIntersects(b_Envelope2D)) {
            System.out.println(
                    a_Envelope2D.toString() + ".intersects(" + b_Envelope2D.toString() + ") "
                    + a_Envelope2D.getIntersects(b_Envelope2D));
        } else {
            System.out.println(
                    a_Envelope2D.toString() + ".intersects(" + b_Envelope2D.toString() + ") "
                    + a_Envelope2D.getIntersects(b_Envelope2D));
        }
        if (b_Envelope2D.getIntersects(a_Envelope2D)) {
            System.out.println(
                    b_Envelope2D.toString() + ".intersects(" + a_Envelope2D.toString() + ") "
                    + b_Envelope2D.getIntersects(a_Envelope2D));
        } else {
            System.out.println(
                    b_Envelope2D.toString() + ".intersects(" + a_Envelope2D.toString() + ") "
                    + b_Envelope2D.getIntersects(a_Envelope2D));
        }
        System.out.println("</test>");


        // Test 4
        System.out.println("<test>");

        a_Point2D = new Vector_Point2D(0, -1);
        b_Point2D = new Vector_Point2D(0, 1);
        a_Envelope2D = new Vector_Envelope2D(
                a_Point2D,
                b_Point2D);
        a_Point2D = new Vector_Point2D(0, -0.5);
        b_Point2D = new Vector_Point2D(0, 0.5);
        b_Envelope2D = new Vector_Envelope2D(
                a_Point2D,
                b_Point2D);
        if (a_Envelope2D.getIntersects(b_Envelope2D)) {
            System.out.println(
                    a_Envelope2D.toString() + ".intersects(" + b_Envelope2D.toString() + ") "
                    + a_Envelope2D.getIntersects(b_Envelope2D));
        } else {
            System.out.println(
                    a_Envelope2D.toString() + ".intersects(" + b_Envelope2D.toString() + ") "
                    + a_Envelope2D.getIntersects(b_Envelope2D));
        }
        if (b_Envelope2D.getIntersects(a_Envelope2D)) {
            System.out.println(
                    b_Envelope2D.toString() + ".intersects(" + a_Envelope2D.toString() + ") "
                    + b_Envelope2D.getIntersects(a_Envelope2D));
        } else {
            System.out.println(
                    b_Envelope2D.toString() + ".intersects(" + a_Envelope2D.toString() + ") "
                    + b_Envelope2D.getIntersects(a_Envelope2D));
        }
        System.out.println("</test>");

        // Test 5
        System.out.println("<test>");

        a_Point2D = new Vector_Point2D(0, -1);
        b_Point2D = new Vector_Point2D(0, 1);
        a_Envelope2D = new Vector_Envelope2D(
                a_Point2D,
                b_Point2D);
        a_Point2D = new Vector_Point2D(0, -0.5);
        b_Point2D = new Vector_Point2D(0, 1.5);
        b_Envelope2D = new Vector_Envelope2D(
                a_Point2D,
                b_Point2D);
        if (a_Envelope2D.getIntersects(b_Envelope2D)) {
            System.out.println(
                    a_Envelope2D.toString() + ".intersects(" + b_Envelope2D.toString() + ") "
                    + a_Envelope2D.getIntersects(b_Envelope2D));
        } else {
            System.out.println(
                    a_Envelope2D.toString() + ".intersects(" + b_Envelope2D.toString() + ") "
                    + a_Envelope2D.getIntersects(b_Envelope2D));
        }
        if (b_Envelope2D.getIntersects(a_Envelope2D)) {
            System.out.println(
                    b_Envelope2D.toString() + ".intersects(" + a_Envelope2D.toString() + ") "
                    + b_Envelope2D.getIntersects(a_Envelope2D));
        } else {
            System.out.println(
                    b_Envelope2D.toString() + ".intersects(" + a_Envelope2D.toString() + ") "
                    + b_Envelope2D.getIntersects(a_Envelope2D));
        }
        System.out.println("</test>");
        System.out.println("</test_IntersectEnvelope2D()>");

    }
}
