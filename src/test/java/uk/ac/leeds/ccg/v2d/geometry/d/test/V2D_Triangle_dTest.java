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

import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.arithmetic.Math_Double;
import uk.ac.leeds.ccg.v2d.core.d.V2D_Environment_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_ConvexArea_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_AABB_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_FiniteGeometry_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_Geometry_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_Line_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_LineSegment_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_Point_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_Ray_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_Triangle_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_Vector_d;

/**
 * Test of V2D_Triangle_d class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_Triangle_dTest extends V2D_Test_d {

    public V2D_Triangle_dTest() {
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
     * Test of intersects method, of class V2D_Triangle_d.
     */
    @Test
    public void testIntersects0_3args() {
        System.out.println("intersects0");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Point_d pt = pP0N1;
        V2D_Triangle_d instance = new V2D_Triangle_d(pP0P2, pP0N2, pP2P0);
        assertTrue(instance.intersects0(pt, epsilon));
        pt = pN1P0;
        assertFalse(instance.intersects0(pt, epsilon));
        instance = new V2D_Triangle_d(pP0P0, pP0P1, pP1P1);
        pt = pP2P2;
        assertFalse(instance.intersects0(pt, epsilon));
        
    }

    /**
     * Test of intersects method, of class V2D_Triangle_d.
     */
    @Test
    public void testIntersects0_2args() {
        System.out.println("intersects0");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_LineSegment_d ls;
        V2D_Triangle_d instance;
        instance = new V2D_Triangle_d(pP0P0, pP0P1, pP1P1);
        ls = new V2D_LineSegment_d(pP2P2, pP2P1);
        assertFalse(instance.intersects0(ls, epsilon));
        
    }

    /**
     * Test of intersects method, of class V2D_Triangle_d.
     */
    @Test
    public void testIntersects_2args() {
        System.out.println("intersects");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Point_d pt = pP0P0;
        V2D_Triangle_d instance = new V2D_Triangle_d(pN1N1, pP0P2, pP1N1);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 2
        pt = pP0P1;
        assertTrue(instance.intersects(pt, epsilon));
        // Test 3
        pt = pP1P2;
        assertFalse(instance.intersects(pt, epsilon));
        // Test 4
        pt = pN1P2;
        assertFalse(instance.intersects(pt, epsilon));
    }
    
    /**
     * Test of intersects method, of class V2D_Triangle_d.
     */
    @Test
    public void testIntersects_2args2() {
        System.out.println("intersects");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Triangle_d t = new V2D_Triangle_d(pN1N1, pP0P1, pP1N1);
        V2D_Triangle_d instance = new V2D_Triangle_d(pN1N1, pP0P1, pP1N1);
        assertTrue(instance.intersects(t, epsilon));        
        // Test 2
        t  = new V2D_Triangle_d(pN2N2, pP0P2, pP2N2);
        assertTrue(instance.intersects(t, epsilon));
    }

    /**
     * Test of getAABB method, of class V2D_Triangle_d.
     */
    @Test
    public void testGetAABB() {
        System.out.println("getAABB");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Triangle_d instance = new V2D_Triangle_d(pP0P0, pP0P1, pP1P0);
        V2D_AABB_d expResult = new V2D_AABB_d(pP0P0, pP0P1, pP1P0);
        V2D_AABB_d result = instance.getAABB();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getArea method, of class V2D_Triangle_d.
     */
    @Test
    public void testGetArea() {
        System.out.println("getArea");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Triangle_d instance = new V2D_Triangle_d(pP0P0, pP0P1, pP1P0);
        double expResult = 1d / 2d;
        double result = instance.getArea();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getPerimeter method, of class V2D_Triangle_d.
     */
    @Test
    public void testGetPerimeter() {
        System.out.println("getPerimeter");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Triangle_d instance = new V2D_Triangle_d(pP0P0, pP0P1, pP1P0);
        double expResult = 2d + Math.sqrt(2);
        double result = instance.getPerimeter();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        expResult = 2d + Math.sqrt(2);
        result = instance.getPerimeter();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getIntersect method, of class V2D_Triangle_d.
     */
    @Test
    public void testGetIntersect_V2D_LineDouble() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V2D_Line_d l = new V2D_Line_d(pP1N1, pP1P2);
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Triangle_d instance = new V2D_Triangle_d(pP0P0, pP1P1, pP2P0);
        V2D_Geometry_d expResult = new V2D_LineSegment_d(pP1P0, pP1P1);
        V2D_Geometry_d result = instance.getIntersect(l, epsilon);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
        // Test 2
        instance = new V2D_Triangle_d(env, P0P0, P1P0, P1P2, P2P0);
        l = new V2D_Line_d(pP1P1, pP1N1);
        result = instance.getIntersect(l, epsilon);
        expResult = new V2D_LineSegment_d(pP1P0, pP1P2);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
        // Test 3
        l = new V2D_Line_d(pP1P0, pP2P0);
        expResult = new V2D_LineSegment_d(pP1P0, pP2P0);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
        // Test 4
        l = new V2D_Line_d(pP1P0, pP2P0);
        expResult = new V2D_LineSegment_d(pP1P0, pP2P0);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
    }

    /**
     * Test of getCentroid method, of class V2D_Triangle_d.
     */
    @Test
    public void testGetCentroid() {
        System.out.println("getCentroid");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Triangle_d instance;
        V2D_Point_d expResult;
        V2D_Point_d result;
        // Test
        instance = new V2D_Triangle_d(pP0P0, pP1P0, pP1P1);
        expResult = new V2D_Point_d(env, 2d / 3d, 1d / 3d);
        result = instance.getCentroid();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of toString method, of class V2D_Triangle_d.
     */
    @Test
    public void testToString() {
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        System.out.println("toString");
        V2D_Triangle_d instance = new V2D_Triangle_d(env, P0P0, P1P0, P0P1, P0P0);
        String expResult = """
                           V2D_Triangle_d(
                            offset=(V2D_Vector_d(dx=0.0, dy=0.0)),
                            p=(V2D_Vector_d(dx=1.0, dy=0.0)),
                            q=(V2D_Vector_d(dx=0.0, dy=1.0)),
                            r=(V2D_Vector_d(dx=0.0, dy=0.0)))""";
        String result = instance.toString();
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of intersects method, of class V2D_Triangle_d.
     */
    @Test
    public void testIsIntersectedBy_V2D_Point_d() {
        System.out.println("intersects");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Point_d pt;
        V2D_Triangle_d instance;
        instance = new V2D_Triangle_d(env, P0P0, P1P0, P0P1, P0P0);
        assertTrue(instance.intersects(pP1P0, epsilon));
        assertTrue(instance.intersects(pP0P1, epsilon));
        assertTrue(instance.intersects(pP0P0, epsilon));
        pt = new V2D_Point_d(env, P1P0, P0P0);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 5
        pt = new V2D_Point_d(env, P0P1, P0P0);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 6
        instance = new V2D_Triangle_d(env, P0P0, P1P0, P1P2, P2P0);
        assertTrue(instance.intersects(pP1P2, epsilon));
        assertTrue(instance.intersects(pP1P1, epsilon));
        assertFalse(instance.intersects(pN1P0, epsilon));
    }

    /**
     * Test of getIntersect method, of class V2D_Triangle_d.
     */
    @Test
    public void testGetIntersect_V2D_LineSegmentDouble() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_LineSegment_d l;
        V2D_Triangle_d instance;
        V2D_Geometry_d expResult;
        V2D_Geometry_d result;
        // Test 1
        instance = new V2D_Triangle_d(pP1P0, pP1P2, pP2P0);
        l = new V2D_LineSegment_d(pP1P1, pP1N1);
        expResult = new V2D_LineSegment_d(pP1P1, pP1P0);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
        // Test 2
        l = new V2D_LineSegment_d(pP1P0, pP2P0);
        expResult = new V2D_LineSegment_d(pP1P0, pP2P0);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
        // Test 3
        l = new V2D_LineSegment_d(pP1P0, pP2P0);
        expResult = new V2D_LineSegment_d(pP1P0, pP2P0);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
        // Test 4
        l = new V2D_LineSegment_d(pP2N2, pP2P1);
        instance = new V2D_Triangle_d(env, P0P0, P2P2,
                new V2D_Vector_d(4d, 0d, 0d));
        expResult = new V2D_LineSegment_d(pP2P0, pP2P1);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
        // Test 5
        l = new V2D_LineSegment_d(pP0P0, pP0P1);
        instance = new V2D_Triangle_d(pN2N2, pP0P2, pP2N2);
        expResult = new V2D_LineSegment_d(pP0P0, pP0P1);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
        // Test 6
        l = new V2D_LineSegment_d(new V2D_Point_d(env, 4d, -2d), pP2P0);
        instance = new V2D_Triangle_d(pP0P0, new V2D_Point_d(env, 4d, 0d), new V2D_Point_d(env, 2d, -4d));
        expResult = new V2D_LineSegment_d(pP2P0, new V2D_Point_d(env, 10d/3d, - 4d/3d));
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
    }

    /**
     * Test of equals method, of class V2D_Triangle_d.
     */
    @Test
    public void testEquals_V2D_Triangle_d() {
        System.out.println("equals");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Triangle_d t = new V2D_Triangle_d(pP1P0, pP1P2, pP2P0);
        V2D_Triangle_d instance = new V2D_Triangle_d(env, P1P0, P0P0, P0P2, P1P0);
        boolean result = instance.equals(t, epsilon);
        assertTrue(result);
        // Test 2
        instance = new V2D_Triangle_d(env, P1P0, P0P2, P0P0, P1P0);
        result = instance.equals(t, epsilon);
        assertTrue(result);
        // Test 3
        instance = new V2D_Triangle_d(env, P1P0, P0P2, P1P0, P0P0);
        result = instance.equals(t, epsilon);
        assertTrue(result);
        // Test 4
        instance = new V2D_Triangle_d(env, P1P0, P1P0, P0P2, P0P0);
        result = instance.equals(t, epsilon);
        assertTrue(result);
        // Test 4
        instance = new V2D_Triangle_d(env, P1P0, P1P0, P0P2, P0P0);
        t = new V2D_Triangle_d(env, P0P0, instance);
        result = instance.equals(t, epsilon);
        assertTrue(result);
        
        
    }

    /**
     * Test of rotate method, of class V2D_Triangle_d.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        double theta;
        V2D_Triangle_d instance;
        V2D_Triangle_d result;
        V2D_Triangle_d expResult;
        double Pi = Math.PI;
        // Test 1
        instance = new V2D_Triangle_d(pP1P0, pP0P1, pP1P1);
        V2D_Point_d pt = pP0P0;
        theta = Pi;
        result = instance.rotate(pt, theta);
        expResult = new V2D_Triangle_d(pN1P0, pN1N1, pP0N1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 2
        instance = new V2D_Triangle_d(pP1P0, pP0P1, pP1P1);
        theta = Pi / 2d;
        result = instance.rotate(pt, theta);
        expResult = new V2D_Triangle_d(pP0N1, pP1P0, pP1N1);
        //expResult = new V2D_Triangle_d(pN1P0, pN1P1, pP0P1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 3
        instance = new V2D_Triangle_d(pP2P0, pP0P2, pP2P2);
        theta = Pi;
        result = instance.rotate(pt,  theta);
        expResult = new V2D_Triangle_d(pN2P0, pN2N2, pP0N2);
        assertTrue(expResult.equals(result, epsilon));
        // Test 4
        instance = new V2D_Triangle_d(pP2P0, pP0P2, pP2P2);
        theta = Pi / 2d;
        result = instance.rotate(pt, theta);
        expResult = new V2D_Triangle_d(pP0N2, pP2P0, pP2N2);
        assertTrue(expResult.equals(result, epsilon));
        // Test 5
        instance = new V2D_Triangle_d(pP2P0, pP0P2, pP2P2);
        theta = 3d * Pi / 2d;
        result = instance.rotate(pt, theta);
        expResult = new V2D_Triangle_d(pN2P0, pN2P2, pP0P2);
        assertTrue(expResult.equals(result, epsilon));
        // Test 5
        instance = new V2D_Triangle_d(pP1P0, pP0P1, pP1P1);
        instance.translate(P1P0);
        theta = Pi;
        result = instance.rotate(pt, theta);
        expResult = new V2D_Triangle_d(pN2P0, pN2N1, pN1N1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 6
        instance = new V2D_Triangle_d(pP1P0, pP0P1, pP1P1);
        theta = Pi;
        result = instance.rotate(pP0P1, theta);
        expResult = new V2D_Triangle_d(pN1P1, pN1P2, pP0P1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 7
        instance = new V2D_Triangle_d(pP1P0, pP0P1, pP1P1);
        theta = Pi / 2d;
        result = instance.rotate(pP0P1, theta);
        expResult = new V2D_Triangle_d(pP0P0, pN1P0, pP0P1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 8
        instance = new V2D_Triangle_d(pP2P0, pP0P2, pP2P2);
        theta = Pi;
        result = instance.rotate(pP0P1, theta);
        expResult = new V2D_Triangle_d(pN2P0, pP0P0, pN2P2);
        assertTrue(expResult.equals(result, epsilon));
    }

    /**
     * Test of getIntersect method, of class V2D_Triangle_d.
     */
    @Test
    public void testGetIntersect_V2D_RayDouble() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Triangle_d t;
        V2D_Ray_d r;
        V2D_Geometry_d expResult;
        V2D_Geometry_d result;
        // Test 1
        t = new V2D_Triangle_d(pP0P1, pP1P0, pP1P1);
        r = new V2D_Ray_d(pP0P0, pP1P0);
        result = t.getIntersect(r, epsilon);
        expResult = pP1P0;
        assertTrue(((V2D_Point_d) expResult).equals((V2D_Point_d) result));
        // Test 2
        t = new V2D_Triangle_d(pP0N2, pP0P2, pP2P0);
        r = new V2D_Ray_d(pP1P0, pP2P0);
        result = t.getIntersect(r, epsilon);
        expResult = new V2D_LineSegment_d(pP1P0, pP2P0);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
        // Test 3
        r = new V2D_Ray_d(pN1P0, pP2P0);
        result = t.getIntersect(r, epsilon);
        expResult = new V2D_LineSegment_d(pP0P0, pP2P0);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
//        // Test 4
//        r = new V2D_Ray_d(pN2P0N2, pP0P0P0);
//        result = t.getIntersect(r, epsilon);
//        expResult = pP0P0P0;
//        assertTrue(((V2D_Point_d) expResult).equals((V2D_Point_d) result));
//        // Test 5
//        r = new V2D_Ray_d(pN2P0N2, pP0N1P0);
//        expResult = pP0N1P0;
//        result = t.getIntersect(r, epsilon);
//        assertTrue(((V2D_Point_d) expResult).equals((V2D_Point_d) result));
//        // Test 6
//        r = new V2D_Ray_d(pN2P0N2, pN1P0P0);
//        assertNull(t.getIntersect(r, epsilon));
//        // Test 7
//        t = new V2D_Triangle_d(pP0N2P0, pP0P2P0, pP2P0P0);
//        r = new V2D_Ray_d(pN2N2N2, pN1N1N1);
//        expResult = pP0P0P0;
//        result = t.getIntersect(r, epsilon);
//        assertTrue(((V2D_Point_d) expResult).equals((V2D_Point_d) result));
//        // Test 8
//        t = new V2D_Triangle_d(pP0N2P0, pP0P2P0, pP2P2P1);
//        r = new V2D_Ray_d(pN2N2N2, pN1N1N1);
//        expResult = pP0P0P0;
//        result = t.getIntersect(r, epsilon);
//        assertTrue(((V2D_Point_d) expResult).equals((V2D_Point_d) result));
//        // Test 9
//        t = new V2D_Triangle_d(pN1N2P0, pN1P2P0, pP2P2P0);
//        r = new V2D_Ray_d(pN2N2N2, pN1N1N1);
//        expResult = pP0P0P0;
//        result = t.getIntersect(r, epsilon);
//        assertTrue(((V2D_Point_d) expResult).equals((V2D_Point_d) result));
//        // Test 10
//        t = new V2D_Triangle_d(pN1N2P0, pN1P2P0, pP2P2N1);
//        r = new V2D_Ray_d(pN2N2N2, pN1N1N1);
//        double nq = -1d / 4d;
//        expResult = new V2D_Point_d(nq, nq, nq);
//        result = t.getIntersect(r, epsilon);
//        assertTrue(((V2D_Point_d) expResult).equals((V2D_Point_d) result));
    }

//    /**
//     * For getting a ray from the camera focal point through the centre of the
//     * screen pixel with ID id.
//     *
//     * @param id The ID of the screen pixel.
//     * @return The ray from the camera focal point through the centre of the
//     * screen pixel.
//     */
//    protected V2D_Ray_d getRay(int row, int col, V2D_RectangleDouble screen,
//            V2D_Point_d pt, V2D_Vector_d vd, V2D_Vector_d v2d,
//            double epsilon) {
//        V2D_Vector_d rv = v2d.multiply((double) row);
//        V2D_Vector_d cv = vd.multiply((double) col);
//        V2D_Point_d rcpt = new V2D_Point_d(screen.getP());
//        rcpt.translate(rv.add(cv));
//        return new V2D_Ray_d(pt, rcpt);
//    }

    /**
     * Test of getIntersect method, of class V2D_Triangle_d.
     *
     * Look for some examples here:
     * https://math.stackexchange.com/questions/1220102/how-do-i-find-the-intersection-of-two-2d-triangles
     */
    @Test
    public void testGetIntersect_V2D_Triangle_d() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Triangle_d t = new V2D_Triangle_d(pP0P0, pP0P1, pP1P0);
        V2D_Triangle_d instance = new V2D_Triangle_d(pP0P0, pP0P1, pP1P0);
        V2D_Geometry_d expResult = new V2D_Triangle_d(pP0P0, pP0P1, pP1P0);
        V2D_Geometry_d result = instance.getIntersect(t, epsilon);
        assertTrue(((V2D_Triangle_d) expResult).equals((V2D_Triangle_d) result, epsilon));
        // Test 2
        t = new V2D_Triangle_d(pN1N1, pP0P2, pP2N1);
        instance = new V2D_Triangle_d(pP0P0, pP0P1, pP1P0);
        expResult = new V2D_Triangle_d(pP0P0, pP0P1, pP1P0);
        result = instance.getIntersect(t, epsilon);
        assertTrue(((V2D_Triangle_d) expResult).equals((V2D_Triangle_d) result, epsilon));
        // Test 3
        t = new V2D_Triangle_d(pP0P0, pP0P1, pP1P0);
        instance = new V2D_Triangle_d(pN1N1, pP0P2, pP2N1);
        expResult = new V2D_Triangle_d(pP0P0, pP0P1, pP1P0);
        result = instance.getIntersect(t, epsilon);
        assertTrue(((V2D_Triangle_d) expResult).equals((V2D_Triangle_d) result, epsilon));
        // Test 4
        t = new V2D_Triangle_d(pP0P0, pP2P0, pP2P2);
        instance = new V2D_Triangle_d(pP1P0, pP2P0, pP2P2);
        expResult = new V2D_Triangle_d(pP1P0, pP2P0, pP2P2);
        result = instance.getIntersect(t, epsilon);
        assertTrue(((V2D_Triangle_d) expResult).equals((V2D_Triangle_d) result, epsilon));
        // Test 5
        t = new V2D_Triangle_d(pP0P0, pP2P0, pP2P2);
        instance = new V2D_Triangle_d(pP1P0, pP2P0, new V2D_Point_d(env, 3d, 2d));
        expResult = new V2D_Triangle_d(pP1P0, pP2P0, pP2P1);
        result = instance.getIntersect(t, epsilon);
        assertTrue(((V2D_Triangle_d) expResult).equals((V2D_Triangle_d) result, epsilon));
        // Test 6 - Test 7 Are more complex intersections
        V2D_Point_d origin = new V2D_Point_d(env, 0d, 0d);
        ArrayList<V2D_Triangle_d> expected;
        V2D_Triangle_d t0;
        V2D_Triangle_d t1;
        double theta;
        // Test 6
        t0 = new V2D_Triangle_d(
                new V2D_Point_d(env, -50d, -50d),
                new V2D_Point_d(env, 0d, 50d),
                new V2D_Point_d(env, 50d, -50d));
        theta = Math.PI;
        t1 = t0.rotate(origin, theta);
        expected = new ArrayList<>();
        /** 
         * Using an additional centroid point and connecting each edge with this 
         * would result in 4 triangles.
         */
//        expected.add(new V2D_Triangle_d(
//                new V2D_Point_d(0d, 0d),
//                new V2D_Point_d(-25d, 0d),
//                new V2D_Point_d(0d, 50d)));
//        expected.add(new V2D_Triangle_d(
//                new V2D_Point_d(0d, 0d),
//                new V2D_Point_d(0d, 50d),
//                new V2D_Point_d(25d, 0d)));
//        expected.add(new V2D_Triangle_d(
//                new V2D_Point_d(0d, 0d),
//                new V2D_Point_d(25d, 0d),
//                new V2D_Point_d(0d, -50d)));
//        expected.add(new V2D_Triangle_d(
//                new V2D_Point_d(0d, 0d),
//                new V2D_Point_d(0d, -50d),
//                new V2D_Point_d(-25d, 0d)));
        expected.add(new V2D_Triangle_d(
                new V2D_Point_d(env, 0d, -50d),
                new V2D_Point_d(env, 25d, 0d),
                new V2D_Point_d(env, 0d, 50d)));
        expected.add(new V2D_Triangle_d(
                new V2D_Point_d(env, 0d, 50d),
                new V2D_Point_d(env, -25d, 0d),
                new V2D_Point_d(env, 0d, -50d)));
//        expected.add(new V2D_Triangle_d(
//                new V2D_Point_d(-25d, 0d),
//                new V2D_Point_d(0d, 50d),
//                new V2D_Point_d(25d, 0d)));
//        expected.add(new V2D_Triangle_d(
//                new V2D_Point_d(-25d, 0d),
//                new V2D_Point_d(25d, 0d),
//                new V2D_Point_d(0d, -50d)));
        // Calculate the intersection
        // We are expecting a convex hull with 4 points that can be tested to 
        // see if they are made up of the two triangles as expected.
        V2D_FiniteGeometry_d gi = t0.getIntersect(t1, epsilon);
        ArrayList<V2D_Triangle_d> git = ((V2D_ConvexArea_d) gi).getTriangles();
        for (int i = 0; i < git.size(); i ++) {
            assertTrue(expected.get(i).equals(git.get(i), epsilon));
        }
        // Test 7
        t0 = new V2D_Triangle_d(new V2D_Point_d(env, -30d, -30d),
        new V2D_Point_d(env, 0d, 60d),
        new V2D_Point_d(env, 30d, -30d));
        theta = Math.PI;
        t1 = t0.rotate(origin, theta);
        expected = new ArrayList<>();
        expected.add(new V2D_Triangle_d(
                new V2D_Point_d(env, -10d, -30d),
                new V2D_Point_d(env, 10d, -30d),
                new V2D_Point_d(env, 20d, 0d)));
        expected.add(new V2D_Triangle_d(
                new V2D_Point_d(env, -10d, -30d),
                new V2D_Point_d(env, 20d, 0d),
                new V2D_Point_d(env, 10d, 30d)));
        expected.add(new V2D_Triangle_d(
                new V2D_Point_d(env, -10d, -30d),
                new V2D_Point_d(env, 10d, 30d),
                new V2D_Point_d(env, -10d, 30d)));
        expected.add(new V2D_Triangle_d(
                new V2D_Point_d(env, -10d, -30d),
                new V2D_Point_d(env, -10d, 30d),
                new V2D_Point_d(env, -20d, 0)));
//        expected.add(new V2D_Triangle_d(
//                new V2D_Point_d(-20d, 0d),
//                new V2D_Point_d(-10d, 30d),
//                new V2D_Point_d(20d, 0d)));
//        expected.add(new V2D_Triangle_d(
//                new V2D_Point_d(-20d, 0d),
//                new V2D_Point_d(20d, 0d),
//                new V2D_Point_d(10d, 30d)));
//        expected.add(new V2D_Triangle_d(
//                new V2D_Point_d(-20d, 0d),
//                new V2D_Point_d(10d, 30d),
//                new V2D_Point_d(-10d, -30d)));
//        expected.add(new V2D_Triangle_d(
//                new V2D_Point_d(-20d, 0d),
//                new V2D_Point_d(-10d, -30d),
//                new V2D_Point_d(10d, -30d)));
        // Calculate the intersection
        // Expecting a convex hull with 6 points that can be tested to 
        // see if they are made up of the four triangles as expected.
        gi = t0.getIntersect(t1, epsilon);
        git = ((V2D_ConvexArea_d) gi).getTriangles();
        for (int i = 0; i < git.size(); i ++) {
            assertTrue(expected.get(i).equals(git.get(i), epsilon));
        }
    }

    /**
     * Test of translate method, of class V2D_Triangle_d.
     */
    @Test
    public void testTranslate() {
        System.out.println("translate");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Triangle_d instance = new V2D_Triangle_d(env, P0P0, P1P0, P0P1, P1P1);
        instance.translate(V2D_Vector_d.I);
        V2D_Triangle_d expResult = new V2D_Triangle_d(env, P0P0, P2P0, P1P1, P2P1);
        assertTrue(expResult.equals(instance, epsilon));
        // Test 2
        instance = new V2D_Triangle_d(env, P0P0, P1P0, P0P1, P1P1);
        instance.translate(V2D_Vector_d.IJ);
        expResult = new V2D_Triangle_d(env, P1P1, P1P0, P0P1, P1P1);
        assertTrue(expResult.equals(instance, epsilon));
    }

    /**
     * Test of getGeometry method, of class V2D_Triangle_d.
     */
    @Test
    public void testGetGeometry_3args_1() {
        System.out.println("getGeometry");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Point_d p;
        V2D_Point_d q;
        V2D_Point_d r;
        V2D_Geometry_d expResult;
        V2D_Geometry_d result;
        // Test 1
        p = new V2D_Point_d(pP0P0);
        q = new V2D_Point_d(pP0P0);
        r = new V2D_Point_d(pP0P0);
        expResult = new V2D_Point_d(pP0P0);
        result = V2D_Triangle_d.getGeometry(p, q, r, epsilon);
        assertTrue(((V2D_Point_d) expResult).equals((V2D_Point_d) result));
        // Test 2
        p = new V2D_Point_d(pP1P0);
        q = new V2D_Point_d(pP0P0);
        r = new V2D_Point_d(pP0P0);
        expResult = new V2D_LineSegment_d(pP0P0, pP1P0);
        result = V2D_Triangle_d.getGeometry(p, q, r, epsilon);
        assertTrue(((V2D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V2D_LineSegment_d) result));
        // Test 2
        p = new V2D_Point_d(pP1P0);
        q = new V2D_Point_d(pP0P1);
        r = new V2D_Point_d(pP0P0);
        expResult = new V2D_Triangle_d(pP0P0, pP1P0, pP0P1);
        result = V2D_Triangle_d.getGeometry(p, q, r, epsilon);
        assertTrue(((V2D_Triangle_d) expResult).equals((V2D_Triangle_d) result, epsilon));
    }

    /**
     * Test of getOpposite method, of class V2D_Triangle_d.
     */
    @Test
    public void testGetOpposite() {
        System.out.println("getOpposite");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_LineSegment_d l = new V2D_LineSegment_d(pP0P0, pP1P0);
        V2D_Triangle_d instance = new V2D_Triangle_d(pP0P0, pP1P0, pP0P1);
        V2D_Point_d expResult = pP0P1;
        V2D_Point_d result = instance.getOpposite(l, epsilon);
        assertTrue(expResult.equals(result, epsilon));
    }

    /**
     * Test of getDistance method, of class V2D_Triangle_d covered by
 {@link #testGetDistanceSquared_V2D_Point_d()}.
     */
    @Test
    public void testGetDistance_V2D_Point_d() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V2D_Triangle_d.
     */
    @Test
    public void testGetDistanceSquared_V2D_Point_d() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Point_d p;
        V2D_Triangle_d t;
        double expResult;
        // Test 1
        p = pP0P0;
        t = new V2D_Triangle_d(pP0P0, pP1P0, pP0P1);
        expResult = 0d;
        double result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        p = pP1P1;
        t = new V2D_Triangle_d(pP0P0, pP1P0, pP0P1);
        expResult = 1d / 2d;
        result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 3
        p = pN1N1;
        t = new V2D_Triangle_d(pN2N2, pP2N2, pN2P2);
        expResult = 0d;
        result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 3
        p = new V2D_Point_d(env, -1, -10);
        expResult = 64d;
        result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V2D_Triangle_d covered by
 {@link #testGetDistanceSquared_V2D_LineDouble()}.
     */
    @Test
    public void testGetDistance_V2D_LineDouble() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V2D_Triangle_d.
     */
    @Test
    public void testGetDistanceSquared_V2D_LineDouble() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Line_d l;
        double expResult;
        double result;
        V2D_Triangle_d instance;
        // Test 1
        l = new V2D_Line_d(pP0P0, pP1P0);
        instance = new V2D_Triangle_d(pP0P0, pP1P0, pP0P1);
        expResult = 0d;
        result = instance.getDistanceSquared(l, epsilon);
        assertEquals(expResult, result);
        // Test 2
        l = new V2D_Line_d(pP0P1, pP1P1);
        instance = new V2D_Triangle_d(pN2N2, pP2N2, pN2P2);
        expResult = 0d;
        result = instance.getDistanceSquared(l, epsilon);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V2D_Triangle_d covered by
 {@link #testGetDistanceSquared_V2D_LineSegmentDouble()}.
     */
    @Test
    public void testGetDistance_V2D_LineSegmentDouble() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V2D_Triangle_d.
     */
    @Test
    public void testGetDistanceSquared_V2D_LineSegmentDouble() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_LineSegment_d l;
        V2D_Triangle_d instance;
        double expResult;
        double result;
        // Test 1
        l = new V2D_LineSegment_d(pP0P0, pP1P0);
        instance = new V2D_Triangle_d(pP0P0, pP1P0, pP0P1);
        expResult = 0d;
        result = instance.getDistanceSquared(l, epsilon);
        assertEquals(expResult, result);
        // Test 2
        l = new V2D_LineSegment_d(pP0P0, pP1P0);
        instance = new V2D_Triangle_d(pN2N2, pP2N2, pN2P2);
        expResult = 0d;
        result = instance.getDistanceSquared(l, epsilon);
        assertEquals(expResult, result);
        // Test 3
        l = new V2D_LineSegment_d(pP0P1, pP1P1);
        expResult = 1d / 2d;
        result = instance.getDistanceSquared(l, epsilon);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V2D_Triangle_d covered by
 {@link #testGetDistanceSquared_V2D_PlaneDouble()}.
     */
    @Test
    public void testGetDistance_V2D_PlaneDouble() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistance method, of class V2D_Triangle_d covered by
 {@link #testGetDistanceSquared_V2D_Triangle_d()}.
     */
    @Test
    public void testGetDistance_V2D_Triangle_d() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V2D_Triangle_d.
     */
    @Test
    public void testGetDistanceSquared_V2D_Triangle_d() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Triangle_d t;
        V2D_Triangle_d instance;
        double expResult;
        double result;
        // Test 1
        t = new V2D_Triangle_d(pP0P0, pP1P0, pP0P1);
        instance = new V2D_Triangle_d(pP0P0, pP1P0, pP0P1);
        expResult = 0d;
        result = instance.getDistanceSquared(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        t = new V2D_Triangle_d(pP0P0, pP1P0, pP0P1);
        instance = new V2D_Triangle_d(pP1P0, pP1P1, pP0P1);
        expResult = 0d;
        result = instance.getDistanceSquared(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 3
        t = new V2D_Triangle_d(pP0P0, pP1P0, pP0P1);
        instance = new V2D_Triangle_d(pP1P1, pP1P2, pP0P2);
        expResult = 1d / 2d;
        result = instance.getDistanceSquared(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }
    
    /**
     * Test of getCircumcentre method, of class V2D_Triangle_d.
     */
    @Test
    public void testGetCircumcentre() {
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Point_d a = new V2D_Point_d(env, 3, 2);
        V2D_Point_d b = new V2D_Point_d(env, 1, 4);
        V2D_Point_d c = new V2D_Point_d(env, 5, 4);
        V2D_Triangle_d t = new V2D_Triangle_d(a, b, c);
        V2D_Point_d expResult = new V2D_Point_d(env, 3, 4);
        V2D_Point_d result = t.getCircumcenter();
        assertTrue(result.equals(expResult, epsilon));
        // Test 2
         a = new V2D_Point_d(env, -2, -3);
         b = new V2D_Point_d(env, -1, 0);
         c = new V2D_Point_d(env, 7, -6);
         t = new V2D_Triangle_d(a, b, c);
         expResult = new V2D_Point_d(env, 3, -3);
         result = t.getCircumcenter();
        assertTrue(result.equals(expResult, epsilon));
    }
    
    /**
     * Test of getAngleP method, of class V2D_Triangle_d.
     */
    @Test
    public void testGetAngleP() {
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Triangle_d t = new V2D_Triangle_d(pP0P0, pP0P1, pP1P1);
        double expResult = Math.PI/4d;
        double result = t.getAngleP();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        t = new V2D_Triangle_d(pP0P0, pP1P1, pP1P0);
        expResult = Math.PI/4d;
        result = t.getAngleP();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 3
        t = new V2D_Triangle_d(pP0P0, pP0P1, pP1P0);
        expResult = Math.PI/2d;
        result = t.getAngleP();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 4
        t = new V2D_Triangle_d(pP1P1, pP1P2, pP2P1);
        expResult = Math.PI/2d;
        result = t.getAngleP();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 5
        t = new V2D_Triangle_d(pP1P1, pP2P2, pP2P1);
        expResult = Math.PI/4d;
        result = t.getAngleP();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getAngleQ method, of class V2D_Triangle_d.
     */
    @Test
    public void testGetAngleQ() {
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Triangle_d t = new V2D_Triangle_d(pP0P0, pP0P1, pP1P1);
        double expResult = Math.PI/2d;
        double result = t.getAngleQ();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        t = new V2D_Triangle_d(pP0P0, pP1P1, pP1P0);
        expResult = Math.PI/4d;
        result = t.getAngleQ();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 3
        t = new V2D_Triangle_d(pP0P0, pP0P1, pP1P0);
        expResult = Math.PI/4d;
        result = t.getAngleQ();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 4
        t = new V2D_Triangle_d(pP1P1, pP1P2, pP2P1);
        expResult = Math.PI/4d;
        result = t.getAngleQ();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 5
        t = new V2D_Triangle_d(pP1P1, pP2P2, pP2P1);
        expResult = Math.PI/4d;
        result = t.getAngleQ();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }
    
    /**
     * Test of getAngleR method, of class V2D_Triangle_d.
     */
    @Test
    public void testGetAngleR() {
        double epsilon = 1d / 10000000d;
        V2D_Environment_d env = new V2D_Environment_d(epsilon);
        V2D_Triangle_d t = new V2D_Triangle_d(pP0P0, pP0P1, pP1P1);
        double expResult = Math.PI/4d;
        double result = t.getAngleR();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        t = new V2D_Triangle_d(pP0P0, pP1P1, pP1P0);
        expResult = Math.PI/2d;
        result = t.getAngleR();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 3
        t = new V2D_Triangle_d(pP0P0, pP0P1, pP1P0);
        expResult = Math.PI/4d;
        result = t.getAngleR();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 4
        t = new V2D_Triangle_d(pP1P1, pP1P2, pP2P1);
        expResult = Math.PI/4d;
        result = t.getAngleR();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 5
        t = new V2D_Triangle_d(pP1P1, pP2P2, pP2P1);
        expResult = Math.PI/2d;
        result = t.getAngleR();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }
}
