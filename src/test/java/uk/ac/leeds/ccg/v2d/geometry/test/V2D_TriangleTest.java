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
package uk.ac.leeds.ccg.v2d.geometry.test;

import ch.obermuhlner.math.big.BigRational;
import java.math.RoundingMode;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v2d.geometry.V2D_ConvexHull;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Envelope;
import uk.ac.leeds.ccg.v2d.geometry.V2D_FiniteGeometry;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Geometry;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Line;
import uk.ac.leeds.ccg.v2d.geometry.V2D_LineSegment;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Point;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Ray;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Triangle;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Vector;

/**
 * Test of V2D_Triangle class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_TriangleTest extends V2D_Test {

    public V2D_TriangleTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of isIntersectedBy method, of class V2D_Triangle.
     */
    @Test
    public void testIsAligned_3args() {
        System.out.println("isAligned");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Point pt = pP0N1;
        V2D_Triangle instance = new V2D_Triangle(pP0P2, pP0N2, pP2P0, oom, rm);
        assertTrue(instance.isAligned(pt, oom, rm));
        pt = pN1P0;
        assertFalse(instance.isAligned(pt, oom, rm));
        instance = new V2D_Triangle(pP0P0, pP0P1, pP1P1, oom, rm);
        pt = pP2P2;
        assertFalse(instance.isAligned(pt, oom, rm));
        
    }

    /**
     * Test of isIntersectedBy method, of class V2D_Triangle.
     */
    @Test
    public void testIsAligned_V2D_LineSegment_double() {
        System.out.println("isAligned");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_LineSegment ls;
        V2D_Triangle instance;
        instance = new V2D_Triangle(pP0P0, pP0P1, pP1P1, oom, rm);
        ls = new V2D_LineSegment(pP2P2, pP2P1, oom, rm);
        assertFalse(instance.isAligned(ls, oom, rm));
        
    }

    /**
     * Test of isIntersectedBy method, of class V2D_Triangle.
     */
    @Test
    public void testIsIntersectedBy() {
        System.out.println("isIntersectedBy");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Point pt = pP0P0;
        V2D_Triangle instance = new V2D_Triangle(pN1N1, pP0P2, pP1N1, oom, rm);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 2
        pt = pP0P1;
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 3
        pt = pP1P2;
        assertFalse(instance.isIntersectedBy(pt, oom, rm));
        // Test 4
        pt = pN1P2;
        assertFalse(instance.isIntersectedBy(pt, oom, rm));
    }

    /**
     * Test of getEnvelope method, of class V2D_Triangle.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Triangle instance = new V2D_Triangle(pP0P0, pP0P1, pP1P0, oom, rm);
        V2D_Envelope expResult = new V2D_Envelope(oom, pP0P0, pP0P1, pP1P0);
        V2D_Envelope result = instance.getEnvelope(oom, rm);
        assertTrue(expResult.equals(result, oom));
    }

    /**
     * Test of getArea method, of class V2D_Triangle.
     */
    @Test
    public void testGetArea() {
        System.out.println("getArea");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Triangle instance = new V2D_Triangle(pP0P0, pP0P1, pP1P0, oom, rm);
        BigRational expResult = BigRational.valueOf(1, 2);
        BigRational result = instance.getArea(oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
    }

    /**
     * Test of translate method, of class V2D_Triangle.
     */
    @Test
    public void testApply() {
        // No test.
    }

    /**
     * Test of getPerimeter method, of class V2D_Triangle.
     */
    @Test
    public void testGetPerimeter() {
        System.out.println("getPerimeter");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Triangle instance = new V2D_Triangle(pP0P0, pP0P1, pP1P0, oom, rm);
        BigRational expResult = BigRational.TWO.add(new Math_BigRationalSqrt(2, oom, rm).getSqrt(oom, rm));
        BigRational result = instance.getPerimeter(oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        expResult = BigRational.TWO.add(new Math_BigRationalSqrt(2, oom, rm).getSqrt(oom, rm));
        result = instance.getPerimeter(oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
    }

    /**
     * Test of getIntersection method, of class V2D_Triangle.
     */
    @Test
    public void testGetIntersection_V2D_Line() {
        System.out.println("getIntersection");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Line l = new V2D_Line(pP1N1, pP1P2, oom, rm);
        V2D_Triangle instance = new V2D_Triangle(pP0P0, pP1P1, pP2P0, oom, rm);
        V2D_Geometry expResult = new V2D_LineSegment(pP1P0, pP1P1, oom, rm);
        V2D_Geometry result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
        System.out.println("getIntersection");
        // Test 2
        instance = new V2D_Triangle(P0P0, P1P0, P1P2, P2P0, oom, rm);
        l = new V2D_Line(pP1P1, pP1N1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        expResult = new V2D_LineSegment(pP1P0, pP1P2, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
        // Test 3
        l = new V2D_Line(pP1P0, pP2P0, oom, rm);
        expResult = new V2D_LineSegment(pP1P0, pP2P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
        // Test 4
        l = new V2D_Line(pP1P0, pP2P0, oom, rm);
        expResult = new V2D_LineSegment(pP1P0, pP2P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
    }

    /**
     * Test of getCentroid method, of class V2D_Triangle.
     */
    @Test
    public void testGetCentroid() {
        System.out.println("getCentroid");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Triangle instance;
        V2D_Point expResult;
        V2D_Point result;
        // Test
        instance = new V2D_Triangle(pP0P0, pP1P0, pP1P1, oom, rm);
        expResult = new V2D_Point(2d / 3d, 1d / 3d);
        result = instance.getCentroid(oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
    }

    /**
     * Test of toString method, of class V2D_Triangle.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V2D_Triangle instance = new V2D_Triangle(P0P0, P1P0, P0P1, P0P0);
        String expResult = """
                           V2D_Triangle(
                            offset=(V2D_Vector(dx=0, dy=0)),
                            p=(V2D_Vector(dx=1, dy=0)),
                            q=(V2D_Vector(dx=0, dy=1)),
                            r=(V2D_Vector(dx=0, dy=0)))""";
        String result = instance.toString();
        System.out.println(result);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isIntersectedBy method, of class V2D_Triangle.
     */
    @Test
    public void testIsIntersectedBy_V2D_Point() {
        System.out.println("isIntersectedBy");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Point pt;
        V2D_Triangle instance;
        instance = new V2D_Triangle(P0P0, P1P0, P0P1, P0P0);
        assertTrue(instance.isIntersectedBy(pP1P0, oom, rm));
        assertTrue(instance.isIntersectedBy(pP0P1, oom, rm));
        assertTrue(instance.isIntersectedBy(pP0P0, oom, rm));
        pt = new V2D_Point(P1P0, P0P0);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 5
        pt = new V2D_Point(P0P1, P0P0);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 6
        instance = new V2D_Triangle(P0P0, P1P0, P1P2, P2P0);
        assertTrue(instance.isIntersectedBy(pP1P2, oom, rm));
        assertTrue(instance.isIntersectedBy(pP1P1, oom, rm));
        assertFalse(instance.isIntersectedBy(pN1P0, oom, rm));
    }

    /**
     * Test of getIntersection method, of class V2D_Triangle.
     */
    @Test
    public void testGetIntersection_V2D_LineSegment() {
        System.out.println("getIntersection");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_LineSegment l;
        V2D_Triangle instance;
        V2D_Geometry expResult;
        V2D_Geometry result;
        // Test 1
        instance = new V2D_Triangle(pP1P0, pP1P2, pP2P0, oom, rm);
        l = new V2D_LineSegment(pP1P1, pP1N1, oom, rm);
        expResult = new V2D_LineSegment(pP1P1, pP1P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
        // Test 2
        l = new V2D_LineSegment(pP1P0, pP2P0, oom, rm);
        expResult = new V2D_LineSegment(pP1P0, pP2P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
        // Test 3
        l = new V2D_LineSegment(pP1P0, pP2P0, oom, rm);
        expResult = new V2D_LineSegment(pP1P0, pP2P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
        // Test 4
        l = new V2D_LineSegment(pP2N2, pP2P1, oom, rm);
        instance = new V2D_Triangle(P0P0, P2P2,
                new V2D_Vector(4, 0), oom, rm);
        expResult = new V2D_LineSegment(pP2P0, pP2P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
        // Test 5
        l = new V2D_LineSegment(pP0P0, pP0P1, oom, rm);
        instance = new V2D_Triangle(pN2N2, pP0P2, pP2N2, oom, rm);
        expResult = new V2D_LineSegment(pP0P0, pP0P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
        // Test 6
        l = new V2D_LineSegment(new V2D_Point(4d, -2d), pP2P0, oom, rm);
        instance = new V2D_Triangle(pP0P0, new V2D_Point(4d, 0d), new V2D_Point(2d, -4d), oom, rm);
        expResult = new V2D_LineSegment(pP2P0, new V2D_Point(10d/3d, - 4d/3d), oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
    }

    /**
     * Test of equals method, of class V2D_Triangle.
     */
    @Test
    public void testEquals_V2D_Triangle() {
        System.out.println("equals");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Triangle t = new V2D_Triangle(pP1P0, pP1P2, pP2P0, oom, rm);
        V2D_Triangle instance = new V2D_Triangle(P1P0, P0P0, P0P2, P1P0);
        boolean result = instance.equals(t, oom, rm);
        assertTrue(result);
        // Test 2
        instance = new V2D_Triangle(P1P0, P0P2, P0P0, P1P0);
        result = instance.equals(t, oom, rm);
        assertTrue(result);
        // Test 3
        instance = new V2D_Triangle(P1P0, P0P2, P1P0, P0P0);
        result = instance.equals(t, oom, rm);
        assertTrue(result);
        // Test 4
        instance = new V2D_Triangle(P1P0, P1P0, P0P2, P0P0);
        result = instance.equals(t, oom, rm);
        assertTrue(result);
        // Test 5
        instance = new V2D_Triangle(P1P0, P1P0, P0P2, P0P0);
        t = new V2D_Triangle(instance);
        result = instance.equals(t, oom, rm);
        assertTrue(result);
    }

    /**
     * Test of rotate method, of class V2D_Triangle.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        Math_BigDecimal bd = new Math_BigDecimal(100);
        BigRational theta;
        V2D_Triangle instance;
        V2D_Triangle result;
        V2D_Triangle expResult;
        BigRational Pi = Math_BigRational.getPi(bd, oom - 2, rm);
        // Test 1
        instance = new V2D_Triangle(pP1P0, pP0P1, pP1P1, oom, rm);
        V2D_Point pt = pP0P0;
        theta = Pi;
        result = instance.rotate(pt, theta, bd, oom, rm);
        expResult = new V2D_Triangle(pN1P0, pN1N1, pP0N1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 2
        instance = new V2D_Triangle(pP1P0, pP0P1, pP1P1, oom, rm);
        theta = Pi.divide(2);
        result = instance.rotate(pt, theta, bd, oom, rm);
        expResult = new V2D_Triangle(pP0N1, pP1P0, pP1N1, oom, rm);
        //expResult = new V2D_Triangle(pN1P0, pN1P1, pP0P1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 3
        instance = new V2D_Triangle(pP2P0, pP0P2, pP2P2, oom, rm);
        theta = Pi;
        result = instance.rotate(pt,  theta, bd, oom, rm);
        expResult = new V2D_Triangle(pN2P0, pN2N2, pP0N2, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 4
        instance = new V2D_Triangle(pP2P0, pP0P2, pP2P2, oom, rm);
        theta = Pi.divide(2);
        result = instance.rotate(pt, theta, bd, oom, rm);
        expResult = new V2D_Triangle(pP0N2, pP2P0, pP2N2, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 5
        instance = new V2D_Triangle(pP2P0, pP0P2, pP2P2, oom, rm);
        theta = Pi.divide(2).multiply(3);
        result = instance.rotate(pt, theta, bd, oom, rm);
        expResult = new V2D_Triangle(pN2P0, pN2P2, pP0P2, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 5
        instance = new V2D_Triangle(pP1P0, pP0P1, pP1P1, oom, rm);
        instance.translate(P1P0, oom, rm);
        theta = Pi;
        result = instance.rotate(pt, theta, bd, oom, rm);
        expResult = new V2D_Triangle(pN2P0, pN2N1, pN1N1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 6
        instance = new V2D_Triangle(pP1P0, pP0P1, pP1P1, oom, rm);
        theta = Pi;
        result = instance.rotate(pP0P1, theta, bd, oom, rm);
        expResult = new V2D_Triangle(pN1P1, pN1P2, pP0P1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 7
        instance = new V2D_Triangle(pP1P0, pP0P1, pP1P1, oom, rm);
        theta = Pi.divide(2);
        result = instance.rotate(pP0P1, theta, bd, oom, rm);
        expResult = new V2D_Triangle(pP0P0, pN1P0, pP0P1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 8
        instance = new V2D_Triangle(pP2P0, pP0P2, pP2P2, oom, rm);
        theta = Pi;
        result = instance.rotate(pP0P1, theta, bd, oom, rm);
        expResult = new V2D_Triangle(pN2P0, pP0P0, pN2P2, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
    }

    /**
     * Test of getIntersection method, of class V2D_Triangle.
     */
    @Test
    public void testGetIntersection_V2D_Ray() {
        System.out.println("getIntersection");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Triangle t;
        V2D_Ray r;
        V2D_Geometry expResult;
        V2D_Geometry result;
        // Test 1
        t = new V2D_Triangle(pP0P1, pP1P0, pP1P1, oom, rm);
        r = new V2D_Ray(pP0P0, pP1P0, oom, rm);
        result = t.getIntersection(r, oom, rm);
        expResult = pP1P0;
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 2
        t = new V2D_Triangle(pP0N2, pP0P2, pP2P0, oom, rm);
        r = new V2D_Ray(pP1P0, pP2P0, oom, rm);
        result = t.getIntersection(r, oom, rm);
        expResult = new V2D_LineSegment(pP1P0, pP2P0, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
        // Test 3
        r = new V2D_Ray(pN1P0, pP2P0, oom, rm);
        result = t.getIntersection(r, oom, rm);
        expResult = new V2D_LineSegment(pP0P0, pP2P0, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
//        // Test 4
//        r = new V2D_Ray(pN2P0N2, pP0P0P0);
//        result = t.getIntersection(r, oom, rm);
//        expResult = pP0P0P0;
//        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result));
//        // Test 5
//        r = new V2D_Ray(pN2P0N2, pP0N1P0);
//        expResult = pP0N1P0;
//        result = t.getIntersection(r, oom, rm);
//        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result));
//        // Test 6
//        r = new V2D_Ray(pN2P0N2, pN1P0P0);
//        assertNull(t.getIntersection(r, oom, rm));
//        // Test 7
//        t = new V2D_Triangle(pP0N2P0, pP0P2P0, pP2P0P0);
//        r = new V2D_Ray(pN2N2N2, pN1N1N1);
//        expResult = pP0P0P0;
//        result = t.getIntersection(r, oom, rm);
//        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result));
//        // Test 8
//        t = new V2D_Triangle(pP0N2P0, pP0P2P0, pP2P2P1);
//        r = new V2D_Ray(pN2N2N2, pN1N1N1);
//        expResult = pP0P0P0;
//        result = t.getIntersection(r, oom, rm);
//        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result));
//        // Test 9
//        t = new V2D_Triangle(pN1N2P0, pN1P2P0, pP2P2P0);
//        r = new V2D_Ray(pN2N2N2, pN1N1N1);
//        expResult = pP0P0P0;
//        result = t.getIntersection(r, oom, rm);
//        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result));
//        // Test 10
//        t = new V2D_Triangle(pN1N2P0, pN1P2P0, pP2P2N1);
//        r = new V2D_Ray(pN2N2N2, pN1N1N1);
//        double nq = -1d / 4d;
//        expResult = new V2D_Point(nq, nq, nq);
//        result = t.getIntersection(r, oom, rm);
//        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result));
    }

//    /**
//     * For getting a ray from the camera focal point through the centre of the
//     * screen pixel with ID id.
//     *
//     * @param id The ID of the screen pixel.
//     * @return The ray from the camera focal point through the centre of the
//     * screen pixel.
//     */
//    protected V2D_Ray getRay(int row, int col, V2D_Rectangle screen,
//            V2D_Point pt, V2D_Vector vd, V2D_Vector v2d,
//            double epsilon) {
//        V2D_Vector rv = v2d.multiply((double) row);
//        V2D_Vector cv = vd.multiply((double) col);
//        V2D_Point rcpt = new V2D_Point(screen.getP());
//        rcpt.translate(rv.add(cv));
//        return new V2D_Ray(pt, rcpt);
//    }

    /**
     * Test of getIntersection method, of class V2D_Triangle.
     *
     * Look for some examples here:
     * https://math.stackexchange.com/questions/1220102/how-do-i-find-the-intersection-of-two-2d-triangles
     */
    @Test
    public void testGetIntersection_V2D_Triangle() {
        System.out.println("getIntersection");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        Math_BigDecimal bd = new Math_BigDecimal(100);
        V2D_Triangle t = new V2D_Triangle(pP0P0, pP0P1, pP1P0, oom, rm);
        V2D_Triangle instance = new V2D_Triangle(pP0P0, pP0P1, pP1P0, oom, rm);
        V2D_Geometry expResult = new V2D_Triangle(pP0P0, pP0P1, pP1P0, oom, rm);
        V2D_Geometry result = instance.getIntersection(t, oom, rm);
        assertTrue(((V2D_Triangle) expResult).equals((V2D_Triangle) result, oom, rm));
        // Test 2
        t = new V2D_Triangle(pN1N1, pP0P2, pP2N1, oom, rm);
        instance = new V2D_Triangle(pP0P0, pP0P1, pP1P0, oom, rm);
        expResult = new V2D_Triangle(pP0P0, pP0P1, pP1P0, oom, rm);
        result = instance.getIntersection(t, oom, rm);
        assertTrue(((V2D_Triangle) expResult).equals((V2D_Triangle) result, oom, rm));
        // Test 3
        t = new V2D_Triangle(pP0P0, pP0P1, pP1P0, oom, rm);
        instance = new V2D_Triangle(pN1N1, pP0P2, pP2N1, oom, rm);
        expResult = new V2D_Triangle(pP0P0, pP0P1, pP1P0, oom, rm);
        result = instance.getIntersection(t, oom, rm);
        assertTrue(((V2D_Triangle) expResult).equals((V2D_Triangle) result, oom, rm));
        // Test 4
        t = new V2D_Triangle(pP0P0, pP2P0, pP2P2, oom, rm);
        instance = new V2D_Triangle(pP1P0, pP2P0, pP2P2, oom, rm);
        expResult = new V2D_Triangle(pP1P0, pP2P0, pP2P2, oom, rm);
        result = instance.getIntersection(t, oom, rm);
        assertTrue(((V2D_Triangle) expResult).equals((V2D_Triangle) result, oom, rm));
        // Test 5
        t = new V2D_Triangle(pP0P0, pP2P0, pP2P2, oom, rm);
        instance = new V2D_Triangle(pP1P0, pP2P0, new V2D_Point(3d, 2d), oom, rm);
        expResult = new V2D_Triangle(pP1P0, pP2P0, pP2P1, oom, rm);
        result = instance.getIntersection(t, oom, rm);
        assertTrue(((V2D_Triangle) expResult).equals((V2D_Triangle) result, oom, rm));
        // Test 6 - Test 7 Are more complex intersections
        V2D_Point origin = new V2D_Point(0d, 0d);
        ArrayList<V2D_Triangle> expected;
        V2D_Triangle t0;
        V2D_Triangle t1;
        BigRational theta;
        // Test 6
        t0 = new V2D_Triangle(
                new V2D_Point(-50d, -50d),
                new V2D_Point(0d, 50d),
                new V2D_Point(50d, -50d), oom, rm);
        theta = Math_BigRational.getPi(bd, oom - 2, rm);
        t1 = t0.rotate(origin, theta, bd, oom, rm);
        expected = new ArrayList<>();
        /** 
         * Using an additional centroid point and connecting each edge with this 
         * would result in 4 triangles.
         */
//        expected.add(new V2D_Triangle(
//                new V2D_Point(0d, 0d),
//                new V2D_Point(-25d, 0d),
//                new V2D_Point(0d, 50d)));
//        expected.add(new V2D_Triangle(
//                new V2D_Point(0d, 0d),
//                new V2D_Point(0d, 50d),
//                new V2D_Point(25d, 0d)));
//        expected.add(new V2D_Triangle(
//                new V2D_Point(0d, 0d),
//                new V2D_Point(25d, 0d),
//                new V2D_Point(0d, -50d)));
//        expected.add(new V2D_Triangle(
//                new V2D_Point(0d, 0d),
//                new V2D_Point(0d, -50d),
//                new V2D_Point(-25d, 0d)));
        expected.add(new V2D_Triangle(
                new V2D_Point(0d, -50d),
                new V2D_Point(25d, 0d),
                new V2D_Point(0d, 50d), oom, rm));
        expected.add(new V2D_Triangle(
                new V2D_Point(0d, 50d),
                new V2D_Point(-25d, 0d),
                new V2D_Point(0d, -50d), oom, rm));
        // Calculate the intersection
        // We are expecting a convex hull with 4 points that can be tested to 
        // see if they are made up of the two triangles as expected.
        V2D_FiniteGeometry gi = t0.getIntersection(t1, oom, rm);
        ArrayList<V2D_Triangle> git = ((V2D_ConvexHull) gi).getTriangles(oom, rm);
        for (int i = 0; i < git.size(); i ++) {
            assertTrue(expected.get(i).equals(git.get(i), oom, rm));
        }
        // Test 7
        t0 = new V2D_Triangle(new V2D_Point(-30d, -30d),
        new V2D_Point(0d, 60d),
        new V2D_Point(30d, -30d), oom, rm);
        theta = Math_BigRational.getPi(bd, oom - 2, rm);
        t1 = t0.rotate(origin, theta, bd, oom, rm);
        expected = new ArrayList<>();
        expected.add(new V2D_Triangle(
                new V2D_Point(-10d, -30d),
                new V2D_Point(10d, -30d),
                new V2D_Point(20d, 0d), oom, rm));
        expected.add(new V2D_Triangle(
                new V2D_Point(-10d, -30d),
                new V2D_Point(20d, 0d),
                new V2D_Point(10d, 30d), oom, rm));
        expected.add(new V2D_Triangle(
                new V2D_Point(-10d, -30d),
                new V2D_Point(10d, 30d),
                new V2D_Point(-10d, 30d), oom, rm));
        expected.add(new V2D_Triangle(
                new V2D_Point(-10d, -30d),
                new V2D_Point(-10d, 30d),
                new V2D_Point(-20d, 0), oom, rm));
//        expected.add(new V2D_Triangle(
//                new V2D_Point(10d, -30d),
//                new V2D_Point(20d, 0d),
//                new V2D_Point(10d, 30d), oom, rm));
//        expected.add(new V2D_Triangle(
//                new V2D_Point(10d, -30d),
//                new V2D_Point(10d, 30d),
//                new V2D_Point(-10d, 30d), oom, rm));
//        expected.add(new V2D_Triangle(
//                new V2D_Point(10d, -30d),
//                new V2D_Point(-10d, 30d),
//                new V2D_Point(-20d, 0d), oom, rm));
//        expected.add(new V2D_Triangle(
//                new V2D_Point(-10d, -30d),
//                new V2D_Point(-20d, 0d),
//                new V2D_Point(10d, -30d), oom, rm));
        // Calculate the intersection
        // Expecting a convex hull with 6 points that can be tested to 
        // see if they are made up of the four triangles as expected.
        gi = t0.getIntersection(t1, oom, rm);
        git = ((V2D_ConvexHull) gi).getTriangles(oom, rm);
        for (int i = 0; i < git.size(); i ++) {
            assertTrue(expected.get(i).equals(git.get(i), oom, rm));
        }
    }

    /**
     * Test of translate method, of class V2D_Triangle.
     */
    @Test
    public void testTranslate() {
        System.out.println("translate");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Triangle instance = new V2D_Triangle(P0P0, P1P0, P0P1, P1P1);
        instance.translate(V2D_Vector.I, oom, rm);
        V2D_Triangle expResult = new V2D_Triangle(P0P0, P2P0, P1P1, P2P1);
        assertTrue(expResult.equals(instance, oom, rm));
        // Test 2
        instance = new V2D_Triangle(P0P0, P1P0, P0P1, P1P1);
        instance.translate(V2D_Vector.IJ, oom, rm);
        expResult = new V2D_Triangle(P1P1, P1P0, P0P1, P1P1);
        assertTrue(expResult.equals(instance, oom, rm));
    }

    /**
     * Test of getGeometry method, of class V2D_Triangle.
     */
    @Test
    public void testGetGeometry_3args_1() {
        System.out.println("getGeometry");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Point p;
        V2D_Point q;
        V2D_Point r;
        V2D_Geometry expResult;
        V2D_Geometry result;
        // Test 1
        p = new V2D_Point(pP0P0);
        q = new V2D_Point(pP0P0);
        r = new V2D_Point(pP0P0);
        expResult = new V2D_Point(pP0P0);
        result = V2D_Triangle.getGeometry(p, q, r, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 2
        p = new V2D_Point(pP1P0);
        q = new V2D_Point(pP0P0);
        r = new V2D_Point(pP0P0);
        expResult = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        result = V2D_Triangle.getGeometry(p, q, r, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
        // Test 2
        p = new V2D_Point(pP1P0);
        q = new V2D_Point(pP0P1);
        r = new V2D_Point(pP0P0);
        expResult = new V2D_Triangle(pP0P0, pP1P0, pP0P1, oom, rm);
        result = V2D_Triangle.getGeometry(p, q, r, oom, rm);
        assertTrue(((V2D_Triangle) expResult).equals((V2D_Triangle) result, oom, rm));
    }

    /**
     * Test of getOpposite method, of class V2D_Triangle.
     */
    @Test
    public void testGetOpposite() {
        System.out.println("getOpposite");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_LineSegment l = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        V2D_Triangle instance = new V2D_Triangle(pP0P0, pP1P0, pP0P1, oom, rm);
        V2D_Point expResult = pP0P1;
        V2D_Point result = instance.getOpposite(l, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
    }

    /**
     * Test of getDistance method, of class V2D_Triangle covered by
     * {@link #testGetDistanceSquared_V2D_Point()}.
     */
    @Test
    public void testGetDistance_V2D_Point() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V2D_Triangle.
     */
    @Test
    public void testGetDistanceSquared_V2D_Point() {
        System.out.println("getDistanceSquared");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Point p;
        V2D_Triangle t;
        BigRational expResult;
        // Test 1
        p = pP0P0;
        t = new V2D_Triangle(pP0P0, pP1P0, pP0P1, oom, rm);
        expResult = BigRational.ZERO;
        BigRational result = t.getDistanceSquared(p, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 2
        p = pP1P1;
        t = new V2D_Triangle(pP0P0, pP1P0, pP0P1, oom, rm);
        expResult = BigRational.valueOf(1, 2);
        result = t.getDistanceSquared(p, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 3
        p = pN1N1;
        t = new V2D_Triangle(pN2N2, pP2N2, pN2P2, oom, rm);
        expResult = BigRational.ZERO;
        result = t.getDistanceSquared(p, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 3
        p = new V2D_Point(-1, -10);
        expResult = BigRational.valueOf(64);
        result = t.getDistanceSquared(p, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
    }

    /**
     * Test of getDistance method, of class V2D_Triangle covered by
     * {@link #testGetDistanceSquared_V2D_Line()}.
     */
    @Test
    public void testGetDistance_V2D_Line() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V2D_Triangle.
     */
    @Test
    public void testGetDistanceSquared_V2D_Line() {
        System.out.println("getDistanceSquared");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Line l;
        BigRational expResult;
        BigRational result;
        V2D_Triangle instance;
        // Test 1
        l = new V2D_Line(pP0P0, pP1P0, oom, rm);
        instance = new V2D_Triangle(pP0P0, pP1P0, pP0P1, oom, rm);
        expResult = BigRational.ZERO;
        result = instance.getDistanceSquared(l, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 2
        l = new V2D_Line(pP0P1, pP1P1, oom, rm);
        instance = new V2D_Triangle(pN2N2, pP2N2, pN2P2, oom, rm);
        expResult = BigRational.ZERO;
        result = instance.getDistanceSquared(l, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
    }

    /**
     * Test of getDistance method, of class V2D_Triangle covered by
     * {@link #testGetDistanceSquared_V2D_LineSegment()}.
     */
    @Test
    public void testGetDistance_V2D_LineSegment() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V2D_Triangle.
     */
    @Test
    public void testGetDistanceSquared_V2D_LineSegment() {
        System.out.println("getDistanceSquared");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_LineSegment l;
        V2D_Triangle instance;
        BigRational expResult;
        BigRational result;
        // Test 1
        l = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        instance = new V2D_Triangle(pP0P0, pP1P0, pP0P1, oom, rm);
        expResult = BigRational.ZERO;
        result = instance.getDistanceSquared(l, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 2
        l = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        instance = new V2D_Triangle(pN2N2, pP2N2, pN2P2, oom, rm);
        expResult = BigRational.ZERO;
        result = instance.getDistanceSquared(l, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 3
        l = new V2D_LineSegment(pP0P1, pP1P1, oom, rm);
        expResult = BigRational.valueOf(1, 2);
        result = instance.getDistanceSquared(l, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
    }

    /**
     * Test of getDistance method, of class V2D_Triangle covered by
     * {@link #testGetDistanceSquared_V2D_Plane()}.
     */
    @Test
    public void testGetDistance_V2D_Plane() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistance method, of class V2D_Triangle covered by
     * {@link #testGetDistanceSquared_V2D_Triangle()}.
     */
    @Test
    public void testGetDistance_V2D_Triangle() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V2D_Triangle.
     */
    @Test
    public void testGetDistanceSquared_V2D_Triangle() {
        System.out.println("getDistanceSquared");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Triangle t;
        V2D_Triangle instance;
        BigRational expResult;
        BigRational result;
        // Test 1
        t = new V2D_Triangle(pP0P0, pP1P0, pP0P1, oom, rm);
        instance = new V2D_Triangle(pP0P0, pP1P0, pP0P1, oom, rm);
        expResult = BigRational.ZERO;
        result = instance.getDistanceSquared(t, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 2
        t = new V2D_Triangle(pP0P0, pP1P0, pP0P1, oom, rm);
        instance = new V2D_Triangle(pP1P0, pP1P1, pP0P1, oom, rm);
        expResult = BigRational.ZERO;
        result = instance.getDistanceSquared(t, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 3
        t = new V2D_Triangle(pP0P0, pP1P0, pP0P1, oom, rm);
        instance = new V2D_Triangle(pP1P1, pP1P2, pP0P2, oom, rm);
        expResult = BigRational.valueOf(1, 2);
        result = instance.getDistanceSquared(t, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
    }
    
    /**
     * Test of getCircumcentre method, of class V2D_Triangle.
     */
    @Test
    public void testGetCircumcentre() {
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Point a = new V2D_Point(3, 2);
        V2D_Point b = new V2D_Point(1, 4);
        V2D_Point c = new V2D_Point(5, 4);
        V2D_Triangle t = new V2D_Triangle(a, b, c, oom, rm);
        V2D_Point expResult = new V2D_Point(3, 4);
        V2D_Point result = t.getCircumcenter(oom, rm);
        assertTrue(result.equals(expResult, oom, rm));
        // Test 2
         a = new V2D_Point(-2, -3);
         b = new V2D_Point(-1, 0);
         c = new V2D_Point(7, -6);
         t = new V2D_Triangle(a, b, c, oom, rm);
         expResult = new V2D_Point(3, -3);
         result = t.getCircumcenter(oom, rm);
        assertTrue(result.equals(expResult, oom, rm));
    }
    
    /**
     * Test of getAngleP method, of class V2D_Triangle.
     */
    @Test
    public void testGetAngleP() {
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Triangle t = new V2D_Triangle(pP0P0, pP0P1, pP1P1, oom, rm);
        Math_BigDecimal bd = new Math_BigDecimal();
        BigRational expResult = Math_BigRational.getPi(bd, oom, rm).divide(4);
        BigRational result = t.getAngleP(oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 2
        t = new V2D_Triangle(pP0P0, pP1P1, pP1P0, oom, rm);
        expResult = Math_BigRational.getPi(bd, oom, rm).divide(4);
        result = t.getAngleP(oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 3
        t = new V2D_Triangle(pP0P0, pP0P1, pP1P0, oom, rm);
        expResult = Math_BigRational.getPi(bd, oom, rm).divide(2);
        result = t.getAngleP(oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 4
        t = new V2D_Triangle(pP1P1, pP1P2, pP2P1, oom, rm);
        expResult = Math_BigRational.getPi(bd, oom, rm).divide(2);
        result = t.getAngleP(oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 5
        t = new V2D_Triangle(pP1P1, pP2P2, pP2P1, oom, rm);
        expResult = Math_BigRational.getPi(bd, oom, rm).divide(4);
        result = t.getAngleP(oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
    }

    /**
     * Test of getAngleQ method, of class V2D_Triangle.
     */
    @Test
    public void testGetAngleQ() {
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        Math_BigDecimal bd = new Math_BigDecimal();
        V2D_Triangle t = new V2D_Triangle(pP0P0, pP0P1, pP1P1, oom, rm);
        BigRational expResult = Math_BigRational.getPi(bd, oom, rm).divide(2);
        BigRational result = t.getAngleQ(oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 2
        t = new V2D_Triangle(pP0P0, pP1P1, pP1P0, oom, rm);
        expResult = Math_BigRational.getPi(bd, oom, rm).divide(4);
        result = t.getAngleQ(oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 3
        t = new V2D_Triangle(pP0P0, pP0P1, pP1P0, oom, rm);
        expResult = Math_BigRational.getPi(bd, oom, rm).divide(4);
        result = t.getAngleQ(oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 4
        t = new V2D_Triangle(pP1P1, pP1P2, pP2P1, oom, rm);
        expResult = Math_BigRational.getPi(bd, oom, rm).divide(4);
        result = t.getAngleQ(oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 5
        t = new V2D_Triangle(pP1P1, pP2P2, pP2P1, oom, rm);
        expResult = Math_BigRational.getPi(bd, oom, rm).divide(4);
        result = t.getAngleQ(oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
    }
    
    /**
     * Test of getAngleR method, of class V2D_Triangle.
     */
    @Test
    public void testGetAngleR() {
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        Math_BigDecimal bd = new Math_BigDecimal();
        V2D_Triangle t = new V2D_Triangle(pP0P0, pP0P1, pP1P1, oom, rm);
       BigRational expResult = Math_BigRational.getPi(bd, oom, rm).divide(4);
       BigRational result = t.getAngleR(oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 2
        t = new V2D_Triangle(pP0P0, pP1P1, pP1P0, oom, rm);
        expResult = Math_BigRational.getPi(bd, oom, rm).divide(2);
        result = t.getAngleR(oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 3
        t = new V2D_Triangle(pP0P0, pP0P1, pP1P0, oom, rm);
        expResult = Math_BigRational.getPi(bd, oom, rm).divide(4);
        result = t.getAngleR(oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 4
        t = new V2D_Triangle(pP1P1, pP1P2, pP2P1, oom, rm);
        expResult = Math_BigRational.getPi(bd, oom, rm).divide(4);
        result = t.getAngleR(oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 5
        t = new V2D_Triangle(pP1P1, pP2P2, pP2P1, oom, rm);
        expResult = Math_BigRational.getPi(bd, oom, rm).divide(2);
        result = t.getAngleR(oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
    }
}
