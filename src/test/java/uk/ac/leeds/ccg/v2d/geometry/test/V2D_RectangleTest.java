/*
 * Copyright 2025 Andy Turner, University of Leeds.
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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v2d.geometry.V2D_AABB;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Geometry;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Line;
import uk.ac.leeds.ccg.v2d.geometry.V2D_LineSegment;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Point;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Rectangle;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Triangle;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Vector;

/**
 * Test of V2D_Rectangle class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_RectangleTest extends V2D_Test {

    public V2D_RectangleTest() {
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
     * Test of getAABB method, of class V2D_Rectangle.
     */
    @Test
    public void testGetAABB() {
        System.out.println("getAABB");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        /*
         * q ----------- r
         * |             |
         * |             |
         * |             |
         * pv ----------- s
         */
        V2D_Rectangle instance;
        V2D_AABB expResult;
        V2D_AABB result;
        instance = new V2D_Rectangle(pN1P1, pP1P1, pP1N1, pN1N1, oom, rm);
        expResult = new V2D_AABB(oom, pN1N1, pP1P1);
        result = instance.getAABB(oom, rm);
        assertTrue(expResult.equals(result, oom));
        // Test 2
        instance = new V2D_Rectangle(pN1P1, pP1P1, pP1N1, pN1N1, oom, rm);
        expResult = new V2D_AABB(oom, pN1N1, pP1P1);
        result = instance.getAABB(oom, rm);
        assertTrue(expResult.equals(result, oom));
        // Test 3
        instance = new V2D_Rectangle(pN1P1, pP1P1, pP1N1, pN1N1, oom, rm);
        expResult = new V2D_AABB(oom, pN1N1, pP1P1);
        result = instance.getAABB(oom, rm);
        assertTrue(expResult.equals(result, oom));
        // Test 4
        instance = new V2D_Rectangle(pN1N1, pP1N1, pP1P1, pN1P1, oom, rm);
        expResult = new V2D_AABB(oom, pN1N1, pP1P1);
        result = instance.getAABB(oom, rm);
        assertTrue(expResult.equals(result, oom));
    }

    /**
     * Test of intersects method, of class V2D_Rectangle.
     */
    @Test
    public void testIsIntersectedBy_V2D_Point_int() {
        System.out.println("intersects");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Point pt = pP0P0;
        V2D_Rectangle instance = new V2D_Rectangle(pN1P1, pP1P1, pP1N1, pN1N1, oom, rm);
        assertTrue(instance.intersects(pt, oom, rm));
        // Test 2
        instance = new V2D_Rectangle(pN1P0, pP0P1, pP1P0, pP0N1, oom, rm);
        assertTrue(instance.intersects(pt, oom, rm));
        // Test 3
        double half = 1d / 2d;
        pt = new V2D_Point(env, half, half);
        assertTrue(instance.intersects(pt, oom, rm));
//        // Test 4
//        double epsilon = 0.00000001d;
//        double halfpe = half + epsilon;
//        double halfne = half - epsilon;
//        pt = new V2D_Point(halfpe, half);
//        assertTrue(instance.intersects(pt, oom, rm));
//        // Test 5
//        pt = new V2D_Point(-halfpe, half);
//        assertFalse(instance.intersects(pt, oom, rm));
//        // Test 6
//        pt = new V2D_Point(half, halfpe);
//        assertFalse(instance.intersects(pt, oom, rm));
//        // Test 7
//        pt = new V2D_Point(half, -halfpe);
//        assertFalse(instance.intersects(pt, oom, rm));
//        // Test 8
//        pt = new V2D_Point(halfne, half);
//        assertTrue(instance.intersects(pt, oom, rm));
//        // Test 9
//        pt = new V2D_Point(-halfne, half);
//        assertTrue(instance.intersects(pt, oom, rm));
//        // Test 10
//        pt = new V2D_Point(half, halfne);
//        assertTrue(instance.intersects(pt, oom, rm));
//        // Test 11
//        pt = new V2D_Point(half, -halfne);
//        assertTrue(instance.intersects(pt, oom, rm));
    }

    /**
     * Test of intersects method, of class V2D_RectangleDouble.
     */
    @Test
    public void testIsIntersectedBy_V2D_LineSegmentDouble_double() {
        System.out.println("intersects");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Rectangle r = new V2D_Rectangle(
                new V2D_Point(env, -30, -22),
                new V2D_Point(env, -30, -21),
                new V2D_Point(env, -29, -21),
                new V2D_Point(env, -29, -22), oom, rm);
        V2D_LineSegment l = new V2D_LineSegment(
                new V2D_Point(env, -30, -30),
                new V2D_Point(env, -20, 0), oom, rm);
        assertTrue(!r.intersects(l, oom, rm));
    }

    /**
     * Test of getIntersect method, of class V2D_Rectangle.
     */
    @Test
    public void testGetIntersection() {
        System.out.println("getIntersect");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Line l = new V2D_Line(pP0N1, pP0P1, oom, rm);
        V2D_Rectangle instance;
        V2D_Geometry expResult;
        V2D_Geometry result;
        instance = new V2D_Rectangle(pN1P1, pP1P1, pP1N1, pN1N1, oom, rm);
        expResult = new V2D_LineSegment(pP0N1, pP0P1, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
    }

    /**
     * Test of toString method, of class V2D_Rectangle.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Rectangle instance = new V2D_Rectangle(pP0P0, pP0P1, pP1P1, pP1P0, oom, rm);
        String expResult = """
                           V2D_Rectangle(
                           offset=V2D_Vector(dx=0, dy=0),
                           pqr=V2D_Triangle(
                            offset=(V2D_Vector(dx=0, dy=0)),
                            p=(V2D_Vector(dx=0, dy=0)),
                            q=(V2D_Vector(dx=0, dy=1)),
                            r=(V2D_Vector(dx=1, dy=1))),
                           rsp=V2D_Triangle(
                            offset=(V2D_Vector(dx=0, dy=0)),
                            p=(V2D_Vector(dx=1, dy=1)),
                            q=(V2D_Vector(dx=1, dy=0)),
                            r=(V2D_Vector(dx=0, dy=0))))""";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of getIntersect method, of class V2D_Rectangle.
     */
    @Test
    public void testGetIntersection_V2D_LineSegment_double() {
        System.out.println("getIntersect");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_LineSegment l = new V2D_LineSegment(pN1N1, pP2P2, oom, rm);
        V2D_Rectangle instance = new V2D_Rectangle(pP0P0, pP0P1, pP1P1, pP1P0, oom, rm);
        V2D_Geometry result = instance.getIntersect(l, oom, rm);
        V2D_Geometry expResult = new V2D_LineSegment(pP0P0, pP1P1, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
        // Test 2
        l = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        instance = new V2D_Rectangle(pP0P0, pP1P0, pP1P1, pP0P1, oom, rm);
        expResult = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
        // Test 3
        l = new V2D_LineSegment(pP1N1, pP1P2, oom, rm);
        instance = new V2D_Rectangle(pP0P0, pP0P1, pP2P1, pP2P0, oom, rm);
        expResult = new V2D_LineSegment(pP1P1, pP1P0, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
    }

//    /**
//     * Test of getPerimeter method, of class V2D_Rectangle.
//     */
//    @Test
//    public void testGetPerimeter() {
//        System.out.println("getPerimeter");
//        int oom = -6;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V2D_Rectangle instance = new V2D_Rectangle(pP0P0, pP0P1, pP1P1, pP1P0, oom, rm);
//        double expResult = 4d;
//        BigRational result = instance.getPerimeter(oom, rm);
//        assertTrue(Math_Double.equals(expResult, result, oom, rm));
//    }
//    /**
//     * Test of getArea method, of class V2D_Rectangle.
//     */
//    @Test
//    public void testGetArea() {
//        System.out.println("getArea");
//        int oom = -6;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V2D_Rectangle instance = new V2D_Rectangle(pP0P0, pP0P1, pP1P1, pP1P0, oom, rm);
//        BigRational expResult = BigRational.valueOf(1);
//        BigRational result = instance.getArea(oom, rm);
//        assertTrue(Math_BigRational.equals(expResult, result, oom, rm));
//    }
    /**
     * Test of getDistance method, of class V2D_Rectangle.
     */
    @Test
    public void testGetDistance() {
        System.out.println("getDistance");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Point p = pN1N1;
        V2D_Rectangle instance = new V2D_Rectangle(pP0P0, pP0P1, pP1P1, pP1P0, oom, rm);
        BigRational expResult = new Math_BigRationalSqrt(2, oom, rm).getSqrt(oom, rm);
        BigRational result = instance.getDistance(p, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
    }

    /**
     * Test of getS method, of class V2D_Rectangle.
     */
    @Test
    public void testGetS() {
        System.out.println("getS");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Rectangle instance = new V2D_Rectangle(pP0P0, pP0P1, pP1P1, pP1P0, oom, rm);
        V2D_Point expResult = pP1P0;
        V2D_Point result = instance.getS(oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
    }

    @Test
    public void testGetIntersection_V2D_Line_double() {
        System.out.println("getIntersect");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Line l = new V2D_Line(pP0P0, pP1P0, oom, rm);
        V2D_Rectangle instance = new V2D_Rectangle(pP0P0, pP0P1, pP1P1, pP1P0, oom, rm);
        V2D_Geometry expResult = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        V2D_Geometry result = instance.getIntersect(l, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
    }

    /**
     * Test of getDistance method, of class V2D_Rectangle.
     */
    @Test
    public void testGetDistance_V2D_Point_int() {
        System.out.println("getDistance");
        V2D_Point p = pP0P0;
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Rectangle instance = new V2D_Rectangle(pP0P0, pP0P1, pP2P1, pP2P0, oom, rm);
        BigRational expResult = BigRational.ZERO;
        BigRational result = instance.getDistance(p, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 2
        p = pN1P0;
        expResult = BigRational.ONE;
        result = instance.getDistance(p, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
    }

    /**
     * Test of getDistanceSquared method, of class V2D_Rectangle.
     */
    @Test
    public void testGetDistanceSquared_V2D_Point_int() {
        System.out.println("getDistanceSquared");
        V2D_Point p = pP0P0;
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Rectangle instance = new V2D_Rectangle(pP0P0, pP0P1, pP1P1, pP1P0, oom, rm);
        BigRational expResult = BigRational.ZERO;
        BigRational result = instance.getDistanceSquared(p, oom, rm);
        assertEquals(expResult, result);
        // Test 2
        p = pN1N1;
        expResult = BigRational.TWO;
        result = instance.getDistanceSquared(p, oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of setOffset method, of class V2D_Rectangle.
     */
    @Test
    public void testSetOffset() {
        System.out.println("setOffset");
        // No test.
//        V2D_Vector offset = P1P1P1;
//        V2D_Rectangle instance = new V2D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
//        instance.setOffset(offset);
//        V2D_Rectangle instance2 = new V2D_Rectangle(pP1P1P1, pP1P2P1, pP2P2P1, pP2P1P1);
//        assertEquals(instance, instance2);
    }

    /**
     * Test of translate method, of class V2D_Rectangle.
     */
    @Test
    public void testTranslate() {
        System.out.println("translate");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Vector v = P1P1;
        V2D_Rectangle instance = new V2D_Rectangle(pP0P0, pP0P1, pP1P1, pP1P0, oom, rm);
        instance.translate(v, oom, rm);
        V2D_Rectangle instance2 = new V2D_Rectangle(pP1P1, pP1P2, pP2P2, pP2P1, oom, rm);
        assertTrue(instance.equals(instance2, oom, rm));
    }

    /**
     * Test of rotate method, of class V2D_Rectangle.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        Math_BigDecimal bd = new Math_BigDecimal(100);
        BigRational theta = Math_BigRational.getPi(bd, oom - 2, rm);
        V2D_Rectangle instance = new V2D_Rectangle(pP0P0, pP0P1, pP1P1, pP1P0, oom, rm);
        V2D_Rectangle result = instance.rotate(pP0P0, theta, bd, oom, rm);
        V2D_Rectangle expResult = new V2D_Rectangle(pP0P0, pP0N1, pN1N1, pN1P0, oom, rm);
        assertTrue(result.equals(expResult, oom, rm));
    }

    /**
     * Test of getIntersect method, of class V2D_Rectangle.
     */
    @Test
    public void testGetIntersection_V2D_Triangle_double() {
        System.out.println("getIntersect");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Triangle t = new V2D_Triangle(pP0P0, pP0P1, pP1P1, oom, rm);
        V2D_Rectangle instance = new V2D_Rectangle(pP0P0, pP0P1, pP1P1, pP1P0, oom, rm);
        V2D_Geometry expResult = new V2D_Triangle(pP0P0, pP0P1, pP1P1, oom, rm);
        V2D_Geometry result = instance.getIntersect(t, oom, rm);
        assertTrue(((V2D_Triangle) expResult).equals((V2D_Triangle) result, oom, rm));
        // Test 2
        t = new V2D_Triangle(pP2P2, new V2D_Point(env, 4d, 4d),
                new V2D_Point(env, 2d, 4d), oom, rm);
        instance = new V2D_Rectangle(
                new V2D_Point(env, 0d, 0d),
                new V2D_Point(env, 0d, 6d),
                new V2D_Point(env, 6d, 6d),
                new V2D_Point(env, 6d, 0d), oom, rm);
        expResult = new V2D_Triangle(pP2P2, new V2D_Point(env, 4d, 4d),
                new V2D_Point(env, 2d, 4d), oom, rm);
        result = instance.getIntersect(t, oom, rm);
        assertTrue(((V2D_Triangle) expResult).equals(
                (V2D_Triangle) result, oom, rm));
        // Test 3
        t = new V2D_Triangle(
                new V2D_Point(env, -1d, 4d),
                new V2D_Point(env, -1d, 6d),
                new V2D_Point(env, 1d, 4d), oom, rm);
        instance = new V2D_Rectangle(
                new V2D_Point(env, 0d, 0d),
                new V2D_Point(env, 0d, 6d),
                new V2D_Point(env, 6d, 6d),
                new V2D_Point(env, 6d, 0d), oom, rm);
        expResult = new V2D_Triangle(
                new V2D_Point(env, 0d, 4d),
                new V2D_Point(env, 0d, 5d),
                new V2D_Point(env, 1d, 4d), oom, rm);
        result = instance.getIntersect(t, oom, rm);
        assertTrue(((V2D_Triangle) expResult).equals(
                (V2D_Triangle) result, oom, rm));
//        // Test 3 This returns a coonvexhull, but this should be simplified to a triangle
//         t = new V2D_Triangle(pN1P0, pN1P2, pP1P0);
//         instance = new V2D_Rectangle(
//                 new V2D_Point(0d,0d),
//                 new V2D_Point(0d,6d),
//                 new V2D_Point(6d,6d),
//                 new V2D_Point(6d,0d));
//         expResult = new V2D_Triangle(pP0P0, pP0P1, pP1P0);
//         result = instance.getIntersect(t, oom, rm);
//        assertTrue(((V2D_Triangle) expResult).equals(
//                (V2D_Triangle) result, oom, rm));
//        // Test 4
//         t = new V2D_Triangle(new V2D_Point(4d,4d), new V2D_Point(5d,5d), 
//                 new V2D_Point(2d, 4d));
//         instance = new V2D_Rectangle(
//                 new V2D_Point(0d,0d),
//                 new V2D_Point(0d,6d),
//                 new V2D_Point(6d,6d),
//                 new V2D_Point(6d,0d));
//         expResult = new V2D_LineSegment(new V2D_Point(4d,4d), new V2D_Point(5d,5d));
//         result = instance.getIntersect(t, oom, rm);
//        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
//                (V2D_LineSegment) result), oom, rm);
//        // Test 5
//         t = new V2D_Triangle(new V2D_Point(7d,7d), new V2D_Point(8d,8d), 
//                 new V2D_Point(2d, 4d));
//         instance = new V2D_Rectangle(
//                 new V2D_Point(0d,0d),
//                 new V2D_Point(0d,6d),
//                 new V2D_Point(6d,6d),
//                 new V2D_Point(6d,0d));
//        result =  instance.getIntersect(t, oom, rm);
//        assertNull(result);
//        // Test 6
//         t = new V2D_Triangle(new V2D_Point(0d,1d,-1), new V2D_Point(0d,5d), 
//                 new V2D_Point(0d, 2d, 1d));
//         instance = new V2D_Rectangle(
//                 new V2D_Point(0d,0d,0d),
//                 new V2D_Point(0d,6d,0d),
//                 new V2D_Point(6d,6d,0d),
//                 new V2D_Point(6d,0d,0d));
//         expResult = new V2D_LineSegment(new V2D_Point(0d,1.5d,0d), new V2D_Point(0d,3.5d,0d));
//         result = instance.getIntersect(t, oom, rm);
//        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
//               (V2D_LineSegment) result, oom, rm));
//        // Test 7
//         t = new V2D_Triangle(new V2D_Point(0d,2d,-2d), new V2D_Point(0d,5d,-2d), 
//                 new V2D_Point(0d, 2d, 2d));
//         instance = new V2D_Rectangle(
//                 new V2D_Point(0d,0d,0d),
//                 new V2D_Point(0d,6d,0d),
//                 new V2D_Point(6d,6d,0d),
//                 new V2D_Point(6d,0d,0d));
//         expResult = new V2D_LineSegment(new V2D_Point(0d,1.5d,0d), new V2D_Point(0d,3.5d,0d));
//         result = instance.getIntersect(t, oom, rm);
//        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
//              (V2D_LineSegment) result, oom, rm));
//        // Test 8
//         t = new V2D_Triangle(new V2D_Point(0d,2d,-2000d), new V2D_Point(0d,5d,-2000d), 
//                 new V2D_Point(0d, 2d, 2000d));
//         instance = new V2D_Rectangle(
//                 new V2D_Point(0d,0d,0d),
//                 new V2D_Point(0d,6d,0d),
//                 new V2D_Point(6d,6d,0d),
//                 new V2D_Point(6d,0d,0d));
//         expResult = new V2D_LineSegment(new V2D_Point(0d,1.5d,0d), new V2D_Point(0d,3.5d,0d));
//         result = instance.getIntersect(t, oom, rm);
//        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
//                 (V2D_LineSegment) result, oom, rm));
//        // Test 9
//         t = new V2D_Triangle(new V2D_Point(0d,2d,-2000000d), new V2D_Point(0d,5d,-2000000d), 
//                 new V2D_Point(0d, 2d, 2000000d));
//         instance = new V2D_Rectangle(
//                 new V2D_Point(0d,0d,0d),
//                 new V2D_Point(0d,6d,0d),
//                 new V2D_Point(6d,6d,0d),
//                 new V2D_Point(6d,0d,0d));
//         expResult = new V2D_LineSegment(new V2D_Point(0d,1.5d,0d), new V2D_Point(0d,3.5d,0d));
//         result = instance.getIntersect(t, oom, rm);
//        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
//                (V2D_LineSegment) result, oom, rm));
//        // Test 10
//         t = new V2D_Triangle(new V2D_Point(0d,1d,-2d), new V2D_Point(0d,5d,-2d), 
//                 new V2D_Point(0d, 3d, 2d));
//         instance = new V2D_Rectangle(
//                 new V2D_Point(0d,0d,0d),
//                 new V2D_Point(0d,6d,0d),
//                 new V2D_Point(6d,6d,0d),
//                 new V2D_Point(6d,0d,0d));
//         expResult = new V2D_LineSegment(new V2D_Point(0d,1.5d,0d), new V2D_Point(0d,3.5d,0d));
//         result = instance.getIntersect(t, oom, rm);
//        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
//                (V2D_LineSegment) result, oom, rm));
    }

    /**
     * Test of getDistance method, of class V2D_Rectangle.
     */
    @Test
    public void testGetDistance_V2D_Line_int() {
        System.out.println("getDistance");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Line l = new V2D_Line(pP0N1, pP1N1, oom, rm);
        V2D_Rectangle instance = new V2D_Rectangle(pP0P0, pP0P1, pP1P1, pP1P0, oom, rm);
        BigRational expResult = BigRational.ONE;
        BigRational result = instance.getDistance(l, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
    }

    /**
     * Test of getDistanceSquared method, of class V2D_Rectangle.
     */
    @Test
    public void testGetDistanceSquared_V2D_Line_int() {
        System.out.println("getDistanceSquared");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Line l = new V2D_Line(pP0N1, pP1N1, oom, rm);
        V2D_Rectangle instance = new V2D_Rectangle(pP0P0, pP0P1, pP1P1, pP1P0, oom, rm);
        BigRational expResult = BigRational.ONE;
        BigRational result = instance.getDistanceSquared(l, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
    }

    /**
     * Test of getDistance method, of class V2D_Rectangle.
     */
    @Test
    public void testGetDistance_V2D_LineSegment_int() {
        System.out.println("getDistance");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_LineSegment l = new V2D_LineSegment(pP0N1, pP1N1, oom, rm);
        V2D_Rectangle instance = new V2D_Rectangle(pP0P0, pP0P1, pP1P1, pP1P0, oom, rm);
        BigRational expResult = BigRational.ONE;
        BigRational result = instance.getDistance(l, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
    }

    /**
     * Test of getDistanceSquared method, of class V2D_Rectangle.
     */
    @Test
    public void testGetDistanceSquared_V2D_LineSegment_int() {
        System.out.println("getDistanceSquared");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_LineSegment l = new V2D_LineSegment(pP0N1, pP1N1, oom, rm);
        V2D_Rectangle instance = new V2D_Rectangle(pP0P0, pP0P1, pP1P1, pP1P0, oom, rm);
        BigRational expResult = BigRational.ONE;
        BigRational result = instance.getDistanceSquared(l, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
    }

    /**
     * Test of getDistance method, of class V2D_Rectangle.
     */
    @Test
    public void testGetDistance_V2D_Triangle_int() {
        System.out.println("getDistance");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Triangle t = new V2D_Triangle(pP0N1, pP1N1, pP0N2, oom, rm);
        V2D_Rectangle instance = new V2D_Rectangle(pP0P0, pP0P1, pP1P1, pP1P0, oom, rm);
        BigRational expResult = BigRational.ONE;
        BigRational result = instance.getDistance(t, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 2
        t = new V2D_Triangle(pP0N1, pP1N1, pP1P0, oom, rm);
        expResult = BigRational.ZERO;
        result = instance.getDistance(t, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
    }

    /**
     * Test of getDistanceSquared method, of class V2D_Rectangle.
     */
    @Test
    public void testGetDistanceSquared_V2D_Triangle_int() {
        System.out.println("getDistanceSquared");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Triangle t = new V2D_Triangle(pP0P0, pP0P1, pP1P1, oom, rm);
        V2D_Rectangle instance = new V2D_Rectangle(pP0P0, pP0P1, pP1P1, pP1P0, oom, rm);
        BigRational expResult = BigRational.ZERO;
        BigRational result = instance.getDistanceSquared(t, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
    }

    /**
     * Test of equals method, of class V2D_Rectangle.
     */
    @Test
    public void testEquals_V2D_Rectangle_int_RoundingMode() {
        System.out.println("equals");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Rectangle r = new V2D_Rectangle(pP0P0, pP0P1, pP1P1, pP1P0, oom, rm);
        V2D_Rectangle instance = new V2D_Rectangle(pP0P0, pP0P1, pP1P1, pP1P0, oom, rm);
        assertTrue(instance.equals(r, oom, rm));
        // Test 2
        instance = new V2D_Rectangle(pP0P1, pP1P1, pP1P0, pP0P0, oom, rm);
        assertTrue(instance.equals(r, oom, rm));
        // Test 3
        instance = new V2D_Rectangle(pP1P1, pP1P0, pP0P0, pP0P1, oom, rm);
        assertTrue(instance.equals(r, oom, rm));
        // Test 4
        instance = new V2D_Rectangle(pP1P0, pP0P0, pP0P1, pP1P1, oom, rm);
        assertTrue(instance.equals(r, oom, rm));
    }

    /**
     * Test of isRectangle method, of class V2D_Rectangle.
     */
    @Test
    public void testIsRectangle() {
        System.out.println("isRectangle");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Point p = pP0P0;
        V2D_Point q = pP1P0;
        V2D_Point r = pP1P1;
        V2D_Point s = pP0P1;
        assertTrue(V2D_Rectangle.isRectangle(p, q, r, s, oom, rm));
        // Test 2
        assertTrue(V2D_Rectangle.isRectangle(p, s, r, q, oom, rm));
        // Test 2
        p = pN1P0;
        assertFalse(V2D_Rectangle.isRectangle(p, q, r, s, oom, rm));
    }
}
