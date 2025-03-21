/*
 * Copyright 2025 Centre for Computational Geography.
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
import java.util.HashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_ConvexArea_d;

/**
 *
 * @author Andy Turner
 */
public class V2D_ConvexArea_dTest extends V2D_Test_d {
    
    public V2D_ConvexArea_dTest() {
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

//    /**
//     * Test of getPointsArray method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testGetPointsArray() {
//        System.out.println("getPointsArray");
//        V2D_ConvexArea_d instance = null;
//        V2D_Point_d[] expResult = null;
//        V2D_Point_d[] result = instance.getPointsArray();
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getPoints method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testGetPoints() {
//        System.out.println("getPoints");
//        V2D_ConvexArea_d instance = null;
//        HashMap<Integer, V2D_Point_d> expResult = null;
//        HashMap<Integer, V2D_Point_d> result = instance.getPoints();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of toString method, of class V2D_ConvexArea.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        double epsilon = 0.0000001d;
        V2D_ConvexArea_d instance = new V2D_ConvexArea_d(epsilon,
                pP0P0, pP0P1, pP1P1, pP1P0);
        String expResult = """
                           uk.ac.leeds.ccg.v2d.geometry.d.V2D_ConvexArea_d(
                           points  (
                            (0, V2D_Point_d(offset=V2D_Vector_d(dx=0.0, dy=0.0), rel=V2D_Vector_d(dx=0.0, dy=0.0))),
                            (1, V2D_Point_d(offset=V2D_Vector_d(dx=0.0, dy=0.0), rel=V2D_Vector_d(dx=1.0, dy=0.0))),
                            (2, V2D_Point_d(offset=V2D_Vector_d(dx=0.0, dy=0.0), rel=V2D_Vector_d(dx=1.0, dy=1.0))),
                            (3, V2D_Point_d(offset=V2D_Vector_d(dx=0.0, dy=0.0), rel=V2D_Vector_d(dx=0.0, dy=1.0)))
                            )
                           )""";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

//    /**
//     * Test of toStringSimple method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testToStringSimple() {
//        System.out.println("toStringSimple");
//        V2D_ConvexArea_d instance = null;
//        String expResult = "";
//        String result = instance.toStringSimple();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of equals method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testEquals() {
//        System.out.println("equals");
//        V2D_ConvexArea_d c = null;
//        double epsilon = 0.0;
//        V2D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.equals(c, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getAABB method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testGetAABB() {
//        System.out.println("getAABB");
//        V2D_ConvexArea_d instance = null;
//        V2D_AABB_d expResult = null;
//        V2D_AABB_d result = instance.getAABB();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of simplify method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testSimplify() {
//        System.out.println("simplify");
//        double epsilon = 0.0;
//        V2D_ConvexArea_d instance = null;
//        V2D_FiniteGeometry_d expResult = null;
//        V2D_FiniteGeometry_d result = instance.simplify(epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testIntersects_V2D_Point_d_double() {
//        System.out.println("intersects");
//        V2D_Point_d pt = null;
//        double epsilon = 0.0;
//        V2D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(pt, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects0 method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testIntersects0_V2D_Point_d_double() {
//        System.out.println("intersects0");
//        V2D_Point_d pt = null;
//        double epsilon = 0.0;
//        V2D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects0(pt, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of contains method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testContains_V2D_Point_d_double() {
//        System.out.println("contains");
//        V2D_Point_d pt = null;
//        double epsilon = 0.0;
//        V2D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.contains(pt, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of contains method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testContains_V2D_LineSegment_d_double() {
//        System.out.println("contains");
//        V2D_LineSegment_d l = null;
//        double epsilon = 0.0;
//        V2D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.contains(l, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of contains method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testContains_V2D_Triangle_d_double() {
//        System.out.println("contains");
//        V2D_Triangle_d t = null;
//        double epsilon = 0.0;
//        V2D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.contains(t, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of contains method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testContains_V2D_Rectangle_d_double() {
//        System.out.println("contains");
//        V2D_Rectangle_d r = null;
//        double epsilon = 0.0;
//        V2D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.contains(r, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of contains method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testContains_V2D_ConvexArea_d_double() {
//        System.out.println("contains");
//        V2D_ConvexArea_d ch = null;
//        double epsilon = 0.0;
//        V2D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.contains(ch, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testIntersects_V2D_LineSegment_d_double() {
//        System.out.println("intersects");
//        V2D_LineSegment_d l = null;
//        double epsilon = 0.0;
//        V2D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(l, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects0 method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testIntersects0_V2D_LineSegment_d_double() {
//        System.out.println("intersects0");
//        V2D_LineSegment_d l = null;
//        double epsilon = 0.0;
//        V2D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects0(l, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testIntersects_V2D_Triangle_d_double() {
//        System.out.println("intersects");
//        V2D_Triangle_d t = null;
//        double epsilon = 0.0;
//        V2D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(t, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects0 method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testIntersects0_V2D_Triangle_d_double() {
//        System.out.println("intersects0");
//        V2D_Triangle_d t = null;
//        double epsilon = 0.0;
//        V2D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects0(t, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testIntersects_V2D_Rectangle_d_double() {
//        System.out.println("intersects");
//        V2D_Rectangle_d r = null;
//        double epsilon = 0.0;
//        V2D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(r, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects0 method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testIntersects0_V2D_Rectangle_d_double() {
//        System.out.println("intersects0");
//        V2D_Rectangle_d r = null;
//        double epsilon = 0.0;
//        V2D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects0(r, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testIntersects_V2D_ConvexArea_d_double() {
//        System.out.println("intersects");
//        V2D_ConvexArea_d ch = null;
//        double epsilon = 0.0;
//        V2D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(ch, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects0 method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testIntersects0_V2D_ConvexArea_d_double() {
//        System.out.println("intersects0");
//        V2D_ConvexArea_d ch = null;
//        double epsilon = 0.0;
//        V2D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects0(ch, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getArea method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testGetArea() {
//        System.out.println("getArea");
//        V2D_ConvexArea_d instance = null;
//        double expResult = 0.0;
//        double result = instance.getArea();
//        assertEquals(expResult, result, 0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of rotate method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testRotate() {
//        System.out.println("rotate");
//        V2D_Point_d pt = null;
//        double theta = 0.0;
//        V2D_ConvexArea_d instance = null;
//        V2D_ConvexArea_d expResult = null;
//        V2D_ConvexArea_d result = instance.rotate(pt, theta);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of rotateN method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testRotateN() {
//        System.out.println("rotateN");
//        V2D_Point_d pt = null;
//        double theta = 0.0;
//        V2D_ConvexArea_d instance = null;
//        V2D_ConvexArea_d expResult = null;
//        V2D_ConvexArea_d result = instance.rotateN(pt, theta);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testIntersects_V2D_AABB_d_double() {
//        System.out.println("intersects");
//        V2D_AABB_d aabb = null;
//        double epsilon = 0.0;
//        V2D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(aabb, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects0 method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testIntersects0_V2D_AABB_d_double() {
//        System.out.println("intersects0");
//        V2D_AABB_d aabb = null;
//        double epsilon = 0.0;
//        V2D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects0(aabb, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isTriangle method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testIsTriangle() {
//        System.out.println("isTriangle");
//        V2D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.isTriangle();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isRectangle method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testIsRectangle() {
//        System.out.println("isRectangle");
//        double epsilon = 0.0;
//        V2D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.isRectangle(epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getGeometry method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testGetGeometry_double_ArrayList() {
//        System.out.println("getGeometry");
//        double epsilon = 0.0;
//        ArrayList<V2D_Point_d> pts = null;
//        V2D_FiniteGeometry_d expResult = null;
//        V2D_FiniteGeometry_d result = V2D_ConvexArea_d.getGeometry(epsilon, pts);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getGeometry method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testGetGeometry_double_V2D_Point_dArr() {
//        System.out.println("getGeometry");
//        double epsilon = 0.0;
//        V2D_Point_d[] pts = null;
//        V2D_FiniteGeometry_d expResult = null;
//        V2D_FiniteGeometry_d result = V2D_ConvexArea_d.getGeometry(epsilon, pts);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getTriangles method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testGetTriangles() {
//        System.out.println("getTriangles");
//        V2D_ConvexArea_d instance = null;
//        ArrayList<V2D_Triangle_d> expResult = null;
//        ArrayList<V2D_Triangle_d> result = instance.getTriangles();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getEdges method, of class V2D_ConvexArea_d.
//     */
//    @Test
//    public void testGetEdges() {
//        System.out.println("getEdges");
//        V2D_ConvexArea_d instance = null;
//        HashMap<Integer, V2D_LineSegment_d> expResult = null;
//        HashMap<Integer, V2D_LineSegment_d> result = instance.getEdges();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
