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
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_AABB_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_Geometry_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_Line_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_LineSegment_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_Point_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_Vector_d;

/**
 * Test of V2D_LineSegment_d class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_LineSegment_dTest extends V2D_Test_d {

    public V2D_LineSegment_dTest() {
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
     * Test of toString method, of class V2D_LineSegment_d.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V2D_LineSegment_d instance = new V2D_LineSegment_d(pP0P0, pP1P0);
        String expResult = """
                           V2D_LineSegment_d
                           (
                            offset=V2D_Vector_d(dx=0.0, dy=0.0),
                            l= offset=V2D_Vector_d(dx=0.0, dy=0.0),
                            p=V2D_Point_d(offset=V2D_Vector_d(dx=0.0, dy=0.0), rel=V2D_Vector_d(dx=0.0, dy=0.0)),
                            v= V2D_Vector_d(dx=1.0, dy=0.0)
                           )""";
        String result = instance.toString();
        System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of getAABB method, of class V2D_LineSegment_d.
     */
    @Test
    public void testGetAABB() {
        System.out.println("getAABB");
        V2D_LineSegment_d instance = new V2D_LineSegment_d(pN1N1, pP1P1);
        V2D_AABB_d expResult = new V2D_AABB_d(pN1N1, pP1P1);
        V2D_AABB_d result = instance.getAABB();
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V2D_LineSegment_d(pP1N1, pN1P1);
        result = instance.getAABB();
        assertTrue(expResult.equals(result));
        
    }

    /**
     * Test of intersects method, of class V2D_LineSegment_d.
     */
    @Test
    public void testIsIntersectedBy_V2D_Point_d() {
        System.out.println("intersects");
        double epsilon = 1d / 1000000d;
        V2D_Point_d p = pP0P0;
        V2D_LineSegment_d instance = new V2D_LineSegment_d(pN1N1, pP1P1);
        assertTrue(instance.intersects(p, epsilon));
        // Test2
        p = pP1P1;
        instance = new V2D_LineSegment_d(pN1N1, pP1P1);
        assertTrue(instance.intersects(p, epsilon));
    }

    /**
     * Test of equals method, of class V2D_LineSegment_d.
     */
    @Test
    public void testEquals_V2D_LineSegment_d_double() {
        System.out.println("equals");
        double epsilon = 1d / 1000000d;
        V2D_LineSegment_d l = new V2D_LineSegment_d(pP0P0, pP1P0);
        V2D_LineSegment_d instance = new V2D_LineSegment_d(pP0P0, pP1P0);
        assertTrue(instance.equals(l, epsilon));
        // Test 2
        instance = new V2D_LineSegment_d(pP1P0, pP0P0);
        assertFalse(instance.equals(l, epsilon));
    }

    /**
     * Test of equalsIgnoreDirection method, of class V2D_LineSegment_d.
     */
    @Test
    public void testEqualsIgnoreDirection() {
        System.out.println("equalsIgnoreDirection");
        double epsilon = 1d / 1000000d;
        V2D_LineSegment_d l = new V2D_LineSegment_d(pP0P0, pP1P1);
        V2D_LineSegment_d instance = new V2D_LineSegment_d(pP0P0, pP2P0);
        assertFalse(instance.equalsIgnoreDirection(epsilon, l));
        // Test 2
        instance = new V2D_LineSegment_d(pP1P1, pP0P0);
        assertTrue(instance.equalsIgnoreDirection(epsilon, l));
        // Test 3
        instance = new V2D_LineSegment_d(pP0P0, pP1P1);
        assertTrue(instance.equalsIgnoreDirection(epsilon, l));
    }

    /**
     * Test of multiply method, of class V2D_LineSegment_d.
     */
    @Test
    public void testTranslate() {
        System.out.println("translate");
        double epsilon = 1d / 1000000d;
        V2D_Vector_d v = V2D_Vector_d.I;
        V2D_LineSegment_d instance = new V2D_LineSegment_d(pP0P0, pP1P0);
        V2D_LineSegment_d expResult = new V2D_LineSegment_d(pP1P0, pP2P0);
        instance.translate(v);
        assertTrue(expResult.equalsIgnoreDirection(epsilon, instance));
        // Test 2
        instance = new V2D_LineSegment_d(pP0P0, pP0P1);
        expResult = new V2D_LineSegment_d(pP1P0, pP1P1);
        instance.translate(v);
        assertTrue(expResult.equalsIgnoreDirection(epsilon, instance));
    }

    
    /**
     * Test of getIntersect method, of class V2D_LineSegment_d.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        double epsilon = 1d / 1000000d;
        double Pi = Math.PI;
        double theta = Pi;
        V2D_LineSegment_d instance = new V2D_LineSegment_d(pP0P0, pP1P0);
        V2D_LineSegment_d expResult = new V2D_LineSegment_d(pP0P0, pN1P0);
        V2D_LineSegment_d result = instance.rotate(pP0P0, theta);
        assertTrue(expResult.equalsIgnoreDirection(epsilon, result));
        // Test 2
        theta = Pi / 2.0d;
        expResult = new V2D_LineSegment_d(pP0P0, pP0N1);
        result = instance.rotate(pP0P0, theta);
        assertTrue(expResult.equalsIgnoreDirection(epsilon, result));
        // Test 2
        theta = 3.0d * Pi / 2.0d;
        expResult = new V2D_LineSegment_d(pP0P0, pP0P1);
        result = instance.rotate(pP0P0, theta);
        assertTrue(expResult.equalsIgnoreDirection(epsilon, result));
        
        
    }
    
    /**
     * Test of getIntersect method, of class V2D_LineSegment_d.
     */
    @Test
    public void testGetIntersection_V2D_LineDouble() {
        System.out.println("getIntersection");
        double epsilon = 1d / 1000000d;
        V2D_Line_d l = new V2D_Line_d(pP0P0, pP1P0);
        V2D_LineSegment_d instance = new V2D_LineSegment_d(pP0P0, pP1P0);
        V2D_Geometry_d expResult = new V2D_LineSegment_d(pP0P0, pP1P0);
        V2D_Geometry_d result = instance.getIntersect(epsilon, l);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
        // Test 2
        instance = new V2D_LineSegment_d(pP0P0, pP1P1);
        result = instance.getIntersect(epsilon, l);
        expResult = pP0P0;
        assertTrue(((V2D_Point_d) expResult).equals((V2D_Point_d) result));
        // Test 3
        instance = new V2D_LineSegment_d(pN1N1, pN1P0);
        expResult = pN1P0;
        result = instance.getIntersect(epsilon, l);
        assertTrue(((V2D_Point_d) expResult).equals((V2D_Point_d) result));
        // Test 4
        l = new V2D_Line_d(pN1N1, pP1P1);
        instance = new V2D_LineSegment_d(pP1N1, pN1P1);
        result = instance.getIntersect(epsilon, l);
        expResult = pP0P0;
        assertTrue(((V2D_Point_d) expResult).equals((V2D_Point_d) result));
        // Test 5
        l = new V2D_Line_d(pP0P0, pP1P0);
        instance = new V2D_LineSegment_d(pN1P0, pP1P0);
        result = instance.getIntersect(epsilon, l);
        expResult = new V2D_LineSegment_d(pN1P0, pP1P0);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
        // Test 6
        l = new V2D_Line_d(pN1P0, pN1P1);
        instance = new V2D_LineSegment_d(pP0P0, pP1P0);
        result = instance.getIntersect(epsilon, l);
        assertNull(result);
    }

    /**
     * Test of getIntersect method, of class V2D_LineSegment_d.
     */
    @Test
    public void testGetIntersection_V2D_LineSegment_d_double() {
        System.out.println("getIntersection");
        double epsilon = 1d / 1000000d;
        V2D_LineSegment_d l = new V2D_LineSegment_d(pP0P0, pP1P0);
        V2D_LineSegment_d instance = new V2D_LineSegment_d(pP0P0, pP1P0);
        V2D_Geometry_d expResult = new V2D_LineSegment_d(pP0P0, pP1P0);
        V2D_Geometry_d result = instance.getIntersect(epsilon, l);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
        // Test 2
        instance = new V2D_LineSegment_d(pP0P0, pP1P1);
        result = instance.getIntersect(epsilon, l);
        expResult = pP0P0;
        assertTrue(((V2D_Point_d) expResult).equals((V2D_Point_d) result));
        // Test 3
        instance = new V2D_LineSegment_d(pN1N1, pN1P1);
        result = instance.getIntersect(epsilon, l);
        assertNull(result);
        // Test 4
        l = new V2D_LineSegment_d(pN1N1, pP1P1);
        instance = new V2D_LineSegment_d(pP1N1, pN1P1);
        result = instance.getIntersect(epsilon, l);
        expResult = pP0P0;
        assertTrue(((V2D_Point_d) expResult).equals((V2D_Point_d) result));
        // Test 5
        l = new V2D_LineSegment_d(pP0P0, pP1P0);
        instance = new V2D_LineSegment_d(pN1P0, pP1P0);
        result = instance.getIntersect(epsilon, l);
        expResult = new V2D_LineSegment_d(pP0P0, pP1P0);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
    }

    /**
     * Test of getLength2 method, of class V2D_LineSegment_d.
     */
    @Test
    public void testGetLength2() {
        System.out.println("getLength2");
        double epsilon = 1d / 1000000d;
        V2D_LineSegment_d instance;
        double expResult;
        double result;
        // Test 1
        instance = new V2D_LineSegment_d(pP0P0, pP1P0);
        expResult = 1d;
        result = instance.getLength2();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        instance = new V2D_LineSegment_d(pP0P0, pP2P0);
        expResult = 4d;
        result = instance.getLength2();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V2D_LineSegment_d covered by
 {@link #testGetDistanceSquared_V2D_Point_d_int()}.
     */
    @Test
    public void testGetDistance_V2D_Point_d() {
        System.out.println("getDistance");
        double epsilon = 1d / 1000000d;
        V2D_LineSegment_d l = new V2D_LineSegment_d(pP0P0, pP2P0);
        V2D_Point_d instance = pP1P1;
        double expResult = 1d;
        double result = l.getDistance(instance, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        instance = pN1N1;
        result = l.getDistance(instance, epsilon);
        expResult = Math.sqrt(2d);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 3
        instance = pP2P2;
        expResult = 2d;
        result = l.getDistance(instance, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 4
        instance = pP2P2;
        l = new V2D_LineSegment_d(pP0P0, pP1P0);
        expResult = Math.sqrt(5d);
        result = l.getDistance(instance, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V2D_LineSegment_d.
     */
    @Test
    public void testGetDistanceSquared_V2D_Point_d() {
        System.out.println("getDistance");
        double epsilon = 1d / 1000000d;
        V2D_LineSegment_d instance;
        V2D_Point_d p;
        double expResult;
        double result;
        // Test 1
        instance = new V2D_LineSegment_d(pP0P0, pP1P0);
        p = pP0P0;
        expResult = 0d;
        result = instance.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        p = pP1P0;
        expResult = 0d;
        result = instance.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 3
        instance = new V2D_LineSegment_d(pP0P0, pP2P0);
        p = pP1P0;
        expResult = 0d;
        result = instance.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 4
        p = pP0P1;
        expResult = 1d;
        result = instance.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 5
        p = pN1N1;
        expResult = 2d;
        result = instance.getDistanceSquared(pN1N1, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 6
        p = pN2N2;
        expResult = 8d;
        result = instance.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 7
        p = pN1P0;
        expResult = 1d;
        result = instance.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 8
        p = pN1P1;
        expResult = 2d;
        result = instance.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V2D_LineSegment_d.
     */
    @Test
    public void testGetDistanceSquared_V2D_LineSegment_d() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 1000000d;
        V2D_LineSegment_d l;
        V2D_LineSegment_d instance;
        double expResult;
        double result;
        // Test 1
        l = new V2D_LineSegment_d(pP0P0, pP1P0);
        instance = new V2D_LineSegment_d(pP0P1, pP1P1);
        expResult = 1d;
        result = instance.getDistanceSquared(l, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        l = new V2D_LineSegment_d(pP0P0, pP1P0);
        instance = new V2D_LineSegment_d(pN1P0, pN1P1);
        expResult = 1d;
        result = instance.getDistanceSquared(l, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 3
        expResult = 1d;
        result = l.getDistanceSquared(instance, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 4
        l = new V2D_LineSegment_d(pP1P0, pP0P1);
        instance = new V2D_LineSegment_d(pN1P0, pN1P1);
        expResult = 1d;
        result = l.getDistanceSquared(instance, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 5
        instance = new V2D_LineSegment_d(pP1P0, pP0P1);
        l = new V2D_LineSegment_d(pN1P0, pN1P1);
        expResult = 1d;
        result = l.getDistanceSquared(instance, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 6
        instance = new V2D_LineSegment_d(pP0P0, pP0P1);
        l = new V2D_LineSegment_d(pN1P0, pN1P1);
        expResult = 1d;
        result = l.getDistanceSquared(instance, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 7
        instance = new V2D_LineSegment_d(pP0P0, pP1P0);
        l = new V2D_LineSegment_d(pN1P0, pN1P1);
        expResult = 1d;
        result = l.getDistanceSquared(instance, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getMidpoint method, of class V2D_LineSegment_d.
     */
    @Test
    public void testGetMidpoint() {
        System.out.println("getMidpoint");
        V2D_LineSegment_d instance;
        V2D_Point_d expResult;
        V2D_Point_d result;
        // Test 1
        instance = new V2D_LineSegment_d(pP0P0, pP2P0);
        expResult = pP1P0;
        result = instance.getMidpoint();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getLineOfIntersect method, of class V2D_Line.
     */
    @Test
    public void testGetLineOfIntersection_V2D_LineDouble() {
        System.out.println("getLineOfIntersection");
        double epsilon = 1d / 1000000d;
        V2D_LineSegment_d l0 = new V2D_LineSegment_d(pP1P0, pP1P1);
        V2D_Line_d l1 = new V2D_Line_d(pP0P0, pP0P1);
        V2D_Geometry_d expResult = new V2D_LineSegment_d(pP0P0, pP1P0);
        V2D_Geometry_d result = l0.getLineOfIntersect(l1, epsilon);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
        // Test 2
        l1 = new V2D_Line_d(pP0P0, pP0P1);
        expResult = new V2D_LineSegment_d(pP0P0, pP1P0);
        result = l0.getLineOfIntersect(l1, epsilon);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
        // Test 3
        l0 = new V2D_LineSegment_d(pP1P0, pP1P1);
        l1 = new V2D_Line_d(pN1P0, pN2P0);
        expResult = null;
        result = l0.getLineOfIntersect(l1, epsilon);
        assertNull(result);
        // Test 4
        l0 = new V2D_LineSegment_d(pP1P0, pP1P1);
        l1 = new V2D_Line_d(pN1P0, pN1P1);
        expResult = new V2D_LineSegment_d(pN1P0, pP1P0);
        result = l0.getLineOfIntersect(l1, epsilon);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
        // Test 5
        l0 = new V2D_LineSegment_d(pP1P0, pP1P1);
        l1 = new V2D_Line_d(pN1P0, pP0P1);
        expResult = new V2D_LineSegment_d(new V2D_Point_d(env, 0.5d, 1.5d), pP1P1);
        result = l0.getLineOfIntersect(l1, epsilon);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
    }

    /**
     * Test of getLineOfIntersect method, of class V2D_Line.
     */
    @Test
    public void testGetLineOfIntersection_V2D_LineSegment_d() {
        System.out.println("getLineOfIntersection");
        double epsilon = 1d / 1000000d;
        V2D_LineSegment_d l0 = new V2D_LineSegment_d(pP1P0, pP1P1);
        V2D_LineSegment_d l1 = new V2D_LineSegment_d(pP0P0, pP0P1);
        V2D_LineSegment_d expResult = new V2D_LineSegment_d(pP0P0, pP1P0);
        V2D_LineSegment_d result = l0.getLineOfIntersect(l1, epsilon);
        assertTrue(expResult.equalsIgnoreDirection(epsilon, result));
        // Test 2
        l1 = new V2D_LineSegment_d(pP0P0, pP1P0);
        result = l0.getLineOfIntersect(l1, epsilon);
        assertNull(result);
        // Test 3
        l0 = new V2D_LineSegment_d(pP0P1, pP0P2);
        l1 = new V2D_LineSegment_d(pN1P0, pN2P0);
        expResult = new V2D_LineSegment_d(pN1P0, pP0P1);
        result = l0.getLineOfIntersect(l1, epsilon);
        assertTrue(expResult.equalsIgnoreDirection(epsilon, result));
        // Test 4
        l0 = new V2D_LineSegment_d(pP1P0, pP0P1);
        l1 = new V2D_LineSegment_d(pN1P0, pN1P1);
        expResult = new V2D_LineSegment_d(pN1P1, pP0P1);
        result = l0.getLineOfIntersect(l1, epsilon);
        assertTrue(expResult.equalsIgnoreDirection(epsilon, result));
    }
}
