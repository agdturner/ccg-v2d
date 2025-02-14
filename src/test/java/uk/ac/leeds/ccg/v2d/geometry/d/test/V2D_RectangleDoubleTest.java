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
package uk.ac.leeds.ccg.v2d.geometry.d.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.arithmetic.Math_Double;
import uk.ac.leeds.ccg.v2d.core.d.V2D_EnvironmentDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_EnvelopeDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_GeometryDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_LineDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_LineSegmentDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_PointDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_RectangleDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_TriangleDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_VectorDouble;

/**
 * Test of V2D_RectangleDouble class.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class V2D_RectangleDoubleTest extends V2D_TestDouble {

    public V2D_RectangleDoubleTest() {
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
     * Test of getEnvelope method, of class V2D_RectangleDouble.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        /*
         * q ----------- r
         * |             |
         * |             |
         * |             |
         * pv ----------- s
         */
        V2D_RectangleDouble instance;
        V2D_EnvelopeDouble expResult;
        V2D_EnvelopeDouble result;
        instance = new V2D_RectangleDouble(pN1P1, pP1P1, pP1N1, pN1N1);
        expResult = new V2D_EnvelopeDouble(pN1N1, pP1P1);
        result = instance.getEnvelope();
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V2D_RectangleDouble(pN1P1, pP1P1, pP1N1, pN1N1);
        expResult = new V2D_EnvelopeDouble(pN1N1, pP1P1);
        result = instance.getEnvelope();
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V2D_RectangleDouble(pN1P1, pP1P1, pP1N1, pN1N1);
        expResult = new V2D_EnvelopeDouble(pN1N1, pP1P1);
        result = instance.getEnvelope();
        assertTrue(expResult.equals(result));
        // Test 4
        instance = new V2D_RectangleDouble(pN1N1, pP1N1, pP1P1, pN1P1);
        expResult = new V2D_EnvelopeDouble(pN1N1, pP1P1);
        result = instance.getEnvelope();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isIntersectedBy method, of class V2D_RectangleDouble.
     */
    @Test
    public void testIsIntersectedBy_V2D_PointDouble_int() {
        System.out.println("isIntersectedBy");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_PointDouble pt = pP0P0;
        V2D_RectangleDouble instance = new V2D_RectangleDouble(pN1P1, pP1P1, pP1N1, pN1N1);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 2
        instance = new V2D_RectangleDouble(pN1P0, pP0P1, pP1P0, pP0N1);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 3
        double half = 1d / 2d;
        pt = new V2D_PointDouble(env, half, half);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 4
        double halfpe = half + epsilon;
        double halfne = half - epsilon;
        pt = new V2D_PointDouble(env, halfpe, half);
        assertFalse(instance.isIntersectedBy(pt, epsilon));
        // Test 5
        pt = new V2D_PointDouble(env, -halfpe, half);
        assertFalse(instance.isIntersectedBy(pt, epsilon));
        // Test 6
        pt = new V2D_PointDouble(env, half, halfpe);
        assertFalse(instance.isIntersectedBy(pt, epsilon));
        // Test 7
        pt = new V2D_PointDouble(env, half, -halfpe);
        assertFalse(instance.isIntersectedBy(pt, epsilon));
        // Test 8
        pt = new V2D_PointDouble(env, halfne, half);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 9
        pt = new V2D_PointDouble(env, -halfne, half);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 10
        pt = new V2D_PointDouble(env, half, halfne);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 11
        pt = new V2D_PointDouble(env, half, -halfne);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
    }

    /**
     * Test of getIntersection method, of class V2D_RectangleDouble.
     */
    @Test
    public void testGetIntersection() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_LineDouble l = new V2D_LineDouble(pP0N1, pP0P1);
        V2D_RectangleDouble instance;
        V2D_GeometryDouble expResult;
        V2D_GeometryDouble result;
        instance = new V2D_RectangleDouble(pN1P1, pP1P1, pP1N1, pN1N1);
        expResult = new V2D_LineSegmentDouble(pP0N1, pP0P1);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V2D_LineSegmentDouble) result));
    }

    /**
     * Test of toString method, of class V2D_RectangleDouble.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_RectangleDouble instance = new V2D_RectangleDouble(pP0P0, pP0P1, pP1P1, pP1P0);
        String expResult = """
                           V2D_RectangleDouble(
                           offset=V2D_VectorDouble(dx=0.0, dy=0.0),
                           pqr=V2D_TriangleDouble(
                            offset=(V2D_VectorDouble(dx=0.0, dy=0.0)),
                            p=(V2D_VectorDouble(dx=0.0, dy=0.0)),
                            q=(V2D_VectorDouble(dx=0.0, dy=1.0)),
                            r=(V2D_VectorDouble(dx=1.0, dy=1.0))),
                           rsp=V2D_TriangleDouble(
                            offset=(V2D_VectorDouble(dx=0.0, dy=0.0)),
                            p=(V2D_VectorDouble(dx=1.0, dy=1.0)),
                            q=(V2D_VectorDouble(dx=1.0, dy=0.0)),
                            r=(V2D_VectorDouble(dx=0.0, dy=0.0))))""";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of getIntersection method, of class V2D_RectangleDouble.
     */
    @Test
    public void testGetIntersection_V2D_LineSegmentDouble_double() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_LineSegmentDouble l = new V2D_LineSegmentDouble(pN1N1, pP2P2);
        V2D_RectangleDouble instance = new V2D_RectangleDouble(pP0P0, pP0P1, pP1P1, pP1P0);
        V2D_GeometryDouble result = instance.getIntersection(l, epsilon);
        V2D_GeometryDouble expResult = new V2D_LineSegmentDouble(pP0P0, pP1P1);
        assertTrue(((V2D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V2D_LineSegmentDouble) result));
        // Test 2
        l = new V2D_LineSegmentDouble(pP0P0, pP1P0);
        instance = new V2D_RectangleDouble(pP0P0, pP1P0, pP1P1, pP0P1);
        expResult = new V2D_LineSegmentDouble(pP0P0, pP1P0);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V2D_LineSegmentDouble) result));
        // Test 3
        l = new V2D_LineSegmentDouble(pP1N1, pP1P2);
        instance = new V2D_RectangleDouble(pP0P0, pP0P1, pP2P1, pP2P0);
        expResult = new V2D_LineSegmentDouble(pP1P1, pP1P0);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V2D_LineSegmentDouble) result));
    }

    /**
     * Test of getPerimeter method, of class V2D_RectangleDouble.
     */
    @Test
    public void testGetPerimeter() {
        System.out.println("getPerimeter");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_RectangleDouble instance = new V2D_RectangleDouble(pP0P0, pP0P1, pP1P1, pP1P0);
        double expResult = 4d;
        double result = instance.getPerimeter();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getArea method, of class V2D_RectangleDouble.
     */
    @Test
    public void testGetArea() {
        System.out.println("getArea");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_RectangleDouble instance = new V2D_RectangleDouble(pP0P0, pP0P1, pP1P1, pP1P0);
        double expResult = 1d;
        double result = instance.getArea();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V2D_RectangleDouble.
     */
    @Test
    public void testGetDistance() {
        System.out.println("getDistance");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_PointDouble p = pN1N1;
        V2D_RectangleDouble instance = new V2D_RectangleDouble(pP0P0, pP0P1, pP1P1, pP1P0);
        double expResult = Math.sqrt(2d);
        double result = instance.getDistance(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getS method, of class V2D_RectangleDouble.
     */
    @Test
    public void testGetS() {
        System.out.println("getS");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_RectangleDouble instance = new V2D_RectangleDouble(pP0P0, pP0P1, pP1P1, pP1P0);
        V2D_PointDouble expResult = pP1P0;
        V2D_PointDouble result = instance.getS();
        assertTrue(expResult.equals(result, epsilon));
    }

    @Test
    public void testGetIntersection_V2D_Line_double() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_LineDouble l = new V2D_LineDouble(pP0P0, pP1P0);
        V2D_RectangleDouble instance = new V2D_RectangleDouble(pP0P0, pP0P1, pP1P1, pP1P0);
        V2D_GeometryDouble expResult = new V2D_LineSegmentDouble(pP0P0, pP1P0);
        V2D_GeometryDouble result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V2D_LineSegmentDouble) result));
    }

    /**
     * Test of isIntersectedBy method, of class V2D_RectangleDouble.
     */
    @Test
    public void testIsIntersectedBy_V2D_LineSegmentDouble_double() {
        System.out.println("isIntersectedBy");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_RectangleDouble r = new V2D_RectangleDouble(
                new V2D_PointDouble(env, -30, -22),
                new V2D_PointDouble(env, -30, -21),
                new V2D_PointDouble(env, -29, -21),
                new V2D_PointDouble(env, -29, -22));
        V2D_LineSegmentDouble l = new V2D_LineSegmentDouble(
                new V2D_PointDouble(env, -30, -30),
                new V2D_PointDouble(env, -20, 0));
        assertTrue(!r.isIntersectedBy(l, epsilon));
    }

    /**
     * Test of getDistance method, of class V2D_RectangleDouble.
     */
    @Test
    public void testGetDistance_V2D_PointDouble_int() {
        System.out.println("getDistance");
        V2D_PointDouble p = pP0P0;
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_RectangleDouble instance = new V2D_RectangleDouble(pP0P0, pP0P1, pP2P1, pP2P0);
        double expResult = 0d;
        double result = instance.getDistance(p, epsilon);
        assertEquals(expResult, result);
        // Test 2
        p = pN1P0;
        expResult = 1d;
        result = instance.getDistance(p, epsilon);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V2D_RectangleDouble.
     */
    @Test
    public void testGetDistanceSquared_V2D_PointDouble_int() {
        System.out.println("getDistanceSquared");
        V2D_PointDouble p = pP0P0;
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_RectangleDouble instance = new V2D_RectangleDouble(pP0P0, pP0P1, pP1P1, pP1P0);
        double expResult = 0d;
        double result = instance.getDistanceSquared(p, epsilon);
        assertEquals(expResult, result);
        // Test 2
        p = pN1N1;
        expResult = 2d;
        result = instance.getDistanceSquared(p, epsilon);
        assertEquals(expResult, result);
    }

    /**
     * Test of translate method, of class V2D_RectangleDouble.
     */
    @Test
    public void testTranslate() {
        System.out.println("translate");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_VectorDouble v = P1P1;
        V2D_RectangleDouble instance = new V2D_RectangleDouble(pP0P0, pP0P1, pP1P1, pP1P0);
        instance.translate(v);
        V2D_RectangleDouble instance2 = new V2D_RectangleDouble(pP1P1, pP1P2, pP2P2, pP2P1);
        assertTrue(instance.equals(instance2, epsilon));
    }

    /**
     * Test of rotate method, of class V2D_RectangleDouble.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        double theta = Math.PI;
        V2D_RectangleDouble instance = new V2D_RectangleDouble(pP0P0, pP0P1, pP1P1, pP1P0);
        V2D_RectangleDouble result = instance.rotate(pP0P0, theta, epsilon);
        V2D_RectangleDouble expResult = new V2D_RectangleDouble(pP0P0, pP0N1, pN1N1, pN1P0);
        assertTrue(result.equals(expResult, epsilon));
    }

    /**
     * Test of getIntersection method, of class V2D_RectangleDouble.
     */
    @Test
    public void testGetIntersection_V2D_TriangleDouble_double() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_TriangleDouble t = new V2D_TriangleDouble(pP0P0, pP0P1, pP1P1);
        V2D_RectangleDouble instance = new V2D_RectangleDouble(pP0P0, pP0P1, pP1P1, pP1P0);
        V2D_GeometryDouble expResult = new V2D_TriangleDouble(pP0P0, pP0P1, pP1P1);
        V2D_GeometryDouble result = instance.getIntersection(t, epsilon);
        assertTrue(((V2D_TriangleDouble) expResult).equals((V2D_TriangleDouble) result, epsilon));
        // Test 2
         t = new V2D_TriangleDouble(pP2P2, new V2D_PointDouble(env, 4d,4d), 
                 new V2D_PointDouble(env, 2d, 4d));
         instance = new V2D_RectangleDouble( 
                 new V2D_PointDouble(env, 0d,0d),
                 new V2D_PointDouble(env, 0d,6d),
                 new V2D_PointDouble(env, 6d,6d),
                 new V2D_PointDouble(env, 6d,0d));
         expResult = new V2D_TriangleDouble(pP2P2, new V2D_PointDouble(env, 4d,4d), 
                 new V2D_PointDouble(env, 2d, 4d));
         result = instance.getIntersection(t, epsilon);
        assertTrue(((V2D_TriangleDouble) expResult).equals(
                (V2D_TriangleDouble) result, epsilon));
        // Test 3
         t = new V2D_TriangleDouble( 
                 new V2D_PointDouble(env, -1d,4d),
                 new V2D_PointDouble(env, -1d,6d),
                 new V2D_PointDouble(env, 1d,4d));
         instance = new V2D_RectangleDouble(
                 new V2D_PointDouble(env, 0d,0d),
                 new V2D_PointDouble(env, 0d,6d),
                 new V2D_PointDouble(env, 6d,6d),
                 new V2D_PointDouble(env, 6d,0d));
         expResult =  new V2D_TriangleDouble(
                 new V2D_PointDouble(env, 0d,4d),
                 new V2D_PointDouble(env, 0d,5d),
                 new V2D_PointDouble(env, 1d,4d));
         result = instance.getIntersection(t, epsilon);
        assertTrue(((V2D_TriangleDouble) expResult).equals(
                (V2D_TriangleDouble) result, epsilon));
//        // Test 3 This returns a coonvexhull, but this should be simplified to a triangle
//         t = new V2D_TriangleDouble(env, pN1P0, pN1P2, pP1P0);
//         instance = new V2D_RectangleDouble(env, 
//                 new V2D_PointDouble(0d,0d),
//                 new V2D_PointDouble(0d,6d),
//                 new V2D_PointDouble(6d,6d),
//                 new V2D_PointDouble(6d,0d));
//         expResult = new V2D_TriangleDouble(env, pP0P0, pP0P1, pP1P0);
//         result = instance.getIntersection(t, epsilon);
//        assertTrue(((V2D_TriangleDouble) expResult).equals(
//                (V2D_TriangleDouble) result, epsilon));
//        // Test 4
//         t = new V2D_TriangleDouble(env, new V2D_PointDouble(4d,4d), new V2D_PointDouble(5d,5d), 
//                 new V2D_PointDouble(2d, 4d));
//         instance = new V2D_RectangleDouble(env, 
//                 new V2D_PointDouble(0d,0d),
//                 new V2D_PointDouble(0d,6d),
//                 new V2D_PointDouble(6d,6d),
//                 new V2D_PointDouble(6d,0d));
//         expResult = new V2D_LineSegmentDouble(new V2D_PointDouble(4d,4d), new V2D_PointDouble(5d,5d));
//         result = instance.getIntersection(t, epsilon);
//        assertTrue(((V2D_LineSegmentDouble) expResult).equalsIgnoreDirection(
//                epsilon, (V2D_LineSegmentDouble) result));
//        // Test 5
//         t = new V2D_TriangleDouble(env, new V2D_PointDouble(7d,7d), new V2D_PointDouble(8d,8d), 
//                 new V2D_PointDouble(2d, 4d));
//         instance = new V2D_RectangleDouble(env, 
//                 new V2D_PointDouble(0d,0d),
//                 new V2D_PointDouble(0d,6d),
//                 new V2D_PointDouble(6d,6d),
//                 new V2D_PointDouble(6d,0d));
//        result =  instance.getIntersection(t, epsilon);
//        assertNull(result);
//        // Test 6
//         t = new V2D_TriangleDouble(env, new V2D_PointDouble(0d,1d,-1), new V2D_PointDouble(0d,5d), 
//                 new V2D_PointDouble(0d, 2d, 1d));
//         instance = new V2D_RectangleDouble(env, 
//                 new V2D_PointDouble(0d,0d,0d),
//                 new V2D_PointDouble(0d,6d,0d),
//                 new V2D_PointDouble(6d,6d,0d),
//                 new V2D_PointDouble(6d,0d,0d));
//         expResult = new V2D_LineSegmentDouble(new V2D_PointDouble(0d,1.5d,0d), new V2D_PointDouble(0d,3.5d,0d));
//         result = instance.getIntersection(t, epsilon);
//        assertTrue(((V2D_LineSegmentDouble) expResult).equalsIgnoreDirection(
//                epsilon, (V2D_LineSegmentDouble) result));
//        // Test 7
//         t = new V2D_TriangleDouble(env, new V2D_PointDouble(0d,2d,-2d), new V2D_PointDouble(0d,5d,-2d), 
//                 new V2D_PointDouble(0d, 2d, 2d));
//         instance = new V2D_RectangleDouble(env, 
//                 new V2D_PointDouble(0d,0d,0d),
//                 new V2D_PointDouble(0d,6d,0d),
//                 new V2D_PointDouble(6d,6d,0d),
//                 new V2D_PointDouble(6d,0d,0d));
//         expResult = new V2D_LineSegmentDouble(new V2D_PointDouble(0d,1.5d,0d), new V2D_PointDouble(0d,3.5d,0d));
//         result = instance.getIntersection(t, epsilon);
//        assertTrue(((V2D_LineSegmentDouble) expResult).equalsIgnoreDirection(
//                epsilon, (V2D_LineSegmentDouble) result));
//        // Test 8
//         t = new V2D_TriangleDouble(env, new V2D_PointDouble(0d,2d,-2000d), new V2D_PointDouble(0d,5d,-2000d), 
//                 new V2D_PointDouble(0d, 2d, 2000d));
//         instance = new V2D_RectangleDouble(env, 
//                 new V2D_PointDouble(0d,0d,0d),
//                 new V2D_PointDouble(0d,6d,0d),
//                 new V2D_PointDouble(6d,6d,0d),
//                 new V2D_PointDouble(6d,0d,0d));
//         expResult = new V2D_LineSegmentDouble(new V2D_PointDouble(0d,1.5d,0d), new V2D_PointDouble(0d,3.5d,0d));
//         result = instance.getIntersection(t, epsilon);
//        assertTrue(((V2D_LineSegmentDouble) expResult).equalsIgnoreDirection(
//                epsilon, (V2D_LineSegmentDouble) result));
//        // Test 9
//         t = new V2D_TriangleDouble(env, new V2D_PointDouble(0d,2d,-2000000d), new V2D_PointDouble(0d,5d,-2000000d), 
//                 new V2D_PointDouble(0d, 2d, 2000000d));
//         instance = new V2D_RectangleDouble(env, 
//                 new V2D_PointDouble(0d,0d,0d),
//                 new V2D_PointDouble(0d,6d,0d),
//                 new V2D_PointDouble(6d,6d,0d),
//                 new V2D_PointDouble(6d,0d,0d));
//         expResult = new V2D_LineSegmentDouble(new V2D_PointDouble(0d,1.5d,0d), new V2D_PointDouble(0d,3.5d,0d));
//         result = instance.getIntersection(t, epsilon);
//        assertTrue(((V2D_LineSegmentDouble) expResult).equalsIgnoreDirection(
//                epsilon, (V2D_LineSegmentDouble) result));
//        // Test 10
//         t = new V2D_TriangleDouble(env, new V2D_PointDouble(0d,1d,-2d), new V2D_PointDouble(0d,5d,-2d), 
//                 new V2D_PointDouble(0d, 3d, 2d));
//         instance = new V2D_RectangleDouble(env, 
//                 new V2D_PointDouble(0d,0d,0d),
//                 new V2D_PointDouble(0d,6d,0d),
//                 new V2D_PointDouble(6d,6d,0d),
//                 new V2D_PointDouble(6d,0d,0d));
//         expResult = new V2D_LineSegmentDouble(new V2D_PointDouble(0d,1.5d,0d), new V2D_PointDouble(0d,3.5d,0d));
//         result = instance.getIntersection(t, epsilon);
//        assertTrue(((V2D_LineSegmentDouble) expResult).equalsIgnoreDirection(
//                epsilon, (V2D_LineSegmentDouble) result));
    }

    /**
     * Test of getDistance method, of class V2D_RectangleDouble.
     */
    @Test
    public void testGetDistance_V2D_Line_int() {
        System.out.println("getDistance");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_LineDouble l = new V2D_LineDouble(pP0N1, pP1N1);
        V2D_RectangleDouble instance = new V2D_RectangleDouble(pP0P0, pP0P1, pP1P1, pP1P0);
        double expResult = 1d;
        double result = instance.getDistance(l, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistanceSquared method, of class V2D_RectangleDouble.
     */
    @Test
    public void testGetDistanceSquared_V2D_Line_int() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_LineDouble l = new V2D_LineDouble(pP0N1, pP1N1);
        V2D_RectangleDouble instance = new V2D_RectangleDouble(pP0P0, pP0P1, pP1P1, pP1P0);
        double expResult = 1d;
        double result = instance.getDistanceSquared(l, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V2D_RectangleDouble.
     */
    @Test
    public void testGetDistance_V2D_LineSegmentDouble_int() {
        System.out.println("getDistance");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_LineSegmentDouble l = new V2D_LineSegmentDouble(pP0N1, pP1N1);
        V2D_RectangleDouble instance = new V2D_RectangleDouble(pP0P0, pP0P1, pP1P1, pP1P0);
        double expResult = 1d;
        double result = instance.getDistance(l, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistanceSquared method, of class V2D_RectangleDouble.
     */
    @Test
    public void testGetDistanceSquared_V2D_LineSegmentDouble_int() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_LineSegmentDouble l = new V2D_LineSegmentDouble(pP0N1, pP1N1);
        V2D_RectangleDouble instance = new V2D_RectangleDouble(pP0P0, pP0P1, pP1P1, pP1P0);
        double expResult = 1d;
        double result = instance.getDistanceSquared(l, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V2D_RectangleDouble.
     */
    @Test
    public void testGetDistance_V2D_TriangleDouble_int() {
        System.out.println("getDistance");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_TriangleDouble t = new V2D_TriangleDouble( pP0N1, pP1N1, pP0P1);
        V2D_RectangleDouble instance = new V2D_RectangleDouble(pP0P0, pP0P1, pP1P1, pP1P0);
        double expResult = 0d;
        double result = instance.getDistance(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        t = new V2D_TriangleDouble(pP0N1, pP1N1, pP1P1);
        expResult = 0d;
        result = instance.getDistance(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistanceSquared method, of class V2D_RectangleDouble.
     */
    @Test
    public void testGetDistanceSquared_V2D_TriangleDouble_int() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_TriangleDouble t = new V2D_TriangleDouble(pP0P0, pP0P1, pP1P1);
        V2D_RectangleDouble instance = new V2D_RectangleDouble(pP0P0, pP0P1, pP1P1, pP1P0);
        double expResult = 0d;
        double result = instance.getDistanceSquared(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of equals method, of class V2D_RectangleDouble.
     */
    @Test
    public void testEquals_V2D_RectangleDouble_int_RoundingMode() {
        System.out.println("equals");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_RectangleDouble r = new V2D_RectangleDouble(pP0P0, pP0P1, pP1P1, pP1P0);
        V2D_RectangleDouble instance = new V2D_RectangleDouble(pP0P0, pP0P1, pP1P1, pP1P0);
        assertTrue(instance.equals(r, epsilon));
        // Test 2
        instance = new V2D_RectangleDouble(pP0P1, pP1P1, pP1P0, pP0P0);
        assertTrue(instance.equals(r, epsilon));
        // Test 3
        instance = new V2D_RectangleDouble(pP1P1, pP1P0, pP0P0, pP0P1);
        assertTrue(instance.equals(r, epsilon));
        // Test 4
        instance = new V2D_RectangleDouble(pP1P0, pP0P0, pP0P1, pP1P1);
        assertTrue(instance.equals(r, epsilon));
    }

    /**
     * Test of isRectangle method, of class V2D_RectangleDouble.
     */
    @Test
    public void testIsRectangle() {
        System.out.println("isRectangle");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_PointDouble p = pP0P0;
        V2D_PointDouble q = pP1P0;
        V2D_PointDouble r = pP1P1;
        V2D_PointDouble s = pP0P1;
        assertTrue(V2D_RectangleDouble.isRectangle(p, q, r, s, epsilon));
        // Test 2
        assertTrue(V2D_RectangleDouble.isRectangle(p, s, r, q, epsilon));
        // Test 2
        p = pN1P0;
        assertFalse(V2D_RectangleDouble.isRectangle(p, q, r, s, epsilon));
    }
}
