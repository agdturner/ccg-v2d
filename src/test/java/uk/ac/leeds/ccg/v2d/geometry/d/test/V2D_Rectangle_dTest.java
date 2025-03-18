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
import uk.ac.leeds.ccg.v2d.core.d.V2D_Environment_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_AABB_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_Geometry_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_Line_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_LineSegment_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_Point_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_Rectangle_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_Triangle_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_Vector_d;

/**
 * Test of V2D_Rectangle_d class.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class V2D_Rectangle_dTest extends V2D_Test_d {

    public V2D_Rectangle_dTest() {
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
     * Test of getAABB method, of class V2D_Rectangle_d.
     */
    @Test
    public void testGetAABB() {
        System.out.println("getAABB");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        /*
         * q ----------- r
         * |             |
         * |             |
         * |             |
         * pv ----------- s
         */
        V2D_Rectangle_d instance;
        V2D_AABB_d expResult;
        V2D_AABB_d result;
        instance = new V2D_Rectangle_d(pN1P1, pP1P1, pP1N1, pN1N1);
        expResult = new V2D_AABB_d(pN1N1, pP1P1);
        result = instance.getAABB();
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V2D_Rectangle_d(pN1P1, pP1P1, pP1N1, pN1N1);
        expResult = new V2D_AABB_d(pN1N1, pP1P1);
        result = instance.getAABB();
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V2D_Rectangle_d(pN1P1, pP1P1, pP1N1, pN1N1);
        expResult = new V2D_AABB_d(pN1N1, pP1P1);
        result = instance.getAABB();
        assertTrue(expResult.equals(result));
        // Test 4
        instance = new V2D_Rectangle_d(pN1N1, pP1N1, pP1P1, pN1P1);
        expResult = new V2D_AABB_d(pN1N1, pP1P1);
        result = instance.getAABB();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of intersects method, of class V2D_Rectangle_d.
     */
    @Test
    public void testIsIntersectedBy_V2D_Point_d_int() {
        System.out.println("intersects");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Point_d pt = pP0P0;
        V2D_Rectangle_d instance = new V2D_Rectangle_d(pN1P1, pP1P1, pP1N1, pN1N1);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 2
        instance = new V2D_Rectangle_d(pN1P0, pP0P1, pP1P0, pP0N1);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 3
        double half = 1d / 2d;
        pt = new V2D_Point_d(env, half, half);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 4
        double halfpe = half + epsilon;
        double halfne = half - epsilon;
        pt = new V2D_Point_d(env, halfpe, half);
        assertFalse(instance.intersects(pt, epsilon));
        // Test 5
        pt = new V2D_Point_d(env, -halfpe, half);
        assertFalse(instance.intersects(pt, epsilon));
        // Test 6
        pt = new V2D_Point_d(env, half, halfpe);
        assertFalse(instance.intersects(pt, epsilon));
        // Test 7
        pt = new V2D_Point_d(env, half, -halfpe);
        assertFalse(instance.intersects(pt, epsilon));
        // Test 8
        pt = new V2D_Point_d(env, halfne, half);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 9
        pt = new V2D_Point_d(env, -halfne, half);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 10
        pt = new V2D_Point_d(env, half, halfne);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 11
        pt = new V2D_Point_d(env, half, -halfne);
        assertTrue(instance.intersects(pt, epsilon));
    }

    /**
     * Test of getIntersect method, of class V2D_Rectangle_d.
     */
    @Test
    public void testGetIntersection() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Line_d l = new V2D_Line_d(pP0N1, pP0P1);
        V2D_Rectangle_d instance;
        V2D_Geometry_d expResult;
        V2D_Geometry_d result;
        instance = new V2D_Rectangle_d(pN1P1, pP1P1, pP1N1, pN1N1);
        expResult = new V2D_LineSegment_d(pP0N1, pP0P1);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
    }

    /**
     * Test of toString method, of class V2D_Rectangle_d.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Rectangle_d instance = new V2D_Rectangle_d(pP0P0, pP0P1, pP1P1, pP1P0);
        String expResult = """
                           V2D_Rectangle_d(
                           offset=V2D_Vector_d(dx=0.0, dy=0.0),
                           pqr=V2D_Triangle_d(
                            offset=(V2D_Vector_d(dx=0.0, dy=0.0)),
                            p=(V2D_Vector_d(dx=0.0, dy=0.0)),
                            q=(V2D_Vector_d(dx=0.0, dy=1.0)),
                            r=(V2D_Vector_d(dx=1.0, dy=1.0))),
                           rsp=V2D_Triangle_d(
                            offset=(V2D_Vector_d(dx=0.0, dy=0.0)),
                            p=(V2D_Vector_d(dx=1.0, dy=1.0)),
                            q=(V2D_Vector_d(dx=1.0, dy=0.0)),
                            r=(V2D_Vector_d(dx=0.0, dy=0.0))))""";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of getIntersect method, of class V2D_Rectangle_d.
     */
    @Test
    public void testGetIntersection_V2D_LineSegmentDouble_double() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_LineSegment_d l = new V2D_LineSegment_d(pN1N1, pP2P2);
        V2D_Rectangle_d instance = new V2D_Rectangle_d(pP0P0, pP0P1, pP1P1, pP1P0);
        V2D_Geometry_d result = instance.getIntersect(l, epsilon);
        V2D_Geometry_d expResult = new V2D_LineSegment_d(pP0P0, pP1P1);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
        // Test 2
        l = new V2D_LineSegment_d(pP0P0, pP1P0);
        instance = new V2D_Rectangle_d(pP0P0, pP1P0, pP1P1, pP0P1);
        expResult = new V2D_LineSegment_d(pP0P0, pP1P0);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
        // Test 3
        l = new V2D_LineSegment_d(pP1N1, pP1P2);
        instance = new V2D_Rectangle_d(pP0P0, pP0P1, pP2P1, pP2P0);
        expResult = new V2D_LineSegment_d(pP1P1, pP1P0);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
    }

    /**
     * Test of getPerimeter method, of class V2D_Rectangle_d.
     */
    @Test
    public void testGetPerimeter() {
        System.out.println("getPerimeter");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Rectangle_d instance = new V2D_Rectangle_d(pP0P0, pP0P1, pP1P1, pP1P0);
        double expResult = 4d;
        double result = instance.getPerimeter();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getArea method, of class V2D_Rectangle_d.
     */
    @Test
    public void testGetArea() {
        System.out.println("getArea");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Rectangle_d instance = new V2D_Rectangle_d(pP0P0, pP0P1, pP1P1, pP1P0);
        double expResult = 1d;
        double result = instance.getArea();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V2D_Rectangle_d.
     */
    @Test
    public void testGetDistance() {
        System.out.println("getDistance");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Point_d p = pN1N1;
        V2D_Rectangle_d instance = new V2D_Rectangle_d(pP0P0, pP0P1, pP1P1, pP1P0);
        double expResult = Math.sqrt(2d);
        double result = instance.getDistance(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getS method, of class V2D_Rectangle_d.
     */
    @Test
    public void testGetS() {
        System.out.println("getS");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Rectangle_d instance = new V2D_Rectangle_d(pP0P0, pP0P1, pP1P1, pP1P0);
        V2D_Point_d expResult = pP1P0;
        V2D_Point_d result = instance.getS();
        assertTrue(expResult.equals(result, epsilon));
    }

    @Test
    public void testGetIntersection_V2D_Line_double() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Line_d l = new V2D_Line_d(pP0P0, pP1P0);
        V2D_Rectangle_d instance = new V2D_Rectangle_d(pP0P0, pP0P1, pP1P1, pP1P0);
        V2D_Geometry_d expResult = new V2D_LineSegment_d(pP0P0, pP1P0);
        V2D_Geometry_d result = instance.getIntersect(l, epsilon);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
    }

    /**
     * Test of intersects method, of class V2D_Rectangle_d.
     */
    @Test
    public void testIsIntersectedBy_V2D_LineSegmentDouble_double() {
        System.out.println("intersects");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Rectangle_d r = new V2D_Rectangle_d(
                new V2D_Point_d(env, -30, -22),
                new V2D_Point_d(env, -30, -21),
                new V2D_Point_d(env, -29, -21),
                new V2D_Point_d(env, -29, -22));
        V2D_LineSegment_d l = new V2D_LineSegment_d(
                new V2D_Point_d(env, -30, -30),
                new V2D_Point_d(env, -20, 0));
        assertTrue(!r.intersects(l, epsilon));
    }

    /**
     * Test of getDistance method, of class V2D_Rectangle_d.
     */
    @Test
    public void testGetDistance_V2D_Point_d_int() {
        System.out.println("getDistance");
        V2D_Point_d p = pP0P0;
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Rectangle_d instance = new V2D_Rectangle_d(pP0P0, pP0P1, pP2P1, pP2P0);
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
     * Test of getDistanceSquared method, of class V2D_Rectangle_d.
     */
    @Test
    public void testGetDistanceSquared_V2D_Point_d_int() {
        System.out.println("getDistanceSquared");
        V2D_Point_d p = pP0P0;
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Rectangle_d instance = new V2D_Rectangle_d(pP0P0, pP0P1, pP1P1, pP1P0);
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
     * Test of translate method, of class V2D_Rectangle_d.
     */
    @Test
    public void testTranslate() {
        System.out.println("translate");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Vector_d v = P1P1;
        V2D_Rectangle_d instance = new V2D_Rectangle_d(pP0P0, pP0P1, pP1P1, pP1P0);
        instance.translate(v);
        V2D_Rectangle_d instance2 = new V2D_Rectangle_d(pP1P1, pP1P2, pP2P2, pP2P1);
        assertTrue(instance.equals(instance2, epsilon));
    }

    /**
     * Test of rotate method, of class V2D_Rectangle_d.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        double theta = Math.PI;
        V2D_Rectangle_d instance = new V2D_Rectangle_d(pP0P0, pP0P1, pP1P1, pP1P0);
        V2D_Rectangle_d result = instance.rotate(pP0P0, theta);
        V2D_Rectangle_d expResult = new V2D_Rectangle_d(pP0P0, pP0N1, pN1N1, pN1P0);
        assertTrue(result.equals(expResult, epsilon));
    }

    /**
     * Test of getIntersect method, of class V2D_Rectangle_d.
     */
    @Test
    public void testGetIntersection_V2D_Triangle_d_double() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Triangle_d t = new V2D_Triangle_d(pP0P0, pP0P1, pP1P1);
        V2D_Rectangle_d instance = new V2D_Rectangle_d(pP0P0, pP0P1, pP1P1, pP1P0);
        V2D_Geometry_d expResult = new V2D_Triangle_d(pP0P0, pP0P1, pP1P1);
        V2D_Geometry_d result = instance.getIntersect(t, epsilon);
        assertTrue(((V2D_Triangle_d) expResult).equals((V2D_Triangle_d) result, epsilon));
        // Test 2
         t = new V2D_Triangle_d(pP2P2, new V2D_Point_d(env, 4d,4d), 
                 new V2D_Point_d(env, 2d, 4d));
         instance = new V2D_Rectangle_d( 
                 new V2D_Point_d(env, 0d,0d),
                 new V2D_Point_d(env, 0d,6d),
                 new V2D_Point_d(env, 6d,6d),
                 new V2D_Point_d(env, 6d,0d));
         expResult = new V2D_Triangle_d(pP2P2, new V2D_Point_d(env, 4d,4d), 
                 new V2D_Point_d(env, 2d, 4d));
         result = instance.getIntersect(t, epsilon);
        assertTrue(((V2D_Triangle_d) expResult).equals((V2D_Triangle_d) result, epsilon));
        // Test 3
         t = new V2D_Triangle_d( 
                 new V2D_Point_d(env, -1d,4d),
                 new V2D_Point_d(env, -1d,6d),
                 new V2D_Point_d(env, 1d,4d));
         instance = new V2D_Rectangle_d(
                 new V2D_Point_d(env, 0d,0d),
                 new V2D_Point_d(env, 0d,6d),
                 new V2D_Point_d(env, 6d,6d),
                 new V2D_Point_d(env, 6d,0d));
         expResult =  new V2D_Triangle_d(
                 new V2D_Point_d(env, 0d,4d),
                 new V2D_Point_d(env, 0d,5d),
                 new V2D_Point_d(env, 1d,4d));
         result = instance.getIntersect(t, epsilon);
        assertTrue(((V2D_Triangle_d) expResult).equals((V2D_Triangle_d) result, epsilon));
//        // Test 3 This returns a coonvexhull, but this should be simplified to a triangle
//         t = new V2D_Triangle_d(env, pN1P0, pN1P2, pP1P0);
//         instance = new V2D_Rectangle_d(env, 
//                 new V2D_Point_d(0d,0d),
//                 new V2D_Point_d(0d,6d),
//                 new V2D_Point_d(6d,6d),
//                 new V2D_Point_d(6d,0d));
//         expResult = new V2D_Triangle_d(env, pP0P0, pP0P1, pP1P0);
//         result = instance.getIntersect(t, epsilon);
//        assertTrue(((V2D_Triangle_d) expResult).equals(
//                (V2D_Triangle_d) result, epsilon));
//        // Test 4
//         t = new V2D_Triangle_d(env, new V2D_Point_d(4d,4d), new V2D_Point_d(5d,5d), 
//                 new V2D_Point_d(2d, 4d));
//         instance = new V2D_Rectangle_d(env, 
//                 new V2D_Point_d(0d,0d),
//                 new V2D_Point_d(0d,6d),
//                 new V2D_Point_d(6d,6d),
//                 new V2D_Point_d(6d,0d));
//         expResult = new V2D_LineSegment_d(new V2D_Point_d(4d,4d), new V2D_Point_d(5d,5d));
//         result = instance.getIntersect(t, epsilon);
//        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(
//                epsilon, (V2D_LineSegment_d) result));
//        // Test 5
//         t = new V2D_Triangle_d(env, new V2D_Point_d(7d,7d), new V2D_Point_d(8d,8d), 
//                 new V2D_Point_d(2d, 4d));
//         instance = new V2D_Rectangle_d(env, 
//                 new V2D_Point_d(0d,0d),
//                 new V2D_Point_d(0d,6d),
//                 new V2D_Point_d(6d,6d),
//                 new V2D_Point_d(6d,0d));
//        result =  instance.getIntersect(t, epsilon);
//        assertNull(result);
//        // Test 6
//         t = new V2D_Triangle_d(env, new V2D_Point_d(0d,1d,-1), new V2D_Point_d(0d,5d), 
//                 new V2D_Point_d(0d, 2d, 1d));
//         instance = new V2D_Rectangle_d(env, 
//                 new V2D_Point_d(0d,0d,0d),
//                 new V2D_Point_d(0d,6d,0d),
//                 new V2D_Point_d(6d,6d,0d),
//                 new V2D_Point_d(6d,0d,0d));
//         expResult = new V2D_LineSegment_d(new V2D_Point_d(0d,1.5d,0d), new V2D_Point_d(0d,3.5d,0d));
//         result = instance.getIntersect(t, epsilon);
//        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(
//                epsilon, (V2D_LineSegment_d) result));
//        // Test 7
//         t = new V2D_Triangle_d(env, new V2D_Point_d(0d,2d,-2d), new V2D_Point_d(0d,5d,-2d), 
//                 new V2D_Point_d(0d, 2d, 2d));
//         instance = new V2D_Rectangle_d(env, 
//                 new V2D_Point_d(0d,0d,0d),
//                 new V2D_Point_d(0d,6d,0d),
//                 new V2D_Point_d(6d,6d,0d),
//                 new V2D_Point_d(6d,0d,0d));
//         expResult = new V2D_LineSegment_d(new V2D_Point_d(0d,1.5d,0d), new V2D_Point_d(0d,3.5d,0d));
//         result = instance.getIntersect(t, epsilon);
//        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(
//                epsilon, (V2D_LineSegment_d) result));
//        // Test 8
//         t = new V2D_Triangle_d(env, new V2D_Point_d(0d,2d,-2000d), new V2D_Point_d(0d,5d,-2000d), 
//                 new V2D_Point_d(0d, 2d, 2000d));
//         instance = new V2D_Rectangle_d(env, 
//                 new V2D_Point_d(0d,0d,0d),
//                 new V2D_Point_d(0d,6d,0d),
//                 new V2D_Point_d(6d,6d,0d),
//                 new V2D_Point_d(6d,0d,0d));
//         expResult = new V2D_LineSegment_d(new V2D_Point_d(0d,1.5d,0d), new V2D_Point_d(0d,3.5d,0d));
//         result = instance.getIntersect(t, epsilon);
//        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(
//                epsilon, (V2D_LineSegment_d) result));
//        // Test 9
//         t = new V2D_Triangle_d(env, new V2D_Point_d(0d,2d,-2000000d), new V2D_Point_d(0d,5d,-2000000d), 
//                 new V2D_Point_d(0d, 2d, 2000000d));
//         instance = new V2D_Rectangle_d(env, 
//                 new V2D_Point_d(0d,0d,0d),
//                 new V2D_Point_d(0d,6d,0d),
//                 new V2D_Point_d(6d,6d,0d),
//                 new V2D_Point_d(6d,0d,0d));
//         expResult = new V2D_LineSegment_d(new V2D_Point_d(0d,1.5d,0d), new V2D_Point_d(0d,3.5d,0d));
//         result = instance.getIntersect(t, epsilon);
//        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(
//                epsilon, (V2D_LineSegment_d) result));
//        // Test 10
//         t = new V2D_Triangle_d(env, new V2D_Point_d(0d,1d,-2d), new V2D_Point_d(0d,5d,-2d), 
//                 new V2D_Point_d(0d, 3d, 2d));
//         instance = new V2D_Rectangle_d(env, 
//                 new V2D_Point_d(0d,0d,0d),
//                 new V2D_Point_d(0d,6d,0d),
//                 new V2D_Point_d(6d,6d,0d),
//                 new V2D_Point_d(6d,0d,0d));
//         expResult = new V2D_LineSegment_d(new V2D_Point_d(0d,1.5d,0d), new V2D_Point_d(0d,3.5d,0d));
//         result = instance.getIntersect(t, epsilon);
//        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(
//                epsilon, (V2D_LineSegment_d) result));
    }

    /**
     * Test of getDistance method, of class V2D_Rectangle_d.
     */
    @Test
    public void testGetDistance_V2D_Line_int() {
        System.out.println("getDistance");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Line_d l = new V2D_Line_d(pP0N1, pP1N1);
        V2D_Rectangle_d instance = new V2D_Rectangle_d(pP0P0, pP0P1, pP1P1, pP1P0);
        double expResult = 1d;
        double result = instance.getDistance(l, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistanceSquared method, of class V2D_Rectangle_d.
     */
    @Test
    public void testGetDistanceSquared_V2D_Line_int() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Line_d l = new V2D_Line_d(pP0N1, pP1N1);
        V2D_Rectangle_d instance = new V2D_Rectangle_d(pP0P0, pP0P1, pP1P1, pP1P0);
        double expResult = 1d;
        double result = instance.getDistanceSquared(l, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V2D_Rectangle_d.
     */
    @Test
    public void testGetDistance_V2D_LineSegmentDouble_int() {
        System.out.println("getDistance");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_LineSegment_d l = new V2D_LineSegment_d(pP0N1, pP1N1);
        V2D_Rectangle_d instance = new V2D_Rectangle_d(pP0P0, pP0P1, pP1P1, pP1P0);
        double expResult = 1d;
        double result = instance.getDistance(l, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistanceSquared method, of class V2D_Rectangle_d.
     */
    @Test
    public void testGetDistanceSquared_V2D_LineSegmentDouble_int() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_LineSegment_d l = new V2D_LineSegment_d(pP0N1, pP1N1);
        V2D_Rectangle_d instance = new V2D_Rectangle_d(pP0P0, pP0P1, pP1P1, pP1P0);
        double expResult = 1d;
        double result = instance.getDistanceSquared(l, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V2D_Rectangle_d.
     */
    @Test
    public void testGetDistance_V2D_Triangle_d_int() {
        System.out.println("getDistance");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Triangle_d t = new V2D_Triangle_d( pP0N1, pP1N1, pP0P1);
        V2D_Rectangle_d instance = new V2D_Rectangle_d(pP0P0, pP0P1, pP1P1, pP1P0);
        double expResult = 0d;
        double result = instance.getDistance(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        t = new V2D_Triangle_d(pP0N1, pP1N1, pP1P1);
        expResult = 0d;
        result = instance.getDistance(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistanceSquared method, of class V2D_Rectangle_d.
     */
    @Test
    public void testGetDistanceSquared_V2D_Triangle_d_int() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Triangle_d t = new V2D_Triangle_d(pP0P0, pP0P1, pP1P1);
        V2D_Rectangle_d instance = new V2D_Rectangle_d(pP0P0, pP0P1, pP1P1, pP1P0);
        double expResult = 0d;
        double result = instance.getDistanceSquared(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of equals method, of class V2D_Rectangle_d.
     */
    @Test
    public void testEquals_V2D_Rectangle_d_int_RoundingMode() {
        System.out.println("equals");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Rectangle_d r = new V2D_Rectangle_d(pP0P0, pP0P1, pP1P1, pP1P0);
        V2D_Rectangle_d instance = new V2D_Rectangle_d(pP0P0, pP0P1, pP1P1, pP1P0);
        assertTrue(instance.equals(r, epsilon));
        // Test 2
        instance = new V2D_Rectangle_d(pP0P1, pP1P1, pP1P0, pP0P0);
        assertTrue(instance.equals(r, epsilon));
        // Test 3
        instance = new V2D_Rectangle_d(pP1P1, pP1P0, pP0P0, pP0P1);
        assertTrue(instance.equals(r, epsilon));
        // Test 4
        instance = new V2D_Rectangle_d(pP1P0, pP0P0, pP0P1, pP1P1);
        assertTrue(instance.equals(r, epsilon));
    }

    /**
     * Test of isRectangle method, of class V2D_Rectangle_d.
     */
    @Test
    public void testIsRectangle() {
        System.out.println("isRectangle");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Point_d p = pP0P0;
        V2D_Point_d q = pP1P0;
        V2D_Point_d r = pP1P1;
        V2D_Point_d s = pP0P1;
        assertTrue(V2D_Rectangle_d.isRectangle(p, q, r, s, epsilon));
        // Test 2
        assertTrue(V2D_Rectangle_d.isRectangle(p, s, r, q, epsilon));
        // Test 2
        p = pN1P0;
        assertFalse(V2D_Rectangle_d.isRectangle(p, q, r, s, epsilon));
    }
}
